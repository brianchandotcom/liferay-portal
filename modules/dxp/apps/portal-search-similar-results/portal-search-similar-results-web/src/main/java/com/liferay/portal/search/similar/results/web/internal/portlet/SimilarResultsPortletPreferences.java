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

/**
 * @author Wade Cao
 */
public interface SimilarResultsPortletPreferences {

	public static final String PREFERENCE_KEY_ANALYZER = "analyzer";

	public static final String PREFERENCE_KEY_DOC_TYPE = "docType";

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public static final String PREFERENCE_KEY_FIELDS = "fields";

	public static final String PREFERENCE_KEY_INDEX_NAME = "indexName";

	public static final String PREFERENCE_KEY_LINK_BEHAVIOR = "linkBehavior";

	public static final String PREFERENCE_KEY_MAX_DOC_FREQUENCY =
		"maxDocFrequency";

	public static final String PREFERENCE_KEY_MAX_ITEM_DISPLAY =
		"maxItemDisplay";

	public static final String PREFERENCE_KEY_MAX_QUERY_TERMS = "maxQueryTerms";

	public static final String PREFERENCE_KEY_MAX_WORD_LENGTH = "maxWordLength";

	public static final String PREFERENCE_KEY_MIN_DOC_FREQUENCY =
		"minDocFrequency";

	public static final String PREFERENCE_KEY_MIN_SHOULD_MATCH =
		"minShouldMatch";

	public static final String PREFERENCE_KEY_MIN_TERM_FREQUENCY =
		"minTermFrequency";

	public static final String PREFERENCE_KEY_MIN_WORD_LENGTH = "minWordLength";

	public static final String PREFERENCE_KEY_SEARCH_SCOPE = "searchScope";

	public static final String PREFERENCE_KEY_STOP_WORDS = "stopWords";

	public static final String PREFERENCE_KEY_TERM_BOOST = "termBoost";

	public String getAnalyzer();

	public String getDocType();

	public String getFederatedSearchKey();

	public String getFields();

	public String getIndexName();

	public String getLinkBehavior();

	public Integer getMaxDocFrequency();

	public Integer getMaxItemDisplay();

	public Integer getMaxQueryTerms();

	public Integer getMaxWordLength();

	public Integer getMinDocFrequency();

	public String getMinShouldMatch();

	public Integer getMinTermFrequency();

	public Integer getMinWordLength();

	public String getSearchScope();

	public String getStopWords();

	public Float getTermBoost();

}