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

package com.liferay.portal.security.audit.storage.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AuditEvent. This utility wraps
 * <code>com.liferay.portal.security.audit.storage.service.impl.AuditEventLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventLocalService
 * @generated
 */
public class AuditEventLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.security.audit.storage.service.impl.AuditEventLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the audit event to the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEvent the audit event
	 * @return the audit event that was added
	 */
	public static com.liferay.portal.security.audit.storage.model.AuditEvent
		addAuditEvent(
			com.liferay.portal.security.audit.storage.model.AuditEvent
				auditEvent) {

		return getService().addAuditEvent(auditEvent);
	}

	public static com.liferay.portal.security.audit.storage.model.AuditEvent
		addAuditEvent(
			com.liferay.portal.kernel.audit.AuditMessage auditMessage) {

		return getService().addAuditEvent(auditMessage);
	}

	/**
	 * Creates a new audit event with the primary key. Does not add the audit event to the database.
	 *
	 * @param auditEventId the primary key for the new audit event
	 * @return the new audit event
	 */
	public static com.liferay.portal.security.audit.storage.model.AuditEvent
		createAuditEvent(long auditEventId) {

		return getService().createAuditEvent(auditEventId);
	}

	/**
	 * Deletes the audit event from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEvent the audit event
	 * @return the audit event that was removed
	 */
	public static com.liferay.portal.security.audit.storage.model.AuditEvent
		deleteAuditEvent(
			com.liferay.portal.security.audit.storage.model.AuditEvent
				auditEvent) {

		return getService().deleteAuditEvent(auditEvent);
	}

	/**
	 * Deletes the audit event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param auditEventId the primary key of the audit event
	 * @return the audit event that was removed
	 * @throws PortalException if a audit event with the primary key could not be found
	 */
	public static com.liferay.portal.security.audit.storage.model.AuditEvent
			deleteAuditEvent(long auditEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAuditEvent(auditEventId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventModelImpl</code>.
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

	public static com.liferay.portal.security.audit.storage.model.AuditEvent
		fetchAuditEvent(long auditEventId) {

		return getService().fetchAuditEvent(auditEventId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the audit event with the primary key.
	 *
	 * @param auditEventId the primary key of the audit event
	 * @return the audit event
	 * @throws PortalException if a audit event with the primary key could not be found
	 */
	public static com.liferay.portal.security.audit.storage.model.AuditEvent
			getAuditEvent(long auditEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAuditEvent(auditEventId);
	}

	/**
	 * Returns a range of all the audit events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of audit events
	 * @param end the upper bound of the range of audit events (not inclusive)
	 * @return the range of audit events
	 */
	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
			getAuditEvents(int start, int end) {

		return getService().getAuditEvents(start, end);
	}

	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
			getAuditEvents(long companyId, int start, int end) {

		return getService().getAuditEvents(companyId, start, end);
	}

	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
			getAuditEvents(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					orderByComparator) {

		return getService().getAuditEvents(
			companyId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
			getAuditEvents(
				long companyId, long userId, String userName,
				java.util.Date createDateGT, java.util.Date createDateLT,
				String eventType, String className, String classPK,
				String clientHost, String clientIP, String serverName,
				int serverPort, String sessionID, boolean andSearch, int start,
				int end) {

		return getService().getAuditEvents(
			companyId, userId, userName, createDateGT, createDateLT, eventType,
			className, classPK, clientHost, clientIP, serverName, serverPort,
			sessionID, andSearch, start, end);
	}

	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
			getAuditEvents(
				long companyId, long userId, String userName,
				java.util.Date createDateGT, java.util.Date createDateLT,
				String eventType, String className, String classPK,
				String clientHost, String clientIP, String serverName,
				int serverPort, String sessionID, boolean andSearch, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					orderByComparator) {

		return getService().getAuditEvents(
			companyId, userId, userName, createDateGT, createDateLT, eventType,
			className, classPK, clientHost, clientIP, serverName, serverPort,
			sessionID, andSearch, start, end, orderByComparator);
	}

	/**
	 * Returns the number of audit events.
	 *
	 * @return the number of audit events
	 */
	public static int getAuditEventsCount() {
		return getService().getAuditEventsCount();
	}

	public static int getAuditEventsCount(long companyId) {
		return getService().getAuditEventsCount(companyId);
	}

	public static int getAuditEventsCount(
		long companyId, long userId, String userName,
		java.util.Date createDateGT, java.util.Date createDateLT,
		String eventType, String className, String classPK, String clientHost,
		String clientIP, String serverName, int serverPort, String sessionID,
		boolean andSearch) {

		return getService().getAuditEventsCount(
			companyId, userId, userName, createDateGT, createDateLT, eventType,
			className, classPK, clientHost, clientIP, serverName, serverPort,
			sessionID, andSearch);
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the audit event in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param auditEvent the audit event
	 * @return the audit event that was updated
	 */
	public static com.liferay.portal.security.audit.storage.model.AuditEvent
		updateAuditEvent(
			com.liferay.portal.security.audit.storage.model.AuditEvent
				auditEvent) {

		return getService().updateAuditEvent(auditEvent);
	}

	public static AuditEventLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AuditEventLocalService, AuditEventLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AuditEventLocalService.class);

		ServiceTracker<AuditEventLocalService, AuditEventLocalService>
			serviceTracker =
				new ServiceTracker
					<AuditEventLocalService, AuditEventLocalService>(
						bundle.getBundleContext(), AuditEventLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}