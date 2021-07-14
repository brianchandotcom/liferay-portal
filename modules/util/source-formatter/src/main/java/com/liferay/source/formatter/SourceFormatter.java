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
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.source.formatter.util.JIRAUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
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
				"^((?!/frontend-js-node-shims/src/).)*/node_modules/.*")
		};

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		try {
			SourceFormatterArgs sourceFormatterArgs = new SourceFormatterArgs();

			sourceFormatterArgs.setAutoFix(
				ArgumentsUtil.getBoolean(
					arguments, "source.auto.fix",
					SourceFormatterArgs.AUTO_FIX));

			String baseDirName = ArgumentsUtil.getString(
				arguments, "source.base.dir",
				SourceFormatterArgs.BASE_DIR_NAME);

			sourceFormatterArgs.setBaseDirName(baseDirName);

			sourceFormatterArgs.setCheckCategoryNames(
				ListUtil.fromString(
					ArgumentsUtil.getString(
						arguments, "source.check.category.names", null),
					StringPool.COMMA));
			sourceFormatterArgs.setCheckNames(
				ListUtil.fromString(
					ArgumentsUtil.getString(
						arguments, "source.check.names", null),
					StringPool.COMMA));
			sourceFormatterArgs.setFailOnAutoFix(
				ArgumentsUtil.getBoolean(
					arguments, "source.fail.on.auto.fix",
					SourceFormatterArgs.FAIL_ON_AUTO_FIX));
			sourceFormatterArgs.setFailOnHasWarning(
				ArgumentsUtil.getBoolean(
					arguments, "source.fail.on.has.warning",
					SourceFormatterArgs.FAIL_ON_HAS_WARNING));
			sourceFormatterArgs.setFormatCurrentBranch(
				ArgumentsUtil.getBoolean(
					arguments, "format.current.branch",
					SourceFormatterArgs.FORMAT_CURRENT_BRANCH));
			sourceFormatterArgs.setFormatLatestAuthor(
				ArgumentsUtil.getBoolean(
					arguments, "format.latest.author",
					SourceFormatterArgs.FORMAT_LATEST_AUTHOR));
			sourceFormatterArgs.setFormatLocalChanges(
				ArgumentsUtil.getBoolean(
					arguments, "format.local.changes",
					SourceFormatterArgs.FORMAT_LOCAL_CHANGES));
			sourceFormatterArgs.setGitWorkingBranchName(
				ArgumentsUtil.getString(
					arguments, "git.working.branch.name",
					SourceFormatterArgs.GIT_WORKING_BRANCH_NAME));

			int commitCount = ArgumentsUtil.getInteger(
				arguments, "commit.count", SourceFormatterArgs.COMMIT_COUNT);

			sourceFormatterArgs.setCommitCount(commitCount);

			if (commitCount > 0) {
				sourceFormatterArgs.addRecentChangesFileNames(
					GitUtil.getModifiedFileNames(baseDirName, commitCount),
					baseDirName);
			}
			else if (sourceFormatterArgs.isFormatCurrentBranch()) {
				sourceFormatterArgs.addRecentChangesFileNames(
					GitUtil.getCurrentBranchFileNames(
						baseDirName,
						sourceFormatterArgs.getGitWorkingBranchName(), false),
					baseDirName);
			}
			else if (sourceFormatterArgs.isFormatLatestAuthor()) {
				sourceFormatterArgs.addRecentChangesFileNames(
					GitUtil.getLatestAuthorFileNames(baseDirName, false),
					baseDirName);
			}
			else if (sourceFormatterArgs.isFormatLocalChanges()) {
				sourceFormatterArgs.addRecentChangesFileNames(
					GitUtil.getLocalChangesFileNames(baseDirName, false),
					baseDirName);
			}

			String[] fileNames = StringUtil.split(
				ArgumentsUtil.getString(
					arguments, "source.files", StringPool.BLANK),
				StringPool.COMMA);

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

			sourceFormatterArgs.setIncludeGeneratedFiles(
				ArgumentsUtil.getBoolean(
					arguments, "include.generated.files",
					SourceFormatterArgs.INCLUDE_GENERATED_FILES));

			boolean includeSubrepositories = ArgumentsUtil.getBoolean(
				arguments, "include.subrepositories",
				SourceFormatterArgs.INCLUDE_SUBREPOSITORIES);

			for (String recentChangesFileName :
					sourceFormatterArgs.getRecentChangesFileNames()) {

				if (recentChangesFileName.endsWith("ci-merge")) {
					includeSubrepositories = true;

					break;
				}
			}

			sourceFormatterArgs.setIncludeSubrepositories(
				includeSubrepositories);

			sourceFormatterArgs.setMaxLineLength(
				ArgumentsUtil.getInteger(
					arguments, "max.line.length",
					SourceFormatterArgs.MAX_LINE_LENGTH));
			sourceFormatterArgs.setMaxDirLevel(
				Math.max(
					ToolsUtil.PORTAL_MAX_DIR_LEVEL,
					StringUtil.count(baseDirName, CharPool.SLASH) + 1));
			sourceFormatterArgs.setOutputFileName(
				ArgumentsUtil.getString(
					arguments, "output.file.name",
					SourceFormatterArgs.OUTPUT_FILE_NAME));
			sourceFormatterArgs.setPrintErrors(
				ArgumentsUtil.getBoolean(
					arguments, "source.print.errors",
					SourceFormatterArgs.PRINT_ERRORS));
			sourceFormatterArgs.setProcessorThreadCount(
				ArgumentsUtil.getInteger(
					arguments, "processor.thread.count",
					SourceFormatterArgs.PROCESSOR_THREAD_COUNT));
			sourceFormatterArgs.setShowDebugInformation(
				ArgumentsUtil.getBoolean(
					arguments, "show.debug.information",
					SourceFormatterArgs.SHOW_DEBUG_INFORMATION));
			sourceFormatterArgs.setShowDocumentation(
				ArgumentsUtil.getBoolean(
					arguments, "show.documentation",
					SourceFormatterArgs.SHOW_DOCUMENTATION));
			sourceFormatterArgs.setShowStatusUpdates(
				ArgumentsUtil.getBoolean(
					arguments, "show.status.updates",
					SourceFormatterArgs.SHOW_STATUS_UPDATES));

			String[] skipCheckNames = StringUtil.split(
				ArgumentsUtil.getString(
					arguments, "skip.check.names", StringPool.BLANK),
				StringPool.COMMA);

			if (ArrayUtil.isNotEmpty(skipCheckNames)) {
				sourceFormatterArgs.setSkipCheckNames(
					Arrays.asList(skipCheckNames));
			}

			String[] sourceFormatterProperties = StringUtil.split(
				ArgumentsUtil.getString(
					arguments, "source.formatter.properties", StringPool.BLANK),
				"\\n");

			if (ArrayUtil.isNotEmpty(sourceFormatterProperties)) {
				sourceFormatterArgs.setSourceFormatterProperties(
					Arrays.asList(sourceFormatterProperties));
			}

			sourceFormatterArgs.setValidateCommitMessages(
				ArgumentsUtil.getBoolean(
					arguments, "validate.commit.messages",
					SourceFormatterArgs.VALIDATE_COMMIT_MESSAGES));

			SourceFormatter sourceFormatter = new SourceFormatter(
				sourceFormatterArgs);

			sourceFormatter.format();
		}
		catch (Exception exception) {
			if (exception instanceof GitException) {
				System.out.println(exception.getMessage());
			}
			else {
				CheckstyleException checkstyleException =
					_getNestedCheckstyleException(exception);

				if (checkstyleException != null) {
					checkstyleException.printStackTrace();
				}
				else {
					exception.printStackTrace();
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

		System.setProperty(
			"javax.xml.parsers.SAXParserFactory",
			"org.apache.xerces.jaxp.SAXParserFactoryImpl");

		_init();

		if (_sourceFormatterArgs.isValidateCommitMessages()) {
			_validateCommitMessages();
		}

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
		_sourceProcessors.add(new PythonSourceProcessor());
		_sourceProcessors.add(new SHSourceProcessor());
		_sourceProcessors.add(new SoySourceProcessor());
		_sourceProcessors.add(new SQLSourceProcessor());
		_sourceProcessors.add(new TLDSourceProcessor());
		_sourceProcessors.add(new TSSourceProcessor());
		_sourceProcessors.add(new TXTSourceProcessor());
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

		ExecutionException executionException1 = null;

		for (Future<Void> future : futures) {
			try {
				future.get();
			}
			catch (ExecutionException executionException2) {
				if (executionException1 == null) {
					executionException1 = executionException2;
				}
				else {
					executionException1.addSuppressed(executionException2);
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

		if (executionException1 != null) {
			throw executionException1;
		}

		if (!_sourceFormatterMessages.isEmpty() ||
			!_sourceMismatchExceptions.isEmpty()) {

			String outputFileName = _sourceFormatterArgs.getOutputFileName();

			if (outputFileName != null) {
				File file = null;

				int pos = outputFileName.lastIndexOf(File.separator);

				if (pos != -1) {
					File directory = new File(outputFileName.substring(0, pos));

					if (directory.exists()) {
						file = new File(outputFileName);
					}
				}

				if (file == null) {
					file = new File(
						_sourceFormatterArgs.getBaseDirName() + outputFileName);
				}

				FileUtil.write(file, _getOutputFileContent());
			}
		}

		if ((_sourceFormatterArgs.isFailOnAutoFix() &&
			 !_sourceMismatchExceptions.isEmpty()) ||
			(_sourceFormatterArgs.isFailOnHasWarning() &&
			 !_sourceFormatterMessages.isEmpty())) {

			throw new Exception(_getExceptionMessage());
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
		Exception exception) {

		Throwable throwable = exception;

		while (true) {
			if (throwable == null) {
				return null;
			}

			if (throwable instanceof CheckstyleException) {
				return (CheckstyleException)throwable;
			}

			throwable = throwable.getCause();
		}
	}

	private Set<String> _addDependentFileName(
		Set<String> dependentFileNames, String fileName) {

		File file = new File(_sourceFormatterArgs.getBaseDirName() + fileName);

		if (file.exists()) {
			dependentFileNames.add(
				_sourceFormatterArgs.getBaseDirName() + fileName);
		}

		return dependentFileNames;
	}

	private Set<String> _addDependentFileName(
		Set<String> dependentFileNames, String fileName,
		String dependentFileName) {

		String dirName = fileName.substring(
			0, fileName.lastIndexOf(CharPool.SLASH));

		while (true) {
			String dependentFilePathName = dirName + "/" + dependentFileName;

			File file = new File(dependentFilePathName);

			if (file.exists()) {
				dependentFileNames.add(dependentFilePathName);

				return dependentFileNames;
			}

			int pos = dirName.lastIndexOf(CharPool.SLASH);

			if (pos == -1) {
				return dependentFileNames;
			}

			dirName = dirName.substring(0, pos);
		}
	}

	private void _addDependentFileNames() throws Exception {
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
				recentChangesFileName.endsWith(".lfrbuild-portal")) {

				dependentFileNames = _addDependentFileName(
					dependentFileNames, "build.properties");

				buildPropertiesAdded = true;
			}

			if (recentChangesFileName.contains("/modules/apps/archived/")) {
				dependentFileNames.addAll(
					SourceFormatterUtil.filterFileNames(
						_allFileNames, new String[0],
						new String[] {
							"**/source-formatter.properties",
							"**/test.properties"
						},
						_sourceFormatterExcludes, false));
			}

			if (recentChangesFileName.endsWith(".java") &&
				recentChangesFileName.contains("/upgrade/")) {

				dependentFileNames = _addDependentFileName(
					dependentFileNames, recentChangesFileName, "bnd.bnd");
			}
			else if (recentChangesFileName.endsWith("ServiceImpl.java")) {
				dependentFileNames = _addDependentFileName(
					dependentFileNames, recentChangesFileName, "service.xml");
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
			else if (recentChangesFileName.endsWith(
						"/modules/source-formatter.properties")) {

				dependentFileNames.addAll(
					SourceFormatterUtil.filterFileNames(
						_allFileNames, new String[0],
						new String[] {"**/build.gradle"},
						_sourceFormatterExcludes, false));
			}
			else if (recentChangesFileName.endsWith(
						_sourceFormatterArgs.getBaseDirName() +
							"release.properties")) {

				dependentFileNames.add(
					_sourceFormatterArgs.getBaseDirName() +
						"/modules/sdk/ant-bnd/src/main/java/com/liferay/ant" +
							"/bnd/social/SocialAnalyzerPlugin.java");
				dependentFileNames.add(
					_sourceFormatterArgs.getBaseDirName() +
						"/portal-impl/src/com/liferay/portal/util" +
							"/EntityResolver.java");
				dependentFileNames.add(
					_sourceFormatterArgs.getBaseDirName() +
						"/portal-impl/src/com/liferay/portlet/social/util" +
							"/SocialConfigurationImpl.java");
			}
		}

		if (_sourceFormatterArgs.isFormatCurrentBranch()) {
			if (!buildPropertiesAdded) {
				List<String> fileNames = GitUtil.getCurrentBranchFileNames(
					_sourceFormatterArgs.getBaseDirName(),
					_sourceFormatterArgs.getGitWorkingBranchName(), true);

				for (String fileName : fileNames) {
					if (!buildPropertiesAdded &&
						fileName.endsWith(".lfrbuild-portal")) {

						dependentFileNames = _addDependentFileName(
							dependentFileNames, "build.properties");

						break;
					}
				}
			}

			List<String> deletedFileNames =
				GitUtil.getCurrentBranchDeletedFileNames(
					_sourceFormatterArgs.getBaseDirName(),
					_sourceFormatterArgs.getGitWorkingBranchName());

			if (!deletedFileNames.isEmpty()) {
				dependentFileNames.addAll(
					SourceFormatterUtil.filterFileNames(
						_allFileNames, new String[0],
						new String[] {
							"**/source-formatter.properties",
							"**/source-formatter-suppressions.xml"
						},
						_sourceFormatterExcludes, false));
			}
		}

		_sourceFormatterArgs.addRecentChangesFileNames(
			dependentFileNames, null);
	}

	private boolean _containsDir(String dirName) {
		File directory = SourceFormatterUtil.getFile(
			_sourceFormatterArgs.getBaseDirName(), dirName,
			_sourceFormatterArgs.getMaxDirLevel());

		if (directory != null) {
			return true;
		}

		return false;
	}

	private void _excludeWorkingDirCheckoutPrivateApps(File portalDir)
		throws Exception {

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

	private String _getExceptionMessage() {
		int size =
			_sourceFormatterMessages.size() + _sourceMismatchExceptions.size();

		StringBundler sb = new StringBundler(size * 4);

		int index = 1;

		if (_sourceFormatterArgs.isFailOnHasWarning()) {
			for (SourceFormatterMessage sourceFormatterMessage :
					_sourceFormatterMessages) {

				sb.append(index);
				sb.append(": ");
				sb.append(sourceFormatterMessage.toString());
				sb.append("\n");

				index = index + 1;
			}
		}

		if (_sourceFormatterArgs.isFailOnAutoFix()) {
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

		return StringBundler.concat(
			"Found ", index - 1, " formatting issues:\n", sb.toString());
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

	private String _getOutputFileContent() {
		JSONObject jsonObject = new JSONObjectImpl();

		JSONArray modifiedFilesJSONArray = new JSONArrayImpl();

		for (SourceMismatchException sourceMismatchException :
				_sourceMismatchExceptions) {

			modifiedFilesJSONArray.put(sourceMismatchException.getFileName());
		}

		jsonObject.put("modifiedFileNames", modifiedFilesJSONArray);

		JSONArray checksJSONArray = new JSONArrayImpl();

		JSONObject checkJSONObject = null;
		String currentCheckName = null;
		JSONArray violationsJSONArray = null;

		Set<SourceFormatterMessage> sortedSourceFormatterMessages =
			new TreeSet<>(new SourceFormatterMessageCheckNameComparator());

		sortedSourceFormatterMessages.addAll(_sourceFormatterMessages);

		int violationsCount = 0;

		for (SourceFormatterMessage sourceFormatterMessage :
				sortedSourceFormatterMessages) {

			String checkName = sourceFormatterMessage.getCheckName();

			if (checkName == null) {
				continue;
			}

			if (!Objects.equals(checkName, currentCheckName)) {
				if (currentCheckName != null) {
					checkJSONObject.put("violations", violationsJSONArray);

					checksJSONArray.put(checkJSONObject);
				}

				checkJSONObject = new JSONObjectImpl();

				checkJSONObject.put("name", checkName);

				String documentationURLString =
					sourceFormatterMessage.getDocumentationURLString();

				if (documentationURLString != null) {
					checkJSONObject.put(
						"documentationURLString",
						sourceFormatterMessage.getDocumentationURLString());
				}

				violationsJSONArray = new JSONArrayImpl();

				currentCheckName = checkName;
			}

			JSONObject violationJSONObject = new JSONObjectImpl();

			violationJSONObject.put(
				"fileName", sourceFormatterMessage.getFileName()
			).put(
				"lineNumber", sourceFormatterMessage.getLineNumber()
			).put(
				"message", sourceFormatterMessage.getMessage()
			);

			violationsCount++;

			violationsJSONArray.put(violationJSONObject);
		}

		if (checkJSONObject != null) {
			checkJSONObject.put("violations", violationsJSONArray);

			checksJSONArray.put(checkJSONObject);

			jsonObject.put("checks", checksJSONArray);
		}

		jsonObject.put("violationsCount", violationsCount);

		return JSONUtil.toString(jsonObject);
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

	private String _getProjectPathPrefix() throws Exception {
		if (!_subrepository) {
			return null;
		}

		String fileName = "gradle.properties";

		for (int i = 0; i < _sourceFormatterArgs.getMaxDirLevel(); i++) {
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

	private Properties _getProperties(File file) throws Exception {
		Properties properties = new Properties();

		if (file.exists()) {
			properties.load(new FileInputStream(file));
		}

		return properties;
	}

	private List<String> _getPropertyValues(String key) {
		List<String> propertyValues = new ArrayList<>();

		for (Map.Entry<String, Properties> entry : _propertiesMap.entrySet()) {
			Properties properties = entry.getValue();

			if (properties.containsKey(key)) {
				propertyValues.addAll(
					ListUtil.fromString(
						properties.getProperty(key), StringPool.COMMA));
			}
		}

		return propertyValues;
	}

	private void _init() throws Exception {
		_sourceFormatterExcludes = new SourceFormatterExcludes(
			SetUtil.fromArray(DEFAULT_EXCLUDE_SYNTAX_PATTERNS));

		_portalSource = _containsDir("portal-impl");

		if (_portalSource) {
			File portalDir = SourceFormatterUtil.getPortalDir(
				_sourceFormatterArgs.getBaseDirName(),
				_sourceFormatterArgs.getMaxLineLength());

			_excludeWorkingDirCheckoutPrivateApps(portalDir);
		}

		_propertiesMap = new HashMap<>();

		// Find properties file in any parent directory

		String parentDirName = _sourceFormatterArgs.getBaseDirName();

		for (int i = 0; i < _sourceFormatterArgs.getMaxDirLevel(); i++) {
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

		for (Properties properties : _propertiesMap.values()) {
			if (GetterUtil.getBoolean(properties.get("liferay.source"))) {
				_portalSource = true;

				break;
			}
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

		for (String sourceFormatterProperty :
				_sourceFormatterArgs.getSourceFormatterProperties()) {

			_readProperties(
				sourceFormatterProperty,
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
			_sourceFormatterExcludes, _sourceFormatterArgs.getMaxDirLevel());

		_sourceFormatterSuppressions = SuppressionsLoader.loadSuppressions(
			_sourceFormatterArgs.getBaseDirName(), suppressionsFiles,
			_propertiesMap);

		_sourceFormatterConfiguration = ConfigurationLoader.loadConfiguration(
			"sourcechecks.xml");

		if (_sourceFormatterArgs.isShowDebugInformation()) {
			DebugUtil.addCheckNames(CheckType.SOURCE_CHECK, _getCheckNames());
		}
	}

	private boolean _isSubrepository() throws Exception {
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

	private void _readProperties(File propertiesFile) throws Exception {
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

		if (value != null) {
			if (FileUtil.exists(propertiesFileLocation + "portal-impl")) {
				_sourceFormatterExcludes.addDefaultExcludeSyntaxPatterns(
					_getExcludeSyntaxPatterns(value));
			}
			else {
				_sourceFormatterExcludes.addExcludeSyntaxPatterns(
					propertiesFileLocation, _getExcludeSyntaxPatterns(value));
			}

			properties.remove("source.formatter.excludes");
		}

		Properties existingProperties = _propertiesMap.get(
			propertiesFileLocation);

		if (existingProperties == null) {
			_propertiesMap.put(propertiesFileLocation, properties);
		}
		else {
			existingProperties.putAll(properties);

			_propertiesMap.put(propertiesFileLocation, existingProperties);
		}
	}

	private void _readProperties(String content, String propertiesFileLocation)
		throws Exception {

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

	private void _validateCommitMessages() throws Exception {
		List<String> commitMessages = GitUtil.getCurrentBranchCommitMessages(
			_sourceFormatterArgs.getBaseDirName(),
			_sourceFormatterArgs.getGitWorkingBranchName());

		JIRAUtil.validateJIRAProjectNames(
			commitMessages, _getPropertyValues("jira.project.keys"));
		JIRAUtil.validateJIRATicketIds(commitMessages, 20);

		JIRAUtil.validateJIRASecurityKeywords(
			commitMessages,
			_getPropertyValues("jira.security.vulnerability.keywords"), 20);
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
				catch (InterruptedException interruptedException) {
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
	private final List<SourceMismatchException> _sourceMismatchExceptions =
		new CopyOnWriteArrayList<>();
	private final List<SourceProcessor> _sourceProcessors = new ArrayList<>();
	private boolean _subrepository;

	private static class SourceFormatterMessageCheckNameComparator
		implements Comparator<SourceFormatterMessage> {

		@Override
		public int compare(
			SourceFormatterMessage sourceFormatterMessage1,
			SourceFormatterMessage sourceFormatterMessage2) {

			String checkName1 = sourceFormatterMessage1.getCheckName();
			String checkName2 = sourceFormatterMessage2.getCheckName();

			if ((checkName1 != null) && (checkName2 != null) &&
				!checkName1.equals(checkName2)) {

				return checkName1.compareTo(checkName2);
			}

			return sourceFormatterMessage1.compareTo(sourceFormatterMessage2);
		}

	}

}