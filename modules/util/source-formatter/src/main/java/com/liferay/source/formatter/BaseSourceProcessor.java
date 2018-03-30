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

import com.liferay.petra.nio.CharsetDecoderUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.configuration.SourceChecksResult;
import com.liferay.source.formatter.checks.configuration.SourceFormatterConfiguration;
import com.liferay.source.formatter.checks.configuration.SourceFormatterSuppressions;
import com.liferay.source.formatter.checks.util.SourceChecksUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.Checker;
import com.liferay.source.formatter.checkstyle.util.CheckstyleLogger;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.awt.Desktop;

import java.io.File;

import java.net.URI;

import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.tools.ant.types.selectors.SelectorUtils;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public abstract class BaseSourceProcessor implements SourceProcessor {

	public static final int PLUGINS_MAX_DIR_LEVEL =
		ToolsUtil.PLUGINS_MAX_DIR_LEVEL;

	public static final int PORTAL_MAX_DIR_LEVEL =
		ToolsUtil.PORTAL_MAX_DIR_LEVEL;

	@Override
	public final void format() throws Exception {
		List<String> fileNames = getFileNames();

		if (sourceFormatterArgs.isShowDebugInformation()) {
			Class<?> clazz = getClass();

			DebugUtil.addProcessorFileCount(
				clazz.getSimpleName(), fileNames.size());
		}

		if (fileNames.isEmpty()) {
			addProgressStatusUpdate(
				new ProgressStatusUpdate(ProgressStatus.CHECKS_INITIALIZED, 0));

			return;
		}

		_sourceFormatterMessagesMap = new HashMap<>();

		_sourceChecks = _getSourceChecks(
			_sourceFormatterConfiguration, _containsModuleFile(fileNames));

		addProgressStatusUpdate(
			new ProgressStatusUpdate(
				ProgressStatus.CHECKS_INITIALIZED, fileNames.size()));

		ExecutorService executorService = Executors.newFixedThreadPool(
			sourceFormatterArgs.getProcessorThreadCount());

		List<Future<Void>> futures = new ArrayList<>(fileNames.size());

		for (final String fileName : fileNames) {
			Future<Void> future = executorService.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_performTask(fileName);

						return null;
					}

				});

			futures.add(future);
		}

		for (Future<Void> future : futures) {
			future.get();
		}

		executorService.shutdown();

		postFormat();
	}

	public final List<String> getFileNames() throws Exception {
		List<String> fileNames = sourceFormatterArgs.getFileNames();

		if (fileNames != null) {
			return SourceFormatterUtil.filterFileNames(
				fileNames, new String[0], getIncludes(),
				new SourceFormatterExcludes(), false);
		}

		return doGetFileNames();
	}

	@Override
	public String[] getIncludes() {
		return filterIncludes(doGetIncludes());
	}

	@Override
	public List<String> getModifiedFileNames() {
		return _modifiedFileNames;
	}

	@Override
	public Set<SourceFormatterMessage> getSourceFormatterMessages() {
		Set<SourceFormatterMessage> sourceFormatterMessages = new TreeSet<>();

		for (Map.Entry<String, Set<SourceFormatterMessage>> entry :
				_sourceFormatterMessagesMap.entrySet()) {

			sourceFormatterMessages.addAll(entry.getValue());
		}

		return sourceFormatterMessages;
	}

	@Override
	public List<File> getSourceFormatterSuppressionsFiles() {
		return _sourceFormatterSuppressionsFiles;
	}

	@Override
	public List<SourceMismatchException> getSourceMismatchExceptions() {
		return _sourceMismatchExceptions;
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	@Override
	public void setPluginsInsideModulesDirectoryNames(
		List<String> pluginsInsideModulesDirectoryNames) {

		_pluginsInsideModulesDirectoryNames =
			pluginsInsideModulesDirectoryNames;
	}

	@Override
	public void setPortalSource(boolean portalSource) {
		this.portalSource = portalSource;
	}

	@Override
	public void setProgressStatusQueue(
		BlockingQueue<ProgressStatusUpdate> progressStatusQueue) {

		_progressStatusQueue = progressStatusQueue;
	}

	@Override
	public void setProjectPathPrefix(String projectPathPrefix) {
		_projectPathPrefix = projectPathPrefix;
	}

	@Override
	public void setPropertiesMap(Map<String, Properties> propertiesMap) {
		_propertiesMap = propertiesMap;
	}

	@Override
	public void setSourceFormatterArgs(
		SourceFormatterArgs sourceFormatterArgs) {

		this.sourceFormatterArgs = sourceFormatterArgs;
	}

	@Override
	public void setSourceFormatterConfiguration(
		SourceFormatterConfiguration sourceFormatterConfiguration) {

		_sourceFormatterConfiguration = sourceFormatterConfiguration;
	}

	@Override
	public void setSourceFormatterExcludes(
		SourceFormatterExcludes sourceFormatterExcludes) {

		_sourceFormatterExcludes = sourceFormatterExcludes;
	}

	@Override
	public void setSourceFormatterSuppressions(
		SourceFormatterSuppressions sourceFormatterSuppressions) {

		_sourceFormatterSuppressions = sourceFormatterSuppressions;
	}

	@Override
	public void setSourceFormatterSuppressionsFiles(
		List<File> sourceFormatterSuppressionsFiles) {

		_sourceFormatterSuppressionsFiles = sourceFormatterSuppressionsFiles;
	}

	@Override
	public void setSubrepository(boolean subrepository) {
		this.subrepository = subrepository;
	}

	protected void addProgressStatusUpdate(
			ProgressStatusUpdate progressStatusUpdate)
		throws Exception {

		_progressStatusQueue.put(progressStatusUpdate);
	}

	protected abstract List<String> doGetFileNames() throws Exception;

	protected abstract String[] doGetIncludes();

	protected String[] filterIncludes(String[] includes) {
		List<String> fileExtensions = sourceFormatterArgs.getFileExtensions();

		if (fileExtensions.isEmpty()) {
			return includes;
		}

		String[] filteredIncludes = new String[0];

		for (String include : includes) {
			for (String fileExtension : fileExtensions) {
				if (include.endsWith(fileExtension)) {
					filteredIncludes = ArrayUtil.append(
						filteredIncludes, include);
				}
			}
		}

		return filteredIncludes;
	}

	protected File format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		Set<String> modifiedContents = new HashSet<>();
		Set<String> modifiedMessages = new TreeSet<>();

		String newContent = format(
			file, fileName, absolutePath, content, content, modifiedContents,
			modifiedMessages, 0);

		return processFormattedFile(
			file, fileName, content, newContent, modifiedMessages);
	}

	protected String format(
			File file, String fileName, String absolutePath, String content,
			String originalContent, Set<String> modifiedContents,
			Set<String> modifiedMessages, int count)
		throws Exception {

		_sourceFormatterMessagesMap.remove(fileName);

		_checkUTF8(file, fileName);

		String newContent = _processSourceChecks(
			file, fileName, absolutePath, content, modifiedMessages);

		if ((newContent == null) || content.equals(newContent)) {
			return newContent;
		}

		if (!modifiedContents.add(newContent)) {
			_sourceFormatterMessagesMap.remove(fileName);

			processMessage(fileName, "Infinite loop in SourceFormatter");

			return originalContent;
		}

		if (newContent.length() > content.length()) {
			count++;

			if (count > 100) {
				_sourceFormatterMessagesMap.remove(fileName);

				processMessage(fileName, "Infinite loop in SourceFormatter");

				return originalContent;
			}
		}
		else {
			count = 0;
		}

		return format(
			file, fileName, absolutePath, newContent, originalContent,
			modifiedContents, modifiedMessages, count);
	}

	protected List<String> getAllFileNames() {
		return _allFileNames;
	}

	protected File getFile(String fileName, int level) {
		return SourceFormatterUtil.getFile(
			sourceFormatterArgs.getBaseDirName(), fileName, level);
	}

	protected List<String> getFileNames(String[] excludes, String[] includes)
		throws Exception {

		return getFileNames(excludes, includes, false);
	}

	protected List<String> getFileNames(
			String[] excludes, String[] includes, boolean forceIncludeAllFiles)
		throws Exception {

		if (!forceIncludeAllFiles &&
			(sourceFormatterArgs.getRecentChangesFileNames() != null)) {

			return SourceFormatterUtil.filterRecentChangesFileNames(
				sourceFormatterArgs.getBaseDirName(),
				sourceFormatterArgs.getRecentChangesFileNames(), excludes,
				includes, _sourceFormatterExcludes,
				sourceFormatterArgs.isIncludeSubrepositories());
		}

		return SourceFormatterUtil.filterFileNames(
			_allFileNames, excludes, includes, _sourceFormatterExcludes,
			forceIncludeAllFiles);
	}

	protected List<String> getPluginsInsideModulesDirectoryNames() {
		return _pluginsInsideModulesDirectoryNames;
	}

	protected BlockingQueue<ProgressStatusUpdate> getProgressStatusQueue() {
		return _progressStatusQueue;
	}

	protected Map<String, Properties> getPropertiesMap() {
		return _propertiesMap;
	}

	protected SourceFormatterExcludes getSourceFormatterExcludes() {
		return _sourceFormatterExcludes;
	}

	protected SourceFormatterSuppressions getSourceFormatterSuppressions() {
		return _sourceFormatterSuppressions;
	}

	protected boolean hasGeneratedTag(String content) {
		if ((content.contains("@generated") || content.contains("$ANTLR")) &&
			!content.contains("hasGeneratedTag")) {

			return true;
		}
		else {
			return false;
		}
	}

	protected void postFormat() throws Exception {
	}

	protected void printError(String fileName, String message) {
		if (sourceFormatterArgs.isPrintErrors()) {
			SourceFormatterUtil.printError(fileName, message);
		}
	}

	protected synchronized Set<SourceFormatterMessage> processCheckstyle(
			Configuration configuration, CheckstyleLogger checkstyleLogger,
			File[] files)
		throws Exception {

		if (ArrayUtil.isEmpty(files)) {
			return Collections.emptySet();
		}

		Checker checker = new Checker();

		Class<?> clazz = getClass();

		checker.setModuleClassLoader(clazz.getClassLoader());

		SourceFormatterSuppressions sourceFormatterSuppressions =
			getSourceFormatterSuppressions();

		checker.addFilter(sourceFormatterSuppressions.getCheckstyleFilterSet());

		checker.configure(configuration);

		checker.addListener(checkstyleLogger);
		checker.setCheckstyleLogger(checkstyleLogger);

		checker.process(Arrays.asList(files));

		return checker.getSourceFormatterMessages();
	}

	protected File processFormattedFile(
			File file, String fileName, String content, String newContent,
			Set<String> modifiedMessages)
		throws Exception {

		if (!content.equals(newContent)) {
			if (sourceFormatterArgs.isPrintErrors()) {
				for (String modifiedMessage : modifiedMessages) {
					SourceFormatterUtil.printError(fileName, modifiedMessage);
				}
			}

			if (sourceFormatterArgs.isAutoFix()) {
				if (newContent != null) {
					FileUtil.write(file, newContent);
				}
				else {
					file.delete();
				}
			}
			else {
				_sourceMismatchExceptions.add(
					new SourceMismatchException(fileName, content, newContent));
			}
		}

		if (sourceFormatterArgs.isPrintErrors()) {
			Set<SourceFormatterMessage> sourceFormatterMessages =
				_sourceFormatterMessagesMap.get(fileName);

			if (sourceFormatterMessages != null) {
				for (SourceFormatterMessage sourceFormatterMessage :
						sourceFormatterMessages) {

					SourceFormatterUtil.printError(
						fileName, sourceFormatterMessage.toString());

					if (_browserStarted ||
						!sourceFormatterArgs.isShowDocumentation() ||
						!Desktop.isDesktopSupported()) {

						continue;
					}

					String markdownFilePath =
						sourceFormatterMessage.getMarkdownFilePath();

					if (Validator.isNotNull(markdownFilePath)) {
						Desktop desktop = Desktop.getDesktop();

						desktop.browse(new URI(markdownFilePath));

						_browserStarted = true;
					}
				}
			}
		}

		_modifiedFileNames.add(file.getAbsolutePath());

		return file;
	}

	protected void processMessage(
		String fileName, SourceFormatterMessage sourceFormatterMessage) {

		Set<SourceFormatterMessage> sourceFormatterMessages =
			_sourceFormatterMessagesMap.get(fileName);

		if (sourceFormatterMessages == null) {
			sourceFormatterMessages = new TreeSet<>();
		}

		sourceFormatterMessages.add(sourceFormatterMessage);

		_sourceFormatterMessagesMap.put(fileName, sourceFormatterMessages);
	}

	protected void processMessage(String fileName, String message) {
		processMessage(
			fileName, new SourceFormatterMessage(fileName, message, null, -1));
	}

	protected boolean portalSource;
	protected SourceFormatterArgs sourceFormatterArgs;
	protected boolean subrepository;

	private void _checkUTF8(File file, String fileName) throws Exception {
		byte[] bytes = FileUtil.getBytes(file);

		try {
			CharsetDecoder charsetDecoder =
				CharsetDecoderUtil.getCharsetDecoder(
					StringPool.UTF8, CodingErrorAction.REPORT);

			charsetDecoder.decode(ByteBuffer.wrap(bytes));
		}
		catch (Exception e) {
			processMessage(fileName, "UTF-8");
		}
	}

	private boolean _containsModuleFile(List<String> fileNames) {
		if (subrepository) {
			return true;
		}

		if (!portalSource) {
			return false;
		}

		for (String fileName : fileNames) {
			if (!_isMatchPath(fileName)) {
				continue;
			}

			String absolutePath = SourceUtil.getAbsolutePath(fileName);

			if (_isModulesFile(absolutePath, true)) {
				return true;
			}
		}

		return false;
	}

	private void _format(String fileName) throws Exception {
		if (!_isMatchPath(fileName)) {
			addProgressStatusUpdate(
				new ProgressStatusUpdate(ProgressStatus.CHECK_FILE_COMPLETED));

			return;
		}

		fileName = StringUtil.replace(
			fileName, CharPool.BACK_SLASH, CharPool.SLASH);

		String absolutePath = SourceUtil.getAbsolutePath(fileName);

		File file = new File(absolutePath);

		String content = FileUtil.read(file);

		if (hasGeneratedTag(content)) {
			return;
		}

		format(file, fileName, absolutePath, content);

		addProgressStatusUpdate(
			new ProgressStatusUpdate(ProgressStatus.CHECK_FILE_COMPLETED));
	}

	private List<SourceCheck> _getSourceChecks(
			SourceFormatterConfiguration sourceFormatterConfiguration,
			boolean includeModuleChecks)
		throws Exception {

		Class<?> clazz = getClass();

		List<SourceCheck> sourceChecks = SourceChecksUtil.getSourceChecks(
			sourceFormatterConfiguration, clazz.getSimpleName(),
			getPropertiesMap(), portalSource, subrepository,
			includeModuleChecks);

		for (SourceCheck sourceCheck : sourceChecks) {
			_initSourceCheck(sourceCheck);
		}

		return sourceChecks;
	}

	private void _initSourceCheck(SourceCheck sourceCheck) throws Exception {
		sourceCheck.setAllFileNames(_allFileNames);
		sourceCheck.setBaseDirName(sourceFormatterArgs.getBaseDirName());
		sourceCheck.setMaxLineLength(sourceFormatterArgs.getMaxLineLength());
		sourceCheck.setPluginsInsideModulesDirectoryNames(
			_pluginsInsideModulesDirectoryNames);
		sourceCheck.setPortalSource(portalSource);
		sourceCheck.setProjectPathPrefix(_projectPathPrefix);
		sourceCheck.setPropertiesMap(_propertiesMap);
		sourceCheck.setSourceFormatterExcludes(_sourceFormatterExcludes);
		sourceCheck.setSubrepository(subrepository);

		sourceCheck.init();
	}

	private boolean _isMatchPath(String fileName) {
		for (String pattern : getIncludes()) {
			if (SelectorUtils.matchPath(_normalizePattern(pattern), fileName)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isModulesFile(String absolutePath) {
		return _isModulesFile(absolutePath, false);
	}

	private boolean _isModulesFile(
		String absolutePath, boolean includePlugins) {

		if (subrepository) {
			return true;
		}

		if (!portalSource) {
			return false;
		}

		if (includePlugins) {
			return absolutePath.contains("/modules/");
		}

		try {
			for (String directoryName : _pluginsInsideModulesDirectoryNames) {
				if (absolutePath.contains(directoryName)) {
					return false;
				}
			}
		}
		catch (Exception e) {
		}

		return absolutePath.contains("/modules/");
	}

	private String _normalizePattern(String originalPattern) {
		String pattern = originalPattern.replace(
			CharPool.SLASH, File.separatorChar);

		pattern = pattern.replace(CharPool.BACK_SLASH, File.separatorChar);

		if (pattern.endsWith(File.separator)) {
			pattern += SelectorUtils.DEEP_TREE_MATCH;
		}

		return pattern;
	}

	private void _performTask(String fileName) {
		try {
			if (!sourceFormatterArgs.isShowDebugInformation()) {
				_format(fileName);

				return;
			}

			DebugUtil.startTask();

			_format(fileName);

			DebugUtil.finishTask();
		}
		catch (Throwable t) {
			throw new RuntimeException("Unable to format " + fileName, t);
		}
	}

	private String _processSourceChecks(
			File file, String fileName, String absolutePath, String content,
			Set<String> modifiedMessages)
		throws Exception {

		SourceChecksResult sourceChecksResult =
			SourceChecksUtil.processSourceChecks(
				file, fileName, absolutePath, content, modifiedMessages,
				_isModulesFile(absolutePath), _sourceChecks,
				_sourceFormatterSuppressions,
				sourceFormatterArgs.isShowDebugInformation());

		for (SourceFormatterMessage sourceFormatterMessage :
				sourceChecksResult.getSourceFormatterMessages()) {

			processMessage(fileName, sourceFormatterMessage);
		}

		return sourceChecksResult.getContent();
	}

	private List<String> _allFileNames;
	private boolean _browserStarted;
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private List<String> _pluginsInsideModulesDirectoryNames;
	private BlockingQueue<ProgressStatusUpdate> _progressStatusQueue;
	private String _projectPathPrefix;
	private Map<String, Properties> _propertiesMap;
	private List<SourceCheck> _sourceChecks = new ArrayList<>();
	private SourceFormatterConfiguration _sourceFormatterConfiguration;
	private SourceFormatterExcludes _sourceFormatterExcludes;
	private Map<String, Set<SourceFormatterMessage>>
		_sourceFormatterMessagesMap = new ConcurrentHashMap<>();
	private SourceFormatterSuppressions _sourceFormatterSuppressions;
	private List<File> _sourceFormatterSuppressionsFiles;
	private final List<SourceMismatchException> _sourceMismatchExceptions =
		new ArrayList<>();

}