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

import com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the m d r rule group instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see com.liferay.mobile.device.rules.service.persistence.impl.MDRRuleGroupInstancePersistenceImpl
 * @see MDRRuleGroupInstanceUtil
 * @generated
 */
@ProviderType
public interface MDRRuleGroupInstancePersistence extends BasePersistence<MDRRuleGroupInstance> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRRuleGroupInstanceUtil} to access the m d r rule group instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the m d r rule group instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r rule group instances
	*/
	public java.util.List<MDRRuleGroupInstance> findByUuid(
		java.lang.String uuid);

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
	public java.util.List<MDRRuleGroupInstance> findByUuid(
		java.lang.String uuid, int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

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
	public java.util.List<MDRRuleGroupInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance[] findByUuid_PrevAndNext(
		long ruleGroupInstanceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Removes all the m d r rule group instances where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of m d r rule group instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r rule group instances
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the m d r rule group instance where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r rule group instance that was removed
	*/
	public MDRRuleGroupInstance removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the number of m d r rule group instances where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching m d r rule group instances
	*/
	public java.util.List<MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId);

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
	public java.util.List<MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

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
	public java.util.List<MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance[] findByUuid_C_PrevAndNext(
		long ruleGroupInstanceId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Removes all the m d r rule group instances where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching m d r rule group instances
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the m d r rule group instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching m d r rule group instances
	*/
	public java.util.List<MDRRuleGroupInstance> findByGroupId(long groupId);

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
	public java.util.List<MDRRuleGroupInstance> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

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
	public java.util.List<MDRRuleGroupInstance> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance[] findByGroupId_PrevAndNext(
		long ruleGroupInstanceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns all the m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching m d r rule group instances that the user has permission to view
	*/
	public java.util.List<MDRRuleGroupInstance> filterFindByGroupId(
		long groupId);

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
	public java.util.List<MDRRuleGroupInstance> filterFindByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set of m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance[] filterFindByGroupId_PrevAndNext(
		long ruleGroupInstanceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Removes all the m d r rule group instances where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of m d r rule group instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the m d r rule group instances where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instances
	*/
	public java.util.List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId);

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
	public java.util.List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

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
	public java.util.List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByRuleGroupId_First(long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the first m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByRuleGroupId_First(long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the last m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByRuleGroupId_Last(long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the last m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByRuleGroupId_Last(long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance[] findByRuleGroupId_PrevAndNext(
		long ruleGroupInstanceId, long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Removes all the m d r rule group instances where ruleGroupId = &#63; from the database.
	*
	* @param ruleGroupId the rule group ID
	*/
	public void removeByRuleGroupId(long ruleGroupId);

	/**
	* Returns the number of m d r rule group instances where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the number of matching m d r rule group instances
	*/
	public int countByRuleGroupId(long ruleGroupId);

	/**
	* Returns all the m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances
	*/
	public java.util.List<MDRRuleGroupInstance> findByC_C(long classNameId,
		long classPK);

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
	public java.util.List<MDRRuleGroupInstance> findByC_C(long classNameId,
		long classPK, int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

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
	public java.util.List<MDRRuleGroupInstance> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the first m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByC_C_First(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the last m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the last m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance[] findByC_C_PrevAndNext(
		long ruleGroupInstanceId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Removes all the m d r rule group instances where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances
	*/
	public java.util.List<MDRRuleGroupInstance> findByG_C_C(long groupId,
		long classNameId, long classPK);

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
	public java.util.List<MDRRuleGroupInstance> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

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
	public java.util.List<MDRRuleGroupInstance> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByG_C_C_First(long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByG_C_C_First(long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByG_C_C_Last(long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByG_C_C_Last(long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance[] findByG_C_C_PrevAndNext(
		long ruleGroupInstanceId, long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns all the m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances that the user has permission to view
	*/
	public java.util.List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK);

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
	public java.util.List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set of m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance[] filterFindByG_C_C_PrevAndNext(
		long ruleGroupInstanceId, long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Removes all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances that the user has permission to view
	*/
	public int filterCountByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or throws a {@link NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance findByC_C_R(long classNameId, long classPK,
		long ruleGroupId) throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByC_C_R(long classNameId, long classPK,
		long ruleGroupId);

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	public MDRRuleGroupInstance fetchByC_C_R(long classNameId, long classPK,
		long ruleGroupId, boolean retrieveFromCache);

	/**
	* Removes the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the m d r rule group instance that was removed
	*/
	public MDRRuleGroupInstance removeByC_C_R(long classNameId, long classPK,
		long ruleGroupId) throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the number of m d r rule group instances where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the number of matching m d r rule group instances
	*/
	public int countByC_C_R(long classNameId, long classPK, long ruleGroupId);

	/**
	* Caches the m d r rule group instance in the entity cache if it is enabled.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	*/
	public void cacheResult(MDRRuleGroupInstance mdrRuleGroupInstance);

	/**
	* Caches the m d r rule group instances in the entity cache if it is enabled.
	*
	* @param mdrRuleGroupInstances the m d r rule group instances
	*/
	public void cacheResult(
		java.util.List<MDRRuleGroupInstance> mdrRuleGroupInstances);

	/**
	* Creates a new m d r rule group instance with the primary key. Does not add the m d r rule group instance to the database.
	*
	* @param ruleGroupInstanceId the primary key for the new m d r rule group instance
	* @return the new m d r rule group instance
	*/
	public MDRRuleGroupInstance create(long ruleGroupInstanceId);

	/**
	* Removes the m d r rule group instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance that was removed
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance remove(long ruleGroupInstanceId)
		throws NoSuchRuleGroupInstanceException;

	public MDRRuleGroupInstance updateImpl(
		MDRRuleGroupInstance mdrRuleGroupInstance);

	/**
	* Returns the m d r rule group instance with the primary key or throws a {@link NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance
	* @throws NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance findByPrimaryKey(long ruleGroupInstanceId)
		throws NoSuchRuleGroupInstanceException;

	/**
	* Returns the m d r rule group instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance, or <code>null</code> if a m d r rule group instance with the primary key could not be found
	*/
	public MDRRuleGroupInstance fetchByPrimaryKey(long ruleGroupInstanceId);

	@Override
	public java.util.Map<java.io.Serializable, MDRRuleGroupInstance> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the m d r rule group instances.
	*
	* @return the m d r rule group instances
	*/
	public java.util.List<MDRRuleGroupInstance> findAll();

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
	public java.util.List<MDRRuleGroupInstance> findAll(int start, int end);

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
	public java.util.List<MDRRuleGroupInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator);

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
	public java.util.List<MDRRuleGroupInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the m d r rule group instances from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of m d r rule group instances.
	*
	* @return the number of m d r rule group instances
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}