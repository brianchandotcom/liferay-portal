/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Albert Gomes Cabral
 * @author Renato Rego
 */
public abstract class BaseTemplateUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeDDMTemplates();
		_upgradeFragmentEntries();
	}

	protected abstract String getContextVariable();

	protected abstract String getDeprecatedClass();

	protected abstract String getDeprecatedClassReplacement();

	private Pattern _getDeprecatedClassPattern() {
		String deprecatedClass = getDeprecatedClass();

		if (deprecatedClass != null) {
			StringBundler sb = new StringBundler(3);

			sb.append("\\w+\\s*\\=\\s*.+");
			sb.append(StringUtil.replace(deprecatedClass, '.', "\\."));
			sb.append("\\\"\\)");

			return Pattern.compile(sb.toString());
		}

		return null;
	}

	private String _getVariableName(Matcher matcher) {
		String matcherGroup = matcher.group();

		String variableName = matcherGroup.substring(
			0, matcherGroup.indexOf(StringPool.EQUAL));

		return variableName.trim();
	}

	private String _replaceDeprecatedClass(
		Pattern emptyAssignPattern, String template) {

		Pattern deprecatedClassPattern = _getDeprecatedClassPattern();

		if (deprecatedClassPattern != null) {
			Matcher deprecatedClassMatcher = deprecatedClassPattern.matcher(
				template);

			while (deprecatedClassMatcher.find()) {
				template = StringUtil.replace(
					template, deprecatedClassMatcher.group(),
					getDeprecatedClassReplacement());

				if (Validator.isNotNull(getContextVariable())) {
					template = StringUtil.replace(
						template, _getVariableName(deprecatedClassMatcher),
						getContextVariable());
				}

				Matcher emptyAssignMatcher = emptyAssignPattern.matcher(
					template);

				if (emptyAssignMatcher.find()) {
					template = emptyAssignMatcher.replaceAll(StringPool.BLANK);
				}
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
						_replaceDeprecatedClass(
							Pattern.compile("\\<\\#assign\\s*\\/?\\>"),
							resultSet.getString("script")));
					updatePreparedStatement.setLong(
						2, resultSet.getLong("templateId"));

					updatePreparedStatement.addBatch();
				}
			}

			updatePreparedStatement.executeBatch();
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
						_replaceDeprecatedClass(
							Pattern.compile("\\[\\#assign\\s*\\/?\\]"),
							resultSet.getString("html")));
					updatePreparedStatement.setLong(
						2, resultSet.getLong("fragmentEntryId"));

					updatePreparedStatement.addBatch();
				}

				updatePreparedStatement.executeBatch();
			}
		}
	}

}