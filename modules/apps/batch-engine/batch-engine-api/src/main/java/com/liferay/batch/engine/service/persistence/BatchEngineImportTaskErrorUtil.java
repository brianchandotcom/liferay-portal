/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.persistence;

import com.liferay.batch.engine.model.BatchEngineImportTaskError;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the batch engine import task error service. This utility wraps <code>com.liferay.batch.engine.service.persistence.impl.BatchEngineImportTaskErrorPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskErrorPersistence
 * @generated
 */
public class BatchEngineImportTaskErrorUtil {

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
		BatchEngineImportTaskError batchEngineImportTaskError) {

		getPersistence().clearCache(batchEngineImportTaskError);
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
	public static Map<Serializable, BatchEngineImportTaskError>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchEngineImportTaskError> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchEngineImportTaskError> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchEngineImportTaskError> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchEngineImportTaskError update(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		return getPersistence().update(batchEngineImportTaskError);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchEngineImportTaskError update(
		BatchEngineImportTaskError batchEngineImportTaskError,
		ServiceContext serviceContext) {

		return getPersistence().update(
			batchEngineImportTaskError, serviceContext);
	}

	/**
	 * Returns all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @return the matching batch engine import task errors
	 */
	public static List<BatchEngineImportTaskError>
		findByBatchEngineImportTaskId(long batchEngineImportTaskId) {

		return getPersistence().findByBatchEngineImportTaskId(
			batchEngineImportTaskId);
	}

	/**
	 * Returns a range of all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @return the range of matching batch engine import task errors
	 */
	public static List<BatchEngineImportTaskError>
		findByBatchEngineImportTaskId(
			long batchEngineImportTaskId, int start, int end) {

		return getPersistence().findByBatchEngineImportTaskId(
			batchEngineImportTaskId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import task errors
	 */
	public static List<BatchEngineImportTaskError>
		findByBatchEngineImportTaskId(
			long batchEngineImportTaskId, int start, int end,
			OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		return getPersistence().findByBatchEngineImportTaskId(
			batchEngineImportTaskId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import task errors
	 */
	public static List<BatchEngineImportTaskError>
		findByBatchEngineImportTaskId(
			long batchEngineImportTaskId, int start, int end,
			OrderByComparator<BatchEngineImportTaskError> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByBatchEngineImportTaskId(
			batchEngineImportTaskId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a matching batch engine import task error could not be found
	 */
	public static BatchEngineImportTaskError
			findByBatchEngineImportTaskId_First(
				long batchEngineImportTaskId,
				OrderByComparator<BatchEngineImportTaskError> orderByComparator)
		throws com.liferay.batch.engine.exception.
			NoSuchImportTaskErrorException {

		return getPersistence().findByBatchEngineImportTaskId_First(
			batchEngineImportTaskId, orderByComparator);
	}

	/**
	 * Returns the first batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task error, or <code>null</code> if a matching batch engine import task error could not be found
	 */
	public static BatchEngineImportTaskError
		fetchByBatchEngineImportTaskId_First(
			long batchEngineImportTaskId,
			OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		return getPersistence().fetchByBatchEngineImportTaskId_First(
			batchEngineImportTaskId, orderByComparator);
	}

	/**
	 * Returns the last batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a matching batch engine import task error could not be found
	 */
	public static BatchEngineImportTaskError findByBatchEngineImportTaskId_Last(
			long batchEngineImportTaskId,
			OrderByComparator<BatchEngineImportTaskError> orderByComparator)
		throws com.liferay.batch.engine.exception.
			NoSuchImportTaskErrorException {

		return getPersistence().findByBatchEngineImportTaskId_Last(
			batchEngineImportTaskId, orderByComparator);
	}

	/**
	 * Returns the last batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task error, or <code>null</code> if a matching batch engine import task error could not be found
	 */
	public static BatchEngineImportTaskError
		fetchByBatchEngineImportTaskId_Last(
			long batchEngineImportTaskId,
			OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		return getPersistence().fetchByBatchEngineImportTaskId_Last(
			batchEngineImportTaskId, orderByComparator);
	}

	/**
	 * Returns the batch engine import task errors before and after the current batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the current batch engine import task error
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	public static BatchEngineImportTaskError[]
			findByBatchEngineImportTaskId_PrevAndNext(
				long batchEngineImportTaskErrorId, long batchEngineImportTaskId,
				OrderByComparator<BatchEngineImportTaskError> orderByComparator)
		throws com.liferay.batch.engine.exception.
			NoSuchImportTaskErrorException {

		return getPersistence().findByBatchEngineImportTaskId_PrevAndNext(
			batchEngineImportTaskErrorId, batchEngineImportTaskId,
			orderByComparator);
	}

	/**
	 * Removes all the batch engine import task errors where batchEngineImportTaskId = &#63; from the database.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 */
	public static void removeByBatchEngineImportTaskId(
		long batchEngineImportTaskId) {

		getPersistence().removeByBatchEngineImportTaskId(
			batchEngineImportTaskId);
	}

	/**
	 * Returns the number of batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @return the number of matching batch engine import task errors
	 */
	public static int countByBatchEngineImportTaskId(
		long batchEngineImportTaskId) {

		return getPersistence().countByBatchEngineImportTaskId(
			batchEngineImportTaskId);
	}

	/**
	 * Caches the batch engine import task error in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTaskError the batch engine import task error
	 */
	public static void cacheResult(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		getPersistence().cacheResult(batchEngineImportTaskError);
	}

	/**
	 * Caches the batch engine import task errors in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTaskErrors the batch engine import task errors
	 */
	public static void cacheResult(
		List<BatchEngineImportTaskError> batchEngineImportTaskErrors) {

		getPersistence().cacheResult(batchEngineImportTaskErrors);
	}

	/**
	 * Creates a new batch engine import task error with the primary key. Does not add the batch engine import task error to the database.
	 *
	 * @param batchEngineImportTaskErrorId the primary key for the new batch engine import task error
	 * @return the new batch engine import task error
	 */
	public static BatchEngineImportTaskError create(
		long batchEngineImportTaskErrorId) {

		return getPersistence().create(batchEngineImportTaskErrorId);
	}

	/**
	 * Removes the batch engine import task error with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error that was removed
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	public static BatchEngineImportTaskError remove(
			long batchEngineImportTaskErrorId)
		throws com.liferay.batch.engine.exception.
			NoSuchImportTaskErrorException {

		return getPersistence().remove(batchEngineImportTaskErrorId);
	}

	public static BatchEngineImportTaskError updateImpl(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		return getPersistence().updateImpl(batchEngineImportTaskError);
	}

	/**
	 * Returns the batch engine import task error with the primary key or throws a <code>NoSuchImportTaskErrorException</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	public static BatchEngineImportTaskError findByPrimaryKey(
			long batchEngineImportTaskErrorId)
		throws com.liferay.batch.engine.exception.
			NoSuchImportTaskErrorException {

		return getPersistence().findByPrimaryKey(batchEngineImportTaskErrorId);
	}

	/**
	 * Returns the batch engine import task error with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error, or <code>null</code> if a batch engine import task error with the primary key could not be found
	 */
	public static BatchEngineImportTaskError fetchByPrimaryKey(
		long batchEngineImportTaskErrorId) {

		return getPersistence().fetchByPrimaryKey(batchEngineImportTaskErrorId);
	}

	/**
	 * Returns all the batch engine import task errors.
	 *
	 * @return the batch engine import task errors
	 */
	public static List<BatchEngineImportTaskError> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the batch engine import task errors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @return the range of batch engine import task errors
	 */
	public static List<BatchEngineImportTaskError> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the batch engine import task errors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine import task errors
	 */
	public static List<BatchEngineImportTaskError> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch engine import task errors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine import task errors
	 */
	public static List<BatchEngineImportTaskError> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the batch engine import task errors from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch engine import task errors.
	 *
	 * @return the number of batch engine import task errors
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchEngineImportTaskErrorPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		BatchEngineImportTaskErrorPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile BatchEngineImportTaskErrorPersistence _persistence;

}