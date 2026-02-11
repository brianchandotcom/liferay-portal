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
public abstract class StringQuery extends Query {

	public abstract void addField(String field);

	public abstract void addField(String field, Float boost);

	public abstract Boolean getAllowLeadingWildcard();

	public abstract String getAnalyzer();

	public abstract Boolean getAnalyzeWildcard();

	public abstract Boolean getAutoGenerateSynonymsPhraseQuery();

	public abstract String getDefaultField();

	public abstract Operator getDefaultOperator();

	public abstract Boolean getEnablePositionIncrements();

	public abstract Boolean getEscape();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getFieldsBoosts}
	 */
	@Deprecated
	public Map<String, Float> getFields() {
		return getFieldsBoosts();
	}

	public abstract Map<String, Float> getFieldsBoosts();

	public abstract Float getFuzziness();

	public abstract Integer getFuzzyMaxExpansions();

	public abstract Integer getFuzzyPrefixLength();

	public abstract String getFuzzyRewrite();

	public abstract Boolean getFuzzyTranspositions();

	public abstract Boolean getLenient();

	public abstract Integer getMaxDeterminedStates();

	public abstract String getMinimumShouldMatch();

	public abstract Integer getPhraseSlop();

	public abstract String getQuery();

	public abstract String getQuoteAnalyzer();

	public abstract String getQuoteFieldSuffix();

	public abstract String getRewrite();

	public abstract Float getTieBreaker();

	public abstract String getTimeZone();

	public abstract void setAllowLeadingWildcard(Boolean allowLeadingWildcard);

	public abstract void setAnalyzer(String analyzer);

	public abstract void setAnalyzeWildcard(Boolean analyzeWildcard);

	public abstract void setAutoGenerateSynonymsPhraseQuery(
		Boolean autoGenerateSynonymsPhraseQuery);

	public abstract void setDefaultField(String defaultField);

	public abstract void setDefaultOperator(Operator defaultOperator);

	public abstract void setEnablePositionIncrements(
		Boolean enablePositionIncrements);

	public abstract void setEscape(boolean escape);

	public abstract void setFuzziness(Float fuzziness);

	public abstract void setFuzzyMaxExpansions(Integer fuzzyMaxExpansions);

	public abstract void setFuzzyPrefixLength(Integer fuzzyPrefixLength);

	public abstract void setFuzzyRewrite(String fuzzyRewrite);

	public abstract void setFuzzyTranspositions(Boolean fuzzyTranspositions);

	public abstract void setLenient(Boolean lenient);

	public abstract void setMaxDeterminedStates(Integer maxDeterminedStates);

	public abstract void setMinimumShouldMatch(String minimumShouldMatch);

	public abstract void setPhraseSlop(Integer phraseSlop);

	public abstract void setQuoteAnalyzer(String quoteAnalyzer);

	public abstract void setQuoteFieldSuffix(String quoteFieldSuffix);

	public abstract void setRewrite(String rewrite);

	public abstract void setTieBreaker(float tieBreaker);

	public abstract void setTimeZone(String timeZone);

}