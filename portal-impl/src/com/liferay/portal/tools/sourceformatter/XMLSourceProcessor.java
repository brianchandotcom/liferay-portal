/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.tools.ComparableRoute;
import com.liferay.util.ContentUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
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
		Pattern pattern = Pattern.compile(
			"\\\"[\\s]*\\>[\\n\\s\\t]*\\</[a-z\\-]+>");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			content = StringUtil.replace(content, matcher.group(), "\" />");
		}

		return content;
	}

	protected String fixPoshiXMLEndLines(String content) {
		Pattern pattern = Pattern.compile("\\>\\n\\n\\n+(\\t*\\<)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String statement = matcher.group();

			String newStatement = StringUtil.replace(
				statement, matcher.group(), ">\n\n" + matcher.group(1));

			content = StringUtil.replace(content, statement, newStatement);
		}

		return content;
	}

	protected String fixPoshiXMLEndLinesAfterClosingElement(String content) {
		Pattern pattern = Pattern.compile(
			"(\\</[a-z\\-]+>)(\\n+)\\t*\\<[a-z]+");

		Matcher matcher = pattern.matcher(content);

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
		Pattern pattern = Pattern.compile("(\\n+)(\\t*</[a-z\\-]+>)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String statement = matcher.group();

			String newStatement = StringUtil.replace(
				statement, matcher.group(1), "\n");

			content = StringUtil.replace(content, statement, newStatement);
		}

		return content;
	}

	protected String fixPoshiXMLNumberOfTabs(String content) {
		int tabCount = 0;

		Pattern pattern = Pattern.compile("\\n*([ \\t]*<).*");

		Matcher matcher = pattern.matcher(content);

		boolean ignoredCdataBlock = false;
		boolean ignoredCommentBlock = false;

		while (matcher.find()) {
			String statement = matcher.group();

			Pattern quoteWithSlashPattern = Pattern.compile(
				"\"[^\"]*\\>[^\"]*\"");

			Matcher quoteWithSlashMatcher = quoteWithSlashPattern.matcher(
				statement);

			String fixedQuoteStatement = statement;

			if (quoteWithSlashMatcher.find()) {
				fixedQuoteStatement = StringUtil.replace(
					statement, quoteWithSlashMatcher.group(), "\"\"");
			}

			Pattern closingTagPattern = Pattern.compile("</[^>/]*>");

			Matcher closingTagMatcher = closingTagPattern.matcher(
				fixedQuoteStatement);

			Pattern openingTagPattern = Pattern.compile("<[^/][^>]*[^/]>");

			Matcher openingTagMatcher = openingTagPattern.matcher(
				fixedQuoteStatement);

			Pattern wholeTagPattern = Pattern.compile("<[^\\>^/]*\\/>");

			Matcher wholeTagMatcher = wholeTagPattern.matcher(
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

				String newStatement = StringUtil.replace(
					statement, matcher.group(1), sb.toString());

				content = StringUtil.replaceFirst(
					content, statement, newStatement, matcher.start());
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
			"**\\.bnd\\**", "**\\.idea\\**", "portal-impl\\**\\*.action",
			"portal-impl\\**\\*.function", "portal-impl\\**\\*.macro",
			"portal-impl\\**\\*.testcase"
		};

		String[] includes = new String[] {
			"**\\*.action","**\\*.function","**\\*.macro","**\\*.testcase",
			"**\\*.xml"
		};

		Properties exclusions = getExclusionsProperties(
			"source_formatter_xml_exclusions.properties");

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			if ((exclusions != null) &&
				(exclusions.getProperty(fileName) != null)) {

				continue;
			}

			String content = fileUtil.read(file);

			String newContent = content;

			if (!fileName.contains("/build")) {
				newContent = trimContent(newContent, false);
			}

			if (fileName.contains("/build") && !fileName.contains("/tools/")) {
				newContent = formatAntXML(fileName, newContent);
			}
			else if (fileName.endsWith("structures.xml")) {
				newContent = formatDDLStructuresXML(newContent);
			}
			else if (fileName.endsWith("routes.xml")) {
				newContent = formatFriendlyURLRoutesXML(fileName, newContent);
			}
			else if ((portalSource &&
					  fileName.endsWith("/portlet-custom.xml")) ||
					 (!portalSource && fileName.endsWith("/portlet.xml"))) {

				newContent = formatPortletXML(newContent);
			}
			else if (portalSource && fileName.endsWith(".action") ||
					 portalSource && fileName.endsWith(".function") ||
					 portalSource && fileName.endsWith(".macro") ||
					 portalSource && fileName.endsWith(".testcase")) {

				newContent = formatPoshiXML(content);
			}
			else if (portalSource && fileName.endsWith("/service.xml")) {
				formatServiceXML(fileName, newContent);
			}
			else if (portalSource && fileName.endsWith("/struts-config.xml")) {
				formatStrutsConfigXML(fileName, content);
			}
			else if (portalSource && fileName.endsWith("/tiles-defs.xml")) {
				formatTilesDefsXML(fileName, content);
			}
			else if (portalSource && fileName.endsWith("WEB-INF/web.xml") ||
					 !portalSource && fileName.endsWith("/web.xml")) {

				newContent = formatWebXML(fileName, content);
			}

			newContent = formatXML(newContent);

			if (isAutoFix() && (newContent != null) &&
				!content.equals(newContent)) {

				fileUtil.write(file, newContent);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	protected String formatAntXML(String fileName, String content)
		throws DocumentException, IOException {

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

	protected String formatDDLStructuresXML(String content)
		throws DocumentException, IOException {

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

	protected String formatFriendlyURLRoutesXML(String fileName, String content)
		throws DocumentException, IOException {

		Properties friendlyUrlRoutesSortExclusions = getExclusionsProperties(
			"source_formatter_friendly_url_routes_sort_exclusions.properties");

		String excluded = null;

		if (friendlyUrlRoutesSortExclusions != null) {
			excluded = friendlyUrlRoutesSortExclusions.getProperty(fileName);
		}

		if (excluded != null) {
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

	protected String formatPortletXML(String content)
		throws DocumentException, IOException {

		Document document = saxReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		rootElement.sortAttributes(true);

		List<Element> portletElements = rootElement.elements("portlet");

		for (Element portletElement : portletElements) {
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

	protected String formatPoshiXML(String content) {
		String newContent = content;

		newContent = fixPoshiXMLElementWithNoChild(newContent);

		newContent = fixPoshiXMLEndLinesAfterClosingElement(newContent);

		newContent = fixPoshiXMLEndLinesBeforeClosingElement(newContent);

		newContent = fixPoshiXMLEndLines(newContent);

		newContent = fixPoshiXMLNumberOfTabs(newContent);

		return newContent.trim();
	}

	protected void formatServiceXML(String fileName, String content)
		throws DocumentException {

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

			String previousReferenceEntity = StringPool.BLANK;
			String previousReferencePackagePath = StringPool.BLANK;

			List<Element> referenceElements = entityElement.elements(
				"reference");

			for (Element referenceElement : referenceElements) {
				String referenceEntity = referenceElement.attributeValue(
					"entity");
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
							"sort: " + fileName + " " + referencePackagePath);
					}
				}

				previousReferenceEntity = referenceEntity;
				previousReferencePackagePath = referencePackagePath;
			}

			previousEntityName = entityName;
		}

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

	protected void formatStrutsConfigXML(String fileName, String content)
		throws DocumentException {

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
		throws DocumentException {

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
		throws IOException {

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

	private static Pattern _commentPattern1 = Pattern.compile(
		">\n\t+<!--[\n ]");
	private static Pattern _commentPattern2 = Pattern.compile(
		"[\t ]-->\n[\t<]");

}