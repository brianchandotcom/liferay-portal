/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.persistence;

import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the batch planner mapping service. This utility wraps <code>com.liferay.batch.planner.service.persistence.impl.BatchPlannerMappingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerMappingPersistence
 * @generated
 */
public class BatchPlannerMappingUtil {

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
	public static void clearCache(BatchPlannerMapping batchPlannerMapping) {
		getPersistence().clearCache(batchPlannerMapping);
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
	public static Map<Serializable, BatchPlannerMapping> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchPlannerMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchPlannerMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchPlannerMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchPlannerMapping update(
		BatchPlannerMapping batchPlannerMapping) {

		return getPersistence().update(batchPlannerMapping);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchPlannerMapping update(
		BatchPlannerMapping batchPlannerMapping,
		ServiceContext serviceContext) {

		return getPersistence().update(batchPlannerMapping, serviceContext);
	}

	/**
	 * Returns all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner mappings
	 */
	public static List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId) {

		return getPersistence().findByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns a range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @return the range of matching batch planner mappings
	 */
	public static List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end) {

		return getPersistence().findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner mappings
	 */
	public static List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return getPersistence().findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner mappings
	 */
	public static List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	public static BatchPlannerMapping findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerMapping> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchMappingException {

		return getPersistence().findByBatchPlannerPlanId_First(
			batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Returns the first batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	public static BatchPlannerMapping fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return getPersistence().fetchByBatchPlannerPlanId_First(
			batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Returns the last batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	public static BatchPlannerMapping findByBatchPlannerPlanId_Last(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerMapping> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchMappingException {

		return getPersistence().findByBatchPlannerPlanId_Last(
			batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Returns the last batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	public static BatchPlannerMapping fetchByBatchPlannerPlanId_Last(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return getPersistence().fetchByBatchPlannerPlanId_Last(
			batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Returns the batch planner mappings before and after the current batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerMappingId the primary key of the current batch planner mapping
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner mapping
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	public static BatchPlannerMapping[] findByBatchPlannerPlanId_PrevAndNext(
			long batchPlannerMappingId, long batchPlannerPlanId,
			OrderByComparator<BatchPlannerMapping> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchMappingException {

		return getPersistence().findByBatchPlannerPlanId_PrevAndNext(
			batchPlannerMappingId, batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Removes all the batch planner mappings where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	public static void removeByBatchPlannerPlanId(long batchPlannerPlanId) {
		getPersistence().removeByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns the number of batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner mappings
	 */
	public static int countByBatchPlannerPlanId(long batchPlannerPlanId) {
		return getPersistence().countByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or throws a <code>NoSuchMappingException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	public static BatchPlannerMapping findByBPPI_EFN_IFN(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws com.liferay.batch.planner.exception.NoSuchMappingException {

		return getPersistence().findByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName);
	}

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	public static BatchPlannerMapping fetchByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName) {

		return getPersistence().fetchByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName);
	}

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	public static BatchPlannerMapping fetchByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName, boolean useFinderCache) {

		return getPersistence().fetchByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName,
			useFinderCache);
	}

	/**
	 * Removes the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the batch planner mapping that was removed
	 */
	public static BatchPlannerMapping removeByBPPI_EFN_IFN(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws com.liferay.batch.planner.exception.NoSuchMappingException {

		return getPersistence().removeByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName);
	}

	/**
	 * Returns the number of batch planner mappings where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the number of matching batch planner mappings
	 */
	public static int countByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName) {

		return getPersistence().countByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName);
	}

	/**
	 * Caches the batch planner mapping in the entity cache if it is enabled.
	 *
	 * @param batchPlannerMapping the batch planner mapping
	 */
	public static void cacheResult(BatchPlannerMapping batchPlannerMapping) {
		getPersistence().cacheResult(batchPlannerMapping);
	}

	/**
	 * Caches the batch planner mappings in the entity cache if it is enabled.
	 *
	 * @param batchPlannerMappings the batch planner mappings
	 */
	public static void cacheResult(
		List<BatchPlannerMapping> batchPlannerMappings) {

		getPersistence().cacheResult(batchPlannerMappings);
	}

	/**
	 * Creates a new batch planner mapping with the primary key. Does not add the batch planner mapping to the database.
	 *
	 * @param batchPlannerMappingId the primary key for the new batch planner mapping
	 * @return the new batch planner mapping
	 */
	public static BatchPlannerMapping create(long batchPlannerMappingId) {
		return getPersistence().create(batchPlannerMappingId);
	}

	/**
	 * Removes the batch planner mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping that was removed
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	public static BatchPlannerMapping remove(long batchPlannerMappingId)
		throws com.liferay.batch.planner.exception.NoSuchMappingException {

		return getPersistence().remove(batchPlannerMappingId);
	}

	public static BatchPlannerMapping updateImpl(
		BatchPlannerMapping batchPlannerMapping) {

		return getPersistence().updateImpl(batchPlannerMapping);
	}

	/**
	 * Returns the batch planner mapping with the primary key or throws a <code>NoSuchMappingException</code> if it could not be found.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	public static BatchPlannerMapping findByPrimaryKey(
			long batchPlannerMappingId)
		throws com.liferay.batch.planner.exception.NoSuchMappingException {

		return getPersistence().findByPrimaryKey(batchPlannerMappingId);
	}

	/**
	 * Returns the batch planner mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping, or <code>null</code> if a batch planner mapping with the primary key could not be found
	 */
	public static BatchPlannerMapping fetchByPrimaryKey(
		long batchPlannerMappingId) {

		return getPersistence().fetchByPrimaryKey(batchPlannerMappingId);
	}

	/**
	 * Returns all the batch planner mappings.
	 *
	 * @return the batch planner mappings
	 */
	public static List<BatchPlannerMapping> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @return the range of batch planner mappings
	 */
	public static List<BatchPlannerMapping> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner mappings
	 */
	public static List<BatchPlannerMapping> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner mappings
	 */
	public static List<BatchPlannerMapping> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the batch planner mappings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch planner mappings.
	 *
	 * @return the number of batch planner mappings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchPlannerMappingPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		BatchPlannerMappingPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile BatchPlannerMappingPersistence _persistence;

}