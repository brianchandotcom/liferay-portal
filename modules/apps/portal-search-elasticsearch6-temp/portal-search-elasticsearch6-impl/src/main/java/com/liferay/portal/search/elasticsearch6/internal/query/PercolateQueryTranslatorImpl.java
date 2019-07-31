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

package com.liferay.portal.search.elasticsearch6.internal.query;

import com.liferay.portal.search.query.PercolateQuery;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.percolator.PercolateQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = PercolateQueryTranslator.class)
public class PercolateQueryTranslatorImpl implements PercolateQueryTranslator {

	@Override
	public QueryBuilder translate(PercolateQuery percolateQuery) {
		List<String> documentJSONs = percolateQuery.getDocumentJSONs();

		List<BytesReference> bytesArrays = new ArrayList<>();

		documentJSONs.forEach(
			documentJSON -> bytesArrays.add(new BytesArray(documentJSON)));

		return new PercolateQueryBuilder(
			percolateQuery.getField(), bytesArrays, XContentType.JSON);
	}

}