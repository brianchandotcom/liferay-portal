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
 * Provides the local service utility for RedirectNotFoundEntry. This utility wraps
 * <code>com.liferay.redirect.service.impl.RedirectNotFoundEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RedirectNotFoundEntryLocalService
 * @generated
 */
public class RedirectNotFoundEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.redirect.service.impl.RedirectNotFoundEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.redirect.model.RedirectNotFoundEntry
		addOrUpdateRedirectNotFoundEntry(
			com.liferay.portal.kernel.model.Group group, String url) {

		return getService().addOrUpdateRedirectNotFoundEntry(group, url);
	}

	/**
	 * Adds the redirect not found entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectNotFoundEntry the redirect not found entry
	 * @return the redirect not found entry that was added
	 */
	public static com.liferay.redirect.model.RedirectNotFoundEntry
		addRedirectNotFoundEntry(
			com.liferay.redirect.model.RedirectNotFoundEntry
				redirectNotFoundEntry) {

		return getService().addRedirectNotFoundEntry(redirectNotFoundEntry);
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
	 * Creates a new redirect not found entry with the primary key. Does not add the redirect not found entry to the database.
	 *
	 * @param redirectNotFoundEntryId the primary key for the new redirect not found entry
	 * @return the new redirect not found entry
	 */
	public static com.liferay.redirect.model.RedirectNotFoundEntry
		createRedirectNotFoundEntry(long redirectNotFoundEntryId) {

		return getService().createRedirectNotFoundEntry(
			redirectNotFoundEntryId);
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
	 * Deletes the redirect not found entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry that was removed
	 * @throws PortalException if a redirect not found entry with the primary key could not be found
	 */
	public static com.liferay.redirect.model.RedirectNotFoundEntry
			deleteRedirectNotFoundEntry(long redirectNotFoundEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRedirectNotFoundEntry(
			redirectNotFoundEntryId);
	}

	/**
	 * Deletes the redirect not found entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectNotFoundEntry the redirect not found entry
	 * @return the redirect not found entry that was removed
	 */
	public static com.liferay.redirect.model.RedirectNotFoundEntry
		deleteRedirectNotFoundEntry(
			com.liferay.redirect.model.RedirectNotFoundEntry
				redirectNotFoundEntry) {

		return getService().deleteRedirectNotFoundEntry(redirectNotFoundEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.redirect.model.impl.RedirectNotFoundEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.redirect.model.impl.RedirectNotFoundEntryModelImpl</code>.
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

	public static com.liferay.redirect.model.RedirectNotFoundEntry
		fetchRedirectNotFoundEntry(long redirectNotFoundEntryId) {

		return getService().fetchRedirectNotFoundEntry(redirectNotFoundEntryId);
	}

	public static com.liferay.redirect.model.RedirectNotFoundEntry
		fetchRedirectNotFoundEntry(long groupId, String url) {

		return getService().fetchRedirectNotFoundEntry(groupId, url);
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
	 * Returns a range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.redirect.model.impl.RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @return the range of redirect not found entries
	 */
	public static java.util.List
		<com.liferay.redirect.model.RedirectNotFoundEntry>
			getRedirectNotFoundEntries(int start, int end) {

		return getService().getRedirectNotFoundEntries(start, end);
	}

	public static java.util.List
		<com.liferay.redirect.model.RedirectNotFoundEntry>
			getRedirectNotFoundEntries(
				long groupId, Boolean ignored, java.util.Date minModifiedDate,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.redirect.model.RedirectNotFoundEntry> obc) {

		return getService().getRedirectNotFoundEntries(
			groupId, ignored, minModifiedDate, start, end, obc);
	}

	public static java.util.List
		<com.liferay.redirect.model.RedirectNotFoundEntry>
			getRedirectNotFoundEntries(
				long groupId, java.util.Date minModifiedDate, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.redirect.model.RedirectNotFoundEntry> obc) {

		return getService().getRedirectNotFoundEntries(
			groupId, minModifiedDate, start, end, obc);
	}

	public static java.util.List
		<com.liferay.redirect.model.RedirectNotFoundEntry>
			getRedirectNotFoundEntries(
				long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.redirect.model.RedirectNotFoundEntry> obc) {

		return getService().getRedirectNotFoundEntries(
			groupId, start, end, obc);
	}

	/**
	 * Returns the number of redirect not found entries.
	 *
	 * @return the number of redirect not found entries
	 */
	public static int getRedirectNotFoundEntriesCount() {
		return getService().getRedirectNotFoundEntriesCount();
	}

	public static int getRedirectNotFoundEntriesCount(long groupId) {
		return getService().getRedirectNotFoundEntriesCount(groupId);
	}

	public static int getRedirectNotFoundEntriesCount(
		long groupId, Boolean ignored, java.util.Date minModifiedDate) {

		return getService().getRedirectNotFoundEntriesCount(
			groupId, ignored, minModifiedDate);
	}

	public static int getRedirectNotFoundEntriesCount(
		long groupId, java.util.Date minModifiedDate) {

		return getService().getRedirectNotFoundEntriesCount(
			groupId, minModifiedDate);
	}

	/**
	 * Returns the redirect not found entry with the primary key.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry
	 * @throws PortalException if a redirect not found entry with the primary key could not be found
	 */
	public static com.liferay.redirect.model.RedirectNotFoundEntry
			getRedirectNotFoundEntry(long redirectNotFoundEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRedirectNotFoundEntry(redirectNotFoundEntryId);
	}

	public static com.liferay.redirect.model.RedirectNotFoundEntry
			updateRedirectNotFoundEntry(
				long redirectNotFoundEntryId, boolean ignored)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRedirectNotFoundEntry(
			redirectNotFoundEntryId, ignored);
	}

	/**
	 * Updates the redirect not found entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param redirectNotFoundEntry the redirect not found entry
	 * @return the redirect not found entry that was updated
	 */
	public static com.liferay.redirect.model.RedirectNotFoundEntry
		updateRedirectNotFoundEntry(
			com.liferay.redirect.model.RedirectNotFoundEntry
				redirectNotFoundEntry) {

		return getService().updateRedirectNotFoundEntry(redirectNotFoundEntry);
	}

	public static RedirectNotFoundEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RedirectNotFoundEntryLocalService, RedirectNotFoundEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RedirectNotFoundEntryLocalService.class);

		ServiceTracker
			<RedirectNotFoundEntryLocalService,
			 RedirectNotFoundEntryLocalService> serviceTracker =
				new ServiceTracker
					<RedirectNotFoundEntryLocalService,
					 RedirectNotFoundEntryLocalService>(
						 bundle.getBundleContext(),
						 RedirectNotFoundEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}