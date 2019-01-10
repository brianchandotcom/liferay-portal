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

package com.liferay.portal.search.solr7.internal.document;

import com.liferay.portal.kernel.search.Field;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

/**
 * @author Bryan Engler
 */
public class SolrInputDocumentAtomicUpdateTranslator {

	public static SolrInputDocument translate(
		SolrInputDocument solrInputDocument) {

		return _addAtomicUpdateModifiers(solrInputDocument);
	}

	private static SolrInputDocument _addAtomicUpdateModifiers(
		SolrInputDocument solrInputDocument) {

		SolrInputDocument modifiedSolrInputDocument = new SolrInputDocument();

		for (String fieldName : solrInputDocument.getFieldNames()) {
			Collection<Object> values = solrInputDocument.getFieldValues(
				fieldName);

			if (fieldName.equals(Field.UID)) {
				modifiedSolrInputDocument.setField(fieldName, values);

				continue;
			}

			Map<String, Object> modifiedValue = new HashMap<>();

			modifiedValue.put("set", values);

			modifiedSolrInputDocument.setField(fieldName, modifiedValue);
		}

		return modifiedSolrInputDocument;
	}

}