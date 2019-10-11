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

package com.liferay.portal.workflow.kaleo.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for KaleoTaskAssignment. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoTaskAssignmentLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignmentLocalService
 * @generated
 */
public class KaleoTaskAssignmentLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoTaskAssignmentLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kaleo task assignment to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignment the kaleo task assignment
	 * @return the kaleo task assignment that was added
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
		addKaleoTaskAssignment(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
				kaleoTaskAssignment) {

		return getService().addKaleoTaskAssignment(kaleoTaskAssignment);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
			addKaleoTaskAssignment(
				String kaleoClassName, long kaleoClassPK,
				long kaleoDefinitionVersionId,
				com.liferay.portal.workflow.kaleo.definition.Assignment
					assignment,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKaleoTaskAssignment(
			kaleoClassName, kaleoClassPK, kaleoDefinitionVersionId, assignment,
			serviceContext);
	}

	/**
	 * Creates a new kaleo task assignment with the primary key. Does not add the kaleo task assignment to the database.
	 *
	 * @param kaleoTaskAssignmentId the primary key for the new kaleo task assignment
	 * @return the new kaleo task assignment
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
		createKaleoTaskAssignment(long kaleoTaskAssignmentId) {

		return getService().createKaleoTaskAssignment(kaleoTaskAssignmentId);
	}

	public static void deleteCompanyKaleoTaskAssignments(long companyId) {
		getService().deleteCompanyKaleoTaskAssignments(companyId);
	}

	public static void deleteKaleoDefinitionVersionKaleoTaskAssignments(
		long kaleoDefinitionVersionId) {

		getService().deleteKaleoDefinitionVersionKaleoTaskAssignments(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo task assignment from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignment the kaleo task assignment
	 * @return the kaleo task assignment that was removed
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
		deleteKaleoTaskAssignment(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
				kaleoTaskAssignment) {

		return getService().deleteKaleoTaskAssignment(kaleoTaskAssignment);
	}

	/**
	 * Deletes the kaleo task assignment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the kaleo task assignment
	 * @return the kaleo task assignment that was removed
	 * @throws PortalException if a kaleo task assignment with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
			deleteKaleoTaskAssignment(long kaleoTaskAssignmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoTaskAssignment(kaleoTaskAssignmentId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentModelImpl</code>.
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

	public static com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
		fetchKaleoTaskAssignment(long kaleoTaskAssignmentId) {

		return getService().fetchKaleoTaskAssignment(kaleoTaskAssignmentId);
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
	 * Returns the kaleo task assignment with the primary key.
	 *
	 * @param kaleoTaskAssignmentId the primary key of the kaleo task assignment
	 * @return the kaleo task assignment
	 * @throws PortalException if a kaleo task assignment with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
			getKaleoTaskAssignment(long kaleoTaskAssignmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoTaskAssignment(kaleoTaskAssignmentId);
	}

	/**
	 * Returns a range of all the kaleo task assignments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignments
	 * @param end the upper bound of the range of kaleo task assignments (not inclusive)
	 * @return the range of kaleo task assignments
	 */
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment>
			getKaleoTaskAssignments(int start, int end) {

		return getService().getKaleoTaskAssignments(start, end);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment>
			getKaleoTaskAssignments(long kaleoTaskId) {

		return getService().getKaleoTaskAssignments(kaleoTaskId);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment>
			getKaleoTaskAssignments(
				long kaleoTaskId, String assigneeClassName) {

		return getService().getKaleoTaskAssignments(
			kaleoTaskId, assigneeClassName);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment>
			getKaleoTaskAssignments(String kaleoClassName, long kaleoClassPK) {

		return getService().getKaleoTaskAssignments(
			kaleoClassName, kaleoClassPK);
	}

	/**
	 * Returns the number of kaleo task assignments.
	 *
	 * @return the number of kaleo task assignments
	 */
	public static int getKaleoTaskAssignmentsCount() {
		return getService().getKaleoTaskAssignmentsCount();
	}

	public static int getKaleoTaskAssignmentsCount(long kaleoTaskId) {
		return getService().getKaleoTaskAssignmentsCount(kaleoTaskId);
	}

	public static int getKaleoTaskAssignmentsCount(
		long kaleoTaskId, String assigneeClassName) {

		return getService().getKaleoTaskAssignmentsCount(
			kaleoTaskId, assigneeClassName);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo task assignment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignment the kaleo task assignment
	 * @return the kaleo task assignment that was updated
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
		updateKaleoTaskAssignment(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment
				kaleoTaskAssignment) {

		return getService().updateKaleoTaskAssignment(kaleoTaskAssignment);
	}

	public static KaleoTaskAssignmentLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoTaskAssignmentLocalService, KaleoTaskAssignmentLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoTaskAssignmentLocalService.class);

		ServiceTracker
			<KaleoTaskAssignmentLocalService, KaleoTaskAssignmentLocalService>
				serviceTracker =
					new ServiceTracker
						<KaleoTaskAssignmentLocalService,
						 KaleoTaskAssignmentLocalService>(
							 bundle.getBundleContext(),
							 KaleoTaskAssignmentLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}