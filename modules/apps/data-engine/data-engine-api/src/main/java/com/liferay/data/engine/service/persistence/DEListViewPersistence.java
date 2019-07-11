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

package com.liferay.data.engine.service.persistence;

import com.liferay.data.engine.exception.NoSuchListViewException;
import com.liferay.data.engine.model.DEListView;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the de list view service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEListViewUtil
 * @generated
 */
@ProviderType
public interface DEListViewPersistence extends BasePersistence<DEListView> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DEListViewUtil} to access the de list view persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the de list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de list views
	 */
	public java.util.List<DEListView> findByUuid(String uuid);

	/**
	 * Returns a range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	public java.util.List<DEListView> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	public java.util.List<DEListView> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	public java.util.List<DEListView> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public DEListView findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public DEListView fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public DEListView findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public DEListView fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where uuid = &#63;.
	 *
	 * @param deListViewId the primary key of the current de list view
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public DEListView[] findByUuid_PrevAndNext(
			long deListViewId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Removes all the de list views where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of de list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de list views
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchListViewException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public DEListView findByUUID_G(String uuid, long groupId)
		throws NoSuchListViewException;

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public DEListView fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the de list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public DEListView fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache);

	/**
	 * Removes the de list view where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the de list view that was removed
	 */
	public DEListView removeByUUID_G(String uuid, long groupId)
		throws NoSuchListViewException;

	/**
	 * Returns the number of de list views where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching de list views
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching de list views
	 */
	public java.util.List<DEListView> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	public java.util.List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	public java.util.List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	public java.util.List<DEListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public DEListView findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Returns the first de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public DEListView fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public DEListView findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Returns the last de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public DEListView fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param deListViewId the primary key of the current de list view
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public DEListView[] findByUuid_C_PrevAndNext(
			long deListViewId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Removes all the de list views where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of de list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching de list views
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the matching de list views
	 */
	public java.util.List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId);

	/**
	 * Returns a range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of matching de list views
	 */
	public java.util.List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end);

	/**
	 * Returns an ordered range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de list views
	 */
	public java.util.List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de list views
	 */
	public java.util.List<DEListView> findByG_C_D(
		long groupId, long companyId, long DDMStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public DEListView findByG_C_D_First(
			long groupId, long companyId, long DDMStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Returns the first de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public DEListView fetchByG_C_D_First(
		long groupId, long companyId, long DDMStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns the last de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view
	 * @throws NoSuchListViewException if a matching de list view could not be found
	 */
	public DEListView findByG_C_D_Last(
			long groupId, long companyId, long DDMStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Returns the last de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	public DEListView fetchByG_C_D_Last(
		long groupId, long companyId, long DDMStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns the de list views before and after the current de list view in the ordered set where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param deListViewId the primary key of the current de list view
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public DEListView[] findByG_C_D_PrevAndNext(
			long deListViewId, long groupId, long companyId,
			long DDMStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DEListView>
				orderByComparator)
		throws NoSuchListViewException;

	/**
	 * Removes all the de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 */
	public void removeByG_C_D(
		long groupId, long companyId, long DDMStructureId);

	/**
	 * Returns the number of de list views where groupId = &#63; and companyId = &#63; and DDMStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param DDMStructureId the ddm structure ID
	 * @return the number of matching de list views
	 */
	public int countByG_C_D(long groupId, long companyId, long DDMStructureId);

	/**
	 * Caches the de list view in the entity cache if it is enabled.
	 *
	 * @param deListView the de list view
	 */
	public void cacheResult(DEListView deListView);

	/**
	 * Caches the de list views in the entity cache if it is enabled.
	 *
	 * @param deListViews the de list views
	 */
	public void cacheResult(java.util.List<DEListView> deListViews);

	/**
	 * Creates a new de list view with the primary key. Does not add the de list view to the database.
	 *
	 * @param deListViewId the primary key for the new de list view
	 * @return the new de list view
	 */
	public DEListView create(long deListViewId);

	/**
	 * Removes the de list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deListViewId the primary key of the de list view
	 * @return the de list view that was removed
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public DEListView remove(long deListViewId) throws NoSuchListViewException;

	public DEListView updateImpl(DEListView deListView);

	/**
	 * Returns the de list view with the primary key or throws a <code>NoSuchListViewException</code> if it could not be found.
	 *
	 * @param deListViewId the primary key of the de list view
	 * @return the de list view
	 * @throws NoSuchListViewException if a de list view with the primary key could not be found
	 */
	public DEListView findByPrimaryKey(long deListViewId)
		throws NoSuchListViewException;

	/**
	 * Returns the de list view with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deListViewId the primary key of the de list view
	 * @return the de list view, or <code>null</code> if a de list view with the primary key could not be found
	 */
	public DEListView fetchByPrimaryKey(long deListViewId);

	/**
	 * Returns all the de list views.
	 *
	 * @return the de list views
	 */
	public java.util.List<DEListView> findAll();

	/**
	 * Returns a range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of de list views
	 */
	public java.util.List<DEListView> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de list views
	 */
	public java.util.List<DEListView> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of de list views
	 */
	public java.util.List<DEListView> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEListView>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the de list views from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of de list views.
	 *
	 * @return the number of de list views
	 */
	public int countAll();

}