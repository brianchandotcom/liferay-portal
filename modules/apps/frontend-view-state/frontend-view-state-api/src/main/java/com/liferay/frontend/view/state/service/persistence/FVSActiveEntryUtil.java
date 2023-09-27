/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the fvs active entry service. This utility wraps <code>com.liferay.frontend.view.state.service.persistence.impl.FVSActiveEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSActiveEntryPersistence
 * @generated
 */
public class FVSActiveEntryUtil {

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
	public static void clearCache(FVSActiveEntry fvsActiveEntry) {
		getPersistence().clearCache(fvsActiveEntry);
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
	public static Map<Serializable, FVSActiveEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FVSActiveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FVSActiveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FVSActiveEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FVSActiveEntry update(FVSActiveEntry fvsActiveEntry) {
		return getPersistence().update(fvsActiveEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FVSActiveEntry update(
		FVSActiveEntry fvsActiveEntry, ServiceContext serviceContext) {

		return getPersistence().update(fvsActiveEntry, serviceContext);
	}

	/**
	 * Returns all the fvs active entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs active entries
	 */
	public static List<FVSActiveEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of matching fvs active entries
	 */
	public static List<FVSActiveEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs active entries
	 */
	public static List<FVSActiveEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs active entries
	 */
	public static List<FVSActiveEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry findByUuid_First(
			String uuid, OrderByComparator<FVSActiveEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry fetchByUuid_First(
		String uuid, OrderByComparator<FVSActiveEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry findByUuid_Last(
			String uuid, OrderByComparator<FVSActiveEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry fetchByUuid_Last(
		String uuid, OrderByComparator<FVSActiveEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the fvs active entries before and after the current fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsActiveEntryId the primary key of the current fvs active entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	public static FVSActiveEntry[] findByUuid_PrevAndNext(
			long fvsActiveEntryId, String uuid,
			OrderByComparator<FVSActiveEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			fvsActiveEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the fvs active entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of fvs active entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs active entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs active entries
	 */
	public static List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of matching fvs active entries
	 */
	public static List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs active entries
	 */
	public static List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs active entries
	 */
	public static List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSActiveEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSActiveEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the fvs active entries before and after the current fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsActiveEntryId the primary key of the current fvs active entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	public static FVSActiveEntry[] findByUuid_C_PrevAndNext(
			long fvsActiveEntryId, String uuid, long companyId,
			OrderByComparator<FVSActiveEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			fvsActiveEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the fvs active entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs active entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchActiveEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry findByU_CDSDI_P_P(
			long userId, String clayDataSetDisplayId, long plid,
			String portletId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().findByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId);
	}

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry fetchByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId) {

		return getPersistence().fetchByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId);
	}

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	public static FVSActiveEntry fetchByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId,
		boolean useFinderCache) {

		return getPersistence().fetchByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId, useFinderCache);
	}

	/**
	 * Removes the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the fvs active entry that was removed
	 */
	public static FVSActiveEntry removeByU_CDSDI_P_P(
			long userId, String clayDataSetDisplayId, long plid,
			String portletId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().removeByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId);
	}

	/**
	 * Returns the number of fvs active entries where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching fvs active entries
	 */
	public static int countByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId) {

		return getPersistence().countByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId);
	}

	/**
	 * Caches the fvs active entry in the entity cache if it is enabled.
	 *
	 * @param fvsActiveEntry the fvs active entry
	 */
	public static void cacheResult(FVSActiveEntry fvsActiveEntry) {
		getPersistence().cacheResult(fvsActiveEntry);
	}

	/**
	 * Caches the fvs active entries in the entity cache if it is enabled.
	 *
	 * @param fvsActiveEntries the fvs active entries
	 */
	public static void cacheResult(List<FVSActiveEntry> fvsActiveEntries) {
		getPersistence().cacheResult(fvsActiveEntries);
	}

	/**
	 * Creates a new fvs active entry with the primary key. Does not add the fvs active entry to the database.
	 *
	 * @param fvsActiveEntryId the primary key for the new fvs active entry
	 * @return the new fvs active entry
	 */
	public static FVSActiveEntry create(long fvsActiveEntryId) {
		return getPersistence().create(fvsActiveEntryId);
	}

	/**
	 * Removes the fvs active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry that was removed
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	public static FVSActiveEntry remove(long fvsActiveEntryId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().remove(fvsActiveEntryId);
	}

	public static FVSActiveEntry updateImpl(FVSActiveEntry fvsActiveEntry) {
		return getPersistence().updateImpl(fvsActiveEntry);
	}

	/**
	 * Returns the fvs active entry with the primary key or throws a <code>NoSuchActiveEntryException</code> if it could not be found.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	public static FVSActiveEntry findByPrimaryKey(long fvsActiveEntryId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchActiveEntryException {

		return getPersistence().findByPrimaryKey(fvsActiveEntryId);
	}

	/**
	 * Returns the fvs active entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry, or <code>null</code> if a fvs active entry with the primary key could not be found
	 */
	public static FVSActiveEntry fetchByPrimaryKey(long fvsActiveEntryId) {
		return getPersistence().fetchByPrimaryKey(fvsActiveEntryId);
	}

	/**
	 * Returns all the fvs active entries.
	 *
	 * @return the fvs active entries
	 */
	public static List<FVSActiveEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of fvs active entries
	 */
	public static List<FVSActiveEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs active entries
	 */
	public static List<FVSActiveEntry> findAll(
		int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs active entries
	 */
	public static List<FVSActiveEntry> findAll(
		int start, int end, OrderByComparator<FVSActiveEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the fvs active entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of fvs active entries.
	 *
	 * @return the number of fvs active entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FVSActiveEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(FVSActiveEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile FVSActiveEntryPersistence _persistence;

}