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

package com.liferay.portal.search.solr7.internal.sort;

import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.GeoDistanceSort;
import com.liferay.portal.search.sort.ScoreSort;
import com.liferay.portal.search.sort.ScriptSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortFieldTranslator;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.SortVisitor;

import org.apache.solr.client.solrj.SolrQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(
	property = "search.engine.impl=Solr",
	service = {SortFieldTranslator.class, SortVisitor.class}
)
public class SolrSortFieldTranslator
	implements SortFieldTranslator<SolrQuery.SortClause>,
			   SortVisitor<SolrQuery.SortClause> {

	@Override
	public SolrQuery.SortClause translate(Sort sort) {
		return sort.accept(this);
	}

	@Override
	public SolrQuery.SortClause visit(FieldSort fieldSort) {
		SolrQuery.ORDER order = SolrQuery.ORDER.asc;

		if (SortOrder.DESC.equals(fieldSort.getSortOrder())) {
			order = SolrQuery.ORDER.desc;
		}

		return SolrQuery.SortClause.create(fieldSort.getField(), order);
	}

	@Override
	public SolrQuery.SortClause visit(GeoDistanceSort geoDistanceSort) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SolrQuery.SortClause visit(ScoreSort scoreSort) {
		SolrQuery.ORDER order = SolrQuery.ORDER.desc;

		if (SortOrder.ASC.equals(scoreSort.getSortOrder())) {
			order = SolrQuery.ORDER.asc;
		}

		return new SolrQuery.SortClause("score", order);
	}

	@Override
	public SolrQuery.SortClause visit(ScriptSort scriptSort) {
		throw new UnsupportedOperationException();
	}

}