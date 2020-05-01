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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WorkflowMetricsSLADefinitionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionLocalService
 * @generated
 */
public class WorkflowMetricsSLADefinitionLocalServiceWrapper
	implements ServiceWrapper<WorkflowMetricsSLADefinitionLocalService>,
			   WorkflowMetricsSLADefinitionLocalService {

	public WorkflowMetricsSLADefinitionLocalServiceWrapper(
		WorkflowMetricsSLADefinitionLocalService
			workflowMetricsSLADefinitionLocalService) {

		_workflowMetricsSLADefinitionLocalService =
			workflowMetricsSLADefinitionLocalService;
	}

	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
				addWorkflowMetricsSLADefinition(
					String calendarKey, String description, long duration,
					String name, String[] pauseNodeKeys, long processId,
					String[] startNodeKeys, String[] stopNodeKeys,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				calendarKey, description, duration, name, pauseNodeKeys,
				processId, startNodeKeys, stopNodeKeys, serviceContext);
	}

	/**
	 * Adds the workflow metrics sla definition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was added
	 */
	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
			addWorkflowMetricsSLADefinition(
				com.liferay.portal.workflow.metrics.model.
					WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return _workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(workflowMetricsSLADefinition);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new workflow metrics sla definition with the primary key. Does not add the workflow metrics sla definition to the database.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key for the new workflow metrics sla definition
	 * @return the new workflow metrics sla definition
	 */
	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
			createWorkflowMetricsSLADefinition(
				long workflowMetricsSLADefinitionId) {

		return _workflowMetricsSLADefinitionLocalService.
			createWorkflowMetricsSLADefinition(workflowMetricsSLADefinitionId);
	}

	@Override
	public void deactivateWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_workflowMetricsSLADefinitionLocalService.
			deactivateWorkflowMetricsSLADefinition(
				workflowMetricsSLADefinitionId, serviceContext);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the workflow metrics sla definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was removed
	 * @throws PortalException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
				deleteWorkflowMetricsSLADefinition(
					long workflowMetricsSLADefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.
			deleteWorkflowMetricsSLADefinition(workflowMetricsSLADefinitionId);
	}

	/**
	 * Deletes the workflow metrics sla definition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was removed
	 */
	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
			deleteWorkflowMetricsSLADefinition(
				com.liferay.portal.workflow.metrics.model.
					WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return _workflowMetricsSLADefinitionLocalService.
			deleteWorkflowMetricsSLADefinition(workflowMetricsSLADefinition);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _workflowMetricsSLADefinitionLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _workflowMetricsSLADefinitionLocalService.dynamicQuery();
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

		return _workflowMetricsSLADefinitionLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionModelImpl</code>.
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

		return _workflowMetricsSLADefinitionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionModelImpl</code>.
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

		return _workflowMetricsSLADefinitionLocalService.dynamicQuery(
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

		return _workflowMetricsSLADefinitionLocalService.dynamicQueryCount(
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

		return _workflowMetricsSLADefinitionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
			fetchWorkflowMetricsSLADefinition(
				long workflowMetricsSLADefinitionId) {

		return _workflowMetricsSLADefinitionLocalService.
			fetchWorkflowMetricsSLADefinition(workflowMetricsSLADefinitionId);
	}

	/**
	 * Returns the workflow metrics sla definition matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla definition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
			fetchWorkflowMetricsSLADefinitionByUuidAndGroupId(
				String uuid, long groupId) {

		return _workflowMetricsSLADefinitionLocalService.
			fetchWorkflowMetricsSLADefinitionByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _workflowMetricsSLADefinitionLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _workflowMetricsSLADefinitionLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _workflowMetricsSLADefinitionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _workflowMetricsSLADefinitionLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the workflow metrics sla definition with the primary key.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition
	 * @throws PortalException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
				getWorkflowMetricsSLADefinition(
					long workflowMetricsSLADefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinition(workflowMetricsSLADefinitionId);
	}

	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
				getWorkflowMetricsSLADefinition(
					long workflowMetricsSLADefinitionId, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinition(
				workflowMetricsSLADefinitionId, active);
	}

	/**
	 * Returns the workflow metrics sla definition matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla definition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla definition
	 * @throws PortalException if a matching workflow metrics sla definition could not be found
	 */
	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
				getWorkflowMetricsSLADefinitionByUuidAndGroupId(
					String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitionByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the workflow metrics sla definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @return the range of workflow metrics sla definitions
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition>
			getWorkflowMetricsSLADefinitions(int start, int end) {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitions(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition>
			getWorkflowMetricsSLADefinitions(
				long companyId, boolean active, long processId, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.metrics.model.
						WorkflowMetricsSLADefinition> obc) {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitions(
				companyId, active, processId, status, start, end, obc);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition>
			getWorkflowMetricsSLADefinitions(
				long companyId, boolean active, long processId,
				String processVersion, int status) {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitions(
				companyId, active, processId, processVersion, status);
	}

	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition>
			getWorkflowMetricsSLADefinitions(long companyId, int status) {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitions(companyId, status);
	}

	/**
	 * Returns all the workflow metrics sla definitions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla definitions
	 * @param companyId the primary key of the company
	 * @return the matching workflow metrics sla definitions, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition>
			getWorkflowMetricsSLADefinitionsByUuidAndCompanyId(
				String uuid, long companyId) {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitionsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of workflow metrics sla definitions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla definitions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of workflow metrics sla definitions
	 * @param end the upper bound of the range of workflow metrics sla definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching workflow metrics sla definitions, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition>
			getWorkflowMetricsSLADefinitionsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.metrics.model.
						WorkflowMetricsSLADefinition> orderByComparator) {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitionsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of workflow metrics sla definitions.
	 *
	 * @return the number of workflow metrics sla definitions
	 */
	@Override
	public int getWorkflowMetricsSLADefinitionsCount() {
		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitionsCount();
	}

	@Override
	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, boolean active, long processId) {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitionsCount(companyId, active, processId);
	}

	@Override
	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, boolean active, long processId, int status) {

		return _workflowMetricsSLADefinitionLocalService.
			getWorkflowMetricsSLADefinitionsCount(
				companyId, active, processId, status);
	}

	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
				updateWorkflowMetricsSLADefinition(
					long workflowMetricsSLADefinitionId, String calendarKey,
					String description, long duration, String name,
					String[] pauseNodeKeys, String[] startNodeKeys,
					String[] stopNodeKeys, int status,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _workflowMetricsSLADefinitionLocalService.
			updateWorkflowMetricsSLADefinition(
				workflowMetricsSLADefinitionId, calendarKey, description,
				duration, name, pauseNodeKeys, startNodeKeys, stopNodeKeys,
				status, serviceContext);
	}

	/**
	 * Updates the workflow metrics sla definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was updated
	 */
	@Override
	public
		com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition
			updateWorkflowMetricsSLADefinition(
				com.liferay.portal.workflow.metrics.model.
					WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return _workflowMetricsSLADefinitionLocalService.
			updateWorkflowMetricsSLADefinition(workflowMetricsSLADefinition);
	}

	@Override
	public WorkflowMetricsSLADefinitionLocalService getWrappedService() {
		return _workflowMetricsSLADefinitionLocalService;
	}

	@Override
	public void setWrappedService(
		WorkflowMetricsSLADefinitionLocalService
			workflowMetricsSLADefinitionLocalService) {

		_workflowMetricsSLADefinitionLocalService =
			workflowMetricsSLADefinitionLocalService;
	}

	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}