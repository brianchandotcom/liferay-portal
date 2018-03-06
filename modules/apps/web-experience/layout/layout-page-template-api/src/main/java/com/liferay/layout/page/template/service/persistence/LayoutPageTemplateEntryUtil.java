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

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the layout page template entry service. This utility wraps {@link com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplateEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryPersistence
 * @see com.liferay.layout.page.template.service.persistence.impl.LayoutPageTemplateEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {
		getPersistence().clearCache(layoutPageTemplateEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutPageTemplateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutPageTemplateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutPageTemplateEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutPageTemplateEntry update(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {
		return getPersistence().update(layoutPageTemplateEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutPageTemplateEntry update(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(layoutPageTemplateEntry, serviceContext);
	}

	/**
	* Returns all the layout page template entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the layout page template entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the layout page template entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page template entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first layout page template entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first layout page template entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last layout page template entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last layout page template entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63;.
	*
	* @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry[] findByGroupId_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(layoutPageTemplateEntryId,
			groupId, orderByComparator);
	}

	/**
	* Returns all the layout page template entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByGroupId(
		long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByGroupId(
		long groupId, int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63;.
	*
	* @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry[] filterFindByGroupId_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(layoutPageTemplateEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the layout page template entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of layout page template entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page template entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of layout page template entries that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching layout page template entries that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @return the matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_L(long groupId,
		long layoutPageTemplateCollectionId) {
		return getPersistence()
				   .findByG_L(groupId, layoutPageTemplateCollectionId);
	}

	/**
	* Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end) {
		return getPersistence()
				   .findByG_L(groupId, layoutPageTemplateCollectionId, start,
			end);
	}

	/**
	* Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .findByG_L(groupId, layoutPageTemplateCollectionId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_L(groupId, layoutPageTemplateCollectionId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByG_L_First(long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_L_First(groupId, layoutPageTemplateCollectionId,
			orderByComparator);
	}

	/**
	* Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByG_L_First(long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_L_First(groupId, layoutPageTemplateCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByG_L_Last(long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_L_Last(groupId, layoutPageTemplateCollectionId,
			orderByComparator);
	}

	/**
	* Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByG_L_Last(long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_L_Last(groupId, layoutPageTemplateCollectionId,
			orderByComparator);
	}

	/**
	* Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry[] findByG_L_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_L_PrevAndNext(layoutPageTemplateEntryId, groupId,
			layoutPageTemplateCollectionId, orderByComparator);
	}

	/**
	* Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @return the matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_L(long groupId,
		long layoutPageTemplateCollectionId) {
		return getPersistence()
				   .filterFindByG_L(groupId, layoutPageTemplateCollectionId);
	}

	/**
	* Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end) {
		return getPersistence()
				   .filterFindByG_L(groupId, layoutPageTemplateCollectionId,
			start, end);
	}

	/**
	* Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_L(groupId, layoutPageTemplateCollectionId,
			start, end, orderByComparator);
	}

	/**
	* Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry[] filterFindByG_L_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .filterFindByG_L_PrevAndNext(layoutPageTemplateEntryId,
			groupId, layoutPageTemplateCollectionId, orderByComparator);
	}

	/**
	* Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	*/
	public static void removeByG_L(long groupId,
		long layoutPageTemplateCollectionId) {
		getPersistence().removeByG_L(groupId, layoutPageTemplateCollectionId);
	}

	/**
	* Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @return the number of matching layout page template entries
	*/
	public static int countByG_L(long groupId,
		long layoutPageTemplateCollectionId) {
		return getPersistence()
				   .countByG_L(groupId, layoutPageTemplateCollectionId);
	}

	/**
	* Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @return the number of matching layout page template entries that the user has permission to view
	*/
	public static int filterCountByG_L(long groupId,
		long layoutPageTemplateCollectionId) {
		return getPersistence()
				   .filterCountByG_L(groupId, layoutPageTemplateCollectionId);
	}

	/**
	* Returns the layout page template entry where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the layout page template entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the layout page template entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the layout page template entry where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the layout page template entry that was removed
	*/
	public static LayoutPageTemplateEntry removeByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of layout page template entries where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching layout page template entries
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @return the matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name) {
		return getPersistence()
				   .findByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
			name);
	}

	/**
	* Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name, int start,
		int end) {
		return getPersistence()
				   .findByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
			name, start, end);
	}

	/**
	* Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .findByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
			name, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByG_L_LikeN_First(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_L_LikeN_First(groupId,
			layoutPageTemplateCollectionId, name, orderByComparator);
	}

	/**
	* Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByG_L_LikeN_First(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_L_LikeN_First(groupId,
			layoutPageTemplateCollectionId, name, orderByComparator);
	}

	/**
	* Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByG_L_LikeN_Last(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_L_LikeN_Last(groupId,
			layoutPageTemplateCollectionId, name, orderByComparator);
	}

	/**
	* Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByG_L_LikeN_Last(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_L_LikeN_Last(groupId,
			layoutPageTemplateCollectionId, name, orderByComparator);
	}

	/**
	* Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry[] findByG_L_LikeN_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_L_LikeN_PrevAndNext(layoutPageTemplateEntryId,
			groupId, layoutPageTemplateCollectionId, name, orderByComparator);
	}

	/**
	* Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @return the matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, java.lang.String name) {
		return getPersistence()
				   .filterFindByG_L_LikeN(groupId,
			layoutPageTemplateCollectionId, name);
	}

	/**
	* Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId,
		java.lang.String name, int start, int end) {
		return getPersistence()
				   .filterFindByG_L_LikeN(groupId,
			layoutPageTemplateCollectionId, name, start, end);
	}

	/**
	* Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId,
		java.lang.String name, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_L_LikeN(groupId,
			layoutPageTemplateCollectionId, name, start, end, orderByComparator);
	}

	/**
	* Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry[] filterFindByG_L_LikeN_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .filterFindByG_L_LikeN_PrevAndNext(layoutPageTemplateEntryId,
			groupId, layoutPageTemplateCollectionId, name, orderByComparator);
	}

	/**
	* Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	*/
	public static void removeByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name) {
		getPersistence()
			.removeByG_L_LikeN(groupId, layoutPageTemplateCollectionId, name);
	}

	/**
	* Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @return the number of matching layout page template entries
	*/
	public static int countByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name) {
		return getPersistence()
				   .countByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
			name);
	}

	/**
	* Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param layoutPageTemplateCollectionId the layout page template collection ID
	* @param name the name
	* @return the number of matching layout page template entries that the user has permission to view
	*/
	public static int filterCountByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name) {
		return getPersistence()
				   .filterCountByG_L_LikeN(groupId,
			layoutPageTemplateCollectionId, name);
	}

	/**
	* Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @return the matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_C_D(long groupId,
		long classNameId, boolean defaultTemplate) {
		return getPersistence()
				   .findByG_C_D(groupId, classNameId, defaultTemplate);
	}

	/**
	* Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_C_D(long groupId,
		long classNameId, boolean defaultTemplate, int start, int end) {
		return getPersistence()
				   .findByG_C_D(groupId, classNameId, defaultTemplate, start,
			end);
	}

	/**
	* Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_C_D(long groupId,
		long classNameId, boolean defaultTemplate, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .findByG_C_D(groupId, classNameId, defaultTemplate, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findByG_C_D(long groupId,
		long classNameId, boolean defaultTemplate, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_D(groupId, classNameId, defaultTemplate, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByG_C_D_First(long groupId,
		long classNameId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_C_D_First(groupId, classNameId, defaultTemplate,
			orderByComparator);
	}

	/**
	* Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByG_C_D_First(long groupId,
		long classNameId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_D_First(groupId, classNameId, defaultTemplate,
			orderByComparator);
	}

	/**
	* Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template entry
	* @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry findByG_C_D_Last(long groupId,
		long classNameId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_C_D_Last(groupId, classNameId, defaultTemplate,
			orderByComparator);
	}

	/**
	* Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	*/
	public static LayoutPageTemplateEntry fetchByG_C_D_Last(long groupId,
		long classNameId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_D_Last(groupId, classNameId, defaultTemplate,
			orderByComparator);
	}

	/**
	* Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry[] findByG_C_D_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId, long classNameId,
		boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .findByG_C_D_PrevAndNext(layoutPageTemplateEntryId, groupId,
			classNameId, defaultTemplate, orderByComparator);
	}

	/**
	* Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @return the matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_C_D(
		long groupId, long classNameId, boolean defaultTemplate) {
		return getPersistence()
				   .filterFindByG_C_D(groupId, classNameId, defaultTemplate);
	}

	/**
	* Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_C_D(
		long groupId, long classNameId, boolean defaultTemplate, int start,
		int end) {
		return getPersistence()
				   .filterFindByG_C_D(groupId, classNameId, defaultTemplate,
			start, end);
	}

	/**
	* Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout page template entries that the user has permission to view
	*/
	public static List<LayoutPageTemplateEntry> filterFindByG_C_D(
		long groupId, long classNameId, boolean defaultTemplate, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_D(groupId, classNameId, defaultTemplate,
			start, end, orderByComparator);
	}

	/**
	* Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry[] filterFindByG_C_D_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId, long classNameId,
		boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence()
				   .filterFindByG_C_D_PrevAndNext(layoutPageTemplateEntryId,
			groupId, classNameId, defaultTemplate, orderByComparator);
	}

	/**
	* Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	*/
	public static void removeByG_C_D(long groupId, long classNameId,
		boolean defaultTemplate) {
		getPersistence().removeByG_C_D(groupId, classNameId, defaultTemplate);
	}

	/**
	* Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @return the number of matching layout page template entries
	*/
	public static int countByG_C_D(long groupId, long classNameId,
		boolean defaultTemplate) {
		return getPersistence()
				   .countByG_C_D(groupId, classNameId, defaultTemplate);
	}

	/**
	* Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and defaultTemplate = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param defaultTemplate the default template
	* @return the number of matching layout page template entries that the user has permission to view
	*/
	public static int filterCountByG_C_D(long groupId, long classNameId,
		boolean defaultTemplate) {
		return getPersistence()
				   .filterCountByG_C_D(groupId, classNameId, defaultTemplate);
	}

	/**
	* Caches the layout page template entry in the entity cache if it is enabled.
	*
	* @param layoutPageTemplateEntry the layout page template entry
	*/
	public static void cacheResult(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {
		getPersistence().cacheResult(layoutPageTemplateEntry);
	}

	/**
	* Caches the layout page template entries in the entity cache if it is enabled.
	*
	* @param layoutPageTemplateEntries the layout page template entries
	*/
	public static void cacheResult(
		List<LayoutPageTemplateEntry> layoutPageTemplateEntries) {
		getPersistence().cacheResult(layoutPageTemplateEntries);
	}

	/**
	* Creates a new layout page template entry with the primary key. Does not add the layout page template entry to the database.
	*
	* @param layoutPageTemplateEntryId the primary key for the new layout page template entry
	* @return the new layout page template entry
	*/
	public static LayoutPageTemplateEntry create(long layoutPageTemplateEntryId) {
		return getPersistence().create(layoutPageTemplateEntryId);
	}

	/**
	* Removes the layout page template entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateEntryId the primary key of the layout page template entry
	* @return the layout page template entry that was removed
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry remove(long layoutPageTemplateEntryId)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence().remove(layoutPageTemplateEntryId);
	}

	public static LayoutPageTemplateEntry updateImpl(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {
		return getPersistence().updateImpl(layoutPageTemplateEntry);
	}

	/**
	* Returns the layout page template entry with the primary key or throws a {@link NoSuchPageTemplateEntryException} if it could not be found.
	*
	* @param layoutPageTemplateEntryId the primary key of the layout page template entry
	* @return the layout page template entry
	* @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry findByPrimaryKey(
		long layoutPageTemplateEntryId)
		throws com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException {
		return getPersistence().findByPrimaryKey(layoutPageTemplateEntryId);
	}

	/**
	* Returns the layout page template entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutPageTemplateEntryId the primary key of the layout page template entry
	* @return the layout page template entry, or <code>null</code> if a layout page template entry with the primary key could not be found
	*/
	public static LayoutPageTemplateEntry fetchByPrimaryKey(
		long layoutPageTemplateEntryId) {
		return getPersistence().fetchByPrimaryKey(layoutPageTemplateEntryId);
	}

	public static java.util.Map<java.io.Serializable, LayoutPageTemplateEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the layout page template entries.
	*
	* @return the layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the layout page template entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @return the range of layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the layout page template entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the layout page template entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template entries
	* @param end the upper bound of the range of layout page template entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of layout page template entries
	*/
	public static List<LayoutPageTemplateEntry> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the layout page template entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of layout page template entries.
	*
	* @return the number of layout page template entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutPageTemplateEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutPageTemplateEntryPersistence, LayoutPageTemplateEntryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(LayoutPageTemplateEntryPersistence.class);
}