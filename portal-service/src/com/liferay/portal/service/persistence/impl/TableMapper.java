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
public interface TableMapper<L extends BaseModel<L>, R extends BaseModel<R>> {

	public boolean addTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey);

	public boolean containsTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey);

	public int deleteLeftPrimaryKeyTableMappings(
		long companyId, long leftPrimaryKey);

	public int deleteRightPrimaryKeyTableMappings(
		long companyId, long rightPrimaryKey);

	public boolean deleteTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey);

	public void destroy();

	public List<L> getLeftBaseModels(
		long companyId, long rightPrimaryKey, int start, int end,
		OrderByComparator<L> obc);

	public long[] getLeftPrimaryKeys(long companyId, long rightPrimaryKey);

	public TableMapper<R, L> getReverseTableMapper();

	public List<R> getRightBaseModels(
		long companyId, long leftPrimaryKey, int start, int end,
		OrderByComparator<R> obc);

	public long[] getRightPrimaryKeys(long companyId, long leftPrimaryKey);

	public boolean matches(String leftColumnName, String rightColumnName);

}