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

package com.liferay.source.formatter.checkstyle.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.configuration.SourceFormatterSuppressions;
import com.liferay.source.formatter.checkstyle.Checker;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.xml.sax.InputSource;

/**
 * @author Hugo Huijser
 */
public class CheckstyleUtil {

	public static final String ALLOY_MVC_SRC_DIR =
		"/src/main/resources/alloy_mvc/jsp/";

	public static final String ALLOY_MVC_TMP_DIR =
		"/tmp/main/resources/alloy_mvc/jsp/";

	public static final int BATCH_SIZE = 1000;

	public static Configuration addAttribute(
		Configuration configuration, String key, String value,
		String... regexChecks) {

		if (!(configuration instanceof DefaultConfiguration)) {
			return configuration;
		}

		DefaultConfiguration defaultConfiguration =
			(DefaultConfiguration)configuration;

		DefaultConfiguration treeWalkerModule = null;

		for (Configuration childConfiguration :
				defaultConfiguration.getChildren()) {

			String name = childConfiguration.getName();

			if (name.equals("TreeWalker") &&
				(childConfiguration instanceof DefaultConfiguration)) {

				treeWalkerModule = (DefaultConfiguration)childConfiguration;

				break;
			}
		}

		if (treeWalkerModule == null) {
			return configuration;
		}

		for (Configuration childConfiguration :
				treeWalkerModule.getChildren()) {

			if (!(childConfiguration instanceof DefaultConfiguration)) {
				continue;
			}

			String name = childConfiguration.getName();

			for (String regexCheck : regexChecks) {
				if (name.matches(regexCheck)) {
					DefaultConfiguration defaultChildConfiguration =
						(DefaultConfiguration)childConfiguration;

					defaultChildConfiguration.addAttribute(key, value);
				}
			}
		}

		return defaultConfiguration;
	}

	public static List<String> getCheckNames(Configuration configuration) {
		List<String> checkNames = new ArrayList<>();

		String name = configuration.getName();

		if (name.startsWith("com.liferay.")) {
			int pos = name.lastIndexOf(CharPool.PERIOD);

			checkNames.add(name.substring(pos + 1));
		}

		for (Configuration childConfiguration : configuration.getChildren()) {
			checkNames.addAll(getCheckNames(childConfiguration));
		}

		return checkNames;
	}

	public static Configuration getConfiguration(String configurationFileName)
		throws Exception {

		ClassLoader classLoader = CheckstyleUtil.class.getClassLoader();

		return ConfigurationLoader.loadConfiguration(
			new InputSource(
				classLoader.getResourceAsStream(configurationFileName)),
			new PropertiesExpander(System.getProperties()), false);
	}

	public static String getJavaFileName(String fileName) {
		if (!fileName.contains(ALLOY_MVC_SRC_DIR)) {
			return fileName;
		}

		String s = fileName.replace(ALLOY_MVC_SRC_DIR, ALLOY_MVC_TMP_DIR);

		return s.substring(0, s.lastIndexOf(".")) + ".java";
	}

	public static String getSourceFileName(String fileName) {
		if (!fileName.contains(ALLOY_MVC_TMP_DIR)) {
			return fileName;
		}

		String s = fileName.replace(ALLOY_MVC_TMP_DIR, ALLOY_MVC_SRC_DIR);

		return s.substring(0, s.lastIndexOf(".")) + ".jspf";
	}

	public static synchronized Set<SourceFormatterMessage>
			getSourceFormatterMessages(
				Configuration configuration, File[] files,
				SourceFormatterSuppressions sourceFormatterSuppressions,
				SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		Checker checker = new Checker();

		ClassLoader classLoader = CheckstyleUtil.class.getClassLoader();

		checker.setModuleClassLoader(classLoader);

		checker.addFilter(sourceFormatterSuppressions.getCheckstyleFilterSet());

		checker.configure(configuration);

		CheckstyleLogger checkstyleLogger = new CheckstyleLogger(
			new UnsyncByteArrayOutputStream(), true,
			sourceFormatterArgs.getBaseDirName());

		checker.addListener(checkstyleLogger);
		checker.setCheckstyleLogger(checkstyleLogger);

		checker.process(Arrays.asList(files));

		return checker.getSourceFormatterMessages();
	}

	public static List<File> getSuppressionsFiles(File[] suppressionsFiles)
		throws Exception {

		List<File> tempSuppressionsFiles = new ArrayList<>();

		for (File suppressionsFile : suppressionsFiles) {
			String fileName = suppressionsFile.getName();

			if (!fileName.endsWith("checkstyle-suppressions.xml")) {
				continue;
			}

			String content = FileUtil.read(suppressionsFile);

			if (!content.contains(ALLOY_MVC_SRC_DIR)) {
				continue;
			}

			File tempSuppressionsFile = new File(
				suppressionsFile.getParentFile() +
					"/tmp/checkstyle-suppressions.xml");

			String[] lines = StringUtil.splitLines(content);

			StringBundler sb = new StringBundler(lines.length * 2);

			for (String line : lines) {
				if (!line.contains(ALLOY_MVC_SRC_DIR)) {
					sb.append(line);
					sb.append("\n");
				}
				else {
					String s = StringUtil.replace(
						line, new String[] {ALLOY_MVC_SRC_DIR, ".jspf"},
						new String[] {ALLOY_MVC_TMP_DIR, ".java"});

					sb.append(s);

					sb.append("\n");
				}
			}

			sb.setIndex(sb.index() - 1);

			FileUtil.write(tempSuppressionsFile, sb.toString());

			tempSuppressionsFiles.add(tempSuppressionsFile);
		}

		return tempSuppressionsFiles;
	}

}