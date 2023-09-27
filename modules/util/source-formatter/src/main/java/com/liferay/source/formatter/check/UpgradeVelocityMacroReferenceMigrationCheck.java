/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.constants.VelocityMigrationConstants;
import com.liferay.source.formatter.check.util.VelocityMigrationUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeVelocityMacroReferenceMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			String newLine = line;

			Matcher matcher = _statementArgumentsPattern.matcher(line);

			if (!matcher.find()) {
				continue;
			}

			String match = matcher.group();

			if (match.contains(
					VelocityMigrationConstants.VELOCITY_ELSEIF_START) ||
				match.contains(
					VelocityMigrationConstants.VELOCITY_FOREACH_START) ||
				match.contains(VelocityMigrationConstants.VELOCITY_IF_START) ||
				match.contains(
					VelocityMigrationConstants.VELOCITY_MACRO_START) ||
				match.contains(VelocityMigrationConstants.VELOCITY_PARSE) ||
				match.contains(VelocityMigrationConstants.VELOCITY_SET)) {

				continue;
			}

			newLine = StringUtil.replaceLast(
				newLine, CharPool.CLOSE_PARENTHESIS,
				CharPool.SPACE + VelocityMigrationConstants.FREEMARKER_TAG_END);

			String newMatch = VelocityMigrationUtil.removeFirstParenthesis(
				match);

			newMatch = StringUtil.replace(newMatch, CharPool.POUND, "<@");

			content = StringUtil.replace(
				content, line, StringUtil.replace(newLine, match, newMatch));
		}

		return content;
	}

	private static final Pattern _statementArgumentsPattern = Pattern.compile(
		"\\#[\\w]+\\s?\\(");

}