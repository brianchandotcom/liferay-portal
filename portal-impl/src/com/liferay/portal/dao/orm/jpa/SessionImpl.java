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

import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

import java.io.Serializable;

import java.sql.Connection;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SessionImpl implements Session {

	@Override
	public void clear() throws ORMException {
		try {
			entityManager.clear();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Connection close() throws ORMException {
		return null;
	}

	@Override
	public boolean contains(Object object) throws ORMException {
		try {
			return entityManager.contains(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Query createQuery(String queryString) throws ORMException {
		return createQuery(queryString, true);
	}

	@Override
	public Query createQuery(String queryString, boolean strictName)
		throws ORMException {

		return new QueryImpl(this, queryString, strictName);
	}

	@Override
	public SQLQuery createSQLQuery(String queryString) throws ORMException {
		return createSQLQuery(queryString, true);
	}

	@Override
	public SQLQuery createSQLQuery(String queryString, boolean strictName)
		throws ORMException {

		return new SQLQueryImpl(this, queryString, strictName);
	}

	@Override
	public void delete(Object object) throws ORMException {
		try {
			entityManager.remove(entityManager.merge(object));
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public void evict(Object object) throws ORMException {
	}

	@Override
	public void flush() throws ORMException {
		try {
			entityManager.flush();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return entityManager.find(clazz, id);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		try {
			Object entity = entityManager.find(clazz, id);

			javax.persistence.LockModeType lockModeType =
				LockModeTranslator.translate(lockMode);

			if (lockModeType != null) {
				entityManager.lock(entity, lockModeType);
			}

			return entity;
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Object getWrappedSession() throws ORMException {
		return entityManager;
	}

	@Override
	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		try {
			return entityManager.getReference(clazz, id);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Object merge(Object object) throws ORMException {
		try {
			return entityManager.merge(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public Serializable save(Object object) throws ORMException {
		try {
			entityManager.persist(object);

			// Hibernate returns generated idenitfier which is not used anywhere

			return null;
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	@Override
	public void saveOrUpdate(Object object) throws ORMException {
		try {
			entityManager.merge(object);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	protected int executeUpdate(
		String queryString, Map<Integer, Object> positionalParameterMap,
		Map<String, Object> namedParameterMap, boolean strictName,
		int firstResult, int maxResults, FlushModeType flushMode,
		LockModeType lockModeType, boolean sqlQuery, Class<?> entityClass) {

		javax.persistence.Query query = _getExecutableQuery(
			queryString, positionalParameterMap, namedParameterMap, strictName,
			firstResult, maxResults, flushMode, lockModeType, sqlQuery,
			entityClass);

		return query.executeUpdate();
	}

	protected List<?> list(
		String queryString, Map<Integer, Object> positionalParameterMap,
		Map<String, Object> namedParameterMap, boolean strictName,
		int firstResult, int maxResults, FlushModeType flushMode,
		LockModeType lockModeType, boolean sqlQuery, Class<?> entityClass) {

		javax.persistence.Query query = _getExecutableQuery(
			queryString, positionalParameterMap, namedParameterMap, strictName,
			firstResult, maxResults, flushMode, lockModeType, sqlQuery,
			entityClass);

		return query.getResultList();
	}

	protected Object uniqueResult(
		String queryString, Map<Integer, Object> positionalParameterMap,
		Map<String, Object> namedParameterMap, boolean strictName,
		int firstResult, int maxResults, FlushModeType flushMode,
		LockModeType lockModeType, boolean sqlQuery, Class<?> entityClass) {

		javax.persistence.Query query = _getExecutableQuery(
			queryString, positionalParameterMap, namedParameterMap, strictName,
			firstResult, maxResults, flushMode, lockModeType, sqlQuery,
			entityClass);

		return query.getSingleResult();

	}

	@PersistenceContext
	protected EntityManager entityManager;

	private javax.persistence.Query _getExecutableQuery(
		String queryString, Map<Integer, Object> positionalParameterMap,
		Map<String, Object> namedParameterMap, boolean strictName,
		int firstResult, int maxResults, FlushModeType flushMode,
		LockModeType lockModeType, boolean sqlQuery, Class<?> entityClass) {

		javax.persistence.Query query = null;

		if (sqlQuery) {
			if (entityClass != null) {
				query = entityManager.createNativeQuery(
					queryString, entityClass);
			}
			else {
				query = entityManager.createNativeQuery(queryString);
			}
		}
		else {
			query = entityManager.createQuery(queryString);
		}

		_setParameters(
			query, positionalParameterMap, namedParameterMap, strictName);

		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}

		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}

		if (flushMode != null) {
			query.setFlushMode(flushMode);
		}

		if (lockModeType != null) {
			query.setLockMode(lockModeType);
		}

		return query;
	}

	private void _setParameters(
		javax.persistence.Query query,
		Map<Integer, Object> positionalParameterMap,
		Map<String, Object> namedParameterMap, boolean strictName) {

		for (Map.Entry<Integer, Object> entry :
				positionalParameterMap.entrySet()) {

			int position = entry.getKey() + 1;
			Object value = entry.getValue();

			if (value instanceof Date) {
				query.setParameter(
					position, (Date)value, TemporalType.TIMESTAMP);
			}
			else {
				query.setParameter(position, value);
			}
		}

		if (!strictName) {
			Set<Parameter<?>> parameters = query.getParameters();

			Set<String> parameterNames = new HashSet<String>();

			if (parameters != null) {
				for (Parameter<?> parameter : parameters) {
					String parameterName = parameter.getName();

					if (parameterName != null) {
						parameterNames.add(parameterName);
					}
				}
			}

			namedParameterMap.keySet().retainAll(parameterNames);
		}

		for (Map.Entry<String, Object> entry : namedParameterMap.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof Date) {
				query.setParameter(name, (Date)value, TemporalType.TIMESTAMP);
			}
			else {
				query.setParameter(name, value);
			}
		}
	}

}