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
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.JavaTermCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.configuration.ConfigurationLoader;
import com.liferay.source.formatter.checks.configuration.SourceCheckConfiguration;
import com.liferay.source.formatter.checks.configuration.SourceFormatterConfiguration;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.awt.Desktop;

import java.io.File;

import java.lang.reflect.Constructor;

import java.net.URI;

import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.nio.file.Paths;

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

import org.apache.commons.beanutils.BeanUtils;
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

			String absolutePath = _getAbsolutePath(pluginBuildFileName);

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

					String markdownFileName =
						sourceFormatterMessage.getMarkdownFileName();

					if (Validator.isNotNull(markdownFileName)) {
						Desktop desktop = Desktop.getDesktop();

						desktop.browse(
							new URI(_DOCUMENTATION_URL + markdownFileName));

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

	protected String processSourceChecks(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (ListUtil.isEmpty(_sourceChecks)) {
			return content;
		}

		JavaClass javaClass = null;
		List<JavaClass> anonymousClasses = null;

		for (SourceCheck sourceCheck : _sourceChecks) {
			if (sourceCheck.isModulesCheck() && !_isModulesFile(absolutePath)) {
				continue;
			}

			String newContent = null;

			if (sourceCheck instanceof FileCheck) {
				newContent = _processFileCheck(
					(FileCheck)sourceCheck, fileName, absolutePath, content);
			}
			else if ((sourceCheck instanceof JavaTermCheck) &&
					 (this instanceof JavaSourceProcessor)) {

				if (javaClass == null) {
					try {
						anonymousClasses =
							JavaClassParser.parseAnonymousClasses(content);
						javaClass = JavaClassParser.parseJavaClass(
							fileName, content);
					}
					catch (ParseException pe) {
						processMessage(
							fileName,
							new SourceFormatterMessage(
								fileName, pe.getMessage(), null, -1));

						continue;
					}
				}

				newContent = _processJavaTermCheck(
					(JavaTermCheck)sourceCheck, javaClass, anonymousClasses,
					fileName, absolutePath, content);
			}

			if (!newContent.equals(content)) {
				return newContent;
			}
		}

		return content;
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
			processMessage(
				fileName,
				new SourceFormatterMessage(fileName, "UTF-8", null, -1));
		}
	}

	private boolean _containsModuleFile(List<String> fileNames) {
		for (String fileName : fileNames) {
			if (!_isMatchPath(fileName)) {
				continue;
			}

			String absolutePath = _getAbsolutePath(fileName);

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

		String absolutePath = _getAbsolutePath(fileName);

		File file = new File(absolutePath);

		String content = FileUtil.read(file);

		String newContent = _format(file, fileName, absolutePath, content);

		processFormattedFile(file, fileName, content, newContent);
	}

	private String _getAbsolutePath(String fileName) {
		Path filePath = Paths.get(fileName);

		filePath = filePath.toAbsolutePath();

		filePath = filePath.normalize();

		return StringUtil.replace(
			filePath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);
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

		SourceFormatterConfiguration sourceFormatterConfiguration =
			ConfigurationLoader.loadConfiguration("sourcechecks.xml");

		Class<?> clazz = getClass();

		List<SourceCheck> sourceChecks = _getSourceChecks(
			sourceFormatterConfiguration, clazz.getSimpleName(),
			includeModuleChecks);

		sourceChecks.addAll(
			_getSourceChecks(
				sourceFormatterConfiguration, "all", includeModuleChecks));

		return sourceChecks;
	}

	private List<SourceCheck> _getSourceChecks(
			SourceFormatterConfiguration sourceFormatterConfiguration,
			String sourceProcessorName, boolean includeModuleChecks)
		throws Exception {

		List<SourceCheck> sourceChecks = new ArrayList<>();

		List<SourceCheckConfiguration> sourceCheckConfigurations =
			sourceFormatterConfiguration.getSourceCheckConfigurations(
				sourceProcessorName);

		if (sourceCheckConfigurations == null) {
			return sourceChecks;
		}

		for (SourceCheckConfiguration sourceCheckConfiguration :
				sourceCheckConfigurations) {

			String sourceCheckName = sourceCheckConfiguration.getName();

			if (!sourceCheckName.contains(StringPool.PERIOD)) {
				sourceCheckName =
					"com.liferay.source.formatter.checks." + sourceCheckName;
			}

			Class<?> sourceCheckClass = null;

			try {
				sourceCheckClass = Class.forName(sourceCheckName);
			}
			catch (ClassNotFoundException cnfe) {
				SourceFormatterUtil.printError(
					"sourcechecks.xml",
					"sourcechecks.xml: Class " + sourceCheckName +
						" cannot be found");

				continue;
			}

			Constructor<?> declaredConstructor =
				sourceCheckClass.getDeclaredConstructor();

			Object instance = declaredConstructor.newInstance();

			if (!(instance instanceof SourceCheck)) {
				continue;
			}

			SourceCheck sourceCheck = (SourceCheck)instance;

			if ((!portalSource && !subrepository &&
				 sourceCheck.isPortalCheck()) ||
				(!includeModuleChecks && sourceCheck.isModulesCheck())) {

				continue;
			}

			for (String attributeName :
					sourceCheckConfiguration.attributeNames()) {

				BeanUtils.setProperty(
					sourceCheck, attributeName,
					sourceCheckConfiguration.getAttributeValue(attributeName));
			}

			_initSourceCheck(sourceCheck);

			sourceChecks.add(sourceCheck);
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
		String baseDirAbsolutePath = _getAbsolutePath(
			sourceFormatterArgs.getBaseDirName());

		int x = baseDirAbsolutePath.length();

		for (int i = 0; i < 2; i++) {
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

	private String _processFileCheck(
			FileCheck fileCheck, String fileName, String absolutePath,
			String content)
		throws Exception {

		content = fileCheck.process(fileName, absolutePath, content);

		for (SourceFormatterMessage sourceFormatterMessage :
				fileCheck.getSourceFormatterMessages(fileName)) {

			processMessage(fileName, sourceFormatterMessage);
		}

		return content;
	}

	private String _processJavaTermCheck(
			JavaTermCheck javaTermCheck, JavaClass javaClass,
			List<JavaClass> anonymousClasses, String fileName,
			String absolutePath, String content)
		throws Exception {

		content = javaTermCheck.process(
			fileName, absolutePath, javaClass, content);

		for (SourceFormatterMessage sourceFormatterMessage :
				javaTermCheck.getSourceFormatterMessages(fileName)) {

			processMessage(fileName, sourceFormatterMessage);
		}

		for (JavaClass anonymousClass : anonymousClasses) {
			content = javaTermCheck.process(
				fileName, absolutePath, anonymousClass, content);

			for (SourceFormatterMessage sourceFormatterMessage :
					javaTermCheck.getSourceFormatterMessages(fileName)) {

				processMessage(fileName, sourceFormatterMessage);
			}
		}

		return content;
	}

	private static final String _DOCUMENTATION_URL =
		"https://github.com/liferay/liferay-portal/blob/master/modules/util" +
			"/source-formatter/documentation/";

	private List<String> _allFileNames;
	private boolean _browserStarted;
	private String[] _excludes;
	private SourceMismatchException _firstSourceMismatchException;
	private final List<String> _modifiedFileNames =
		new CopyOnWriteArrayList<>();
	private List<String> _pluginsInsideModulesDirectoryNames;
	private Properties _properties;
	private List<SourceCheck> _sourceChecks = new ArrayList<>();
	private Map<String, Set<SourceFormatterMessage>>
		_sourceFormatterMessagesMap = new ConcurrentHashMap<>();

}