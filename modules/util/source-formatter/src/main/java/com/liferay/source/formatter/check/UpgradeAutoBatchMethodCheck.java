/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Regisson Aguiar
 */
public class UpgradeAutoBatchMethodCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		if (javaClass == null) {
			return content;
		}

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!javaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)javaTerm;

			String javaMethodContent = javaMethod.getContent();

			content = _formatContent(
				javaClass.getExtendedClassNames(), content, fileName,
				javaMethodContent);
		}

		return content;
	}

	private String _formatContent(
		List<String> classNames, String content, String fileName,
		String javaMethodContent) {

		Matcher matcher = _autoBatchPrepareStatementPattern.matcher(
			javaMethodContent);

		String newContent = javaMethodContent;

		if (matcher.find()) {
			if (!hasClassOrVariableName(
					"Connection", javaMethodContent, content, fileName,
					matcher.group(1)) &&
				!classNames.contains("BaseUpgradeProcess")) {

				return javaMethodContent;
			}

			newContent = StringUtil.replace(
				javaMethodContent, matcher.group(),
				_getNewMethodCall(matcher.group(2), matcher, matcher.group(3)));
		}
		else {
			matcher = _autoBatchPattern.matcher(javaMethodContent);

			if (!matcher.find()) {
				return content;
			}

			String variable = matcher.group(1);

			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"\\t*", variable, "\\s*",
					"=\\s*(\\w+)\\.prepareStatement\\(\\s*([\\s\\S]+\")\\);"));

			Matcher declarationMatcher = pattern.matcher(javaMethodContent);

			if (declarationMatcher.find()) {
				newContent = StringUtil.replace(
					javaMethodContent, matcher.group(),
					_getNewMethodCall(
						declarationMatcher.group(1), matcher,
						declarationMatcher.group(2)));
			}
		}

		return StringUtil.replace(content, javaMethodContent, newContent);
	}

	private String _getNewMethodCall(
		String connectionAttribute, Matcher matcher, String sqlAttribute) {

		return StringUtil.replace(
			matcher.group(), matcher.group(1),
			StringBundler.concat(
				StringPool.NEW_LINE, SourceUtil.getIndent(matcher.group()),
				StringPool.TAB, connectionAttribute, StringPool.COMMA,
				StringPool.SPACE, sqlAttribute));
	}

	private static final Pattern _autoBatchPattern = Pattern.compile(
		"\\t*\\w+\\.autoBatch\\((\\w+)\\)");
	private static final Pattern _autoBatchPrepareStatementPattern =
		Pattern.compile(
			"\\t*\\w+\\.autoBatch\\((\\s*(\\w+)\\.prepareStatement\\(" +
				"\\s*([\\s\\S]+)\\))\\)");

}