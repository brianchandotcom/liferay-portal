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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hugo Huijser
 */
public abstract class BaseSourceCheck implements SourceCheck {

	@Override
	public Set<SourceFormatterMessage> getSourceFormatterMessages(
		String fileName) {

		if (_sourceFormatterMessagesMap.containsKey(fileName)) {
			return _sourceFormatterMessagesMap.get(fileName);
		}

		return Collections.emptySet();
	}

	@Override
	public void init() throws Exception {
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
	}

	@Override
	public void setBaseDirName(String baseDirName) {
		_baseDirName = baseDirName;
	}

	@Override
	public void setExcludes(String[] excludes) {
		_excludes = excludes;
	}

	@Override
	public void setMaxLineLength(int maxLineLength) {
		_maxLineLength = maxLineLength;
	}

	@Override
	public void setPluginsInsideModulesDirectoryNames(
		List<String> pluginsInsideModulesDirectoryNames) {

		_pluginsInsideModulesDirectoryNames =
			pluginsInsideModulesDirectoryNames;
	}

	@Override
	public void setPortalSource(boolean portalSource) {
		_portalSource = portalSource;
	}

	@Override
	public void setProperties(Properties properties) {
		_properties = properties;
	}

	@Override
	public void setSubrepository(boolean subrepository) {
		_subrepository = subrepository;
	}

	protected void addMessage(String fileName, String message) {
		addMessage(fileName, message, -1);
	}

	protected void addMessage(String fileName, String message, int lineCount) {
		addMessage(fileName, message, null, lineCount);
	}

	protected void addMessage(
		String fileName, String message, String markdownFileName) {

		addMessage(fileName, message, markdownFileName, -1);
	}

	protected void addMessage(
		String fileName, String message, String markdownFileName,
		int lineCount) {

		Set<SourceFormatterMessage> sourceFormatterMessages =
			_sourceFormatterMessagesMap.get(fileName);

		if (sourceFormatterMessages == null) {
			sourceFormatterMessages = new TreeSet<>();
		}

		sourceFormatterMessages.add(
			new SourceFormatterMessage(
				fileName, message, markdownFileName, lineCount));

		_sourceFormatterMessagesMap.put(fileName, sourceFormatterMessages);
	}

	protected void clearSourceFormatterMessages(String fileName) {
		_sourceFormatterMessagesMap.remove(fileName);
	}

	protected String getBaseDirName() {
		return _baseDirName;
	}

	protected Map<String, String> getCompatClassNamesMap() throws Exception {
		Map<String, String> compatClassNamesMap = new HashMap<>();

		String[] includes = new String[] {
			"**/portal-compat-shared/src/com/liferay/compat/**/*.java"
		};

		String baseDirName = _baseDirName;

		List<String> fileNames = new ArrayList<>();

		for (int i = 0; i < ToolsUtil.PLUGINS_MAX_DIR_LEVEL; i++) {
			File sharedDir = new File(baseDirName + "shared");

			if (sharedDir.exists()) {
				fileNames = getFileNames(baseDirName, new String[0], includes);

				break;
			}

			baseDirName = baseDirName + "../";
		}

		for (String fileName : fileNames) {
			File file = new File(fileName);

			String content = FileUtil.read(file);

			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			fileName = StringUtil.replace(
				fileName, CharPool.SLASH, CharPool.PERIOD);

			int pos = fileName.indexOf("com.");

			String compatClassName = fileName.substring(pos);

			compatClassName = compatClassName.substring(
				0, compatClassName.length() - 5);

			String extendedClassName = StringUtil.replace(
				compatClassName, "compat.", StringPool.BLANK);

			if (content.contains("extends " + extendedClassName)) {
				compatClassNamesMap.put(compatClassName, extendedClassName);
			}
		}

		return compatClassNamesMap;
	}

	protected String getContent(String fileName, int level) throws Exception {
		File file = getFile(fileName, level);

		if (file != null) {
			String content = FileUtil.read(file);

			if (Validator.isNotNull(content)) {
				return content;
			}
		}

		return StringPool.BLANK;
	}

	protected String[] getExcludes() {
		return _excludes;
	}

	protected File getFile(String fileName, int level) {
		for (int i = 0; i < level; i++) {
			File file = new File(_baseDirName + fileName);

			if (file.exists()) {
				return file;
			}

			fileName = "../" + fileName;
		}

		return null;
	}

	protected List<String> getFileNames(
			String basedir, String[] excludes, String[] includes)
		throws Exception {

		if (_excludes != null) {
			excludes = ArrayUtil.append(excludes, _excludes);
		}

		return SourceFormatterUtil.scanForFiles(
			basedir, excludes, includes, true);
	}

