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

package com.liferay.blogs.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for BlogsEntry. This utility wraps
 * <code>com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryLocalService
 * @generated
 */
public class BlogsEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlogsEntryLocalServiceUtil} to access the blogs entry local service. Add custom service methods to <code>com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.portal.kernel.repository.model.FileEntry
			addAttachmentFileEntry(
				com.liferay.blogs.model.BlogsEntry blogsEntry, long userId,
				String fileName, String mimeType, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAttachmentFileEntry(
			blogsEntry, userId, fileName, mimeType, is);
	}

	public static com.liferay.portal.kernel.repository.model.Folder
			addAttachmentsFolder(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAttachmentsFolder(userId, groupId);
	}

	/**
	 * Adds the blogs entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param blogsEntry the blogs entry
	 * @return the blogs entry that was added
	 */
	public static com.liferay.blogs.model.BlogsEntry addBlogsEntry(
		com.liferay.blogs.model.BlogsEntry blogsEntry) {

		return getService().addBlogsEntry(blogsEntry);
	}

	public static void addCoverImage(
			long entryId,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addCoverImage(entryId, imageSelector);
	}

	public static com.liferay.blogs.model.BlogsEntry addEntry(
			long userId, String title, String content,
			java.util.Date displayDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(
			userId, title, content, displayDate, serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry addEntry(
			long userId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(userId, title, content, serviceContext);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #addEntry(long,
	 String, String, String, String, int, int, int, int, int,
	 boolean, boolean, String[], String, ImageSelector,
	 ImageSelector, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.blogs.model.BlogsEntry addEntry(
			long userId, String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			java.io.InputStream smallImageInputStream,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(
			userId, title, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry addEntry(
			long userId, String title, String subtitle, String description,
			String content, java.util.Date displayDate, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				coverImageImageSelector,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				smallImageImageSelector,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(
			userId, title, subtitle, description, content, displayDate,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry addEntry(
			long userId, String title, String subtitle, String description,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				coverImageImageSelector,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				smallImageImageSelector,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(
			userId, title, subtitle, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry addEntry(
			long userId, String title, String subtitle, String urlTitle,
			String description, String content, java.util.Date displayDate,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				coverImageImageSelector,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				smallImageImageSelector,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(
			userId, title, subtitle, urlTitle, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry addEntry(
			long userId, String title, String subtitle, String urlTitle,
			String description, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				coverImageImageSelector,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				smallImageImageSelector,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEntry(
			userId, title, subtitle, urlTitle, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	public static void addEntryResources(
			com.liferay.blogs.model.BlogsEntry entry,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addEntryResources(
			entry, addGroupPermissions, addGuestPermissions);
	}

	public static void addEntryResources(
			com.liferay.blogs.model.BlogsEntry entry,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addEntryResources(entry, modelPermissions);
	}

	public static void addEntryResources(
			long entryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addEntryResources(
			entryId, addGroupPermissions, addGuestPermissions);
	}

	public static void addEntryResources(
			long entryId,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addEntryResources(entryId, modelPermissions);
	}

	public static long addOriginalImageFileEntry(
			long userId, long groupId, long entryId,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addOriginalImageFileEntry(
			userId, groupId, entryId, imageSelector);
	}

	public static void addSmallImage(
			long entryId,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addSmallImage(entryId, imageSelector);
	}

	public static void checkEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkEntries();
	}

	/**
	 * Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	 *
	 * @param entryId the primary key for the new blogs entry
	 * @return the new blogs entry
	 */
	public static com.liferay.blogs.model.BlogsEntry createBlogsEntry(
		long entryId) {

		return getService().createBlogsEntry(entryId);
	}

	/**
	 * Deletes the blogs entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param blogsEntry the blogs entry
	 * @return the blogs entry that was removed
	 */
	public static com.liferay.blogs.model.BlogsEntry deleteBlogsEntry(
		com.liferay.blogs.model.BlogsEntry blogsEntry) {

		return getService().deleteBlogsEntry(blogsEntry);
	}

	/**
	 * Deletes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the blogs entry
	 * @return the blogs entry that was removed
	 * @throws PortalException if a blogs entry with the primary key could not be found
	 */
	public static com.liferay.blogs.model.BlogsEntry deleteBlogsEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteBlogsEntry(entryId);
	}

	public static void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteEntries(groupId);
	}

	public static com.liferay.blogs.model.BlogsEntry deleteEntry(
			com.liferay.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteEntry(entry);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteEntry(entryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsEntryModelImpl</code>.
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

	public static com.liferay.portal.kernel.repository.model.Folder
		fetchAttachmentsFolder(long userId, long groupId) {

		return getService().fetchAttachmentsFolder(userId, groupId);
	}

	public static com.liferay.blogs.model.BlogsEntry fetchBlogsEntry(
		long entryId) {

		return getService().fetchBlogsEntry(entryId);
	}

	/**
	 * Returns the blogs entry matching the UUID and group.
	 *
	 * @param uuid the blogs entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	public static com.liferay.blogs.model.BlogsEntry
		fetchBlogsEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.blogs.model.BlogsEntry fetchEntry(
		long groupId, String urlTitle) {

		return getService().fetchEntry(groupId, urlTitle);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the blogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of blogs entries
	 */
	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getBlogsEntries(int start, int end) {

		return getService().getBlogsEntries(start, end);
	}

	/**
	 * Returns all the blogs entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the blogs entries
	 * @param companyId the primary key of the company
	 * @return the matching blogs entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getBlogsEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getBlogsEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of blogs entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the blogs entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching blogs entries, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getBlogsEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.blogs.model.BlogsEntry> orderByComparator) {

		return getService().getBlogsEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of blogs entries.
	 *
	 * @return the number of blogs entries
	 */
	public static int getBlogsEntriesCount() {
		return getService().getBlogsEntriesCount();
	}

	/**
	 * Returns the blogs entry with the primary key.
	 *
	 * @param entryId the primary key of the blogs entry
	 * @return the blogs entry
	 * @throws PortalException if a blogs entry with the primary key could not be found
	 */
	public static com.liferay.blogs.model.BlogsEntry getBlogsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBlogsEntry(entryId);
	}

	/**
	 * Returns the blogs entry matching the UUID and group.
	 *
	 * @param uuid the blogs entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching blogs entry
	 * @throws PortalException if a matching blogs entry could not be found
	 */
	public static com.liferay.blogs.model.BlogsEntry
			getBlogsEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getCompanyEntries(
			long companyId, java.util.Date displayDate,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getCompanyEntries(
			companyId, displayDate, queryDefinition);
	}

	public static int getCompanyEntriesCount(
		long companyId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getCompanyEntriesCount(
			companyId, displayDate, queryDefinition);
	}

	public static com.liferay.blogs.model.BlogsEntry[] getEntriesPrevAndNext(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEntriesPrevAndNext(entryId);
	}

	public static com.liferay.blogs.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEntry(entryId);
	}

	public static com.liferay.blogs.model.BlogsEntry getEntry(
			long groupId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEntry(groupId, urlTitle);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getGroupEntries(
			long groupId, java.util.Date displayDate,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getGroupEntries(
			groupId, displayDate, queryDefinition);
	}

	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getGroupEntries(
			long groupId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getGroupEntries(groupId, queryDefinition);
	}

	public static int getGroupEntriesCount(
		long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getGroupEntriesCount(
			groupId, displayDate, queryDefinition);
	}

	public static int getGroupEntriesCount(
		long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getGroupEntriesCount(groupId, queryDefinition);
	}

	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getGroupsEntries(
			long companyId, long groupId, java.util.Date displayDate,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getGroupsEntries(
			companyId, groupId, displayDate, queryDefinition);
	}

	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getGroupUserEntries(
			long groupId, long userId, java.util.Date displayDate,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getGroupUserEntries(
			groupId, userId, displayDate, queryDefinition);
	}

	public static int getGroupUserEntriesCount(
		long groupId, long userId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getGroupUserEntriesCount(
			groupId, userId, displayDate, queryDefinition);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getNoAssetEntries() {

		return getService().getNoAssetEntries();
	}

	public static java.util.List<com.liferay.blogs.model.BlogsEntry>
		getOrganizationEntries(
			long organizationId, java.util.Date displayDate,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getOrganizationEntries(
			organizationId, displayDate, queryDefinition);
	}

	public static int getOrganizationEntriesCount(
		long organizationId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.blogs.model.BlogsEntry> queryDefinition) {

		return getService().getOrganizationEntriesCount(
			organizationId, displayDate, queryDefinition);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static String getUniqueUrlTitle(
		com.liferay.blogs.model.BlogsEntry entry) {

		return getService().getUniqueUrlTitle(entry);
	}

	public static void moveEntriesToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().moveEntriesToTrash(groupId, userId);
	}

	/**
	 * Moves the blogs entry to the recycle bin. Social activity counters for
	 * this entry get disabled.
	 *
	 * @param userId the primary key of the user moving the blogs entry
	 * @param entry the blogs entry to be moved
	 * @return the moved blogs entry
	 */
	public static com.liferay.blogs.model.BlogsEntry moveEntryToTrash(
			long userId, com.liferay.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveEntryToTrash(userId, entry);
	}

	/**
	 * Moves the blogs entry with the ID to the recycle bin.
	 *
	 * @param userId the primary key of the user moving the blogs entry
	 * @param entryId the primary key of the blogs entry to be moved
	 * @return the moved blogs entry
	 */
	public static com.liferay.blogs.model.BlogsEntry moveEntryToTrash(
			long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveEntryToTrash(userId, entryId);
	}

	/**
	 * Restores the blogs entry with the ID from the recycle bin. Social
	 * activity counters for this entry get activated.
	 *
	 * @param userId the primary key of the user restoring the blogs entry
	 * @param entryId the primary key of the blogs entry to be restored
	 * @return the restored blogs entry from the recycle bin
	 */
	public static com.liferay.blogs.model.BlogsEntry restoreEntryFromTrash(
			long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().restoreEntryFromTrash(userId, entryId);
	}

	public static void subscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribe(userId, groupId);
	}

	public static void unsubscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribe(userId, groupId);
	}

	public static void updateAsset(
			long userId, com.liferay.blogs.model.BlogsEntry entry,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds, Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateAsset(
			userId, entry, assetCategoryIds, assetTagNames, assetLinkEntryIds,
			priority);
	}

	/**
	 * Updates the blogs entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param blogsEntry the blogs entry
	 * @return the blogs entry that was updated
	 */
	public static com.liferay.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.blogs.model.BlogsEntry blogsEntry) {

		return getService().updateBlogsEntry(blogsEntry);
	}

	public static com.liferay.blogs.model.BlogsEntry updateEntry(
			long userId, long entryId, String title, String content,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(
			userId, entryId, title, content, serviceContext);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #updateEntry(long,
	 long, String, String, String, String, int, int, int, int,
	 int, boolean, boolean, String[], String, ImageSelector,
	 ImageSelector, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.blogs.model.BlogsEntry updateEntry(
			long userId, long entryId, String title, String description,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, boolean smallImage, String smallImageURL,
			String smallImageFileName,
			java.io.InputStream smallImageInputStream,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(
			userId, entryId, title, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String description, String content, java.util.Date displayDate,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				coverImageImageSelector,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				smallImageImageSelector,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(
			userId, entryId, title, subtitle, description, content, displayDate,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String description, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				coverImageImageSelector,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				smallImageImageSelector,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(
			userId, entryId, title, subtitle, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String urlTitle, String description, String content,
			java.util.Date displayDate, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				coverImageImageSelector,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				smallImageImageSelector,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(
			userId, entryId, title, subtitle, urlTitle, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String urlTitle, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				coverImageImageSelector,
			com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector
				smallImageImageSelector,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEntry(
			userId, entryId, title, subtitle, urlTitle, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	public static void updateEntryResources(
			com.liferay.blogs.model.BlogsEntry entry,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateEntryResources(entry, modelPermissions);
	}

	public static void updateEntryResources(
			com.liferay.blogs.model.BlogsEntry entry, String[] groupPermissions,
			String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateEntryResources(
			entry, groupPermissions, guestPermissions);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #updateStatus(long,
	 long, int, ServiceContext, Map)}
	 */
	@Deprecated
	public static com.liferay.blogs.model.BlogsEntry updateStatus(
			long userId, long entryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, entryId, status, serviceContext);
	}

	public static com.liferay.blogs.model.BlogsEntry updateStatus(
			long userId, long entryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, entryId, status, serviceContext, workflowContext);
	}

	public static BlogsEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BlogsEntryLocalService, BlogsEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(BlogsEntryLocalService.class);

		ServiceTracker<BlogsEntryLocalService, BlogsEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<BlogsEntryLocalService, BlogsEntryLocalService>(
						bundle.getBundleContext(), BlogsEntryLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}