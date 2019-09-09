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

/**
 * Provides a wrapper for {@link BatchEngineTaskLocalService}.
 *
 * @author Shuyang Zhou
 * @see BatchEngineTaskLocalService
 * @generated
 */
public class BatchEngineTaskLocalServiceWrapper
	implements BatchEngineTaskLocalService,
			   ServiceWrapper<BatchEngineTaskLocalService> {

	public BatchEngineTaskLocalServiceWrapper(
		BatchEngineTaskLocalService batchEngineTaskLocalService) {

		_batchEngineTaskLocalService = batchEngineTaskLocalService;
	}

	/**
	 * Adds the batch engine task to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTask the batch engine task
	 * @return the batch engine task that was added
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineTask addBatchEngineTask(
		com.liferay.batch.engine.model.BatchEngineTask batchEngineTask) {

		return _batchEngineTaskLocalService.addBatchEngineTask(batchEngineTask);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineTask addBatchEngineTask(
		long companyId, long userId,
		com.liferay.batch.engine.BatchEngineTaskContentType
			batchEngineTaskContentType,
		com.liferay.batch.engine.BatchEngineTaskOperation
			batchEngineTaskOperation,
		long batchSize, String className, byte[] content, String version) {

		return _batchEngineTaskLocalService.addBatchEngineTask(
			companyId, userId, batchEngineTaskContentType,
			batchEngineTaskOperation, batchSize, className, content, version);
	}

	/**
	 * Creates a new batch engine task with the primary key. Does not add the batch engine task to the database.
	 *
	 * @param batchEngineTaskId the primary key for the new batch engine task
	 * @return the new batch engine task
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineTask createBatchEngineTask(
		long batchEngineTaskId) {

		return _batchEngineTaskLocalService.createBatchEngineTask(
			batchEngineTaskId);
	}

	/**
	 * Deletes the batch engine task from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTask the batch engine task
	 * @return the batch engine task that was removed
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineTask deleteBatchEngineTask(
		com.liferay.batch.engine.model.BatchEngineTask batchEngineTask) {

		return _batchEngineTaskLocalService.deleteBatchEngineTask(
			batchEngineTask);
	}

	/**
	 * Deletes the batch engine task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task that was removed
	 * @throws PortalException if a batch engine task with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineTask deleteBatchEngineTask(
			long batchEngineTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineTaskLocalService.deleteBatchEngineTask(
			batchEngineTaskId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineTaskLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _batchEngineTaskLocalService.dynamicQuery();
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

		return _batchEngineTaskLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _batchEngineTaskLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _batchEngineTaskLocalService.dynamicQuery(
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

		return _batchEngineTaskLocalService.dynamicQueryCount(dynamicQuery);
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

		return _batchEngineTaskLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineTask fetchBatchEngineTask(
		long batchEngineTaskId) {

		return _batchEngineTaskLocalService.fetchBatchEngineTask(
			batchEngineTaskId);
	}

	/**
	 * Returns the batch engine task with the matching UUID and company.
	 *
	 * @param uuid the batch engine task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineTask
		fetchBatchEngineTaskByUuidAndCompanyId(String uuid, long companyId) {

		return _batchEngineTaskLocalService.
			fetchBatchEngineTaskByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _batchEngineTaskLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the batch engine task with the primary key.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task
	 * @throws PortalException if a batch engine task with the primary key could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineTask getBatchEngineTask(
			long batchEngineTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineTaskLocalService.getBatchEngineTask(
			batchEngineTaskId);
	}

	/**
	 * Returns the batch engine task with the matching UUID and company.
	 *
	 * @param uuid the batch engine task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine task
	 * @throws PortalException if a matching batch engine task could not be found
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineTask
			getBatchEngineTaskByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineTaskLocalService.
			getBatchEngineTaskByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the batch engine tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of batch engine tasks
	 */
	@Override
	public java.util.List<com.liferay.batch.engine.model.BatchEngineTask>
		getBatchEngineTasks(int start, int end) {

		return _batchEngineTaskLocalService.getBatchEngineTasks(start, end);
	}

	/**
	 * Returns the number of batch engine tasks.
	 *
	 * @return the number of batch engine tasks
	 */
	@Override
	public int getBatchEngineTasksCount() {
		return _batchEngineTaskLocalService.getBatchEngineTasksCount();
	}

	@Override
	public com.liferay.batch.engine.model.BatchEngineTaskContentBlobModel
		getContentBlobModel(java.io.Serializable primaryKey) {

		return _batchEngineTaskLocalService.getContentBlobModel(primaryKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _batchEngineTaskLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _batchEngineTaskLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _batchEngineTaskLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _batchEngineTaskLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch engine task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTask the batch engine task
	 * @return the batch engine task that was updated
	 */
	@Override
	public com.liferay.batch.engine.model.BatchEngineTask updateBatchEngineTask(
		com.liferay.batch.engine.model.BatchEngineTask batchEngineTask) {

		return _batchEngineTaskLocalService.updateBatchEngineTask(
			batchEngineTask);
	}

	@Override
	public BatchEngineTaskLocalService getWrappedService() {
		return _batchEngineTaskLocalService;
	}

	@Override
	public void setWrappedService(
		BatchEngineTaskLocalService batchEngineTaskLocalService) {

		_batchEngineTaskLocalService = batchEngineTaskLocalService;
	}

	private BatchEngineTaskLocalService _batchEngineTaskLocalService;

}