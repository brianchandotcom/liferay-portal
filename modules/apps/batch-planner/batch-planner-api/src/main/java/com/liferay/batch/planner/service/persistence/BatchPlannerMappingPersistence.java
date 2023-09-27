/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.persistence;

import com.liferay.batch.planner.exception.NoSuchMappingException;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch planner mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerMappingUtil
 * @generated
 */
@ProviderType
public interface BatchPlannerMappingPersistence
	extends BasePersistence<BatchPlannerMapping> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchPlannerMappingUtil} to access the batch planner mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner mappings
	 */
	public java.util.List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId);

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
	public java.util.List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end);

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
	public java.util.List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerMapping>
			orderByComparator);

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
	public java.util.List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerMapping>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	public BatchPlannerMapping findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchPlannerMapping> orderByComparator)
		throws NoSuchMappingException;

	/**
	 * Returns the first batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	public BatchPlannerMapping fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerMapping>
			orderByComparator);

	/**
	 * Returns the last batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	public BatchPlannerMapping findByBatchPlannerPlanId_Last(
			long batchPlannerPlanId,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchPlannerMapping> orderByComparator)
		throws NoSuchMappingException;

	/**
	 * Returns the last batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	public BatchPlannerMapping fetchByBatchPlannerPlanId_Last(
		long batchPlannerPlanId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerMapping>
			orderByComparator);

	/**
	 * Returns the batch planner mappings before and after the current batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerMappingId the primary key of the current batch planner mapping
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner mapping
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	public BatchPlannerMapping[] findByBatchPlannerPlanId_PrevAndNext(
			long batchPlannerMappingId, long batchPlannerPlanId,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchPlannerMapping> orderByComparator)
		throws NoSuchMappingException;

	/**
	 * Removes all the batch planner mappings where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	public void removeByBatchPlannerPlanId(long batchPlannerPlanId);

	/**
	 * Returns the number of batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner mappings
	 */
	public int countByBatchPlannerPlanId(long batchPlannerPlanId);

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or throws a <code>NoSuchMappingException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	public BatchPlannerMapping findByBPPI_EFN_IFN(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws NoSuchMappingException;

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	public BatchPlannerMapping fetchByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName);

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	public BatchPlannerMapping fetchByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName, boolean useFinderCache);

	/**
	 * Removes the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the batch planner mapping that was removed
	 */
	public BatchPlannerMapping removeByBPPI_EFN_IFN(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws NoSuchMappingException;

	/**
	 * Returns the number of batch planner mappings where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the number of matching batch planner mappings
	 */
	public int countByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName);

	/**
	 * Caches the batch planner mapping in the entity cache if it is enabled.
	 *
	 * @param batchPlannerMapping the batch planner mapping
	 */
	public void cacheResult(BatchPlannerMapping batchPlannerMapping);

	/**
	 * Caches the batch planner mappings in the entity cache if it is enabled.
	 *
	 * @param batchPlannerMappings the batch planner mappings
	 */
	public void cacheResult(
		java.util.List<BatchPlannerMapping> batchPlannerMappings);

	/**
	 * Creates a new batch planner mapping with the primary key. Does not add the batch planner mapping to the database.
	 *
	 * @param batchPlannerMappingId the primary key for the new batch planner mapping
	 * @return the new batch planner mapping
	 */
	public BatchPlannerMapping create(long batchPlannerMappingId);

	/**
	 * Removes the batch planner mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping that was removed
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	public BatchPlannerMapping remove(long batchPlannerMappingId)
		throws NoSuchMappingException;

	public BatchPlannerMapping updateImpl(
		BatchPlannerMapping batchPlannerMapping);

	/**
	 * Returns the batch planner mapping with the primary key or throws a <code>NoSuchMappingException</code> if it could not be found.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	public BatchPlannerMapping findByPrimaryKey(long batchPlannerMappingId)
		throws NoSuchMappingException;

	/**
	 * Returns the batch planner mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping, or <code>null</code> if a batch planner mapping with the primary key could not be found
	 */
	public BatchPlannerMapping fetchByPrimaryKey(long batchPlannerMappingId);

	/**
	 * Returns all the batch planner mappings.
	 *
	 * @return the batch planner mappings
	 */
	public java.util.List<BatchPlannerMapping> findAll();

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
	public java.util.List<BatchPlannerMapping> findAll(int start, int end);

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
	public java.util.List<BatchPlannerMapping> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerMapping>
			orderByComparator);

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
	public java.util.List<BatchPlannerMapping> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerMapping>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the batch planner mappings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch planner mappings.
	 *
	 * @return the number of batch planner mappings
	 */
	public int countAll();

}