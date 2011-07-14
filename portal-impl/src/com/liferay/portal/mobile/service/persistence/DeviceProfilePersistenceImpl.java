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
import com.liferay.portal.mobile.NoSuchProfileException;
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.mobile.model.impl.DeviceProfileImpl;
import com.liferay.portal.mobile.model.impl.DeviceProfileModelImpl;
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
 * The persistence implementation for the device profile service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see DeviceProfilePersistence
 * @see DeviceProfileUtil
 * @generated
 */
public class DeviceProfilePersistenceImpl extends BasePersistenceImpl<DeviceProfile>
	implements DeviceProfilePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DeviceProfileUtil} to access the device profile persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DeviceProfileImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileModelImpl.FINDER_CACHE_ENABLED,
			DeviceProfileImpl.class, FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileModelImpl.FINDER_CACHE_ENABLED,
			DeviceProfileImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the device profile in the entity cache if it is enabled.
	 *
	 * @param deviceProfile the device profile
	 */
	public void cacheResult(DeviceProfile deviceProfile) {
		EntityCacheUtil.putResult(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileImpl.class, deviceProfile.getPrimaryKey(),
			deviceProfile);

		deviceProfile.resetOriginalValues();
	}

	/**
	 * Caches the device profiles in the entity cache if it is enabled.
	 *
	 * @param deviceProfiles the device profiles
	 */
	public void cacheResult(List<DeviceProfile> deviceProfiles) {
		for (DeviceProfile deviceProfile : deviceProfiles) {
			if (EntityCacheUtil.getResult(
						DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
						DeviceProfileImpl.class, deviceProfile.getPrimaryKey(),
						this) == null) {
				cacheResult(deviceProfile);
			}
		}
	}

	/**
	 * Clears the cache for all device profiles.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DeviceProfileImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DeviceProfileImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the device profile.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DeviceProfile deviceProfile) {
		EntityCacheUtil.removeResult(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileImpl.class, deviceProfile.getPrimaryKey());
	}

	/**
	 * Creates a new device profile with the primary key. Does not add the device profile to the database.
	 *
	 * @param deviceProfileId the primary key for the new device profile
	 * @return the new device profile
	 */
	public DeviceProfile create(long deviceProfileId) {
		DeviceProfile deviceProfile = new DeviceProfileImpl();

		deviceProfile.setNew(true);
		deviceProfile.setPrimaryKey(deviceProfileId);

		String uuid = PortalUUIDUtil.generate();

		deviceProfile.setUuid(uuid);

		return deviceProfile;
	}

	/**
	 * Removes the device profile with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the device profile
	 * @return the device profile that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a device profile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfile remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the device profile with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deviceProfileId the primary key of the device profile
	 * @return the device profile that was removed
	 * @throws com.liferay.portal.mobile.NoSuchProfileException if a device profile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfile remove(long deviceProfileId)
		throws NoSuchProfileException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DeviceProfile deviceProfile = (DeviceProfile)session.get(DeviceProfileImpl.class,
					Long.valueOf(deviceProfileId));

			if (deviceProfile == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						deviceProfileId);
				}

				throw new NoSuchProfileException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					deviceProfileId);
			}

			return deviceProfilePersistence.remove(deviceProfile);
		}
		catch (NoSuchProfileException nsee) {
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
	 * Removes the device profile from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deviceProfile the device profile
	 * @return the device profile that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfile remove(DeviceProfile deviceProfile)
		throws SystemException {
		return super.remove(deviceProfile);
	}

	@Override
	protected DeviceProfile removeImpl(DeviceProfile deviceProfile)
		throws SystemException {
		deviceProfile = toUnwrappedModel(deviceProfile);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, deviceProfile);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileImpl.class, deviceProfile.getPrimaryKey());

		return deviceProfile;
	}

	@Override
	public DeviceProfile updateImpl(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile,
		boolean merge) throws SystemException {
		deviceProfile = toUnwrappedModel(deviceProfile);

		if (Validator.isNull(deviceProfile.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			deviceProfile.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, deviceProfile, merge);

			deviceProfile.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
			DeviceProfileImpl.class, deviceProfile.getPrimaryKey(),
			deviceProfile);

		return deviceProfile;
	}

	protected DeviceProfile toUnwrappedModel(DeviceProfile deviceProfile) {
		if (deviceProfile instanceof DeviceProfileImpl) {
			return deviceProfile;
		}

		DeviceProfileImpl deviceProfileImpl = new DeviceProfileImpl();

		deviceProfileImpl.setNew(deviceProfile.isNew());
		deviceProfileImpl.setPrimaryKey(deviceProfile.getPrimaryKey());

		deviceProfileImpl.setUuid(deviceProfile.getUuid());
		deviceProfileImpl.setDeviceProfileId(deviceProfile.getDeviceProfileId());
		deviceProfileImpl.setName(deviceProfile.getName());
		deviceProfileImpl.setDescription(deviceProfile.getDescription());

		return deviceProfileImpl;
	}

	/**
	 * Returns the device profile with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the device profile
	 * @return the device profile
	 * @throws com.liferay.portal.NoSuchModelException if a device profile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfile findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the device profile with the primary key or throws a {@link com.liferay.portal.mobile.NoSuchProfileException} if it could not be found.
	 *
	 * @param deviceProfileId the primary key of the device profile
	 * @return the device profile
	 * @throws com.liferay.portal.mobile.NoSuchProfileException if a device profile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfile findByPrimaryKey(long deviceProfileId)
		throws NoSuchProfileException, SystemException {
		DeviceProfile deviceProfile = fetchByPrimaryKey(deviceProfileId);

		if (deviceProfile == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + deviceProfileId);
			}

			throw new NoSuchProfileException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				deviceProfileId);
		}

		return deviceProfile;
	}

	/**
	 * Returns the device profile with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the device profile
	 * @return the device profile, or <code>null</code> if a device profile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DeviceProfile fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the device profile with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deviceProfileId the primary key of the device profile
	 * @return the device profile, or <code>null</code> if a device profile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfile fetchByPrimaryKey(long deviceProfileId)
		throws SystemException {
		DeviceProfile deviceProfile = (DeviceProfile)EntityCacheUtil.getResult(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
				DeviceProfileImpl.class, deviceProfileId, this);

		if (deviceProfile == _nullDeviceProfile) {
			return null;
		}

		if (deviceProfile == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				deviceProfile = (DeviceProfile)session.get(DeviceProfileImpl.class,
						Long.valueOf(deviceProfileId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (deviceProfile != null) {
					cacheResult(deviceProfile);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DeviceProfileModelImpl.ENTITY_CACHE_ENABLED,
						DeviceProfileImpl.class, deviceProfileId,
						_nullDeviceProfile);
				}

				closeSession(session);
			}
		}

		return deviceProfile;
	}

	/**
	 * Returns all the device profiles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching device profiles
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfile> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the device profiles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of device profiles
	 * @param end the upper bound of the range of device profiles (not inclusive)
	 * @return the range of matching device profiles
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfile> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the device profiles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of device profiles
	 * @param end the upper bound of the range of device profiles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching device profiles
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfile> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeviceProfile> list = (List<DeviceProfile>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

			query.append(_SQL_SELECT_DEVICEPROFILE_WHERE);

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

				list = (List<DeviceProfile>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first device profile in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching device profile
	 * @throws com.liferay.portal.mobile.NoSuchProfileException if a matching device profile could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfile findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchProfileException, SystemException {
		List<DeviceProfile> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last device profile in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching device profile
	 * @throws com.liferay.portal.mobile.NoSuchProfileException if a matching device profile could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfile findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchProfileException, SystemException {
		int count = countByUuid(uuid);

		List<DeviceProfile> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProfileException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the device profiles before and after the current device profile in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param deviceProfileId the primary key of the current device profile
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next device profile
	 * @throws com.liferay.portal.mobile.NoSuchProfileException if a device profile with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DeviceProfile[] findByUuid_PrevAndNext(long deviceProfileId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchProfileException, SystemException {
		DeviceProfile deviceProfile = findByPrimaryKey(deviceProfileId);

		Session session = null;

		try {
			session = openSession();

			DeviceProfile[] array = new DeviceProfileImpl[3];

			array[0] = getByUuid_PrevAndNext(session, deviceProfile, uuid,
					orderByComparator, true);

			array[1] = deviceProfile;

			array[2] = getByUuid_PrevAndNext(session, deviceProfile, uuid,
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

	protected DeviceProfile getByUuid_PrevAndNext(Session session,
		DeviceProfile deviceProfile, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DEVICEPROFILE_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(deviceProfile);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DeviceProfile> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the device profiles.
	 *
	 * @return the device profiles
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfile> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the device profiles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of device profiles
	 * @param end the upper bound of the range of device profiles (not inclusive)
	 * @return the range of device profiles
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfile> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the device profiles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of device profiles
	 * @param end the upper bound of the range of device profiles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of device profiles
	 * @throws SystemException if a system exception occurred
	 */
	public List<DeviceProfile> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DeviceProfile> list = (List<DeviceProfile>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DEVICEPROFILE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DEVICEPROFILE;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DeviceProfile>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DeviceProfile>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the device profiles where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DeviceProfile deviceProfile : findByUuid(uuid)) {
			deviceProfilePersistence.remove(deviceProfile);
		}
	}

	/**
	 * Removes all the device profiles from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DeviceProfile deviceProfile : findAll()) {
			deviceProfilePersistence.remove(deviceProfile);
		}
	}

	/**
	 * Returns the number of device profiles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching device profiles
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEVICEPROFILE_WHERE);

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
	 * Returns the number of device profiles.
	 *
	 * @return the number of device profiles
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

				Query q = session.createQuery(_SQL_COUNT_DEVICEPROFILE);

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
	 * Initializes the device profile persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.mobile.model.DeviceProfile")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DeviceProfile>> listenersList = new ArrayList<ModelListener<DeviceProfile>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DeviceProfile>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DeviceProfileImpl.class.getName());
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
	private static final String _SQL_SELECT_DEVICEPROFILE = "SELECT deviceProfile FROM DeviceProfile deviceProfile";
	private static final String _SQL_SELECT_DEVICEPROFILE_WHERE = "SELECT deviceProfile FROM DeviceProfile deviceProfile WHERE ";
	private static final String _SQL_COUNT_DEVICEPROFILE = "SELECT COUNT(deviceProfile) FROM DeviceProfile deviceProfile";
	private static final String _SQL_COUNT_DEVICEPROFILE_WHERE = "SELECT COUNT(deviceProfile) FROM DeviceProfile deviceProfile WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "deviceProfile.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "deviceProfile.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(deviceProfile.uuid IS NULL OR deviceProfile.uuid = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "deviceProfile.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DeviceProfile exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DeviceProfile exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DeviceProfilePersistenceImpl.class);
	private static DeviceProfile _nullDeviceProfile = new DeviceProfileImpl() {
			public Object clone() {
				return this;
			}

			public CacheModel<DeviceProfile> toCacheModel() {
				return _nullDeviceProfileCacheModel;
			}
		};

	private static CacheModel<DeviceProfile> _nullDeviceProfileCacheModel = new CacheModel<DeviceProfile>() {
			public DeviceProfile toEntityModel() {
				return _nullDeviceProfile;
			}
		};
}