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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeDocumentLibraryTypeContent extends UpgradeProcess {

	public UpgradeDocumentLibraryTypeContent(
		DLAppLocalService dlAppLocalService) {

		_dlAppLocalService = dlAppLocalService;
	}

	protected String convertContent(String content) throws Exception {
		Document contentDocument = SAXReaderUtil.read(content);

		contentDocument = contentDocument.clone();

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='document_library']");

		List<Node> imageNodes = xPath.selectNodes(contentDocument);

		for (Node imageNode : imageNodes) {
			Element imageEl = (Element)imageNode;

			List<Element> dynamicContentEls = imageEl.elements(
				"dynamic-content");

			for (Element dynamicContentEl : dynamicContentEls) {
				String data = getDocumentLibraryValue(
					dynamicContentEl.getText());

				dynamicContentEl.clearContent();

				dynamicContentEl.addCDATA(data);
			}
		}

		return contentDocument.formattedString();
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateContent();
	}

	protected String getDocumentLibraryValue(String url) {
		try {
			FileEntry fileEntry = null;

			if (url.contains("/c/document_library/get_file?") ||
				url.contains("/image/image_gallery?")) {

				fileEntry = getFileEntryByOldDocumentLibraryURL(url);
			}
			else if (url.contains("/documents/")) {
				fileEntry = getFileEntryByDocumentLibraryURL(url);
			}

			if (fileEntry == null) {
				return StringPool.BLANK;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("groupId", fileEntry.getGroupId());
			jsonObject.put("title", fileEntry.getTitle());
			jsonObject.put("type", "document");
			jsonObject.put("uuid", fileEntry.getUuid());

			return jsonObject.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	protected FileEntry getFileEntryByDocumentLibraryURL(String url)
		throws PortalException {

		int x = url.indexOf("/documents/");

		int y = url.indexOf(StringPool.QUESTION);

		if (y == -1) {
			y = url.length();
		}

		url = url.substring(x, y);

		String[] parts = StringUtil.split(url, CharPool.SLASH);

		long groupId = GetterUtil.getLong(parts[2]);

		String uuid = null;

		if (parts.length == 5) {
			uuid = getUuidByDocumentLibraryURLWithoutUuid(parts);
		}
		else {
			uuid = parts[5];
		}

		return _dlAppLocalService.getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	protected FileEntry getFileEntryByOldDocumentLibraryURL(String url)
		throws PortalException {

		Matcher matcher = _oldDocumentLibraryURLPattern.matcher(url);

		if (!matcher.find()) {
			return null;
		}

		long groupId = GetterUtil.getLong(matcher.group(2));

		return _dlAppLocalService.getFileEntryByUuidAndGroupId(
			matcher.group(1), groupId);
	}

	protected String getUuidByDocumentLibraryURLWithoutUuid(String[] splitURL)
		throws PortalException {

		long groupId = GetterUtil.getLong(splitURL[2]);
		long folderId = GetterUtil.getLong(splitURL[3]);
		String title = HttpUtil.decodeURL(HtmlUtil.escape(splitURL[4]));

		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(
				groupId, folderId, title);

			return fileEntry.getUuid();
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get file entry with group ID " + groupId +
					", folder ID " + folderId + ", and title " + title,
				pe);

			throw pe;
		}
	}

	protected void updateContent() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select content, id_ from JournalArticle where content like " +
					"?")) {

			ps1.setString(1, "%type=\"document_library\"%");

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				String content = rs1.getString(1);
				long id = rs1.getLong(2);

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update JournalArticle set content = ? where id_ " +
								"= ?")) {

					ps2.setString(1, convertContent(content));
					ps2.setLong(2, id);

					ps2.executeUpdate();
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibraryTypeContent.class);

	private final DLAppLocalService _dlAppLocalService;
	private final Pattern _oldDocumentLibraryURLPattern = Pattern.compile(
		"uuid=([^&]+)&groupId=([^&]+)");

}