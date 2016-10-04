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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.GitException;
import com.liferay.portal.tools.GitUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Hugo Huijser
 */
public class SourceFormatter {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		try {
			SourceFormatterArgs sourceFormatterArgs = new SourceFormatterArgs();

			boolean autoFix = ArgumentsUtil.getBoolean(
				arguments, "source.auto.fix", SourceFormatterArgs.AUTO_FIX);

			sourceFormatterArgs.setAutoFix(autoFix);

			String baseDirName = ArgumentsUtil.getString(
				arguments, "source.base.dir",
				SourceFormatterArgs.BASE_DIR_NAME);

			sourceFormatterArgs.setBaseDirName(baseDirName);

			boolean formatCurrentBranch = ArgumentsUtil.getBoolean(
				arguments, "format.current.branch",
				SourceFormatterArgs.FORMAT_CURRENT_BRANCH);

			sourceFormatterArgs.setFormatCurrentBranch(formatCurrentBranch);

			boolean formatLatestAuthor = ArgumentsUtil.getBoolean(
				arguments, "format.latest.author",
				SourceFormatterArgs.FORMAT_LATEST_AUTHOR);

			sourceFormatterArgs.setFormatLatestAuthor(formatLatestAuthor);

			boolean formatLocalChanges = ArgumentsUtil.getBoolean(
				arguments, "format.local.changes",
				SourceFormatterArgs.FORMAT_LOCAL_CHANGES);

			sourceFormatterArgs.setFormatLocalChanges(formatLocalChanges);

			if (formatCurrentBranch) {
				String gitWorkingBranchName = ArgumentsUtil.getString(
					arguments, "git.working.branch.name",
					SourceFormatterArgs.GIT_WORKING_BRANCH_NAME);

				sourceFormatterArgs.setGitWorkingBranchName(
					gitWorkingBranchName);

				sourceFormatterArgs.setRecentChangesFileNames(
					GitUtil.getCurrentBranchFileNames(
						baseDirName, gitWorkingBranchName));
			}
			else if (formatLatestAuthor) {
				sourceFormatterArgs.setRecentChangesFileNames(
					GitUtil.getLatestAuthorFileNames(baseDirName));
			}
			else if (formatLocalChanges) {
				sourceFormatterArgs.setRecentChangesFileNames(
					GitUtil.getLocalChangesFileNames(baseDirName));
			}

			String copyrightFileName = ArgumentsUtil.getString(
				arguments, "source.copyright.file",
				SourceFormatterArgs.COPYRIGHT_FILE_NAME);

			sourceFormatterArgs.setCopyrightFileName(copyrightFileName);

			String fileNamesString = ArgumentsUtil.getString(
				arguments, "source.files", StringPool.BLANK);

			String[] fileNames = StringUtil.split(
				fileNamesString, StringPool.COMMA);

			if (ArrayUtil.isNotEmpty(fileNames)) {
				sourceFormatterArgs.setFileNames(Arrays.asList(fileNames));
			}

			boolean includeSubrepositories = ArgumentsUtil.getBoolean(
				arguments, "include.subrepositories",
				SourceFormatterArgs.INCLUDE_SUBREPOSITORIES);

			sourceFormatterArgs.setIncludeSubrepositories(
				includeSubrepositories);

			int maxLineLength = ArgumentsUtil.getInteger(
				arguments, "max.line.length",
				SourceFormatterArgs.MAX_LINE_LENGTH);

			sourceFormatterArgs.setMaxLineLength(maxLineLength);

			boolean printErrors = ArgumentsUtil.getBoolean(
				arguments, "source.print.errors",
				SourceFormatterArgs.PRINT_ERRORS);

			sourceFormatterArgs.setPrintErrors(printErrors);

			int processorThreadCount = ArgumentsUtil.getInteger(
				arguments, "processor.thread.count",
				SourceFormatterArgs.PROCESSOR_THREAD_COUNT);

			sourceFormatterArgs.setProcessorThreadCount(processorThreadCount);

			boolean throwException = ArgumentsUtil.getBoolean(
				arguments, "source.throw.exception",
				SourceFormatterArgs.THROW_EXCEPTION);

			sourceFormatterArgs.setThrowException(throwException);

			boolean useProperties = ArgumentsUtil.getBoolean(
				arguments, "source.use.properties",
				SourceFormatterArgs.USE_PROPERTIES);

