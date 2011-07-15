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
import com.liferay.portal.mobile.model.DeviceProfileRule;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the device profile rule service. This utility wraps {@link DeviceProfileRulePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see DeviceProfileRulePersistence
 * @see DeviceProfileRulePersistenceImpl
 * @generated
 */
public class DeviceProfileRuleUtil {
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
	public static void clearCache(DeviceProfileRule deviceProfileRule) {
		getPersistence().clearCache(deviceProfileRule);
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
	public static List<DeviceProfileRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DeviceProfileRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DeviceProfileRule> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DeviceProfileRule remove(DeviceProfileRule deviceProfileRule)
		throws SystemException {
		return getPersistence().remove(deviceProfileRule);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DeviceProfileRule update(
		DeviceProfileRule deviceProfileRule, boolean merge)
		throws SystemException {
		return getPersistence().update(deviceProfileRule, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DeviceProfileRule update(
		DeviceProfileRule deviceProfileRule, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(deviceProfileRule, merge, serviceContext);
	}

	/**
	* Caches the device profile rule in the entity cache if it is enabled.
	*
	* @param deviceProfileRule the device profile rule
	*/
	public static void cacheResult(
		com.liferay.portal.mobile.model.DeviceProfileRule deviceProfileRule) {
		getPersistence().cacheResult(deviceProfileRule);
	}

	/**
	* Caches the device profile rules in the entity cache if it is enabled.
	*
	* @param deviceProfileRules the device profile rules
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> deviceProfileRules) {
		getPersistence().cacheResult(deviceProfileRules);
	}

	/**
	* Creates a new device profile rule with the primary key. Does not add the device profile rule to the database.
	*
	* @param deviceProfileRuleId the primary key for the new device profile rule
	* @return the new device profile rule
	*/
	public static com.liferay.portal.mobile.model.DeviceProfileRule create(
		long deviceProfileRuleId) {
		return getPersistence().create(deviceProfileRuleId);
	}

	/**
	* Removes the device profile rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileRuleId the primary key of the device profile rule
	* @return the device profile rule that was removed
	* @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a device profile rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.mobile.model.DeviceProfileRule remove(
		long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileRuleException {
		return getPersistence().remove(deviceProfileRuleId);
	}

	public static com.liferay.portal.mobile.model.DeviceProfileRule updateImpl(
		com.liferay.portal.mobile.model.DeviceProfileRule deviceProfileRule,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(deviceProfileRule, merge);
	}

	/**
	* Returns the device profile rule with the primary key or throws a {@link com.liferay.portal.mobile.NoSuchProfileRuleException} if it could not be found.
	*
	* @param deviceProfileRuleId the primary key of the device profile rule
	* @return the device profile rule
	* @throws com.liferay.portal.mobile.NoSuchProfileRuleException if a device profile rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.mobile.model.DeviceProfileRule findByPrimaryKey(
		long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileRuleException {
		return getPersistence().findByPrimaryKey(deviceProfileRuleId);
	}

	/**
	* Returns the device profile rule with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param deviceProfileRuleId the primary key of the device profile rule
	* @return the device profile rule, or <code>null</code> if a device profile rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.mobile.model.DeviceProfileRule fetchByPrimaryKey(
		long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(deviceProfileRuleId);
	}

	/**
	* Returns all the device profile rules where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching device profile rules
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfileRule findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileRuleException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfileRule findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileRuleException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfileRule[] findByUuid_PrevAndNext(
		long deviceProfileRuleId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileRuleException {
		return getPersistence()
				   .findByUuid_PrevAndNext(deviceProfileRuleId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the device profile rules where deviceProfileId = &#63;.
	*
	* @param deviceProfileId the device profile ID
	* @return the matching device profile rules
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findByDeviceProfileId(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByDeviceProfileId(deviceProfileId);
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findByDeviceProfileId(
		long deviceProfileId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByDeviceProfileId(deviceProfileId, start, end);
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findByDeviceProfileId(
		long deviceProfileId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByDeviceProfileId(deviceProfileId, start, end,
			orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfileRule findByDeviceProfileId_First(
		long deviceProfileId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileRuleException {
		return getPersistence()
				   .findByDeviceProfileId_First(deviceProfileId,
			orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfileRule findByDeviceProfileId_Last(
		long deviceProfileId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileRuleException {
		return getPersistence()
				   .findByDeviceProfileId_Last(deviceProfileId,
			orderByComparator);
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
	public static com.liferay.portal.mobile.model.DeviceProfileRule[] findByDeviceProfileId_PrevAndNext(
		long deviceProfileRuleId, long deviceProfileId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.mobile.NoSuchProfileRuleException {
		return getPersistence()
				   .findByDeviceProfileId_PrevAndNext(deviceProfileRuleId,
			deviceProfileId, orderByComparator);
	}

	/**
	* Returns all the device profile rules.
	*
	* @return the device profile rules
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
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
	public static java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the device profile rules where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the device profile rules where deviceProfileId = &#63; from the database.
	*
	* @param deviceProfileId the device profile ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByDeviceProfileId(long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByDeviceProfileId(deviceProfileId);
	}

	/**
	* Removes all the device profile rules from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of device profile rules where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching device profile rules
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of device profile rules where deviceProfileId = &#63;.
	*
	* @param deviceProfileId the device profile ID
	* @return the number of matching device profile rules
	* @throws SystemException if a system exception occurred
	*/
	public static int countByDeviceProfileId(long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByDeviceProfileId(deviceProfileId);
	}

	/**
	* Returns the number of device profile rules.
	*
	* @return the number of device profile rules
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DeviceProfileRulePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DeviceProfileRulePersistence)PortalBeanLocatorUtil.locate(DeviceProfileRulePersistence.class.getName());

			ReferenceRegistry.registerReference(DeviceProfileRuleUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DeviceProfileRulePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DeviceProfileRuleUtil.class,
			"_persistence");
	}

	private static DeviceProfileRulePersistence _persistence;
}