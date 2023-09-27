/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.TermRangeQuery;

import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(service = TermRangeQueryTranslator.class)
public class TermRangeQueryTranslatorImpl implements TermRangeQueryTranslator {

	@Override
	public Query translate(TermRangeQuery termRangeQuery) {
		Query query = org.apache.lucene.search.TermRangeQuery.newStringRange(
			termRangeQuery.getField(), termRangeQuery.getLowerTerm(),
			termRangeQuery.getUpperTerm(), termRangeQuery.includesLower(),
			termRangeQuery.includesUpper());

		if (!termRangeQuery.isDefaultBoost()) {
			return new BoostQuery(query, termRangeQuery.getBoost());
		}

		return query;
	}

}