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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the device profile service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see DeviceProfilePersistenceImpl
 * @see DeviceProfileUtil
 * @generated
 */
public interface DeviceProfilePersistence extends BasePersistence<DeviceProfile> {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DeviceProfileUtil} to access the device profile persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the device profile in the entity cache if it is enabled.
	*
	* @param deviceProfile the device profile
	*/
	public void cacheResult(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile);

	/**
	* Caches the device profiles in the entity cache if it is enabled.
	*
	* @param deviceProfiles the device profiles
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.mobile.model.DeviceProfile> deviceProfiles);

	/**
	* Creates a new device profile with the primary key. Does not add the device profile to the database.
	*
	* @param deviceProfileId the primary key for the new device profile
	* @return the new device profile
	*/
	public com.liferay.portal.mobile.model.DeviceProfile create(
		long deviceProfileId);

	/**
	* Removes the device profile with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileId the primary key of the device profile
	* @return the device profile that was removed
	* @throws com.liferay.portal.mobile.NoSuchProfileException if a device profile with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfile remove(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException;

	public com.liferay.portal.mobile.model.DeviceProfile updateImpl(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the device profile with the primary key or throws a {@link com.liferay.portal.mobile.NoSuchProfileException} if it could not be found.
	*
	* @param deviceProfileId the primary key of the device profile
	* @return the device profile
	* @throws com.liferay.portal.mobile.NoSuchProfileException if a device profile with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfile findByPrimaryKey(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException;

	/**
	* Returns the device profile with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param deviceProfileId the primary key of the device profile
	* @return the device profile, or <code>null</code> if a device profile with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfile fetchByPrimaryKey(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the device profiles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching device profiles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.mobile.model.DeviceProfile findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException;

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
	public com.liferay.portal.mobile.model.DeviceProfile findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException;

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
	public com.liferay.portal.mobile.model.DeviceProfile[] findByUuid_PrevAndNext(
		long deviceProfileId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileException;

	/**
	* Returns all the device profiles.
	*
	* @return the device profiles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfile> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the device profiles where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the device profiles from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of device profiles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching device profiles
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of device profiles.
	*
	* @return the number of device profiles
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DeviceProfile remove(DeviceProfile deviceProfile)
		throws SystemException;
}