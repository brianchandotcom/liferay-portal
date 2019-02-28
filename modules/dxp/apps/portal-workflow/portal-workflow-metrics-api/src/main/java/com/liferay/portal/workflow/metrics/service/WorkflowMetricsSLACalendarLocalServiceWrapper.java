/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WorkflowMetricsSLACalendarLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLACalendarLocalService
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLACalendarLocalServiceWrapper
	implements WorkflowMetricsSLACalendarLocalService,
			   ServiceWrapper<WorkflowMetricsSLACalendarLocalService> {

	public WorkflowMetricsSLACalendarLocalServiceWrapper(
		WorkflowMetricsSLACalendarLocalService
			workflowMetricsSLACalendarLocalService) {

		_workflowMetricsSLACalendarLocalService =
			workflowMetricsSLACalendarLocalService;
	}

	/**
	 * Adds the workflow metrics sla calendar to the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACalendar the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar that was added
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
		addWorkflowMetricsSLACalendar(
			com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
				workflowMetricsSLACalendar) {

		return _workflowMetricsSLACalendarLocalService.
			addWorkflowMetricsSLACalendar(workflowMetricsSLACalendar);
	}

	/**
	 * Creates a new workflow metrics sla calendar with the primary key. Does not add the workflow metrics sla calendar to the database.
	 *
	 * @param workflowMetricsSLACalendarId the primary key for the new workflow metrics sla calendar
	 * @return the new workflow metrics sla calendar
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
		createWorkflowMetricsSLACalendar(long workflowMetricsSLACalendarId) {

		return _workflowMetricsSLACalendarLocalService.
			createWorkflowMetricsSLACalendar(workflowMetricsSLACalendarId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLACalendarLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the workflow metrics sla calendar with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar that was removed
	 * @throws PortalException if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
			deleteWorkflowMetricsSLACalendar(long workflowMetricsSLACalendarId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLACalendarLocalService.
			deleteWorkflowMetricsSLACalendar(workflowMetricsSLACalendarId);
	}

	/**
	 * Deletes the workflow metrics sla calendar from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACalendar the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar that was removed
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
		deleteWorkflowMetricsSLACalendar(
			com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
				workflowMetricsSLACalendar) {

		return _workflowMetricsSLACalendarLocalService.
			deleteWorkflowMetricsSLACalendar(workflowMetricsSLACalendar);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _workflowMetricsSLACalendarLocalService.dynamicQuery();
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

		return _workflowMetricsSLACalendarLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _workflowMetricsSLACalendarLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _workflowMetricsSLACalendarLocalService.dynamicQuery(
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

		return _workflowMetricsSLACalendarLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _workflowMetricsSLACalendarLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
		fetchWorkflowMetricsSLACalendar(long workflowMetricsSLACalendarId) {

		return _workflowMetricsSLACalendarLocalService.
			fetchWorkflowMetricsSLACalendar(workflowMetricsSLACalendarId);
	}

	/**
	 * Returns the workflow metrics sla calendar matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla calendar's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla calendar, or <code>null</code> if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
		fetchWorkflowMetricsSLACalendarByUuidAndGroupId(
			String uuid, long groupId) {

		return _workflowMetricsSLACalendarLocalService.
			fetchWorkflowMetricsSLACalendarByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _workflowMetricsSLACalendarLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _workflowMetricsSLACalendarLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _workflowMetricsSLACalendarLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _workflowMetricsSLACalendarLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLACalendarLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the workflow metrics sla calendar with the primary key.
	 *
	 * @param workflowMetricsSLACalendarId the primary key of the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar
	 * @throws PortalException if a workflow metrics sla calendar with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
			getWorkflowMetricsSLACalendar(long workflowMetricsSLACalendarId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLACalendarLocalService.
			getWorkflowMetricsSLACalendar(workflowMetricsSLACalendarId);
	}

	/**
	 * Returns the workflow metrics sla calendar matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla calendar's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla calendar
	 * @throws PortalException if a matching workflow metrics sla calendar could not be found
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
			getWorkflowMetricsSLACalendarByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLACalendarLocalService.
			getWorkflowMetricsSLACalendarByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the workflow metrics sla calendars.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLACalendarModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @return the range of workflow metrics sla calendars
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar>
			getWorkflowMetricsSLACalendars(int start, int end) {

		return _workflowMetricsSLACalendarLocalService.
			getWorkflowMetricsSLACalendars(start, end);
	}

	/**
	 * Returns all the workflow metrics sla calendars matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla calendars
	 * @param companyId the primary key of the company
	 * @return the matching workflow metrics sla calendars, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar>
			getWorkflowMetricsSLACalendarsByUuidAndCompanyId(
				String uuid, long companyId) {

		return _workflowMetricsSLACalendarLocalService.
			getWorkflowMetricsSLACalendarsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of workflow metrics sla calendars matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla calendars
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of workflow metrics sla calendars
	 * @param end the upper bound of the range of workflow metrics sla calendars (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching workflow metrics sla calendars, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar>
			getWorkflowMetricsSLACalendarsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.metrics.model.
						WorkflowMetricsSLACalendar> orderByComparator) {

		return _workflowMetricsSLACalendarLocalService.
			getWorkflowMetricsSLACalendarsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of workflow metrics sla calendars.
	 *
	 * @return the number of workflow metrics sla calendars
	 */
	@Override
	public int getWorkflowMetricsSLACalendarsCount() {
		return _workflowMetricsSLACalendarLocalService.
			getWorkflowMetricsSLACalendarsCount();
	}

	/**
	 * Updates the workflow metrics sla calendar in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACalendar the workflow metrics sla calendar
	 * @return the workflow metrics sla calendar that was updated
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
		updateWorkflowMetricsSLACalendar(
			com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar
				workflowMetricsSLACalendar) {

		return _workflowMetricsSLACalendarLocalService.
			updateWorkflowMetricsSLACalendar(workflowMetricsSLACalendar);
	}

	@Override
	public WorkflowMetricsSLACalendarLocalService getWrappedService() {
		return _workflowMetricsSLACalendarLocalService;
	}

	@Override
	public void setWrappedService(
		WorkflowMetricsSLACalendarLocalService
			workflowMetricsSLACalendarLocalService) {

		_workflowMetricsSLACalendarLocalService =
			workflowMetricsSLACalendarLocalService;
	}

	private WorkflowMetricsSLACalendarLocalService
		_workflowMetricsSLACalendarLocalService;

}