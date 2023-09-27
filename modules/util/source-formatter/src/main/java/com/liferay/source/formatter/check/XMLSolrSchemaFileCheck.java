/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.comparator.ElementComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLSolrSchemaFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/schema.xml") && absolutePath.contains("solr")) {
			_checkSolrSchemaXML(fileName, content);
		}

		return content;
	}

	private void _checkSolrSchemaXML(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		Element rootElement = document.getRootElement();

		checkElementOrder(
			fileName, rootElement.element("fields"), "field", null,
			new ElementComparator());
		checkElementOrder(
			fileName, rootElement.element("types"), "fieldType", null,
			new ElementComparator());
	}

}