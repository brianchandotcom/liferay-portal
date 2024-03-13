/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeJavaScreenContributorClassCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		String oldJavaClassContent = javaClass.getContent();

		Matcher matcher = _screenContributorPattern.matcher(
			oldJavaClassContent);

		if (!matcher.find()) {
			return content;
		}

		String javaTermContent = null;

		List<String> oldVariables = new ArrayList<>();

		List<String> oldMethods = new ArrayList<>();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (childJavaTerm.isJavaVariable()) {
				JavaVariable javaVariable = (JavaVariable)childJavaTerm;

				javaTermContent = javaVariable.getContent();

				oldVariables.add(javaTermContent);
			}
			else if (childJavaTerm.isJavaMethod()) {
				JavaMethod javaMethod = (JavaMethod)childJavaTerm;

				javaTermContent = javaMethod.getContent();

				oldMethods.add(javaTermContent);
			}
		}

		return StringUtil.replace(
			content, oldJavaClassContent,
			_formatContent(
				matcher, oldJavaClassContent, oldMethods, oldVariables));
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.configuration.admin.display.ConfigurationScreen",
			"com.liferay.configuration.admin.display." +
				"ConfigurationScreenWrapper",
			"com.liferay.portal.settings.configuration.admin.display." +
				"PortalSettingsConfigurationScreenFactory"
		};
	}

	private static List<String> _formatMethodsLines(List<String> methods) {
		List<String> methodsLines = new ArrayList<>();

		for (String method : methods) {
			String[] methodLines = StringUtil.splitLines(method);

			for (int i = 0; i < methodLines.length; i++) {
				methodLines[i] = StringUtil.removeFirst(
					methodLines[i], StringPool.TAB);

				if (StringUtil.equals(
						methodLines[i], StringPool.CLOSE_CURLY_BRACE)) {

					methodLines[i] = methodLines[i] + StringPool.NEW_LINE;
				}

				if (methodLines[i].isEmpty()) {
					methodsLines.set(
						methodsLines.size() - 1,
						methodLines[i - 1] + StringPool.NEW_LINE);

					continue;
				}

				methodsLines.add(methodLines[i]);
			}
		}

		return methodsLines;
	}

	private String _formatContent(
		Matcher matcher, String oldJavaClassContent, List<String> oldMethods,
		List<String> oldVariables) {

		StringBundler sb = new StringBundler(17);

		sb.append("@Component(service = ConfigurationScreen.class)\n");
		sb.append(matcher.group(1));
		sb.append("extends ConfigurationScreenWrapper {\n\n\t");
		sb.append("@Override\n\tprotected ConfigurationScreen ");
		sb.append("getConfigurationScreen() {\n\t\treturn ");
		sb.append("_portalSettingsConfigurationScreenFactory.create(\n\t\t\t");
		sb.append("new ");

		String className = matcher.group(2);

		if (className.contains("ScreenContributor")) {
			className = StringUtil.removeSubstring(
				className, "ScreenContributor");
		}
		else {
			className = className + "InnerClass";
		}

		sb.append(className);
		sb.append("());\n\t}\n\n\t@Reference\n\t");
		sb.append("private PortalSettingsConfigurationScreenFactory ");
		sb.append("_portalSettingsConfigurationScreenFactory;\n\n\t");
		sb.append(StringUtil.merge(oldVariables, "\n\n\t"));
		sb.append("\n\n\t");

		String innerClassName = StringBundler.concat(
			"private class ", className,
			"\n\t\timplements PortalSettingsConfigurationScreenContributor {");

		sb.append(innerClassName);

		sb.append("\n\n\t\t");

		List<String> oldMethodLines = _formatMethodsLines(oldMethods);

		sb.append(
			com.liferay.petra.string.StringUtil.merge(
				oldMethodLines, "\n\t\t"));

		sb.append("\n\t}\n\n}");

		return StringUtil.replace(
			oldJavaClassContent, oldJavaClassContent, sb.toString());
	}

	private static final Pattern _screenContributorPattern = Pattern.compile(
		"(\\t*public\\s*class\\s*(\\w*)\\s*)implements\\s*" +
			"PortalSettingsConfigurationScreenContributor");

}