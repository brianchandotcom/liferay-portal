/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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