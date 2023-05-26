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

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link VersionedEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryLocalService
 * @generated
 */
public class VersionedEntryLocalServiceWrapper
	implements ServiceWrapper<VersionedEntryLocalService>,
			   VersionedEntryLocalService {

	public VersionedEntryLocalServiceWrapper() {
		this(null);
	}

	public VersionedEntryLocalServiceWrapper(
		VersionedEntryLocalService versionedEntryLocalService) {

		_versionedEntryLocalService = versionedEntryLocalService;
	}

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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
		addVersionedEntry(
			com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				versionedEntry) {

		return _versionedEntryLocalService.addVersionedEntry(versionedEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			checkout(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry publishedVersionedEntry,
				int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.checkout(
			publishedVersionedEntry, version);
	}

	/**
	 * Creates a new versioned entry. Does not add the versioned entry to the database.
	 *
	 * @return the new versioned entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
		create() {

		return _versionedEntryLocalService.create();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			delete(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry publishedVersionedEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.delete(publishedVersionedEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			deleteDraft(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry draftVersionedEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.deleteDraft(draftVersionedEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion deleteVersion(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntryVersion versionedEntryVersion)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.deleteVersion(versionedEntryVersion);
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			deleteVersionedEntry(long versionedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.deleteVersionedEntry(
			versionedEntryId);
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
		deleteVersionedEntry(
			com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				versionedEntry) {

		return _versionedEntryLocalService.deleteVersionedEntry(versionedEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _versionedEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _versionedEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _versionedEntryLocalService.dynamicQuery();
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

		return _versionedEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _versionedEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _versionedEntryLocalService.dynamicQuery(
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

		return _versionedEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _versionedEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
		fetchDraft(long primaryKey) {

		return _versionedEntryLocalService.fetchDraft(primaryKey);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
		fetchDraft(
			com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				versionedEntry) {

		return _versionedEntryLocalService.fetchDraft(versionedEntry);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion fetchLatestVersion(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry) {

		return _versionedEntryLocalService.fetchLatestVersion(versionedEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
		fetchPublished(long primaryKey) {

		return _versionedEntryLocalService.fetchPublished(primaryKey);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
		fetchPublished(
			com.liferay.portal.tools.service.builder.test.model.VersionedEntry
				versionedEntry) {

		return _versionedEntryLocalService.fetchPublished(versionedEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
		fetchVersionedEntry(long versionedEntryId) {

		return _versionedEntryLocalService.fetchVersionedEntry(
			versionedEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _versionedEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			getDraft(long primaryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.getDraft(primaryKey);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			getDraft(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.getDraft(versionedEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _versionedEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _versionedEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion getVersion(
					com.liferay.portal.tools.service.builder.test.model.
						VersionedEntry versionedEntry,
					int version)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.getVersion(versionedEntry, version);
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
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.VersionedEntry>
			getVersionedEntries(int start, int end) {

		return _versionedEntryLocalService.getVersionedEntries(start, end);
	}

	/**
	 * Returns the number of versioned entries.
	 *
	 * @return the number of versioned entries
	 */
	@Override
	public int getVersionedEntriesCount() {
		return _versionedEntryLocalService.getVersionedEntriesCount();
	}

	/**
	 * Returns the versioned entry with the primary key.
	 *
	 * @param versionedEntryId the primary key of the versioned entry
	 * @return the versioned entry
	 * @throws PortalException if a versioned entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			getVersionedEntry(long versionedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.getVersionedEntry(versionedEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			VersionedEntryVersion> getVersions(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry versionedEntry) {

		return _versionedEntryLocalService.getVersions(versionedEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			publishDraft(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry draftVersionedEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.publishDraft(draftVersionedEntry);
	}

	@Override
	public void registerListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.portal.tools.service.builder.test.model.VersionedEntry,
			 com.liferay.portal.tools.service.builder.test.model.
				 VersionedEntryVersion> versionServiceListener) {

		_versionedEntryLocalService.registerListener(versionServiceListener);
	}

	@Override
	public void unregisterListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.portal.tools.service.builder.test.model.VersionedEntry,
			 com.liferay.portal.tools.service.builder.test.model.
				 VersionedEntryVersion> versionServiceListener) {

		_versionedEntryLocalService.unregisterListener(versionServiceListener);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			updateDraft(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry draftVersionedEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.updateDraft(draftVersionedEntry);
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.VersionedEntry
			updateVersionedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					VersionedEntry draftVersionedEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _versionedEntryLocalService.updateVersionedEntry(
			draftVersionedEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _versionedEntryLocalService.getBasePersistence();
	}

	@Override
	public VersionedEntryLocalService getWrappedService() {
		return _versionedEntryLocalService;
	}

	@Override
	public void setWrappedService(
		VersionedEntryLocalService versionedEntryLocalService) {

		_versionedEntryLocalService = versionedEntryLocalService;
	}

	private VersionedEntryLocalService _versionedEntryLocalService;

}