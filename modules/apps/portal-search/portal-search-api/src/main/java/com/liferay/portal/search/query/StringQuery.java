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
 * @author Bruno Farache
 * @author Petteri Karttunen
 */
@ProviderType
public interface StringQuery extends Query {

	public void addField(String field);

	public void addField(String field, Float boost);

	public Boolean getAllowLeadingWildcard();

	public String getAnalyzer();

	public Boolean getAnalyzeWildcard();

	public Boolean getAutoGenerateSynonymsPhraseQuery();

	public String getDefaultField();

	public Operator getDefaultOperator();

	public Boolean getEnablePositionIncrements();

	public Boolean getEscape();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getFieldsBoosts}
	 */
	@Deprecated
	public default Map<String, Float> getFields() {
		return getFieldsBoosts();
	}

	public Map<String, Float> getFieldsBoosts();

	public Float getFuzziness();

	public Integer getFuzzyMaxExpansions();

	public Integer getFuzzyPrefixLength();

	public String getFuzzyRewrite();

	public Boolean getFuzzyTranspositions();

	public Boolean getLenient();

	public Integer getMaxDeterminedStates();

	public String getMinimumShouldMatch();

	public Integer getPhraseSlop();

	public String getQuery();

	public String getQuoteAnalyzer();

	public String getQuoteFieldSuffix();

	public String getRewrite();

	public Float getTieBreaker();

	public String getTimeZone();

	public void setAllowLeadingWildcard(Boolean allowLeadingWildcard);

	public void setAnalyzer(String analyzer);

	public void setAnalyzeWildcard(Boolean analyzeWildcard);

	public void setAutoGenerateSynonymsPhraseQuery(
		Boolean autoGenerateSynonymsPhraseQuery);

	public void setDefaultField(String defaultField);

	public void setDefaultOperator(Operator defaultOperator);

	public void setEnablePositionIncrements(Boolean enablePositionIncrements);

	public void setEscape(boolean escape);

	public void setFuzziness(Float fuzziness);

	public void setFuzzyMaxExpansions(Integer fuzzyMaxExpansions);

	public void setFuzzyPrefixLength(Integer fuzzyPrefixLength);

	public void setFuzzyRewrite(String fuzzyRewrite);

	public void setFuzzyTranspositions(Boolean fuzzyTranspositions);

	public void setLenient(Boolean lenient);

	public void setMaxDeterminedStates(Integer maxDeterminedStates);

	public void setMinimumShouldMatch(String minimumShouldMatch);

	public void setPhraseSlop(Integer phraseSlop);

	public void setQuoteAnalyzer(String quoteAnalyzer);

	public void setQuoteFieldSuffix(String quoteFieldSuffix);

	public void setRewrite(String rewrite);

	public void setTieBreaker(float tieBreaker);

	public void setTimeZone(String timeZone);

}