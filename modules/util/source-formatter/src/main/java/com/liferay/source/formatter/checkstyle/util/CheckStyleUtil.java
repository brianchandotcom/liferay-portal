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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.ProgressStatus;
import com.liferay.source.formatter.ProgressStatusUpdate;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checkstyle.Checker;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.DebugUtil;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.filters.SuppressionsLoader;

import java.io.File;
import java.io.OutputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

import org.xml.sax.InputSource;

/**
 * @author Hugo Huijser
 */
public class CheckStyleUtil {

	public static Set<SourceFormatterMessage> process(
			Set<File> files, List<File> suppressionsFiles,
			SourceFormatterArgs sourceFormatterArgs,
			BlockingQueue<ProgressStatusUpdate> progressStatusQueue)
		throws Exception {

		_progressStatusQueue = progressStatusQueue;

		_sourceFormatterMessages.clear();

		Configuration configuration = _getConfiguration(sourceFormatterArgs);

		if (sourceFormatterArgs.isShowDebugInformation()) {
			DebugUtil.addCheckNames(
				CheckType.CHECKSTYLE, _getCheckNames(configuration));
		}

		Checker checker = _getChecker(
			configuration, suppressionsFiles, sourceFormatterArgs);

		checker.process(ListUtil.fromCollection(files));

		return _sourceFormatterMessages;
	}

	private static Configuration _addAttribute(
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

	private static Checker _getChecker(
			Configuration configuration, List<File> suppressionsFiles,
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		Checker checker = new Checker();

		ClassLoader classLoader = CheckStyleUtil.class.getClassLoader();

		checker.setModuleClassLoader(classLoader);

		for (File suppressionsFile : suppressionsFiles) {
			checker.addFilter(
				SuppressionsLoader.loadSuppressions(
					suppressionsFile.getAbsolutePath()));
		}

		checker.configure(configuration);

		AuditListener listener = new SourceFormatterLogger(
			new UnsyncByteArrayOutputStream(), true,
			sourceFormatterArgs.getBaseDirName());

		checker.addListener(listener);

		return checker;
	}

	private static List<String> _getCheckNames(Configuration configuration) {
		List<String> checkNames = new ArrayList<>();

		String name = configuration.getName();

		if (name.startsWith("com.liferay.")) {
			int pos = name.lastIndexOf(CharPool.PERIOD);

			if (!name.endsWith("Check")) {
				name = name.concat("Check");
			}

			checkNames.add(name.substring(pos + 1));
		}

		for (Configuration childConfiguration : configuration.getChildren()) {
			checkNames.addAll(_getCheckNames(childConfiguration));
		}

		return checkNames;
	}

	private static Configuration _getConfiguration(
			SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		ClassLoader classLoader = CheckStyleUtil.class.getClassLoader();

		Configuration configuration = ConfigurationLoader.loadConfiguration(
			new InputSource(classLoader.getResourceAsStream("checkstyle.xml")),
			new PropertiesExpander(System.getProperties()), false);

		configuration = _addAttribute(
			configuration, "maxLineLength",
			String.valueOf(sourceFormatterArgs.getMaxLineLength()),
			"com.liferay.source.formatter.checkstyle.checks.PlusStatement");
		configuration = _addAttribute(
			configuration, "showDebugInformation",
			String.valueOf(sourceFormatterArgs.isShowDebugInformation()),
			"com.liferay.*");

		return configuration;
	}

	private static BlockingQueue<ProgressStatusUpdate> _progressStatusQueue;
	private static final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new TreeSet<>();

	private static class SourceFormatterLogger extends DefaultLogger {

		public SourceFormatterLogger(
			OutputStream outputStream, boolean closeStreamsAfterUse,
			String baseDirName) {

			super(outputStream, closeStreamsAfterUse);

			_baseDirName = baseDirName;
		}

		@Override
		public void addError(AuditEvent auditEvent) {
			_sourceFormatterMessages.add(
				new SourceFormatterMessage(
					_getRelativizedFileName(auditEvent),
					auditEvent.getMessage(), auditEvent.getLine()));

			super.addError(auditEvent);
		}

		@Override
		public void fileFinished(AuditEvent auditEvent) {
			try {
				_progressStatusQueue.put(
					new ProgressStatusUpdate(
						ProgressStatus.CHECK_STYLE_FILE_COMPLETED));
			}
			catch (InterruptedException ie) {
			}

			super.fileFinished(auditEvent);
		}

		private Path _getAbsoluteNormalizedPath(String pathName) {
			Path path = Paths.get(pathName);

			path = path.toAbsolutePath();

			return path.normalize();
		}

		private String _getRelativizedFileName(AuditEvent auditEvent) {
			if (Validator.isNull(_baseDirName)) {
				return auditEvent.getFileName();
			}

			Path baseDirPath = _getAbsoluteNormalizedPath(_baseDirName);

			Path relativizedPath = baseDirPath.relativize(
				_getAbsoluteNormalizedPath(auditEvent.getFileName()));

			return _baseDirName +
				StringUtil.replace(
					relativizedPath.toString(), CharPool.BACK_SLASH,
					CharPool.SLASH);
		}

		private final String _baseDirName;

	}

}