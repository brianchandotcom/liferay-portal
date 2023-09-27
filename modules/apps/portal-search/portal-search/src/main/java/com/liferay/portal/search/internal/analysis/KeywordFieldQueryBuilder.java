/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.analysis;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.search.analysis.FieldQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joshua Cords
 */
@Component(
	property = "query.builder.type=keyword", service = FieldQueryBuilder.class
)
public class KeywordFieldQueryBuilder implements FieldQueryBuilder {

	public Query build(String field, String value) {
		try {
			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.add(
				new WildcardQueryImpl(field, value + StringPool.STAR),
				BooleanClauseOccur.MUST);

			TermQuery termQuery = new TermQueryImpl(field, value);

			if (_boost != null) {
				termQuery.setBoost(_boost);
			}

			booleanQuery.add(termQuery, BooleanClauseOccur.SHOULD);

			return booleanQuery;
		}
		catch (ParseException parseException) {
			throw new SystemException(parseException);
		}
	}

	public void setBoost(float boost) {
		_boost = boost;
	}

	private Float _boost;

}