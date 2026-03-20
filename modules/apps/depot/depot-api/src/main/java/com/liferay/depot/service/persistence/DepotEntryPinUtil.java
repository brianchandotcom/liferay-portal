/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.service.persistence;

import com.liferay.depot.model.DepotEntryPin;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the depot entry pin service. This utility wraps <code>com.liferay.depot.service.persistence.impl.DepotEntryPinPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryPinPersistence
 * @generated
 */
public class DepotEntryPinUtil {

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
	public static void clearCache(DepotEntryPin depotEntryPin) {
		getPersistence().clearCache(depotEntryPin);
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
	public static Map<Serializable, DepotEntryPin> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DepotEntryPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DepotEntryPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DepotEntryPin> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DepotEntryPin update(DepotEntryPin depotEntryPin) {
		return getPersistence().update(depotEntryPin);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DepotEntryPin update(
		DepotEntryPin depotEntryPin, ServiceContext serviceContext) {

		return getPersistence().update(depotEntryPin, serviceContext);
	}

	/**
	 * Returns all the depot entry pins where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin findByUuid_First(
			String uuid, OrderByComparator<DepotEntryPin> orderByComparator)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin fetchByUuid_First(
		String uuid, OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Removes all the depot entry pins where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of depot entry pins where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching depot entry pins
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin findByUUID_G(String uuid, long groupId)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the depot entry pin where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the depot entry pin that was removed
	 */
	public static DepotEntryPin removeByUUID_G(String uuid, long groupId)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of depot entry pins where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching depot entry pins
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DepotEntryPin> orderByComparator)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the depot entry pins where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching depot entry pins
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the depot entry pins where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUserId(
		long userId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByUserId(
		long userId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin findByUserId_First(
			long userId, OrderByComparator<DepotEntryPin> orderByComparator)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin fetchByUserId_First(
		long userId, OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Removes all the depot entry pins where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of depot entry pins where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching depot entry pins
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns all the depot entry pins where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pins
	 */
	public static List<DepotEntryPin> findByDepotEntryId(long depotEntryId) {
		return getPersistence().findByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns a range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return getPersistence().findByDepotEntryId(depotEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().findByDepotEntryId(
			depotEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	public static List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<DepotEntryPin> orderByComparator)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().findByDepotEntryId_First(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the first depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin fetchByDepotEntryId_First(
		long depotEntryId, OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().fetchByDepotEntryId_First(
			depotEntryId, orderByComparator);
	}

	/**
	 * Removes all the depot entry pins where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public static void removeByDepotEntryId(long depotEntryId) {
		getPersistence().removeByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns the number of depot entry pins where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot entry pins
	 */
	public static int countByDepotEntryId(long depotEntryId) {
		return getPersistence().countByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin findByU_D(long userId, long depotEntryId)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().findByU_D(userId, depotEntryId);
	}

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin fetchByU_D(long userId, long depotEntryId) {
		return getPersistence().fetchByU_D(userId, depotEntryId);
	}

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public static DepotEntryPin fetchByU_D(
		long userId, long depotEntryId, boolean useFinderCache) {

		return getPersistence().fetchByU_D(
			userId, depotEntryId, useFinderCache);
	}

	/**
	 * Removes the depot entry pin where userId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the depot entry pin that was removed
	 */
	public static DepotEntryPin removeByU_D(long userId, long depotEntryId)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().removeByU_D(userId, depotEntryId);
	}

	/**
	 * Returns the number of depot entry pins where userId = &#63; and depotEntryId = &#63;.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot entry pins
	 */
	public static int countByU_D(long userId, long depotEntryId) {
		return getPersistence().countByU_D(userId, depotEntryId);
	}

	/**
	 * Caches the depot entry pin in the entity cache if it is enabled.
	 *
	 * @param depotEntryPin the depot entry pin
	 */
	public static void cacheResult(DepotEntryPin depotEntryPin) {
		getPersistence().cacheResult(depotEntryPin);
	}

	/**
	 * Caches the depot entry pins in the entity cache if it is enabled.
	 *
	 * @param depotEntryPins the depot entry pins
	 */
	public static void cacheResult(List<DepotEntryPin> depotEntryPins) {
		getPersistence().cacheResult(depotEntryPins);
	}

	/**
	 * Creates a new depot entry pin with the primary key. Does not add the depot entry pin to the database.
	 *
	 * @param depotEntryPinId the primary key for the new depot entry pin
	 * @return the new depot entry pin
	 */
	public static DepotEntryPin create(long depotEntryPinId) {
		return getPersistence().create(depotEntryPinId);
	}

	/**
	 * Removes the depot entry pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin that was removed
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	public static DepotEntryPin remove(long depotEntryPinId)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().remove(depotEntryPinId);
	}

	public static DepotEntryPin updateImpl(DepotEntryPin depotEntryPin) {
		return getPersistence().updateImpl(depotEntryPin);
	}

	/**
	 * Returns the depot entry pin with the primary key or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	public static DepotEntryPin findByPrimaryKey(long depotEntryPinId)
		throws com.liferay.depot.exception.NoSuchEntryPinException {

		return getPersistence().findByPrimaryKey(depotEntryPinId);
	}

	/**
	 * Returns the depot entry pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin, or <code>null</code> if a depot entry pin with the primary key could not be found
	 */
	public static DepotEntryPin fetchByPrimaryKey(long depotEntryPinId) {
		return getPersistence().fetchByPrimaryKey(depotEntryPinId);
	}

	/**
	 * Returns all the depot entry pins.
	 *
	 * @return the depot entry pins
	 */
	public static List<DepotEntryPin> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of depot entry pins
	 */
	public static List<DepotEntryPin> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of depot entry pins
	 */
	public static List<DepotEntryPin> findAll(
		int start, int end,
		OrderByComparator<DepotEntryPin> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of depot entry pins
	 */
	public static List<DepotEntryPin> findAll(
		int start, int end, OrderByComparator<DepotEntryPin> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the depot entry pins from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of depot entry pins.
	 *
	 * @return the number of depot entry pins
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DepotEntryPinPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(DepotEntryPinPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile DepotEntryPinPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:1009747983