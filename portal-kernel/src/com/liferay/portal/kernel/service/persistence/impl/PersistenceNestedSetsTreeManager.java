/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				StringBundler.concat(
					"SELECT count(*) FROM ", _tableName, " WHERE ",
					_nestedSetsTreeNodeScopeIdName, " = ? AND ",
					_nestedSetsTreeNodeLeftName, " <= ? AND ",
					_nestedSetsTreeNodeRightName, " >= ?"));

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(nestedSetsTreeNodeScopeId);
			queryPos.add(nestedSetsTreeNodeLeft);
			queryPos.add(nestedSetsTreeNodeRight);

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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				StringBundler.concat(
					"SELECT count(*) FROM ", _tableName, " WHERE ",
					_nestedSetsTreeNodeScopeIdName, " = ? AND ",
					_nestedSetsTreeNodeLeftName, " >= ? AND ",
					_nestedSetsTreeNodeRightName, " <= ?"));

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(nestedSetsTreeNodeScopeId);
			queryPos.add(nestedSetsTreeNodeLeft);
			queryPos.add(nestedSetsTreeNodeRight);

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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				StringBundler.concat(
					"SELECT {", _entityName, ".*} FROM ", _tableName, " WHERE ",
					_nestedSetsTreeNodeScopeIdName, " = ? AND ",
					_nestedSetsTreeNodeLeftName, " <= ? AND ",
					_nestedSetsTreeNodeRightName, " >= ?"));

			sqlQuery.addEntity(_entityName, _entityImplClass);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(nestedSetsTreeNodeScopeId);
			queryPos.add(nestedSetsTreeNodeLeft);
			queryPos.add(nestedSetsTreeNodeRight);

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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				StringBundler.concat(
					"SELECT {", _entityName, ".*} FROM ", _tableName, " WHERE ",
					_nestedSetsTreeNodeScopeIdName, " = ? AND ",
					_nestedSetsTreeNodeLeftName, " >= ? AND ",
					_nestedSetsTreeNodeRightName, " <= ?"));

			sqlQuery.addEntity(_entityName, _entityImplClass);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(nestedSetsTreeNodeScopeId);
			queryPos.add(nestedSetsTreeNodeLeft);
			queryPos.add(nestedSetsTreeNodeRight);

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
			sb = new StringBundler(17 + (includeList.size() * 2));
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

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(delta);
			queryPos.add(nestedSetsTreeNodeScopeId);
			queryPos.add(start);
			queryPos.add(end);

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

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(delta);
			queryPos.add(nestedSetsTreeNodeScopeId);
			queryPos.add(limit);

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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				StringBundler.concat(
					"SELECT MAX(", _nestedSetsTreeNodeRightName,
					") AS maxNestedSetsTreeNodeRight FROM ", _tableName,
					" WHERE ", _nestedSetsTreeNodeScopeIdName, " = ? AND ",
					_nestedSetsTreeNodeRightName, " > 0"));

			sqlQuery.addScalar("maxNestedSetsTreeNodeRight", Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(nestedSetsTreeNodeScopeId);

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