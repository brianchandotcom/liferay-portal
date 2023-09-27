/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.service.persistence;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the layout utility page entry service. This utility wraps <code>com.liferay.layout.utility.page.service.persistence.impl.LayoutUtilityPageEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutUtilityPageEntryPersistence
 * @generated
 */
public class LayoutUtilityPageEntryUtil {

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
	public static void clearCache(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		getPersistence().clearCache(layoutUtilityPageEntry);
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
	public static Map<Serializable, LayoutUtilityPageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutUtilityPageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutUtilityPageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutUtilityPageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutUtilityPageEntry update(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		return getPersistence().update(layoutUtilityPageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutUtilityPageEntry update(
		LayoutUtilityPageEntry layoutUtilityPageEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(layoutUtilityPageEntry, serviceContext);
	}

	/**
	 * Returns all the layout utility page entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the layout utility page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByUuid_First(
			String uuid,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where uuid = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry[] findByUuid_PrevAndNext(
			long LayoutUtilityPageEntryId, String uuid,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			LayoutUtilityPageEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the layout utility page entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of layout utility page entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout utility page entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the layout utility page entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout utility page entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout utility page entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the layout utility page entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout utility page entry that was removed
	 */
	public static LayoutUtilityPageEntry removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of layout utility page entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout utility page entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry[] findByUuid_C_PrevAndNext(
			long LayoutUtilityPageEntryId, String uuid, long companyId,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			LayoutUtilityPageEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the layout utility page entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of layout utility page entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout utility page entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the layout utility page entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the layout utility page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByGroupId_First(
			long groupId,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where groupId = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry[] findByGroupId_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			LayoutUtilityPageEntryId, groupId, orderByComparator);
	}

	/**
	 * Returns all the layout utility page entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByGroupId(
		long groupId) {

		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	 * Returns a range of all the layout utility page entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByGroupId(
		long groupId, int start, int end) {

		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set of layout utility page entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry[] filterFindByGroupId_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().filterFindByGroupId_PrevAndNext(
			LayoutUtilityPageEntryId, groupId, orderByComparator);
	}

	/**
	 * Removes all the layout utility page entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of layout utility page entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout utility page entries
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the number of layout utility page entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout utility page entries that the user has permission to view
	 */
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	 * Returns the layout utility page entry where plid = &#63; or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param plid the plid
	 * @return the matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByPlid(long plid)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByPlid(plid);
	}

	/**
	 * Returns the layout utility page entry where plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param plid the plid
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByPlid(long plid) {
		return getPersistence().fetchByPlid(plid);
	}

	/**
	 * Returns the layout utility page entry where plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByPlid(
		long plid, boolean useFinderCache) {

		return getPersistence().fetchByPlid(plid, useFinderCache);
	}

	/**
	 * Removes the layout utility page entry where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 * @return the layout utility page entry that was removed
	 */
	public static LayoutUtilityPageEntry removeByPlid(long plid)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().removeByPlid(plid);
	}

	/**
	 * Returns the number of layout utility page entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout utility page entries
	 */
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	 * Returns all the layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByG_T(
		long groupId, String type) {

		return getPersistence().findByG_T(groupId, type);
	}

	/**
	 * Returns a range of all the layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByG_T(
		long groupId, String type, int start, int end) {

		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByG_T(
		long groupId, String type, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByG_T(
		long groupId, String type, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_T(
			groupId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByG_T_First(
			long groupId, String type,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByG_T_First(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByG_T_First(
		long groupId, String type,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByG_T_First(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByG_T_Last(
			long groupId, String type,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByG_T_Last(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByG_T_Last(
		long groupId, String type,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByG_T_Last(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry[] findByG_T_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId, String type,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByG_T_PrevAndNext(
			LayoutUtilityPageEntryId, groupId, type, orderByComparator);
	}

	/**
	 * Returns all the layout utility page entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByG_T(
		long groupId, String type) {

		return getPersistence().filterFindByG_T(groupId, type);
	}

	/**
	 * Returns a range of all the layout utility page entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByG_T(
		long groupId, String type, int start, int end) {

		return getPersistence().filterFindByG_T(groupId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByG_T(
		long groupId, String type, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().filterFindByG_T(
			groupId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set of layout utility page entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry[] filterFindByG_T_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId, String type,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().filterFindByG_T_PrevAndNext(
			LayoutUtilityPageEntryId, groupId, type, orderByComparator);
	}

	/**
	 * Removes all the layout utility page entries where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public static void removeByG_T(long groupId, String type) {
		getPersistence().removeByG_T(groupId, type);
	}

	/**
	 * Returns the number of layout utility page entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout utility page entries
	 */
	public static int countByG_T(long groupId, String type) {
		return getPersistence().countByG_T(groupId, type);
	}

	/**
	 * Returns the number of layout utility page entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout utility page entries that the user has permission to view
	 */
	public static int filterCountByG_T(long groupId, String type) {
		return getPersistence().filterCountByG_T(groupId, type);
	}

	/**
	 * Returns all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @return the matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type) {

		return getPersistence().findByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type);
	}

	/**
	 * Returns a range of all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end) {

		return getPersistence().findByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type, start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().findByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByG_D_T_First(
			long groupId, boolean defaultLayoutUtilityPageEntry, String type,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByG_D_T_First(
			groupId, defaultLayoutUtilityPageEntry, type, orderByComparator);
	}

	/**
	 * Returns the first layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByG_D_T_First(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByG_D_T_First(
			groupId, defaultLayoutUtilityPageEntry, type, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByG_D_T_Last(
			long groupId, boolean defaultLayoutUtilityPageEntry, String type,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByG_D_T_Last(
			groupId, defaultLayoutUtilityPageEntry, type, orderByComparator);
	}

	/**
	 * Returns the last layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByG_D_T_Last(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().fetchByG_D_T_Last(
			groupId, defaultLayoutUtilityPageEntry, type, orderByComparator);
	}

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry[] findByG_D_T_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId,
			boolean defaultLayoutUtilityPageEntry, String type,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByG_D_T_PrevAndNext(
			LayoutUtilityPageEntryId, groupId, defaultLayoutUtilityPageEntry,
			type, orderByComparator);
	}

	/**
	 * Returns all the layout utility page entries that the user has permission to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @return the matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type) {

		return getPersistence().filterFindByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type);
	}

	/**
	 * Returns a range of all the layout utility page entries that the user has permission to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end) {

		return getPersistence().filterFindByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type, start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries that the user has permissions to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout utility page entries that the user has permission to view
	 */
	public static List<LayoutUtilityPageEntry> filterFindByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type,
		int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().filterFindByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type, start, end,
			orderByComparator);
	}

	/**
	 * Returns the layout utility page entries before and after the current layout utility page entry in the ordered set of layout utility page entries that the user has permission to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the current layout utility page entry
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry[] filterFindByG_D_T_PrevAndNext(
			long LayoutUtilityPageEntryId, long groupId,
			boolean defaultLayoutUtilityPageEntry, String type,
			OrderByComparator<LayoutUtilityPageEntry> orderByComparator)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().filterFindByG_D_T_PrevAndNext(
			LayoutUtilityPageEntryId, groupId, defaultLayoutUtilityPageEntry,
			type, orderByComparator);
	}

	/**
	 * Removes all the layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 */
	public static void removeByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type) {

		getPersistence().removeByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type);
	}

	/**
	 * Returns the number of layout utility page entries where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @return the number of matching layout utility page entries
	 */
	public static int countByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type) {

		return getPersistence().countByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type);
	}

	/**
	 * Returns the number of layout utility page entries that the user has permission to view where groupId = &#63; and defaultLayoutUtilityPageEntry = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultLayoutUtilityPageEntry the default layout utility page entry
	 * @param type the type
	 * @return the number of matching layout utility page entries that the user has permission to view
	 */
	public static int filterCountByG_D_T(
		long groupId, boolean defaultLayoutUtilityPageEntry, String type) {

		return getPersistence().filterCountByG_D_T(
			groupId, defaultLayoutUtilityPageEntry, type);
	}

	/**
	 * Returns the layout utility page entry where groupId = &#63; and name = &#63; and type = &#63; or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByG_N_T(
			long groupId, String name, String type)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByG_N_T(groupId, name, type);
	}

	/**
	 * Returns the layout utility page entry where groupId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByG_N_T(
		long groupId, String name, String type) {

		return getPersistence().fetchByG_N_T(groupId, name, type);
	}

	/**
	 * Returns the layout utility page entry where groupId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByG_N_T(
		long groupId, String name, String type, boolean useFinderCache) {

		return getPersistence().fetchByG_N_T(
			groupId, name, type, useFinderCache);
	}

	/**
	 * Removes the layout utility page entry where groupId = &#63; and name = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the layout utility page entry that was removed
	 */
	public static LayoutUtilityPageEntry removeByG_N_T(
			long groupId, String name, String type)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().removeByG_N_T(groupId, name, type);
	}

	/**
	 * Returns the number of layout utility page entries where groupId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout utility page entries
	 */
	public static int countByG_N_T(long groupId, String name, String type) {
		return getPersistence().countByG_N_T(groupId, name, type);
	}

	/**
	 * Returns the layout utility page entry where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry findByERC_G(
			String externalReferenceCode, long groupId)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByERC_G(externalReferenceCode, groupId);
	}

	/**
	 * Returns the layout utility page entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return getPersistence().fetchByERC_G(externalReferenceCode, groupId);
	}

	/**
	 * Returns the layout utility page entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout utility page entry, or <code>null</code> if a matching layout utility page entry could not be found
	 */
	public static LayoutUtilityPageEntry fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByERC_G(
			externalReferenceCode, groupId, useFinderCache);
	}

	/**
	 * Removes the layout utility page entry where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the layout utility page entry that was removed
	 */
	public static LayoutUtilityPageEntry removeByERC_G(
			String externalReferenceCode, long groupId)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().removeByERC_G(externalReferenceCode, groupId);
	}

	/**
	 * Returns the number of layout utility page entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching layout utility page entries
	 */
	public static int countByERC_G(String externalReferenceCode, long groupId) {
		return getPersistence().countByERC_G(externalReferenceCode, groupId);
	}

	/**
	 * Caches the layout utility page entry in the entity cache if it is enabled.
	 *
	 * @param layoutUtilityPageEntry the layout utility page entry
	 */
	public static void cacheResult(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		getPersistence().cacheResult(layoutUtilityPageEntry);
	}

	/**
	 * Caches the layout utility page entries in the entity cache if it is enabled.
	 *
	 * @param layoutUtilityPageEntries the layout utility page entries
	 */
	public static void cacheResult(
		List<LayoutUtilityPageEntry> layoutUtilityPageEntries) {

		getPersistence().cacheResult(layoutUtilityPageEntries);
	}

	/**
	 * Creates a new layout utility page entry with the primary key. Does not add the layout utility page entry to the database.
	 *
	 * @param LayoutUtilityPageEntryId the primary key for the new layout utility page entry
	 * @return the new layout utility page entry
	 */
	public static LayoutUtilityPageEntry create(long LayoutUtilityPageEntryId) {
		return getPersistence().create(LayoutUtilityPageEntryId);
	}

	/**
	 * Removes the layout utility page entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the layout utility page entry
	 * @return the layout utility page entry that was removed
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry remove(long LayoutUtilityPageEntryId)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().remove(LayoutUtilityPageEntryId);
	}

	public static LayoutUtilityPageEntry updateImpl(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		return getPersistence().updateImpl(layoutUtilityPageEntry);
	}

	/**
	 * Returns the layout utility page entry with the primary key or throws a <code>NoSuchLayoutUtilityPageEntryException</code> if it could not be found.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the layout utility page entry
	 * @return the layout utility page entry
	 * @throws NoSuchLayoutUtilityPageEntryException if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry findByPrimaryKey(
			long LayoutUtilityPageEntryId)
		throws com.liferay.layout.utility.page.exception.
			NoSuchLayoutUtilityPageEntryException {

		return getPersistence().findByPrimaryKey(LayoutUtilityPageEntryId);
	}

	/**
	 * Returns the layout utility page entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param LayoutUtilityPageEntryId the primary key of the layout utility page entry
	 * @return the layout utility page entry, or <code>null</code> if a layout utility page entry with the primary key could not be found
	 */
	public static LayoutUtilityPageEntry fetchByPrimaryKey(
		long LayoutUtilityPageEntryId) {

		return getPersistence().fetchByPrimaryKey(LayoutUtilityPageEntryId);
	}

	/**
	 * Returns all the layout utility page entries.
	 *
	 * @return the layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout utility page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @return the range of layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout utility page entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutUtilityPageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout utility page entries
	 * @param end the upper bound of the range of layout utility page entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout utility page entries
	 */
	public static List<LayoutUtilityPageEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the layout utility page entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout utility page entries.
	 *
	 * @return the number of layout utility page entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutUtilityPageEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		LayoutUtilityPageEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile LayoutUtilityPageEntryPersistence _persistence;

}