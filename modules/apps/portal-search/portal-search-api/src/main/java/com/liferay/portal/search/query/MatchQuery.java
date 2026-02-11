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
public abstract class MatchQuery extends Query {

	public abstract String getAnalyzer();

	public abstract Float getCutOffFrequency();

	public abstract String getField();

	public abstract Float getFuzziness();

	public abstract RewriteMethod getFuzzyRewriteMethod();

	public abstract Integer getMaxExpansions();

	public abstract String getMinShouldMatch();

	public abstract Operator getOperator();

	public abstract Integer getPrefixLength();

	public abstract Integer getSlop();

	public abstract Type getType();

	public abstract Object getValue();

	public abstract ZeroTermsQuery getZeroTermsQuery();

	public abstract Boolean isFuzzyTranspositions();

	public abstract Boolean isLenient();

	public abstract void setAnalyzer(String analyzer);

	public abstract void setCutOffFrequency(Float cutOffFrequency);

	public abstract void setFuzziness(Float fuzziness);

	public abstract void setFuzzyRewriteMethod(
		RewriteMethod fuzzyRewriteMethod);

	public abstract void setFuzzyTranspositions(Boolean fuzzyTranspositions);

	public abstract void setLenient(Boolean lenient);

	public abstract void setMaxExpansions(Integer maxExpansions);

	public abstract void setMinShouldMatch(String minShouldMatch);

	public abstract void setOperator(Operator operator);

	public abstract void setPrefixLength(Integer prefixLength);

	public abstract void setSlop(Integer slop);

	public abstract void setType(Type type);

	public abstract void setZeroTermsQuery(ZeroTermsQuery zeroTermsQuery);

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