/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.BaseModel;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ReverseTableMapping<L extends BaseModel<L>, R extends BaseModel<R>>
	implements TableMapping<L, R> {

	public ReverseTableMapping(TableMapping<R, L> tableMapping) {
		_tableMapping = tableMapping;
	}

	@Override
	public boolean addMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException {

		return _tableMapping.addMapping(rightPrimaryKey, leftPrimaryKey);
	}

	@Override
	public boolean containsMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException {

		return _tableMapping.containsMapping(rightPrimaryKey, leftPrimaryKey);
	}

	@Override
	public boolean deleteMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException {

		return _tableMapping.deleteMapping(rightPrimaryKey, leftPrimaryKey);
	}

	@Override
	public int deleteMappingsByLeftPrimaryKey(long leftPrimaryKey)
		throws SystemException {

		return _tableMapping.deleteMappingsByRightPrimaryKey(leftPrimaryKey);
	}

	@Override
	public int deleteMappingsByRightPrimaryKey(long rightPrimaryKey)
		throws SystemException {

		return _tableMapping.deleteMappingsByLeftPrimaryKey(rightPrimaryKey);
	}

	@Override
	public List<L> getLeftBaseModelsByRightPrimaryKey(
			long rightPrimaryKey, int start, int end, OrderByComparator obc)
		throws SystemException {

		return _tableMapping.getRightBaseModelsByLeftPrimaryKey(
			rightPrimaryKey, start, end, obc);
	}

	@Override
	public long[] getLeftPrimaryKeysByRightPrimaryKey(long rightPrimaryKey)
		throws SystemException {

		return _tableMapping.getRightPrimaryKeysByLeftPrimaryKey(
			rightPrimaryKey);
	}

	@Override
	public TableMapping<R, L> getReverseTableMapping() {
		return _tableMapping;
	}

	@Override
	public List<R> getRightBaseModelsByLeftPrimaryKey(
			long leftPrimaryKey, int start, int end, OrderByComparator obc)
		throws SystemException {

		return _tableMapping.getLeftBaseModelsByRightPrimaryKey(
			leftPrimaryKey, start, end, obc);
	}

	@Override
	public long[] getRightPrimaryKeysByLeftPrimaryKey(long leftPrimaryKey)
		throws SystemException {

		return _tableMapping.getLeftPrimaryKeysByRightPrimaryKey(
			leftPrimaryKey);
	}

	@Override
	public boolean matches(String leftColumnName, String rightColumnName) {
		return _tableMapping.matches(rightColumnName, leftColumnName);
	}

	private TableMapping<R, L> _tableMapping;

}