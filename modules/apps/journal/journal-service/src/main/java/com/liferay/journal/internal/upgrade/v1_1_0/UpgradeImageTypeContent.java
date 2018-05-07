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

package com.liferay.journal.internal.upgrade.v1_1_0;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeImageTypeContent extends UpgradeProcess {

	public UpgradeImageTypeContent(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	protected String convertTypeImageElements(
			long userId, long groupId, String content, long resourcePrimKey)
		throws Exception {

		Document contentDocument = SAXReaderUtil.read(content);

		contentDocument = contentDocument.clone();

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='image']");

		List<Node> imageNodes = xPath.selectNodes(contentDocument);

		for (Node imageNode : imageNodes) {
			Element imageEl = (Element)imageNode;

			List<Element> dynamicContentEls = imageEl.elements(
				"dynamic-content");

			for (Element dynamicContentEl : dynamicContentEls) {
				String id = dynamicContentEl.attributeValue("id");

				if (Validator.isNull(id)) {
					continue;
				}

				long folderId = getFolderId(userId, groupId, resourcePrimKey);

				FileEntry fileEntry = null;

				try {
					fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
						groupId, folderId, id);
				}
				catch (PortalException pe) {
					_log.error(
						StringBundler.concat(
							"Unable to get file entry with group ID ",
							String.valueOf(groupId), ", folder ID ",
							String.valueOf(folderId), ", and file name ", id),
						pe);
				}

				if (fileEntry == null) {
					continue;
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("alt", StringPool.BLANK);
				jsonObject.put("groupId", fileEntry.getGroupId());
				jsonObject.put("name", fileEntry.getFileName());
				jsonObject.put("resourcePrimKey", resourcePrimKey);
				jsonObject.put("title", fileEntry.getTitle());
				jsonObject.put("type", "journal");
				jsonObject.put("uuid", fileEntry.getUuid());

				dynamicContentEl.clearContent();

				dynamicContentEl.addCDATA(jsonObject.toString());
			}
		}

		return contentDocument.formattedString();
	}

	protected void copyJournalArticleImagesToJournalRepository()
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select articleId, articleImageId, groupId from " +
					"JournalArticleImage");
			ResultSet rs1 = ps1.executeQuery()) {

			while (rs1.next()) {
				String articleId = rs1.getString(1);
				long articleImageId = rs1.getLong(2);
				long groupId = rs1.getLong(3);

				try (PreparedStatement ps2 = connection.prepareStatement(
						"select resourcePrimKey, userId from JournalArticle " +
							"where groupId = ? and articleId = ?")) {

					ps2.setLong(1, groupId);
					ps2.setString(2, articleId);

					ResultSet rs2 = ps2.executeQuery();

					if (!rs2.next()) {
						continue;
					}

					long resourcePrimKey = rs2.getLong(1);
					long userId = rs2.getLong(2);

					long folderId = getFolderId(
						userId, groupId, resourcePrimKey);

					FileEntry fileEntry =
						PortletFileRepositoryUtil.fetchPortletFileEntry(
							groupId, folderId, String.valueOf(articleImageId));

					if (fileEntry != null) {
						continue;
					}

					Image image = _imageLocalService.getImage(articleImageId);

					if (image == null) {
						continue;
					}

					PortletFileRepositoryUtil.addPortletFileEntry(
						groupId, userId, JournalArticle.class.getName(),
						resourcePrimKey, JournalConstants.SERVICE_NAME,
						folderId, image.getTextObj(),
						String.valueOf(articleImageId), image.getType(), false);
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		copyJournalArticleImagesToJournalRepository();

		updateContentImages();
	}

	protected long getFolderId(long userId, long groupId, long resourcePrimKey)
		throws PortalException {

		long repositoryId = getRepositoryId(groupId);

		try {
			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(resourcePrimKey));

			return folder.getFolderId();
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get folder for " +
						String.valueOf(resourcePrimKey),
					nsfe);
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(resourcePrimKey), serviceContext);

		return folder.getFolderId();
	}

	protected long getRepositoryId(long groupId) throws PortalException {
		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, JournalConstants.SERVICE_NAME);

		if (repository != null) {
			return repository.getRepositoryId();
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, JournalConstants.SERVICE_NAME, serviceContext);

		return repository.getRepositoryId();
	}

	protected void updateContentImages() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select content, groupId, id_, resourcePrimKey, userId from " +
					"JournalArticle where content like ?")) {

			ps1.setString(1, "%type=\"image\"%");

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				String content = rs1.getString(1);
				long groupId = rs1.getLong(2);
				long id = rs1.getLong(3);
				long resourcePrimKey = rs1.getLong(4);
				long userId = rs1.getLong(5);

				String newContent = convertTypeImageElements(
					userId, groupId, content, resourcePrimKey);

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update JournalArticle set content = ? where id_ " +
								"= ?")) {

					ps2.setString(1, newContent);
					ps2.setLong(2, id);

					ps2.executeUpdate();
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeImageTypeContent.class);

	private final ImageLocalService _imageLocalService;

}