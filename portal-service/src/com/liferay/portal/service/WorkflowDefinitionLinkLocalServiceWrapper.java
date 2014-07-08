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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link WorkflowDefinitionLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowDefinitionLinkLocalService
 * @generated
 */
@ProviderType
public class WorkflowDefinitionLinkLocalServiceWrapper
	implements WorkflowDefinitionLinkLocalService,
		ServiceWrapper<WorkflowDefinitionLinkLocalService> {
	public WorkflowDefinitionLinkLocalServiceWrapper(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {
		_workflowDefinitionLinkLocalService = workflowDefinitionLinkLocalService;
	}

	/**
	* Adds the workflow definition link to the database. Also notifies the appropriate model listeners.
	*
	* @param workflowDefinitionLink the workflow definition link
	* @return the workflow definition link that was added
	*/
	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink addWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink) {
		return _workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(workflowDefinitionLink);
	}

	/**
	* Creates a new workflow definition link with the primary key. Does not add the workflow definition link to the database.
	*
	* @param workflowDefinitionLinkId the primary key for the new workflow definition link
	* @return the new workflow definition link
	*/
	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink createWorkflowDefinitionLink(
		long workflowDefinitionLinkId) {
		return _workflowDefinitionLinkLocalService.createWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	/**
	* Deletes the workflow definition link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowDefinitionLinkId the primary key of the workflow definition link
	* @return the workflow definition link that was removed
	* @throws PortalException if a workflow definition link with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink deleteWorkflowDefinitionLink(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	/**
	* Deletes the workflow definition link from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowDefinitionLink the workflow definition link
	* @return the workflow definition link that was removed
	*/
	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink deleteWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink) {
		return _workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(workflowDefinitionLink);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _workflowDefinitionLinkLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _workflowDefinitionLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _workflowDefinitionLinkLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _workflowDefinitionLinkLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _workflowDefinitionLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _workflowDefinitionLinkLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink fetchWorkflowDefinitionLink(
		long workflowDefinitionLinkId) {
		return _workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	/**
	* Returns the workflow definition link with the primary key.
	*
	* @param workflowDefinitionLinkId the primary key of the workflow definition link
	* @return the workflow definition link
	* @throws PortalException if a workflow definition link with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink getWorkflowDefinitionLink(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _workflowDefinitionLinkLocalService.getActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the workflow definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @return the range of workflow definition links
	*/
	@Override
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> getWorkflowDefinitionLinks(
		int start, int end) {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLinks(start,
			end);
	}

	/**
	* Returns the number of workflow definition links.
	*
	* @return the number of workflow definition links
	*/
	@Override
	public int getWorkflowDefinitionLinksCount() {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLinksCount();
	}

	/**
	* Updates the workflow definition link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param workflowDefinitionLink the workflow definition link
	* @return the workflow definition link that was updated
	*/
	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink updateWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink) {
		return _workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(workflowDefinitionLink);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _workflowDefinitionLinkLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_workflowDefinitionLinkLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink addWorkflowDefinitionLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long typePK, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(userId,
			companyId, groupId, className, classPK, typePK,
			workflowDefinitionName, workflowDefinitionVersion);
	}

	@Override
	public void deleteWorkflowDefinitionLink(long companyId, long groupId,
		java.lang.String className, long classPK, long typePK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(companyId,
			groupId, className, classPK, typePK);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink fetchDefaultWorkflowDefinitionLink(
		long companyId, java.lang.String className, long classPK, long typePK) {
		return _workflowDefinitionLinkLocalService.fetchDefaultWorkflowDefinitionLink(companyId,
			className, classPK, typePK);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink fetchWorkflowDefinitionLink(
		long companyId, long groupId, java.lang.String className, long classPK,
		long typePK) throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(companyId,
			groupId, className, classPK, typePK);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink fetchWorkflowDefinitionLink(
		long companyId, long groupId, java.lang.String className, long classPK,
		long typePK, boolean strict)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(companyId,
			groupId, className, classPK, typePK, strict);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink getDefaultWorkflowDefinitionLink(
		long companyId, java.lang.String className, long classPK, long typePK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.getDefaultWorkflowDefinitionLink(companyId,
			className, classPK, typePK);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink getWorkflowDefinitionLink(
		long companyId, long groupId, java.lang.String className, long classPK,
		long typePK) throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(companyId,
			groupId, className, classPK, typePK);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink getWorkflowDefinitionLink(
		long companyId, long groupId, java.lang.String className, long classPK,
		long typePK, boolean strict)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(companyId,
			groupId, className, classPK, typePK, strict);
	}

	@Override
	public int getWorkflowDefinitionLinksCount(long companyId, long groupId,
		java.lang.String className) {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLinksCount(companyId,
			groupId, className);
	}

	@Override
	public int getWorkflowDefinitionLinksCount(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion) {
		return _workflowDefinitionLinkLocalService.getWorkflowDefinitionLinksCount(companyId,
			workflowDefinitionName, workflowDefinitionVersion);
	}

	@Override
	public boolean hasWorkflowDefinitionLink(long companyId, long groupId,
		java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(companyId,
			groupId, className);
	}

	@Override
	public boolean hasWorkflowDefinitionLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(companyId,
			groupId, className, classPK);
	}

	@Override
	public boolean hasWorkflowDefinitionLink(long companyId, long groupId,
		java.lang.String className, long classPK, long typePK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(companyId,
			groupId, className, classPK, typePK);
	}

	@Override
	public void updateWorkflowDefinitionLink(long userId, long companyId,
		long groupId, java.lang.String className, long classPK, long typePK,
		java.lang.String workflowDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(userId,
			companyId, groupId, className, classPK, typePK, workflowDefinition);
	}

	@Override
	public com.liferay.portal.model.WorkflowDefinitionLink updateWorkflowDefinitionLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long typePK, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(userId,
			companyId, groupId, className, classPK, typePK,
			workflowDefinitionName, workflowDefinitionVersion);
	}

	@Override
	public void updateWorkflowDefinitionLinks(long userId, long companyId,
		long groupId, java.lang.String className, long classPK,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.Long, java.lang.String>> workflowDefinitionOVPs)
		throws com.liferay.portal.kernel.exception.PortalException {
		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLinks(userId,
			companyId, groupId, className, classPK, workflowDefinitionOVPs);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public WorkflowDefinitionLinkLocalService getWrappedWorkflowDefinitionLinkLocalService() {
		return _workflowDefinitionLinkLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {
		_workflowDefinitionLinkLocalService = workflowDefinitionLinkLocalService;
	}

	@Override
	public WorkflowDefinitionLinkLocalService getWrappedService() {
		return _workflowDefinitionLinkLocalService;
	}

	@Override
	public void setWrappedService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {
		_workflowDefinitionLinkLocalService = workflowDefinitionLinkLocalService;
	}

	private WorkflowDefinitionLinkLocalService _workflowDefinitionLinkLocalService;
}