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

package com.liferay.portal.tools.service.builder.test.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for VersionedEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.VersionedEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryLocalService
 * @generated
 */
public class VersionedEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.VersionedEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the versioned entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntry the versioned entry
	 * @return the versioned entry that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			addVersionedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry) {

		return getService().addVersionedEntry(versionedEntry);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				checkout(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry publishedVersionedEntry,
					int version)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkout(publishedVersionedEntry, version);
	}

	/**
	 * Creates a new versioned entry. Does not add the versioned entry to the database.
	 *
	 * @return the new versioned entry
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			create() {

		return getService().create();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				delete(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry publishedVersionedEntry)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().delete(publishedVersionedEntry);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				deleteDraft(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry draftVersionedEntry)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDraft(draftVersionedEntry);
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

	public static
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion deleteVersion(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntryVersion versionedEntryVersion)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteVersion(versionedEntryVersion);
	}

	/**
	 * Deletes the versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry that was removed
	 * @throws PortalException if a versioned entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				deleteVersionedEntry(long versionedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteVersionedEntry(versionedEntryId);
	}

	/**
	 * Deletes the versioned entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntry the versioned entry
	 * @return the versioned entry that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			deleteVersionedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry) {

		return getService().deleteVersionedEntry(versionedEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryModelImpl</code>.
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
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			fetchDraft(long primaryKey) {

		return getService().fetchDraft(primaryKey);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			fetchDraft(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry) {

		return getService().fetchDraft(versionedEntry);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion fetchLatestVersion(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry) {

		return getService().fetchLatestVersion(versionedEntry);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			fetchPublished(long primaryKey) {

		return getService().fetchPublished(primaryKey);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			fetchPublished(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry) {

		return getService().fetchPublished(versionedEntry);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			fetchVersionedEntry(long versionedEntryId) {

		return getService().fetchVersionedEntry(versionedEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				getDraft(long primaryKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDraft(primaryKey);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				getDraft(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry versionedEntry)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDraft(versionedEntry);
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

	public static
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion getVersion(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry versionedEntry,
					int version)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVersion(versionedEntry, version);
	}

	/**
	 * Returns a range of all the versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entries
	 * @param end the upper bound of the range of versioned entries (not inclusive)
	 * @return the range of versioned entries
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.VersionedEntry>
			getVersionedEntries(int start, int end) {

		return getService().getVersionedEntries(start, end);
	}

	/**
	 * Returns the number of versioned entries.
	 *
	 * @return the number of versioned entries
	 */
	public static int getVersionedEntriesCount() {
		return getService().getVersionedEntriesCount();
	}

	/**
	 * Returns the versioned entry with the primary key.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry
	 * @throws PortalException if a versioned entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				getVersionedEntry(long versionedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVersionedEntry(versionedEntryId);
	}

	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion> getVersions(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry) {

		return getService().getVersions(versionedEntry);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				publishDraft(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry draftVersionedEntry)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().publishDraft(draftVersionedEntry);
	}

	public static void registerListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.portal.tools.service.builder.test.model.VersionedEntry,
			 com.liferay.portal.tools.service.builder.test.model.
				 VersionedEntryVersion> versionServiceListener) {

		getService().registerListener(versionServiceListener);
	}

	public static void unregisterListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.portal.tools.service.builder.test.model.VersionedEntry,
			 com.liferay.portal.tools.service.builder.test.model.
				 VersionedEntryVersion> versionServiceListener) {

		getService().unregisterListener(versionServiceListener);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				updateDraft(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry draftVersionedEntry)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDraft(draftVersionedEntry);
	}

	/**
	 * Updates the versioned entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntry the versioned entry
	 * @return the versioned entry that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				updateVersionedEntry(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry draftVersionedEntry)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateVersionedEntry(draftVersionedEntry);
	}

	public static VersionedEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<VersionedEntryLocalService, VersionedEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			VersionedEntryLocalService.class);

		ServiceTracker<VersionedEntryLocalService, VersionedEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<VersionedEntryLocalService, VersionedEntryLocalService>(
						bundle.getBundleContext(),
						VersionedEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}