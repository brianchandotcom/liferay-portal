/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
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

		_checkUnnecessaryFeatureFlags(fileName, content);

		return _generateFeatureFlags(content);
	}

	private void _checkUnnecessaryFeatureFlags(String fileName, String content)
		throws IOException {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			if (!key.startsWith("feature.flag.") || !key.endsWith(".type")) {
				continue;
			}

			String value = properties.getProperty(key);

			if (StringUtil.equals(value, "dev")) {
				addMessage(
					fileName,
					"Remove unnecessary property '" + key +
						"', since 'dev' is the default value");
			}
		}
	}

	private String _generateFeatureFlags(String content) throws IOException {
		List<String> featureFlags = new ArrayList<>();

		List<String> fileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[] {"**/test/**"},
			new String[] {
				"**/bnd.bnd", "**/*.java", "**/*.js", "**/*.json", "**/*.jsp",
				"**/*.jspf", "**/*.jsx", "**/*.ts", "**/*.tsx"
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
				featureFlags.addAll(_getFeatureFlags(fileContent, true));
			}
			else if (fileName.endsWith(".json")) {
				featureFlags.addAll(
					_getFeatureFlags(fileContent, _featureFlagPattern4));
			}
			else if (fileName.endsWith(".jsp")) {
				featureFlags.addAll(
					_getFeatureFlags(fileContent, _featureFlagPattern3));
				featureFlags.addAll(_getFeatureFlags(fileContent, false));
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

			List<String> deprecationFeatureFlags = new ArrayList<>();

			Matcher deprecationFeatureFlagMatcher =
				_deprecationFeatureFlagPattern.matcher(content);

			while (deprecationFeatureFlagMatcher.find()) {
				deprecationFeatureFlags.add(
					deprecationFeatureFlagMatcher.group(1));
			}

			StringBundler sb = new StringBundler(featureFlags.size() * 14);

			for (String featureFlag : featureFlags) {
				String featureFlagPropertyKey = "feature.flag." + featureFlag;

				String environmentVariable =
					ToolsUtil.encodeEnvironmentProperty(featureFlagPropertyKey);

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
				sb.append(featureFlagPropertyKey);
				sb.append(StringPool.EQUAL);

				if (deprecationFeatureFlags.contains(featureFlag)) {
					sb.append(true);
				}
				else {
					sb.append(false);
				}
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

	private List<String> _getFeatureFlags(String content, boolean javaSource) {
		List<String> featureFlags = new ArrayList<>();

		Matcher matcher = _featureFlagPattern2.matcher(content);

		while (matcher.find()) {
			String methodCall = null;

			if (javaSource) {
				methodCall = JavaSourceUtil.getMethodCall(
					content, matcher.start());
			}
			else {
				methodCall = JavaSourceUtil.getMethodCall(
					content.substring(matcher.start()), 0);
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

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

	private static final Pattern _deprecationFeatureFlagPattern =
		Pattern.compile("feature\\.flag\\.([A-Z]+-\\d+)\\.type=deprecation");
	private static final Pattern _featureFlagPattern1 = Pattern.compile(
		"feature\\.flag[.=]([A-Z]+-\\d+)");
	private static final Pattern _featureFlagPattern2 = Pattern.compile(
		"FeatureFlagManagerUtil\\.isEnabled\\(");
	private static final Pattern _featureFlagPattern3 = Pattern.compile(
		"Liferay\\.FeatureFlags\\['(.+?)'\\]");
	private static final Pattern _featureFlagPattern4 = Pattern.compile(
		"\"featureFlag\": \"(.+?)\"");
	private static final Pattern _featureFlagsPattern = Pattern.compile(
		"(\n|\\A)##\n## Feature Flag\n##(\n\n[\\s\\S]*?)(?=(\n\n##|\\Z))");

	private List<String> _allFileNames;

}