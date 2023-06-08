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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.index.SyncReindexManager;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.DateRangeTermQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;

import java.text.Format;

import java.util.Date;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = SyncReindexManager.class)
public class SyncReindexManagerImpl implements SyncReindexManager {

	@Override
	public void deleteStaleDocuments(
		long companyId, Date date, Set<String> classNames) {

		Format format = _fastDateFormatFactory.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		DateRangeTermQuery dateRangeTermQuery = _queries.dateRangeTerm(
			"timestamp", false, false, null, format.format(date));

		TermsQuery termsQuery = _queries.terms(Field.ENTRY_CLASS_NAME);

		termsQuery.addValues(classNames.toArray());

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(termsQuery, dateRangeTermQuery);

		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			new DeleteByQueryDocumentRequest(
				booleanQuery, _indexNameBuilder.getIndexName(companyId));

		_searchEngineAdapter.execute(deleteByQueryDocumentRequest);
	}

	@Reference
	private FastDateFormatFactory _fastDateFormatFactory;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Queries _queries;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}