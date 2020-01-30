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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for BatchEngineExportTask. This utility wraps
 * <code>com.liferay.batch.engine.service.impl.BatchEngineExportTaskLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTaskLocalService
 * @generated
 */
public class BatchEngineExportTaskLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchEngineExportTaskLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the batch engine export task to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineExportTask the batch engine export task
	 * @return the batch engine export task that was added
	 */
	public static com.liferay.batch.engine.model.BatchEngineExportTask
		addBatchEngineExportTask(
			com.liferay.batch.engine.model.BatchEngineExportTask
				batchEngineExportTask) {

		return getService().addBatchEngineExportTask(batchEngineExportTask);
	}

	public static com.liferay.batch.engine.model.BatchEngineExportTask
		addBatchEngineExportTask(
			long companyId, long userId, String callbackURL, String className,
			String contentType, String executeStatus,
			java.util.List<String> fieldNamesList,
			java.util.Map<String, java.io.Serializable> parameters) {

		return getService().addBatchEngineExportTask(
			companyId, userId, callbackURL, className, contentType,
			executeStatus, fieldNamesList, parameters);
	}

	/**
	 * Creates a new batch engine export task with the primary key. Does not add the batch engine export task to the database.
	 *
	 * @param batchEngineExportTaskId the primary key for the new batch engine export task
	 * @return the new batch engine export task
	 */
	public static com.liferay.batch.engine.model.BatchEngineExportTask
		createBatchEngineExportTask(long batchEngineExportTaskId) {

		return getService().createBatchEngineExportTask(
			batchEngineExportTaskId);
	}

	/**
	 * Deletes the batch engine export task from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineExportTask the batch engine export task
	 * @return the batch engine export task that was removed
	 */
	public static com.liferay.batch.engine.model.BatchEngineExportTask
		deleteBatchEngineExportTask(
			com.liferay.batch.engine.model.BatchEngineExportTask
				batchEngineExportTask) {

		return getService().deleteBatchEngineExportTask(batchEngineExportTask);
	}

	/**
	 * Deletes the batch engine export task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineExportTaskId the primary key of the batch engine export task
	 * @return the batch engine export task that was removed
	 * @throws PortalException if a batch engine export task with the primary key could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineExportTask
			deleteBatchEngineExportTask(long batchEngineExportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteBatchEngineExportTask(
			batchEngineExportTaskId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineExportTaskModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineExportTaskModelImpl</code>.
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

	public static com.liferay.batch.engine.model.BatchEngineExportTask
		fetchBatchEngineExportTask(long batchEngineExportTaskId) {

		return getService().fetchBatchEngineExportTask(batchEngineExportTaskId);
	}

	/**
	 * Returns the batch engine export task with the matching UUID and company.
	 *
	 * @param uuid the batch engine export task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineExportTask
		fetchBatchEngineExportTaskByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchBatchEngineExportTaskByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the batch engine export task with the primary key.
	 *
	 * @param batchEngineExportTaskId the primary key of the batch engine export task
	 * @return the batch engine export task
	 * @throws PortalException if a batch engine export task with the primary key could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineExportTask
			getBatchEngineExportTask(long batchEngineExportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBatchEngineExportTask(batchEngineExportTaskId);
	}

	/**
	 * Returns the batch engine export task with the matching UUID and company.
	 *
	 * @param uuid the batch engine export task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine export task
	 * @throws PortalException if a matching batch engine export task could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineExportTask
			getBatchEngineExportTaskByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBatchEngineExportTaskByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the batch engine export tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of batch engine export tasks
	 */
	public static java.util.List
		<com.liferay.batch.engine.model.BatchEngineExportTask>
			getBatchEngineExportTasks(int start, int end) {

		return getService().getBatchEngineExportTasks(start, end);
	}

	public static java.util.List
		<com.liferay.batch.engine.model.BatchEngineExportTask>
			getBatchEngineExportTasks(String executeStatus) {

		return getService().getBatchEngineExportTasks(executeStatus);
	}

	/**
	 * Returns the number of batch engine export tasks.
	 *
	 * @return the number of batch engine export tasks
	 */
	public static int getBatchEngineExportTasksCount() {
		return getService().getBatchEngineExportTasksCount();
	}

	public static
		com.liferay.batch.engine.model.BatchEngineExportTaskContentBlobModel
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

	public static java.io.InputStream openContentInputStream(
		long batchEngineExportTaskId) {

		return getService().openContentInputStream(batchEngineExportTaskId);
	}

	/**
	 * Updates the batch engine export task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineExportTask the batch engine export task
	 * @return the batch engine export task that was updated
	 */
	public static com.liferay.batch.engine.model.BatchEngineExportTask
		updateBatchEngineExportTask(
			com.liferay.batch.engine.model.BatchEngineExportTask
				batchEngineExportTask) {

		return getService().updateBatchEngineExportTask(batchEngineExportTask);
	}

	public static BatchEngineExportTaskLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchEngineExportTaskLocalService, BatchEngineExportTaskLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchEngineExportTaskLocalService.class);

		ServiceTracker
			<BatchEngineExportTaskLocalService,
			 BatchEngineExportTaskLocalService> serviceTracker =
				new ServiceTracker
					<BatchEngineExportTaskLocalService,
					 BatchEngineExportTaskLocalService>(
						 bundle.getBundleContext(),
						 BatchEngineExportTaskLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}