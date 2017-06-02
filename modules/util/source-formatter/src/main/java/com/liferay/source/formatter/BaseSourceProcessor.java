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

import com.liferay.portal.kernel.nio.charset.CharsetDecoderUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.configuration.SourceChecksResult;
import com.liferay.source.formatter.checks.configuration.SourceChecksSuppressions;
import com.liferay.source.formatter.checks.configuration.SuppressionsLoader;
import com.liferay.source.formatter.checks.util.SourceChecksUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.awt.Desktop;

import java.io.File;

import java.net.URI;

import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

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
		if (sourceFormatterArgs.isShowDocumentation()) {
			System.setProperty("java.awt.headless", "false");
		}

		List<String> fileNames = getFileNames();

		if (fileNames.isEmpty()) {
			return;
		}

		_pluginsInsideModulesDirectoryNames =
			getPluginsInsideModulesDirectoryNames();

		_sourceChecks = _getSourceChecks(_containsModuleFile(fileNames));

		_sourceChecksSuppressions = SuppressionsLoader.loadSuppressions(
			getSuppressionsFiles("sourcechecks-suppressions.xml"));

		ExecutorService executorService = Executors.newFixedThreadPool(
			sourceFormatterArgs.getProcessorThreadCount());

		List<Future<Void>> futures = new ArrayList<>(fileNames.size());

		for (final String fileName : fileNames) {
			Future<Void> future = executorService.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						try {
							_format(fileName);

							return null;
						}
						catch (Exception e) {
							throw new RuntimeException(
								"Unable to format " + fileName, e);
						}
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
			return fileNames;
		}

		return doGetFileNames();
	}

	@Override
	public SourceMismatchException getFirstSourceMismatchException() {
		return _firstSourceMismatchException;
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
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	@Override
	public void setProperties(Properties properties) {
		_properties = properties;
	}

	@Override
	public void setSourceFormatterArgs(
		SourceFormatterArgs sourceFormatterArgs) {

		this.sourceFormatterArgs = sourceFormatterArgs;

		_init();
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

	protected File getFile(String fileName, int level) {
		return SourceFormatterUtil.getFile(
			sourceFormatterArgs.getBaseDirName(), fileName, level);
	}

	protected List<String> getFileNames(
			String basedir, String[] excludes, String[] includes)
		throws Exception {

		if (_excludes != null) {
			excludes = ArrayUtil.append(excludes, _excludes);
		}

		return SourceFormatterUtil.scanForFiles(
			basedir, excludes, includes,
			sourceFormatterArgs.isIncludeSubrepositories());
	}

	protected List<String> getFileNames(String[] excludes, String[] includes)
		throws Exception {

		return getFileNames(excludes, includes, false);
	}

	protected List<String> getFileNames(
			String[] excludes, String[] includes, boolean forceIncludeAllFiles)
		throws Exception {

		if (_excludes != null) {
			excludes = ArrayUtil.append(excludes, _excludes);
		}

		if (!forceIncludeAllFiles &&
			(sourceFormatterArgs.getRecentChangesFileNames() != null)) {

			return SourceFormatterUtil.filterRecentChangesFileNames(
				sourceFormatterArgs.getBaseDirName(),
				sourceFormatterArgs.getRecentChangesFileNames(), excludes,
				includes, sourceFormatterArgs.isIncludeSubrepositories());
		}

		return SourceFormatterUtil.filterFileNames(
			_allFileNames, excludes, includes);
	}

	protected List<String> getPluginsInsideModulesDirectoryNames()
		throws Exception {

		if (_pluginsInsideModulesDirectoryNames != null) {
			return _pluginsInsideModulesDirectoryNames;
		}

		List<String> pluginsInsideModulesDirectoryNames = new ArrayList<>();

		List<String> pluginBuildFileNames = getFileNames(
			new String[0],
			new String[] {
				"**/modules/apps/**/build.xml",
				"**/modules/private/apps/**/build.xml"
			},
			true);

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

	protected String getProperty(String key) {
		return _properties.getProperty(key);
	}

	protected List<String> getPropertyList(String key) {
		return ListUtil.fromString(
			GetterUtil.getString(getProperty(key)), StringPool.COMMA);
	}

	protected List<File> getSuppressionsFiles(String fileName)
		throws Exception {

		List<File> suppressionsFiles = new ArrayList<>();

		// Find suppressions file in portal-impl/src/

		if (portalSource) {
			File suppressionsFile = getFile(
				"portal-impl/src/" + fileName, PORTAL_MAX_DIR_LEVEL);

			if (suppressionsFile != null) {
				suppressionsFiles.add(suppressionsFile);
			}
		}

		// Find suppressions files in any parent directory

		int maxDirLevel = PLUGINS_MAX_DIR_LEVEL;
		String parentDirName = sourceFormatterArgs.getBaseDirName();

		if (portalSource || subrepository) {
			maxDirLevel = PORTAL_MAX_DIR_LEVEL;
		}

		for (int i = 0; i < maxDirLevel; i++) {
			File suppressionsFile = new File(parentDirName + fileName);

			if (suppressionsFile.exists()) {
				suppressionsFiles.add(suppressionsFile);
			}

			parentDirName += "../";
		}

		if (!portalSource && !subrepository) {
			return suppressionsFiles;
		}

		// Find suppressions files in any child directory

		List<String> moduleSuppressionsFileNames = getFileNames(
			new String[0], new String[] {"**/modules/**/" + fileName}, true);

		for (String moduleSuppressionsFileName : moduleSuppressionsFileNames) {
			moduleSuppressionsFileName = StringUtil.replace(
				moduleSuppressionsFileName, CharPool.BACK_SLASH,
				CharPool.SLASH);

			suppressionsFiles.add(new File(moduleSuppressionsFileName));
		}

		return suppressionsFiles;
	}

	protected void postFormat() throws Exception {
	}

	protected void printError(String fileName, String message) {
		if (sourceFormatterArgs.isPrintErrors()) {
			SourceFormatterUtil.printError(fileName, message);
		}
	}

	protected void processFormattedFile(
			File file, String fileName, String content, String newContent)
		throws Exception {

		if (!content.equals(newContent)) {
			if (sourceFormatterArgs.isPrintErrors()) {
				SourceFormatterUtil.printError(fileName, file);
			}

			if (sourceFormatterArgs.isAutoFix()) {
				FileUtil.write(file, newContent);
			}
			else if (_firstSourceMismatchException == null) {
				_firstSourceMismatchException = new SourceMismatchException(
					fileName, content, newContent);
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

	protected String processSourceChecks(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		SourceChecksResult sourceChecksResult =
			SourceChecksUtil.processSourceChecks(
				file, fileName, absolutePath, content,
				_isModulesFile(absolutePath), _sourceChecks,
				_sourceChecksSuppressions);

		for (SourceFormatterMessage sourceFormatterMessage :
				sourceChecksResult.getSourceFormatterMessages()) {

			processMessage(fileName, sourceFormatterMessage);
		}

		return sourceChecksResult.getContent();
	}

	protected static Pattern javaSourceInsideJSPLinePattern = Pattern.compile(
		"<%=(.+?)%>");
	protected static boolean portalSource;
	protected static boolean subrepository;

	protected SourceFormatterArgs sourceFormatterArgs;

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

	private final String _format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		_sourceFormatterMessagesMap.remove(fileName);

		_checkUTF8(file, fileName);

		String newContent = processSourceChecks(
			file, fileName, absolutePath, content);

		if (content.equals(newContent)) {
			return content;
		}

		return _format(file, fileName, absolutePath, newContent);
	}

	private final void _format(String fileName) throws Exception {
		if (!_isMatchPath(fileName)) {
			return;
		}

		fileName = StringUtil.replace(
			fileName, CharPool.BACK_SLASH, CharPool.SLASH);

		String absolutePath = SourceUtil.getAbsolutePath(fileName);

		File file = new File(absolutePath);

		String content = FileUtil.read(file);

		String newContent = _format(file, fileName, absolutePath, content);

		processFormattedFile(file, fileName, content, newContent);
	}

	private String[] _getExcludes() {
		if (sourceFormatterArgs.getFileNames() != null) {
			return new String[0];
		}

		List<String> excludesList = ListUtil.fromString(
			GetterUtil.getString(
				System.getProperty("source.formatter.excludes")));

		excludesList.addAll(getPropertyList("source.formatter.excludes"));

		return excludesList.toArray(new String[excludesList.size()]);
	}

	private List<SourceCheck> _getSourceChecks(boolean includeModuleChecks)
		throws Exception {

		Class<?> clazz = getClass();

		List<SourceCheck> sourceChecks = SourceChecksUtil.getSourceChecks(
			clazz.getSimpleName(), portalSource, subrepository,
			includeModuleChecks);

		for (SourceCheck sourceCheck : sourceChecks) {
			_initSourceCheck(sourceCheck);
		}

		return sourceChecks;
	}

	private void _init() {
		try {
			portalSource = _isPortalSource();
			subrepository = _isSubrepository();

			_sourceFormatterMessagesMap = new HashMap<>();
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}

		_excludes = _getExcludes();
	}

	private void _initSourceCheck(SourceCheck sourceCheck) throws Exception {
		sourceCheck.setAllFileNames(_allFileNames);
		sourceCheck.setBaseDirName(sourceFormatterArgs.getBaseDirName());
		sourceCheck.setExcludes(_excludes);
		sourceCheck.setMaxLineLength(sourceFormatterArgs.getMaxLineLength());
		sourceCheck.setPluginsInsideModulesDirectoryNames(
			_pluginsInsideModulesDirectoryNames);
		sourceCheck.setPortalSource(portalSource);
		sourceCheck.setProperties(_properties);
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

		if (includePlugins) {
			return absolutePath.contains("/modules/");
		}

		try {
			for (String directoryName :
					getPluginsInsideModulesDirectoryNames()) {

				if (absolutePath.contains(directoryName)) {
					return false;
				}
			}
		}
		catch (Exception e) {
		}

		return absolutePath.contains("/modules/");
	}

	private boolean _isPortalSource() {
		if (getFile("portal-impl", PORTAL_MAX_DIR_LEVEL) != null) {
			return true;
		}

		return false;
	}

	private boolean _isSubrepository() {
		String baseDirAbsolutePath = SourceUtil.getAbsolutePath(
			sourceFormatterArgs.getBaseDirName());

		int x = baseDirAbsolutePath.length();

		for (int i = 0; i < _SUBREPOSITORY_MAX_DIR_LEVEL; i++) {
			x = baseDirAbsolutePath.lastIndexOf(CharPool.FORWARD_SLASH, x - 1);

			if (x == -1) {
				return false;
			}

			String dirName = baseDirAbsolutePath.substring(x + 1);

			if (dirName.startsWith("com-liferay-")) {
				return true;
			}
		}

		return false;
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

	private static final int _SUBREPOSITORY_MAX_DIR_LEVEL = 3;

	private List<String> _allFileNames;
	private boolean _browserStarted;
	private String[] _excludes;
	private SourceMismatchException _firstSourceMismatchException;
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private List<String> _pluginsInsideModulesDirectoryNames;
	private Properties _properties;
	private List<SourceCheck> _sourceChecks = new ArrayList<>();
	private SourceChecksSuppressions _sourceChecksSuppressions;
	private Map<String, Set<SourceFormatterMessage>>
		_sourceFormatterMessagesMap = new ConcurrentHashMap<>();

}