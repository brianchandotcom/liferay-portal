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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.GitException;
import com.liferay.portal.tools.GitUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Hugo Huijser
 */
public class SourceFormatter {

	public static final ExcludeSyntaxPattern[]
		DEFAULT_EXCLUDE_SYNTAX_PATTERNS = {
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.git/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.gradle/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/bin/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/build/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/classes/**"),
			new ExcludeSyntaxPattern(
				ExcludeSyntax.GLOB, "**/liferay-theme.json"),
			new ExcludeSyntaxPattern(
				ExcludeSyntax.GLOB, "**/npm-shrinkwrap.json"),
			new ExcludeSyntaxPattern(
				ExcludeSyntax.GLOB, "**/package-lock.json"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/test-classes/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/test-coverage/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/test-results/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/tmp/**"),
			new ExcludeSyntaxPattern(
				ExcludeSyntax.REGEX,
				"^((?!/frontend-js-node-shims/src/).)*/node_modules/.*")
		};

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

			String fileNamesString = ArgumentsUtil.getString(
				arguments, "source.files", StringPool.BLANK);

			String[] fileNames = StringUtil.split(
				fileNamesString, StringPool.COMMA);

			if (ArrayUtil.isNotEmpty(fileNames)) {
				sourceFormatterArgs.setFileNames(Arrays.asList(fileNames));
			}
			else {
				String fileExtensionsString = ArgumentsUtil.getString(
					arguments, "source.file.extensions", StringPool.BLANK);

				String[] fileExtensions = StringUtil.split(
					fileExtensionsString, StringPool.COMMA);

				sourceFormatterArgs.setFileExtensions(
					Arrays.asList(fileExtensions));
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

			boolean showDebugInformation = ArgumentsUtil.getBoolean(
				arguments, "show.debug.information",
				SourceFormatterArgs.SHOW_DEBUG_INFORMATION);

			sourceFormatterArgs.setShowDebugInformation(showDebugInformation);

			boolean showDocumentation = ArgumentsUtil.getBoolean(
				arguments, "show.documentation",
				SourceFormatterArgs.SHOW_DOCUMENTATION);

			sourceFormatterArgs.setShowDocumentation(showDocumentation);

			boolean showStatusUpdates = ArgumentsUtil.getBoolean(
				arguments, "show.status.updates",
				SourceFormatterArgs.SHOW_STATUS_UPDATES);

			sourceFormatterArgs.setShowStatusUpdates(showStatusUpdates);

			boolean throwException = ArgumentsUtil.getBoolean(
				arguments, "source.throw.exception",
				SourceFormatterArgs.THROW_EXCEPTION);

			sourceFormatterArgs.setThrowException(throwException);

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

		if (sourceFormatterArgs.isShowDocumentation()) {
			System.setProperty("java.awt.headless", "false");
		}
		else {
			System.setProperty("java.awt.headless", "true");
		}
	}

	public void format() throws Exception {
		_printProgressStatusMessage("Scanning for files...");

		_init();

		_printProgressStatusMessage("Initializing checks...");

		_progressStatusThread.setDaemon(true);
		_progressStatusThread.setName(
			"Source Formatter Progress Status Thread");

		_progressStatusThread.start();

		_sourceProcessors.add(new BNDSourceProcessor());
		_sourceProcessors.add(new CodeownersSourceProcessor());
		_sourceProcessors.add(new CQLSourceProcessor());
		_sourceProcessors.add(new CSSSourceProcessor());
		_sourceProcessors.add(new DockerfileSourceProcessor());
		_sourceProcessors.add(new FTLSourceProcessor());
		_sourceProcessors.add(new GradleSourceProcessor());
		_sourceProcessors.add(new GroovySourceProcessor());
		_sourceProcessors.add(new JavaSourceProcessor());
		_sourceProcessors.add(new JSONSourceProcessor());
		_sourceProcessors.add(new JSPSourceProcessor());
		_sourceProcessors.add(new JSSourceProcessor());
		_sourceProcessors.add(new MarkdownSourceProcessor());
		_sourceProcessors.add(new PropertiesSourceProcessor());
		_sourceProcessors.add(new SHSourceProcessor());
		_sourceProcessors.add(new SoySourceProcessor());
		_sourceProcessors.add(new SQLSourceProcessor());
		_sourceProcessors.add(new TLDSourceProcessor());
		_sourceProcessors.add(new XMLSourceProcessor());
		_sourceProcessors.add(new YMLSourceProcessor());

		ExecutorService executorService = Executors.newFixedThreadPool(
			_sourceProcessors.size());

		List<Future<Void>> futures = new ArrayList<>(_sourceProcessors.size());

		for (final SourceProcessor sourceProcessor : _sourceProcessors) {
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
			catch (ExecutionException ee) {
				if (ee1 == null) {
					ee1 = ee;
				}
				else {
					ee1.addSuppressed(ee);
				}
			}
		}

		executorService.shutdown();

		while (!executorService.isTerminated()) {
			Thread.sleep(20);
		}

		if (_sourceFormatterArgs.isShowDebugInformation()) {
			DebugUtil.printSourceFormatterInformation();
		}

		_progressStatusQueue.put(
			new ProgressStatusUpdate(ProgressStatus.SOURCE_FORMAT_COMPLETED));

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

	private List<ExcludeSyntaxPattern> _getExcludeSyntaxPatterns(
		String sourceFormatterExcludes) {

		List<ExcludeSyntaxPattern> excludeSyntaxPatterns = new ArrayList<>();

		List<String> excludes = ListUtil.fromString(
			sourceFormatterExcludes, StringPool.COMMA);

		for (String exclude : excludes) {
			excludeSyntaxPatterns.add(
				new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, exclude));
		}

		// See the source-format-jdk8 task in built-test-batch.xml for more
		// information

		String systemExcludes = System.getProperty("source.formatter.excludes");

		excludes = ListUtil.fromString(GetterUtil.getString(systemExcludes));

		for (String exclude : excludes) {
			excludeSyntaxPatterns.add(
				new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, exclude));
		}

		return excludeSyntaxPatterns;
	}

