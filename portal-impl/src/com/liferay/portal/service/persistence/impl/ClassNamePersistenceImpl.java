/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchClassNameException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.ClassNameUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.model.impl.ClassNameModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the class name service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ClassNamePersistenceImpl
	extends BasePersistenceImpl<ClassName> implements ClassNamePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ClassNameUtil</code> to access the class name persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ClassNameImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByValue;
	private FinderPath _finderPathCountByValue;

	/**
	 * Returns the class name where value = &#63; or throws a <code>NoSuchClassNameException</code> if it could not be found.
	 *
	 * @param value the value
	 * @return the matching class name
	 * @throws NoSuchClassNameException if a matching class name could not be found
	 */
	@Override
	public ClassName findByValue(String value) throws NoSuchClassNameException {
		ClassName className = fetchByValue(value);

		if (className == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("value=");
			sb.append(value);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchClassNameException(sb.toString());
		}

		return className;
	}

	/**
	 * Returns the class name where value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param value the value
	 * @return the matching class name, or <code>null</code> if a matching class name could not be found
	 */
	@Override
	public ClassName fetchByValue(String value) {
		return fetchByValue(value, true);
	}

	/**
	 * Returns the class name where value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param value the value
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching class name, or <code>null</code> if a matching class name could not be found
	 */
	@Override
	public ClassName fetchByValue(String value, boolean useFinderCache) {
		value = Objects.toString(value, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {value};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByValue, finderArgs, this);
		}

		if (result instanceof ClassName) {
			ClassName className = (ClassName)result;

			if (!Objects.equals(value, className.getValue())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_CLASSNAME_WHERE);

			boolean bindValue = false;

			if (value.isEmpty()) {
				sb.append(_FINDER_COLUMN_VALUE_VALUE_3);
			}
			else {
				bindValue = true;

				sb.append(_FINDER_COLUMN_VALUE_VALUE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindValue) {
					queryPos.add(value);
				}

				List<ClassName> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByValue, finderArgs, list);
					}
				}
				else {
					ClassName className = list.get(0);

					result = className;

					cacheResult(className);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 */
	@Override
	public ClassName removeByValue(String value)
		throws NoSuchClassNameException {

		ClassName className = findByValue(value);

		return remove(className);
	}

	/**
	 * Returns the number of class names where value = &#63;.
	 *
	 * @param value the value
	 * @return the number of matching class names
	 */
	@Override
	public int countByValue(String value) {
		value = Objects.toString(value, "");

		FinderPath finderPath = _finderPathCountByValue;

		Object[] finderArgs = new Object[] {value};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CLASSNAME_WHERE);

			boolean bindValue = false;

			if (value.isEmpty()) {
				sb.append(_FINDER_COLUMN_VALUE_VALUE_3);
			}
			else {
				bindValue = true;

				sb.append(_FINDER_COLUMN_VALUE_VALUE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindValue) {
					queryPos.add(value);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_VALUE_VALUE_2 =
		"className.value = ?";

	private static final String _FINDER_COLUMN_VALUE_VALUE_3 =
		"(className.value IS NULL OR className.value = '')";

	public ClassNamePersistenceImpl() {
		setModelClass(ClassName.class);

		setModelImplClass(ClassNameImpl.class);
		setModelPKClass(long.class);

		setTable(ClassNameTable.INSTANCE);
	}

	/**
	 * Caches the class name in the entity cache if it is enabled.
	 *
	 * @param className the class name
	 */
	@Override
	public void cacheResult(ClassName className) {
		EntityCacheUtil.putResult(
			ClassNameImpl.class, className.getPrimaryKey(), className);

		FinderCacheUtil.putResult(
			_finderPathFetchByValue, new Object[] {className.getValue()},
			className);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the class names in the entity cache if it is enabled.
	 *
	 * @param classNames the class names
	 */
	@Override
	public void cacheResult(List<ClassName> classNames) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (classNames.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ClassName className : classNames) {
			if (EntityCacheUtil.getResult(
					ClassNameImpl.class, className.getPrimaryKey()) == null) {

				cacheResult(className);
			}
		}
	}

	/**
	 * Clears the cache for all class names.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ClassNameImpl.class);

		FinderCacheUtil.clearCache(ClassNameImpl.class);
	}

	/**
	 * Clears the cache for the class name.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ClassName className) {
		EntityCacheUtil.removeResult(ClassNameImpl.class, className);
	}

	@Override
	public void clearCache(List<ClassName> classNames) {
		for (ClassName className : classNames) {
			EntityCacheUtil.removeResult(ClassNameImpl.class, className);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(ClassNameImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(ClassNameImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ClassNameModelImpl classNameModelImpl) {

		Object[] args = new Object[] {classNameModelImpl.getValue()};

		FinderCacheUtil.putResult(
			_finderPathCountByValue, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByValue, args, classNameModelImpl);
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
	 * @throws NoSuchClassNameException if a class name with the primary key could not be found
	 */
	@Override
	public ClassName remove(long classNameId) throws NoSuchClassNameException {
		return remove((Serializable)classNameId);
	}

	/**
	 * Removes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the class name
	 * @return the class name that was removed
	 * @throws NoSuchClassNameException if a class name with the primary key could not be found
	 */
	@Override
	public ClassName remove(Serializable primaryKey)
		throws NoSuchClassNameException {

		Session session = null;

		try {
			session = openSession();

			ClassName className = (ClassName)session.get(
				ClassNameImpl.class, primaryKey);

			if (className == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchClassNameException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(className);
		}
		catch (NoSuchClassNameException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ClassName removeImpl(ClassName className) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(className)) {
				className = (ClassName)session.get(
					ClassNameImpl.class, className.getPrimaryKeyObj());
			}

			if (className != null) {
				session.delete(className);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	public ClassName updateImpl(ClassName className) {
		boolean isNew = className.isNew();

		if (!(className instanceof ClassNameModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(className.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(className);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in className proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ClassName implementation " +
					className.getClass());
		}

		ClassNameModelImpl classNameModelImpl = (ClassNameModelImpl)className;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(className);
			}
			else {
				className = (ClassName)session.merge(className);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			ClassNameImpl.class, classNameModelImpl, false, true);

		cacheUniqueFindersCache(classNameModelImpl);

		if (isNew) {
			className.setNew(false);
		}

		className.resetOriginalValues();

		return className;
	}

	/**
	 * Returns the class name with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the class name
	 * @return the class name
	 * @throws NoSuchClassNameException if a class name with the primary key could not be found
	 */
	@Override
	public ClassName findByPrimaryKey(Serializable primaryKey)
		throws NoSuchClassNameException {

		ClassName className = fetchByPrimaryKey(primaryKey);

		if (className == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchClassNameException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return className;
	}

	/**
	 * Returns the class name with the primary key or throws a <code>NoSuchClassNameException</code> if it could not be found.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name
	 * @throws NoSuchClassNameException if a class name with the primary key could not be found
	 */
	@Override
	public ClassName findByPrimaryKey(long classNameId)
		throws NoSuchClassNameException {

		return findByPrimaryKey((Serializable)classNameId);
	}

	/**
	 * Returns the class name with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name, or <code>null</code> if a class name with the primary key could not be found
	 */
	@Override
	public ClassName fetchByPrimaryKey(long classNameId) {
		return fetchByPrimaryKey((Serializable)classNameId);
	}

	/**
	 * Returns all the class names.
	 *
	 * @return the class names
	 */
	@Override
	public List<ClassName> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @return the range of class names
	 */
	@Override
	public List<ClassName> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of class names
	 */
	@Override
	public List<ClassName> findAll(
		int start, int end, OrderByComparator<ClassName> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of class names
	 */
	@Override
	public List<ClassName> findAll(
		int start, int end, OrderByComparator<ClassName> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<ClassName> list = null;

		if (useFinderCache) {
			list = (List<ClassName>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CLASSNAME);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CLASSNAME;

				sql = sql.concat(ClassNameModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ClassName>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 */
	@Override
	public void removeAll() {
		for (ClassName className : findAll()) {
			remove(className);
		}
	}

	/**
	 * Returns the number of class names.
	 *
	 * @return the number of class names
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CLASSNAME);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "classNameId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CLASSNAME;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ClassNameModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the class name persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByValue = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByValue",
			new String[] {String.class.getName()}, new String[] {"value"},
			true);

		_finderPathCountByValue = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByValue",
			new String[] {String.class.getName()}, new String[] {"value"},
			false);

		ClassNameUtil.setPersistence(this);
	}

	public void destroy() {
		ClassNameUtil.setPersistence(null);

		EntityCacheUtil.removeCache(ClassNameImpl.class.getName());
	}

	private static final String _SQL_SELECT_CLASSNAME =
		"SELECT className FROM ClassName className";

	private static final String _SQL_SELECT_CLASSNAME_WHERE =
		"SELECT className FROM ClassName className WHERE ";

	private static final String _SQL_COUNT_CLASSNAME =
		"SELECT COUNT(className) FROM ClassName className";

	private static final String _SQL_COUNT_CLASSNAME_WHERE =
		"SELECT COUNT(className) FROM ClassName className WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "className.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ClassName exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ClassName exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ClassNamePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}