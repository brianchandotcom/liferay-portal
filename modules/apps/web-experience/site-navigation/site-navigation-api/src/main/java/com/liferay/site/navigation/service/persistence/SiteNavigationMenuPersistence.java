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

import com.liferay.site.navigation.exception.NoSuchMenuException;
import com.liferay.site.navigation.model.SiteNavigationMenu;

/**
 * The persistence interface for the site navigation menu service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.site.navigation.service.persistence.impl.SiteNavigationMenuPersistenceImpl
 * @see SiteNavigationMenuUtil
 * @generated
 */
@ProviderType
public interface SiteNavigationMenuPersistence extends BasePersistence<SiteNavigationMenu> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteNavigationMenuUtil} to access the site navigation menu persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the site navigation menus where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching site navigation menus
	*/
	public java.util.List<SiteNavigationMenu> findByGroupId(long groupId);

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
	public java.util.List<SiteNavigationMenu> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<SiteNavigationMenu> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public java.util.List<SiteNavigationMenu> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public SiteNavigationMenu[] findByGroupId_PrevAndNext(
		long siteNavigationMenuId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns all the site navigation menus that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching site navigation menus that the user has permission to view
	*/
	public java.util.List<SiteNavigationMenu> filterFindByGroupId(long groupId);

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
	public java.util.List<SiteNavigationMenu> filterFindByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<SiteNavigationMenu> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

	/**
	* Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63;.
	*
	* @param siteNavigationMenuId the primary key of the current site navigation menu
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public SiteNavigationMenu[] filterFindByGroupId_PrevAndNext(
		long siteNavigationMenuId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Removes all the site navigation menus where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of site navigation menus where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching site navigation menus
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of site navigation menus that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching site navigation menus that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching site navigation menus
	*/
	public java.util.List<SiteNavigationMenu> findByG_N(long groupId,
		java.lang.String name);

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
	public java.util.List<SiteNavigationMenu> findByG_N(long groupId,
		java.lang.String name, int start, int end);

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
	public java.util.List<SiteNavigationMenu> findByG_N(long groupId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public java.util.List<SiteNavigationMenu> findByG_N(long groupId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu findByG_N_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu fetchByG_N_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu findByG_N_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu fetchByG_N_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public SiteNavigationMenu[] findByG_N_PrevAndNext(
		long siteNavigationMenuId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching site navigation menus that the user has permission to view
	*/
	public java.util.List<SiteNavigationMenu> filterFindByG_N(long groupId,
		java.lang.String name);

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
	public java.util.List<SiteNavigationMenu> filterFindByG_N(long groupId,
		java.lang.String name, int start, int end);

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
	public java.util.List<SiteNavigationMenu> filterFindByG_N(long groupId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public SiteNavigationMenu[] filterFindByG_N_PrevAndNext(
		long siteNavigationMenuId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Removes all the site navigation menus where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public void removeByG_N(long groupId, java.lang.String name);

	/**
	* Returns the number of site navigation menus where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching site navigation menus
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching site navigation menus that the user has permission to view
	*/
	public int filterCountByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the site navigation menus where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching site navigation menus
	*/
	public java.util.List<SiteNavigationMenu> findByG_T(long groupId, int type);

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
	public java.util.List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end);

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
	public java.util.List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public java.util.List<SiteNavigationMenu> findByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu findByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu fetchByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu findByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu fetchByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public SiteNavigationMenu[] findByG_T_PrevAndNext(
		long siteNavigationMenuId, long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching site navigation menus that the user has permission to view
	*/
	public java.util.List<SiteNavigationMenu> filterFindByG_T(long groupId,
		int type);

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
	public java.util.List<SiteNavigationMenu> filterFindByG_T(long groupId,
		int type, int start, int end);

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
	public java.util.List<SiteNavigationMenu> filterFindByG_T(long groupId,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public SiteNavigationMenu[] filterFindByG_T_PrevAndNext(
		long siteNavigationMenuId, long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Removes all the site navigation menus where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public void removeByG_T(long groupId, int type);

	/**
	* Returns the number of site navigation menus where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching site navigation menus
	*/
	public int countByG_T(long groupId, int type);

	/**
	* Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching site navigation menus that the user has permission to view
	*/
	public int filterCountByG_T(long groupId, int type);

	/**
	* Returns all the site navigation menus where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @return the matching site navigation menus
	*/
	public java.util.List<SiteNavigationMenu> findByG_A(long groupId,
		boolean auto);

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
	public java.util.List<SiteNavigationMenu> findByG_A(long groupId,
		boolean auto, int start, int end);

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
	public java.util.List<SiteNavigationMenu> findByG_A(long groupId,
		boolean auto, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public java.util.List<SiteNavigationMenu> findByG_A(long groupId,
		boolean auto, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu findByG_A_First(long groupId, boolean auto,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu fetchByG_A_First(long groupId, boolean auto,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu
	* @throws NoSuchMenuException if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu findByG_A_Last(long groupId, boolean auto,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	*/
	public SiteNavigationMenu fetchByG_A_Last(long groupId, boolean auto,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public SiteNavigationMenu[] findByG_A_PrevAndNext(
		long siteNavigationMenuId, long groupId, boolean auto,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Returns all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @return the matching site navigation menus that the user has permission to view
	*/
	public java.util.List<SiteNavigationMenu> filterFindByG_A(long groupId,
		boolean auto);

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
	public java.util.List<SiteNavigationMenu> filterFindByG_A(long groupId,
		boolean auto, int start, int end);

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
	public java.util.List<SiteNavigationMenu> filterFindByG_A(long groupId,
		boolean auto, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public SiteNavigationMenu[] filterFindByG_A_PrevAndNext(
		long siteNavigationMenuId, long groupId, boolean auto,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException;

	/**
	* Removes all the site navigation menus where groupId = &#63; and auto = &#63; from the database.
	*
	* @param groupId the group ID
	* @param auto the auto
	*/
	public void removeByG_A(long groupId, boolean auto);

	/**
	* Returns the number of site navigation menus where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @return the number of matching site navigation menus
	*/
	public int countByG_A(long groupId, boolean auto);

	/**
	* Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	*
	* @param groupId the group ID
	* @param auto the auto
	* @return the number of matching site navigation menus that the user has permission to view
	*/
	public int filterCountByG_A(long groupId, boolean auto);

	/**
	* Caches the site navigation menu in the entity cache if it is enabled.
	*
	* @param siteNavigationMenu the site navigation menu
	*/
	public void cacheResult(SiteNavigationMenu siteNavigationMenu);

	/**
	* Caches the site navigation menus in the entity cache if it is enabled.
	*
	* @param siteNavigationMenus the site navigation menus
	*/
	public void cacheResult(
		java.util.List<SiteNavigationMenu> siteNavigationMenus);

	/**
	* Creates a new site navigation menu with the primary key. Does not add the site navigation menu to the database.
	*
	* @param siteNavigationMenuId the primary key for the new site navigation menu
	* @return the new site navigation menu
	*/
	public SiteNavigationMenu create(long siteNavigationMenuId);

	/**
	* Removes the site navigation menu with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu that was removed
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public SiteNavigationMenu remove(long siteNavigationMenuId)
		throws NoSuchMenuException;

	public SiteNavigationMenu updateImpl(SiteNavigationMenu siteNavigationMenu);

	/**
	* Returns the site navigation menu with the primary key or throws a {@link NoSuchMenuException} if it could not be found.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu
	* @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	*/
	public SiteNavigationMenu findByPrimaryKey(long siteNavigationMenuId)
		throws NoSuchMenuException;

	/**
	* Returns the site navigation menu with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu, or <code>null</code> if a site navigation menu with the primary key could not be found
	*/
	public SiteNavigationMenu fetchByPrimaryKey(long siteNavigationMenuId);

	@Override
	public java.util.Map<java.io.Serializable, SiteNavigationMenu> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the site navigation menus.
	*
	* @return the site navigation menus
	*/
	public java.util.List<SiteNavigationMenu> findAll();

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
	public java.util.List<SiteNavigationMenu> findAll(int start, int end);

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
	public java.util.List<SiteNavigationMenu> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator);

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
	public java.util.List<SiteNavigationMenu> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the site navigation menus from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of site navigation menus.
	*
	* @return the number of site navigation menus
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}