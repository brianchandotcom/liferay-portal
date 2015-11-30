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

package com.liferay.portal.search.internal.query;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.QueryTermImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.search.query.FieldQueryFactory;
import com.liferay.portal.kernel.search.query.QueryPreProcessConfiguration;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.analysis.KeywordTokenizer;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = FieldQueryFactory.class)
public class FieldQueryFactoryImpl implements FieldQueryFactory {

	@Override
	public Query createQuery(
		String field, String value, boolean like, boolean splitKeywords) {

		KeywordTokenizer keywordTokenizer = getKeywordTokenizer();

		if (!splitKeywords && (keywordTokenizer != null)) {
			splitKeywords = keywordTokenizer.requiresTokenization(value);
		}

		if (splitKeywords && (keywordTokenizer != null)) {
			List<String> tokens = keywordTokenizer.tokenize(value);

			if (tokens.size() == 1) {
				return createQuery(field, tokens.get(0), like, false);
			}

			BooleanQueryImpl booleanQuery = new BooleanQueryImpl();

			for (String token : tokens) {
				Query query = doCreateQuery(field, token, like);

				booleanQuery.add(query, BooleanClauseOccur.SHOULD);
			}

			return booleanQuery;
		}

		return doCreateQuery(field, value, like);
	}

	protected Query doCreateQuery(String field, String value, boolean like) {
		boolean isSubstringSearchAlways = false;

		if (_queryPreProcessConfiguration != null) {
			isSubstringSearchAlways =
				_queryPreProcessConfiguration.isSubstringSearchAlways(field);
		}

		boolean phrase = false;

		if (value.startsWith(StringPool.QUOTE) &&
			value.endsWith(StringPool.QUOTE)) {

			phrase = true;
		}

		Query query = null;

		if (!phrase && (like || isSubstringSearchAlways)) {
			value = StringUtil.replace(
				value, StringPool.PERCENT, StringPool.BLANK);

			if (isSubstringSearchAlways) {
				if (value.length() == 0) {
					value = StringPool.STAR;
				}
				else {
					value = StringUtil.toLowerCase(value);

					value = StringPool.STAR + value + StringPool.STAR;
				}
			}

			query = new WildcardQueryImpl(new QueryTermImpl(field, value));
		}
		else {
			MatchQuery matchQuery = new MatchQuery(field, value);

			if (value.startsWith(StringPool.QUOTE) &&
				value.endsWith(StringPool.QUOTE)) {

				value = value.substring(1, value.length() - 1);

				if (value.endsWith(StringPool.STAR)) {
					matchQuery.setType(MatchQuery.Type.PHRASE_PREFIX);
				}
				else {
					matchQuery.setType(MatchQuery.Type.PHRASE);
				}
			}

			query = matchQuery;
		}

		return query;
	}

	protected KeywordTokenizer getKeywordTokenizer() {
		if (_keywordTokenizer != null) {
			return _keywordTokenizer;
		}

		return _defaultKeywordTokenizer;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setKeywordTokenizer(
		KeywordTokenizer keywordTokenizer, Map<String, Object> properties) {

		String mode = (String)properties.get("mode");

		if (Validator.isNotNull(mode) && mode.equals("default")) {
			_defaultKeywordTokenizer = keywordTokenizer;
		}
		else {
			_keywordTokenizer = keywordTokenizer;
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setQueryPreProcessConfiguration(
		QueryPreProcessConfiguration queryPreProcessConfiguration) {

		_queryPreProcessConfiguration = queryPreProcessConfiguration;
	}

	protected void unsetKeywordTokenizer(
		KeywordTokenizer keywordTokenizer, Map<String, Object> properties) {

		String mode = (String)properties.get("mode");

		if (Validator.isNotNull(mode) && mode.equals("default")) {
			_defaultKeywordTokenizer = null;
		}
		else {
			_keywordTokenizer = null;
		}
	}

	protected void unsetQueryPreProcessConfiguration(
		QueryPreProcessConfiguration queryPreProcessConfiguration) {

		_queryPreProcessConfiguration = null;
	}

	private KeywordTokenizer _defaultKeywordTokenizer;
	private KeywordTokenizer _keywordTokenizer;
	private volatile QueryPreProcessConfiguration _queryPreProcessConfiguration;

}