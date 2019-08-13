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
 * Provides a wrapper for {@link BatchTaskLocalService}.
 *
 * @author Shuyang Zhou
 * @see BatchTaskLocalService
 * @generated
 */
@ProviderType
public class BatchTaskLocalServiceWrapper
	implements BatchTaskLocalService, ServiceWrapper<BatchTaskLocalService> {

	public BatchTaskLocalServiceWrapper(
		BatchTaskLocalService batchTaskLocalService) {

		_batchTaskLocalService = batchTaskLocalService;
	}

	/**
	 * Adds the batch task to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchTask the batch task
	 * @return the batch task that was added
	 */
	@Override
	public com.liferay.batch.engine.model.BatchTask addBatchTask(
		com.liferay.batch.engine.model.BatchTask batchTask) {

		return _batchTaskLocalService.addBatchTask(batchTask);
	}

	@Override
	public com.liferay.batch.engine.model.BatchTask addBatchTask(
		String className, String version, byte[] batchContent,
		com.liferay.batch.engine.BatchContentType batchContentType,
		com.liferay.batch.engine.BatchOperation batchOperation,
		long batchSize) {

		return _batchTaskLocalService.addBatchTask(
			className, version, batchContent, batchContentType, batchOperation,
			batchSize);
	}

	/**
	 * Creates a new batch task with the primary key. Does not add the batch task to the database.
	 *
	 * @param batchTaskId the primary key for the new batch task
	 * @return the new batch task
	 */
	@Override
	public com.liferay.batch.engine.model.BatchTask createBatchTask(
		long batchTaskId) {

		return _batchTaskLocalService.createBatchTask(batchTaskId);
	}

	/**
	 * Deletes the batch task from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchTask the batch task
	 * @return the batch task that was removed
	 */
	@Override
	public com.liferay.batch.engine.model.BatchTask deleteBatchTask(
		com.liferay.batch.engine.model.BatchTask batchTask) {

		return _batchTaskLocalService.deleteBatchTask(batchTask);
	}

	/**
	 * Deletes the batch task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchTaskId the primary key of the batch task
	 * @return the batch task that was removed
	 * @throws PortalException if a batch task with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchTask deleteBatchTask(
			long batchTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchTaskLocalService.deleteBatchTask(batchTaskId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchTaskLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchTaskLocalService.dynamicQuery();
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

		return _batchTaskLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _batchTaskLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _batchTaskLocalService.dynamicQuery(
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

		return _batchTaskLocalService.dynamicQueryCount(dynamicQuery);
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

		return _batchTaskLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.engine.model.BatchTask fetchBatchTask(
		long batchTaskId) {

		return _batchTaskLocalService.fetchBatchTask(batchTaskId);
	}

	/**
	 * Returns the batch task with the matching UUID and company.
	 *
	 * @param uuid the batch task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch task, or <code>null</code> if a matching batch task could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchTask
		fetchBatchTaskByUuidAndCompanyId(String uuid, long companyId) {

		return _batchTaskLocalService.fetchBatchTaskByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchTaskLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the batch task with the primary key.
	 *
	 * @param batchTaskId the primary key of the batch task
	 * @return the batch task
	 * @throws PortalException if a batch task with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchTask getBatchTask(
			long batchTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchTaskLocalService.getBatchTask(batchTaskId);
	}

	/**
	 * Returns the batch task with the matching UUID and company.
	 *
	 * @param uuid the batch task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch task
	 * @throws PortalException if a matching batch task could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchTask
			getBatchTaskByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchTaskLocalService.getBatchTaskByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the batch tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @return the range of batch tasks
	 */
	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchTask>
		getBatchTasks(int start, int end) {

		return _batchTaskLocalService.getBatchTasks(start, end);
	}

	/**
	 * Returns the number of batch tasks.
	 *
	 * @return the number of batch tasks
	 */
	@Override
	public int getBatchTasksCount() {
		return _batchTaskLocalService.getBatchTasksCount();
	}

	@Override
	public com.liferay.batch.engine.model.BatchTaskContentBlobModel
		getContentBlobModel(java.io.Serializable primaryKey) {

		return _batchTaskLocalService.getContentBlobModel(primaryKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _batchTaskLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchTaskLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchTaskLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchTaskLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchTask the batch task
	 * @return the batch task that was updated
	 */
	@Override
	public com.liferay.batch.engine.model.BatchTask updateBatchTask(
		com.liferay.batch.engine.model.BatchTask batchTask) {

		return _batchTaskLocalService.updateBatchTask(batchTask);
	}

	@Override
	public BatchTaskLocalService getWrappedService() {
		return _batchTaskLocalService;
	}

	@Override
	public void setWrappedService(BatchTaskLocalService batchTaskLocalService) {
		_batchTaskLocalService = batchTaskLocalService;
	}

	private BatchTaskLocalService _batchTaskLocalService;

}