			sourceFormatterArgs.setUseProperties(useProperties);

			SourceFormatter sourceFormatter = new SourceFormatter(
				sourceFormatterArgs);

			sourceFormatter.format();
		}
		catch (GitException ge) {
			System.out.println(ge.getMessage());

			System.exit(0);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public SourceFormatter(SourceFormatterArgs sourceFormatterArgs) {
		_sourceFormatterArgs = sourceFormatterArgs;
	}

	public void format() throws Exception {
		_properties = _getProperties();

		List<SourceProcessor> sourceProcessors = new ArrayList<>();

		sourceProcessors.add(new BNDSourceProcessor());
		sourceProcessors.add(new CSSSourceProcessor());
		sourceProcessors.add(new FTLSourceProcessor());
		sourceProcessors.add(new GradleSourceProcessor());
		sourceProcessors.add(new GroovySourceProcessor());
		sourceProcessors.add(new JavaSourceProcessor());
		sourceProcessors.add(new JSONSourceProcessor());
		sourceProcessors.add(new JSPSourceProcessor());
		sourceProcessors.add(new JSSourceProcessor());
		sourceProcessors.add(new MarkdownSourceProcessor());
		sourceProcessors.add(new PropertiesSourceProcessor());
		sourceProcessors.add(new SHSourceProcessor());
		sourceProcessors.add(new SoySourceProcessor());
		sourceProcessors.add(new SQLSourceProcessor());
		sourceProcessors.add(new TLDSourceProcessor());
		sourceProcessors.add(new XMLSourceProcessor());

		ExecutorService executorService = Executors.newFixedThreadPool(
			sourceProcessors.size());

		List<Future<Void>> futures = new ArrayList<>(sourceProcessors.size());

		for (final SourceProcessor sourceProcessor : sourceProcessors) {
			Future<Void> future = executorService.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_runSourceProcessor(sourceProcessor);

						return null;
					}

				});

