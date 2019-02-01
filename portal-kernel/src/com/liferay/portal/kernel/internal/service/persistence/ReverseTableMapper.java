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

package com.liferay.portal.kernel.internal.service.persistence;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.util.OrderByComparator;

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
	public long[] addTableMappings(
		long companyId, long leftPrimaryKey, long[] rightPrimaryKeys) {

		return _tableMapper.addTableMappings(
			companyId, rightPrimaryKeys, leftPrimaryKey);
	}

	@Override
	public long[] addTableMappings(
		long companyId, long[] leftPrimaryKeys, long rightPrimaryKey) {

		return _tableMapper.addTableMappings(
			companyId, rightPrimaryKey, leftPrimaryKeys);
	}

	@Override
	public boolean containsTableMapping(
		long leftPrimaryKey, long rightPrimaryKey) {

		return _tableMapper.containsTableMapping(
			rightPrimaryKey, leftPrimaryKey);
	}

	@Override
	public int deleteLeftPrimaryKeyTableMappings(long leftPrimaryKey) {
		return _tableMapper.deleteRightPrimaryKeyTableMappings(leftPrimaryKey);
	}

	@Override
	public int deleteRightPrimaryKeyTableMappings(long rightPrimaryKey) {
		return _tableMapper.deleteLeftPrimaryKeyTableMappings(rightPrimaryKey);
	}

	@Override
	public boolean deleteTableMapping(
		long leftPrimaryKey, long rightPrimaryKey) {

		return _tableMapper.deleteTableMapping(rightPrimaryKey, leftPrimaryKey);
	}

	@Override
	public long[] deleteTableMappings(
		long leftPrimaryKey, long[] rightPrimaryKeys) {

		return _tableMapper.deleteTableMappings(
			rightPrimaryKeys, leftPrimaryKey);
	}

	@Override
	public long[] deleteTableMappings(
		long[] leftPrimaryKeys, long rightPrimaryKey) {

		return _tableMapper.deleteTableMappings(
			rightPrimaryKey, leftPrimaryKeys);
	}

	@Override
	public void destroy() {
		_tableMapper.destroy();
	}

	@Override
	public List<L> getLeftBaseModels(
		long rightPrimaryKey, int start, int end, OrderByComparator<L> obc) {

		return _tableMapper.getRightBaseModels(
			rightPrimaryKey, start, end, obc);
	}

	@Override
	public long[] getLeftPrimaryKeys(long rightPrimaryKey) {
		return _tableMapper.getRightPrimaryKeys(rightPrimaryKey);
	}

	@Override
	public TableMapper<R, L> getReverseTableMapper() {
		return _tableMapper;
	}

	@Override
	public List<R> getRightBaseModels(
		long leftPrimaryKey, int start, int end, OrderByComparator<R> obc) {

		return _tableMapper.getLeftBaseModels(leftPrimaryKey, start, end, obc);
	}

	@Override
	public long[] getRightPrimaryKeys(long leftPrimaryKey) {
		return _tableMapper.getLeftPrimaryKeys(leftPrimaryKey);
	}

	@Override
	public boolean matches(String leftColumnName, String rightColumnName) {
		return _tableMapper.matches(rightColumnName, leftColumnName);
	}

	private final TableMapper<R, L> _tableMapper;

}