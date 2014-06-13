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

package com.liferay.portlet.blogs.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

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

	/**
	* Adds the blogs entry to the database. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was added
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry addBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry) {
		return _blogsEntryLocalService.addBlogsEntry(blogsEntry);
	}

	/**
	* Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	*
	* @param entryId the primary key for the new blogs entry
	* @return the new blogs entry
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry createBlogsEntry(
		long entryId) {
		return _blogsEntryLocalService.createBlogsEntry(entryId);
	}

	/**
	* Deletes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry that was removed
	* @throws PortalException if a blogs entry with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry deleteBlogsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.deleteBlogsEntry(entryId);
	}

	/**
	* Deletes the blogs entry from the database. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was removed
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry deleteBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry) {
		return _blogsEntryLocalService.deleteBlogsEntry(blogsEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _blogsEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
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
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
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
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _blogsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _blogsEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry fetchBlogsEntry(
		long entryId) {
		return _blogsEntryLocalService.fetchBlogsEntry(entryId);
	}

	/**
	* Returns the blogs entry with the matching UUID and company.
	*
	* @param uuid the blogs entry's UUID
	* @param companyId the primary key of the company
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry fetchBlogsEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _blogsEntryLocalService.fetchBlogsEntryByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the blogs entry matching the UUID and group.
	*
	* @param uuid the blogs entry's UUID
	* @param groupId the primary key of the group
	* @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry fetchBlogsEntryByUuidAndGroupId(
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
	public com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getBlogsEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _blogsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return _blogsEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the blogs entry with the matching UUID and company.
	*
	* @param uuid the blogs entry's UUID
	* @param companyId the primary key of the company
	* @return the matching blogs entry
	* @throws PortalException if a matching blogs entry could not be found
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getBlogsEntryByUuidAndCompanyId(uuid,
			companyId);
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
	public com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getBlogsEntryByUuidAndGroupId(uuid,
			groupId);
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
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getBlogsEntries(
		int start, int end) {
		return _blogsEntryLocalService.getBlogsEntries(start, end);
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

	/**
	* Updates the blogs entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was updated
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry) {
		return _blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _blogsEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_blogsEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(long userId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addEntry(userId, title, description,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, smallImage, smallImageURL,
			smallImageFileName, smallImageInputStream, serviceContext);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(long userId,
		java.lang.String title, java.lang.String deckTitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		boolean smallImage, java.lang.String smallImageURL,
		java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.addEntry(userId, title, deckTitle,
			description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	@Override
	public void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addEntryResources(entry, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addEntryResources(entry, groupPermissions,
			guestPermissions);
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
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.addEntryResources(entryId, groupPermissions,
			guestPermissions);
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
	public com.liferay.portlet.blogs.model.BlogsEntry deleteEntry(
		com.liferay.portlet.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.deleteEntry(entry);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.deleteEntry(entryId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getCompanyEntries(long,
	Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, java.util.Date displayDate, int status, int start,
		int end) {
		return _blogsEntryLocalService.getCompanyEntries(companyId,
			displayDate, status, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getCompanyEntries(long,
	Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, java.util.Date displayDate, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc) {
		return _blogsEntryLocalService.getCompanyEntries(companyId,
			displayDate, status, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getCompanyEntries(companyId,
			displayDate, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getCompanyEntriesCount(long,
	Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public int getCompanyEntriesCount(long companyId,
		java.util.Date displayDate, int status) {
		return _blogsEntryLocalService.getCompanyEntriesCount(companyId,
			displayDate, status);
	}

	@Override
	public int getCompanyEntriesCount(long companyId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getCompanyEntriesCount(companyId,
			displayDate, queryDefinition);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry[] getEntriesPrevAndNext(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getEntriesPrevAndNext(entryId);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getEntry(entryId);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.getEntry(groupId, urlTitle);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupEntries(long, Date,
	QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int start, int end) {
		return _blogsEntryLocalService.getGroupEntries(groupId, displayDate,
			status, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupEntries(long, Date,
	QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc) {
		return _blogsEntryLocalService.getGroupEntries(groupId, displayDate,
			status, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getGroupEntries(groupId, displayDate,
			queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupEntries(long,
	QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end) {
		return _blogsEntryLocalService.getGroupEntries(groupId, status, start,
			end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupEntries(long,
	QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc) {
		return _blogsEntryLocalService.getGroupEntries(groupId, status, start,
			end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getGroupEntries(groupId, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupEntriesCount(long,
	Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public int getGroupEntriesCount(long groupId, java.util.Date displayDate,
		int status) {
		return _blogsEntryLocalService.getGroupEntriesCount(groupId,
			displayDate, status);
	}

	@Override
	public int getGroupEntriesCount(long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getGroupEntriesCount(groupId,
			displayDate, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupEntriesCount(long,
	QueryDefinition)}
	*/
	@Deprecated
	@Override
	public int getGroupEntriesCount(long groupId, int status) {
		return _blogsEntryLocalService.getGroupEntriesCount(groupId, status);
	}

	@Override
	public int getGroupEntriesCount(long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getGroupEntriesCount(groupId,
			queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupsEntries(long, long,
	Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate, int status,
		int start, int end) {
		return _blogsEntryLocalService.getGroupsEntries(companyId, groupId,
			displayDate, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getGroupsEntries(companyId, groupId,
			displayDate, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupUserEntries(long,
	long, Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, java.util.Date displayDate, int status,
		int start, int end) {
		return _blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			displayDate, status, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupUserEntries(long,
	long, Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, java.util.Date displayDate, int status,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc) {
		return _blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			displayDate, status, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			displayDate, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getGroupUserEntriesCount(long, long, Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public int getGroupUserEntriesCount(long groupId, long userId,
		java.util.Date displayDate, int status) {
		return _blogsEntryLocalService.getGroupUserEntriesCount(groupId,
			userId, displayDate, status);
	}

	@Override
	public int getGroupUserEntriesCount(long groupId, long userId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getGroupUserEntriesCount(groupId,
			userId, displayDate, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getNoAssetEntries() {
		return _blogsEntryLocalService.getNoAssetEntries();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getOrganizationEntries(long,
	Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, java.util.Date displayDate, int status, int start,
		int end) {
		return _blogsEntryLocalService.getOrganizationEntries(organizationId,
			displayDate, status, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getOrganizationEntries(long,
	Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, java.util.Date displayDate, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc) {
		return _blogsEntryLocalService.getOrganizationEntries(organizationId,
			displayDate, status, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getOrganizationEntries(organizationId,
			displayDate, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link
	#getOrganizationEntriesCount(long, Date, QueryDefinition)}
	*/
	@Deprecated
	@Override
	public int getOrganizationEntriesCount(long organizationId,
		java.util.Date displayDate, int status) {
		return _blogsEntryLocalService.getOrganizationEntriesCount(organizationId,
			displayDate, status);
	}

	@Override
	public int getOrganizationEntriesCount(long organizationId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition) {
		return _blogsEntryLocalService.getOrganizationEntriesCount(organizationId,
			displayDate, queryDefinition);
	}

	@Override
	public void moveEntriesToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.moveEntriesToTrash(groupId, userId);
	}

	/**
	* Moves the blogs entry to the recycle bin. Social activity counters for
	* this entry get disabled.
	*
	* @param userId the primary key of the user moving the blogs entry
	* @param entry the blogs entry to be moved
	* @return the moved blogs entry
	* @throws PortalException if a user with the primary key could not be found
	or if the blogs entry owner's social activity counter could not
	be updated
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry moveEntryToTrash(
		long userId, com.liferay.portlet.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.moveEntryToTrash(userId, entry);
	}

	/**
	* Moves the blogs entry with the ID to the recycle bin.
	*
	* @param userId the primary key of the user moving the blogs entry
	* @param entryId the primary key of the blogs entry to be moved
	* @return the moved blogs entry
	* @throws PortalException if a user or blogs entry with the primary key
	could not be found or if the blogs entry owner's social activity
	counter could not be updated
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry moveEntryToTrash(
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
	* @throws PortalException if a user or blogs entry with the primary key
	could not be found or if the blogs entry owner's social activity
	counter could not be updated
	*/
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry restoreEntryFromTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.restoreEntryFromTrash(userId, entryId);
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
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.updateAsset(userId, entry, assetCategoryIds,
			assetTagNames, assetLinkEntryIds);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(long userId,
		long entryId, java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateEntry(userId, entryId, title,
			description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(long userId,
		long entryId, java.lang.String title, java.lang.String deckTitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		boolean smallImage, java.lang.String smallImageURL,
		java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateEntry(userId, entryId, title,
			deckTitle, description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	@Override
	public void updateEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryLocalService.updateEntryResources(entry, groupPermissions,
			guestPermissions);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, long,
	int, ServiceContext, Map)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry updateStatus(
		long userId, long entryId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateStatus(userId, entryId, status,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry updateStatus(
		long userId, long entryId, int status,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryLocalService.updateStatus(userId, entryId, status,
			serviceContext, workflowContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public BlogsEntryLocalService getWrappedBlogsEntryLocalService() {
		return _blogsEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {
		_blogsEntryLocalService = blogsEntryLocalService;
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