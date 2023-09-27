/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.generic.MultiMatchQuery;

import java.util.Map;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.util.ClientUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = MultiMatchQueryTranslator.class)
public class MultiMatchQueryTranslatorImpl
	implements MultiMatchQueryTranslator {

	@Override
	public Query translate(MultiMatchQuery multiMatchQuery) {
		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		for (String field : multiMatchQuery.getFields()) {
			builder.add(
				translate(field, multiMatchQuery), BooleanClause.Occur.SHOULD);
		}

		return builder.build();
	}

	protected Query translate(String field, MultiMatchQuery multiMatchQuery) {
		Query query = translate(
			field, multiMatchQuery.getType(),
			ClientUtils.escapeQueryChars(multiMatchQuery.getValue()),
			multiMatchQuery.getSlop());

		Map<String, Float> boostMap = multiMatchQuery.getFieldsBoosts();

		Float boost = boostMap.get(field);

		if (boost != null) {
			return new BoostQuery(query, boost);
		}

		return query;
	}

	protected Query translate(
		String field, MultiMatchQuery.Type type, String value, Integer slop) {

		if (type == MultiMatchQuery.Type.PHRASE) {
			if (slop == null) {
				return new PhraseQuery(field, value);
			}

			return new PhraseQuery(slop, field, value);
		}
		else if (type == MultiMatchQuery.Type.PHRASE_PREFIX) {
			return new PrefixQuery(new Term(field, value));
		}

		return new TermQuery(new Term(field, value));
	}

}