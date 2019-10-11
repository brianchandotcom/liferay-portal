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
 * Provides the local service utility for KaleoNotificationRecipient. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoNotificationRecipientLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotificationRecipientLocalService
 * @generated
 */
public class KaleoNotificationRecipientLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoNotificationRecipientLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kaleo notification recipient to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was added
	 */
	public static
		com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient
			addKaleoNotificationRecipient(
				com.liferay.portal.workflow.kaleo.model.
					KaleoNotificationRecipient kaleoNotificationRecipient) {

		return getService().addKaleoNotificationRecipient(
			kaleoNotificationRecipient);
	}

	public static
		com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient
				addKaleoNotificationRecipient(
					long kaleoDefinitionVersionId, long kaleoNotificationId,
					com.liferay.portal.workflow.kaleo.definition.Recipient
						recipient,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKaleoNotificationRecipient(
			kaleoDefinitionVersionId, kaleoNotificationId, recipient,
			serviceContext);
	}

	/**
	 * Creates a new kaleo notification recipient with the primary key. Does not add the kaleo notification recipient to the database.
	 *
	 * @param kaleoNotificationRecipientId the primary key for the new kaleo notification recipient
	 * @return the new kaleo notification recipient
	 */
	public static
		com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient
			createKaleoNotificationRecipient(
				long kaleoNotificationRecipientId) {

		return getService().createKaleoNotificationRecipient(
			kaleoNotificationRecipientId);
	}

	public static void deleteCompanyKaleoNotificationRecipients(
		long companyId) {

		getService().deleteCompanyKaleoNotificationRecipients(companyId);
	}

	public static void deleteKaleoDefinitionVersionKaleoNotificationRecipients(
		long kaleoDefinitionVersionId) {

		getService().deleteKaleoDefinitionVersionKaleoNotificationRecipients(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo notification recipient from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was removed
	 */
	public static
		com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient
			deleteKaleoNotificationRecipient(
				com.liferay.portal.workflow.kaleo.model.
					KaleoNotificationRecipient kaleoNotificationRecipient) {

		return getService().deleteKaleoNotificationRecipient(
			kaleoNotificationRecipient);
	}

	/**
	 * Deletes the kaleo notification recipient with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient that was removed
	 * @throws PortalException if a kaleo notification recipient with the primary key could not be found
	 */
	public static
		com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient
				deleteKaleoNotificationRecipient(
					long kaleoNotificationRecipientId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoNotificationRecipient(
			kaleoNotificationRecipientId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
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

	public static
		com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient
			fetchKaleoNotificationRecipient(long kaleoNotificationRecipientId) {

		return getService().fetchKaleoNotificationRecipient(
			kaleoNotificationRecipientId);
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
	 * Returns the kaleo notification recipient with the primary key.
	 *
	 * @param kaleoNotificationRecipientId the primary key of the kaleo notification recipient
	 * @return the kaleo notification recipient
	 * @throws PortalException if a kaleo notification recipient with the primary key could not be found
	 */
	public static
		com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient
				getKaleoNotificationRecipient(long kaleoNotificationRecipientId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoNotificationRecipient(
			kaleoNotificationRecipientId);
	}

	/**
	 * Returns a range of all the kaleo notification recipients.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoNotificationRecipientModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notification recipients
	 * @param end the upper bound of the range of kaleo notification recipients (not inclusive)
	 * @return the range of kaleo notification recipients
	 */
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient>
			getKaleoNotificationRecipients(int start, int end) {

		return getService().getKaleoNotificationRecipients(start, end);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient>
			getKaleoNotificationRecipients(long kaleoNotificationId) {

		return getService().getKaleoNotificationRecipients(kaleoNotificationId);
	}

	/**
	 * Returns the number of kaleo notification recipients.
	 *
	 * @return the number of kaleo notification recipients
	 */
	public static int getKaleoNotificationRecipientsCount() {
		return getService().getKaleoNotificationRecipientsCount();
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
	 * Updates the kaleo notification recipient in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotificationRecipient the kaleo notification recipient
	 * @return the kaleo notification recipient that was updated
	 */
	public static
		com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient
			updateKaleoNotificationRecipient(
				com.liferay.portal.workflow.kaleo.model.
					KaleoNotificationRecipient kaleoNotificationRecipient) {

		return getService().updateKaleoNotificationRecipient(
			kaleoNotificationRecipient);
	}

	public static KaleoNotificationRecipientLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoNotificationRecipientLocalService,
		 KaleoNotificationRecipientLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoNotificationRecipientLocalService.class);

		ServiceTracker
			<KaleoNotificationRecipientLocalService,
			 KaleoNotificationRecipientLocalService> serviceTracker =
				new ServiceTracker
					<KaleoNotificationRecipientLocalService,
					 KaleoNotificationRecipientLocalService>(
						 bundle.getBundleContext(),
						 KaleoNotificationRecipientLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}