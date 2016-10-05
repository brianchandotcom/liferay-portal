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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for BlogsEntry. This utility wraps
 * {@link com.liferay.portlet.blogs.service.impl.BlogsEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryLocalService
 * @see com.liferay.portlet.blogs.service.base.BlogsEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.blogs.service.impl.BlogsEntryLocalServiceImpl
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl}
 * @generated
 */
@Deprecated
@ProviderType
public class BlogsEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.blogs.service.impl.BlogsEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the blogs entry to the database. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was added
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry addBlogsEntry(
		com.liferay.blogs.kernel.model.BlogsEntry blogsEntry) {
		return getService().addBlogsEntry(blogsEntry);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry addEntry(
		long userId, java.lang.String title, java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addEntry(userId, title, content, serviceContext);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry addEntry(
		long userId, java.lang.String title, java.lang.String content,
		java.util.Date displayDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntry(userId, title, content, displayDate, serviceContext);
	}

	/**
	* @throws PortalException
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

	/**
	* @throws PortalException
	*/
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

	/**
	* @throws PortalException
	*/
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

	/**
	* Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	*
	* @param entryId the primary key for the new blogs entry
	* @return the new blogs entry
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry createBlogsEntry(
		long entryId) {
		return getService().createBlogsEntry(entryId);
	}

	/**
	* Deletes the blogs entry from the database. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was removed
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry deleteBlogsEntry(
		com.liferay.blogs.kernel.model.BlogsEntry blogsEntry) {
		return getService().deleteBlogsEntry(blogsEntry);
	}

	/**
	* Deletes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry that was removed
	* @throws PortalException if a blogs entry with the primary key could not be found
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry deleteBlogsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteBlogsEntry(entryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry deleteEntry(
		com.liferay.blogs.kernel.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteEntry(entry);
	}

	public static com.liferay.blogs.kernel.model.BlogsEntry fetchBlogsEntry(
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
	public static com.liferay.blogs.kernel.model.BlogsEntry fetchBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the blogs entry with the primary key.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry
	* @throws PortalException if a blogs entry with the primary key could not be found
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry getBlogsEntry(
		long entryId)
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
	public static com.liferay.blogs.kernel.model.BlogsEntry getBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntry(entryId);
	}

	/**
	* @throws PortalException
	*/
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
	* @throws PortalException
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
	* @throws PortalException
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
	* @throws PortalException
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry restoreEntryFromTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().restoreEntryFromTrash(userId, entryId);
	}

	/**
	* Updates the blogs entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was updated
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry updateBlogsEntry(
		com.liferay.blogs.kernel.model.BlogsEntry blogsEntry) {
		return getService().updateBlogsEntry(blogsEntry);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry updateEntry(
		long userId, long entryId, java.lang.String title,
		java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEntry(userId, entryId, title, content, serviceContext);
	}

	/**
	* @throws PortalException
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

	/**
	* @throws PortalException
	*/
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

	/**
	* @throws PortalException
	*/
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
	* @throws PortalException
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

	/**
	* @throws PortalException
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry updateStatus(
		long userId, long entryId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, entryId, status, serviceContext,
			workflowContext);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.blogs.kernel.model.BlogsEntry[] getEntriesPrevAndNext(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntriesPrevAndNext(entryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addAttachmentsFolder(userId, groupId);
	}

	public static com.liferay.portal.kernel.repository.model.Folder fetchAttachmentsFolder(
		long userId, long groupId) {
		return getService().fetchAttachmentsFolder(userId, groupId);
	}

	/**
	* Returns the number of blogs entries.
	*
	* @return the number of blogs entries
	*/
	public static int getBlogsEntriesCount() {
		return getService().getBlogsEntriesCount();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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
	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getBlogsEntries(
		int start, int end) {
		return getService().getBlogsEntries(start, end);
	}

	/**
	* Returns all the blogs entries matching the UUID and company.
	*
	* @param uuid the UUID of the blogs entries
	* @param companyId the primary key of the company
	* @return the matching blogs entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.blogs.kernel.model.BlogsEntry> getBlogsEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
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

	/**
	* @throws PortalException
	*/
	public static long addOriginalImageFileEntry(long userId, long groupId,
		long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOriginalImageFileEntry(userId, groupId, entryId,
			imageSelector);
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

	/**
	* @throws PortalException
	*/
	public static void addCoverImage(long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addCoverImage(entryId, imageSelector);
	}

	/**
	* @throws PortalException
	*/
	public static void addEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addEntryResources(entry, addGroupPermissions, addGuestPermissions);
	}

	/**
	* @throws PortalException
	*/
	public static void addEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addEntryResources(entry, modelPermissions);
	}

	/**
	* @throws PortalException
	*/
	public static void addEntryResources(long entryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addEntryResources(entryId, addGroupPermissions, addGuestPermissions);
	}

	/**
	* @throws PortalException
	*/
	public static void addEntryResources(long entryId,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addEntryResources(entryId, modelPermissions);
	}

	/**
	* @throws PortalException
	*/
	public static void addSmallImage(long entryId,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector imageSelector)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addSmallImage(entryId, imageSelector);
	}

	/**
	* @throws PortalException
	*/
	public static void checkEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkEntries();
	}

	/**
	* @throws PortalException
	*/
	public static void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteEntries(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteEntry(entryId);
	}

	/**
	* @throws PortalException
	*/
	public static void moveEntriesToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().moveEntriesToTrash(groupId, userId);
	}

	/**
	* @throws PortalException
	*/
	public static void subscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().subscribe(userId, groupId);
	}

	/**
	* @throws PortalException
	*/
	public static void unsubscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsubscribe(userId, groupId);
	}

	/**
	* @throws PortalException
	*/
	public static void updateAsset(long userId,
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateAsset(userId, entry, assetCategoryIds, assetTagNames,
			assetLinkEntryIds, priority);
	}

	/**
	* @throws PortalException
	*/
	public static void updateEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		com.liferay.portal.kernel.service.permission.ModelPermissions modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateEntryResources(entry, modelPermissions);
	}

	/**
	* @throws PortalException
	*/
	public static void updateEntryResources(
		com.liferay.blogs.kernel.model.BlogsEntry entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateEntryResources(entry, groupPermissions, guestPermissions);
	}

	public static BlogsEntryLocalService getService() {
		if (_service == null) {
			_service = (BlogsEntryLocalService)PortalBeanLocatorUtil.locate(BlogsEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(BlogsEntryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static BlogsEntryLocalService _service;
}