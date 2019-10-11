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

import com.liferay.mobile.device.rules.exception.NoSuchActionException;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mdr action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRActionUtil
 * @generated
 */
@ProviderType
public interface MDRActionPersistence extends BasePersistence<MDRAction> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRActionUtil} to access the mdr action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the mdr actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching mdr actions
	 */
	public java.util.List<MDRAction> findByUuid(String uuid);

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
	public java.util.List<MDRAction> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<MDRAction> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

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
	public java.util.List<MDRAction> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public MDRAction findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public MDRAction fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public MDRAction findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public MDRAction fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

	/**
	 * Returns the mdr actions before and after the current mdr action in the ordered set where uuid = &#63;.
	 *
	 * @param actionId the primary key of the current mdr action
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public MDRAction[] findByUuid_PrevAndNext(
			long actionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Removes all the mdr actions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of mdr actions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching mdr actions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchActionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public MDRAction findByUUID_G(String uuid, long groupId)
		throws NoSuchActionException;

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public MDRAction fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the mdr action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public MDRAction fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the mdr action where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the mdr action that was removed
	 */
	public MDRAction removeByUUID_G(String uuid, long groupId)
		throws NoSuchActionException;

	/**
	 * Returns the number of mdr actions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching mdr actions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching mdr actions
	 */
	public java.util.List<MDRAction> findByUuid_C(String uuid, long companyId);

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
	public java.util.List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

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
	public java.util.List<MDRAction> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public MDRAction findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Returns the first mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public MDRAction fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public MDRAction findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Returns the last mdr action in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public MDRAction fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

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
	public MDRAction[] findByUuid_C_PrevAndNext(
			long actionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Removes all the mdr actions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of mdr actions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching mdr actions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @return the matching mdr actions
	 */
	public java.util.List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId);

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
	public java.util.List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end);

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
	public java.util.List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

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
	public java.util.List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public MDRAction findByRuleGroupInstanceId_First(
			long ruleGroupInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Returns the first mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public MDRAction fetchByRuleGroupInstanceId_First(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

	/**
	 * Returns the last mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action
	 * @throws NoSuchActionException if a matching mdr action could not be found
	 */
	public MDRAction findByRuleGroupInstanceId_Last(
			long ruleGroupInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Returns the last mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr action, or <code>null</code> if a matching mdr action could not be found
	 */
	public MDRAction fetchByRuleGroupInstanceId_Last(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

	/**
	 * Returns the mdr actions before and after the current mdr action in the ordered set where ruleGroupInstanceId = &#63;.
	 *
	 * @param actionId the primary key of the current mdr action
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public MDRAction[] findByRuleGroupInstanceId_PrevAndNext(
			long actionId, long ruleGroupInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
				orderByComparator)
		throws NoSuchActionException;

	/**
	 * Removes all the mdr actions where ruleGroupInstanceId = &#63; from the database.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 */
	public void removeByRuleGroupInstanceId(long ruleGroupInstanceId);

	/**
	 * Returns the number of mdr actions where ruleGroupInstanceId = &#63;.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID
	 * @return the number of matching mdr actions
	 */
	public int countByRuleGroupInstanceId(long ruleGroupInstanceId);

	/**
	 * Caches the mdr action in the entity cache if it is enabled.
	 *
	 * @param mdrAction the mdr action
	 */
	public void cacheResult(MDRAction mdrAction);

	/**
	 * Caches the mdr actions in the entity cache if it is enabled.
	 *
	 * @param mdrActions the mdr actions
	 */
	public void cacheResult(java.util.List<MDRAction> mdrActions);

	/**
	 * Creates a new mdr action with the primary key. Does not add the mdr action to the database.
	 *
	 * @param actionId the primary key for the new mdr action
	 * @return the new mdr action
	 */
	public MDRAction create(long actionId);

	/**
	 * Removes the mdr action with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action that was removed
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public MDRAction remove(long actionId) throws NoSuchActionException;

	public MDRAction updateImpl(MDRAction mdrAction);

	/**
	 * Returns the mdr action with the primary key or throws a <code>NoSuchActionException</code> if it could not be found.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action
	 * @throws NoSuchActionException if a mdr action with the primary key could not be found
	 */
	public MDRAction findByPrimaryKey(long actionId)
		throws NoSuchActionException;

	/**
	 * Returns the mdr action with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param actionId the primary key of the mdr action
	 * @return the mdr action, or <code>null</code> if a mdr action with the primary key could not be found
	 */
	public MDRAction fetchByPrimaryKey(long actionId);

	/**
	 * Returns all the mdr actions.
	 *
	 * @return the mdr actions
	 */
	public java.util.List<MDRAction> findAll();

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
	public java.util.List<MDRAction> findAll(int start, int end);

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
	public java.util.List<MDRAction> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator);

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
	public java.util.List<MDRAction> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mdr actions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of mdr actions.
	 *
	 * @return the number of mdr actions
	 */
	public int countAll();

}