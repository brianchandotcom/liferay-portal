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

package com.liferay.user.associated.data.aggregator;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.user.associated.data.util.UADDynamicQueryUtil;

import java.io.Serializable;

import java.util.List;

/**
 * @author Drew Brokke
 */
public abstract class DynamicQueryUADAggregator<T extends BaseModel>
	implements UADAggregator<T> {

	@Override
	public long count(long userId) {
		return doCount(_getDynamicQuery(userId));
	}

	@Override
	public List<T> getAll(long userId) {
		return getRange(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public Serializable getPrimaryKey(T baseModel) {
		return baseModel.getPrimaryKeyObj();
	}

	@Override
	public List<T> getRange(long userId, int start, int end) {
		return doGetRange(_getDynamicQuery(userId), start, end);
	}

	protected abstract long doCount(DynamicQuery dynamicQuery);

	protected abstract DynamicQuery doGetDynamicQuery();

	protected abstract List<T> doGetRange(
		DynamicQuery dynamicQuery, int start, int end);

	protected abstract String[] doGetUserIdFieldNames();

	private DynamicQuery _getDynamicQuery(long userId) {
		return UADDynamicQueryUtil.addDynamicQueryCriteria(
			doGetDynamicQuery(), doGetUserIdFieldNames(), userId);
	}

}