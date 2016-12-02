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
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.util.ContentUtil;
import com.liferay.util.xml.Dom4jUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.artifact.versioning.ComparableVersion;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLSourceProcessor extends BaseSourceProcessor {

	public static String formatXML(String content) {
		String newContent = StringUtil.replace(content, "\"/>\n", "\" />\n");

		while (true) {
			Matcher matcher = _commentPattern1.matcher(newContent);

			if (matcher.find()) {
				newContent = StringUtil.replaceFirst(
					newContent, ">\n", ">\n\n", matcher.start());

				continue;
			}

			matcher = _commentPattern2.matcher(newContent);

			if (!matcher.find()) {
				break;
			}

			newContent = StringUtil.replaceFirst(
				newContent, "-->\n", "-->\n\n", matcher.start());
		}

		return newContent;
	}

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
	public String[] getIncludes() {
		return _INCLUDES;
	}

	protected void checkAntXMLProjectName(String fileName, Document document) {
		Matcher matcher = _projectNamePattern.matcher(fileName);

		if (!matcher.find()) {
			return;
		}

		String expectedProjectName = matcher.group(1);

		Element rootElement = document.getRootElement();

		String projectName = rootElement.attributeValue("name");

		if (!projectName.equals(expectedProjectName)) {
			processMessage(
				fileName, "Incorrect project name '" + projectName + "'");
		}
	}

	protected void checkImportFiles(String fileName, String content) {
		Matcher matcher = _importFilePattern.matcher(content);

		while (matcher.find()) {
			String importFileName = fileName;

			int pos = importFileName.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return;
			}

			importFileName = importFileName.substring(0, pos + 1);

			importFileName = importFileName + matcher.group(1);

			File file = new File(importFileName);

			if (!file.exists()) {
				processMessage(
					fileName,
					"Incorrect import file '" + matcher.group(1) + "'");
			}
		}
	}

	protected void checkPoshiCharactersAfterDefinition(
		String fileName, String content) {

		if (content.contains("/definition>") &&
			!content.endsWith("/definition>")) {

			processMessage(
				fileName, "Characters found after definition element");
		}
	}

	protected void checkPoshiCharactersBeforeDefinition(
		String fileName, String content) {

		if (!content.startsWith("<definition")) {
			processMessage(
				fileName, "Characters found before definition element");
		}
	}

	protected void checkTargetName(
			String targetName, String buildFileName, String fileName)
		throws Exception {

		List<String> targetNames = getTargetNames(
			buildFileName, fileName, null, false);

		if ((targetNames == null) || targetNames.contains(targetName)) {
			return;
		}

		int x = targetName.lastIndexOf(CharPool.PERIOD);

		if (x != -1) {
			targetName = targetName.substring(x + 1);
		}

		if (!targetNames.contains(targetName)) {
			processMessage(
				fileName, "Target '" + targetName + "' does not exist");
		}
	}

	protected void checkTargetNames(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> antCallElements = getElementsByName(
			"antcall", rootElement, null);

		for (Element antCallElement : antCallElements) {
			String targetName = antCallElement.attributeValue("target");

			if ((targetName == null) ||
				targetName.contains(StringPool.OPEN_CURLY_BRACE)) {

				continue;
			}

			checkTargetName(targetName, absolutePath, fileName);
		}

		String fileDirName = fileName.substring(
			0, fileName.lastIndexOf(CharPool.SLASH) + 1);

		List<Element> antElements = getElementsByName("ant", rootElement, null);

		for (Element antElement : antElements) {
			String targetName = antElement.attributeValue("target");

			if ((targetName == null) ||
				targetName.contains(StringPool.OPEN_CURLY_BRACE)) {

				continue;
			}

			String fullDirName = fileDirName;

			String dirName = antElement.attributeValue("dir");

			if (dirName != null) {
				if (dirName.contains(StringPool.OPEN_CURLY_BRACE)) {
					continue;
				}

				fullDirName = fullDirName + dirName + StringPool.SLASH;
			}

			String antFileName = antElement.attributeValue("antfile");

			if (antFileName == null) {
				antFileName = "build.xml";
			}

			checkTargetName(targetName, fullDirName + antFileName, fileName);
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (isExcludedPath(_XML_EXCLUDES, absolutePath)) {
			return content;
		}

		String newContent = content;

		if (!fileName.startsWith(
				sourceFormatterArgs.getBaseDirName() + "build") &&
			!fileName.contains("/build")) {

			newContent = trimContent(newContent, false);
		}

		if (fileName.startsWith(
				sourceFormatterArgs.getBaseDirName() + "build") ||
			(fileName.contains("/build") && !fileName.contains("/tools/"))) {

			newContent = formatAntXML(fileName, absolutePath, newContent);
		}
		else if (fileName.contains("/custom-sql/")) {
			formatCustomSQLXML(fileName, newContent);
		}
		else if (fileName.endsWith("structures.xml")) {
			newContent = formatDDLStructuresXML(newContent);
		}
		else if (fileName.endsWith("routes.xml")) {
			newContent = formatFriendlyURLRoutesXML(
				fileName, absolutePath, newContent);
		}
		else if (fileName.endsWith("-hbm.xml")) {
			formatHBMXML(fileName, newContent);
		}
		else if (fileName.endsWith("-log4j.xml")) {
			formatLog4jXML(fileName, newContent);
		}
		else if (fileName.endsWith("-look-and-feel.xml")) {
			formatLookAndFeelXML(fileName, newContent);
		}
		else if (fileName.endsWith("-model-hints.xml")) {
			formatModelHintsXML(fileName, newContent);
		}
		else if (fileName.endsWith("portlet-preferences.xml")) {
			formatPortletPreferencesXML(fileName, newContent);
		}
		else if (fileName.endsWith("/liferay-portlet.xml") ||
				 (portalSource && fileName.endsWith("/portlet-custom.xml")) ||
				 (!portalSource && fileName.endsWith("/portlet.xml"))) {

			newContent = formatPortletXML(fileName, absolutePath, newContent);
		}
		else if (fileName.endsWith(".action") ||
				 fileName.endsWith(".function") ||
				 fileName.endsWith(".macro") ||
				 fileName.endsWith(".testcase")) {

			newContent = formatPoshiXML(fileName, newContent);
		}
		else if (fileName.contains("/resource-actions/")) {
			formatResourceActionXML(fileName, newContent, "model");
			formatResourceActionXML(fileName, newContent, "portlet");
		}
		else if (fileName.endsWith("/service.xml")) {
			formatServiceXML(fileName, absolutePath, newContent);
		}
		else if (fileName.endsWith("/schema.xml") &&
				 absolutePath.contains("solr")) {

			formatSolrSchema(fileName, newContent);
		}
		else if (portalSource && fileName.endsWith("/struts-config.xml")) {
			formatStrutsConfigXML(fileName, newContent);
		}
		else if (portalSource &&
				 fileName.endsWith("/test-ignorable-error-lines.xml")) {

			formatTestIgnorableErrorLinesXml(fileName, newContent);
		}
		else if (portalSource && fileName.endsWith("/tiles-defs.xml")) {
			formatTilesDefsXML(fileName, newContent);
		}
		else if ((portalSource &&
				  fileName.endsWith("portal-web/docroot/WEB-INF/web.xml")) ||
				 (!portalSource && fileName.endsWith("/web.xml"))) {

			newContent = formatWebXML(fileName, newContent);
		}

		newContent = sortAttributes(fileName, newContent);

		newContent = fixEmptyLinesInNestedTags(newContent);

		return formatXML(newContent);
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

	protected String fixPoshiXMLElementWithNoChild(String content) {
		Matcher matcher = _poshiElementWithNoChildPattern.matcher(content);

		while (matcher.find()) {
			content = StringUtil.replace(content, matcher.group(), "\" />");
		}

		return content;
	}

	protected String fixPoshiXMLEndLines(String content) {
		Matcher matcher = _poshiEndLinesPattern.matcher(content);

		while (matcher.find()) {
			String statement = matcher.group();

			String newStatement = StringUtil.replace(
				statement, matcher.group(), ">\n\n" + matcher.group(1));

			content = StringUtil.replace(content, statement, newStatement);
		}

		return content;
	}

	protected String fixPoshiXMLEndLinesAfterClosingElement(String content) {
		Matcher matcher = _poshiEndLinesAfterClosingElementPattern.matcher(
			content);

		while (matcher.find()) {
			String statement = matcher.group();

			String closingElementName = matcher.group(1);

			if (StringUtil.equalsIgnoreCase("</and>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</elseif>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</not>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</or>", closingElementName) ||
				StringUtil.equalsIgnoreCase("</then>", closingElementName)) {

				String newStatement = StringUtil.replace(
					statement, matcher.group(2), "\n");

				content = StringUtil.replace(content, statement, newStatement);
			}
			else if (!StringUtil.equalsIgnoreCase(
						"</var>", closingElementName)) {

				String newStatement = StringUtil.replace(
					statement, matcher.group(2), "\n\n");

				content = StringUtil.replace(content, statement, newStatement);
			}
		}

		return content;
	}

	protected String fixPoshiXMLEndLinesBeforeClosingElement(String content) {
		Matcher matcher = _poshiEndLinesBeforeClosingElementPattern.matcher(
			content);

		while (matcher.find()) {
			String statement = matcher.group();

			String newStatement = StringUtil.replace(
				statement, matcher.group(1), "\n");

			content = StringUtil.replace(content, statement, newStatement);
		}

		return content;
	}

	protected String fixPoshiXMLNumberOfTabs(String content) {
		Matcher matcher = _poshiTabsPattern.matcher(content);

		int tabCount = 0;

		boolean ignoredCdataBlock = false;
		boolean ignoredCommentBlock = false;

		while (matcher.find()) {
			String statement = matcher.group();

			Matcher quoteWithSlashMatcher = _poshiQuoteWithSlashPattern.matcher(
				statement);

			String fixedQuoteStatement = statement;

			if (quoteWithSlashMatcher.find()) {
				fixedQuoteStatement = StringUtil.replace(
					statement, quoteWithSlashMatcher.group(), "\"\"");
			}

			Matcher closingTagMatcher = _poshiClosingTagPattern.matcher(
				fixedQuoteStatement);
			Matcher openingTagMatcher = _poshiOpeningTagPattern.matcher(
				fixedQuoteStatement);
			Matcher wholeTagMatcher = _poshiWholeTagPattern.matcher(
				fixedQuoteStatement);

			if (closingTagMatcher.find() && !openingTagMatcher.find() &&
				!wholeTagMatcher.find() && !statement.contains("<!--") &&
				!statement.contains("-->") &&
				!statement.contains("<![CDATA[") &&
				!statement.contains("]]>")) {

				tabCount--;
			}

			if (statement.contains("]]>")) {
				ignoredCdataBlock = false;
			}
			else if (statement.contains("<![CDATA[")) {
				ignoredCdataBlock = true;
			}

			if (statement.contains("-->")) {
				ignoredCommentBlock = false;
			}
			else if (statement.contains("<!--")) {
				ignoredCommentBlock = true;
			}

			if (!ignoredCommentBlock && !ignoredCdataBlock) {
				StringBundler sb = new StringBundler(tabCount + 1);

				for (int i = 0; i < tabCount; i++) {
					sb.append(StringPool.TAB);
				}

				sb.append(StringPool.LESS_THAN);

				String replacement = sb.toString();

				if (!replacement.equals(matcher.group(1))) {
					String newStatement = StringUtil.replace(
						statement, matcher.group(1), replacement);

					return StringUtil.replaceFirst(
						content, statement, newStatement, matcher.start());
				}
			}

			if (openingTagMatcher.find() && !closingTagMatcher.find() &&
				!wholeTagMatcher.find() && !statement.contains("<!--") &&
				!statement.contains("-->") &&
				!statement.contains("<![CDATA[") &&
				!statement.contains("]]>")) {

				tabCount++;
			}
		}

		return content;
	}

	protected String formatAntXML(
			String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = trimContent(content, true);

		Document document = readXML(content);

		checkAntXMLProjectName(fileName, document);

		checkOrder(
			fileName, document.getRootElement(), "macrodef", null,
			new ElementComparator());
		checkOrder(
			fileName, document.getRootElement(), "target", null,
			new ElementComparator());

		int x = content.lastIndexOf("\n\t</macrodef>");
		int y = content.indexOf("\n\t<process-ivy");

		if ((y != -1) && (x > y)) {
			processMessage(fileName, "Macrodefs go before process-ivy");
		}

		int z = content.indexOf("\n\t</target>");

		if ((z != -1) && (x > z)) {
			processMessage(fileName, "Macrodefs go before targets");
		}

		checkImportFiles(fileName, newContent);

		checkTargetNames(fileName, absolutePath, content);

		return newContent;
	}

	protected void formatCustomSQLXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "sql", null,
			new CustomSQLElementComparator("id"));

		Matcher matcher = _whereNotInSQLPattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		int x = content.lastIndexOf("<sql id=", matcher.start());

		int y = content.indexOf(CharPool.QUOTE, x);

		int z = content.indexOf(CharPool.QUOTE, y + 1);

		processMessage(
			fileName,
			"Avoid using WHERE ... NOT IN: " + content.substring(y + 1, z) +
				", see LPS-51315");
	}

	protected String formatDDLStructuresXML(String content) throws Exception {
		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		sortElementsByChildElement(rootElement, "structure", "name");

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			Element structureRootElement = structureElement.element("root");

			sortElementsByAttribute(
				structureRootElement, "dynamic-element", "name");

			List<Element> dynamicElementElements =
				structureRootElement.elements("dynamic-element");

			for (Element dynamicElementElement : dynamicElementElements) {
				Element metaDataElement = dynamicElementElement.element(
					"meta-data");

				sortElementsByAttribute(metaDataElement, "entry", "name");
			}
		}

		return Dom4jUtil.toString(document);
	}

	protected String formatFriendlyURLRoutesXML(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> routeElements = rootElement.elements("route");

		ElementComparator elementComparator = new ElementComparator();

		for (Element routeElement : routeElements) {
			checkOrder(
				fileName, routeElement, "ignored-parameter", null,
				elementComparator);
			checkOrder(
				fileName, routeElement, "implicit-parameter", null,
				elementComparator);
			checkOrder(
				fileName, routeElement, "overridden-parameter", null,
				elementComparator);
		}

		int pos = content.indexOf("<routes>\n");

		if (pos == -1) {
			return content;
		}

		StringBundler sb = new StringBundler(9);

		ComparableVersion mainReleaseComparableVersion =
			getMainReleaseComparableVersion(fileName, absolutePath, false);

		String mainReleaseVersion = mainReleaseComparableVersion.toString();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE routes PUBLIC \"-//Liferay//DTD Friendly URL ");
		sb.append("Routes ");
		sb.append(mainReleaseVersion);
		sb.append("//EN\" \"http://www.liferay.com/dtd/");
		sb.append("liferay-friendly-url-routes_");
		sb.append(
			StringUtil.replace(
				mainReleaseVersion, StringPool.PERIOD, StringPool.UNDERLINE));
		sb.append(".dtd\">\n\n");
		sb.append(content.substring(pos));

		return sb.toString();
	}

	protected void formatHBMXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "import", null,
			new ElementComparator("class"));
	}

	protected void formatLog4jXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "category", null,
			new ElementComparator(true));
	}

	protected void formatLookAndFeelXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> themeElements = rootElement.elements("theme");

		for (Element themeElement : themeElements) {
			checkOrder(
				fileName, themeElement, "portlet-decorator", null,
				new ElementComparator("id"));

			Element settingsElement = themeElement.element("settings");

			checkOrder(
				fileName, settingsElement, "setting", null,
				new ElementComparator("key"));
		}
	}

	protected void formatModelHintsXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		checkOrder(
			fileName, rootElement, "hint-collection", null,
			new ElementComparator());
		checkOrder(
			fileName, rootElement, "model", null, new ElementComparator());
	}

	protected void formatPortletPreferencesXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "preference", null,
			new PortletPreferenceElementComparator());

		Matcher matcher = _incorrectDefaultPreferencesFileName.matcher(
			fileName);

		if (matcher.find()) {
			String correctFileName =
				matcher.group(1) + "-default-portlet-preferences.xml";

			processMessage(fileName, "Rename file to " + correctFileName);
		}
	}

	protected String formatPortletXML(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		boolean checkNumericalPortletNameElement = !isExcludedPath(
			_NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES, absolutePath);

		List<Element> portletElements = rootElement.elements("portlet");

		for (Element portletElement : portletElements) {
			if (checkNumericalPortletNameElement) {
				Element portletNameElement = portletElement.element(
					"portlet-name");

				String portletNameText = portletNameElement.getText();

				if (!Validator.isNumber(portletNameText)) {
					processMessage(
						fileName,
						"Nonstandard portlet-name element '" + portletNameText +
							"'");
				}
			}

			if (fileName.endsWith("/liferay-portlet.xml")) {
				continue;
			}

			sortElementsByChildElement(portletElement, "init-param", "name");

			Element portletPreferencesElement = portletElement.element(
				"portlet-preferences");

			if (portletPreferencesElement != null) {
				sortElementsByChildElement(
					portletPreferencesElement, "preference", "name");
			}
		}

		return Dom4jUtil.toString(document);
	}

	protected String formatPoshiXML(String fileName, String content)
		throws Exception {

		checkPoshiCharactersAfterDefinition(fileName, content);
		checkPoshiCharactersBeforeDefinition(fileName, content);

		try {
			Document document = readXML(content);

			Element rootElement = document.getRootElement();

			List<Element> commandElements = rootElement.elements("command");

			for (Element commandElement : commandElements) {
				checkOrder(
					fileName, commandElement, "property", null,
					new ElementComparator());
			}
		}
		catch (Exception e) {
		}

		content = sortPoshiCommands(content);
		content = sortPoshiVariables(content);

		content = fixPoshiXMLElementWithNoChild(content);

		content = fixPoshiXMLEndLinesAfterClosingElement(content);

		content = fixPoshiXMLEndLinesBeforeClosingElement(content);

		content = fixPoshiXMLEndLines(content);

		return fixPoshiXMLNumberOfTabs(content);
	}

	protected void formatResourceActionXML(
			String fileName, String content, String type)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> resourceElements = rootElement.elements(
			type + "-resource");

		for (Element resourceElement : resourceElements) {
			Element nameElement = resourceElement.element(type + "-name");

			if (nameElement == null) {
				continue;
			}

			String name = nameElement.getText();

			Element permissionsElement = resourceElement.element("permissions");

			if (permissionsElement == null) {
				continue;
			}

			List<Element> permissionsChildElements =
				permissionsElement.elements();

			for (Element permissionsChildElement : permissionsChildElements) {
				checkOrder(
					fileName, permissionsChildElement, "action-key", name,
					new ResourceActionActionKeyElementComparator());
			}
		}

		checkOrder(
			fileName, rootElement, type + "-resource", null,
			new ResourceActionResourceElementComparator(type + "-name"));
	}

	protected void formatServiceXML(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> entityElements = rootElement.elements("entity");

		ServiceReferenceElementComparator serviceReferenceElementComparator =
			new ServiceReferenceElementComparator("entity");

		for (Element entityElement : entityElements) {
			String entityName = entityElement.attributeValue("name");

			List<String> columnNames = getColumnNames(
				fileName, absolutePath, entityName);

			checkOrder(
				fileName, entityElement, "finder", entityName,
				new ServiceFinderElementComparator(columnNames));
			checkOrder(
				fileName, entityElement, "reference", entityName,
				serviceReferenceElementComparator);
		}

		checkOrder(
			fileName, rootElement, "entity", null, new ElementComparator());
		checkOrder(
			fileName, rootElement.element("exceptions"), "exception", null,
			new ServiceExceptionElementComparator());
	}

	protected void formatSolrSchema(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		SolrElementComparator solrElementComparator =
			new SolrElementComparator();

		Element typesElement = rootElement.element("types");

		_solrElementsContent = typesElement.asXML();

		checkOrder(
			fileName, typesElement, "fieldType", null, solrElementComparator);

		Element fieldsElement = rootElement.element("fields");

		_solrElementsContent = fieldsElement.asXML();

		checkOrder(
			fileName, fieldsElement, "field", null, solrElementComparator);
	}

	protected void formatStrutsConfigXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		checkOrder(
			fileName, rootElement.element("action-mappings"), "action", null,
			new StrutsActionElementComparator("path"));
	}

	protected void formatTestIgnorableErrorLinesXml(
			String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> javascriptElements = rootElement.elements("javascript");

		for (Element javascriptElement : javascriptElements) {
			checkOrder(
				fileName, javascriptElement, "ignore-error", null,
				new ElementComparator("description"));
		}

		List<Element> logElements = rootElement.elements("log");

		for (Element logElement : logElements) {
			checkOrder(
				fileName, logElement, "ignore-error", null,
				new ElementComparator("description"));
		}
	}

	protected void formatTilesDefsXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "definition", null,
			new TilesDefinitionElementComparator());
	}

	protected String formatWebXML(String fileName, String content)
		throws Exception {

		if (!portalSource) {
			String webXML = ContentUtil.get(
				"com/liferay/portal/deploy/dependencies/web.xml");

			if (content.equals(webXML)) {
				processMessage(fileName, StringPool.BLANK);
			}

			return content;
		}

		Properties properties = new Properties();

		File propertiesFile = new File(
			sourceFormatterArgs.getBaseDirName(),
			"portal-impl/src/portal.properties");

		String propertiesContent = FileUtil.read(propertiesFile);

		PropertiesUtil.load(properties, propertiesContent);

		String[] locales = StringUtil.split(
			properties.getProperty(PropsKeys.LOCALES));

		Arrays.sort(locales);

		Set<String> urlPatterns = new TreeSet<>();

		for (String locale : locales) {
			int pos = locale.indexOf(CharPool.UNDERLINE);

			String languageCode = locale.substring(0, pos);

			urlPatterns.add(languageCode);
			urlPatterns.add(locale);
		}

		StringBundler sb = new StringBundler(6 * urlPatterns.size());

		for (String urlPattern : urlPatterns) {
			sb.append("\t<servlet-mapping>\n");
			sb.append("\t\t<servlet-name>I18n Servlet</servlet-name>\n");
			sb.append("\t\t<url-pattern>/");
			sb.append(urlPattern);
			sb.append("/*</url-pattern>\n");
			sb.append("\t</servlet-mapping>\n");
		}

		int x = content.indexOf("<servlet-mapping>");

		x = content.indexOf("<servlet-name>I18n Servlet</servlet-name>", x);

		x = content.lastIndexOf("<servlet-mapping>", x) - 1;

		int y = content.lastIndexOf(
			"<servlet-name>I18n Servlet</servlet-name>");

		y = content.indexOf("</servlet-mapping>", y) + 19;

		String newContent =
			content.substring(0, x) + sb.toString() + content.substring(y);

		x = newContent.indexOf("<security-constraint>");

		x = newContent.indexOf("<web-resource-collection>", x);

		x = newContent.indexOf("<url-pattern>", x) - 3;

		y = newContent.indexOf("</web-resource-collection>", x);

		y = newContent.lastIndexOf("</url-pattern>", y) + 15;

		sb = new StringBundler(3 * urlPatterns.size() + 1);

		sb.append("\t\t\t<url-pattern>/c/portal/protected</url-pattern>\n");

		for (String urlPattern : urlPatterns) {
			sb.append("\t\t\t<url-pattern>/");
			sb.append(urlPattern);
			sb.append("/c/portal/protected</url-pattern>\n");
		}

		return newContent.substring(0, x) + sb.toString() +
			newContent.substring(y);
	}

	protected List<String> getColumnNames(
			String fileName, String absolutePath, String entityName)
		throws Exception {

		List<String> columnNames = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			"create table " + entityName + "_? \\(\n([\\s\\S]*?)\n\\);");

		String tablesContent = getTablesContent(fileName, absolutePath);

		if (tablesContent == null) {
			return columnNames;
		}

		Matcher matcher = pattern.matcher(tablesContent);

		if (!matcher.find()) {
			return columnNames;
		}

		String tableContent = matcher.group(1);

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(tableContent));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = StringUtil.trim(line);

			String columnName = line.substring(0, line.indexOf(CharPool.SPACE));

			columnName = StringUtil.replace(
				columnName, StringPool.UNDERLINE, StringPool.BLANK);

			columnNames.add(columnName);
		}

		return columnNames;
	}

	protected List<Element> getElementsByName(
		String name, Element element, List<Element> elements) {

		if (elements == null) {
			elements = new ArrayList<>();
		}

		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			String elementName = childElement.getName();

			if (elementName.equals(name)) {
				elements.add(childElement);
			}

			elements = getElementsByName(name, childElement, elements);
		}

		return elements;
	}

	protected String getTablesContent(String fileName, String absolutePath)
		throws Exception {

		if (portalSource && !isModulesFile(absolutePath)) {
			if (_tablesContent == null) {
				String tablesContent = getContent(
					"sql/portal-tables.sql", PORTAL_MAX_DIR_LEVEL);

				_tablesContent = tablesContent;
			}

			return _tablesContent;
		}

		String tablesContent = _tablesContentMap.get(fileName);

		if (tablesContent != null) {
			return tablesContent;
		}

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		String moduleOrPluginFolder = fileName.substring(0, pos);

		tablesContent = FileUtil.read(
			new File(
				moduleOrPluginFolder +
					"/src/main/resources/META-INF/sql/tables.sql"));

		if (tablesContent == null) {
			tablesContent = FileUtil.read(
				new File(
					moduleOrPluginFolder + "/src/META-INF/sql/tables.sql"));
		}

		if (tablesContent == null) {
			tablesContent = FileUtil.read(
				new File(moduleOrPluginFolder + "/sql/tables.sql"));
		}

		if (tablesContent != null) {
			_tablesContentMap.put(fileName, tablesContent);
		}

		return tablesContent;
	}

	protected List<String> getTargetNames(
			String buildFileName, String fileName, List<String> targetNames,
			boolean importFile)
		throws Exception {

		if (buildFileName.contains(StringPool.OPEN_CURLY_BRACE)) {
			return null;
		}

		File file = new File(buildFileName);

		if (!file.exists()) {
			if (!importFile) {
				processMessage(
					fileName,
					"Ant element points to non-existing build file '" +
						buildFileName + "'");
			}

			return null;
		}

		Document document = readXML(FileUtil.read(file));

		Element rootElement = document.getRootElement();

		if (targetNames == null) {
			targetNames = new ArrayList<>();
		}

		List<Element> targetElements = rootElement.elements("target");

		for (Element targetElement : targetElements) {
			targetNames.add(targetElement.attributeValue("name"));
		}

		List<Element> importElements = rootElement.elements("import");

		for (Element importElement : importElements) {
			String buildDirName = buildFileName.substring(
				0, buildFileName.lastIndexOf(CharPool.SLASH) + 1);

			String importFileName =
				buildDirName + importElement.attributeValue("file");

			targetNames = getTargetNames(
				importFileName, fileName, targetNames, true);
		}

		return targetNames;
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

	protected String sortPoshiCommands(String content) {
		Matcher matcher = _poshiCommandsPattern.matcher(content);

		Map<String, String> commandBlocksMap = new TreeMap<>(
			String.CASE_INSENSITIVE_ORDER);

		String previousName = StringPool.BLANK;

		boolean hasUnsortedCommands = false;

		while (matcher.find()) {
			String commandBlock = matcher.group();
			String commandName = matcher.group(1);

			commandBlocksMap.put(commandName, commandBlock);

			if (!hasUnsortedCommands &&
				(commandName.compareToIgnoreCase(previousName) < 0)) {

				hasUnsortedCommands = true;
			}

			previousName = commandName;
		}

		if (!hasUnsortedCommands) {
			return content;
		}

		StringBundler sb = new StringBundler();

		matcher = _poshiSetUpPattern.matcher(content);

		if (matcher.find()) {
			String setUpBlock = matcher.group();

			content = content.replace(setUpBlock, "");

			sb.append(setUpBlock);
		}

		matcher = _poshiTearDownPattern.matcher(content);

		if (matcher.find()) {
			String tearDownBlock = matcher.group();

			content = content.replace(tearDownBlock, "");

			sb.append(tearDownBlock);
		}

		for (Map.Entry<String, String> entry : commandBlocksMap.entrySet()) {
			sb.append("\n\t");
			sb.append(entry.getValue());
			sb.append("\n");
		}

		int x = content.indexOf("<command");
		int y = content.lastIndexOf("</command>");

		String commandBlock = content.substring(x, y);

		commandBlock = "\n\t" + commandBlock + "</command>\n";

		String newCommandBlock = sb.toString();

		return StringUtil.replaceFirst(content, commandBlock, newCommandBlock);
	}

	protected String sortPoshiVariables(String content) {
		Matcher matcher = _poshiVariablesBlockPattern.matcher(content);

		while (matcher.find()) {
			String previousName = StringPool.BLANK;
			String tabs = StringPool.BLANK;

			Map<String, String> variableLinesMap = new TreeMap<>(
				String.CASE_INSENSITIVE_ORDER);

			String variableBlock = matcher.group(1);

			variableBlock = variableBlock.trim();

			Matcher variableLineMatcher = _poshiVariableLinePattern.matcher(
				variableBlock);

			boolean hasUnsortedVariables = false;

			while (variableLineMatcher.find()) {
				if (tabs.equals(StringPool.BLANK)) {
					tabs = variableLineMatcher.group(1);
				}

				String variableLine = variableLineMatcher.group(2);
				String variableName = variableLineMatcher.group(3);

				variableLinesMap.put(variableName, variableLine);

				if (!hasUnsortedVariables &&
					(variableName.compareToIgnoreCase(previousName) < 0)) {

					hasUnsortedVariables = true;
				}

				previousName = variableName;
			}

			if (!hasUnsortedVariables) {
				continue;
			}

			StringBundler sb = new StringBundler();

			for (Map.Entry<String, String> entry :
					variableLinesMap.entrySet()) {

				sb.append(tabs);
				sb.append(entry.getValue());
				sb.append("\n");
			}

			String newVariableBlock = sb.toString();

			newVariableBlock = newVariableBlock.trim();

			content = StringUtil.replaceFirst(
				content, variableBlock, newVariableBlock);
		}

		return content;
	}

	private static final String[] _INCLUDES = new String[] {
		"**/*.action", "**/*.function", "**/*.macro", "**/*.testcase",
		"**/*.xml"
	};

	private static final String _NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES =
		"numerical.portlet.name.element.excludes";

	private static final String _XML_EXCLUDES = "xml.excludes";

	private static final Pattern _commentPattern1 = Pattern.compile(
		">\n\t+<!--[\n ]");
	private static final Pattern _commentPattern2 = Pattern.compile(
		"[\t ]-->\n[\t<]");

	private final Pattern _importFilePattern = Pattern.compile(
		"<import file=\"(.*)\"");
	private final Pattern _incorrectDefaultPreferencesFileName =
		Pattern.compile("/default-([\\w-]+)-portlet-preferences\\.xml$");
	private final Pattern _poshiClosingTagPattern = Pattern.compile(
		"</[^>/]*>");
	private final Pattern _poshiCommandsPattern = Pattern.compile(
		"\\<command.*name=\\\"([^\\\"]*)\\\".*\\>[\\s\\S]*?\\</command\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+");
	private final Pattern _poshiElementWithNoChildPattern = Pattern.compile(
		"\\\"[\\s]*\\>[\\n\\s\\t]*\\</[a-z\\-]+>");
	private final Pattern _poshiEndLinesAfterClosingElementPattern =
		Pattern.compile("(\\</[a-z\\-]+>)(\\n+)\\t*\\<[a-z]+");
	private final Pattern _poshiEndLinesBeforeClosingElementPattern =
		Pattern.compile("(\\n+)(\\t*</[a-z\\-]+>)");
	private final Pattern _poshiEndLinesPattern = Pattern.compile(
		"\\>\\n\\n\\n+(\\t*\\<)");
	private final Pattern _poshiOpeningTagPattern = Pattern.compile(
		"<[^/][^>]*[^/]>");
	private final Pattern _poshiQuoteWithSlashPattern = Pattern.compile(
		"\"[^\"]*\\>[^\"]*\"");
	private final Pattern _poshiSetUpPattern = Pattern.compile(
		"\\n[\\t]++\\<set-up\\>([\\s\\S]*?)\\</set-up\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+\\n");
	private final Pattern _poshiTabsPattern = Pattern.compile(
		"\\n*([ \\t]*<).*");
	private final Pattern _poshiTearDownPattern = Pattern.compile(
		"\\n[\\t]++\\<tear-down\\>([\\s\\S]*?)\\</tear-down\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+\\n");
	private final Pattern _poshiVariableLinePattern = Pattern.compile(
		"([\\t]*+)(\\<var.*?name=\\\"([^\\\"]*)\\\".*?/\\>" +
			".*+(?:\\</var\\>)??)");
	private final Pattern _poshiVariablesBlockPattern = Pattern.compile(
		"((?:[\\t]*+\\<var.*?\\>\\n[\\t]*+){2,}?)" +
			"(?:(?:\\n){1,}+|\\</execute\\>)");
	private final Pattern _poshiWholeTagPattern = Pattern.compile(
		"<[^\\>^/]*\\/>");
	private final Pattern _projectNamePattern = Pattern.compile(
		"/(\\w*-(ext|hooks|layouttpl|portlet|theme|web))/build\\.xml$");
	private String _solrElementsContent;
	private String _tablesContent;
	private final Map<String, String> _tablesContentMap =
		new ConcurrentHashMap<>();
	private final Pattern _whereNotInSQLPattern = Pattern.compile(
		"WHERE[ \t\n]+\\(*[a-zA-z0-9.]+ NOT IN");

	private static class PortletPreferenceElementComparator
		extends ElementComparator {

		@Override
		protected String getElementName(Element preferenceElement) {
			Element nameElement = preferenceElement.element(getNameAttribute());

			return nameElement.getStringValue();
		}

	}

	private static class ResourceActionActionKeyElementComparator
		extends ElementComparator {

		@Override
		protected String getElementName(Element actionKeyElement) {
			return actionKeyElement.getStringValue();
		}

	}

	private static class ResourceActionResourceElementComparator
		extends ElementComparator {

		public ResourceActionResourceElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		protected String getElementName(Element portletResourceElement) {
			Element portletNameElement = portletResourceElement.element(
				getNameAttribute());

			if (portletNameElement == null) {
				return null;
			}

			return portletNameElement.getText();
		}

	}

	private static class ServiceExceptionElementComparator
		extends ElementComparator {

		@Override
		protected String getElementName(Element exceptionElement) {
			return exceptionElement.getStringValue();
		}

	}

	private static class ServiceReferenceElementComparator
		extends ElementComparator {

		public ServiceReferenceElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public int compare(
			Element referenceElement1, Element referenceElement2) {

			String packagePath1 = referenceElement1.attributeValue(
				"package-path");
			String packagePath2 = referenceElement2.attributeValue(
				"package-path");

			if (!packagePath1.equals(packagePath2)) {
				return packagePath1.compareToIgnoreCase(packagePath2);
			}

			String entityName1 = referenceElement1.attributeValue("entity");
			String entityName2 = referenceElement2.attributeValue("entity");

			return entityName1.compareToIgnoreCase(entityName2);
		}

	}

	private static class StrutsActionElementComparator
		extends ElementComparator {

		public StrutsActionElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public int compare(Element actionElement1, Element actionElement2) {
			String path1 = actionElement1.attributeValue("path");
			String path2 = actionElement2.attributeValue("path");

			if (!path1.startsWith("/portal/") && path2.startsWith("/portal/")) {
				return 1;
			}

			if (path1.startsWith("/portal/") && !path2.startsWith("/portal/")) {
				return -1;
			}

			return path1.compareTo(path2);
		}

	}

	private static class TilesDefinitionElementComparator
		extends ElementComparator {

		@Override
		public int compare(
			Element definitionElement1, Element definitionElement2) {

			String definitionName1 = getElementName(definitionElement1);

			if (definitionName1.equals("portlet")) {
				return -1;
			}

			return super.compare(definitionElement1, definitionElement2);
		}

	}

	private class CustomSQLElementComparator extends ElementComparator {

		public CustomSQLElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public int compare(Element sqlElement1, Element sqlElement2) {
			String sqlElementName1 = getElementName(sqlElement1);
			String sqlElementName2 = getElementName(sqlElement2);

			String finderObjectName1 = _getFinderObjectName(sqlElementName1);
			String finderObjectName2 = _getFinderObjectName(sqlElementName2);

			if ((finderObjectName1 == null) || (finderObjectName2 == null)) {
				return 0;
			}

			int value = finderObjectName1.compareToIgnoreCase(
				finderObjectName2);

			if (value != 0) {
				return value;
			}

			String finderKeyName1 = _getFinderKeyName(sqlElementName1);
			String finderKeyName2 = _getFinderKeyName(sqlElementName2);

			int startsWithWeight = StringUtil.startsWithWeight(
				finderKeyName1, finderKeyName2);

			if (startsWithWeight == 0) {
				return finderKeyName1.compareTo(finderKeyName2);
			}

			String startFinder = finderKeyName1.substring(0, startsWithWeight);

			if (!startFinder.contains("By")) {
				NaturalOrderStringComparator comparator =
					new NaturalOrderStringComparator();

				return comparator.compare(finderKeyName1, finderKeyName2);
			}

			int columnCount1 = StringUtil.count(
				sqlElementName1, CharPool.UNDERLINE);
			int columnCount2 = StringUtil.count(
				sqlElementName2, CharPool.UNDERLINE);

			return columnCount1 - columnCount2;
		}

		private String _getFinderKeyName(String elementName) {
			if (Validator.isNull(elementName)) {
				return null;
			}

			int pos = elementName.lastIndexOf(StringPool.PERIOD);

			if (pos == -1) {
				return null;
			}

			return elementName.substring(pos + 1);
		}

		private String _getFinderObjectName(String elementName) {
			if (Validator.isNull(elementName)) {
				return null;
			}

			int pos = elementName.lastIndexOf(StringPool.PERIOD);

			if (pos == -1) {
				return null;
			}

			return elementName.substring(0, pos);
		}

	}

	private class ServiceFinderElementComparator extends ElementComparator {

		public ServiceFinderElementComparator(List<String> columnNames) {
			_columnNames = columnNames;
		}

		@Override
		public int compare(Element finderElement1, Element finderElement2) {
			List<Element> finderColumnElements1 = finderElement1.elements(
				"finder-column");
			List<Element> finderColumnElements2 = finderElement2.elements(
				"finder-column");

			int finderColumnCount1 = finderColumnElements1.size();
			int finderColumnCount2 = finderColumnElements2.size();

			if (finderColumnCount1 != finderColumnCount2) {
				return finderColumnCount1 - finderColumnCount2;
			}

			for (int i = 0; i < finderColumnCount1; i++) {
				Element finderColumnElement1 = finderColumnElements1.get(i);
				Element finderColumnElement2 = finderColumnElements2.get(i);

				String finderColumnName1 = finderColumnElement1.attributeValue(
					"name");
				String finderColumnName2 = finderColumnElement2.attributeValue(
					"name");

				int index1 = _columnNames.indexOf(finderColumnName1);
				int index2 = _columnNames.indexOf(finderColumnName2);

				if (index1 != index2) {
					return index1 - index2;
				}
			}

			String finderName1 = finderElement1.attributeValue("name");
			String finderName2 = finderElement2.attributeValue("name");

			int startsWithWeight = StringUtil.startsWithWeight(
				finderName1, finderName2);

			String strippedFinderName1 = finderName1.substring(
				startsWithWeight);
			String strippedFinderName2 = finderName2.substring(
				startsWithWeight);

			if (strippedFinderName1.startsWith("Gt") ||
				strippedFinderName1.startsWith("Like") ||
				strippedFinderName1.startsWith("Lt") ||
				strippedFinderName1.startsWith("Not")) {

				if (!strippedFinderName2.startsWith("Gt") &&
					!strippedFinderName2.startsWith("Like") &&
					!strippedFinderName2.startsWith("Lt") &&
					!strippedFinderName2.startsWith("Not")) {

					return 1;
				}
				else {
					return strippedFinderName1.compareTo(strippedFinderName2);
				}
			}

			return 0;
		}

		private final List<String> _columnNames;

	}

	private class SolrElementComparator extends ElementComparator {

		@Override
		public int compare(Element solrElement1, Element solrElement2) {
			int value = super.compare(solrElement1, solrElement2);

			if (value <= 0) {
				return value;
			}

			String solrElementContent1 = solrElement1.asXML();
			String solrElementContent2 = solrElement2.asXML();

			int x =
				_solrElementsContent.indexOf(solrElementContent1) +
					solrElementContent1.length();
			int y = _solrElementsContent.indexOf(solrElementContent2);

			if (x > y) {
				return -1;
			}

			String betweenElementsContent = _solrElementsContent.substring(
				x, y);

			if (betweenElementsContent.contains("<!--")) {
				return -1;
			}

			return 1;
		}

	}

}