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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public interface PortletItemPersistence extends BasePersistence<PortletItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletItemUtil} to access the portlet item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId);

	/**
	* Returns a range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @return the range of matching portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem findByG_C_First(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Returns the first portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet item, or <code>null</code> if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem fetchByG_C_First(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem findByG_C_Last(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Returns the last portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet item, or <code>null</code> if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem fetchByG_C_Last(long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param portletItemId the primary key of the current portlet item
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	*/
	public com.liferay.portal.model.PortletItem[] findByG_C_PrevAndNext(
		long portletItemId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Removes all the portlet items where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of portlet items where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching portlet items
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the matching portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId);

	/**
	* Returns a range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @return the range of matching portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end);

	/**
	* Returns an ordered range of all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem findByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Returns the first portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet item, or <code>null</code> if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem fetchByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem findByG_P_C_Last(long groupId,
		java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Returns the last portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet item, or <code>null</code> if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem fetchByG_P_C_Last(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the portlet items before and after the current portlet item in the ordered set where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param portletItemId the primary key of the current portlet item
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	*/
	public com.liferay.portal.model.PortletItem[] findByG_P_C_PrevAndNext(
		long portletItemId, long groupId, java.lang.String portletId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Removes all the portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	*/
	public void removeByG_P_C(long groupId, java.lang.String portletId,
		long classNameId);

	/**
	* Returns the number of portlet items where groupId = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the number of matching portlet items
	*/
	public int countByG_P_C(long groupId, java.lang.String portletId,
		long classNameId);

	/**
	* Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the matching portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem findByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem fetchByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId);

	/**
	* Returns the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching portlet item, or <code>null</code> if a matching portlet item could not be found
	*/
	public com.liferay.portal.model.PortletItem fetchByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId,
		boolean retrieveFromCache);

	/**
	* Removes the portlet item where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the portlet item that was removed
	*/
	public com.liferay.portal.model.PortletItem removeByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Returns the number of portlet items where groupId = &#63; and name = &#63; and portletId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param portletId the portlet ID
	* @param classNameId the class name ID
	* @return the number of matching portlet items
	*/
	public int countByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId);

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
	*/
	public com.liferay.portal.model.PortletItem remove(long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException;

	public com.liferay.portal.model.PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem);

	/**
	* Returns the portlet item with the primary key or throws a {@link com.liferay.portal.NoSuchPortletItemException} if it could not be found.
	*
	* @param portletItemId the primary key of the portlet item
	* @return the portlet item
	* @throws com.liferay.portal.NoSuchPortletItemException if a portlet item with the primary key could not be found
	*/
	public com.liferay.portal.model.PortletItem findByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException;

	/**
	* Returns the portlet item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portletItemId the primary key of the portlet item
	* @return the portlet item, or <code>null</code> if a portlet item with the primary key could not be found
	*/
	public com.liferay.portal.model.PortletItem fetchByPrimaryKey(
		long portletItemId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.PortletItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the portlet items.
	*
	* @return the portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findAll();

	/**
	* Returns a range of all the portlet items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @return the range of portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the portlet items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet items
	* @param end the upper bound of the range of portlet items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portlet items
	*/
	public java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the portlet items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of portlet items.
	*
	* @return the number of portlet items
	*/
	public int countAll();
}