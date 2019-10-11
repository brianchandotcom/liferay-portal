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
 * Provides the local service utility for KaleoTransition. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoTransitionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTransitionLocalService
 * @generated
 */
public class KaleoTransitionLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoTransitionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kaleo transition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransition the kaleo transition
	 * @return the kaleo transition that was added
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
		addKaleoTransition(
			com.liferay.portal.workflow.kaleo.model.KaleoTransition
				kaleoTransition) {

		return getService().addKaleoTransition(kaleoTransition);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
			addKaleoTransition(
				long kaleoDefinitionVersionId, long kaleoNodeId,
				com.liferay.portal.workflow.kaleo.definition.Transition
					transition,
				com.liferay.portal.workflow.kaleo.model.KaleoNode
					sourceKaleoNode,
				com.liferay.portal.workflow.kaleo.model.KaleoNode
					targetKaleoNode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKaleoTransition(
			kaleoDefinitionVersionId, kaleoNodeId, transition, sourceKaleoNode,
			targetKaleoNode, serviceContext);
	}

	/**
	 * Creates a new kaleo transition with the primary key. Does not add the kaleo transition to the database.
	 *
	 * @param kaleoTransitionId the primary key for the new kaleo transition
	 * @return the new kaleo transition
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
		createKaleoTransition(long kaleoTransitionId) {

		return getService().createKaleoTransition(kaleoTransitionId);
	}

	public static void deleteCompanyKaleoTransitions(long companyId) {
		getService().deleteCompanyKaleoTransitions(companyId);
	}

	public static void deleteKaleoDefinitionVersionKaleoTransitions(
		long kaleoDefinitionVersionId) {

		getService().deleteKaleoDefinitionVersionKaleoTransitions(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo transition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransition the kaleo transition
	 * @return the kaleo transition that was removed
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
		deleteKaleoTransition(
			com.liferay.portal.workflow.kaleo.model.KaleoTransition
				kaleoTransition) {

		return getService().deleteKaleoTransition(kaleoTransition);
	}

	/**
	 * Deletes the kaleo transition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransitionId the primary key of the kaleo transition
	 * @return the kaleo transition that was removed
	 * @throws PortalException if a kaleo transition with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
			deleteKaleoTransition(long kaleoTransitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoTransition(kaleoTransitionId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionModelImpl</code>.
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

	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
		fetchKaleoTransition(long kaleoTransitionId) {

		return getService().fetchKaleoTransition(kaleoTransitionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
			getDefaultKaleoTransition(long kaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDefaultKaleoTransition(kaleoNodeId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTransition>
			getKaleoDefinitionVersionKaleoTransitions(
				long kaleoDefinitionVersionId) {

		return getService().getKaleoDefinitionVersionKaleoTransitions(
			kaleoDefinitionVersionId);
	}

	/**
	 * Returns the kaleo transition with the primary key.
	 *
	 * @param kaleoTransitionId the primary key of the kaleo transition
	 * @return the kaleo transition
	 * @throws PortalException if a kaleo transition with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
			getKaleoTransition(long kaleoTransitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoTransition(kaleoTransitionId);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
			getKaleoTransition(long kaleoNodeId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoTransition(kaleoNodeId, name);
	}

	/**
	 * Returns a range of all the kaleo transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @return the range of kaleo transitions
	 */
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTransition>
			getKaleoTransitions(int start, int end) {

		return getService().getKaleoTransitions(start, end);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoTransition>
			getKaleoTransitions(long kaleoNodeId) {

		return getService().getKaleoTransitions(kaleoNodeId);
	}

	/**
	 * Returns the number of kaleo transitions.
	 *
	 * @return the number of kaleo transitions
	 */
	public static int getKaleoTransitionsCount() {
		return getService().getKaleoTransitionsCount();
	}

	public static int getKaleoTransitionsCount(long kaleoNodeId) {
		return getService().getKaleoTransitionsCount(kaleoNodeId);
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
	 * Updates the kaleo transition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransition the kaleo transition
	 * @return the kaleo transition that was updated
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoTransition
		updateKaleoTransition(
			com.liferay.portal.workflow.kaleo.model.KaleoTransition
				kaleoTransition) {

		return getService().updateKaleoTransition(kaleoTransition);
	}

	public static KaleoTransitionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoTransitionLocalService, KaleoTransitionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoTransitionLocalService.class);

		ServiceTracker<KaleoTransitionLocalService, KaleoTransitionLocalService>
			serviceTracker =
				new ServiceTracker
					<KaleoTransitionLocalService, KaleoTransitionLocalService>(
						bundle.getBundleContext(),
						KaleoTransitionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}