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

package com.liferay.journal.internal.upgrade.v3_5_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.sql.PreparedStatement;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alejandro Tardín
 */
public class JournalArticleDataFileEntryIdUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		processConcurrently(
			"select id_, content from JournalArticle",
			resultSet -> new Object[] {
				resultSet.getLong("id_"), resultSet.getString("content")
			},
			columns -> {
				long id = (long)columns[0];
				String content = (String)columns[1];

				String sql =
					"update JournalArticle set content = ? where id_ = ?";

				try (PreparedStatement updatePreparedStatement =
						connection.prepareStatement(sql)) {

					String upgradedContent = _upgradeContent(content);

					if (!Objects.equals(content, upgradedContent)) {
						updatePreparedStatement.setString(1, upgradedContent);
						updatePreparedStatement.setLong(2, id);

						updatePreparedStatement.executeUpdate();
					}
				}
			},
			"Unable to update file entry ID for journal article data");
	}

	private String _upgradeContent(String content) throws DocumentException {
		Document document = SAXReaderUtil.read(content);

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='text_area']");

		List<Node> ddmJournalArticleNodes = xPath.selectNodes(document);

		for (Node ddmJournalArticleNode : ddmJournalArticleNodes) {
			Element ddmJournalArticleElement = (Element)ddmJournalArticleNode;

			List<Element> dynamicContentElements =
				ddmJournalArticleElement.elements("dynamic-content");

			for (Element dynamicContentElement : dynamicContentElements) {
				String stringValue = dynamicContentElement.getStringValue();

				Matcher matcher = _dataFileEntryIdPattern.matcher(stringValue);

				String upgradedStringValue = matcher.replaceAll(
					"data-fileentryid=");

				if (!upgradedStringValue.equals(stringValue)) {
					dynamicContentElement.clearContent();

					dynamicContentElement.addCDATA(upgradedStringValue);
				}
			}
		}

		return document.asXML();
	}

	private static final Pattern _dataFileEntryIdPattern = Pattern.compile(
		"data-fileEntryId=", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

}