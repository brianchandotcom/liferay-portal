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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.model.PortletPreferences;

/**
 * The persistence interface for the portlet preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesUtil
 * @generated
 */
@ProviderType
public interface PortletPreferencesPersistence extends BasePersistence<PortletPreferences> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletPreferencesUtil} to access the portlet preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the portlet preferenceses where ownerId = &#63;.
	*
	* @param ownerId the owner ID
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByOwnerId(long ownerId);

	/**
	* Returns a range of all the portlet preferenceses where ownerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByOwnerId(long ownerId,
		int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByOwnerId(long ownerId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByOwnerId(long ownerId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63;.
	*
	* @param ownerId the owner ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByOwnerId_First(long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63;.
	*
	* @param ownerId the owner ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByOwnerId_First(long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63;.
	*
	* @param ownerId the owner ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByOwnerId_Last(long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63;.
	*
	* @param ownerId the owner ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByOwnerId_Last(long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerId the owner ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByOwnerId_PrevAndNext(
		long portletPreferencesId, long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where ownerId = &#63; from the database.
	*
	* @param ownerId the owner ID
	*/
	public void removeByOwnerId(long ownerId);

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63;.
	*
	* @param ownerId the owner ID
	* @return the number of matching portlet preferenceses
	*/
	public int countByOwnerId(long ownerId);

	/**
	* Returns all the portlet preferenceses where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByPlid(long plid);

	/**
	* Returns a range of all the portlet preferenceses where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByPlid(long plid, int start,
		int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByPlid(long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByPlid(long plid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where plid = &#63; from the database.
	*
	* @param plid the plid
	*/
	public void removeByPlid(long plid);

	/**
	* Returns the number of portlet preferenceses where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching portlet preferenceses
	*/
	public int countByPlid(long plid);

	/**
	* Returns all the portlet preferenceses where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByPortletId(String portletId);

	/**
	* Returns a range of all the portlet preferenceses where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByPortletId(
		String portletId, int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByPortletId(
		String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByPortletId(
		String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByPortletId_First(String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByPortletId_First(String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByPortletId_Last(String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByPortletId_Last(String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByPortletId_PrevAndNext(
		long portletPreferencesId, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where portletId = &#63; from the database.
	*
	* @param portletId the portlet ID
	*/
	public void removeByPortletId(String portletId);

	/**
	* Returns the number of portlet preferenceses where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public int countByPortletId(String portletId);

	/**
	* Returns all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_P(int ownerType,
		String portletId);

	/**
	* Returns a range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_P(int ownerType,
		String portletId, int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_P(int ownerType,
		String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_P(int ownerType,
		String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_P_First(int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_P_First(int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_P_Last(int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_P_Last(int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByO_P_PrevAndNext(
		long portletPreferencesId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where ownerType = &#63; and portletId = &#63; from the database.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	*/
	public void removeByO_P(int ownerType, String portletId);

	/**
	* Returns the number of portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public int countByO_P(int ownerType, String portletId);

	/**
	* Returns all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByP_P(long plid,
		String portletId);

	/**
	* Returns a range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByP_P(long plid,
		String portletId, int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByP_P(long plid,
		String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByP_P(long plid,
		String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByP_P_First(long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByP_P_First(long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByP_P_Last(long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByP_P_Last(long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where plid = &#63; and portletId = &#63; from the database.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	*/
	public void removeByP_P(long plid, String portletId);

	/**
	* Returns the number of portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public int countByP_P(long plid, String portletId);

	/**
	* Returns all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_O_P(long ownerId,
		int ownerType, long plid);

	/**
	* Returns a range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_O_P(long ownerId,
		int ownerType, long plid, int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_O_P(long ownerId,
		int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_O_P(long ownerId,
		int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_O_P_First(long ownerId, int ownerType,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_O_P_First(long ownerId, int ownerType,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_O_P_Last(long ownerId, int ownerType,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_O_P_Last(long ownerId, int ownerType,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	*/
	public void removeByO_O_P(long ownerId, int ownerType, long plid);

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @return the number of matching portlet preferenceses
	*/
	public int countByO_O_P(long ownerId, int ownerType, long plid);

	/**
	* Returns all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_O_PI(long ownerId,
		int ownerType, String portletId);

	/**
	* Returns a range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_O_PI(long ownerId,
		int ownerType, String portletId, int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_O_PI(long ownerId,
		int ownerType, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_O_PI(long ownerId,
		int ownerType, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_O_PI_First(long ownerId, int ownerType,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_O_PI_First(long ownerId, int ownerType,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_O_PI_Last(long ownerId, int ownerType,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_O_PI_Last(long ownerId, int ownerType,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByO_O_PI_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	*/
	public void removeByO_O_PI(long ownerId, int ownerType, String portletId);

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public int countByO_O_PI(long ownerId, int ownerType, String portletId);

	/**
	* Returns all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_P_P(int ownerType,
		long plid, String portletId);

	/**
	* Returns a range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_P_P(int ownerType,
		long plid, String portletId, int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_P_P(int ownerType,
		long plid, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByO_P_P(int ownerType,
		long plid, String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_P_P_First(int ownerType, long plid,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_P_P_First(int ownerType, long plid,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_P_P_Last(int ownerType, long plid,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_P_P_Last(int ownerType, long plid,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByO_P_P_PrevAndNext(
		long portletPreferencesId, int ownerType, long plid, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	*/
	public void removeByO_P_P(int ownerType, long plid, String portletId);

	/**
	* Returns the number of portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public int countByO_P_P(int ownerType, long plid, String portletId);

	/**
	* Returns all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId);

	/**
	* Returns a range of all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first portlet preferences in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByC_O_O_LikeP_First(long companyId,
		long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the first portlet preferences in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByC_O_O_LikeP_First(long companyId,
		long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the last portlet preferences in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByC_O_O_LikeP_Last(long companyId,
		long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the last portlet preferences in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByC_O_O_LikeP_Last(long companyId,
		long ownerId, int ownerType, String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences[] findByC_O_O_LikeP_PrevAndNext(
		long portletPreferencesId, long companyId, long ownerId, int ownerType,
		String portletId,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator)
		throws NoSuchPortletPreferencesException;

	/**
	* Removes all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	*/
	public void removeByC_O_O_LikeP(long companyId, long ownerId,
		int ownerType, String portletId);

	/**
	* Returns the number of portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	*
	* @param companyId the company ID
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public int countByC_O_O_LikeP(long companyId, long ownerId, int ownerType,
		String portletId);

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchPortletPreferencesException</code> if it could not be found.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferences
	* @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public PortletPreferences findByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId) throws NoSuchPortletPreferencesException;

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId);

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId, boolean retrieveFromCache);

	/**
	* Removes the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the portlet preferences that was removed
	*/
	public PortletPreferences removeByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId) throws NoSuchPortletPreferencesException;

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public int countByO_O_P_P(long ownerId, int ownerType, long plid,
		String portletId);

	/**
	* Caches the portlet preferences in the entity cache if it is enabled.
	*
	* @param portletPreferences the portlet preferences
	*/
	public void cacheResult(PortletPreferences portletPreferences);

	/**
	* Caches the portlet preferenceses in the entity cache if it is enabled.
	*
	* @param portletPreferenceses the portlet preferenceses
	*/
	public void cacheResult(
		java.util.List<PortletPreferences> portletPreferenceses);

	/**
	* Creates a new portlet preferences with the primary key. Does not add the portlet preferences to the database.
	*
	* @param portletPreferencesId the primary key for the new portlet preferences
	* @return the new portlet preferences
	*/
	public PortletPreferences create(long portletPreferencesId);

	/**
	* Removes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences that was removed
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences remove(long portletPreferencesId)
		throws NoSuchPortletPreferencesException;

	public PortletPreferences updateImpl(PortletPreferences portletPreferences);

	/**
	* Returns the portlet preferences with the primary key or throws a <code>NoSuchPortletPreferencesException</code> if it could not be found.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences
	* @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences findByPrimaryKey(long portletPreferencesId)
		throws NoSuchPortletPreferencesException;

	/**
	* Returns the portlet preferences with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences, or <code>null</code> if a portlet preferences with the primary key could not be found
	*/
	public PortletPreferences fetchByPrimaryKey(long portletPreferencesId);

	/**
	* Returns all the portlet preferenceses.
	*
	* @return the portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findAll();

	/**
	* Returns a range of all the portlet preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findAll(int start, int end);

	/**
	* Returns an ordered range of all the portlet preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portlet preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of portlet preferenceses
	*/
	public java.util.List<PortletPreferences> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortletPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the portlet preferenceses from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of portlet preferenceses.
	*
	* @return the number of portlet preferenceses
	*/
	public int countAll();
}