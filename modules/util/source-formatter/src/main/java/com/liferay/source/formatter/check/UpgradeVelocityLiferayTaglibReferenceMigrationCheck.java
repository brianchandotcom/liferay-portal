/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.constants.VelocityMigrationConstants;
import com.liferay.source.formatter.check.util.SourceUtil;

/**
 * @author Nícolas Moura
 */
public class UpgradeVelocityLiferayTaglibReferenceMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = StringUtil.splitLines(content);

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			if (line.contains(_VELOCITY_LIFERAY_BREADCRUMBS)) {
				lines[i] = _migrateVelocityLiferayBreadCrumbs(line);
			}
			else if (line.contains(_VELOCITY_LIFERAY_LANGUAGE)) {
				if (line.contains(_VELOCITY_LIFERAY_LANGUAGE_FORMAT)) {
					lines[i] = _migrateVelocityLanguageFormat(line);
				}
				else {
					lines[i] = _migrateVelocityLanguage(line);
				}
			}
			else if (line.contains(_VELOCITY_LIFERAY_THEME_INCLUDE)) {
				lines[i] = _migrateVelocityLiferayThemeInclude(line);
			}
			else if (line.contains(_VELOCITY_LIFERAY_THEME_WRAP)) {
				lines[i] = _migrateVelocityLiferayThemeWrap(line);
			}
		}

		return com.liferay.petra.string.StringUtil.merge(
			lines, StringPool.NEW_LINE);
	}

	private String _migrateVelocityLanguage(String line) {
		String newLine = _replaceLine(
			line, _VELOCITY_LIFERAY_LANGUAGE, StringPool.OPEN_PARENTHESIS,
			_FREEMARKER_LIFERAY_LANGUAGE_KEY);

		int languageKeyIndex = newLine.indexOf(
			_FREEMARKER_LIFERAY_LANGUAGE_KEY);

		String endLine = newLine.substring(languageKeyIndex);

		String newEndLine = StringUtil.replaceFirst(
			endLine, CharPool.CLOSE_PARENTHESIS,
			CharPool.SPACE + VelocityMigrationConstants.FREEMARKER_TAG_END);

		return StringUtil.replace(newLine, endLine, newEndLine);
	}

	private String _migrateVelocityLanguageFormat(String line) {
		return _replaceLine(
			line, _VELOCITY_LIFERAY_LANGUAGE_FORMAT,
			_VELOCITY_LIFERAY_LANGUAGE_FORMAT_ARGUMENTS,
			_FREEMARKER_LIFERAY_LANGUAGE_FORMAT);
	}

	private String _migrateVelocityLiferayBreadCrumbs(String line) {
		return _replaceLine(
			line, _VELOCITY_LIFERAY_BREADCRUMBS, "()",
			_FREEMARKER_LIFERAY_BREADCRUMBS);
	}

	private String _migrateVelocityLiferayThemeInclude(String line) {
		String newLine = _replaceLine(
			line, _VELOCITY_LIFERAY_THEME_INCLUDE, "($",
			_FREEMARKER_LIFERAY_THEME_INCLUDE);

		return StringUtil.replaceLast(
			newLine, CharPool.CLOSE_PARENTHESIS,
			CharPool.SPACE + VelocityMigrationConstants.FREEMARKER_TAG_END);
	}

	private String _migrateVelocityLiferayThemeWrap(String line) {
		String newLine = _replaceLine(
			line, _VELOCITY_LIFERAY_THEME_WRAP, StringPool.OPEN_PARENTHESIS,
			_FREEMARKER_LIFERAY_THEME_WRAP);

		int newStartIndex = newLine.indexOf(_FREEMARKER_LIFERAY_THEME_WRAP);

		String indent = SourceUtil.getIndent(newLine);

		newLine = StringUtil.replace(
			newLine, EXTENSION_VELOCITY, EXTENSION_FREEMARKER);
		newLine = StringUtil.replaceFirst(
			newLine, StringPool.CLOSE_PARENTHESIS,
			StringBundler.concat(
				StringPool.SPACE, VelocityMigrationConstants.FREEMARKER_TAG_END,
				StringPool.NEW_LINE, indent, "</@>"),
			newStartIndex);
		newLine = StringUtil.replaceFirst(
			newLine, StringPool.COMMA,
			StringBundler.concat(
				StringPool.GREATER_THAN + StringPool.NEW_LINE, indent,
				StringPool.TAB, _FREEMARKER_LIFERAY_THEME_INCLUDE),
			newStartIndex);

		return _replaceLine(
			newLine, StringPool.EQUAL, StringPool.DOLLAR, StringPool.EQUAL);
	}

	private String _replaceLine(
		String line, String oldSub1, String oldSub2, String newSub) {

		return StringUtil.replace(
			line,
			new String[] {
				oldSub1 + oldSub2, oldSub1 + CharPool.SPACE + oldSub2
			},
			new String[] {newSub, newSub});
	}

	private static final String _FREEMARKER_LIFERAY_BREADCRUMBS =
		"<@liferay.breadcrumbs />";

	private static final String _FREEMARKER_LIFERAY_LANGUAGE_FORMAT =
		"<@liferay.language_format arguments=\"${site_name}\" " +
			"key=\"go-to-x\" />";

	private static final String _FREEMARKER_LIFERAY_LANGUAGE_KEY =
		"<@liferay.language key=";

	private static final String _FREEMARKER_LIFERAY_THEME_INCLUDE =
		"<@liferay_util[\"include\"] page=";

	private static final String _FREEMARKER_LIFERAY_THEME_WRAP =
		"<@liferay_theme[\"wrap-portlet\"] page=";

	private static final String _VELOCITY_LIFERAY_BREADCRUMBS = "#breadcrumbs";

	private static final String _VELOCITY_LIFERAY_LANGUAGE = "#language";

	private static final String _VELOCITY_LIFERAY_LANGUAGE_FORMAT =
		"#language_format";

	private static final String _VELOCITY_LIFERAY_LANGUAGE_FORMAT_ARGUMENTS =
		"(\"go-to-x\", [$site_name])";

	private static final String _VELOCITY_LIFERAY_THEME_INCLUDE =
		"$theme.include";

	private static final String _VELOCITY_LIFERAY_THEME_WRAP =
		"$theme.wrapPortlet";

}