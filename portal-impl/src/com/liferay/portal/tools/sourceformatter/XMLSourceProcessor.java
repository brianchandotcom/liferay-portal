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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.tools.ComparableRoute;
import com.liferay.util.ContentUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	protected void checkPoshiCharactersAfterDefinition(
		String fileName, String content) {

		Matcher matcher = _poshiCharactersAfterDefinitionTag.matcher(content);

		if (matcher.find()) {
			processErrorMessage(
				fileName,
				"Characters found after definition element: " + fileName);
		}
	}

	protected void checkPoshiCharactersBeforeDefinition(
		String fileName, String content) {

		Matcher matcher = _poshiCharactersBeforeDefinitionTag.matcher(content);

		if (matcher.find()) {
			processErrorMessage(
				fileName,
				"Characters found before definition element: " + fileName);
		}
	}

	protected void checkServiceXMLExceptions(
		String fileName, Element rootElement) {

		Element exceptionsElement = rootElement.element("exceptions");

		if (exceptionsElement == null) {
			return;
		}

		List<Element> exceptionElements = exceptionsElement.elements(
			"exception");

		String previousException = StringPool.BLANK;

		for (Element exceptionElement : exceptionElements) {
			String exception = exceptionElement.getStringValue();

			if (Validator.isNotNull(previousException) &&
				(previousException.compareToIgnoreCase(exception) > 0)) {

				processErrorMessage(
					fileName, "sort: " + fileName + " " + exception);
			}

			previousException = exception;
		}
	}

	protected void checkServiceXMLFinders(
			String fileName, Element entityElement, String entityName)
		throws Exception {

		_columnNames = getColumnNames(fileName, entityName);

		FinderElementComparator finderElementComparator =
			new FinderElementComparator();

		List<Element> finderElements = entityElement.elements("finder");

		for (int i = 1; i < finderElements.size(); i++) {
			Element finderElement = finderElements.get(i);
			Element previousFinderElement = finderElements.get(i - 1);

			if (finderElementComparator.compare(
					previousFinderElement, finderElement) > 0) {

				String finderName = finderElement.attributeValue("name");

				processErrorMessage(
					fileName,
					"order: " + fileName + " " + entityName + " " + finderName);
			}
		}
	}

	protected void checkServiceXMLReferences(
		String fileName, Element entityElement, String entityName) {

		String previousReferenceEntity = StringPool.BLANK;
		String previousReferencePackagePath = StringPool.BLANK;

		List<Element> referenceElements = entityElement.elements("reference");

		for (Element referenceElement : referenceElements) {
			String referenceEntity = referenceElement.attributeValue("entity");
			String referencePackagePath = referenceElement.attributeValue(
				"package-path");

			if (Validator.isNotNull(previousReferencePackagePath)) {
				if ((previousReferencePackagePath.compareToIgnoreCase(
						referencePackagePath) > 0) ||
					(previousReferencePackagePath.equals(
						referencePackagePath) &&
					 (previousReferenceEntity.compareToIgnoreCase(
						 referenceEntity) > 0))) {

					processErrorMessage(
						fileName,
						"sort: " + fileName + " " + entityName + " " +
							referenceEntity);
				}
			}

			previousReferenceEntity = referenceEntity;
			previousReferencePackagePath = referencePackagePath;
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (isExcluded(_xmlExclusions, absolutePath)) {
			return content;
		}

		String newContent = content;

		if (!fileName.contains("/build")) {
			newContent = trimContent(newContent, false);
		}

		if (fileName.contains("/build") && !fileName.contains("/tools/")) {
			newContent = formatAntXML(fileName, newContent);
		}
		else if (fileName.contains("/custom-sql/")) {
			formatCustomSQLXML(fileName, newContent);
		}
		else if (fileName.endsWith("structures.xml")) {
			newContent = formatDDLStructuresXML(newContent);
		}
		else if (fileName.endsWith("routes.xml")) {
			newContent = formatFriendlyURLRoutesXML(absolutePath, newContent);
		}
		else if (fileName.endsWith("/liferay-portlet.xml") ||
				 (portalSource &&
				  fileName.endsWith("/portlet-custom.xml")) ||
				 (!portalSource && fileName.endsWith("/portlet.xml"))) {

			newContent = formatPortletXML(fileName, absolutePath, newContent);
		}
		else if (portalSource &&
				 (fileName.endsWith(".action") ||
				  fileName.endsWith(".function") ||
				  fileName.endsWith(".macro") ||
				  fileName.endsWith(".testcase") ||
				  fileName.endsWith(".testxml"))) {

			newContent = formatPoshiXML(fileName, newContent);
		}
		else if (fileName.endsWith("/service.xml")) {
			formatServiceXML(fileName, newContent);
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

	protected String fixAntXMLProjectName(String fileName, String content) {
		int x = 0;

		if (fileName.endsWith("-ext/build.xml")) {
			if (fileName.startsWith("ext/")) {
				x = 4;
			}
		}
		else if (fileName.endsWith("-hook/build.xml")) {
			if (fileName.startsWith("hooks/")) {
				x = 6;
			}
		}
		else if (fileName.endsWith("-layouttpl/build.xml")) {
			if (fileName.startsWith("layouttpl/")) {
				x = 10;
			}
		}
		else if (fileName.endsWith("-portlet/build.xml")) {
			if (fileName.startsWith("portlets/")) {
				x = 9;
			}
		}
		else if (fileName.endsWith("-theme/build.xml")) {
			if (fileName.startsWith("themes/")) {
				x = 7;
			}
		}
		else if (fileName.endsWith("-web/build.xml") &&
				 !fileName.endsWith("/ext-web/build.xml")) {

			if (fileName.startsWith("webs/")) {
				x = 5;
			}
		}
		else {
			return content;
		}

		if (content.contains("<project>")) {
			return content;
		}

		int y = fileName.indexOf("/", x);

		String correctProjectElementText =
			"<project name=\"" + fileName.substring(x, y) + "\"";

		if (!content.contains(correctProjectElementText)) {
			x = content.indexOf("<project name=\"");

			y = content.indexOf("\"", x) + 1;
			y = content.indexOf("\"", y) + 1;

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

	@Override
	protected void format() throws Exception {
		String[] excludes = new String[] {
			"**\\.bnd\\**", "**\\.idea\\**", "**\\.ivy\\**",
			"portal-impl\\**\\*.action", "portal-impl\\**\\*.function",
			"portal-impl\\**\\*.macro", "portal-impl\\**\\*.testcase"
		};

		String[] includes = new String[] {
			"**\\*.action","**\\*.function","**\\*.macro","**\\*.testcase",
			"**\\*.xml"
		};

		_friendlyUrlRoutesSortExclusions = getPropertyList(
			"friendly.url.routes.sort.excludes.files");
		_numericalPortletNameElementExclusions = getPropertyList(
			"numerical.portlet.name.element.excludes.files");
		_xmlExclusions = getPropertyList("xml.excludes.files");

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

	protected String formatAntXML(String fileName, String content)
		throws Exception {

		String newContent = trimContent(content, true);

		newContent = fixAntXMLProjectName(fileName, newContent);

		Document document = saxReaderUtil.read(newContent);

		Element rootElement = document.getRootElement();

		String previousName = StringPool.BLANK;

		List<Element> targetElements = rootElement.elements("target");

		for (Element targetElement : targetElements) {
			String name = targetElement.attributeValue("name");

			if (name.equals("Test")) {
				name = StringUtil.toLowerCase(name);
			}

			if (name.compareTo(previousName) < -1) {
				processErrorMessage(
					fileName, fileName + " has an unordered target " + name);

				break;
			}

			previousName = name;
		}

		return newContent;
	}

	protected void formatCustomSQLXML(String fileName, String content) {
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
		Document document = saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		rootElement.sortAttributes(true);

		rootElement.sortElementsByChildElement("structure", "name");

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			Element structureRootElement = structureElement.element("root");

			structureRootElement.sortElementsByAttribute(
				"dynamic-element", "name");

			List<Element> dynamicElementElements =
				structureRootElement.elements("dynamic-element");

			for (Element dynamicElementElement : dynamicElementElements) {
				Element metaDataElement = dynamicElementElement.element(
					"meta-data");

				metaDataElement.sortElementsByAttribute("entry", "name");
			}
		}

		return document.formattedString();
	}

	protected String formatFriendlyURLRoutesXML(
			String absolutePath, String content)
		throws Exception {

		if (isExcluded(_friendlyUrlRoutesSortExclusions, absolutePath)) {
			return content;
		}

		Document document = saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<ComparableRoute> comparableRoutes =
			new ArrayList<ComparableRoute>();

		for (Element routeElement : rootElement.elements("route")) {
			String pattern = routeElement.elementText("pattern");

			ComparableRoute comparableRoute = new ComparableRoute(pattern);

			for (Element generatedParameterElement :
					routeElement.elements("generated-parameter")) {

				String name = generatedParameterElement.attributeValue("name");
				String value = generatedParameterElement.getText();

				comparableRoute.addGeneratedParameter(name, value);
			}

			for (Element ignoredParameterElement :
					routeElement.elements("ignored-parameter")) {

				String name = ignoredParameterElement.attributeValue("name");

				comparableRoute.addIgnoredParameter(name);
			}

			for (Element implicitParameterElement :
					routeElement.elements("implicit-parameter")) {

				String name = implicitParameterElement.attributeValue("name");
				String value = implicitParameterElement.getText();

				comparableRoute.addImplicitParameter(name, value);
			}

			for (Element overriddenParameterElement :
					routeElement.elements("overridden-parameter")) {

				String name = overriddenParameterElement.attributeValue("name");
				String value = overriddenParameterElement.getText();

				comparableRoute.addOverriddenParameter(name, value);
			}

			comparableRoutes.add(comparableRoute);
		}

		Collections.sort(comparableRoutes);

		String mainReleaseVersion = getMainReleaseVersion();

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE routes PUBLIC \"-//Liferay//DTD Friendly URL ");
		sb.append("Routes ");
		sb.append(mainReleaseVersion);
		sb.append("//EN\" \"http://www.liferay.com/dtd/");
		sb.append("liferay-friendly-url-routes_");
		sb.append(
			StringUtil.replace(
				mainReleaseVersion, StringPool.PERIOD, StringPool.UNDERLINE));
		sb.append(".dtd\">\n\n<routes>\n");

		for (ComparableRoute comparableRoute : comparableRoutes) {
			sb.append("\t<route>\n");
			sb.append("\t\t<pattern>");
			sb.append(comparableRoute.getPattern());
			sb.append("</pattern>\n");

			Map<String, String> generatedParameters =
				comparableRoute.getGeneratedParameters();

			for (Map.Entry<String, String> entry :
					generatedParameters.entrySet()) {

				sb.append("\t\t<generated-parameter name=\"");
				sb.append(entry.getKey());
				sb.append("\">");
				sb.append(entry.getValue());
				sb.append("</generated-parameter>\n");
			}

			Set<String> ignoredParameters =
				comparableRoute.getIgnoredParameters();

			for (String entry : ignoredParameters) {
				sb.append("\t\t<ignored-parameter name=\"");
				sb.append(entry);
				sb.append("\" />\n");
			}

			Map<String, String> implicitParameters =
				comparableRoute.getImplicitParameters();

			for (Map.Entry<String, String> entry :
					implicitParameters.entrySet()) {

				sb.append("\t\t<implicit-parameter name=\"");
				sb.append(entry.getKey());
				sb.append("\">");
				sb.append(entry.getValue());
				sb.append("</implicit-parameter>\n");
			}

			Map<String, String> overriddenParameters =
				comparableRoute.getOverriddenParameters();

			for (Map.Entry<String, String> entry :
					overriddenParameters.entrySet()) {

				sb.append("\t\t<overridden-parameter name=\"");
				sb.append(entry.getKey());
				sb.append("\">");
				sb.append(entry.getValue());
				sb.append("</overridden-parameter>\n");
			}

			sb.append("\t</route>\n");
		}

		sb.append("</routes>");

		return sb.toString();
	}

	protected String formatPortletXML(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		rootElement.sortAttributes(true);

		boolean checkNumericalPortletNameElement = !isExcluded(
			_numericalPortletNameElementExclusions, absolutePath);

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

			portletElement.sortElementsByChildElement("init-param", "name");

			Element portletPreferencesElement = portletElement.element(
				"portlet-preferences");

			if (portletPreferencesElement != null) {
				portletPreferencesElement.sortElementsByChildElement(
					"preference", "name");
			}
		}

		return document.formattedString();
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

	protected void formatServiceXML(String fileName, String content)
		throws Exception {

		Document document = saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> entityElements = rootElement.elements("entity");

		String previousEntityName = StringPool.BLANK;

		for (Element entityElement : entityElements) {
			String entityName = entityElement.attributeValue("name");

			if (Validator.isNotNull(previousEntityName) &&
				(previousEntityName.compareToIgnoreCase(entityName) > 0)) {

				processErrorMessage(
					fileName, "sort: " + fileName + " " + entityName);
			}

			checkServiceXMLFinders(fileName, entityElement, entityName);
			checkServiceXMLReferences(fileName, entityElement, entityName);

			previousEntityName = entityName;
		}

		checkServiceXMLExceptions(fileName, rootElement);
	}

	protected void formatStrutsConfigXML(String fileName, String content)
		throws Exception {

		Document document = saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		Element actionMappingsElement = rootElement.element("action-mappings");

		List<Element> actionElements = actionMappingsElement.elements("action");

		String previousPath = StringPool.BLANK;

		for (Element actionElement : actionElements) {
			String path = actionElement.attributeValue("path");

			if (Validator.isNotNull(previousPath) &&
				(previousPath.compareTo(path) > 0) &&
				(!previousPath.startsWith("/portal/") ||
				 path.startsWith("/portal/"))) {

				processErrorMessage(fileName, "sort: " + fileName + " " + path);
			}

			previousPath = path;
		}
	}

	protected void formatTilesDefsXML(String fileName, String content)
		throws Exception {

		Document document = saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> definitionElements = rootElement.elements("definition");

		String previousName = StringPool.BLANK;

		for (Element definitionElement : definitionElements) {
			String name = definitionElement.attributeValue("name");

			if (Validator.isNotNull(previousName) &&
				(previousName.compareTo(name) > 0) &&
				!previousName.equals("portlet")) {

				processErrorMessage(fileName, "sort: " + fileName + " " + name);
			}

			previousName = name;
		}
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

		String propertiesContent = fileUtil.read(
			BASEDIR + "portal-impl/src/portal.properties");

		PropertiesUtil.load(properties, propertiesContent);

		String[] locales = StringUtil.split(
			properties.getProperty(PropsKeys.LOCALES));

		Arrays.sort(locales);

		Set<String> urlPatterns = new TreeSet<String>();

		for (String locale : locales) {
			int pos = locale.indexOf(StringPool.UNDERLINE);

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

	protected List<String> getColumnNames(String fileName, String entityName)
		throws Exception {

		List<String> columnNames = new ArrayList<String>();

		Pattern pattern = Pattern.compile(
			"create table " + entityName + "_? \\(\n([\\s\\S]*?)\n\\);");

		Matcher matcher = pattern.matcher(getTablesContent(fileName));

		if (!matcher.find()) {
			return columnNames;
		}

		String tableContent = matcher.group(1);

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(tableContent));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = StringUtil.trim(line);

			String columnName = line.substring(
				0, line.indexOf(StringPool.SPACE));

			columnName = StringUtil.replace(
				columnName, StringPool.UNDERLINE, StringPool.BLANK);

			columnNames.add(columnName);
		}

		return columnNames;
	}

	protected String getTablesContent(String fileName) throws Exception {
		if (portalSource) {
			if (_tablesContent == null) {
				_tablesContent = getContent("sql/portal-tables.sql", 4);
			}

			return _tablesContent;
		}

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		return getContent(fileName.substring(0, pos) + "/sql/tables.sql", 1);
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

		Map<String, String> commandBlocksMap = new TreeMap<String, String>(
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

			Map<String, String> variableLinesMap = new TreeMap<String, String>(
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

	private static Pattern _commentPattern1 = Pattern.compile(
		">\n\t+<!--[\n ]");
	private static Pattern _commentPattern2 = Pattern.compile(
		"[\t ]-->\n[\t<]");

	private List<String> _columnNames;
	private List<String> _friendlyUrlRoutesSortExclusions;
	private List<String> _numericalPortletNameElementExclusions;
	private Pattern _poshiCharactersAfterDefinitionTag = Pattern.compile(
		"</definition>([\\w\\s\\t\\n\\<\\>]+)");
	private Pattern _poshiCharactersBeforeDefinitionTag = Pattern.compile(
		"([\\w\\s\\t\\n\\<\\>]+)<definition");
	private Pattern _poshiClosingTagPattern = Pattern.compile("</[^>/]*>");
	private Pattern _poshiCommandsPattern = Pattern.compile(
		"\\<command.*name=\\\"([^\\\"]*)\\\".*\\>[\\s\\S]*?\\</command\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+");
	private Pattern _poshiElementWithNoChildPattern = Pattern.compile(
		"\\\"[\\s]*\\>[\\n\\s\\t]*\\</[a-z\\-]+>");
	private Pattern _poshiEndLinesAfterClosingElementPattern = Pattern.compile(
		"(\\</[a-z\\-]+>)(\\n+)\\t*\\<[a-z]+");
	private Pattern _poshiEndLinesBeforeClosingElementPattern = Pattern.compile(
		"(\\n+)(\\t*</[a-z\\-]+>)");
	private Pattern _poshiEndLinesPattern = Pattern.compile(
		"\\>\\n\\n\\n+(\\t*\\<)");
	private Pattern _poshiOpeningTagPattern = Pattern.compile(
		"<[^/][^>]*[^/]>");
	private Pattern _poshiQuoteWithSlashPattern = Pattern.compile(
		"\"[^\"]*\\>[^\"]*\"");
	private Pattern _poshiSetUpPattern = Pattern.compile(
		"\\n[\\t]++\\<set-up\\>([\\s\\S]*?)\\</set-up\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+\\n");
	private Pattern _poshiTabsPattern = Pattern.compile("\\n*([ \\t]*<).*");
	private Pattern _poshiTearDownPattern = Pattern.compile(
		"\\n[\\t]++\\<tear-down\\>([\\s\\S]*?)\\</tear-down\\>" +
			"[\\n|\\t]*?(?:[^(?:/\\>)]*?--\\>)*+\\n");
	private Pattern _poshiVariableLinePattern = Pattern.compile(
		"([\\t]*+)(\\<var name=\\\"([^\\\"]*)\\\".*?/\\>.*+(?:\\</var\\>)??)");
	private Pattern _poshiVariablesBlockPattern = Pattern.compile(
		"((?:[\\t]*+\\<var.*?\\>\\n[\\t]*+){2,}?)" +
			"(?:(?:\\n){1,}+|\\</execute\\>)");
	private Pattern _poshiWholeTagPattern = Pattern.compile("<[^\\>^/]*\\/>");
	private String _tablesContent;
	private Pattern _whereNotInSQLPattern = Pattern.compile(
		"WHERE[ \t\n]+\\(*[a-zA-z0-9.]+ NOT IN");
	private List<String> _xmlExclusions;

	private class FinderElementComparator implements Comparator<Element> {

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

}