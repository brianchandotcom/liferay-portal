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

package com.liferay.blogs.internal.upgrade.v1_1_2;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portlet.blogs.constants.BlogsConstants;

import java.io.File;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author István András Dézsi
 */
public class UpgradeBlogsImages extends UpgradeProcess {

	protected Folder addFolder(long userId, long groupId, String folderName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, BlogsConstants.SERVICE_NAME, serviceContext);

		return PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderName,
			serviceContext);
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateEntries();
	}

	protected void updateEntries() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select entryId, groupId, smallImageId, userId from " +
					"BlogsEntry where smallImage = TRUE");
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update BlogsEntry set smallImageFileEntryId = ? where " +
						"entryId = ?"));
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long entryId = rs.getLong("entryId");
				long groupId = rs.getLong("groupId");
				long smallImageId = rs.getLong("smallImageId");
				long userId = rs.getLong("userId");

				Image smallImage = ImageLocalServiceUtil.getImage(smallImageId);

				if (smallImage == null) {
					continue;
				}

				byte[] bytes = smallImage.getTextObj();

				StringBundler sb = new StringBundler(3);

				sb.append(smallImage.getImageId());
				sb.append(StringPool.PERIOD);
				sb.append(smallImage.getType());

				String fileName = sb.toString();

				File tempFile = FileUtil.createTempFile(bytes);

				String mimeType = MimeTypesUtil.getContentType(tempFile);

				Folder smallImagefolder = addFolder(
					userId, groupId, "Small Image");

				FileEntry processedImageFileEntry =
					PortletFileRepositoryUtil.addPortletFileEntry(
						groupId, userId, BlogsEntry.class.getName(), entryId,
						BlogsConstants.SERVICE_NAME,
						smallImagefolder.getFolderId(), bytes, fileName,
						mimeType, true);

				Folder blogsImagefolder = addFolder(
					userId, groupId, BlogsConstants.SERVICE_NAME);

				PortletFileRepositoryUtil.addPortletFileEntry(
					groupId, userId, null, 0, BlogsConstants.SERVICE_NAME,
					blogsImagefolder.getFolderId(), bytes, fileName, mimeType,
					true);

				long smallImageFileEntryId =
					processedImageFileEntry.getFileEntryId();

				ps2.setLong(1, smallImageFileEntryId);

				ps2.setLong(2, entryId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}