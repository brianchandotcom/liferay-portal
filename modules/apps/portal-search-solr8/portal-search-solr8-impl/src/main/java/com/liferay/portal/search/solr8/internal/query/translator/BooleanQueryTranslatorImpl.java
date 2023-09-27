/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query.translator;

import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Query;

import java.util.List;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BoostQuery;

/**
 * @author André de Oliveira
 */
public class BooleanQueryTranslatorImpl {

	public org.apache.lucene.search.Query translate(
		BooleanQuery booleanQuery, SolrQueryTranslator solrQueryTranslator) {

		org.apache.lucene.search.BooleanQuery.Builder builder =
			new org.apache.lucene.search.BooleanQuery.Builder();

		processQueryClause(
			booleanQuery.getMustQueryClauses(), solrQueryTranslator,
			query -> builder.add(query, BooleanClause.Occur.MUST));

		processQueryClause(
			booleanQuery.getMustNotQueryClauses(), solrQueryTranslator,
			query -> builder.add(query, BooleanClause.Occur.MUST_NOT));

		org.apache.lucene.search.Query query = builder.build();

		if (booleanQuery.getBoost() != null) {
			return new BoostQuery(query, booleanQuery.getBoost());
		}

		return query;
	}

	protected void processQueryClause(
		List<Query> queryClauses, SolrQueryTranslator solrQueryTranslator,
		LuceneQueryConsumer luceneQueryConsumer) {

		for (Query query : queryClauses) {
			luceneQueryConsumer.accept(translate(query, solrQueryTranslator));
		}
	}

	protected org.apache.lucene.search.Query translate(
		Query query, SolrQueryTranslator solrQueryTranslator) {

		return solrQueryTranslator.convert(query);
	}

	protected interface LuceneQueryConsumer {

		public void accept(org.apache.lucene.search.Query query);

	}

}