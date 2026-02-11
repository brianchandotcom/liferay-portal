/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.document;

import java.util.Map;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class DocumentBuilderFactory {

	public static DocumentBuilder builder() {
		return new DocumentBuilder();
	}

	public static DocumentBuilder builder(Document document) {
		DocumentBuilder documentBuilder = new DocumentBuilder();

		Map<String, Field> map = document.getFields();

		map.forEach(
			(name, field) -> documentBuilder.setValues(
				name, field.getValues()));

		return documentBuilder;
	}

}