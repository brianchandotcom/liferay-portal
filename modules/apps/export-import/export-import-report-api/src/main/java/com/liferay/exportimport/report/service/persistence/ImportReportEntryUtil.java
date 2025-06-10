/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.persistence;

import com.liferay.exportimport.report.model.ImportReportEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the import report entry service. This utility wraps <code>com.liferay.exportimport.report.service.persistence.impl.ImportReportEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Carlos Correa
 * @see ImportReportEntryPersistence
 * @generated
 */
public class ImportReportEntryUtil {

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
	public static void clearCache(ImportReportEntry importReportEntry) {
		getPersistence().clearCache(importReportEntry);
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
	public static Map<Serializable, ImportReportEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ImportReportEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ImportReportEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ImportReportEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ImportReportEntry update(
		ImportReportEntry importReportEntry) {

		return getPersistence().update(importReportEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ImportReportEntry update(
		ImportReportEntry importReportEntry, ServiceContext serviceContext) {

		return getPersistence().update(importReportEntry, serviceContext);
	}

	/**
	 * Returns all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching import report entries
	 */
	public static List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK) {

		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	/**
	 * Returns a range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @return the range of matching import report entries
	 */
	public static List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C_C(
			companyId, classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching import report entries
	 */
	public static List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		return getPersistence().findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching import report entries
	 */
	public static List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching import report entry
	 * @throws NoSuchImportReportEntryException if a matching import report entry could not be found
	 */
	public static ImportReportEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			OrderByComparator<ImportReportEntry> orderByComparator)
		throws com.liferay.exportimport.report.exception.
			NoSuchImportReportEntryException {

		return getPersistence().findByC_C_C_First(
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching import report entry, or <code>null</code> if a matching import report entry could not be found
	 */
	public static ImportReportEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		return getPersistence().fetchByC_C_C_First(
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching import report entry
	 * @throws NoSuchImportReportEntryException if a matching import report entry could not be found
	 */
	public static ImportReportEntry findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			OrderByComparator<ImportReportEntry> orderByComparator)
		throws com.liferay.exportimport.report.exception.
			NoSuchImportReportEntryException {

		return getPersistence().findByC_C_C_Last(
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching import report entry, or <code>null</code> if a matching import report entry could not be found
	 */
	public static ImportReportEntry fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		return getPersistence().fetchByC_C_C_Last(
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the import report entries before and after the current import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param importReportEntryId the primary key of the current import report entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next import report entry
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	public static ImportReportEntry[] findByC_C_C_PrevAndNext(
			long importReportEntryId, long companyId, long classNameId,
			long classPK,
			OrderByComparator<ImportReportEntry> orderByComparator)
		throws com.liferay.exportimport.report.exception.
			NoSuchImportReportEntryException {

		return getPersistence().findByC_C_C_PrevAndNext(
			importReportEntryId, companyId, classNameId, classPK,
			orderByComparator);
	}

	/**
	 * Removes all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C_C(
		long companyId, long classNameId, long classPK) {

		getPersistence().removeByC_C_C(companyId, classNameId, classPK);
	}

	/**
	 * Returns the number of import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching import report entries
	 */
	public static int countByC_C_C(
		long companyId, long classNameId, long classPK) {

		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	/**
	 * Caches the import report entry in the entity cache if it is enabled.
	 *
	 * @param importReportEntry the import report entry
	 */
	public static void cacheResult(ImportReportEntry importReportEntry) {
		getPersistence().cacheResult(importReportEntry);
	}

	/**
	 * Caches the import report entries in the entity cache if it is enabled.
	 *
	 * @param importReportEntries the import report entries
	 */
	public static void cacheResult(
		List<ImportReportEntry> importReportEntries) {

		getPersistence().cacheResult(importReportEntries);
	}

	/**
	 * Creates a new import report entry with the primary key. Does not add the import report entry to the database.
	 *
	 * @param importReportEntryId the primary key for the new import report entry
	 * @return the new import report entry
	 */
	public static ImportReportEntry create(long importReportEntryId) {
		return getPersistence().create(importReportEntryId);
	}

	/**
	 * Removes the import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry that was removed
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	public static ImportReportEntry remove(long importReportEntryId)
		throws com.liferay.exportimport.report.exception.
			NoSuchImportReportEntryException {

		return getPersistence().remove(importReportEntryId);
	}

	public static ImportReportEntry updateImpl(
		ImportReportEntry importReportEntry) {

		return getPersistence().updateImpl(importReportEntry);
	}

	/**
	 * Returns the import report entry with the primary key or throws a <code>NoSuchImportReportEntryException</code> if it could not be found.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	public static ImportReportEntry findByPrimaryKey(long importReportEntryId)
		throws com.liferay.exportimport.report.exception.
			NoSuchImportReportEntryException {

		return getPersistence().findByPrimaryKey(importReportEntryId);
	}

	/**
	 * Returns the import report entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry, or <code>null</code> if a import report entry with the primary key could not be found
	 */
	public static ImportReportEntry fetchByPrimaryKey(
		long importReportEntryId) {

		return getPersistence().fetchByPrimaryKey(importReportEntryId);
	}

	/**
	 * Returns all the import report entries.
	 *
	 * @return the import report entries
	 */
	public static List<ImportReportEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @return the range of import report entries
	 */
	public static List<ImportReportEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of import report entries
	 */
	public static List<ImportReportEntry> findAll(
		int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of import report entries
	 */
	public static List<ImportReportEntry> findAll(
		int start, int end,
		OrderByComparator<ImportReportEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the import report entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of import report entries.
	 *
	 * @return the number of import report entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ImportReportEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ImportReportEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ImportReportEntryPersistence _persistence;

}