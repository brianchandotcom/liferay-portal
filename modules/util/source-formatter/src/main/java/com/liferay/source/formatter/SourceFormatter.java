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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.GitException;
import com.liferay.portal.tools.GitUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.configuration.ConfigurationLoader;
import com.liferay.source.formatter.checks.configuration.SourceCheckConfiguration;
import com.liferay.source.formatter.checks.configuration.SourceFormatterConfiguration;
import com.liferay.source.formatter.checks.configuration.SourceFormatterSuppressions;
import com.liferay.source.formatter.checks.configuration.SuppressionsLoader;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.CheckType;
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
import java.util.Objects;
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
		_sourceProcessors.add(new HTMLSourceProcessor());
		_sourceProcessors.add(new JavaSourceProcessor());
		_sourceProcessors.add(new JSONSourceProcessor());
		_sourceProcessors.add(new JSPSourceProcessor());
		_sourceProcessors.add(new JSSourceProcessor());
		_sourceProcessors.add(new MarkdownSourceProcessor());
		_sourceProcessors.add(new PackageinfoSourceProcessor());
		_sourceProcessors.add(new PropertiesSourceProcessor());
		_sourceProcessors.add(new SHSourceProcessor());
		_sourceProcessors.add(new SoySourceProcessor());
		_sourceProcessors.add(new SQLSourceProcessor());
		_sourceProcessors.add(new TSSourceProcessor());
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

		if (_sourceFormatterArgs.isThrowException() &&
			(!_sourceFormatterMessages.isEmpty() ||
			 !_sourceMismatchExceptions.isEmpty())) {

			StringBundler sb = new StringBundler(
				(_sourceFormatterMessages.size() +
					_sourceMismatchExceptions.size()) * 4);

			int index = 1;

			if (!_sourceFormatterMessages.isEmpty()) {
				for (SourceFormatterMessage sourceFormatterMessage :
						_sourceFormatterMessages) {

					sb.append(index);
					sb.append(": ");
					sb.append(sourceFormatterMessage.toString());
					sb.append("\n");

					index = index + 1;
				}
			}

			if (!_sourceMismatchExceptions.isEmpty()) {
				for (SourceMismatchException sourceMismatchException :
						_sourceMismatchExceptions) {

					String message = sourceMismatchException.getMessage();

					if (!Objects.isNull(message)) {
						sb.append(index);
						sb.append(": ");
						sb.append(message);
						sb.append("\n");

						index = index + 1;
					}
				}
			}

			String message = StringBundler.concat(
				"Found ", String.valueOf(index - 1), " formatting issues:\n",
				sb.toString());

			throw new Exception(message);
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

	public List<SourceMismatchException> getSourceMismatchExceptions() {
		return _sourceMismatchExceptions;
	}

	private List<String> _getCheckNames() {
		List<String> checkNames = new ArrayList<>();

		for (String sourceProcessorName :
				_sourceFormatterConfiguration.getSourceProcessorNames()) {

			for (SourceCheckConfiguration sourceCheckConfiguration :
					_sourceFormatterConfiguration.getSourceCheckConfigurations(
						sourceProcessorName)) {

				checkNames.add(sourceCheckConfiguration.getName());
			}
		}

		return checkNames;
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

	private List<String> _getPluginsInsideModulesDirectoryNames()
		throws Exception {

		List<String> pluginsInsideModulesDirectoryNames = new ArrayList<>();

		List<String> pluginBuildFileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[0],
			new String[] {
				"**/modules/apps/**/build.xml",
				"**/modules/private/apps/**/build.xml"
			},
			_sourceFormatterExcludes, true);

		for (String pluginBuildFileName : pluginBuildFileNames) {
			pluginBuildFileName = StringUtil.replace(
				pluginBuildFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String absolutePath = SourceUtil.getAbsolutePath(
				pluginBuildFileName);

			int x = absolutePath.indexOf("/modules/apps/");

			if (x == -1) {
				x = absolutePath.indexOf("/modules/private/apps/");
			}

			int y = absolutePath.lastIndexOf(StringPool.SLASH);

			pluginsInsideModulesDirectoryNames.add(
				absolutePath.substring(x, y + 1));
		}

		return pluginsInsideModulesDirectoryNames;
	}

	private String _getProjectPathPrefix() throws Exception {
		if (!_subrepository) {
			return null;
		}

		File file = SourceFormatterUtil.getFile(
			_sourceFormatterArgs.getBaseDirName(), "gradle.properties",
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (file == null) {
			return null;
		}

		Properties properties = new Properties();

		properties.load(new FileInputStream(file));

		return properties.getProperty("project.path.prefix");
	}

	private Properties _getProperties(File file) throws Exception {
		Properties properties = new Properties();

		if (file.exists()) {
			properties.load(new FileInputStream(file));
		}

		return properties;
	}

	private SourceFormatterSuppressions _getSourceFormatterSuppressions()
		throws Exception {

		List<File> suppressionsFiles = SourceFormatterUtil.getSuppressionsFiles(
			_sourceFormatterArgs.getBaseDirName(), _allFileNames,
			_sourceFormatterExcludes, "checkstyle-suppressions.xml",
			"sourcechecks-suppressions.xml");

		return SuppressionsLoader.loadSuppressions(
			_sourceFormatterArgs.getBaseDirName(), suppressionsFiles);
	}

	private void _init() throws Exception {
		_sourceFormatterExcludes = new SourceFormatterExcludes(
			SetUtil.fromArray(DEFAULT_EXCLUDE_SYNTAX_PATTERNS));

		// Find properties file in any parent directory

		String parentDirName = _sourceFormatterArgs.getBaseDirName();

		for (int i = 0; i < ToolsUtil.PORTAL_MAX_DIR_LEVEL; i++) {
			_readProperties(new File(parentDirName + _PROPERTIES_FILE_NAME));

			parentDirName += "../";
		}

		_allFileNames = SourceFormatterUtil.scanForFiles(
			_sourceFormatterArgs.getBaseDirName(), new String[0],
			new String[] {
				"**/*.*", "**/CODEOWNERS", "**/Dockerfile", "**/packageinfo"
			},
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

		_pluginsInsideModulesDirectoryNames =
			_getPluginsInsideModulesDirectoryNames();

		_portalSource = _isPortalSource();
		_subrepository = _isSubrepository();

		_projectPathPrefix = _getProjectPathPrefix();

		_sourceFormatterSuppressions = _getSourceFormatterSuppressions();

		_sourceFormatterConfiguration = ConfigurationLoader.loadConfiguration(
			"sourcechecks.xml");

		if (_sourceFormatterArgs.isShowDebugInformation()) {
			DebugUtil.addCheckNames(CheckType.SOURCE_CHECK, _getCheckNames());
		}
	}

	private boolean _isPortalSource() {
		File portalImplDir = SourceFormatterUtil.getFile(
			_sourceFormatterArgs.getBaseDirName(), "portal-impl",
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (portalImplDir != null) {
			return true;
		}

		return false;
	}

	private boolean _isSubrepository() throws Exception {
		if (_isPortalSource()) {
			return false;
		}

		String baseDirAbsolutePath = SourceUtil.getAbsolutePath(
			_sourceFormatterArgs.getBaseDirName());

		File baseDir = new File(baseDirAbsolutePath);

		for (int i = 0; i < _SUBREPOSITORY_MAX_DIR_LEVEL; i++) {
			if (!baseDir.exists()) {
				return false;
			}

			File gradlePropertiesFile = new File(baseDir, "gradle.properties");
			File gradlewFile = new File(baseDir, "gradlew");

			if (gradlePropertiesFile.exists() && gradlewFile.exists()) {
				String content = FileUtil.read(gradlePropertiesFile);

				if (content.contains("project.path.prefix=")) {
					return true;
				}
			}

			baseDir = baseDir.getParentFile();
		}

		return false;
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
		sourceProcessor.setPluginsInsideModulesDirectoryNames(
			_pluginsInsideModulesDirectoryNames);
		sourceProcessor.setPortalSource(_portalSource);
		sourceProcessor.setProgressStatusQueue(_progressStatusQueue);
		sourceProcessor.setProjectPathPrefix(_projectPathPrefix);
		sourceProcessor.setPropertiesMap(_propertiesMap);
		sourceProcessor.setSourceFormatterArgs(_sourceFormatterArgs);
		sourceProcessor.setSourceFormatterConfiguration(
			_sourceFormatterConfiguration);
		sourceProcessor.setSourceFormatterExcludes(_sourceFormatterExcludes);
		sourceProcessor.setSourceFormatterSuppressions(
			_sourceFormatterSuppressions);
		sourceProcessor.setSubrepository(_subrepository);

		sourceProcessor.format();

		_sourceFormatterMessages.addAll(
			sourceProcessor.getSourceFormatterMessages());
		_sourceMismatchExceptions.addAll(
			sourceProcessor.getSourceMismatchExceptions());
		_modifiedFileNames.addAll(sourceProcessor.getModifiedFileNames());
	}

	private static final String _PROPERTIES_FILE_NAME =
		"source-formatter.properties";

	private static final int _SUBREPOSITORY_MAX_DIR_LEVEL = 3;

	private List<String> _allFileNames;
	private int _maxStatusMessageLength = -1;
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private List<String> _pluginsInsideModulesDirectoryNames;
	private boolean _portalSource;
	private final BlockingQueue<ProgressStatusUpdate> _progressStatusQueue =
		new LinkedBlockingQueue<>();

	private final Thread _progressStatusThread = new Thread() {

		@Override
		public void run() {
			int fileScansCompletedCount = 0;
			int percentage = 0;
			int processedChecksFileCount = 0;
			int totalChecksFileCount = 0;

			boolean checksInitialized = false;

			while (true) {
				try {
					ProgressStatusUpdate progressStatusUpdate =
						_progressStatusQueue.take();

					ProgressStatus progressStatus =
						progressStatusUpdate.getProgressStatus();

					if (progressStatus.equals(
							ProgressStatus.CHECKS_INITIALIZED)) {

						fileScansCompletedCount++;
						totalChecksFileCount += progressStatusUpdate.getCount();

						if (fileScansCompletedCount ==
								_sourceProcessors.size()) {

							checksInitialized = true;

							// Some SourceProcessors might already have
							// processed files before other SourceProcessors
							// finished initializing. In order to show the
							// status for the remaining files, we deduct the
							// processed files from the total count and reset
							// the processed files count.

							totalChecksFileCount -= processedChecksFileCount;

							processedChecksFileCount = 0;
						}
					}
					else if (progressStatus.equals(
								ProgressStatus.CHECK_FILE_COMPLETED)) {

						processedChecksFileCount++;

						if (!checksInitialized) {

							// Do not show progress when there are still other
							// checks that are still being finalized.

							continue;
						}

						percentage = _processCompletedPercentage(
							percentage, processedChecksFileCount,
							totalChecksFileCount);
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
			int percentage, int count, int total) {

			int newPercentage = (count * 100) / total;

			if (newPercentage > percentage) {
				_printProgressStatusMessage(
					"Processing checks: " + newPercentage + "% completed");
			}

			return newPercentage;
		}

	};

	private String _projectPathPrefix;
	private Map<String, Properties> _propertiesMap = new HashMap<>();
	private final SourceFormatterArgs _sourceFormatterArgs;
	private SourceFormatterConfiguration _sourceFormatterConfiguration;
	private SourceFormatterExcludes _sourceFormatterExcludes;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new ConcurrentSkipListSet<>();
	private SourceFormatterSuppressions _sourceFormatterSuppressions;
	private volatile List<SourceMismatchException> _sourceMismatchExceptions =
		new ArrayList<>();
	private List<SourceProcessor> _sourceProcessors = new ArrayList<>();
	private boolean _subrepository;

}