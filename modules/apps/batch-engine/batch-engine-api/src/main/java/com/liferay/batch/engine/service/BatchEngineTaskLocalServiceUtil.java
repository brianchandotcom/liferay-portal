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
 * Provides the local service utility for BatchEngineTask. This utility wraps
 * <code>com.liferay.batch.engine.service.impl.BatchEngineTaskLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Shuyang Zhou
 * @see BatchEngineTaskLocalService
 * @generated
 */
public class BatchEngineTaskLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.batch.engine.service.impl.BatchEngineTaskLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the batch engine task to the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTask the batch engine task
	 * @return the batch engine task that was added
	 */
	public static com.liferay.batch.engine.model.BatchEngineTask
		addBatchEngineTask(
			com.liferay.batch.engine.model.BatchEngineTask batchEngineTask) {

		return getService().addBatchEngineTask(batchEngineTask);
	}

	public static com.liferay.batch.engine.model.BatchEngineTask
		addBatchEngineTask(
			long companyId, long userId, long batchSize, String callbackURL,
			String className, byte[] content, String contentType,
			String executeStatus, String operation, String version) {

		return getService().addBatchEngineTask(
			companyId, userId, batchSize, callbackURL, className, content,
			contentType, executeStatus, operation, version);
	}

	/**
	 * Creates a new batch engine task with the primary key. Does not add the batch engine task to the database.
	 *
	 * @param batchEngineTaskId the primary key for the new batch engine task
	 * @return the new batch engine task
	 */
	public static com.liferay.batch.engine.model.BatchEngineTask
		createBatchEngineTask(long batchEngineTaskId) {

		return getService().createBatchEngineTask(batchEngineTaskId);
	}

	/**
	 * Deletes the batch engine task from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTask the batch engine task
	 * @return the batch engine task that was removed
	 */
	public static com.liferay.batch.engine.model.BatchEngineTask
		deleteBatchEngineTask(
			com.liferay.batch.engine.model.BatchEngineTask batchEngineTask) {

		return getService().deleteBatchEngineTask(batchEngineTask);
	}

	/**
	 * Deletes the batch engine task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task that was removed
	 * @throws PortalException if a batch engine task with the primary key could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineTask
			deleteBatchEngineTask(long batchEngineTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteBatchEngineTask(batchEngineTaskId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineTaskModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineTaskModelImpl</code>.
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

	public static com.liferay.batch.engine.model.BatchEngineTask
		fetchBatchEngineTask(long batchEngineTaskId) {

		return getService().fetchBatchEngineTask(batchEngineTaskId);
	}

	/**
	 * Returns the batch engine task with the matching UUID and company.
	 *
	 * @param uuid the batch engine task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineTask
		fetchBatchEngineTaskByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchBatchEngineTaskByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the batch engine task with the primary key.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task
	 * @throws PortalException if a batch engine task with the primary key could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineTask
			getBatchEngineTask(long batchEngineTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBatchEngineTask(batchEngineTaskId);
	}

	/**
	 * Returns the batch engine task with the matching UUID and company.
	 *
	 * @param uuid the batch engine task's UUID
	 * @param companyId the primary key of the company
	 * @return the matching batch engine task
	 * @throws PortalException if a matching batch engine task could not be found
	 */
	public static com.liferay.batch.engine.model.BatchEngineTask
			getBatchEngineTaskByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBatchEngineTaskByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the batch engine tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.batch.engine.model.impl.BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of batch engine tasks
	 */
	public static java.util.List<com.liferay.batch.engine.model.BatchEngineTask>
		getBatchEngineTasks(int start, int end) {

		return getService().getBatchEngineTasks(start, end);
	}

	public static java.util.List<com.liferay.batch.engine.model.BatchEngineTask>
		getBatchEngineTasks(String executeStatus) {

		return getService().getBatchEngineTasks(executeStatus);
	}

	/**
	 * Returns the number of batch engine tasks.
	 *
	 * @return the number of batch engine tasks
	 */
	public static int getBatchEngineTasksCount() {
		return getService().getBatchEngineTasksCount();
	}

	public static com.liferay.batch.engine.model.BatchEngineTaskContentBlobModel
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
	 * Updates the batch engine task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTask the batch engine task
	 * @return the batch engine task that was updated
	 */
	public static com.liferay.batch.engine.model.BatchEngineTask
		updateBatchEngineTask(
			com.liferay.batch.engine.model.BatchEngineTask batchEngineTask) {

		return getService().updateBatchEngineTask(batchEngineTask);
	}

	public static BatchEngineTaskLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchEngineTaskLocalService, BatchEngineTaskLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchEngineTaskLocalService.class);

		ServiceTracker<BatchEngineTaskLocalService, BatchEngineTaskLocalService>
			serviceTracker =
				new ServiceTracker
					<BatchEngineTaskLocalService, BatchEngineTaskLocalService>(
						bundle.getBundleContext(),
						BatchEngineTaskLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}