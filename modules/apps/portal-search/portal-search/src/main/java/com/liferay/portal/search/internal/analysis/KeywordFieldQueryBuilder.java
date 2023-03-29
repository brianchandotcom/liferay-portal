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
@Component(service = KeywordFieldQueryBuilder.class)
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