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

package com.liferay.journal.internal.upgrade.v3_0_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
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
 * @author Matthew Chan
 */
public class UpgradeContentRadioFields extends UpgradeProcess {

	protected String convertRadioElements(String content) throws Exception {
		Document contentDocument = SAXReaderUtil.read(content);

		contentDocument = contentDocument.clone();

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='radio']");

		List<Node> imageNodes = xPath.selectNodes(contentDocument);

		for (Node imageNode : imageNodes) {
			Element imageElement = (Element)imageNode;

			List<Element> dynamicContentElements = imageElement.elements(
				"dynamic-content");

			for (Element dynamicContentElement : dynamicContentElements) {
				String data = String.valueOf(dynamicContentElement.getData());

				data = removeUnusedChars(data);

				dynamicContentElement.clearContent();

				dynamicContentElement.addCDATA(data);
			}
		}

		return contentDocument.formattedString();
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateContentRadioFields();
	}

	protected void updateContentRadioFields() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select id_, content from JournalArticle where content like ?")) {

			ps1.setString(1, "%type=\"radio\"%");

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				long id = rs1.getLong(1);
				String content = rs1.getString(2);

				String newContent = convertRadioElements(content);

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

	private String removeUnusedChars(String data) {
		if (Validator.isNull(data)) {
			return data;
		}

		int start = 0;
		int end = data.length() - 1;

		if (data.charAt(start) == '[' && data.charAt(end) == ']') {
			start = start + 1;
			end = end - 1;

			if (data.charAt(start) == '"' && data.charAt(end) == '"') {
				start = start + 1;
				end = end - 1;
			}
		}

		return data.substring(start, end + 1);
	}

}