/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.persistence;

import com.liferay.batch.engine.model.BatchEngineImportReportEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the batch engine import report entry service. This utility wraps <code>com.liferay.batch.engine.service.persistence.impl.BatchEngineImportReportEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportReportEntryPersistence
 * @generated
 */
public class BatchEngineImportReportEntryUtil {

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
		BatchEngineImportReportEntry batchEngineImportReportEntry) {

		getPersistence().clearCache(batchEngineImportReportEntry);
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
	public static Map<Serializable, BatchEngineImportReportEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchEngineImportReportEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchEngineImportReportEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchEngineImportReportEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchEngineImportReportEntry update(
		BatchEngineImportReportEntry batchEngineImportReportEntry) {

		return getPersistence().update(batchEngineImportReportEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchEngineImportReportEntry update(
		BatchEngineImportReportEntry batchEngineImportReportEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			batchEngineImportReportEntry, serviceContext);
	}

	/**
	 * Returns all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching batch engine import report entries
	 */
	public static List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK) {

		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	/**
	 * Returns a range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @return the range of matching batch engine import report entries
	 */
	public static List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C_C(
			companyId, classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import report entries
	 */
	public static List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		return getPersistence().findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import report entries
	 */
	public static List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a matching batch engine import report entry could not be found
	 */
	public static BatchEngineImportReportEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			OrderByComparator<BatchEngineImportReportEntry> orderByComparator)
		throws com.liferay.batch.engine.exception.
			NoSuchImportReportEntryException {

		return getPersistence().findByC_C_C_First(
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import report entry, or <code>null</code> if a matching batch engine import report entry could not be found
	 */
	public static BatchEngineImportReportEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		return getPersistence().fetchByC_C_C_First(
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a matching batch engine import report entry could not be found
	 */
	public static BatchEngineImportReportEntry findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			OrderByComparator<BatchEngineImportReportEntry> orderByComparator)
		throws com.liferay.batch.engine.exception.
			NoSuchImportReportEntryException {

		return getPersistence().findByC_C_C_Last(
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import report entry, or <code>null</code> if a matching batch engine import report entry could not be found
	 */
	public static BatchEngineImportReportEntry fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		return getPersistence().fetchByC_C_C_Last(
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the batch engine import report entries before and after the current batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the current batch engine import report entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	public static BatchEngineImportReportEntry[] findByC_C_C_PrevAndNext(
			long batchEngineImportReportEntryId, long companyId,
			long classNameId, long classPK,
			OrderByComparator<BatchEngineImportReportEntry> orderByComparator)
		throws com.liferay.batch.engine.exception.
			NoSuchImportReportEntryException {

		return getPersistence().findByC_C_C_PrevAndNext(
			batchEngineImportReportEntryId, companyId, classNameId, classPK,
			orderByComparator);
	}

	/**
	 * Removes all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
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
	 * Returns the number of batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching batch engine import report entries
	 */
	public static int countByC_C_C(
		long companyId, long classNameId, long classPK) {

		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	/**
	 * Caches the batch engine import report entry in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportReportEntry the batch engine import report entry
	 */
	public static void cacheResult(
		BatchEngineImportReportEntry batchEngineImportReportEntry) {

		getPersistence().cacheResult(batchEngineImportReportEntry);
	}

	/**
	 * Caches the batch engine import report entries in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportReportEntries the batch engine import report entries
	 */
	public static void cacheResult(
		List<BatchEngineImportReportEntry> batchEngineImportReportEntries) {

		getPersistence().cacheResult(batchEngineImportReportEntries);
	}

	/**
	 * Creates a new batch engine import report entry with the primary key. Does not add the batch engine import report entry to the database.
	 *
	 * @param batchEngineImportReportEntryId the primary key for the new batch engine import report entry
	 * @return the new batch engine import report entry
	 */
	public static BatchEngineImportReportEntry create(
		long batchEngineImportReportEntryId) {

		return getPersistence().create(batchEngineImportReportEntryId);
	}

	/**
	 * Removes the batch engine import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry that was removed
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	public static BatchEngineImportReportEntry remove(
			long batchEngineImportReportEntryId)
		throws com.liferay.batch.engine.exception.
			NoSuchImportReportEntryException {

		return getPersistence().remove(batchEngineImportReportEntryId);
	}

	public static BatchEngineImportReportEntry updateImpl(
		BatchEngineImportReportEntry batchEngineImportReportEntry) {

		return getPersistence().updateImpl(batchEngineImportReportEntry);
	}

	/**
	 * Returns the batch engine import report entry with the primary key or throws a <code>NoSuchImportReportEntryException</code> if it could not be found.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	public static BatchEngineImportReportEntry findByPrimaryKey(
			long batchEngineImportReportEntryId)
		throws com.liferay.batch.engine.exception.
			NoSuchImportReportEntryException {

		return getPersistence().findByPrimaryKey(
			batchEngineImportReportEntryId);
	}

	/**
	 * Returns the batch engine import report entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry, or <code>null</code> if a batch engine import report entry with the primary key could not be found
	 */
	public static BatchEngineImportReportEntry fetchByPrimaryKey(
		long batchEngineImportReportEntryId) {

		return getPersistence().fetchByPrimaryKey(
			batchEngineImportReportEntryId);
	}

	/**
	 * Returns all the batch engine import report entries.
	 *
	 * @return the batch engine import report entries
	 */
	public static List<BatchEngineImportReportEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @return the range of batch engine import report entries
	 */
	public static List<BatchEngineImportReportEntry> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine import report entries
	 */
	public static List<BatchEngineImportReportEntry> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine import report entries
	 */
	public static List<BatchEngineImportReportEntry> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportReportEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the batch engine import report entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch engine import report entries.
	 *
	 * @return the number of batch engine import report entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchEngineImportReportEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		BatchEngineImportReportEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile BatchEngineImportReportEntryPersistence
		_persistence;

}