/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.PortletItem;

/**
 * The persistence interface for the portlet item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletItemPersistenceImpl
 * @see PortletItemUtil
 * @generated
 */
public interface PortletItemPersistence extends BasePersistence<PortletItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletItemUtil} to access the portlet item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the portlet item in the entity cache if it is enabled.
	*
	* @param portletItem the portlet item
	*/
	public void cacheResult(com.liferay.portal.model.PortletItem portletItem);

	/**
	* Caches the portlet items in the entity cache if it is enabled.
	*
	* @param portletItems the portlet items
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.PortletItem> portletItems);

	/**
	* Creates a new portlet item with the primary key. Does not add the portlet item to the database.
	*
	* @param portletItemId the primary key for the new portlet item
	* @return the new portlet item
	*/
	public com.liferay.portal.model.PortletItem create(long portletItemId);

	/**
	* Removes the portlet item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portletItemId the primary key of the portlet item
	* @return the portlet item that was removed
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem remove(long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet item with the primary key or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	*
	* @param portletItemId the primary key of the portlet item
	* @return the portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem findByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portletItemId the primary key of the portlet item
	* @return the portlet item, or <code>null</code> if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem fetchByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @return the range of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem findByG_C_First(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem findByG_C_Last(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param portletItemId the primary key of the current portlet item
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem[] findByG_C_PrevAndNext(
		long portletItemId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @return the range of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem findByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem findByG_P_C_Last(long groupId,
		java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param portletItemId the primary key of the current portlet item
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem[] findByG_P_C_PrevAndNext(
		long portletItemId, long groupId, java.lang.String portletId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem findByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem fetchByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem fetchByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet items.
	*
	* @return the portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @return the range of portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portlet items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet items where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_C(long groupId, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the portlet item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletItem removeByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet items from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the number of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_C(long groupId, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet items where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the number of matching portlet items
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet items.
	*
	* @return the number of portlet items
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}