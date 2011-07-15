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
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the device profile action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see DeviceProfileActionPersistenceImpl
 * @see DeviceProfileActionUtil
 * @generated
 */
public interface DeviceProfileActionPersistence extends BasePersistence<DeviceProfileAction> {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DeviceProfileActionUtil} to access the device profile action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the device profile action in the entity cache if it is enabled.
	*
	* @param deviceProfileAction the device profile action
	*/
	public void cacheResult(
		com.liferay.portal.mobile.model.DeviceProfileAction deviceProfileAction);

	/**
	* Caches the device profile actions in the entity cache if it is enabled.
	*
	* @param deviceProfileActions the device profile actions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> deviceProfileActions);

	/**
	* Creates a new device profile action with the primary key. Does not add the device profile action to the database.
	*
	* @param deviceProfileActionId the primary key for the new device profile action
	* @return the new device profile action
	*/
	public com.liferay.portal.mobile.model.DeviceProfileAction create(
		long deviceProfileActionId);

	/**
	* Removes the device profile action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileActionId the primary key of the device profile action
	* @return the device profile action that was removed
	* @throws com.liferay.portal.mobile.NoSuchProfileActionException if a device profile action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfileAction remove(
		long deviceProfileActionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileActionException;

	public com.liferay.portal.mobile.model.DeviceProfileAction updateImpl(
		com.liferay.portal.mobile.model.DeviceProfileAction deviceProfileAction,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the device profile action with the primary key or throws a {@link com.liferay.portal.mobile.NoSuchProfileActionException} if it could not be found.
	*
	* @param deviceProfileActionId the primary key of the device profile action
	* @return the device profile action
	* @throws com.liferay.portal.mobile.NoSuchProfileActionException if a device profile action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfileAction findByPrimaryKey(
		long deviceProfileActionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileActionException;

	/**
	* Returns the device profile action with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param deviceProfileActionId the primary key of the device profile action
	* @return the device profile action, or <code>null</code> if a device profile action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfileAction fetchByPrimaryKey(
		long deviceProfileActionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the device profile actions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching device profile actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.mobile.model.DeviceProfileAction findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileActionException;

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
	public com.liferay.portal.mobile.model.DeviceProfileAction findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileActionException;

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
	public com.liferay.portal.mobile.model.DeviceProfileAction[] findByUuid_PrevAndNext(
		long deviceProfileActionId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileActionException;

	/**
	* Returns all the device profile actions where deviceProfileRuleId = &#63;.
	*
	* @param deviceProfileRuleId the device profile rule ID
	* @return the matching device profile actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findByDeviceProfileRuleId(
		long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findByDeviceProfileRuleId(
		long deviceProfileRuleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findByDeviceProfileRuleId(
		long deviceProfileRuleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.mobile.model.DeviceProfileAction findByDeviceProfileRuleId_First(
		long deviceProfileRuleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileActionException;

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
	public com.liferay.portal.mobile.model.DeviceProfileAction findByDeviceProfileRuleId_Last(
		long deviceProfileRuleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileActionException;

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
	public com.liferay.portal.mobile.model.DeviceProfileAction[] findByDeviceProfileRuleId_PrevAndNext(
		long deviceProfileActionId, long deviceProfileRuleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileActionException;

	/**
	* Returns all the device profile actions.
	*
	* @return the device profile actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileAction> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the device profile actions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the device profile actions where deviceProfileRuleId = &#63; from the database.
	*
	* @param deviceProfileRuleId the device profile rule ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByDeviceProfileRuleId(long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the device profile actions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of device profile actions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching device profile actions
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of device profile actions where deviceProfileRuleId = &#63;.
	*
	* @param deviceProfileRuleId the device profile rule ID
	* @return the number of matching device profile actions
	* @throws SystemException if a system exception occurred
	*/
	public int countByDeviceProfileRuleId(long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of device profile actions.
	*
	* @return the number of device profile actions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DeviceProfileAction remove(DeviceProfileAction deviceProfileAction)
		throws SystemException;
}