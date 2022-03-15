/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.portlet;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.similar.results.web.internal.helper.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SimilarResultsPortletPreferencesImpl
	implements SimilarResultsPortletPreferences {

	public SimilarResultsPortletPreferencesImpl(
		Optional<PortletPreferences> optional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(optional);
	}

	@Override
	public String getAnalyzer() {
		return _getStringNullable(PREFERENCE_KEY_ANALYZER);
	}

	@Override
	public String getDocType() {
		return _getStringNullable(PREFERENCE_KEY_DOC_TYPE);
	}

	@Override
	public String getFederatedSearchKey() {
		return _portletPreferencesHelper.getString(
			PREFERENCE_KEY_FEDERATED_SEARCH_KEY, "morelikethis");
	}

	@Override
	public String getFields() {
		return _getStringNullable(PREFERENCE_KEY_FIELDS);
	}

	@Override
	public String getIndexName() {
		return _getStringNullable(PREFERENCE_KEY_INDEX_NAME);
	}

	@Override
	public String getLinkBehavior() {
		return _portletPreferencesHelper.getString(
			PREFERENCE_KEY_LINK_BEHAVIOR, "show-content");
	}

	@Override
	public Integer getMaxDocFrequency() {
		return _getIntegerNullable(PREFERENCE_KEY_MAX_DOC_FREQUENCY);
	}

	@Override
	public Integer getMaxItemDisplay() {
		return _portletPreferencesHelper.getInteger(
			PREFERENCE_KEY_MAX_ITEM_DISPLAY, 10);
	}

	@Override
	public Integer getMaxQueryTerms() {
		return _getIntegerNullable(PREFERENCE_KEY_MAX_QUERY_TERMS);
	}

	@Override
	public Integer getMaxWordLength() {
		return _getIntegerNullable(PREFERENCE_KEY_MAX_WORD_LENGTH);
	}

	@Override
	public Integer getMinDocFrequency() {
		return _getIntegerNullable(PREFERENCE_KEY_MIN_DOC_FREQUENCY);
	}

	@Override
	public String getMinShouldMatch() {
		return _getStringNullable(PREFERENCE_KEY_MIN_SHOULD_MATCH);
	}

	@Override
	public Integer getMinTermFrequency() {
		return _getIntegerNullable(PREFERENCE_KEY_MIN_TERM_FREQUENCY);
	}

	@Override
	public Integer getMinWordLength() {
		return _getIntegerNullable(PREFERENCE_KEY_MIN_WORD_LENGTH);
	}

	@Override
	public String getSearchScope() {
		return _portletPreferencesHelper.getString(
			PREFERENCE_KEY_SEARCH_SCOPE, "this-site");
	}

	@Override
	public String getStopWords() {
		return _getStringNullable(PREFERENCE_KEY_STOP_WORDS);
	}

	@Override
	public Float getTermBoost() {
		Optional<String> optional = _portletPreferencesHelper.getString(
			PREFERENCE_KEY_TERM_BOOST);

		return optional.map(
			GetterUtil::getFloat
		).orElse(
			null
		);
	}

	private Integer _getIntegerNullable(String key) {
		Optional<Integer> optional = _portletPreferencesHelper.getInteger(key);

		return optional.orElse(null);
	}

	private String _getStringNullable(String key) {
		return _portletPreferencesHelper.getString(key, null);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}