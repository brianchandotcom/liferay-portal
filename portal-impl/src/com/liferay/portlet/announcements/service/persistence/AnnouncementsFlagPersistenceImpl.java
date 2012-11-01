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

package com.liferay.portlet.announcements.service.persistence;

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
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchFlagException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the announcements flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlagPersistence
 * @see AnnouncementsFlagUtil
 * @generated
 */
public class AnnouncementsFlagPersistenceImpl extends BasePersistenceImpl<AnnouncementsFlag>
	implements AnnouncementsFlagPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AnnouncementsFlagUtil} to access the announcements flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsFlagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID =
		new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEntryId",
			new String[] { Long.class.getName() },
			AnnouncementsFlagModelImpl.ENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_E_V = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			AnnouncementsFlagModelImpl.USERID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.ENTRYID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.VALUE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_E_V = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the announcements flag in the entity cache if it is enabled.
	 *
	 * @param announcementsFlag the announcements flag
	 */
	public void cacheResult(AnnouncementsFlag announcementsFlag) {
		EntityCacheUtil.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
			new Object[] {
				Long.valueOf(announcementsFlag.getUserId()),
				Long.valueOf(announcementsFlag.getEntryId()),
				Integer.valueOf(announcementsFlag.getValue())
			}, announcementsFlag);

		announcementsFlag.resetOriginalValues();
	}

	/**
	 * Caches the announcements flags in the entity cache if it is enabled.
	 *
	 * @param announcementsFlags the announcements flags
	 */
	public void cacheResult(List<AnnouncementsFlag> announcementsFlags) {
		for (AnnouncementsFlag announcementsFlag : announcementsFlags) {
			if (EntityCacheUtil.getResult(
						AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsFlagImpl.class,
						announcementsFlag.getPrimaryKey()) == null) {
				cacheResult(announcementsFlag);
			}
			else {
				announcementsFlag.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all announcements flags.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AnnouncementsFlagImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AnnouncementsFlagImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the announcements flag.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnnouncementsFlag announcementsFlag) {
		EntityCacheUtil.removeResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(announcementsFlag);
	}

	@Override
	public void clearCache(List<AnnouncementsFlag> announcementsFlags) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AnnouncementsFlag announcementsFlag : announcementsFlags) {
			EntityCacheUtil.removeResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

			clearUniqueFindersCache(announcementsFlag);
		}
	}

	protected void clearUniqueFindersCache(AnnouncementsFlag announcementsFlag) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_E_V,
			new Object[] {
				Long.valueOf(announcementsFlag.getUserId()),
				Long.valueOf(announcementsFlag.getEntryId()),
				Integer.valueOf(announcementsFlag.getValue())
			});
	}

	/**
	 * Creates a new announcements flag with the primary key. Does not add the announcements flag to the database.
	 *
	 * @param flagId the primary key for the new announcements flag
	 * @return the new announcements flag
	 */
	public AnnouncementsFlag create(long flagId) {
		AnnouncementsFlag announcementsFlag = new AnnouncementsFlagImpl();

		announcementsFlag.setNew(true);
		announcementsFlag.setPrimaryKey(flagId);

		return announcementsFlag;
	}

	/**
	 * Removes the announcements flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param flagId the primary key of the announcements flag
	 * @return the announcements flag that was removed
	 * @throws com.liferay.portlet.announcements.NoSuchFlagException if a announcements flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag remove(long flagId)
		throws NoSuchFlagException, SystemException {
		return remove(Long.valueOf(flagId));
	}

	/**
	 * Removes the announcements flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the announcements flag
	 * @return the announcements flag that was removed
	 * @throws com.liferay.portlet.announcements.NoSuchFlagException if a announcements flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsFlag remove(Serializable primaryKey)
		throws NoSuchFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
					primaryKey);

			if (announcementsFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(announcementsFlag);
		}
		catch (NoSuchFlagException nsee) {
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
	protected AnnouncementsFlag removeImpl(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		announcementsFlag = toUnwrappedModel(announcementsFlag);

		Session session = null;

		try {
			session = openSession();

			if (announcementsFlag.isCachedModel()) {
				announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
						announcementsFlag.getPrimaryKeyObj());
			}

			if (announcementsFlag != null) {
				session.delete(announcementsFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (announcementsFlag != null) {
			clearCache(announcementsFlag);
		}

		return announcementsFlag;
	}

	@Override
	public AnnouncementsFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws SystemException {
		announcementsFlag = toUnwrappedModel(announcementsFlag);

		boolean isNew = announcementsFlag.isNew();

		AnnouncementsFlagModelImpl announcementsFlagModelImpl = (AnnouncementsFlagModelImpl)announcementsFlag;

		Session session = null;

		try {
			session = openSession();

			if (announcementsFlag.isNew()) {
				session.save(announcementsFlag);

				announcementsFlag.setNew(false);
			}
			else {
				session.merge(announcementsFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AnnouncementsFlagModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((announcementsFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(announcementsFlagModelImpl.getOriginalEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
					args);

				args = new Object[] {
						Long.valueOf(announcementsFlagModelImpl.getEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
					args);
			}
		}

		EntityCacheUtil.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
				new Object[] {
					Long.valueOf(announcementsFlag.getUserId()),
					Long.valueOf(announcementsFlag.getEntryId()),
					Integer.valueOf(announcementsFlag.getValue())
				}, announcementsFlag);
		}
		else {
			if ((announcementsFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_E_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(announcementsFlagModelImpl.getOriginalUserId()),
						Long.valueOf(announcementsFlagModelImpl.getOriginalEntryId()),
						Integer.valueOf(announcementsFlagModelImpl.getOriginalValue())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_E_V, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_E_V, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
					new Object[] {
						Long.valueOf(announcementsFlag.getUserId()),
						Long.valueOf(announcementsFlag.getEntryId()),
						Integer.valueOf(announcementsFlag.getValue())
					}, announcementsFlag);
			}
		}

		return announcementsFlag;
	}

	protected AnnouncementsFlag toUnwrappedModel(
		AnnouncementsFlag announcementsFlag) {
		if (announcementsFlag instanceof AnnouncementsFlagImpl) {
			return announcementsFlag;
		}

		AnnouncementsFlagImpl announcementsFlagImpl = new AnnouncementsFlagImpl();

		announcementsFlagImpl.setNew(announcementsFlag.isNew());
		announcementsFlagImpl.setPrimaryKey(announcementsFlag.getPrimaryKey());

		announcementsFlagImpl.setFlagId(announcementsFlag.getFlagId());
		announcementsFlagImpl.setUserId(announcementsFlag.getUserId());
		announcementsFlagImpl.setCreateDate(announcementsFlag.getCreateDate());
		announcementsFlagImpl.setEntryId(announcementsFlag.getEntryId());
		announcementsFlagImpl.setValue(announcementsFlag.getValue());

		return announcementsFlagImpl;
	}

	/**
	 * Returns the announcements flag with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements flag
	 * @return the announcements flag
	 * @throws com.liferay.portal.NoSuchModelException if a announcements flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsFlag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the announcements flag with the primary key or throws a {@link com.liferay.portlet.announcements.NoSuchFlagException} if it could not be found.
	 *
	 * @param flagId the primary key of the announcements flag
	 * @return the announcements flag
	 * @throws com.liferay.portlet.announcements.NoSuchFlagException if a announcements flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag findByPrimaryKey(long flagId)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByPrimaryKey(flagId);

		if (announcementsFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + flagId);
			}

			throw new NoSuchFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				flagId);
		}

		return announcementsFlag;
	}

	/**
	 * Returns the announcements flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements flag
	 * @return the announcements flag, or <code>null</code> if a announcements flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsFlag fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the announcements flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param flagId the primary key of the announcements flag
	 * @return the announcements flag, or <code>null</code> if a announcements flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag fetchByPrimaryKey(long flagId)
		throws SystemException {
		AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)EntityCacheUtil.getResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsFlagImpl.class, flagId);

		if (announcementsFlag == _nullAnnouncementsFlag) {
			return null;
		}

		if (announcementsFlag == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
						Long.valueOf(flagId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (announcementsFlag != null) {
					cacheResult(announcementsFlag);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsFlagImpl.class, flagId,
						_nullAnnouncementsFlag);
				}

				closeSession(session);
			}
		}

		return announcementsFlag;
	}

	/**
	 * Returns all the announcements flags where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the matching announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsFlag> findByEntryId(long entryId)
		throws SystemException {
		return findByEntryId(entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements flags where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @return the range of matching announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end) throws SystemException {
		return findByEntryId(entryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements flags where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID;
			finderArgs = new Object[] { entryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID;
			finderArgs = new Object[] { entryId, start, end, orderByComparator };
		}

		List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AnnouncementsFlag announcementsFlag : list) {
				if ((entryId != announcementsFlag.getEntryId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				list = (List<AnnouncementsFlag>)QueryUtil.list(q, getDialect(),
						start, end);
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
	 * Returns the first announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements flag
	 * @throws com.liferay.portlet.announcements.NoSuchFlagException if a matching announcements flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag findByEntryId_First(long entryId,
		OrderByComparator orderByComparator)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByEntryId_First(entryId,
				orderByComparator);

		if (announcementsFlag != null) {
			return announcementsFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFlagException(msg.toString());
	}

	/**
	 * Returns the first announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag fetchByEntryId_First(long entryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AnnouncementsFlag> list = findByEntryId(entryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements flag
	 * @throws com.liferay.portlet.announcements.NoSuchFlagException if a matching announcements flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag findByEntryId_Last(long entryId,
		OrderByComparator orderByComparator)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByEntryId_Last(entryId,
				orderByComparator);

		if (announcementsFlag != null) {
			return announcementsFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFlagException(msg.toString());
	}

	/**
	 * Returns the last announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag fetchByEntryId_Last(long entryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByEntryId(entryId);

		List<AnnouncementsFlag> list = findByEntryId(entryId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements flags before and after the current announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param flagId the primary key of the current announcements flag
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements flag
	 * @throws com.liferay.portlet.announcements.NoSuchFlagException if a announcements flag with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag[] findByEntryId_PrevAndNext(long flagId,
		long entryId, OrderByComparator orderByComparator)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = findByPrimaryKey(flagId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag[] array = new AnnouncementsFlagImpl[3];

			array[0] = getByEntryId_PrevAndNext(session, announcementsFlag,
					entryId, orderByComparator, true);

			array[1] = announcementsFlag;

			array[2] = getByEntryId_PrevAndNext(session, announcementsFlag,
					entryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsFlag getByEntryId_PrevAndNext(Session session,
		AnnouncementsFlag announcementsFlag, long entryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or throws a {@link com.liferay.portlet.announcements.NoSuchFlagException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @return the matching announcements flag
	 * @throws com.liferay.portlet.announcements.NoSuchFlagException if a matching announcements flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag findByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByU_E_V(userId, entryId,
				value);

		if (announcementsFlag == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", entryId=");
			msg.append(entryId);

			msg.append(", value=");
			msg.append(value);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFlagException(msg.toString());
		}

		return announcementsFlag;
	}

	/**
	 * Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value)
		throws SystemException {
		return fetchByU_E_V(userId, entryId, value, true);
	}

	/**
	 * Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, entryId, value };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_E_V,
					finderArgs, this);
		}

		if (result instanceof AnnouncementsFlag) {
			AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)result;

			if ((userId != announcementsFlag.getUserId()) ||
					(entryId != announcementsFlag.getEntryId()) ||
					(value != announcementsFlag.getValue())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_E_V_USERID_2);

			query.append(_FINDER_COLUMN_U_E_V_ENTRYID_2);

			query.append(_FINDER_COLUMN_U_E_V_VALUE_2);

			query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

				List<AnnouncementsFlag> list = q.list();

				result = list;

				AnnouncementsFlag announcementsFlag = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
						finderArgs, list);
				}
				else {
					announcementsFlag = list.get(0);

					cacheResult(announcementsFlag);

					if ((announcementsFlag.getUserId() != userId) ||
							(announcementsFlag.getEntryId() != entryId) ||
							(announcementsFlag.getValue() != value)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
							finderArgs, announcementsFlag);
					}
				}

				return announcementsFlag;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_E_V,
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
				return (AnnouncementsFlag)result;
			}
		}
	}

	/**
	 * Returns all the announcements flags.
	 *
	 * @return the announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @return the range of announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsFlag> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsFlag> findAll(int start, int end,
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

		List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ANNOUNCEMENTSFLAG.concat(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
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
	 * Removes all the announcements flags where entryId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByEntryId(long entryId) throws SystemException {
		for (AnnouncementsFlag announcementsFlag : findByEntryId(entryId)) {
			remove(announcementsFlag);
		}
	}

	/**
	 * Removes the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @return the announcements flag that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsFlag removeByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = findByU_E_V(userId, entryId, value);

		return remove(announcementsFlag);
	}

	/**
	 * Removes all the announcements flags from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AnnouncementsFlag announcementsFlag : findAll()) {
			remove(announcementsFlag);
		}
	}

	/**
	 * Returns the number of announcements flags where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the number of matching announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByEntryId(long entryId) throws SystemException {
		Object[] finderArgs = new Object[] { entryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ENTRYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of announcements flags where userId = &#63; and entryId = &#63; and value = &#63;.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @return the number of matching announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_E_V(long userId, long entryId, int value)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, entryId, value };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_E_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_E_V_USERID_2);

			query.append(_FINDER_COLUMN_U_E_V_ENTRYID_2);

			query.append(_FINDER_COLUMN_U_E_V_VALUE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_E_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of announcements flags.
	 *
	 * @return the number of announcements flags
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSFLAG);

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
	 * Initializes the announcements flag persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsFlag")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsFlag>> listenersList = new ArrayList<ModelListener<AnnouncementsFlag>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsFlag>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AnnouncementsFlagImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AnnouncementsDeliveryPersistence.class)
	protected AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(type = AnnouncementsEntryPersistence.class)
	protected AnnouncementsEntryPersistence announcementsEntryPersistence;
	@BeanReference(type = AnnouncementsFlagPersistence.class)
	protected AnnouncementsFlagPersistence announcementsFlagPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG = "SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag";
	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE = "SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE ";
	private static final String _SQL_COUNT_ANNOUNCEMENTSFLAG = "SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag";
	private static final String _SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE = "SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag WHERE ";
	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 = "announcementsFlag.entryId = ?";
	private static final String _FINDER_COLUMN_U_E_V_USERID_2 = "announcementsFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_E_V_ENTRYID_2 = "announcementsFlag.entryId = ? AND ";
	private static final String _FINDER_COLUMN_U_E_V_VALUE_2 = "announcementsFlag.value = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsFlag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AnnouncementsFlag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AnnouncementsFlag exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsFlagPersistenceImpl.class);
	private static AnnouncementsFlag _nullAnnouncementsFlag = new AnnouncementsFlagImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AnnouncementsFlag> toCacheModel() {
				return _nullAnnouncementsFlagCacheModel;
			}
		};

	private static CacheModel<AnnouncementsFlag> _nullAnnouncementsFlagCacheModel =
		new CacheModel<AnnouncementsFlag>() {
			public AnnouncementsFlag toEntityModel() {
				return _nullAnnouncementsFlag;
			}
		};
}