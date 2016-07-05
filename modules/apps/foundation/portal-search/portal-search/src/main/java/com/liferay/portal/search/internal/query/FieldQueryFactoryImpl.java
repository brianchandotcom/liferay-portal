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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.analysis.KeywordTokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"full.text.exact.match.boost=2.0", "full.text.proximity.slop=50"
	},
	service = FieldQueryFactory.class
)
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
			return createQueryForFullTextSearch(field, value);
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
				Query tokenQuery = createTokenQuery(field, token);

				booleanQuery.add(tokenQuery, BooleanClauseOccur.SHOULD);
			}

			return booleanQuery;
		}

		return createTokenQuery(field, value);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_fullTextExactMatchBoost = GetterUtil.getFloat(
			properties.get("full.text.exact.match.boost"),
			_fullTextExactMatchBoost);

		_fullTextProximitySlop = GetterUtil.getInteger(
			properties.get("full.text.proximity.slop"), _fullTextProximitySlop);
	}

	protected Query createPhraseMatchQuery(String field, String value) {
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

	protected Query createQueryForFullTextExactMatch(
		String field, String value) {

		MatchQuery matchQuery = new MatchQuery(field, value);

		matchQuery.setType(MatchQuery.Type.PHRASE);

		matchQuery.setBoost(_fullTextExactMatchBoost);

		return matchQuery;
	}

	protected Query createQueryForFullTextProximity(
		String field, String value) {

		MatchQuery matchQuery = new MatchQuery(field, value);

		matchQuery.setType(MatchQuery.Type.PHRASE);

		matchQuery.setSlop(_fullTextProximitySlop);

		return matchQuery;
	}

	protected Query createQueryForFullTextScoring(String field, String value) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new MatchQuery(field, value), BooleanClauseOccur.MUST);

		booleanQueryImpl.add(
			createQueryForFullTextExactMatch(field, value),
			BooleanClauseOccur.SHOULD);

		booleanQueryImpl.add(
			createQueryForFullTextProximity(field, value),
			BooleanClauseOccur.SHOULD);

		List<String> phrases = getEmbeddedPhrases(value);

		for (String phrase : phrases) {
			booleanQueryImpl.add(
				createPhraseMatchQuery(field, phrase), BooleanClauseOccur.MUST);
		}

		return booleanQueryImpl;
	}

	protected Query createQueryForFullTextSearch(String field, String value) {
		Query query = createPhraseMatchQuery(field, value);

		if (query != null) {
			return query;
		}

		return createQueryForFullTextScoring(field, value);
	}

	protected Query createQueryForSubstringSearch(String field, String value) {
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

	protected Query createTokenQuery(String field, String value) {
		Query query = createPhraseMatchQuery(field, value);

		if (query != null) {
			return query;
		}

		return createQueryForSubstringSearch(field, value);
	}

	protected List<String> getEmbeddedPhrases(String value) {
		KeywordTokenizer keywordTokenizer = getKeywordTokenizer();

		if (keywordTokenizer == null) {
			return Collections.emptyList();
		}

		List<String> tokens = keywordTokenizer.tokenize(value);

		List<String> phrases = new ArrayList<>(tokens.size());

		for (String token : tokens) {
			if (isPhrase(token)) {
				phrases.add(token);
			}
		}

		return phrases;
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

	private KeywordTokenizer _defaultKeywordTokenizer;
	private volatile float _fullTextExactMatchBoost = 2.0f;
	private volatile int _fullTextProximitySlop = 50;
	private KeywordTokenizer _keywordTokenizer;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile QueryPreProcessConfiguration _queryPreProcessConfiguration;

}