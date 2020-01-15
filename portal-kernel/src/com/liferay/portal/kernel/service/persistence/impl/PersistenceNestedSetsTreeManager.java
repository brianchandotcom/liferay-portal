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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.model.NestedSetsTreeNodeModel;

import java.util.Iterator;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class PersistenceNestedSetsTreeManager<T extends NestedSetsTreeNodeModel>
	extends NestedSetsTreeManager<T> {

	public PersistenceNestedSetsTreeManager(
		BasePersistenceImpl<?> basePersistenceImpl, String tableName,
		String entityName, Class<?> entityImplClass, String primaryKeyName,
		String nestedSetsTreeNodeScopeIdName, String nestedSetsTreeNodeLeftName,
		String nestedSetsTreeNodeRightName) {

		_basePersistenceImpl = basePersistenceImpl;
		_tableName = tableName;
		_entityName = entityName;
		_entityImplClass = entityImplClass;
		_primaryKeyName = primaryKeyName;
		_nestedSetsTreeNodeScopeIdName = nestedSetsTreeNodeScopeIdName;
		_nestedSetsTreeNodeLeftName = nestedSetsTreeNodeLeftName;
		_nestedSetsTreeNodeRightName = nestedSetsTreeNodeRightName;
	}

	@Override
	protected long doCountAncestors(
		long nestedSetsTreeNodeScopeId, long nestedSetsTreeNodeLeft,
		long nestedSetsTreeNodeRight) {

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			StringBundler sb = new StringBundler(9);

			sb.append("SELECT count(*) FROM ");
			sb.append(_tableName);
			sb.append(" WHERE ");
			sb.append(_nestedSetsTreeNodeScopeIdName);
			sb.append(" = ? AND ");
			sb.append(_nestedSetsTreeNodeLeftName);
			sb.append(" <= ? AND ");
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" >= ?");

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				sb.toString());

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(nestedSetsTreeNodeScopeId);
			qPos.add(nestedSetsTreeNodeLeft);
			qPos.add(nestedSetsTreeNodeRight);

			Number number = (Number)sqlQuery.uniqueResult();

			return number.longValue();
		}
		catch (Exception exception) {
			throw _basePersistenceImpl.processException(exception);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected long doCountDescendants(
		long nestedSetsTreeNodeScopeId, long nestedSetsTreeNodeLeft,
		long nestedSetsTreeNodeRight) {

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			StringBundler sb = new StringBundler(9);

			sb.append("SELECT count(*) FROM ");
			sb.append(_tableName);
			sb.append(" WHERE ");
			sb.append(_nestedSetsTreeNodeScopeIdName);
			sb.append(" = ? AND ");
			sb.append(_nestedSetsTreeNodeLeftName);
			sb.append(" >= ? AND ");
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" <= ?");

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				sb.toString());

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(nestedSetsTreeNodeScopeId);
			qPos.add(nestedSetsTreeNodeLeft);
			qPos.add(nestedSetsTreeNodeRight);

			Number number = (Number)sqlQuery.uniqueResult();

			return number.longValue();
		}
		catch (Exception exception) {
			throw _basePersistenceImpl.processException(exception);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected List<T> doGetAncestors(
		long nestedSetsTreeNodeScopeId, long nestedSetsTreeNodeLeft,
		long nestedSetsTreeNodeRight) {

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			StringBundler sb = new StringBundler(11);

			sb.append("SELECT {");
			sb.append(_entityName);
			sb.append(".*} FROM ");
			sb.append(_tableName);
			sb.append(" WHERE ");
			sb.append(_nestedSetsTreeNodeScopeIdName);
			sb.append(" = ? AND ");
			sb.append(_nestedSetsTreeNodeLeftName);
			sb.append(" <= ? AND ");
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" >= ?");

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				sb.toString());

			sqlQuery.addEntity(_entityName, _entityImplClass);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(nestedSetsTreeNodeScopeId);
			qPos.add(nestedSetsTreeNodeLeft);
			qPos.add(nestedSetsTreeNodeRight);

			return (List<T>)QueryUtil.list(
				sqlQuery, _basePersistenceImpl.getDialect(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw _basePersistenceImpl.processException(exception);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected List<T> doGetDescendants(
		long nestedSetsTreeNodeScopeId, long nestedSetsTreeNodeLeft,
		long nestedSetsTreeNodeRight) {

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			StringBundler sb = new StringBundler(11);

			sb.append("SELECT {");
			sb.append(_entityName);
			sb.append(".*} FROM ");
			sb.append(_tableName);
			sb.append(" WHERE ");
			sb.append(_nestedSetsTreeNodeScopeIdName);
			sb.append(" = ? AND ");
			sb.append(_nestedSetsTreeNodeLeftName);
			sb.append(" >= ? AND ");
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" <= ?");

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				sb.toString());

			sqlQuery.addEntity(_entityName, _entityImplClass);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(nestedSetsTreeNodeScopeId);
			qPos.add(nestedSetsTreeNodeLeft);
			qPos.add(nestedSetsTreeNodeRight);

			return (List<T>)QueryUtil.list(
				sqlQuery, _basePersistenceImpl.getDialect(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw _basePersistenceImpl.processException(exception);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	protected void doUpdate(
		boolean leftOrRight, long nestedSetsTreeNodeScopeId, long delta,
		long start, boolean startInclusive, long end, boolean endInclusive,
		List<T> includeList) {

		StringBundler sb = null;

		if (includeList == null) {
			sb = new StringBundler(14);
		}
		else {
			sb = new StringBundler(17 + includeList.size() * 2);
		}

		sb.append("UPDATE ");
		sb.append(_tableName);
		sb.append(" SET ");

		if (leftOrRight) {
			sb.append(_nestedSetsTreeNodeLeftName);
			sb.append(" = (");
			sb.append(_nestedSetsTreeNodeLeftName);
			sb.append(" + ?)");
		}
		else {
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" = (");
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" + ?)");
		}

		sb.append(" WHERE ");
		sb.append(_nestedSetsTreeNodeScopeIdName);
		sb.append(" = ? AND ");

		if (leftOrRight) {
			sb.append(_nestedSetsTreeNodeLeftName);
		}
		else {
			sb.append(_nestedSetsTreeNodeRightName);
		}

		if (startInclusive) {
			sb.append(" >= ? AND ");
		}
		else {
			sb.append(" > ? AND ");
		}

		if (leftOrRight) {
			sb.append(_nestedSetsTreeNodeLeftName);
		}
		else {
			sb.append(_nestedSetsTreeNodeRightName);
		}

		if (endInclusive) {
			sb.append(" <= ? ");
		}
		else {
			sb.append(" < ? ");
		}

		if (includeList != null) {
			sb.append(" AND ");
			sb.append(_primaryKeyName);
			sb.append(" IN(");

			for (T t : includeList) {
				sb.append(t.getPrimaryKey());
				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);

			sb.append(")");
		}

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				sb.toString());

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(delta);
			qPos.add(nestedSetsTreeNodeScopeId);
			qPos.add(start);
			qPos.add(end);

			sqlQuery.executeUpdate();
		}
		catch (Exception exception) {
			throw _basePersistenceImpl.processException(exception);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected void doUpdate(
		long nestedSetsTreeNodeScopeId, boolean leftOrRight, long delta,
		long limit, boolean inclusive) {

		StringBundler sb = new StringBundler(12);

		sb.append("UPDATE ");
		sb.append(_tableName);
		sb.append(" SET ");

		if (leftOrRight) {
			sb.append(_nestedSetsTreeNodeLeftName);
			sb.append(" = (");
			sb.append(_nestedSetsTreeNodeLeftName);
			sb.append(" + ?)");
		}
		else {
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" = (");
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" + ?)");
		}

		sb.append(" WHERE ");
		sb.append(_nestedSetsTreeNodeScopeIdName);
		sb.append(" = ? AND ");

		if (leftOrRight) {
			sb.append(_nestedSetsTreeNodeLeftName);
		}
		else {
			sb.append(_nestedSetsTreeNodeRightName);
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				sb.toString());

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(delta);
			qPos.add(nestedSetsTreeNodeScopeId);
			qPos.add(limit);

			sqlQuery.executeUpdate();
		}
		catch (Exception exception) {
			throw _basePersistenceImpl.processException(exception);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	@Override
	protected void doUpdate(
		long nestedSetsTreeNodeScopeId, long delta, long start,
		boolean startInclusive, long end, boolean endInclusive,
		List<T> includeList) {

		doUpdate(
			false, nestedSetsTreeNodeScopeId, delta, start, startInclusive, end,
			endInclusive, includeList);
		doUpdate(
			true, nestedSetsTreeNodeScopeId, delta, start, startInclusive, end,
			endInclusive, includeList);
	}

	@Override
	protected long getMaxNestedSetsTreeNodeRight(
		long nestedSetsTreeNodeScopeId) {

		Session session = null;

		try {
			session = _basePersistenceImpl.openSession();

			StringBundler sb = new StringBundler(9);

			sb.append("SELECT MAX(");
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(") AS maxNestedSetsTreeNodeRight FROM ");
			sb.append(_tableName);
			sb.append(" WHERE ");
			sb.append(_nestedSetsTreeNodeScopeIdName);
			sb.append(" = ? AND ");
			sb.append(_nestedSetsTreeNodeRightName);
			sb.append(" > 0");

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				sb.toString());

			sqlQuery.addScalar("maxNestedSetsTreeNodeRight", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(sqlQuery);

			qPos.add(nestedSetsTreeNodeScopeId);

			Iterator<Long> iterator = (Iterator<Long>)QueryUtil.iterate(
				sqlQuery, _basePersistenceImpl.getDialect(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			Long maxNSRight = iterator.next();

			if (maxNSRight != null) {
				return maxNSRight + 1;
			}

			return 1;
		}
		catch (Exception exception) {
			throw _basePersistenceImpl.processException(exception);
		}
		finally {
			_basePersistenceImpl.closeSession(session);
		}
	}

	private final BasePersistenceImpl<?> _basePersistenceImpl;
	private final Class<?> _entityImplClass;
	private final String _entityName;
	private final String _nestedSetsTreeNodeLeftName;
	private final String _nestedSetsTreeNodeRightName;
	private final String _nestedSetsTreeNodeScopeIdName;
	private final String _primaryKeyName;
	private final String _tableName;

}