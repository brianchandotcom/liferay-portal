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
import com.liferay.portal.NoSuchSubscriptionException;
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
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.impl.SubscriptionImpl;
import com.liferay.portal.model.impl.SubscriptionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the subscription service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SubscriptionPersistence
 * @see SubscriptionUtil
 * @generated
 */
public class SubscriptionPersistenceImpl extends BasePersistenceImpl<Subscription>
	implements SubscriptionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SubscriptionUtil} to access the subscription persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SubscriptionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			SubscriptionModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			SubscriptionModelImpl.USERID_COLUMN_BITMASK |
			SubscriptionModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_C = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_C = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SubscriptionModelImpl.COMPANYID_COLUMN_BITMASK |
			SubscriptionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SubscriptionModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_U_C_C = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_U_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			SubscriptionModelImpl.COMPANYID_COLUMN_BITMASK |
			SubscriptionModelImpl.USERID_COLUMN_BITMASK |
			SubscriptionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SubscriptionModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U_C_C = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, SubscriptionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the subscription in the entity cache if it is enabled.
	 *
	 * @param subscription the subscription
	 */
	public void cacheResult(Subscription subscription) {
		EntityCacheUtil.putResult(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionImpl.class, subscription.getPrimaryKey(), subscription);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_C_C,
			new Object[] {
				Long.valueOf(subscription.getCompanyId()),
				Long.valueOf(subscription.getUserId()),
				Long.valueOf(subscription.getClassNameId()),
				Long.valueOf(subscription.getClassPK())
			}, subscription);

		subscription.resetOriginalValues();
	}

	/**
	 * Caches the subscriptions in the entity cache if it is enabled.
	 *
	 * @param subscriptions the subscriptions
	 */
	public void cacheResult(List<Subscription> subscriptions) {
		for (Subscription subscription : subscriptions) {
			if (EntityCacheUtil.getResult(
						SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
						SubscriptionImpl.class, subscription.getPrimaryKey()) == null) {
				cacheResult(subscription);
			}
			else {
				subscription.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all subscriptions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SubscriptionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SubscriptionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the subscription.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Subscription subscription) {
		EntityCacheUtil.removeResult(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionImpl.class, subscription.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(subscription);
	}

	@Override
	public void clearCache(List<Subscription> subscriptions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Subscription subscription : subscriptions) {
			EntityCacheUtil.removeResult(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
				SubscriptionImpl.class, subscription.getPrimaryKey());

			clearUniqueFindersCache(subscription);
		}
	}

	protected void clearUniqueFindersCache(Subscription subscription) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_C_C,
			new Object[] {
				Long.valueOf(subscription.getCompanyId()),
				Long.valueOf(subscription.getUserId()),
				Long.valueOf(subscription.getClassNameId()),
				Long.valueOf(subscription.getClassPK())
			});
	}

	/**
	 * Creates a new subscription with the primary key. Does not add the subscription to the database.
	 *
	 * @param subscriptionId the primary key for the new subscription
	 * @return the new subscription
	 */
	public Subscription create(long subscriptionId) {
		Subscription subscription = new SubscriptionImpl();

		subscription.setNew(true);
		subscription.setPrimaryKey(subscriptionId);

		return subscription;
	}

	/**
	 * Removes the subscription with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param subscriptionId the primary key of the subscription
	 * @return the subscription that was removed
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription remove(long subscriptionId)
		throws NoSuchSubscriptionException, SystemException {
		return remove(Long.valueOf(subscriptionId));
	}

	/**
	 * Removes the subscription with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the subscription
	 * @return the subscription that was removed
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Subscription remove(Serializable primaryKey)
		throws NoSuchSubscriptionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Subscription subscription = (Subscription)session.get(SubscriptionImpl.class,
					primaryKey);

			if (subscription == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSubscriptionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(subscription);
		}
		catch (NoSuchSubscriptionException nsee) {
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
	protected Subscription removeImpl(Subscription subscription)
		throws SystemException {
		subscription = toUnwrappedModel(subscription);

		Session session = null;

		try {
			session = openSession();

			if (subscription.isCachedModel()) {
				subscription = (Subscription)session.get(SubscriptionImpl.class,
						subscription.getPrimaryKeyObj());
			}

			if (subscription != null) {
				session.delete(subscription);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (subscription != null) {
			clearCache(subscription);
		}

		return subscription;
	}

	@Override
	public Subscription updateImpl(
		com.liferay.portal.model.Subscription subscription)
		throws SystemException {
		subscription = toUnwrappedModel(subscription);

		boolean isNew = subscription.isNew();

		SubscriptionModelImpl subscriptionModelImpl = (SubscriptionModelImpl)subscription;

		Session session = null;

		try {
			session = openSession();

			if (subscription.isNew()) {
				session.save(subscription);

				subscription.setNew(false);
			}
			else {
				session.merge(subscription);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SubscriptionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((subscriptionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(subscriptionModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(subscriptionModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((subscriptionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(subscriptionModelImpl.getOriginalUserId()),
						Long.valueOf(subscriptionModelImpl.getOriginalClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C,
					args);

				args = new Object[] {
						Long.valueOf(subscriptionModelImpl.getUserId()),
						Long.valueOf(subscriptionModelImpl.getClassNameId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C,
					args);
			}

			if ((subscriptionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(subscriptionModelImpl.getOriginalCompanyId()),
						Long.valueOf(subscriptionModelImpl.getOriginalClassNameId()),
						Long.valueOf(subscriptionModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C,
					args);

				args = new Object[] {
						Long.valueOf(subscriptionModelImpl.getCompanyId()),
						Long.valueOf(subscriptionModelImpl.getClassNameId()),
						Long.valueOf(subscriptionModelImpl.getClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C,
					args);
			}
		}

		EntityCacheUtil.putResult(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
			SubscriptionImpl.class, subscription.getPrimaryKey(), subscription);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_C_C,
				new Object[] {
					Long.valueOf(subscription.getCompanyId()),
					Long.valueOf(subscription.getUserId()),
					Long.valueOf(subscription.getClassNameId()),
					Long.valueOf(subscription.getClassPK())
				}, subscription);
		}
		else {
			if ((subscriptionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_U_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(subscriptionModelImpl.getOriginalCompanyId()),
						Long.valueOf(subscriptionModelImpl.getOriginalUserId()),
						Long.valueOf(subscriptionModelImpl.getOriginalClassNameId()),
						Long.valueOf(subscriptionModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_U_C_C, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_C_C, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_C_C,
					new Object[] {
						Long.valueOf(subscription.getCompanyId()),
						Long.valueOf(subscription.getUserId()),
						Long.valueOf(subscription.getClassNameId()),
						Long.valueOf(subscription.getClassPK())
					}, subscription);
			}
		}

		return subscription;
	}

	protected Subscription toUnwrappedModel(Subscription subscription) {
		if (subscription instanceof SubscriptionImpl) {
			return subscription;
		}

		SubscriptionImpl subscriptionImpl = new SubscriptionImpl();

		subscriptionImpl.setNew(subscription.isNew());
		subscriptionImpl.setPrimaryKey(subscription.getPrimaryKey());

		subscriptionImpl.setSubscriptionId(subscription.getSubscriptionId());
		subscriptionImpl.setCompanyId(subscription.getCompanyId());
		subscriptionImpl.setUserId(subscription.getUserId());
		subscriptionImpl.setUserName(subscription.getUserName());
		subscriptionImpl.setCreateDate(subscription.getCreateDate());
		subscriptionImpl.setModifiedDate(subscription.getModifiedDate());
		subscriptionImpl.setClassNameId(subscription.getClassNameId());
		subscriptionImpl.setClassPK(subscription.getClassPK());
		subscriptionImpl.setFrequency(subscription.getFrequency());

		return subscriptionImpl;
	}

	/**
	 * Returns the subscription with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the subscription
	 * @return the subscription
	 * @throws com.liferay.portal.NoSuchModelException if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Subscription findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the subscription with the primary key or throws a {@link com.liferay.portal.NoSuchSubscriptionException} if it could not be found.
	 *
	 * @param subscriptionId the primary key of the subscription
	 * @return the subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription findByPrimaryKey(long subscriptionId)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = fetchByPrimaryKey(subscriptionId);

		if (subscription == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + subscriptionId);
			}

			throw new NoSuchSubscriptionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				subscriptionId);
		}

		return subscription;
	}

	/**
	 * Returns the subscription with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the subscription
	 * @return the subscription, or <code>null</code> if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Subscription fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the subscription with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param subscriptionId the primary key of the subscription
	 * @return the subscription, or <code>null</code> if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByPrimaryKey(long subscriptionId)
		throws SystemException {
		Subscription subscription = (Subscription)EntityCacheUtil.getResult(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
				SubscriptionImpl.class, subscriptionId);

		if (subscription == _nullSubscription) {
			return null;
		}

		if (subscription == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				subscription = (Subscription)session.get(SubscriptionImpl.class,
						Long.valueOf(subscriptionId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (subscription != null) {
					cacheResult(subscription);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(SubscriptionModelImpl.ENTITY_CACHE_ENABLED,
						SubscriptionImpl.class, subscriptionId,
						_nullSubscription);
				}

				closeSession(session);
			}
		}

		return subscription;
	}

	/**
	 * Returns all the subscriptions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the subscriptions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of subscriptions
	 * @param end the upper bound of the range of subscriptions (not inclusive)
	 * @return the range of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the subscriptions where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of subscriptions
	 * @param end the upper bound of the range of subscriptions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<Subscription> list = (List<Subscription>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Subscription subscription : list) {
				if ((userId != subscription.getUserId())) {
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

			query.append(_SQL_SELECT_SUBSCRIPTION_WHERE);

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

				list = (List<Subscription>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first subscription in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = fetchByUserId_First(userId,
				orderByComparator);

		if (subscription != null) {
			return subscription;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSubscriptionException(msg.toString());
	}

	/**
	 * Returns the first subscription in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching subscription, or <code>null</code> if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Subscription> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last subscription in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = fetchByUserId_Last(userId, orderByComparator);

		if (subscription != null) {
			return subscription;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSubscriptionException(msg.toString());
	}

	/**
	 * Returns the last subscription in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching subscription, or <code>null</code> if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<Subscription> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the subscriptions before and after the current subscription in the ordered set where userId = &#63;.
	 *
	 * @param subscriptionId the primary key of the current subscription
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription[] findByUserId_PrevAndNext(long subscriptionId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = findByPrimaryKey(subscriptionId);

		Session session = null;

		try {
			session = openSession();

			Subscription[] array = new SubscriptionImpl[3];

			array[0] = getByUserId_PrevAndNext(session, subscription, userId,
					orderByComparator, true);

			array[1] = subscription;

			array[2] = getByUserId_PrevAndNext(session, subscription, userId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Subscription getByUserId_PrevAndNext(Session session,
		Subscription subscription, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SUBSCRIPTION_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(subscription);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Subscription> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the subscriptions where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByU_C(long userId, long classNameId)
		throws SystemException {
		return findByU_C(userId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the subscriptions where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of subscriptions
	 * @param end the upper bound of the range of subscriptions (not inclusive)
	 * @return the range of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByU_C(long userId, long classNameId,
		int start, int end) throws SystemException {
		return findByU_C(userId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the subscriptions where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of subscriptions
	 * @param end the upper bound of the range of subscriptions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByU_C(long userId, long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_C;
			finderArgs = new Object[] { userId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_C;
			finderArgs = new Object[] {
					userId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<Subscription> list = (List<Subscription>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Subscription subscription : list) {
				if ((userId != subscription.getUserId()) ||
						(classNameId != subscription.getClassNameId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SUBSCRIPTION_WHERE);

			query.append(_FINDER_COLUMN_U_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

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

				qPos.add(classNameId);

				list = (List<Subscription>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first subscription in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription findByU_C_First(long userId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = fetchByU_C_First(userId, classNameId,
				orderByComparator);

		if (subscription != null) {
			return subscription;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSubscriptionException(msg.toString());
	}

	/**
	 * Returns the first subscription in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching subscription, or <code>null</code> if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByU_C_First(long userId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Subscription> list = findByU_C(userId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last subscription in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription findByU_C_Last(long userId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = fetchByU_C_Last(userId, classNameId,
				orderByComparator);

		if (subscription != null) {
			return subscription;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSubscriptionException(msg.toString());
	}

	/**
	 * Returns the last subscription in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching subscription, or <code>null</code> if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByU_C_Last(long userId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU_C(userId, classNameId);

		List<Subscription> list = findByU_C(userId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the subscriptions before and after the current subscription in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param subscriptionId the primary key of the current subscription
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription[] findByU_C_PrevAndNext(long subscriptionId,
		long userId, long classNameId, OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = findByPrimaryKey(subscriptionId);

		Session session = null;

		try {
			session = openSession();

			Subscription[] array = new SubscriptionImpl[3];

			array[0] = getByU_C_PrevAndNext(session, subscription, userId,
					classNameId, orderByComparator, true);

			array[1] = subscription;

			array[2] = getByU_C_PrevAndNext(session, subscription, userId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Subscription getByU_C_PrevAndNext(Session session,
		Subscription subscription, long userId, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SUBSCRIPTION_WHERE);

		query.append(_FINDER_COLUMN_U_C_USERID_2);

		query.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

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

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(subscription);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Subscription> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByC_C_C(long companyId, long classNameId,
		long classPK) throws SystemException {
		return findByC_C_C(companyId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of subscriptions
	 * @param end the upper bound of the range of subscriptions (not inclusive)
	 * @return the range of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of subscriptions
	 * @param end the upper bound of the range of subscriptions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C;
			finderArgs = new Object[] { companyId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_C;
			finderArgs = new Object[] {
					companyId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<Subscription> list = (List<Subscription>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Subscription subscription : list) {
				if ((companyId != subscription.getCompanyId()) ||
						(classNameId != subscription.getClassNameId()) ||
						(classPK != subscription.getClassPK())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SUBSCRIPTION_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

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

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<Subscription>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first subscription in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription findByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = fetchByC_C_C_First(companyId, classNameId,
				classPK, orderByComparator);

		if (subscription != null) {
			return subscription;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSubscriptionException(msg.toString());
	}

	/**
	 * Returns the first subscription in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching subscription, or <code>null</code> if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		List<Subscription> list = findByC_C_C(companyId, classNameId, classPK,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last subscription in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription findByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = fetchByC_C_C_Last(companyId, classNameId,
				classPK, orderByComparator);

		if (subscription != null) {
			return subscription;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSubscriptionException(msg.toString());
	}

	/**
	 * Returns the last subscription in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching subscription, or <code>null</code> if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_C_C(companyId, classNameId, classPK);

		List<Subscription> list = findByC_C_C(companyId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the subscriptions before and after the current subscription in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param subscriptionId the primary key of the current subscription
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a subscription with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription[] findByC_C_C_PrevAndNext(long subscriptionId,
		long companyId, long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = findByPrimaryKey(subscriptionId);

		Session session = null;

		try {
			session = openSession();

			Subscription[] array = new SubscriptionImpl[3];

			array[0] = getByC_C_C_PrevAndNext(session, subscription, companyId,
					classNameId, classPK, orderByComparator, true);

			array[1] = subscription;

			array[2] = getByC_C_C_PrevAndNext(session, subscription, companyId,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Subscription getByC_C_C_PrevAndNext(Session session,
		Subscription subscription, long companyId, long classNameId,
		long classPK, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SUBSCRIPTION_WHERE);

		query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

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

		qPos.add(companyId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(subscription);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Subscription> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the subscription where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchSubscriptionException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching subscription
	 * @throws com.liferay.portal.NoSuchSubscriptionException if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription findByC_U_C_C(long companyId, long userId,
		long classNameId, long classPK)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = fetchByC_U_C_C(companyId, userId,
				classNameId, classPK);

		if (subscription == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchSubscriptionException(msg.toString());
		}

		return subscription;
	}

	/**
	 * Returns the subscription where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching subscription, or <code>null</code> if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByC_U_C_C(long companyId, long userId,
		long classNameId, long classPK) throws SystemException {
		return fetchByC_U_C_C(companyId, userId, classNameId, classPK, true);
	}

	/**
	 * Returns the subscription where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching subscription, or <code>null</code> if a matching subscription could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription fetchByC_U_C_C(long companyId, long userId,
		long classNameId, long classPK, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, userId, classNameId, classPK
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_U_C_C,
					finderArgs, this);
		}

		if (result instanceof Subscription) {
			Subscription subscription = (Subscription)result;

			if ((companyId != subscription.getCompanyId()) ||
					(userId != subscription.getUserId()) ||
					(classNameId != subscription.getClassNameId()) ||
					(classPK != subscription.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_SUBSCRIPTION_WHERE);

			query.append(_FINDER_COLUMN_C_U_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_C_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_U_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Subscription> list = q.list();

				result = list;

				Subscription subscription = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_C_C,
						finderArgs, list);
				}
				else {
					subscription = list.get(0);

					cacheResult(subscription);

					if ((subscription.getCompanyId() != companyId) ||
							(subscription.getUserId() != userId) ||
							(subscription.getClassNameId() != classNameId) ||
							(subscription.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_C_C,
							finderArgs, subscription);
					}
				}

				return subscription;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_C_C,
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
				return (Subscription)result;
			}
		}
	}

	/**
	 * Returns all the subscriptions.
	 *
	 * @return the subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the subscriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of subscriptions
	 * @param end the upper bound of the range of subscriptions (not inclusive)
	 * @return the range of subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the subscriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of subscriptions
	 * @param end the upper bound of the range of subscriptions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public List<Subscription> findAll(int start, int end,
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

		List<Subscription> list = (List<Subscription>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SUBSCRIPTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SUBSCRIPTION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Subscription>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Subscription>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the subscriptions where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (Subscription subscription : findByUserId(userId)) {
			remove(subscription);
		}
	}

	/**
	 * Removes all the subscriptions where userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_C(long userId, long classNameId)
		throws SystemException {
		for (Subscription subscription : findByU_C(userId, classNameId)) {
			remove(subscription);
		}
	}

	/**
	 * Removes all the subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		for (Subscription subscription : findByC_C_C(companyId, classNameId,
				classPK)) {
			remove(subscription);
		}
	}

	/**
	 * Removes the subscription where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the subscription that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public Subscription removeByC_U_C_C(long companyId, long userId,
		long classNameId, long classPK)
		throws NoSuchSubscriptionException, SystemException {
		Subscription subscription = findByC_U_C_C(companyId, userId,
				classNameId, classPK);

		return remove(subscription);
	}

	/**
	 * Removes all the subscriptions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (Subscription subscription : findAll()) {
			remove(subscription);
		}
	}

	/**
	 * Returns the number of subscriptions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SUBSCRIPTION_WHERE);

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
	 * Returns the number of subscriptions where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_C(long userId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SUBSCRIPTION_WHERE);

			query.append(_FINDER_COLUMN_U_C_USERID_2);

			query.append(_FINDER_COLUMN_U_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of subscriptions where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SUBSCRIPTION_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of subscriptions where companyId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_U_C_C(long companyId, long userId, long classNameId,
		long classPK) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, userId, classNameId, classPK
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SUBSCRIPTION_WHERE);

			query.append(_FINDER_COLUMN_C_U_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_C_C_USERID_2);

			query.append(_FINDER_COLUMN_C_U_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_U_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of subscriptions.
	 *
	 * @return the number of subscriptions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SUBSCRIPTION);

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
	 * Initializes the subscription persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Subscription")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Subscription>> listenersList = new ArrayList<ModelListener<Subscription>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Subscription>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SubscriptionImpl.class.getName());
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
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	private static final String _SQL_SELECT_SUBSCRIPTION = "SELECT subscription FROM Subscription subscription";
	private static final String _SQL_SELECT_SUBSCRIPTION_WHERE = "SELECT subscription FROM Subscription subscription WHERE ";
	private static final String _SQL_COUNT_SUBSCRIPTION = "SELECT COUNT(subscription) FROM Subscription subscription";
	private static final String _SQL_COUNT_SUBSCRIPTION_WHERE = "SELECT COUNT(subscription) FROM Subscription subscription WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "subscription.userId = ?";
	private static final String _FINDER_COLUMN_U_C_USERID_2 = "subscription.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_C_CLASSNAMEID_2 = "subscription.classNameId = ?";
	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 = "subscription.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "subscription.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "subscription.classPK = ?";
	private static final String _FINDER_COLUMN_C_U_C_C_COMPANYID_2 = "subscription.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_C_C_USERID_2 = "subscription.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_C_C_CLASSNAMEID_2 = "subscription.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_C_C_CLASSPK_2 = "subscription.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "subscription.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Subscription exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Subscription exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SubscriptionPersistenceImpl.class);
	private static Subscription _nullSubscription = new SubscriptionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Subscription> toCacheModel() {
				return _nullSubscriptionCacheModel;
			}
		};

	private static CacheModel<Subscription> _nullSubscriptionCacheModel = new CacheModel<Subscription>() {
			public Subscription toEntityModel() {
				return _nullSubscription;
			}
		};
}