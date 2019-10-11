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

package com.liferay.portal.workflow.kaleo.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for KaleoTask. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoTaskLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskLocalService
 * @generated
 */
public class KaleoTaskLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoTaskLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kaleo task to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTask the kaleo task
	 * @return the kaleo task that was added
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
		addKaleoTask(
			com.liferay.portal.workflow.kaleo.model.KaleoTask kaleoTask) {

		return getService().addKaleoTask(kaleoTask);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
			addKaleoTask(
				long kaleoDefinitionVersionId, long kaleoNodeId,
				com.liferay.portal.workflow.kaleo.definition.Task task,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKaleoTask(
			kaleoDefinitionVersionId, kaleoNodeId, task, serviceContext);
	}

	/**
	 * Creates a new kaleo task with the primary key. Does not add the kaleo task to the database.
	 *
	 * @param kaleoTaskId the primary key for the new kaleo task
	 * @return the new kaleo task
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
		createKaleoTask(long kaleoTaskId) {

		return getService().createKaleoTask(kaleoTaskId);
	}

	public static void deleteCompanyKaleoTasks(long companyId) {
		getService().deleteCompanyKaleoTasks(companyId);
	}

	public static void deleteKaleoDefinitionVersionKaleoTasks(
		long kaleoDefinitionVersionId) {

		getService().deleteKaleoDefinitionVersionKaleoTasks(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo task from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTask the kaleo task
	 * @return the kaleo task that was removed
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
		deleteKaleoTask(
			com.liferay.portal.workflow.kaleo.model.KaleoTask kaleoTask) {

		return getService().deleteKaleoTask(kaleoTask);
	}

	/**
	 * Deletes the kaleo task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskId the primary key of the kaleo task
	 * @return the kaleo task that was removed
	 * @throws PortalException if a kaleo task with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
			deleteKaleoTask(long kaleoTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoTask(kaleoTaskId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskModelImpl</code>.
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

	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
		fetchKaleoTask(long kaleoTaskId) {

		return getService().fetchKaleoTask(kaleoTaskId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
			getKaleoNodeKaleoTask(long kaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoNodeKaleoTask(kaleoNodeId);
	}

	/**
	 * Returns the kaleo task with the primary key.
	 *
	 * @param kaleoTaskId the primary key of the kaleo task
	 * @return the kaleo task
	 * @throws PortalException if a kaleo task with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
			getKaleoTask(long kaleoTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoTask(kaleoTaskId);
	}

	/**
	 * Returns a range of all the kaleo tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo tasks
	 * @param end the upper bound of the range of kaleo tasks (not inclusive)
	 * @return the range of kaleo tasks
	 */
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTask> getKaleoTasks(
			int start, int end) {

		return getService().getKaleoTasks(start, end);
	}

	/**
	 * Returns the number of kaleo tasks.
	 *
	 * @return the number of kaleo tasks
	 */
	public static int getKaleoTasksCount() {
		return getService().getKaleoTasksCount();
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
	 * Updates the kaleo task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTask the kaleo task
	 * @return the kaleo task that was updated
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTask
		updateKaleoTask(
			com.liferay.portal.workflow.kaleo.model.KaleoTask kaleoTask) {

		return getService().updateKaleoTask(kaleoTask);
	}

	public static KaleoTaskLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KaleoTaskLocalService, KaleoTaskLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KaleoTaskLocalService.class);

		ServiceTracker<KaleoTaskLocalService, KaleoTaskLocalService>
			serviceTracker =
				new ServiceTracker
					<KaleoTaskLocalService, KaleoTaskLocalService>(
						bundle.getBundleContext(), KaleoTaskLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}