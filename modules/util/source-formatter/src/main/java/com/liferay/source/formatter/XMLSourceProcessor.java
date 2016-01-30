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
import com.liferay.portal.xml.SAXReaderFactory;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.util.ContentUtil;
import com.liferay.util.xml.Dom4jUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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

	protected void checkOrder(
		String fileName, Element rootElement, String elementName,
		String parentElementName, ElementComparator elementComparator) {

		if (rootElement == null) {
			return;
		}

		List<Element> elements = rootElement.elements(elementName);

		Element previousElement = null;

		for (Element element : elements) {
			if ((previousElement != null) &&
				(elementComparator.compare(previousElement, element) > 0)) {

				StringBundler sb = new StringBundler(8);

				sb.append("order ");
				sb.append(elementName);
				sb.append(": ");
				sb.append(fileName);
				sb.append(StringPool.SPACE);

				if (Validator.isNotNull(parentElementName)) {
					sb.append(parentElementName);
					sb.append(StringPool.SPACE);
				}

				sb.append(elementComparator.getElementName(element));

				processErrorMessage(fileName, sb.toString());
			}

			previousElement = element;
		}
	}

	protected void checkPoshiCharactersAfterDefinition(
		String fileName, String content) {

		if (content.contains("/definition>") &&
			!content.endsWith("/definition>")) {

			processErrorMessage(
				fileName,
				"Characters found after definition element: " + fileName);
		}
	}

	protected void checkPoshiCharactersBeforeDefinition(
		String fileName, String content) {

		if (!content.startsWith("<definition")) {
			processErrorMessage(
				fileName,
				"Characters found before definition element: " + fileName);
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (isExcludedPath(_xmlExcludes, absolutePath)) {
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

			newContent = formatAntXML(fileName, newContent);
		}
		else if (fileName.contains("/custom-sql/")) {
			formatCustomSQLXML(fileName, newContent);
		}
		else if (fileName.endsWith("structures.xml")) {
			newContent = formatDDLStructuresXML(newContent);
		}
		else if (fileName.endsWith("routes.xml")) {
			newContent = formatFriendlyURLRoutesXML(fileName, newContent);
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
			formatResourceActionXML(fileName, newContent);
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
		else if (portalSource && fileName.endsWith("/tiles-defs.xml")) {
			formatTilesDefsXML(fileName, newContent);
		}
		else if ((portalSource &&
				  fileName.endsWith(
					  "portal-web/docroot/WEB-INF/web.xml")) ||
				 (!portalSource && fileName.endsWith("/web.xml"))) {

			newContent = formatWebXML(fileName, newContent);
		}

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

		_numericalPortletNameElementExcludes = getPropertyList(
			"numerical.portlet.name.element.excludes");
		_xmlExcludes = getPropertyList("xml.excludes");

		return getFileNames(excludes, getIncludes());
	}

	protected String fixAntXMLProjectName(String fileName, String content) {
		String baseDirName = sourceFormatterArgs.getBaseDirName();

		int x = baseDirName.length();

		if (fileName.endsWith("-ext/build.xml")) {
			if (fileName.startsWith(baseDirName + "ext/")) {
				x += 4;
			}
		}
		else if (fileName.endsWith("-hook/build.xml")) {
			if (fileName.startsWith(baseDirName + "hooks/")) {
				x += 6;
			}
		}
		else if (fileName.endsWith("-layouttpl/build.xml")) {
			if (fileName.startsWith(baseDirName + "layouttpl/")) {
				x += 10;
			}
		}
		else if (fileName.endsWith("-portlet/build.xml")) {
			if (fileName.startsWith(baseDirName + "portlets/")) {
				x += 9;
			}
		}
		else if (fileName.endsWith("-theme/build.xml")) {
			if (fileName.startsWith(baseDirName + "themes/")) {
				x += 7;
			}
		}
		else if (fileName.endsWith("-web/build.xml") &&
				 !fileName.endsWith("/ext-web/build.xml")) {

			if (fileName.startsWith(baseDirName + "webs/")) {
				x += 5;
			}
		}
		else {
			return content;
		}

		if (content.contains("<project>")) {
			return content;
		}

		int y = fileName.indexOf(CharPool.SLASH, x);

		String correctProjectElementText =
			"<project name=\"" + fileName.substring(x, y) + "\"";

		if (!content.contains(correctProjectElementText)) {
			x = content.indexOf("<project name=\"");

			y = content.indexOf(CharPool.QUOTE, x) + 1;
			y = content.indexOf(CharPool.QUOTE, y) + 1;

			content =
				content.substring(0, x) + correctProjectElementText +
					content.substring(y);

			processErrorMessage(
				fileName, fileName + " has an incorrect project name");
		}

		return content;
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

	protected String formatAntXML(String fileName, String content)
		throws Exception {

		String newContent = trimContent(content, true);

		newContent = fixAntXMLProjectName(fileName, newContent);

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "macrodef", null,
			new ElementComparator());
		checkOrder(
			fileName, document.getRootElement(), "target", null,
			new ElementComparator());

		int x = content.lastIndexOf("</macrodef>");
		int y = content.indexOf("<process-ivy");

		if ((y != -1) && (x > y)) {
			processErrorMessage(
				fileName, "macrodefs go before process-ivy: " + fileName);
		}

		int z = content.indexOf("</target>");

		if ((z != -1) && (x > z)) {
			processErrorMessage(
				fileName, "macrodefs go before targets: " + fileName);
		}

		return newContent;
	}

	protected void formatCustomSQLXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "sql", null,
			new CustomSQLElementComparator());

		Matcher matcher = _whereNotInSQLPattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		int x = content.lastIndexOf("<sql id=", matcher.start());

		int y = content.indexOf(CharPool.QUOTE, x);

		int z = content.indexOf(CharPool.QUOTE, y + 1);

		processErrorMessage(
			fileName,
				"LPS-51315 Avoid using WHERE ... NOT IN: " + fileName + " " +
					content.substring(y + 1, z));
	}

	protected String formatDDLStructuresXML(String content) throws Exception {
		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		sortAttributes(rootElement, true);

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

	protected String formatFriendlyURLRoutesXML(String fileName, String content)
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

		String mainReleaseVersion = getMainReleaseVersion();

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

	protected String formatPortletXML(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		sortAttributes(rootElement, true);

		boolean checkNumericalPortletNameElement = !isExcludedPath(
			_numericalPortletNameElementExcludes, absolutePath);

		List<Element> portletElements = rootElement.elements("portlet");

		for (Element portletElement : portletElements) {
			if (checkNumericalPortletNameElement) {
				Element portletNameElement = portletElement.element(
					"portlet-name");

				String portletNameText = portletNameElement.getText();

				if (!Validator.isNumber(portletNameText)) {
					processErrorMessage(
						fileName,
						fileName +
							" contains a nonstandard portlet-name element " +
								portletNameText);
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

		content = sortPoshiAttributes(fileName, content);

		content = sortPoshiCommands(content);

		content = sortPoshiVariables(content);

		content = fixPoshiXMLElementWithNoChild(content);

		content = fixPoshiXMLEndLinesAfterClosingElement(content);

		content = fixPoshiXMLEndLinesBeforeClosingElement(content);

		content = fixPoshiXMLEndLines(content);

		return fixPoshiXMLNumberOfTabs(content);
	}

	protected void formatResourceActionXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> portletResourceElements = rootElement.elements(
			"portlet-resource");

		for (Element portletResourceElement : portletResourceElements) {
			Element portletNameElement = portletResourceElement.element(
				"portlet-name");

			String portletName = portletNameElement.getText();

			Element permissionsElement = portletResourceElement.element(
				"permissions");

			List<Element> permissionsChildElements =
				permissionsElement.elements();

			for (Element permissionsChildElement : permissionsChildElements) {
				checkOrder(
					fileName, permissionsChildElement, "action-key",
					portletName,
					new ResourceActionActionKeyElementComparator());
			}
		}

		checkOrder(
			fileName, rootElement, "portlet-resource", null,
			new ResourceActionPortletResourceElementComparator());
	}

	protected void formatServiceXML(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> entityElements = rootElement.elements("entity");

		ServiceFinderElementComparator serviceFinderElementComparator =
			new ServiceFinderElementComparator();
		ServiceReferenceElementComparator serviceReferenceElementComparator =
			new ServiceReferenceElementComparator();

		for (Element entityElement : entityElements) {
			String entityName = entityElement.attributeValue("name");

			_columnNames = getColumnNames(fileName, absolutePath, entityName);

			checkOrder(
				fileName, entityElement, "finder", entityName,
				serviceFinderElementComparator);
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
			new StrutsActionElementComparator());
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
				processErrorMessage(fileName, fileName);
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

		x = newContent.indexOf(
			"<web-resource-name>/c/portal/protected</web-resource-name>", x);

		x = newContent.indexOf("<url-pattern>", x) - 3;

		y = newContent.indexOf("<http-method>", x);

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

	protected String getTablesContent(String fileName, String absolutePath)
		throws Exception {

		if (portalSource && !isModulesFile(absolutePath)) {
			if (_tablesContent == null) {
				_tablesContent = getContent(
					"sql/portal-tables.sql",
					BaseSourceProcessor.PORTAL_MAX_DIR_LEVEL);
			}

			return _tablesContent;
		}

		String tablesContent = _tablesContentMap.get(fileName);

		if (tablesContent != null) {
			return tablesContent;
		}

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		String moduleOrPluginFolder = fileName.substring(0, pos);

		if (portalSource) {
			tablesContent = FileUtil.read(
				new File(
					moduleOrPluginFolder +
						"/src/main/resources/META-INF/sql/tables.sql"));

			if (tablesContent == null) {
				tablesContent = FileUtil.read(
					new File(
						moduleOrPluginFolder + "/src/META-INF/sql/tables.sql"));
			}
		}
		else {
			tablesContent = FileUtil.read(
				new File(moduleOrPluginFolder + "/sql/tables.sql"));
		}

		_tablesContentMap.put(fileName, tablesContent);

		return tablesContent;
	}

	protected Document readXML(String content) throws DocumentException {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		return saxReader.read(new UnsyncStringReader(content));
	}

	protected String sortPoshiAttributes(String fileName, String content)
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
						!trimmedLine.startsWith("<%") &&
						!trimmedLine.startsWith("<!")) {

						line = sortAttributes(fileName, line, lineCount, false);
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

	private static final Pattern _commentPattern1 = Pattern.compile(
		">\n\t+<!--[\n ]");
	private static final Pattern _commentPattern2 = Pattern.compile(
		"[\t ]-->\n[\t<]");

	private List<String> _columnNames;
	private List<String> _numericalPortletNameElementExcludes;
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
	private String _solrElementsContent;
	private String _tablesContent;
	private final Map<String, String> _tablesContentMap = new HashMap<>();
	private final Pattern _whereNotInSQLPattern = Pattern.compile(
		"WHERE[ \t\n]+\\(*[a-zA-z0-9.]+ NOT IN");
	private List<String> _xmlExcludes;

	private class CustomSQLElementComparator extends ElementComparator {

		@Override
		public int compare(Element sqlElement1, Element sqlElement2) {
			String sqlElementName1 = getElementName(sqlElement1);
			String sqlElementName2 = getElementName(sqlElement2);

			if ((sqlElementName1 == null) || (sqlElementName2 == null)) {
				return 0;
			}

			return sqlElementName1.compareToIgnoreCase(sqlElementName2);
		}

		@Override
		protected String getElementName(Element element) {
			String elementName = element.attributeValue(getNameAttribute());

			if (Validator.isNull(elementName)) {
				return null;
			}

			int pos = elementName.lastIndexOf(StringPool.PERIOD);

			if (pos == -1) {
				return null;
			}

			return elementName.substring(0, pos);
		}

		@Override
		protected String getNameAttribute() {
			return _NAME_ATTRIBUTE;
		}

		private static final String _NAME_ATTRIBUTE = "id";

	}

	private static class ElementComparator
		extends NaturalOrderStringComparator {

		public int compare(Element element1, Element element2) {
			String elementName1 = getElementName(element1);
			String elementName2 = getElementName(element2);

			return super.compare(elementName1, elementName2);
		}

		protected String getElementName(Element element) {
			return element.attributeValue(getNameAttribute());
		}

		protected String getNameAttribute() {
			return _NAME_ATTRIBUTE;
		}

		private static final String _NAME_ATTRIBUTE = "name";

	}

	private static class ResourceActionActionKeyElementComparator
		extends ElementComparator {

		@Override
		protected String getElementName(Element actionKeyElement) {
			return actionKeyElement.getStringValue();
		}

	}

	private static class ResourceActionPortletResourceElementComparator
		extends ElementComparator {

		@Override
		protected String getElementName(Element portletResourceElement) {
			Element portletNameElement = portletResourceElement.element(
				"portlet-name");

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

	private class ServiceFinderElementComparator extends ElementComparator {

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

	}

	private static class ServiceReferenceElementComparator
		extends ElementComparator {

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

		@Override
		protected String getNameAttribute() {
			return _NAME_ATTRIBUTE;
		}

		private static final String _NAME_ATTRIBUTE = "entity";

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

	private static class StrutsActionElementComparator
		extends ElementComparator {

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

		@Override
		protected String getNameAttribute() {
			return _NAME_ATTRIBUTE;
		}

		private static final String _NAME_ATTRIBUTE = "path";

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

}