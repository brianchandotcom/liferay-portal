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
 * Provides a wrapper for {@link SchedulerProcessLogLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see SchedulerProcessLogLocalService
 * @generated
 */
public class SchedulerProcessLogLocalServiceWrapper
	implements SchedulerProcessLogLocalService,
			   ServiceWrapper<SchedulerProcessLogLocalService> {

	public SchedulerProcessLogLocalServiceWrapper(
		SchedulerProcessLogLocalService schedulerProcessLogLocalService) {

		_schedulerProcessLogLocalService = schedulerProcessLogLocalService;
	}

	/**
	 * Adds the scheduler process log to the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessLog the scheduler process log
	 * @return the scheduler process log that was added
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcessLog
		addSchedulerProcessLog(
			com.liferay.scheduler.model.SchedulerProcessLog
				schedulerProcessLog) {

		return _schedulerProcessLogLocalService.addSchedulerProcessLog(
			schedulerProcessLog);
	}

	/**
	 * Creates a new scheduler process log with the primary key. Does not add the scheduler process log to the database.
	 *
	 * @param schedulerProcessLogId the primary key for the new scheduler process log
	 * @return the new scheduler process log
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcessLog
		createSchedulerProcessLog(long schedulerProcessLogId) {

		return _schedulerProcessLogLocalService.createSchedulerProcessLog(
			schedulerProcessLogId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schedulerProcessLogLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the scheduler process log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessLogId the primary key of the scheduler process log
	 * @return the scheduler process log that was removed
	 * @throws PortalException if a scheduler process log with the primary key could not be found
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcessLog
			deleteSchedulerProcessLog(long schedulerProcessLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schedulerProcessLogLocalService.deleteSchedulerProcessLog(
			schedulerProcessLogId);
	}

	/**
	 * Deletes the scheduler process log from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessLog the scheduler process log
	 * @return the scheduler process log that was removed
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcessLog
		deleteSchedulerProcessLog(
			com.liferay.scheduler.model.SchedulerProcessLog
				schedulerProcessLog) {

		return _schedulerProcessLogLocalService.deleteSchedulerProcessLog(
			schedulerProcessLog);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _schedulerProcessLogLocalService.dynamicQuery();
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

		return _schedulerProcessLogLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.scheduler.model.impl.SchedulerProcessLogModelImpl</code>.
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

		return _schedulerProcessLogLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.scheduler.model.impl.SchedulerProcessLogModelImpl</code>.
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

		return _schedulerProcessLogLocalService.dynamicQuery(
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

		return _schedulerProcessLogLocalService.dynamicQueryCount(dynamicQuery);
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

		return _schedulerProcessLogLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.scheduler.model.SchedulerProcessLog
		fetchSchedulerProcessLog(long schedulerProcessLogId) {

		return _schedulerProcessLogLocalService.fetchSchedulerProcessLog(
			schedulerProcessLogId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _schedulerProcessLogLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _schedulerProcessLogLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _schedulerProcessLogLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schedulerProcessLogLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the scheduler process log with the primary key.
	 *
	 * @param schedulerProcessLogId the primary key of the scheduler process log
	 * @return the scheduler process log
	 * @throws PortalException if a scheduler process log with the primary key could not be found
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcessLog
			getSchedulerProcessLog(long schedulerProcessLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schedulerProcessLogLocalService.getSchedulerProcessLog(
			schedulerProcessLogId);
	}

	/**
	 * Returns a range of all the scheduler process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.scheduler.model.impl.SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @return the range of scheduler process logs
	 */
	@Override
	public java.util.List<com.liferay.scheduler.model.SchedulerProcessLog>
		getSchedulerProcessLogs(int start, int end) {

		return _schedulerProcessLogLocalService.getSchedulerProcessLogs(
			start, end);
	}

	/**
	 * Returns the number of scheduler process logs.
	 *
	 * @return the number of scheduler process logs
	 */
	@Override
	public int getSchedulerProcessLogsCount() {
		return _schedulerProcessLogLocalService.getSchedulerProcessLogsCount();
	}

	/**
	 * Updates the scheduler process log in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessLog the scheduler process log
	 * @return the scheduler process log that was updated
	 */
	@Override
	public com.liferay.scheduler.model.SchedulerProcessLog
		updateSchedulerProcessLog(
			com.liferay.scheduler.model.SchedulerProcessLog
				schedulerProcessLog) {

		return _schedulerProcessLogLocalService.updateSchedulerProcessLog(
			schedulerProcessLog);
	}

	@Override
	public SchedulerProcessLogLocalService getWrappedService() {
		return _schedulerProcessLogLocalService;
	}

	@Override
	public void setWrappedService(
		SchedulerProcessLogLocalService schedulerProcessLogLocalService) {

		_schedulerProcessLogLocalService = schedulerProcessLogLocalService;
	}

	private SchedulerProcessLogLocalService _schedulerProcessLogLocalService;

}