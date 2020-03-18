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

package com.liferay.portal.search.solr7.internal.query.translator;

import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermQuery;

/**
 * @author André de Oliveira
 */
public class SolrQueryTranslator {

	public org.apache.lucene.search.Query convert(Query query) {
		if (query instanceof BooleanQuery) {
			return visit((BooleanQuery)query);
		}

		if (query instanceof TermQuery) {
			return visit((TermQuery)query);
		}

		return null;
	}

	public String translate(Query query) {
		org.apache.lucene.search.Query luceneQuery = convert(query);

		if (luceneQuery != null) {
			return luceneQuery.toString();
		}

		return null;
	}

	public org.apache.lucene.search.Query visit(BooleanQuery booleanQuery) {
		BooleanQueryTranslatorImpl booleanQueryTranslatorImpl =
			new BooleanQueryTranslatorImpl();

		return booleanQueryTranslatorImpl.translate(booleanQuery, this);
	}

	public org.apache.lucene.search.Query visit(TermQuery termQuery) {
		TermQueryTranslatorImpl termQueryTranslatorImpl =
			new TermQueryTranslatorImpl();

		return termQueryTranslatorImpl.translate(termQuery);
	}

}