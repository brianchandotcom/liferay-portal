/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v1_1_8;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
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
public class JournalArticleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select id_, content from JournalArticle where content like " +
					"'%type=\"radio\"%'");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set content = ? where id_ = ?")) {

			while (resultSet.next()) {
				preparedStatement2.setString(
					1,
					_convertRadioDynamicElements(
						resultSet.getString("content")));
				preparedStatement2.setLong(2, resultSet.getLong("id_"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private String _convertRadioDynamicElements(String content)
		throws Exception {

		Document document = SAXReaderUtil.read(content);

		document = document.clone();

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='radio']");

		List<Node> nodes = xPath.selectNodes(document);

		for (Node node : nodes) {
			Element element = (Element)node;

			List<Element> dynamicContentElements = element.elements(
				"dynamic-content");

			for (Element dynamicContentElement : dynamicContentElements) {
				String data = String.valueOf(dynamicContentElement.getData());

				data = _removeUnusedChars(data);

				dynamicContentElement.clearContent();

				dynamicContentElement.addCDATA(data);
			}
		}

		return document.formattedString();
	}

	private String _removeUnusedChars(String data) {
		if ((data != null) && (data.length() > 3)) {
			int start = 0;
			int end = data.length() - 1;

			if ((data.charAt(start) == '[') && (data.charAt(end) == ']') &&
				(data.charAt(start + 1) == '"') &&
				(data.charAt(end - 1) == '"')) {

				data = data.substring(start + 2, end - 1);
			}
		}

		return data;
	}

}