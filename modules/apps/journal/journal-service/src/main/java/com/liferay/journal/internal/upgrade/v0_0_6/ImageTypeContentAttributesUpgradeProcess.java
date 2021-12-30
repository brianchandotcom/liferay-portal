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

package com.liferay.journal.internal.upgrade.v0_0_6;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
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
 * @author Alberto Chaparro
 */
public class ImageTypeContentAttributesUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateContentImages();
	}

	private String _addImageContentAttributes(String content) throws Exception {
		Document document = SAXReaderUtil.read(content);

		document = document.clone();

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='image']");

		List<Node> imageNodes = xPath.selectNodes(document);

		for (Node imageNode : imageNodes) {
			Element imageElement = (Element)imageNode;

			List<Element> dynamicContentElements = imageElement.elements(
				"dynamic-content");

			String id = null;

			for (Element dynamicContentElement : dynamicContentElements) {
				id = dynamicContentElement.attributeValue("id");

				dynamicContentElement.addAttribute("alt", StringPool.BLANK);
				dynamicContentElement.addAttribute("name", id);
				dynamicContentElement.addAttribute("title", id);
				dynamicContentElement.addAttribute("type", "journal");
			}

			if (Validator.isNotNull(id)) {
				imageElement.addAttribute(
					"instance-id", _getImageInstanceId(id));
			}
		}

		return document.formattedString();
	}

	private String _getImageInstanceId(String articleImageId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select elInstanceId from JournalArticleImage where " +
					"articleImageId = ?")) {

			preparedStatement.setLong(1, Long.valueOf(articleImageId));

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getString(1);
			}

			return StringPool.BLANK;
		}
	}

	private void _updateContentImages() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select content, id_ from JournalArticle where content like " +
					"?")) {

			preparedStatement1.setString(1, "%type=\"image\"%");

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				String content = resultSet.getString(1);
				long id = resultSet.getLong(2);

				String newContent = _addImageContentAttributes(content);

				try (PreparedStatement preparedStatement =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update JournalArticle set content = ? where id_ " +
								"= ?")) {

					preparedStatement.setString(1, newContent);
					preparedStatement.setLong(2, id);

					preparedStatement.executeUpdate();
				}
			}
		}
	}

}