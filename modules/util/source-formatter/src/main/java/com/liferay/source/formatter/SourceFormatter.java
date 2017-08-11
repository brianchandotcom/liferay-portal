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

	public static final ExcludeSyntaxPattern[]
		DEFAULT_EXCLUDE_SYNTAX_PATTERNS = {
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.git/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/.gradle/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/bin/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/build/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/classes/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/node_modules/**"),
			new ExcludeSyntaxPattern(
				ExcludeSyntax.GLOB, "**/npm-shrinkwrap.json"),
			new ExcludeSyntaxPattern(
				ExcludeSyntax.GLOB, "**/package-lock.json"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/test-classes/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/test-coverage/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/test-results/**"),
			new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, "**/tmp/**")
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

			boolean showDocumentation = ArgumentsUtil.getBoolean(
				arguments, "show.documentation",
				SourceFormatterArgs.SHOW_DOCUMENTATION);

			sourceFormatterArgs.setShowDocumentation(showDocumentation);

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
		_init();

		List<SourceProcessor> sourceProcessors = new ArrayList<>();

		sourceProcessors.add(new BNDSourceProcessor());
		sourceProcessors.add(new CodeownersSourceProcessor());
		sourceProcessors.add(new CQLSourceProcessor());
		sourceProcessors.add(new CSSSourceProcessor());
		sourceProcessors.add(new DockerfileSourceProcessor());
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
		sourceProcessors.add(new YMLSourceProcessor());

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

		List<String> excludes = ListUtil.fromString(
			sourceFormatterExcludes, StringPool.COMMA);

		List<ExcludeSyntaxPattern> excludeSyntaxPatterns =
			new ArrayList<ExcludeSyntaxPattern>();

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
				_sourceFormatterExcludes);

		for (String modulePropertiesFileName : modulePropertiesFileNames) {
			_readProperties(new File(modulePropertiesFileName));
		}
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
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private Map<String, Properties> _propertiesMap = new HashMap<>();
	private final SourceFormatterArgs _sourceFormatterArgs;
	private SourceFormatterExcludes _sourceFormatterExcludes;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new ConcurrentSkipListSet<>();

}