	protected int getLeadingTabCount(String line) {
		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	protected int getLevel(String s) {
		return SourceUtil.getLevel(s);
	}

	protected int getLevel(
		String s, String increaseLevelString, String decreaseLevelString) {

		return SourceUtil.getLevel(s, increaseLevelString, decreaseLevelString);
	}

	protected int getLevel(
		String s, String[] increaseLevelStrings,
		String[] decreaseLevelStrings) {

		return SourceUtil.getLevel(
			s, increaseLevelStrings, decreaseLevelStrings);
	}

	protected int getLevel(
		String s, String[] increaseLevelStrings, String[] decreaseLevelStrings,
		int startLevel) {

		return SourceUtil.getLevel(
			s, increaseLevelStrings, decreaseLevelStrings, startLevel);
	}

	protected int getLineCount(String content, int pos) {
		return StringUtil.count(content, 0, pos, CharPool.NEW_LINE) + 1;
	}

	protected int getMaxLineLength() {
		return _maxLineLength;
	}

	protected List<String> getPluginsInsideModulesDirectoryNames() {
		return _pluginsInsideModulesDirectoryNames;
	}

	protected Properties getPortalLanguageProperties() throws Exception {
		Properties portalLanguageProperties = new Properties();

		File portalLanguagePropertiesFile = getFile(
			"portal-impl/src/content/Language.properties",
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (portalLanguagePropertiesFile != null) {
			InputStream inputStream = new FileInputStream(
				portalLanguagePropertiesFile);

			portalLanguageProperties.load(inputStream);
		}

		return portalLanguageProperties;
	}

	protected String getProjectPathPrefix() throws Exception {
		if (!_subrepository) {
			return null;
		}

		File file = getFile(
			"gradle.properties", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (!file.exists()) {
			return null;
		}

		Properties properties = new Properties();

		properties.load(new FileInputStream(file));

		return properties.getProperty("project.path.prefix");
	}

	protected List<String> getPropertyList(String key) {
		return ListUtil.fromString(
			GetterUtil.getString(_properties.getProperty(key)),
			StringPool.COMMA);
	}

	protected boolean isExcludedPath(String key, String path) {
		return isExcludedPath(key, path, -1);
	}

	protected boolean isExcludedPath(String key, String path, int lineCount) {
		return isExcludedPath(key, path, lineCount, null);
	}

	protected boolean isExcludedPath(
		String key, String path, int lineCount, String parameter) {

		List<String> excludes = getPropertyList(key);

		if (ListUtil.isEmpty(excludes)) {
			return false;
		}

		String pathWithParameter = null;

		if (Validator.isNotNull(parameter)) {
			pathWithParameter = path + StringPool.AT + parameter;
		}

		String pathWithLineCount = null;

		if (lineCount > 0) {
			pathWithLineCount = path + StringPool.AT + lineCount;
		}

		for (String exclude : excludes) {
			if (Validator.isNull(exclude)) {
				continue;
			}

			if (exclude.startsWith("**")) {
				exclude = exclude.substring(2);
			}

			if (exclude.endsWith("**")) {
				exclude = exclude.substring(0, exclude.length() - 2);

				if (path.contains(exclude)) {
					return true;
				}

				continue;
			}

			if (path.endsWith(exclude) ||
				((pathWithParameter != null) &&
				 pathWithParameter.endsWith(exclude)) ||
				((pathWithLineCount != null) &&
				 pathWithLineCount.endsWith(exclude))) {

				return true;
			}
		}

		return false;
	}

	protected boolean isExcludedPath(
		String key, String path, String parameter) {

		return isExcludedPath(key, path, -1, parameter);
	}

	protected boolean isModulesApp(
		String absolutePath, String projectPathPrefix, boolean privateOnly) {

		if (absolutePath.contains("/modules/private/apps/") ||
			(!privateOnly && absolutePath.contains("/modules/apps/"))) {

			return true;
		}

		if (projectPathPrefix == null) {
			return false;
		}

		if (projectPathPrefix.startsWith(":private:apps") ||
			(!privateOnly && projectPathPrefix.startsWith(":apps:"))) {

			return true;
		}

		return false;
	}

	protected boolean isModulesFile(String absolutePath) {
		return isModulesFile(absolutePath, null);
	}

	protected boolean isModulesFile(
		String absolutePath, List<String> pluginsInsideModulesDirectoryNames) {

		if (_subrepository) {
			return true;
		}

		if (pluginsInsideModulesDirectoryNames == null) {
			return absolutePath.contains("/modules/");
		}

		try {
			for (String directoryName : pluginsInsideModulesDirectoryNames) {
				if (absolutePath.contains(directoryName)) {
					return false;
				}
			}
		}
		catch (Exception e) {
		}

		return absolutePath.contains("/modules/");
	}

	protected boolean isPortalSource() {
		return _portalSource;
	}

	protected boolean isSubrepository() {
		return _subrepository;
	}

	protected String stripQuotes(String s) {
		return stripQuotes(s, CharPool.APOSTROPHE, CharPool.QUOTE);
	}

	protected String stripQuotes(String s, char... delimeters) {
		List<Character> delimetersList = ListUtil.toList(delimeters);

		char delimeter = CharPool.SPACE;
		boolean insideQuotes = false;

		StringBundler sb = new StringBundler();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (insideQuotes) {
				if (c == delimeter) {
					int precedingBackSlashCount = 0;

					for (int j = i - 1; j >= 0; j--) {
						if (s.charAt(j) == CharPool.BACK_SLASH) {
							precedingBackSlashCount += 1;
						}
						else {
							break;
						}
					}

					if ((precedingBackSlashCount == 0) ||
						((precedingBackSlashCount % 2) == 0)) {

						insideQuotes = false;
					}
				}
			}
			else if (delimetersList.contains(c)) {
				delimeter = c;
				insideQuotes = true;
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	private String _baseDirName;
	private String[] _excludes;
	private int _maxLineLength;
	private List<String> _pluginsInsideModulesDirectoryNames;
	private boolean _portalSource;
	private Properties _properties;
	private final Map<String, Set<SourceFormatterMessage>>
		_sourceFormatterMessagesMap = new ConcurrentHashMap<>();
	private boolean _subrepository;

}