	private int _getMaxDirLevel() {
		File portalImplDir = SourceFormatterUtil.getFile(
			_sourceFormatterArgs.getBaseDirName(), "portal-impl",
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (portalImplDir != null) {
			return ToolsUtil.PORTAL_MAX_DIR_LEVEL;
		}

		return ToolsUtil.PLUGINS_MAX_DIR_LEVEL;
	}

	private Properties _getProperties(File file) throws Exception {
		Properties properties = new Properties();

		if (file.exists()) {
			properties.load(new FileInputStream(file));
		}

		return properties;
	}

	private void _init() throws Exception {
		_sourceFormatterExcludes = new SourceFormatterExcludes(
			SetUtil.fromArray(DEFAULT_EXCLUDE_SYNTAX_PATTERNS));

		// Find properties file in any parent directory

		String parentDirName = _sourceFormatterArgs.getBaseDirName();

		for (int i = 0; i < _getMaxDirLevel(); i++) {
			_readProperties(new File(parentDirName + _PROPERTIES_FILE_NAME));

			parentDirName += "../";
		}

		_allFileNames = SourceFormatterUtil.scanForFiles(
			_sourceFormatterArgs.getBaseDirName(), new String[0],
			new String[] {"**/*.*", "**/CODEOWNERS", "**/Dockerfile"},
			_sourceFormatterExcludes,
			_sourceFormatterArgs.isIncludeSubrepositories());

		// Find properties file in any child directory

		List<String> modulePropertiesFileNames =
			SourceFormatterUtil.filterFileNames(
				_allFileNames, new String[0],
				new String[] {"**/" + _PROPERTIES_FILE_NAME},
				_sourceFormatterExcludes, true);

		for (String modulePropertiesFileName : modulePropertiesFileNames) {
			_readProperties(new File(modulePropertiesFileName));
		}
	}

	private void _printProgressStatusMessage(String message) {
		if (!_sourceFormatterArgs.isShowStatusUpdates()) {
			return;
		}

		if (message.length() > _maxStatusMessageLength) {
			_maxStatusMessageLength = message.length();
		}

		System.out.print(message + "\r");
	}

	private void _readProperties(File propertiesFile) throws Exception {
		Properties properties = _getProperties(propertiesFile);

		if (properties.isEmpty()) {
			return;
		}

		String propertiesFileLocation = SourceUtil.getAbsolutePath(
			propertiesFile);

		int pos = propertiesFileLocation.lastIndexOf(StringPool.SLASH);

		propertiesFileLocation = propertiesFileLocation.substring(0, pos + 1);

		String value = properties.getProperty("source.formatter.excludes");

		if (value == null) {
			_propertiesMap.put(propertiesFileLocation, properties);

			return;
		}

		if (FileUtil.exists(propertiesFileLocation + "portal-impl")) {
			_sourceFormatterExcludes.addDefaultExcludeSyntaxPatterns(
				_getExcludeSyntaxPatterns(value));
		}
		else {
			_sourceFormatterExcludes.addExcludeSyntaxPatterns(
				propertiesFileLocation, _getExcludeSyntaxPatterns(value));
		}

		properties.remove("source.formatter.excludes");

		_propertiesMap.put(propertiesFileLocation, properties);
	}

	private void _runSourceProcessor(SourceProcessor sourceProcessor)
		throws Exception {

		sourceProcessor.setAllFileNames(_allFileNames);
		sourceProcessor.setProgressStatusQueue(_progressStatusQueue);
		sourceProcessor.setPropertiesMap(_propertiesMap);
		sourceProcessor.setSourceFormatterArgs(_sourceFormatterArgs);
		sourceProcessor.setSourceFormatterExcludes(_sourceFormatterExcludes);

		sourceProcessor.format();

		_sourceFormatterMessages.addAll(
			sourceProcessor.getSourceFormatterMessages());
		_modifiedFileNames.addAll(sourceProcessor.getModifiedFileNames());

		if (_firstSourceMismatchException == null) {
			_firstSourceMismatchException =
				sourceProcessor.getFirstSourceMismatchException();
		}
	}

	private static final String _PROPERTIES_FILE_NAME =
		"source-formatter.properties";

	private List<String> _allFileNames;
	private volatile SourceMismatchException _firstSourceMismatchException;
	private int _maxStatusMessageLength = -1;
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private final BlockingQueue<ProgressStatusUpdate> _progressStatusQueue =
		new LinkedBlockingQueue<>();

	private final Thread _progressStatusThread = new Thread() {

		@Override
		public void run() {
			int fileScansCompletedCount = 0;
			int percentage = 0;
			int processedCheckStyleFileCount = 0;
			int processedSourceChecksFileCount = 0;
			int totalCheckStyleFileCount = 0;
			int totalSourceChecksFileCount = 0;

			boolean sourceChecksInitialized = false;
			boolean sourceChecksCompleted = false;

			while (true) {
				try {
					ProgressStatusUpdate progressStatusUpdate =
						_progressStatusQueue.take();

					ProgressStatus progressStatus =
						progressStatusUpdate.getProgressStatus();

					if (progressStatus.equals(
							ProgressStatus.CHECK_STYLE_FILE_COMPLETED)) {

						processedCheckStyleFileCount++;

						if (!sourceChecksCompleted) {

							// Do not show progress for CheckStyle when there
							// are still source checks that are not done yet.

							continue;
						}

						percentage = _processCompletedPercentage(
							percentage, processedCheckStyleFileCount,
							totalCheckStyleFileCount, "CheckStyle checks");
					}
					else if (progressStatus.equals(
								ProgressStatus.CHECK_STYLE_STARTING)) {

						totalCheckStyleFileCount =
							progressStatusUpdate.getCount();
					}
					else if (progressStatus.equals(
								ProgressStatus.SOURCE_CHECKS_INITIALIZED)) {

						fileScansCompletedCount++;
						totalSourceChecksFileCount +=
							progressStatusUpdate.getCount();

						if (fileScansCompletedCount ==
								_sourceProcessors.size()) {

							sourceChecksInitialized = true;

							// Some SourceProcessors might already have
							// processed files before other SourceProcessors
							// finished initializing. In order to show the
							// status for the remaining files, we deduct the
							// processed files from the total count and reset
							// the processed files count.

							totalSourceChecksFileCount -=
								processedSourceChecksFileCount;

							processedSourceChecksFileCount = 0;
						}
					}
					else if (progressStatus.equals(
								ProgressStatus.SOURCE_CHECK_FILE_COMPLETED)) {

						processedSourceChecksFileCount++;

						if (!sourceChecksInitialized) {

							// Do not show progress when there are still other
							// source checks that are still being finalized.

							continue;
						}

						percentage = _processCompletedPercentage(
							percentage, processedSourceChecksFileCount,
							totalSourceChecksFileCount, "source checks");

						if (percentage == 100) {
							sourceChecksCompleted = true;

							// Checkstyle might already have processed files
							// before all the source checks finished. In order
							// to show the status for the remaining files, we
							// deduct the processed files from the total count
							// and reset the processed files count.

							totalCheckStyleFileCount -=
								processedCheckStyleFileCount;

							processedCheckStyleFileCount = 0;

							percentage = 0;
						}
					}
					else if (progressStatus.equals(
								ProgressStatus.SOURCE_FORMAT_COMPLETED)) {

						if (_maxStatusMessageLength == -1) {
							break;
						}

						// Print empty line to clear the line in order to
						// prevent characters from old lines to still show

						StringBundler sb = new StringBundler(
							_maxStatusMessageLength);

						for (int i = 0; i < _maxStatusMessageLength; i++) {
							sb.append(CharPool.SPACE);
						}

						_printProgressStatusMessage(sb.toString());

						break;
					}
				}
				catch (InterruptedException ie) {
				}
			}
		}

		private int _processCompletedPercentage(
			int percentage, int count, int total, String checkType) {

			int newPercentage = (count * 100) / total;

			if (newPercentage > percentage) {
				StringBundler sb = new StringBundler();

				sb.append("Processing ");
				sb.append(checkType);
				sb.append(": ");
				sb.append(newPercentage);
				sb.append("% completed");

				_printProgressStatusMessage(sb.toString());
			}

			return newPercentage;
		}

	};

	private Map<String, Properties> _propertiesMap = new HashMap<>();
	private final SourceFormatterArgs _sourceFormatterArgs;
	private SourceFormatterExcludes _sourceFormatterExcludes;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new ConcurrentSkipListSet<>();
	private List<SourceProcessor> _sourceProcessors = new ArrayList<>();

}