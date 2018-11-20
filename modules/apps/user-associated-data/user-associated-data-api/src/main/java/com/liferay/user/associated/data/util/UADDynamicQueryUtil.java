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

package com.liferay.user.associated.data.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;

/**
 * @author Noah Sherrill
 */
public class UADDynamicQueryUtil {

	public static ActionableDynamicQuery addActionableDynamicQueryCriteria(
		ActionableDynamicQuery actionableDynamicQuery,
		final String[] userIdFieldNames, final long userId) {

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				_getCriterion(userIdFieldNames, userId)));

		return actionableDynamicQuery;
	}

	public static DynamicQuery addDynamicQueryCriteria(
		DynamicQuery dynamicQuery, String[] userIdFieldNames, long userId) {

		dynamicQuery.add(_getCriterion(userIdFieldNames, userId));

		return dynamicQuery;
	}

	private static Criterion _getCriterion(
		String[] userIdFieldNames, long userId) {

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		for (String userIdFieldName : userIdFieldNames) {
			disjunction.add(
				RestrictionsFactoryUtil.eq(userIdFieldName, userId));
		}

		return disjunction;
	}

}