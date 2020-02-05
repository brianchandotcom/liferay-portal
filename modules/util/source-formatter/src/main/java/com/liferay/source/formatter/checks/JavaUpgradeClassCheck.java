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

import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaUpgradeClassCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.contains("/upgrade/")) {
			return content;
		}

		_checkLocaleUtil(fileName, content);
		_checkServiceUtil(fileName, absolutePath, content);
		_checkTimestamp(fileName, content);

		if (!fileName.endsWith("Upgrade.java")) {
			return content;
		}

		_checkAnnotation(fileName, content);
		_checkRegistryVersion(fileName, content);

		return content;
	}

	private void _checkAnnotation(String fileName, String content) {

		// LPS-59828

		if (!content.contains("implements UpgradeStepRegistrator")) {
			return;
		}

		Matcher matcher = _componentAnnotationPattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		String componentAnnotation = matcher.group();

		if (!componentAnnotation.contains("service =")) {
			addMessage(fileName, "@Component requires 'service' parameter");
		}
	}

	private void _checkLocaleUtil(String fileName, String content) {
		int pos = content.indexOf("LocaleUtil.getDefault()");

		if (pos != -1) {
			addMessage(
				fileName,
				"Use UpgradeProcessUtil.getDefaultLanguageId(companyId) " +
					"instead of LocaleUtil.getDefault()",
				getLineNumber(content, pos));
		}
	}

	private void _checkRegistryVersion(String fileName, String content) {
		Matcher matcher1 = _registryRegisterPattern.matcher(content);

		while (matcher1.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher1.start())) {
				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(matcher1.start()));

			if (parameterList.size() <= 4) {
				continue;
			}

			String previousUpgradeClassName = null;

			for (int i = 3; i < parameterList.size(); i++) {
				String parameter = parameterList.get(i);

				Matcher matcher2 = _upgradeClassNamePattern.matcher(parameter);

				if (!matcher2.find()) {
					break;
				}

				String upgradeClassName = matcher2.group(1);

				if ((previousUpgradeClassName != null) &&
					(previousUpgradeClassName.compareTo(upgradeClassName) >
						0)) {

					addMessage(
						fileName,
						"Break up Upgrade classes with a minor version " +
							"increment or order alphabetically",
						getLineNumber(content, matcher1.start()));

					break;
				}

				previousUpgradeClassName = upgradeClassName;
			}
		}
	}

	private void _checkServiceUtil(
		String fileName, String absolutePath, String content) {

		if (!isExcludedPath(_UPGRADE_SERVICE_UTIL_EXCLUDES, absolutePath) &&
			fileName.contains("/portal/upgrade/") &&
			!fileName.contains("/test/") &&
			!fileName.contains("/testIntegration/")) {

			int pos = content.indexOf("ServiceUtil.");

			if (pos != -1) {
				addMessage(
					fileName,
					"Do not use *ServiceUtil classes in upgrade classes",
					getLineNumber(content, pos));
			}
		}
	}

	private void _checkTimestamp(String fileName, String content) {
		int pos = content.indexOf("rs.getDate(");

		if (pos != -1) {
			addMessage(
				fileName, "Use rs.getTimestamp instead of rs.getDate",
				getLineNumber(content, pos));
		}
	}

	private static final String _UPGRADE_SERVICE_UTIL_EXCLUDES =
		"upgrade.service.util.excludes";

	private static final Pattern _componentAnnotationPattern = Pattern.compile(
		"@Component(\n|\\([\\s\\S]*?\\)\n)");
	private static final Pattern _registryRegisterPattern = Pattern.compile(
		"registry\\.register\\((.*?)\\);\n", Pattern.DOTALL);
	private static final Pattern _upgradeClassNamePattern = Pattern.compile(
		"new .*?(\\w+)\\(", Pattern.DOTALL);

}