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

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.DisMaxQuery;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collection;
import java.util.HashSet;

import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.DisjunctionMaxQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DisMaxQueryTranslator.class)
public class DisMaxQueryTranslatorImpl implements DisMaxQueryTranslator {

	@Override
	public org.apache.lucene.search.Query translate(
		DisMaxQuery disMaxQuery,
		QueryVisitor<org.apache.lucene.search.Query> queryVisitor) {

		Collection<org.apache.lucene.search.Query> queries = new HashSet<>();

		for (Query query : disMaxQuery.getQueries()) {
			queries.add(query.accept(queryVisitor));
		}

		org.apache.lucene.search.Query query = new DisjunctionMaxQuery(
			queries, GetterUtil.getFloat(disMaxQuery.getTieBreaker()));

		if (!disMaxQuery.isDefaultBoost()) {
			return new BoostQuery(query, disMaxQuery.getBoost());
		}

		return query;
	}

}