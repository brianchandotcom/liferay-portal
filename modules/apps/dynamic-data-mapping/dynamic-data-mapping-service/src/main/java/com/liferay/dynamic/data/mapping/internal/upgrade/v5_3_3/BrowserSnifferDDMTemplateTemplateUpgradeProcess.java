/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_3_3;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.BaseTemplateUpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Pattern;

/**
 * @author Albert Gomes Cabral
 * @author Renato Rego
 */
public class BrowserSnifferDDMTemplateTemplateUpgradeProcess
	extends BaseTemplateUpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		_upgradeDDMTemplates();
	}

	@Override
	protected String getContextVariable() {
		return "browserSniffer";
	}

	@Override
	protected String getDeprecatedClass() {
		return "com.liferay.portal.kernel.servlet.BrowserSnifferUtil";
	}

	@Override
	protected String getDeprecatedClassReplacement() {
		return StringPool.BLANK;
	}

	private void _upgradeDDMTemplates() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select ctCollectionId, templateId, script from " +
						"DDMTemplate");
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMTemplate set script = ? where ctCollectionId " +
						"= ? and templateId = ?")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					if (resultSet.getString("script") == null) {
						continue;
					}

					updatePreparedStatement.setString(
						1,
						replaceDeprecatedClass(
							Pattern.compile("\\<\\#assign\\s*\\/?\\>"),
							resultSet.getString("script")));
					updatePreparedStatement.setLong(
						2, resultSet.getLong("ctCollectionId"));
					updatePreparedStatement.setLong(
						3, resultSet.getLong("templateId"));

					updatePreparedStatement.addBatch();
				}
			}

			updatePreparedStatement.executeBatch();
		}
	}

}