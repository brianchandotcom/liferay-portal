/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.model.FVSCustomEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the fvs custom entry service. This utility wraps <code>com.liferay.frontend.view.state.service.persistence.impl.FVSCustomEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSCustomEntryPersistence
 * @generated
 */
public class FVSCustomEntryUtil {

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
	public static void clearCache(FVSCustomEntry fvsCustomEntry) {
		getPersistence().clearCache(fvsCustomEntry);
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
	public static Map<Serializable, FVSCustomEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FVSCustomEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FVSCustomEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FVSCustomEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FVSCustomEntry update(FVSCustomEntry fvsCustomEntry) {
		return getPersistence().update(fvsCustomEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FVSCustomEntry update(
		FVSCustomEntry fvsCustomEntry, ServiceContext serviceContext) {

		return getPersistence().update(fvsCustomEntry, serviceContext);
	}

	/**
	 * Returns all the fvs custom entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs custom entries
	 */
	public static List<FVSCustomEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of matching fvs custom entries
	 */
	public static List<FVSCustomEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs custom entries
	 */
	public static List<FVSCustomEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs custom entries
	 */
	public static List<FVSCustomEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry findByUuid_First(
			String uuid, OrderByComparator<FVSCustomEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchCustomEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry fetchByUuid_First(
		String uuid, OrderByComparator<FVSCustomEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry findByUuid_Last(
			String uuid, OrderByComparator<FVSCustomEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchCustomEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry fetchByUuid_Last(
		String uuid, OrderByComparator<FVSCustomEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the fvs custom entries before and after the current fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsCustomEntryId the primary key of the current fvs custom entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	public static FVSCustomEntry[] findByUuid_PrevAndNext(
			long fvsCustomEntryId, String uuid,
			OrderByComparator<FVSCustomEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchCustomEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			fvsCustomEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the fvs custom entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of fvs custom entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs custom entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs custom entries
	 */
	public static List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of matching fvs custom entries
	 */
	public static List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs custom entries
	 */
	public static List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs custom entries
	 */
	public static List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSCustomEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchCustomEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSCustomEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchCustomEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	public static FVSCustomEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the fvs custom entries before and after the current fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsCustomEntryId the primary key of the current fvs custom entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	public static FVSCustomEntry[] findByUuid_C_PrevAndNext(
			long fvsCustomEntryId, String uuid, long companyId,
			OrderByComparator<FVSCustomEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchCustomEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			fvsCustomEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the fvs custom entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs custom entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Caches the fvs custom entry in the entity cache if it is enabled.
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 */
	public static void cacheResult(FVSCustomEntry fvsCustomEntry) {
		getPersistence().cacheResult(fvsCustomEntry);
	}

	/**
	 * Caches the fvs custom entries in the entity cache if it is enabled.
	 *
	 * @param fvsCustomEntries the fvs custom entries
	 */
	public static void cacheResult(List<FVSCustomEntry> fvsCustomEntries) {
		getPersistence().cacheResult(fvsCustomEntries);
	}

	/**
	 * Creates a new fvs custom entry with the primary key. Does not add the fvs custom entry to the database.
	 *
	 * @param fvsCustomEntryId the primary key for the new fvs custom entry
	 * @return the new fvs custom entry
	 */
	public static FVSCustomEntry create(long fvsCustomEntryId) {
		return getPersistence().create(fvsCustomEntryId);
	}

	/**
	 * Removes the fvs custom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry that was removed
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	public static FVSCustomEntry remove(long fvsCustomEntryId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchCustomEntryException {

		return getPersistence().remove(fvsCustomEntryId);
	}

	public static FVSCustomEntry updateImpl(FVSCustomEntry fvsCustomEntry) {
		return getPersistence().updateImpl(fvsCustomEntry);
	}

	/**
	 * Returns the fvs custom entry with the primary key or throws a <code>NoSuchCustomEntryException</code> if it could not be found.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	public static FVSCustomEntry findByPrimaryKey(long fvsCustomEntryId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchCustomEntryException {

		return getPersistence().findByPrimaryKey(fvsCustomEntryId);
	}

	/**
	 * Returns the fvs custom entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry, or <code>null</code> if a fvs custom entry with the primary key could not be found
	 */
	public static FVSCustomEntry fetchByPrimaryKey(long fvsCustomEntryId) {
		return getPersistence().fetchByPrimaryKey(fvsCustomEntryId);
	}

	/**
	 * Returns all the fvs custom entries.
	 *
	 * @return the fvs custom entries
	 */
	public static List<FVSCustomEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of fvs custom entries
	 */
	public static List<FVSCustomEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs custom entries
	 */
	public static List<FVSCustomEntry> findAll(
		int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs custom entries
	 */
	public static List<FVSCustomEntry> findAll(
		int start, int end, OrderByComparator<FVSCustomEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the fvs custom entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of fvs custom entries.
	 *
	 * @return the number of fvs custom entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FVSCustomEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(FVSCustomEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile FVSCustomEntryPersistence _persistence;

}