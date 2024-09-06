/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kyle Miho
 */
public class UpgradeJavaSortFieldNameTranslatorCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (Objects.equals(javaTerm.getName(), "getEntityClass")) {
				return content;
			}
		}

		for (String implementedClassName :
				javaClass.getImplementedClassNames()) {

			if (implementedClassName.equals("SortFieldNameTranslator")) {
				return _getNewContent(content);
			}
		}

		return content;
	}

	@Override
	protected String[] getNewImports() {
		return _newImports;
	}

	private String _getModelClassPath(String content) throws Exception {
		Matcher matcher = _modelClassPathPattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(2);
		}

		throw new Exception(
			"Unable to find the value of " +
				"ContributorConstants.ENTRY_CLASS_NAME_PROPERTY_KEY");
	}

	private String _getNewContent(String content) throws Exception {
		_newImports = new String[] {_getModelClassPath(content)};

		String method = _getNewMethod(content);

		String newContent = content.replaceFirst(
			"@Component\\((\n.*)*?\\)",
			joinLines(
				"@Component(", "\tservice = SortFieldNameTranslator.class",
				")"));

		return newContent.replaceFirst(
			"(public class .*?\\s*implements SortFieldNameTranslator \\{)",
			"$1\n\n" + method);
	}

	private String _getNewMethod(String content) throws Exception {
		Matcher matcher = _sortFieldNameClassPattern.matcher(content);

		if (matcher.find()) {
			String clazz = matcher.group(1);

			return joinLines(
				"\t@Override", "\tpublic Class<?> getEntityClass() {",
				String.format("\t\treturn %s.class;", clazz), "\t}", "");
		}

		throw new Exception(
			"Unable to find class that implements SortFieldNameTranslator");
	}

	private static final Pattern _modelClassPathPattern = Pattern.compile(
		"@Component\\((\\n.*)*?property.*?\"=(.*?)\".*?(\\n.*)*?\\)");
	private static final Pattern _sortFieldNameClassPattern = Pattern.compile(
		"public class (\\w+)SortFieldNameTranslator.*?" +
			"\\s*implements SortFieldNameTranslator");

	private String[] _newImports;

}