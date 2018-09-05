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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLServiceFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException {

		if (fileName.endsWith("/service.xml")) {
			_checkServiceXML(fileName, absolutePath, content);
		}

		return content;
	}

	private void _checkServiceXML(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		ServiceReferenceElementComparator serviceReferenceElementComparator =
			new ServiceReferenceElementComparator("entity");

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

			String entityName = entityElement.attributeValue("name");

			List<String> columnNames = _getColumnNames(
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

					checkElementOrder(
						fileName, finderElement, "finder-column",
						entityName + "#" + finderName,
						serviceFinderColumnElementComparator);
				}
			}

			checkElementOrder(
				fileName, entityElement, "finder", entityName,
				new ServiceFinderElementComparator(columnNames));
			checkElementOrder(
				fileName, entityElement, "reference", entityName,
				serviceReferenceElementComparator);
		}

		checkElementOrder(
			fileName, rootElement, "entity", null, new ElementComparator());
		checkElementOrder(
			fileName, rootElement.element("exceptions"), "exception", null,
			new ServiceExceptionElementComparator());
	}

	private List<String> _getColumnNames(
			String fileName, String absolutePath, String entityName)
		throws IOException {

		List<String> columnNames = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			"create table " + entityName + "_? \\(\n([\\s\\S]*?)\n\\);");

		String tablesContent = _getTablesContent(fileName, absolutePath);

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
				columnName, CharPool.UNDERLINE, StringPool.BLANK);

			columnNames.add(columnName);
		}

		return columnNames;
	}

	private synchronized String _getPortalTablesContent() throws IOException {
		if (_portalTablesContent != null) {
			return _portalTablesContent;
		}

		_portalTablesContent = getContent(
			"sql/portal-tables.sql", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		return _portalTablesContent;
	}

	private String _getTablesContent(String fileName, String absolutePath)
		throws IOException {

		List<String> pluginsInsideModulesDirectoryNames =
			getPluginsInsideModulesDirectoryNames();

		if (isPortalSource() &&
			!isModulesFile(absolutePath, pluginsInsideModulesDirectoryNames)) {

			return _getPortalTablesContent();
		}

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		String moduleOrPluginFolder = fileName.substring(0, pos);

		String tablesContent = FileUtil.read(
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

		return tablesContent;
	}

	private static final String _SERVICE_FINDER_COLUMN_SORT_EXCLUDES =
		"service.finder.column.sort.excludes";

	private String _portalTablesContent;

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

			if ((index1 == -1) || (index2 == -1)) {
				return 0;
			}

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

				return strippedFinderName1.compareTo(strippedFinderName2);
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

			String packageName1 = referenceElement1.attributeValue(
				"package-path");
			String packageName2 = referenceElement2.attributeValue(
				"package-path");

			if (!packageName1.equals(packageName2)) {
				return packageName1.compareToIgnoreCase(packageName2);
			}

			String entityName1 = referenceElement1.attributeValue("entity");
			String entityName2 = referenceElement2.attributeValue("entity");

			return entityName1.compareToIgnoreCase(entityName2);
		}

	}

}