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

package com.liferay.layout.page.template.service.persistence;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateStructureException;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout page template structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplateStructurePersistence
	extends BasePersistence<LayoutPageTemplateStructure>,
			CTPersistence<LayoutPageTemplateStructure> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateStructureUtil} to access the layout page template structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout page template structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout page template structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the first layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns the last layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the last layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns the layout page template structures before and after the current layout page template structure in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the current layout page template structure
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	public LayoutPageTemplateStructure[] findByUuid_PrevAndNext(
			long layoutPageTemplateStructureId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Removes all the layout page template structures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout page template structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout page template structures
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout page template structure where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageTemplateStructureException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure findByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the layout page template structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the layout page template structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout page template structure where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout page template structure that was removed
	 */
	public LayoutPageTemplateStructure removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the number of layout page template structures where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout page template structures
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the first layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns the last layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the last layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns the layout page template structures before and after the current layout page template structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the current layout page template structure
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	public LayoutPageTemplateStructure[] findByUuid_C_PrevAndNext(
			long layoutPageTemplateStructureId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Removes all the layout page template structures where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout page template structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout page template structures
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the layout page template structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByGroupId(
		long groupId);

	/**
	 * Returns a range of all the layout page template structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the first layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns the last layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the last layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns the layout page template structures before and after the current layout page template structure in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the current layout page template structure
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	public LayoutPageTemplateStructure[] findByGroupId_PrevAndNext(
			long layoutPageTemplateStructureId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructure> orderByComparator)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Removes all the layout page template structures where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of layout page template structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template structures
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the layout page template structure where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchPageTemplateStructureException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure findByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the layout page template structure where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByG_C_C(
		long groupId, long classNameId, long classPK);

	/**
	 * Returns the layout page template structure where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	public LayoutPageTemplateStructure fetchByG_C_C(
		long groupId, long classNameId, long classPK, boolean useFinderCache);

	/**
	 * Removes the layout page template structure where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the layout page template structure that was removed
	 */
	public LayoutPageTemplateStructure removeByG_C_C(
			long groupId, long classNameId, long classPK)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the number of layout page template structures where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching layout page template structures
	 */
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	 * Caches the layout page template structure in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 */
	public void cacheResult(
		LayoutPageTemplateStructure layoutPageTemplateStructure);

	/**
	 * Caches the layout page template structures in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateStructures the layout page template structures
	 */
	public void cacheResult(
		java.util.List<LayoutPageTemplateStructure>
			layoutPageTemplateStructures);

	/**
	 * Creates a new layout page template structure with the primary key. Does not add the layout page template structure to the database.
	 *
	 * @param layoutPageTemplateStructureId the primary key for the new layout page template structure
	 * @return the new layout page template structure
	 */
	public LayoutPageTemplateStructure create(
		long layoutPageTemplateStructureId);

	/**
	 * Removes the layout page template structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure that was removed
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	public LayoutPageTemplateStructure remove(
			long layoutPageTemplateStructureId)
		throws NoSuchPageTemplateStructureException;

	public LayoutPageTemplateStructure updateImpl(
		LayoutPageTemplateStructure layoutPageTemplateStructure);

	/**
	 * Returns the layout page template structure with the primary key or throws a <code>NoSuchPageTemplateStructureException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure
	 * @throws NoSuchPageTemplateStructureException if a layout page template structure with the primary key could not be found
	 */
	public LayoutPageTemplateStructure findByPrimaryKey(
			long layoutPageTemplateStructureId)
		throws NoSuchPageTemplateStructureException;

	/**
	 * Returns the layout page template structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure, or <code>null</code> if a layout page template structure with the primary key could not be found
	 */
	public LayoutPageTemplateStructure fetchByPrimaryKey(
		long layoutPageTemplateStructureId);

	/**
	 * Returns all the layout page template structures.
	 *
	 * @return the layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findAll();

	/**
	 * Returns a range of all the layout page template structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout page template structures
	 */
	public java.util.List<LayoutPageTemplateStructure> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructure> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout page template structures from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout page template structures.
	 *
	 * @return the number of layout page template structures
	 */
	public int countAll();

}