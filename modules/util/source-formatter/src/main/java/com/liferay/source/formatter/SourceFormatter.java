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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
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

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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

import org.dom4j.DocumentException;

/**
 * @author Hugo Huijser
 */
public class SourceFormatter {

	public static final ExcludeSyntaxPattern[] DEFAULT_EXCLUDE_SYNTAX_PATTERNS =
		{
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.git/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.gradle/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.idea/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.m2/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.settings/**"),
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
				ExcludeSyntax.GLOB, "**/node_modules_cache/**"),
			new ExcludeSyntaxPattern(
				ExcludeSyntax.REGEX,
				"^((?!/frontend-js-node-shims/src/).)*/node_modules/.*"),
			new ExcludeSyntaxPattern(
				ExcludeSyntax.REGEX, ".*/([^/.]+\\.){2,}properties")
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

			String checkName = ArgumentsUtil.getString(
				arguments, "source.check.name", null);

			sourceFormatterArgs.setCheckName(checkName);

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

				sourceFormatterArgs.addRecentChangesFileNames(
					GitUtil.getCurrentBranchFileNames(
						baseDirName, gitWorkingBranchName, false),
					baseDirName);
			}
			else if (formatLatestAuthor) {
				sourceFormatterArgs.addRecentChangesFileNames(
					GitUtil.getLatestAuthorFileNames(baseDirName, false),
					baseDirName);
			}
			else if (formatLocalChanges) {
				sourceFormatterArgs.addRecentChangesFileNames(
					GitUtil.getLocalChangesFileNames(baseDirName, false),
					baseDirName);
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

			boolean includeGeneratedFiles = ArgumentsUtil.getBoolean(
				arguments, "include.generated.files",
				SourceFormatterArgs.INCLUDE_GENERATED_FILES);

			sourceFormatterArgs.setIncludeGeneratedFiles(includeGeneratedFiles);

			boolean includeSubrepositories = ArgumentsUtil.getBoolean(
				arguments, "include.subrepositories",
				SourceFormatterArgs.INCLUDE_SUBREPOSITORIES);

			Set<String> recentChangesFileNames =
				sourceFormatterArgs.getRecentChangesFileNames();

			for (String recentChangesFileName : recentChangesFileNames) {
				if (recentChangesFileName.endsWith("ci-merge")) {
					includeSubrepositories = true;

					break;
				}
			}

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

			String skipCheckNamesString = ArgumentsUtil.getString(
				arguments, "skip.check.names", null);

			String[] skipCheckNames = StringUtil.split(
				skipCheckNamesString, StringPool.COMMA);

			if (ArrayUtil.isNotEmpty(skipCheckNames)) {
				sourceFormatterArgs.setSkipCheckNames(
					Arrays.asList(skipCheckNames));
			}

			boolean throwException = ArgumentsUtil.getBoolean(
				arguments, "source.throw.exception",
				SourceFormatterArgs.THROW_EXCEPTION);

			sourceFormatterArgs.setThrowException(throwException);

			SourceFormatter sourceFormatter = new SourceFormatter(
				sourceFormatterArgs);

			sourceFormatter.format();
		}
		catch (Exception e) {
			if (e instanceof GitException) {
				System.out.println(e.getMessage());
			}
			else {
				CheckstyleException checkstyleException =
					_getNestedCheckstyleException(e);

				if (checkstyleException != null) {
					checkstyleException.printStackTrace();
				}
				else {
					e.printStackTrace();
				}
			}

			System.exit(1);
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

		_sourceProcessors.add(new BNDRunSourceProcessor());
		_sourceProcessors.add(new BNDSourceProcessor());
		_sourceProcessors.add(new CodeownersSourceProcessor());
		_sourceProcessors.add(new ConfigSourceProcessor());
		_sourceProcessors.add(new CQLSourceProcessor());
		_sourceProcessors.add(new CSSSourceProcessor());
		_sourceProcessors.add(new DockerfileSourceProcessor());
		_sourceProcessors.add(new DTDSourceProcessor());
		_sourceProcessors.add(new LFRBuildSourceProcessor());
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
		_sourceProcessors.add(new PoshiSourceProcessor());
		_sourceProcessors.add(new PropertiesSourceProcessor());
		_sourceProcessors.add(new SHSourceProcessor());
		_sourceProcessors.add(new SoySourceProcessor());
		_sourceProcessors.add(new SQLSourceProcessor());
		_sourceProcessors.add(new TLDSourceProcessor());
		_sourceProcessors.add(new TSSourceProcessor());
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
				"Found ", index - 1, " formatting issues:\n", sb.toString());

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

