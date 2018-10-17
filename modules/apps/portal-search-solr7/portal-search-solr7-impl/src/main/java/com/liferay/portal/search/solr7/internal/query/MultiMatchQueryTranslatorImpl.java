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

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = MultiMatchQueryTranslator.class)
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
			field, multiMatchQuery.getType(), multiMatchQuery.getValue(),
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