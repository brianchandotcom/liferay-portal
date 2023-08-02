/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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

	private String _replaceTemplatePattern(
			Pattern isAssignEmptyPattern, String template)
		throws Exception {

		Pattern templatePattern = getTemplatePattern();

		Matcher templateMatcher = templatePattern.matcher(template);

		while (templateMatcher.find()) {
			template = StringUtil.replace(
				template, templateMatcher.group(),
				getTemplatePatternReplacement());

			if (Validator.isNotNull(getTemplateContextVariable())) {
				template = StringUtil.replace(
					template, _getVariableName(templateMatcher),
					getTemplateContextVariable());
			}

			Matcher isAssignEmptyMatcher = isAssignEmptyPattern.matcher(
				template);

			if (isAssignEmptyMatcher.find()) {
				template = isAssignEmptyMatcher.replaceAll(
					getTemplatePatternReplacement());
			}
		}

		return template;
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
					updatePreparedStatement.setString(
						1,
						_replaceTemplatePattern(
							_isAssignEmptyDDMTemplatePattern,
							resultSet.getString("script")));
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
					updatePreparedStatement.setString(
						1,
						_replaceTemplatePattern(
							_isAssignEmptyFragmentEntryPattern,
							resultSet.getString("html")));
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