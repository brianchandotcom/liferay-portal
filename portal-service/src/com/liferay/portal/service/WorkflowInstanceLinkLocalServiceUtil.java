/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for WorkflowInstanceLink. This utility wraps
 * {@link com.liferay.portal.service.impl.WorkflowInstanceLinkLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLinkLocalService
 * @see com.liferay.portal.service.base.WorkflowInstanceLinkLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.WorkflowInstanceLinkLocalServiceImpl
 * @generated
 */
public class WorkflowInstanceLinkLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.WorkflowInstanceLinkLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the workflow instance link to the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @return the workflow instance link that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	* Creates a new workflow instance link with the primary key. Does not add the workflow instance link to the database.
	*
	* @param workflowInstanceLinkId the primary key for the new workflow instance link
	* @return the new workflow instance link
	*/
	public static com.liferay.portal.model.WorkflowInstanceLink createWorkflowInstanceLink(
		long workflowInstanceLinkId) {
		return getService().createWorkflowInstanceLink(workflowInstanceLinkId);
	}

	/**
	* Deletes the workflow instance link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link that was removed
	* @throws PortalException if a workflow instance link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.WorkflowInstanceLink deleteWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteWorkflowInstanceLink(workflowInstanceLinkId);
	}

	/**
	* Deletes the workflow instance link from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @return the workflow instance link that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.WorkflowInstanceLink deleteWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink fetchWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchWorkflowInstanceLink(workflowInstanceLinkId);
	}

	/**
	* Returns the workflow instance link with the primary key.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link
	* @throws PortalException if a workflow instance link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowInstanceLinks(start, end);
	}

	/**
	* Returns the number of workflow instance links.
	*
	* @return the number of workflow instance links
	* @throws SystemException if a system exception occurred
	*/
	public static int getWorkflowInstanceLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowInstanceLinksCount();
	}

	/**
	* Updates the workflow instance link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLink the workflow instance link
	* @return the workflow instance link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long workflowInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addWorkflowInstanceLink(userId, companyId, groupId,
			className, classPK, workflowInstanceId);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink deleteWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteWorkflowInstanceLink(companyId, groupId, className,
			classPK);
	}

	public static void deleteWorkflowInstanceLinks(long companyId,
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteWorkflowInstanceLinks(companyId, groupId, className, classPK);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink fetchWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .fetchWorkflowInstanceLink(companyId, groupId, className,
			classPK);
	}

	public static java.lang.String getState(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getState(companyId, groupId, className, classPK);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getWorkflowInstanceLink(companyId, groupId, className,
			classPK);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getWorkflowInstanceLinks(companyId, groupId, className,
			classPK);
	}

	public static boolean hasWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasWorkflowInstanceLink(companyId, groupId, className,
			classPK);
	}

	public static boolean isEnded(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isEnded(companyId, groupId, className, classPK);
	}

	public static void startWorkflowInstance(long companyId, long groupId,
		long userId, java.lang.String className, long classPK,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.startWorkflowInstance(companyId, groupId, userId, className,
			classPK, workflowContext);
	}

	public static void updateClassPK(long companyId, long groupId,
		java.lang.String className, long oldClassPK, long newClassPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateClassPK(companyId, groupId, className, oldClassPK, newClassPK);
	}

	public static WorkflowInstanceLinkLocalService getService() {
		if (_service == null) {
			_service = (WorkflowInstanceLinkLocalService)PortalBeanLocatorUtil.locate(WorkflowInstanceLinkLocalService.class.getName());

			ReferenceRegistry.registerReference(WorkflowInstanceLinkLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(WorkflowInstanceLinkLocalService service) {
	}

	private static WorkflowInstanceLinkLocalService _service;
}