/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat730.model.ERCVersionedEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the erc versioned entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.compat730.service.persistence.impl.ERCVersionedEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntryPersistence
 * @generated
 */
public class ERCVersionedEntryUtil {

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
	public static void clearCache(ERCVersionedEntry ercVersionedEntry) {
		getPersistence().clearCache(ercVersionedEntry);
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
	public static Map<Serializable, ERCVersionedEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ERCVersionedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ERCVersionedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ERCVersionedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ERCVersionedEntry update(
		ERCVersionedEntry ercVersionedEntry) {

		return getPersistence().update(ercVersionedEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ERCVersionedEntry update(
		ERCVersionedEntry ercVersionedEntry, ServiceContext serviceContext) {

		return getPersistence().update(ercVersionedEntry, serviceContext);
	}

	/**
	 * Returns all the erc versioned entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching erc versioned entries
	 */
	public static List<ERCVersionedEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	public static List<ERCVersionedEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	public static List<ERCVersionedEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	public static List<ERCVersionedEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry findByUuid_First(
			String uuid, OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry fetchByUuid_First(
		String uuid, OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry findByUuid_Last(
			String uuid, OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry fetchByUuid_Last(
		String uuid, OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	public static ERCVersionedEntry[] findByUuid_PrevAndNext(
			long ercVersionedEntryId, String uuid,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			ercVersionedEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching erc versioned entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the erc versioned entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the erc versioned entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the erc versioned entry that was removed
	 */
	public static ERCVersionedEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching erc versioned entries
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching erc versioned entries
	 */
	public static List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of matching erc versioned entries
	 */
	public static List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entries
	 */
	public static List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entries
	 */
	public static List<ERCVersionedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the erc versioned entries before and after the current erc versioned entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ercVersionedEntryId the primary key of the current erc versioned entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	public static ERCVersionedEntry[] findByUuid_C_PrevAndNext(
			long ercVersionedEntryId, String uuid, long companyId,
			OrderByComparator<ERCVersionedEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			ercVersionedEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of erc versioned entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching erc versioned entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the erc versioned entry where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry findByG_ERC(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the erc versioned entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode) {

		return getPersistence().fetchByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the erc versioned entry where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	public static ERCVersionedEntry fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByG_ERC(
			groupId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the erc versioned entry where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc versioned entry that was removed
	 */
	public static ERCVersionedEntry removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().removeByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the number of erc versioned entries where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc versioned entries
	 */
	public static int countByG_ERC(long groupId, String externalReferenceCode) {
		return getPersistence().countByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Caches the erc versioned entry in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntry the erc versioned entry
	 */
	public static void cacheResult(ERCVersionedEntry ercVersionedEntry) {
		getPersistence().cacheResult(ercVersionedEntry);
	}

	/**
	 * Caches the erc versioned entries in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntries the erc versioned entries
	 */
	public static void cacheResult(
		List<ERCVersionedEntry> ercVersionedEntries) {

		getPersistence().cacheResult(ercVersionedEntries);
	}

	/**
	 * Creates a new erc versioned entry with the primary key. Does not add the erc versioned entry to the database.
	 *
	 * @param ercVersionedEntryId the primary key for the new erc versioned entry
	 * @return the new erc versioned entry
	 */
	public static ERCVersionedEntry create(long ercVersionedEntryId) {
		return getPersistence().create(ercVersionedEntryId);
	}

	/**
	 * Removes the erc versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry that was removed
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	public static ERCVersionedEntry remove(long ercVersionedEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().remove(ercVersionedEntryId);
	}

	public static ERCVersionedEntry updateImpl(
		ERCVersionedEntry ercVersionedEntry) {

		return getPersistence().updateImpl(ercVersionedEntry);
	}

	/**
	 * Returns the erc versioned entry with the primary key or throws a <code>NoSuchERCVersionedEntryException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry
	 * @throws NoSuchERCVersionedEntryException if a erc versioned entry with the primary key could not be found
	 */
	public static ERCVersionedEntry findByPrimaryKey(long ercVersionedEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat730.
			exception.NoSuchERCVersionedEntryException {

		return getPersistence().findByPrimaryKey(ercVersionedEntryId);
	}

	/**
	 * Returns the erc versioned entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry, or <code>null</code> if a erc versioned entry with the primary key could not be found
	 */
	public static ERCVersionedEntry fetchByPrimaryKey(
		long ercVersionedEntryId) {

		return getPersistence().fetchByPrimaryKey(ercVersionedEntryId);
	}

	/**
	 * Returns all the erc versioned entries.
	 *
	 * @return the erc versioned entries
	 */
	public static List<ERCVersionedEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of erc versioned entries
	 */
	public static List<ERCVersionedEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc versioned entries
	 */
	public static List<ERCVersionedEntry> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc versioned entries
	 */
	public static List<ERCVersionedEntry> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the erc versioned entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of erc versioned entries.
	 *
	 * @return the number of erc versioned entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ERCVersionedEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ERCVersionedEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ERCVersionedEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:1897339534