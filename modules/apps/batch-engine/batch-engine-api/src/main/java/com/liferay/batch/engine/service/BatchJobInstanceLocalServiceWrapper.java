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

package com.liferay.batch.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link BatchJobInstanceLocalService}.
 *
 * @author Ivica Cardic
 * @see BatchJobInstanceLocalService
 * @generated
 */
@ProviderType
public class BatchJobInstanceLocalServiceWrapper
	implements BatchJobInstanceLocalService,
			   ServiceWrapper<BatchJobInstanceLocalService> {

	public BatchJobInstanceLocalServiceWrapper(
		BatchJobInstanceLocalService batchJobInstanceLocalService) {

		_batchJobInstanceLocalService = batchJobInstanceLocalService;
	}

	/**
	 * Adds the batch job instance to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobInstance the batch job instance
	 * @return the batch job instance that was added
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobInstance addBatchJobInstance(
		com.liferay.batch.engine.model.BatchJobInstance batchJobInstance) {

		return _batchJobInstanceLocalService.addBatchJobInstance(
			batchJobInstance);
	}

	@Override
	public com.liferay.batch.engine.model.BatchJobInstance addBatchJobInstance(
		String jobName, String jobKey) {

		return _batchJobInstanceLocalService.addBatchJobInstance(
			jobName, jobKey);
	}

	/**
	 * Creates a new batch job instance with the primary key. Does not add the batch job instance to the database.
	 *
	 * @param batchJobInstanceId the primary key for the new batch job instance
	 * @return the new batch job instance
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobInstance
		createBatchJobInstance(long batchJobInstanceId) {

		return _batchJobInstanceLocalService.createBatchJobInstance(
			batchJobInstanceId);
	}

	/**
	 * Deletes the batch job instance from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobInstance the batch job instance
	 * @return the batch job instance that was removed
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobInstance
		deleteBatchJobInstance(
			com.liferay.batch.engine.model.BatchJobInstance batchJobInstance) {

		return _batchJobInstanceLocalService.deleteBatchJobInstance(
			batchJobInstance);
	}

	/**
	 * Deletes the batch job instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance that was removed
	 * @throws PortalException if a batch job instance with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobInstance
			deleteBatchJobInstance(long batchJobInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobInstanceLocalService.deleteBatchJobInstance(
			batchJobInstanceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobInstanceLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchJobInstanceLocalService.dynamicQuery();
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

		return _batchJobInstanceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _batchJobInstanceLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _batchJobInstanceLocalService.dynamicQuery(
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

		return _batchJobInstanceLocalService.dynamicQueryCount(dynamicQuery);
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

		return _batchJobInstanceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.engine.model.BatchJobInstance
		fetchBatchJobInstance(long batchJobInstanceId) {

		return _batchJobInstanceLocalService.fetchBatchJobInstance(
			batchJobInstanceId);
	}

	@Override
	public com.liferay.batch.engine.model.BatchJobInstance
		fetchBatchJobInstance(String jobName, String jobKey) {

		return _batchJobInstanceLocalService.fetchBatchJobInstance(
			jobName, jobKey);
	}

	/**
	 * Returns the batch job instance with the matching UUID and company.
	 *
	 * @param uuid the batch job instance's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobInstance
		fetchBatchJobInstanceByUuidAndCompanyId(String uuid, long companyId) {

		return _batchJobInstanceLocalService.
			fetchBatchJobInstanceByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchJobInstanceLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the batch job instance with the primary key.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance
	 * @throws PortalException if a batch job instance with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobInstance getBatchJobInstance(
			long batchJobInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobInstanceLocalService.getBatchJobInstance(
			batchJobInstanceId);
	}

	/**
	 * Returns the batch job instance with the matching UUID and company.
	 *
	 * @param uuid the batch job instance's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch job instance
	 * @throws PortalException if a matching batch job instance could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobInstance
			getBatchJobInstanceByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobInstanceLocalService.
			getBatchJobInstanceByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the batch job instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @return the range of batch job instances
	 */
	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchJobInstance>
		getBatchJobInstances(int start, int end) {

		return _batchJobInstanceLocalService.getBatchJobInstances(start, end);
	}

	/**
	 * Returns the number of batch job instances.
	 *
	 * @return the number of batch job instances
	 */
	@Override
	public int getBatchJobInstancesCount() {
		return _batchJobInstanceLocalService.getBatchJobInstancesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _batchJobInstanceLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchJobInstanceLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchJobInstanceLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobInstanceLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch job instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobInstance the batch job instance
	 * @return the batch job instance that was updated
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobInstance
		updateBatchJobInstance(
			com.liferay.batch.engine.model.BatchJobInstance batchJobInstance) {

		return _batchJobInstanceLocalService.updateBatchJobInstance(
			batchJobInstance);
	}

	@Override
	public BatchJobInstanceLocalService getWrappedService() {
		return _batchJobInstanceLocalService;
	}

	@Override
	public void setWrappedService(
		BatchJobInstanceLocalService batchJobInstanceLocalService) {

		_batchJobInstanceLocalService = batchJobInstanceLocalService;
	}

	private BatchJobInstanceLocalService _batchJobInstanceLocalService;

}