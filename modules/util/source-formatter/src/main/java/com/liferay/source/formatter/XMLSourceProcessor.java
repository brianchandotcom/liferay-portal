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
import com.liferay.portal.tools.ImportPackage;
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
import com.liferay.source.formatter.checks.XMLWhitespaceCheck;
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
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		if (fileName.contains("/resource-actions/")) {
			formatResourceActionXML(fileName, newContent, "model");
			formatResourceActionXML(fileName, newContent, "portlet");
		}
		else if (fileName.endsWith("/service.xml")) {
			formatServiceXML(fileName, absolutePath, newContent);
		}
		else if (fileName.endsWith("/schema.xml") &&
				 absolutePath.contains("solr")) {

			formatSolrSchemaXML(fileName, newContent);
		}
		else if (fileName.endsWith("-spring.xml")) {
			formatSpringXML(fileName, newContent);
		}
		else if ((portalSource || subrepository) &&
				 fileName.endsWith("/struts-config.xml")) {

			formatStrutsConfigXML(fileName, newContent);
		}
		else if ((portalSource || subrepository) &&
				 fileName.endsWith("/test-ignorable-error-lines.xml")) {

			formatTestIgnorableErrorLinesXml(fileName, newContent);
		}
		else if ((portalSource || subrepository) &&
				 fileName.endsWith("/tiles-defs.xml")) {

			formatTilesDefsXML(fileName, newContent);
		}
		else if (fileName.endsWith(".toggle")) {
			formatToggleXML(fileName, newContent);
		}
		else if (((portalSource || subrepository) &&
				  fileName.endsWith("portal-web/docroot/WEB-INF/web.xml")) ||
				 (!portalSource && !subrepository &&
				  fileName.endsWith("/web.xml"))) {

			newContent = formatWebXML(fileName, newContent);
		}

		newContent = sortAttributes(fileName, newContent);

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

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
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

		ServiceReferenceElementComparator serviceReferenceElementComparator =
			new ServiceReferenceElementComparator("entity");

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

			String entityName = entityElement.attributeValue("name");

			List<String> columnNames = getColumnNames(
				fileName, absolutePath, entityName);

			ServiceFinderColumnElementComparator
				serviceFinderColumnElementComparator =
					new ServiceFinderColumnElementComparator(columnNames);

			if (!isExcludedPath(
					_SERVICE_FINDER_COLUMN_SORT_EXCLUDES, absolutePath,
					entityName)) {

				for (Element finderElement :
						(List<Element>)entityElement.elements("finder")) {

					String finderName = finderElement.attributeValue("name");

					checkOrder(
						fileName, finderElement, "finder-column",
						entityName + "#" + finderName,
						serviceFinderColumnElementComparator);
				}
			}

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

	protected void formatSolrSchemaXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		checkOrder(
			fileName, rootElement.element("fields"), "field", null,
			new ElementComparator());
		checkOrder(
			fileName, rootElement.element("types"), "fieldType", null,
			new ElementComparator());
	}

	protected void formatSpringXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "bean", null,
			new SpringBeanElementComparator("id"));
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

	protected void formatToggleXML(String fileName, String content)
		throws Exception {

		Document document = readXML(content);

		checkOrder(
			fileName, document.getRootElement(), "toggle", null,
			new ElementComparator());
	}

	protected String formatWebXML(String fileName, String content)
		throws Exception {

		if (!portalSource && !subrepository) {
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

		newContent =
			newContent.substring(0, x) + sb.toString() +
				newContent.substring(y);

		return newContent;
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

		fileChecks.add(
			new XMLBuildFileCheck(sourceFormatterArgs.getBaseDirName()));

		if (portalSource || subrepository) {
			fileChecks.add(
				new XMLEmptyLinesCheck(sourceFormatterArgs.getBaseDirName()));
		}

		return fileChecks;
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

	@Override
	protected void preFormat() throws Exception {
		_numericalPortletNameElementExcludes = getExcludes(
			_NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES);
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

	private static final String[] _INCLUDES = new String[] {
		"**/*.action", "**/*.function", "**/*.jrxml", "**/*.macro",
		"**/*.testcase", "**/*.toggle", "**/*.xml"
	};

	private static final String _NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES =
		"numerical.portlet.name.element.excludes";

	private static final String _SERVICE_FINDER_COLUMN_SORT_EXCLUDES =
		"service.finder.column.sort.excludes";

	private static final Pattern _commentPattern1 = Pattern.compile(
		">\n\t+<!--[\n ]");
	private static final Pattern _commentPattern2 = Pattern.compile(
		"[\t ]-->\n[\t<]");

	private List<String> _numericalPortletNameElementExcludes;
	private String _tablesContent;
	private final Map<String, String> _tablesContentMap =
		new ConcurrentHashMap<>();

	private class ResourceActionActionKeyElementComparator
		extends ElementComparator {

		@Override
		public String getElementName(Element actionKeyElement) {
			return actionKeyElement.getStringValue();
		}

	}

	private class ResourceActionResourceElementComparator
		extends ElementComparator {

		public ResourceActionResourceElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public String getElementName(Element portletResourceElement) {
			Element portletNameElement = portletResourceElement.element(
				getNameAttribute());

			if (portletNameElement == null) {
				return null;
			}

			return portletNameElement.getText();
		}

	}

	private class ServiceExceptionElementComparator extends ElementComparator {

		@Override
		public String getElementName(Element exceptionElement) {
			return exceptionElement.getStringValue();
		}

	}

	private class ServiceFinderColumnElementComparator
		extends ElementComparator {

		public ServiceFinderColumnElementComparator(List<String> columnNames) {
			_columnNames = columnNames;
		}

		@Override
		public int compare(
			Element finderColumnElement1, Element finderColumnElement2) {

			String finderColumnName1 = finderColumnElement1.attributeValue(
				"name");
			String finderColumnName2 = finderColumnElement2.attributeValue(
				"name");

			int index1 = _columnNames.indexOf(finderColumnName1);
			int index2 = _columnNames.indexOf(finderColumnName2);

			return index1 - index2;
		}

		private final List<String> _columnNames;

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

	private class ServiceReferenceElementComparator extends ElementComparator {

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

	private class SpringBeanElementComparator extends ElementComparator {

		public SpringBeanElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public int compare(Element element1, Element element2) {
			String elementName1 = getElementName(element1);
			String elementName2 = getElementName(element2);

			if ((elementName1 == null) || (elementName2 == null)) {
				return 0;
			}

			int startsWithWeight = StringUtil.startsWithWeight(
				elementName1, elementName2);

			if (startsWithWeight != 0) {
				String startKey = elementName1.substring(0, startsWithWeight);

				if (startKey.contains(".service.")) {
					return _compareServiceElements(elementName1, elementName2);
				}
			}

			if ((StringUtil.count(elementName1, StringPool.PERIOD) > 1) &&
				(StringUtil.count(elementName2, StringPool.PERIOD) > 1)) {

				ImportPackage importPackage1 = new ImportPackage(
					elementName1, false, elementName1);
				ImportPackage importPackage2 = new ImportPackage(
					elementName2, false, elementName2);

				return importPackage1.compareTo(importPackage2);
			}

			if (StringUtil.count(elementName1, StringPool.PERIOD) > 1) {
				return -1;
			}

			return super.compare(element1, element2);
		}

		@Override
		public String getElementName(Element element) {
			String elementName = super.getElementName(element);

			if ((elementName != null) &&
				(StringUtil.count(elementName, StringPool.PERIOD) > 1)) {

				return elementName;
			}

			return element.attributeValue("class");
		}

		private int _compareServiceElements(String name1, String name2) {
			SpringBeanServiceElement springBeanServiceElemen1 =
				new SpringBeanServiceElement(name1);
			SpringBeanServiceElement springBeanServiceElemen2 =
				new SpringBeanServiceElement(name2);

			return springBeanServiceElemen1.compareTo(springBeanServiceElemen2);
		}

		private class SpringBeanServiceElement
			implements Comparable<SpringBeanServiceElement> {

			public SpringBeanServiceElement(String name) {
				_beanObjectName = StringPool.BLANK;
				_type = -1;

				Matcher matcher = _localServicePattern.matcher(name);

				if (matcher.find()) {
					_beanObjectName = matcher.group(1);
					_type = _LOCAL_SERVICE;

					return;
				}

				matcher = _servicePattern.matcher(name);

				if (matcher.find()) {
					_beanObjectName = matcher.group(1);
					_type = _SERVICE;

					return;
				}

				matcher = _persistencePattern.matcher(name);

				if (matcher.find()) {
					_beanObjectName = matcher.group(1);
					_type = _PERSISTENCE;

					return;
				}

				matcher = _finderPattern.matcher(name);

				if (matcher.find()) {
					_beanObjectName = matcher.group(1);
					_type = _FINDER;
				}
			}

			@Override
			public int compareTo(
				SpringBeanServiceElement springBeanServiceElement) {

				if (_beanObjectName.equals(
						springBeanServiceElement.getBeanObjectName())) {

					return _type - springBeanServiceElement.getType();
				}

				NaturalOrderStringComparator comparator =
					new NaturalOrderStringComparator();

				return comparator.compare(
					_beanObjectName,
					springBeanServiceElement.getBeanObjectName());
			}

			public String getBeanObjectName() {
				return _beanObjectName;
			}

			public int getType() {
				return _type;
			}

			private static final int _FINDER = 4;

			private static final int _LOCAL_SERVICE = 1;

			private static final int _PERSISTENCE = 3;

			private static final int _SERVICE = 2;

			private String _beanObjectName;
			private final Pattern _finderPattern = Pattern.compile(
				"\\.service\\.persistence\\.(\\w+)Finder");
			private final Pattern _localServicePattern = Pattern.compile(
				"\\.service\\.(\\w+)LocalService");
			private final Pattern _persistencePattern = Pattern.compile(
				"\\.service\\.persistence\\.(\\w+)Persistence");
			private final Pattern _servicePattern = Pattern.compile(
				"\\.service\\.(\\w+)Service");
			private int _type;

		}

	}

	private class StrutsActionElementComparator extends ElementComparator {

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

	private class TilesDefinitionElementComparator extends ElementComparator {

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