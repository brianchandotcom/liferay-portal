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

package com.liferay.portal.search.internal.analysis;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.MatchQuery;

/**
 * @author André de Oliveira
 */
public class PhraseQueryBuilder {

	public Query build(String field, String value) {
		MatchQuery.Type type = MatchQuery.Type.PHRASE;

		if (_prefix) {
			type = MatchQuery.Type.PHRASE_PREFIX;
		}

		if (_trailingStarAware && value.endsWith(StringPool.STAR)) {
			value = value.substring(0, value.length() - 1);

			type = MatchQuery.Type.PHRASE_PREFIX;
		}

		MatchQuery matchQuery = new MatchQuery(field, value);

		matchQuery.setType(type);

		if (_boost != null) {
			matchQuery.setBoost(_boost);
		}

		if (_prefix && (_maxExpansions != null)) {
			matchQuery.setMaxExpansions(_maxExpansions);
		}

		if (_slop != null) {
			matchQuery.setSlop(_slop);
		}

		return matchQuery;
	}

	public void setBoost(float boost) {
		_boost = boost;
	}

	public void setMaxExpansions(int maxExpansions) {
		_maxExpansions = maxExpansions;
	}

	public void setPrefix(boolean prefix) {
		_prefix = prefix;
	}

	public void setSlop(int slop) {
		_slop = slop;
	}

	public void setTrailingStarAware(boolean trailingStarAware) {
		_trailingStarAware = trailingStarAware;
	}

	private Float _boost;
	private Integer _maxExpansions;
	private boolean _prefix;
	private Integer _slop;
	private boolean _trailingStarAware;

}