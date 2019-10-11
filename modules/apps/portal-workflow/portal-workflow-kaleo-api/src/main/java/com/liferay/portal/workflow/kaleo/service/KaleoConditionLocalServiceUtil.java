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
 * Provides the local service utility for KaleoCondition. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoConditionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoConditionLocalService
 * @generated
 */
public class KaleoConditionLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoConditionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kaleo condition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoCondition the kaleo condition
	 * @return the kaleo condition that was added
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
		addKaleoCondition(
			com.liferay.portal.workflow.kaleo.model.KaleoCondition
				kaleoCondition) {

		return getService().addKaleoCondition(kaleoCondition);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
			addKaleoCondition(
				long kaleoDefinitionVersionId, long kaleoNodeId,
				com.liferay.portal.workflow.kaleo.definition.Condition
					condition,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKaleoCondition(
			kaleoDefinitionVersionId, kaleoNodeId, condition, serviceContext);
	}

	/**
	 * Creates a new kaleo condition with the primary key. Does not add the kaleo condition to the database.
	 *
	 * @param kaleoConditionId the primary key for the new kaleo condition
	 * @return the new kaleo condition
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
		createKaleoCondition(long kaleoConditionId) {

		return getService().createKaleoCondition(kaleoConditionId);
	}

	public static void deleteCompanyKaleoConditions(long companyId) {
		getService().deleteCompanyKaleoConditions(companyId);
	}

	/**
	 * Deletes the kaleo condition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoCondition the kaleo condition
	 * @return the kaleo condition that was removed
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
		deleteKaleoCondition(
			com.liferay.portal.workflow.kaleo.model.KaleoCondition
				kaleoCondition) {

		return getService().deleteKaleoCondition(kaleoCondition);
	}

	/**
	 * Deletes the kaleo condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoConditionId the primary key of the kaleo condition
	 * @return the kaleo condition that was removed
	 * @throws PortalException if a kaleo condition with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
			deleteKaleoCondition(long kaleoConditionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoCondition(kaleoConditionId);
	}

	public static void deleteKaleoDefinitionVersionKaleoCondition(
		long kaleoDefinitionVersionId) {

		getService().deleteKaleoDefinitionVersionKaleoCondition(
			kaleoDefinitionVersionId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
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

	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
		fetchKaleoCondition(long kaleoConditionId) {

		return getService().fetchKaleoCondition(kaleoConditionId);
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
	 * Returns the kaleo condition with the primary key.
	 *
	 * @param kaleoConditionId the primary key of the kaleo condition
	 * @return the kaleo condition
	 * @throws PortalException if a kaleo condition with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
			getKaleoCondition(long kaleoConditionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoCondition(kaleoConditionId);
	}

	/**
	 * Returns a range of all the kaleo conditions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo conditions
	 * @param end the upper bound of the range of kaleo conditions (not inclusive)
	 * @return the range of kaleo conditions
	 */
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoCondition>
			getKaleoConditions(int start, int end) {

		return getService().getKaleoConditions(start, end);
	}

	/**
	 * Returns the number of kaleo conditions.
	 *
	 * @return the number of kaleo conditions
	 */
	public static int getKaleoConditionsCount() {
		return getService().getKaleoConditionsCount();
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
			getKaleoNodeKaleoCondition(long kaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoNodeKaleoCondition(kaleoNodeId);
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
	 * Updates the kaleo condition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoCondition the kaleo condition
	 * @return the kaleo condition that was updated
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoCondition
		updateKaleoCondition(
			com.liferay.portal.workflow.kaleo.model.KaleoCondition
				kaleoCondition) {

		return getService().updateKaleoCondition(kaleoCondition);
	}

	public static KaleoConditionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoConditionLocalService, KaleoConditionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoConditionLocalService.class);

		ServiceTracker<KaleoConditionLocalService, KaleoConditionLocalService>
			serviceTracker =
				new ServiceTracker
					<KaleoConditionLocalService, KaleoConditionLocalService>(
						bundle.getBundleContext(),
						KaleoConditionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}