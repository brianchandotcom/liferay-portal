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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchDeliveryException;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the announcements delivery service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryPersistence
 * @see AnnouncementsDeliveryUtil
 * @generated
 */
public class AnnouncementsDeliveryPersistenceImpl extends BasePersistenceImpl<AnnouncementsDelivery>
	implements AnnouncementsDeliveryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AnnouncementsDeliveryUtil} to access the announcements delivery persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsDeliveryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			AnnouncementsDeliveryModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_T = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_T",
			new String[] { Long.class.getName(), String.class.getName() },
			AnnouncementsDeliveryModelImpl.USERID_COLUMN_BITMASK |
			AnnouncementsDeliveryModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the announcements delivery in the entity cache if it is enabled.
	 *
	 * @param announcementsDelivery the announcements delivery
	 */
	public void cacheResult(AnnouncementsDelivery announcementsDelivery) {
		EntityCacheUtil.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey(), announcementsDelivery);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				Long.valueOf(announcementsDelivery.getUserId()),
				
			announcementsDelivery.getType()
			}, announcementsDelivery);

		announcementsDelivery.resetOriginalValues();
	}

	/**
	 * Caches the announcements deliveries in the entity cache if it is enabled.
	 *
	 * @param announcementsDeliveries the announcements deliveries
	 */
	public void cacheResult(List<AnnouncementsDelivery> announcementsDeliveries) {
		for (AnnouncementsDelivery announcementsDelivery : announcementsDeliveries) {
			if (EntityCacheUtil.getResult(
						AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKey()) == null) {
				cacheResult(announcementsDelivery);
			}
			else {
				announcementsDelivery.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all announcements deliveries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AnnouncementsDeliveryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AnnouncementsDeliveryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the announcements delivery.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnnouncementsDelivery announcementsDelivery) {
		EntityCacheUtil.removeResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(announcementsDelivery);
	}

	@Override
	public void clearCache(List<AnnouncementsDelivery> announcementsDeliveries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AnnouncementsDelivery announcementsDelivery : announcementsDeliveries) {
			EntityCacheUtil.removeResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsDeliveryImpl.class,
				announcementsDelivery.getPrimaryKey());

			clearUniqueFindersCache(announcementsDelivery);
		}
	}

	protected void clearUniqueFindersCache(
		AnnouncementsDelivery announcementsDelivery) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				Long.valueOf(announcementsDelivery.getUserId()),
				
			announcementsDelivery.getType()
			});
	}

	/**
	 * Creates a new announcements delivery with the primary key. Does not add the announcements delivery to the database.
	 *
	 * @param deliveryId the primary key for the new announcements delivery
	 * @return the new announcements delivery
	 */
	public AnnouncementsDelivery create(long deliveryId) {
		AnnouncementsDelivery announcementsDelivery = new AnnouncementsDeliveryImpl();

		announcementsDelivery.setNew(true);
		announcementsDelivery.setPrimaryKey(deliveryId);

		return announcementsDelivery;
	}

	/**
	 * Removes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery that was removed
	 * @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery remove(long deliveryId)
		throws NoSuchDeliveryException, SystemException {
		return remove(Long.valueOf(deliveryId));
	}

	/**
	 * Removes the announcements delivery with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the announcements delivery
	 * @return the announcements delivery that was removed
	 * @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsDelivery remove(Serializable primaryKey)
		throws NoSuchDeliveryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
					primaryKey);

			if (announcementsDelivery == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDeliveryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(announcementsDelivery);
		}
		catch (NoSuchDeliveryException nsee) {
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
	protected AnnouncementsDelivery removeImpl(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		announcementsDelivery = toUnwrappedModel(announcementsDelivery);

		Session session = null;

		try {
			session = openSession();

			if (announcementsDelivery.isCachedModel()) {
				announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKeyObj());
			}

			if (announcementsDelivery != null) {
				session.delete(announcementsDelivery);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (announcementsDelivery != null) {
			clearCache(announcementsDelivery);
		}

		return announcementsDelivery;
	}

	@Override
	public AnnouncementsDelivery updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws SystemException {
		announcementsDelivery = toUnwrappedModel(announcementsDelivery);

		boolean isNew = announcementsDelivery.isNew();

		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl = (AnnouncementsDeliveryModelImpl)announcementsDelivery;

		Session session = null;

		try {
			session = openSession();

			if (announcementsDelivery.isNew()) {
				session.save(announcementsDelivery);

				announcementsDelivery.setNew(false);
			}
			else {
				session.merge(announcementsDelivery);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AnnouncementsDeliveryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((announcementsDeliveryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(announcementsDeliveryModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(announcementsDeliveryModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		EntityCacheUtil.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey(), announcementsDelivery);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					Long.valueOf(announcementsDelivery.getUserId()),
					
				announcementsDelivery.getType()
				}, announcementsDelivery);
		}
		else {
			if ((announcementsDeliveryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(announcementsDeliveryModelImpl.getOriginalUserId()),
						
						announcementsDeliveryModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_T, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
					new Object[] {
						Long.valueOf(announcementsDelivery.getUserId()),
						
					announcementsDelivery.getType()
					}, announcementsDelivery);
			}
		}

		return announcementsDelivery;
	}

	protected AnnouncementsDelivery toUnwrappedModel(
		AnnouncementsDelivery announcementsDelivery) {
		if (announcementsDelivery instanceof AnnouncementsDeliveryImpl) {
			return announcementsDelivery;
		}

		AnnouncementsDeliveryImpl announcementsDeliveryImpl = new AnnouncementsDeliveryImpl();

		announcementsDeliveryImpl.setNew(announcementsDelivery.isNew());
		announcementsDeliveryImpl.setPrimaryKey(announcementsDelivery.getPrimaryKey());

		announcementsDeliveryImpl.setDeliveryId(announcementsDelivery.getDeliveryId());
		announcementsDeliveryImpl.setCompanyId(announcementsDelivery.getCompanyId());
		announcementsDeliveryImpl.setUserId(announcementsDelivery.getUserId());
		announcementsDeliveryImpl.setType(announcementsDelivery.getType());
		announcementsDeliveryImpl.setEmail(announcementsDelivery.isEmail());
		announcementsDeliveryImpl.setSms(announcementsDelivery.isSms());
		announcementsDeliveryImpl.setWebsite(announcementsDelivery.isWebsite());

		return announcementsDeliveryImpl;
	}

	/**
	 * Returns the announcements delivery with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements delivery
	 * @return the announcements delivery
	 * @throws com.liferay.portal.NoSuchModelException if a announcements delivery with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsDelivery findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the announcements delivery with the primary key or throws a {@link com.liferay.portlet.announcements.NoSuchDeliveryException} if it could not be found.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery
	 * @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery findByPrimaryKey(long deliveryId)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByPrimaryKey(deliveryId);

		if (announcementsDelivery == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + deliveryId);
			}

			throw new NoSuchDeliveryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				deliveryId);
		}

		return announcementsDelivery;
	}

	/**
	 * Returns the announcements delivery with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements delivery
	 * @return the announcements delivery, or <code>null</code> if a announcements delivery with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AnnouncementsDelivery fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the announcements delivery with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deliveryId the primary key of the announcements delivery
	 * @return the announcements delivery, or <code>null</code> if a announcements delivery with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery fetchByPrimaryKey(long deliveryId)
		throws SystemException {
		AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)EntityCacheUtil.getResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsDeliveryImpl.class, deliveryId);

		if (announcementsDelivery == _nullAnnouncementsDelivery) {
			return null;
		}

		if (announcementsDelivery == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
						Long.valueOf(deliveryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (announcementsDelivery != null) {
					cacheResult(announcementsDelivery);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsDeliveryImpl.class, deliveryId,
						_nullAnnouncementsDelivery);
				}

				closeSession(session);
			}
		}

		return announcementsDelivery;
	}

	/**
	 * Returns all the announcements deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsDelivery> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @return the range of matching announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end) throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AnnouncementsDelivery announcementsDelivery : list) {
				if ((userId != announcementsDelivery.getUserId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements delivery
	 * @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a matching announcements delivery could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByUserId_First(userId,
				orderByComparator);

		if (announcementsDelivery != null) {
			return announcementsDelivery;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDeliveryException(msg.toString());
	}

	/**
	 * Returns the first announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AnnouncementsDelivery> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements delivery
	 * @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a matching announcements delivery could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByUserId_Last(userId,
				orderByComparator);

		if (announcementsDelivery != null) {
			return announcementsDelivery;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDeliveryException(msg.toString());
	}

	/**
	 * Returns the last announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<AnnouncementsDelivery> list = findByUserId(userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements deliveries before and after the current announcements delivery in the ordered set where userId = &#63;.
	 *
	 * @param deliveryId the primary key of the current announcements delivery
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements delivery
	 * @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a announcements delivery with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery[] findByUserId_PrevAndNext(long deliveryId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = findByPrimaryKey(deliveryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsDelivery[] array = new AnnouncementsDeliveryImpl[3];

			array[0] = getByUserId_PrevAndNext(session, announcementsDelivery,
					userId, orderByComparator, true);

			array[1] = announcementsDelivery;

			array[2] = getByUserId_PrevAndNext(session, announcementsDelivery,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsDelivery getByUserId_PrevAndNext(Session session,
		AnnouncementsDelivery announcementsDelivery, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsDelivery);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsDelivery> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or throws a {@link com.liferay.portlet.announcements.NoSuchDeliveryException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching announcements delivery
	 * @throws com.liferay.portlet.announcements.NoSuchDeliveryException if a matching announcements delivery could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery findByU_T(long userId, String type)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByU_T(userId, type);

		if (announcementsDelivery == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDeliveryException(msg.toString());
		}

		return announcementsDelivery;
	}

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery fetchByU_T(long userId, String type)
		throws SystemException {
		return fetchByU_T(userId, type, true);
	}

	/**
	 * Returns the announcements delivery where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching announcements delivery, or <code>null</code> if a matching announcements delivery could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery fetchByU_T(long userId, String type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_T,
					finderArgs, this);
		}

		if (result instanceof AnnouncementsDelivery) {
			AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)result;

			if ((userId != announcementsDelivery.getUserId()) ||
					!Validator.equals(type, announcementsDelivery.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_U_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_T_TYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				List<AnnouncementsDelivery> list = q.list();

				result = list;

				AnnouncementsDelivery announcementsDelivery = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, list);
				}
				else {
					announcementsDelivery = list.get(0);

					cacheResult(announcementsDelivery);

					if ((announcementsDelivery.getUserId() != userId) ||
							(announcementsDelivery.getType() == null) ||
							!announcementsDelivery.getType().equals(type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
							finderArgs, announcementsDelivery);
					}
				}

				return announcementsDelivery;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
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
				return (AnnouncementsDelivery)result;
			}
		}
	}

	/**
	 * Returns all the announcements deliveries.
	 *
	 * @return the announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsDelivery> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @return the range of announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsDelivery> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements deliveries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements deliveries
	 * @param end the upper bound of the range of announcements deliveries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AnnouncementsDelivery> findAll(int start, int end,
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

		List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ANNOUNCEMENTSDELIVERY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
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
	 * Removes all the announcements deliveries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (AnnouncementsDelivery announcementsDelivery : findByUserId(userId)) {
			remove(announcementsDelivery);
		}
	}

	/**
	 * Removes the announcements delivery where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the announcements delivery that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AnnouncementsDelivery removeByU_T(long userId, String type)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = findByU_T(userId, type);

		return remove(announcementsDelivery);
	}

	/**
	 * Removes all the announcements deliveries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AnnouncementsDelivery announcementsDelivery : findAll()) {
			remove(announcementsDelivery);
		}
	}

	/**
	 * Returns the number of announcements deliveries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of announcements deliveries where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_T(long userId, String type) throws SystemException {
		Object[] finderArgs = new Object[] { userId, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_U_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_T_TYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of announcements deliveries.
	 *
	 * @return the number of announcements deliveries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSDELIVERY);

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
	 * Initializes the announcements delivery persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsDelivery")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsDelivery>> listenersList = new ArrayList<ModelListener<AnnouncementsDelivery>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsDelivery>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(AnnouncementsDeliveryImpl.class.getName());
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
	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY = "SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery";
	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE = "SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE ";
	private static final String _SQL_COUNT_ANNOUNCEMENTSDELIVERY = "SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery";
	private static final String _SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE = "SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "announcementsDelivery.userId = ?";
	private static final String _FINDER_COLUMN_U_T_USERID_2 = "announcementsDelivery.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_TYPE_1 = "announcementsDelivery.type IS NULL";
	private static final String _FINDER_COLUMN_U_T_TYPE_2 = "announcementsDelivery.type = ?";
	private static final String _FINDER_COLUMN_U_T_TYPE_3 = "(announcementsDelivery.type IS NULL OR announcementsDelivery.type = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsDelivery.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AnnouncementsDelivery exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AnnouncementsDelivery exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsDeliveryPersistenceImpl.class);
	private static AnnouncementsDelivery _nullAnnouncementsDelivery = new AnnouncementsDeliveryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AnnouncementsDelivery> toCacheModel() {
				return _nullAnnouncementsDeliveryCacheModel;
			}
		};

	private static CacheModel<AnnouncementsDelivery> _nullAnnouncementsDeliveryCacheModel =
		new CacheModel<AnnouncementsDelivery>() {
			public AnnouncementsDelivery toEntityModel() {
				return _nullAnnouncementsDelivery;
			}
		};
}