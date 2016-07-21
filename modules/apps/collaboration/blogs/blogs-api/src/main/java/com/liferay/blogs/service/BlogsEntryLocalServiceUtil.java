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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for BlogsEntry. This utility wraps
 * {@link com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryLocalService
 * @see com.liferay.blogs.service.base.BlogsEntryLocalServiceBaseImpl
 * @see com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class BlogsEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.blogs.kernel.model.BlogsEntry addEntry(
		long userId, java.lang.String title, java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addEntry(userId, title, content, serviceContext);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry addEntry(
		long userId, java.lang.String title, java.lang.String content,
		java.util.Date displayDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntry(userId, title, content, displayDate, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addEntry(long, String,
	String, String, String, int, int, int, int, int, boolean,
	boolean, String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.blogs.kernel.model.BlogsEntry addEntry(
		long userId, java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntry(userId, title, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			smallImage, smallImageURL, smallImageFileName,
			smallImageInputStream, serviceContext);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry addEntry(
		long userId, java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntry(userId, title, subtitle, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector,
			smallImageImageSelector, serviceContext);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry addEntry(
		long userId, java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		java.util.Date displayDate, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntry(userId, title, subtitle, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector,
			smallImageImageSelector, serviceContext);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry deleteEntry(
		com.liferay.blogs.kernel.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteEntry(entry);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry fetchBlogsEntry(
		long entryId) {
		return getService().fetchBlogsEntry(entryId);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry fetchBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry getBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry getEntry(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntry(groupId, urlTitle);
	}

	/**
	* Moves the blogs entry to the recycle bin. Social activity counters for
	* this entry get disabled.
	*
	* @param userId the primary key of the user moving the blogs entry
	* @param entry the blogs entry to be moved
	* @return the moved blogs entry
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry moveEntryToTrash(
		long userId, com.liferay.blogs.kernel.model.BlogsEntry entry)
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
	public static com.liferay.blogs.kernel.model.BlogsEntry moveEntryToTrash(
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
	public static com.liferay.blogs.kernel.model.BlogsEntry restoreEntryFromTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().restoreEntryFromTrash(userId, entryId);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry updateBlogsEntry(
		com.liferay.blogs.kernel.model.BlogsEntry blogsEntry) {
		return getService().updateBlogsEntry(blogsEntry);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEntry(userId, entryId, title, content, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	String, String, String, String, int, int, int, int, int,
	boolean, boolean, String[], String, ImageSelector,
	ImageSelector, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.blogs.kernel.model.BlogsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		boolean smallImage, java.lang.String smallImageURL,
		java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEntry(userId, entryId, title, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			smallImage, smallImageURL, smallImageFileName,
			smallImageInputStream, serviceContext);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String subtitle, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEntry(userId, entryId, title, subtitle, description,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String subtitle, java.lang.String description,
		java.lang.String content, java.util.Date displayDate,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEntry(userId, entryId, title, subtitle, description,
			content, displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector,
			smallImageImageSelector, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, long,
	int, ServiceContext, Map)}
	*/
	@Deprecated
	public static com.liferay.blogs.kernel.model.BlogsEntry updateStatus(
		long userId, long entryId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateStatus(userId, entryId, status, serviceContext);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry updateStatus(
		long userId, long entryId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, entryId, status, serviceContext,
			workflowContext);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry[] getEntriesPrevAndNext(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntriesPrevAndNext(entryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addAttachmentsFolder(userId, groupId);
	}

	public static com.liferay.portal.kernel.repository.model.Folder fetchAttachmentsFolder(
		long userId, long groupId) {
		return getService().fetchAttachmentsFolder(userId, groupId);
	}

	public static int getCompanyEntriesCount(long companyId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getCompanyEntriesCount(companyId, displayDate,
			queryDefinition);
	}

	public static int getGroupEntriesCount(long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService().getGroupEntriesCount(groupId, queryDefinition);
	}

	public static int getGroupEntriesCount(long groupId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getGroupEntriesCount(groupId, displayDate, queryDefinition);
	}

	public static int getGroupUserEntriesCount(long groupId, long userId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getGroupUserEntriesCount(groupId, userId, displayDate,
			queryDefinition);
	}

	public static int getOrganizationEntriesCount(long organizationId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getOrganizationEntriesCount(organizationId, displayDate,
			queryDefinition);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getBlogsEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsEntry> orderByComparator) {
		return getService()
				   .getBlogsEntriesByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getCompanyEntries(
		long companyId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getCompanyEntries(companyId, displayDate, queryDefinition);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getGroupEntries(
		long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService().getGroupEntries(groupId, queryDefinition);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getGroupEntries(groupId, displayDate, queryDefinition);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getGroupUserEntries(groupId, userId, displayDate,
			queryDefinition);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getGroupsEntries(companyId, groupId, displayDate,
			queryDefinition);
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getNoAssetEntries() {
		return getService().getNoAssetEntries();
	}

	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getOrganizationEntries(
		long organizationId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return getService()
				   .getOrganizationEntries(organizationId, displayDate,
			queryDefinition);
	}

	public static long addOriginalImageFileEntry(long userId, long groupId,
		long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOriginalImageFileEntry(userId, groupId, entryId,
			imageSelector);
	}

	public static void addCoverImage(long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addCoverImage(entryId, imageSelector);
	}

	public static void addEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addEntryResources(entry, addGroupPermissions, addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addEntryResources(entry, modelPermissions);
	}

	public static void addEntryResources(long entryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addEntryResources(entryId, addGroupPermissions, addGuestPermissions);
	}

	public static void addEntryResources(long entryId,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addEntryResources(entryId, modelPermissions);
	}

	public static void addSmallImage(long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addSmallImage(entryId, imageSelector);
	}

	public static void checkEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkEntries();
	}

	public static void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteEntries(groupId);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteEntry(entryId);
	}

	public static void moveEntriesToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().moveEntriesToTrash(groupId, userId);
	}

	public static void subscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().subscribe(userId, groupId);
	}

	public static void unsubscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsubscribe(userId, groupId);
	}

	public static void updateAsset(long userId,
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateAsset(userId, entry, assetCategoryIds, assetTagNames,
			assetLinkEntryIds, priority);
	}

	public static void updateEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateEntryResources(entry, modelPermissions);
	}

	public static void updateEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateEntryResources(entry, groupPermissions, guestPermissions);
	}

	public static BlogsEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BlogsEntryLocalService, BlogsEntryLocalService> _serviceTracker =
		ServiceTrackerFactory.open(BlogsEntryLocalService.class);
}