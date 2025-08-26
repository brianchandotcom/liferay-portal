/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.ParseException;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Albert Gomes Cabral
 */
public class UpgradeJavaGetFDSTableSchemaParameterCheck
	extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws IOException, ParseException {

		String newContent = content;

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			if (extendedClassNames.contains("BaseTableFDSView")) {
				JavaMethod javaMethod = (JavaMethod)childJavaTerm;

				String javaMethodContent = javaMethod.getContent();

				Matcher matcher = _getFDSTableSchemaPattern.matcher(
					javaMethodContent);

				while (matcher.find()) {
					String methodCall = matcher.group();

					newContent = StringUtil.replace(
						content, methodCall,
						StringUtil.replace(
							methodCall, matcher.group(1), "(Locale locale)"));
				}

				matcher = _getFDSTableSchemaFieldPattern.matcher(
					javaMethodContent);

				if (matcher.find()) {
					newContent = _formatFDSTableSchemaFieldCall(
						matcher, newContent, javaMethodContent);
				}
			}
		}

		return newContent;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {"java.util.Locale"};
	}

	private String _formatFDSTableSchemaFieldCall(
		Matcher matcher, String content, String javaMethodContent) {

		String newContent = content;

		String attributeName = matcher.group(1);

		Pattern methodPattern = Pattern.compile(
			"\\s*(" + Pattern.quote(attributeName) +
				"\\s*(\\.\\w+\\([^)]*\\)))\\s*;");

		Matcher methodCallMatcher = methodPattern.matcher(javaMethodContent);

		if (!methodCallMatcher.find()) {
			return newContent;
		}

		String indent = SourceUtil.getIndent(matcher.group());

		String newLineAndIndent = StringPool.NEW_LINE + indent;

		StringBundler sb = new StringBundler();

		sb.append(indent);
		sb.append(matcher.group(2));
		sb.append(newLineAndIndent);
		sb.append(StringPool.TAB);
		sb.append(matcher.group(3));

		newContent = StringUtil.removeSubstring(
			newContent, methodCallMatcher.group());

		sb.append(StringPool.COMMA);
		sb.append(newLineAndIndent);
		sb.append(StringPool.TAB);
		sb.append(attributeName);
		sb.append(" -> ");
		sb.append(methodCallMatcher.group(1));

		while (methodCallMatcher.find()) {
			sb.append(newLineAndIndent);
			sb.append(StringPool.TAB);
			sb.append(methodCallMatcher.group(2));

			newContent = StringUtil.removeSubstring(
				newContent, methodCallMatcher.group());
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return StringUtil.replace(
			newContent, matcher.group(),
			StringUtil.replace(
				matcher.group(), matcher.group(), sb.toString()));
	}

	private static final Pattern _getFDSTableSchemaFieldPattern =
		Pattern.compile(
			"^[^\\n]\\s+FDSTableSchemaField\\s+(\\w+)\\s=\\s*(\\w+\\" +
				".add\\()\\s*([^)]*)\\)\\s*;",
			Pattern.MULTILINE);
	private static final Pattern _getFDSTableSchemaPattern = Pattern.compile(
		"\\w+\\s+FDSTableSchema\\s+getFDSTableSchema(\\(\\))");

}