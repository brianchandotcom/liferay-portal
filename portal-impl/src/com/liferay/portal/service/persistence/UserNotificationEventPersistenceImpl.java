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
import com.liferay.portal.NoSuchUserNotificationEventException;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.model.impl.UserNotificationEventImpl;
import com.liferay.portal.model.impl.UserNotificationEventModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the user notification event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventPersistence
 * @see UserNotificationEventUtil
 * @generated
 */
public class UserNotificationEventPersistenceImpl extends BasePersistenceImpl<UserNotificationEvent>
	implements UserNotificationEventPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserNotificationEventUtil} to access the user notification event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserNotificationEventImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			UserNotificationEventModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			UserNotificationEventModelImpl.UUID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the user notification event in the entity cache if it is enabled.
	 *
	 * @param userNotificationEvent the user notification event
	 */
	public void cacheResult(UserNotificationEvent userNotificationEvent) {
		EntityCacheUtil.putResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			userNotificationEvent.getPrimaryKey(), userNotificationEvent);

		userNotificationEvent.resetOriginalValues();
	}

	/**
	 * Caches the user notification events in the entity cache if it is enabled.
	 *
	 * @param userNotificationEvents the user notification events
	 */
	public void cacheResult(List<UserNotificationEvent> userNotificationEvents) {
		for (UserNotificationEvent userNotificationEvent : userNotificationEvents) {
			if (EntityCacheUtil.getResult(
						UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
						UserNotificationEventImpl.class,
						userNotificationEvent.getPrimaryKey()) == null) {
				cacheResult(userNotificationEvent);
			}
			else {
				userNotificationEvent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user notification events.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserNotificationEventImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserNotificationEventImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user notification event.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserNotificationEvent userNotificationEvent) {
		EntityCacheUtil.removeResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			userNotificationEvent.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<UserNotificationEvent> userNotificationEvents) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserNotificationEvent userNotificationEvent : userNotificationEvents) {
			EntityCacheUtil.removeResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
				UserNotificationEventImpl.class,
				userNotificationEvent.getPrimaryKey());
		}
	}

	/**
	 * Creates a new user notification event with the primary key. Does not add the user notification event to the database.
	 *
	 * @param userNotificationEventId the primary key for the new user notification event
	 * @return the new user notification event
	 */
	public UserNotificationEvent create(long userNotificationEventId) {
		UserNotificationEvent userNotificationEvent = new UserNotificationEventImpl();

		userNotificationEvent.setNew(true);
		userNotificationEvent.setPrimaryKey(userNotificationEventId);

		String uuid = PortalUUIDUtil.generate();

		userNotificationEvent.setUuid(uuid);

		return userNotificationEvent;
	}

	/**
	 * Removes the user notification event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event that was removed
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent remove(long userNotificationEventId)
		throws NoSuchUserNotificationEventException, SystemException {
		return remove(Long.valueOf(userNotificationEventId));
	}

	/**
	 * Removes the user notification event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user notification event
	 * @return the user notification event that was removed
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserNotificationEvent remove(Serializable primaryKey)
		throws NoSuchUserNotificationEventException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent userNotificationEvent = (UserNotificationEvent)session.get(UserNotificationEventImpl.class,
					primaryKey);

			if (userNotificationEvent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserNotificationEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(userNotificationEvent);
		}
		catch (NoSuchUserNotificationEventException nsee) {
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
	protected UserNotificationEvent removeImpl(
		UserNotificationEvent userNotificationEvent) throws SystemException {
		userNotificationEvent = toUnwrappedModel(userNotificationEvent);

		Session session = null;

		try {
			session = openSession();

			if (userNotificationEvent.isCachedModel()) {
				userNotificationEvent = (UserNotificationEvent)session.get(UserNotificationEventImpl.class,
						userNotificationEvent.getPrimaryKeyObj());
			}

			if (userNotificationEvent != null) {
				session.delete(userNotificationEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (userNotificationEvent != null) {
			clearCache(userNotificationEvent);
		}

		return userNotificationEvent;
	}

	@Override
	public UserNotificationEvent updateImpl(
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent)
		throws SystemException {
		userNotificationEvent = toUnwrappedModel(userNotificationEvent);

		boolean isNew = userNotificationEvent.isNew();

		UserNotificationEventModelImpl userNotificationEventModelImpl = (UserNotificationEventModelImpl)userNotificationEvent;

		if (Validator.isNull(userNotificationEvent.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			userNotificationEvent.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (userNotificationEvent.isNew()) {
				session.save(userNotificationEvent);

				userNotificationEvent.setNew(false);
			}
			else {
				session.merge(userNotificationEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserNotificationEventModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { userNotificationEventModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUuid(),
						Long.valueOf(userNotificationEventModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUuid(),
						Long.valueOf(userNotificationEventModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(userNotificationEventModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(userNotificationEventModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(userNotificationEventModelImpl.getOriginalUserId()),
						Boolean.valueOf(userNotificationEventModelImpl.getOriginalArchived())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A,
					args);

				args = new Object[] {
						Long.valueOf(userNotificationEventModelImpl.getUserId()),
						Boolean.valueOf(userNotificationEventModelImpl.getArchived())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A,
					args);
			}
		}

		EntityCacheUtil.putResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			userNotificationEvent.getPrimaryKey(), userNotificationEvent);

		return userNotificationEvent;
	}

	protected UserNotificationEvent toUnwrappedModel(
		UserNotificationEvent userNotificationEvent) {
		if (userNotificationEvent instanceof UserNotificationEventImpl) {
			return userNotificationEvent;
		}

		UserNotificationEventImpl userNotificationEventImpl = new UserNotificationEventImpl();

		userNotificationEventImpl.setNew(userNotificationEvent.isNew());
		userNotificationEventImpl.setPrimaryKey(userNotificationEvent.getPrimaryKey());

		userNotificationEventImpl.setUuid(userNotificationEvent.getUuid());
		userNotificationEventImpl.setUserNotificationEventId(userNotificationEvent.getUserNotificationEventId());
		userNotificationEventImpl.setCompanyId(userNotificationEvent.getCompanyId());
		userNotificationEventImpl.setUserId(userNotificationEvent.getUserId());
		userNotificationEventImpl.setType(userNotificationEvent.getType());
		userNotificationEventImpl.setTimestamp(userNotificationEvent.getTimestamp());
		userNotificationEventImpl.setDeliverBy(userNotificationEvent.getDeliverBy());
		userNotificationEventImpl.setPayload(userNotificationEvent.getPayload());
		userNotificationEventImpl.setArchived(userNotificationEvent.isArchived());

		return userNotificationEventImpl;
	}

	/**
	 * Returns the user notification event with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user notification event
	 * @return the user notification event
	 * @throws com.liferay.portal.NoSuchModelException if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserNotificationEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user notification event with the primary key or throws a {@link com.liferay.portal.NoSuchUserNotificationEventException} if it could not be found.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByPrimaryKey(long userNotificationEventId)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByPrimaryKey(userNotificationEventId);

		if (userNotificationEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userNotificationEventId);
			}

			throw new NoSuchUserNotificationEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userNotificationEventId);
		}

		return userNotificationEvent;
	}

	/**
	 * Returns the user notification event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user notification event
	 * @return the user notification event, or <code>null</code> if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public UserNotificationEvent fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the user notification event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event, or <code>null</code> if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByPrimaryKey(long userNotificationEventId)
		throws SystemException {
		UserNotificationEvent userNotificationEvent = (UserNotificationEvent)EntityCacheUtil.getResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
				UserNotificationEventImpl.class, userNotificationEventId);

		if (userNotificationEvent == _nullUserNotificationEvent) {
			return null;
		}

		if (userNotificationEvent == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				userNotificationEvent = (UserNotificationEvent)session.get(UserNotificationEventImpl.class,
						Long.valueOf(userNotificationEventId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (userNotificationEvent != null) {
					cacheResult(userNotificationEvent);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
						UserNotificationEventImpl.class,
						userNotificationEventId, _nullUserNotificationEvent);
				}

				closeSession(session);
			}
		}

		return userNotificationEvent;
	}

	/**
	 * Returns all the user notification events where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUuid(String uuid, int start,
		int end) throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if (!Validator.equals(uuid, userNotificationEvent.getUuid())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<UserNotificationEvent>)QueryUtil.list(q,
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
	 * Returns the first user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByUuid_First(uuid,
				orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<UserNotificationEvent> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByUuid_Last(uuid,
				orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<UserNotificationEvent> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent[] findByUuid_PrevAndNext(
		long userNotificationEventId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUuid_PrevAndNext(session, userNotificationEvent,
					uuid, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByUuid_PrevAndNext(session, userNotificationEvent,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByUuid_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUuid_C(String uuid,
		long companyId, int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUuid_C(String uuid,
		long companyId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if (!Validator.equals(uuid, userNotificationEvent.getUuid()) ||
						(companyId != userNotificationEvent.getCompanyId())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<UserNotificationEvent>)QueryUtil.list(q,
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
	 * Returns the first user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByUuid_C_First(String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByUuid_C_First(String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws SystemException {
		List<UserNotificationEvent> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByUuid_C_Last(String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<UserNotificationEvent> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent[] findByUuid_C_PrevAndNext(
		long userNotificationEventId, String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, userNotificationEvent,
					uuid, companyId, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByUuid_C_PrevAndNext(session, userNotificationEvent,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByUuid_C_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, String uuid,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user notification events where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUserId(long userId, int start,
		int end) throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByUserId(long userId, int start,
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

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<UserNotificationEvent>)QueryUtil.list(q,
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
	 * Returns the first user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByUserId_First(userId,
				orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<UserNotificationEvent> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByUserId_Last(userId,
				orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<UserNotificationEvent> list = findByUserId(userId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent[] findByUserId_PrevAndNext(
		long userNotificationEventId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUserId_PrevAndNext(session, userNotificationEvent,
					userId, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByUserId_PrevAndNext(session, userNotificationEvent,
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

	protected UserNotificationEvent getByUserId_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

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

		else {
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @return the matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByU_A(long userId, boolean archived)
		throws SystemException {
		return findByU_A(userId, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByU_A(long userId, boolean archived,
		int start, int end) throws SystemException {
		return findByU_A(userId, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findByU_A(long userId, boolean archived,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A;
			finderArgs = new Object[] { userId, archived };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_A;
			finderArgs = new Object[] {
					userId, archived,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(archived != userNotificationEvent.getArchived())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_A_USERID_2);

			query.append(_FINDER_COLUMN_U_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(q,
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByU_A_First(long userId, boolean archived,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByU_A_First(userId,
				archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", archived=");
		msg.append(archived);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByU_A_First(long userId,
		boolean archived, OrderByComparator orderByComparator)
		throws SystemException {
		List<UserNotificationEvent> list = findByU_A(userId, archived, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent findByU_A_Last(long userId, boolean archived,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = fetchByU_A_Last(userId,
				archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", archived=");
		msg.append(archived);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent fetchByU_A_Last(long userId, boolean archived,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByU_A(userId, archived);

		List<UserNotificationEvent> list = findByU_A(userId, archived,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserNotificationEvent[] findByU_A_PrevAndNext(
		long userNotificationEventId, long userId, boolean archived,
		OrderByComparator orderByComparator)
		throws NoSuchUserNotificationEventException, SystemException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_A_PrevAndNext(session, userNotificationEvent,
					userId, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_A_PrevAndNext(session, userNotificationEvent,
					userId, archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_A_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		boolean archived, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_A_USERID_2);

		query.append(_FINDER_COLUMN_U_A_ARCHIVED_2);

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(archived);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the user notification events.
	 *
	 * @return the user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserNotificationEvent> findAll(int start, int end,
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

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERNOTIFICATIONEVENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERNOTIFICATIONEVENT.concat(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
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
	 * Removes all the user notification events where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (UserNotificationEvent userNotificationEvent : findByUuid(uuid)) {
			remove(userNotificationEvent);
		}
	}

	/**
	 * Removes all the user notification events where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (UserNotificationEvent userNotificationEvent : findByUuid_C(uuid,
				companyId)) {
			remove(userNotificationEvent);
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (UserNotificationEvent userNotificationEvent : findByUserId(userId)) {
			remove(userNotificationEvent);
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_A(long userId, boolean archived)
		throws SystemException {
		for (UserNotificationEvent userNotificationEvent : findByU_A(userId,
				archived)) {
			remove(userNotificationEvent);
		}
	}

	/**
	 * Removes all the user notification events from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (UserNotificationEvent userNotificationEvent : findAll()) {
			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user notification events where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

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
	 * Returns the number of user notification events where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @return the number of matching user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_A(long userId, boolean archived)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId, archived };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_A_USERID_2);

			query.append(_FINDER_COLUMN_U_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(archived);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of user notification events.
	 *
	 * @return the number of user notification events
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERNOTIFICATIONEVENT);

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
	 * Initializes the user notification event persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.UserNotificationEvent")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserNotificationEvent>> listenersList = new ArrayList<ModelListener<UserNotificationEvent>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserNotificationEvent>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(UserNotificationEventImpl.class.getName());
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
	private static final String _SQL_SELECT_USERNOTIFICATIONEVENT = "SELECT userNotificationEvent FROM UserNotificationEvent userNotificationEvent";
	private static final String _SQL_SELECT_USERNOTIFICATIONEVENT_WHERE = "SELECT userNotificationEvent FROM UserNotificationEvent userNotificationEvent WHERE ";
	private static final String _SQL_COUNT_USERNOTIFICATIONEVENT = "SELECT COUNT(userNotificationEvent) FROM UserNotificationEvent userNotificationEvent";
	private static final String _SQL_COUNT_USERNOTIFICATIONEVENT_WHERE = "SELECT COUNT(userNotificationEvent) FROM UserNotificationEvent userNotificationEvent WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "userNotificationEvent.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "userNotificationEvent.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(userNotificationEvent.uuid IS NULL OR userNotificationEvent.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "userNotificationEvent.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "userNotificationEvent.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(userNotificationEvent.uuid IS NULL OR userNotificationEvent.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "userNotificationEvent.companyId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "userNotificationEvent.userId = ?";
	private static final String _FINDER_COLUMN_U_A_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_A_ARCHIVED_2 = "userNotificationEvent.archived = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userNotificationEvent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserNotificationEvent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserNotificationEvent exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(UserNotificationEventPersistenceImpl.class);
	private static UserNotificationEvent _nullUserNotificationEvent = new UserNotificationEventImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserNotificationEvent> toCacheModel() {
				return _nullUserNotificationEventCacheModel;
			}
		};

	private static CacheModel<UserNotificationEvent> _nullUserNotificationEventCacheModel =
		new CacheModel<UserNotificationEvent>() {
			public UserNotificationEvent toEntityModel() {
				return _nullUserNotificationEvent;
			}
		};
}