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

package com.liferay.portal.kernel.dao.orm;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.Iterator;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@ProviderType
public interface Query {

	public int executeUpdate() throws ORMException;

	@SuppressWarnings("rawtypes")
	public Iterator iterate() throws ORMException;

	@SuppressWarnings("rawtypes")
	public Iterator iterate(boolean modifiable) throws ORMException;

	public Object iterateNext() throws ORMException;

	@SuppressWarnings("rawtypes")
	public List list() throws ORMException;

	@SuppressWarnings("rawtypes")
	public List list(boolean unmodifiable) throws ORMException;

	@SuppressWarnings("rawtypes")
	public List list(boolean copy, boolean unmodifiable) throws ORMException;

	public ScrollableResults scroll() throws ORMException;

	public Query setBigDecimal(int pos, BigDecimal value);

	public Query setBigDecimal(String name, BigDecimal value);

	public Query setBoolean(int pos, boolean value);

	public Query setBoolean(String name, boolean value);

	public Query setCacheable(boolean cacheable);

	public Query setCacheMode(CacheMode cacheMode);

	public Query setCacheRegion(String cacheRegion);

	public Query setDouble(int pos, double value);

	public Query setDouble(String name, double value);

	public Query setFirstResult(int firstResult);

	public Query setFloat(int pos, float value);

	public Query setFloat(String name, float value);

	public Query setInteger(int pos, int value);

	public Query setInteger(String name, int value);

	public Query setLockMode(String alias, LockMode lockMode);

	public Query setLong(int pos, long value);

	public Query setLong(String name, long value);

	public Query setMaxResults(int maxResults);

	public Query setSerializable(int pos, Serializable value);

	public Query setSerializable(String name, Serializable value);

	public Query setShort(int pos, short value);

	public Query setShort(String name, short value);

	public Query setString(int pos, String value);

	public Query setString(String name, String value);

	public Query setTimestamp(int pos, Timestamp value);

	public Query setTimestamp(String name, Timestamp value);

	public Object uniqueResult() throws ORMException;

}