/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaAddFDSTableSchemaFieldCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<String> importNames = javaClass.getImportNames();

		if (!importNames.contains(
				"com.liferay.frontend.data.set.view.table." +
					"FDSTableSchemaBuilder")) {

			return content;
		}

		return _replaceAddFDSTableSchemaFieldMethodCalls(content, javaClass);
	}

	private String _replaceAddFDSTableSchemaFieldMethodCall(
		String content, String fileContent, String methodCall) {

		if (!hasClassOrVariableName(
				"FDSTableSchemaBuilder", content, fileContent, methodCall)) {

			return methodCall;
		}

		return StringUtil.replace(methodCall, "addFDSTableSchemaField", "add");
	}

	private String _replaceAddFDSTableSchemaFieldMethodCalls(
		String content, JavaClass javaClass) {

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			Matcher matcher = _addFDSTableSchemaFieldPattern.matcher(
				javaMethodContent);

			while (matcher.find()) {
				javaMethodContent = StringUtil.replace(
					javaMethodContent, matcher.group(0),
					_replaceAddFDSTableSchemaFieldMethodCall(
						javaMethodContent, content, matcher.group(0)));
			}

			content = StringUtil.replace(
				content, javaMethod.getContent(), javaMethodContent);
		}

		return content;
	}

	private static final Pattern _addFDSTableSchemaFieldPattern =
		Pattern.compile(
			"\\w+\\.\\s*addFDSTableSchemaField\\s*\\(\\s*.+\\s*\\);");

}