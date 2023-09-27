/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the erc company entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.ERCCompanyEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntryPersistence
 * @generated
 */
public class ERCCompanyEntryUtil {

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
	public static void clearCache(ERCCompanyEntry ercCompanyEntry) {
		getPersistence().clearCache(ercCompanyEntry);
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
	public static Map<Serializable, ERCCompanyEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ERCCompanyEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ERCCompanyEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ERCCompanyEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ERCCompanyEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ERCCompanyEntry update(ERCCompanyEntry ercCompanyEntry) {
		return getPersistence().update(ercCompanyEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ERCCompanyEntry update(
		ERCCompanyEntry ercCompanyEntry, ServiceContext serviceContext) {

		return getPersistence().update(ercCompanyEntry, serviceContext);
	}

	/**
	 * Returns all the erc company entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching erc company entries
	 */
	public static List<ERCCompanyEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the erc company entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @return the range of matching erc company entries
	 */
	public static List<ERCCompanyEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the erc company entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc company entries
	 */
	public static List<ERCCompanyEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCCompanyEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc company entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc company entries
	 */
	public static List<ERCCompanyEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCCompanyEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc company entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc company entry
	 * @throws NoSuchERCCompanyEntryException if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry findByUuid_First(
			String uuid, OrderByComparator<ERCCompanyEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first erc company entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry fetchByUuid_First(
		String uuid, OrderByComparator<ERCCompanyEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last erc company entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc company entry
	 * @throws NoSuchERCCompanyEntryException if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry findByUuid_Last(
			String uuid, OrderByComparator<ERCCompanyEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last erc company entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry fetchByUuid_Last(
		String uuid, OrderByComparator<ERCCompanyEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the erc company entries before and after the current erc company entry in the ordered set where uuid = &#63;.
	 *
	 * @param ercCompanyEntryId the primary key of the current erc company entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc company entry
	 * @throws NoSuchERCCompanyEntryException if a erc company entry with the primary key could not be found
	 */
	public static ERCCompanyEntry[] findByUuid_PrevAndNext(
			long ercCompanyEntryId, String uuid,
			OrderByComparator<ERCCompanyEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			ercCompanyEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the erc company entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of erc company entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching erc company entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the erc company entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching erc company entries
	 */
	public static List<ERCCompanyEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the erc company entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @return the range of matching erc company entries
	 */
	public static List<ERCCompanyEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the erc company entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc company entries
	 */
	public static List<ERCCompanyEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCCompanyEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc company entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc company entries
	 */
	public static List<ERCCompanyEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCCompanyEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc company entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc company entry
	 * @throws NoSuchERCCompanyEntryException if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ERCCompanyEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first erc company entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ERCCompanyEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last erc company entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc company entry
	 * @throws NoSuchERCCompanyEntryException if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ERCCompanyEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last erc company entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ERCCompanyEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the erc company entries before and after the current erc company entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ercCompanyEntryId the primary key of the current erc company entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc company entry
	 * @throws NoSuchERCCompanyEntryException if a erc company entry with the primary key could not be found
	 */
	public static ERCCompanyEntry[] findByUuid_C_PrevAndNext(
			long ercCompanyEntryId, String uuid, long companyId,
			OrderByComparator<ERCCompanyEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			ercCompanyEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the erc company entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of erc company entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching erc company entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the erc company entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchERCCompanyEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching erc company entry
	 * @throws NoSuchERCCompanyEntryException if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the erc company entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the erc company entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the erc company entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the erc company entry that was removed
	 */
	public static ERCCompanyEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of erc company entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching erc company entries
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the erc company entry in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntry the erc company entry
	 */
	public static void cacheResult(ERCCompanyEntry ercCompanyEntry) {
		getPersistence().cacheResult(ercCompanyEntry);
	}

	/**
	 * Caches the erc company entries in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntries the erc company entries
	 */
	public static void cacheResult(List<ERCCompanyEntry> ercCompanyEntries) {
		getPersistence().cacheResult(ercCompanyEntries);
	}

	/**
	 * Creates a new erc company entry with the primary key. Does not add the erc company entry to the database.
	 *
	 * @param ercCompanyEntryId the primary key for the new erc company entry
	 * @return the new erc company entry
	 */
	public static ERCCompanyEntry create(long ercCompanyEntryId) {
		return getPersistence().create(ercCompanyEntryId);
	}

	/**
	 * Removes the erc company entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry that was removed
	 * @throws NoSuchERCCompanyEntryException if a erc company entry with the primary key could not be found
	 */
	public static ERCCompanyEntry remove(long ercCompanyEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().remove(ercCompanyEntryId);
	}

	public static ERCCompanyEntry updateImpl(ERCCompanyEntry ercCompanyEntry) {
		return getPersistence().updateImpl(ercCompanyEntry);
	}

	/**
	 * Returns the erc company entry with the primary key or throws a <code>NoSuchERCCompanyEntryException</code> if it could not be found.
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry
	 * @throws NoSuchERCCompanyEntryException if a erc company entry with the primary key could not be found
	 */
	public static ERCCompanyEntry findByPrimaryKey(long ercCompanyEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntryException {

		return getPersistence().findByPrimaryKey(ercCompanyEntryId);
	}

	/**
	 * Returns the erc company entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry, or <code>null</code> if a erc company entry with the primary key could not be found
	 */
	public static ERCCompanyEntry fetchByPrimaryKey(long ercCompanyEntryId) {
		return getPersistence().fetchByPrimaryKey(ercCompanyEntryId);
	}

	/**
	 * Returns all the erc company entries.
	 *
	 * @return the erc company entries
	 */
	public static List<ERCCompanyEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the erc company entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @return the range of erc company entries
	 */
	public static List<ERCCompanyEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the erc company entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc company entries
	 */
	public static List<ERCCompanyEntry> findAll(
		int start, int end,
		OrderByComparator<ERCCompanyEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc company entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc company entries
	 */
	public static List<ERCCompanyEntry> findAll(
		int start, int end,
		OrderByComparator<ERCCompanyEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the erc company entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of erc company entries.
	 *
	 * @return the number of erc company entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ERCCompanyEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ERCCompanyEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ERCCompanyEntryPersistence _persistence;

}