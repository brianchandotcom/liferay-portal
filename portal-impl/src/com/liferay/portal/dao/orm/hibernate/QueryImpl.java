/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.CacheMode;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hibernate.LockOptions;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class QueryImpl implements Query {

	public QueryImpl(org.hibernate.Query query, boolean strictName) {
		_query = query;
		_strictName = strictName;

		if (!_strictName) {
			_names = query.getNamedParameters();

			Arrays.sort(_names);
		}
	}

	public int executeUpdate() throws ORMException {
		try {
			return _query.executeUpdate();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Iterator<?> iterate() throws ORMException {
		return iterate(true);
	}

	public Iterator<?> iterate(boolean unmodifiable) throws ORMException {
		try {
			return list(unmodifiable).iterator();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public List<?> list() throws ORMException {
		return list(false, false);
	}

	public List<?> list(boolean unmodifiable) throws ORMException {
		return list(true, unmodifiable);
	}

	public List<?> list(boolean copy, boolean unmodifiable)
		throws ORMException {

		try {
			List<?> list = _query.list();

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

	public ScrollableResults scroll() throws ORMException {
		try {
			return new ScrollableResultsImpl(_query.scroll());
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Query setBoolean(int pos, boolean value) {
		_query.setBoolean(pos, value);

		return this;
	}

	public Query setBoolean(String name, boolean value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setBoolean(name, value);

		return this;
	}

	public Query setCacheable(boolean cacheable) {
		_query.setCacheable(cacheable);

		return this;
	}

	public Query setCacheMode(CacheMode cacheMode) {
		_query.setCacheMode(CacheModeTranslator.translate(cacheMode));

		return this;
	}

	public Query setCacheRegion(String cacheRegion) {
		_query.setCacheRegion(cacheRegion);

		return this;
	}

	public Query setDouble(int pos, double value) {
		_query.setDouble(pos, value);

		return this;
	}

	public Query setDouble(String name, double value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setDouble(name, value);

		return this;
	}

	public Query setFirstResult(int firstResult) {
		_query.setFirstResult(firstResult);

		return this;
	}

	public Query setFloat(int pos, float value) {
		_query.setFloat(pos, value);

		return this;
	}

	public Query setFloat(String name, float value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setFloat(name, value);

		return this;
	}

	public Query setInteger(int pos, int value) {
		_query.setInteger(pos, value);

		return this;
	}

	public Query setInteger(String name, int value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setInteger(name, value);

		return this;
	}

	public Query setLockMode(String alias, LockMode lockMode) {
		org.hibernate.LockMode realLockMode = LockModeTranslator.translate(
			lockMode);

		LockOptions lockOptions = new LockOptions(realLockMode);

		lockOptions.setAliasSpecificLockMode(alias, realLockMode);

		_query.setLockOptions(lockOptions);

		return this;
	}

	public Query setLong(int pos, long value) {
		_query.setLong(pos, value);

		return this;
	}

	public Query setLong(String name, long value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setLong(name, value);

		return this;
	}

	public Query setMaxResults(int maxResults) {
		_query.setMaxResults(maxResults);

		return this;
	}

	public Query setSerializable(int pos, Serializable value) {
		_query.setSerializable(pos, value);

		return this;
	}

	public Query setSerializable(String name, Serializable value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setSerializable(name, value);

		return this;
	}

	public Query setShort(int pos, short value) {
		_query.setShort(pos, value);

		return this;
	}

	public Query setShort(String name, short value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setShort(name, value);

		return this;
	}

	public Query setString(int pos, String value) {
		_query.setString(pos, value);

		return this;
	}

	public Query setString(String name, String value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setString(name, value);

		return this;
	}

	public Query setTimestamp(int pos, Timestamp value) {
		_query.setTimestamp(pos, value);

		return this;
	}

	public Query setTimestamp(String name, Timestamp value) {
		if (!_strictName && (Arrays.binarySearch(_names, name) < 0)) {
			return this;
		}

		_query.setTimestamp(name, value);

		return this;
	}

	public Object uniqueResult() throws ORMException {
		try {
			return _query.uniqueResult();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	private String[] _names;
	private org.hibernate.Query _query;
	private boolean _strictName;

}