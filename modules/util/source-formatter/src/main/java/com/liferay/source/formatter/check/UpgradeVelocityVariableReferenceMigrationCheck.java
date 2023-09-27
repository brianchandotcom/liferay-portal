/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.constants.VelocityMigrationConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeVelocityVariableReferenceMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			String newLine = line;

			Matcher matcher = _variableReferencePattern.matcher(line);

			while (matcher.find()) {
				String match = matcher.group();

				if (_isJQuery(match)) {
					continue;
				}

				if (!_isValidReplacement(newLine, match)) {
					newLine = StringUtil.replace(
						newLine, match,
						StringUtil.removeChar(match, CharPool.DOLLAR));

					continue;
				}

				int beginCharIndex = newLine.indexOf(match) + match.length();

				if ((newLine.length() > beginCharIndex) &&
					(newLine.charAt(beginCharIndex) ==
						CharPool.OPEN_PARENTHESIS)) {

					newLine = _encapsulateMethodCall(
						newLine, match, beginCharIndex);
				}
				else {
					String startNewMatch = StringUtil.replace(
						match, CharPool.DOLLAR,
						VelocityMigrationConstants.
							FREEMARKER_ENCAPSULATION_START);

					newLine = StringUtil.replace(
						newLine, match,
						startNewMatch + CharPool.CLOSE_CURLY_BRACE);
				}
			}

			content = StringUtil.replace(content, line, newLine);
		}

		return content;
	}

	private static String _encapsulateMethodCall(
		String newLine, String match, int beginCharIndex) {

		boolean newMethodCall = false;
		int parenthesisStack = 0;

		String endLine = newLine.substring(beginCharIndex);

		for (int i = 0; i < endLine.length(); i++) {
			int nextCharIndex = beginCharIndex + i;

			if (newLine.charAt(nextCharIndex) == CharPool.OPEN_PARENTHESIS) {
				parenthesisStack += 1;
			}
			else if (newLine.charAt(nextCharIndex) ==
						CharPool.CLOSE_PARENTHESIS) {

				parenthesisStack -= 1;
				newMethodCall = false;

				if ((newLine.length() > (nextCharIndex + 1)) &&
					(newLine.charAt(nextCharIndex + 1) == CharPool.PERIOD)) {

					newMethodCall = true;
				}
			}

			if ((parenthesisStack == 0) && !newMethodCall) {
				String endMatch = newLine.substring(
					beginCharIndex, nextCharIndex + 1);

				String fullMatch = match + endMatch;

				String newReference = StringUtil.replace(
					match, CharPool.DOLLAR,
					VelocityMigrationConstants.FREEMARKER_ENCAPSULATION_START);

				newLine = StringUtil.replaceFirst(
					newLine, fullMatch,
					newReference + endMatch + CharPool.CLOSE_CURLY_BRACE);

				break;
			}
		}

		return newLine;
	}

	private static boolean _isAttribute(String line, String match) {
		boolean attribute = false;

		int initMatchIndex = line.indexOf(match);

		String afterMatch = line.substring(initMatchIndex + match.length());
		String beforeMatch = line.substring(0, initMatchIndex);

		for (int i = 1; i < (beforeMatch.length() + 1); i++) {
			int previousCharIndex = initMatchIndex - i;

			if ((line.charAt(previousCharIndex) == CharPool.QUOTE) &&
				((StringUtil.count(afterMatch, CharPool.QUOTE) % 2) != 0)) {

				attribute = true;

				break;
			}

			if ((line.charAt(previousCharIndex) == CharPool.APOSTROPHE) &&
				((StringUtil.count(afterMatch, CharPool.APOSTROPHE) % 2) !=
					0)) {

				attribute = true;

				break;
			}
		}

		return attribute;
	}

	private boolean _isJQuery(String match) {
		if ((match.charAt(1) == CharPool.OPEN_PARENTHESIS) ||
			(match.charAt(1) == CharPool.PERIOD)) {

			return true;
		}

		return false;
	}

	private boolean _isValidReplacement(String line, String match) {
		boolean validReplacement = true;

		line = StringUtil.removeChars(line, CharPool.SPACE, CharPool.TAB);

		String lineBegin = line.substring(0, 2);

		if ((lineBegin.equals(StringPool.LESS_THAN + StringPool.POUND) ||
			 lineBegin.equals(StringPool.LESS_THAN + StringPool.AT) ||
			 (line.charAt(0) == CharPool.POUND)) &&
			!_isAttribute(line, match)) {

			validReplacement = false;
		}

		if ((line.charAt(0) != CharPool.LESS_THAN) &&
			!_isAttribute(line, match) &&
			(StringUtil.count(line, CharPool.OPEN_PARENTHESIS) !=
				StringUtil.count(line, CharPool.CLOSE_PARENTHESIS))) {

			validReplacement = false;
		}

		return validReplacement;
	}

	private static final Pattern _variableReferencePattern = Pattern.compile(
		"\\$[\\w\\.]+");

}