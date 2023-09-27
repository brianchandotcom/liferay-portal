/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaGetLayoutDisplayPageObjectProviderCheck
	extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String javaMethodContent = javaMethod.getContent();

			javaMethodContent = _beforeFormatIteration(
				javaMethodContent, fileName);

			Matcher methodMatcher = _methodPattern.matcher(javaMethodContent);

			while (methodMatcher.find()) {
				boolean infoItemReference = Objects.equals(
					getVariableTypeName(
						javaMethodContent, content, methodMatcher.group(2)),
					"InfoItemReference");

				if (infoItemReference ||
					!hasClassOrVariableName(
						"LayoutDisplayPageProvider", javaMethodContent, content,
						methodMatcher.group(1))) {

					continue;
				}

				String variableClassName = _WARNING_CASE_VARIABLE;

				Matcher methodByClassNameMatcher =
					_methodByClassNamePattern.matcher(javaMethodContent);

				if (methodByClassNameMatcher.find()) {
					variableClassName = methodByClassNameMatcher.group(1);
				}

				String line = methodMatcher.group(0);

				String indent = SourceUtil.getIndent(line);

				javaMethodContent = StringUtil.replace(
					javaMethodContent, line,
					_replaceLine(
						variableClassName, methodMatcher.group(2), indent,
						line));
			}

			content = StringUtil.replace(
				content, javaMethod.getContent(), javaMethodContent);
		}

		return content;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {"com.liferay.info.item.InfoItemReference"};
	}

	private String _beforeFormatIteration(String content, String fileName) {
		if (content.contains(_WARNING_CASE_VARIABLE)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Could not resolve variable className for new ",
					"InfoItemReference(). Replace ",
					"'TO_BE_REPLACED_FOR_CLASSNAME' with the correct type"));
		}

		return content;
	}

	private String _getInfoItemReferenceImplementation(
		String className, String classPK, String indent) {

		StringBundler sb = new StringBundler(7);

		sb.append(indent);
		sb.append("InfoItemReference infoItemReference = new");
		sb.append("InfoItemReference(");
		sb.append(className);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(classPK);
		sb.append(");");

		return sb.toString();
	}

	private String _replaceLine(
		String className, String classPK, String indent, String line) {

		String infoItemReferenceImplementation =
			_getInfoItemReferenceImplementation(className, classPK, indent);
		String newLine = StringUtil.replace(line, classPK, "infoItemReference");

		return StringBundler.concat(
			infoItemReferenceImplementation, "\n\n", newLine);
	}

	private static final String _WARNING_CASE_VARIABLE =
		"TO_BE_REPLACED_FOR_CLASSNAME";

	private static final Pattern _methodByClassNamePattern = Pattern.compile(
		"\\.\\s*getLayoutDisplayPageProviderByClassName" +
			"\\(\\s*(\\w+)\\s*\\);");
	private static final Pattern _methodPattern = Pattern.compile(
		"\\t+\\w+\\s*\\w+\\s*=\\s*(\\w+\\.\\s*" +
			"getLayoutDisplayPageObjectProvider\\(\\s*(\\w+)\\s*\\));");

}