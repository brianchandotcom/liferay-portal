/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		String[] userIdFieldNames, long userId) {

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