/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.analysis;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.search.analysis.FieldQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joshua Cords
 */
@Component(
	property = "query.builder.type=keyword", service = FieldQueryBuilder.class
)
public class KeywordFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String value) {
		BooleanQuery booleanQuery = new BooleanQuery();

		booleanQuery.add(
			new WildcardQuery(field, value + StringPool.STAR),
			BooleanClauseOccur.MUST);

		TermQuery termQuery = new TermQuery(field, value);

		if (_boost != null) {
			termQuery.setBoost(_boost);
		}

		booleanQuery.add(termQuery, BooleanClauseOccur.SHOULD);

		return booleanQuery;
	}

	public void setBoost(float boost) {
		_boost = boost;
	}

	private Float _boost;

}