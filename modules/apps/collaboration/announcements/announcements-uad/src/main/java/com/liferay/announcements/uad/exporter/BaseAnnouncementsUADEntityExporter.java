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

package com.liferay.announcements.uad.exporter;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.user.associated.data.exporter.BaseUADEntityExporter;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
public abstract class BaseAnnouncementsUADEntityExporter
	extends BaseUADEntityExporter {

	protected Folder getFolder(long companyId, String folderName)
		throws PortalException {

		Group guestGroup = groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				guestGroup.getGroupId(),
				AnnouncementsPortletKeys.ANNOUNCEMENTS);

		ServiceContext serviceContext = new ServiceContext();

		if (repository == null) {
			repository = PortletFileRepositoryUtil.addPortletRepository(
				guestGroup.getGroupId(), AnnouncementsPortletKeys.ANNOUNCEMENTS,
				serviceContext);
		}

		Folder folder = null;

		try {
			folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderName);
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			folder = PortletFileRepositoryUtil.addPortletFolder(
				userLocalService.getDefaultUserId(companyId),
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderName,
				serviceContext);
		}

		return folder;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAnnouncementsUADEntityExporter.class);

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected UserLocalService userLocalService;

}