/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface MatchQuery extends Query {

	public String getAnalyzer();

	public Float getCutOffFrequency();

	public String getField();

	public Float getFuzziness();

	public RewriteMethod getFuzzyRewriteMethod();

	public Integer getMaxExpansions();

	public String getMinShouldMatch();

	public Operator getOperator();

	public Integer getPrefixLength();

	public Integer getSlop();

	public Type getType();

	public Object getValue();

	public ZeroTermsQuery getZeroTermsQuery();

	public Boolean isFuzzyTranspositions();

	public Boolean isLenient();

	public void setAnalyzer(String analyzer);

	public void setCutOffFrequency(Float cutOffFrequency);

	public void setFuzziness(Float fuzziness);

	public void setFuzzyRewriteMethod(RewriteMethod fuzzyRewriteMethod);

	public void setFuzzyTranspositions(Boolean fuzzyTranspositions);

	public void setLenient(Boolean lenient);

	public void setMaxExpansions(Integer maxExpansions);

	public void setMinShouldMatch(String minShouldMatch);

	public void setOperator(Operator operator);

	public void setPrefixLength(Integer prefixLength);

	public void setSlop(Integer slop);

	public void setType(Type type);

	public void setZeroTermsQuery(ZeroTermsQuery zeroTermsQuery);

	public enum RewriteMethod {

		CONSTANT_SCORE_AUTO, CONSTANT_SCORE_BOOLEAN, CONSTANT_SCORE_FILTER,
		SCORING_BOOLEAN, TOP_TERMS_BOOST_N, TOP_TERMS_N

	}

	public enum Type {

		BOOLEAN, PHRASE, PHRASE_PREFIX

	}

	public enum ZeroTermsQuery {

		ALL, NONE

	}

}