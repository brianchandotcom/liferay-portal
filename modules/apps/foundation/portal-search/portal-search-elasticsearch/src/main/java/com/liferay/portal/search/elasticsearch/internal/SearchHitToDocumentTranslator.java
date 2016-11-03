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

package com.liferay.portal.search.elasticsearch.internal;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Collection;
import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;

/**
 * @author André de Oliveira
 */
public class SearchHitToDocumentTranslator {

	public Document translate(SearchHit searchHit) {
		Document document = new DocumentImpl();

		Map<String, SearchHitField> searchHitFields = searchHit.getFields();

		for (SearchHitField searchHitField : searchHitFields.values()) {
			document.add(translate(searchHitField));
		}

		return document;
	}

	protected Field translate(SearchHitField searchHitField) {
		String name = searchHitField.getName();
		Collection<Object> values = searchHitField.getValues();

		Field field = new Field(
			name,
			ArrayUtil.toStringArray(values.toArray(new Object[values.size()])));

		return field;
	}

}