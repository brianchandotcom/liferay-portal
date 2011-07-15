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
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.mobile.NoSuchProfileRuleException;
import com.liferay.portal.mobile.model.DeviceProfileRule;
import com.liferay.portal.mobile.model.impl.DeviceProfileRuleImpl;
import com.liferay.portal.mobile.model.impl.DeviceProfileRuleModelImpl;
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
 * The persistence implementation for the device profile rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see DeviceProfileRulePersistence
 * @see DeviceProfileRuleUtil
 * @generated
 */
public class DeviceProfileRulePersistenceImpl extends BasePersistenceImpl<DeviceProfileRule>
	implements DeviceProfileRulePersistence {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DeviceProfileRuleUtil} to access the device profile rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DeviceProfileRuleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleModelImpl.FINDER_CACHE_ENABLED,
			DeviceProfileRuleImpl.class, FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),

			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_DEVICEPROFILEID = new FinderPath(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleModelImpl.FINDER_CACHE_ENABLED,
			DeviceProfileRuleImpl.class, FINDER_CLASS_NAME_LIST,
			"findByDeviceProfileId",
			new String[] {
				Long.class.getName(),

			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_DEVICEPROFILEID = new FinderPath(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByDeviceProfileId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleModelImpl.FINDER_CACHE_ENABLED,
			DeviceProfileRuleImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the device profile rule in the entity cache if it is enabled.
	 *
	 * @param deviceProfileRule the device profile rule
	 */
	public void cacheResult(DeviceProfileRule deviceProfileRule) {
		EntityCacheUtil.putResult(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleImpl.class, deviceProfileRule.getPrimaryKey(),
			deviceProfileRule);

		deviceProfileRule.resetOriginalValues();
	}

	/**
	 * Caches the device profile rules in the entity cache if it is enabled.
	 *
	 * @param deviceProfileRules the device profile rules
	 */
	public void cacheResult(List<DeviceProfileRule> deviceProfileRules) {
		for (DeviceProfileRule deviceProfileRule : deviceProfileRules) {
			if (EntityCacheUtil.getResult(
						DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
						DeviceProfileRuleImpl.class,
						deviceProfileRule.getPrimaryKey(), this) == null) {
				cacheResult(deviceProfileRule);
			}
		}
	}

	/**
	 * Clears the cache for all device profile rules.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DeviceProfileRuleImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DeviceProfileRuleImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the device profile rule.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DeviceProfileRule deviceProfileRule) {
		EntityCacheUtil.removeResult(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleImpl.class, deviceProfileRule.getPrimaryKey());
	}

	/**
	 * Creates a new device profile rule with the primary key. Does not add the device profile rule to the database.
	 *
	 * @param deviceProfileRuleId the primary key for the new device profile rule
	 * @return the new device profile rule
	 */
	public DeviceProfileRule create(long deviceProfileRuleId) {
		DeviceProfileRule deviceProfileRule = new DeviceProfileRuleImpl();

		deviceProfileRule.setNew(true);
		deviceProfileRule.setPrimaryKey(deviceProfileRuleId);

		String uuid = PortalUUIDUtil.generate();

		deviceProfileRule.setUuid(uuid);

		return deviceProfileRule;
	}

	/**
	 * Removes the device profile rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the device profile rule
	 * @return the device profile rule that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a device profile rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfileRule remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the device profile rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deviceProfileRuleId the primary key of the device profile rule
	 * @return the device profile rule that was removed
	 * @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a device profile rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule remove(long deviceProfileRuleId)
		throws NoSuchProfileRuleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DeviceProfileRule deviceProfileRule = (DeviceProfileRule)session.get(DeviceProfileRuleImpl.class,
					Long.valueOf(deviceProfileRuleId));

			if (deviceProfileRule == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						deviceProfileRuleId);
				}

				throw new NoSuchProfileRuleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					deviceProfileRuleId);
			}

			return deviceProfileRulePersistence.remove(deviceProfileRule);
		}
		catch (NoSuchProfileRuleException nsee) {
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
	 * Removes the device profile rule from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deviceProfileRule the device profile rule
	 * @return the device profile rule that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfileRule remove(DeviceProfileRule deviceProfileRule)
		throws SystemException {
		return super.remove(deviceProfileRule);
	}

	@Override
	protected DeviceProfileRule removeImpl(DeviceProfileRule deviceProfileRule)
		throws SystemException {
		deviceProfileRule = toUnwrappedModel(deviceProfileRule);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, deviceProfileRule);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleImpl.class, deviceProfileRule.getPrimaryKey());

		return deviceProfileRule;
	}

	@Override
	public DeviceProfileRule updateImpl(
		com.liferay.portal.mobile.model.DeviceProfileRule deviceProfileRule,
		boolean merge) throws SystemException {
		deviceProfileRule = toUnwrappedModel(deviceProfileRule);

		if (Validator.isNull(deviceProfileRule.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			deviceProfileRule.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, deviceProfileRule, merge);

			deviceProfileRule.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileRuleImpl.class, deviceProfileRule.getPrimaryKey(),
			deviceProfileRule);

		return deviceProfileRule;
	}

	protected DeviceProfileRule toUnwrappedModel(
		DeviceProfileRule deviceProfileRule) {
		if (deviceProfileRule instanceof DeviceProfileRuleImpl) {
			return deviceProfileRule;
		}

		DeviceProfileRuleImpl deviceProfileRuleImpl = new DeviceProfileRuleImpl();

		deviceProfileRuleImpl.setNew(deviceProfileRule.isNew());
		deviceProfileRuleImpl.setPrimaryKey(deviceProfileRule.getPrimaryKey());

		deviceProfileRuleImpl.setUuid(deviceProfileRule.getUuid());
		deviceProfileRuleImpl.setDeviceProfileRuleId(deviceProfileRule.getDeviceProfileRuleId());
		deviceProfileRuleImpl.setDeviceProfileId(deviceProfileRule.getDeviceProfileId());
		deviceProfileRuleImpl.setName(deviceProfileRule.getName());
		deviceProfileRuleImpl.setDescription(deviceProfileRule.getDescription());
		deviceProfileRuleImpl.setRuleType(deviceProfileRule.getRuleType());
		deviceProfileRuleImpl.setRuleTypeSettings(deviceProfileRule.getRuleTypeSettings());

		return deviceProfileRuleImpl;
	}

	/**
	 * Returns the device profile rule with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the device profile rule
	 * @return the device profile rule
	 * @throws com.liferay.portal.NoSuchModelException if a device profile rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfileRule findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the device profile rule with the primary key or throws a {@link com.liferay.portal.mobile.NoSuchProfileRuleException} if it could not be found.
	 *
	 * @param deviceProfileRuleId the primary key of the device profile rule
	 * @return the device profile rule
	 * @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a device profile rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule findByPrimaryKey(long deviceProfileRuleId)
		throws NoSuchProfileRuleException, SystemException {
		DeviceProfileRule deviceProfileRule = fetchByPrimaryKey(deviceProfileRuleId);

		if (deviceProfileRule == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					deviceProfileRuleId);
			}

			throw new NoSuchProfileRuleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				deviceProfileRuleId);
		}

		return deviceProfileRule;
	}

	/**
	 * Returns the device profile rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the device profile rule
	 * @return the device profile rule, or <code>null</code> if a device profile rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfileRule fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the device profile rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deviceProfileRuleId the primary key of the device profile rule
	 * @return the device profile rule, or <code>null</code> if a device profile rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule fetchByPrimaryKey(long deviceProfileRuleId)
		throws SystemException {
		DeviceProfileRule deviceProfileRule = (DeviceProfileRule)EntityCacheUtil.getResult(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
				DeviceProfileRuleImpl.class, deviceProfileRuleId, this);

		if (deviceProfileRule == _nullDeviceProfileRule) {
			return null;
		}

		if (deviceProfileRule == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				deviceProfileRule = (DeviceProfileRule)session.get(DeviceProfileRuleImpl.class,
						Long.valueOf(deviceProfileRuleId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (deviceProfileRule != null) {
					cacheResult(deviceProfileRule);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DeviceProfileRuleModelImpl.ENTITY_CACHE_ENABLED,
						DeviceProfileRuleImpl.class, deviceProfileRuleId,
						_nullDeviceProfileRule);
				}

				closeSession(session);
			}
		}

		return deviceProfileRule;
	}

	/**
	 * Returns all the device profile rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the device profile rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of device profile rules
	 * @param end the upper bound of the range of device profile rules (not inclusive)
	 * @return the range of matching device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the device profile rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of device profile rules
	 * @param end the upper bound of the range of device profile rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,

				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeviceProfileRule> list = (List<DeviceProfileRule>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_DEVICEPROFILERULE_WHERE);

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

				list = (List<DeviceProfileRule>)QueryUtil.list(q, getDialect(),
						start, end);
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
	 * Returns the first device profile rule in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching device profile rule
	 * @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a matching device profile rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchProfileRuleException, SystemException {
		List<DeviceProfileRule> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileRuleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last device profile rule in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching device profile rule
	 * @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a matching device profile rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchProfileRuleException, SystemException {
		int count = countByUuid(uuid);

		List<DeviceProfileRule> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileRuleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the device profile rules before and after the current device profile rule in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileRuleId the primary key of the current device profile rule
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next device profile rule
	 * @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a device profile rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule[] findByUuid_PrevAndNext(
		long deviceProfileRuleId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchProfileRuleException, SystemException {
		DeviceProfileRule deviceProfileRule = findByPrimaryKey(deviceProfileRuleId);

		Session session = null;

		try {
			session = openSession();

			DeviceProfileRule[] array = new DeviceProfileRuleImpl[3];

			array[0] = getByUuid_PrevAndNext(session, deviceProfileRule, uuid,
					orderByComparator, true);

			array[1] = deviceProfileRule;

			array[2] = getByUuid_PrevAndNext(session, deviceProfileRule, uuid,
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

	protected DeviceProfileRule getByUuid_PrevAndNext(Session session,
		DeviceProfileRule deviceProfileRule, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DEVICEPROFILERULE_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(deviceProfileRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeviceProfileRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the device profile rules where deviceProfileId = &#63;.
	 *
	 * @param deviceProfileId the device profile ID
	 * @return the matching device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findByDeviceProfileId(long deviceProfileId)
		throws SystemException {
		return findByDeviceProfileId(deviceProfileId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the device profile rules where deviceProfileId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileId the device profile ID
	 * @param start the lower bound of the range of device profile rules
	 * @param end the upper bound of the range of device profile rules (not inclusive)
	 * @return the range of matching device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findByDeviceProfileId(long deviceProfileId,
		int start, int end) throws SystemException {
		return findByDeviceProfileId(deviceProfileId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the device profile rules where deviceProfileId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileId the device profile ID
	 * @param start the lower bound of the range of device profile rules
	 * @param end the upper bound of the range of device profile rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findByDeviceProfileId(long deviceProfileId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				deviceProfileId,

				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeviceProfileRule> list = (List<DeviceProfileRule>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_DEVICEPROFILEID,
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

			query.append(_SQL_SELECT_DEVICEPROFILERULE_WHERE);

			query.append(_FINDER_COLUMN_DEVICEPROFILEID_DEVICEPROFILEID_2);

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

				qPos.add(deviceProfileId);

				list = (List<DeviceProfileRule>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_DEVICEPROFILEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_DEVICEPROFILEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first device profile rule in the ordered set where deviceProfileId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileId the device profile ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching device profile rule
	 * @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a matching device profile rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule findByDeviceProfileId_First(long deviceProfileId,
		OrderByComparator orderByComparator)
		throws NoSuchProfileRuleException, SystemException {
		List<DeviceProfileRule> list = findByDeviceProfileId(deviceProfileId,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("deviceProfileId=");
			msg.append(deviceProfileId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileRuleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last device profile rule in the ordered set where deviceProfileId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileId the device profile ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching device profile rule
	 * @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a matching device profile rule could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule findByDeviceProfileId_Last(long deviceProfileId,
		OrderByComparator orderByComparator)
		throws NoSuchProfileRuleException, SystemException {
		int count = countByDeviceProfileId(deviceProfileId);

		List<DeviceProfileRule> list = findByDeviceProfileId(deviceProfileId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("deviceProfileId=");
			msg.append(deviceProfileId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileRuleException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the device profile rules before and after the current device profile rule in the ordered set where deviceProfileId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileRuleId the primary key of the current device profile rule
	 * @param deviceProfileId the device profile ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next device profile rule
	 * @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a device profile rule with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfileRule[] findByDeviceProfileId_PrevAndNext(
		long deviceProfileRuleId, long deviceProfileId,
		OrderByComparator orderByComparator)
		throws NoSuchProfileRuleException, SystemException {
		DeviceProfileRule deviceProfileRule = findByPrimaryKey(deviceProfileRuleId);

		Session session = null;

		try {
			session = openSession();

			DeviceProfileRule[] array = new DeviceProfileRuleImpl[3];

			array[0] = getByDeviceProfileId_PrevAndNext(session,
					deviceProfileRule, deviceProfileId, orderByComparator, true);

			array[1] = deviceProfileRule;

			array[2] = getByDeviceProfileId_PrevAndNext(session,
					deviceProfileRule, deviceProfileId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DeviceProfileRule getByDeviceProfileId_PrevAndNext(
		Session session, DeviceProfileRule deviceProfileRule,
		long deviceProfileId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DEVICEPROFILERULE_WHERE);

		query.append(_FINDER_COLUMN_DEVICEPROFILEID_DEVICEPROFILEID_2);

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

		qPos.add(deviceProfileId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(deviceProfileRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeviceProfileRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the device profile rules.
	 *
	 * @return the device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the device profile rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of device profile rules
	 * @param end the upper bound of the range of device profile rules (not inclusive)
	 * @return the range of device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the device profile rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of device profile rules
	 * @param end the upper bound of the range of device profile rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfileRule> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeviceProfileRule> list = (List<DeviceProfileRule>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DEVICEPROFILERULE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DEVICEPROFILERULE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DeviceProfileRule>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DeviceProfileRule>)QueryUtil.list(q,
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
	 * Removes all the device profile rules where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DeviceProfileRule deviceProfileRule : findByUuid(uuid)) {
			deviceProfileRulePersistence.remove(deviceProfileRule);
		}
	}

	/**
	 * Removes all the device profile rules where deviceProfileId = &#63; from the database.
	 *
	 * @param deviceProfileId the device profile ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByDeviceProfileId(long deviceProfileId)
		throws SystemException {
		for (DeviceProfileRule deviceProfileRule : findByDeviceProfileId(
				deviceProfileId)) {
			deviceProfileRulePersistence.remove(deviceProfileRule);
		}
	}

	/**
	 * Removes all the device profile rules from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DeviceProfileRule deviceProfileRule : findAll()) {
			deviceProfileRulePersistence.remove(deviceProfileRule);
		}
	}

	/**
	 * Returns the number of device profile rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEVICEPROFILERULE_WHERE);

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
	 * Returns the number of device profile rules where deviceProfileId = &#63;.
	 *
	 * @param deviceProfileId the device profile ID
	 * @return the number of matching device profile rules
	 * @throws SystemException if a system exception occurred
	 */
	public int countByDeviceProfileId(long deviceProfileId)
		throws SystemException {
		Object[] finderArgs = new Object[] { deviceProfileId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DEVICEPROFILEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEVICEPROFILERULE_WHERE);

			query.append(_FINDER_COLUMN_DEVICEPROFILEID_DEVICEPROFILEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(deviceProfileId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DEVICEPROFILEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of device profile rules.
	 *
	 * @return the number of device profile rules
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

				Query q = session.createQuery(_SQL_COUNT_DEVICEPROFILERULE);

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
	 * Returns all the device profile actions associated with the device profile rule.
	 *
	 * @param pk the primary key of the device profile rule
	 * @return the device profile actions associated with the device profile rule
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.mobile.model.DeviceProfileAction> getDeviceProfileActions(
		long pk) throws SystemException {
		return getDeviceProfileActions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the device profile actions associated with the device profile rule.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the device profile rule
	 * @param start the lower bound of the range of device profile rules
	 * @param end the upper bound of the range of device profile rules (not inclusive)
	 * @return the range of device profile actions associated with the device profile rule
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.mobile.model.DeviceProfileAction> getDeviceProfileActions(
		long pk, int start, int end) throws SystemException {
		return getDeviceProfileActions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_DEVICEPROFILEACTIONS = new FinderPath(com.liferay.portal.mobile.model.impl.DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			com.liferay.portal.mobile.model.impl.DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED,
			com.liferay.portal.mobile.model.impl.DeviceProfileActionImpl.class,
			com.liferay.portal.mobile.service.persistence.DeviceProfileActionPersistenceImpl.FINDER_CLASS_NAME_LIST,
			"getDeviceProfileActions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	/**
	 * Returns an ordered range of all the device profile actions associated with the device profile rule.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the device profile rule
	 * @param start the lower bound of the range of device profile rules
	 * @param end the upper bound of the range of device profile rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of device profile actions associated with the device profile rule
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.mobile.model.DeviceProfileAction> getDeviceProfileActions(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				pk, String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portal.mobile.model.DeviceProfileAction> list = (List<com.liferay.portal.mobile.model.DeviceProfileAction>)FinderCacheUtil.getResult(FINDER_PATH_GET_DEVICEPROFILEACTIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETDEVICEPROFILEACTIONS.concat(ORDER_BY_CLAUSE)
													  .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETDEVICEPROFILEACTIONS;
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("DeviceProfileAction",
					com.liferay.portal.mobile.model.impl.DeviceProfileActionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.mobile.model.DeviceProfileAction>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_DEVICEPROFILEACTIONS,
						finderArgs);
				}
				else {
					deviceProfileActionPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_DEVICEPROFILEACTIONS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_DEVICEPROFILEACTIONS_SIZE = new FinderPath(com.liferay.portal.mobile.model.impl.DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			com.liferay.portal.mobile.model.impl.DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED,
			com.liferay.portal.mobile.model.impl.DeviceProfileActionImpl.class,
			com.liferay.portal.mobile.service.persistence.DeviceProfileActionPersistenceImpl.FINDER_CLASS_NAME_LIST,
			"getDeviceProfileActionsSize", new String[] { Long.class.getName() });

	/**
	 * Returns the number of device profile actions associated with the device profile rule.
	 *
	 * @param pk the primary key of the device profile rule
	 * @return the number of device profile actions associated with the device profile rule
	 * @throws SystemException if a system exception occurred
	 */
	public int getDeviceProfileActionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_DEVICEPROFILEACTIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETDEVICEPROFILEACTIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_DEVICEPROFILEACTIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_DEVICEPROFILEACTION = new FinderPath(com.liferay.portal.mobile.model.impl.DeviceProfileActionModelImpl.ENTITY_CACHE_ENABLED,
			com.liferay.portal.mobile.model.impl.DeviceProfileActionModelImpl.FINDER_CACHE_ENABLED,
			com.liferay.portal.mobile.model.impl.DeviceProfileActionImpl.class,
			com.liferay.portal.mobile.service.persistence.DeviceProfileActionPersistenceImpl.FINDER_CLASS_NAME_LIST,
			"containsDeviceProfileAction",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the device profile action is associated with the device profile rule.
	 *
	 * @param pk the primary key of the device profile rule
	 * @param deviceProfileActionPK the primary key of the device profile action
	 * @return <code>true</code> if the device profile action is associated with the device profile rule; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDeviceProfileAction(long pk,
		long deviceProfileActionPK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, deviceProfileActionPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_DEVICEPROFILEACTION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsDeviceProfileAction.contains(
							pk, deviceProfileActionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_DEVICEPROFILEACTION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the device profile rule has any device profile actions associated with it.
	 *
	 * @param pk the primary key of the device profile rule to check for associations with device profile actions
	 * @return <code>true</code> if the device profile rule has any device profile actions associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsDeviceProfileActions(long pk)
		throws SystemException {
		if (getDeviceProfileActionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Initializes the device profile rule persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.mobile.model.DeviceProfileRule")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DeviceProfileRule>> listenersList = new ArrayList<ModelListener<DeviceProfileRule>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DeviceProfileRule>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsDeviceProfileAction = new ContainsDeviceProfileAction(this);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DeviceProfileRuleImpl.class.getName());
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
	protected ContainsDeviceProfileAction containsDeviceProfileAction;

	protected class ContainsDeviceProfileAction {
		protected ContainsDeviceProfileAction(
			DeviceProfileRulePersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSDEVICEPROFILEACTION,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long deviceProfileRuleId,
			long deviceProfileActionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(deviceProfileRuleId),
						new Long(deviceProfileActionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	private static final String _SQL_SELECT_DEVICEPROFILERULE = "SELECT deviceProfileRule FROM DeviceProfileRule deviceProfileRule";
	private static final String _SQL_SELECT_DEVICEPROFILERULE_WHERE = "SELECT deviceProfileRule FROM DeviceProfileRule deviceProfileRule WHERE ";
	private static final String _SQL_COUNT_DEVICEPROFILERULE = "SELECT COUNT(deviceProfileRule) FROM DeviceProfileRule deviceProfileRule";
	private static final String _SQL_COUNT_DEVICEPROFILERULE_WHERE = "SELECT COUNT(deviceProfileRule) FROM DeviceProfileRule deviceProfileRule WHERE ";
	private static final String _SQL_GETDEVICEPROFILEACTIONS = "SELECT {DeviceProfileAction.*} FROM DeviceProfileAction INNER JOIN DeviceProfileRule ON (DeviceProfileRule.deviceProfileRuleId = DeviceProfileAction.deviceProfileRuleId) WHERE (DeviceProfileRule.deviceProfileRuleId = ?)";
	private static final String _SQL_GETDEVICEPROFILEACTIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM DeviceProfileAction WHERE deviceProfileRuleId = ?";
	private static final String _SQL_CONTAINSDEVICEPROFILEACTION = "SELECT COUNT(*) AS COUNT_VALUE FROM DeviceProfileAction WHERE deviceProfileRuleId = ? AND deviceProfileActionId = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "deviceProfileRule.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "deviceProfileRule.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(deviceProfileRule.uuid IS NULL OR deviceProfileRule.uuid = ?)";
	private static final String _FINDER_COLUMN_DEVICEPROFILEID_DEVICEPROFILEID_2 =
		"deviceProfileRule.deviceProfileId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "deviceProfileRule.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DeviceProfileRule exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DeviceProfileRule exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DeviceProfileRulePersistenceImpl.class);
	private static DeviceProfileRule _nullDeviceProfileRule = new DeviceProfileRuleImpl() {
			public Object clone() {
				return this;
			}

			public CacheModel<DeviceProfileRule> toCacheModel() {
				return _nullDeviceProfileRuleCacheModel;
			}
		};

	private static CacheModel<DeviceProfileRule> _nullDeviceProfileRuleCacheModel =
		new CacheModel<DeviceProfileRule>() {
			public DeviceProfileRule toEntityModel() {
				return _nullDeviceProfileRule;
			}
		};
}