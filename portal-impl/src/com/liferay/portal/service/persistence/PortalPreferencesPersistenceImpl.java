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
import com.liferay.portal.NoSuchPreferencesException;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortalPreferences;
import com.liferay.portal.model.impl.PortalPreferencesImpl;
import com.liferay.portal.model.impl.PortalPreferencesModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the portal preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferencesPersistence
 * @see PortalPreferencesUtil
 * @generated
 */
public class PortalPreferencesPersistenceImpl extends BasePersistenceImpl<PortalPreferences>
	implements PortalPreferencesPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PortalPreferencesUtil} to access the portal preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PortalPreferencesImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_O_O = new FinderPath(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortalPreferencesImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByO_O",
			new String[] { Long.class.getName(), Integer.class.getName() },
			PortalPreferencesModelImpl.OWNERID_COLUMN_BITMASK |
			PortalPreferencesModelImpl.OWNERTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_O_O = new FinderPath(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_O",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortalPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortalPreferencesImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the portal preferences in the entity cache if it is enabled.
	 *
	 * @param portalPreferences the portal preferences
	 */
	public void cacheResult(PortalPreferences portalPreferences) {
		EntityCacheUtil.putResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesImpl.class, portalPreferences.getPrimaryKey(),
			portalPreferences);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O,
			new Object[] {
				Long.valueOf(portalPreferences.getOwnerId()),
				Integer.valueOf(portalPreferences.getOwnerType())
			}, portalPreferences);

		portalPreferences.resetOriginalValues();
	}

	/**
	 * Caches the portal preferenceses in the entity cache if it is enabled.
	 *
	 * @param portalPreferenceses the portal preferenceses
	 */
	public void cacheResult(List<PortalPreferences> portalPreferenceses) {
		for (PortalPreferences portalPreferences : portalPreferenceses) {
			if (EntityCacheUtil.getResult(
						PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
						PortalPreferencesImpl.class,
						portalPreferences.getPrimaryKey()) == null) {
				cacheResult(portalPreferences);
			}
			else {
				portalPreferences.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all portal preferenceses.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PortalPreferencesImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PortalPreferencesImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the portal preferences.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortalPreferences portalPreferences) {
		EntityCacheUtil.removeResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesImpl.class, portalPreferences.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(portalPreferences);
	}

	@Override
	public void clearCache(List<PortalPreferences> portalPreferenceses) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PortalPreferences portalPreferences : portalPreferenceses) {
			EntityCacheUtil.removeResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
				PortalPreferencesImpl.class, portalPreferences.getPrimaryKey());

			clearUniqueFindersCache(portalPreferences);
		}
	}

	protected void clearUniqueFindersCache(PortalPreferences portalPreferences) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O,
			new Object[] {
				Long.valueOf(portalPreferences.getOwnerId()),
				Integer.valueOf(portalPreferences.getOwnerType())
			});
	}

	/**
	 * Creates a new portal preferences with the primary key. Does not add the portal preferences to the database.
	 *
	 * @param portalPreferencesId the primary key for the new portal preferences
	 * @return the new portal preferences
	 */
	public PortalPreferences create(long portalPreferencesId) {
		PortalPreferences portalPreferences = new PortalPreferencesImpl();

		portalPreferences.setNew(true);
		portalPreferences.setPrimaryKey(portalPreferencesId);

		return portalPreferences;
	}

	/**
	 * Removes the portal preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences that was removed
	 * @throws com.liferay.portal.NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortalPreferences remove(long portalPreferencesId)
		throws NoSuchPreferencesException, SystemException {
		return remove(Long.valueOf(portalPreferencesId));
	}

	/**
	 * Removes the portal preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portal preferences
	 * @return the portal preferences that was removed
	 * @throws com.liferay.portal.NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortalPreferences remove(Serializable primaryKey)
		throws NoSuchPreferencesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortalPreferences portalPreferences = (PortalPreferences)session.get(PortalPreferencesImpl.class,
					primaryKey);

			if (portalPreferences == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPreferencesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(portalPreferences);
		}
		catch (NoSuchPreferencesException nsee) {
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
	protected PortalPreferences removeImpl(PortalPreferences portalPreferences)
		throws SystemException {
		portalPreferences = toUnwrappedModel(portalPreferences);

		Session session = null;

		try {
			session = openSession();

			if (portalPreferences.isCachedModel()) {
				portalPreferences = (PortalPreferences)session.get(PortalPreferencesImpl.class,
						portalPreferences.getPrimaryKeyObj());
			}

			if (portalPreferences != null) {
				session.delete(portalPreferences);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (portalPreferences != null) {
			clearCache(portalPreferences);
		}

		return portalPreferences;
	}

	@Override
	public PortalPreferences updateImpl(
		com.liferay.portal.model.PortalPreferences portalPreferences)
		throws SystemException {
		portalPreferences = toUnwrappedModel(portalPreferences);

		boolean isNew = portalPreferences.isNew();

		PortalPreferencesModelImpl portalPreferencesModelImpl = (PortalPreferencesModelImpl)portalPreferences;

		Session session = null;

		try {
			session = openSession();

			if (portalPreferences.isNew()) {
				session.save(portalPreferences);

				portalPreferences.setNew(false);
			}
			else {
				session.merge(portalPreferences);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PortalPreferencesModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesImpl.class, portalPreferences.getPrimaryKey(),
			portalPreferences);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O,
				new Object[] {
					Long.valueOf(portalPreferences.getOwnerId()),
					Integer.valueOf(portalPreferences.getOwnerType())
				}, portalPreferences);
		}
		else {
			if ((portalPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_O_O.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(portalPreferencesModelImpl.getOriginalOwnerId()),
						Integer.valueOf(portalPreferencesModelImpl.getOriginalOwnerType())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O,
					new Object[] {
						Long.valueOf(portalPreferences.getOwnerId()),
						Integer.valueOf(portalPreferences.getOwnerType())
					}, portalPreferences);
			}
		}

		return portalPreferences;
	}

	protected PortalPreferences toUnwrappedModel(
		PortalPreferences portalPreferences) {
		if (portalPreferences instanceof PortalPreferencesImpl) {
			return portalPreferences;
		}

		PortalPreferencesImpl portalPreferencesImpl = new PortalPreferencesImpl();

		portalPreferencesImpl.setNew(portalPreferences.isNew());
		portalPreferencesImpl.setPrimaryKey(portalPreferences.getPrimaryKey());

		portalPreferencesImpl.setPortalPreferencesId(portalPreferences.getPortalPreferencesId());
		portalPreferencesImpl.setOwnerId(portalPreferences.getOwnerId());
		portalPreferencesImpl.setOwnerType(portalPreferences.getOwnerType());
		portalPreferencesImpl.setPreferences(portalPreferences.getPreferences());

		return portalPreferencesImpl;
	}

	/**
	 * Returns the portal preferences with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the portal preferences
	 * @return the portal preferences
	 * @throws com.liferay.portal.NoSuchModelException if a portal preferences with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortalPreferences findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the portal preferences with the primary key or throws a {@link com.liferay.portal.NoSuchPreferencesException} if it could not be found.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences
	 * @throws com.liferay.portal.NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortalPreferences findByPrimaryKey(long portalPreferencesId)
		throws NoSuchPreferencesException, SystemException {
		PortalPreferences portalPreferences = fetchByPrimaryKey(portalPreferencesId);

		if (portalPreferences == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					portalPreferencesId);
			}

			throw new NoSuchPreferencesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				portalPreferencesId);
		}

		return portalPreferences;
	}

	/**
	 * Returns the portal preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portal preferences
	 * @return the portal preferences, or <code>null</code> if a portal preferences with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortalPreferences fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the portal preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences, or <code>null</code> if a portal preferences with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortalPreferences fetchByPrimaryKey(long portalPreferencesId)
		throws SystemException {
		PortalPreferences portalPreferences = (PortalPreferences)EntityCacheUtil.getResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
				PortalPreferencesImpl.class, portalPreferencesId);

		if (portalPreferences == _nullPortalPreferences) {
			return null;
		}

		if (portalPreferences == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				portalPreferences = (PortalPreferences)session.get(PortalPreferencesImpl.class,
						Long.valueOf(portalPreferencesId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (portalPreferences != null) {
					cacheResult(portalPreferences);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
						PortalPreferencesImpl.class, portalPreferencesId,
						_nullPortalPreferences);
				}

				closeSession(session);
			}
		}

		return portalPreferences;
	}

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or throws a {@link com.liferay.portal.NoSuchPreferencesException} if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the matching portal preferences
	 * @throws com.liferay.portal.NoSuchPreferencesException if a matching portal preferences could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortalPreferences findByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException, SystemException {
		PortalPreferences portalPreferences = fetchByO_O(ownerId, ownerType);

		if (portalPreferences == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ownerId=");
			msg.append(ownerId);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPreferencesException(msg.toString());
		}

		return portalPreferences;
	}

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortalPreferences fetchByO_O(long ownerId, int ownerType)
		throws SystemException {
		return fetchByO_O(ownerId, ownerType, true);
	}

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public PortalPreferences fetchByO_O(long ownerId, int ownerType,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { ownerId, ownerType };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_O_O,
					finderArgs, this);
		}

		if (result instanceof PortalPreferences) {
			PortalPreferences portalPreferences = (PortalPreferences)result;

			if ((ownerId != portalPreferences.getOwnerId()) ||
					(ownerType != portalPreferences.getOwnerType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_PORTALPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_O_OWNERID_2);

			query.append(_FINDER_COLUMN_O_O_OWNERTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				List<PortalPreferences> list = q.list();

				result = list;

				PortalPreferences portalPreferences = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O,
						finderArgs, list);
				}
				else {
					portalPreferences = list.get(0);

					cacheResult(portalPreferences);

					if ((portalPreferences.getOwnerId() != ownerId) ||
							(portalPreferences.getOwnerType() != ownerType)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O,
							finderArgs, portalPreferences);
					}
				}

				return portalPreferences;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O,
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
				return (PortalPreferences)result;
			}
		}
	}

	/**
	 * Returns all the portal preferenceses.
	 *
	 * @return the portal preferenceses
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortalPreferences> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portal preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @return the range of portal preferenceses
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortalPreferences> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portal preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portal preferenceses
	 * @throws SystemException if a system exception occurred
	 */
	public List<PortalPreferences> findAll(int start, int end,
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

		List<PortalPreferences> list = (List<PortalPreferences>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PORTALPREFERENCES);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PORTALPREFERENCES;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<PortalPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PortalPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes the portal preferences where ownerId = &#63; and ownerType = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the portal preferences that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public PortalPreferences removeByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException, SystemException {
		PortalPreferences portalPreferences = findByO_O(ownerId, ownerType);

		return remove(portalPreferences);
	}

	/**
	 * Removes all the portal preferenceses from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (PortalPreferences portalPreferences : findAll()) {
			remove(portalPreferences);
		}
	}

	/**
	 * Returns the number of portal preferenceses where ownerId = &#63; and ownerType = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the number of matching portal preferenceses
	 * @throws SystemException if a system exception occurred
	 */
	public int countByO_O(long ownerId, int ownerType)
		throws SystemException {
		Object[] finderArgs = new Object[] { ownerId, ownerType };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_O_O,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PORTALPREFERENCES_WHERE);

			query.append(_FINDER_COLUMN_O_O_OWNERID_2);

			query.append(_FINDER_COLUMN_O_O_OWNERTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of portal preferenceses.
	 *
	 * @return the number of portal preferenceses
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PORTALPREFERENCES);

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
	 * Initializes the portal preferences persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PortalPreferences")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortalPreferences>> listenersList = new ArrayList<ModelListener<PortalPreferences>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortalPreferences>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PortalPreferencesImpl.class.getName());
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
	private static final String _SQL_SELECT_PORTALPREFERENCES = "SELECT portalPreferences FROM PortalPreferences portalPreferences";
	private static final String _SQL_SELECT_PORTALPREFERENCES_WHERE = "SELECT portalPreferences FROM PortalPreferences portalPreferences WHERE ";
	private static final String _SQL_COUNT_PORTALPREFERENCES = "SELECT COUNT(portalPreferences) FROM PortalPreferences portalPreferences";
	private static final String _SQL_COUNT_PORTALPREFERENCES_WHERE = "SELECT COUNT(portalPreferences) FROM PortalPreferences portalPreferences WHERE ";
	private static final String _FINDER_COLUMN_O_O_OWNERID_2 = "portalPreferences.ownerId = ? AND ";
	private static final String _FINDER_COLUMN_O_O_OWNERTYPE_2 = "portalPreferences.ownerType = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portalPreferences.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortalPreferences exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortalPreferences exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PortalPreferencesPersistenceImpl.class);
	private static PortalPreferences _nullPortalPreferences = new PortalPreferencesImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PortalPreferences> toCacheModel() {
				return _nullPortalPreferencesCacheModel;
			}
		};

	private static CacheModel<PortalPreferences> _nullPortalPreferencesCacheModel =
		new CacheModel<PortalPreferences>() {
			public PortalPreferences toEntityModel() {
				return _nullPortalPreferences;
			}
		};
}