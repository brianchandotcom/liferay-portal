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
import com.liferay.portal.kernel.util.Validator;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
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

		content = _generateFeatureFlags(content);
		content = _generateFeatureFlagUI(fileName, content);

		return content;
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
		_featureFlagKeys = new ArrayList<>();

		List<String> fileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[] {"**/test/**"},
			new String[] {
				"**/bnd.bnd", "**/*.java", "**/*.js", "**/*.json", "**/*.jsp",
				"**/*.jspf", "**/*.jsx", "**/*.ts", "**/*.tsx"
			},
			getSourceFormatterExcludes(), true);

		for (String curFileName : fileNames) {
			curFileName = StringUtil.replace(
				curFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String fileContent = FileUtil.read(new File(curFileName));

			if (curFileName.endsWith("bnd.bnd")) {
				String liferaySiteInitializerFeatureFlagKey =
					BNDSourceUtil.getDefinitionValue(
						fileContent, "Liferay-Site-Initializer-Feature-Flag");

				if (liferaySiteInitializerFeatureFlagKey == null) {
					continue;
				}

				_featureFlagKeys.add(liferaySiteInitializerFeatureFlagKey);
			}
			else if (curFileName.endsWith(".java")) {
				_featureFlagKeys.addAll(
					_getFeatureFlagKeys(fileContent, _featureFlagPattern1));
				_featureFlagKeys.addAll(_getFeatureFlagKeys(fileContent, true));
			}
			else if (curFileName.endsWith(".json")) {
				_featureFlagKeys.addAll(
					_getFeatureFlagKeys(fileContent, _featureFlagPattern4));
			}
			else if (curFileName.endsWith(".jsp") ||
					 curFileName.endsWith(".jspf")) {

				_featureFlagKeys.addAll(
					_getFeatureFlagKeys(fileContent, _featureFlagPattern3));
				_featureFlagKeys.addAll(
					_getFeatureFlagKeys(fileContent, false));
			}
			else {
				_featureFlagKeys.addAll(
					_getFeatureFlagKeys(fileContent, _featureFlagPattern3));
			}
		}

		ListUtil.distinct(_featureFlagKeys, new NaturalOrderStringComparator());

		Matcher matcher = _featureFlagsPattern.matcher(content);

		if (matcher.find()) {
			String matchedFeatureFlags = matcher.group(2);

			if (_featureFlagKeys.isEmpty()) {
				if (matchedFeatureFlags.contains("feature.flag.")) {
					return StringUtil.replaceFirst(
						content, matchedFeatureFlags, StringPool.BLANK,
						matcher.start(2));
				}

				return content;
			}

			List<String> deprecationFeatureFlagKeys = new ArrayList<>();

			Matcher deprecationFeatureFlagKeyMatcher =
				_deprecationFeatureFlagPattern.matcher(content);

			while (deprecationFeatureFlagKeyMatcher.find()) {
				deprecationFeatureFlagKeys.add(
					deprecationFeatureFlagKeyMatcher.group(1));
			}

			StringBundler sb = new StringBundler(_featureFlagKeys.size() * 14);

			for (String featureFlagKey : _featureFlagKeys) {
				String featureFlagPropertyKey =
					"feature.flag." + featureFlagKey;

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

				if (deprecationFeatureFlagKeys.contains(featureFlagKey)) {
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

	private String _generateFeatureFlagUI(String fileName, String content) {
		Map<String, Map<String, String>> featureFlagKeysUIMap = new TreeMap<>(
			new NaturalOrderStringComparator());

		for (String featureFlagKey : _featureFlagKeys) {
			featureFlagKeysUIMap.put(featureFlagKey, new HashMap<>());
		}

		Matcher matcher = _featureFlagUIPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String matchedFeatureFlagUI = matcher.group(2);
		int startPos = -1;

		Matcher featureFlagMatcher = _featureFlagPattern5.matcher(
			matchedFeatureFlagUI);

		while (featureFlagMatcher.find()) {
			if (startPos == -1) {
				startPos = featureFlagMatcher.start();
			}

			if (_featureFlagKeys.isEmpty()) {
				return StringUtil.replaceFirst(
					content, matchedFeatureFlagUI,
					matchedFeatureFlagUI.substring(0, startPos),
					matcher.start(2));
			}

			String featureFlagKey = featureFlagMatcher.group(2);

			if (!featureFlagKeysUIMap.containsKey(featureFlagKey)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Remove ", featureFlagMatcher.group(1), ", because ",
						featureFlagKey, " not be found in ## Feature Flag"));

				featureFlagKeysUIMap.put(featureFlagKey, new HashMap<>());
			}

			Map<String, String> propertyMap = featureFlagKeysUIMap.get(
				featureFlagKey);

			propertyMap.put(
				featureFlagMatcher.group(3), featureFlagMatcher.group(4));
		}

		if (startPos == -1) {
			return content;
		}

		StringBundler sb = new StringBundler();

		sb.append(matchedFeatureFlagUI.substring(0, startPos));

		for (Map.Entry<String, Map<String, String>> entry :
				featureFlagKeysUIMap.entrySet()) {

			Map<String, String> propertyMap = entry.getValue();

			String typeValue = propertyMap.get("type");

			if (Validator.isNotNull(typeValue) &&
				(StringUtil.equals(typeValue, "beta") ||
				 StringUtil.equals(typeValue, "deprecation") ||
				 StringUtil.equals(typeValue, "release")) &&
				(Validator.isNull(propertyMap.get("title")) ||
				 Validator.isNull(propertyMap.get("description")))) {

				addMessage(
					fileName,
					"'Description' and 'Title' is necessary when type is " +
						"'beta', 'deprecation' or 'release' for " +
							entry.getKey());
			}

			String propertyMapString = _propertyMapToString(
				propertyMap, entry.getKey());

			if (Validator.isNotNull(propertyMapString)) {
				sb.append(propertyMapString);
			}
		}

		return StringUtil.replaceFirst(
			content, matchedFeatureFlagUI, sb.toString(), matcher.start(2));
	}

	private List<String> _getFeatureFlagKeys(
		String content, boolean javaSource) {

		List<String> featureFlagKeys = new ArrayList<>();

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
				return featureFlagKeys;
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

				featureFlagKeys.add(StringUtil.unquote(parameter));
			}
		}

		return featureFlagKeys;
	}

	private List<String> _getFeatureFlagKeys(String content, Pattern pattern) {
		List<String> featureFlagKeys = new ArrayList<>();

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			featureFlagKeys.add(matcher.group(1));
		}

		return featureFlagKeys;
	}

	private String _propertyMapToString(
		Map<String, String> propertyMap, String featureFlag) {

		Set<String> keySet = propertyMap.keySet();

		List<String> keys = new ArrayList<>(keySet);

		keys.sort(new NaturalOrderStringComparator());

		StringBundler sb = new StringBundler();

		for (String key : keys) {
			String featureFlagPropertyKey = StringBundler.concat(
				"feature.flag.", featureFlag, ".", key);

			String environmentVariable = ToolsUtil.encodeEnvironmentProperty(
				featureFlagPropertyKey);

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
			sb.append(propertyMap.get(key));
		}

		return sb.toString();
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
	private static final Pattern _featureFlagPattern5 = Pattern.compile(
		"\n+ +#\n +# Env: LIFERAY_FEATURE_PERIOD_FLAG_PERIOD__.+\n +#\n +" +
			"(feature\\.flag\\.([A-Z]+-\\d+)\\.(\\w+))=(.+)");
	private static final Pattern _featureFlagsPattern = Pattern.compile(
		"(\n|\\A)##\n## Feature Flag\n##(\n\n[\\s\\S]*?)(?=(\n\n##|\\Z))");
	private static final Pattern _featureFlagUIPattern = Pattern.compile(
		"(\n|\\A)##\n## Feature Flag UI\n##(\n\n[\\s\\S]*?)(?=(\n\n##|\\Z))");

	private List<String> _allFileNames;
	private List<String> _featureFlagKeys;

}