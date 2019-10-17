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

package com.liferay.info.service.persistence;

import com.liferay.info.exception.NoSuchItemUsageException;
import com.liferay.info.model.InfoItemUsage;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the info item usage service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see InfoItemUsageUtil
 * @generated
 */
@ProviderType
public interface InfoItemUsagePersistence
	extends BasePersistence<InfoItemUsage> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link InfoItemUsageUtil} to access the info item usage persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the info item usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByUuid(String uuid);

	/**
	 * Returns a range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the info item usages where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the first info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the last info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the last info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where uuid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public InfoItemUsage[] findByUuid_PrevAndNext(
			long infoItemUsageId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Removes all the info item usages where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of info item usages where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching info item usages
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByUUID_G(String uuid, long groupId)
		throws NoSuchItemUsageException;

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the info item usage where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the info item usage where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the info item usage that was removed
	 */
	public InfoItemUsage removeByUUID_G(String uuid, long groupId)
		throws NoSuchItemUsageException;

	/**
	 * Returns the number of info item usages where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching info item usages
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the info item usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByPlid(long plid);

	/**
	 * Returns a range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByPlid(
		long plid, int start, int end);

	/**
	 * Returns an ordered range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the info item usages where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByPlid_First(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the first info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the last info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByPlid_Last(
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the last info item usage in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where plid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public InfoItemUsage[] findByPlid_PrevAndNext(
			long infoItemUsageId, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Removes all the info item usages where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public void removeByPlid(long plid);

	/**
	 * Returns the number of info item usages where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	public int countByPlid(long plid);

	/**
	 * Returns all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByC_C(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByC_C_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public InfoItemUsage[] findByC_C_PrevAndNext(
			long infoItemUsageId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Removes all the info item usages where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching info item usages
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type);

	/**
	 * Returns a range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end);

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByC_C_T(
		long classNameId, long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByC_C_T_First(
			long classNameId, long classPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the first info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByC_C_T_First(
		long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByC_C_T_Last(
			long classNameId, long classPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the last info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByC_C_T_Last(
		long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public InfoItemUsage[] findByC_C_T_PrevAndNext(
			long infoItemUsageId, long classNameId, long classPK, int type,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Removes all the info item usages where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	public void removeByC_C_T(long classNameId, long classPK, int type);

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching info item usages
	 */
	public int countByC_C_T(long classNameId, long classPK, int type);

	/**
	 * Returns all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns a range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end);

	/**
	 * Returns an ordered range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching info item usages
	 */
	public java.util.List<InfoItemUsage> findByCK_CT_P(
		String containerKey, long containerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByCK_CT_P_First(
			String containerKey, long containerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the first info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByCK_CT_P_First(
		String containerKey, long containerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the last info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByCK_CT_P_Last(
			String containerKey, long containerType, long plid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Returns the last info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByCK_CT_P_Last(
		String containerKey, long containerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns the info item usages before and after the current info item usage in the ordered set where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param infoItemUsageId the primary key of the current info item usage
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public InfoItemUsage[] findByCK_CT_P_PrevAndNext(
			long infoItemUsageId, String containerKey, long containerType,
			long plid,
			com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
				orderByComparator)
		throws NoSuchItemUsageException;

	/**
	 * Removes all the info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 */
	public void removeByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns the number of info item usages where containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	public int countByCK_CT_P(
		String containerKey, long containerType, long plid);

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usage
	 * @throws NoSuchItemUsageException if a matching info item usage could not be found
	 */
	public InfoItemUsage findByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws NoSuchItemUsageException;

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid);

	/**
	 * Returns the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching info item usage, or <code>null</code> if a matching info item usage could not be found
	 */
	public InfoItemUsage fetchByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid, boolean useFinderCache);

	/**
	 * Removes the info item usage where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the info item usage that was removed
	 */
	public InfoItemUsage removeByC_C_CK_CT_P(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid)
		throws NoSuchItemUsageException;

	/**
	 * Returns the number of info item usages where classNameId = &#63; and classPK = &#63; and containerKey = &#63; and containerType = &#63; and plid = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param containerKey the container key
	 * @param containerType the container type
	 * @param plid the plid
	 * @return the number of matching info item usages
	 */
	public int countByC_C_CK_CT_P(
		long classNameId, long classPK, String containerKey, long containerType,
		long plid);

	/**
	 * Caches the info item usage in the entity cache if it is enabled.
	 *
	 * @param infoItemUsage the info item usage
	 */
	public void cacheResult(InfoItemUsage infoItemUsage);

	/**
	 * Caches the info item usages in the entity cache if it is enabled.
	 *
	 * @param infoItemUsages the info item usages
	 */
	public void cacheResult(java.util.List<InfoItemUsage> infoItemUsages);

	/**
	 * Creates a new info item usage with the primary key. Does not add the info item usage to the database.
	 *
	 * @param infoItemUsageId the primary key for the new info item usage
	 * @return the new info item usage
	 */
	public InfoItemUsage create(long infoItemUsageId);

	/**
	 * Removes the info item usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage that was removed
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public InfoItemUsage remove(long infoItemUsageId)
		throws NoSuchItemUsageException;

	public InfoItemUsage updateImpl(InfoItemUsage infoItemUsage);

	/**
	 * Returns the info item usage with the primary key or throws a <code>NoSuchItemUsageException</code> if it could not be found.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage
	 * @throws NoSuchItemUsageException if a info item usage with the primary key could not be found
	 */
	public InfoItemUsage findByPrimaryKey(long infoItemUsageId)
		throws NoSuchItemUsageException;

	/**
	 * Returns the info item usage with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param infoItemUsageId the primary key of the info item usage
	 * @return the info item usage, or <code>null</code> if a info item usage with the primary key could not be found
	 */
	public InfoItemUsage fetchByPrimaryKey(long infoItemUsageId);

	/**
	 * Returns all the info item usages.
	 *
	 * @return the info item usages
	 */
	public java.util.List<InfoItemUsage> findAll();

	/**
	 * Returns a range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @return the range of info item usages
	 */
	public java.util.List<InfoItemUsage> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of info item usages
	 */
	public java.util.List<InfoItemUsage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator);

	/**
	 * Returns an ordered range of all the info item usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>InfoItemUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of info item usages
	 * @param end the upper bound of the range of info item usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of info item usages
	 */
	public java.util.List<InfoItemUsage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<InfoItemUsage>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the info item usages from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of info item usages.
	 *
	 * @return the number of info item usages
	 */
	public int countAll();

}