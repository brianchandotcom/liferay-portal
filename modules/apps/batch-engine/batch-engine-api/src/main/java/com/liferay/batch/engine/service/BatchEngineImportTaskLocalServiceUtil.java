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
 * Provides the local service utility for BatchEngineImportTask. This utility wraps
 * <code>com.liferay.batch.engine.service.impl.BatchEngineImportTaskLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskLocalService
 * @generated
 */
public class BatchEngineImportTaskLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchEngineImportTaskLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the batch engine import task to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTask the batch engine import task
	 * @return the batch engine import task that was added
	 */
	public static com.liferay.batch.engine.model.BatchEngineImportTask
		addBatchEngineImportTask(
			com.liferay.batch.engine.model.BatchEngineImportTask
				batchEngineImportTask) {

		return getService().addBatchEngineImportTask(batchEngineImportTask);
	}

	public static com.liferay.batch.engine.model.BatchEngineImportTask
		addBatchEngineImportTask(
			long companyId, long userId, long batchSize, String callbackURL,
			String className, byte[] content, String contentType,
			String executeStatus,
			java.util.Map<String, String> fieldNameMappingMap, String operation,
			String version) {

		return getService().addBatchEngineImportTask(
			companyId, userId, batchSize, callbackURL, className, content,
			contentType, executeStatus, fieldNameMappingMap, operation,
			version);
	}

	/**
	 * Creates a new batch engine import task with the primary key. Does not add the batch engine import task to the database.
	 *
	 * @param batchEngineImportTaskId the primary key for the new batch engine import task
	 * @return the new batch engine import task
	 */
	public static com.liferay.batch.engine.model.BatchEngineImportTask
		createBatchEngineImportTask(long batchEngineImportTaskId) {

		return getService().createBatchEngineImportTask(
			batchEngineImportTaskId);
	}

	/**
	 * Deletes the batch engine import task from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTask the batch engine import task
	 * @return the batch engine import task that was removed
	 */
	public static com.liferay.batch.engine.model.BatchEngineImportTask
		deleteBatchEngineImportTask(
			com.liferay.batch.engine.model.BatchEngineImportTask
				batchEngineImportTask) {

		return getService().deleteBatchEngineImportTask(batchEngineImportTask);
	}

	/**
	 * Deletes the batch engine import task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task that was removed
	 * @throws PortalException if a batch engine import task with the primary key could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineImportTask
			deleteBatchEngineImportTask(long batchEngineImportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteBatchEngineImportTask(
			batchEngineImportTaskId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskModelImpl</code>.
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

	public static com.liferay.batch.engine.model.BatchEngineImportTask
		fetchBatchEngineImportTask(long batchEngineImportTaskId) {

		return getService().fetchBatchEngineImportTask(batchEngineImportTaskId);
	}

	/**
	 * Returns the batch engine import task with the matching UUID and company.
	 *
	 * @param uuid the batch engine import task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineImportTask
		fetchBatchEngineImportTaskByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchBatchEngineImportTaskByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the batch engine import task with the primary key.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task
	 * @throws PortalException if a batch engine import task with the primary key could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineImportTask
			getBatchEngineImportTask(long batchEngineImportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBatchEngineImportTask(batchEngineImportTaskId);
	}

	/**
	 * Returns the batch engine import task with the matching UUID and company.
	 *
	 * @param uuid the batch engine import task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine import task
	 * @throws PortalException if a matching batch engine import task could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineImportTask
			getBatchEngineImportTaskByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBatchEngineImportTaskByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the batch engine import tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of batch engine import tasks
	 */
	public static java.util.List
		<com.liferay.batch.engine.model.BatchEngineImportTask>
			getBatchEngineImportTasks(int start, int end) {

		return getService().getBatchEngineImportTasks(start, end);
	}

	public static java.util.List
		<com.liferay.batch.engine.model.BatchEngineImportTask>
			getBatchEngineImportTasks(String executeStatus) {

		return getService().getBatchEngineImportTasks(executeStatus);
	}

	/**
	 * Returns the number of batch engine import tasks.
	 *
	 * @return the number of batch engine import tasks
	 */
	public static int getBatchEngineImportTasksCount() {
		return getService().getBatchEngineImportTasksCount();
	}

	public static
		com.liferay.batch.engine.model.BatchEngineImportTaskContentBlobModel
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
	 * Updates the batch engine import task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTask the batch engine import task
	 * @return the batch engine import task that was updated
	 */
	public static com.liferay.batch.engine.model.BatchEngineImportTask
		updateBatchEngineImportTask(
			com.liferay.batch.engine.model.BatchEngineImportTask
				batchEngineImportTask) {

		return getService().updateBatchEngineImportTask(batchEngineImportTask);
	}

	public static BatchEngineImportTaskLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchEngineImportTaskLocalService, BatchEngineImportTaskLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchEngineImportTaskLocalService.class);

		ServiceTracker
			<BatchEngineImportTaskLocalService,
			 BatchEngineImportTaskLocalService> serviceTracker =
				new ServiceTracker
					<BatchEngineImportTaskLocalService,
					 BatchEngineImportTaskLocalService>(
						 bundle.getBundleContext(),
						 BatchEngineImportTaskLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}