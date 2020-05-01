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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for WorkflowMetricsSLADefinitionVersion. This utility wraps
 * <code>com.liferay.portal.workflow.metrics.service.impl.WorkflowMetricsSLADefinitionVersionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionVersionLocalService
 * @generated
 */
public class WorkflowMetricsSLADefinitionVersionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.metrics.service.impl.WorkflowMetricsSLADefinitionVersionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the workflow metrics sla definition version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionVersion the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version that was added
	 */
	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
			addWorkflowMetricsSLADefinitionVersion(
				com.liferay.portal.workflow.metrics.model.
					WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion) {

		return getService().addWorkflowMetricsSLADefinitionVersion(
			workflowMetricsSLADefinitionVersion);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new workflow metrics sla definition version with the primary key. Does not add the workflow metrics sla definition version to the database.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key for the new workflow metrics sla definition version
	 * @return the new workflow metrics sla definition version
	 */
	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
			createWorkflowMetricsSLADefinitionVersion(
				long workflowMetricsSLADefinitionVersionId) {

		return getService().createWorkflowMetricsSLADefinitionVersion(
			workflowMetricsSLADefinitionVersionId);
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
	 * Deletes the workflow metrics sla definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version that was removed
	 * @throws PortalException if a workflow metrics sla definition version with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
				deleteWorkflowMetricsSLADefinitionVersion(
					long workflowMetricsSLADefinitionVersionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteWorkflowMetricsSLADefinitionVersion(
			workflowMetricsSLADefinitionVersionId);
	}

	/**
	 * Deletes the workflow metrics sla definition version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionVersion the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version that was removed
	 */
	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
			deleteWorkflowMetricsSLADefinitionVersion(
				com.liferay.portal.workflow.metrics.model.
					WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion) {

		return getService().deleteWorkflowMetricsSLADefinitionVersion(
			workflowMetricsSLADefinitionVersion);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionModelImpl</code>.
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

	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
			fetchWorkflowMetricsSLADefinitionVersion(
				long workflowMetricsSLADefinitionVersionId) {

		return getService().fetchWorkflowMetricsSLADefinitionVersion(
			workflowMetricsSLADefinitionVersionId);
	}

	/**
	 * Returns the workflow metrics sla definition version matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla definition version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla definition version, or <code>null</code> if a matching workflow metrics sla definition version could not be found
	 */
	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
			fetchWorkflowMetricsSLADefinitionVersionByUuidAndGroupId(
				String uuid, long groupId) {

		return getService().
			fetchWorkflowMetricsSLADefinitionVersionByUuidAndGroupId(
				uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the workflow metrics sla definition version with the primary key.
	 *
	 * @param workflowMetricsSLADefinitionVersionId the primary key of the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version
	 * @throws PortalException if a workflow metrics sla definition version with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
				getWorkflowMetricsSLADefinitionVersion(
					long workflowMetricsSLADefinitionVersionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWorkflowMetricsSLADefinitionVersion(
			workflowMetricsSLADefinitionVersionId);
	}

	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
				getWorkflowMetricsSLADefinitionVersion(
					long workflowMetricsSLADefinitionId, String version)
			throws com.liferay.portal.workflow.metrics.exception.
				NoSuchSLADefinitionVersionException {

		return getService().getWorkflowMetricsSLADefinitionVersion(
			workflowMetricsSLADefinitionId, version);
	}

	/**
	 * Returns the workflow metrics sla definition version matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla definition version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla definition version
	 * @throws PortalException if a matching workflow metrics sla definition version could not be found
	 */
	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
				getWorkflowMetricsSLADefinitionVersionByUuidAndGroupId(
					String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().
			getWorkflowMetricsSLADefinitionVersionByUuidAndGroupId(
				uuid, groupId);
	}

	/**
	 * Returns a range of all the workflow metrics sla definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLADefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @return the range of workflow metrics sla definition versions
	 */
	public static java.util.List
		<com.liferay.portal.workflow.metrics.model.
			WorkflowMetricsSLADefinitionVersion>
				getWorkflowMetricsSLADefinitionVersions(int start, int end) {

		return getService().getWorkflowMetricsSLADefinitionVersions(start, end);
	}

	public static java.util.List
		<com.liferay.portal.workflow.metrics.model.
			WorkflowMetricsSLADefinitionVersion>
				getWorkflowMetricsSLADefinitionVersions(
					long workflowMetricsSLADefinitionId) {

		return getService().getWorkflowMetricsSLADefinitionVersions(
			workflowMetricsSLADefinitionId);
	}

	public static java.util.List
		<com.liferay.portal.workflow.metrics.model.
			WorkflowMetricsSLADefinitionVersion>
				getWorkflowMetricsSLADefinitionVersions(
					long companyId, java.util.Date createDate, Long processId,
					int status) {

		return getService().getWorkflowMetricsSLADefinitionVersions(
			companyId, createDate, processId, status);
	}

	public static java.util.List
		<com.liferay.portal.workflow.metrics.model.
			WorkflowMetricsSLADefinitionVersion>
				getWorkflowMetricsSLADefinitionVersions(
					long workflowMetricsSLADefinitionId,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.workflow.metrics.model.
							WorkflowMetricsSLADefinitionVersion>
								orderByComparator) {

		return getService().getWorkflowMetricsSLADefinitionVersions(
			workflowMetricsSLADefinitionId, orderByComparator);
	}

	/**
	 * Returns all the workflow metrics sla definition versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla definition versions
	 * @param companyId the primary key of the company
	 * @return the matching workflow metrics sla definition versions, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.portal.workflow.metrics.model.
			WorkflowMetricsSLADefinitionVersion>
				getWorkflowMetricsSLADefinitionVersionsByUuidAndCompanyId(
					String uuid, long companyId) {

		return getService().
			getWorkflowMetricsSLADefinitionVersionsByUuidAndCompanyId(
				uuid, companyId);
	}

	/**
	 * Returns a range of workflow metrics sla definition versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla definition versions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of workflow metrics sla definition versions
	 * @param end the upper bound of the range of workflow metrics sla definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching workflow metrics sla definition versions, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.portal.workflow.metrics.model.
			WorkflowMetricsSLADefinitionVersion>
				getWorkflowMetricsSLADefinitionVersionsByUuidAndCompanyId(
					String uuid, long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.workflow.metrics.model.
							WorkflowMetricsSLADefinitionVersion>
								orderByComparator) {

		return getService().
			getWorkflowMetricsSLADefinitionVersionsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of workflow metrics sla definition versions.
	 *
	 * @return the number of workflow metrics sla definition versions
	 */
	public static int getWorkflowMetricsSLADefinitionVersionsCount() {
		return getService().getWorkflowMetricsSLADefinitionVersionsCount();
	}

	/**
	 * Updates the workflow metrics sla definition version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionVersion the workflow metrics sla definition version
	 * @return the workflow metrics sla definition version that was updated
	 */
	public static com.liferay.portal.workflow.metrics.model.
		WorkflowMetricsSLADefinitionVersion
			updateWorkflowMetricsSLADefinitionVersion(
				com.liferay.portal.workflow.metrics.model.
					WorkflowMetricsSLADefinitionVersion
						workflowMetricsSLADefinitionVersion) {

		return getService().updateWorkflowMetricsSLADefinitionVersion(
			workflowMetricsSLADefinitionVersion);
	}

	public static WorkflowMetricsSLADefinitionVersionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<WorkflowMetricsSLADefinitionVersionLocalService,
		 WorkflowMetricsSLADefinitionVersionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowMetricsSLADefinitionVersionLocalService.class);

		ServiceTracker
			<WorkflowMetricsSLADefinitionVersionLocalService,
			 WorkflowMetricsSLADefinitionVersionLocalService> serviceTracker =
				new ServiceTracker
					<WorkflowMetricsSLADefinitionVersionLocalService,
					 WorkflowMetricsSLADefinitionVersionLocalService>(
						 bundle.getBundleContext(),
						 WorkflowMetricsSLADefinitionVersionLocalService.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}