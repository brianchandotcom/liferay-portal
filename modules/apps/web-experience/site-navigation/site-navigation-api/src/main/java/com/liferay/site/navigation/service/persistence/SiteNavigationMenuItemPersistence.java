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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.site.navigation.exception.NoSuchMenuItemException;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

/**
 * The persistence interface for the site navigation menu item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.site.navigation.service.persistence.impl.SiteNavigationMenuItemPersistenceImpl
 * @see SiteNavigationMenuItemUtil
 * @generated
 */
@ProviderType
public interface SiteNavigationMenuItemPersistence extends BasePersistence<SiteNavigationMenuItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteNavigationMenuItemUtil} to access the site navigation menu item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the site navigation menu items where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @return the matching site navigation menu items
	*/
	public java.util.List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId);

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
	public java.util.List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end);

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
	public java.util.List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator);

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
	public java.util.List<SiteNavigationMenuItem> findBySiteNavigationMenuId(
		long siteNavigationMenuId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public SiteNavigationMenuItem findBySiteNavigationMenuId_First(
		long siteNavigationMenuId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException;

	/**
	* Returns the first site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public SiteNavigationMenuItem fetchBySiteNavigationMenuId_First(
		long siteNavigationMenuId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator);

	/**
	* Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public SiteNavigationMenuItem findBySiteNavigationMenuId_Last(
		long siteNavigationMenuId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException;

	/**
	* Returns the last site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public SiteNavigationMenuItem fetchBySiteNavigationMenuId_Last(
		long siteNavigationMenuId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator);

	/**
	* Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	* @param siteNavigationMenuId the site navigation menu ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu item
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public SiteNavigationMenuItem[] findBySiteNavigationMenuId_PrevAndNext(
		long siteNavigationMenuItemId, long siteNavigationMenuId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException;

	/**
	* Removes all the site navigation menu items where siteNavigationMenuId = &#63; from the database.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	*/
	public void removeBySiteNavigationMenuId(long siteNavigationMenuId);

	/**
	* Returns the number of site navigation menu items where siteNavigationMenuId = &#63;.
	*
	* @param siteNavigationMenuId the site navigation menu ID
	* @return the number of matching site navigation menu items
	*/
	public int countBySiteNavigationMenuId(long siteNavigationMenuId);

	/**
	* Returns all the site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @return the matching site navigation menu items
	*/
	public java.util.List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId);

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
	public java.util.List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end);

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
	public java.util.List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator);

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
	public java.util.List<SiteNavigationMenuItem> findByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public SiteNavigationMenuItem findByParentSiteNavigationMenuItemId_First(
		long parentSiteNavigationMenuItemId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException;

	/**
	* Returns the first site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public SiteNavigationMenuItem fetchByParentSiteNavigationMenuItemId_First(
		long parentSiteNavigationMenuItemId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator);

	/**
	* Returns the last site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item
	* @throws NoSuchMenuItemException if a matching site navigation menu item could not be found
	*/
	public SiteNavigationMenuItem findByParentSiteNavigationMenuItemId_Last(
		long parentSiteNavigationMenuItemId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException;

	/**
	* Returns the last site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	*/
	public SiteNavigationMenuItem fetchByParentSiteNavigationMenuItemId_Last(
		long parentSiteNavigationMenuItemId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator);

	/**
	* Returns the site navigation menu items before and after the current site navigation menu item in the ordered set where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param siteNavigationMenuItemId the primary key of the current site navigation menu item
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu item
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public SiteNavigationMenuItem[] findByParentSiteNavigationMenuItemId_PrevAndNext(
		long siteNavigationMenuItemId, long parentSiteNavigationMenuItemId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator)
		throws NoSuchMenuItemException;

	/**
	* Removes all the site navigation menu items where parentSiteNavigationMenuItemId = &#63; from the database.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	*/
	public void removeByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId);

	/**
	* Returns the number of site navigation menu items where parentSiteNavigationMenuItemId = &#63;.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID
	* @return the number of matching site navigation menu items
	*/
	public int countByParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId);

	/**
	* Caches the site navigation menu item in the entity cache if it is enabled.
	*
	* @param siteNavigationMenuItem the site navigation menu item
	*/
	public void cacheResult(SiteNavigationMenuItem siteNavigationMenuItem);

	/**
	* Caches the site navigation menu items in the entity cache if it is enabled.
	*
	* @param siteNavigationMenuItems the site navigation menu items
	*/
	public void cacheResult(
		java.util.List<SiteNavigationMenuItem> siteNavigationMenuItems);

	/**
	* Creates a new site navigation menu item with the primary key. Does not add the site navigation menu item to the database.
	*
	* @param siteNavigationMenuItemId the primary key for the new site navigation menu item
	* @return the new site navigation menu item
	*/
	public SiteNavigationMenuItem create(long siteNavigationMenuItemId);

	/**
	* Removes the site navigation menu item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenuItemId the primary key of the site navigation menu item
	* @return the site navigation menu item that was removed
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public SiteNavigationMenuItem remove(long siteNavigationMenuItemId)
		throws NoSuchMenuItemException;

	public SiteNavigationMenuItem updateImpl(
		SiteNavigationMenuItem siteNavigationMenuItem);

	/**
	* Returns the site navigation menu item with the primary key or throws a {@link NoSuchMenuItemException} if it could not be found.
	*
	* @param siteNavigationMenuItemId the primary key of the site navigation menu item
	* @return the site navigation menu item
	* @throws NoSuchMenuItemException if a site navigation menu item with the primary key could not be found
	*/
	public SiteNavigationMenuItem findByPrimaryKey(
		long siteNavigationMenuItemId) throws NoSuchMenuItemException;

	/**
	* Returns the site navigation menu item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param siteNavigationMenuItemId the primary key of the site navigation menu item
	* @return the site navigation menu item, or <code>null</code> if a site navigation menu item with the primary key could not be found
	*/
	public SiteNavigationMenuItem fetchByPrimaryKey(
		long siteNavigationMenuItemId);

	@Override
	public java.util.Map<java.io.Serializable, SiteNavigationMenuItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the site navigation menu items.
	*
	* @return the site navigation menu items
	*/
	public java.util.List<SiteNavigationMenuItem> findAll();

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
	public java.util.List<SiteNavigationMenuItem> findAll(int start, int end);

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
	public java.util.List<SiteNavigationMenuItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator);

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
	public java.util.List<SiteNavigationMenuItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenuItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the site navigation menu items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of site navigation menu items.
	*
	* @return the number of site navigation menu items
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}