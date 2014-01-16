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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchClassNameException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.model.impl.ClassNameModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the class name service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClassNamePersistence
 * @see ClassNameUtil
 * @generated
 */
public class ClassNamePersistenceImpl extends BasePersistenceImpl<ClassName>
	implements ClassNamePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ClassNameUtil} to access the class name persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ClassNameImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, ClassNameImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, ClassNameImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_VALUE = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, ClassNameImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByValue",
			new String[] { String.class.getName() },
			ClassNameModelImpl.VALUE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VALUE = new FinderPath(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByValue",
			new String[] { String.class.getName() });

	/**
	 * Returns the class name where value = &#63; or throws a {@link com.liferay.portal.NoSuchClassNameException} if it could not be found.
	 *
	 * @param value the value
	 * @return the matching class name
	 * @throws com.liferay.portal.NoSuchClassNameException if a matching class name could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName findByValue(String value)
		throws NoSuchClassNameException, SystemException {
		ClassName className = fetchByValue(value);

		if (className == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("value=");
			msg.append(value);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchClassNameException(msg.toString());
		}

		return className;
	}

	/**
	 * Returns the class name where value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param value the value
	 * @return the matching class name, or <code>null</code> if a matching class name could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName fetchByValue(String value) throws SystemException {
		return fetchByValue(value, true);
	}

	/**
	 * Returns the class name where value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param value the value
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching class name, or <code>null</code> if a matching class name could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName fetchByValue(String value, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { value };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_VALUE,
					finderArgs, this);
		}

		if (result instanceof ClassName) {
			ClassName className = (ClassName)result;

			if (!Validator.equals(value, className.getValue())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CLASSNAME_WHERE);

			boolean bindValue = false;

			if (value == null) {
				query.append(_FINDER_COLUMN_VALUE_VALUE_1);
			}
			else if (value.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_VALUE_VALUE_3);
			}
			else {
				bindValue = true;

				query.append(_FINDER_COLUMN_VALUE_VALUE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindValue) {
					qPos.add(value);
				}

				List<ClassName> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE,
						finderArgs, list);
				}
				else {
					ClassName className = list.get(0);

					result = className;

					cacheResult(className);

					if ((className.getValue() == null) ||
							!className.getValue().equals(value)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE,
							finderArgs, className);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VALUE,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ClassName)result;
		}
	}

	/**
	 * Removes the class name where value = &#63; from the database.
	 *
	 * @param value the value
	 * @return the class name that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName removeByValue(String value)
		throws NoSuchClassNameException, SystemException {
		ClassName className = findByValue(value);

		return remove(className);
	}

	/**
	 * Returns the number of class names where value = &#63;.
	 *
	 * @param value the value
	 * @return the number of matching class names
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByValue(String value) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VALUE;

		Object[] finderArgs = new Object[] { value };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CLASSNAME_WHERE);

			boolean bindValue = false;

			if (value == null) {
				query.append(_FINDER_COLUMN_VALUE_VALUE_1);
			}
			else if (value.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_VALUE_VALUE_3);
			}
			else {
				bindValue = true;

				query.append(_FINDER_COLUMN_VALUE_VALUE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindValue) {
					qPos.add(value);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_VALUE_VALUE_1 = "className.value IS NULL";
	private static final String _FINDER_COLUMN_VALUE_VALUE_2 = "className.value = ?";
	private static final String _FINDER_COLUMN_VALUE_VALUE_3 = "(className.value IS NULL OR className.value = '')";

	public ClassNamePersistenceImpl() {
		setModelClass(ClassName.class);
	}

	/**
	 * Caches the class name in the entity cache if it is enabled.
	 *
	 * @param className the class name
	 */
	@Override
	public void cacheResult(ClassName className) {
		EntityCacheUtil.putResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameImpl.class, className.getPrimaryKey(), className);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE,
			new Object[] { className.getValue() }, className);

		className.resetOriginalValues();
	}

	/**
	 * Caches the class names in the entity cache if it is enabled.
	 *
	 * @param classNames the class names
	 */
	@Override
	public void cacheResult(List<ClassName> classNames) {
		for (ClassName className : classNames) {
			if (EntityCacheUtil.getResult(
						ClassNameModelImpl.ENTITY_CACHE_ENABLED,
						ClassNameImpl.class, className.getPrimaryKey()) == null) {
				cacheResult(className);
			}
			else {
				className.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all class names.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ClassNameImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ClassNameImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the class name.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ClassName className) {
		EntityCacheUtil.removeResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameImpl.class, className.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(className);
	}

	@Override
	public void clearCache(List<ClassName> classNames) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ClassName className : classNames) {
			EntityCacheUtil.removeResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
				ClassNameImpl.class, className.getPrimaryKey());

			clearUniqueFindersCache(className);
		}
	}

	protected void cacheUniqueFindersCache(ClassName className) {
		if (className.isNew()) {
			Object[] args = new Object[] { className.getValue() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VALUE, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE, args,
				className);
		}
		else {
			ClassNameModelImpl classNameModelImpl = (ClassNameModelImpl)className;

			if ((classNameModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_VALUE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { className.getValue() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VALUE, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VALUE, args,
					className);
			}
		}
	}

	protected void clearUniqueFindersCache(ClassName className) {
		ClassNameModelImpl classNameModelImpl = (ClassNameModelImpl)className;

		Object[] args = new Object[] { className.getValue() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_VALUE, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VALUE, args);

		if ((classNameModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_VALUE.getColumnBitmask()) != 0) {
			args = new Object[] { classNameModelImpl.getOriginalValue() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_VALUE, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VALUE, args);
		}
	}

	/**
	 * Creates a new class name with the primary key. Does not add the class name to the database.
	 *
	 * @param classNameId the primary key for the new class name
	 * @return the new class name
	 */
	@Override
	public ClassName create(long classNameId) {
		ClassName className = new ClassNameImpl();

		className.setNew(true);
		className.setPrimaryKey(classNameId);

		return className;
	}

	/**
	 * Removes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name that was removed
	 * @throws com.liferay.portal.NoSuchClassNameException if a class name with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName remove(long classNameId)
		throws NoSuchClassNameException, SystemException {
		return remove((Serializable)classNameId);
	}

	/**
	 * Removes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the class name
	 * @return the class name that was removed
	 * @throws com.liferay.portal.NoSuchClassNameException if a class name with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName remove(Serializable primaryKey)
		throws NoSuchClassNameException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ClassName className = (ClassName)session.get(ClassNameImpl.class,
					primaryKey);

			if (className == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchClassNameException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(className);
		}
		catch (NoSuchClassNameException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ClassName removeImpl(ClassName className)
		throws SystemException {
		className = toUnwrappedModel(className);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(className)) {
				className = (ClassName)session.get(ClassNameImpl.class,
						className.getPrimaryKeyObj());
			}

			if (className != null) {
				session.delete(className);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (className != null) {
			clearCache(className);
		}

		return className;
	}

	@Override
	public ClassName updateImpl(com.liferay.portal.model.ClassName className)
		throws SystemException {
		className = toUnwrappedModel(className);

		boolean isNew = className.isNew();

		Session session = null;

		try {
			session = openSession();

			if (className.isNew()) {
				session.save(className);

				className.setNew(false);
			}
			else {
				session.merge(className);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ClassNameModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
			ClassNameImpl.class, className.getPrimaryKey(), className);

		clearUniqueFindersCache(className);
		cacheUniqueFindersCache(className);

		className.resetOriginalValues();

		return className;
	}

	protected ClassName toUnwrappedModel(ClassName className) {
		if (className instanceof ClassNameImpl) {
			return className;
		}

		ClassNameImpl classNameImpl = new ClassNameImpl();

		classNameImpl.setNew(className.isNew());
		classNameImpl.setPrimaryKey(className.getPrimaryKey());

		classNameImpl.setClassNameId(className.getClassNameId());
		classNameImpl.setValue(className.getValue());
		classNameImpl.setMvccVersion(className.getMvccVersion());

		return classNameImpl;
	}

	/**
	 * Returns the class name with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the class name
	 * @return the class name
	 * @throws com.liferay.portal.NoSuchClassNameException if a class name with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName findByPrimaryKey(Serializable primaryKey)
		throws NoSuchClassNameException, SystemException {
		ClassName className = fetchByPrimaryKey(primaryKey);

		if (className == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchClassNameException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return className;
	}

	/**
	 * Returns the class name with the primary key or throws a {@link com.liferay.portal.NoSuchClassNameException} if it could not be found.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name
	 * @throws com.liferay.portal.NoSuchClassNameException if a class name with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName findByPrimaryKey(long classNameId)
		throws NoSuchClassNameException, SystemException {
		return findByPrimaryKey((Serializable)classNameId);
	}

	/**
	 * Returns the class name with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the class name
	 * @return the class name, or <code>null</code> if a class name with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		ClassName className = (ClassName)EntityCacheUtil.getResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
				ClassNameImpl.class, primaryKey);

		if (className == _nullClassName) {
			return null;
		}

		if (className == null) {
			Session session = null;

			try {
				session = openSession();

				className = (ClassName)session.get(ClassNameImpl.class,
						primaryKey);

				if (className != null) {
					cacheResult(className);
				}
				else {
					EntityCacheUtil.putResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
						ClassNameImpl.class, primaryKey, _nullClassName);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ClassNameModelImpl.ENTITY_CACHE_ENABLED,
					ClassNameImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return className;
	}

	/**
	 * Returns the class name with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name, or <code>null</code> if a class name with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ClassName fetchByPrimaryKey(long classNameId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)classNameId);
	}

	/**
	 * Returns all the class names.
	 *
	 * @return the class names
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ClassName> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClassNameModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @return the range of class names
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ClassName> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClassNameModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of class names
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ClassName> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ClassName> list = (List<ClassName>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CLASSNAME);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CLASSNAME;

				if (pagination) {
					sql = sql.concat(ClassNameModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ClassName>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ClassName>(list);
				}
				else {
					list = (List<ClassName>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the class names from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (ClassName className : findAll()) {
			remove(className);
		}
	}

	/**
	 * Returns the number of class names.
	 *
	 * @return the number of class names
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CLASSNAME);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the class name persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ClassName")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ClassName>> listenersList = new ArrayList<ModelListener<ClassName>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ClassName>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ClassNameImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_CLASSNAME = "SELECT className FROM ClassName className";
	private static final String _SQL_SELECT_CLASSNAME_WHERE = "SELECT className FROM ClassName className WHERE ";
	private static final String _SQL_COUNT_CLASSNAME = "SELECT COUNT(className) FROM ClassName className";
	private static final String _SQL_COUNT_CLASSNAME_WHERE = "SELECT COUNT(className) FROM ClassName className WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "className.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ClassName exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ClassName exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ClassNamePersistenceImpl.class);
	private static ClassName _nullClassName = new ClassNameImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ClassName> toCacheModel() {
				return _nullClassNameCacheModel;
			}
		};

	private static CacheModel<ClassName> _nullClassNameCacheModel = new CacheModel<ClassName>() {
			@Override
			public ClassName toEntityModel() {
				return _nullClassName;
			}
		};
}