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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.JavaTermCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.configuration.ConfigurationLoader;
import com.liferay.source.formatter.checks.configuration.SourceCheckConfiguration;
import com.liferay.source.formatter.checks.configuration.SourceChecksResult;
import com.liferay.source.formatter.checks.configuration.SourceChecksSuppressions;
import com.liferay.source.formatter.checks.configuration.SourceFormatterConfiguration;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author Hugo Huijser
 */
public class SourceChecksUtil {

	public static List<SourceCheck> getSourceChecks(
			String sourceProcessorName, boolean portalSource,
			boolean subrepository, boolean includeModuleChecks)
		throws Exception {

		SourceFormatterConfiguration sourceFormatterConfiguration =
			ConfigurationLoader.loadConfiguration("sourcechecks.xml");

		List<SourceCheck> sourceChecks = _getSourceChecks(
			sourceFormatterConfiguration, sourceProcessorName, portalSource,
			subrepository, includeModuleChecks);

		sourceChecks.addAll(
			_getSourceChecks(
				sourceFormatterConfiguration, "all", includeModuleChecks,
				subrepository, includeModuleChecks));

		return sourceChecks;
	}

	public static SourceChecksResult processSourceChecks(
			File file, String fileName, String absolutePath, String content,
			boolean modulesFile, List<SourceCheck> sourceChecks,
			SourceChecksSuppressions sourceChecksSuppressions)
		throws Exception {

		SourceChecksResult sourceChecksResult = new SourceChecksResult(content);

		if (ListUtil.isEmpty(sourceChecks)) {
			return sourceChecksResult;
		}

		JavaClass javaClass = null;
		List<JavaClass> anonymousClasses = null;

		for (SourceCheck sourceCheck : sourceChecks) {
			if (sourceCheck.isModulesCheck() && !modulesFile) {
				continue;
			}

			Class<?> clazz = sourceCheck.getClass();

			if (sourceChecksSuppressions.isSuppressed(
					clazz.getSimpleName(), absolutePath)) {

				continue;
			}

			if (sourceCheck instanceof FileCheck) {
				sourceChecksResult = _processFileCheck(
					sourceChecksResult, (FileCheck)sourceCheck, fileName,
					absolutePath);
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
								fileName, pe.getMessage(), null, -1));

						continue;
					}
				}

				sourceChecksResult = _processJavaTermCheck(
					sourceChecksResult, (JavaTermCheck)sourceCheck, javaClass,
					anonymousClasses, fileName, absolutePath);
			}

			if (!content.equals(sourceChecksResult.getContent())) {
				return sourceChecksResult;
			}
		}

		return sourceChecksResult;
	}

	private static List<SourceCheck> _getSourceChecks(
			SourceFormatterConfiguration sourceFormatterConfiguration,
			String sourceProcessorName, boolean portalSource,
			boolean subrepository, boolean includeModuleChecks)
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

				BeanUtils.setProperty(
					sourceCheck, attributeName,
					sourceCheckConfiguration.getAttributeValue(attributeName));
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