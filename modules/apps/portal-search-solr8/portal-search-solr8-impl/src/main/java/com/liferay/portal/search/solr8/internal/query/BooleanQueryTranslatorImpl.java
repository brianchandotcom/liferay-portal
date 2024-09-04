/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.search.solr8.internal.filter.FilterTranslator;

import java.util.List;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BoostQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 * @author Petteri Karttunen
 */
@Component(service = BooleanQueryTranslator.class)
public class BooleanQueryTranslatorImpl implements BooleanQueryTranslator {

	@Override
	public org.apache.lucene.search.Query translate(
		BooleanQuery booleanQuery,
		QueryVisitor<org.apache.lucene.search.Query> queryVisitor) {

		org.apache.lucene.search.BooleanQuery.Builder booleanQueryBuilder =
			new org.apache.lucene.search.BooleanQuery.Builder();

		List<BooleanClause<Query>> clauses = booleanQuery.clauses();

		for (BooleanClause<Query> booleanClause : clauses) {
			org.apache.lucene.search.Query query = translate(
				booleanClause.getClause(), queryVisitor);

			if (query != null) {
				booleanQueryBuilder.add(
					query, translate(booleanClause.getBooleanClauseOccur()));
			}
		}

		BooleanFilter booleanFilter = booleanQuery.getPreBooleanFilter();

		if (booleanFilter == null) {
			return _addBoost(booleanQuery, booleanQueryBuilder.build());
		}

		org.apache.lucene.search.BooleanQuery.Builder
			wrapperBooleanQueryBuilder =
				new org.apache.lucene.search.BooleanQuery.Builder();

		if (!clauses.isEmpty()) {
			wrapperBooleanQueryBuilder.add(
				booleanQueryBuilder.build(), Occur.MUST);
		}

		FilterTranslator<org.apache.lucene.search.Query> filterTranslator =
			_filterTranslatorSnapshot.get();

		wrapperBooleanQueryBuilder.add(
			filterTranslator.translate(booleanFilter), Occur.MUST);

		return _addBoost(booleanQuery, wrapperBooleanQueryBuilder.build());
	}

	protected Occur translate(BooleanClauseOccur booleanClauseOccur) {
		if (booleanClauseOccur.equals(BooleanClauseOccur.MUST)) {
			return Occur.MUST;
		}
		else if (booleanClauseOccur.equals(BooleanClauseOccur.MUST_NOT)) {
			return Occur.MUST_NOT;
		}
		else if (booleanClauseOccur.equals(BooleanClauseOccur.SHOULD)) {
			return Occur.SHOULD;
		}

		throw new IllegalArgumentException();
	}

	protected org.apache.lucene.search.Query translate(
		Query query,
		QueryVisitor<org.apache.lucene.search.Query> queryVisitor) {

		return query.accept(queryVisitor);
	}

	private org.apache.lucene.search.Query _addBoost(
		BooleanQuery booleanQuery, org.apache.lucene.search.Query query) {

		if (!booleanQuery.isDefaultBoost()) {
			return new BoostQuery(query, booleanQuery.getBoost());
		}

		return query;
	}

	private static final Snapshot
		<FilterTranslator<org.apache.lucene.search.Query>>
			_filterTranslatorSnapshot = new Snapshot<>(
				BooleanQueryTranslatorImpl.class,
				Snapshot.cast(FilterTranslator.class),
				"(search.engine.impl=Solr)", true);

}