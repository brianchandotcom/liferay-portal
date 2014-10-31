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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchPreferencesException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.PortalPreferences;
import com.liferay.portal.model.impl.PortalPreferencesImpl;
import com.liferay.portal.model.impl.PortalPreferencesModelImpl;
import com.liferay.portal.service.persistence.PortalPreferencesPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@ProviderType
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

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or throws a {@link com.liferay.portal.NoSuchPreferencesException} if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the matching portal preferences
	 * @throws com.liferay.portal.NoSuchPreferencesException if a matching portal preferences could not be found
	 */
	@Override
	public PortalPreferences findByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException {
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
	 */
	@Override
	public PortalPreferences fetchByO_O(long ownerId, int ownerType) {
		return fetchByO_O(ownerId, ownerType, true);
	}

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	 */
	@Override
	public PortalPreferences fetchByO_O(long ownerId, int ownerType,
		boolean retrieveFromCache) {
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
			StringBundler query = new StringBundler(4);

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

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"PortalPreferencesPersistenceImpl.fetchByO_O(long, int, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					PortalPreferences portalPreferences = list.get(0);

					result = portalPreferences;

					cacheResult(portalPreferences);

					if ((portalPreferences.getOwnerId() != ownerId) ||
							(portalPreferences.getOwnerType() != ownerType)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O,
							finderArgs, portalPreferences);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O,
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
			return (PortalPreferences)result;
		}
	}

	/**
	 * Removes the portal preferences where ownerId = &#63; and ownerType = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the portal preferences that was removed
	 */
	@Override
	public PortalPreferences removeByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException {
		PortalPreferences portalPreferences = findByO_O(ownerId, ownerType);

		return remove(portalPreferences);
	}

	/**
	 * Returns the number of portal preferenceses where ownerId = &#63; and ownerType = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the number of matching portal preferenceses
	 */
	@Override
	public int countByO_O(long ownerId, int ownerType) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_O_O;

		Object[] finderArgs = new Object[] { ownerId, ownerType };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_O_O_OWNERID_2 = "portalPreferences.ownerId = ? AND ";
	private static final String _FINDER_COLUMN_O_O_OWNERTYPE_2 = "portalPreferences.ownerType = ?";

	public PortalPreferencesPersistenceImpl() {
		setModelClass(PortalPreferences.class);
	}

	/**
	 * Caches the portal preferences in the entity cache if it is enabled.
	 *
	 * @param portalPreferences the portal preferences
	 */
	@Override
	public void cacheResult(PortalPreferences portalPreferences) {
		EntityCacheUtil.putResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortalPreferencesImpl.class, portalPreferences.getPrimaryKey(),
			portalPreferences);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O,
			new Object[] {
				portalPreferences.getOwnerId(), portalPreferences.getOwnerType()
			}, portalPreferences);

		portalPreferences.resetOriginalValues();
	}

	/**
	 * Caches the portal preferenceses in the entity cache if it is enabled.
	 *
	 * @param portalPreferenceses the portal preferenceses
	 */
	@Override
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

		EntityCacheUtil.clearCache(PortalPreferencesImpl.class);

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

	protected void cacheUniqueFindersCache(PortalPreferences portalPreferences) {
		if (portalPreferences.isNew()) {
			Object[] args = new Object[] {
					portalPreferences.getOwnerId(),
					portalPreferences.getOwnerType()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O, args,
				portalPreferences);
		}
		else {
			PortalPreferencesModelImpl portalPreferencesModelImpl = (PortalPreferencesModelImpl)portalPreferences;

			if ((portalPreferencesModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_O_O.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portalPreferences.getOwnerId(),
						portalPreferences.getOwnerType()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O, args,
					portalPreferences);
			}
		}
	}

	protected void clearUniqueFindersCache(PortalPreferences portalPreferences) {
		PortalPreferencesModelImpl portalPreferencesModelImpl = (PortalPreferencesModelImpl)portalPreferences;

		Object[] args = new Object[] {
				portalPreferences.getOwnerId(), portalPreferences.getOwnerType()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O, args);

		if ((portalPreferencesModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_O_O.getColumnBitmask()) != 0) {
			args = new Object[] {
					portalPreferencesModelImpl.getOriginalOwnerId(),
					portalPreferencesModelImpl.getOriginalOwnerType()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_O_O, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O, args);
		}
	}

	/**
	 * Creates a new portal preferences with the primary key. Does not add the portal preferences to the database.
	 *
	 * @param portalPreferencesId the primary key for the new portal preferences
	 * @return the new portal preferences
	 */
	@Override
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
	 */
	@Override
	public PortalPreferences remove(long portalPreferencesId)
		throws NoSuchPreferencesException {
		return remove((Serializable)portalPreferencesId);
	}

	/**
	 * Removes the portal preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portal preferences
	 * @return the portal preferences that was removed
	 * @throws com.liferay.portal.NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences remove(Serializable primaryKey)
		throws NoSuchPreferencesException {
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
	protected PortalPreferences removeImpl(PortalPreferences portalPreferences) {
		portalPreferences = toUnwrappedModel(portalPreferences);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portalPreferences)) {
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
		com.liferay.portal.model.PortalPreferences portalPreferences) {
		portalPreferences = toUnwrappedModel(portalPreferences);

		boolean isNew = portalPreferences.isNew();

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
			portalPreferences, false);

		clearUniqueFindersCache(portalPreferences);
		cacheUniqueFindersCache(portalPreferences);

		portalPreferences.resetOriginalValues();

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

		portalPreferencesImpl.setMvccVersion(portalPreferences.getMvccVersion());
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
	 * @throws com.liferay.portal.NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPreferencesException {
		PortalPreferences portalPreferences = fetchByPrimaryKey(primaryKey);

		if (portalPreferences == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPreferencesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return portalPreferences;
	}

	/**
	 * Returns the portal preferences with the primary key or throws a {@link com.liferay.portal.NoSuchPreferencesException} if it could not be found.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences
	 * @throws com.liferay.portal.NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences findByPrimaryKey(long portalPreferencesId)
		throws NoSuchPreferencesException {
		return findByPrimaryKey((Serializable)portalPreferencesId);
	}

	/**
	 * Returns the portal preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portal preferences
	 * @return the portal preferences, or <code>null</code> if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences fetchByPrimaryKey(Serializable primaryKey) {
		PortalPreferences portalPreferences = (PortalPreferences)EntityCacheUtil.getResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
				PortalPreferencesImpl.class, primaryKey);

		if (portalPreferences == _nullPortalPreferences) {
			return null;
		}

		if (portalPreferences == null) {
			Session session = null;

			try {
				session = openSession();

				portalPreferences = (PortalPreferences)session.get(PortalPreferencesImpl.class,
						primaryKey);

				if (portalPreferences != null) {
					cacheResult(portalPreferences);
				}
				else {
					EntityCacheUtil.putResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
						PortalPreferencesImpl.class, primaryKey,
						_nullPortalPreferences);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
					PortalPreferencesImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return portalPreferences;
	}

	/**
	 * Returns the portal preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences, or <code>null</code> if a portal preferences with the primary key could not be found
	 */
	@Override
	public PortalPreferences fetchByPrimaryKey(long portalPreferencesId) {
		return fetchByPrimaryKey((Serializable)portalPreferencesId);
	}

	@Override
	public Map<Serializable, PortalPreferences> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PortalPreferences> map = new HashMap<Serializable, PortalPreferences>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PortalPreferences portalPreferences = fetchByPrimaryKey(primaryKey);

			if (portalPreferences != null) {
				map.put(primaryKey, portalPreferences);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			PortalPreferences portalPreferences = (PortalPreferences)EntityCacheUtil.getResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
					PortalPreferencesImpl.class, primaryKey);

			if (portalPreferences == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, portalPreferences);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_PORTALPREFERENCES_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (PortalPreferences portalPreferences : (List<PortalPreferences>)q.list()) {
				map.put(portalPreferences.getPrimaryKeyObj(), portalPreferences);

				cacheResult(portalPreferences);

				uncachedPrimaryKeys.remove(portalPreferences.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(PortalPreferencesModelImpl.ENTITY_CACHE_ENABLED,
					PortalPreferencesImpl.class, primaryKey,
					_nullPortalPreferences);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the portal preferenceses.
	 *
	 * @return the portal preferenceses
	 */
	@Override
	public List<PortalPreferences> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portal preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortalPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @return the range of portal preferenceses
	 */
	@Override
	public List<PortalPreferences> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portal preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortalPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portal preferenceses
	 */
	@Override
	public List<PortalPreferences> findAll(int start, int end,
		OrderByComparator<PortalPreferences> orderByComparator) {
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

				if (pagination) {
					sql = sql.concat(PortalPreferencesModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PortalPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PortalPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the portal preferenceses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PortalPreferences portalPreferences : findAll()) {
			remove(portalPreferences);
		}
	}

	/**
	 * Returns the number of portal preferenceses.
	 *
	 * @return the number of portal preferenceses
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PORTALPREFERENCES);

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
	 * Initializes the portal preferences persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PortalPreferencesImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PORTALPREFERENCES = "SELECT portalPreferences FROM PortalPreferences portalPreferences";
	private static final String _SQL_SELECT_PORTALPREFERENCES_WHERE_PKS_IN = "SELECT portalPreferences FROM PortalPreferences portalPreferences WHERE portalPreferencesId IN (";
	private static final String _SQL_SELECT_PORTALPREFERENCES_WHERE = "SELECT portalPreferences FROM PortalPreferences portalPreferences WHERE ";
	private static final String _SQL_COUNT_PORTALPREFERENCES = "SELECT COUNT(portalPreferences) FROM PortalPreferences portalPreferences";
	private static final String _SQL_COUNT_PORTALPREFERENCES_WHERE = "SELECT COUNT(portalPreferences) FROM PortalPreferences portalPreferences WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portalPreferences.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortalPreferences exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortalPreferences exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(PortalPreferencesPersistenceImpl.class);
	private static final PortalPreferences _nullPortalPreferences = new PortalPreferencesImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PortalPreferences> toCacheModel() {
				return _nullPortalPreferencesCacheModel;
			}
		};

	private static final CacheModel<PortalPreferences> _nullPortalPreferencesCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<PortalPreferences>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public PortalPreferences toEntityModel() {
			return _nullPortalPreferences;
		}
	}
}