	private static CheckstyleException _getNestedCheckstyleException(
		Exception e) {

		Throwable cause = e;

		while (true) {
			if (cause == null) {
				return null;
			}

			if (cause instanceof CheckstyleException) {
				return (CheckstyleException)cause;
			}

			cause = cause.getCause();
		}
	}

	private void _addDependentFileNames() {
		Set<String> recentChangesFileNames =
			_sourceFormatterArgs.getRecentChangesFileNames();

		if (recentChangesFileNames == null) {
			return;
		}

		Set<String> dependentFileNames = new HashSet<>();

		boolean buildPropertiesAdded = false;
		boolean tagJavaFilesAdded = false;

		for (String recentChangesFileName : recentChangesFileNames) {
			if (!buildPropertiesAdded &&
				recentChangesFileName.contains("/module/")) {

				File file = new File(
					_sourceFormatterArgs.getBaseDirName() + "build.properties");

				if (file.exists()) {
					dependentFileNames.add(
						_sourceFormatterArgs.getBaseDirName() +
							"build.properties");
				}

				buildPropertiesAdded = true;
			}

			if (recentChangesFileName.endsWith("ServiceImpl.java")) {
				dependentFileNames = _addServiceXMLFileName(
					dependentFileNames, recentChangesFileName);
			}
			else if (!tagJavaFilesAdded &&
					 recentChangesFileName.endsWith(".tld")) {

				dependentFileNames.addAll(
					SourceFormatterUtil.filterFileNames(
						_allFileNames, new String[0],
						new String[] {"**/*Tag.java"}, _sourceFormatterExcludes,
						false));

				tagJavaFilesAdded = true;
			}
		}

		_sourceFormatterArgs.addRecentChangesFileNames(
			dependentFileNames, null);
	}

	private Set<String> _addServiceXMLFileName(
		Set<String> dependentFileNames, String serviceImplFileName) {

		String dirName = serviceImplFileName.substring(
			0, serviceImplFileName.lastIndexOf(CharPool.SLASH));

		while (true) {
			String serviceFileName = dirName + "/service.xml";

			File file = new File(
				_sourceFormatterArgs.getBaseDirName() + serviceFileName);

			if (file.exists()) {
				dependentFileNames.add(
					_sourceFormatterArgs.getBaseDirName() + serviceFileName);

				return dependentFileNames;
			}

			int pos = dirName.lastIndexOf(CharPool.SLASH);

			if (pos == -1) {
				return dependentFileNames;
			}

			dirName = dirName.substring(0, pos);
		}
	}

