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

package com.liferay.remote.web.app.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for RemoteWebAppEntry. This utility wraps
 * <code>com.liferay.remote.web.app.service.impl.RemoteWebAppEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteWebAppEntryLocalService
 * @generated
 */
public class RemoteWebAppEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.remote.web.app.service.impl.RemoteWebAppEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry addEntry(
			long userId, java.util.Map<java.util.Locale, String> nameMap,
			String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(userId, nameMap, url, serviceContext);
	}

	/**
	 * Adds the remote web app entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteWebAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 * @return the remote web app entry that was added
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
		addRemoteWebAppEntry(
			com.liferay.remote.web.app.model.RemoteWebAppEntry
				remoteWebAppEntry) {

		return getService().addRemoteWebAppEntry(remoteWebAppEntry);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new remote web app entry with the primary key. Does not add the remote web app entry to the database.
	 *
	 * @param entryId the primary key for the new remote web app entry
	 * @return the new remote web app entry
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
		createRemoteWebAppEntry(long entryId) {

		return getService().createRemoteWebAppEntry(entryId);
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
	 * Deletes the remote web app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteWebAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry that was removed
	 * @throws PortalException if a remote web app entry with the primary key could not be found
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
			deleteRemoteWebAppEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRemoteWebAppEntry(entryId);
	}

	/**
	 * Deletes the remote web app entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteWebAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 * @return the remote web app entry that was removed
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
		deleteRemoteWebAppEntry(
			com.liferay.remote.web.app.model.RemoteWebAppEntry
				remoteWebAppEntry) {

		return getService().deleteRemoteWebAppEntry(remoteWebAppEntry);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.app.model.impl.RemoteWebAppEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.app.model.impl.RemoteWebAppEntryModelImpl</code>.
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

	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
		fetchRemoteWebAppEntry(long entryId) {

		return getService().fetchRemoteWebAppEntry(entryId);
	}

	/**
	 * Returns the remote web app entry with the matching UUID and company.
	 *
	 * @param uuid the remote web app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
		fetchRemoteWebAppEntryByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchRemoteWebAppEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
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
	 * Returns a range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.app.model.impl.RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of remote web app entries
	 */
	public static java.util.List
		<com.liferay.remote.web.app.model.RemoteWebAppEntry>
			getRemoteWebAppEntries(int start, int end) {

		return getService().getRemoteWebAppEntries(start, end);
	}

	/**
	 * Returns the number of remote web app entries.
	 *
	 * @return the number of remote web app entries
	 */
	public static int getRemoteWebAppEntriesCount() {
		return getService().getRemoteWebAppEntriesCount();
	}

	/**
	 * Returns the remote web app entry with the primary key.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry
	 * @throws PortalException if a remote web app entry with the primary key could not be found
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
			getRemoteWebAppEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRemoteWebAppEntry(entryId);
	}

	/**
	 * Returns the remote web app entry with the matching UUID and company.
	 *
	 * @param uuid the remote web app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web app entry
	 * @throws PortalException if a matching remote web app entry could not be found
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
			getRemoteWebAppEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRemoteWebAppEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
			updateEntry(
				long entryId, java.util.Map<java.util.Locale, String> nameMap,
				String url,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(entryId, nameMap, url, serviceContext);
	}

	/**
	 * Updates the remote web app entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteWebAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 * @return the remote web app entry that was updated
	 */
	public static com.liferay.remote.web.app.model.RemoteWebAppEntry
		updateRemoteWebAppEntry(
			com.liferay.remote.web.app.model.RemoteWebAppEntry
				remoteWebAppEntry) {

		return getService().updateRemoteWebAppEntry(remoteWebAppEntry);
	}

	public static RemoteWebAppEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RemoteWebAppEntryLocalService, RemoteWebAppEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RemoteWebAppEntryLocalService.class);

		ServiceTracker
			<RemoteWebAppEntryLocalService, RemoteWebAppEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<RemoteWebAppEntryLocalService,
						 RemoteWebAppEntryLocalService>(
							 bundle.getBundleContext(),
							 RemoteWebAppEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}