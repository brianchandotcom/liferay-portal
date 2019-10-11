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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateStructureRelException;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout page template structure rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRelUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplateStructureRelPersistence
	extends BasePersistence<LayoutPageTemplateStructureRel> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateStructureRelUtil} to access the layout page template structure rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout page template structure rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findByUuid(
		String uuid);

	/**
	 * Returns a range of all the layout page template structure rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structure rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structure rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the first layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns the last layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the last layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns the layout page template structure rels before and after the current layout page template structure rel in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the current layout page template structure rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public LayoutPageTemplateStructureRel[] findByUuid_PrevAndNext(
			long layoutPageTemplateStructureRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Removes all the layout page template structure rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout page template structure rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout page template structure rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout page template structure rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageTemplateStructureRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel findByUUID_G(
			String uuid, long groupId)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the layout page template structure rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns the layout page template structure rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout page template structure rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout page template structure rel that was removed
	 */
	public LayoutPageTemplateStructureRel removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the number of layout page template structure rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout page template structure rels
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the first layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns the last layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the last layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns the layout page template structure rels before and after the current layout page template structure rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the current layout page template structure rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public LayoutPageTemplateStructureRel[] findByUuid_C_PrevAndNext(
			long layoutPageTemplateStructureRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Removes all the layout page template structure rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout page template structure rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout page template structure rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @return the matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel>
		findByLayoutPageTemplateStructureId(long layoutPageTemplateStructureId);

	/**
	 * Returns a range of all the layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel>
		findByLayoutPageTemplateStructureId(
			long layoutPageTemplateStructureId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel>
		findByLayoutPageTemplateStructureId(
			long layoutPageTemplateStructureId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel>
		findByLayoutPageTemplateStructureId(
			long layoutPageTemplateStructureId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel
			findByLayoutPageTemplateStructureId_First(
				long layoutPageTemplateStructureId,
				com.liferay.portal.kernel.util.OrderByComparator
					<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the first layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel
		fetchByLayoutPageTemplateStructureId_First(
			long layoutPageTemplateStructureId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns the last layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel
			findByLayoutPageTemplateStructureId_Last(
				long layoutPageTemplateStructureId,
				com.liferay.portal.kernel.util.OrderByComparator
					<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the last layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel
		fetchByLayoutPageTemplateStructureId_Last(
			long layoutPageTemplateStructureId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns the layout page template structure rels before and after the current layout page template structure rel in the ordered set where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the current layout page template structure rel
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public LayoutPageTemplateStructureRel[]
			findByLayoutPageTemplateStructureId_PrevAndNext(
				long layoutPageTemplateStructureRelId,
				long layoutPageTemplateStructureId,
				com.liferay.portal.kernel.util.OrderByComparator
					<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Removes all the layout page template structure rels where layoutPageTemplateStructureId = &#63; from the database.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 */
	public void removeByLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId);

	/**
	 * Returns the number of layout page template structure rels where layoutPageTemplateStructureId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @return the number of matching layout page template structure rels
	 */
	public int countByLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId);

	/**
	 * Returns all the layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel>
		findBySegmentsExperienceId(long segmentsExperienceId);

	/**
	 * Returns a range of all the layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel>
		findBySegmentsExperienceId(
			long segmentsExperienceId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel>
		findBySegmentsExperienceId(
			long segmentsExperienceId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel>
		findBySegmentsExperienceId(
			long segmentsExperienceId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel findBySegmentsExperienceId_First(
			long segmentsExperienceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the first layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchBySegmentsExperienceId_First(
		long segmentsExperienceId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns the last layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel findBySegmentsExperienceId_Last(
			long segmentsExperienceId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the last layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchBySegmentsExperienceId_Last(
		long segmentsExperienceId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns the layout page template structure rels before and after the current layout page template structure rel in the ordered set where segmentsExperienceId = &#63;.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the current layout page template structure rel
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public LayoutPageTemplateStructureRel[]
			findBySegmentsExperienceId_PrevAndNext(
				long layoutPageTemplateStructureRelId,
				long segmentsExperienceId,
				com.liferay.portal.kernel.util.OrderByComparator
					<LayoutPageTemplateStructureRel> orderByComparator)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Removes all the layout page template structure rels where segmentsExperienceId = &#63; from the database.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 */
	public void removeBySegmentsExperienceId(long segmentsExperienceId);

	/**
	 * Returns the number of layout page template structure rels where segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @return the number of matching layout page template structure rels
	 */
	public int countBySegmentsExperienceId(long segmentsExperienceId);

	/**
	 * Returns the layout page template structure rel where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63; or throws a <code>NoSuchPageTemplateStructureRelException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel findByL_S(
			long layoutPageTemplateStructureId, long segmentsExperienceId)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the layout page template structure rel where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByL_S(
		long layoutPageTemplateStructureId, long segmentsExperienceId);

	/**
	 * Returns the layout page template structure rel where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template structure rel, or <code>null</code> if a matching layout page template structure rel could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByL_S(
		long layoutPageTemplateStructureId, long segmentsExperienceId,
		boolean useFinderCache);

	/**
	 * Removes the layout page template structure rel where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63; from the database.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the layout page template structure rel that was removed
	 */
	public LayoutPageTemplateStructureRel removeByL_S(
			long layoutPageTemplateStructureId, long segmentsExperienceId)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the number of layout page template structure rels where layoutPageTemplateStructureId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param layoutPageTemplateStructureId the layout page template structure ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the number of matching layout page template structure rels
	 */
	public int countByL_S(
		long layoutPageTemplateStructureId, long segmentsExperienceId);

	/**
	 * Caches the layout page template structure rel in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateStructureRel the layout page template structure rel
	 */
	public void cacheResult(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel);

	/**
	 * Caches the layout page template structure rels in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateStructureRels the layout page template structure rels
	 */
	public void cacheResult(
		java.util.List<LayoutPageTemplateStructureRel>
			layoutPageTemplateStructureRels);

	/**
	 * Creates a new layout page template structure rel with the primary key. Does not add the layout page template structure rel to the database.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key for the new layout page template structure rel
	 * @return the new layout page template structure rel
	 */
	public LayoutPageTemplateStructureRel create(
		long layoutPageTemplateStructureRelId);

	/**
	 * Removes the layout page template structure rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the layout page template structure rel
	 * @return the layout page template structure rel that was removed
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public LayoutPageTemplateStructureRel remove(
			long layoutPageTemplateStructureRelId)
		throws NoSuchPageTemplateStructureRelException;

	public LayoutPageTemplateStructureRel updateImpl(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel);

	/**
	 * Returns the layout page template structure rel with the primary key or throws a <code>NoSuchPageTemplateStructureRelException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the layout page template structure rel
	 * @return the layout page template structure rel
	 * @throws NoSuchPageTemplateStructureRelException if a layout page template structure rel with the primary key could not be found
	 */
	public LayoutPageTemplateStructureRel findByPrimaryKey(
			long layoutPageTemplateStructureRelId)
		throws NoSuchPageTemplateStructureRelException;

	/**
	 * Returns the layout page template structure rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateStructureRelId the primary key of the layout page template structure rel
	 * @return the layout page template structure rel, or <code>null</code> if a layout page template structure rel with the primary key could not be found
	 */
	public LayoutPageTemplateStructureRel fetchByPrimaryKey(
		long layoutPageTemplateStructureRelId);

	/**
	 * Returns all the layout page template structure rels.
	 *
	 * @return the layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findAll();

	/**
	 * Returns a range of all the layout page template structure rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @return the range of layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the layout page template structure rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template structure rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateStructureRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structure rels
	 * @param end the upper bound of the range of layout page template structure rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout page template structure rels
	 */
	public java.util.List<LayoutPageTemplateStructureRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateStructureRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout page template structure rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout page template structure rels.
	 *
	 * @return the number of layout page template structure rels
	 */
	public int countAll();

}