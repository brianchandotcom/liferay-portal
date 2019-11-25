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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SchedulerProcessLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see SchedulerProcessLocalService
 * @generated
 */
public class SchedulerProcessLocalServiceWrapper
	implements SchedulerProcessLocalService,
			   ServiceWrapper<SchedulerProcessLocalService> {

	public SchedulerProcessLocalServiceWrapper(
		SchedulerProcessLocalService schedulerProcessLocalService) {

		_schedulerProcessLocalService = schedulerProcessLocalService;
	}

	/**
	 * Adds the scheduler process to the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcess the scheduler process
	 * @return the scheduler process that was added
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcess addSchedulerProcess(
		com.liferay.scheduler.model.SchedulerProcess schedulerProcess) {

		return _schedulerProcessLocalService.addSchedulerProcess(
			schedulerProcess);
	}

	/**
	 * Creates a new scheduler process with the primary key. Does not add the scheduler process to the database.
	 *
	 * @param schedulerProcessId the primary key for the new scheduler process
	 * @return the new scheduler process
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcess createSchedulerProcess(
		long schedulerProcessId) {

		return _schedulerProcessLocalService.createSchedulerProcess(
			schedulerProcessId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schedulerProcessLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the scheduler process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process that was removed
	 * @throws PortalException if a scheduler process with the primary key could not be found
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcess deleteSchedulerProcess(
			long schedulerProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schedulerProcessLocalService.deleteSchedulerProcess(
			schedulerProcessId);
	}

	/**
	 * Deletes the scheduler process from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcess the scheduler process
	 * @return the scheduler process that was removed
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcess deleteSchedulerProcess(
		com.liferay.scheduler.model.SchedulerProcess schedulerProcess) {

		return _schedulerProcessLocalService.deleteSchedulerProcess(
			schedulerProcess);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _schedulerProcessLocalService.dynamicQuery();
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

		return _schedulerProcessLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _schedulerProcessLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _schedulerProcessLocalService.dynamicQuery(
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

		return _schedulerProcessLocalService.dynamicQueryCount(dynamicQuery);
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

		return _schedulerProcessLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.scheduler.model.SchedulerProcess fetchSchedulerProcess(
		long schedulerProcessId) {

		return _schedulerProcessLocalService.fetchSchedulerProcess(
			schedulerProcessId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _schedulerProcessLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _schedulerProcessLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _schedulerProcessLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schedulerProcessLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the scheduler process with the primary key.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process
	 * @throws PortalException if a scheduler process with the primary key could not be found
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcess getSchedulerProcess(
			long schedulerProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schedulerProcessLocalService.getSchedulerProcess(
			schedulerProcessId);
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
	@Override
	public java.util.List<com.liferay.scheduler.model.SchedulerProcess>
		getSchedulerProcesses(int start, int end) {

		return _schedulerProcessLocalService.getSchedulerProcesses(start, end);
	}

	/**
	 * Returns the number of scheduler processes.
	 *
	 * @return the number of scheduler processes
	 */
	@Override
	public int getSchedulerProcessesCount() {
		return _schedulerProcessLocalService.getSchedulerProcessesCount();
	}

	/**
	 * Updates the scheduler process in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcess the scheduler process
	 * @return the scheduler process that was updated
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcess updateSchedulerProcess(
		com.liferay.scheduler.model.SchedulerProcess schedulerProcess) {

		return _schedulerProcessLocalService.updateSchedulerProcess(
			schedulerProcess);
	}

	@Override
	public SchedulerProcessLocalService getWrappedService() {
		return _schedulerProcessLocalService;
	}

	@Override
	public void setWrappedService(
		SchedulerProcessLocalService schedulerProcessLocalService) {

		_schedulerProcessLocalService = schedulerProcessLocalService;
	}

	private SchedulerProcessLocalService _schedulerProcessLocalService;

}