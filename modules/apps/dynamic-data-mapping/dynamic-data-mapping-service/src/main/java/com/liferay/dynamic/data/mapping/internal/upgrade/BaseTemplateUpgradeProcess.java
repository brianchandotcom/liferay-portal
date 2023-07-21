/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Albert Gomes Cabral
 */
public abstract class BaseTemplateUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeDDMTemplates();
		_upgradeFragmentEntries();
	}

	protected String getTemplateContextVariable() {
		return null;
	}

	protected abstract Pattern getTemplatePattern() throws Exception;

	protected abstract String getTemplatePatternReplacement() throws Exception;

	private String _getVariableName(Matcher matcher) {
		String matcherGroup = matcher.group();

		String variableName = matcherGroup.substring(
			0, matcherGroup.indexOf(StringPool.EQUAL));

		return variableName.trim();
	}

	private void _upgradeDDMTemplates() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select templateId, script from DDMTemplate");
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMTemplate set script = ? where templateId = ?")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String script = resultSet.getString("script");

					Pattern templatePattern = getTemplatePattern();

					Matcher templateMatcher = templatePattern.matcher(script);

					while (templateMatcher.find()) {
						script = StringUtil.replace(
							script, templateMatcher.group(),
							getTemplatePatternReplacement());

						if (Validator.isNotNull(getTemplateContextVariable())) {
							script = StringUtil.replace(
								script, _getVariableName(templateMatcher),
								getTemplateContextVariable());
						}

						Matcher isAssignEmptyMatcher =
							_isAssignEmptyDDMTemplatePattern.matcher(script);

						if (isAssignEmptyMatcher.find()) {
							script = isAssignEmptyMatcher.replaceAll(
								getTemplatePatternReplacement());
						}
					}

					updatePreparedStatement.setString(1, script);
					updatePreparedStatement.setLong(
						2, resultSet.getLong("templateId"));

					updatePreparedStatement.addBatch();
				}

				updatePreparedStatement.executeBatch();
			}
		}
	}

	private void _upgradeFragmentEntries() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select fragmentEntryId, html from FragmentEntry");
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update FragmentEntry set html = ? where fragmentEntryId " +
						"= ?")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String html = resultSet.getString("html");

					Pattern templatePattern = getTemplatePattern();

					Matcher templateMatcher = templatePattern.matcher(html);

					while (templateMatcher.find()) {
						html = StringUtil.replace(
							html, templateMatcher.group(),
							getTemplatePatternReplacement());

						if (Validator.isNotNull(getTemplateContextVariable())) {
							html = StringUtil.replace(
								html, _getVariableName(templateMatcher),
								getTemplateContextVariable());
						}

						Matcher isAssignEmptyMatcher =
							_isAssignEmptyFragmentEntryPattern.matcher(html);

						if (isAssignEmptyMatcher.find()) {
							html = isAssignEmptyMatcher.replaceAll(
								getTemplatePatternReplacement());
						}
					}

					updatePreparedStatement.setString(1, html);
					updatePreparedStatement.setLong(
						2, resultSet.getLong("fragmentEntryId"));

					updatePreparedStatement.addBatch();
				}

				updatePreparedStatement.executeBatch();
			}
		}
	}

	private static final Pattern _isAssignEmptyDDMTemplatePattern =
		Pattern.compile("\\<\\#assign\\s*\\/?\\>");
	private static final Pattern _isAssignEmptyFragmentEntryPattern =
		Pattern.compile("\\[\\#assign\\s*\\/?\\]");

}