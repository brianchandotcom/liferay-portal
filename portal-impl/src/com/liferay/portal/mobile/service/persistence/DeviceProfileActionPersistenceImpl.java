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

package com.liferay.portal.mobile.service.persistence;

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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.mobile.NoSuchProfileActionException;
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.mobile.model.impl.DeviceProfileActionImpl;
import com.liferay.portal.mobile.model.impl.DeviceProfileActionModelImpl;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the device profile action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see DeviceProfileActionPersistence
 * @see DeviceProfileActionUtil
 * @generated
 */
public class DeviceProfileActionPersistenceImpl extends BasePersistenceImpl<DeviceProfileAction>
	implements DeviceProfileActionPersistence {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DeviceProfileActionUtil} to access the device profile action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DeviceProfileActionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED,
			DeviceProfileActionImpl.class, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),

			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_DEVICEPROFILERULEID = new FinderPath(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED,
			DeviceProfileActionImpl.class, FINDER_CLASS_NAME_LIST,
			"findByDeviceProfileRuleId",
			new String[] {
				Long.class.getName(),

			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_DEVICEPROFILERULEID = new FinderPath(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByDeviceProfileRuleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED,
			DeviceProfileActionImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the device profile action in the entity cache if it is enabled.
	 *
	 * @param deviceProfileAction the device profile action
	 */
	public void cacheResult(DeviceProfileAction deviceProfileAction) {
		EntityCacheUtil.putResult(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionImpl.class, deviceProfileAction.getPrimaryKey(),
			deviceProfileAction);

		deviceProfileAction.resetOriginalValues();
	}

	/**
	 * Caches the device profile actions in the entity cache if it is enabled.
	 *
	 * @param deviceProfileActions the device profile actions
	 */
	public void cacheResult(List<DeviceProfileAction> deviceProfileActions) {
		for (DeviceProfileAction deviceProfileAction : deviceProfileActions) {
			if (EntityCacheUtil.getResult(
						DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
						DeviceProfileActionImpl.class,
						deviceProfileAction.getPrimaryKey(), this) == null) {
				cacheResult(deviceProfileAction);
			}
		}
	}

	/**
	 * Clears the cache for all device profile actions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DeviceProfileActionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DeviceProfileActionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the device profile action.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DeviceProfileAction deviceProfileAction) {
		EntityCacheUtil.removeResult(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionImpl.class, deviceProfileAction.getPrimaryKey());
	}

	/**
	 * Creates a new device profile action with the primary key. Does not add the device profile action to the database.
	 *
	 * @param deviceProfileActionId the primary key for the new device profile action
	 * @return the new device profile action
	 */
	public DeviceProfileAction create(long deviceProfileActionId) {
		DeviceProfileAction deviceProfileAction = new DeviceProfileActionImpl();

		deviceProfileAction.setNew(true);
		deviceProfileAction.setPrimaryKey(deviceProfileActionId);

		String uuid = PortalUUIDUtil.generate();

		deviceProfileAction.setUuid(uuid);

		return deviceProfileAction;
	}

	/**
	 * Removes the device profile action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the device profile action
	 * @return the device profile action that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a device profile action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfileAction remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the device profile action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deviceProfileActionId the primary key of the device profile action
	 * @return the device profile action that was removed
	 * @throws com.liferay.portal.mobile.NoSuchProfileActionException if a device profile action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction remove(long deviceProfileActionId)
		throws NoSuchProfileActionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DeviceProfileAction deviceProfileAction = (DeviceProfileAction)session.get(DeviceProfileActionImpl.class,
					Long.valueOf(deviceProfileActionId));

			if (deviceProfileAction == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						deviceProfileActionId);
				}

				throw new NoSuchProfileActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					deviceProfileActionId);
			}

			return deviceProfileActionPersistence.remove(deviceProfileAction);
		}
		catch (NoSuchProfileActionException nsee) {
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
	 * Removes the device profile action from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deviceProfileAction the device profile action
	 * @return the device profile action that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfileAction remove(DeviceProfileAction deviceProfileAction)
		throws SystemException {
		return super.remove(deviceProfileAction);
	}

	@Override
	protected DeviceProfileAction removeImpl(
		DeviceProfileAction deviceProfileAction) throws SystemException {
		deviceProfileAction = toUnwrappedModel(deviceProfileAction);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, deviceProfileAction);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionImpl.class, deviceProfileAction.getPrimaryKey());

		return deviceProfileAction;
	}

	@Override
	public DeviceProfileAction updateImpl(
		com.liferay.portal.mobile.model.DeviceProfileAction deviceProfileAction,
		boolean merge) throws SystemException {
		deviceProfileAction = toUnwrappedModel(deviceProfileAction);

		if (Validator.isNull(deviceProfileAction.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			deviceProfileAction.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, deviceProfileAction, merge);

			deviceProfileAction.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileActionImpl.class, deviceProfileAction.getPrimaryKey(),
			deviceProfileAction);

		return deviceProfileAction;
	}

	protected DeviceProfileAction toUnwrappedModel(
		DeviceProfileAction deviceProfileAction) {
		if (deviceProfileAction instanceof DeviceProfileActionImpl) {
			return deviceProfileAction;
		}

		DeviceProfileActionImpl deviceProfileActionImpl = new DeviceProfileActionImpl();

		deviceProfileActionImpl.setNew(deviceProfileAction.isNew());
		deviceProfileActionImpl.setPrimaryKey(deviceProfileAction.getPrimaryKey());

		deviceProfileActionImpl.setUuid(deviceProfileAction.getUuid());
		deviceProfileActionImpl.setDeviceProfileActionId(deviceProfileAction.getDeviceProfileActionId());
		deviceProfileActionImpl.setDeviceProfileId(deviceProfileAction.getDeviceProfileId());
		deviceProfileActionImpl.setDeviceProfileRuleId(deviceProfileAction.getDeviceProfileRuleId());
		deviceProfileActionImpl.setName(deviceProfileAction.getName());
		deviceProfileActionImpl.setDescription(deviceProfileAction.getDescription());
		deviceProfileActionImpl.setType(deviceProfileAction.getType());
		deviceProfileActionImpl.setTypeSettings(deviceProfileAction.getTypeSettings());

		return deviceProfileActionImpl;
	}

	/**
	 * Returns the device profile action with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the device profile action
	 * @return the device profile action
	 * @throws com.liferay.portal.NoSuchModelException if a device profile action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfileAction findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the device profile action with the primary key or throws a {@link com.liferay.portal.mobile.NoSuchProfileActionException} if it could not be found.
	 *
	 * @param deviceProfileActionId the primary key of the device profile action
	 * @return the device profile action
	 * @throws com.liferay.portal.mobile.NoSuchProfileActionException if a device profile action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction findByPrimaryKey(long deviceProfileActionId)
		throws NoSuchProfileActionException, SystemException {
		DeviceProfileAction deviceProfileAction = fetchByPrimaryKey(deviceProfileActionId);

		if (deviceProfileAction == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					deviceProfileActionId);
			}

			throw new NoSuchProfileActionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				deviceProfileActionId);
		}

		return deviceProfileAction;
	}

	/**
	 * Returns the device profile action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the device profile action
	 * @return the device profile action, or <code>null</code> if a device profile action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfileAction fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the device profile action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deviceProfileActionId the primary key of the device profile action
	 * @return the device profile action, or <code>null</code> if a device profile action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction fetchByPrimaryKey(long deviceProfileActionId)
		throws SystemException {
		DeviceProfileAction deviceProfileAction = (DeviceProfileAction)EntityCacheUtil.getResult(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
				DeviceProfileActionImpl.class, deviceProfileActionId, this);

		if (deviceProfileAction == _nullDeviceProfileAction) {
			return null;
		}

		if (deviceProfileAction == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				deviceProfileAction = (DeviceProfileAction)session.get(DeviceProfileActionImpl.class,
						Long.valueOf(deviceProfileActionId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (deviceProfileAction != null) {
					cacheResult(deviceProfileAction);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
						DeviceProfileActionImpl.class, deviceProfileActionId,
						_nullDeviceProfileAction);
				}

				closeSession(session);
			}
		}

		return deviceProfileAction;
	}

	/**
	 * Returns all the device profile actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the device profile actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of device profile actions
	 * @param end the upper bound of the range of device profile actions (not inclusive)
	 * @return the range of matching device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the device profile actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of device profile actions
	 * @param end the upper bound of the range of device profile actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,

				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeviceProfileAction> list = (List<DeviceProfileAction>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DEVICEPROFILEACTION_WHERE);

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

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<DeviceProfileAction>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first device profile action in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching device profile action
	 * @throws com.liferay.portal.mobile.NoSuchProfileActionException if a matching device profile action could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchProfileActionException, SystemException {
		List<DeviceProfileAction> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileActionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last device profile action in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching device profile action
	 * @throws com.liferay.portal.mobile.NoSuchProfileActionException if a matching device profile action could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchProfileActionException, SystemException {
		int count = countByUuid(uuid);

		List<DeviceProfileAction> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileActionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the device profile actions before and after the current device profile action in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileActionId the primary key of the current device profile action
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next device profile action
	 * @throws com.liferay.portal.mobile.NoSuchProfileActionException if a device profile action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction[] findByUuid_PrevAndNext(
		long deviceProfileActionId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchProfileActionException, SystemException {
		DeviceProfileAction deviceProfileAction = findByPrimaryKey(deviceProfileActionId);

		Session session = null;

		try {
			session = openSession();

			DeviceProfileAction[] array = new DeviceProfileActionImpl[3];

			array[0] = getByUuid_PrevAndNext(session, deviceProfileAction,
					uuid, orderByComparator, true);

			array[1] = deviceProfileAction;

			array[2] = getByUuid_PrevAndNext(session, deviceProfileAction,
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

	protected DeviceProfileAction getByUuid_PrevAndNext(Session session,
		DeviceProfileAction deviceProfileAction, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DEVICEPROFILEACTION_WHERE);

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
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(deviceProfileAction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeviceProfileAction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the device profile actions where deviceProfileRuleId = &#63;.
	 *
	 * @param deviceProfileRuleId the device profile rule ID
	 * @return the matching device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findByDeviceProfileRuleId(
		long deviceProfileRuleId) throws SystemException {
		return findByDeviceProfileRuleId(deviceProfileRuleId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the device profile actions where deviceProfileRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileRuleId the device profile rule ID
	 * @param start the lower bound of the range of device profile actions
	 * @param end the upper bound of the range of device profile actions (not inclusive)
	 * @return the range of matching device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findByDeviceProfileRuleId(
		long deviceProfileRuleId, int start, int end) throws SystemException {
		return findByDeviceProfileRuleId(deviceProfileRuleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the device profile actions where deviceProfileRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileRuleId the device profile rule ID
	 * @param start the lower bound of the range of device profile actions
	 * @param end the upper bound of the range of device profile actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findByDeviceProfileRuleId(
		long deviceProfileRuleId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				deviceProfileRuleId,

				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeviceProfileAction> list = (List<DeviceProfileAction>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_DEVICEPROFILERULEID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DEVICEPROFILEACTION_WHERE);

			query.append(_FINDER_COLUMN_DEVICEPROFILERULEID_DEVICEPROFILERULEID_2);

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

				qPos.add(deviceProfileRuleId);

				list = (List<DeviceProfileAction>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_DEVICEPROFILERULEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_DEVICEPROFILERULEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first device profile action in the ordered set where deviceProfileRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileRuleId the device profile rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching device profile action
	 * @throws com.liferay.portal.mobile.NoSuchProfileActionException if a matching device profile action could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction findByDeviceProfileRuleId_First(
		long deviceProfileRuleId, OrderByComparator orderByComparator)
		throws NoSuchProfileActionException, SystemException {
		List<DeviceProfileAction> list = findByDeviceProfileRuleId(deviceProfileRuleId,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("deviceProfileRuleId=");
			msg.append(deviceProfileRuleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileActionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last device profile action in the ordered set where deviceProfileRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileRuleId the device profile rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching device profile action
	 * @throws com.liferay.portal.mobile.NoSuchProfileActionException if a matching device profile action could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction findByDeviceProfileRuleId_Last(
		long deviceProfileRuleId, OrderByComparator orderByComparator)
		throws NoSuchProfileActionException, SystemException {
		int count = countByDeviceProfileRuleId(deviceProfileRuleId);

		List<DeviceProfileAction> list = findByDeviceProfileRuleId(deviceProfileRuleId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("deviceProfileRuleId=");
			msg.append(deviceProfileRuleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileActionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the device profile actions before and after the current device profile action in the ordered set where deviceProfileRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileActionId the primary key of the current device profile action
	 * @param deviceProfileRuleId the device profile rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next device profile action
	 * @throws com.liferay.portal.mobile.NoSuchProfileActionException if a device profile action with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileAction[] findByDeviceProfileRuleId_PrevAndNext(
		long deviceProfileActionId, long deviceProfileRuleId,
		OrderByComparator orderByComparator)
		throws NoSuchProfileActionException, SystemException {
		DeviceProfileAction deviceProfileAction = findByPrimaryKey(deviceProfileActionId);

		Session session = null;

		try {
			session = openSession();

			DeviceProfileAction[] array = new DeviceProfileActionImpl[3];

			array[0] = getByDeviceProfileRuleId_PrevAndNext(session,
					deviceProfileAction, deviceProfileRuleId,
					orderByComparator, true);

			array[1] = deviceProfileAction;

			array[2] = getByDeviceProfileRuleId_PrevAndNext(session,
					deviceProfileAction, deviceProfileRuleId,
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

	protected DeviceProfileAction getByDeviceProfileRuleId_PrevAndNext(
		Session session, DeviceProfileAction deviceProfileAction,
		long deviceProfileRuleId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DEVICEPROFILEACTION_WHERE);

		query.append(_FINDER_COLUMN_DEVICEPROFILERULEID_DEVICEPROFILERULEID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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

		qPos.add(deviceProfileRuleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(deviceProfileAction);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeviceProfileAction> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the device profile actions.
	 *
	 * @return the device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the device profile actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of device profile actions
	 * @param end the upper bound of the range of device profile actions (not inclusive)
	 * @return the range of device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the device profile actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of device profile actions
	 * @param end the upper bound of the range of device profile actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileAction> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeviceProfileAction> list = (List<DeviceProfileAction>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DEVICEPROFILEACTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DEVICEPROFILEACTION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DeviceProfileAction>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DeviceProfileAction>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the device profile actions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DeviceProfileAction deviceProfileAction : findByUuid(uuid)) {
			deviceProfileActionPersistence.remove(deviceProfileAction);
		}
	}

	/**
	 * Removes all the device profile actions where deviceProfileRuleId = &#63; from the database.
	 *
	 * @param deviceProfileRuleId the device profile rule ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByDeviceProfileRuleId(long deviceProfileRuleId)
		throws SystemException {
		for (DeviceProfileAction deviceProfileAction : findByDeviceProfileRuleId(
				deviceProfileRuleId)) {
			deviceProfileActionPersistence.remove(deviceProfileAction);
		}
	}

	/**
	 * Removes all the device profile actions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DeviceProfileAction deviceProfileAction : findAll()) {
			deviceProfileActionPersistence.remove(deviceProfileAction);
		}
	}

	/**
	 * Returns the number of device profile actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEVICEPROFILEACTION_WHERE);

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
	 * Returns the number of device profile actions where deviceProfileRuleId = &#63;.
	 *
	 * @param deviceProfileRuleId the device profile rule ID
	 * @return the number of matching device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByDeviceProfileRuleId(long deviceProfileRuleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { deviceProfileRuleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DEVICEPROFILERULEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEVICEPROFILEACTION_WHERE);

			query.append(_FINDER_COLUMN_DEVICEPROFILERULEID_DEVICEPROFILERULEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(deviceProfileRuleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DEVICEPROFILERULEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of device profile actions.
	 *
	 * @return the number of device profile actions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DEVICEPROFILEACTION);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the device profile action persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.mobile.model.DeviceProfileAction")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DeviceProfileAction>> listenersList = new ArrayList<ModelListener<DeviceProfileAction>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DeviceProfileAction>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DeviceProfileActionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DeviceProfilePersistence.class)
	protected DeviceProfilePersistence deviceProfilePersistence;
	@BeanReference(type = DeviceProfileActionPersistence.class)
	protected DeviceProfileActionPersistence deviceProfileActionPersistence;
	@BeanReference(type = DeviceProfileRulePersistence.class)
	protected DeviceProfileRulePersistence deviceProfileRulePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DEVICEPROFILEACTION = "SELECT deviceProfileAction FROM DeviceProfileAction deviceProfileAction";
	private static final String _SQL_SELECT_DEVICEPROFILEACTION_WHERE = "SELECT deviceProfileAction FROM DeviceProfileAction deviceProfileAction WHERE ";
	private static final String _SQL_COUNT_DEVICEPROFILEACTION = "SELECT COUNT(deviceProfileAction) FROM DeviceProfileAction deviceProfileAction";
	private static final String _SQL_COUNT_DEVICEPROFILEACTION_WHERE = "SELECT COUNT(deviceProfileAction) FROM DeviceProfileAction deviceProfileAction WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "deviceProfileAction.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "deviceProfileAction.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(deviceProfileAction.uuid IS NULL OR deviceProfileAction.uuid = ?)";
	private static final String _FINDER_COLUMN_DEVICEPROFILERULEID_DEVICEPROFILERULEID_2 =
		"deviceProfileAction.deviceProfileRuleId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "deviceProfileAction.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DeviceProfileAction exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DeviceProfileAction exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DeviceProfileActionPersistenceImpl.class);
	private static DeviceProfileAction _nullDeviceProfileAction = new DeviceProfileActionImpl() {
			public Object clone() {
				return this;
			}

			public CacheModel<DeviceProfileAction> toCacheModel() {
				return _nullDeviceProfileActionCacheModel;
			}
		};

	private static CacheModel<DeviceProfileAction> _nullDeviceProfileActionCacheModel =
		new CacheModel<DeviceProfileAction>() {
			public DeviceProfileAction toEntityModel() {
				return _nullDeviceProfileAction;
			}
		};
}