/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.mobile.device.rules.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the m d r rule group instance service. This utility wraps {@link com.liferay.mobile.device.rules.service.persistence.impl.MDRRuleGroupInstancePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstancePersistence
 * @see com.liferay.mobile.device.rules.service.persistence.impl.MDRRuleGroupInstancePersistenceImpl
 * @generated
 */
@ProviderType
public class MDRRuleGroupInstanceUtil {
	/*
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
	public static void clearCache(MDRRuleGroupInstance mdrRuleGroupInstance) {
		getPersistence().clearCache(mdrRuleGroupInstance);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MDRRuleGroupInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MDRRuleGroupInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MDRRuleGroupInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MDRRuleGroupInstance update(
		MDRRuleGroupInstance mdrRuleGroupInstance) {
		return getPersistence().update(mdrRuleGroupInstance);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MDRRuleGroupInstance update(
		MDRRuleGroupInstance mdrRuleGroupInstance, ServiceContext serviceContext) {
		return getPersistence().update(mdrRuleGroupInstance, serviceContext);
	}

	/**
	* Returns all the m d r rule group instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the m d r rule group instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByUuid_First(java.lang.String uuid,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByUuid_Last(java.lang.String uuid,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance[] findByUuid_PrevAndNext(
		long ruleGroupInstanceId, java.lang.String uuid,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByUuid_PrevAndNext(ruleGroupInstanceId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of m d r rule group instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r rule group instances
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the m d r rule group instance where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r rule group instance that was removed
	*/
	public static MDRRuleGroupInstance removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of m d r rule group instances where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance[] findByUuid_C_PrevAndNext(
		long ruleGroupInstanceId, java.lang.String uuid, long companyId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(ruleGroupInstanceId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching m d r rule group instances
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the m d r rule group instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the m d r rule group instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByGroupId_First(long groupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByGroupId_First(long groupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByGroupId_Last(long groupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByGroupId_Last(long groupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance[] findByGroupId_PrevAndNext(
		long ruleGroupInstanceId, long groupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(ruleGroupInstanceId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching m d r rule group instances that the user has permission to view
	*/
	public static List<MDRRuleGroupInstance> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances that the user has permission to view
	*/
	public static List<MDRRuleGroupInstance> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances that the user has permission to view
	*/
	public static List<MDRRuleGroupInstance> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set of m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance[] filterFindByGroupId_PrevAndNext(
		long ruleGroupInstanceId, long groupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(ruleGroupInstanceId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of m d r rule group instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the m d r rule group instances where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByRuleGroupId(long ruleGroupId) {
		return getPersistence().findByRuleGroupId(ruleGroupId);
	}

	/**
	* Returns a range of all the m d r rule group instances where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end) {
		return getPersistence().findByRuleGroupId(ruleGroupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .findByRuleGroupId(ruleGroupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRuleGroupId(ruleGroupId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByRuleGroupId_First(
		long ruleGroupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByRuleGroupId_First(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByRuleGroupId_First(
		long ruleGroupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .fetchByRuleGroupId_First(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByRuleGroupId_Last(
		long ruleGroupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByRuleGroupId_Last(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByRuleGroupId_Last(
		long ruleGroupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .fetchByRuleGroupId_Last(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance[] findByRuleGroupId_PrevAndNext(
		long ruleGroupInstanceId, long ruleGroupId,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByRuleGroupId_PrevAndNext(ruleGroupInstanceId,
			ruleGroupId, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where ruleGroupId = &#63; from the database.
	*
	* @param ruleGroupId the rule group ID
	*/
	public static void removeByRuleGroupId(long ruleGroupId) {
		getPersistence().removeByRuleGroupId(ruleGroupId);
	}

	/**
	* Returns the number of m d r rule group instances where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the number of matching m d r rule group instances
	*/
	public static int countByRuleGroupId(long ruleGroupId) {
		return getPersistence().countByRuleGroupId(ruleGroupId);
	}

	/**
	* Returns all the m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByC_C(long classNameId,
		long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByC_C_First(long classNameId,
		long classPK, OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByC_C_Last(long classNameId,
		long classPK, OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance[] findByC_C_PrevAndNext(
		long ruleGroupInstanceId, long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByC_C_PrevAndNext(ruleGroupInstanceId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByG_C_C(long groupId,
		long classNameId, long classPK) {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByG_C_C_Last(long groupId,
		long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByG_C_C_Last(long groupId,
		long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance[] findByG_C_C_PrevAndNext(
		long ruleGroupInstanceId, long groupId, long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByG_C_C_PrevAndNext(ruleGroupInstanceId, groupId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Returns all the m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances that the user has permission to view
	*/
	public static List<MDRRuleGroupInstance> filterFindByG_C_C(long groupId,
		long classNameId, long classPK) {
		return getPersistence().filterFindByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances that the user has permission to view
	*/
	public static List<MDRRuleGroupInstance> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .filterFindByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances that the user has permission to view
	*/
	public static List<MDRRuleGroupInstance> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_C(groupId, classNameId, classPK, start,
			end, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set of m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance[] filterFindByG_C_C_PrevAndNext(
		long ruleGroupInstanceId, long groupId, long classNameId, long classPK,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .filterFindByG_C_C_PrevAndNext(ruleGroupInstanceId, groupId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByG_C_C(long groupId, long classNameId,
		long classPK) {
		getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK) {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances that the user has permission to view
	*/
	public static int filterCountByG_C_C(long groupId, long classNameId,
		long classPK) {
		return getPersistence().filterCountByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or throws a {@link com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance findByC_C_R(long classNameId,
		long classPK, long ruleGroupId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().findByC_C_R(classNameId, classPK, ruleGroupId);
	}

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByC_C_R(long classNameId,
		long classPK, long ruleGroupId) {
		return getPersistence().fetchByC_C_R(classNameId, classPK, ruleGroupId);
	}

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public static MDRRuleGroupInstance fetchByC_C_R(long classNameId,
		long classPK, long ruleGroupId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C_R(classNameId, classPK, ruleGroupId,
			retrieveFromCache);
	}

	/**
	* Removes the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the m d r rule group instance that was removed
	*/
	public static MDRRuleGroupInstance removeByC_C_R(long classNameId,
		long classPK, long ruleGroupId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().removeByC_C_R(classNameId, classPK, ruleGroupId);
	}

	/**
	* Returns the number of m d r rule group instances where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the number of matching m d r rule group instances
	*/
	public static int countByC_C_R(long classNameId, long classPK,
		long ruleGroupId) {
		return getPersistence().countByC_C_R(classNameId, classPK, ruleGroupId);
	}

	/**
	* Caches the m d r rule group instance in the entity cache if it is enabled.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	*/
	public static void cacheResult(MDRRuleGroupInstance mdrRuleGroupInstance) {
		getPersistence().cacheResult(mdrRuleGroupInstance);
	}

	/**
	* Caches the m d r rule group instances in the entity cache if it is enabled.
	*
	* @param mdrRuleGroupInstances the m d r rule group instances
	*/
	public static void cacheResult(
		List<MDRRuleGroupInstance> mdrRuleGroupInstances) {
		getPersistence().cacheResult(mdrRuleGroupInstances);
	}

	/**
	* Creates a new m d r rule group instance with the primary key. Does not add the m d r rule group instance to the database.
	*
	* @param ruleGroupInstanceId the primary key for the new m d r rule group instance
	* @return the new m d r rule group instance
	*/
	public static MDRRuleGroupInstance create(long ruleGroupInstanceId) {
		return getPersistence().create(ruleGroupInstanceId);
	}

	/**
	* Removes the m d r rule group instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance that was removed
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance remove(long ruleGroupInstanceId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().remove(ruleGroupInstanceId);
	}

	public static MDRRuleGroupInstance updateImpl(
		MDRRuleGroupInstance mdrRuleGroupInstance) {
		return getPersistence().updateImpl(mdrRuleGroupInstance);
	}

	/**
	* Returns the m d r rule group instance with the primary key or throws a {@link com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance
	* @throws com.liferay.mobile.device.rules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance findByPrimaryKey(
		long ruleGroupInstanceId)
		throws com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException {
		return getPersistence().findByPrimaryKey(ruleGroupInstanceId);
	}

	/**
	* Returns the m d r rule group instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance, or <code>null</code> if a m d r rule group instance with the primary key could not be found
	*/
	public static MDRRuleGroupInstance fetchByPrimaryKey(
		long ruleGroupInstanceId) {
		return getPersistence().fetchByPrimaryKey(ruleGroupInstanceId);
	}

	public static java.util.Map<java.io.Serializable, MDRRuleGroupInstance> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the m d r rule group instances.
	*
	* @return the m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the m d r rule group instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findAll(int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of m d r rule group instances
	*/
	public static List<MDRRuleGroupInstance> findAll(int start, int end,
		OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the m d r rule group instances from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of m d r rule group instances.
	*
	* @return the number of m d r rule group instances
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static MDRRuleGroupInstancePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(MDRRuleGroupInstancePersistence persistence) {
	}

	private static ServiceTracker<MDRRuleGroupInstancePersistence, MDRRuleGroupInstancePersistence> _serviceTracker =
		ServiceTrackerFactory.open(MDRRuleGroupInstancePersistence.class);
}