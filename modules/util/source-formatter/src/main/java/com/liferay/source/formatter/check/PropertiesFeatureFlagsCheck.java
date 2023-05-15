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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesFeatureFlagsCheck extends BaseFileCheck {

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/portal-impl/src/portal.properties")) {
			return content;
		}

		return _generateFeatureFlags(content);
	}

	private String _generateFeatureFlags(String content) throws IOException {
		List<String> featureFlags = new ArrayList<>();

		List<String> fileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[] {"**/test/**"},
			new String[] {
				"**/bnd.bnd", "**/*.java", "**/*.js", "**/*.jsp", "**/*.jspf",
				"**/*.jsx", "**/*.ts", "**/*.tsx"
			},
			getSourceFormatterExcludes(), true);

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String fileContent = FileUtil.read(new File(fileName));

			if (fileName.endsWith("bnd.bnd")) {
				String liferaySiteInitializerFeatureFlag =
					BNDSourceUtil.getDefinitionValue(
						fileContent, "Liferay-Site-Initializer-Feature-Flag");

				if (liferaySiteInitializerFeatureFlag == null) {
					continue;
				}

				featureFlags.add(liferaySiteInitializerFeatureFlag);
			}
			else if (fileName.endsWith(".java")) {
				featureFlags.addAll(
					_getFeatureFlags(fileContent, _featureFlagPattern1));
				featureFlags.addAll(_getFeatureFlags(fileContent));
			}
			else {
				featureFlags.addAll(
					_getFeatureFlags(fileContent, _featureFlagPattern3));
			}
		}

		ListUtil.distinct(featureFlags, new NaturalOrderStringComparator());

		Matcher matcher = _featureFlagsPattern.matcher(content);

		if (matcher.find()) {
			String matchedFeatureFlags = matcher.group(2);

			if (featureFlags.isEmpty()) {
				if (matchedFeatureFlags.contains("feature.flag.")) {
					return StringUtil.replaceFirst(
						content, matchedFeatureFlags, StringPool.BLANK,
						matcher.start(2));
				}

				return content;
			}

			StringBundler sb = new StringBundler(featureFlags.size() * 14);

			for (String featureFlag : featureFlags) {
				featureFlag = "feature.flag." + featureFlag;

				String environmentVariable =
					ToolsUtil.encodeEnvironmentProperty(featureFlag);

				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(StringPool.POUND);
				sb.append(StringPool.NEW_LINE);
				sb.append("    # Env: ");
				sb.append(environmentVariable);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(StringPool.POUND);
				sb.append(StringPool.NEW_LINE);
				sb.append(StringPool.FOUR_SPACES);
				sb.append(featureFlag);
				sb.append("=false");
			}

			if (matchedFeatureFlags.contains("feature.flag.")) {
				content = StringUtil.replaceFirst(
					content, matchedFeatureFlags, sb.toString(),
					matcher.start(2));
			}
			else {
				content = StringUtil.insert(
					content, sb.toString(), matcher.start(2));
			}
		}

		return content;
	}

	private List<String> _getFeatureFlags(String content) {
		List<String> featureFlags = new ArrayList<>();

		Matcher matcher = _featureFlagPattern2.matcher(content);

		while (matcher.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				JavaSourceUtil.getMethodCall(content, matcher.start()));

			if (parameterList.isEmpty()) {
				return featureFlags;
			}

			String parameter = null;

			if (parameterList.size() == 1) {
				parameter = parameterList.get(0);
			}
			else {
				parameter = parameterList.get(1);
			}

			if ((parameter != null) && parameter.endsWith(StringPool.QUOTE) &&
				parameter.startsWith(StringPool.QUOTE)) {

				featureFlags.add(StringUtil.unquote(parameter));
			}
		}

		return featureFlags;
	}

	private List<String> _getFeatureFlags(String content, Pattern pattern) {
		List<String> featureFlags = new ArrayList<>();

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			featureFlags.add(matcher.group(1));
		}

		return featureFlags;
	}

	private static final Pattern _featureFlagPattern1 = Pattern.compile(
		"\\.feature\\.flag=(.+?)\"");
	private static final Pattern _featureFlagPattern2 = Pattern.compile(
		"FeatureFlagManagerUtil\\.isEnabled\\(");
	private static final Pattern _featureFlagPattern3 = Pattern.compile(
		"Liferay\\.FeatureFlags\\['(.+?)'\\]");
	private static final Pattern _featureFlagsPattern = Pattern.compile(
		"(\n|\\A)##\n## Feature Flag\n##(\n\n[\\s\\S]*?)(?=(\n\n##|\\Z))");

	private List<String> _allFileNames;

}