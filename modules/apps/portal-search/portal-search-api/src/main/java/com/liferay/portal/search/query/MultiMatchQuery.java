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

import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface MultiMatchQuery extends Query {

	public String getAnalyzer();

	public Float getCutOffFrequency();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getFieldsBoosts()}
	 */
	@Deprecated
	public Set<String> getFields();

	public Map<String, Float> getFieldsBoosts();

	public String getFuzziness();

	public MatchQuery.RewriteMethod getFuzzyRewriteMethod();

	public Integer getMaxExpansions();

	public String getMinShouldMatch();

	public Operator getOperator();

	public Integer getPrefixLength();

	public Integer getSlop();

	public Float getTieBreaker();

	public Type getType();

	public Object getValue();

	public MatchQuery.ZeroTermsQuery getZeroTermsQuery();

	public boolean isFieldBoostsEmpty();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #isFieldBoostsEmpty()}
	 */
	@Deprecated
	public boolean isFieldsEmpty();

	public Boolean isLenient();

	public void setAnalyzer(String analyzer);

	public void setCutOffFrequency(Float cutOffFrequency);

	public void setFuzziness(String fuzziness);

	public void setFuzzyRewriteMethod(
		MatchQuery.RewriteMethod fuzzyRewriteMethod);

	public void setLenient(Boolean lenient);

	public void setMaxExpansions(Integer maxExpansions);

	public void setMinShouldMatch(String minShouldMatch);

	public void setOperator(Operator operator);

	public void setPrefixLength(Integer prefixLength);

	public void setSlop(Integer slop);

	public void setTieBreaker(Float tieBreaker);

	public void setType(Type type);

	public void setZeroTermsQuery(MatchQuery.ZeroTermsQuery zeroTermsQuery);

	public enum Type {

		BEST_FIELDS, CROSS_FIELDS, MOST_FIELDS, PHRASE, PHRASE_PREFIX

	}

}