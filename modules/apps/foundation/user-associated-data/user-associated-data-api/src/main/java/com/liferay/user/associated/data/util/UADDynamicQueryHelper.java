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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;

import java.util.List;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noah Sherrill
 */
@Component(immediate = true, service = UADDynamicQueryHelper.class)
public class UADDynamicQueryHelper {

	public ActionableDynamicQuery getActionableDynamicQuery(
		Supplier<ActionableDynamicQuery> actionableDynamicQuerySupplier,
		final List<String> userIdFieldNames, final long userId) {

		ActionableDynamicQuery actionableDynamicQuery =
			actionableDynamicQuerySupplier.get();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(_getCriterion(userIdFieldNames, userId));
				}

			});

		return actionableDynamicQuery;
	}

	public DynamicQuery getDynamicQuery(
		Supplier<DynamicQuery> dynamicQuerySupplier,
		List<String> userIdFieldNames, long userId) {

		DynamicQuery dynamicQuery = dynamicQuerySupplier.get();

		dynamicQuery.add(_getCriterion(userIdFieldNames, userId));

		return dynamicQuery;
	}

	private Criterion _getCriterion(
		List<String> userIdFieldNames, long userId) {

		Criterion criterion = RestrictionsFactoryUtil.eq(
			userIdFieldNames.get(0), userId);

		for (int i = 1; i < userIdFieldNames.size(); i++) {
			Criterion userIdCriterion = RestrictionsFactoryUtil.eq(
				userIdFieldNames.get(i), userId);

			criterion = RestrictionsFactoryUtil.or(criterion, userIdCriterion);
		}

		return criterion;
	}

}