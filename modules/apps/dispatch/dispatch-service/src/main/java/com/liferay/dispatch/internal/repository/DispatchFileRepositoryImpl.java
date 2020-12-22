/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dispatch.internal.repository;

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.repository.DispatchFileValidator;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.InputStream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = DispatchFileRepository.class
)
public class DispatchFileRepositoryImpl implements DispatchFileRepository {

	@Override
	public FileEntry addFileEntry(
			long userId, long dispatchTriggerId, String fileName, long size,
			String contentType, InputStream inputStream)
		throws PortalException {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.getDispatchTrigger(dispatchTriggerId);

		DispatchFileValidator dispatchFileValidator = _getFileValidator(
			dispatchTrigger.getDispatchTaskExecutorType());

		dispatchFileValidator.validateExtension(fileName);
		dispatchFileValidator.validateSize(size);

		Company company = _companyLocalService.getCompany(
			dispatchTrigger.getCompanyId());

		return _addFileEntry(
			company.getGroupId(), userId, dispatchTriggerId, contentType,
			inputStream);
	}

	@Override
	public FileEntry fetchFileEntry(long dispatchTriggerId) {
		try {
			DispatchTrigger dispatchTrigger =
				_dispatchTriggerLocalService.getDispatchTrigger(
					dispatchTriggerId);

			Company company = _companyLocalService.getCompany(
				dispatchTrigger.getCompanyId());

			Folder folder = _getFolder(
				company.getGroupId(), dispatchTrigger.getUserId());

			return _portletFileRepository.fetchPortletFileEntry(
				company.getGroupId(), folder.getFolderId(),
				String.valueOf(dispatchTriggerId));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to fetch file entry", portalException);
			}
		}

		return null;
	}

	@Override
	public String fetchFileEntryName(long dispatchTriggerId) {
		FileEntry fileEntry = fetchFileEntry(dispatchTriggerId);

		if (fileEntry != null) {
			return fileEntry.getFileName();
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dispatchFileValidatorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DispatchFileValidator.class,
				"dispatch.file.validator.type");
	}

	@Deactivate
	protected void deactivate() {
		_dispatchFileValidatorServiceTrackerMap.close();
	}

	private FileEntry _addFileEntry(
			long groupId, long userId, long dispatchTriggerId,
			String contentType, InputStream inputStream)
		throws PortalException {

		Folder folder = _getFolder(groupId, userId);

		FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
			groupId, folder.getFolderId(), String.valueOf(dispatchTriggerId));

		if (fileEntry != null) {
			_portletFileRepository.deletePortletFileEntry(
				fileEntry.getFileEntryId());
		}

		return _portletFileRepository.addPortletFileEntry(
			groupId, userId, DispatchTrigger.class.getName(), dispatchTriggerId,
			DispatchPortletKeys.DISPATCH, folder.getFolderId(), inputStream,
			String.valueOf(dispatchTriggerId), contentType, false);
	}

	private DispatchFileValidator _getFileValidator(String taskExecutorType) {
		if (_dispatchFileValidatorServiceTrackerMap.containsKey(
				taskExecutorType)) {

			return _dispatchFileValidatorServiceTrackerMap.getService(
				taskExecutorType);
		}

		return _dispatchFileValidatorServiceTrackerMap.getService("default");
	}

	private Folder _getFolder(long groupId, long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = _portletFileRepository.addPortletRepository(
			groupId, DispatchPortletKeys.DISPATCH, serviceContext);

		return _portletFileRepository.addPortletFolder(
			userId, repository.getRepositoryId(),
			DispatchConstants.REPOSITORY_DEFAULT_PARENT_FOLDER_ID,
			DispatchConstants.REPOSITORY_FOLDER_NAME, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchFileRepositoryImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private ServiceTrackerMap<String, DispatchFileValidator>
		_dispatchFileValidatorServiceTrackerMap;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}