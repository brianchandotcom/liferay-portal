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

package com.liferay.redirect.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for RedirectEntry. This utility wraps
 * <code>com.liferay.redirect.service.impl.RedirectEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RedirectEntryLocalService
 * @generated
 */
public class RedirectEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.redirect.service.impl.RedirectEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addEntryResources(
			com.liferay.redirect.model.RedirectEntry entry,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addEntryResources(
			entry, addGroupPermissions, addGuestPermissions);
	}

	public static void addEntryResources(
			com.liferay.redirect.model.RedirectEntry entry,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addEntryResources(entry, modelPermissions);
	}

	public static com.liferay.redirect.model.RedirectEntry addRedirectEntry(
			long groupId, String destinationURL, java.util.Date expirationDate,
			boolean permanent, String sourceURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRedirectEntry(
			groupId, destinationURL, expirationDate, permanent, sourceURL,
			serviceContext);
	}

	/**
	 * Adds the redirect entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectEntry the redirect entry
	 * @return the redirect entry that was added
	 */
	public static com.liferay.redirect.model.RedirectEntry addRedirectEntry(
		com.liferay.redirect.model.RedirectEntry redirectEntry) {

		return getService().addRedirectEntry(redirectEntry);
	}

	public static boolean checkRedirectEntriesByGroupAndDestinationURL(
		long groupId, String destinationURL) {

		return getService().checkRedirectEntriesByGroupAndDestinationURL(
			groupId, destinationURL);
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
	 * Creates a new redirect entry with the primary key. Does not add the redirect entry to the database.
	 *
	 * @param redirectEntryId the primary key for the new redirect entry
	 * @return the new redirect entry
	 */
	public static com.liferay.redirect.model.RedirectEntry createRedirectEntry(
		long redirectEntryId) {

		return getService().createRedirectEntry(redirectEntryId);
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
	 * Deletes the redirect entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectEntryId the primary key of the redirect entry
	 * @return the redirect entry that was removed
	 * @throws PortalException if a redirect entry with the primary key could not be found
	 */
	public static com.liferay.redirect.model.RedirectEntry deleteRedirectEntry(
			long redirectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRedirectEntry(redirectEntryId);
	}

	/**
	 * Deletes the redirect entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectEntry the redirect entry
	 * @return the redirect entry that was removed
	 */
	public static com.liferay.redirect.model.RedirectEntry deleteRedirectEntry(
		com.liferay.redirect.model.RedirectEntry redirectEntry) {

		return getService().deleteRedirectEntry(redirectEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.redirect.model.impl.RedirectEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.redirect.model.impl.RedirectEntryModelImpl</code>.
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

	public static com.liferay.redirect.model.RedirectEntry fetchRedirectEntry(
		long redirectEntryId) {

		return getService().fetchRedirectEntry(redirectEntryId);
	}

	public static com.liferay.redirect.model.RedirectEntry fetchRedirectEntry(
		long groupId, String sourceURL) {

		return getService().fetchRedirectEntry(groupId, sourceURL);
	}

	public static com.liferay.redirect.model.RedirectEntry fetchRedirectEntry(
		long groupId, String sourceURL, boolean updateLastOccurrenceDate) {

		return getService().fetchRedirectEntry(
			groupId, sourceURL, updateLastOccurrenceDate);
	}

	/**
	 * Returns the redirect entry matching the UUID and group.
	 *
	 * @param uuid the redirect entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching redirect entry, or <code>null</code> if a matching redirect entry could not be found
	 */
	public static com.liferay.redirect.model.RedirectEntry
		fetchRedirectEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchRedirectEntryByUuidAndGroupId(uuid, groupId);
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
	 * Returns a range of all the redirect entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.redirect.model.impl.RedirectEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @return the range of redirect entries
	 */
	public static java.util.List<com.liferay.redirect.model.RedirectEntry>
		getRedirectEntries(int start, int end) {

		return getService().getRedirectEntries(start, end);
	}

	public static java.util.List<com.liferay.redirect.model.RedirectEntry>
		getRedirectEntries(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.redirect.model.RedirectEntry> obc) {

		return getService().getRedirectEntries(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.redirect.model.RedirectEntry>
		getRedirectEntriesByGroupAndDestinationURL(
			long groupId, String destinationURL) {

		return getService().getRedirectEntriesByGroupAndDestinationURL(
			groupId, destinationURL);
	}

	/**
	 * Returns all the redirect entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the redirect entries
	 * @param companyId the primary key of the company
	 * @return the matching redirect entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.redirect.model.RedirectEntry>
		getRedirectEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getRedirectEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of redirect entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the redirect entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of redirect entries
	 * @param end the upper bound of the range of redirect entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching redirect entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.redirect.model.RedirectEntry>
		getRedirectEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.redirect.model.RedirectEntry> orderByComparator) {

		return getService().getRedirectEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of redirect entries.
	 *
	 * @return the number of redirect entries
	 */
	public static int getRedirectEntriesCount() {
		return getService().getRedirectEntriesCount();
	}

	public static int getRedirectEntriesCount(long groupId) {
		return getService().getRedirectEntriesCount(groupId);
	}

	/**
	 * Returns the redirect entry with the primary key.
	 *
	 * @param redirectEntryId the primary key of the redirect entry
	 * @return the redirect entry
	 * @throws PortalException if a redirect entry with the primary key could not be found
	 */
	public static com.liferay.redirect.model.RedirectEntry getRedirectEntry(
			long redirectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRedirectEntry(redirectEntryId);
	}

	/**
	 * Returns the redirect entry matching the UUID and group.
	 *
	 * @param uuid the redirect entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching redirect entry
	 * @throws PortalException if a matching redirect entry could not be found
	 */
	public static com.liferay.redirect.model.RedirectEntry
			getRedirectEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRedirectEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.redirect.model.RedirectEntry updateRedirectEntry(
			long redirectEntryId, String destinationURL,
			java.util.Date expirationDate, boolean permanent, String sourceURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRedirectEntry(
			redirectEntryId, destinationURL, expirationDate, permanent,
			sourceURL);
	}

	/**
	 * Updates the redirect entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param redirectEntry the redirect entry
	 * @return the redirect entry that was updated
	 */
	public static com.liferay.redirect.model.RedirectEntry updateRedirectEntry(
		com.liferay.redirect.model.RedirectEntry redirectEntry) {

		return getService().updateRedirectEntry(redirectEntry);
	}

	public static RedirectEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RedirectEntryLocalService, RedirectEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RedirectEntryLocalService.class);

		ServiceTracker<RedirectEntryLocalService, RedirectEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<RedirectEntryLocalService, RedirectEntryLocalService>(
						bundle.getBundleContext(),
						RedirectEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}