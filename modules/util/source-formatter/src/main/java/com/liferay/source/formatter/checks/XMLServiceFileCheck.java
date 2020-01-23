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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultComment;

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

	private void _checkMVCCEnabled(
		String fileName, String absolutePath, Element element) {

		if (!isAttributeValue(_CHECK_MVCC_ENABLED_KEY, absolutePath) ||
			(element.attributeValue("mvcc-enabled") != null)) {

			return;
		}

		List<String> allowedFileNames = getAttributeValues(
			_ALLOWED_MISSING_MVCC_ENABLED_FILE_NAMES_KEY, absolutePath);

		for (String allowedFileName : allowedFileNames) {
			if (absolutePath.endsWith(allowedFileName)) {
				return;
			}
		}

		addMessage(
			fileName,
			"Attribute 'mvcc-enabled' should always be set in service.xml. " +
				"Preferably, set 'mvcc-enabled=\"true\"'.");
	}

	private void _checkServiceXML(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		_checkMVCCEnabled(fileName, absolutePath, rootElement);

		ServiceReferenceElementComparator serviceReferenceElementComparator =
			new ServiceReferenceElementComparator("entity");

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

			if (GetterUtil.getBoolean(
					entityElement.attributeValue("deprecated"))) {

				continue;
			}

			String entityName = entityElement.attributeValue("name");

			_checkStatusColumns(fileName, entityElement, entityName);

			List<String> columnNames = new ArrayList<>();

			for (Element columnElement :
					(List<Element>)entityElement.elements("column")) {

				columnNames.add(columnElement.attributeValue("name"));
			}

			if (!columnNames.isEmpty() && !columnNames.contains("companyId")) {
				List<String> allowedEntityNames = getAttributeValues(
					_ALLOWED_MISSING_COMPANY_ID_ENTITY_NAMES_KEY, absolutePath);

				if (!allowedEntityNames.isEmpty() &&
					!allowedEntityNames.contains(entityName)) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Entity '", entityName,
							"' should have a column named 'companyId', See ",
							"LPS-107076"));
				}
			}

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

	private void _checkStatusColumns(
		String fileName, Element entityElement, String entityName) {

		Iterator<Node> iterator = entityElement.nodeIterator();

		boolean otherFields = false;
		String previousColumnName = null;

		while (iterator.hasNext()) {
			Node node = (Node)iterator.next();

			if (node instanceof DefaultComment) {
				DefaultComment defaultComment = (DefaultComment)node;

				if (Objects.equals(
						defaultComment.asXML(), "<!-- Other fields -->")) {

					otherFields = true;
				}
				else if (otherFields) {
					return;
				}
			}
			else if (otherFields && (node instanceof Element)) {
				Element element = (Element)node;

				if (!Objects.equals(element.getName(), "column")) {
					continue;
				}

				String columnName = element.attributeValue("name");

				if (_isStatusColumnName(previousColumnName) &&
					!_isStatusColumnName(columnName)) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Incorrect order '", entityName, "#",
							previousColumnName, "'. Status columns should ",
							"come last in the category 'Other fields'."));
				}

				previousColumnName = columnName;
			}
		}
	}

	private boolean _isStatusColumnName(String columnName) {
		if ((columnName != null) &&
			(columnName.equals("status") ||
			 columnName.equals("statusByUserId") ||
			 columnName.equals("statusByUserName") ||
			 columnName.equals("statusDate") ||
			 columnName.equals("statusMessage"))) {

			return true;
		}

		return false;
	}

	private static final String _ALLOWED_MISSING_COMPANY_ID_ENTITY_NAMES_KEY =
		"allowedMissingCompanyIdEntityNames";

	private static final String _ALLOWED_MISSING_MVCC_ENABLED_FILE_NAMES_KEY =
		"allowedMissingMVVCEnabledFileNames";

	private static final String _CHECK_MVCC_ENABLED_KEY = "checkMVCCEnabled";

	private static final String _SERVICE_FINDER_COLUMN_SORT_EXCLUDES =
		"service.finder.column.sort.excludes";

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

			if (strippedFinderName1.startsWith("Gt") ||
				strippedFinderName1.startsWith("Like") ||
				strippedFinderName1.startsWith("Lt") ||
				strippedFinderName1.startsWith("Not")) {

				String strippedFinderName2 = finderName2.substring(
					startsWithWeight);

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