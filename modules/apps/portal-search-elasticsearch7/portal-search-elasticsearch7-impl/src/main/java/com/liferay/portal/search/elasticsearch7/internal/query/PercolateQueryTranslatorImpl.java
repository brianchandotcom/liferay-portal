/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.PercolateQuery;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.percolator.PercolateQueryBuilder;
import org.elasticsearch.xcontent.XContentType;

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