/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides support for parsing raw, human readable query syntax. No
 * transformation is made on user input.
 *
 * <p>
 * The actual query syntax and any further processing are dependent on your
 * search engine's implementation details. Consult your search provider's
 * documentation for more information.
 * </p>
 *
 * @author Michael C. Han
 */
@ProviderType
public interface SimpleStringQuery extends Query {

	public void addField(String field, float boost);

	public void addFields(String... fields);

	public String getAnalyzer();

	public Boolean getAnalyzeWildcard();

	public Boolean getAutoGenerateSynonymsPhraseQuery();

	public Operator getDefaultOperator();

	public Map<String, Float> getFieldBoostMap();

	public Integer getFuzzyMaxExpansions();

	public Integer getFuzzyPrefixLength();

	public Boolean getFuzzyTranspositions();

	public Boolean getLenient();

	public String getQuery();

	public String getQuoteFieldSuffix();

	public void setAnalyzer(String analyzer);

	public void setAnalyzeWildcard(Boolean analyzeWildcard);

	public void setAutoGenerateSynonymsPhraseQuery(
		Boolean autoGenerateSynonymsPhraseQuery);

	public void setDefaultOperator(Operator defaultOperator);

	public void setFuzzyMaxExpansions(Integer fuzzyMaxExpansions);

	public void setFuzzyPrefixLength(Integer fuzzyPrefixLength);

	public void setFuzzyTranspositions(Boolean fuzzyTranspositions);

	public void setLenient(Boolean lenient);

	public void setQuoteFieldSuffix(String quoteFieldSuffix);

}