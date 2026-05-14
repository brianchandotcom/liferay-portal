/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.service.persistence;

import com.liferay.batch.planner.exception.NoSuchPolicyException;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch planner policy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerPolicyUtil
 * @generated
 */
@ProviderType
public interface BatchPlannerPolicyPersistence
	extends BasePersistence<BatchPlannerPolicy> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchPlannerPolicyUtil} to access the batch planner policy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner policies
	 */
	public java.util.List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPolicy>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner policy
	 * @throws NoSuchPolicyException if a matching batch planner policy could not be found
	 */
	public BatchPlannerPolicy findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPolicy>
				orderByComparator)
		throws NoSuchPolicyException;

	/**
	 * Returns the first batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	public BatchPlannerPolicy fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPolicy>
			orderByComparator);

	/**
	 * Removes all the batch planner policies where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	public void removeByBatchPlannerPlanId(long batchPlannerPlanId);

	/**
	 * Returns the number of batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner policies
	 */
	public int countByBatchPlannerPlanId(long batchPlannerPlanId);

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or throws a <code>NoSuchPolicyException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the matching batch planner policy
	 * @throws NoSuchPolicyException if a matching batch planner policy could not be found
	 */
	public BatchPlannerPolicy findByBPPI_N(long batchPlannerPlanId, String name)
		throws NoSuchPolicyException;

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	public BatchPlannerPolicy fetchByBPPI_N(
		long batchPlannerPlanId, String name, boolean useFinderCache);

	/**
	 * Removes the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the batch planner policy that was removed
	 */
	public BatchPlannerPolicy removeByBPPI_N(
			long batchPlannerPlanId, String name)
		throws NoSuchPolicyException;

	/**
	 * Returns the number of batch planner policies where batchPlannerPlanId = &#63; and name = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the number of matching batch planner policies
	 */
	public int countByBPPI_N(long batchPlannerPlanId, String name);

	/**
	 * Creates a new batch planner policy with the primary key. Does not add the batch planner policy to the database.
	 *
	 * @param batchPlannerPolicyId the primary key for the new batch planner policy
	 * @return the new batch planner policy
	 */
	public BatchPlannerPolicy create(long batchPlannerPolicyId);

	/**
	 * Removes the batch planner policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy that was removed
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	public BatchPlannerPolicy remove(long batchPlannerPolicyId)
		throws NoSuchPolicyException;

	public BatchPlannerPolicy updateImpl(BatchPlannerPolicy batchPlannerPolicy);

	/**
	 * Returns the batch planner policy with the primary key or throws a <code>NoSuchPolicyException</code> if it could not be found.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	public BatchPlannerPolicy findByPrimaryKey(long batchPlannerPolicyId)
		throws NoSuchPolicyException;

	/**
	 * Returns the batch planner policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy, or <code>null</code> if a batch planner policy with the primary key could not be found
	 */
	public BatchPlannerPolicy fetchByPrimaryKey(long batchPlannerPolicyId);

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	public default BatchPlannerPolicy fetchByBPPI_N(
		long batchPlannerPlanId, String name) {

		return fetchByBPPI_N(batchPlannerPlanId, name, true);
	}

	/**
	 * Returns all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner policies
	 */
	public default java.util.List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @return the range of matching batch planner policies
	 */
	public default java.util.List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner policies
	 */
	public default java.util.List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPolicy>
			orderByComparator) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1547804752