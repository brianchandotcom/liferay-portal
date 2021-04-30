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

package com.liferay.batch.planner.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BatchPlannerPlanLocalService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerPlanLocalService
 * @generated
 */
public class BatchPlannerPlanLocalServiceWrapper
	implements BatchPlannerPlanLocalService,
			   ServiceWrapper<BatchPlannerPlanLocalService> {

	public BatchPlannerPlanLocalServiceWrapper(
		BatchPlannerPlanLocalService batchPlannerPlanLocalService) {

		_batchPlannerPlanLocalService = batchPlannerPlanLocalService;
	}

	/**
	 * Adds the batch planner plan to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerPlanLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerPlan the batch planner plan
	 * @return the batch planner plan that was added
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan addBatchPlannerPlan(
		com.liferay.batch.planner.model.BatchPlannerPlan batchPlannerPlan) {

		return _batchPlannerPlanLocalService.addBatchPlannerPlan(
			batchPlannerPlan);
	}

	/**
	 * Creates a new batch planner plan with the primary key. Does not add the batch planner plan to the database.
	 *
	 * @param batchPlannerPlanId the primary key for the new batch planner plan
	 * @return the new batch planner plan
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
		createBatchPlannerPlan(long batchPlannerPlanId) {

		return _batchPlannerPlanLocalService.createBatchPlannerPlan(
			batchPlannerPlanId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the batch planner plan from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerPlanLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerPlan the batch planner plan
	 * @return the batch planner plan that was removed
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
		deleteBatchPlannerPlan(
			com.liferay.batch.planner.model.BatchPlannerPlan batchPlannerPlan) {

		return _batchPlannerPlanLocalService.deleteBatchPlannerPlan(
			batchPlannerPlan);
	}

	/**
	 * Deletes the batch planner plan with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerPlanLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerPlanId the primary key of the batch planner plan
	 * @return the batch planner plan that was removed
	 * @throws PortalException if a batch planner plan with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
			deleteBatchPlannerPlan(long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanLocalService.deleteBatchPlannerPlan(
			batchPlannerPlanId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _batchPlannerPlanLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchPlannerPlanLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _batchPlannerPlanLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _batchPlannerPlanLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _batchPlannerPlanLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _batchPlannerPlanLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _batchPlannerPlanLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
		fetchBatchPlannerPlan(long batchPlannerPlanId) {

		return _batchPlannerPlanLocalService.fetchBatchPlannerPlan(
			batchPlannerPlanId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchPlannerPlanLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the batch planner plan with the primary key.
	 *
	 * @param batchPlannerPlanId the primary key of the batch planner plan
	 * @return the batch planner plan
	 * @throws PortalException if a batch planner plan with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan getBatchPlannerPlan(
			long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanLocalService.getBatchPlannerPlan(
			batchPlannerPlanId);
	}

	/**
	 * Returns a range of all the batch planner plans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of batch planner plans
	 */
	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPlan>
		getBatchPlannerPlans(int start, int end) {

		return _batchPlannerPlanLocalService.getBatchPlannerPlans(start, end);
	}

	/**
	 * Returns the number of batch planner plans.
	 *
	 * @return the number of batch planner plans
	 */
	@Override
	public int getBatchPlannerPlansCount() {
		return _batchPlannerPlanLocalService.getBatchPlannerPlansCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchPlannerPlanLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerPlanLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPlanLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch planner plan in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerPlanLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerPlan the batch planner plan
	 * @return the batch planner plan that was updated
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPlan
		updateBatchPlannerPlan(
			com.liferay.batch.planner.model.BatchPlannerPlan batchPlannerPlan) {

		return _batchPlannerPlanLocalService.updateBatchPlannerPlan(
			batchPlannerPlan);
	}

	@Override
	public BatchPlannerPlanLocalService getWrappedService() {
		return _batchPlannerPlanLocalService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerPlanLocalService batchPlannerPlanLocalService) {

		_batchPlannerPlanLocalService = batchPlannerPlanLocalService;
	}

	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

}