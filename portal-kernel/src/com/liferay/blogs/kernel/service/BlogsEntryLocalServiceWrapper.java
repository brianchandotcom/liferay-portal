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

package com.liferay.blogs.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BlogsEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryLocalService
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl}
 * @generated
 */
@Deprecated
@ProviderType
public class BlogsEntryLocalServiceWrapper implements BlogsEntryLocalService,
	ServiceWrapper<BlogsEntryLocalService> {
	public BlogsEntryLocalServiceWrapper(
		BlogsEntryLocalService blogsEntryLocalService) {
		_blogsEntryLocalService = blogsEntryLocalService;
	}

	/**
	* Adds the blogs entry to the database. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was added
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry addBlogsEntry(
		com.liferay.blogs.kernel.model.BlogsEntry blogsEntry) {
		return _blogsEntryLocalService.addBlogsEntry(blogsEntry);
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

	/**
	* Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	*
	* @param entryId the primary key for the new blogs entry
	* @return the new blogs entry
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry createBlogsEntry(
		long entryId) {
		return _blogsEntryLocalService.createBlogsEntry(entryId);
	}

	/**
	* Deletes the blogs entry from the database. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was removed
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry deleteBlogsEntry(
		com.liferay.blogs.kernel.model.BlogsEntry blogsEntry) {
		return _blogsEntryLocalService.deleteBlogsEntry(blogsEntry);
	}

	/**
	* Deletes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry that was removed
	* @throws PortalException if a blogs entry with the primary key could not be found
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry deleteBlogsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.deleteBlogsEntry(entryId);
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

	/**
	* Returns the blogs entry matching the UUID and group.
	*
	* @param uuid the blogs entry's UUID
	* @param groupId the primary key of the group
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry fetchBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _blogsEntryLocalService.fetchBlogsEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the blogs entry with the primary key.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry
	* @throws PortalException if a blogs entry with the primary key could not be found
	*/
	@Override
	public com.liferay.blogs.kernel.model.BlogsEntry getBlogsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getBlogsEntry(entryId);
	}

	/**
	* Returns the blogs entry matching the UUID and group.
	*
	* @param uuid the blogs entry's UUID
	* @param groupId the primary key of the group
	* @return the matching blogs entry
	* @throws PortalException if a matching blogs entry could not be found
	*/
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

	/**
	* Updates the blogs entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was updated
	*/
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
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _blogsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _blogsEntryLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _blogsEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _blogsEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getPersistedModel(primaryKeyObj);
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

	/**
	* Returns the number of blogs entries.
	*
	* @return the number of blogs entries
	*/
	@Override
	public int getBlogsEntriesCount() {
		return _blogsEntryLocalService.getBlogsEntriesCount();
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

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the blogs entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of blogs entries
	*/
	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getBlogsEntries(
		int start, int end) {
		return _blogsEntryLocalService.getBlogsEntries(start, end);
	}

	/**
	* Returns all the blogs entries matching the UUID and company.
	*
	* @param uuid the UUID of the blogs entries
	* @param companyId the primary key of the company
	* @return the matching blogs entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getBlogsEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _blogsEntryLocalService.getBlogsEntriesByUuidAndCompanyId(uuid,
			companyId);
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

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _blogsEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _blogsEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
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