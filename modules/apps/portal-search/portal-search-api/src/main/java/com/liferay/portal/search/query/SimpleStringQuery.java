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

package com.liferay.portal.search.query;

import aQute.bnd.annotation.ProviderType;

import java.util.Map;

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