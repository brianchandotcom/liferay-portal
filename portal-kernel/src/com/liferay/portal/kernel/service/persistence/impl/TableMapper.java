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

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Shuyang Zhou
 */
@ProviderType
public interface TableMapper<L extends BaseModel<L>, R extends BaseModel<R>> {

	public boolean addTableMapping(
		long companyId, long leftPrimaryKey, long rightPrimaryKey);

	public long[] addTableMappings(
		long companyId, long leftPrimaryKey, long[] rightPrimaryKeys);

	public long[] addTableMappings(
		long companyId, long[] leftPrimaryKeys, long rightPrimaryKey);

	public boolean containsTableMapping(
		long leftPrimaryKey, long rightPrimaryKey);

	public int deleteLeftPrimaryKeyTableMappings(long leftPrimaryKey);

	public int deleteRightPrimaryKeyTableMappings(long rightPrimaryKey);

	public boolean deleteTableMapping(
		long leftPrimaryKey, long rightPrimaryKey);

	public long[] deleteTableMappings(
		long leftPrimaryKey, long[] rightPrimaryKeys);

	public long[] deleteTableMappings(
		long[] leftPrimaryKeys, long rightPrimaryKey);

	public void destroy();

	public List<L> getLeftBaseModels(
		long rightPrimaryKey, int start, int end, OrderByComparator<L> obc);

	public long[] getLeftPrimaryKeys(long rightPrimaryKey);

	public TableMapper<R, L> getReverseTableMapper();

	public List<R> getRightBaseModels(
		long leftPrimaryKey, int start, int end, OrderByComparator<R> obc);

	public long[] getRightPrimaryKeys(long leftPrimaryKey);

	public boolean matches(String leftColumnName, String rightColumnName);

}