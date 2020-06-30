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

package com.liferay.analytics.message.storage.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AnalyticsMessage. This utility wraps
 * <code>com.liferay.analytics.message.storage.service.impl.AnalyticsMessageLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageLocalService
 * @generated
 */
public class AnalyticsMessageLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.analytics.message.storage.service.impl.AnalyticsMessageLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the analytics message to the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessage the analytics message
	 * @return the analytics message that was added
	 */
	public static com.liferay.analytics.message.storage.model.AnalyticsMessage
		addAnalyticsMessage(
			com.liferay.analytics.message.storage.model.AnalyticsMessage
				analyticsMessage) {

		return getService().addAnalyticsMessage(analyticsMessage);
	}

	public static com.liferay.analytics.message.storage.model.AnalyticsMessage
			addAnalyticsMessage(long companyId, long userId, byte[] body)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAnalyticsMessage(companyId, userId, body);
	}

	/**
	 * Creates a new analytics message with the primary key. Does not add the analytics message to the database.
	 *
	 * @param analyticsMessageId the primary key for the new analytics message
	 * @return the new analytics message
	 */
	public static com.liferay.analytics.message.storage.model.AnalyticsMessage
		createAnalyticsMessage(long analyticsMessageId) {

		return getService().createAnalyticsMessage(analyticsMessageId);
	}

	/**
	 * Deletes the analytics message from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessage the analytics message
	 * @return the analytics message that was removed
	 */
	public static com.liferay.analytics.message.storage.model.AnalyticsMessage
		deleteAnalyticsMessage(
			com.liferay.analytics.message.storage.model.AnalyticsMessage
				analyticsMessage) {

		return getService().deleteAnalyticsMessage(analyticsMessage);
	}

	/**
	 * Deletes the analytics message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message that was removed
	 * @throws PortalException if a analytics message with the primary key could not be found
	 */
	public static com.liferay.analytics.message.storage.model.AnalyticsMessage
			deleteAnalyticsMessage(long analyticsMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAnalyticsMessage(analyticsMessageId);
	}

	public static void deleteAnalyticsMessages(
		java.util.List
			<com.liferay.analytics.message.storage.model.AnalyticsMessage>
				analyticsMessages) {

		getService().deleteAnalyticsMessages(analyticsMessages);
	}

	public static void deleteAnalyticsMessages(long companyId) {
		getService().deleteAnalyticsMessages(companyId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageModelImpl</code>.
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

	public static com.liferay.analytics.message.storage.model.AnalyticsMessage
		fetchAnalyticsMessage(long analyticsMessageId) {

		return getService().fetchAnalyticsMessage(analyticsMessageId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the analytics message with the primary key.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message
	 * @throws PortalException if a analytics message with the primary key could not be found
	 */
	public static com.liferay.analytics.message.storage.model.AnalyticsMessage
			getAnalyticsMessage(long analyticsMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAnalyticsMessage(analyticsMessageId);
	}

	/**
	 * Returns a range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @return the range of analytics messages
	 */
	public static java.util.List
		<com.liferay.analytics.message.storage.model.AnalyticsMessage>
			getAnalyticsMessages(int start, int end) {

		return getService().getAnalyticsMessages(start, end);
	}

	public static java.util.List
		<com.liferay.analytics.message.storage.model.AnalyticsMessage>
			getAnalyticsMessages(long companyId, int start, int end) {

		return getService().getAnalyticsMessages(companyId, start, end);
	}

	/**
	 * Returns the number of analytics messages.
	 *
	 * @return the number of analytics messages
	 */
	public static int getAnalyticsMessagesCount() {
		return getService().getAnalyticsMessagesCount();
	}

	public static
		com.liferay.analytics.message.storage.model.
			AnalyticsMessageBodyBlobModel getBodyBlobModel(
				java.io.Serializable primaryKey) {

		return getService().getBodyBlobModel(primaryKey);
	}

	public static java.util.List<Long> getCompanyIds() {
		return getService().getCompanyIds();
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

	public static java.io.InputStream openBodyInputStream(
		long analyticsMessageId) {

		return getService().openBodyInputStream(analyticsMessageId);
	}

	/**
	 * Updates the analytics message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessage the analytics message
	 * @return the analytics message that was updated
	 */
	public static com.liferay.analytics.message.storage.model.AnalyticsMessage
		updateAnalyticsMessage(
			com.liferay.analytics.message.storage.model.AnalyticsMessage
				analyticsMessage) {

		return getService().updateAnalyticsMessage(analyticsMessage);
	}

	public static AnalyticsMessageLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AnalyticsMessageLocalService, AnalyticsMessageLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AnalyticsMessageLocalService.class);

		ServiceTracker
			<AnalyticsMessageLocalService, AnalyticsMessageLocalService>
				serviceTracker =
					new ServiceTracker
						<AnalyticsMessageLocalService,
						 AnalyticsMessageLocalService>(
							 bundle.getBundleContext(),
							 AnalyticsMessageLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}