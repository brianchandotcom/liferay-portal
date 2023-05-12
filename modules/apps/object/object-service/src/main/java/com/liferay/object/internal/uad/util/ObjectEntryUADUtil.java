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

package com.liferay.object.internal.uad.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.user.associated.data.util.UADDynamicQueryUtil;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryUADUtil {

	public static ActionableDynamicQuery addActionableDynamicQueryCriteria(
		ActionableDynamicQuery actionableDynamicQuery,
		long objectDefinitionId) {

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> addDynamicQueryCriteria(
				dynamicQuery, objectDefinitionId));

		return actionableDynamicQuery;
	}

	public static ActionableDynamicQuery addActionableDynamicQueryCriteria(
		ActionableDynamicQuery actionableDynamicQuery,
		String[] userIdFieldNames, long userId) {

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				UADDynamicQueryUtil.addDynamicQueryCriteria(
					dynamicQuery, userIdFieldNames, userId);
			});

		return actionableDynamicQuery;
	}

	public static DynamicQuery addDynamicQueryCriteria(
		DynamicQuery dynamicQuery, long objectDefinitionId) {

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectDefinitionId", objectDefinitionId));

		return dynamicQuery;
	}

}