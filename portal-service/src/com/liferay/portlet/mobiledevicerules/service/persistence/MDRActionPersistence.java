/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.mobiledevicerules.model.MDRAction;

/**
 * The persistence interface for the m d r action service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRActionPersistenceImpl
 * @see MDRActionUtil
 * @generated
 */
public interface MDRActionPersistence extends BasePersistence<MDRAction> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRActionUtil} to access the m d r action persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the m d r action in the entity cache if it is enabled.
	*
	* @param mdrAction the m d r action
	*/
	public void cacheResult(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction);

	/**
	* Caches the m d r actions in the entity cache if it is enabled.
	*
	* @param mdrActions the m d r actions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> mdrActions);

	/**
	* Creates a new m d r action with the primary key. Does not add the m d r action to the database.
	*
	* @param actionId the primary key for the new m d r action
	* @return the new m d r action
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction create(
		long actionId);

	/**
	* Removes the m d r action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action that was removed
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction remove(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	public com.liferay.portlet.mobiledevicerules.model.MDRAction updateImpl(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the m d r action with the primary key or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchActionException} if it could not be found.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction findByPrimaryKey(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Returns the m d r action with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action, or <code>null</code> if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByPrimaryKey(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the m d r actions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the m d r actions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the m d r actions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Returns the m d r actions before and after the current m d r action in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actionId the primary key of the current m d r action
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction[] findByUuid_PrevAndNext(
		long actionId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchActionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @return the matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction findByRuleGroupInstanceId_First(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Returns the last m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction findByRuleGroupInstanceId_Last(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Returns the m d r actions before and after the current m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actionId the primary key of the current m d r action
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction[] findByRuleGroupInstanceId_PrevAndNext(
		long actionId, long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Returns all the m d r actions.
	*
	* @return the m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the m d r actions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the m d r action where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r action that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRAction removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException;

	/**
	* Removes all the m d r actions where ruleGroupInstanceId = &#63; from the database.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByRuleGroupInstanceId(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the m d r actions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of m d r actions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of m d r actions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of m d r actions where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @return the number of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public int countByRuleGroupInstanceId(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of m d r actions.
	*
	* @return the number of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}