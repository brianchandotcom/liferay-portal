/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query.field;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;

/**
 * @author André de Oliveira
 */
public class PhraseQueryBuilder {

	public PhraseQueryBuilder(Queries queries) {
		_queries = queries;
	}

	public Query build(String field, String value) {
		MatchQuery.Type type = MatchQuery.Type.PHRASE;

		if (_prefix) {
			type = MatchQuery.Type.PHRASE_PREFIX;
		}

		if (_trailingStarAware && value.endsWith(StringPool.STAR)) {
			value = value.substring(0, value.length() - 1);

			type = MatchQuery.Type.PHRASE_PREFIX;
		}

		MatchQuery matchQuery = _queries.match(field, value);

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
	private final Queries _queries;
	private Integer _slop;
	private boolean _trailingStarAware;

}