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

import com.liferay.mobile.device.rules.exception.NoSuchActionException;
import com.liferay.mobile.device.rules.model.MDRAction;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the m d r action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see com.liferay.mobile.device.rules.service.persistence.impl.MDRActionPersistenceImpl
 * @see MDRActionUtil
 * @generated
 */
@ProviderType
public interface MDRActionPersistence extends BasePersistence<MDRAction> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRActionUtil} to access the m d r action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the m d r actions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r actions
	*/
	public java.util.List<MDRAction> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the m d r actions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the m d r actions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns an ordered range of all the m d r actions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action
	* @throws NoSuchActionException if a matching m d r action could not be found
	*/
	public MDRAction findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	public MDRAction fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action
	* @throws NoSuchActionException if a matching m d r action could not be found
	*/
	public MDRAction findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	public MDRAction fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns the m d r actions before and after the current m d r action in the ordered set where uuid = &#63;.
	*
	* @param actionId the primary key of the current m d r action
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r action
	* @throws NoSuchActionException if a m d r action with the primary key could not be found
	*/
	public MDRAction[] findByUuid_PrevAndNext(long actionId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Removes all the m d r actions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of m d r actions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r actions
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchActionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r action
	* @throws NoSuchActionException if a matching m d r action could not be found
	*/
	public MDRAction findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchActionException;

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	public MDRAction fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	public MDRAction fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the m d r action where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r action that was removed
	*/
	public MDRAction removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchActionException;

	/**
	* Returns the number of m d r actions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r actions
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching m d r actions
	*/
	public java.util.List<MDRAction> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns an ordered range of all the m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action
	* @throws NoSuchActionException if a matching m d r action could not be found
	*/
	public MDRAction findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	public MDRAction fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action
	* @throws NoSuchActionException if a matching m d r action could not be found
	*/
	public MDRAction findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	public MDRAction fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns the m d r actions before and after the current m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param actionId the primary key of the current m d r action
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r action
	* @throws NoSuchActionException if a m d r action with the primary key could not be found
	*/
	public MDRAction[] findByUuid_C_PrevAndNext(long actionId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Removes all the m d r actions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching m d r actions
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @return the matching m d r actions
	*/
	public java.util.List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId);

	/**
	* Returns a range of all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns an ordered range of all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching m d r actions
	*/
	public java.util.List<MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action
	* @throws NoSuchActionException if a matching m d r action could not be found
	*/
	public MDRAction findByRuleGroupInstanceId_First(long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Returns the first m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	public MDRAction fetchByRuleGroupInstanceId_First(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns the last m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action
	* @throws NoSuchActionException if a matching m d r action could not be found
	*/
	public MDRAction findByRuleGroupInstanceId_Last(long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Returns the last m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	public MDRAction fetchByRuleGroupInstanceId_Last(long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns the m d r actions before and after the current m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param actionId the primary key of the current m d r action
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r action
	* @throws NoSuchActionException if a m d r action with the primary key could not be found
	*/
	public MDRAction[] findByRuleGroupInstanceId_PrevAndNext(long actionId,
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator)
		throws NoSuchActionException;

	/**
	* Removes all the m d r actions where ruleGroupInstanceId = &#63; from the database.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	*/
	public void removeByRuleGroupInstanceId(long ruleGroupInstanceId);

	/**
	* Returns the number of m d r actions where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @return the number of matching m d r actions
	*/
	public int countByRuleGroupInstanceId(long ruleGroupInstanceId);

	/**
	* Caches the m d r action in the entity cache if it is enabled.
	*
	* @param mdrAction the m d r action
	*/
	public void cacheResult(MDRAction mdrAction);

	/**
	* Caches the m d r actions in the entity cache if it is enabled.
	*
	* @param mdrActions the m d r actions
	*/
	public void cacheResult(java.util.List<MDRAction> mdrActions);

	/**
	* Creates a new m d r action with the primary key. Does not add the m d r action to the database.
	*
	* @param actionId the primary key for the new m d r action
	* @return the new m d r action
	*/
	public MDRAction create(long actionId);

	/**
	* Removes the m d r action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action that was removed
	* @throws NoSuchActionException if a m d r action with the primary key could not be found
	*/
	public MDRAction remove(long actionId) throws NoSuchActionException;

	public MDRAction updateImpl(MDRAction mdrAction);

	/**
	* Returns the m d r action with the primary key or throws a {@link NoSuchActionException} if it could not be found.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action
	* @throws NoSuchActionException if a m d r action with the primary key could not be found
	*/
	public MDRAction findByPrimaryKey(long actionId)
		throws NoSuchActionException;

	/**
	* Returns the m d r action with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action, or <code>null</code> if a m d r action with the primary key could not be found
	*/
	public MDRAction fetchByPrimaryKey(long actionId);

	@Override
	public java.util.Map<java.io.Serializable, MDRAction> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the m d r actions.
	*
	* @return the m d r actions
	*/
	public java.util.List<MDRAction> findAll();

	/**
	* Returns a range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of m d r actions
	*/
	public java.util.List<MDRAction> findAll(int start, int end);

	/**
	* Returns an ordered range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of m d r actions
	*/
	public java.util.List<MDRAction> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator);

	/**
	* Returns an ordered range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of m d r actions
	*/
	public java.util.List<MDRAction> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRAction> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the m d r actions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of m d r actions.
	*
	* @return the number of m d r actions
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}