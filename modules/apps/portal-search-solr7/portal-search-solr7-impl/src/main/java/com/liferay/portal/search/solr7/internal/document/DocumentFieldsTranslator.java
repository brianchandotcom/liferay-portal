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

import com.liferay.portal.search.document.DocumentBuilder;

import java.util.Collection;

import org.apache.solr.common.SolrDocument;

/**
 * @author Bryan Engler
 */
public class DocumentFieldsTranslator {

	public static void translate(
		DocumentBuilder documentBuilder, SolrDocument solrDocument) {

		Collection<String> fieldNames = solrDocument.getFieldNames();

		for (String fieldName : fieldNames) {
			if (fieldName.equals(_VERSION_FIELD)) {
				continue;
			}

			documentBuilder.setValues(
				fieldName, solrDocument.getFieldValues(fieldName));
		}
	}

	private static final String _VERSION_FIELD = "_version_";

}