/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.util.StringUtil;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;
import org.apache.solr.client.solrj.util.ClientUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(service = TermQueryTranslator.class)
public class TermQueryTranslatorImpl implements TermQueryTranslator {

	@Override
	public Query translate(TermQuery termQuery) {
		QueryTerm queryTerm = termQuery.getQueryTerm();

		String value = queryTerm.getValue();

		if (value.isEmpty()) {
			value = StringPool.DOUBLE_APOSTROPHE;
		}

		Query query = new org.apache.lucene.search.TermQuery(
			new Term(
				_escape(queryTerm.getField()),
				ClientUtils.escapeQueryChars(value)));

		if (!termQuery.isDefaultBoost()) {
			return new BoostQuery(query, termQuery.getBoost());
		}

		return query;
	}

	private String _escape(String value) {
		return StringUtil.replace(
			value, CharPool.SPACE, StringPool.BACK_SLASH + StringPool.SPACE);
	}

}