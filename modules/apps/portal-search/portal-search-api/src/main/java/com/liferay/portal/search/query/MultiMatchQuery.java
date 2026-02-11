/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class MultiMatchQuery extends Query {

	public abstract String getAnalyzer();

	public abstract Float getCutOffFrequency();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getFieldsBoosts()}
	 */
	@Deprecated
	public abstract Set<String> getFields();

	public abstract Map<String, Float> getFieldsBoosts();

	public abstract String getFuzziness();

	public abstract MatchQuery.RewriteMethod getFuzzyRewriteMethod();

	public abstract Integer getMaxExpansions();

	public abstract String getMinShouldMatch();

	public abstract Operator getOperator();

	public abstract Integer getPrefixLength();

	public abstract Integer getSlop();

	public abstract Float getTieBreaker();

	public abstract Type getType();

	public abstract Object getValue();

	public abstract MatchQuery.ZeroTermsQuery getZeroTermsQuery();

	public abstract boolean isFieldBoostsEmpty();

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #isFieldBoostsEmpty()}
	 */
	@Deprecated
	public abstract boolean isFieldsEmpty();

	public abstract Boolean isLenient();

	public abstract void setAnalyzer(String analyzer);

	public abstract void setCutOffFrequency(Float cutOffFrequency);

	public abstract void setFuzziness(String fuzziness);

	public abstract void setFuzzyRewriteMethod(
		MatchQuery.RewriteMethod fuzzyRewriteMethod);

	public abstract void setLenient(Boolean lenient);

	public abstract void setMaxExpansions(Integer maxExpansions);

	public abstract void setMinShouldMatch(String minShouldMatch);

	public abstract void setOperator(Operator operator);

	public abstract void setPrefixLength(Integer prefixLength);

	public abstract void setSlop(Integer slop);

	public abstract void setTieBreaker(Float tieBreaker);

	public abstract void setType(Type type);

	public abstract void setZeroTermsQuery(
		MatchQuery.ZeroTermsQuery zeroTermsQuery);

	public enum Type {

		BEST_FIELDS, BOOL_PREFIX, CROSS_FIELDS, MOST_FIELDS, PHRASE,
		PHRASE_PREFIX

	}

}