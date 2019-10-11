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

import com.liferay.data.engine.exception.NoSuchDataListViewException;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the de data list view service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataListViewUtil
 * @generated
 */
@ProviderType
public interface DEDataListViewPersistence
	extends BasePersistence<DEDataListView> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DEDataListViewUtil} to access the de data list view persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the de data list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de data list views
	 */
	public java.util.List<DEDataListView> findByUuid(String uuid);

	/**
	 * Returns a range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public DEDataListView findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public DEDataListView fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public DEDataListView findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public DEDataListView fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public DEDataListView[] findByUuid_PrevAndNext(
			long deDataListViewId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Removes all the de data list views where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of de data list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de data list views
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDataListViewException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public DEDataListView findByUUID_G(String uuid, long groupId)
		throws NoSuchDataListViewException;

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public DEDataListView fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public DEDataListView fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the de data list view where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the de data list view that was removed
	 */
	public DEDataListView removeByUUID_G(String uuid, long groupId)
		throws NoSuchDataListViewException;

	/**
	 * Returns the number of de data list views where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching de data list views
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching de data list views
	 */
	public java.util.List<DEDataListView> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public DEDataListView findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public DEDataListView fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public DEDataListView findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public DEDataListView fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public DEDataListView[] findByUuid_C_PrevAndNext(
			long deDataListViewId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Removes all the de data list views where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching de data list views
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching de data list views
	 */
	public java.util.List<DEDataListView> findByG_C_D(
		long groupId, long companyId, long ddmStructureId);

	/**
	 * Returns a range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end);

	/**
	 * Returns an ordered range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	public java.util.List<DEDataListView> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public DEDataListView findByG_C_D_First(
			long groupId, long companyId, long ddmStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Returns the first de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public DEDataListView fetchByG_C_D_First(
		long groupId, long companyId, long ddmStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns the last de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	public DEDataListView findByG_C_D_Last(
			long groupId, long companyId, long ddmStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Returns the last de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	public DEDataListView fetchByG_C_D_Last(
		long groupId, long companyId, long ddmStructureId,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public DEDataListView[] findByG_C_D_PrevAndNext(
			long deDataListViewId, long groupId, long companyId,
			long ddmStructureId,
			com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
				orderByComparator)
		throws NoSuchDataListViewException;

	/**
	 * Removes all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 */
	public void removeByG_C_D(
		long groupId, long companyId, long ddmStructureId);

	/**
	 * Returns the number of de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching de data list views
	 */
	public int countByG_C_D(long groupId, long companyId, long ddmStructureId);

	/**
	 * Caches the de data list view in the entity cache if it is enabled.
	 *
	 * @param deDataListView the de data list view
	 */
	public void cacheResult(DEDataListView deDataListView);

	/**
	 * Caches the de data list views in the entity cache if it is enabled.
	 *
	 * @param deDataListViews the de data list views
	 */
	public void cacheResult(java.util.List<DEDataListView> deDataListViews);

	/**
	 * Creates a new de data list view with the primary key. Does not add the de data list view to the database.
	 *
	 * @param deDataListViewId the primary key for the new de data list view
	 * @return the new de data list view
	 */
	public DEDataListView create(long deDataListViewId);

	/**
	 * Removes the de data list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view that was removed
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public DEDataListView remove(long deDataListViewId)
		throws NoSuchDataListViewException;

	public DEDataListView updateImpl(DEDataListView deDataListView);

	/**
	 * Returns the de data list view with the primary key or throws a <code>NoSuchDataListViewException</code> if it could not be found.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	public DEDataListView findByPrimaryKey(long deDataListViewId)
		throws NoSuchDataListViewException;

	/**
	 * Returns the de data list view with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view, or <code>null</code> if a de data list view with the primary key could not be found
	 */
	public DEDataListView fetchByPrimaryKey(long deDataListViewId);

	/**
	 * Returns all the de data list views.
	 *
	 * @return the de data list views
	 */
	public java.util.List<DEDataListView> findAll();

	/**
	 * Returns a range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of de data list views
	 */
	public java.util.List<DEDataListView> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de data list views
	 */
	public java.util.List<DEDataListView> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator);

	/**
	 * Returns an ordered range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of de data list views
	 */
	public java.util.List<DEDataListView> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DEDataListView>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the de data list views from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of de data list views.
	 *
	 * @return the number of de data list views
	 */
	public int countAll();

}