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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.XMLBuildFileCheck;
import com.liferay.source.formatter.checks.XMLCustomSQLFileCheck;
import com.liferay.source.formatter.checks.XMLDDLStructuresFileCheck;
import com.liferay.source.formatter.checks.XMLEmptyLinesCheck;
import com.liferay.source.formatter.checks.XMLFriendlyURLRoutesFileCheck;
import com.liferay.source.formatter.checks.XMLHBMFileCheck;
import com.liferay.source.formatter.checks.XMLLog4jFileCheck;
import com.liferay.source.formatter.checks.XMLLookAndFeelFileCheck;
import com.liferay.source.formatter.checks.XMLModelHintsFileCheck;
import com.liferay.source.formatter.checks.XMLPortletFileCheck;
import com.liferay.source.formatter.checks.XMLPortletPreferencesFileCheck;
import com.liferay.source.formatter.checks.XMLPoshiFileCheck;
import com.liferay.source.formatter.checks.XMLResourceActionsFileCheck;
import com.liferay.source.formatter.checks.XMLServiceFileCheck;
import com.liferay.source.formatter.checks.XMLSolrSchemaFileCheck;
import com.liferay.source.formatter.checks.XMLSpringFileCheck;
import com.liferay.source.formatter.checks.XMLStrutsConfigFileCheck;
import com.liferay.source.formatter.checks.XMLTestIgnorableErrorLinesFileCheck;
import com.liferay.source.formatter.checks.XMLTilesDefsFileCheck;
import com.liferay.source.formatter.checks.XMLToggleFileCheck;
import com.liferay.source.formatter.checks.XMLWebFileCheck;
import com.liferay.source.formatter.checks.XMLWhitespaceCheck;
import com.liferay.util.xml.Dom4jUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLSourceProcessor extends BaseSourceProcessor {

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static void sortAttributes(Element element, boolean recursive) {
		Map<String, Attribute> attributesMap = new TreeMap<>();

		List<Attribute> attributes = new ArrayList<>(element.attributes());

		for (Attribute attribute : attributes) {
			attribute.detach();

			attributesMap.put(attribute.getName(), attribute);
		}

		for (Map.Entry<String, Attribute> entry : attributesMap.entrySet()) {
			Attribute attribute = entry.getValue();

			element.add(attribute);
		}

		if (!recursive) {
			return;
		}

		List<Element> elements = element.elements();

		for (Element curElement : elements) {
			sortAttributes(curElement, recursive);
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String sortAttributes(String content) throws Exception {
		XMLSourceProcessor xmlSourceProcessor = new XMLSourceProcessor();

		Document document = xmlSourceProcessor.readXML(content);

		sortAttributes(document.getRootElement(), true);

		return Dom4jUtil.toString(document);
	}

	public static void sortElementsByAttribute(
		Element element, String elementName, String attributeName) {

		Map<String, Element> elementsMap = new TreeMap<>();

		List<Element> elements = element.elements();

		for (Element curElement : elements) {
			curElement.detach();

			if (elementName.equals(curElement.getName())) {
				String attributeValue = curElement.attributeValue(
					attributeName);

				elementsMap.put(attributeValue, curElement);
			}
		}

		for (Element curElement : elements) {
			if (elementName.equals(curElement.getName())) {
				break;
			}

			element.add(curElement);
		}

		for (Map.Entry<String, Element> entry : elementsMap.entrySet()) {
			Element curElement = entry.getValue();

			element.add(curElement);
		}

		boolean foundLastElementWithElementName = false;

		for (int i = 0; i < elements.size(); i++) {
			Element curElement = elements.get(i);

			if (!foundLastElementWithElementName) {
				if (elementName.equals(curElement.getName()) &&
					((i + 1) < elements.size())) {

					Element nextElement = elements.get(i + 1);

					if (!elementName.equals(nextElement.getName())) {
						foundLastElementWithElementName = true;
					}
				}
			}
			else {
				element.add(curElement);
			}
		}
	}

	public static void sortElementsByChildElement(
		Element element, String elementName, String childElementName) {

		Map<String, Element> elementsMap = new TreeMap<>();

		List<Element> elements = element.elements();

		for (Element curElement : elements) {
			curElement.detach();

			if (elementName.equals(curElement.getName())) {
				String childElementValue = curElement.elementText(
					childElementName);

				elementsMap.put(childElementValue, curElement);
			}
		}

		for (Element curElement : elements) {
			if (elementName.equals(curElement.getName())) {
				break;
			}

			element.add(curElement);
		}

		for (Map.Entry<String, Element> entry : elementsMap.entrySet()) {
			Element curElement = entry.getValue();

			element.add(curElement);
		}

		boolean foundLastElementWithElementName = false;

		for (int i = 0; i < elements.size(); i++) {
			Element curElement = elements.get(i);

			if (!foundLastElementWithElementName) {
				if (elementName.equals(curElement.getName()) &&
					((i + 1) < elements.size())) {

					Element nextElement = elements.get(i + 1);

					if (!elementName.equals(nextElement.getName())) {
						foundLastElementWithElementName = true;
					}
				}
			}
			else {
				element.add(curElement);
			}
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		return sortAttributes(fileName, content);
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {
			"**/.bnd/**", "**/.idea/**", "**/.ivy/**", "**/bin/**",
			"**/javadocs-*.xml", "**/logs/**", "**/portal-impl/**/*.action",
			"**/portal-impl/**/*.function", "**/portal-impl/**/*.macro",
			"**/portal-impl/**/*.testcase", "**/src/test/**",
			"**/test-classes/unit/**", "**/test-results/**", "**/test/unit/**",
			"**/tools/node**"
		};

		return getFileNames(excludes, getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		List<FileCheck> fileChecks = new ArrayList<>();

		fileChecks.add(
			new XMLWhitespaceCheck(sourceFormatterArgs.getBaseDirName()));
		fileChecks.add(new XMLCustomSQLFileCheck());
		fileChecks.add(new XMLDDLStructuresFileCheck());
		fileChecks.add(new XMLFriendlyURLRoutesFileCheck());
		fileChecks.add(new XMLHBMFileCheck());
		fileChecks.add(new XMLLog4jFileCheck());
		fileChecks.add(new XMLLookAndFeelFileCheck());
		fileChecks.add(new XMLModelHintsFileCheck());
		fileChecks.add(
			new XMLPortletFileCheck(
				_numericalPortletNameElementExcludes, portalSource,
				subrepository));
		fileChecks.add(new XMLPortletPreferencesFileCheck());
		fileChecks.add(new XMLPoshiFileCheck());
		fileChecks.add(new XMLResourceActionsFileCheck());
		fileChecks.add(
			new XMLServiceFileCheck(
				_serviceFinderColumnSortExcludes, portalSource, subrepository,
				_portalTablesContent, _pluginsInsideModulesDirectoryNames));
		fileChecks.add(new XMLSolrSchemaFileCheck());
		fileChecks.add(new XMLSpringFileCheck());
		fileChecks.add(new XMLToggleFileCheck());

		fileChecks.add(
			new XMLBuildFileCheck(sourceFormatterArgs.getBaseDirName()));

		if (portalSource || subrepository) {
			fileChecks.add(
				new XMLEmptyLinesCheck(sourceFormatterArgs.getBaseDirName()));

			fileChecks.add(new XMLStrutsConfigFileCheck());
			fileChecks.add(new XMLTestIgnorableErrorLinesFileCheck());
			fileChecks.add(new XMLTilesDefsFileCheck());
			fileChecks.add(
				new XMLWebFileCheck(sourceFormatterArgs.getBaseDirName()));
		}

		return fileChecks;
	}

	@Override
	protected void preFormat() throws Exception {
		_numericalPortletNameElementExcludes = getExcludes(
			_NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES);
		_serviceFinderColumnSortExcludes = getExcludes(
			_SERVICE_FINDER_COLUMN_SORT_EXCLUDES);

		if (portalSource) {
			_portalTablesContent = getContent(
				"sql/portal-tables.sql", PORTAL_MAX_DIR_LEVEL);
			_pluginsInsideModulesDirectoryNames =
				_getPluginsInsideModulesDirectoryNames();
		}
	}

	protected String sortAttributes(String fileName, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			int lineCount = 0;

			boolean sortAttributes = true;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				String trimmedLine = StringUtil.trimLeading(line);

				if (sortAttributes) {
					if (trimmedLine.startsWith(StringPool.LESS_THAN) &&
						trimmedLine.endsWith(StringPool.GREATER_THAN) &&
						!trimmedLine.startsWith("<?") &&
						!trimmedLine.startsWith("<%") &&
						!trimmedLine.startsWith("<!") &&
						!(line.contains("<![CDATA[") && line.contains("]]>"))) {

						line = formatAttributes(
							fileName, line, trimmedLine, lineCount, true);
					}
					else if (trimmedLine.startsWith("<![CDATA[") &&
							 !trimmedLine.endsWith("]]>")) {

						sortAttributes = false;
					}
				}
				else if (trimmedLine.endsWith("]]>")) {
					sortAttributes = true;
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	private List<String> _getPluginsInsideModulesDirectoryNames()
		throws Exception {

		List<String> pluginsInsideModulesDirectoryNames = new ArrayList<>();

		List<String> pluginBuildFileNames = getFileNames(
			new String[0],
			new String[] {
				"**/modules/apps/**/build.xml",
				"**/modules/private/apps/**/build.xml"
			});

		for (String pluginBuildFileName : pluginBuildFileNames) {
			pluginBuildFileName = StringUtil.replace(
				pluginBuildFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String absolutePath = getAbsolutePath(pluginBuildFileName);

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

	private static final String[] _INCLUDES = new String[] {
		"**/*.action", "**/*.function", "**/*.jrxml", "**/*.macro",
		"**/*.testcase", "**/*.toggle", "**/*.xml"
	};

	private static final String _NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES =
		"numerical.portlet.name.element.excludes";

	private static final String _SERVICE_FINDER_COLUMN_SORT_EXCLUDES =
		"service.finder.column.sort.excludes";

	private List<String> _numericalPortletNameElementExcludes;
	private List<String> _pluginsInsideModulesDirectoryNames;
	private String _portalTablesContent;
	private List<String> _serviceFinderColumnSortExcludes;

}