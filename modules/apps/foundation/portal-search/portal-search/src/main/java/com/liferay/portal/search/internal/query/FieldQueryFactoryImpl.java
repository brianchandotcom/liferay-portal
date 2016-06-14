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
import com.liferay.portal.kernel.util.CharPool;
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

		boolean isSubstringSearchAlways = false;

		if (_queryPreProcessConfiguration != null) {
			isSubstringSearchAlways =
				_queryPreProcessConfiguration.isSubstringSearchAlways(field);
		}

		if (!isSubstringSearchAlways) {
			return doCreateQueryForFullTextSearch(field, value);
		}

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

	protected Query doCreatePhraseMatchQuery(String field, String value) {
		if (!isPhrase(value)) {
			return null;
		}

		value = value.substring(1, value.length() - 1);

		MatchQuery matchQuery = new MatchQuery(field, value);

		if (value.endsWith(StringPool.STAR)) {
			matchQuery.setType(MatchQuery.Type.PHRASE_PREFIX);
		}
		else {
			matchQuery.setType(MatchQuery.Type.PHRASE);
		}

		return matchQuery;
	}

	protected Query doCreateQuery(String field, String value, boolean like) {
		Query query = doCreatePhraseMatchQuery(field, value);

		if (query != null) {
			return query;
		}

		return doCreateQueryForSubstringSearch(field, value);
	}

	protected Query doCreateQueryForFullTextExactMatch(
		String field, String value) {

		MatchQuery matchQuery = new MatchQuery(field, value);

		matchQuery.setType(MatchQuery.Type.PHRASE);

		matchQuery.setBoost(_FULL_TEXT_EXACT_MATCH_BOOST);

		return matchQuery;
	}

	protected Query doCreateQueryForFullTextProximity(
		String field, String value) {

		MatchQuery matchQuery = new MatchQuery(field, value);

		matchQuery.setType(MatchQuery.Type.PHRASE);

		matchQuery.setSlop(_FULL_TEXT_PROXIMITY_SLOP);

		return matchQuery;
	}

	protected Query doCreateQueryForFullTextScoring(
		String field, String value) {

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new MatchQuery(field, value), BooleanClauseOccur.MUST);

		booleanQueryImpl.add(
			doCreateQueryForFullTextExactMatch(field, value),
			BooleanClauseOccur.SHOULD);

		booleanQueryImpl.add(
			doCreateQueryForFullTextProximity(field, value),
			BooleanClauseOccur.SHOULD);

		return booleanQueryImpl;
	}

	protected Query doCreateQueryForFullTextSearch(String field, String value) {
		Query query = doCreatePhraseMatchQuery(field, value);

		if (query != null) {
			return query;
		}

		return doCreateQueryForFullTextScoring(field, value);
	}

	protected Query doCreateQueryForSubstringSearch(
		String field, String value) {

		value = StringUtil.replace(value, CharPool.PERCENT, StringPool.BLANK);

		if (value.length() == 0) {
			value = StringPool.STAR;
		}
		else {
			value = StringUtil.toLowerCase(value);

			value = StringPool.STAR + value + StringPool.STAR;
		}

		return new WildcardQueryImpl(new QueryTermImpl(field, value));
	}

	protected KeywordTokenizer getKeywordTokenizer() {
		if (_keywordTokenizer != null) {
			return _keywordTokenizer;
		}

		return _defaultKeywordTokenizer;
	}

	protected boolean isPhrase(String value) {
		if (value.startsWith(StringPool.QUOTE) &&
			value.endsWith(StringPool.QUOTE)) {

			return true;
		}

		return false;
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

	private static final float _FULL_TEXT_EXACT_MATCH_BOOST = 2.0f;

	private static final int _FULL_TEXT_PROXIMITY_SLOP = 50;

	private KeywordTokenizer _defaultKeywordTokenizer;
	private KeywordTokenizer _keywordTokenizer;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile QueryPreProcessConfiguration _queryPreProcessConfiguration;

}