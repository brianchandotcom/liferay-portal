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

package com.liferay.layout.page.template.admin.web.internal.upgrade.v1_1_0;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rubén Pulido
 */
public class UpgradePreviewFileEntryId extends UpgradeProcess {

	public UpgradePreviewFileEntryId() {
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateDLFileEntryId();
	}

	protected void updateDLFileEntryId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select distinct groupId from LayoutPageTemplateEntry where " +
					"previewFileEntryId > 0");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				Repository repository = _getRepository(groupId);

				Folder folder = _getFolder(repository);

				StringBundler sb = new StringBundler(4);

				sb.append("update DLFileEntry set repositoryId = ?, folderId ");
				sb.append("= ?, treePath = ? where fileEntryId in (select ");
				sb.append("previewFileEntryId from LayoutPageTemplateEntry ");
				sb.append("where previewFileEntryId > 0 and groupId = ?)");

				try (PreparedStatement ps2 = connection.prepareStatement(
						sb.toString())) {

					long folderId = folder.getFolderId();

					ps2.setLong(1, repository.getRepositoryId());
					ps2.setLong(2, folderId);
					ps2.setString(
						3, StringPool.SLASH + folderId + StringPool.SLASH);
					ps2.setLong(4, groupId);

					ps2.executeUpdate();
				}
			}
		}
	}

	private Folder _getFolder(Repository repository) throws PortalException {
		try {
			return PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(), 0,
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return PortletFileRepositoryUtil.addPortletFolder(
			repository.getUserId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
			serviceContext);
	}

	private Repository _getRepository(long groupId) throws PortalException {
		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId,
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES);

		if (repository == null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId,
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
				serviceContext);
		}

		return repository;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePreviewFileEntryId.class);

}