/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.document;

import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;

import java.util.Map;

/**
 * @author André de Oliveira
 */
public class DocumentTranslator {

	public com.liferay.portal.kernel.search.Document toLegacyDocument(
		Document document) {

		DocumentImpl documentImpl = new DocumentImpl();

		Map<String, com.liferay.portal.search.document.Field> fields =
			document.getFields();

		fields.forEach(
			(key, field) -> documentImpl.add(
				new Field(key, String.valueOf(field.getValue()))));

		return documentImpl;
	}

}