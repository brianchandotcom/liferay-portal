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

import com.liferay.mobile.device.rules.exception.NoSuchRuleGroupInstanceException;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mdr rule group instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstanceUtil
 * @generated
 */
@ProviderType
public interface MDRRuleGroupInstancePersistence
	extends BasePersistence<MDRRuleGroupInstance> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRRuleGroupInstanceUtil} to access the mdr rule group instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the mdr rule group instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByUuid(String uuid);

	/**
	 * Returns a range of all the mdr rule group instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule group instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the first mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the last mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the last mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where uuid = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance[] findByUuid_PrevAndNext(
			long ruleGroupInstanceId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Removes all the mdr rule group instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of mdr rule group instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching mdr rule group instances
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the mdr rule group instance where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRuleGroupInstanceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByUUID_G(String uuid, long groupId)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the mdr rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the mdr rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the mdr rule group instance where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the mdr rule group instance that was removed
	 */
	public MDRRuleGroupInstance removeByUUID_G(String uuid, long groupId)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the number of mdr rule group instances where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching mdr rule group instances
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the first mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the last mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the last mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance[] findByUuid_C_PrevAndNext(
			long ruleGroupInstanceId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Removes all the mdr rule group instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of mdr rule group instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching mdr rule group instances
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the mdr rule group instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByGroupId(long groupId);

	/**
	 * Returns a range of all the mdr rule group instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule group instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the first mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the last mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the last mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where groupId = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance[] findByGroupId_PrevAndNext(
			long ruleGroupInstanceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns all the mdr rule group instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching mdr rule group instances that the user has permission to view
	 */
	public java.util.List<MDRRuleGroupInstance> filterFindByGroupId(
		long groupId);

	/**
	 * Returns a range of all the mdr rule group instances that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances that the user has permission to view
	 */
	public java.util.List<MDRRuleGroupInstance> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances that the user has permission to view
	 */
	public java.util.List<MDRRuleGroupInstance> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set of mdr rule group instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance[] filterFindByGroupId_PrevAndNext(
			long ruleGroupInstanceId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Removes all the mdr rule group instances where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of mdr rule group instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching mdr rule group instances
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of mdr rule group instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching mdr rule group instances that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns all the mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId);

	/**
	 * Returns a range of all the mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByRuleGroupId_First(
			long ruleGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the first mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByRuleGroupId_First(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the last mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByRuleGroupId_Last(
			long ruleGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the last mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByRuleGroupId_Last(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance[] findByRuleGroupId_PrevAndNext(
			long ruleGroupInstanceId, long ruleGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Removes all the mdr rule group instances where ruleGroupId = &#63; from the database.
	 *
	 * @param ruleGroupId the rule group ID
	 */
	public void removeByRuleGroupId(long ruleGroupId);

	/**
	 * Returns the number of mdr rule group instances where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the number of matching mdr rule group instances
	 */
	public int countByRuleGroupId(long ruleGroupId);

	/**
	 * Returns all the mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the first mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the last mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the last mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance[] findByC_C_PrevAndNext(
			long ruleGroupInstanceId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Removes all the mdr rule group instances where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of mdr rule group instances where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching mdr rule group instances
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK);

	/**
	 * Returns a range of all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the first mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the last mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the last mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance[] findByG_C_C_PrevAndNext(
			long ruleGroupInstanceId, long groupId, long classNameId,
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns all the mdr rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching mdr rule group instances that the user has permission to view
	 */
	public java.util.List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK);

	/**
	 * Returns a range of all the mdr rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of matching mdr rule group instances that the user has permission to view
	 */
	public java.util.List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule group instances that the user has permission to view
	 */
	public java.util.List<MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns the mdr rule group instances before and after the current mdr rule group instance in the ordered set of mdr rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param ruleGroupInstanceId the primary key of the current mdr rule group instance
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance[] filterFindByG_C_C_PrevAndNext(
			long ruleGroupInstanceId, long groupId, long classNameId,
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<MDRRuleGroupInstance> orderByComparator)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Removes all the mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	 * Returns the number of mdr rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching mdr rule group instances
	 */
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	 * Returns the number of mdr rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching mdr rule group instances that the user has permission to view
	 */
	public int filterCountByG_C_C(long groupId, long classNameId, long classPK);

	/**
	 * Returns the mdr rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or throws a <code>NoSuchRuleGroupInstanceException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @return the matching mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance findByC_C_R(
			long classNameId, long classPK, long ruleGroupId)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the mdr rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @return the matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByC_C_R(
		long classNameId, long classPK, long ruleGroupId);

	/**
	 * Returns the mdr rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr rule group instance, or <code>null</code> if a matching mdr rule group instance could not be found
	 */
	public MDRRuleGroupInstance fetchByC_C_R(
		long classNameId, long classPK, long ruleGroupId,
		boolean useFinderCache);

	/**
	 * Removes the mdr rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @return the mdr rule group instance that was removed
	 */
	public MDRRuleGroupInstance removeByC_C_R(
			long classNameId, long classPK, long ruleGroupId)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the number of mdr rule group instances where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ruleGroupId the rule group ID
	 * @return the number of matching mdr rule group instances
	 */
	public int countByC_C_R(long classNameId, long classPK, long ruleGroupId);

	/**
	 * Caches the mdr rule group instance in the entity cache if it is enabled.
	 *
	 * @param mdrRuleGroupInstance the mdr rule group instance
	 */
	public void cacheResult(MDRRuleGroupInstance mdrRuleGroupInstance);

	/**
	 * Caches the mdr rule group instances in the entity cache if it is enabled.
	 *
	 * @param mdrRuleGroupInstances the mdr rule group instances
	 */
	public void cacheResult(
		java.util.List<MDRRuleGroupInstance> mdrRuleGroupInstances);

	/**
	 * Creates a new mdr rule group instance with the primary key. Does not add the mdr rule group instance to the database.
	 *
	 * @param ruleGroupInstanceId the primary key for the new mdr rule group instance
	 * @return the new mdr rule group instance
	 */
	public MDRRuleGroupInstance create(long ruleGroupInstanceId);

	/**
	 * Removes the mdr rule group instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleGroupInstanceId the primary key of the mdr rule group instance
	 * @return the mdr rule group instance that was removed
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance remove(long ruleGroupInstanceId)
		throws NoSuchRuleGroupInstanceException;

	public MDRRuleGroupInstance updateImpl(
		MDRRuleGroupInstance mdrRuleGroupInstance);

	/**
	 * Returns the mdr rule group instance with the primary key or throws a <code>NoSuchRuleGroupInstanceException</code> if it could not be found.
	 *
	 * @param ruleGroupInstanceId the primary key of the mdr rule group instance
	 * @return the mdr rule group instance
	 * @throws NoSuchRuleGroupInstanceException if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance findByPrimaryKey(long ruleGroupInstanceId)
		throws NoSuchRuleGroupInstanceException;

	/**
	 * Returns the mdr rule group instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ruleGroupInstanceId the primary key of the mdr rule group instance
	 * @return the mdr rule group instance, or <code>null</code> if a mdr rule group instance with the primary key could not be found
	 */
	public MDRRuleGroupInstance fetchByPrimaryKey(long ruleGroupInstanceId);

	/**
	 * Returns all the mdr rule group instances.
	 *
	 * @return the mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findAll();

	/**
	 * Returns a range of all the mdr rule group instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @return the range of mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule group instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule group instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule group instances
	 * @param end the upper bound of the range of mdr rule group instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mdr rule group instances
	 */
	public java.util.List<MDRRuleGroupInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroupInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mdr rule group instances from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of mdr rule group instances.
	 *
	 * @return the number of mdr rule group instances
	 */
	public int countAll();

}