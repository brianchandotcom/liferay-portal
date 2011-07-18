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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the device profile service. This utility wraps {@link DeviceProfilePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see DeviceProfilePersistence
 * @see DeviceProfilePersistenceImpl
 * @generated
 */
public class DeviceProfileUtil {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(DeviceProfile deviceProfile) {
		getPersistence().clearCache(deviceProfile);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DeviceProfile> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DeviceProfile> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DeviceProfile> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DeviceProfile remove(DeviceProfile deviceProfile)
		throws SystemException {
		return getPersistence().remove(deviceProfile);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DeviceProfile update(DeviceProfile deviceProfile,
		boolean merge) throws SystemException {
		return getPersistence().update(deviceProfile, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DeviceProfile update(DeviceProfile deviceProfile,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(deviceProfile, merge, serviceContext);
	}

	/**
	* Caches the device profile in the entity cache if it is enabled.
	*
	* @param deviceProfile the device profile
	*/
	public static void cacheResult(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile) {
		getPersistence().cacheResult(deviceProfile);
	}

	/**
	* Caches the device profiles in the entity cache if it is enabled.
	*
	* @param deviceProfiles the device profiles
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.mobile.model.DeviceProfile> deviceProfiles) {
		getPersistence().cacheResult(deviceProfiles);
	}

	/**
	* Creates a new device profile with the primary key. Does not add the device profile to the database.
	*
	* @param deviceProfileId the primary key for the new device profile
	* @return the new device profile
	*/
	public static com.liferay.portal.mobile.model.DeviceProfile create(
		long deviceProfileId) {
		return getPersistence().create(deviceProfileId);
	}

	/**
	* Removes the device profile with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileId the primary key of the device profile
	* @return the device profile that was removed
	* @throws com.liferay.portal.mobile.NoSuchProfileException if a device profile with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.mobile.model.DeviceProfile remove(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException {
		return getPersistence().remove(deviceProfileId);
	}

	public static com.liferay.portal.mobile.model.DeviceProfile updateImpl(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(deviceProfile, merge);
	}

	/**
	* Returns the device profile with the primary key or throws a {@link com.liferay.portal.mobile.NoSuchProfileException} if it could not be found.
	*
	* @param deviceProfileId the primary key of the device profile
	* @return the device profile
	* @throws com.liferay.portal.mobile.NoSuchProfileException if a device profile with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.mobile.model.DeviceProfile findByPrimaryKey(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException {
		return getPersistence().findByPrimaryKey(deviceProfileId);
	}

	/**
	* Returns the device profile with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param deviceProfileId the primary key of the device profile
	* @return the device profile, or <code>null</code> if a device profile with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.mobile.model.DeviceProfile fetchByPrimaryKey(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(deviceProfileId);
	}

	/**
	* Returns all the device profiles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching device profiles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfile findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfile findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfile[] findByUuid_PrevAndNext(
		long deviceProfileId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException {
		return getPersistence()
				   .findByUuid_PrevAndNext(deviceProfileId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the device profiles.
	*
	* @return the device profiles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the device profiles where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the device profiles from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of device profiles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching device profiles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of device profiles.
	*
	* @return the number of device profiles
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DeviceProfilePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DeviceProfilePersistence)PortalBeanLocatorUtil.locate(DeviceProfilePersistence.class.getName());

			ReferenceRegistry.registerReference(DeviceProfileUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DeviceProfilePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DeviceProfileUtil.class,
			"_persistence");
	}

	private static DeviceProfilePersistence _persistence;
}