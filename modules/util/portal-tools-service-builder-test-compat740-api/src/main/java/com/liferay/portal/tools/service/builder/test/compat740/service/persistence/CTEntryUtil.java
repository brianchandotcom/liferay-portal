/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat740.model.CTEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the ct entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.compat740.service.persistence.impl.CTEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryPersistence
 * @generated
 */
public class CTEntryUtil {

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
	public static void clearCache(CTEntry ctEntry) {
		getPersistence().clearCache(ctEntry);
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
	public static Map<Serializable, CTEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTEntry update(CTEntry ctEntry) {
		return getPersistence().update(ctEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTEntry update(
		CTEntry ctEntry, ServiceContext serviceContext) {

		return getPersistence().update(ctEntry, serviceContext);
	}

	/**
	 * Returns all the ct entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByCompanyId_First(
			long companyId, OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchCTEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByCompanyId_Last(
			long companyId, OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchCTEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where companyId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByCompanyId_PrevAndNext(
			long ctEntryId, long companyId,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchCTEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			ctEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the ct entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of ct entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchCTEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching ct entry
	 * @throws NoSuchCTEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_N(long companyId, String name)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchCTEntryException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the ct entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the ct entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the ct entry that was removed
	 */
	public static CTEntry removeByC_N(long companyId, String name)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchCTEntryException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of ct entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching ct entries
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	public static void cacheResult(CTEntry ctEntry) {
		getPersistence().cacheResult(ctEntry);
	}

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	public static void cacheResult(List<CTEntry> ctEntries) {
		getPersistence().cacheResult(ctEntries);
	}

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	public static CTEntry create(long ctEntryId) {
		return getPersistence().create(ctEntryId);
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry remove(long ctEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchCTEntryException {

		return getPersistence().remove(ctEntryId);
	}

	public static CTEntry updateImpl(CTEntry ctEntry) {
		return getPersistence().updateImpl(ctEntry);
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchCTEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchCTEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry findByPrimaryKey(long ctEntryId)
		throws com.liferay.portal.tools.service.builder.test.compat740.
			exception.NoSuchCTEntryException {

		return getPersistence().findByPrimaryKey(ctEntryId);
	}

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	public static CTEntry fetchByPrimaryKey(long ctEntryId) {
		return getPersistence().fetchByPrimaryKey(ctEntryId);
	}

	/**
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	public static List<CTEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	public static List<CTEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	public static List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct entries
	 */
	public static List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CTEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CTEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1944602338