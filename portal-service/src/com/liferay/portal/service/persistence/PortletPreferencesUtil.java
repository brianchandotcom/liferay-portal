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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the portlet preferences service. This utility wraps {@link PortletPreferencesPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesPersistence
 * @see PortletPreferencesPersistenceImpl
 * @generated
 */
@ProviderType
public class PortletPreferencesUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(PortletPreferences portletPreferences) {
		getPersistence().clearCache(portletPreferences);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PortletPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PortletPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PortletPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static PortletPreferences update(
		PortletPreferences portletPreferences) {
		return getPersistence().update(portletPreferences);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static PortletPreferences update(
		PortletPreferences portletPreferences, ServiceContext serviceContext) {
		return getPersistence().update(portletPreferences, serviceContext);
	}

	/**
	* Returns all the portlet preferenceses where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching portlet preferenceses
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid) {
		return getPersistence().findByPlid(plid);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end) {
		return getPersistence().findByPlid(plid, start, end);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByPlid_PrevAndNext(portletPreferencesId, plid,
			orderByComparator);
	}

	/**
	* Removes all the portlet preferenceses where plid = &#63; from the database.
	*
	* @param plid the plid
	*/
	public static void removeByPlid(long plid) {
		getPersistence().removeByPlid(plid);
	}

	/**
	* Returns the number of portlet preferenceses where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching portlet preferenceses
	*/
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	* Returns all the portlet preferenceses where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPortletId(
		java.lang.String portletId) {
		return getPersistence().findByPortletId(portletId);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPortletId(
		java.lang.String portletId, int start, int end) {
		return getPersistence().findByPortletId(portletId, start, end);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByPortletId(
		java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByPortletId(portletId, start, end, orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByPortletId_First(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByPortletId_First(portletId, orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByPortletId_First(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByPortletId_First(portletId, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByPortletId_Last(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByPortletId_Last(portletId, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByPortletId_Last(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByPortletId_Last(portletId, orderByComparator);
	}

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences[] findByPortletId_PrevAndNext(
		long portletPreferencesId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByPortletId_PrevAndNext(portletPreferencesId,
			portletId, orderByComparator);
	}

	/**
	* Removes all the portlet preferenceses where portletId = &#63; from the database.
	*
	* @param portletId the portlet ID
	*/
	public static void removeByPortletId(java.lang.String portletId) {
		getPersistence().removeByPortletId(portletId);
	}

	/**
	* Returns the number of portlet preferenceses where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public static int countByPortletId(java.lang.String portletId) {
		return getPersistence().countByPortletId(portletId);
	}

	/**
	* Returns all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P(
		int ownerType, java.lang.String portletId) {
		return getPersistence().findByO_P(ownerType, portletId);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P(
		int ownerType, java.lang.String portletId, int start, int end) {
		return getPersistence().findByO_P(ownerType, portletId, start, end);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P(
		int ownerType, java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByO_P(ownerType, portletId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_P_First(
		int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_P_First(ownerType, portletId, orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_P_First(
		int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByO_P_First(ownerType, portletId, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_P_Last(
		int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_P_Last(ownerType, portletId, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_P_Last(
		int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByO_P_Last(ownerType, portletId, orderByComparator);
	}

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences[] findByO_P_PrevAndNext(
		long portletPreferencesId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_P_PrevAndNext(portletPreferencesId, ownerType,
			portletId, orderByComparator);
	}

	/**
	* Removes all the portlet preferenceses where ownerType = &#63; and portletId = &#63; from the database.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	*/
	public static void removeByO_P(int ownerType, java.lang.String portletId) {
		getPersistence().removeByO_P(ownerType, portletId);
	}

	/**
	* Returns the number of portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public static int countByO_P(int ownerType, java.lang.String portletId) {
		return getPersistence().countByO_P(ownerType, portletId);
	}

	/**
	* Returns all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId) {
		return getPersistence().findByP_P(plid, portletId);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end) {
		return getPersistence().findByP_P(plid, portletId, start, end);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByP_P(plid, portletId, start, end, orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByP_P_First(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByP_P_First(plid, portletId, orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByP_P_First(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByP_P_First(plid, portletId, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByP_P_Last(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByP_P_Last(plid, portletId, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByP_P_Last(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByP_P_Last(plid, portletId, orderByComparator);
	}

	/**
	* Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	*
	* @param portletPreferencesId the primary key of the current portlet preferences
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByP_P_PrevAndNext(portletPreferencesId, plid,
			portletId, orderByComparator);
	}

	/**
	* Removes all the portlet preferenceses where plid = &#63; and portletId = &#63; from the database.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	*/
	public static void removeByP_P(long plid, java.lang.String portletId) {
		getPersistence().removeByP_P(plid, portletId);
	}

	/**
	* Returns the number of portlet preferenceses where plid = &#63; and portletId = &#63;.
	*
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public static int countByP_P(long plid, java.lang.String portletId) {
		return getPersistence().countByP_P(plid, portletId);
	}

	/**
	* Returns all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @return the matching portlet preferenceses
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid) {
		return getPersistence().findByO_O_P(ownerId, ownerType, plid);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end) {
		return getPersistence().findByO_O_P(ownerId, ownerType, plid, start, end);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByO_O_P(ownerId, ownerType, plid, start, end,
			orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_O_P_First(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_O_P_First(ownerId, ownerType, plid,
			orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_O_P_First(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByO_O_P_First(ownerId, ownerType, plid,
			orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_O_P_Last(ownerId, ownerType, plid, orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByO_O_P_Last(ownerId, ownerType, plid,
			orderByComparator);
	}

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
	*/
	public static com.liferay.portal.model.PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_O_P_PrevAndNext(portletPreferencesId, ownerId,
			ownerType, plid, orderByComparator);
	}

	/**
	* Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	*/
	public static void removeByO_O_P(long ownerId, int ownerType, long plid) {
		getPersistence().removeByO_O_P(ownerId, ownerType, plid);
	}

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @return the number of matching portlet preferenceses
	*/
	public static int countByO_O_P(long ownerId, int ownerType, long plid) {
		return getPersistence().countByO_O_P(ownerId, ownerType, plid);
	}

	/**
	* Returns all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, java.lang.String portletId) {
		return getPersistence().findByO_O_PI(ownerId, ownerType, portletId);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, java.lang.String portletId, int start,
		int end) {
		return getPersistence()
				   .findByO_O_PI(ownerId, ownerType, portletId, start, end);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, java.lang.String portletId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByO_O_PI(ownerId, ownerType, portletId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_O_PI_First(
		long ownerId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_O_PI_First(ownerId, ownerType, portletId,
			orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_O_PI_First(
		long ownerId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByO_O_PI_First(ownerId, ownerType, portletId,
			orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_O_PI_Last(
		long ownerId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_O_PI_Last(ownerId, ownerType, portletId,
			orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_O_PI_Last(
		long ownerId, int ownerType, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByO_O_PI_Last(ownerId, ownerType, portletId,
			orderByComparator);
	}

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
	*/
	public static com.liferay.portal.model.PortletPreferences[] findByO_O_PI_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_O_PI_PrevAndNext(portletPreferencesId, ownerId,
			ownerType, portletId, orderByComparator);
	}

	/**
	* Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	*/
	public static void removeByO_O_PI(long ownerId, int ownerType,
		java.lang.String portletId) {
		getPersistence().removeByO_O_PI(ownerId, ownerType, portletId);
	}

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public static int countByO_O_PI(long ownerId, int ownerType,
		java.lang.String portletId) {
		return getPersistence().countByO_O_PI(ownerId, ownerType, portletId);
	}

	/**
	* Returns all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferenceses
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P_P(
		int ownerType, long plid, java.lang.String portletId) {
		return getPersistence().findByO_P_P(ownerType, plid, portletId);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P_P(
		int ownerType, long plid, java.lang.String portletId, int start, int end) {
		return getPersistence()
				   .findByO_P_P(ownerType, plid, portletId, start, end);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findByO_P_P(
		int ownerType, long plid, java.lang.String portletId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByO_P_P(ownerType, plid, portletId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_P_P_First(
		int ownerType, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_P_P_First(ownerType, plid, portletId,
			orderByComparator);
	}

	/**
	* Returns the first portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_P_P_First(
		int ownerType, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByO_P_P_First(ownerType, plid, portletId,
			orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_P_P_Last(
		int ownerType, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_P_P_Last(ownerType, plid, portletId,
			orderByComparator);
	}

	/**
	* Returns the last portlet preferences in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_P_P_Last(
		int ownerType, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByO_P_P_Last(ownerType, plid, portletId,
			orderByComparator);
	}

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
	*/
	public static com.liferay.portal.model.PortletPreferences[] findByO_P_P_PrevAndNext(
		long portletPreferencesId, int ownerType, long plid,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_P_P_PrevAndNext(portletPreferencesId, ownerType,
			plid, portletId, orderByComparator);
	}

	/**
	* Removes all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	*/
	public static void removeByO_P_P(int ownerType, long plid,
		java.lang.String portletId) {
		getPersistence().removeByO_P_P(ownerType, plid, portletId);
	}

	/**
	* Returns the number of portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public static int countByO_P_P(int ownerType, long plid,
		java.lang.String portletId) {
		return getPersistence().countByO_P_P(ownerType, plid, portletId);
	}

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletPreferencesException} if it could not be found.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .findByO_O_P_P(ownerId, ownerType, plid, portletId);
	}

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId) {
		return getPersistence()
				   .fetchByO_O_P_P(ownerId, ownerType, plid, portletId);
	}

	/**
	* Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByO_O_P_P(ownerId, ownerType, plid, portletId,
			retrieveFromCache);
	}

	/**
	* Removes the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the portlet preferences that was removed
	*/
	public static com.liferay.portal.model.PortletPreferences removeByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence()
				   .removeByO_O_P_P(ownerId, ownerType, plid, portletId);
	}

	/**
	* Returns the number of portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param plid the plid
	* @param portletId the portlet ID
	* @return the number of matching portlet preferenceses
	*/
	public static int countByO_O_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId) {
		return getPersistence()
				   .countByO_O_P_P(ownerId, ownerType, plid, portletId);
	}

	/**
	* Caches the portlet preferences in the entity cache if it is enabled.
	*
	* @param portletPreferences the portlet preferences
	*/
	public static void cacheResult(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		getPersistence().cacheResult(portletPreferences);
	}

	/**
	* Caches the portlet preferenceses in the entity cache if it is enabled.
	*
	* @param portletPreferenceses the portlet preferenceses
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PortletPreferences> portletPreferenceses) {
		getPersistence().cacheResult(portletPreferenceses);
	}

	/**
	* Creates a new portlet preferences with the primary key. Does not add the portlet preferences to the database.
	*
	* @param portletPreferencesId the primary key for the new portlet preferences
	* @return the new portlet preferences
	*/
	public static com.liferay.portal.model.PortletPreferences create(
		long portletPreferencesId) {
		return getPersistence().create(portletPreferencesId);
	}

	/**
	* Removes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences that was removed
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences remove(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().remove(portletPreferencesId);
	}

	public static com.liferay.portal.model.PortletPreferences updateImpl(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		return getPersistence().updateImpl(portletPreferences);
	}

	/**
	* Returns the portlet preferences with the primary key or throws a {@link com.liferay.portal.NoSuchPortletPreferencesException} if it could not be found.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences
	* @throws com.liferay.portal.NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences findByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByPrimaryKey(portletPreferencesId);
	}

	/**
	* Returns the portlet preferences with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portletPreferencesId the primary key of the portlet preferences
	* @return the portlet preferences, or <code>null</code> if a portlet preferences with the primary key could not be found
	*/
	public static com.liferay.portal.model.PortletPreferences fetchByPrimaryKey(
		long portletPreferencesId) {
		return getPersistence().fetchByPrimaryKey(portletPreferencesId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.PortletPreferences> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the portlet preferenceses.
	*
	* @return the portlet preferenceses
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findAll() {
		return getPersistence().findAll();
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	*/
	public static java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the portlet preferenceses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of portlet preferenceses.
	*
	* @return the number of portlet preferenceses
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PortletPreferencesPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortletPreferencesPersistence)PortalBeanLocatorUtil.locate(PortletPreferencesPersistence.class.getName());

			ReferenceRegistry.registerReference(PortletPreferencesUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(PortletPreferencesPersistence persistence) {
	}

	private static PortletPreferencesPersistence _persistence;
}