	private boolean _containsDir(String dirName) {
		File directory = SourceFormatterUtil.getFile(
			_sourceFormatterArgs.getBaseDirName(), dirName,
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (directory != null) {
			return true;
		}

		return false;
	}

	private void _excludeWorkingDirCheckoutPrivateApps(File portalDir)
		throws IOException {

		File file = new File(portalDir, "working.dir.properties");

		if (!file.exists()) {
			return;
		}

		Properties properties = _getProperties(file);

		for (Object key : properties.keySet()) {
			String s = (String)key;

			if (s.matches("working.dir.checkout.private.apps.(\\w)+.dirs")) {
				List<String> dirs = ListUtil.fromString(
					properties.getProperty(s), StringPool.COMMA);

				for (String dir : dirs) {
					_sourceFormatterExcludes.addDefaultExcludeSyntaxPatterns(
						_getExcludeSyntaxPatterns("**/" + dir + "/**"));
				}
			}
		}
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

	private List<String> _getPluginsInsideModulesDirectoryNames() {
		List<String> pluginsInsideModulesDirectoryNames = new ArrayList<>();

		List<String> pluginBuildFileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[0],
			new String[] {
				"**/modules/apps/**/build.xml",
				"**/modules/dxp/apps/**/build.xml",
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
				x = absolutePath.indexOf("/modules/dxp/apps/");
			}

			if (x == -1) {
				x = absolutePath.indexOf("/modules/private/apps/");
			}

			int y = absolutePath.lastIndexOf(StringPool.SLASH);

			pluginsInsideModulesDirectoryNames.add(
				absolutePath.substring(x, y + 1));
		}

		return pluginsInsideModulesDirectoryNames;
	}

	private String _getPortalBranchName() {
		for (Map.Entry<String, Properties> entry : _propertiesMap.entrySet()) {
			Properties properties = entry.getValue();

			if (properties.containsKey(
					SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH)) {

				return properties.getProperty(
					SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH);
			}
		}

		return null;
	}

	private String _getProjectPathPrefix() throws IOException {
		if (!_subrepository) {
			return null;
		}

		String fileName = "gradle.properties";

		for (int i = 0; i < ToolsUtil.PORTAL_MAX_DIR_LEVEL; i++) {
			File file = new File(
				_sourceFormatterArgs.getBaseDirName() + fileName);

			if (file.exists()) {
				Properties properties = new Properties();

				properties.load(new FileInputStream(file));

				if (properties.containsKey("project.path.prefix")) {
					return properties.getProperty("project.path.prefix");
				}
			}

			fileName = "../" + fileName;
		}

		return null;
	}

	private Properties _getProperties(File file) throws IOException {
		Properties properties = new Properties();

		if (file.exists()) {
			properties.load(new FileInputStream(file));
		}

		return properties;
	}

	private void _init() throws DocumentException, IOException {
		_sourceFormatterExcludes = new SourceFormatterExcludes(
			SetUtil.fromArray(DEFAULT_EXCLUDE_SYNTAX_PATTERNS));

		_portalSource = _containsDir("portal-impl");

		if (_portalSource) {
			File portalDir = SourceFormatterUtil.getPortalDir(
				_sourceFormatterArgs.getBaseDirName());

			_excludeWorkingDirCheckoutPrivateApps(portalDir);
		}

		_propertiesMap = new HashMap<>();

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

		if (!_portalSource && _containsDir("modules/private/apps")) {

			// Grab and read properties from portal branch

			String propertiesContent = SourceFormatterUtil.getGitContent(
				_PROPERTIES_FILE_NAME, _getPortalBranchName());

			_readProperties(
				propertiesContent,
				SourceUtil.getAbsolutePath(
					_sourceFormatterArgs.getBaseDirName()));
		}

		_addDependentFileNames();

		_pluginsInsideModulesDirectoryNames =
			_getPluginsInsideModulesDirectoryNames();

		_subrepository = _isSubrepository();

		_projectPathPrefix = _getProjectPathPrefix();

		List<File> suppressionsFiles = SourceFormatterUtil.getSuppressionsFiles(
			_sourceFormatterArgs.getBaseDirName(), _allFileNames,
			_sourceFormatterExcludes, "checkstyle-suppressions.xml",
			"source-formatter-suppressions.xml",
			"sourcechecks-suppressions.xml");

		_sourceFormatterSuppressions = SuppressionsLoader.loadSuppressions(
			_sourceFormatterArgs.getBaseDirName(), suppressionsFiles);

		_sourceFormatterConfiguration = ConfigurationLoader.loadConfiguration(
			"sourcechecks.xml");

		if (_sourceFormatterArgs.isShowDebugInformation()) {
			DebugUtil.addCheckNames(CheckType.SOURCE_CHECK, _getCheckNames());
		}
	}

	private boolean _isSubrepository() throws IOException {
		if (_portalSource) {
			return false;
		}

		String baseDirAbsolutePath = SourceUtil.getAbsolutePath(
			_sourceFormatterArgs.getBaseDirName());

		File baseDir = new File(baseDirAbsolutePath);

		for (int i = 0; i < _SUBREPOSITORY_MAX_DIR_LEVEL; i++) {
			if ((baseDir == null) || !baseDir.exists()) {
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

	private void _readProperties(File propertiesFile) throws IOException {
		Properties properties = _getProperties(propertiesFile);

		if (properties.isEmpty()) {
			return;
		}

		String propertiesFileLocation = SourceUtil.getAbsolutePath(
			propertiesFile);

		int pos = propertiesFileLocation.lastIndexOf(StringPool.SLASH);

		propertiesFileLocation = propertiesFileLocation.substring(0, pos);

		_readProperties(properties, propertiesFileLocation);
	}

	private void _readProperties(
		Properties properties, String propertiesFileLocation) {

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

	private void _readProperties(String content, String propertiesFileLocation)
		throws IOException {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		if (properties.isEmpty()) {
			return;
		}

		_readProperties(properties, propertiesFileLocation);
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
	private Map<String, Properties> _propertiesMap;
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