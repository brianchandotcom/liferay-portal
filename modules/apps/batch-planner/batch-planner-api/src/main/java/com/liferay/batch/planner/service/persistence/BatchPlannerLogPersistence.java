/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.planner.service.persistence;

import com.liferay.batch.planner.exception.NoSuchLogException;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch planner log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerLogUtil
 * @generated
 */
@ProviderType
public interface BatchPlannerLogPersistence
	extends BasePersistence<BatchPlannerLog> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchPlannerLogUtil} to access the batch planner log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner logs
	 */
	public java.util.List<BatchPlannerLog> findByBatchPlannerPlanId(
		long batchPlannerPlanId);

	/**
	 * Returns a range of all the batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @return the range of matching batch planner logs
	 */
	public java.util.List<BatchPlannerLog> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner logs
	 */
	public java.util.List<BatchPlannerLog> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner logs
	 */
	public java.util.List<BatchPlannerLog> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public BatchPlannerLog findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Returns the first batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public BatchPlannerLog fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
			orderByComparator);

	/**
	 * Returns the last batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public BatchPlannerLog findByBatchPlannerPlanId_Last(
			long batchPlannerPlanId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Returns the last batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public BatchPlannerLog fetchByBatchPlannerPlanId_Last(
		long batchPlannerPlanId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
			orderByComparator);

	/**
	 * Returns the batch planner logs before and after the current batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerLogId the primary key of the current batch planner log
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner log
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	public BatchPlannerLog[] findByBatchPlannerPlanId_PrevAndNext(
			long batchPlannerLogId, long batchPlannerPlanId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
				orderByComparator)
		throws NoSuchLogException;

	/**
	 * Removes all the batch planner logs where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	public void removeByBatchPlannerPlanId(long batchPlannerPlanId);

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner logs
	 */
	public int countByBatchPlannerPlanId(long batchPlannerPlanId);

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchExternalReferenceCode = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchExternalReferenceCode the batch external reference code
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public BatchPlannerLog findByBPPI_BEERC(
			long batchPlannerPlanId, String batchExternalReferenceCode)
		throws NoSuchLogException;

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchExternalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchExternalReferenceCode the batch external reference code
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public BatchPlannerLog fetchByBPPI_BEERC(
		long batchPlannerPlanId, String batchExternalReferenceCode);

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchExternalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchExternalReferenceCode the batch external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public BatchPlannerLog fetchByBPPI_BEERC(
		long batchPlannerPlanId, String batchExternalReferenceCode,
		boolean useFinderCache);

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and batchExternalReferenceCode = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchExternalReferenceCode the batch external reference code
	 * @return the batch planner log that was removed
	 */
	public BatchPlannerLog removeByBPPI_BEERC(
			long batchPlannerPlanId, String batchExternalReferenceCode)
		throws NoSuchLogException;

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and batchExternalReferenceCode = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchExternalReferenceCode the batch external reference code
	 * @return the number of matching batch planner logs
	 */
	public int countByBPPI_BEERC(
		long batchPlannerPlanId, String batchExternalReferenceCode);

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchExternalReferenceCode = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchExternalReferenceCode the dispatch external reference code
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public BatchPlannerLog findByBPPI_DERC(
			long batchPlannerPlanId, String dispatchExternalReferenceCode)
		throws NoSuchLogException;

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchExternalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchExternalReferenceCode the dispatch external reference code
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public BatchPlannerLog fetchByBPPI_DERC(
		long batchPlannerPlanId, String dispatchExternalReferenceCode);

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchExternalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchExternalReferenceCode the dispatch external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public BatchPlannerLog fetchByBPPI_DERC(
		long batchPlannerPlanId, String dispatchExternalReferenceCode,
		boolean useFinderCache);

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and dispatchExternalReferenceCode = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchExternalReferenceCode the dispatch external reference code
	 * @return the batch planner log that was removed
	 */
	public BatchPlannerLog removeByBPPI_DERC(
			long batchPlannerPlanId, String dispatchExternalReferenceCode)
		throws NoSuchLogException;

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and dispatchExternalReferenceCode = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchExternalReferenceCode the dispatch external reference code
	 * @return the number of matching batch planner logs
	 */
	public int countByBPPI_DERC(
		long batchPlannerPlanId, String dispatchExternalReferenceCode);

	/**
	 * Caches the batch planner log in the entity cache if it is enabled.
	 *
	 * @param batchPlannerLog the batch planner log
	 */
	public void cacheResult(BatchPlannerLog batchPlannerLog);

	/**
	 * Caches the batch planner logs in the entity cache if it is enabled.
	 *
	 * @param batchPlannerLogs the batch planner logs
	 */
	public void cacheResult(java.util.List<BatchPlannerLog> batchPlannerLogs);

	/**
	 * Creates a new batch planner log with the primary key. Does not add the batch planner log to the database.
	 *
	 * @param batchPlannerLogId the primary key for the new batch planner log
	 * @return the new batch planner log
	 */
	public BatchPlannerLog create(long batchPlannerLogId);

	/**
	 * Removes the batch planner log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log that was removed
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	public BatchPlannerLog remove(long batchPlannerLogId)
		throws NoSuchLogException;

	public BatchPlannerLog updateImpl(BatchPlannerLog batchPlannerLog);

	/**
	 * Returns the batch planner log with the primary key or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	public BatchPlannerLog findByPrimaryKey(long batchPlannerLogId)
		throws NoSuchLogException;

	/**
	 * Returns the batch planner log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log, or <code>null</code> if a batch planner log with the primary key could not be found
	 */
	public BatchPlannerLog fetchByPrimaryKey(long batchPlannerLogId);

	/**
	 * Returns all the batch planner logs.
	 *
	 * @return the batch planner logs
	 */
	public java.util.List<BatchPlannerLog> findAll();

	/**
	 * Returns a range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @return the range of batch planner logs
	 */
	public java.util.List<BatchPlannerLog> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner logs
	 */
	public java.util.List<BatchPlannerLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner logs
	 */
	public java.util.List<BatchPlannerLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerLog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the batch planner logs from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch planner logs.
	 *
	 * @return the number of batch planner logs
	 */
	public int countAll();

}