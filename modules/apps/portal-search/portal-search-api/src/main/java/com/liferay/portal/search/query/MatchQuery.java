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