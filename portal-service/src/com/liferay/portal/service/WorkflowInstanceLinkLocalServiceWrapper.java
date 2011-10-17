/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link WorkflowInstanceLinkLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLinkLocalService
 * @generated
 */
public class WorkflowInstanceLinkLocalServiceWrapper
	implements WorkflowInstanceLinkLocalService,
		ServiceWrapper<WorkflowInstanceLinkLocalService> {
	public WorkflowInstanceLinkLocalServiceWrapper(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	/**
	* Adds the workflow instance link to the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @return the workflow instance link that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.addWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	* Creates a new workflow instance link with the primary key. Does not add the workflow instance link to the database.
	*
	* @param workflowInstanceLinkId the primary key for the new workflow instance link
	* @return the new workflow instance link
	*/
	public com.liferay.portal.model.WorkflowInstanceLink createWorkflowInstanceLink(
		long workflowInstanceLinkId) {
		return _workflowInstanceLinkLocalService.createWorkflowInstanceLink(workflowInstanceLinkId);
	}

	/**
	* Deletes the workflow instance link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @throws PortalException if a workflow instance link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteWorkflowInstanceLink(long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(workflowInstanceLinkId);
	}

	/**
	* Deletes the workflow instance link from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @throws SystemException if a system exception occurred
	*/
	public void deleteWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the workflow instance link with the primary key.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link
	* @throws PortalException if a workflow instance link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the workflow instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @return the range of workflow instance links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinks(start,
			end);
	}

	/**
	* Returns the number of workflow instance links.
	*
	* @return the number of workflow instance links
	* @throws SystemException if a system exception occurred
	*/
	public int getWorkflowInstanceLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinksCount();
	}

	/**
	* Updates the workflow instance link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @return the workflow instance link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.updateWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	* Updates the workflow instance link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @param merge whether to merge the workflow instance link with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the workflow instance link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.updateWorkflowInstanceLink(workflowInstanceLink,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _workflowInstanceLinkLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_workflowInstanceLinkLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long workflowInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.addWorkflowInstanceLink(userId,
			companyId, groupId, className, classPK, workflowInstanceId);
	}

	public void deleteWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public void deleteWorkflowInstanceLinks(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(companyId,
			groupId, className, classPK);
	}

	public com.liferay.portal.model.WorkflowInstanceLink fetchWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public java.lang.String getState(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getState(companyId, groupId,
			className, classPK);
	}

	public com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.getWorkflowInstanceLinks(companyId,
			groupId, className, classPK);
	}

	public boolean hasWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.hasWorkflowInstanceLink(companyId,
			groupId, className, classPK);
	}

	public boolean isEnded(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLinkLocalService.isEnded(companyId, groupId,
			className, classPK);
	}

	public void startWorkflowInstance(long companyId, long groupId,
		long userId, java.lang.String className, long classPK,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.startWorkflowInstance(companyId,
			groupId, userId, className, classPK, workflowContext);
	}

	public void updateClassPK(long companyId, long groupId,
		java.lang.String className, long oldClassPK, long newClassPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_workflowInstanceLinkLocalService.updateClassPK(companyId, groupId,
			className, oldClassPK, newClassPK);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public WorkflowInstanceLinkLocalService getWrappedWorkflowInstanceLinkLocalService() {
		return _workflowInstanceLinkLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedWorkflowInstanceLinkLocalService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	public WorkflowInstanceLinkLocalService getWrappedService() {
		return _workflowInstanceLinkLocalService;
	}

	public void setWrappedService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;
}