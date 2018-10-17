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

package com.liferay.portal.search.solr7.internal.query;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BoostQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(immediate = true, service = BooleanQueryTranslator.class)
public class BooleanQueryTranslatorImpl implements BooleanQueryTranslator {

	@Override
	public org.apache.lucene.search.Query translate(
		BooleanQuery booleanQuery,
		QueryVisitor<org.apache.lucene.search.Query> queryVisitor) {

		org.apache.lucene.search.BooleanQuery.Builder builder =
			new org.apache.lucene.search.BooleanQuery.Builder();

		for (BooleanClause<Query> booleanClause : booleanQuery.clauses()) {
			builder.add(
				translate(booleanClause.getClause(), queryVisitor),
				translate(booleanClause.getBooleanClauseOccur()));
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
		else {
			throw new IllegalArgumentException();
		}
	}

	protected org.apache.lucene.search.Query translate(
		Query query,
		QueryVisitor<org.apache.lucene.search.Query> queryVisitor) {

		return query.accept(queryVisitor);
	}

}