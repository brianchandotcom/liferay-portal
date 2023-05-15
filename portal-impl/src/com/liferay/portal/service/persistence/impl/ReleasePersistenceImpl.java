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
import com.liferay.portal.kernel.exception.NoSuchReleaseException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseTable;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.ReleasePersistence;
import com.liferay.portal.kernel.service.persistence.ReleaseUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.ReleaseImpl;
import com.liferay.portal.model.impl.ReleaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the release service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ReleasePersistenceImpl
	extends BasePersistenceImpl<Release> implements ReleasePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ReleaseUtil</code> to access the release persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ReleaseImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByServletContextName;
	private FinderPath _finderPathCountByServletContextName;

	/**
	 * Returns the release where servletContextName = &#63; or throws a <code>NoSuchReleaseException</code> if it could not be found.
	 *
	 * @param servletContextName the servlet context name
	 * @return the matching release
	 * @throws NoSuchReleaseException if a matching release could not be found
	 */
	@Override
	public Release findByServletContextName(String servletContextName)
		throws NoSuchReleaseException {

		Release release = fetchByServletContextName(servletContextName);

		if (release == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("servletContextName=");
			sb.append(servletContextName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchReleaseException(sb.toString());
		}

		return release;
	}

	/**
	 * Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param servletContextName the servlet context name
	 * @return the matching release, or <code>null</code> if a matching release could not be found
	 */
	@Override
	public Release fetchByServletContextName(String servletContextName) {
		return fetchByServletContextName(servletContextName, true);
	}

	/**
	 * Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param servletContextName the servlet context name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching release, or <code>null</code> if a matching release could not be found
	 */
	@Override
	public Release fetchByServletContextName(
		String servletContextName, boolean useFinderCache) {

		servletContextName = Objects.toString(servletContextName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {servletContextName};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByServletContextName, finderArgs, this);
		}

		if (result instanceof Release) {
			Release release = (Release)result;

			if (!Objects.equals(
					servletContextName, release.getServletContextName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_RELEASE__WHERE);

			boolean bindServletContextName = false;

			if (servletContextName.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_3);
			}
			else {
				bindServletContextName = true;

				sb.append(
					_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindServletContextName) {
					queryPos.add(StringUtil.toLowerCase(servletContextName));
				}

				List<Release> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByServletContextName, finderArgs,
							list);
					}
				}
				else {
					Release release = list.get(0);

					result = release;

					cacheResult(release);
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
			return (Release)result;
		}
	}

	/**
	 * Removes the release where servletContextName = &#63; from the database.
	 *
	 * @param servletContextName the servlet context name
	 * @return the release that was removed
	 */
	@Override
	public Release removeByServletContextName(String servletContextName)
		throws NoSuchReleaseException {

		Release release = findByServletContextName(servletContextName);

		return remove(release);
	}

	/**
	 * Returns the number of releases where servletContextName = &#63;.
	 *
	 * @param servletContextName the servlet context name
	 * @return the number of matching releases
	 */
	@Override
	public int countByServletContextName(String servletContextName) {
		servletContextName = Objects.toString(servletContextName, "");

		FinderPath finderPath = _finderPathCountByServletContextName;

		Object[] finderArgs = new Object[] {servletContextName};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_RELEASE__WHERE);

			boolean bindServletContextName = false;

			if (servletContextName.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_3);
			}
			else {
				bindServletContextName = true;

				sb.append(
					_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindServletContextName) {
					queryPos.add(StringUtil.toLowerCase(servletContextName));
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

	private static final String
		_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_2 =
			"lower(release_.servletContextName) = ?";

	private static final String
		_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_3 =
			"(release_.servletContextName IS NULL OR release_.servletContextName = '')";

	public ReleasePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("state", "state_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Release.class);

		setModelImplClass(ReleaseImpl.class);
		setModelPKClass(long.class);

		setTable(ReleaseTable.INSTANCE);
	}

	/**
	 * Caches the release in the entity cache if it is enabled.
	 *
	 * @param release the release
	 */
	@Override
	public void cacheResult(Release release) {
		EntityCacheUtil.putResult(
			ReleaseImpl.class, release.getPrimaryKey(), release);

		FinderCacheUtil.putResult(
			_finderPathFetchByServletContextName,
			new Object[] {release.getServletContextName()}, release);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the releases in the entity cache if it is enabled.
	 *
	 * @param releases the releases
	 */
	@Override
	public void cacheResult(List<Release> releases) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (releases.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Release release : releases) {
			if (EntityCacheUtil.getResult(
					ReleaseImpl.class, release.getPrimaryKey()) == null) {

				cacheResult(release);
			}
		}
	}

	/**
	 * Clears the cache for all releases.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ReleaseImpl.class);

		FinderCacheUtil.clearCache(ReleaseImpl.class);
	}

	/**
	 * Clears the cache for the release.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Release release) {
		EntityCacheUtil.removeResult(ReleaseImpl.class, release);
	}

	@Override
	public void clearCache(List<Release> releases) {
		for (Release release : releases) {
			EntityCacheUtil.removeResult(ReleaseImpl.class, release);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(ReleaseImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(ReleaseImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(ReleaseModelImpl releaseModelImpl) {
		Object[] args = new Object[] {releaseModelImpl.getServletContextName()};

		FinderCacheUtil.putResult(
			_finderPathCountByServletContextName, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByServletContextName, args, releaseModelImpl);
	}

	/**
	 * Creates a new release with the primary key. Does not add the release to the database.
	 *
	 * @param releaseId the primary key for the new release
	 * @return the new release
	 */
	@Override
	public Release create(long releaseId) {
		Release release = new ReleaseImpl();

		release.setNew(true);
		release.setPrimaryKey(releaseId);

		return release;
	}

	/**
	 * Removes the release with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release that was removed
	 * @throws NoSuchReleaseException if a release with the primary key could not be found
	 */
	@Override
	public Release remove(long releaseId) throws NoSuchReleaseException {
		return remove((Serializable)releaseId);
	}

	/**
	 * Removes the release with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the release
	 * @return the release that was removed
	 * @throws NoSuchReleaseException if a release with the primary key could not be found
	 */
	@Override
	public Release remove(Serializable primaryKey)
		throws NoSuchReleaseException {

		Session session = null;

		try {
			session = openSession();

			Release release = (Release)session.get(
				ReleaseImpl.class, primaryKey);

			if (release == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchReleaseException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(release);
		}
		catch (NoSuchReleaseException noSuchEntityException) {
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
	protected Release removeImpl(Release release) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(release)) {
				release = (Release)session.get(
					ReleaseImpl.class, release.getPrimaryKeyObj());
			}

			if (release != null) {
				session.delete(release);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (release != null) {
			clearCache(release);
		}

		return release;
	}

	@Override
	public Release updateImpl(Release release) {
		boolean isNew = release.isNew();

		if (!(release instanceof ReleaseModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(release.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(release);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in release proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Release implementation " +
					release.getClass());
		}

		ReleaseModelImpl releaseModelImpl = (ReleaseModelImpl)release;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (release.getCreateDate() == null)) {
			if (serviceContext == null) {
				release.setCreateDate(date);
			}
			else {
				release.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!releaseModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				release.setModifiedDate(date);
			}
			else {
				release.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(release);
			}
			else {
				release = (Release)session.merge(release);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			ReleaseImpl.class, releaseModelImpl, false, true);

		cacheUniqueFindersCache(releaseModelImpl);

		if (isNew) {
			release.setNew(false);
		}

		release.resetOriginalValues();

		return release;
	}

	/**
	 * Returns the release with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the release
	 * @return the release
	 * @throws NoSuchReleaseException if a release with the primary key could not be found
	 */
	@Override
	public Release findByPrimaryKey(Serializable primaryKey)
		throws NoSuchReleaseException {

		Release release = fetchByPrimaryKey(primaryKey);

		if (release == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchReleaseException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return release;
	}

	/**
	 * Returns the release with the primary key or throws a <code>NoSuchReleaseException</code> if it could not be found.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release
	 * @throws NoSuchReleaseException if a release with the primary key could not be found
	 */
	@Override
	public Release findByPrimaryKey(long releaseId)
		throws NoSuchReleaseException {

		return findByPrimaryKey((Serializable)releaseId);
	}

	/**
	 * Returns the release with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release, or <code>null</code> if a release with the primary key could not be found
	 */
	@Override
	public Release fetchByPrimaryKey(long releaseId) {
		return fetchByPrimaryKey((Serializable)releaseId);
	}

	/**
	 * Returns all the releases.
	 *
	 * @return the releases
	 */
	@Override
	public List<Release> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReleaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @return the range of releases
	 */
	@Override
	public List<Release> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReleaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of releases
	 */
	@Override
	public List<Release> findAll(
		int start, int end, OrderByComparator<Release> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ReleaseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of releases
	 */
	@Override
	public List<Release> findAll(
		int start, int end, OrderByComparator<Release> orderByComparator,
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

		List<Release> list = null;

		if (useFinderCache) {
			list = (List<Release>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_RELEASE_);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_RELEASE_;

				sql = sql.concat(ReleaseModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Release>)QueryUtil.list(
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
	 * Removes all the releases from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Release release : findAll()) {
			remove(release);
		}
	}

	/**
	 * Returns the number of releases.
	 *
	 * @return the number of releases
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_RELEASE_);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "releaseId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RELEASE_;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ReleaseModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the release persistence.
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

		_finderPathFetchByServletContextName = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByServletContextName",
			new String[] {String.class.getName()},
			new String[] {"servletContextName"}, true);

		_finderPathCountByServletContextName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByServletContextName", new String[] {String.class.getName()},
			new String[] {"servletContextName"}, false);

		ReleaseUtil.setPersistence(this);
	}

	public void destroy() {
		ReleaseUtil.setPersistence(null);

		EntityCacheUtil.removeCache(ReleaseImpl.class.getName());
	}

	private static final String _SQL_SELECT_RELEASE_ =
		"SELECT release_ FROM Release release_";

	private static final String _SQL_SELECT_RELEASE__WHERE =
		"SELECT release_ FROM Release release_ WHERE ";

	private static final String _SQL_COUNT_RELEASE_ =
		"SELECT COUNT(release_) FROM Release release_";

	private static final String _SQL_COUNT_RELEASE__WHERE =
		"SELECT COUNT(release_) FROM Release release_ WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "release_.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Release exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Release exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ReleasePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"state"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}