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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LVEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalService
 * @generated
 */
@ProviderType
public class LVEntryLocalServiceWrapper implements LVEntryLocalService,
	ServiceWrapper<LVEntryLocalService> {
	public LVEntryLocalServiceWrapper(LVEntryLocalService lvEntryLocalService) {
		_lvEntryLocalService = lvEntryLocalService;
	}

	/**
	* Adds the lv entry to the database. Also notifies the appropriate model listeners.
	*
	* @param lvEntry the lv entry
	* @return the lv entry that was added
	*/
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry addLVEntry(
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry) {
		return _lvEntryLocalService.addLVEntry(lvEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry checkout(
		com.liferay.portal.tools.service.builder.test.model.LVEntry publishedLVEntry,
		int version) throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.checkout(publishedLVEntry, version);
	}

	/**
	* Creates a new lv entry. Does not add the lv entry to the database.
	*
	* @return the new lv entry
	*/
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry create() {
		return _lvEntryLocalService.create();
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry delete(
		com.liferay.portal.tools.service.builder.test.model.LVEntry publishedLVEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.delete(publishedLVEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry deleteDraft(
		com.liferay.portal.tools.service.builder.test.model.LVEntry draftLVEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.deleteDraft(draftLVEntry);
	}

	/**
	* Deletes the lv entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lvEntryId the primary key of the lv entry
	* @return the lv entry that was removed
	* @throws PortalException if a lv entry with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry deleteLVEntry(
		long lvEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.deleteLVEntry(lvEntryId);
	}

	/**
	* Deletes the lv entry from the database. Also notifies the appropriate model listeners.
	*
	* @param lvEntry the lv entry
	* @return the lv entry that was removed
	*/
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry deleteLVEntry(
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry) {
		return _lvEntryLocalService.deleteLVEntry(lvEntry);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntryVersion deleteVersion(
		com.liferay.portal.tools.service.builder.test.model.LVEntryVersion lvEntryVersion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.deleteVersion(lvEntryVersion);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _lvEntryLocalService.dynamicQuery();
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
		return _lvEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.tools.service.builder.test.model.impl.LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _lvEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.tools.service.builder.test.model.impl.LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _lvEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _lvEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _lvEntryLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry fetchDraft(
		long primaryKey) {
		return _lvEntryLocalService.fetchDraft(primaryKey);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry fetchDraft(
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry) {
		return _lvEntryLocalService.fetchDraft(lvEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntryVersion fetchLatestVersion(
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry) {
		return _lvEntryLocalService.fetchLatestVersion(lvEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry fetchLVEntry(
		long lvEntryId) {
		return _lvEntryLocalService.fetchLVEntry(lvEntryId);
	}

	/**
	* Returns the lv entry matching the UUID and group.
	*
	* @param uuid the lv entry's UUID
	* @param groupId the primary key of the group
	* @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	*/
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry fetchLVEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return _lvEntryLocalService.fetchLVEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization fetchLVEntryLocalization(
		long lvEntryId, String languageId) {
		return _lvEntryLocalService.fetchLVEntryLocalization(lvEntryId,
			languageId);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion fetchLVEntryLocalizationVersion(
		long lvEntryId, String languageId, int version) {
		return _lvEntryLocalService.fetchLVEntryLocalizationVersion(lvEntryId,
			languageId, version);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry fetchPublished(
		long primaryKey) {
		return _lvEntryLocalService.fetchPublished(primaryKey);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry fetchPublished(
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry) {
		return _lvEntryLocalService.fetchPublished(lvEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _lvEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry getDraft(
		long primaryKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.getDraft(primaryKey);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry getDraft(
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.getDraft(lvEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _lvEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns a range of all the lv entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.tools.service.builder.test.model.impl.LVEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entries
	* @param end the upper bound of the range of lv entries (not inclusive)
	* @return the range of lv entries
	*/
	@Override
	public java.util.List<com.liferay.portal.tools.service.builder.test.model.LVEntry> getLVEntries(
		int start, int end) {
		return _lvEntryLocalService.getLVEntries(start, end);
	}

	/**
	* Returns the number of lv entries.
	*
	* @return the number of lv entries
	*/
	@Override
	public int getLVEntriesCount() {
		return _lvEntryLocalService.getLVEntriesCount();
	}

	/**
	* Returns the lv entry with the primary key.
	*
	* @param lvEntryId the primary key of the lv entry
	* @return the lv entry
	* @throws PortalException if a lv entry with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry getLVEntry(
		long lvEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.getLVEntry(lvEntryId);
	}

	/**
	* Returns the lv entry matching the UUID and group.
	*
	* @param uuid the lv entry's UUID
	* @param groupId the primary key of the group
	* @return the matching lv entry
	* @throws PortalException if a matching lv entry could not be found
	*/
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry getLVEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.getLVEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization getLVEntryLocalization(
		long lvEntryId, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.getLVEntryLocalization(lvEntryId, languageId);
	}

	@Override
	public java.util.List<com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization> getLVEntryLocalizations(
		long lvEntryId) {
		return _lvEntryLocalService.getLVEntryLocalizations(lvEntryId);
	}

	@Override
	public java.util.List<com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion> getLVEntryLocalizationVersions(
		long lvEntryId) {
		return _lvEntryLocalService.getLVEntryLocalizationVersions(lvEntryId);
	}

	@Override
	public java.util.List<com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion> getLVEntryLocalizationVersions(
		long lvEntryId, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.getLVEntryLocalizationVersions(lvEntryId,
			languageId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _lvEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntryVersion getVersion(
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry,
		int version) throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.getVersion(lvEntry, version);
	}

	@Override
	public java.util.List<com.liferay.portal.tools.service.builder.test.model.LVEntryVersion> getVersions(
		com.liferay.portal.tools.service.builder.test.model.LVEntry lvEntry) {
		return _lvEntryLocalService.getVersions(lvEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry publishDraft(
		com.liferay.portal.tools.service.builder.test.model.LVEntry draftLVEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.publishDraft(draftLVEntry);
	}

	@Override
	public void registerListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener<com.liferay.portal.tools.service.builder.test.model.LVEntry, com.liferay.portal.tools.service.builder.test.model.LVEntryVersion> versionServiceListener) {
		_lvEntryLocalService.registerListener(versionServiceListener);
	}

	@Override
	public void unregisterListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener<com.liferay.portal.tools.service.builder.test.model.LVEntry, com.liferay.portal.tools.service.builder.test.model.LVEntryVersion> versionServiceListener) {
		_lvEntryLocalService.unregisterListener(versionServiceListener);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry updateDraft(
		com.liferay.portal.tools.service.builder.test.model.LVEntry draftLVEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.updateDraft(draftLVEntry);
	}

	/**
	* Updates the lv entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param lvEntry the lv entry
	* @return the lv entry that was updated
	*/
	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntry updateLVEntry(
		com.liferay.portal.tools.service.builder.test.model.LVEntry draftLVEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.updateLVEntry(draftLVEntry);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization updateLVEntryLocalization(
		com.liferay.portal.tools.service.builder.test.model.LVEntry draftLVEntry,
		String languageId, String title, String content)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.updateLVEntryLocalization(draftLVEntry,
			languageId, title, content);
	}

	@Override
	public java.util.List<com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization> updateLVEntryLocalizations(
		com.liferay.portal.tools.service.builder.test.model.LVEntry draftLVEntry,
		java.util.Map<String, String> titleMap,
		java.util.Map<String, String> contentMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _lvEntryLocalService.updateLVEntryLocalizations(draftLVEntry,
			titleMap, contentMap);
	}

	@Override
	public LVEntryLocalService getWrappedService() {
		return _lvEntryLocalService;
	}

	@Override
	public void setWrappedService(LVEntryLocalService lvEntryLocalService) {
		_lvEntryLocalService = lvEntryLocalService;
	}

	private LVEntryLocalService _lvEntryLocalService;
}