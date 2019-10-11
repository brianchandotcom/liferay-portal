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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddm structure layout service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutUtil
 * @generated
 */
@ProviderType
public interface DDMStructureLayoutPersistence
	extends BasePersistence<DDMStructureLayout> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureLayoutUtil} to access the ddm structure layout persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddm structure layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByUuid(String uuid);

	/**
	 * Returns a range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout[] findByUuid_PrevAndNext(
			long structureLayoutId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Removes all the ddm structure layouts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddm structure layouts
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the ddm structure layout where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddm structure layout that was removed
	 */
	public DDMStructureLayout removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddm structure layouts
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout[] findByUuid_C_PrevAndNext(
			long structureLayoutId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Removes all the ddm structure layouts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddm structure layouts
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the ddm structure layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByGroupId(long groupId);

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout[] findByGroupId_PrevAndNext(
			long structureLayoutId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddm structure layouts
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey);

	/**
	 * Returns a range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByStructureLayoutKey_First(
			String structureLayoutKey,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the first ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByStructureLayoutKey_First(
		String structureLayoutKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the last ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByStructureLayoutKey_Last(
			String structureLayoutKey,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the last ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByStructureLayoutKey_Last(
		String structureLayoutKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout[] findByStructureLayoutKey_PrevAndNext(
			long structureLayoutId, String structureLayoutKey,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Removes all the ddm structure layouts where structureLayoutKey = &#63; from the database.
	 *
	 * @param structureLayoutKey the structure layout key
	 */
	public void removeByStructureLayoutKey(String structureLayoutKey);

	/**
	 * Returns the number of ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @return the number of matching ddm structure layouts
	 */
	public int countByStructureLayoutKey(String structureLayoutKey);

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByStructureVersionId(long structureVersionId)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId);

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param structureVersionId the structure version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId, boolean useFinderCache);

	/**
	 * Removes the ddm structure layout where structureVersionId = &#63; from the database.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the ddm structure layout that was removed
	 */
	public DDMStructureLayout removeByStructureVersionId(
			long structureVersionId)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the number of ddm structure layouts where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the number of matching ddm structure layouts
	 */
	public int countByStructureVersionId(long structureVersionId);

	/**
	 * Returns all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId);

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByG_C_First(
			long groupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByG_C_First(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByG_C_Last(
			long groupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByG_C_Last(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout[] findByG_C_PrevAndNext(
			long structureLayoutId, long groupId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	public void removeByG_C(long groupId, long classNameId);

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching ddm structure layouts
	 */
	public int countByG_C(long groupId, long classNameId);

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByG_C_S(
			long groupId, long classNameId, String structureLayoutKey)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByG_C_S(
		long groupId, long classNameId, String structureLayoutKey);

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByG_C_S(
		long groupId, long classNameId, String structureLayoutKey,
		boolean useFinderCache);

	/**
	 * Removes the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the ddm structure layout that was removed
	 */
	public DDMStructureLayout removeByG_C_S(
			long groupId, long classNameId, String structureLayoutKey)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the number of matching ddm structure layouts
	 */
	public int countByG_C_S(
		long groupId, long classNameId, String structureLayoutKey);

	/**
	 * Returns all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId);

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end);

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByG_C_SV_First(
			long groupId, long classNameId, long structureVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByG_C_SV_First(
		long groupId, long classNameId, long structureVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout findByG_C_SV_Last(
			long groupId, long classNameId, long structureVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	public DDMStructureLayout fetchByG_C_SV_Last(
		long groupId, long classNameId, long structureVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout[] findByG_C_SV_PrevAndNext(
			long structureLayoutId, long groupId, long classNameId,
			long structureVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
				orderByComparator)
		throws NoSuchStructureLayoutException;

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 */
	public void removeByG_C_SV(
		long groupId, long classNameId, long structureVersionId);

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @return the number of matching ddm structure layouts
	 */
	public int countByG_C_SV(
		long groupId, long classNameId, long structureVersionId);

	/**
	 * Caches the ddm structure layout in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLayout the ddm structure layout
	 */
	public void cacheResult(DDMStructureLayout ddmStructureLayout);

	/**
	 * Caches the ddm structure layouts in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLayouts the ddm structure layouts
	 */
	public void cacheResult(
		java.util.List<DDMStructureLayout> ddmStructureLayouts);

	/**
	 * Creates a new ddm structure layout with the primary key. Does not add the ddm structure layout to the database.
	 *
	 * @param structureLayoutId the primary key for the new ddm structure layout
	 * @return the new ddm structure layout
	 */
	public DDMStructureLayout create(long structureLayoutId);

	/**
	 * Removes the ddm structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout that was removed
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout remove(long structureLayoutId)
		throws NoSuchStructureLayoutException;

	public DDMStructureLayout updateImpl(DDMStructureLayout ddmStructureLayout);

	/**
	 * Returns the ddm structure layout with the primary key or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout findByPrimaryKey(long structureLayoutId)
		throws NoSuchStructureLayoutException;

	/**
	 * Returns the ddm structure layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout, or <code>null</code> if a ddm structure layout with the primary key could not be found
	 */
	public DDMStructureLayout fetchByPrimaryKey(long structureLayoutId);

	/**
	 * Returns all the ddm structure layouts.
	 *
	 * @return the ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findAll();

	/**
	 * Returns a range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm structure layouts
	 */
	public java.util.List<DDMStructureLayout> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStructureLayout>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm structure layouts from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddm structure layouts.
	 *
	 * @return the number of ddm structure layouts
	 */
	public int countAll();

}