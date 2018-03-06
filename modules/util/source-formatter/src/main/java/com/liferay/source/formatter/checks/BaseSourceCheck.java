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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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
	public boolean isModulesCheck() {
		return false;
	}

	@Override
	public boolean isPortalCheck() {
		return false;
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
	}

	@Override
	public void setBaseDirName(String baseDirName) {
		_baseDirName = baseDirName;
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
	public void setProjectPathPrefix(String projectPathPrefix) {
		_projectPathPrefix = projectPathPrefix;
	}

	@Override
	public void setPropertiesMap(Map<String, Properties> propertiesMap) {
		_propertiesMap = propertiesMap;
	}

	@Override
	public void setSourceFormatterExcludes(
		SourceFormatterExcludes sourceFormatterExcludes) {

		_sourceFormatterExcludes = sourceFormatterExcludes;
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

		Class<?> clazz = getClass();

		sourceFormatterMessages.add(
			new SourceFormatterMessage(
				fileName, message, CheckType.SOURCE_CHECK,
				clazz.getSimpleName(), markdownFileName, lineCount));

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

		String[] includes =
			{"**/portal-compat-shared/src/com/liferay/compat/**/*.java"};

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

	protected Document getCustomSQLDocument(
			String fileName, String absolutePath,
			Document portalCustomSQLDocument)
		throws Exception {

		if (isPortalSource() && !isModulesFile(absolutePath)) {
			return portalCustomSQLDocument;
		}

		int i = fileName.lastIndexOf("/src/");

		if (i == -1) {
			return null;
		}

		File customSQLFile = new File(
			fileName.substring(0, i) + "/src/custom-sql/default.xml");

		if (!customSQLFile.exists()) {
			customSQLFile = new File(
				fileName.substring(0, i) +
					"/src/main/resources/META-INF/custom-sql/default.xml");
		}

		if (!customSQLFile.exists()) {
			customSQLFile = new File(
				fileName.substring(0, i) +
					"/src/main/resources/custom-sql/default.xml");
		}

		if (!customSQLFile.exists()) {
			return null;
		}

		return SourceUtil.readXML(customSQLFile);
	}

	protected File getFile(String fileName, int level) {
		return SourceFormatterUtil.getFile(_baseDirName, fileName, level);
	}

	protected List<String> getFileNames(
			String basedir, String[] excludes, String[] includes)
		throws Exception {

		return SourceFormatterUtil.scanForFiles(
			basedir, excludes, includes, _sourceFormatterExcludes, true);
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

	protected String getLine(String content, int lineCount) {
		int nextLineStartPos = getLineStartPos(content, lineCount);

		if (nextLineStartPos == -1) {
			return null;
		}

		int nextLineEndPos = content.indexOf(
			CharPool.NEW_LINE, nextLineStartPos);

		if (nextLineEndPos == -1) {
			return content.substring(nextLineStartPos);
		}

		return content.substring(nextLineStartPos, nextLineEndPos);
	}

	protected int getLineCount(String content, int pos) {
		return StringUtil.count(content, 0, pos, CharPool.NEW_LINE) + 1;
	}

	protected int getLineStartPos(String content, int lineCount) {
		int x = 0;

		for (int i = 1; i < lineCount; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
	}

	protected int getMaxLineLength() {
		return _maxLineLength;
	}

	protected List<String> getPluginsInsideModulesDirectoryNames() {
		return _pluginsInsideModulesDirectoryNames;
	}

	protected String getPortalContent(String fileName) throws Exception {
		String content = getContent(fileName, ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (Validator.isNotNull(content)) {
			return content;
		}

		String portalBranchName = _getPortalBranchName();

		if (portalBranchName == null) {
			return null;
		}

		try {
			URL url = new URL(
				StringBundler.concat(
					_GIT_LIFERAY_PORTAL_URL, portalBranchName, StringPool.SLASH,
					fileName));

			return StringUtil.read(url.openStream());
		}
		catch (Exception e) {
			return null;
		}
	}

	protected Document getPortalCustomSQLDocument() throws Exception {
		if (!isPortalSource()) {
			return null;
		}

		String portalCustomSQLDefaultContent = getPortalContent(
			"portal-impl/src/custom-sql/default.xml");

		if (portalCustomSQLDefaultContent == null) {
			return null;
		}

		Document document = DocumentHelper.createDocument();

		Element rootElement = document.addElement("custom-sql");

		Document customSQLDefaultDocument = SourceUtil.readXML(
			portalCustomSQLDefaultContent);

		Element customSQLDefaultRootElement =
			customSQLDefaultDocument.getRootElement();

		for (Element sqlElement :
				(List<Element>)customSQLDefaultRootElement.elements("sql")) {

			String customSQLFileContent = getPortalContent(
				"portal-impl/src/" + sqlElement.attributeValue("file"));

			if (customSQLFileContent == null) {
				continue;
			}

			Document customSQLDocument = SourceUtil.readXML(
				customSQLFileContent);

			Element customSQLRootElement = customSQLDocument.getRootElement();

			for (Element customSQLElement :
					(List<Element>)customSQLRootElement.elements("sql")) {

				rootElement.add(customSQLElement.detach());
			}
		}

		return document;
	}

	protected File getPortalDir() {
		File portalImplDir = SourceFormatterUtil.getFile(
			getBaseDirName(), "portal-impl", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (portalImplDir == null) {
			return null;
		}

		return portalImplDir.getParentFile();
	}

	protected String getProjectPathPrefix() {
		return _projectPathPrefix;
	}

	protected Map<String, Properties> getPropertiesMap() {
		return _propertiesMap;
	}

	protected SourceFormatterExcludes getSourceFormatterExcludes() {
		return _sourceFormatterExcludes;
	}

	protected boolean isExcludedPath(
		Properties properties, String key, String path, int lineCount,
		String parameter) {

		List<String> excludes = ListUtil.fromString(
			GetterUtil.getString(properties.getProperty(key)),
			StringPool.COMMA);

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

	protected boolean isExcludedPath(String key, String path) {
		return isExcludedPath(key, path, -1);
	}

	protected boolean isExcludedPath(String key, String path, int lineCount) {
		return isExcludedPath(key, path, lineCount, null);
	}

	protected boolean isExcludedPath(
		String key, String path, int lineCount, String parameter) {

		for (Map.Entry<String, Properties> entry : _propertiesMap.entrySet()) {
			String propertiesFileLocation = entry.getKey();

			if (path.startsWith(propertiesFileLocation) &&
				isExcludedPath(
					entry.getValue(), key, path, lineCount, parameter)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isExcludedPath(
		String key, String path, String parameter) {

		return isExcludedPath(key, path, -1, parameter);
	}

	protected boolean isModulesApp(String absolutePath, boolean privateOnly) {
		if (absolutePath.contains("/modules/private/apps/") ||
			(!privateOnly && absolutePath.contains("/modules/apps/"))) {

			return true;
		}

		if (_projectPathPrefix == null) {
			return false;
		}

		if (_projectPathPrefix.startsWith(":private:apps") ||
			(!privateOnly && _projectPathPrefix.startsWith(":apps:"))) {

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

	protected boolean isReadOnly(String absolutePath) {

		// This method should only be called temporarily by checks with new
		// logic. After all source in all subrepositories has been changed
		// according to new formatting rules, the call should be reverted.

		for (String readOnlyDirName : _readOnlyDirNames) {
			if (absolutePath.contains(readOnlyDirName)) {
				return true;
			}
		}

		return false;
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

	protected static final String RUN_OUTSIDE_PORTAL_EXCLUDES =
		"run.outside.portal.excludes";

	private String _getPortalBranchName() {
		for (Map.Entry<String, Properties> entry : _propertiesMap.entrySet()) {
			Properties properties = entry.getValue();

			String portalBranchName = properties.getProperty(
				_GIT_LIFERAY_PORTAL_BRANCH);

			if (portalBranchName != null) {
				return portalBranchName;
			}
		}

		return null;
	}

	private static final String _GIT_LIFERAY_PORTAL_BRANCH =
		"git.liferay.portal.branch";

	private static final String _GIT_LIFERAY_PORTAL_URL =
		"https://raw.githubusercontent.com/liferay/liferay-portal/";

	private static final List<String> _readOnlyDirNames = Arrays.asList(
		"/modules/apps/adaptive-media/", "/modules/apps/analytics/",
		"/modules/apps/forms-and-workflow/dynamic-data-mapping/",
		"/modules/apps/forms-and-workflow/portal-workflow/",
		"/modules/apps/foundation/vulcan/",
		"/modules/apps/web-experience/journal/", "/modules/private/apps/");

	private String _baseDirName;
	private int _maxLineLength;
	private List<String> _pluginsInsideModulesDirectoryNames;
	private boolean _portalSource;
	private String _projectPathPrefix;
	private Map<String, Properties> _propertiesMap;
	private SourceFormatterExcludes _sourceFormatterExcludes;
	private final Map<String, Set<SourceFormatterMessage>>
		_sourceFormatterMessagesMap = new ConcurrentHashMap<>();
	private boolean _subrepository;

}