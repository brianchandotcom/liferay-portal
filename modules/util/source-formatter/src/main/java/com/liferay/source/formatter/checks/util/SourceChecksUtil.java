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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
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
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author Hugo Huijser
 */
public class SourceChecksUtil {

	public static List<SourceCheck> getSourceChecks(
			SourceFormatterConfiguration sourceFormatterConfiguration,
			String sourceProcessorName, Map<String, Properties> propertiesMap,
			boolean portalSource, boolean subrepository,
			boolean includeModuleChecks)
		throws Exception {

		List<SourceCheck> sourceChecks = _getSourceChecks(
			sourceFormatterConfiguration, sourceProcessorName, propertiesMap,
			portalSource, subrepository, includeModuleChecks);

		sourceChecks.addAll(
			_getSourceChecks(
				sourceFormatterConfiguration, "all", propertiesMap,
				includeModuleChecks, subrepository, includeModuleChecks));

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
			if (sourceCheck.isModulesCheck() && !modulesFile) {
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
					try {
						gradleFile = GradleFileParser.parse(
							fileName, sourceChecksResult.getContent());
					}
					catch (ParseException pe) {
						sourceChecksResult.addSourceFormatterMessage(
							new SourceFormatterMessage(
								fileName, pe.getMessage(),
								CheckType.SOURCE_CHECK, clazz.getSimpleName(),
								null, -1));

						continue;
					}
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
					catch (ParseException pe) {
						sourceChecksResult.addSourceFormatterMessage(
							new SourceFormatterMessage(
								fileName, pe.getMessage(),
								CheckType.SOURCE_CHECK, clazz.getSimpleName(),
								null, -1));

						continue;
					}
				}

				sourceChecksResult = _processJavaTermCheck(
					sourceChecksResult, (JavaTermCheck)sourceCheck, javaClass,
					anonymousClasses, fileName, absolutePath);
			}

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

	private static List<String> _getOverrideValues(
		String attributeName, Class<? extends SourceCheck> sourceCheckClass,
		Map<String, Properties> propertiesMap) {

		String simpleName = sourceCheckClass.getSimpleName();

		String[] simpleNameArray = simpleName.split(
			"(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

		String simpleNameValue = StringUtil.merge(simpleNameArray, ".");

		String[] attributeNameArray = attributeName.split(
			"(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

		String attributeNameValue = StringUtil.merge(attributeNameArray, ".");

		String key = StringBundler.concat(
			"override.", StringUtil.toLowerCase(simpleNameValue), ".",
			StringUtil.toLowerCase(attributeNameValue));

		StringBundler sb = new StringBundler(propertiesMap.size() * 2);

		for (Map.Entry<String, Properties> entry : propertiesMap.entrySet()) {
			Properties properties = entry.getValue();

			String value = properties.getProperty(key);

			if (value != null) {
				sb.append(value);
				sb.append(CharPool.COMMA);
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return ListUtil.toList(StringUtil.split(sb.toString()));
	}

	private static List<SourceCheck> _getSourceChecks(
			SourceFormatterConfiguration sourceFormatterConfiguration,
			String sourceProcessorName, Map<String, Properties> propertiesMap,
			boolean portalSource, boolean subrepository,
			boolean includeModuleChecks)
		throws Exception {

		List<SourceCheck> sourceChecks = new ArrayList<>();

		List<SourceCheckConfiguration> sourceCheckConfigurations =
			sourceFormatterConfiguration.getSourceCheckConfigurations(
				sourceProcessorName);

		if (sourceCheckConfigurations == null) {
			return sourceChecks;
		}

		for (SourceCheckConfiguration sourceCheckConfiguration :
				sourceCheckConfigurations) {

			String sourceCheckName = sourceCheckConfiguration.getName();

			if (!sourceCheckName.contains(StringPool.PERIOD)) {
				sourceCheckName =
					"com.liferay.source.formatter.checks." + sourceCheckName;
			}

			Class<?> sourceCheckClass = null;

			try {
				sourceCheckClass = Class.forName(sourceCheckName);
			}
			catch (ClassNotFoundException cnfe) {
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
				 sourceCheck.isPortalCheck()) ||
				(!includeModuleChecks && sourceCheck.isModulesCheck())) {

				continue;
			}

			for (String attributeName :
					sourceCheckConfiguration.attributeNames()) {

				List<String> values = Collections.emptyList();

				if (portalSource) {
					values = _getOverrideValues(
						attributeName, sourceCheck.getClass(), propertiesMap);
				}

				if (values.isEmpty()) {
					values = sourceCheckConfiguration.getAttributeValues(
						attributeName);
				}

				for (String value : values) {
					BeanUtils.setProperty(sourceCheck, attributeName, value);
				}
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
		throws Exception {

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