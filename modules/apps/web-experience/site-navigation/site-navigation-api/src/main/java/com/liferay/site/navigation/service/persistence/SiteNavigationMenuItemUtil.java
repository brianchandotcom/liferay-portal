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

import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the site navigation menu item service. This utility wraps {@link com.liferay.site.navigation.service.persistence.impl.SiteNavigationMenuItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemPersistence
 * @see com.liferay.site.navigation.service.persistence.impl.SiteNavigationMenuItemPersistenceImpl
 * @generated
 */
@ProviderType
public class SiteNavigationMenuItemUtil {
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
	public static void clearCache(SiteNavigationMenuItem siteNavigationMenuItem) {
		getPersistence().clearCache(siteNavigationMenuItem);
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
	public static List<SiteNavigationMenuItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SiteNavigationMenuItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SiteNavigationMenuItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SiteNavigationMenuItem update(
		SiteNavigationMenuItem siteNavigationMenuItem) {
		return getPersistence().update(siteNavigationMenuItem);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SiteNavigationMenuItem update(
		SiteNavigationMenuItem siteNavigationMenuItem,
		ServiceContext serviceContext) {
		return getPersistence().update(siteNavigationMenuItem, serviceContext);
	}

	/**
	* Returns all the site navigation menu items where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @return the matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId) {
		return getPersistence().findBySiteNavigationMenuId(siteNavigationMenuId);
	}

	/**
	* Returns a range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @return the range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end) {
		return getPersistence()
				   .findBySiteNavigationMenuId(siteNavigationMenuId, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .findBySiteNavigationMenuId(siteNavigationMenuId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findBySiteNavigationMenuId(siteNavigationMenuId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem findBySiteNavigationMenuId_First(
		long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findBySiteNavigationMenuId_First(siteNavigationMenuId,
			orderByComparator);
	}

	/**
	* Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem fetchBySiteNavigationMenuId_First(
		long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .fetchBySiteNavigationMenuId_First(siteNavigationMenuId,
			orderByComparator);
	}

	/**
	* Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem findBySiteNavigationMenuId_Last(
		long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findBySiteNavigationMenuId_Last(siteNavigationMenuId,
			orderByComparator);
	}

	/**
	* Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem fetchBySiteNavigationMenuId_Last(
		long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .fetchBySiteNavigationMenuId_Last(siteNavigationMenuId,
			orderByComparator);
	}

	/**
	* Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu item
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public static SiteNavigationMenuItem[] findBySiteNavigationMenuId_PrevAndNext(
		long siteNavigationMenuItemId, long siteNavigationMenuId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findBySiteNavigationMenuId_PrevAndNext(siteNavigationMenuItemId,
			siteNavigationMenuId, orderByComparator);
	}

	/**
	* Removes all the site navigation menu items where siteNavigationMenuId = &#63; from the database.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	*/
	public static void removeBySiteNavigationMenuId(long siteNavigationMenuId) {
		getPersistence().removeBySiteNavigationMenuId(siteNavigationMenuId);
	}

	/**
	* Returns the number of site navigation menu items where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @return the number of matching site navigation menu items
	*/
	public static int countBySiteNavigationMenuId(long siteNavigationMenuId) {
		return getPersistence().countBySiteNavigationMenuId(siteNavigationMenuId);
	}

	/**
	* Returns all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @return the matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {
		return getPersistence()
				   .findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId);
	}

	/**
	* Returns a range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @return the range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end) {
		return getPersistence()
				   .findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId,
			start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem findByParentSiteNavigationMenuItemId_First(
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findByParentSiteNavigationMenuItemId_First(parentSiteNavigationMenuItemId,
			orderByComparator);
	}

	/**
	* Returns the first site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem fetchByParentSiteNavigationMenuItemId_First(
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .fetchByParentSiteNavigationMenuItemId_First(parentSiteNavigationMenuItemId,
			orderByComparator);
	}

	/**
	* Returns the last site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem findByParentSiteNavigationMenuItemId_Last(
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findByParentSiteNavigationMenuItemId_Last(parentSiteNavigationMenuItemId,
			orderByComparator);
	}

	/**
	* Returns the last site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem fetchByParentSiteNavigationMenuItemId_Last(
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .fetchByParentSiteNavigationMenuItemId_Last(parentSiteNavigationMenuItemId,
			orderByComparator);
	}

	/**
	* Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu item
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public static SiteNavigationMenuItem[] findByParentSiteNavigationMenuItemId_PrevAndNext(
		long siteNavigationMenuItemId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findByParentSiteNavigationMenuItemId_PrevAndNext(siteNavigationMenuItemId,
			parentSiteNavigationMenuItemId, orderByComparator);
	}

	/**
	* Removes all the site navigation menu items where parentSiteNavigationMenuItemId = &#63; from the database.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	*/
	public static void removeByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {
		getPersistence()
			.removeByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId);
	}

	/**
	* Returns the number of site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @return the number of matching site navigation menu items
	*/
	public static int countByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {
		return getPersistence()
				   .countByParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId);
	}

	/**
	* Returns all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @return the matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId) {
		return getPersistence()
				   .findByS_P(siteNavigationMenuId,
			parentSiteNavigationMenuItemId);
	}

	/**
	* Returns a range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @return the range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int start, int end) {
		return getPersistence()
				   .findByS_P(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .findByS_P(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findByS_P(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByS_P(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem findByS_P_First(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findByS_P_First(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, orderByComparator);
	}

	/**
	* Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem fetchByS_P_First(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .fetchByS_P_First(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, orderByComparator);
	}

	/**
	* Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem findByS_P_Last(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findByS_P_Last(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, orderByComparator);
	}

	/**
	* Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public static SiteNavigationMenuItem fetchByS_P_Last(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence()
				   .fetchByS_P_Last(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, orderByComparator);
	}

	/**
	* Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu item
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public static SiteNavigationMenuItem[] findByS_P_PrevAndNext(
		long siteNavigationMenuItemId, long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence()
				   .findByS_P_PrevAndNext(siteNavigationMenuItemId,
			siteNavigationMenuId, parentSiteNavigationMenuItemId,
			orderByComparator);
	}

	/**
	* Removes all the site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63; from the database.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	*/
	public static void removeByS_P(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId) {
		getPersistence()
			.removeByS_P(siteNavigationMenuId, parentSiteNavigationMenuItemId);
	}

	/**
	* Returns the number of site navigation menu items where siteNavigationMenuId = &#63; and parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @return the number of matching site navigation menu items
	*/
	public static int countByS_P(long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId) {
		return getPersistence()
				   .countByS_P(siteNavigationMenuId,
			parentSiteNavigationMenuItemId);
	}

	/**
	* Caches the site navigation menu item in the entity cache if it is enabled.
	*
	* @param siteNavigationMenuItem the site navigation menu item
	*/
	public static void cacheResult(
		SiteNavigationMenuItem siteNavigationMenuItem) {
		getPersistence().cacheResult(siteNavigationMenuItem);
	}

	/**
	* Caches the site navigation menu items in the entity cache if it is enabled.
	*
	* @param siteNavigationMenuItems the site navigation menu items
	*/
	public static void cacheResult(
		List<SiteNavigationMenuItem> siteNavigationMenuItems) {
		getPersistence().cacheResult(siteNavigationMenuItems);
	}

	/**
	* Creates a new site navigation menu item with the primary key. Does not add the site navigation menu item to the database.
	*
	* @param siteNavigationMenuItemId the primary key for the new site navigation menu item
	* @return the new site navigation menu item
	*/
	public static SiteNavigationMenuItem create(long siteNavigationMenuItemId) {
		return getPersistence().create(siteNavigationMenuItemId);
	}

	/**
	* Removes the site navigation menu item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenuItemId the primary key of the site navigation menu item
	* @return the site navigation menu item that was removed
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public static SiteNavigationMenuItem remove(long siteNavigationMenuItemId)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence().remove(siteNavigationMenuItemId);
	}

	public static SiteNavigationMenuItem updateImpl(
		SiteNavigationMenuItem siteNavigationMenuItem) {
		return getPersistence().updateImpl(siteNavigationMenuItem);
	}

	/**
	* Returns the site navigation menu item with the primary key or throws a {@link NoSuchMenuItemException} if it could not be found.
	*
	* @param siteNavigationMenuItemId the primary key of the site navigation menu item
	* @return the site navigation menu item
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public static SiteNavigationMenuItem findByPrimaryKey(
		long siteNavigationMenuItemId)
		throws com.liferay.site.navigation.exception.NoSuchMenuItemException {
		return getPersistence().findByPrimaryKey(siteNavigationMenuItemId);
	}

	/**
	* Returns the site navigation menu item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param siteNavigationMenuItemId the primary key of the site navigation menu item
	* @return the site navigation menu item, or <code>null</code> if a site navigation menu item with the primary key could not be found
	*/
	public static SiteNavigationMenuItem fetchByPrimaryKey(
		long siteNavigationMenuItemId) {
		return getPersistence().fetchByPrimaryKey(siteNavigationMenuItemId);
	}

	public static java.util.Map<java.io.Serializable, SiteNavigationMenuItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the site navigation menu items.
	*
	* @return the site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the site navigation menu items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @return the range of site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the site navigation menu items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findAll(int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the site navigation menu items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SiteNavigationMenuItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site navigation menu items
	* @param end the upper bound of the range of site navigation menu items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of site navigation menu items
	*/
	public static List<SiteNavigationMenuItem> findAll(int start, int end,
		OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the site navigation menu items from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of site navigation menu items.
	*
	* @return the number of site navigation menu items
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SiteNavigationMenuItemPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SiteNavigationMenuItemPersistence, SiteNavigationMenuItemPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SiteNavigationMenuItemPersistence.class);

		ServiceTracker<SiteNavigationMenuItemPersistence, SiteNavigationMenuItemPersistence> serviceTracker =
			new ServiceTracker<SiteNavigationMenuItemPersistence, SiteNavigationMenuItemPersistence>(bundle.getBundleContext(),
				SiteNavigationMenuItemPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}