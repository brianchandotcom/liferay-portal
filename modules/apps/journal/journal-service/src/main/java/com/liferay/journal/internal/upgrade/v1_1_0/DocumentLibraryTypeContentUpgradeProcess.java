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

import com.liferay.journal.internal.upgrade.helper.JournalArticleImageUpgradeHelper;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
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
public class DocumentLibraryTypeContentUpgradeProcess extends UpgradeProcess {

	public DocumentLibraryTypeContentUpgradeProcess(
		JournalArticleImageUpgradeHelper journalArticleImageUpgradeHelper) {

		_journalArticleImageUpgradeHelper = journalArticleImageUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateContent();
	}

	private String _convertContent(String content) throws Exception {
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
				String data =
					_journalArticleImageUpgradeHelper.getDocumentLibraryValue(
						dynamicContentEl.getText());

				dynamicContentEl.clearContent();

				dynamicContentEl.addCDATA(data);
			}
		}

		return contentDocument.formattedString();
	}

	private void _updateContent() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select content, id_ from JournalArticle where content like " +
					"?")) {

			preparedStatement1.setString(1, "%type=\"document_library\"%");

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				String content = resultSet1.getString(1);
				long id = resultSet1.getLong(2);

				try (PreparedStatement preparedStatement2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update JournalArticle set content = ? where id_ " +
								"= ?")) {

					preparedStatement2.setString(1, _convertContent(content));
					preparedStatement2.setLong(2, id);

					preparedStatement2.executeUpdate();
				}
			}
		}
	}

	private final JournalArticleImageUpgradeHelper
		_journalArticleImageUpgradeHelper;

}