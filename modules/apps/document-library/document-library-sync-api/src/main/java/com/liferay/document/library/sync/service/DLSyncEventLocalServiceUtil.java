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

package com.liferay.document.library.sync.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DLSyncEvent. This utility wraps
 * <code>com.liferay.document.library.sync.service.impl.DLSyncEventLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventLocalService
 * @generated
 */
public class DLSyncEventLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.document.library.sync.service.impl.DLSyncEventLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the dl sync event to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlSyncEvent the dl sync event
	 * @return the dl sync event that was added
	 */
	public static com.liferay.document.library.sync.model.DLSyncEvent
		addDLSyncEvent(
			com.liferay.document.library.sync.model.DLSyncEvent dlSyncEvent) {

		return getService().addDLSyncEvent(dlSyncEvent);
	}

	public static com.liferay.document.library.sync.model.DLSyncEvent
		addDLSyncEvent(String event, String type, long typePK) {

		return getService().addDLSyncEvent(event, type, typePK);
	}

	/**
	 * Creates a new dl sync event with the primary key. Does not add the dl sync event to the database.
	 *
	 * @param syncEventId the primary key for the new dl sync event
	 * @return the new dl sync event
	 */
	public static com.liferay.document.library.sync.model.DLSyncEvent
		createDLSyncEvent(long syncEventId) {

		return getService().createDLSyncEvent(syncEventId);
	}

	/**
	 * Deletes the dl sync event from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlSyncEvent the dl sync event
	 * @return the dl sync event that was removed
	 */
	public static com.liferay.document.library.sync.model.DLSyncEvent
		deleteDLSyncEvent(
			com.liferay.document.library.sync.model.DLSyncEvent dlSyncEvent) {

		return getService().deleteDLSyncEvent(dlSyncEvent);
	}

	/**
	 * Deletes the dl sync event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event that was removed
	 * @throws PortalException if a dl sync event with the primary key could not be found
	 */
	public static com.liferay.document.library.sync.model.DLSyncEvent
			deleteDLSyncEvent(long syncEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDLSyncEvent(syncEventId);
	}

	public static void deleteDLSyncEvents() {
		getService().deleteDLSyncEvents();
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.sync.model.impl.DLSyncEventModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.sync.model.impl.DLSyncEventModelImpl</code>.
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

	public static com.liferay.document.library.sync.model.DLSyncEvent
		fetchDLSyncEvent(long syncEventId) {

		return getService().fetchDLSyncEvent(syncEventId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the dl sync event with the primary key.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event
	 * @throws PortalException if a dl sync event with the primary key could not be found
	 */
	public static com.liferay.document.library.sync.model.DLSyncEvent
			getDLSyncEvent(long syncEventId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDLSyncEvent(syncEventId);
	}

	/**
	 * Returns a range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.sync.model.impl.DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @return the range of dl sync events
	 */
	public static java.util.List
		<com.liferay.document.library.sync.model.DLSyncEvent> getDLSyncEvents(
			int start, int end) {

		return getService().getDLSyncEvents(start, end);
	}

	public static java.util.List
		<com.liferay.document.library.sync.model.DLSyncEvent> getDLSyncEvents(
			long modifiedTime) {

		return getService().getDLSyncEvents(modifiedTime);
	}

	/**
	 * Returns the number of dl sync events.
	 *
	 * @return the number of dl sync events
	 */
	public static int getDLSyncEventsCount() {
		return getService().getDLSyncEventsCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.document.library.sync.model.DLSyncEvent>
			getLatestDLSyncEvents() {

		return getService().getLatestDLSyncEvents();
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
	 * Updates the dl sync event in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param dlSyncEvent the dl sync event
	 * @return the dl sync event that was updated
	 */
	public static com.liferay.document.library.sync.model.DLSyncEvent
		updateDLSyncEvent(
			com.liferay.document.library.sync.model.DLSyncEvent dlSyncEvent) {

		return getService().updateDLSyncEvent(dlSyncEvent);
	}

	public static DLSyncEventLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DLSyncEventLocalService, DLSyncEventLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DLSyncEventLocalService.class);

		ServiceTracker<DLSyncEventLocalService, DLSyncEventLocalService>
			serviceTracker =
				new ServiceTracker
					<DLSyncEventLocalService, DLSyncEventLocalService>(
						bundle.getBundleContext(),
						DLSyncEventLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}