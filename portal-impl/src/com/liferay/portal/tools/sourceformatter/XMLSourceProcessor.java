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

/**
 * @author Hugo Huijser
 */
public class XMLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected void doFormat() throws Exception {
		formatAntXML();
		formatDDLStructuresXML();
		formatFriendlyURLRoutesXML();
		formatPortletXML();
		formatServiceXML();
		formatStrutsConfigXML();
		formatTilesDefsXML();
		formatWebXML();
	}

	protected String fixAntXMLProjectName(String fileName, String content) {
		int x = 0;

		if (fileName.endsWith("-ext/build.xml")) {
			x = fileName.indexOf("ext/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 4;
			}
		}
		else if (fileName.endsWith("-hook/build.xml")) {
			x = fileName.indexOf("hooks/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 6;
			}
		}
		else if (fileName.endsWith("-layouttpl/build.xml")) {
			x = fileName.indexOf("layouttpl/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 10;
			}
		}
		else if (fileName.endsWith("-portlet/build.xml")) {
			x = fileName.indexOf("portlets/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 9;
			}
		}
		else if (fileName.endsWith("-theme/build.xml")) {
			x = fileName.indexOf("themes/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 7;
			}
		}
		else if (fileName.endsWith("-web/build.xml") &&
				 !fileName.endsWith("/ext-web/build.xml")) {

			x = fileName.indexOf("webs/");

			if (x == -1) {
				x = 0;
			}
			else {
				x = x + 5;
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

	protected void formatAntXML() throws DocumentException, IOException {
		String[] excludes = new String[] {"**\\tools\\**"};
		String[] includes = new String[] {"**\\b*.xml"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String content = fileUtil.read(file);

			String newContent = trimContent(content, true);

			newContent = fixAntXMLProjectName(fileName, newContent);

			Document document = saxReaderUtil.read(newContent);

			Element rootElement = document.getRootElement();

			String previousName = StringPool.BLANK;

			List<Element> targetElements = rootElement.elements("target");

			for (Element targetElement : targetElements) {
				String name = targetElement.attributeValue("name");

				if (name.equals("Test")) {
					name = name.toLowerCase();
				}

				if (name.compareTo(previousName) < -1) {
					processErrorMessage(
						fileName,
						fileName + " has an unordered target " + name);

					break;
				}

				previousName = name;
			}

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	protected void formatDDLStructuresXML()
		throws DocumentException, IOException {

		String basedir =
			"./portal-impl/src/com/liferay/portal/events/dependencies/";

		if (!fileUtil.exists(basedir)) {
			return;
		}

		String[] includes = new String [] {"**\\*structures.xml"};

		List<String> fileNames = getFileNames(basedir, new String[0], includes);

		for (String fileName : fileNames) {
			File file = new File(basedir + fileName);

			String content = fileUtil.read(file);

			String newContent = trimContent(content, false);

			newContent = formatDDLStructuresXML(content);

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				fileName = StringUtil.replace(
					fileName, StringPool.BACK_SLASH, StringPool.SLASH);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
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

	protected void formatFriendlyURLRoutesXML()
		throws DocumentException, IOException {

		String[] excludes = new String[] {"**\\classes\\**", "**\\bin\\**"};
		String[] includes = new String[] {"**\\*routes.xml"};

		List<String> fileNames = getFileNames(excludes, includes);

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			String content = fileUtil.read(file);

			if (content.contains("<!-- SourceFormatter.Ignore -->")) {
				continue;
			}

			String newContent = trimContent(content, false);

			newContent = formatFriendlyURLRoutesXML(content);

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				fileName = StringUtil.replace(
					fileName, StringPool.BACK_SLASH, StringPool.SLASH);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
	}

	protected String formatFriendlyURLRoutesXML(String content)
		throws DocumentException {

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

	protected void formatPortletXML() throws DocumentException, IOException {
		if (portalSource) {
			File file = new File(
				BASEDIR + "portal-web/docroot/WEB-INF/portlet-custom.xml");

			String content = fileUtil.read(file);

			String newContent = formatPortletXML(content);

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				sourceFormatterHelper.printError(file.toString(), file);
			}
		}
		else {
			String[] includes = new String[] {"**\\portlet.xml"};

			List<String> fileNames = getFileNames(new String[0], includes);

			for (String fileName : fileNames) {
				File file = new File(BASEDIR + fileName);

				String content = fileUtil.read(file);

				String newContent = trimContent(content, false);

				newContent = formatPortletXML(content);

				if ((newContent != null) && !content.equals(newContent)) {
					fileUtil.write(file, newContent);

					fileName = StringUtil.replace(
						fileName, StringPool.BACK_SLASH, StringPool.SLASH);

					sourceFormatterHelper.printError(fileName, file);
				}
			}
		}
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

	protected void formatServiceXML() throws DocumentException, IOException {
		String[] includes = new String[] {"**\\service.xml"};

		List<String> fileNames = getFileNames(new String[0], includes);

		for (String fileName : fileNames) {
			File file = new File(BASEDIR + fileName);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String content = fileUtil.read(file);

			String newContent = trimContent(content, false);

			formatServiceXML(fileName, content);

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				sourceFormatterHelper.printError(fileName, file);
			}
		}
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

	protected void formatStrutsConfigXML()
		throws DocumentException, IOException {

		if (!portalSource) {
			return;
		}

		String fileName = "portal-web/docroot/WEB-INF/struts-config.xml";

		File file = new File(BASEDIR + fileName);

		String content = fileUtil.read(file);

		String newContent = trimContent(content, false);

		Document document = saxReaderUtil.read(newContent);

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

		if ((newContent != null) && !content.equals(newContent)) {
			fileUtil.write(file, newContent);

			sourceFormatterHelper.printError(fileName, file);
		}
	}

	protected void formatTilesDefsXML() throws DocumentException, IOException {
		if (!portalSource) {
			return;
		}

		String fileName = "portal-web/docroot/WEB-INF/tiles-defs.xml";

		File file = new File(BASEDIR + fileName);

		String content = fileUtil.read(file);

		String newContent = trimContent(content, false);

		Document document = saxReaderUtil.read(newContent);

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

		if ((newContent != null) && !content.equals(newContent)) {
			fileUtil.write(file, newContent);

			sourceFormatterHelper.printError(fileName, file);
		}
	}

	protected void formatWebXML() throws IOException {
		if (portalSource) {
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

			StringBundler sb = new StringBundler();

			for (String urlPattern : urlPatterns) {
				sb.append("\t<servlet-mapping>\n");
				sb.append("\t\t<servlet-name>I18n Servlet</servlet-name>\n");
				sb.append(
					"\t\t<url-pattern>/" + urlPattern +"/*</url-pattern>\n");
				sb.append("\t</servlet-mapping>\n");
			}

			File file = new File(
				BASEDIR + "portal-web/docroot/WEB-INF/web.xml");

			String content = fileUtil.read(file);

			String newContent = trimContent(content, false);

			int x = newContent.indexOf("<servlet-mapping>");

			x = newContent.indexOf(
				"<servlet-name>I18n Servlet</servlet-name>", x);

			x = newContent.lastIndexOf("<servlet-mapping>", x) - 1;

			int y = newContent.lastIndexOf(
				"<servlet-name>I18n Servlet</servlet-name>");

			y = newContent.indexOf("</servlet-mapping>", y) + 19;

			newContent =
				newContent.substring(0, x) + sb.toString() +
					newContent.substring(y);

			x = newContent.indexOf("<security-constraint>");

			x = newContent.indexOf(
				"<web-resource-name>/c/portal/protected</web-resource-name>",
				x);

			x = newContent.indexOf("<url-pattern>", x) - 3;

			y = newContent.indexOf("<http-method>", x);

			y = newContent.lastIndexOf("</url-pattern>", y) + 15;

			sb = new StringBundler();

			sb.append("\t\t\t<url-pattern>/c/portal/protected</url-pattern>\n");

			for (String urlPattern : urlPatterns) {
				sb.append(
					"\t\t\t<url-pattern>/" + urlPattern +
						"/c/portal/protected</url-pattern>\n");
			}

			newContent =
				newContent.substring(0, x) + sb.toString() +
					newContent.substring(y);

			if ((newContent != null) && !content.equals(newContent)) {
				fileUtil.write(file, newContent);

				System.out.println(file);
			}
		}
		else {
			String webXML = ContentUtil.get(
				"com/liferay/portal/deploy/dependencies/web.xml");

			String[] includes = new String[] {"**\\web.xml"};

			List<String> fileNames = getFileNames(new String[0], includes);

			for (String fileName : fileNames) {
				String content = fileUtil.read(BASEDIR + fileName);

				if (content.equals(webXML)) {
					fileName = StringUtil.replace(
						fileName, StringPool.BACK_SLASH, StringPool.SLASH);

					processErrorMessage(fileName, fileName);
				}
			}
		}
	}

}