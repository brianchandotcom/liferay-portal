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

import com.liferay.mobile.device.rules.exception.NoSuchRuleGroupException;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mdr rule group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupUtil
 * @generated
 */
@ProviderType
public interface MDRRuleGroupPersistence extends BasePersistence<MDRRuleGroup> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRRuleGroupUtil} to access the mdr rule group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the mdr rule groups where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByUuid(String uuid);

	/**
	 * Returns a range of all the mdr rule groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @return the range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group
	 * @throws NoSuchRuleGroupException if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the first mdr rule group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns the last mdr rule group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group
	 * @throws NoSuchRuleGroupException if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the last mdr rule group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns the mdr rule groups before and after the current mdr rule group in the ordered set where uuid = &#63;.
	 *
	 * @param ruleGroupId the primary key of the current mdr rule group
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group
	 * @throws NoSuchRuleGroupException if a mdr rule group with the primary key could not be found
	 */
	public MDRRuleGroup[] findByUuid_PrevAndNext(
			long ruleGroupId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Removes all the mdr rule groups where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of mdr rule groups where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching mdr rule groups
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the mdr rule group where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchRuleGroupException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr rule group
	 * @throws NoSuchRuleGroupException if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup findByUUID_G(String uuid, long groupId)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the mdr rule group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the mdr rule group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the mdr rule group where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the mdr rule group that was removed
	 */
	public MDRRuleGroup removeByUUID_G(String uuid, long groupId)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the number of mdr rule groups where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching mdr rule groups
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the mdr rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the mdr rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @return the range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group
	 * @throws NoSuchRuleGroupException if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the first mdr rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns the last mdr rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group
	 * @throws NoSuchRuleGroupException if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the last mdr rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns the mdr rule groups before and after the current mdr rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ruleGroupId the primary key of the current mdr rule group
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group
	 * @throws NoSuchRuleGroupException if a mdr rule group with the primary key could not be found
	 */
	public MDRRuleGroup[] findByUuid_C_PrevAndNext(
			long ruleGroupId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Removes all the mdr rule groups where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of mdr rule groups where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching mdr rule groups
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the mdr rule groups where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByGroupId(long groupId);

	/**
	 * Returns a range of all the mdr rule groups where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @return the range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule groups where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule groups where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first mdr rule group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group
	 * @throws NoSuchRuleGroupException if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the first mdr rule group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns the last mdr rule group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group
	 * @throws NoSuchRuleGroupException if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the last mdr rule group in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public MDRRuleGroup fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns the mdr rule groups before and after the current mdr rule group in the ordered set where groupId = &#63;.
	 *
	 * @param ruleGroupId the primary key of the current mdr rule group
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group
	 * @throws NoSuchRuleGroupException if a mdr rule group with the primary key could not be found
	 */
	public MDRRuleGroup[] findByGroupId_PrevAndNext(
			long ruleGroupId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Returns all the mdr rule groups that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching mdr rule groups that the user has permission to view
	 */
	public java.util.List<MDRRuleGroup> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the mdr rule groups that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @return the range of matching mdr rule groups that the user has permission to view
	 */
	public java.util.List<MDRRuleGroup> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule groups that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule groups that the user has permission to view
	 */
	public java.util.List<MDRRuleGroup> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns the mdr rule groups before and after the current mdr rule group in the ordered set of mdr rule groups that the user has permission to view where groupId = &#63;.
	 *
	 * @param ruleGroupId the primary key of the current mdr rule group
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mdr rule group
	 * @throws NoSuchRuleGroupException if a mdr rule group with the primary key could not be found
	 */
	public MDRRuleGroup[] filterFindByGroupId_PrevAndNext(
			long ruleGroupId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
				orderByComparator)
		throws NoSuchRuleGroupException;

	/**
	 * Returns all the mdr rule groups that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching mdr rule groups that the user has permission to view
	 */
	public java.util.List<MDRRuleGroup> filterFindByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the mdr rule groups that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @return the range of matching mdr rule groups that the user has permission to view
	 */
	public java.util.List<MDRRuleGroup> filterFindByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule groups that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule groups that the user has permission to view
	 */
	public java.util.List<MDRRuleGroup> filterFindByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns all the mdr rule groups where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the mdr rule groups where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @return the range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule groups where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule groups where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mdr rule groups where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of mdr rule groups where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching mdr rule groups
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of mdr rule groups where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching mdr rule groups
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns the number of mdr rule groups that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching mdr rule groups that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the number of mdr rule groups that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching mdr rule groups that the user has permission to view
	 */
	public int filterCountByGroupId(long[] groupIds);

	/**
	 * Caches the mdr rule group in the entity cache if it is enabled.
	 *
	 * @param mdrRuleGroup the mdr rule group
	 */
	public void cacheResult(MDRRuleGroup mdrRuleGroup);

	/**
	 * Caches the mdr rule groups in the entity cache if it is enabled.
	 *
	 * @param mdrRuleGroups the mdr rule groups
	 */
	public void cacheResult(java.util.List<MDRRuleGroup> mdrRuleGroups);

	/**
	 * Creates a new mdr rule group with the primary key. Does not add the mdr rule group to the database.
	 *
	 * @param ruleGroupId the primary key for the new mdr rule group
	 * @return the new mdr rule group
	 */
	public MDRRuleGroup create(long ruleGroupId);

	/**
	 * Removes the mdr rule group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleGroupId the primary key of the mdr rule group
	 * @return the mdr rule group that was removed
	 * @throws NoSuchRuleGroupException if a mdr rule group with the primary key could not be found
	 */
	public MDRRuleGroup remove(long ruleGroupId)
		throws NoSuchRuleGroupException;

	public MDRRuleGroup updateImpl(MDRRuleGroup mdrRuleGroup);

	/**
	 * Returns the mdr rule group with the primary key or throws a <code>NoSuchRuleGroupException</code> if it could not be found.
	 *
	 * @param ruleGroupId the primary key of the mdr rule group
	 * @return the mdr rule group
	 * @throws NoSuchRuleGroupException if a mdr rule group with the primary key could not be found
	 */
	public MDRRuleGroup findByPrimaryKey(long ruleGroupId)
		throws NoSuchRuleGroupException;

	/**
	 * Returns the mdr rule group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ruleGroupId the primary key of the mdr rule group
	 * @return the mdr rule group, or <code>null</code> if a mdr rule group with the primary key could not be found
	 */
	public MDRRuleGroup fetchByPrimaryKey(long ruleGroupId);

	/**
	 * Returns all the mdr rule groups.
	 *
	 * @return the mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findAll();

	/**
	 * Returns a range of all the mdr rule groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @return the range of mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the mdr rule groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mdr rule groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mdr rule groups
	 */
	public java.util.List<MDRRuleGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MDRRuleGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mdr rule groups from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of mdr rule groups.
	 *
	 * @return the number of mdr rule groups
	 */
	public int countAll();

}