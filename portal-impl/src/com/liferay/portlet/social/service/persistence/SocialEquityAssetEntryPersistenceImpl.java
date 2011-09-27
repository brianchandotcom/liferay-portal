/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchEquityAssetEntryException;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.impl.SocialEquityAssetEntryImpl;
import com.liferay.portlet.social.model.impl.SocialEquityAssetEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social equity asset entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityAssetEntryPersistence
 * @see SocialEquityAssetEntryUtil
 * @generated
 */
public class SocialEquityAssetEntryPersistenceImpl extends BasePersistenceImpl<SocialEquityAssetEntry>
	implements SocialEquityAssetEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialEquityAssetEntryUtil} to access the social equity asset entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquityAssetEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_FETCH_BY_ASSETENTRYID = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByAssetEntryId", new String[] { Long.class.getName() },
			SocialEquityAssetEntryModelImpl.ASSETENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYID = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the social equity asset entry in the entity cache if it is enabled.
	 *
	 * @param socialEquityAssetEntry the social equity asset entry
	 */
	public void cacheResult(SocialEquityAssetEntry socialEquityAssetEntry) {
		EntityCacheUtil.putResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			socialEquityAssetEntry.getPrimaryKey(), socialEquityAssetEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
			new Object[] { Long.valueOf(
					socialEquityAssetEntry.getAssetEntryId()) },
			socialEquityAssetEntry);

		socialEquityAssetEntry.resetOriginalValues();
	}

	/**
	 * Caches the social equity asset entries in the entity cache if it is enabled.
	 *
	 * @param socialEquityAssetEntries the social equity asset entries
	 */
	public void cacheResult(
		List<SocialEquityAssetEntry> socialEquityAssetEntries) {
		for (SocialEquityAssetEntry socialEquityAssetEntry : socialEquityAssetEntries) {
			if (EntityCacheUtil.getResult(
						SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityAssetEntryImpl.class,
						socialEquityAssetEntry.getPrimaryKey()) == null) {
				cacheResult(socialEquityAssetEntry);
			}
		}
	}

	/**
	 * Clears the cache for all social equity asset entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialEquityAssetEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialEquityAssetEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social equity asset entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialEquityAssetEntry socialEquityAssetEntry) {
		EntityCacheUtil.removeResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			socialEquityAssetEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
			new Object[] { Long.valueOf(
					socialEquityAssetEntry.getAssetEntryId()) });
	}

	/**
	 * Creates a new social equity asset entry with the primary key. Does not add the social equity asset entry to the database.
	 *
	 * @param equityAssetEntryId the primary key for the new social equity asset entry
	 * @return the new social equity asset entry
	 */
	public SocialEquityAssetEntry create(long equityAssetEntryId) {
		SocialEquityAssetEntry socialEquityAssetEntry = new SocialEquityAssetEntryImpl();

		socialEquityAssetEntry.setNew(true);
		socialEquityAssetEntry.setPrimaryKey(equityAssetEntryId);

		return socialEquityAssetEntry;
	}

	/**
	 * Removes the social equity asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social equity asset entry
	 * @return the social equity asset entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a social equity asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityAssetEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the social equity asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param equityAssetEntryId the primary key of the social equity asset entry
	 * @return the social equity asset entry that was removed
	 * @throws com.liferay.portlet.social.NoSuchEquityAssetEntryException if a social equity asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityAssetEntry remove(long equityAssetEntryId)
		throws NoSuchEquityAssetEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquityAssetEntry socialEquityAssetEntry = (SocialEquityAssetEntry)session.get(SocialEquityAssetEntryImpl.class,
					Long.valueOf(equityAssetEntryId));

			if (socialEquityAssetEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						equityAssetEntryId);
				}

				throw new NoSuchEquityAssetEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityAssetEntryId);
			}

			return socialEquityAssetEntryPersistence.remove(socialEquityAssetEntry);
		}
		catch (NoSuchEquityAssetEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the social equity asset entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param socialEquityAssetEntry the social equity asset entry
	 * @return the social equity asset entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityAssetEntry remove(
		SocialEquityAssetEntry socialEquityAssetEntry)
		throws SystemException {
		return super.remove(socialEquityAssetEntry);
	}

	@Override
	protected SocialEquityAssetEntry removeImpl(
		SocialEquityAssetEntry socialEquityAssetEntry)
		throws SystemException {
		socialEquityAssetEntry = toUnwrappedModel(socialEquityAssetEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, socialEquityAssetEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		SocialEquityAssetEntryModelImpl socialEquityAssetEntryModelImpl = (SocialEquityAssetEntryModelImpl)socialEquityAssetEntry;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
			new Object[] {
				Long.valueOf(socialEquityAssetEntryModelImpl.getAssetEntryId())
			});

		EntityCacheUtil.removeResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			socialEquityAssetEntry.getPrimaryKey());

		return socialEquityAssetEntry;
	}

	@Override
	public SocialEquityAssetEntry updateImpl(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry,
		boolean merge) throws SystemException {
		socialEquityAssetEntry = toUnwrappedModel(socialEquityAssetEntry);

		boolean isNew = socialEquityAssetEntry.isNew();

		SocialEquityAssetEntryModelImpl socialEquityAssetEntryModelImpl = (SocialEquityAssetEntryModelImpl)socialEquityAssetEntry;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquityAssetEntry, merge);

			socialEquityAssetEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SocialEquityAssetEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquityAssetEntryImpl.class,
			socialEquityAssetEntry.getPrimaryKey(), socialEquityAssetEntry);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
				new Object[] {
					Long.valueOf(socialEquityAssetEntry.getAssetEntryId())
				}, socialEquityAssetEntry);
		}
		else {
			if ((socialEquityAssetEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_ASSETENTRYID.getColumnBitmask()) != 0) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
					new Object[] {
						Long.valueOf(
							socialEquityAssetEntryModelImpl.getOriginalAssetEntryId())
					});

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
					new Object[] {
						Long.valueOf(socialEquityAssetEntry.getAssetEntryId())
					}, socialEquityAssetEntry);
			}
		}

		return socialEquityAssetEntry;
	}

	protected SocialEquityAssetEntry toUnwrappedModel(
		SocialEquityAssetEntry socialEquityAssetEntry) {
		if (socialEquityAssetEntry instanceof SocialEquityAssetEntryImpl) {
			return socialEquityAssetEntry;
		}

		SocialEquityAssetEntryImpl socialEquityAssetEntryImpl = new SocialEquityAssetEntryImpl();

		socialEquityAssetEntryImpl.setNew(socialEquityAssetEntry.isNew());
		socialEquityAssetEntryImpl.setPrimaryKey(socialEquityAssetEntry.getPrimaryKey());

		socialEquityAssetEntryImpl.setEquityAssetEntryId(socialEquityAssetEntry.getEquityAssetEntryId());
		socialEquityAssetEntryImpl.setGroupId(socialEquityAssetEntry.getGroupId());
		socialEquityAssetEntryImpl.setCompanyId(socialEquityAssetEntry.getCompanyId());
		socialEquityAssetEntryImpl.setUserId(socialEquityAssetEntry.getUserId());
		socialEquityAssetEntryImpl.setAssetEntryId(socialEquityAssetEntry.getAssetEntryId());
		socialEquityAssetEntryImpl.setInformationK(socialEquityAssetEntry.getInformationK());
		socialEquityAssetEntryImpl.setInformationB(socialEquityAssetEntry.getInformationB());

		return socialEquityAssetEntryImpl;
	}

	/**
	 * Returns the social equity asset entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity asset entry
	 * @return the social equity asset entry
	 * @throws com.liferay.portal.NoSuchModelException if a social equity asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityAssetEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity asset entry with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityAssetEntryException} if it could not be found.
	 *
	 * @param equityAssetEntryId the primary key of the social equity asset entry
	 * @return the social equity asset entry
	 * @throws com.liferay.portlet.social.NoSuchEquityAssetEntryException if a social equity asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityAssetEntry findByPrimaryKey(long equityAssetEntryId)
		throws NoSuchEquityAssetEntryException, SystemException {
		SocialEquityAssetEntry socialEquityAssetEntry = fetchByPrimaryKey(equityAssetEntryId);

		if (socialEquityAssetEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equityAssetEntryId);
			}

			throw new NoSuchEquityAssetEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equityAssetEntryId);
		}

		return socialEquityAssetEntry;
	}

	/**
	 * Returns the social equity asset entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity asset entry
	 * @return the social equity asset entry, or <code>null</code> if a social equity asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialEquityAssetEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the social equity asset entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param equityAssetEntryId the primary key of the social equity asset entry
	 * @return the social equity asset entry, or <code>null</code> if a social equity asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityAssetEntry fetchByPrimaryKey(long equityAssetEntryId)
		throws SystemException {
		SocialEquityAssetEntry socialEquityAssetEntry = (SocialEquityAssetEntry)EntityCacheUtil.getResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquityAssetEntryImpl.class, equityAssetEntryId);

		if (socialEquityAssetEntry == _nullSocialEquityAssetEntry) {
			return null;
		}

		if (socialEquityAssetEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				socialEquityAssetEntry = (SocialEquityAssetEntry)session.get(SocialEquityAssetEntryImpl.class,
						Long.valueOf(equityAssetEntryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (socialEquityAssetEntry != null) {
					cacheResult(socialEquityAssetEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SocialEquityAssetEntryModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquityAssetEntryImpl.class, equityAssetEntryId,
						_nullSocialEquityAssetEntry);
				}

				closeSession(session);
			}
		}

		return socialEquityAssetEntry;
	}

	/**
	 * Returns the social equity asset entry where assetEntryId = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquityAssetEntryException} if it could not be found.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching social equity asset entry
	 * @throws com.liferay.portlet.social.NoSuchEquityAssetEntryException if a matching social equity asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityAssetEntry findByAssetEntryId(long assetEntryId)
		throws NoSuchEquityAssetEntryException, SystemException {
		SocialEquityAssetEntry socialEquityAssetEntry = fetchByAssetEntryId(assetEntryId);

		if (socialEquityAssetEntry == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("assetEntryId=");
			msg.append(assetEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEquityAssetEntryException(msg.toString());
		}

		return socialEquityAssetEntry;
	}

	/**
	 * Returns the social equity asset entry where assetEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the matching social equity asset entry, or <code>null</code> if a matching social equity asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityAssetEntry fetchByAssetEntryId(long assetEntryId)
		throws SystemException {
		return fetchByAssetEntryId(assetEntryId, true);
	}

	/**
	 * Returns the social equity asset entry where assetEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetEntryId the asset entry ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching social equity asset entry, or <code>null</code> if a matching social equity asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityAssetEntry fetchByAssetEntryId(long assetEntryId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { assetEntryId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_SOCIALEQUITYASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				List<SocialEquityAssetEntry> list = q.list();

				result = list;

				SocialEquityAssetEntry socialEquityAssetEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
						finderArgs, list);
				}
				else {
					socialEquityAssetEntry = list.get(0);

					cacheResult(socialEquityAssetEntry);

					if ((socialEquityAssetEntry.getAssetEntryId() != assetEntryId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
							finderArgs, socialEquityAssetEntry);
					}
				}

				return socialEquityAssetEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYID,
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
				return (SocialEquityAssetEntry)result;
			}
		}
	}

	/**
	 * Returns all the social equity asset entries.
	 *
	 * @return the social equity asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityAssetEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social equity asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity asset entries
	 * @param end the upper bound of the range of social equity asset entries (not inclusive)
	 * @return the range of social equity asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityAssetEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social equity asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity asset entries
	 * @param end the upper bound of the range of social equity asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social equity asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityAssetEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SocialEquityAssetEntry> list = (List<SocialEquityAssetEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALEQUITYASSETENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALEQUITYASSETENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquityAssetEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquityAssetEntry>)QueryUtil.list(q,
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
	 * Removes the social equity asset entry where assetEntryId = &#63; from the database.
	 *
	 * @param assetEntryId the asset entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByAssetEntryId(long assetEntryId)
		throws NoSuchEquityAssetEntryException, SystemException {
		SocialEquityAssetEntry socialEquityAssetEntry = findByAssetEntryId(assetEntryId);

		socialEquityAssetEntryPersistence.remove(socialEquityAssetEntry);
	}

	/**
	 * Removes all the social equity asset entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialEquityAssetEntry socialEquityAssetEntry : findAll()) {
			socialEquityAssetEntryPersistence.remove(socialEquityAssetEntry);
		}
	}

	/**
	 * Returns the number of social equity asset entries where assetEntryId = &#63;.
	 *
	 * @param assetEntryId the asset entry ID
	 * @return the number of matching social equity asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByAssetEntryId(long assetEntryId) throws SystemException {
		Object[] finderArgs = new Object[] { assetEntryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ASSETENTRYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SOCIALEQUITYASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(assetEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ASSETENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of social equity asset entries.
	 *
	 * @return the number of social equity asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYASSETENTRY);

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
	 * Initializes the social equity asset entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialEquityAssetEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquityAssetEntry>> listenersList = new ArrayList<ModelListener<SocialEquityAssetEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquityAssetEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SocialEquityAssetEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialEquityAssetEntryPersistence.class)
	protected SocialEquityAssetEntryPersistence socialEquityAssetEntryPersistence;
	@BeanReference(type = SocialEquityGroupSettingPersistence.class)
	protected SocialEquityGroupSettingPersistence socialEquityGroupSettingPersistence;
	@BeanReference(type = SocialEquityHistoryPersistence.class)
	protected SocialEquityHistoryPersistence socialEquityHistoryPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = SocialEquitySettingPersistence.class)
	protected SocialEquitySettingPersistence socialEquitySettingPersistence;
	@BeanReference(type = SocialEquityUserPersistence.class)
	protected SocialEquityUserPersistence socialEquityUserPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALEQUITYASSETENTRY = "SELECT socialEquityAssetEntry FROM SocialEquityAssetEntry socialEquityAssetEntry";
	private static final String _SQL_SELECT_SOCIALEQUITYASSETENTRY_WHERE = "SELECT socialEquityAssetEntry FROM SocialEquityAssetEntry socialEquityAssetEntry WHERE ";
	private static final String _SQL_COUNT_SOCIALEQUITYASSETENTRY = "SELECT COUNT(socialEquityAssetEntry) FROM SocialEquityAssetEntry socialEquityAssetEntry";
	private static final String _SQL_COUNT_SOCIALEQUITYASSETENTRY_WHERE = "SELECT COUNT(socialEquityAssetEntry) FROM SocialEquityAssetEntry socialEquityAssetEntry WHERE ";
	private static final String _FINDER_COLUMN_ASSETENTRYID_ASSETENTRYID_2 = "socialEquityAssetEntry.assetEntryId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquityAssetEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquityAssetEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialEquityAssetEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialEquityAssetEntryPersistenceImpl.class);
	private static SocialEquityAssetEntry _nullSocialEquityAssetEntry = new SocialEquityAssetEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialEquityAssetEntry> toCacheModel() {
				return _nullSocialEquityAssetEntryCacheModel;
			}
		};

	private static CacheModel<SocialEquityAssetEntry> _nullSocialEquityAssetEntryCacheModel =
		new CacheModel<SocialEquityAssetEntry>() {
			public SocialEquityAssetEntry toEntityModel() {
				return _nullSocialEquityAssetEntry;
			}
		};
}