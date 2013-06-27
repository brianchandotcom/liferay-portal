/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.PortletPreferences;

/**
 * The persistence interface for the portlet preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesPersistenceImpl
 * @see PortletPreferencesUtil
 * @generated
 */
public interface PortletPreferencesPersistence extends BasePersistence<PortletPreferences> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletPreferencesUtil} to access the portlet preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the portlet preferenceses where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet preferenceses where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet preferenceses where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet preferenceses where plid = &#63; from the database.
	*
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet preferenceses where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPortletId(
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet preferenceses where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPortletId(
		java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet preferenceses where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPortletId(
		java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByPortletId_First(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByPortletId_First(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByPortletId_Last(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByPortletId_Last(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences[] findByPortletId_PrevAndNext(
		long portletPreferencesId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet preferenceses where portletId = &#63; from the database.
	*
	* @param portletId the portlet ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPortletId(java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countByPortletId(java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P(
		int ownerType, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P(
		int ownerType, java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P(
		int ownerType, java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_P_First(
		int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_P_First(
		int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_P_Last(
		int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_P_Last(
		int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences[] findByO_P_PrevAndNext(
		long portletPreferencesId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet preferenceses where ownerType = &#63; and portletId = &#63; from the database.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByO_P(int ownerType, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countByO_P(int ownerType, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByP_P_First(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByP_P_First(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByP_P_Last(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByP_P_Last(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet preferenceses where plid = &#63; and portletId = &#63; from the database.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByP_P(long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countByP_P(long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @return the matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_O_P_First(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_O_P_First(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByO_O_P(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @return the number of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countByO_O_P(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, java.lang.String portletId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, java.lang.String portletId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_O_PI_First(
		long ownerId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_O_PI_First(
		long ownerId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_O_PI_Last(
		long ownerId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_O_PI_Last(
		long ownerId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences[] findByO_O_PI_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByO_O_PI(long ownerId, int ownerType,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countByO_O_PI(long ownerId, int ownerType,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P_P(
		int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P_P(
		int ownerType, long plid, java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P_P(
		int ownerType, long plid, java.lang.String portletId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_P_P_First(
		int ownerType, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_P_P_First(
		int ownerType, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_P_P_Last(
		int ownerType, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_P_P_Last(
		int ownerType, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences[] findByO_P_P_PrevAndNext(
		long portletPreferencesId, int ownerType, long plid,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByO_P_P(int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countByO_P_P(int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletPreferencesException} if it could not be found.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the portlet preferences that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences removeByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countByO_O_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the portlet preferences in the entity cache if it is enabled.
	*
	* @param portletPreferences the portlet preferences
	*/
	public void cacheResult(
		com.liferay.portal.model.PortletPreferences portletPreferences);

	/**
	* Caches the portlet preferenceses in the entity cache if it is enabled.
	*
	* @param portletPreferenceses the portlet preferenceses
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.PortletPreferences> portletPreferenceses);

	/**
	* Creates a new portlet preferences with the primary key. Does not add the portlet preferences to the database.
	*
	* @param portletPreferencesId the primary key for the new portlet preferences
	* @return the new portlet preferences
	*/
	public com.liferay.portal.model.PortletPreferences create(
		long portletPreferencesId);

	/**
	* Removes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences that was removed
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences remove(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences updateImpl(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferences with the primary key or throws a {@link com.liferay.portal.NoSuchPortletPreferencesException} if it could not be found.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences findByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portlet preferences with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences, or <code>null</code> if a portlet preferences with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PortletPreferences fetchByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portlet preferenceses.
	*
	* @return the portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portlet preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @return the range of portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portlet preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PortletPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portlet preferenceses
	* @param end the upper bound of the range of portlet preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlet preferenceses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portlet preferenceses.
	*
	* @return the number of portlet preferenceses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}