			futures.add(future);
		}

		ExecutionException ee1 = null;

		for (Future<Void> future : futures) {
			try {
				future.get();
			}
			catch (ExecutionException ee2) {
				if (ee1 == null) {
					ee1 = ee2;
				}
				else {
					ee1.addSuppressed(ee2);
				}
			}
		}

		executorService.shutdown();

		while (!executorService.isTerminated()) {
			Thread.sleep(20);
		}

		if (ee1 != null) {
			throw ee1;
		}

		if (_sourceFormatterArgs.isThrowException()) {
			if (!_sourceFormatterMessages.isEmpty()) {
				StringBundler sb = new StringBundler(
					_sourceFormatterMessages.size() * 2);

				for (SourceFormatterMessage sourceFormatterMessage :
						_sourceFormatterMessages) {

					sb.append(sourceFormatterMessage.toString());
					sb.append("\n");
				}

				throw new Exception(sb.toString());
			}

			if (_firstSourceMismatchException != null) {
				throw _firstSourceMismatchException;
			}
		}
	}

	public List<String> getModifiedFileNames() {
		return _modifiedFileNames;
	}

	public SourceFormatterArgs getSourceFormatterArgs() {
		return _sourceFormatterArgs;
	}

	public Set<SourceFormatterMessage> getSourceFormatterMessages() {
		return _sourceFormatterMessages;
	}

	public SourceMismatchException getSourceMismatchException() {
		return _firstSourceMismatchException;
	}

	private Properties _getProperties() throws Exception {
		SourceFormatterHelper sourceFormatterHelper = new SourceFormatterHelper(
			_sourceFormatterArgs.isUseProperties());

		String fileName = "source-formatter.properties";

		File portalImplDir = sourceFormatterHelper.getFile(
			_sourceFormatterArgs.getBaseDirName(), "portal-impl",
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (portalImplDir == null) {
			Properties properties = null;

			for (int i = 0; i <= ToolsUtil.PLUGINS_MAX_DIR_LEVEL; i++) {
				try {
					InputStream inputStream = new FileInputStream(
						_sourceFormatterArgs.getBaseDirName() + fileName);

					Properties props = new Properties();

					props.load(inputStream);

					properties = props;

					break;
				}
				catch (FileNotFoundException fnfe) {
				}

				fileName = "../" + fileName;
			}

			if (properties == null) {
				properties = new Properties();
			}

			String excludesValue = properties.getProperty(
				"source.formatter.excludes");

			if (Validator.isNull(excludesValue)) {
				excludesValue = StringUtil.merge(_defaultExcludes);
			}
			else {
				excludesValue +=
					StringPool.COMMA + StringUtil.merge(_defaultExcludes);
			}

			properties.setProperty("source.formatter.excludes", excludesValue);

			return properties;
		}

		Set<String> excludes = new HashSet<>(_defaultExcludes);

		List<Properties> propertiesList = new ArrayList<>();

		// Find properties file in portal-impl/src/

		File propertiesFile = sourceFormatterHelper.getFile(
			_sourceFormatterArgs.getBaseDirName(),
			"portal-impl/src/" + fileName, ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (propertiesFile != null) {
			InputStream inputStream = new FileInputStream(propertiesFile);

			Properties properties = new Properties();

			properties.load(inputStream);

			propertiesList.add(properties);

			String excludesValue = properties.getProperty(
				"source.formatter.excludes");

			List<String> excludesList = ListUtil.fromString(
				GetterUtil.getString(excludesValue), StringPool.COMMA);

			excludes.addAll(excludesList);
		}

		// Find properties files in any parent directory

		String parentDirName = _sourceFormatterArgs.getBaseDirName() + "../";

		for (int i = 0; i < ToolsUtil.PORTAL_MAX_DIR_LEVEL - 1; i++) {
			try {
				InputStream inputStream = new FileInputStream(
					parentDirName + fileName);

				Properties properties = new Properties();

				properties.load(inputStream);

				propertiesList.add(properties);
			}
			catch (FileNotFoundException fnfe) {
			}

			parentDirName += "../";
		}

		// Find properties file in any child directory

		List<String> modulePropertiesFileNames =
			sourceFormatterHelper.getFileNames(
				_sourceFormatterArgs.getBaseDirName(), null,
				excludes.toArray(new String[excludes.size()]),
				new String[] {"**/modules/**/" + fileName},
				_sourceFormatterArgs.isIncludeSubrepositories());

		for (String modulePropertiesFileName : modulePropertiesFileNames) {
			InputStream inputStream = new FileInputStream(
				modulePropertiesFileName);

			Properties properties = new Properties();

			properties.load(inputStream);

			propertiesList.add(properties);
		}

		Properties properties = new Properties();

		properties.setProperty(
			"source.formatter.excludes", StringUtil.merge(excludes));

		if (propertiesList.isEmpty()) {
			return properties;
		}

		for (int i = 0; i < propertiesList.size(); i++) {
			Properties props = propertiesList.get(i);

			Enumeration<String> enu =
				(Enumeration<String>)props.propertyNames();

			while (enu.hasMoreElements()) {
				String key = enu.nextElement();

				String value = props.getProperty(key);

				if (Validator.isNull(value)) {
					continue;
				}

				if (key.contains("excludes")) {
					String existingValue = properties.getProperty(key);

					if (Validator.isNotNull(existingValue)) {
						value = existingValue + StringPool.COMMA + value;
					}

					properties.put(key, value);
				}
				else if (!properties.containsKey(key)) {
					properties.put(key, value);
				}
			}
		}

		return properties;
	}

	private void _runSourceProcessor(SourceProcessor sourceProcessor)
		throws Exception {

		sourceProcessor.setProperties(_properties);
		sourceProcessor.setSourceFormatterArgs(_sourceFormatterArgs);

		sourceProcessor.format();

		_sourceFormatterMessages.addAll(
			sourceProcessor.getSourceFormatterMessages());
		_modifiedFileNames.addAll(sourceProcessor.getModifiedFileNames());

		if (_firstSourceMismatchException == null) {
			_firstSourceMismatchException =
				sourceProcessor.getFirstSourceMismatchException();
		}
	}

	private static final List<String> _defaultExcludes = Arrays.asList(
		"**/.git/**", "**/.gradle/**", "**/bin/**", "**/build/**",
		"**/classes/**", "**/node_modules/**", "**/npm-shrinkwrap.json",
		"**/test-classes/**", "**/test-coverage/**", "**/test-results/**",
		"**/tmp/**");

	private volatile SourceMismatchException _firstSourceMismatchException;
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private Properties _properties;
	private final SourceFormatterArgs _sourceFormatterArgs;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new ConcurrentSkipListSet<>();

}