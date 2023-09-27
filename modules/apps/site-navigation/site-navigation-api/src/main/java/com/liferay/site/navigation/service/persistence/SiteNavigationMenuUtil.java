/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.site.navigation.model.SiteNavigationMenu;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the site navigation menu service. This utility wraps <code>com.liferay.site.navigation.service.persistence.impl.SiteNavigationMenuPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuPersistence
 * @generated
 */
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
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, SiteNavigationMenu> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
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

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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
	 * Returns all the site navigation menus where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the site navigation menus where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByUuid_First(
			String uuid,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByUuid_First(
		String uuid, OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByUuid_Last(
			String uuid,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByUuid_Last(
		String uuid, OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	public static SiteNavigationMenu[] findByUuid_PrevAndNext(
			long siteNavigationMenuId, String uuid,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByUuid_PrevAndNext(
			siteNavigationMenuId, uuid, orderByComparator);
	}

	/**
	 * Removes all the site navigation menus where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of site navigation menus where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching site navigation menus
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the site navigation menu where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchMenuException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByUUID_G(String uuid, long groupId)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the site navigation menu where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the site navigation menu where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the site navigation menu where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the site navigation menu that was removed
	 */
	public static SiteNavigationMenu removeByUUID_G(String uuid, long groupId)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of site navigation menus where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching site navigation menus
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	public static SiteNavigationMenu[] findByUuid_C_PrevAndNext(
			long siteNavigationMenuId, String uuid, long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByUuid_C_PrevAndNext(
			siteNavigationMenuId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the site navigation menus where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching site navigation menus
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByGroupId_First(
			long groupId,
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
	public static SiteNavigationMenu fetchByGroupId_First(
		long groupId, OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByGroupId_Last(
			long groupId,
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
	public static SiteNavigationMenu fetchByGroupId_Last(
		long groupId, OrderByComparator<SiteNavigationMenu> orderByComparator) {

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

		return getPersistence().findByGroupId_PrevAndNext(
			siteNavigationMenuId, groupId, orderByComparator);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByGroupId(
		long groupId, int start, int end) {

		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupId, start, end, orderByComparator);
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

		return getPersistence().filterFindByGroupId_PrevAndNext(
			siteNavigationMenuId, groupId, orderByComparator);
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByGroupId(
		long[] groupIds) {

		return getPersistence().filterFindByGroupId(groupIds);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByGroupId(
		long[] groupIds, int start, int end) {

		return getPersistence().filterFindByGroupId(groupIds, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupIds, start, end, orderByComparator);
	}

	/**
	 * Returns all the site navigation menus where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByGroupId(long[] groupIds) {
		return getPersistence().findByGroupId(groupIds);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByGroupId(
		long[] groupIds, int start, int end) {

		return getPersistence().findByGroupId(groupIds, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByGroupId(
			groupIds, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupIds, start, end, orderByComparator, useFinderCache);
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
	 * Returns the number of site navigation menus where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching site navigation menus
	 */
	public static int countByGroupId(long[] groupIds) {
		return getPersistence().countByGroupId(groupIds);
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
	 * Returns the number of site navigation menus that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	public static int filterCountByGroupId(long[] groupIds) {
		return getPersistence().filterCountByGroupId(groupIds);
	}

	/**
	 * Returns all the site navigation menus where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the site navigation menus where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByCompanyId_First(
			long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByCompanyId_First(
		long companyId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByCompanyId_Last(
			long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where companyId = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	public static SiteNavigationMenu[] findByCompanyId_PrevAndNext(
			long siteNavigationMenuId, long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByCompanyId_PrevAndNext(
			siteNavigationMenuId, companyId, orderByComparator);
	}

	/**
	 * Removes all the site navigation menus where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of site navigation menus where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching site navigation menus
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the site navigation menu where groupId = &#63; and name = &#63; or throws a <code>NoSuchMenuException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu findByG_N(long groupId, String name)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByG_N(groupId, name);
	}

	/**
	 * Returns the site navigation menu where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByG_N(long groupId, String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	 * Returns the site navigation menu where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByG_N(
		long groupId, String name, boolean useFinderCache) {

		return getPersistence().fetchByG_N(groupId, name, useFinderCache);
	}

	/**
	 * Removes the site navigation menu where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the site navigation menu that was removed
	 */
	public static SiteNavigationMenu removeByG_N(long groupId, String name)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching site navigation menus
	 */
	public static int countByG_N(long groupId, String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	 * Returns all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_LikeN(
		long groupId, String name) {

		return getPersistence().findByG_LikeN(groupId, name);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_LikeN(
		long groupId, String name, int start, int end) {

		return getPersistence().findByG_LikeN(groupId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_LikeN(
			groupId, name, start, end, orderByComparator, useFinderCache);
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
	public static SiteNavigationMenu findByG_LikeN_First(
			long groupId, String name,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByG_LikeN_First(
			groupId, name, orderByComparator);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByG_LikeN_First(
		long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByG_LikeN_First(
			groupId, name, orderByComparator);
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
	public static SiteNavigationMenu findByG_LikeN_Last(
			long groupId, String name,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByG_LikeN_Last(
			groupId, name, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByG_LikeN_Last(
		long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByG_LikeN_Last(
			groupId, name, orderByComparator);
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
	public static SiteNavigationMenu[] findByG_LikeN_PrevAndNext(
			long siteNavigationMenuId, long groupId, String name,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByG_LikeN_PrevAndNext(
			siteNavigationMenuId, groupId, name, orderByComparator);
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_LikeN(
		long groupId, String name) {

		return getPersistence().filterFindByG_LikeN(groupId, name);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_LikeN(
		long groupId, String name, int start, int end) {

		return getPersistence().filterFindByG_LikeN(groupId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().filterFindByG_LikeN(
			groupId, name, start, end, orderByComparator);
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
	public static SiteNavigationMenu[] filterFindByG_LikeN_PrevAndNext(
			long siteNavigationMenuId, long groupId, String name,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().filterFindByG_LikeN_PrevAndNext(
			siteNavigationMenuId, groupId, name, orderByComparator);
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_LikeN(
		long[] groupIds, String name) {

		return getPersistence().filterFindByG_LikeN(groupIds, name);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_LikeN(
		long[] groupIds, String name, int start, int end) {

		return getPersistence().filterFindByG_LikeN(groupIds, name, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_LikeN(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().filterFindByG_LikeN(
			groupIds, name, start, end, orderByComparator);
	}

	/**
	 * Returns all the site navigation menus where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_LikeN(
		long[] groupIds, String name) {

		return getPersistence().findByG_LikeN(groupIds, name);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_LikeN(
		long[] groupIds, String name, int start, int end) {

		return getPersistence().findByG_LikeN(groupIds, name, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_LikeN(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByG_LikeN(
			groupIds, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_LikeN(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_LikeN(
			groupIds, name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the site navigation menus where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	public static void removeByG_LikeN(long groupId, String name) {
		getPersistence().removeByG_LikeN(groupId, name);
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching site navigation menus
	 */
	public static int countByG_LikeN(long groupId, String name) {
		return getPersistence().countByG_LikeN(groupId, name);
	}

	/**
	 * Returns the number of site navigation menus where groupId = any &#63; and name LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the number of matching site navigation menus
	 */
	public static int countByG_LikeN(long[] groupIds, String name) {
		return getPersistence().countByG_LikeN(groupIds, name);
	}

	/**
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	public static int filterCountByG_LikeN(long groupId, String name) {
		return getPersistence().filterCountByG_LikeN(groupId, name);
	}

	/**
	 * Returns the number of site navigation menus that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	public static int filterCountByG_LikeN(long[] groupIds, String name) {
		return getPersistence().filterCountByG_LikeN(groupIds, name);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_T(
		long groupId, int type, int start, int end) {

		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_T(
			groupId, type, start, end, orderByComparator, useFinderCache);
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
	public static SiteNavigationMenu findByG_T_First(
			long groupId, int type,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByG_T_First(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByG_T_First(
		long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByG_T_First(
			groupId, type, orderByComparator);
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
	public static SiteNavigationMenu findByG_T_Last(
			long groupId, int type,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByG_T_Last(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByG_T_Last(
		long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByG_T_Last(
			groupId, type, orderByComparator);
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

		return getPersistence().findByG_T_PrevAndNext(
			siteNavigationMenuId, groupId, type, orderByComparator);
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_T(
		long groupId, int type) {

		return getPersistence().filterFindByG_T(groupId, type);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_T(
		long groupId, int type, int start, int end) {

		return getPersistence().filterFindByG_T(groupId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().filterFindByG_T(
			groupId, type, start, end, orderByComparator);
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

		return getPersistence().filterFindByG_T_PrevAndNext(
			siteNavigationMenuId, groupId, type, orderByComparator);
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
	public static List<SiteNavigationMenu> findByG_A(
		long groupId, boolean auto) {

		return getPersistence().findByG_A(groupId, auto);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_A(
		long groupId, boolean auto, int start, int end) {

		return getPersistence().findByG_A(groupId, auto, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_A(
		long groupId, boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findByG_A(
			groupId, auto, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	public static List<SiteNavigationMenu> findByG_A(
		long groupId, boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_A(
			groupId, auto, start, end, orderByComparator, useFinderCache);
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
	public static SiteNavigationMenu findByG_A_First(
			long groupId, boolean auto,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByG_A_First(
			groupId, auto, orderByComparator);
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByG_A_First(
		long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByG_A_First(
			groupId, auto, orderByComparator);
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
	public static SiteNavigationMenu findByG_A_Last(
			long groupId, boolean auto,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws com.liferay.site.navigation.exception.NoSuchMenuException {

		return getPersistence().findByG_A_Last(
			groupId, auto, orderByComparator);
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	public static SiteNavigationMenu fetchByG_A_Last(
		long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().fetchByG_A_Last(
			groupId, auto, orderByComparator);
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

		return getPersistence().findByG_A_PrevAndNext(
			siteNavigationMenuId, groupId, auto, orderByComparator);
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_A(
		long groupId, boolean auto) {

		return getPersistence().filterFindByG_A(groupId, auto);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_A(
		long groupId, boolean auto, int start, int end) {

		return getPersistence().filterFindByG_A(groupId, auto, start, end);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	public static List<SiteNavigationMenu> filterFindByG_A(
		long groupId, boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().filterFindByG_A(
			groupId, auto, start, end, orderByComparator);
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

		return getPersistence().filterFindByG_A_PrevAndNext(
			siteNavigationMenuId, groupId, auto, orderByComparator);
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
	public static void cacheResult(
		List<SiteNavigationMenu> siteNavigationMenus) {

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
	 * Returns the site navigation menu with the primary key or throws a <code>NoSuchMenuException</code> if it could not be found.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site navigation menus
	 */
	public static List<SiteNavigationMenu> findAll(
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the site navigation menus.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of site navigation menus
	 */
	public static List<SiteNavigationMenu> findAll(
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
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

	public static SiteNavigationMenuPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		SiteNavigationMenuPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile SiteNavigationMenuPersistence _persistence;

}