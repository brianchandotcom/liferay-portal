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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSignatureStylingCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String javaTermContent = javaTerm.getContent();

		String indent = SourceUtil.getIndent(javaTermContent);

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"(", indent, javaTerm.getAccessModifier(),
				" .*?[;{]\\s*?\n)((\n*)([^\n]+)\n)?"),
			Pattern.DOTALL);

		Matcher matcher = pattern.matcher(javaTermContent);

		if (!matcher.find()) {
			return javaTermContent;
		}

		String signature = matcher.group(1);

		String[] signatureLines = StringUtil.splitLines(signature);

		String newLineChars = matcher.group(3);
		String nextLine = StringUtil.trim(matcher.group(4));

		if (signatureLines.length == 1) {
			return _formatSingleLineSignature(
				javaTermContent, signature, newLineChars, nextLine);
		}

		return _formatMultiLinesSignature(
			javaTermContent, signature, signatureLines, indent, newLineChars,
			nextLine);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _fixLeadingTabs(
		String content, String line, int expectedTabCount) {

		int leadingTabCount = getLeadingTabCount(line);

		String newLine = line;

		while (leadingTabCount != expectedTabCount) {
			if (leadingTabCount > expectedTabCount) {
				newLine = StringUtil.replaceFirst(
					newLine, CharPool.TAB, StringPool.BLANK);

				leadingTabCount--;
			}
			else {
				newLine = StringPool.TAB + newLine;

				leadingTabCount++;
			}
		}

		return StringUtil.replace(content, line, newLine);
	}

	private String _formatMultiLinesSignature(
		String javaTermContent, String signature, String[] signatureLines,
		String indent, String newLineChars, String nextLine) {

		if (signature.endsWith("{\n") && (newLineChars != null) &&
			(newLineChars.length() == 0) &&
			!nextLine.equals(StringPool.CLOSE_CURLY_BRACE)) {

			return StringUtil.replace(
				javaTermContent, signature, signature + "\n");
		}

		int throwsPos = signature.indexOf("\tthrows ");

		String newSignature = signature;

		int expectedTabCount = -1;

		outerLoop:
		for (int i = 0; i < signatureLines.length; i++) {
			String line = signatureLines[i];

			if (line.contains(indent + "throws ")) {
				newSignature = _fixLeadingTabs(
					newSignature, line, indent.length() + 1);

				break;
			}

			if (expectedTabCount == -1) {
				if (line.endsWith(StringPool.OPEN_PARENTHESIS)) {
					expectedTabCount = Math.max(
						getLeadingTabCount(line), indent.length()) + 1;

					if ((throwsPos != -1) &&
						(expectedTabCount == (indent.length() + 1))) {

						expectedTabCount += 1;
					}
				}
			}
			else {
				String previousLine = signatureLines[i - 1];

				if (previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {
					newSignature = _fixLeadingTabs(
						newSignature, line, expectedTabCount);
				}
				else if (previousLine.endsWith(StringPool.COMMA)) {
					int level = 0;

					for (int j = i - 1; j >= 0; j--) {
						String curLine = signatureLines[j];

						level += getLevel(curLine, "<", ">");

						if (level > 0) {
							newSignature = _fixLeadingTabs(
								newSignature, line,
								getLeadingTabCount(curLine));

							continue outerLoop;
						}
					}

					newSignature = _fixLeadingTabs(
						newSignature, line, expectedTabCount);
				}
				else {
					newSignature = _fixLeadingTabs(
						newSignature, line,
						getLeadingTabCount(previousLine) + 1);
				}
			}
		}

		throwsPos = newSignature.indexOf("\tthrows ");

		if (throwsPos != -1) {
			String throwsExceptions = newSignature.substring(throwsPos + 1);

			String newThrowsExceptions = throwsExceptions.replaceAll(
				"\n\t* *(\\S)", "\n" + indent + "\t\t   $1");

			newSignature = StringUtil.replace(
				newSignature, throwsExceptions, newThrowsExceptions);
		}

		return StringUtil.replace(javaTermContent, signature, newSignature);
	}

	private String _formatSingleLineSignature(
		String javaTermContent, String signature, String newLineChars,
		String nextLine) {

		if ((newLineChars != null) && (newLineChars.length() > 0) &&
			!nextLine.matches("/[/*].*")) {

			return StringUtil.replace(
				javaTermContent, signature + "\n", signature);
		}

		return javaTermContent;
	}

}