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
public class ReverseMappingTable<L extends BaseModel<L>, R extends BaseModel<R>>
	implements MappingTable<L, R> {

	public ReverseMappingTable(MappingTable<R, L> mappingTable) {
		_mappingTable = mappingTable;
	}

	@Override
	public boolean addMapping(long leftPk, long rightPk)
		throws SystemException {

		return _mappingTable.addMapping(rightPk, leftPk);
	}

	@Override
	public boolean containsMapping(long leftPk, long rightPk)
		throws SystemException {

		return _mappingTable.containsMapping(rightPk, leftPk);
	}

	@Override
	public int deleteByLeftPk(long leftPk) throws SystemException {
		return _mappingTable.deleteByRightPk(leftPk);
	}

	@Override
	public int deleteByRightPk(long rightPk) throws SystemException {
		return _mappingTable.deleteByLeftPk(rightPk);
	}

	@Override
	public boolean deleteMapping(long leftPk, long rightPk)
		throws SystemException {

		return _mappingTable.deleteMapping(rightPk, leftPk);
	}

	@Override
	public long[] findByLeftPk(long leftPk) throws SystemException {
		return _mappingTable.findByRightPk(leftPk);
	}

	@Override
	public List<R> findByLeftPkAsEntities(
			long leftPk, int start, int end, OrderByComparator obc)
		throws SystemException {

		return _mappingTable.findByRightPkAsEntities(leftPk, start, end, obc);
	}

	@Override
	public long[] findByRightPk(long rightPk) throws SystemException {
		return _mappingTable.findByLeftPk(rightPk);
	}

	@Override
	public List<L> findByRightPkAsEntities(
			long rightPk, int start, int end, OrderByComparator obc)
		throws SystemException {

		return _mappingTable.findByLeftPkAsEntities(rightPk, start, end, obc);
	}

	@Override
	public MappingTable<R, L> getReverseMappingTable() {
		return _mappingTable;
	}

	@Override
	public boolean matches(String leftColumnName, String rightColumnName) {
		return _mappingTable.matches(rightColumnName, leftColumnName);
	}

	private MappingTable<R, L> _mappingTable;

}