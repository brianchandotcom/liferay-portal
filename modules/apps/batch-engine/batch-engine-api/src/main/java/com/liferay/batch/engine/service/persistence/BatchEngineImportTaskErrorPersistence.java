/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.persistence;

import com.liferay.batch.engine.exception.NoSuchImportTaskErrorException;
import com.liferay.batch.engine.model.BatchEngineImportTaskError;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch engine import task error service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskErrorUtil
 * @generated
 */
@ProviderType
public interface BatchEngineImportTaskErrorPersistence
	extends BasePersistence<BatchEngineImportTaskError> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchEngineImportTaskErrorUtil} to access the batch engine import task error persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @return the matching batch engine import task errors
	 */
	public java.util.List<BatchEngineImportTaskError>
		findByBatchEngineImportTaskId(long batchEngineImportTaskId);

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
	public java.util.List<BatchEngineImportTaskError>
		findByBatchEngineImportTaskId(
			long batchEngineImportTaskId, int start, int end);

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
	public java.util.List<BatchEngineImportTaskError>
		findByBatchEngineImportTaskId(
			long batchEngineImportTaskId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineImportTaskError> orderByComparator);

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
	public java.util.List<BatchEngineImportTaskError>
		findByBatchEngineImportTaskId(
			long batchEngineImportTaskId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineImportTaskError> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a matching batch engine import task error could not be found
	 */
	public BatchEngineImportTaskError findByBatchEngineImportTaskId_First(
			long batchEngineImportTaskId,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineImportTaskError> orderByComparator)
		throws NoSuchImportTaskErrorException;

	/**
	 * Returns the first batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task error, or <code>null</code> if a matching batch engine import task error could not be found
	 */
	public BatchEngineImportTaskError fetchByBatchEngineImportTaskId_First(
		long batchEngineImportTaskId,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportTaskError> orderByComparator);

	/**
	 * Returns the last batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a matching batch engine import task error could not be found
	 */
	public BatchEngineImportTaskError findByBatchEngineImportTaskId_Last(
			long batchEngineImportTaskId,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineImportTaskError> orderByComparator)
		throws NoSuchImportTaskErrorException;

	/**
	 * Returns the last batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task error, or <code>null</code> if a matching batch engine import task error could not be found
	 */
	public BatchEngineImportTaskError fetchByBatchEngineImportTaskId_Last(
		long batchEngineImportTaskId,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportTaskError> orderByComparator);

	/**
	 * Returns the batch engine import task errors before and after the current batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the current batch engine import task error
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	public BatchEngineImportTaskError[]
			findByBatchEngineImportTaskId_PrevAndNext(
				long batchEngineImportTaskErrorId, long batchEngineImportTaskId,
				com.liferay.portal.kernel.util.OrderByComparator
					<BatchEngineImportTaskError> orderByComparator)
		throws NoSuchImportTaskErrorException;

	/**
	 * Removes all the batch engine import task errors where batchEngineImportTaskId = &#63; from the database.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 */
	public void removeByBatchEngineImportTaskId(long batchEngineImportTaskId);

	/**
	 * Returns the number of batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @return the number of matching batch engine import task errors
	 */
	public int countByBatchEngineImportTaskId(long batchEngineImportTaskId);

	/**
	 * Caches the batch engine import task error in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTaskError the batch engine import task error
	 */
	public void cacheResult(
		BatchEngineImportTaskError batchEngineImportTaskError);

	/**
	 * Caches the batch engine import task errors in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTaskErrors the batch engine import task errors
	 */
	public void cacheResult(
		java.util.List<BatchEngineImportTaskError> batchEngineImportTaskErrors);

	/**
	 * Creates a new batch engine import task error with the primary key. Does not add the batch engine import task error to the database.
	 *
	 * @param batchEngineImportTaskErrorId the primary key for the new batch engine import task error
	 * @return the new batch engine import task error
	 */
	public BatchEngineImportTaskError create(long batchEngineImportTaskErrorId);

	/**
	 * Removes the batch engine import task error with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error that was removed
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	public BatchEngineImportTaskError remove(long batchEngineImportTaskErrorId)
		throws NoSuchImportTaskErrorException;

	public BatchEngineImportTaskError updateImpl(
		BatchEngineImportTaskError batchEngineImportTaskError);

	/**
	 * Returns the batch engine import task error with the primary key or throws a <code>NoSuchImportTaskErrorException</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	public BatchEngineImportTaskError findByPrimaryKey(
			long batchEngineImportTaskErrorId)
		throws NoSuchImportTaskErrorException;

	/**
	 * Returns the batch engine import task error with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error, or <code>null</code> if a batch engine import task error with the primary key could not be found
	 */
	public BatchEngineImportTaskError fetchByPrimaryKey(
		long batchEngineImportTaskErrorId);

	/**
	 * Returns all the batch engine import task errors.
	 *
	 * @return the batch engine import task errors
	 */
	public java.util.List<BatchEngineImportTaskError> findAll();

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
	public java.util.List<BatchEngineImportTaskError> findAll(
		int start, int end);

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
	public java.util.List<BatchEngineImportTaskError> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportTaskError> orderByComparator);

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
	public java.util.List<BatchEngineImportTaskError> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportTaskError> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the batch engine import task errors from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch engine import task errors.
	 *
	 * @return the number of batch engine import task errors
	 */
	public int countAll();

}