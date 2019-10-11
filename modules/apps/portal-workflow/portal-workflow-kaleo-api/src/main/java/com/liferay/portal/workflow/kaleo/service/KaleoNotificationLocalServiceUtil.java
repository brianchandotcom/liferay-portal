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
 * Provides the local service utility for KaleoNotification. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoNotificationLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotificationLocalService
 * @generated
 */
public class KaleoNotificationLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoNotificationLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kaleo notification to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotification the kaleo notification
	 * @return the kaleo notification that was added
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoNotification
		addKaleoNotification(
			com.liferay.portal.workflow.kaleo.model.KaleoNotification
				kaleoNotification) {

		return getService().addKaleoNotification(kaleoNotification);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoNotification
			addKaleoNotification(
				String kaleoClassName, long kaleoClassPK,
				long kaleoDefinitionVersionId, String kaleoNodeName,
				com.liferay.portal.workflow.kaleo.definition.Notification
					notification,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKaleoNotification(
			kaleoClassName, kaleoClassPK, kaleoDefinitionVersionId,
			kaleoNodeName, notification, serviceContext);
	}

	/**
	 * Creates a new kaleo notification with the primary key. Does not add the kaleo notification to the database.
	 *
	 * @param kaleoNotificationId the primary key for the new kaleo notification
	 * @return the new kaleo notification
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoNotification
		createKaleoNotification(long kaleoNotificationId) {

		return getService().createKaleoNotification(kaleoNotificationId);
	}

	public static void deleteCompanyKaleoNotifications(long companyId) {
		getService().deleteCompanyKaleoNotifications(companyId);
	}

	public static void deleteKaleoDefinitionVersionKaleoNotifications(
		long kaleoDefinitionVersionId) {

		getService().deleteKaleoDefinitionVersionKaleoNotifications(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo notification from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotification the kaleo notification
	 * @return the kaleo notification that was removed
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoNotification
		deleteKaleoNotification(
			com.liferay.portal.workflow.kaleo.model.KaleoNotification
				kaleoNotification) {

		return getService().deleteKaleoNotification(kaleoNotification);
	}

	/**
	 * Deletes the kaleo notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotificationId the primary key of the kaleo notification
	 * @return the kaleo notification that was removed
	 * @throws PortalException if a kaleo notification with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoNotification
			deleteKaleoNotification(long kaleoNotificationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoNotification(kaleoNotificationId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationModelImpl</code>.
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

	public static com.liferay.portal.workflow.kaleo.model.KaleoNotification
		fetchKaleoNotification(long kaleoNotificationId) {

		return getService().fetchKaleoNotification(kaleoNotificationId);
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
	 * Returns the kaleo notification with the primary key.
	 *
	 * @param kaleoNotificationId the primary key of the kaleo notification
	 * @return the kaleo notification
	 * @throws PortalException if a kaleo notification with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoNotification
			getKaleoNotification(long kaleoNotificationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoNotification(kaleoNotificationId);
	}

	/**
	 * Returns a range of all the kaleo notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @return the range of kaleo notifications
	 */
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoNotification>
			getKaleoNotifications(int start, int end) {

		return getService().getKaleoNotifications(start, end);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoNotification>
			getKaleoNotifications(String kaleoClassName, long kaleoClassPK) {

		return getService().getKaleoNotifications(kaleoClassName, kaleoClassPK);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoNotification>
			getKaleoNotifications(
				String kaleoClassName, long kaleoClassPK,
				String executionType) {

		return getService().getKaleoNotifications(
			kaleoClassName, kaleoClassPK, executionType);
	}

	/**
	 * Returns the number of kaleo notifications.
	 *
	 * @return the number of kaleo notifications
	 */
	public static int getKaleoNotificationsCount() {
		return getService().getKaleoNotificationsCount();
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
	 * Updates the kaleo notification in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotification the kaleo notification
	 * @return the kaleo notification that was updated
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoNotification
		updateKaleoNotification(
			com.liferay.portal.workflow.kaleo.model.KaleoNotification
				kaleoNotification) {

		return getService().updateKaleoNotification(kaleoNotification);
	}

	public static KaleoNotificationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoNotificationLocalService, KaleoNotificationLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoNotificationLocalService.class);

		ServiceTracker
			<KaleoNotificationLocalService, KaleoNotificationLocalService>
				serviceTracker =
					new ServiceTracker
						<KaleoNotificationLocalService,
						 KaleoNotificationLocalService>(
							 bundle.getBundleContext(),
							 KaleoNotificationLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}