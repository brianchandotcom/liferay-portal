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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.BaseModel;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ReverseTableMapper<L extends BaseModel<L>, R extends BaseModel<R>>
	implements TableMapper<L, R> {

	public ReverseTableMapper(TableMapper<R, L> tableMapper) {
		_tableMapper = tableMapper;
	}

	@Override
	public boolean addTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey) {

		return _tableMapper.addTableMapping(
			companyId, rightPrimaryKey, leftPrimaryKey);
	}

	@Override
	public boolean containsTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey) {

		return _tableMapper.containsTableMapping(
			companyId, rightPrimaryKey, leftPrimaryKey);
	}

	@Override
	public int deleteLeftPrimaryKeyTableMappings(
		long companyId, long leftPrimaryKey) {

		return _tableMapper.deleteRightPrimaryKeyTableMappings(
			companyId, leftPrimaryKey);
	}

	@Override
	public int deleteRightPrimaryKeyTableMappings(
		long companyId, long rightPrimaryKey) {

		return _tableMapper.deleteLeftPrimaryKeyTableMappings(
			companyId, rightPrimaryKey);
	}

	@Override
	public boolean deleteTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey) {

		return _tableMapper.deleteTableMapping(
			companyId, rightPrimaryKey, leftPrimaryKey);
	}

	@Override
	public void destroy() {
		_tableMapper.destroy();
	}

	@Override
	public List<L> getLeftBaseModels(
		long companyId, long rightPrimaryKey, int start, int end,
		OrderByComparator<L> obc) {

		return _tableMapper.getRightBaseModels(
			companyId, rightPrimaryKey, start, end, obc);
	}

	@Override
	public long[] getLeftPrimaryKeys(long companyId, long rightPrimaryKey) {
		return _tableMapper.getRightPrimaryKeys(companyId, rightPrimaryKey);
	}

	@Override
	public TableMapper<R, L> getReverseTableMapper() {
		return _tableMapper;
	}

	@Override
	public List<R> getRightBaseModels(
		long companyId, long leftPrimaryKey, int start, int end,
		OrderByComparator<R> obc) {

		return _tableMapper.getLeftBaseModels(
			companyId, leftPrimaryKey, start, end, obc);
	}

	@Override
	public long[] getRightPrimaryKeys(long companyId, long leftPrimaryKey) {
		return _tableMapper.getLeftPrimaryKeys(companyId, leftPrimaryKey);
	}

	@Override
	public boolean matches(String leftColumnName, String rightColumnName) {
		return _tableMapper.matches(rightColumnName, leftColumnName);
	}

	private final TableMapper<R, L> _tableMapper;

}