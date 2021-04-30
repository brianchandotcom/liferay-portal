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
 * Provides a wrapper for {@link BatchPlannerPolicyLocalService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerPolicyLocalService
 * @generated
 */
public class BatchPlannerPolicyLocalServiceWrapper
	implements BatchPlannerPolicyLocalService,
			   ServiceWrapper<BatchPlannerPolicyLocalService> {

	public BatchPlannerPolicyLocalServiceWrapper(
		BatchPlannerPolicyLocalService batchPlannerPolicyLocalService) {

		_batchPlannerPolicyLocalService = batchPlannerPolicyLocalService;
	}

	/**
	 * Adds the batch planner policy to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerPolicyLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerPolicy the batch planner policy
	 * @return the batch planner policy that was added
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
		addBatchPlannerPolicy(
			com.liferay.batch.planner.model.BatchPlannerPolicy
				batchPlannerPolicy) {

		return _batchPlannerPolicyLocalService.addBatchPlannerPolicy(
			batchPlannerPolicy);
	}

	/**
	 * Creates a new batch planner policy with the primary key. Does not add the batch planner policy to the database.
	 *
	 * @param batchPlannerPolicyId the primary key for the new batch planner policy
	 * @return the new batch planner policy
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
		createBatchPlannerPolicy(long batchPlannerPolicyId) {

		return _batchPlannerPolicyLocalService.createBatchPlannerPolicy(
			batchPlannerPolicyId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the batch planner policy from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerPolicyLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerPolicy the batch planner policy
	 * @return the batch planner policy that was removed
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
		deleteBatchPlannerPolicy(
			com.liferay.batch.planner.model.BatchPlannerPolicy
				batchPlannerPolicy) {

		return _batchPlannerPolicyLocalService.deleteBatchPlannerPolicy(
			batchPlannerPolicy);
	}

	/**
	 * Deletes the batch planner policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerPolicyLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy that was removed
	 * @throws PortalException if a batch planner policy with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
			deleteBatchPlannerPolicy(long batchPlannerPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyLocalService.deleteBatchPlannerPolicy(
			batchPlannerPolicyId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _batchPlannerPolicyLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchPlannerPolicyLocalService.dynamicQuery();
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

		return _batchPlannerPolicyLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPolicyModelImpl</code>.
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

		return _batchPlannerPolicyLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPolicyModelImpl</code>.
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

		return _batchPlannerPolicyLocalService.dynamicQuery(
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

		return _batchPlannerPolicyLocalService.dynamicQueryCount(dynamicQuery);
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

		return _batchPlannerPolicyLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
		fetchBatchPlannerPolicy(long batchPlannerPolicyId) {

		return _batchPlannerPolicyLocalService.fetchBatchPlannerPolicy(
			batchPlannerPolicyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchPlannerPolicyLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the batch planner policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @return the range of batch planner policies
	 */
	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerPolicy>
		getBatchPlannerPolicies(int start, int end) {

		return _batchPlannerPolicyLocalService.getBatchPlannerPolicies(
			start, end);
	}

	/**
	 * Returns the number of batch planner policies.
	 *
	 * @return the number of batch planner policies
	 */
	@Override
	public int getBatchPlannerPoliciesCount() {
		return _batchPlannerPolicyLocalService.getBatchPlannerPoliciesCount();
	}

	/**
	 * Returns the batch planner policy with the primary key.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy
	 * @throws PortalException if a batch planner policy with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
			getBatchPlannerPolicy(long batchPlannerPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyLocalService.getBatchPlannerPolicy(
			batchPlannerPolicyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchPlannerPolicyLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerPolicyLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerPolicyLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch planner policy in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerPolicyLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerPolicy the batch planner policy
	 * @return the batch planner policy that was updated
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerPolicy
		updateBatchPlannerPolicy(
			com.liferay.batch.planner.model.BatchPlannerPolicy
				batchPlannerPolicy) {

		return _batchPlannerPolicyLocalService.updateBatchPlannerPolicy(
			batchPlannerPolicy);
	}

	@Override
	public BatchPlannerPolicyLocalService getWrappedService() {
		return _batchPlannerPolicyLocalService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerPolicyLocalService batchPlannerPolicyLocalService) {

		_batchPlannerPolicyLocalService = batchPlannerPolicyLocalService;
	}

	private BatchPlannerPolicyLocalService _batchPlannerPolicyLocalService;

}