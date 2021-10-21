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

import com.liferay.batch.planner.exception.NoSuchPlanException;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch planner plan service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerPlanUtil
 * @generated
 */
@ProviderType
public interface BatchPlannerPlanPersistence
	extends BasePersistence<BatchPlannerPlan> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchPlannerPlanUtil} to access the batch planner plan persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch planner plans where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] findByCompanyId_PrevAndNext(
			long batchPlannerPlanId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] filterFindByCompanyId_PrevAndNext(
			long batchPlannerPlanId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Removes all the batch planner plans where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of batch planner plans where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching batch planner plans
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_U(
		long companyId, long userId);

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_U(
		long companyId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_U(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_U(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_U_First(
			long companyId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_U_First(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_U_Last(
			long companyId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_U_Last(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] findByC_U_PrevAndNext(
			long batchPlannerPlanId, long companyId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_U(
		long companyId, long userId);

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_U(
		long companyId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_U(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] filterFindByC_U_PrevAndNext(
			long batchPlannerPlanId, long companyId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Removes all the batch planner plans where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	public void removeByC_U(long companyId, long userId);

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching batch planner plans
	 */
	public int countByC_U(long companyId, long userId);

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public int filterCountByC_U(long companyId, long userId);

	/**
	 * Returns all the batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @return the matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_E(
		long companyId, boolean export);

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_E(
		long companyId, boolean export, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_E(
		long companyId, boolean export, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_E(
		long companyId, boolean export, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_E_First(
			long companyId, boolean export,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_E_First(
		long companyId, boolean export,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_E_Last(
			long companyId, boolean export,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_E_Last(
		long companyId, boolean export,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] findByC_E_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean export,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_E(
		long companyId, boolean export);

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_E(
		long companyId, boolean export, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_E(
		long companyId, boolean export, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63; and export = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] filterFindByC_E_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean export,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Removes all the batch planner plans where companyId = &#63; and export = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 */
	public void removeByC_E(long companyId, boolean export);

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @return the number of matching batch planner plans
	 */
	public int countByC_E(long companyId, boolean export);

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public int filterCountByC_E(long companyId, boolean export);

	/**
	 * Returns the batch planner plan where companyId = &#63; and name = &#63; or throws a <code>NoSuchPlanException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_N(long companyId, String name)
		throws NoSuchPlanException;

	/**
	 * Returns the batch planner plan where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_N(long companyId, String name);

	/**
	 * Returns the batch planner plan where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the batch planner plan where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the batch planner plan that was removed
	 */
	public BatchPlannerPlan removeByC_N(long companyId, String name)
		throws NoSuchPlanException;

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching batch planner plans
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Returns all the batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @return the matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_T(
		long companyId, boolean template);

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_T(
		long companyId, boolean template, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_T(
		long companyId, boolean template, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_T(
		long companyId, boolean template, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_T_First(
			long companyId, boolean template,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_T_First(
		long companyId, boolean template,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_T_Last(
			long companyId, boolean template,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_T_Last(
		long companyId, boolean template,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] findByC_T_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean template,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_T(
		long companyId, boolean template);

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_T(
		long companyId, boolean template, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_T(
		long companyId, boolean template, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63; and template = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] filterFindByC_T_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean template,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Removes all the batch planner plans where companyId = &#63; and template = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 */
	public void removeByC_T(long companyId, boolean template);

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @return the number of matching batch planner plans
	 */
	public int countByC_T(long companyId, boolean template);

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public int filterCountByC_T(long companyId, boolean template);

	/**
	 * Returns all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @return the matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_E_T(
		long companyId, boolean export, boolean template);

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_E_T(
		long companyId, boolean export, boolean template, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_E_T(
		long companyId, boolean export, boolean template, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findByC_E_T(
		long companyId, boolean export, boolean template, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_E_T_First(
			long companyId, boolean export, boolean template,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_E_T_First(
		long companyId, boolean export, boolean template,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan findByC_E_T_Last(
			long companyId, boolean export, boolean template,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public BatchPlannerPlan fetchByC_E_T_Last(
		long companyId, boolean export, boolean template,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] findByC_E_T_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean export,
			boolean template,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_E_T(
		long companyId, boolean export, boolean template);

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_E_T(
		long companyId, boolean export, boolean template, int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public java.util.List<BatchPlannerPlan> filterFindByC_E_T(
		long companyId, boolean export, boolean template, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan[] filterFindByC_E_T_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean export,
			boolean template,
			com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
				orderByComparator)
		throws NoSuchPlanException;

	/**
	 * Removes all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 */
	public void removeByC_E_T(long companyId, boolean export, boolean template);

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @return the number of matching batch planner plans
	 */
	public int countByC_E_T(long companyId, boolean export, boolean template);

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public int filterCountByC_E_T(
		long companyId, boolean export, boolean template);

	/**
	 * Caches the batch planner plan in the entity cache if it is enabled.
	 *
	 * @param batchPlannerPlan the batch planner plan
	 */
	public void cacheResult(BatchPlannerPlan batchPlannerPlan);

	/**
	 * Caches the batch planner plans in the entity cache if it is enabled.
	 *
	 * @param batchPlannerPlans the batch planner plans
	 */
	public void cacheResult(java.util.List<BatchPlannerPlan> batchPlannerPlans);

	/**
	 * Creates a new batch planner plan with the primary key. Does not add the batch planner plan to the database.
	 *
	 * @param batchPlannerPlanId the primary key for the new batch planner plan
	 * @return the new batch planner plan
	 */
	public BatchPlannerPlan create(long batchPlannerPlanId);

	/**
	 * Removes the batch planner plan with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerPlanId the primary key of the batch planner plan
	 * @return the batch planner plan that was removed
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan remove(long batchPlannerPlanId)
		throws NoSuchPlanException;

	public BatchPlannerPlan updateImpl(BatchPlannerPlan batchPlannerPlan);

	/**
	 * Returns the batch planner plan with the primary key or throws a <code>NoSuchPlanException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the primary key of the batch planner plan
	 * @return the batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan findByPrimaryKey(long batchPlannerPlanId)
		throws NoSuchPlanException;

	/**
	 * Returns the batch planner plan with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the primary key of the batch planner plan
	 * @return the batch planner plan, or <code>null</code> if a batch planner plan with the primary key could not be found
	 */
	public BatchPlannerPlan fetchByPrimaryKey(long batchPlannerPlanId);

	/**
	 * Returns all the batch planner plans.
	 *
	 * @return the batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findAll();

	/**
	 * Returns a range of all the batch planner plans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the batch planner plans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch planner plans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner plans
	 */
	public java.util.List<BatchPlannerPlan> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchPlannerPlan>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the batch planner plans from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch planner plans.
	 *
	 * @return the number of batch planner plans
	 */
	public int countAll();

}