/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query.translator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.WildcardQuery;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class SolrQueryTranslator {

	public org.apache.lucene.search.Query convert(Query query) {
		if (query instanceof BooleanQuery) {
			return visit((BooleanQuery)query);
		}

		if (query instanceof TermQuery) {
			return visit((TermQuery)query);
		}

		if (query instanceof WildcardQuery) {
			return visit((WildcardQuery)query);
		}

		_log.error("Query translator not found for " + query.getClass());

		return null;
	}

	public String translate(Query query) {
		org.apache.lucene.search.Query luceneQuery = convert(query);

		if (luceneQuery != null) {
			return luceneQuery.toString();
		}

		return null;
	}

	public org.apache.lucene.search.Query visit(BooleanQuery booleanQuery) {
		BooleanQueryTranslatorImpl booleanQueryTranslatorImpl =
			new BooleanQueryTranslatorImpl();

		return booleanQueryTranslatorImpl.translate(booleanQuery, this);
	}

	public org.apache.lucene.search.Query visit(TermQuery termQuery) {
		TermQueryTranslatorImpl termQueryTranslatorImpl =
			new TermQueryTranslatorImpl();

		return termQueryTranslatorImpl.translate(termQuery);
	}

	public org.apache.lucene.search.Query visit(WildcardQuery wildcardQuery) {
		WildcardQueryTranslatorImpl wildcardQueryTranslatorImpl =
			new WildcardQueryTranslatorImpl();

		return wildcardQueryTranslatorImpl.translate(wildcardQuery);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SolrQueryTranslator.class);

}