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
 * Provides a wrapper for {@link BatchJobExecutionLocalService}.
 *
 * @author Ivica Cardic
 * @see BatchJobExecutionLocalService
 * @generated
 */
@ProviderType
public class BatchJobExecutionLocalServiceWrapper
	implements BatchJobExecutionLocalService,
			   ServiceWrapper<BatchJobExecutionLocalService> {

	public BatchJobExecutionLocalServiceWrapper(
		BatchJobExecutionLocalService batchJobExecutionLocalService) {

		_batchJobExecutionLocalService = batchJobExecutionLocalService;
	}

	/**
	 * Adds the batch job execution to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecution the batch job execution
	 * @return the batch job execution that was added
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
		addBatchJobExecution(
			com.liferay.batch.engine.model.BatchJobExecution
				batchJobExecution) {

		return _batchJobExecutionLocalService.addBatchJobExecution(
			batchJobExecution);
	}

	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
			addBatchJobExecution(
				String jobName,
				com.liferay.portal.kernel.util.UnicodeProperties
					jobSettingsProperties)
		throws com.liferay.batch.engine.exception.
			JobExecutionAlreadyRunningException,
			   com.liferay.batch.engine.exception.
				   JobInstanceAlreadyCompleteException,
			   com.liferay.batch.engine.exception.JobRestartException {

		return _batchJobExecutionLocalService.addBatchJobExecution(
			jobName, jobSettingsProperties);
	}

	/**
	 * Creates a new batch job execution with the primary key. Does not add the batch job execution to the database.
	 *
	 * @param batchJobExecutionId the primary key for the new batch job execution
	 * @return the new batch job execution
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
		createBatchJobExecution(long batchJobExecutionId) {

		return _batchJobExecutionLocalService.createBatchJobExecution(
			batchJobExecutionId);
	}

	/**
	 * Deletes the batch job execution from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecution the batch job execution
	 * @return the batch job execution that was removed
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
		deleteBatchJobExecution(
			com.liferay.batch.engine.model.BatchJobExecution
				batchJobExecution) {

		return _batchJobExecutionLocalService.deleteBatchJobExecution(
			batchJobExecution);
	}

	/**
	 * Deletes the batch job execution with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution that was removed
	 * @throws PortalException if a batch job execution with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
			deleteBatchJobExecution(long batchJobExecutionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobExecutionLocalService.deleteBatchJobExecution(
			batchJobExecutionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobExecutionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchJobExecutionLocalService.dynamicQuery();
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

		return _batchJobExecutionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _batchJobExecutionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _batchJobExecutionLocalService.dynamicQuery(
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

		return _batchJobExecutionLocalService.dynamicQueryCount(dynamicQuery);
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

		return _batchJobExecutionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
		fetchBatchJobExecution(long batchJobExecutionId) {

		return _batchJobExecutionLocalService.fetchBatchJobExecution(
			batchJobExecutionId);
	}

	/**
	 * Returns the batch job execution with the matching UUID and company.
	 *
	 * @param uuid the batch job execution's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
		fetchBatchJobExecutionByUuidAndCompanyId(String uuid, long companyId) {

		return _batchJobExecutionLocalService.
			fetchBatchJobExecutionByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
		fetchLastBatchJobExecution(long batchJobInstanceId) {

		return _batchJobExecutionLocalService.fetchLastBatchJobExecution(
			batchJobInstanceId);
	}

	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
		fetchLastBatchJobExecution(
			String batchJobName,
			com.liferay.portal.kernel.util.UnicodeProperties
				jobSettingsProperties) {

		return _batchJobExecutionLocalService.fetchLastBatchJobExecution(
			batchJobName, jobSettingsProperties);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchJobExecutionLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the batch job execution with the primary key.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution
	 * @throws PortalException if a batch job execution with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
			getBatchJobExecution(long batchJobExecutionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobExecutionLocalService.getBatchJobExecution(
			batchJobExecutionId);
	}

	/**
	 * Returns the batch job execution with the matching UUID and company.
	 *
	 * @param uuid the batch job execution's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch job execution
	 * @throws PortalException if a matching batch job execution could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
			getBatchJobExecutionByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobExecutionLocalService.
			getBatchJobExecutionByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the batch job executions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of batch job executions
	 */
	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchJobExecution>
		getBatchJobExecutions(int start, int end) {

		return _batchJobExecutionLocalService.getBatchJobExecutions(start, end);
	}

	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchJobExecution>
		getBatchJobExecutions(String status) {

		return _batchJobExecutionLocalService.getBatchJobExecutions(status);
	}

	/**
	 * Returns the number of batch job executions.
	 *
	 * @return the number of batch job executions
	 */
	@Override
	public int getBatchJobExecutionsCount() {
		return _batchJobExecutionLocalService.getBatchJobExecutionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _batchJobExecutionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchJobExecutionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchJobExecutionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchJobExecutionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch job execution in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecution the batch job execution
	 * @return the batch job execution that was updated
	 */
	@Override
	public com.liferay.batch.engine.model.BatchJobExecution
		updateBatchJobExecution(
			com.liferay.batch.engine.model.BatchJobExecution
				batchJobExecution) {

		return _batchJobExecutionLocalService.updateBatchJobExecution(
			batchJobExecution);
	}

	@Override
	public BatchJobExecutionLocalService getWrappedService() {
		return _batchJobExecutionLocalService;
	}

	@Override
	public void setWrappedService(
		BatchJobExecutionLocalService batchJobExecutionLocalService) {

		_batchJobExecutionLocalService = batchJobExecutionLocalService;
	}

	private BatchJobExecutionLocalService _batchJobExecutionLocalService;

}