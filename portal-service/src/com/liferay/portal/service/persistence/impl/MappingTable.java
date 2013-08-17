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
public interface MappingTable<L extends BaseModel<L>, R extends BaseModel<R>> {

	public boolean addMapping(long leftPk, long rightPk) throws SystemException;

	public boolean containsMapping(long leftPk, long rightPk)
		throws SystemException;

	public int deleteByLeftPk(long leftPk) throws SystemException;

	public int deleteByRightPk(long rightPk) throws SystemException;

	public boolean deleteMapping(long leftPk, long rightPk)
		throws SystemException;

	public long[] findByLeftPk(long leftPk) throws SystemException;

	public List<R> findByLeftPkAsEntities(
			long leftPk, int start, int end, OrderByComparator obc)
		throws SystemException;

	public long[] findByRightPk(long rightPk) throws SystemException;

	public List<L> findByRightPkAsEntities(
			long rightPk, int start, int end, OrderByComparator obc)
		throws SystemException;

	public MappingTable<R, L> getReverseMappingTable();

	public boolean matches(String leftColumnName, String rightColumnName);

}