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

package com.liferay.portal.search.solr7.internal.filter;

import com.liferay.portal.kernel.search.filter.TermsFilter;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermInSetQuery;
import org.apache.lucene.util.BytesRef;
import org.apache.solr.client.solrj.util.ClientUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = TermsFilterTranslator.class)
public class TermsFilterTranslatorImpl implements TermsFilterTranslator {

	@Override
	public Query translate(TermsFilter termsFilter) {
		String field = termsFilter.getField();

		List<BytesRef> bytesRefs = new ArrayList<>();

		for (String value : termsFilter.getValues()) {
			Term term = new Term(field, ClientUtils.escapeQueryChars(value));

			bytesRefs.add(term.bytes());
		}

		Query query = new TermInSetQuery(field, bytesRefs);

		if (bytesRefs.size() == 1) {
			return query;
		}

		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		builder.add(query, BooleanClause.Occur.SHOULD);

		return builder.build();
	}

}