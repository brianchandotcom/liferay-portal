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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntry;

import java.io.Serializable;

import java.util.List;

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
	 * <p>
	 * <strong>Important:</strong> Inspect VersionedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param versionedEntry the versioned entry
	 * @return the versioned entry that was added
	 */
	public static VersionedEntry addVersionedEntry(
		VersionedEntry versionedEntry) {

		return getService().addVersionedEntry(versionedEntry);
	}

	public static VersionedEntry checkout(
			VersionedEntry publishedVersionedEntry, int version)
		throws PortalException {

		return getService().checkout(publishedVersionedEntry, version);
	}

	/**
	 * Creates a new versioned entry. Does not add the versioned entry to the database.
	 *
	 * @return the new versioned entry
	 */
	public static VersionedEntry create() {
		return getService().create();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static VersionedEntry delete(VersionedEntry publishedVersionedEntry)
		throws PortalException {

		return getService().delete(publishedVersionedEntry);
	}

	public static VersionedEntry deleteDraft(VersionedEntry draftVersionedEntry)
		throws PortalException {

		return getService().deleteDraft(draftVersionedEntry);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion deleteVersion(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntryVersion versionedEntryVersion)
				throws PortalException {

		return getService().deleteVersion(versionedEntryVersion);
	}

	/**
	 * Deletes the versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect VersionedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry that was removed
	 * @throws PortalException if a versioned entry with the primary key could not be found
	 */
	public static VersionedEntry deleteVersionedEntry(long versionedEntryId)
		throws PortalException {

		return getService().deleteVersionedEntry(versionedEntryId);
	}

	/**
	 * Deletes the versioned entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect VersionedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param versionedEntry the versioned entry
	 * @return the versioned entry that was removed
	 */
	public static VersionedEntry deleteVersionedEntry(
		VersionedEntry versionedEntry) {

		return getService().deleteVersionedEntry(versionedEntry);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static VersionedEntry fetchDraft(long primaryKey) {
		return getService().fetchDraft(primaryKey);
	}

	public static VersionedEntry fetchDraft(VersionedEntry versionedEntry) {
		return getService().fetchDraft(versionedEntry);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion fetchLatestVersion(
				VersionedEntry versionedEntry) {

		return getService().fetchLatestVersion(versionedEntry);
	}

	public static VersionedEntry fetchPublished(long primaryKey) {
		return getService().fetchPublished(primaryKey);
	}

	public static VersionedEntry fetchPublished(VersionedEntry versionedEntry) {
		return getService().fetchPublished(versionedEntry);
	}

	public static VersionedEntry fetchVersionedEntry(long versionedEntryId) {
		return getService().fetchVersionedEntry(versionedEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static VersionedEntry getDraft(long primaryKey)
		throws PortalException {

		return getService().getDraft(primaryKey);
	}

	public static VersionedEntry getDraft(VersionedEntry versionedEntry)
		throws PortalException {

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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion getVersion(
					VersionedEntry versionedEntry, int version)
				throws PortalException {

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
	public static List<VersionedEntry> getVersionedEntries(int start, int end) {
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
	public static VersionedEntry getVersionedEntry(long versionedEntryId)
		throws PortalException {

		return getService().getVersionedEntry(versionedEntryId);
	}

	public static List
		<com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion> getVersions(VersionedEntry versionedEntry) {

		return getService().getVersions(versionedEntry);
	}

	public static VersionedEntry publishDraft(
			VersionedEntry draftVersionedEntry)
		throws PortalException {

		return getService().publishDraft(draftVersionedEntry);
	}

	public static void registerListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<VersionedEntry,
			 com.liferay.portal.tools.service.builder.test.model.
				 VersionedEntryVersion> versionServiceListener) {

		getService().registerListener(versionServiceListener);
	}

	public static void unregisterListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<VersionedEntry,
			 com.liferay.portal.tools.service.builder.test.model.
				 VersionedEntryVersion> versionServiceListener) {

		getService().unregisterListener(versionServiceListener);
	}

	public static VersionedEntry updateDraft(VersionedEntry draftVersionedEntry)
		throws PortalException {

		return getService().updateDraft(draftVersionedEntry);
	}

	/**
	 * Updates the versioned entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect VersionedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param draftVersionedEntry the versioned entry
	 * @return the versioned entry that was updated
	 */
	public static VersionedEntry updateVersionedEntry(
			VersionedEntry draftVersionedEntry)
		throws PortalException {

		return getService().updateVersionedEntry(draftVersionedEntry);
	}

	public static VersionedEntryLocalService getService() {
		return _service;
	}

	public static void setService(VersionedEntryLocalService service) {
		_service = service;
	}

	private static volatile VersionedEntryLocalService _service;

}