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

package com.liferay.user.associated.data.anonymizer;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.user.associated.data.util.UADDynamicQueryUtil;

/**
 * @author William Newbury
 */
public abstract class DynamicQueryUADEntityAnonymizer<T extends BaseModel>
	implements UADEntityAnonymizer<T> {

	@Override
	public void autoAnonymizeAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			(T entity) -> autoAnonymize(entity, userId));

		actionableDynamicQuery.performActions();
	}

	@Override
	public void deleteAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			(T entity) -> delete(entity));

		actionableDynamicQuery.performActions();
	}

	protected abstract ActionableDynamicQuery doGetActionableDynamicQuery();

	protected abstract String[] doGetUserIdFieldNames();

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return UADDynamicQueryUtil.addActionableDynamicQueryCriteria(
			doGetActionableDynamicQuery(), doGetUserIdFieldNames(), userId);
	}

}