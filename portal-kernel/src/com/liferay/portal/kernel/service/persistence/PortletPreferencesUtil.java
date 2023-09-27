/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the portlet preferences service. This utility wraps <code>com.liferay.portal.service.persistence.impl.PortletPreferencesPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesPersistence
 * @generated
 */
public class PortletPreferencesUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(PortletPreferences portletPreferences) {
		getPersistence().clearCache(portletPreferences);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, PortletPreferences> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PortletPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PortletPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PortletPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PortletPreferences update(
		PortletPreferences portletPreferences) {

		return getPersistence().update(portletPreferences);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PortletPreferences update(
		PortletPreferences portletPreferences, ServiceContext serviceContext) {

		return getPersistence().update(portletPreferences, serviceContext);
	}

	/**
	 * Returns all the portlet preferenceses where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @return the matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByOwnerId(long ownerId) {
		return getPersistence().findByOwnerId(ownerId);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByOwnerId(
		long ownerId, int start, int end) {

		return getPersistence().findByOwnerId(ownerId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByOwnerId(
		long ownerId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByOwnerId(
			ownerId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByOwnerId(
		long ownerId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOwnerId(
			ownerId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByOwnerId_First(
			long ownerId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByOwnerId_First(ownerId, orderByComparator);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByOwnerId_First(
		long ownerId, OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByOwnerId_First(
			ownerId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByOwnerId_Last(
			long ownerId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByOwnerId_Last(ownerId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByOwnerId_Last(
		long ownerId, OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByOwnerId_Last(ownerId, orderByComparator);
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where ownerId = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences[] findByOwnerId_PrevAndNext(
			long portletPreferencesId, long ownerId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByOwnerId_PrevAndNext(
			portletPreferencesId, ownerId, orderByComparator);
	}

	/**
	 * Removes all the portlet preferenceses where ownerId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 */
	public static void removeByOwnerId(long ownerId) {
		getPersistence().removeByOwnerId(ownerId);
	}

	/**
	 * Returns the number of portlet preferenceses where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @return the number of matching portlet preferenceses
	 */
	public static int countByOwnerId(long ownerId) {
		return getPersistence().countByOwnerId(ownerId);
	}

	/**
	 * Returns all the portlet preferenceses where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByPlid(long plid) {
		return getPersistence().findByPlid(plid);
	}

	/**
	 * Returns a range of all the portlet preferenceses where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByPlid(
		long plid, int start, int end) {

		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByPlid(
		long plid, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByPlid(
		long plid, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPlid(
			plid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByPlid_First(
			long plid, OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByPlid_First(
		long plid, OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByPlid_Last(
			long plid, OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByPlid_Last(
		long plid, OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where plid = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences[] findByPlid_PrevAndNext(
			long portletPreferencesId, long plid,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByPlid_PrevAndNext(
			portletPreferencesId, plid, orderByComparator);
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
	public static List<PortletPreferences> findByPortletId(String portletId) {
		return getPersistence().findByPortletId(portletId);
	}

	/**
	 * Returns a range of all the portlet preferenceses where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByPortletId(
		String portletId, int start, int end) {

		return getPersistence().findByPortletId(portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByPortletId(
			portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPortletId(
			portletId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByPortletId_First(
			String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByPortletId_First(
			portletId, orderByComparator);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByPortletId_First(
		String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByPortletId_First(
			portletId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByPortletId_Last(
			String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByPortletId_Last(
			portletId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByPortletId_Last(
		String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByPortletId_Last(
			portletId, orderByComparator);
	}

	/**
	 * Returns the portlet preferenceses before and after the current portlet preferences in the ordered set where portletId = &#63;.
	 *
	 * @param portletPreferencesId the primary key of the current portlet preferences
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preferences
	 * @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences[] findByPortletId_PrevAndNext(
			long portletPreferencesId, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByPortletId_PrevAndNext(
			portletPreferencesId, portletId, orderByComparator);
	}

	/**
	 * Removes all the portlet preferenceses where portletId = &#63; from the database.
	 *
	 * @param portletId the portlet ID
	 */
	public static void removeByPortletId(String portletId) {
		getPersistence().removeByPortletId(portletId);
	}

	/**
	 * Returns the number of portlet preferenceses where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	public static int countByPortletId(String portletId) {
		return getPersistence().countByPortletId(portletId);
	}

	/**
	 * Returns all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_P(
		int ownerType, String portletId) {

		return getPersistence().findByO_P(ownerType, portletId);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_P(
		int ownerType, String portletId, int start, int end) {

		return getPersistence().findByO_P(ownerType, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_P(
		int ownerType, String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByO_P(
			ownerType, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_P(
		int ownerType, String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByO_P(
			ownerType, portletId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByO_P_First(
			int ownerType, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_P_First(
			ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByO_P_First(
		int ownerType, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByO_P_First(
			ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByO_P_Last(
			int ownerType, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_P_Last(
			ownerType, portletId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByO_P_Last(
		int ownerType, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByO_P_Last(
			ownerType, portletId, orderByComparator);
	}

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
	public static PortletPreferences[] findByO_P_PrevAndNext(
			long portletPreferencesId, int ownerType, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_P_PrevAndNext(
			portletPreferencesId, ownerType, portletId, orderByComparator);
	}

	/**
	 * Removes all the portlet preferenceses where ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public static void removeByO_P(int ownerType, String portletId) {
		getPersistence().removeByO_P(ownerType, portletId);
	}

	/**
	 * Returns the number of portlet preferenceses where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	public static int countByO_P(int ownerType, String portletId) {
		return getPersistence().countByO_P(ownerType, portletId);
	}

	/**
	 * Returns all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByP_P(
		long plid, String portletId) {

		return getPersistence().findByP_P(plid, portletId);
	}

	/**
	 * Returns a range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByP_P(
		long plid, String portletId, int start, int end) {

		return getPersistence().findByP_P(plid, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByP_P(
		long plid, String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByP_P(
			plid, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByP_P(
		long plid, String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByP_P(
			plid, portletId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByP_P_First(
			long plid, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByP_P_First(
			plid, portletId, orderByComparator);
	}

	/**
	 * Returns the first portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByP_P_First(
		long plid, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByP_P_First(
			plid, portletId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences
	 * @throws NoSuchPortletPreferencesException if a matching portlet preferences could not be found
	 */
	public static PortletPreferences findByP_P_Last(
			long plid, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByP_P_Last(
			plid, portletId, orderByComparator);
	}

	/**
	 * Returns the last portlet preferences in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByP_P_Last(
		long plid, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByP_P_Last(
			plid, portletId, orderByComparator);
	}

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
	public static PortletPreferences[] findByP_P_PrevAndNext(
			long portletPreferencesId, long plid, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByP_P_PrevAndNext(
			portletPreferencesId, plid, portletId, orderByComparator);
	}

	/**
	 * Removes all the portlet preferenceses where plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	public static void removeByP_P(long plid, String portletId) {
		getPersistence().removeByP_P(plid, portletId);
	}

	/**
	 * Returns the number of portlet preferenceses where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	public static int countByP_P(long plid, String portletId) {
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
	public static List<PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid) {

		return getPersistence().findByO_O_P(ownerId, ownerType, plid);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end) {

		return getPersistence().findByO_O_P(
			ownerId, ownerType, plid, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
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
	public static List<PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByO_O_P(
			ownerId, ownerType, plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByO_O_P(
			ownerId, ownerType, plid, start, end, orderByComparator,
			useFinderCache);
	}

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
	public static PortletPreferences findByO_O_P_First(
			long ownerId, int ownerType, long plid,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_O_P_First(
			ownerId, ownerType, plid, orderByComparator);
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
	public static PortletPreferences fetchByO_O_P_First(
		long ownerId, int ownerType, long plid,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByO_O_P_First(
			ownerId, ownerType, plid, orderByComparator);
	}

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
	public static PortletPreferences findByO_O_P_Last(
			long ownerId, int ownerType, long plid,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_O_P_Last(
			ownerId, ownerType, plid, orderByComparator);
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
	public static PortletPreferences fetchByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByO_O_P_Last(
			ownerId, ownerType, plid, orderByComparator);
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
	 * @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences[] findByO_O_P_PrevAndNext(
			long portletPreferencesId, long ownerId, int ownerType, long plid,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_O_P_PrevAndNext(
			portletPreferencesId, ownerId, ownerType, plid, orderByComparator);
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
	public static List<PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, String portletId) {

		return getPersistence().findByO_O_PI(ownerId, ownerType, portletId);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end) {

		return getPersistence().findByO_O_PI(
			ownerId, ownerType, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
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
	public static List<PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByO_O_PI(
			ownerId, ownerType, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByO_O_PI(
			ownerId, ownerType, portletId, start, end, orderByComparator,
			useFinderCache);
	}

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
	public static PortletPreferences findByO_O_PI_First(
			long ownerId, int ownerType, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_O_PI_First(
			ownerId, ownerType, portletId, orderByComparator);
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
	public static PortletPreferences fetchByO_O_PI_First(
		long ownerId, int ownerType, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByO_O_PI_First(
			ownerId, ownerType, portletId, orderByComparator);
	}

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
	public static PortletPreferences findByO_O_PI_Last(
			long ownerId, int ownerType, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_O_PI_Last(
			ownerId, ownerType, portletId, orderByComparator);
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
	public static PortletPreferences fetchByO_O_PI_Last(
		long ownerId, int ownerType, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByO_O_PI_Last(
			ownerId, ownerType, portletId, orderByComparator);
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
	 * @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences[] findByO_O_PI_PrevAndNext(
			long portletPreferencesId, long ownerId, int ownerType,
			String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_O_PI_PrevAndNext(
			portletPreferencesId, ownerId, ownerType, portletId,
			orderByComparator);
	}

	/**
	 * Removes all the portlet preferenceses where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public static void removeByO_O_PI(
		long ownerId, int ownerType, String portletId) {

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
	public static int countByO_O_PI(
		long ownerId, int ownerType, String portletId) {

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
	public static List<PortletPreferences> findByO_P_P(
		int ownerType, long plid, String portletId) {

		return getPersistence().findByO_P_P(ownerType, plid, portletId);
	}

	/**
	 * Returns a range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end) {

		return getPersistence().findByO_P_P(
			ownerType, plid, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
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
	public static List<PortletPreferences> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByO_P_P(
			ownerType, plid, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByO_P_P(
			ownerType, plid, portletId, start, end, orderByComparator,
			useFinderCache);
	}

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
	public static PortletPreferences findByO_P_P_First(
			int ownerType, long plid, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_P_P_First(
			ownerType, plid, portletId, orderByComparator);
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
	public static PortletPreferences fetchByO_P_P_First(
		int ownerType, long plid, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByO_P_P_First(
			ownerType, plid, portletId, orderByComparator);
	}

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
	public static PortletPreferences findByO_P_P_Last(
			int ownerType, long plid, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_P_P_Last(
			ownerType, plid, portletId, orderByComparator);
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
	public static PortletPreferences fetchByO_P_P_Last(
		int ownerType, long plid, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByO_P_P_Last(
			ownerType, plid, portletId, orderByComparator);
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
	 * @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences[] findByO_P_P_PrevAndNext(
			long portletPreferencesId, int ownerType, long plid,
			String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_P_P_PrevAndNext(
			portletPreferencesId, ownerType, plid, portletId,
			orderByComparator);
	}

	/**
	 * Removes all the portlet preferenceses where ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	public static void removeByO_P_P(
		int ownerType, long plid, String portletId) {

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
	public static int countByO_P_P(int ownerType, long plid, String portletId) {
		return getPersistence().countByO_P_P(ownerType, plid, portletId);
	}

	/**
	 * Returns all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	/**
	 * Returns a range of all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
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
	public static List<PortletPreferences> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
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
	public static List<PortletPreferences> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preferenceses
	 */
	public static List<PortletPreferences> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end,
			orderByComparator, useFinderCache);
	}

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
	public static PortletPreferences findByC_O_O_LikeP_First(
			long companyId, long ownerId, int ownerType, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByC_O_O_LikeP_First(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

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
	public static PortletPreferences fetchByC_O_O_LikeP_First(
		long companyId, long ownerId, int ownerType, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByC_O_O_LikeP_First(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

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
	public static PortletPreferences findByC_O_O_LikeP_Last(
			long companyId, long ownerId, int ownerType, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByC_O_O_LikeP_Last(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

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
	public static PortletPreferences fetchByC_O_O_LikeP_Last(
		long companyId, long ownerId, int ownerType, String portletId,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().fetchByC_O_O_LikeP_Last(
			companyId, ownerId, ownerType, portletId, orderByComparator);
	}

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
	public static PortletPreferences[] findByC_O_O_LikeP_PrevAndNext(
			long portletPreferencesId, long companyId, long ownerId,
			int ownerType, String portletId,
			OrderByComparator<PortletPreferences> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByC_O_O_LikeP_PrevAndNext(
			portletPreferencesId, companyId, ownerId, ownerType, portletId,
			orderByComparator);
	}

	/**
	 * Removes all the portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	public static void removeByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		getPersistence().removeByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

	/**
	 * Returns the number of portlet preferenceses where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching portlet preferenceses
	 */
	public static int countByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		return getPersistence().countByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);
	}

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
	public static PortletPreferences findByO_O_P_P(
			long ownerId, int ownerType, long plid, String portletId)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByO_O_P_P(
			ownerId, ownerType, plid, portletId);
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
	public static PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId) {

		return getPersistence().fetchByO_O_P_P(
			ownerId, ownerType, plid, portletId);
	}

	/**
	 * Returns the portlet preferences where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching portlet preferences, or <code>null</code> if a matching portlet preferences could not be found
	 */
	public static PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId,
		boolean useFinderCache) {

		return getPersistence().fetchByO_O_P_P(
			ownerId, ownerType, plid, portletId, useFinderCache);
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
	public static PortletPreferences removeByO_O_P_P(
			long ownerId, int ownerType, long plid, String portletId)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().removeByO_O_P_P(
			ownerId, ownerType, plid, portletId);
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
	public static int countByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId) {

		return getPersistence().countByO_O_P_P(
			ownerId, ownerType, plid, portletId);
	}

	/**
	 * Caches the portlet preferences in the entity cache if it is enabled.
	 *
	 * @param portletPreferences the portlet preferences
	 */
	public static void cacheResult(PortletPreferences portletPreferences) {
		getPersistence().cacheResult(portletPreferences);
	}

	/**
	 * Caches the portlet preferenceses in the entity cache if it is enabled.
	 *
	 * @param portletPreferenceses the portlet preferenceses
	 */
	public static void cacheResult(
		List<PortletPreferences> portletPreferenceses) {

		getPersistence().cacheResult(portletPreferenceses);
	}

	/**
	 * Creates a new portlet preferences with the primary key. Does not add the portlet preferences to the database.
	 *
	 * @param portletPreferencesId the primary key for the new portlet preferences
	 * @return the new portlet preferences
	 */
	public static PortletPreferences create(long portletPreferencesId) {
		return getPersistence().create(portletPreferencesId);
	}

	/**
	 * Removes the portlet preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletPreferencesId the primary key of the portlet preferences
	 * @return the portlet preferences that was removed
	 * @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences remove(long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().remove(portletPreferencesId);
	}

	public static PortletPreferences updateImpl(
		PortletPreferences portletPreferences) {

		return getPersistence().updateImpl(portletPreferences);
	}

	/**
	 * Returns the portlet preferences with the primary key or throws a <code>NoSuchPortletPreferencesException</code> if it could not be found.
	 *
	 * @param portletPreferencesId the primary key of the portlet preferences
	 * @return the portlet preferences
	 * @throws NoSuchPortletPreferencesException if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences findByPrimaryKey(long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.
			NoSuchPortletPreferencesException {

		return getPersistence().findByPrimaryKey(portletPreferencesId);
	}

	/**
	 * Returns the portlet preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletPreferencesId the primary key of the portlet preferences
	 * @return the portlet preferences, or <code>null</code> if a portlet preferences with the primary key could not be found
	 */
	public static PortletPreferences fetchByPrimaryKey(
		long portletPreferencesId) {

		return getPersistence().fetchByPrimaryKey(portletPreferencesId);
	}

	/**
	 * Returns all the portlet preferenceses.
	 *
	 * @return the portlet preferenceses
	 */
	public static List<PortletPreferences> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the portlet preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @return the range of portlet preferenceses
	 */
	public static List<PortletPreferences> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlet preferenceses
	 */
	public static List<PortletPreferences> findAll(
		int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the portlet preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preferenceses
	 * @param end the upper bound of the range of portlet preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of portlet preferenceses
	 */
	public static List<PortletPreferences> findAll(
		int start, int end,
		OrderByComparator<PortletPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
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
		return _persistence;
	}

	public static void setPersistence(
		PortletPreferencesPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile PortletPreferencesPersistence _persistence;

}