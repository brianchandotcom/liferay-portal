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

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for BatchTask. This utility wraps
 * <code>com.liferay.batch.engine.service.impl.BatchTaskLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shuyang Zhou
 * @see BatchTaskLocalService
 * @generated
 */
@ProviderType
public class BatchTaskLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchTaskLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the batch task to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchTask the batch task
	 * @return the batch task that was added
	 */
	public static com.liferay.batch.engine.model.BatchTask addBatchTask(
		com.liferay.batch.engine.model.BatchTask batchTask) {

		return getService().addBatchTask(batchTask);
	}

	public static com.liferay.batch.engine.model.BatchTask addBatchTask(
		String className, String version, byte[] batchContent,
		com.liferay.batch.engine.BatchContentType batchContentType,
		com.liferay.batch.engine.BatchOperation batchOperation,
		long batchSize) {

		return getService().addBatchTask(
			className, version, batchContent, batchContentType, batchOperation,
			batchSize);
	}

	/**
	 * Creates a new batch task with the primary key. Does not add the batch task to the database.
	 *
	 * @param batchTaskId the primary key for the new batch task
	 * @return the new batch task
	 */
	public static com.liferay.batch.engine.model.BatchTask createBatchTask(
		long batchTaskId) {

		return getService().createBatchTask(batchTaskId);
	}

	/**
	 * Deletes the batch task from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchTask the batch task
	 * @return the batch task that was removed
	 */
	public static com.liferay.batch.engine.model.BatchTask deleteBatchTask(
		com.liferay.batch.engine.model.BatchTask batchTask) {

		return getService().deleteBatchTask(batchTask);
	}

	/**
	 * Deletes the batch task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchTaskId the primary key of the batch task
	 * @return the batch task that was removed
	 * @throws PortalException if a batch task with the primary key could not be found
	 */
	public static com.liferay.batch.engine.model.BatchTask deleteBatchTask(
			long batchTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteBatchTask(batchTaskId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.batch.engine.model.BatchTask fetchBatchTask(
		long batchTaskId) {

		return getService().fetchBatchTask(batchTaskId);
	}

	/**
	 * Returns the batch task with the matching UUID and company.
	 *
	 * @param uuid the batch task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch task, or <code>null</code> if a matching batch task could not be found
	 */
	public static com.liferay.batch.engine.model.BatchTask
		fetchBatchTaskByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchBatchTaskByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the batch task with the primary key.
	 *
	 * @param batchTaskId the primary key of the batch task
	 * @return the batch task
	 * @throws PortalException if a batch task with the primary key could not be found
	 */
	public static com.liferay.batch.engine.model.BatchTask getBatchTask(
			long batchTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBatchTask(batchTaskId);
	}

	/**
	 * Returns the batch task with the matching UUID and company.
	 *
	 * @param uuid the batch task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch task
	 * @throws PortalException if a matching batch task could not be found
	 */
	public static com.liferay.batch.engine.model.BatchTask
			getBatchTaskByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBatchTaskByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.batch.engine.model.BatchTask>
		getBatchTasks(int start, int end) {

		return getService().getBatchTasks(start, end);
	}

	/**
	 * Returns the number of batch tasks.
	 *
	 * @return the number of batch tasks
	 */
	public static int getBatchTasksCount() {
		return getService().getBatchTasksCount();
	}

	public static com.liferay.batch.engine.model.BatchTaskContentBlobModel
		getContentBlobModel(java.io.Serializable primaryKey) {

		return getService().getContentBlobModel(primaryKey);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the batch task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchTask the batch task
	 * @return the batch task that was updated
	 */
	public static com.liferay.batch.engine.model.BatchTask updateBatchTask(
		com.liferay.batch.engine.model.BatchTask batchTask) {

		return getService().updateBatchTask(batchTask);
	}

	public static BatchTaskLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BatchTaskLocalService, BatchTaskLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(BatchTaskLocalService.class);

		ServiceTracker<BatchTaskLocalService, BatchTaskLocalService>
			serviceTracker =
				new ServiceTracker
					<BatchTaskLocalService, BatchTaskLocalService>(
						bundle.getBundleContext(), BatchTaskLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}