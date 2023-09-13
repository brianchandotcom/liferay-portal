/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradePortletDisplayCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		if (fileName.endsWith(".jsp")) {
			Matcher getPortletInstanceConfigurationMatcher =
				_getPortletInstanceConfigurationPattern.matcher(content);

			while (getPortletInstanceConfigurationMatcher.find()) {
				return _getNewContent(
					newContent, fileName,
					getPortletInstanceConfigurationMatcher, content,
					"themeDisplay");
			}
		}
		else {
			JavaClass javaClass = JavaClassParser.parseJavaClass(
				fileName, content);

			for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
				if (!childJavaTerm.isJavaMethod()) {
					continue;
				}

				JavaMethod javaMethod = (JavaMethod)childJavaTerm;

				String javaMethodContent = javaMethod.getContent();

				Matcher getPortletInstanceConfigurationMatcher =
					_getPortletInstanceConfigurationPattern.matcher(
						javaMethodContent);

				while (getPortletInstanceConfigurationMatcher.find()) {
					Matcher themeDisplayMatcher = _themeDisplayPattern.matcher(
						javaMethodContent);

					if (themeDisplayMatcher.find()) {
						newContent = _getNewContent(
							javaMethodContent, fileName,
							getPortletInstanceConfigurationMatcher, newContent,
							themeDisplayMatcher.group(1));
					}
					else {
						newContent = _getNewContent(
							javaMethodContent, fileName,
							getPortletInstanceConfigurationMatcher, newContent,
							getPortletInstanceConfigurationMatcher.group(1) +
								".getThemeDisplay()");
					}
				}
			}
		}

		return newContent;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.configuration.module.configuration." +
				"ConfigurationProviderUtil"
		};
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"java", "jsp"};
	}

	private String _getNewContent(
		String content, String fileName, Matcher matcher, String newContent,
		String newParameters) {

		String methodCall = JavaSourceUtil.getMethodCall(
			content, matcher.start());

		String variableName = getVariableName(methodCall);

		if (!hasClassOrVariableName(
				"PortletDisplay", newContent, fileName, methodCall) &&
			!variableName.contains("portletDisplay")) {

			return newContent;
		}

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		if (parameterList.size() > 1) {
			return newContent;
		}

		String indent = JavaSourceUtil.getIndent(methodCall);

		return StringUtil.replace(
			newContent, content,
			StringUtil.replace(
				content, methodCall,
				JavaSourceUtil.addMethodNewParameters(
					indent, new int[] {parameterList.size()},
					indent + "ConfigurationProviderUtil." +
						"getPortletInstanceConfiguration(",
					new String[] {newParameters}, parameterList)));
	}

	private static final Pattern _getPortletInstanceConfigurationPattern =
		Pattern.compile("\\t*(\\w+)(.\\s*getPortletInstanceConfiguration\\()");
	private static final Pattern _themeDisplayPattern = Pattern.compile(
		"ThemeDisplay\\s*(\\w+)");

}