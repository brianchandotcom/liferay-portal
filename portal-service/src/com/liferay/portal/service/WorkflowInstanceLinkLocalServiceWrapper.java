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
 * Provides a wrapper for {@link WorkflowInstanceLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLinkLocalService
 * @generated
 */
@ProviderType
public class WorkflowInstanceLinkLocalServiceWrapper
	implements WorkflowInstanceLinkLocalService,
		ServiceWrapper<WorkflowInstanceLinkLocalService> {
	public WorkflowInstanceLinkLocalServiceWrapper(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	@Override
	public com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long workflowInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.addWorkflowInstanceLink(userId,
			companyId, groupId, className, classPK, workflowInstanceId);
	}

	/**
	* Adds the workflow instance link to the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @return the workflow instance link that was added
	*/
	@Override
	public com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink) {
		return _workflowInstanceLinkLocalService.addWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	* Creates a new workflow instance link with the primary key. Does not add the workflow instance link to the database.
	*
	* @param workflowInstanceLinkId the primary key for the new workflow instance link
	* @return the new workflow instance link
	*/
	@Override
	public com.liferay.portal.model.WorkflowInstanceLink createWorkflowInstanceLink(
		long workflowInstanceLinkId) {
		return _workflowInstanceLinkLocalService.createWorkflowInstanceLink(workflowInstanceLinkId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.WorkflowInstanceLink deleteWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	/**
	* Deletes the workflow instance link from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @return the workflow instance link that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.WorkflowInstanceLink deleteWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	* Deletes the workflow instance link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link that was removed
	* @throws PortalException if a workflow instance link with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.WorkflowInstanceLink deleteWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(workflowInstanceLinkId);
	}

	@Override
	public void deleteWorkflowInstanceLinks(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(companyId,
			groupId, className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _workflowInstanceLinkLocalService.dynamicQuery();
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
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _workflowInstanceLinkLocalService.dynamicQueryCount(dynamicQuery);
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
		return _workflowInstanceLinkLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.WorkflowInstanceLink fetchWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK) {
		return _workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	@Override
	public com.liferay.portal.model.WorkflowInstanceLink fetchWorkflowInstanceLink(
		long workflowInstanceLinkId) {
		return _workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(workflowInstanceLinkId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _workflowInstanceLinkLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _workflowInstanceLinkLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _workflowInstanceLinkLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.lang.String getState(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.getState(companyId, groupId,
			className, classPK);
	}

	@Override
	public com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	/**
	* Returns the workflow instance link with the primary key.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link
	* @throws PortalException if a workflow instance link with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLink(workflowInstanceLinkId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		long companyId, long groupId, java.lang.String className, long classPK) {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinks(companyId,
			groupId, className, classPK);
	}

	/**
	* Returns a range of all the workflow instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @return the range of workflow instance links
	*/
	@Override
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		int start, int end) {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinks(start,
			end);
	}

	/**
	* Returns the number of workflow instance links.
	*
	* @return the number of workflow instance links
	*/
	@Override
	public int getWorkflowInstanceLinksCount() {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinksCount();
	}

	@Override
	public boolean hasWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK) {
		return _workflowInstanceLinkLocalService.hasWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	@Override
	public boolean isEnded(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _workflowInstanceLinkLocalService.isEnded(companyId, groupId,
			className, classPK);
	}

	@Override
	public void startWorkflowInstance(long companyId, long groupId,
		long userId, java.lang.String className, long classPK,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_workflowInstanceLinkLocalService.startWorkflowInstance(companyId,
			groupId, userId, className, classPK, workflowContext);
	}

	@Override
	public void updateClassPK(long companyId, long groupId,
		java.lang.String className, long oldClassPK, long newClassPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_workflowInstanceLinkLocalService.updateClassPK(companyId, groupId,
			className, oldClassPK, newClassPK);
	}

	/**
	* Updates the workflow instance link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @return the workflow instance link that was updated
	*/
	@Override
	public com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink) {
		return _workflowInstanceLinkLocalService.updateWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public WorkflowInstanceLinkLocalService getWrappedWorkflowInstanceLinkLocalService() {
		return _workflowInstanceLinkLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedWorkflowInstanceLinkLocalService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	@Override
	public WorkflowInstanceLinkLocalService getWrappedService() {
		return _workflowInstanceLinkLocalService;
	}

	@Override
	public void setWrappedService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;
}