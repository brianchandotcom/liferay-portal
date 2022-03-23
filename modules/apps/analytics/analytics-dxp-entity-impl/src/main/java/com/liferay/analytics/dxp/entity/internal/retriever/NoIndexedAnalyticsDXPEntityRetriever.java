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

package com.liferay.analytics.dxp.entity.internal.retriever;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

/**
 * @author Marcos Martins
 */
public abstract class NoIndexedAnalyticsDXPEntityRetriever {

	protected DynamicQuery buildDynamicQuery(
		long companyId, DynamicQuery dynamicQuery, Filter filter) {

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));

		if (filter instanceof QueryFilter) {
			QueryFilter queryFilter = (QueryFilter)filter;

			Query query = queryFilter.getQuery();

			if (query instanceof TermRangeQuery) {
				TermRangeQuery termRangeQuery = (TermRangeQuery)query;

				if (StringUtil.startsWith(
						termRangeQuery.getField(), "modified")) {

					String lowerTerm = termRangeQuery.getLowerTerm();

					if ((lowerTerm != null) && Validator.isNumber(lowerTerm)) {
						dynamicQuery.add(
							RestrictionsFactoryUtil.gt(
								"modifiedDate",
								new Date(GetterUtil.getLong(lowerTerm))));
					}

					String upperTerm = termRangeQuery.getUpperTerm();

					if ((upperTerm != null) && Validator.isNumber(upperTerm)) {
						dynamicQuery.add(
							RestrictionsFactoryUtil.le(
								"modifiedDate",
								new Date(GetterUtil.getLong(upperTerm))));
					}
				}
			}
		}

		return dynamicQuery;
	}

}