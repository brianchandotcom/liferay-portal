/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.generic.MatchAllQuery;

import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = MatchAllQueryTranslator.class)
public class MatchAllQueryTranslatorImpl implements MatchAllQueryTranslator {

	@Override
	public Query translate(MatchAllQuery matchAllQuery) {
		Query query = new MatchAllDocsQuery();

		if (!matchAllQuery.isDefaultBoost()) {
			return new BoostQuery(query, matchAllQuery.getBoost());
		}

		return query;
	}

}