/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class XMLServiceMissingCompanyIdCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/service.xml") ||
			absolutePath.contains("/gradleTest/") ||
			absolutePath.contains("-test-")) {

			return content;
		}

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return content;
		}

		Element rootElement = document.getRootElement();

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

			if (GetterUtil.getBoolean(
					entityElement.attributeValue("deprecated"))) {

				continue;
			}

			String entityName = entityElement.attributeValue("name");

			List<String> columnNames = new ArrayList<>();

			for (Element columnElement :
					(List<Element>)entityElement.elements("column")) {

				columnNames.add(columnElement.attributeValue("name"));
			}

			if (columnNames.isEmpty() || columnNames.contains("companyId")) {
				continue;
			}

			List<String> allowedEntityNames = getAttributeValues(
				_ALLOWED_MISSING_COMPANY_ID_ENTITY_NAMES_KEY, absolutePath);

			if (allowedEntityNames.isEmpty() ||
				allowedEntityNames.contains(entityName)) {

				continue;
			}

			addMessage(
				fileName,
				StringBundler.concat(
					"Entity \"", entityName,
					"\" should have a column named \"companyId\", see ",
					"LPS-107076"));
		}

		return content;
	}

	private static final String _ALLOWED_MISSING_COMPANY_ID_ENTITY_NAMES_KEY =
		"allowedMissingCompanyIdEntityNames";

}