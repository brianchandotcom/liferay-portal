/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.sort;

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
@Component(service = SolrSortFieldTranslator.class)
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