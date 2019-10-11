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

import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the mdr action service. This utility wraps <code>com.liferay.mobile.device.rules.service.persistence.impl.MDRActionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRActionPersistence
 * @generated
 */
public class MDRActionUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(MDRAction mdrAction) {
		getPersistence().clearCache(mdrAction);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, MDRAction> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MDRAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MDRAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MDRAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MDRAction update(MDRAction mdrAction) {
		return getPersistence().update(mdrAction);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MDRAction update(
		MDRAction mdrAction, ServiceContext serviceContext) {

		return getPersistence().update(mdrAction, serviceContext);
	}

	/**
	 * Returns all the mdr actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching mdr actions
	 */
	public static List<MDRAction> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the mdr actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @return the range of matching mdr actions
	 */
	public static List<MDRAction> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the mdr actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr actions
	 */
	public static List<MDRAction> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mdr actions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr actions
	 */
	public static List<MDRAction> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<MDRAction> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public static MDRAction findByUuid_First(
			String uuid, OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public static MDRAction fetchByUuid_First(
		String uuid, OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public static MDRAction findByUuid_Last(
			String uuid, OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public static MDRAction fetchByUuid_Last(
		String uuid, OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the mdr actions before and after the current mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param actionId the primary key of the current mdr action
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public static MDRAction[] findByUuid_PrevAndNext(
			long actionId, String uuid,
			OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByUuid_PrevAndNext(
			actionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the mdr actions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of mdr actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching mdr actions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchActionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public static MDRAction findByUUID_G(String uuid, long groupId)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public static MDRAction fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public static MDRAction fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the mdr action where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the mdr action that was removed
	 */
	public static MDRAction removeByUUID_G(String uuid, long groupId)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of mdr actions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching mdr actions
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching mdr actions
	 */
	public static List<MDRAction> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @return the range of matching mdr actions
	 */
	public static List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr actions
	 */
	public static List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr actions
	 */
	public static List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MDRAction> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public static MDRAction findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public static MDRAction fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public static MDRAction findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public static MDRAction fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the mdr actions before and after the current mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param actionId the primary key of the current mdr action
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public static MDRAction[] findByUuid_C_PrevAndNext(
			long actionId, String uuid, long companyId,
			OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			actionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the mdr actions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching mdr actions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @return the matching mdr actions
	 */
	public static List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId) {

		return getPersistence().findByRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	 * Returns a range of all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @return the range of matching mdr actions
	 */
	public static List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end) {

		return getPersistence().findByRuleGroupInstanceId(
			ruleGroupInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr actions
	 */
	public static List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().findByRuleGroupInstanceId(
			ruleGroupInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr actions
	 */
	public static List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		OrderByComparator<MDRAction> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRuleGroupInstanceId(
			ruleGroupInstanceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public static MDRAction findByRuleGroupInstanceId_First(
			long ruleGroupInstanceId,
			OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByRuleGroupInstanceId_First(
			ruleGroupInstanceId, orderByComparator);
	}

	/**
	 * Returns the first mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public static MDRAction fetchByRuleGroupInstanceId_First(
		long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().fetchByRuleGroupInstanceId_First(
			ruleGroupInstanceId, orderByComparator);
	}

	/**
	 * Returns the last mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public static MDRAction findByRuleGroupInstanceId_Last(
			long ruleGroupInstanceId,
			OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByRuleGroupInstanceId_Last(
			ruleGroupInstanceId, orderByComparator);
	}

	/**
	 * Returns the last mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public static MDRAction fetchByRuleGroupInstanceId_Last(
		long ruleGroupInstanceId,
		OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().fetchByRuleGroupInstanceId_Last(
			ruleGroupInstanceId, orderByComparator);
	}

	/**
	 * Returns the mdr actions before and after the current mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param actionId the primary key of the current mdr action
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public static MDRAction[] findByRuleGroupInstanceId_PrevAndNext(
			long actionId, long ruleGroupInstanceId,
			OrderByComparator<MDRAction> orderByComparator)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByRuleGroupInstanceId_PrevAndNext(
			actionId, ruleGroupInstanceId, orderByComparator);
	}

	/**
	 * Removes all the mdr actions where ruleGroupInstanceId = &#63; from the database.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 */
	public static void removeByRuleGroupInstanceId(long ruleGroupInstanceId) {
		getPersistence().removeByRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	 * Returns the number of mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @return the number of matching mdr actions
	 */
	public static int countByRuleGroupInstanceId(long ruleGroupInstanceId) {
		return getPersistence().countByRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	 * Caches the mdr action in the entity cache if it is enabled.
	 *
	 * @param mdrAction the mdr action
	 */
	public static void cacheResult(MDRAction mdrAction) {
		getPersistence().cacheResult(mdrAction);
	}

	/**
	 * Caches the mdr actions in the entity cache if it is enabled.
	 *
	 * @param mdrActions the mdr actions
	 */
	public static void cacheResult(List<MDRAction> mdrActions) {
		getPersistence().cacheResult(mdrActions);
	}

	/**
	 * Creates a new mdr action with the primary key. Does not add the mdr action to the database.
	 *
	 * @param actionId the primary key for the new mdr action
	 * @return the new mdr action
	 */
	public static MDRAction create(long actionId) {
		return getPersistence().create(actionId);
	}

	/**
	 * Removes the mdr action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action that was removed
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public static MDRAction remove(long actionId)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().remove(actionId);
	}

	public static MDRAction updateImpl(MDRAction mdrAction) {
		return getPersistence().updateImpl(mdrAction);
	}

	/**
	 * Returns the mdr action with the primary key or throws a <code>NoSuchActionException</code> if it could not be found.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public static MDRAction findByPrimaryKey(long actionId)
		throws com.liferay.mobile.device.rules.exception.NoSuchActionException {

		return getPersistence().findByPrimaryKey(actionId);
	}

	/**
	 * Returns the mdr action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action, or <code>null</code> if a mdr action with the primary key could not be found
	 */
	public static MDRAction fetchByPrimaryKey(long actionId) {
		return getPersistence().fetchByPrimaryKey(actionId);
	}

	/**
	 * Returns all the mdr actions.
	 *
	 * @return the mdr actions
	 */
	public static List<MDRAction> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the mdr actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @return the range of mdr actions
	 */
	public static List<MDRAction> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the mdr actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mdr actions
	 */
	public static List<MDRAction> findAll(
		int start, int end, OrderByComparator<MDRAction> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mdr actions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRActionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr actions
	 * @param end the upper bound of the range of mdr actions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mdr actions
	 */
	public static List<MDRAction> findAll(
		int start, int end, OrderByComparator<MDRAction> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the mdr actions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of mdr actions.
	 *
	 * @return the number of mdr actions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MDRActionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MDRActionPersistence, MDRActionPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MDRActionPersistence.class);

		ServiceTracker<MDRActionPersistence, MDRActionPersistence>
			serviceTracker =
				new ServiceTracker<MDRActionPersistence, MDRActionPersistence>(
					bundle.getBundleContext(), MDRActionPersistence.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}