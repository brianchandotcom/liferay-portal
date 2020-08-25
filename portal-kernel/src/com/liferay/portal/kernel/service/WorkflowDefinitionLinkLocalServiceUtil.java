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
 * Provides the local service utility for WorkflowDefinitionLink. This utility wraps
 * <code>com.liferay.portal.service.impl.WorkflowDefinitionLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowDefinitionLinkLocalService
 * @generated
 */
public class WorkflowDefinitionLinkLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.WorkflowDefinitionLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
			addWorkflowDefinitionLink(
				long userId, long companyId, long groupId, String className,
				long classPK, long typePK, String workflowDefinitionName,
				int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addWorkflowDefinitionLink(
			userId, companyId, groupId, className, classPK, typePK,
			workflowDefinitionName, workflowDefinitionVersion);
	}

	/**
	 * Adds the workflow definition link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WorkflowDefinitionLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param workflowDefinitionLink the workflow definition link
	 * @return the workflow definition link that was added
	 */
	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
		addWorkflowDefinitionLink(
			com.liferay.portal.kernel.model.WorkflowDefinitionLink
				workflowDefinitionLink) {

		return getService().addWorkflowDefinitionLink(workflowDefinitionLink);
	}

	/**
	 * Creates a new workflow definition link with the primary key. Does not add the workflow definition link to the database.
	 *
	 * @param workflowDefinitionLinkId the primary key for the new workflow definition link
	 * @return the new workflow definition link
	 */
	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
		createWorkflowDefinitionLink(long workflowDefinitionLinkId) {

		return getService().createWorkflowDefinitionLink(
			workflowDefinitionLinkId);
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
	 * Deletes the workflow definition link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WorkflowDefinitionLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param workflowDefinitionLinkId the primary key of the workflow definition link
	 * @return the workflow definition link that was removed
	 * @throws PortalException if a workflow definition link with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
			deleteWorkflowDefinitionLink(long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteWorkflowDefinitionLink(
			workflowDefinitionLinkId);
	}

	public static void deleteWorkflowDefinitionLink(
		long companyId, long groupId, String className, long classPK,
		long typePK) {

		getService().deleteWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK);
	}

	/**
	 * Deletes the workflow definition link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WorkflowDefinitionLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param workflowDefinitionLink the workflow definition link
	 * @return the workflow definition link that was removed
	 */
	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
		deleteWorkflowDefinitionLink(
			com.liferay.portal.kernel.model.WorkflowDefinitionLink
				workflowDefinitionLink) {

		return getService().deleteWorkflowDefinitionLink(
			workflowDefinitionLink);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WorkflowDefinitionLinkModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WorkflowDefinitionLinkModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
		fetchDefaultWorkflowDefinitionLink(
			long companyId, String className, long classPK, long typePK) {

		return getService().fetchDefaultWorkflowDefinitionLink(
			companyId, className, classPK, typePK);
	}

	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
		fetchWorkflowDefinitionLink(long workflowDefinitionLinkId) {

		return getService().fetchWorkflowDefinitionLink(
			workflowDefinitionLinkId);
	}

	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
		fetchWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK) {

		return getService().fetchWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK);
	}

	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
		fetchWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK, boolean strict) {

		return getService().fetchWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK, strict);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.WorkflowDefinitionLink>
			fetchWorkflowDefinitionLinks(
				long companyId, long groupId, String className, long classPK) {

		return getService().fetchWorkflowDefinitionLinks(
			companyId, groupId, className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
			getDefaultWorkflowDefinitionLink(
				long companyId, String className, long classPK, long typePK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDefaultWorkflowDefinitionLink(
			companyId, className, classPK, typePK);
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
	 * Returns the workflow definition link with the primary key.
	 *
	 * @param workflowDefinitionLinkId the primary key of the workflow definition link
	 * @return the workflow definition link
	 * @throws PortalException if a workflow definition link with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
			getWorkflowDefinitionLink(long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
			getWorkflowDefinitionLink(
				long companyId, long groupId, String className, long classPK,
				long typePK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK);
	}

	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
			getWorkflowDefinitionLink(
				long companyId, long groupId, String className, long classPK,
				long typePK, boolean strict)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK, strict);
	}

	/**
	 * Returns a range of all the workflow definition links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.WorkflowDefinitionLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow definition links
	 * @param end the upper bound of the range of workflow definition links (not inclusive)
	 * @return the range of workflow definition links
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.WorkflowDefinitionLink>
			getWorkflowDefinitionLinks(int start, int end) {

		return getService().getWorkflowDefinitionLinks(start, end);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.WorkflowDefinitionLink>
				getWorkflowDefinitionLinks(
					long companyId, long groupId, String className,
					long classPK)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWorkflowDefinitionLinks(
			companyId, groupId, className, classPK);
	}

	public static java.util.List
		<com.liferay.portal.kernel.model.WorkflowDefinitionLink>
				getWorkflowDefinitionLinks(
					long companyId, String workflowDefinitionName,
					int workflowDefinitionVersion)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getWorkflowDefinitionLinks(
			companyId, workflowDefinitionName, workflowDefinitionVersion);
	}

	/**
	 * Returns the number of workflow definition links.
	 *
	 * @return the number of workflow definition links
	 */
	public static int getWorkflowDefinitionLinksCount() {
		return getService().getWorkflowDefinitionLinksCount();
	}

	public static int getWorkflowDefinitionLinksCount(
		long companyId, long groupId, String className) {

		return getService().getWorkflowDefinitionLinksCount(
			companyId, groupId, className);
	}

	public static int getWorkflowDefinitionLinksCount(
		long companyId, String workflowDefinitionName,
		int workflowDefinitionVersion) {

		return getService().getWorkflowDefinitionLinksCount(
			companyId, workflowDefinitionName, workflowDefinitionVersion);
	}

	public static boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, String className) {

		return getService().hasWorkflowDefinitionLink(
			companyId, groupId, className);
	}

	public static boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, String className, long classPK) {

		return getService().hasWorkflowDefinitionLink(
			companyId, groupId, className, classPK);
	}

	public static boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, String className, long classPK,
		long typePK) {

		return getService().hasWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK);
	}

	public static void updateWorkflowDefinitionLink(
			long userId, long companyId, long groupId, String className,
			long classPK, long typePK, String workflowDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateWorkflowDefinitionLink(
			userId, companyId, groupId, className, classPK, typePK,
			workflowDefinition);
	}

	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
			updateWorkflowDefinitionLink(
				long userId, long companyId, long groupId, String className,
				long classPK, long typePK, String workflowDefinitionName,
				int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateWorkflowDefinitionLink(
			userId, companyId, groupId, className, classPK, typePK,
			workflowDefinitionName, workflowDefinitionVersion);
	}

	/**
	 * Updates the workflow definition link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WorkflowDefinitionLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param workflowDefinitionLink the workflow definition link
	 * @return the workflow definition link that was updated
	 */
	public static com.liferay.portal.kernel.model.WorkflowDefinitionLink
		updateWorkflowDefinitionLink(
			com.liferay.portal.kernel.model.WorkflowDefinitionLink
				workflowDefinitionLink) {

		return getService().updateWorkflowDefinitionLink(
			workflowDefinitionLink);
	}

	public static void updateWorkflowDefinitionLinks(
			long userId, long companyId, long groupId, String className,
			long classPK,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair<Long, String>>
					workflowDefinitionOVPs)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateWorkflowDefinitionLinks(
			userId, companyId, groupId, className, classPK,
			workflowDefinitionOVPs);
	}

	public static WorkflowDefinitionLinkLocalService getService() {
		if (_service == null) {
			_service =
				(WorkflowDefinitionLinkLocalService)
					PortalBeanLocatorUtil.locate(
						WorkflowDefinitionLinkLocalService.class.getName());
		}

		return _service;
	}

	private static WorkflowDefinitionLinkLocalService _service;

}