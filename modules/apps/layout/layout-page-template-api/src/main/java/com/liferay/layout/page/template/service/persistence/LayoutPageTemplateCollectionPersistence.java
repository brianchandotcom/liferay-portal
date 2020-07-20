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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateCollectionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the layout page template collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollectionUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplateCollectionPersistence
	extends BasePersistence<LayoutPageTemplateCollection>,
			CTPersistence<LayoutPageTemplateCollection> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateCollectionUtil} to access the layout page template collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout page template collections where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout page template collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template collections where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the first layout page template collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the last layout page template collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the last layout page template collection in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection[] findByUuid_PrevAndNext(
			long layoutPageTemplateCollectionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Removes all the layout page template collections where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout page template collections where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout page template collections
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout page template collection where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageTemplateCollectionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the layout page template collection where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns the layout page template collection where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the layout page template collection where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout page template collection that was removed
	 */
	public LayoutPageTemplateCollection removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the number of layout page template collections where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout page template collections
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout page template collections where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout page template collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template collections where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the first layout page template collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the last layout page template collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the last layout page template collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection[] findByUuid_C_PrevAndNext(
			long layoutPageTemplateCollectionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Removes all the layout page template collections where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout page template collections where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout page template collections
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the layout page template collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByGroupId(
		long groupId);

	/**
	 * Returns a range of all the layout page template collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the first layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection[] findByGroupId_PrevAndNext(
			long layoutPageTemplateCollectionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns all the layout page template collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template collections that the user has permission to view
	 */
	public java.util.List<LayoutPageTemplateCollection> filterFindByGroupId(
		long groupId);

	/**
	 * Returns a range of all the layout page template collections that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections that the user has permission to view
	 */
	public java.util.List<LayoutPageTemplateCollection> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template collections that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections that the user has permission to view
	 */
	public java.util.List<LayoutPageTemplateCollection> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set of layout page template collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection[] filterFindByGroupId_PrevAndNext(
			long layoutPageTemplateCollectionId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Removes all the layout page template collections where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of layout page template collections where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template collections
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of layout page template collections that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template collections that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the layout page template collection where groupId = &#63; and layoutPageTemplateCollectionKey = &#63; or throws a <code>NoSuchPageTemplateCollectionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionKey the layout page template collection key
	 * @return the matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByG_LPTCK(
			long groupId, String layoutPageTemplateCollectionKey)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the layout page template collection where groupId = &#63; and layoutPageTemplateCollectionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionKey the layout page template collection key
	 * @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByG_LPTCK(
		long groupId, String layoutPageTemplateCollectionKey);

	/**
	 * Returns the layout page template collection where groupId = &#63; and layoutPageTemplateCollectionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionKey the layout page template collection key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByG_LPTCK(
		long groupId, String layoutPageTemplateCollectionKey,
		boolean useFinderCache);

	/**
	 * Removes the layout page template collection where groupId = &#63; and layoutPageTemplateCollectionKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionKey the layout page template collection key
	 * @return the layout page template collection that was removed
	 */
	public LayoutPageTemplateCollection removeByG_LPTCK(
			long groupId, String layoutPageTemplateCollectionKey)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the number of layout page template collections where groupId = &#63; and layoutPageTemplateCollectionKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionKey the layout page template collection key
	 * @return the number of matching layout page template collections
	 */
	public int countByG_LPTCK(
		long groupId, String layoutPageTemplateCollectionKey);

	/**
	 * Returns the layout page template collection where groupId = &#63; and name = &#63; or throws a <code>NoSuchPageTemplateCollectionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByG_N(long groupId, String name)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the layout page template collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByG_N(long groupId, String name);

	/**
	 * Returns the layout page template collection where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByG_N(
		long groupId, String name, boolean useFinderCache);

	/**
	 * Removes the layout page template collection where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the layout page template collection that was removed
	 */
	public LayoutPageTemplateCollection removeByG_N(long groupId, String name)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the number of layout page template collections where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template collections
	 */
	public int countByG_N(long groupId, String name);

	/**
	 * Returns all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByG_LikeN(
		long groupId, String name);

	/**
	 * Returns a range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByG_LikeN(
		long groupId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByG_LikeN_First(
			long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the first layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByG_LikeN_First(
		long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection findByG_LikeN_Last(
			long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the last layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template collection, or <code>null</code> if a matching layout page template collection could not be found
	 */
	public LayoutPageTemplateCollection fetchByG_LikeN_Last(
		long groupId, String name,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection[] findByG_LikeN_PrevAndNext(
			long layoutPageTemplateCollectionId, long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns all the layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template collections that the user has permission to view
	 */
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, String name);

	/**
	 * Returns a range of all the layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of matching layout page template collections that the user has permission to view
	 */
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template collections that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template collections that the user has permission to view
	 */
	public java.util.List<LayoutPageTemplateCollection> filterFindByG_LikeN(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns the layout page template collections before and after the current layout page template collection in the ordered set of layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the current layout page template collection
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection[] filterFindByG_LikeN_PrevAndNext(
			long layoutPageTemplateCollectionId, long groupId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Removes all the layout page template collections where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	public void removeByG_LikeN(long groupId, String name);

	/**
	 * Returns the number of layout page template collections where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template collections
	 */
	public int countByG_LikeN(long groupId, String name);

	/**
	 * Returns the number of layout page template collections that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template collections that the user has permission to view
	 */
	public int filterCountByG_LikeN(long groupId, String name);

	/**
	 * Caches the layout page template collection in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateCollection the layout page template collection
	 */
	public void cacheResult(
		LayoutPageTemplateCollection layoutPageTemplateCollection);

	/**
	 * Caches the layout page template collections in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateCollections the layout page template collections
	 */
	public void cacheResult(
		java.util.List<LayoutPageTemplateCollection>
			layoutPageTemplateCollections);

	/**
	 * Creates a new layout page template collection with the primary key. Does not add the layout page template collection to the database.
	 *
	 * @param layoutPageTemplateCollectionId the primary key for the new layout page template collection
	 * @return the new layout page template collection
	 */
	public LayoutPageTemplateCollection create(
		long layoutPageTemplateCollectionId);

	/**
	 * Removes the layout page template collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	 * @return the layout page template collection that was removed
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection remove(
			long layoutPageTemplateCollectionId)
		throws NoSuchPageTemplateCollectionException;

	public LayoutPageTemplateCollection updateImpl(
		LayoutPageTemplateCollection layoutPageTemplateCollection);

	/**
	 * Returns the layout page template collection with the primary key or throws a <code>NoSuchPageTemplateCollectionException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	 * @return the layout page template collection
	 * @throws NoSuchPageTemplateCollectionException if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection findByPrimaryKey(
			long layoutPageTemplateCollectionId)
		throws NoSuchPageTemplateCollectionException;

	/**
	 * Returns the layout page template collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateCollectionId the primary key of the layout page template collection
	 * @return the layout page template collection, or <code>null</code> if a layout page template collection with the primary key could not be found
	 */
	public LayoutPageTemplateCollection fetchByPrimaryKey(
		long layoutPageTemplateCollectionId);

	/**
	 * Returns all the layout page template collections.
	 *
	 * @return the layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findAll();

	/**
	 * Returns a range of all the layout page template collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @return the range of layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the layout page template collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateCollectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template collections
	 * @param end the upper bound of the range of layout page template collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout page template collections
	 */
	public java.util.List<LayoutPageTemplateCollection> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateCollection> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the layout page template collections from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout page template collections.
	 *
	 * @return the number of layout page template collections
	 */
	public int countAll();

}