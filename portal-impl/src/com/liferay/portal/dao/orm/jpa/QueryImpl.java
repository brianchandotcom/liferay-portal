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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.orm.CacheMode;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class QueryImpl implements Query {

	public QueryImpl(
		SessionImpl sessionImpl, String queryString, boolean strictName) {

		this.sessionImpl = sessionImpl;
		this.queryString = SQLTransformer.transformFromHqlToJpql(queryString);
		this.strictName = strictName;
	}

	@Override
	public int executeUpdate() throws ORMException {
		try {
			return sessionImpl.executeUpdate(
				queryString, positionalParameterMap, namedParameterMap,
				strictName, firstResult, maxResults, flushModeType,
				lockModeType, sqlQuery, entityClass);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Iterator<?> iterate() throws ORMException {
		return iterate(true);
	}

	@Override
	public Iterator<?> iterate(boolean unmodifiable) throws ORMException {
		try {
			return list(unmodifiable).iterator();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Object iterateNext() throws ORMException {
		Iterator<?> iterator = iterate(false);

		if (iterator.hasNext()) {
			return iterator.next();
		}

		return null;
	}

	@Override
	public List<?> list() throws ORMException {
		return list(false, false);
	}

	@Override
	public List<?> list(boolean unmodifiable) throws ORMException {
		return list(true, unmodifiable);
	}

	@Override
	public List<?> list(boolean copy, boolean unmodifiable)
		throws ORMException {

		try {
			List<?> list = sessionImpl.list(
				queryString, positionalParameterMap, namedParameterMap,
				strictName, firstResult, maxResults, flushModeType,
				lockModeType, sqlQuery, entityClass);

			if (unmodifiable) {
				list = new UnmodifiableList<Object>(list);
			}
			else if (copy) {
				list = ListUtil.copy(list);
			}

			return list;
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public ScrollableResults scroll() throws ORMException {
		try {
			return new ScrollableResultsImpl(list());
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Query setBoolean(int pos, boolean value) {
		positionalParameterMap.put(pos, value);

		return this;
	}

	@Override
	public Query setBoolean(String name, boolean value) {
		namedParameterMap.put(name, value);

		return this;
	}

	@Override
	public Query setCacheable(boolean cacheable) {
		return this;
	}

	@Override
	public Query setCacheMode(CacheMode cacheMode) {
		return this;
	}

	@Override
	public Query setCacheRegion(String cacheRegion) {
		return this;
	}

	@Override
	public Query setDouble(int pos, double value) {
		positionalParameterMap.put(pos, Double.valueOf(value));

		return this;
	}

	@Override
	public Query setDouble(String name, double value) {
		namedParameterMap.put(name, Double.valueOf(value));

		return this;
	}

	@Override
	public Query setFirstResult(int firstResult) {
		this.firstResult = firstResult;

		return this;
	}

	@Override
	public Query setFloat(int pos, float value) {
		positionalParameterMap.put(pos, Float.valueOf(value));

		return this;
	}

	@Override
	public Query setFloat(String name, float value) {
		namedParameterMap.put(name, Float.valueOf(value));

		return this;
	}

	public Query setFlushMode(FlushModeType flushModeType) {
		this.flushModeType = flushModeType;

		return this;
	}

	@Override
	public Query setInteger(int pos, int value) {
		positionalParameterMap.put(pos, Integer.valueOf(value));

		return this;
	}

	@Override
	public Query setInteger(String name, int value) {
		namedParameterMap.put(name, Integer.valueOf(value));

		return this;
	}

	@Override
	public Query setLockMode(String alias, LockMode lockMode) {
		lockModeType = LockModeTranslator.translate(lockMode);

		return this;
	}

	@Override
	public Query setLong(int pos, long value) {
		positionalParameterMap.put(pos, Long.valueOf(value));

		return this;
	}

	@Override
	public Query setLong(String name, long value) {
		namedParameterMap.put(name, Long.valueOf(value));

		return this;
	}

	@Override
	public Query setMaxResults(int maxResults) {
		this.maxResults = maxResults;

		return this;
	}

	@Override
	public Query setSerializable(int pos, Serializable value) {
		positionalParameterMap.put(pos, value);

		return this;
	}

	@Override
	public Query setSerializable(String name, Serializable value) {
		namedParameterMap.put(name, value);

		return this;
	}

	@Override
	public Query setShort(int pos, short value) {
		positionalParameterMap.put(pos, Short.valueOf(value));

		return this;
	}

	@Override
	public Query setShort(String name, short value) {
		namedParameterMap.put(name, Short.valueOf(value));

		return this;
	}

	@Override
	public Query setString(int pos, String value) {
		positionalParameterMap.put(pos, value);

		return this;
	}

	@Override
	public Query setString(String name, String value) {
		namedParameterMap.put(name, value);

		return this;
	}

	@Override
	public Query setTimestamp(int pos, Timestamp value) {
		Date date = null;

		if (value != null) {
			date = new Date(value.getTime());
		}

		positionalParameterMap.put(pos, date);

		return this;
	}

	@Override
	public Query setTimestamp(String name, Timestamp value) {
		Date date = null;

		if (value != null) {
			date = new Date(value.getTime());
		}

		namedParameterMap.put(name, date);

		return this;
	}

	@Override
	public Object uniqueResult() throws ORMException {
		try {
			return sessionImpl.uniqueResult(
				queryString, positionalParameterMap, namedParameterMap,
				strictName, firstResult, maxResults, flushModeType,
				lockModeType, sqlQuery, entityClass);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	protected Class<?> entityClass;
	protected int firstResult = -1;
	protected FlushModeType flushModeType;
	protected LockModeType lockModeType;
	protected int maxResults = -1;
	protected Map<String, Object> namedParameterMap =
		new HashMap<String, Object>();
	protected Map<Integer, Object> positionalParameterMap =
		new HashMap<Integer, Object>();
	protected String queryString;
	protected SessionImpl sessionImpl;
	protected boolean sqlQuery;
	protected boolean strictName = true;

}