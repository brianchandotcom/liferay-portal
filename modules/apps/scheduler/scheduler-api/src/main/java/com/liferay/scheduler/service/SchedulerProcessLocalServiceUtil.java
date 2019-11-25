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

package com.liferay.scheduler.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SchedulerProcess. This utility wraps
 * <code>com.liferay.scheduler.service.impl.SchedulerProcessLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see SchedulerProcessLocalService
 * @generated
 */
public class SchedulerProcessLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.scheduler.service.impl.SchedulerProcessLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the scheduler process to the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcess the scheduler process
	 * @return the scheduler process that was added
	 */
	public static com.liferay.scheduler.model.SchedulerProcess
		addSchedulerProcess(
			com.liferay.scheduler.model.SchedulerProcess schedulerProcess) {

		return getService().addSchedulerProcess(schedulerProcess);
	}

	/**
	 * Creates a new scheduler process with the primary key. Does not add the scheduler process to the database.
	 *
	 * @param schedulerProcessId the primary key for the new scheduler process
	 * @return the new scheduler process
	 */
	public static com.liferay.scheduler.model.SchedulerProcess
		createSchedulerProcess(long schedulerProcessId) {

		return getService().createSchedulerProcess(schedulerProcessId);
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

	/**
	 * Deletes the scheduler process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process that was removed
	 * @throws PortalException if a scheduler process with the primary key could not be found
	 */
	public static com.liferay.scheduler.model.SchedulerProcess
			deleteSchedulerProcess(long schedulerProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSchedulerProcess(schedulerProcessId);
	}

	/**
	 * Deletes the scheduler process from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcess the scheduler process
	 * @return the scheduler process that was removed
	 */
	public static com.liferay.scheduler.model.SchedulerProcess
		deleteSchedulerProcess(
			com.liferay.scheduler.model.SchedulerProcess schedulerProcess) {

		return getService().deleteSchedulerProcess(schedulerProcess);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.scheduler.model.impl.SchedulerProcessModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.scheduler.model.impl.SchedulerProcessModelImpl</code>.
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

	public static com.liferay.scheduler.model.SchedulerProcess
		fetchSchedulerProcess(long schedulerProcessId) {

		return getService().fetchSchedulerProcess(schedulerProcessId);
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
	 * Returns the scheduler process with the primary key.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process
	 * @throws PortalException if a scheduler process with the primary key could not be found
	 */
	public static com.liferay.scheduler.model.SchedulerProcess
			getSchedulerProcess(long schedulerProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSchedulerProcess(schedulerProcessId);
	}

	/**
	 * Returns a range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.scheduler.model.impl.SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of scheduler processes
	 */
	public static java.util.List<com.liferay.scheduler.model.SchedulerProcess>
		getSchedulerProcesses(int start, int end) {

		return getService().getSchedulerProcesses(start, end);
	}

	/**
	 * Returns the number of scheduler processes.
	 *
	 * @return the number of scheduler processes
	 */
	public static int getSchedulerProcessesCount() {
		return getService().getSchedulerProcessesCount();
	}

	/**
	 * Updates the scheduler process in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcess the scheduler process
	 * @return the scheduler process that was updated
	 */
	public static com.liferay.scheduler.model.SchedulerProcess
		updateSchedulerProcess(
			com.liferay.scheduler.model.SchedulerProcess schedulerProcess) {

		return getService().updateSchedulerProcess(schedulerProcess);
	}

	public static SchedulerProcessLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SchedulerProcessLocalService, SchedulerProcessLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SchedulerProcessLocalService.class);

		ServiceTracker
			<SchedulerProcessLocalService, SchedulerProcessLocalService>
				serviceTracker =
					new ServiceTracker
						<SchedulerProcessLocalService,
						 SchedulerProcessLocalService>(
							 bundle.getBundleContext(),
							 SchedulerProcessLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}