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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for WorkflowInstanceLink. This utility wraps
 * <code>com.liferay.portal.service.impl.WorkflowInstanceLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLinkLocalService
 * @generated
 */
public class WorkflowInstanceLinkLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.WorkflowInstanceLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
			addWorkflowInstanceLink(
				long userId, long companyId, long groupId, String className,
				long classPK, long workflowInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addWorkflowInstanceLink(
			userId, companyId, groupId, className, classPK, workflowInstanceId);
	}

	/**
	 * Adds the workflow instance link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WorkflowInstanceLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param workflowInstanceLink the workflow instance link
	 * @return the workflow instance link that was added
	 */
	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
		addWorkflowInstanceLink(
			com.liferay.portal.kernel.model.WorkflowInstanceLink
				workflowInstanceLink) {

		return getService().addWorkflowInstanceLink(workflowInstanceLink);
	}

	/**
	 * Creates a new workflow instance link with the primary key. Does not add the workflow instance link to the database.
	 *
	 * @param workflowInstanceLinkId the primary key for the new workflow instance link
	 * @return the new workflow instance link
	 */
	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
		createWorkflowInstanceLink(long workflowInstanceLinkId) {

		return getService().createWorkflowInstanceLink(workflowInstanceLinkId);
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
	 * Deletes the workflow instance link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WorkflowInstanceLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param workflowInstanceLinkId the primary key of the workflow instance link
	 * @return the workflow instance link that was removed
	 * @throws PortalException if a workflow instance link with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
			deleteWorkflowInstanceLink(long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
			deleteWorkflowInstanceLink(
				long companyId, long groupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteWorkflowInstanceLink(
			companyId, groupId, className, classPK);
	}

	/**
	 * Deletes the workflow instance link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WorkflowInstanceLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param workflowInstanceLink the workflow instance link
	 * @return the workflow instance link that was removed
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
			deleteWorkflowInstanceLink(
				com.liferay.portal.kernel.model.WorkflowInstanceLink
					workflowInstanceLink)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	public static void deleteWorkflowInstanceLinks(
			long companyId, long groupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteWorkflowInstanceLinks(
			companyId, groupId, className, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
		fetchWorkflowInstanceLink(long workflowInstanceLinkId) {

		return getService().fetchWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
		fetchWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK) {

		return getService().fetchWorkflowInstanceLink(
			companyId, groupId, className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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

	public static String getState(
			long companyId, long groupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getState(companyId, groupId, className, classPK);
	}

	/**
	 * Returns the workflow instance link with the primary key.
	 *
	 * @param workflowInstanceLinkId the primary key of the workflow instance link
	 * @return the workflow instance link
	 * @throws PortalException if a workflow instance link with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
			getWorkflowInstanceLink(long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
			getWorkflowInstanceLink(
				long companyId, long groupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWorkflowInstanceLink(
			companyId, groupId, className, classPK);
	}

	/**
	 * Returns a range of all the workflow instance links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow instance links
	 * @param end the upper bound of the range of workflow instance links (not inclusive)
	 * @return the range of workflow instance links
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.WorkflowInstanceLink>
			getWorkflowInstanceLinks(int start, int end) {

		return getService().getWorkflowInstanceLinks(start, end);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.WorkflowInstanceLink>
			getWorkflowInstanceLinks(
				long companyId, long groupId, String className, long classPK) {

		return getService().getWorkflowInstanceLinks(
			companyId, groupId, className, classPK);
	}

	/**
	 * Returns the number of workflow instance links.
	 *
	 * @return the number of workflow instance links
	 */
	public static int getWorkflowInstanceLinksCount() {
		return getService().getWorkflowInstanceLinksCount();
	}

	public static boolean hasWorkflowInstanceLink(
		long companyId, long groupId, String className, long classPK) {

		return getService().hasWorkflowInstanceLink(
			companyId, groupId, className, classPK);
	}

	public static boolean isEnded(
			long companyId, long groupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().isEnded(companyId, groupId, className, classPK);
	}

	public static void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().startWorkflowInstance(
			companyId, groupId, userId, className, classPK, workflowContext);
	}

	public static void updateClassPK(
			long companyId, long groupId, String className, long oldClassPK,
			long newClassPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateClassPK(
			companyId, groupId, className, oldClassPK, newClassPK);
	}

	/**
	 * Updates the workflow instance link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WorkflowInstanceLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param workflowInstanceLink the workflow instance link
	 * @return the workflow instance link that was updated
	 */
	public static com.liferay.portal.kernel.model.WorkflowInstanceLink
		updateWorkflowInstanceLink(
			com.liferay.portal.kernel.model.WorkflowInstanceLink
				workflowInstanceLink) {

		return getService().updateWorkflowInstanceLink(workflowInstanceLink);
	}

	public static WorkflowInstanceLinkLocalService getService() {
		if (_service == null) {
			_service =
				(WorkflowInstanceLinkLocalService)PortalBeanLocatorUtil.locate(
					WorkflowInstanceLinkLocalService.class.getName());
		}

		return _service;
	}

	private static WorkflowInstanceLinkLocalService _service;

}