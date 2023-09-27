/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query.translator;

import com.liferay.portal.search.query.TermQuery;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;

/**
 * @author André de Oliveira
 */
public class TermQueryTranslatorImpl {

	public Query translate(TermQuery termQuery) {
		Query query = new org.apache.lucene.search.TermQuery(
			new Term(
				termQuery.getField(), String.valueOf(termQuery.getValue())));

		if (termQuery.getBoost() != null) {
			return new BoostQuery(query, termQuery.getBoost());
		}

		return query;
	}

}