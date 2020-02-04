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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterCheckUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
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
	public boolean isEnabled(String absolutePath) {
		return isAttributeValue(
			SourceFormatterCheckUtil.ENABLED_KEY, absolutePath, true);
	}

	@Override
	public boolean isLiferaySourceCheck() {
		return false;
	}

	@Override
	public boolean isModuleSourceCheck() {
		return false;
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
	}

	@Override
	public void setAttributes(String attributes) throws JSONException {
		_attributesJSONObject = new JSONObjectImpl(attributes);
	}

	@Override
	public void setBaseDirName(String baseDirName) {
		_baseDirName = baseDirName;
	}

	@Override
	public void setExcludes(String excludes) throws JSONException {
		_excludesJSONObject = new JSONObjectImpl(excludes);
	}

	@Override
	public void setFileExtensions(List<String> fileExtensions) {
		_fileExtensions = fileExtensions;
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

	protected void addMessage(String fileName, String message, int lineNumber) {
		addMessage(fileName, message, null, lineNumber);
	}

	protected void addMessage(
		String fileName, String message, String markdownFileName) {

		addMessage(fileName, message, markdownFileName, -1);
	}

	protected void addMessage(
		String fileName, String message, String markdownFileName,
		int lineNumber) {

		Set<SourceFormatterMessage> sourceFormatterMessages =
			_sourceFormatterMessagesMap.get(fileName);

		if (sourceFormatterMessages == null) {
			sourceFormatterMessages = new TreeSet<>();
		}

		Class<?> clazz = getClass();

		Class<?> superClass = clazz.getSuperclass();

		sourceFormatterMessages.add(
			new SourceFormatterMessage(
				fileName, message, CheckType.SOURCE_CHECK,
				clazz.getSimpleName(), superClass.getSimpleName(),
				markdownFileName, lineNumber));

		_sourceFormatterMessagesMap.put(fileName, sourceFormatterMessages);
	}

	protected void clearSourceFormatterMessages(String fileName) {
		_sourceFormatterMessagesMap.remove(fileName);
	}

	protected String getAttributeValue(
		String attributeKey, String absolutePath) {

		return getAttributeValue(attributeKey, StringPool.BLANK, absolutePath);
	}

	protected String getAttributeValue(
		String attributeKey, String defaultValue, String absolutePath) {

		return SourceFormatterCheckUtil.getJSONObjectValue(
			_attributesJSONObject, _attributeValueMap, attributeKey,
			defaultValue, absolutePath, _baseDirName);
	}

	protected List<String> getAttributeValues(
		String attributeKey, String absolutePath) {

		return SourceFormatterCheckUtil.getJSONObjectValues(
			_attributesJSONObject, attributeKey, _attributeValuesMap,
			absolutePath, _baseDirName);
	}

	protected String getBaseDirName() {
		return _baseDirName;
	}

	protected BNDSettings getBNDSettings(String fileName) throws IOException {
		String bndFileLocation = fileName;

		while (true) {
			int pos = bndFileLocation.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return null;
			}

			bndFileLocation = bndFileLocation.substring(0, pos + 1);

			BNDSettings bndSettings = _bndSettingsMap.get(bndFileLocation);

			if (bndSettings != null) {
				return bndSettings;
			}

			File file = new File(bndFileLocation + "bnd.bnd");

			if (file.exists()) {
				bndSettings = new BNDSettings(
					bndFileLocation + "bnd.bnd", FileUtil.read(file));

				_bndSettingsMap.put(bndFileLocation, bndSettings);

				return bndSettings;
			}

			bndFileLocation = StringUtil.replaceLast(
				bndFileLocation, CharPool.SLASH, StringPool.BLANK);
		}
	}

	protected String getBuildGradleContent(String absolutePath)
		throws IOException {

		int x = absolutePath.length();

		while (true) {
			x = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

			if (x == -1) {
				return null;
			}

			String buildGradleFileName =
				absolutePath.substring(0, x + 1) + "build.gradle";

			File file = new File(buildGradleFileName);

			if (file.exists()) {
				return FileUtil.read(file);
			}
		}
	}

	protected String getContent(String fileName, int level) throws IOException {
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
		throws DocumentException {

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

	protected List<String> getFileExtensions() {
		return _fileExtensions;
	}

	protected List<String> getFileNames(
			String baseDirName, String[] excludes, String[] includes)
		throws IOException {

		return SourceFormatterUtil.scanForFiles(
			baseDirName, excludes, includes, _sourceFormatterExcludes, true);
	}

	protected String getGitContent(String fileName, String branchName) {
		return SourceFormatterUtil.getGitContent(fileName, branchName);
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
		return ToolsUtil.getLevel(s);
	}

	protected int getLevel(
		String s, String increaseLevelString, String decreaseLevelString) {

		return ToolsUtil.getLevel(s, increaseLevelString, decreaseLevelString);
	}

	protected int getLevel(
		String s, String[] increaseLevelStrings,
		String[] decreaseLevelStrings) {

		return ToolsUtil.getLevel(
			s, increaseLevelStrings, decreaseLevelStrings);
	}

	protected int getLevel(
		String s, String[] increaseLevelStrings, String[] decreaseLevelStrings,
		int startLevel) {

		return ToolsUtil.getLevel(
			s, increaseLevelStrings, decreaseLevelStrings, startLevel);
	}

	protected String getLine(String content, int lineNumber) {
		return SourceUtil.getLine(content, lineNumber);
	}

	protected int getLineNumber(String content, int pos) {
		return SourceUtil.getLineNumber(content, pos);
	}

	protected int getLineStartPos(String content, int lineNumber) {
		return SourceUtil.getLineStartPos(content, lineNumber);
	}

	protected int getMaxLineLength() {
		return _maxLineLength;
	}

	protected String getModulesPropertiesContent(String absolutePath)
		throws IOException {

		if (!isPortalSource()) {
			return getPortalContent(
				_MODULES_PROPERTIES_FILE_NAME, absolutePath);
		}

		return getContent(
			_MODULES_PROPERTIES_FILE_NAME, ToolsUtil.PORTAL_MAX_DIR_LEVEL);
	}

	protected List<String> getPluginsInsideModulesDirectoryNames() {
		return _pluginsInsideModulesDirectoryNames;
	}

	protected String getPortalContent(String fileName, String absolutePath)
		throws IOException {

		return getPortalContent(fileName, absolutePath, false);
	}

	protected String getPortalContent(
			String fileName, String absolutePath, boolean forceRetrieveFromGit)
		throws IOException {

		String portalBranchName = getAttributeValue(
			SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH, absolutePath);

		if (forceRetrieveFromGit) {
			return getGitContent(fileName, portalBranchName);
		}

		String content = getContent(fileName, ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (Validator.isNotNull(content)) {
			return content;
		}

		return getGitContent(fileName, portalBranchName);
	}

	protected synchronized Document getPortalCustomSQLDocument(
			String absolutePath)
		throws DocumentException, IOException {

		if (_portalCustomSQLDocument != null) {
			return _portalCustomSQLDocument;
		}

		_portalCustomSQLDocument = DocumentHelper.createDocument();

		if (!isPortalSource()) {
			return _portalCustomSQLDocument;
		}

		String portalCustomSQLDefaultContent = getPortalContent(
			"portal-impl/src/custom-sql/default.xml", absolutePath);

		if (portalCustomSQLDefaultContent == null) {
			return _portalCustomSQLDocument;
		}

		Element rootElement = _portalCustomSQLDocument.addElement("custom-sql");

		Document customSQLDefaultDocument = SourceUtil.readXML(
			portalCustomSQLDefaultContent);

		Element customSQLDefaultRootElement =
			customSQLDefaultDocument.getRootElement();

		for (Element sqlElement :
				(List<Element>)customSQLDefaultRootElement.elements("sql")) {

			String customSQLFileContent = getPortalContent(
				"portal-impl/src/" + sqlElement.attributeValue("file"),
				absolutePath);

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

		return _portalCustomSQLDocument;
	}

	protected File getPortalDir() {
		File portalImplDir = SourceFormatterUtil.getFile(
			getBaseDirName(), "portal-impl", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (portalImplDir == null) {
			return null;
		}

		return portalImplDir.getParentFile();
	}

	protected InputStream getPortalInputStream(
			String fileName, String absolutePath)
		throws IOException {

		File file = getFile(fileName, ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (file != null) {
			return new FileInputStream(file);
		}

		String portalBranchName = getAttributeValue(
			SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH, absolutePath);

		URL url = SourceFormatterUtil.getPortalGitURL(
			fileName, portalBranchName);

		if (url != null) {
			return url.openStream();
		}

		return null;
	}

	protected String getProjectName() {
		if (_projectName != null) {
			return _projectName;
		}

		if (Validator.isNull(_projectPathPrefix) ||
			!_projectPathPrefix.contains(StringPool.COLON)) {

			_projectName = StringPool.BLANK;

			return _projectName;
		}

		int pos = _projectPathPrefix.lastIndexOf(StringPool.COLON);

		_projectName = _projectPathPrefix.substring(pos + 1);

		return _projectName;
	}

	protected String getProjectPathPrefix() {
		return _projectPathPrefix;
	}

	protected SourceFormatterExcludes getSourceFormatterExcludes() {
		return _sourceFormatterExcludes;
	}

	protected String getVariableTypeName(
		String content, String fileContent, String variableName) {

		return getVariableTypeName(content, fileContent, variableName, false);
	}

	protected String getVariableTypeName(
		String content, String fileContent, String variableName,
		boolean includeArrayOrCollectionTypes) {

		if (variableName == null) {
			return null;
		}

		String variableTypeName = _getVariableTypeName(
			content, variableName, includeArrayOrCollectionTypes);

		if ((variableTypeName != null) || content.equals(fileContent)) {
			return variableTypeName;
		}

		return _getVariableTypeName(
			fileContent, variableName, includeArrayOrCollectionTypes);
	}

	protected boolean isAttributeValue(
		String attributeKey, String absolutePath) {

		return GetterUtil.getBoolean(
			getAttributeValue(attributeKey, absolutePath));
	}

	protected boolean isAttributeValue(
		String attributeKey, String absolutePath, boolean defaultValue) {

		String attributeValue = getAttributeValue(attributeKey, absolutePath);

		if (Validator.isNull(attributeValue)) {
			return defaultValue;
		}

		return GetterUtil.getBoolean(attributeValue);
	}

	protected boolean isExcludedPath(String key, String path) {
		return isExcludedPath(key, path, -1);
	}

	protected boolean isExcludedPath(String key, String path, int lineNumber) {
		return SourceFormatterCheckUtil.isExcludedPath(
			_excludesJSONObject, _excludesValuesMap, key, path, lineNumber,
			null, _baseDirName);
	}

	protected boolean isExcludedPath(
		String key, String path, String parameter) {

		return SourceFormatterCheckUtil.isExcludedPath(
			_excludesJSONObject, _excludesValuesMap, key, path, -1, parameter,
			_baseDirName);
	}

	protected boolean isModulesApp(String absolutePath, boolean privateOnly) {
		if (absolutePath.contains("/modules/dxp/apps") ||
			absolutePath.contains("/modules/private/apps/") ||
			(!privateOnly && absolutePath.contains("/modules/apps/"))) {

			return true;
		}

		if (_projectPathPrefix == null) {
			return false;
		}

		if (_projectPathPrefix.startsWith(":private:apps") ||
			_projectPathPrefix.startsWith(":dxp:apps") ||
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
		catch (Exception exception) {
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
		List<Character> delimetersList = ListUtil.fromArray(delimeters);

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

	private String _getVariableTypeName(
		String content, String variableName,
		boolean includeArrayOrCollectionTypes) {

		Pattern pattern = Pattern.compile(
			"\\W(\\w+)\\s+" + variableName + "\\s*[;=),]");

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		if (!includeArrayOrCollectionTypes) {
			return null;
		}

		pattern = Pattern.compile("[\\]>]\\s+" + variableName + "\\s*[;=),]");

		matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return null;
		}

		int i = matcher.start() + 1;

		for (int j = i - 2; j > 0; j--) {
			if (Character.isLetterOrDigit(content.charAt(j)) ||
				!Character.isLetterOrDigit(content.charAt(j + 1))) {

				continue;
			}

			String typeName = content.substring(j + 1, i);

			if ((getLevel(typeName, "<", ">") == 0) &&
				(getLevel(typeName, "[", "]") == 0)) {

				return typeName;
			}
		}

		return null;
	}

	private static final String _MODULES_PROPERTIES_FILE_NAME =
		"modules/modules.properties";

	private JSONObject _attributesJSONObject = new JSONObjectImpl();
	private final Map<String, String> _attributeValueMap =
		new ConcurrentHashMap<>();
	private final Map<String, List<String>> _attributeValuesMap =
		new ConcurrentHashMap<>();
	private String _baseDirName;
	private final Map<String, BNDSettings> _bndSettingsMap =
		new ConcurrentHashMap<>();
	private JSONObject _excludesJSONObject;
	private final Map<String, List<String>> _excludesValuesMap =
		new ConcurrentHashMap<>();
	private List<String> _fileExtensions;
	private int _maxLineLength;
	private List<String> _pluginsInsideModulesDirectoryNames;
	private Document _portalCustomSQLDocument;
	private boolean _portalSource;
	private String _projectName;
	private String _projectPathPrefix;
	private SourceFormatterExcludes _sourceFormatterExcludes;
	private final Map<String, Set<SourceFormatterMessage>>
		_sourceFormatterMessagesMap = new ConcurrentHashMap<>();
	private boolean _subrepository;

}