/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.comparator.ParameterNameComparator;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class JavaDocumentAddCallsOrderCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String content = javaTerm.getContent();

		Matcher matcher = _documentVariableDefinitionPattern.matcher(content);

		while (matcher.find()) {
			String variableName = matcher.group(1);

			int x = matcher.end();

			while (true) {
				x = content.indexOf("\t" + variableName + ".add", x + 1);

				if (x == -1) {
					break;
				}

				String codeBlock = StringPool.BLANK;
				int y = content.indexOf("\n\n", x + 1);

				if (y == -1) {
					codeBlock = content.substring(x);
				}
				else {
					codeBlock = content.substring(x, y);
				}

				String sortedCodeBlock = _sortedCodeBlock(
					codeBlock, variableName);

				if (!codeBlock.equals(sortedCodeBlock)) {
					return StringUtil.replaceFirst(
						content, codeBlock, sortedCodeBlock, matcher.start());
				}

				if (y == -1) {
					break;
				}

				x = y;
			}
		}

		return content;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _sortedCodeBlock(String codeBlock, String variableName) {
		String previousMethodCall = null;
		String previousParameterName = null;

		ParameterNameComparator parameterNameComparator =
			new ParameterNameComparator();

		int x = 0;

		while (true) {
			String trimmedCodeBlock = StringUtil.trimLeading(
				codeBlock.substring(x));

			if (!trimmedCodeBlock.startsWith(variableName + ".add")) {
				return codeBlock;
			}

			int y = codeBlock.indexOf(CharPool.OPEN_PARENTHESIS, x + 1);

			if (y == -1) {
				return codeBlock;
			}

			String methodCall = null;
			String parameters = null;
			int z = y;

			while (true) {
				z = codeBlock.indexOf(CharPool.CLOSE_PARENTHESIS, z + 1);

				if (z == -1) {
					return codeBlock;
				}

				String s = codeBlock.substring(y, z + 1);

				if ((ToolsUtil.getLevel(s, "(", ")") != 0) ||
					(ToolsUtil.getLevel(s, "{", "}") != 0)) {

					continue;
				}

				if (codeBlock.charAt(z + 1) != CharPool.SEMICOLON) {
					return codeBlock;
				}

				methodCall = StringUtil.trim(codeBlock.substring(x, z + 1));
				parameters = codeBlock.substring(y + 1, z);

				x = z + 2;

				break;
			}

			List<String> parametersList = JavaSourceUtil.splitParameters(
				parameters);

			String parameterName = parametersList.get(0);

			if (previousParameterName == null) {
				previousMethodCall = methodCall;
				previousParameterName = parameterName;

				continue;
			}

			int compare = parameterNameComparator.compare(
				previousParameterName, parameterName);

			if (compare <= 0) {
				previousMethodCall = methodCall;
				previousParameterName = parameterName;

				continue;
			}

			String sortedCodeBlock = StringUtil.replaceFirst(
				codeBlock, previousMethodCall, methodCall);

			return StringUtil.replaceLast(
				sortedCodeBlock, methodCall, previousMethodCall);
		}
	}

	private static final Pattern _documentVariableDefinitionPattern =
		Pattern.compile("\\bDocument (\\w+)\\b");

}