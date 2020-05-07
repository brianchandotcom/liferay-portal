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

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for WorkflowMetricsSLADefinition. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinitionLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface WorkflowMetricsSLADefinitionLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WorkflowMetricsSLADefinitionLocalServiceUtil} to access the workflow metrics sla definition local service. Add custom service methods to <code>com.liferay.portal.workflow.metrics.service.impl.WorkflowMetricsSLADefinitionLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public WorkflowMetricsSLADefinition addWorkflowMetricsSLADefinition(
			String calendarKey, String description, long duration, String name,
			String[] pauseNodeKeys, long processId, String[] startNodeKeys,
			String[] stopNodeKeys, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Adds the workflow metrics sla definition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public WorkflowMetricsSLADefinition addWorkflowMetricsSLADefinition(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Creates a new workflow metrics sla definition with the primary key. Does not add the workflow metrics sla definition to the database.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key for the new workflow metrics sla definition
	 * @return the new workflow metrics sla definition
	 */
	@Transactional(enabled = false)
	public WorkflowMetricsSLADefinition createWorkflowMetricsSLADefinition(
		long workflowMetricsSLADefinitionId);

	public void deactivateWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * Deletes the workflow metrics sla definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was removed
	 * @throws PortalException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public WorkflowMetricsSLADefinition deleteWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId)
		throws PortalException;

	/**
	 * Deletes the workflow metrics sla definition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public WorkflowMetricsSLADefinition deleteWorkflowMetricsSLADefinition(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WorkflowMetricsSLADefinition fetchWorkflowMetricsSLADefinition(
		long workflowMetricsSLADefinitionId);

	/**
	 * Returns the workflow metrics sla definition matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla definition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla definition, or <code>null</code> if a matching workflow metrics sla definition could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WorkflowMetricsSLADefinition
		fetchWorkflowMetricsSLADefinitionByUuidAndGroupId(
			String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Returns the workflow metrics sla definition with the primary key.
	 *
	 * @param workflowMetricsSLADefinitionId the primary key of the workflow metrics sla definition
	 * @return the workflow metrics sla definition
	 * @throws PortalException if a workflow metrics sla definition with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WorkflowMetricsSLADefinition getWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WorkflowMetricsSLADefinition getWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId, boolean active)
		throws PortalException;

	/**
	 * Returns the workflow metrics sla definition matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla definition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla definition
	 * @throws PortalException if a matching workflow metrics sla definition could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WorkflowMetricsSLADefinition
			getWorkflowMetricsSLADefinitionByUuidAndGroupId(
				String uuid, long groupId)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, boolean active, long processId, int status, int start,
		int end, OrderByComparator<WorkflowMetricsSLADefinition> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, boolean active, long processId, String processVersion,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, int status);

	/**
	 * Returns all the workflow metrics sla definitions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla definitions
	 * @param companyId the primary key of the company
	 * @return the matching workflow metrics sla definitions, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WorkflowMetricsSLADefinition>
		getWorkflowMetricsSLADefinitionsByUuidAndCompanyId(
			String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WorkflowMetricsSLADefinition>
		getWorkflowMetricsSLADefinitionsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator);

	/**
	 * Returns the number of workflow metrics sla definitions.
	 *
	 * @return the number of workflow metrics sla definitions
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWorkflowMetricsSLADefinitionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, boolean active, long processId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, boolean active, long processId, int status);

	public WorkflowMetricsSLADefinition updateWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId, String calendarKey,
			String description, long duration, String name,
			String[] pauseNodeKeys, String[] startNodeKeys,
			String[] stopNodeKeys, int status, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Updates the workflow metrics sla definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLADefinition the workflow metrics sla definition
	 * @return the workflow metrics sla definition that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public WorkflowMetricsSLADefinition updateWorkflowMetricsSLADefinition(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition);

}