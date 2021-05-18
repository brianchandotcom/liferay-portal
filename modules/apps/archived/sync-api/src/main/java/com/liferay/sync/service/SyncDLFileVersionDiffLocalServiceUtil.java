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

package com.liferay.sync.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.sync.model.SyncDLFileVersionDiff;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for SyncDLFileVersionDiff. This utility wraps
 * <code>com.liferay.sync.service.impl.SyncDLFileVersionDiffLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLFileVersionDiffLocalService
 * @generated
 */
public class SyncDLFileVersionDiffLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.sync.service.impl.SyncDLFileVersionDiffLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SyncDLFileVersionDiff addSyncDLFileVersionDiff(
			long fileEntryId, long sourceFileVersionId,
			long targetFileVersionId, java.io.File file)
		throws PortalException {

		return getService().addSyncDLFileVersionDiff(
			fileEntryId, sourceFileVersionId, targetFileVersionId, file);
	}

	/**
	 * Adds the sync dl file version diff to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SyncDLFileVersionDiffLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param syncDLFileVersionDiff the sync dl file version diff
	 * @return the sync dl file version diff that was added
	 */
	public static SyncDLFileVersionDiff addSyncDLFileVersionDiff(
		SyncDLFileVersionDiff syncDLFileVersionDiff) {

		return getService().addSyncDLFileVersionDiff(syncDLFileVersionDiff);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new sync dl file version diff with the primary key. Does not add the sync dl file version diff to the database.
	 *
	 * @param syncDLFileVersionDiffId the primary key for the new sync dl file version diff
	 * @return the new sync dl file version diff
	 */
	public static SyncDLFileVersionDiff createSyncDLFileVersionDiff(
		long syncDLFileVersionDiffId) {

		return getService().createSyncDLFileVersionDiff(
			syncDLFileVersionDiffId);
	}

	public static void deleteExpiredSyncDLFileVersionDiffs()
		throws PortalException {

		getService().deleteExpiredSyncDLFileVersionDiffs();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the sync dl file version diff with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SyncDLFileVersionDiffLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync dl file version diff
	 * @return the sync dl file version diff that was removed
	 * @throws PortalException if a sync dl file version diff with the primary key could not be found
	 */
	public static SyncDLFileVersionDiff deleteSyncDLFileVersionDiff(
			long syncDLFileVersionDiffId)
		throws PortalException {

		return getService().deleteSyncDLFileVersionDiff(
			syncDLFileVersionDiffId);
	}

	/**
	 * Deletes the sync dl file version diff from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SyncDLFileVersionDiffLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param syncDLFileVersionDiff the sync dl file version diff
	 * @return the sync dl file version diff that was removed
	 * @throws PortalException
	 */
	public static SyncDLFileVersionDiff deleteSyncDLFileVersionDiff(
			SyncDLFileVersionDiff syncDLFileVersionDiff)
		throws PortalException {

		return getService().deleteSyncDLFileVersionDiff(syncDLFileVersionDiff);
	}

	public static void deleteSyncDLFileVersionDiffs(long fileEntryId)
		throws PortalException {

		getService().deleteSyncDLFileVersionDiffs(fileEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLFileVersionDiffModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLFileVersionDiffModelImpl</code>.
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

	public static SyncDLFileVersionDiff fetchSyncDLFileVersionDiff(
		long syncDLFileVersionDiffId) {

		return getService().fetchSyncDLFileVersionDiff(syncDLFileVersionDiffId);
	}

	public static SyncDLFileVersionDiff fetchSyncDLFileVersionDiff(
		long fileEntryId, long sourceFileVersionId, long targetFileVersionId) {

		return getService().fetchSyncDLFileVersionDiff(
			fileEntryId, sourceFileVersionId, targetFileVersionId);
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the sync dl file version diff with the primary key.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync dl file version diff
	 * @return the sync dl file version diff
	 * @throws PortalException if a sync dl file version diff with the primary key could not be found
	 */
	public static SyncDLFileVersionDiff getSyncDLFileVersionDiff(
			long syncDLFileVersionDiffId)
		throws PortalException {

		return getService().getSyncDLFileVersionDiff(syncDLFileVersionDiffId);
	}

	/**
	 * Returns a range of all the sync dl file version diffs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sync.model.impl.SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @return the range of sync dl file version diffs
	 */
	public static List<SyncDLFileVersionDiff> getSyncDLFileVersionDiffs(
		int start, int end) {

		return getService().getSyncDLFileVersionDiffs(start, end);
	}

	/**
	 * Returns the number of sync dl file version diffs.
	 *
	 * @return the number of sync dl file version diffs
	 */
	public static int getSyncDLFileVersionDiffsCount() {
		return getService().getSyncDLFileVersionDiffsCount();
	}

	public static void refreshExpirationDate(long syncDLFileVersionDiffId)
		throws PortalException {

		getService().refreshExpirationDate(syncDLFileVersionDiffId);
	}

	/**
	 * Updates the sync dl file version diff in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SyncDLFileVersionDiffLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param syncDLFileVersionDiff the sync dl file version diff
	 * @return the sync dl file version diff that was updated
	 */
	public static SyncDLFileVersionDiff updateSyncDLFileVersionDiff(
		SyncDLFileVersionDiff syncDLFileVersionDiff) {

		return getService().updateSyncDLFileVersionDiff(syncDLFileVersionDiff);
	}

	public static SyncDLFileVersionDiffLocalService getService() {
		return _service;
	}

	private static volatile SyncDLFileVersionDiffLocalService _service;

}