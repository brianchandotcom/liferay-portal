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
 * Provides a wrapper for {@link BatchPlannerMappingLocalService}.
 *
 * @author Igor Beslic
 * @see BatchPlannerMappingLocalService
 * @generated
 */
public class BatchPlannerMappingLocalServiceWrapper
	implements BatchPlannerMappingLocalService,
			   ServiceWrapper<BatchPlannerMappingLocalService> {

	public BatchPlannerMappingLocalServiceWrapper(
		BatchPlannerMappingLocalService batchPlannerMappingLocalService) {

		_batchPlannerMappingLocalService = batchPlannerMappingLocalService;
	}

	/**
	 * Adds the batch planner mapping to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerMappingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerMapping the batch planner mapping
	 * @return the batch planner mapping that was added
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
		addBatchPlannerMapping(
			com.liferay.batch.planner.model.BatchPlannerMapping
				batchPlannerMapping) {

		return _batchPlannerMappingLocalService.addBatchPlannerMapping(
			batchPlannerMapping);
	}

	/**
	 * Creates a new batch planner mapping with the primary key. Does not add the batch planner mapping to the database.
	 *
	 * @param batchPlannerMappingId the primary key for the new batch planner mapping
	 * @return the new batch planner mapping
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
		createBatchPlannerMapping(long batchPlannerMappingId) {

		return _batchPlannerMappingLocalService.createBatchPlannerMapping(
			batchPlannerMappingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the batch planner mapping from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerMappingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerMapping the batch planner mapping
	 * @return the batch planner mapping that was removed
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
		deleteBatchPlannerMapping(
			com.liferay.batch.planner.model.BatchPlannerMapping
				batchPlannerMapping) {

		return _batchPlannerMappingLocalService.deleteBatchPlannerMapping(
			batchPlannerMapping);
	}

	/**
	 * Deletes the batch planner mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerMappingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping that was removed
	 * @throws PortalException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
			deleteBatchPlannerMapping(long batchPlannerMappingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingLocalService.deleteBatchPlannerMapping(
			batchPlannerMappingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _batchPlannerMappingLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchPlannerMappingLocalService.dynamicQuery();
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

		return _batchPlannerMappingLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerMappingModelImpl</code>.
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

		return _batchPlannerMappingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerMappingModelImpl</code>.
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

		return _batchPlannerMappingLocalService.dynamicQuery(
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

		return _batchPlannerMappingLocalService.dynamicQueryCount(dynamicQuery);
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

		return _batchPlannerMappingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
		fetchBatchPlannerMapping(long batchPlannerMappingId) {

		return _batchPlannerMappingLocalService.fetchBatchPlannerMapping(
			batchPlannerMappingId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchPlannerMappingLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the batch planner mapping with the primary key.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping
	 * @throws PortalException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
			getBatchPlannerMapping(long batchPlannerMappingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingLocalService.getBatchPlannerMapping(
			batchPlannerMappingId);
	}

	/**
	 * Returns a range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.planner.model.impl.BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @return the range of batch planner mappings
	 */
	@Override
	public java.util.List<com.liferay.batch.planner.model.BatchPlannerMapping>
		getBatchPlannerMappings(int start, int end) {

		return _batchPlannerMappingLocalService.getBatchPlannerMappings(
			start, end);
	}

	/**
	 * Returns the number of batch planner mappings.
	 *
	 * @return the number of batch planner mappings
	 */
	@Override
	public int getBatchPlannerMappingsCount() {
		return _batchPlannerMappingLocalService.getBatchPlannerMappingsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchPlannerMappingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchPlannerMappingLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchPlannerMappingLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the batch planner mapping in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BatchPlannerMappingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param batchPlannerMapping the batch planner mapping
	 * @return the batch planner mapping that was updated
	 */
	@Override
	public com.liferay.batch.planner.model.BatchPlannerMapping
		updateBatchPlannerMapping(
			com.liferay.batch.planner.model.BatchPlannerMapping
				batchPlannerMapping) {

		return _batchPlannerMappingLocalService.updateBatchPlannerMapping(
			batchPlannerMapping);
	}

	@Override
	public BatchPlannerMappingLocalService getWrappedService() {
		return _batchPlannerMappingLocalService;
	}

	@Override
	public void setWrappedService(
		BatchPlannerMappingLocalService batchPlannerMappingLocalService) {

		_batchPlannerMappingLocalService = batchPlannerMappingLocalService;
	}

	private BatchPlannerMappingLocalService _batchPlannerMappingLocalService;

}