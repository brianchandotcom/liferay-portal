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

package com.liferay.source.formatter.checks.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.GradleFileCheck;
import com.liferay.source.formatter.checks.JavaTermCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.configuration.SourceCheckConfiguration;
import com.liferay.source.formatter.checks.configuration.SourceChecksResult;
import com.liferay.source.formatter.checks.configuration.SourceFormatterConfiguration;
import com.liferay.source.formatter.checks.configuration.SourceFormatterSuppressions;
import com.liferay.source.formatter.parser.GradleFile;
import com.liferay.source.formatter.parser.GradleFileParser;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.SourceFormatterCheckUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class SourceChecksUtil {

	public static List<SourceCheck> getSourceChecks(
			SourceFormatterConfiguration sourceFormatterConfiguration,
			String sourceProcessorName, Map<String, Properties> propertiesMap,
			List<String> skipCheckNames, boolean portalSource,
			boolean subrepository, boolean includeModuleChecks,
			String checkName)
		throws Exception {

		List<SourceCheck> sourceChecks = _getSourceChecks(
			sourceFormatterConfiguration, sourceProcessorName, propertiesMap,
			skipCheckNames, portalSource, subrepository, includeModuleChecks,
			checkName);

		sourceChecks.addAll(
			_getSourceChecks(
				sourceFormatterConfiguration, "all", propertiesMap,
				skipCheckNames, includeModuleChecks, subrepository,
				includeModuleChecks, checkName));

		return sourceChecks;
	}

	public static SourceChecksResult processSourceChecks(
			File file, String fileName, String absolutePath, String content,
			Set<String> modifiedMessages, boolean modulesFile,
			List<SourceCheck> sourceChecks,
			SourceFormatterSuppressions sourceFormatterSuppressions,
			boolean showDebugInformation)
		throws Exception {

		SourceChecksResult sourceChecksResult = new SourceChecksResult(content);

		if (ListUtil.isEmpty(sourceChecks)) {
			return sourceChecksResult;
		}

		GradleFile gradleFile = null;
		JavaClass javaClass = null;
		List<JavaClass> anonymousClasses = null;

		for (SourceCheck sourceCheck : sourceChecks) {
			if (!sourceCheck.isEnabled(absolutePath) ||
				(sourceCheck.isModuleSourceCheck() && !modulesFile)) {

				continue;
			}

			Class<?> clazz = sourceCheck.getClass();

			if (sourceFormatterSuppressions.isSuppressed(
					clazz.getSimpleName(), absolutePath)) {

				continue;
			}

			long startTime = System.currentTimeMillis();

			if (sourceCheck instanceof FileCheck) {
				sourceChecksResult = _processFileCheck(
					sourceChecksResult, (FileCheck)sourceCheck, fileName,
					absolutePath);
			}
			else if (sourceCheck instanceof GradleFileCheck) {
				if (gradleFile == null) {
					gradleFile = GradleFileParser.parse(
						fileName, sourceChecksResult.getContent());
				}

				sourceChecksResult = _processGradleFileCheck(
					sourceChecksResult, (GradleFileCheck)sourceCheck,
					gradleFile, fileName, absolutePath);
			}
			else {
				if (javaClass == null) {
					try {
						anonymousClasses =
							JavaClassParser.parseAnonymousClasses(
								sourceChecksResult.getContent());
						javaClass = JavaClassParser.parseJavaClass(
							fileName, sourceChecksResult.getContent());
					}
					catch (ParseException parseException) {
						Class<?> superClass = clazz.getSuperclass();

						sourceChecksResult.addSourceFormatterMessage(
							new SourceFormatterMessage(
								fileName, parseException.getMessage(),
								CheckType.SOURCE_CHECK, clazz.getSimpleName(),
								superClass.getSimpleName(), null, -1));

						continue;
					}
				}

				sourceChecksResult = _processJavaTermCheck(
					sourceChecksResult, (JavaTermCheck)sourceCheck, javaClass,
					anonymousClasses, fileName, absolutePath);
			}

			sourceChecksResult.setMostRecentProcessedSourceCheck(sourceCheck);

			if (showDebugInformation) {
				long endTime = System.currentTimeMillis();

				DebugUtil.increaseProcessingTime(
					clazz.getSimpleName(), endTime - startTime);
			}

			if (!content.equals(sourceChecksResult.getContent())) {
				StringBundler sb = new StringBundler(7);

				sb.append(file.toString());
				sb.append(CharPool.SPACE);
				sb.append(CharPool.OPEN_PARENTHESIS);

				CheckType checkType = CheckType.SOURCE_CHECK;

				sb.append(checkType.getValue());

				sb.append(CharPool.COLON);
				sb.append(clazz.getSimpleName());
				sb.append(CharPool.CLOSE_PARENTHESIS);

				modifiedMessages.add(sb.toString());

				if (showDebugInformation) {
					DebugUtil.printContentModifications(
						clazz.getSimpleName(), fileName, content,
						sourceChecksResult.getContent());
				}

				return sourceChecksResult;
			}
		}

		return sourceChecksResult;
	}

	private static JSONObject _getAttributesJSONObject(
		Map<String, Properties> propertiesMap, String checkName,
		SourceCheckConfiguration sourceCheckConfiguration) {

		JSONObject attributesJSONObject = new JSONObjectImpl();

		JSONObject configurationAttributesJSONObject =
			sourceCheckConfiguration.getAttributesJSONObject();

		if (configurationAttributesJSONObject.length() != 0) {
			attributesJSONObject.put(
				SourceFormatterCheckUtil.CONFIGURATION_FILE_LOCATION,
				configurationAttributesJSONObject);
		}

		attributesJSONObject = SourceFormatterCheckUtil.addPropertiesAttributes(
			attributesJSONObject, propertiesMap,
			SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH);

		return SourceFormatterCheckUtil.addPropertiesAttributes(
			attributesJSONObject, propertiesMap, CheckType.SOURCE_CHECK,
			checkName);
	}

	private static List<SourceCheck> _getSourceChecks(
			SourceFormatterConfiguration sourceFormatterConfiguration,
			String sourceProcessorName, Map<String, Properties> propertiesMap,
			List<String> skipCheckNames, boolean portalSource,
			boolean subrepository, boolean includeModuleChecks,
			String checkName)
		throws Exception {

		List<SourceCheck> sourceChecks = new ArrayList<>();

		List<SourceCheckConfiguration> sourceCheckConfigurations =
			sourceFormatterConfiguration.getSourceCheckConfigurations(
				sourceProcessorName);

		if (sourceCheckConfigurations == null) {
			return sourceChecks;
		}

		JSONObject excludesJSONObject =
			SourceFormatterCheckUtil.getExcludesJSONObject(propertiesMap);

		for (SourceCheckConfiguration sourceCheckConfiguration :
				sourceCheckConfigurations) {

			String sourceCheckName = SourceFormatterUtil.getSimpleName(
				sourceCheckConfiguration.getName());

			if ((checkName != null) && !checkName.equals(sourceCheckName)) {
				continue;
			}

			sourceCheckName =
				"com.liferay.source.formatter.checks." + sourceCheckName;

			Class<?> sourceCheckClass = null;

			try {
				sourceCheckClass = Class.forName(sourceCheckName);
			}
			catch (ClassNotFoundException classNotFoundException) {
				SourceFormatterUtil.printError(
					"sourcechecks.xml",
					"sourcechecks.xml: Class " + sourceCheckName +
						" cannot be found");

				continue;
			}

			Constructor<?> declaredConstructor =
				sourceCheckClass.getDeclaredConstructor();

			Object instance = declaredConstructor.newInstance();

			if (!(instance instanceof SourceCheck)) {
				continue;
			}

			SourceCheck sourceCheck = (SourceCheck)instance;

			if ((!portalSource && !subrepository &&
				 sourceCheck.isLiferaySourceCheck()) ||
				(!includeModuleChecks && sourceCheck.isModuleSourceCheck())) {

				continue;
			}

			Class<?> clazz = sourceCheck.getClass();

			if (skipCheckNames.contains(clazz.getSimpleName())) {
				continue;
			}

			if (excludesJSONObject.length() != 0) {
				sourceCheck.setExcludes(excludesJSONObject.toString());
			}

			JSONObject attributesJSONObject = _getAttributesJSONObject(
				propertiesMap, clazz.getSimpleName(), sourceCheckConfiguration);

			if (attributesJSONObject.length() != 0) {
				sourceCheck.setAttributes(attributesJSONObject.toString());
			}

			sourceChecks.add(sourceCheck);
		}

		return sourceChecks;
	}

	private static SourceChecksResult _processFileCheck(
			SourceChecksResult sourceChecksResult, FileCheck fileCheck,
			String fileName, String absolutePath)
		throws Exception {

		sourceChecksResult.setContent(
			fileCheck.process(
				fileName, absolutePath, sourceChecksResult.getContent()));

		for (SourceFormatterMessage sourceFormatterMessage :
				fileCheck.getSourceFormatterMessages(fileName)) {

			sourceChecksResult.addSourceFormatterMessage(
				sourceFormatterMessage);
		}

		return sourceChecksResult;
	}

	private static SourceChecksResult _processGradleFileCheck(
			SourceChecksResult sourceChecksResult,
			GradleFileCheck gradleFileCheck, GradleFile gradleFile,
			String fileName, String absolutePath)
		throws IOException {

		String content = gradleFileCheck.process(
			fileName, absolutePath, gradleFile,
			sourceChecksResult.getContent());

		sourceChecksResult.setContent(content);

		for (SourceFormatterMessage sourceFormatterMessage :
				gradleFileCheck.getSourceFormatterMessages(fileName)) {

			sourceChecksResult.addSourceFormatterMessage(
				sourceFormatterMessage);
		}

		return sourceChecksResult;
	}

	private static SourceChecksResult _processJavaTermCheck(
			SourceChecksResult sourceChecksResult, JavaTermCheck javaTermCheck,
			JavaClass javaClass, List<JavaClass> anonymousClasses,
			String fileName, String absolutePath)
		throws Exception {

		sourceChecksResult.setContent(
			javaTermCheck.process(
				fileName, absolutePath, javaClass,
				sourceChecksResult.getContent()));

		for (SourceFormatterMessage sourceFormatterMessage :
				javaTermCheck.getSourceFormatterMessages(fileName)) {

			sourceChecksResult.addSourceFormatterMessage(
				sourceFormatterMessage);
		}

		for (JavaClass anonymousClass : anonymousClasses) {
			sourceChecksResult.setContent(
				javaTermCheck.process(
					fileName, absolutePath, anonymousClass,
					sourceChecksResult.getContent()));

			for (SourceFormatterMessage sourceFormatterMessage :
					javaTermCheck.getSourceFormatterMessages(fileName)) {

				sourceChecksResult.addSourceFormatterMessage(
					sourceFormatterMessage);
			}
		}

		return sourceChecksResult;
	}

}