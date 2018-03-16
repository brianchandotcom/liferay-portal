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

package com.liferay.site.navigation.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.site.navigation.model.SiteNavigationMenu;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the site navigation menu service. This utility wraps {@link com.liferay.site.navigation.service.persistence.impl.SiteNavigationMenuPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuPersistence
 * @see com.liferay.site.navigation.service.persistence.impl.SiteNavigationMenuPersistenceImpl
 * @generated
 */
@ProviderType
public class SiteNavigationMenuUtil {
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
	public static void clearCache(SiteNavigationMenu siteNavigationMenu) {
		getPersistence().clearCache(siteNavigationMenu);
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
	public static List<SiteNavigationMenu> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SiteNavigationMenu> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SiteNavigationMenu> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SiteNavigationMenu update(
		SiteNavigationMenu siteNavigationMenu) {
		return getPersistence().update(siteNavigationMenu);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SiteNavigationMenu update(
		SiteNavigationMenu siteNavigationMenu, ServiceContext serviceContext) {
		return getPersistence().update(siteNavigationMenu, serviceContext);
	}

	/**
	* Returns all the site navigation menus where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the site navigation menus where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menus where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu findByGroupId_First(long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu fetchByGroupId_First(long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu findByGroupId_Last(long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu fetchByGroupId_Last(long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu[] findByGroupId_PrevAndNext(
		long siteNavigationMenuId, long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(siteNavigationMenuId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the site navigation menus that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu[] filterFindByGroupId_PrevAndNext(
		long siteNavigationMenuId, long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(siteNavigationMenuId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the site navigation menus where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of site navigation menus where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching site navigation menus
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of site navigation menus that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching site navigation menus that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns a range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_N(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByG_N(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_N(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .findByG_N(groupId, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_N(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_N(groupId, name, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu findByG_N_First(long groupId,
		java.lang.String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByG_N_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu fetchByG_N_First(long groupId,
		java.lang.String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .fetchByG_N_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu findByG_N_Last(long groupId,
		java.lang.String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByG_N_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu fetchByG_N_Last(long groupId,
		java.lang.String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence().fetchByG_N_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu[] findByG_N_PrevAndNext(
		long siteNavigationMenuId, long groupId, java.lang.String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence()
				   .findByG_N_PrevAndNext(siteNavigationMenuId, groupId, name,
			orderByComparator);
	}

	/**
	* Returns all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().filterFindByG_N(groupId, name);
	}

	/**
	* Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_N(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().filterFindByG_N(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_N(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .filterFindByG_N(groupId, name, start, end, orderByComparator);
	}

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu[] filterFindByG_N_PrevAndNext(
		long siteNavigationMenuId, long groupId, java.lang.String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence()
				   .filterFindByG_N_PrevAndNext(siteNavigationMenuId, groupId,
			name, orderByComparator);
	}

	/**
	* Removes all the site navigation menus where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public static void removeByG_N(long groupId, java.lang.String name) {
		getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of site navigation menus where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching site navigation menus
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching site navigation menus that the user has permission to view
	*/
	public static int filterCountByG_N(long groupId, java.lang.String name) {
		return getPersistence().filterCountByG_N(groupId, name);
	}

	/**
	* Returns all the site navigation menus where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_T(long groupId, int type) {
		return getPersistence().findByG_T(groupId, type);
	}

	/**
	* Returns a range of all the site navigation menus where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end) {
		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menus where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_T(groupId, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu findByG_T_First(long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu fetchByG_T_First(long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .fetchByG_T_First(groupId, type, orderByComparator);
	}

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu findByG_T_Last(long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu fetchByG_T_Last(long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence().fetchByG_T_Last(groupId, type, orderByComparator);
	}

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu[] findByG_T_PrevAndNext(
		long siteNavigationMenuId, long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence()
				   .findByG_T_PrevAndNext(siteNavigationMenuId, groupId, type,
			orderByComparator);
	}

	/**
	* Returns all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_T(long groupId,
		int type) {
		return getPersistence().filterFindByG_T(groupId, type);
	}

	/**
	* Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_T(long groupId,
		int type, int start, int end) {
		return getPersistence().filterFindByG_T(groupId, type, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_T(long groupId,
		int type, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .filterFindByG_T(groupId, type, start, end, orderByComparator);
	}

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu[] filterFindByG_T_PrevAndNext(
		long siteNavigationMenuId, long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence()
				   .filterFindByG_T_PrevAndNext(siteNavigationMenuId, groupId,
			type, orderByComparator);
	}

	/**
	* Removes all the site navigation menus where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public static void removeByG_T(long groupId, int type) {
		getPersistence().removeByG_T(groupId, type);
	}

	/**
	* Returns the number of site navigation menus where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching site navigation menus
	*/
	public static int countByG_T(long groupId, int type) {
		return getPersistence().countByG_T(groupId, type);
	}

	/**
	* Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching site navigation menus that the user has permission to view
	*/
	public static int filterCountByG_T(long groupId, int type) {
		return getPersistence().filterCountByG_T(groupId, type);
	}

	/**
	* Returns all the site navigation menus where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @return the matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_A(long groupId, boolean auto) {
		return getPersistence().findByG_A(groupId, auto);
	}

	/**
	* Returns a range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_A(long groupId,
		boolean auto, int start, int end) {
		return getPersistence().findByG_A(groupId, auto, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_A(long groupId,
		boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, auto, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching site navigation menus
	*/
	public static List<SiteNavigationMenu> findByG_A(long groupId,
		boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, auto, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu findByG_A_First(long groupId,
		boolean auto, OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByG_A_First(groupId, auto, orderByComparator);
	}

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu fetchByG_A_First(long groupId,
		boolean auto, OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, auto, orderByComparator);
	}

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu findByG_A_Last(long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByG_A_Last(groupId, auto, orderByComparator);
	}

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public static SiteNavigationMenu fetchByG_A_Last(long groupId,
		boolean auto, OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence().fetchByG_A_Last(groupId, auto, orderByComparator);
	}

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu[] findByG_A_PrevAndNext(
		long siteNavigationMenuId, long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence()
				   .findByG_A_PrevAndNext(siteNavigationMenuId, groupId, auto,
			orderByComparator);
	}

	/**
	* Returns all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @return the matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_A(long groupId,
		boolean auto) {
		return getPersistence().filterFindByG_A(groupId, auto);
	}

	/**
	* Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_A(long groupId,
		boolean auto, int start, int end) {
		return getPersistence().filterFindByG_A(groupId, auto, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and auto = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menus that the user has permission to view
	*/
	public static List<SiteNavigationMenu> filterFindByG_A(long groupId,
		boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence()
				   .filterFindByG_A(groupId, auto, start, end, orderByComparator);
	}

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu[] filterFindByG_A_PrevAndNext(
		long siteNavigationMenuId, long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence()
				   .filterFindByG_A_PrevAndNext(siteNavigationMenuId, groupId,
			auto, orderByComparator);
	}

	/**
	* Removes all the site navigation menus where groupId = &#63; and auto = &#63; from the database.
	*
	* @param groupId the group ID
	* @param auto the auto
	*/
	public static void removeByG_A(long groupId, boolean auto) {
		getPersistence().removeByG_A(groupId, auto);
	}

	/**
	* Returns the number of site navigation menus where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @return the number of matching site navigation menus
	*/
	public static int countByG_A(long groupId, boolean auto) {
		return getPersistence().countByG_A(groupId, auto);
	}

	/**
	* Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @return the number of matching site navigation menus that the user has permission to view
	*/
	public static int filterCountByG_A(long groupId, boolean auto) {
		return getPersistence().filterCountByG_A(groupId, auto);
	}

	/**
	* Caches the site navigation menu in the entity cache if it is enabled.
	*
	* @param siteNavigationMenu the site navigation menu
	*/
	public static void cacheResult(SiteNavigationMenu siteNavigationMenu) {
		getPersistence().cacheResult(siteNavigationMenu);
	}

	/**
	* Caches the site navigation menus in the entity cache if it is enabled.
	*
	* @param siteNavigationMenus the site navigation menus
	*/
	public static void cacheResult(List<SiteNavigationMenu> siteNavigationMenus) {
		getPersistence().cacheResult(siteNavigationMenus);
	}

	/**
	* Creates a new site navigation menu with the primary key. Does not add the site navigation menu to the database.
	*
	* @param siteNavigationMenuId the primary key for the new site navigation menu
	* @return the new site navigation menu
	*/
	public static SiteNavigationMenu create(long siteNavigationMenuId) {
		return getPersistence().create(siteNavigationMenuId);
	}

	/**
	* Removes the site navigation menu with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu that was removed
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu remove(long siteNavigationMenuId)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().remove(siteNavigationMenuId);
	}

	public static SiteNavigationMenu updateImpl(
		SiteNavigationMenu siteNavigationMenu) {
		return getPersistence().updateImpl(siteNavigationMenu);
	}

	/**
	* Returns the site navigation menu with the primary key or throws a {@link NoSuchMenuException} if it could not be found.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu findByPrimaryKey(long siteNavigationMenuId)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {
		return getPersistence().findByPrimaryKey(siteNavigationMenuId);
	}

	/**
	* Returns the site navigation menu with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu, or <code>null</code> if a site navigation menu with the primary key could not be found
	*/
	public static SiteNavigationMenu fetchByPrimaryKey(
		long siteNavigationMenuId) {
		return getPersistence().fetchByPrimaryKey(siteNavigationMenuId);
	}

	public static java.util.Map<java.io.Serializable, SiteNavigationMenu> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the site navigation menus.
	*
	* @return the site navigation menus
	*/
	public static List<SiteNavigationMenu> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the site navigation menus.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of site navigation menus
	*/
	public static List<SiteNavigationMenu> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menus.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of site navigation menus
	*/
	public static List<SiteNavigationMenu> findAll(int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menus.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of site navigation menus
	*/
	public static List<SiteNavigationMenu> findAll(int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the site navigation menus from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of site navigation menus.
	*
	* @return the number of site navigation menus
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SiteNavigationMenuPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SiteNavigationMenuPersistence, SiteNavigationMenuPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SiteNavigationMenuPersistence.class);

		ServiceTracker<SiteNavigationMenuPersistence, SiteNavigationMenuPersistence> serviceTracker =
			new ServiceTracker<SiteNavigationMenuPersistence, SiteNavigationMenuPersistence>(bundle.getBundleContext(),
				SiteNavigationMenuPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}