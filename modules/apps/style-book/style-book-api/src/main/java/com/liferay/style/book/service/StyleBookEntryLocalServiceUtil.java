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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for StyleBookEntry. This utility wraps
 * <code>com.liferay.style.book.service.impl.StyleBookEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryLocalService
 * @generated
 */
public class StyleBookEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.style.book.service.impl.StyleBookEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.style.book.model.StyleBookEntry addStyleBookEntry(
			long userId, long groupId, String name, String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addStyleBookEntry(
			userId, groupId, name, styleBookEntryKey, serviceContext);
	}

	public static com.liferay.style.book.model.StyleBookEntry addStyleBookEntry(
			long userId, long groupId, String frontendTokensValues, String name,
			String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addStyleBookEntry(
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
	public static com.liferay.style.book.model.StyleBookEntry addStyleBookEntry(
		com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return getService().addStyleBookEntry(styleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntry checkout(
			com.liferay.style.book.model.StyleBookEntry publishedStyleBookEntry,
			int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkout(publishedStyleBookEntry, version);
	}

	public static com.liferay.style.book.model.StyleBookEntry
			copyStyleBookEntry(
				long userId, long groupId, long styleBookEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyStyleBookEntry(
			userId, groupId, styleBookEntryId, serviceContext);
	}

	/**
	 * Creates a new style book entry. Does not add the style book entry to the database.
	 *
	 * @return the new style book entry
	 */
	public static com.liferay.style.book.model.StyleBookEntry create() {
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

	public static com.liferay.style.book.model.StyleBookEntry delete(
			com.liferay.style.book.model.StyleBookEntry publishedStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().delete(publishedStyleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntry deleteDraft(
			com.liferay.style.book.model.StyleBookEntry draftStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDraft(draftStyleBookEntry);
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
	public static com.liferay.style.book.model.StyleBookEntry
			deleteStyleBookEntry(long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteStyleBookEntry(styleBookEntryId);
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
	public static com.liferay.style.book.model.StyleBookEntry
			deleteStyleBookEntry(
				com.liferay.style.book.model.StyleBookEntry styleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteStyleBookEntry(styleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntryVersion
			deleteVersion(
				com.liferay.style.book.model.StyleBookEntryVersion
					styleBookEntryVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteVersion(styleBookEntryVersion);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookEntryModelImpl</code>.
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

	public static com.liferay.style.book.model.StyleBookEntry
		fetchDefaultStyleBookEntry(long groupId) {

		return getService().fetchDefaultStyleBookEntry(groupId);
	}

	public static com.liferay.style.book.model.StyleBookEntry fetchDraft(
		long primaryKey) {

		return getService().fetchDraft(primaryKey);
	}

	public static com.liferay.style.book.model.StyleBookEntry fetchDraft(
		com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return getService().fetchDraft(styleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntryVersion
		fetchLatestVersion(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return getService().fetchLatestVersion(styleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntry fetchPublished(
		long primaryKey) {

		return getService().fetchPublished(primaryKey);
	}

	public static com.liferay.style.book.model.StyleBookEntry fetchPublished(
		com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return getService().fetchPublished(styleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntry
		fetchStyleBookEntry(long styleBookEntryId) {

		return getService().fetchStyleBookEntry(styleBookEntryId);
	}

	public static com.liferay.style.book.model.StyleBookEntry
		fetchStyleBookEntry(long groupId, String styleBookEntryKey) {

		return getService().fetchStyleBookEntry(groupId, styleBookEntryKey);
	}

	public static String generateStyleBookEntryKey(long groupId, String name) {
		return getService().generateStyleBookEntryKey(groupId, name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.style.book.model.StyleBookEntry getDraft(
			long primaryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDraft(primaryKey);
	}

	public static com.liferay.style.book.model.StyleBookEntry getDraft(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDraft(styleBookEntry);
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
	public static java.util.List<com.liferay.style.book.model.StyleBookEntry>
		getStyleBookEntries(int start, int end) {

		return getService().getStyleBookEntries(start, end);
	}

	public static java.util.List<com.liferay.style.book.model.StyleBookEntry>
		getStyleBookEntries(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.style.book.model.StyleBookEntry>
					orderByComparator) {

		return getService().getStyleBookEntries(
			groupId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.style.book.model.StyleBookEntry>
		getStyleBookEntries(
			long groupId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.style.book.model.StyleBookEntry>
					orderByComparator) {

		return getService().getStyleBookEntries(
			groupId, name, start, end, orderByComparator);
	}

	/**
	 * Returns the number of style book entries.
	 *
	 * @return the number of style book entries
	 */
	public static int getStyleBookEntriesCount() {
		return getService().getStyleBookEntriesCount();
	}

	public static int getStyleBookEntriesCount(long groupId) {
		return getService().getStyleBookEntriesCount(groupId);
	}

	public static int getStyleBookEntriesCount(long groupId, String name) {
		return getService().getStyleBookEntriesCount(groupId, name);
	}

	/**
	 * Returns the style book entry with the primary key.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry
	 * @throws PortalException if a style book entry with the primary key could not be found
	 */
	public static com.liferay.style.book.model.StyleBookEntry getStyleBookEntry(
			long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getStyleBookEntry(styleBookEntryId);
	}

	public static com.liferay.style.book.model.StyleBookEntryVersion getVersion(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry,
			int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVersion(styleBookEntry, version);
	}

	public static java.util.List
		<com.liferay.style.book.model.StyleBookEntryVersion> getVersions(
			com.liferay.style.book.model.StyleBookEntry styleBookEntry) {

		return getService().getVersions(styleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntry publishDraft(
			com.liferay.style.book.model.StyleBookEntry draftStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().publishDraft(draftStyleBookEntry);
	}

	public static void registerListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.style.book.model.StyleBookEntry,
			 com.liferay.style.book.model.StyleBookEntryVersion>
				versionServiceListener) {

		getService().registerListener(versionServiceListener);
	}

	public static void unregisterListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.style.book.model.StyleBookEntry,
			 com.liferay.style.book.model.StyleBookEntryVersion>
				versionServiceListener) {

		getService().unregisterListener(versionServiceListener);
	}

	public static com.liferay.style.book.model.StyleBookEntry
			updateDefaultStyleBookEntry(
				long styleBookEntryId, boolean defaultStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDefaultStyleBookEntry(
			styleBookEntryId, defaultStyleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntry updateDraft(
			com.liferay.style.book.model.StyleBookEntry draftStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDraft(draftStyleBookEntry);
	}

	public static com.liferay.style.book.model.StyleBookEntry
			updateFrontendTokensValues(
				long styleBookEntryId, String frontendTokensValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFrontendTokensValues(
			styleBookEntryId, frontendTokensValues);
	}

	public static com.liferay.style.book.model.StyleBookEntry updateName(
			long styleBookEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateName(styleBookEntryId, name);
	}

	public static com.liferay.style.book.model.StyleBookEntry
			updatePreviewFileEntryId(
				long styleBookEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updatePreviewFileEntryId(
			styleBookEntryId, previewFileEntryId);
	}

	public static com.liferay.style.book.model.StyleBookEntry
			updateStyleBookEntry(
				long styleBookEntryId, String frontendTokensValues, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStyleBookEntry(
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
	public static com.liferay.style.book.model.StyleBookEntry
			updateStyleBookEntry(
				com.liferay.style.book.model.StyleBookEntry draftStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStyleBookEntry(draftStyleBookEntry);
	}

	public static StyleBookEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<StyleBookEntryLocalService, StyleBookEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			StyleBookEntryLocalService.class);

		ServiceTracker<StyleBookEntryLocalService, StyleBookEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<StyleBookEntryLocalService, StyleBookEntryLocalService>(
						bundle.getBundleContext(),
						StyleBookEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}