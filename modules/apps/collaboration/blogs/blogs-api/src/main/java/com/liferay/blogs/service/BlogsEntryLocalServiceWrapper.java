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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BlogsEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryLocalService
 * @generated
 */
@ProviderType
public class BlogsEntryLocalServiceWrapper implements BlogsEntryLocalService,
	ServiceWrapper<BlogsEntryLocalService> {
	public BlogsEntryLocalServiceWrapper(
		BlogsEntryLocalService blogsEntryLocalService) {
		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry addEntry(long userId,
		java.lang.String title, java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addEntry(userId, title, content,
			serviceContext);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry addEntry(long userId,
		java.lang.String title, java.lang.String content,
		java.util.Date displayDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addEntry(userId, title, content,
			displayDate, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addEntry(long, String,
	String, String, String, int, int, int, int, int, boolean,
	boolean, String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry addEntry(long userId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addEntry(userId, title, description,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, smallImage, smallImageURL,
			smallImageFileName, smallImageInputStream, serviceContext);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry addEntry(long userId,
		java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addEntry(userId, title, subtitle,
			description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry addEntry(long userId,
		java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		java.util.Date displayDate, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addEntry(userId, title, subtitle,
			description, content, displayDate, allowPingbacks, allowTrackbacks,
			trackbacks, coverImageCaption, coverImageImageSelector,
			smallImageImageSelector, serviceContext);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry deleteEntry(
		com.liferay.blogs.kernel.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.deleteEntry(entry);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry fetchBlogsEntry(
		long entryId) {
		return _blogsEntryLocalService.fetchBlogsEntry(entryId);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry fetchBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _blogsEntryLocalService.fetchBlogsEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry getBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getBlogsEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getEntry(entryId);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry getEntry(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getEntry(groupId, urlTitle);
	}

	/**
	* Moves the blogs entry to the recycle bin. Social activity counters for
	* this entry get disabled.
	*
	* @param userId the primary key of the user moving the blogs entry
	* @param entry the blogs entry to be moved
	* @return the moved blogs entry
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry moveEntryToTrash(
		long userId, com.liferay.blogs.kernel.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.moveEntryToTrash(userId, entry);
	}

	/**
	* Moves the blogs entry with the ID to the recycle bin.
	*
	* @param userId the primary key of the user moving the blogs entry
	* @param entryId the primary key of the blogs entry to be moved
	* @return the moved blogs entry
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry moveEntryToTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.moveEntryToTrash(userId, entryId);
	}

	/**
	* Restores the blogs entry with the ID from the recycle bin. Social
	* activity counters for this entry get activated.
	*
	* @param userId the primary key of the user restoring the blogs entry
	* @param entryId the primary key of the blogs entry to be restored
	* @return the restored blogs entry from the recycle bin
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry restoreEntryFromTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.restoreEntryFromTrash(userId, entryId);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry updateBlogsEntry(
		com.liferay.blogs.kernel.model.BlogsEntry blogsEntry) {
		return _blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry updateEntry(long userId,
		long entryId, java.lang.String title, java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateEntry(userId, entryId, title,
			content, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	String, String, String, String, int, int, int, int, int,
	boolean, boolean, String[], String, ImageSelector,
	ImageSelector, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry updateEntry(long userId,
		long entryId, java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateEntry(userId, entryId, title,
			description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry updateEntry(long userId,
		long entryId, java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateEntry(userId, entryId, title,
			subtitle, description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry updateEntry(long userId,
		long entryId, java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		java.util.Date displayDate, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateEntry(userId, entryId, title,
			subtitle, description, content, displayDate, allowPingbacks,
			allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, long,
	int, ServiceContext, Map)}
	*/
	@Deprecated
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry updateStatus(long userId,
		long entryId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateStatus(userId, entryId, status,
			serviceContext);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry updateStatus(long userId,
		long entryId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateStatus(userId, entryId, status,
			serviceContext, workflowContext);
	}

	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry[] getEntriesPrevAndNext(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getEntriesPrevAndNext(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _blogsEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addAttachmentsFolder(userId, groupId);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder fetchAttachmentsFolder(
		long userId, long groupId) {
		return _blogsEntryLocalService.fetchAttachmentsFolder(userId, groupId);
	}

	@Override
	public int getCompanyEntriesCount(long companyId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getCompanyEntriesCount(companyId,
			displayDate, queryDefinition);
	}

	@Override
	public int getGroupEntriesCount(long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getGroupEntriesCount(groupId,
			queryDefinition);
	}

	@Override
	public int getGroupEntriesCount(long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getGroupEntriesCount(groupId,
			displayDate, queryDefinition);
	}

	@Override
	public int getGroupUserEntriesCount(long groupId, long userId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getGroupUserEntriesCount(groupId,
			userId, displayDate, queryDefinition);
	}

	@Override
	public int getOrganizationEntriesCount(long organizationId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getOrganizationEntriesCount(organizationId,
			displayDate, queryDefinition);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _blogsEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getBlogsEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.blogs.kernel.model.BlogsEntry> orderByComparator) {
		return _blogsEntryLocalService.getBlogsEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getCompanyEntries(
		long companyId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getCompanyEntries(companyId,
			displayDate, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getGroupEntries(
		long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getGroupEntries(groupId, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getGroupEntries(groupId, displayDate,
			queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			displayDate, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getGroupsEntries(companyId, groupId,
			displayDate, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getNoAssetEntries() {
		return _blogsEntryLocalService.getNoAssetEntries();
	}

	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getOrganizationEntries(
		long organizationId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.blogs.kernel.model.BlogsEntry> queryDefinition) {
		return _blogsEntryLocalService.getOrganizationEntries(organizationId,
			displayDate, queryDefinition);
	}

	@Override
	public long addOriginalImageFileEntry(long userId, long groupId,
		long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addOriginalImageFileEntry(userId,
			groupId, entryId, imageSelector);
	}

	@Override
	public void addCoverImage(long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addCoverImage(entryId, imageSelector);
	}

	@Override
	public void addEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addEntryResources(entry, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addEntryResources(entry, modelPermissions);
	}

	@Override
	public void addEntryResources(long entryId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addEntryResources(entryId, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addEntryResources(long entryId,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addEntryResources(entryId, modelPermissions);
	}

	@Override
	public void addSmallImage(long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addSmallImage(entryId, imageSelector);
	}

	@Override
	public void checkEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.checkEntries();
	}

	@Override
	public void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.deleteEntries(groupId);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.deleteEntry(entryId);
	}

	@Override
	public void moveEntriesToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.moveEntriesToTrash(groupId, userId);
	}

	@Override
	public void subscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.subscribe(userId, groupId);
	}

	@Override
	public void unsubscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.unsubscribe(userId, groupId);
	}

	@Override
	public void updateAsset(long userId,
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.updateAsset(userId, entry, assetCategoryIds,
			assetTagNames, assetLinkEntryIds, priority);
	}

	@Override
	public void updateEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.updateEntryResources(entry, modelPermissions);
	}

	@Override
	public void updateEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.updateEntryResources(entry, groupPermissions,
			guestPermissions);
	}

	@Override
	public BlogsEntryLocalService getWrappedService() {
		return _blogsEntryLocalService;
	}

	@Override
	public void setWrappedService(BlogsEntryLocalService blogsEntryLocalService) {
		_blogsEntryLocalService = blogsEntryLocalService;
	}

	private BlogsEntryLocalService _blogsEntryLocalService;
}