/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v3_0_1;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.BaseTemplateUpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Pattern;

/**
 * @author Saurasish Basak
 */
public class BrowserSnifferFragmentEntryTemplateUpgradeProcess
	extends BaseTemplateUpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		_upgradeFragmentEntries();
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

	private void _upgradeFragmentEntries() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select ctCollectionId, fragmentEntryId, html from " +
						"FragmentEntry");
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update FragmentEntry set html = ? where ctCollectionId " +
						"= ? and fragmentEntryId = ?")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					updatePreparedStatement.setString(
						1,
						replaceDeprecatedClass(
							Pattern.compile("\\[\\#assign\\s*\\/?\\]"),
							resultSet.getString("html")));
					updatePreparedStatement.setLong(
						2, resultSet.getLong("ctCollectionId"));
					updatePreparedStatement.setLong(
						3, resultSet.getLong("fragmentEntryId"));
					updatePreparedStatement.addBatch();
				}

				updatePreparedStatement.executeBatch();
			}
		}
	}

}