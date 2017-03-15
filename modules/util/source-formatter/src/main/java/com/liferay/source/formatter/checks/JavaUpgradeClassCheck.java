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

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaUpgradeClassCheck extends BaseFileCheck {

	public JavaUpgradeClassCheck(List<String> excludes) {
		_excludes = excludes;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (hasGeneratedTag(content)) {
			return new Tuple(content, Collections.emptySet());
		}

		if (!fileName.contains("/upgrade/")) {
			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		_checkLocaleUtil(sourceFormatterMessages, fileName, content);
		_checkServiceUtil(
			sourceFormatterMessages, fileName, absolutePath, content);
		_checkTimestamp(sourceFormatterMessages, fileName, content);

		if (!fileName.endsWith("Upgrade.java")) {
			return new Tuple(content, sourceFormatterMessages);
		}

		_checkAnnotation(sourceFormatterMessages, fileName, content);
		_checkRegistryVersion(sourceFormatterMessages, fileName, content);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkAnnotation(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String content) {

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
			addMessage(
				sourceFormatterMessages, fileName,
				"@Component requires 'service' parameter");
		}
	}

	private void _checkLocaleUtil(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String content) {

		// LPS-41205

		int pos = content.indexOf("LocaleUtil.getDefault()");

		if (pos != -1) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Use UpgradeProcessUtil.getDefaultLanguageId(companyId) " +
					"instead of LocaleUtil.getDefault()",
				getLineCount(content, pos));
		}
	}

	private void _checkRegistryVersion(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String content) {

		// LPS-65685

		Matcher matcher1 = _registryRegisterPattern.matcher(content);

		while (matcher1.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher1.start())) {
				continue;
			}

			List<String> parametersList = JavaSourceUtil.getParameterList(
				content.substring(matcher1.start()));

			if (parametersList.size() <= 4) {
				continue;
			}

			String previousUpgradeClassName = null;

			for (int i = 3; i < parametersList.size(); i++) {
				String parameter = parametersList.get(i);

				Matcher matcher2 = _upgradeClassNamePattern.matcher(parameter);

				if (!matcher2.find()) {
					break;
				}

				String upgradeClassName = matcher2.group(1);

				if ((previousUpgradeClassName != null) &&
					(previousUpgradeClassName.compareTo(upgradeClassName) >
						0)) {

					addMessage(
						sourceFormatterMessages, fileName,
						"Break up Upgrade classes with a minor version " +
							"increment or order alphabetically, see LPS-65685",
						getLineCount(content, matcher1.start()));

					break;
				}

				previousUpgradeClassName = upgradeClassName;
			}
		}
	}

	private void _checkServiceUtil(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String absolutePath, String content) {

		// LPS-34911

		if (!isExcludedPath(_excludes, absolutePath) &&
			fileName.contains("/portal/upgrade/") &&
			!fileName.contains("/test/") &&
			!fileName.contains("/testIntegration/")) {

			int pos = content.indexOf("ServiceUtil.");

			if (pos != -1) {
				addMessage(
					sourceFormatterMessages, fileName,
					"Do not use *ServiceUtil classes in upgrade classes, see " +
						"LPS-34911",
					getLineCount(content, pos));
			}
		}
	}

	private void _checkTimestamp(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String content) {

		// LPS-41205

		int pos = content.indexOf("rs.getDate(");

		if (pos != -1) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Use rs.getTimestamp instead of rs.getDate",
				getLineCount(content, pos));
		}
	}

	private final Pattern _componentAnnotationPattern = Pattern.compile(
		"@Component(\n|\\([\\s\\S]*?\\)\n)");
	private final List<String> _excludes;
	private final Pattern _registryRegisterPattern = Pattern.compile(
		"registry\\.register\\((.*?)\\);\n", Pattern.DOTALL);
	private final Pattern _upgradeClassNamePattern = Pattern.compile(
		"new .*?(\\w+)\\(", Pattern.DOTALL);

}