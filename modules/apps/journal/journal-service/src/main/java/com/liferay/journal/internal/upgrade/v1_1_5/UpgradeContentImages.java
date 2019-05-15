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

package com.liferay.journal.internal.upgrade.v1_1_5;

import com.liferay.journal.internal.upgrade.util.JournalArticleImageUpgradeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
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
 * @author Jürgen Kappler
 */
public class UpgradeContentImages extends UpgradeProcess {

	public UpgradeContentImages(
		JournalArticleImageUpgradeUtil journalArticleImageUpgradeUtil) {

		_journalArticleImageUpgradeUtil = journalArticleImageUpgradeUtil;
	}

	protected String convertTypeImageElements(
			long userId, long groupId, long companyId, String content,
			long resourcePrimKey)
		throws Exception {

		Document contentDocument = SAXReaderUtil.read(content);

		contentDocument = contentDocument.clone();

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='image']");

		List<Node> imageNodes = xPath.selectNodes(contentDocument);

		for (Node imageNode : imageNodes) {
			Element imageElement = (Element)imageNode;

			List<Element> dynamicContentElements = imageElement.elements(
				"dynamic-content");

			for (Element dynamicContentElement : dynamicContentElements) {
				long fileEntryId = GetterUtil.getLong(
					dynamicContentElement.attributeValue("fileEntryId"));

				String id = dynamicContentElement.attributeValue("id");

				FileEntry fileEntry = null;

				if (Validator.isNotNull(id)) {
					fileEntry = _getFileEntryById(
						userId, groupId, companyId, resourcePrimKey, id);
				}
				else if (fileEntryId > 0) {
					fileEntry = _getFileEntryByFileEntryId(fileEntryId);
				}
				else {
					String data = String.valueOf(
						dynamicContentElement.getData());

					fileEntry =
						_journalArticleImageUpgradeUtil.getFileEntryFromURL(
							data);
				}

				dynamicContentElement.clearContent();

				if (fileEntry == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Deleted dynamic content from file entry " +
								fileEntryId);
					}

					continue;
				}

				dynamicContentElement.addCDATA(
					JSONUtil.put(
						"alt",
						GetterUtil.getString(
							dynamicContentElement.attributeValue("alt"))
					).put(
						"groupId", fileEntry.getGroupId()
					).put(
						"name", fileEntry.getFileName()
					).put(
						"resourcePrimKey", resourcePrimKey
					).put(
						"title", fileEntry.getTitle()
					).put(
						"type", "journal"
					).put(
						"uuid", fileEntry.getUuid()
					).toString());

				if (fileEntryId <= 0) {
					dynamicContentElement.addAttribute(
						"fileEntryId",
						String.valueOf(fileEntry.getFileEntryId()));
				}
			}
		}

		return contentDocument.formattedString();
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateContentImages();
	}

	protected void updateContentImages() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select id_, resourcePrimKey, groupId, companyId, userId, " +
					"content from JournalArticle where content like ?")) {

			ps1.setString(1, "%type=\"image\"%");

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				long id = rs1.getLong(1);

				long resourcePrimKey = rs1.getLong(2);
				long groupId = rs1.getLong(3);
				long companyId = rs1.getLong(4);
				long userId = rs1.getLong(5);
				String content = rs1.getString(6);

				String newContent = convertTypeImageElements(
					userId, groupId, companyId, content, resourcePrimKey);

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

	private FileEntry _getFileEntryByFileEntryId(long fileEntryId) {
		FileEntry fileEntry = null;

		try {
			fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				fileEntryId);
		}
		catch (PortalException pe) {
			String message = "Unable to get file entry " + fileEntryId;

			if (_log.isDebugEnabled()) {
				_log.debug(message, pe);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(message);
			}
		}

		return fileEntry;
	}

	private FileEntry _getFileEntryById(
			long userId, long groupId, long companyId, long resourcePrimKey,
			String id)
		throws PortalException {

		userId = PortalUtil.getValidUserId(companyId, userId);

		long folderId = _journalArticleImageUpgradeUtil.getFolderId(
			userId, groupId, resourcePrimKey);

		FileEntry fileEntry = null;

		try {
			fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				groupId, folderId, id);
		}
		catch (PortalException pe) {
			String message = StringBundler.concat(
				"Unable to get file entry with group ID ",
				String.valueOf(groupId), ", folder ID ",
				String.valueOf(folderId), ", and file name ", id,
				" for resourcePrimKey ", String.valueOf(resourcePrimKey));

			if (_log.isDebugEnabled()) {
				_log.debug(message, pe);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(message);
			}
		}

		return fileEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeContentImages.class);

	private final JournalArticleImageUpgradeUtil
		_journalArticleImageUpgradeUtil;

}