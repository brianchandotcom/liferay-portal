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

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.NestedSetsTreeNodeModel;

import java.util.Iterator;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class DefaultPersistenceNestedSetsTreeManager
		<T extends NestedSetsTreeNodeModel>
	extends NestedSetsTreeManager<T> {

	public DefaultPersistenceNestedSetsTreeManager(
		BasePersistenceImpl<?> basePersistenceImpl, String tableName,
		String entityName, Class<?> entityImplClass, String pkName,
		String scopeIdName, String nestedSetsLeftName,
		String nestedSetsRightName) {

		_basePersistenceImpl = basePersistenceImpl;
		_tableName = tableName;
		_entityName = entityName;
		_entityImplClass = entityImplClass;
		_pkName = pkName;
		_scopeIdName = scopeIdName;
		_nestedSetsLeftName = nestedSetsLeftName;
		_nestedSetsRightName = nestedSetsRightName;
	}

	@Override
	protected long doCountAncestors(
			long scopeId, long nestedSetsLeft, long nestedSetsRight)
		throws SystemException {

		StringBundler sb = new StringBundler(9);

		sb.append("SELECT count(*) FROM ");
		sb.append(_tableName);
		sb.append(" WHERE ");
		sb.append(_scopeIdName);
		sb.append(" = ? AND ");
		sb.append(_nestedSetsLeftName);
		sb.append(" <= ? AND ");
		sb.append(_nestedSetsRightName);
		sb.append(" >= ?");

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(scopeId);
			qPos.add(nestedSetsLeft);
			qPos.add(nestedSetsRight);

			Number number = (Number)sqlQuery.uniqueResult();

			return number.longValue();
		}
		catch (Exception e) {
			throw _basePersistenceImpl.processException(e);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected long doCountDescendants(
			long scopeId, long nestedSetsLeft, long nestedSetsRight)
		throws SystemException {

		StringBundler sb = new StringBundler(9);

		sb.append("SELECT count(*) FROM ");
		sb.append(_tableName);
		sb.append(" WHERE ");
		sb.append(_scopeIdName);
		sb.append(" = ? AND ");
		sb.append(_nestedSetsLeftName);
		sb.append(" >= ? AND ");
		sb.append(_nestedSetsRightName);
		sb.append(" <= ?");

		Session session = null;

		try {
			session =_basePersistenceImpl.openSession();

			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(scopeId);
			qPos.add(nestedSetsLeft);
			qPos.add(nestedSetsRight);

			Number number = (Number)sqlQuery.uniqueResult();

			return number.longValue();
		}
		catch (Exception e) {
			throw _basePersistenceImpl.processException(e);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected List<T> doGetAncestors(
			long scopeId, long nestedSetsLeft, long nestedSetsRight)
		throws SystemException {

		StringBundler sb = new StringBundler(11);

		sb.append("SELECT {");
		sb.append(_entityName);
		sb.append(".*} FROM ");
		sb.append(_tableName);
		sb.append(" WHERE ");
		sb.append(_scopeIdName);
		sb.append(" = ? AND ");
		sb.append(_nestedSetsLeftName);
		sb.append(" <= ? AND ");
		sb.append(_nestedSetsRightName);
		sb.append(" >= ?");

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

			sqlQuery.addEntity(_entityName, _entityImplClass);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(scopeId);
			qPos.add(nestedSetsLeft);
			qPos.add(nestedSetsRight);

			return (List<T>)QueryUtil.list(
				sqlQuery, _basePersistenceImpl.getDialect(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw _basePersistenceImpl.processException(e);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected List<T> doGetDescendants(
			long scopeId, long nestedSetsLeft, long nestedSetsRight)
		throws SystemException {

		StringBundler sb = new StringBundler(11);

		sb.append("SELECT {");
		sb.append(_entityName);
		sb.append(".*} FROM ");
		sb.append(_tableName);
		sb.append(" WHERE ");
		sb.append(_scopeIdName);
		sb.append(" = ? AND ");
		sb.append(_nestedSetsLeftName);
		sb.append(" >= ? AND ");
		sb.append(_nestedSetsRightName);
		sb.append(" <= ?");

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

			sqlQuery.addEntity(_entityName, _entityImplClass);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(scopeId);
			qPos.add(nestedSetsLeft);
			qPos.add(nestedSetsRight);

			return (List<T>)QueryUtil.list(
				sqlQuery, _basePersistenceImpl.getDialect(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw _basePersistenceImpl.processException(e);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	protected void doUpdate(
			boolean leftOrRight, long scopeId, long delta, long start,
			boolean startIncluside, long end, boolean endInclusive,
			List<T> inList)
		throws SystemException {

		StringBundler sb = null;

		if (inList == null) {
			sb = new StringBundler(14);
		}
		else {
			sb = new StringBundler(17 + inList.size() * 2);
		}

		sb.append("UPDATE ");
		sb.append(_tableName);
		sb.append(" SET ");

		if (leftOrRight) {
			sb.append(_nestedSetsLeftName);
			sb.append(" = (");
			sb.append(_nestedSetsLeftName);
			sb.append(" + ?)");
		}
		else {
			sb.append(_nestedSetsRightName);
			sb.append(" = (");
			sb.append(_nestedSetsRightName);
			sb.append(" + ?)");
		}

		sb.append(" WHERE ");
		sb.append(_scopeIdName);
		sb.append(" = ? AND ");

		if (leftOrRight) {
			sb.append(_nestedSetsLeftName);
		}
		else {
			sb.append(_nestedSetsRightName);
		}

		if (startIncluside) {
			sb.append(" >= ? AND ");
		}
		else {
			sb.append(" > ? AND ");
		}

		if (leftOrRight) {
			sb.append(_nestedSetsLeftName);
		}
		else {
			sb.append(_nestedSetsRightName);
		}

		if (endInclusive) {
			sb.append(" <= ? ");
		}
		else {
			sb.append(" < ? ");
		}

		if (inList != null) {
			sb.append(" AND ");
			sb.append(_pkName);
			sb.append(" IN(");

			for (T t : inList) {
				sb.append(t.getPrimaryKey());
				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);

			sb.append(")");
		}

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(delta);
			qPos.add(scopeId);
			qPos.add(start);
			qPos.add(end);

			sqlQuery.executeUpdate();
		}
		catch (Exception e) {
			throw _basePersistenceImpl.processException(e);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected void doUpdate(
			long scopeId, boolean leftOrRight, long delta, long limit,
			boolean inclusive)
		throws SystemException {

		StringBundler sb = new StringBundler(12);

		sb.append("UPDATE ");
		sb.append(_tableName);
		sb.append(" SET ");

		if (leftOrRight) {
			sb.append(_nestedSetsLeftName);
			sb.append(" = (");
			sb.append(_nestedSetsLeftName);
			sb.append(" + ?)");
		}
		else {
			sb.append(_nestedSetsRightName);
			sb.append(" = (");
			sb.append(_nestedSetsRightName);
			sb.append(" + ?)");
		}

		sb.append(" WHERE ");
		sb.append(_scopeIdName);
		sb.append(" = ? AND ");

		if (leftOrRight) {
			sb.append(_nestedSetsLeftName);
		}
		else {
			sb.append(_nestedSetsRightName);
		}

		if (inclusive) {
			sb.append(" >= ?");
		}
		else {
			sb.append(" > ?");
		}

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(delta);
			qPos.add(scopeId);
			qPos.add(limit);

			sqlQuery.executeUpdate();
		}
		catch (Exception e) {
			throw _basePersistenceImpl.processException(e);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected void doUpdate(
			long scopeId, long delta, long start, boolean startIncluside,
			long end, boolean endInclusive, List<T> inList)
		throws SystemException {

		doUpdate(
			false, scopeId, delta, start, startIncluside, end, endInclusive,
			inList);
		doUpdate(
			true, scopeId, delta, start, startIncluside, end, endInclusive,
			inList);
	}

	@Override
	protected long getMaxNestedSetsRight(long scopeId) throws SystemException {
		StringBundler sb = new StringBundler(9);

		sb.append("SELECT max(");
		sb.append(_nestedSetsRightName);
		sb.append(") AS MAX_NSRIGHT FROM ");
		sb.append(_tableName);
		sb.append(" WHERE ");
		sb.append(_scopeIdName);
		sb.append(" = ? AND ");
		sb.append(_nestedSetsRightName);
		sb.append(" > 0");

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

			sqlQuery.addScalar("MAX_NSRIGHT", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(scopeId);

			Iterator<Long> iterator = (Iterator<Long>)QueryUtil.iterate(
				sqlQuery, _basePersistenceImpl.getDialect(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			Long maxNSRight = iterator.next();

			if (maxNSRight != null) {
				return maxNSRight + 1;
			}

			return 1;
		}
		catch (Exception e) {
			throw _basePersistenceImpl.processException(e);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	private final BasePersistenceImpl<?> _basePersistenceImpl;
	private final Class<?> _entityImplClass;
	private final String _entityName;
	private final String _nestedSetsLeftName;
	private final String _nestedSetsRightName;
	private final String _pkName;
	private final String _scopeIdName;
	private final String _tableName;

}