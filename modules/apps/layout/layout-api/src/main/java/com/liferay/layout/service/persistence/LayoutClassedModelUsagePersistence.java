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

package com.liferay.layout.service.persistence;

import com.liferay.layout.exception.NoSuchClassedModelUsageException;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout classed model usage service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutClassedModelUsageUtil
 * @generated
 */
@ProviderType
public interface LayoutClassedModelUsagePersistence
	extends BasePersistence<LayoutClassedModelUsage> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutClassedModelUsageUtil} to access the layout classed model usage persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout classed model usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout classed model usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout classed model usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns an ordered range of all the layout classed model usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the first layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the last layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the last layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where uuid = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage[] findByUuid_PrevAndNext(
			long layoutClassedModelUsageId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Removes all the layout classed model usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout classed model usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout classed model usages
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout classed model usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchClassedModelUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByUUID_G(String uuid, long groupId)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the layout classed model usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the layout classed model usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout classed model usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout classed model usage that was removed
	 */
	public LayoutClassedModelUsage removeByUUID_G(String uuid, long groupId)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the number of layout classed model usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout classed model usages
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns an ordered range of all the layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the first layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the last layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the last layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage[] findByUuid_C_PrevAndNext(
			long layoutClassedModelUsageId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Removes all the layout classed model usages where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout classed model usages where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout classed model usages
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the layout classed model usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByPlid(long plid);

	/**
	 * Returns a range of all the layout classed model usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByPlid(
		long plid, int start, int end);

	/**
	 * Returns an ordered range of all the layout classed model usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns an ordered range of all the layout classed model usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByPlid_First(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the first layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the last layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByPlid_Last(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the last layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where plid = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage[] findByPlid_PrevAndNext(
			long layoutClassedModelUsageId, long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Removes all the layout classed model usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public void removeByPlid(long plid);

	/**
	 * Returns the number of layout classed model usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout classed model usages
	 */
	public int countByPlid(long plid);

	/**
	 * Returns all the layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns an ordered range of all the layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the first layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the last layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the last layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage[] findByC_C_PrevAndNext(
			long layoutClassedModelUsageId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Removes all the layout classed model usages where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of layout classed model usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching layout classed model usages
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByC_C_T(
		long classNameId, long classPK, int type);

	/**
	 * Returns a range of all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end);

	/**
	 * Returns an ordered range of all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns an ordered range of all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByC_C_T_First(
			long classNameId, long classPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the first layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByC_C_T_First(
		long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the last layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByC_C_T_Last(
			long classNameId, long classPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the last layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByC_C_T_Last(
		long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage[] findByC_C_T_PrevAndNext(
			long layoutClassedModelUsageId, long classNameId, long classPK,
			int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Removes all the layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	public void removeByC_C_T(long classNameId, long classPK, int type);

	/**
	 * Returns the number of layout classed model usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching layout classed model usages
	 */
	public int countByC_C_T(long classNameId, long classPK, int type);

	/**
	 * Returns all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns a range of all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end);

	/**
	 * Returns an ordered range of all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns an ordered range of all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByCK_CT_P_First(
			String containerKey, long containerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the first layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByCK_CT_P_First(
		String containerKey, long containerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the last layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByCK_CT_P_Last(
			String containerKey, long containerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the last layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByCK_CT_P_Last(
		String containerKey, long containerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns the layout classed model usages before and after the current layout classed model usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param layoutClassedModelUsageId the primary key of the current layout classed model usage
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage[] findByCK_CT_P_PrevAndNext(
			long layoutClassedModelUsageId, String containerKey,
			long containerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutClassedModelUsage> orderByComparator)
		throws NoSuchClassedModelUsageException;

	/**
	 * Removes all the layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 */
	public void removeByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns the number of layout classed model usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching layout classed model usages
	 */
	public int countByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns the layout classed model usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or throws a <code>NoSuchClassedModelUsageException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage findByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the layout classed model usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid);

	/**
	 * Returns the layout classed model usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public LayoutClassedModelUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid, boolean useFinderCache);

	/**
	 * Removes the layout classed model usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the layout classed model usage that was removed
	 */
	public LayoutClassedModelUsage removeByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the number of layout classed model usages where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching layout classed model usages
	 */
	public int countByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid);

	/**
	 * Caches the layout classed model usage in the entity cache if it is enabled.
	 *
	 * @param layoutClassedModelUsage the layout classed model usage
	 */
	public void cacheResult(LayoutClassedModelUsage layoutClassedModelUsage);

	/**
	 * Caches the layout classed model usages in the entity cache if it is enabled.
	 *
	 * @param layoutClassedModelUsages the layout classed model usages
	 */
	public void cacheResult(
		java.util.List<LayoutClassedModelUsage> layoutClassedModelUsages);

	/**
	 * Creates a new layout classed model usage with the primary key. Does not add the layout classed model usage to the database.
	 *
	 * @param layoutClassedModelUsageId the primary key for the new layout classed model usage
	 * @return the new layout classed model usage
	 */
	public LayoutClassedModelUsage create(long layoutClassedModelUsageId);

	/**
	 * Removes the layout classed model usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage that was removed
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage remove(long layoutClassedModelUsageId)
		throws NoSuchClassedModelUsageException;

	public LayoutClassedModelUsage updateImpl(
		LayoutClassedModelUsage layoutClassedModelUsage);

	/**
	 * Returns the layout classed model usage with the primary key or throws a <code>NoSuchClassedModelUsageException</code> if it could not be found.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage
	 * @throws NoSuchClassedModelUsageException if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage findByPrimaryKey(
			long layoutClassedModelUsageId)
		throws NoSuchClassedModelUsageException;

	/**
	 * Returns the layout classed model usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage, or <code>null</code> if a layout classed model usage with the primary key could not be found
	 */
	public LayoutClassedModelUsage fetchByPrimaryKey(
		long layoutClassedModelUsageId);

	/**
	 * Returns all the layout classed model usages.
	 *
	 * @return the layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findAll();

	/**
	 * Returns a range of all the layout classed model usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the layout classed model usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator);

	/**
	 * Returns an ordered range of all the layout classed model usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout classed model usages
	 */
	public java.util.List<LayoutClassedModelUsage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutClassedModelUsage> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout classed model usages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout classed model usages.
	 *
	 * @return the number of layout classed model usages
	 */
	public int countAll();

}