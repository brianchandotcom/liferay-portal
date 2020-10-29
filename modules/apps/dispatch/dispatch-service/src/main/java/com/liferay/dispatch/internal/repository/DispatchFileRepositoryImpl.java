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

import com.liferay.dispatch.constants.DispatchPortletKeys;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.talend.web.internal.configuration.DispatchTalendConfiguration;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.InputStream;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(
	configurationPid = "com.liferay.dispatch.talend.web.internal.configuration.DispatchTalendConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = DispatchFileRepository.class
)
public class TalendDispatchTaskExecutorHelper
	implements DispatchFileRepository {

	@Override
	public FileEntry addFileEntry(
			long companyId, long userId, long dispatchTriggerId,
			String fileName, long size, String contentType,
			InputStream inputStream)
		throws PortalException {

		_validateFile(fileName, size);

		Company company = _companyLocalService.getCompany(companyId);

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

			return PortletFileRepositoryUtil.fetchPortletFileEntry(
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
	protected void activate(Map<String, Object> properties) {
		_dispatchTalendConfiguration = ConfigurableUtil.createConfigurable(
			DispatchTalendConfiguration.class, properties);
	}

	private FileEntry _addFileEntry(
			long groupId, long userId, long dispatchTriggerId,
			String contentType, InputStream inputStream)
		throws PortalException {

		Folder folder = _getFolder(groupId, userId);

		FileEntry fileEntry = PortletFileRepositoryUtil.fetchPortletFileEntry(
			groupId, folder.getFolderId(), String.valueOf(dispatchTriggerId));

		if (fileEntry != null) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId());
		}

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, DispatchTrigger.class.getName(), dispatchTriggerId,
			DispatchPortletKeys.DISPATCH, folder.getFolderId(), inputStream,
			String.valueOf(dispatchTriggerId), contentType, false);
	}

	private Folder _getFolder(long groupId, long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, DispatchPortletKeys.DISPATCH, serviceContext);

		return PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			TalendDispatchTaskExecutor.DISPATCH_TASK_EXECUTOR_TYPE_TALEND,
			serviceContext);
	}

	private void _validateFile(String fileName, long size)
		throws FileExtensionException, FileSizeException {

		if ((_dispatchTalendConfiguration.fileMaxSize() > 0) &&
			(size > _dispatchTalendConfiguration.fileMaxSize())) {

			throw new FileSizeException("File size exceeds configured limit");
		}

		String extension = StringPool.PERIOD + FileUtil.getExtension(fileName);

		for (String imageExtension :
				_dispatchTalendConfiguration.fileExtensions()) {

			if (Objects.equals(StringPool.STAR, imageExtension) ||
				Objects.equals(imageExtension, extension)) {

				return;
			}
		}

		throw new FileExtensionException(
			"Invalid file extension for " + fileName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TalendDispatchTaskExecutorHelper.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private volatile DispatchTalendConfiguration _dispatchTalendConfiguration;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

}