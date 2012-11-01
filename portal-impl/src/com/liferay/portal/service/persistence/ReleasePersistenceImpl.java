/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchReleaseException;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.impl.ReleaseImpl;
import com.liferay.portal.model.impl.ReleaseModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the release service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReleasePersistence
 * @see ReleaseUtil
 * @generated
 */
public class ReleasePersistenceImpl extends BasePersistenceImpl<Release>
	implements ReleasePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ReleaseUtil} to access the release persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ReleaseImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME = new FinderPath(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
			ReleaseModelImpl.FINDER_CACHE_ENABLED, ReleaseImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByServletContextName",
			new String[] { String.class.getName() },
			ReleaseModelImpl.SERVLETCONTEXTNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SERVLETCONTEXTNAME = new FinderPath(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
			ReleaseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByServletContextName", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
			ReleaseModelImpl.FINDER_CACHE_ENABLED, ReleaseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
			ReleaseModelImpl.FINDER_CACHE_ENABLED, ReleaseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
			ReleaseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the release in the entity cache if it is enabled.
	 *
	 * @param release the release
	 */
	public void cacheResult(Release release) {
		EntityCacheUtil.putResult(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
			ReleaseImpl.class, release.getPrimaryKey(), release);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
			new Object[] { release.getServletContextName() }, release);

		release.resetOriginalValues();
	}

	/**
	 * Caches the releases in the entity cache if it is enabled.
	 *
	 * @param releases the releases
	 */
	public void cacheResult(List<Release> releases) {
		for (Release release : releases) {
			if (EntityCacheUtil.getResult(
						ReleaseModelImpl.ENTITY_CACHE_ENABLED,
						ReleaseImpl.class, release.getPrimaryKey()) == null) {
				cacheResult(release);
			}
			else {
				release.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all releases.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ReleaseImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ReleaseImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the release.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Release release) {
		EntityCacheUtil.removeResult(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
			ReleaseImpl.class, release.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(release);
	}

	@Override
	public void clearCache(List<Release> releases) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Release release : releases) {
			EntityCacheUtil.removeResult(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
				ReleaseImpl.class, release.getPrimaryKey());

			clearUniqueFindersCache(release);
		}
	}

	protected void clearUniqueFindersCache(Release release) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
			new Object[] { release.getServletContextName() });
	}

	/**
	 * Creates a new release with the primary key. Does not add the release to the database.
	 *
	 * @param releaseId the primary key for the new release
	 * @return the new release
	 */
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
	 * @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Release remove(long releaseId)
		throws NoSuchReleaseException, SystemException {
		return remove(Long.valueOf(releaseId));
	}

	/**
	 * Removes the release with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the release
	 * @return the release that was removed
	 * @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Release remove(Serializable primaryKey)
		throws NoSuchReleaseException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Release release = (Release)session.get(ReleaseImpl.class, primaryKey);

			if (release == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchReleaseException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(release);
		}
		catch (NoSuchReleaseException nsee) {
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
	protected Release removeImpl(Release release) throws SystemException {
		release = toUnwrappedModel(release);

		Session session = null;

		try {
			session = openSession();

			if (release.isCachedModel()) {
				release = (Release)session.get(ReleaseImpl.class,
						release.getPrimaryKeyObj());
			}

			if (release != null) {
				session.delete(release);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
	public Release updateImpl(com.liferay.portal.model.Release release)
		throws SystemException {
		release = toUnwrappedModel(release);

		boolean isNew = release.isNew();

		ReleaseModelImpl releaseModelImpl = (ReleaseModelImpl)release;

		Session session = null;

		try {
			session = openSession();

			if (release.isNew()) {
				session.save(release);

				release.setNew(false);
			}
			else {
				session.merge(release);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ReleaseModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
			ReleaseImpl.class, release.getPrimaryKey(), release);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
				new Object[] { release.getServletContextName() }, release);
		}
		else {
			if ((releaseModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						releaseModelImpl.getOriginalServletContextName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_SERVLETCONTEXTNAME,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
					new Object[] { release.getServletContextName() }, release);
			}
		}

		return release;
	}

	protected Release toUnwrappedModel(Release release) {
		if (release instanceof ReleaseImpl) {
			return release;
		}

		ReleaseImpl releaseImpl = new ReleaseImpl();

		releaseImpl.setNew(release.isNew());
		releaseImpl.setPrimaryKey(release.getPrimaryKey());

		releaseImpl.setReleaseId(release.getReleaseId());
		releaseImpl.setCreateDate(release.getCreateDate());
		releaseImpl.setModifiedDate(release.getModifiedDate());
		releaseImpl.setServletContextName(release.getServletContextName());
		releaseImpl.setBuildNumber(release.getBuildNumber());
		releaseImpl.setBuildDate(release.getBuildDate());
		releaseImpl.setVerified(release.isVerified());
		releaseImpl.setState(release.getState());
		releaseImpl.setTestString(release.getTestString());

		return releaseImpl;
	}

	/**
	 * Returns the release with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the release
	 * @return the release
	 * @throws com.liferay.portal.NoSuchModelException if a release with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Release findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the release with the primary key or throws a {@link com.liferay.portal.NoSuchReleaseException} if it could not be found.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release
	 * @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Release findByPrimaryKey(long releaseId)
		throws NoSuchReleaseException, SystemException {
		Release release = fetchByPrimaryKey(releaseId);

		if (release == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + releaseId);
			}

			throw new NoSuchReleaseException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				releaseId);
		}

		return release;
	}

	/**
	 * Returns the release with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the release
	 * @return the release, or <code>null</code> if a release with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Release fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the release with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param releaseId the primary key of the release
	 * @return the release, or <code>null</code> if a release with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Release fetchByPrimaryKey(long releaseId) throws SystemException {
		Release release = (Release)EntityCacheUtil.getResult(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
				ReleaseImpl.class, releaseId);

		if (release == _nullRelease) {
			return null;
		}

		if (release == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				release = (Release)session.get(ReleaseImpl.class,
						Long.valueOf(releaseId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (release != null) {
					cacheResult(release);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ReleaseModelImpl.ENTITY_CACHE_ENABLED,
						ReleaseImpl.class, releaseId, _nullRelease);
				}

				closeSession(session);
			}
		}

		return release;
	}

	/**
	 * Returns the release where servletContextName = &#63; or throws a {@link com.liferay.portal.NoSuchReleaseException} if it could not be found.
	 *
	 * @param servletContextName the servlet context name
	 * @return the matching release
	 * @throws com.liferay.portal.NoSuchReleaseException if a matching release could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Release findByServletContextName(String servletContextName)
		throws NoSuchReleaseException, SystemException {
		Release release = fetchByServletContextName(servletContextName);

		if (release == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("servletContextName=");
			msg.append(servletContextName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchReleaseException(msg.toString());
		}

		return release;
	}

	/**
	 * Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param servletContextName the servlet context name
	 * @return the matching release, or <code>null</code> if a matching release could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Release fetchByServletContextName(String servletContextName)
		throws SystemException {
		return fetchByServletContextName(servletContextName, true);
	}

	/**
	 * Returns the release where servletContextName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param servletContextName the servlet context name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching release, or <code>null</code> if a matching release could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Release fetchByServletContextName(String servletContextName,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { servletContextName };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
					finderArgs, this);
		}

		if (result instanceof Release) {
			Release release = (Release)result;

			if (!Validator.equals(servletContextName,
						release.getServletContextName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_RELEASE_WHERE);

			if (servletContextName == null) {
				query.append(_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_1);
			}
			else {
				if (servletContextName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (servletContextName != null) {
					qPos.add(servletContextName);
				}

				List<Release> list = q.list();

				result = list;

				Release release = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
						finderArgs, list);
				}
				else {
					release = list.get(0);

					cacheResult(release);

					if ((release.getServletContextName() == null) ||
							!release.getServletContextName()
										.equals(servletContextName)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
							finderArgs, release);
					}
				}

				return release;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_SERVLETCONTEXTNAME,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Release)result;
			}
		}
	}

	/**
	 * Returns all the releases.
	 *
	 * @return the releases
	 * @throws SystemException if a system exception occurred
	 */
	public List<Release> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @return the range of releases
	 * @throws SystemException if a system exception occurred
	 */
	public List<Release> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the releases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of releases
	 * @param end the upper bound of the range of releases (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of releases
	 * @throws SystemException if a system exception occurred
	 */
	public List<Release> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Release> list = (List<Release>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RELEASE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RELEASE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Release>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Release>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes the release where servletContextName = &#63; from the database.
	 *
	 * @param servletContextName the servlet context name
	 * @return the release that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Release removeByServletContextName(String servletContextName)
		throws NoSuchReleaseException, SystemException {
		Release release = findByServletContextName(servletContextName);

		return remove(release);
	}

	/**
	 * Removes all the releases from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Release release : findAll()) {
			remove(release);
		}
	}

	/**
	 * Returns the number of releases where servletContextName = &#63;.
	 *
	 * @param servletContextName the servlet context name
	 * @return the number of matching releases
	 * @throws SystemException if a system exception occurred
	 */
	public int countByServletContextName(String servletContextName)
		throws SystemException {
		Object[] finderArgs = new Object[] { servletContextName };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SERVLETCONTEXTNAME,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RELEASE_WHERE);

			if (servletContextName == null) {
				query.append(_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_1);
			}
			else {
				if (servletContextName.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (servletContextName != null) {
					qPos.add(servletContextName);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SERVLETCONTEXTNAME,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of releases.
	 *
	 * @return the number of releases
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RELEASE);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the release persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Release")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Release>> listenersList = new ArrayList<ModelListener<Release>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Release>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ReleaseImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_RELEASE = "SELECT release FROM Release release";
	private static final String _SQL_SELECT_RELEASE_WHERE = "SELECT release FROM Release release WHERE ";
	private static final String _SQL_COUNT_RELEASE = "SELECT COUNT(release) FROM Release release";
	private static final String _SQL_COUNT_RELEASE_WHERE = "SELECT COUNT(release) FROM Release release WHERE ";
	private static final String _FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_1 =
		"release.servletContextName IS NULL";
	private static final String _FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_2 =
		"lower(release.servletContextName) = lower(CAST_TEXT(?))";
	private static final String _FINDER_COLUMN_SERVLETCONTEXTNAME_SERVLETCONTEXTNAME_3 =
		"(release.servletContextName IS NULL OR lower(release.servletContextName) = lower(CAST_TEXT(?)))";
	private static final String _ORDER_BY_ENTITY_ALIAS = "release.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Release exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Release exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ReleasePersistenceImpl.class);
	private static Release _nullRelease = new ReleaseImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Release> toCacheModel() {
				return _nullReleaseCacheModel;
			}
		};

	private static CacheModel<Release> _nullReleaseCacheModel = new CacheModel<Release>() {
			public Release toEntityModel() {
				return _nullRelease;
			}
		};
}