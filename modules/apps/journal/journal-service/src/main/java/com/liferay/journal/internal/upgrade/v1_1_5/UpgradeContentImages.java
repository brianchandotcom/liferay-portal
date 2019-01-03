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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
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
 * @author Jürgen Kappler
 */
public class UpgradeContentImages extends UpgradeProcess {

	public UpgradeContentImages(
		JournalArticleImageUpgradeUtil journalArticleImageUpgradeUtil,
		UserLocalService userLocalService) {

		_journalArticleImageUpgradeUtil = journalArticleImageUpgradeUtil;
		_userLocalService = userLocalService;
	}

	protected String convertTypeImageElements(
			long companyId, long userId, long groupId, String content,
			long resourcePrimKey)
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
				long fileEntryId = GetterUtil.getLong(
					dynamicContentEl.attributeValue("fileEntryId"));

				String id = dynamicContentEl.attributeValue("id");

				FileEntry fileEntry = null;

				if (Validator.isNotNull(id)) {
					fileEntry = _getFileEntryById(
						companyId, userId, groupId, resourcePrimKey, id);
				}
				else if (fileEntryId > 0) {
					fileEntry = _getFileEntryByFileEntryId(fileEntryId);
				}
				else {
					String data = String.valueOf(dynamicContentEl.getData());

					fileEntry =
						_journalArticleImageUpgradeUtil.getFileEntryFromURL(
							data);
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

				if (fileEntryId <= 0) {
					dynamicContentEl.addAttribute(
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
				"select companyId, content, groupId, id_, resourcePrimKey, " +
					"userId from JournalArticle where content like ?")) {

			ps1.setString(1, "%type=\"image\"%");

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				long companyId = rs1.getLong(1);
				String content = rs1.getString(2);
				long groupId = rs1.getLong(3);
				long id = rs1.getLong(4);
				long resourcePrimKey = rs1.getLong(5);
				long userId = rs1.getLong(6);

				String newContent = convertTypeImageElements(
					companyId, userId, groupId, content, resourcePrimKey);

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
			_log.error("Unable to get file entry " + fileEntryId, pe);
		}

		return fileEntry;
	}

	private FileEntry _getFileEntryById(
			long companyId, long userId, long groupId, long resourcePrimKey,
			String id)
		throws PortalException {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			user = _userLocalService.getFallbackUser(companyId);

			userId = user.getUserId();

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"UserId for ", id,
						" has been changed to fallback user."));
			}
		}

		long folderId = _journalArticleImageUpgradeUtil.getFolderId(
			userId, groupId, resourcePrimKey);

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

		return fileEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeContentImages.class);

	private final JournalArticleImageUpgradeUtil
		_journalArticleImageUpgradeUtil;
	private final UserLocalService _userLocalService;

}