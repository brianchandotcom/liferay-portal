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
public interface TableMapper<L extends BaseModel<L>, R extends BaseModel<R>> {

	public boolean addTableMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException;

	public boolean containsTableMapping(
			long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException;

	public int deleteLeftPrimaryKeyTableMappings(long leftPrimaryKey)
		throws SystemException;

	public int deleteRightPrimaryKeyTableMappings(long rightPrimaryKey)
		throws SystemException;

	public boolean deleteTableMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException;

	public void destroy();

	public List<L> getLeftBaseModels(
			long rightPrimaryKey, int start, int end, OrderByComparator obc)
		throws SystemException;

	public long[] getLeftPrimaryKeys(long rightPrimaryKey)
		throws SystemException;

	public TableMapper<R, L> getReverseTableMapper();

	public List<R> getRightBaseModels(
			long leftPrimaryKey, int start, int end, OrderByComparator obc)
		throws SystemException;

	public long[] getRightPrimaryKeys(long leftPrimaryKey)
		throws SystemException;

	public boolean matches(String leftColumnName, String rightColumnName);

}