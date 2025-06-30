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

			content = _formatMethod(content, javaMethod.getContent());
		}

		return content;
	}

	private String _formatMethod(String content, String javaMethodContent) {
		Matcher matcher = _autoBatchPattern.matcher(javaMethodContent);

		String newMethodContent = javaMethodContent;

		if (matcher.find()) {
			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"\\t*", matcher.group(1), "\\s*",
					"=\\s*(\\w+)\\.prepareStatement\\(\\s*([\\s\\S]+\")\\);"));

			Matcher declarationMatcher = pattern.matcher(javaMethodContent);

			if (declarationMatcher.find()) {
				StringBuilder sb = new StringBuilder();

				sb.append(StringPool.NEW_LINE);
				sb.append(SourceUtil.getIndent(matcher.group()));
				sb.append(StringPool.TAB);
				sb.append(declarationMatcher.group(1));
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
				sb.append(declarationMatcher.group(2));

				newMethodContent = StringUtil.replace(
					javaMethodContent, matcher.group(),
					StringUtil.replace(
						matcher.group(), matcher.group(1), sb.toString()));
			}
		}

		return StringUtil.replace(content, javaMethodContent, newMethodContent);
	}

	private static final Pattern _autoBatchPattern = Pattern.compile(
		"\\t*\\w+\\.autoBatch\\((\\w+)\\)");

}