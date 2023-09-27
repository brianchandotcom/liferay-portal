/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import java.util.List;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BoostQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(service = BooleanQueryTranslator.class)
public class BooleanQueryTranslatorImpl implements BooleanQueryTranslator {

	@Override
	public org.apache.lucene.search.Query translate(
		BooleanQuery booleanQuery,
		QueryVisitor<org.apache.lucene.search.Query> queryVisitor) {

		List<BooleanClause<Query>> clauses = booleanQuery.clauses();

		if (clauses.isEmpty()) {
			return null;
		}

		org.apache.lucene.search.BooleanQuery.Builder builder =
			new org.apache.lucene.search.BooleanQuery.Builder();

		for (BooleanClause<Query> booleanClause : clauses) {
			org.apache.lucene.search.Query query = translate(
				booleanClause.getClause(), queryVisitor);

			if (query != null) {
				builder.add(
					query, translate(booleanClause.getBooleanClauseOccur()));
			}
		}

		org.apache.lucene.search.Query query = builder.build();

		if (!booleanQuery.isDefaultBoost()) {
			return new BoostQuery(query, booleanQuery.getBoost());
		}

		return query;
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

}