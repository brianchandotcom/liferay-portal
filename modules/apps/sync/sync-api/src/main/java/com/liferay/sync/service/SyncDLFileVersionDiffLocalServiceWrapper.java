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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SyncDLFileVersionDiffLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLFileVersionDiffLocalService
 * @generated
 */
public class SyncDLFileVersionDiffLocalServiceWrapper
	implements ServiceWrapper<SyncDLFileVersionDiffLocalService>,
			   SyncDLFileVersionDiffLocalService {

	public SyncDLFileVersionDiffLocalServiceWrapper(
		SyncDLFileVersionDiffLocalService syncDLFileVersionDiffLocalService) {

		_syncDLFileVersionDiffLocalService = syncDLFileVersionDiffLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SyncDLFileVersionDiffLocalServiceUtil} to access the sync dl file version diff local service. Add custom service methods to <code>com.liferay.sync.service.impl.SyncDLFileVersionDiffLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
			addSyncDLFileVersionDiff(
				long fileEntryId, long sourceFileVersionId,
				long targetFileVersionId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLFileVersionDiffLocalService.addSyncDLFileVersionDiff(
			fileEntryId, sourceFileVersionId, targetFileVersionId, file);
	}

	/**
	 * Adds the sync dl file version diff to the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiff the sync dl file version diff
	 * @return the sync dl file version diff that was added
	 */
	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
		addSyncDLFileVersionDiff(
			com.liferay.sync.model.SyncDLFileVersionDiff
				syncDLFileVersionDiff) {

		return _syncDLFileVersionDiffLocalService.addSyncDLFileVersionDiff(
			syncDLFileVersionDiff);
	}

	/**
	 * Creates a new sync dl file version diff with the primary key. Does not add the sync dl file version diff to the database.
	 *
	 * @param syncDLFileVersionDiffId the primary key for the new sync dl file version diff
	 * @return the new sync dl file version diff
	 */
	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
		createSyncDLFileVersionDiff(long syncDLFileVersionDiffId) {

		return _syncDLFileVersionDiffLocalService.createSyncDLFileVersionDiff(
			syncDLFileVersionDiffId);
	}

	@Override
	public void deleteExpiredSyncDLFileVersionDiffs()
		throws com.liferay.portal.kernel.exception.PortalException {

		_syncDLFileVersionDiffLocalService.
			deleteExpiredSyncDLFileVersionDiffs();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLFileVersionDiffLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the sync dl file version diff with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync dl file version diff
	 * @return the sync dl file version diff that was removed
	 * @throws PortalException if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
			deleteSyncDLFileVersionDiff(long syncDLFileVersionDiffId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLFileVersionDiffLocalService.deleteSyncDLFileVersionDiff(
			syncDLFileVersionDiffId);
	}

	/**
	 * Deletes the sync dl file version diff from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiff the sync dl file version diff
	 * @return the sync dl file version diff that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
			deleteSyncDLFileVersionDiff(
				com.liferay.sync.model.SyncDLFileVersionDiff
					syncDLFileVersionDiff)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLFileVersionDiffLocalService.deleteSyncDLFileVersionDiff(
			syncDLFileVersionDiff);
	}

	@Override
	public void deleteSyncDLFileVersionDiffs(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_syncDLFileVersionDiffLocalService.deleteSyncDLFileVersionDiffs(
			fileEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _syncDLFileVersionDiffLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _syncDLFileVersionDiffLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _syncDLFileVersionDiffLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _syncDLFileVersionDiffLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _syncDLFileVersionDiffLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _syncDLFileVersionDiffLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
		fetchSyncDLFileVersionDiff(long syncDLFileVersionDiffId) {

		return _syncDLFileVersionDiffLocalService.fetchSyncDLFileVersionDiff(
			syncDLFileVersionDiffId);
	}

	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
		fetchSyncDLFileVersionDiff(
			long fileEntryId, long sourceFileVersionId,
			long targetFileVersionId) {

		return _syncDLFileVersionDiffLocalService.fetchSyncDLFileVersionDiff(
			fileEntryId, sourceFileVersionId, targetFileVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _syncDLFileVersionDiffLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _syncDLFileVersionDiffLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _syncDLFileVersionDiffLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLFileVersionDiffLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the sync dl file version diff with the primary key.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync dl file version diff
	 * @return the sync dl file version diff
	 * @throws PortalException if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
			getSyncDLFileVersionDiff(long syncDLFileVersionDiffId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _syncDLFileVersionDiffLocalService.getSyncDLFileVersionDiff(
			syncDLFileVersionDiffId);
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
	@Override
	public java.util.List<com.liferay.sync.model.SyncDLFileVersionDiff>
		getSyncDLFileVersionDiffs(int start, int end) {

		return _syncDLFileVersionDiffLocalService.getSyncDLFileVersionDiffs(
			start, end);
	}

	/**
	 * Returns the number of sync dl file version diffs.
	 *
	 * @return the number of sync dl file version diffs
	 */
	@Override
	public int getSyncDLFileVersionDiffsCount() {
		return _syncDLFileVersionDiffLocalService.
			getSyncDLFileVersionDiffsCount();
	}

	@Override
	public void refreshExpirationDate(long syncDLFileVersionDiffId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_syncDLFileVersionDiffLocalService.refreshExpirationDate(
			syncDLFileVersionDiffId);
	}

	/**
	 * Updates the sync dl file version diff in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiff the sync dl file version diff
	 * @return the sync dl file version diff that was updated
	 */
	@Override
	public com.liferay.sync.model.SyncDLFileVersionDiff
		updateSyncDLFileVersionDiff(
			com.liferay.sync.model.SyncDLFileVersionDiff
				syncDLFileVersionDiff) {

		return _syncDLFileVersionDiffLocalService.updateSyncDLFileVersionDiff(
			syncDLFileVersionDiff);
	}

	@Override
	public SyncDLFileVersionDiffLocalService getWrappedService() {
		return _syncDLFileVersionDiffLocalService;
	}

	@Override
	public void setWrappedService(
		SyncDLFileVersionDiffLocalService syncDLFileVersionDiffLocalService) {

		_syncDLFileVersionDiffLocalService = syncDLFileVersionDiffLocalService;
	}

	private SyncDLFileVersionDiffLocalService
		_syncDLFileVersionDiffLocalService;

}