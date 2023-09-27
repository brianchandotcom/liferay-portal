/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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