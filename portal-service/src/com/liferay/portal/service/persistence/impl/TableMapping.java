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
 *
 * @author Shuyang Zhou
 */
public interface TableMapping<L extends BaseModel<L>, R extends BaseModel<R>> {

	public boolean addMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException;

	public boolean containsMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException;

	public boolean deleteMapping(long leftPrimaryKey, long rightPrimaryKey)
		throws SystemException;

	public int deleteMappingsByLeftPrimaryKey(long leftPrimaryKey)
		throws SystemException;

	public int deleteMappingsByRightPrimaryKey(long rightPrimaryKey)
		throws SystemException;

	public List<L> getLeftBaseModelsByRightPrimaryKey(
			long rightPrimaryKey, int start, int end, OrderByComparator obc)
		throws SystemException;

	public long[] getLeftPrimaryKeysByRightPrimaryKey(long rightPrimaryKey)
		throws SystemException;

	public TableMapping<R, L> getReverseTableMapping();

	public List<R> getRightBaseModelsByLeftPrimaryKey(
			long leftPrimaryKey, int start, int end, OrderByComparator obc)
		throws SystemException;

	public long[] getRightPrimaryKeysByLeftPrimaryKey(long leftPrimaryKey)
		throws SystemException;

	public boolean matches(String leftColumnName, String rightColumnName);

}