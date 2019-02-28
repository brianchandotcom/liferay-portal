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
 * Provides a wrapper for {@link WorkflowMetricsSLAConditionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLAConditionLocalService
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLAConditionLocalServiceWrapper
	implements WorkflowMetricsSLAConditionLocalService,
			   ServiceWrapper<WorkflowMetricsSLAConditionLocalService> {

	public WorkflowMetricsSLAConditionLocalServiceWrapper(
		WorkflowMetricsSLAConditionLocalService
			workflowMetricsSLAConditionLocalService) {

		_workflowMetricsSLAConditionLocalService =
			workflowMetricsSLAConditionLocalService;
	}

	/**
	 * Adds the workflow metrics sla condition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was added
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
		addWorkflowMetricsSLACondition(
			com.liferay.portal.workflow.metrics.model.
				WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		return _workflowMetricsSLAConditionLocalService.
			addWorkflowMetricsSLACondition(workflowMetricsSLACondition);
	}

	/**
	 * Creates a new workflow metrics sla condition with the primary key. Does not add the workflow metrics sla condition to the database.
	 *
	 * @param workflowMetricsSLAConditionId the primary key for the new workflow metrics sla condition
	 * @return the new workflow metrics sla condition
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
		createWorkflowMetricsSLACondition(long workflowMetricsSLAConditionId) {

		return _workflowMetricsSLAConditionLocalService.
			createWorkflowMetricsSLACondition(workflowMetricsSLAConditionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLAConditionLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the workflow metrics sla condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was removed
	 * @throws PortalException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
			deleteWorkflowMetricsSLACondition(
				long workflowMetricsSLAConditionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLAConditionLocalService.
			deleteWorkflowMetricsSLACondition(workflowMetricsSLAConditionId);
	}

	/**
	 * Deletes the workflow metrics sla condition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was removed
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
		deleteWorkflowMetricsSLACondition(
			com.liferay.portal.workflow.metrics.model.
				WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		return _workflowMetricsSLAConditionLocalService.
			deleteWorkflowMetricsSLACondition(workflowMetricsSLACondition);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _workflowMetricsSLAConditionLocalService.dynamicQuery();
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

		return _workflowMetricsSLAConditionLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _workflowMetricsSLAConditionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _workflowMetricsSLAConditionLocalService.dynamicQuery(
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

		return _workflowMetricsSLAConditionLocalService.dynamicQueryCount(
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

		return _workflowMetricsSLAConditionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
		fetchWorkflowMetricsSLACondition(long workflowMetricsSLAConditionId) {

		return _workflowMetricsSLAConditionLocalService.
			fetchWorkflowMetricsSLACondition(workflowMetricsSLAConditionId);
	}

	/**
	 * Returns the workflow metrics sla condition matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla condition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
		fetchWorkflowMetricsSLAConditionByUuidAndGroupId(
			String uuid, long groupId) {

		return _workflowMetricsSLAConditionLocalService.
			fetchWorkflowMetricsSLAConditionByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _workflowMetricsSLAConditionLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _workflowMetricsSLAConditionLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _workflowMetricsSLAConditionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _workflowMetricsSLAConditionLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLAConditionLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the workflow metrics sla condition with the primary key.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition
	 * @throws PortalException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
			getWorkflowMetricsSLACondition(long workflowMetricsSLAConditionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLAConditionLocalService.
			getWorkflowMetricsSLACondition(workflowMetricsSLAConditionId);
	}

	/**
	 * Returns the workflow metrics sla condition matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla condition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla condition
	 * @throws PortalException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
			getWorkflowMetricsSLAConditionByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLAConditionLocalService.
			getWorkflowMetricsSLAConditionByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the workflow metrics sla conditions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @return the range of workflow metrics sla conditions
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition>
			getWorkflowMetricsSLAConditions(int start, int end) {

		return _workflowMetricsSLAConditionLocalService.
			getWorkflowMetricsSLAConditions(start, end);
	}

	/**
	 * Returns all the workflow metrics sla conditions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla conditions
	 * @param companyId the primary key of the company
	 * @return the matching workflow metrics sla conditions, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition>
			getWorkflowMetricsSLAConditionsByUuidAndCompanyId(
				String uuid, long companyId) {

		return _workflowMetricsSLAConditionLocalService.
			getWorkflowMetricsSLAConditionsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of workflow metrics sla conditions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla conditions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching workflow metrics sla conditions, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition>
			getWorkflowMetricsSLAConditionsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.metrics.model.
						WorkflowMetricsSLACondition> orderByComparator) {

		return _workflowMetricsSLAConditionLocalService.
			getWorkflowMetricsSLAConditionsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of workflow metrics sla conditions.
	 *
	 * @return the number of workflow metrics sla conditions
	 */
	@Override
	public int getWorkflowMetricsSLAConditionsCount() {
		return _workflowMetricsSLAConditionLocalService.
			getWorkflowMetricsSLAConditionsCount();
	}

	/**
	 * Updates the workflow metrics sla condition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was updated
	 */
	@Override
	public com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition
		updateWorkflowMetricsSLACondition(
			com.liferay.portal.workflow.metrics.model.
				WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		return _workflowMetricsSLAConditionLocalService.
			updateWorkflowMetricsSLACondition(workflowMetricsSLACondition);
	}

	@Override
	public WorkflowMetricsSLAConditionLocalService getWrappedService() {
		return _workflowMetricsSLAConditionLocalService;
	}

	@Override
	public void setWrappedService(
		WorkflowMetricsSLAConditionLocalService
			workflowMetricsSLAConditionLocalService) {

		_workflowMetricsSLAConditionLocalService =
			workflowMetricsSLAConditionLocalService;
	}

	private WorkflowMetricsSLAConditionLocalService
		_workflowMetricsSLAConditionLocalService;

}