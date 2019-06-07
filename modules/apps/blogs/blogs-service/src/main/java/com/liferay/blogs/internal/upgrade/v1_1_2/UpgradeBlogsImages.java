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

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.internal.upgrade.v1_1_1.util.BlogsEntryTable;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.File;
import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author István András Dézsi
 */
public class UpgradeBlogsImages extends UpgradeProcess {

	public UpgradeBlogsImages(
		ImageLocalService imageLocalService,
		PortletFileRepository portletFileRepository) {

		_imageLocalService = imageLocalService;
		_portletFileRepository = portletFileRepository;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumnType("BlogsEntry", "smallImageId", "LONG null")) {
			alter(
				BlogsEntryTable.class,
				new UpgradeProcess.AlterColumnType(
					"smallImageId", "LONG null"));
		}

		try (PreparedStatement ps1 = connection.prepareStatement(
				SQLTransformer.transform(
					"select entryId, groupId, companyId, userId, " +
						"smallImageId from BlogsEntry where smallImage = " +
							"[$TRUE$] and smallImageId != 0"));
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update BlogsEntry set smallImageFileEntryId = ?, " +
						"smallImageId = 0 where entryId = ?"));
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long smallImageId = rs.getLong("smallImageId");

				Image smallImage = _imageLocalService.fetchImage(smallImageId);

				if (smallImage == null) {
					continue;
				}

				long entryId = rs.getLong("entryId");
				long groupId = rs.getLong("groupId");

				long companyId = rs.getLong("companyId");

				long userId = PortalUtil.getValidUserId(
					companyId, rs.getLong("userId"));

				byte[] bytes = smallImage.getTextObj();

				String fileName = StringBundler.concat(
					smallImage.getImageId(), StringPool.PERIOD,
					smallImage.getType());

				String mimeType = _getContentType(bytes);

				Folder smallImagefolder = _addFolder(
					userId, groupId, "Small Image");

				FileEntry processedImageFileEntry =
					_portletFileRepository.addPortletFileEntry(
						groupId, userId, BlogsEntry.class.getName(), entryId,
						BlogsConstants.SERVICE_NAME,
						smallImagefolder.getFolderId(), bytes, fileName,
						mimeType, true);

				Folder blogsImagefolder = _addFolder(
					userId, groupId, BlogsConstants.SERVICE_NAME);

				_portletFileRepository.addPortletFileEntry(
					groupId, userId, null, 0, BlogsConstants.SERVICE_NAME,
					blogsImagefolder.getFolderId(), bytes, fileName, mimeType,
					true);

				ps2.setLong(1, processedImageFileEntry.getFileEntryId());
				ps2.setLong(2, entryId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private Folder _addFolder(long userId, long groupId, String folderName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = _portletFileRepository.addPortletRepository(
			groupId, BlogsConstants.SERVICE_NAME, serviceContext);

		return _portletFileRepository.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderName,
			serviceContext);
	}

	private String _getContentType(byte[] bytes) throws IOException {
		File tempFile = FileUtil.createTempFile(bytes);

		try {
			return MimeTypesUtil.getContentType(tempFile);
		}
		finally {
			FileUtil.delete(tempFile);
		}
	}

	private final ImageLocalService _imageLocalService;
	private final PortletFileRepository _portletFileRepository;

}