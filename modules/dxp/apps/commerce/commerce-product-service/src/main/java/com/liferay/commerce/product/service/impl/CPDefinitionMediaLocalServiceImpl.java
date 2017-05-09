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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinitionMedia;
import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.base.CPDefinitionMediaLocalServiceBaseImpl;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPDefinitionMediaLocalServiceImpl
	extends CPDefinitionMediaLocalServiceBaseImpl {

	@Override
	public CPDefinitionMedia addCPDefinitionMedia(
			long cpDefinitionId,long fileEntryId,String ddmContent,
			int priority, long CPMediaTypeId, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition media

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpDefinitionMediaId = counterLocalService.increment();

		CPDefinitionMedia cpDefinitionMedia =
			cpDefinitionMediaPersistence.create(cpDefinitionMediaId);

		cpDefinitionMedia.setUuid(serviceContext.getUuid());
		cpDefinitionMedia.setGroupId(groupId);
		cpDefinitionMedia.setCompanyId(user.getCompanyId());
		cpDefinitionMedia.setUserId(user.getUserId());
		cpDefinitionMedia.setUserName(user.getFullName());
		cpDefinitionMedia.setCPDefinitionId(cpDefinitionId);
		cpDefinitionMedia.setFileEntryId(fileEntryId);
		cpDefinitionMedia.setDDMContent(ddmContent);
		cpDefinitionMedia.setPriority(priority);
		cpDefinitionMedia.setCPMediaTypeId(CPMediaTypeId);

		cpDefinitionMediaPersistence.update(cpDefinitionMedia);

		// Resources

		resourceLocalService.addModelResources(cpDefinitionMedia, serviceContext);

		return cpDefinitionMedia;
	}

	@Override
	public CPDefinitionMedia updateCPDefinitionMedia(
			long cpDefinitionMediaId,String ddmContent, int priority,
			long CPMediaTypeId, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition media

		CPDefinitionMedia cpDefinitionMedia =
			cpDefinitionMediaPersistence.findByPrimaryKey(cpDefinitionMediaId);

		cpDefinitionMedia.setDDMContent(ddmContent);
		cpDefinitionMedia.setPriority(priority);
		cpDefinitionMedia.setCPMediaTypeId(CPMediaTypeId);

		cpDefinitionMediaPersistence.update(cpDefinitionMedia);

		return cpDefinitionMedia;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionMedia deleteCPDefinitionMedia(CPDefinitionMedia cpMediaType)
		throws PortalException {

		// Commerce product definition media

		cpMediaTypePersistence.remove(cpMediaType);

		// Attachments

		long fileEntryId = cpMediaType.getFileEntryId();

		if (fileEntryId != 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntryId);
		}

		return cpMediaType;
	}

	@Override
	public CPMediaType deleteCPMediaType(long cpMediaTypeId)
		throws PortalException {

		CPMediaType cpMediaType = cpMediaTypePersistence.findByPrimaryKey(
			cpMediaTypeId);

		return cpMediaTypeLocalService.deleteCPMediaType(cpMediaType);
	}

	protected Folder doAddFolder(long userId, long groupId, String folderName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, CPConstants.SERVICE_NAME, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderName,
			serviceContext);

		return folder;
	}
}