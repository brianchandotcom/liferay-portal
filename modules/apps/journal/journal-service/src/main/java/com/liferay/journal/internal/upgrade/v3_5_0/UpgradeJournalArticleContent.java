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

package com.liferay.journal.internal.upgrade.v3_5_0;

import com.liferay.journal.content.compatibility.converter.JournalContentCompatibilityLayer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeJournalArticleContent extends UpgradeProcess {

	public UpgradeJournalArticleContent(
		JournalContentCompatibilityLayer journalContentCompatibilityLayer) {

		_journalContentCompatibilityLayer = journalContentCompatibilityLayer;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select id_, content from JournalArticle")) {

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				long id = rs1.getLong("id_");

				String content = rs1.getString("content");

				content = _journalContentCompatibilityLayer.convert(content);

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update JournalArticle set content = ? where id_ " +
								"= ?")) {

					ps2.setString(1, content);
					ps2.setLong(2, id);

					ps2.executeUpdate();
				}
			}
		}
	}

	private final JournalContentCompatibilityLayer
		_journalContentCompatibilityLayer;

}