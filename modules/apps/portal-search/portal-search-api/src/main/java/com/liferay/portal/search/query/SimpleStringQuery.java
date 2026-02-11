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
public abstract class SimpleStringQuery extends Query {

	public abstract void addField(String field, float boost);

	public abstract void addFields(String... fields);

	public abstract String getAnalyzer();

	public abstract Boolean getAnalyzeWildcard();

	public abstract Boolean getAutoGenerateSynonymsPhraseQuery();

	public abstract Operator getDefaultOperator();

	public abstract Map<String, Float> getFieldBoostMap();

	public abstract Integer getFuzzyMaxExpansions();

	public abstract Integer getFuzzyPrefixLength();

	public abstract Boolean getFuzzyTranspositions();

	public abstract Boolean getLenient();

	public abstract String getQuery();

	public abstract String getQuoteFieldSuffix();

	public abstract void setAnalyzer(String analyzer);

	public abstract void setAnalyzeWildcard(Boolean analyzeWildcard);

	public abstract void setAutoGenerateSynonymsPhraseQuery(
		Boolean autoGenerateSynonymsPhraseQuery);

	public abstract void setDefaultOperator(Operator defaultOperator);

	public abstract void setFuzzyMaxExpansions(Integer fuzzyMaxExpansions);

	public abstract void setFuzzyPrefixLength(Integer fuzzyPrefixLength);

	public abstract void setFuzzyTranspositions(Boolean fuzzyTranspositions);

	public abstract void setLenient(Boolean lenient);

	public abstract void setQuoteFieldSuffix(String quoteFieldSuffix);

}