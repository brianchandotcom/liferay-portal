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

package com.liferay.style.book.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link StyleBookEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryLocalService
 * @generated
 */
public class StyleBookEntryLocalServiceWrapper
	implements ServiceWrapper<StyleBookEntryLocalService>,
			   StyleBookEntryLocalService {

	public StyleBookEntryLocalServiceWrapper(
		StyleBookEntryLocalService styleBookEntryLocalService) {

		_styleBookEntryLocalService = styleBookEntryLocalService;
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry addStyleBookEntry(
			long userId, long groupId, String name, String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.addStyleBookEntry(
			userId, groupId, name, styleBookEntryKey, serviceContext);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry addStyleBookEntry(
			long userId, long groupId, String frontendTokensValues, String name,
			String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.addStyleBookEntry(
			userId, groupId, frontendTokensValues, name, styleBookEntryKey,
			serviceContext);
	}

	/**
	 * Adds the style book entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookEntry the style book entry
	 * @return the style book entry that was added
	 */
	@Override
	public com.liferay.style.book.model.StyleBookEntry addStyleBookEntry(
		com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return _styleBookEntryLocalService.addStyleBookEntry(styleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry checkout(
			com.liferay.style.book.model.StyleBookEntry publishedStyleBookEntry,
			int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.checkout(
			publishedStyleBookEntry, version);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry copyStyleBookEntry(
			long userId, long groupId, long styleBookEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.copyStyleBookEntry(
			userId, groupId, styleBookEntryId, serviceContext);
	}

	/**
	 * Creates a new style book entry. Does not add the style book entry to the database.
	 *
	 * @return the new style book entry
	 */
	@Override
	public com.liferay.style.book.model.StyleBookEntry create() {
		return _styleBookEntryLocalService.create();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry delete(
			com.liferay.style.book.model.StyleBookEntry publishedStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.delete(publishedStyleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry deleteDraft(
			com.liferay.style.book.model.StyleBookEntry draftStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.deleteDraft(draftStyleBookEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the style book entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry that was removed
	 * @throws PortalException if a style book entry with the primary key could not be found
	 */
	@Override
	public com.liferay.style.book.model.StyleBookEntry deleteStyleBookEntry(
			long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.deleteStyleBookEntry(
			styleBookEntryId);
	}

	/**
	 * Deletes the style book entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookEntry the style book entry
	 * @return the style book entry that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.style.book.model.StyleBookEntry deleteStyleBookEntry(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.deleteStyleBookEntry(styleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntryVersion deleteVersion(
			com.liferay.style.book.model.StyleBookEntryVersion
				styleBookEntryVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.deleteVersion(styleBookEntryVersion);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _styleBookEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _styleBookEntryLocalService.dynamicQuery();
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

		return _styleBookEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookEntryModelImpl</code>.
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

		return _styleBookEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookEntryModelImpl</code>.
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

		return _styleBookEntryLocalService.dynamicQuery(
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

		return _styleBookEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _styleBookEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry
		fetchDefaultStyleBookEntry(long groupId) {

		return _styleBookEntryLocalService.fetchDefaultStyleBookEntry(groupId);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry fetchDraft(
		long primaryKey) {

		return _styleBookEntryLocalService.fetchDraft(primaryKey);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry fetchDraft(
		com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return _styleBookEntryLocalService.fetchDraft(styleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntryVersion
		fetchLatestVersion(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return _styleBookEntryLocalService.fetchLatestVersion(styleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry fetchPublished(
		long primaryKey) {

		return _styleBookEntryLocalService.fetchPublished(primaryKey);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry fetchPublished(
		com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return _styleBookEntryLocalService.fetchPublished(styleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry fetchStyleBookEntry(
		long styleBookEntryId) {

		return _styleBookEntryLocalService.fetchStyleBookEntry(
			styleBookEntryId);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry fetchStyleBookEntry(
		long groupId, String styleBookEntryKey) {

		return _styleBookEntryLocalService.fetchStyleBookEntry(
			groupId, styleBookEntryKey);
	}

	@Override
	public String generateStyleBookEntryKey(long groupId, String name) {
		return _styleBookEntryLocalService.generateStyleBookEntryKey(
			groupId, name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _styleBookEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry getDraft(long primaryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.getDraft(primaryKey);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry getDraft(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.getDraft(styleBookEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _styleBookEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _styleBookEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of style book entries
	 */
	@Override
	public java.util.List<com.liferay.style.book.model.StyleBookEntry>
		getStyleBookEntries(int start, int end) {

		return _styleBookEntryLocalService.getStyleBookEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.style.book.model.StyleBookEntry>
		getStyleBookEntries(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.style.book.model.StyleBookEntry>
					orderByComparator) {

		return _styleBookEntryLocalService.getStyleBookEntries(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.style.book.model.StyleBookEntry>
		getStyleBookEntries(
			long groupId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.style.book.model.StyleBookEntry>
					orderByComparator) {

		return _styleBookEntryLocalService.getStyleBookEntries(
			groupId, name, start, end, orderByComparator);
	}

	/**
	 * Returns the number of style book entries.
	 *
	 * @return the number of style book entries
	 */
	@Override
	public int getStyleBookEntriesCount() {
		return _styleBookEntryLocalService.getStyleBookEntriesCount();
	}

	@Override
	public int getStyleBookEntriesCount(long groupId) {
		return _styleBookEntryLocalService.getStyleBookEntriesCount(groupId);
	}

	@Override
	public int getStyleBookEntriesCount(long groupId, String name) {
		return _styleBookEntryLocalService.getStyleBookEntriesCount(
			groupId, name);
	}

	/**
	 * Returns the style book entry with the primary key.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry
	 * @throws PortalException if a style book entry with the primary key could not be found
	 */
	@Override
	public com.liferay.style.book.model.StyleBookEntry getStyleBookEntry(
			long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.getStyleBookEntry(styleBookEntryId);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntryVersion getVersion(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry,
			int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.getVersion(styleBookEntry, version);
	}

	@Override
	public java.util.List<com.liferay.style.book.model.StyleBookEntryVersion>
		getVersions(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return _styleBookEntryLocalService.getVersions(styleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry publishDraft(
			com.liferay.style.book.model.StyleBookEntry draftStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.publishDraft(draftStyleBookEntry);
	}

	@Override
	public void registerListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.style.book.model.StyleBookEntry,
			 com.liferay.style.book.model.StyleBookEntryVersion>
				versionServiceListener) {

		_styleBookEntryLocalService.registerListener(versionServiceListener);
	}

	@Override
	public void unregisterListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.style.book.model.StyleBookEntry,
			 com.liferay.style.book.model.StyleBookEntryVersion>
				versionServiceListener) {

		_styleBookEntryLocalService.unregisterListener(versionServiceListener);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry
			updateDefaultStyleBookEntry(
				long styleBookEntryId, boolean defaultStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.updateDefaultStyleBookEntry(
			styleBookEntryId, defaultStyleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry updateDraft(
			com.liferay.style.book.model.StyleBookEntry draftStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.updateDraft(draftStyleBookEntry);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry
			updateFrontendTokensValues(
				long styleBookEntryId, String frontendTokensValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.updateFrontendTokensValues(
			styleBookEntryId, frontendTokensValues);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry updateName(
			long styleBookEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.updateName(styleBookEntryId, name);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry updatePreviewFileEntryId(
			long styleBookEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.updatePreviewFileEntryId(
			styleBookEntryId, previewFileEntryId);
	}

	@Override
	public com.liferay.style.book.model.StyleBookEntry updateStyleBookEntry(
			long styleBookEntryId, String frontendTokensValues, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.updateStyleBookEntry(
			styleBookEntryId, frontendTokensValues, name);
	}

	/**
	 * Updates the style book entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookEntry the style book entry
	 * @return the style book entry that was updated
	 */
	@Override
	public com.liferay.style.book.model.StyleBookEntry updateStyleBookEntry(
			com.liferay.style.book.model.StyleBookEntry draftStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _styleBookEntryLocalService.updateStyleBookEntry(
			draftStyleBookEntry);
	}

	@Override
	public StyleBookEntryLocalService getWrappedService() {
		return _styleBookEntryLocalService;
	}

	@Override
	public void setWrappedService(
		StyleBookEntryLocalService styleBookEntryLocalService) {

		_styleBookEntryLocalService = styleBookEntryLocalService;
	}

	private StyleBookEntryLocalService _styleBookEntryLocalService;

}