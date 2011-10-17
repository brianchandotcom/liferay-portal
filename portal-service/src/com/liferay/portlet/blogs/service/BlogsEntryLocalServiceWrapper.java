/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link BlogsEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryLocalService
 * @generated
 */
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
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsEntry addBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.addBlogsEntry(blogsEntry);
	}

	/**
	* Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	*
	* @param entryId the primary key for the new blogs entry
	* @return the new blogs entry
	*/
	public com.liferay.portlet.blogs.model.BlogsEntry createBlogsEntry(
		long entryId) {
		return _blogsEntryLocalService.createBlogsEntry(entryId);
	}

	/**
	* Deletes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the blogs entry
	* @throws PortalException if a blogs entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBlogsEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteBlogsEntry(entryId);
	}

	/**
	* Deletes the blogs entry from the database. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteBlogsEntry(blogsEntry);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the blogs entry with the primary key.
	*
	* @param entryId the primary key of the blogs entry
	* @return the blogs entry
	* @throws PortalException if a blogs entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getBlogsEntry(entryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the blogs entry with the UUID in the group.
	*
	* @param uuid the UUID of blogs entry
	* @param groupId the group id of the blogs entry
	* @return the blogs entry
	* @throws PortalException if a blogs entry with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsEntry getBlogsEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getBlogsEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the blogs entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of blogs entries
	* @param end the upper bound of the range of blogs entries (not inclusive)
	* @return the range of blogs entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getBlogsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getBlogsEntries(start, end);
	}

	/**
	* Returns the number of blogs entries.
	*
	* @return the number of blogs entries
	* @throws SystemException if a system exception occurred
	*/
	public int getBlogsEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getBlogsEntriesCount();
	}

	/**
	* Updates the blogs entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @return the blogs entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	/**
	* Updates the blogs entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param blogsEntry the blogs entry
	* @param merge whether to merge the blogs entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the blogs entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsEntry updateBlogsEntry(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.updateBlogsEntry(blogsEntry, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _blogsEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_blogsEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(long userId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.addEntry(userId, title, description,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, smallImage, smallImageURL,
			smallImageFileName, smallImageInputStream, serviceContext);
	}

	public void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.addEntryResources(entry, addGroupPermissions,
			addGuestPermissions);
	}

	public void addEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.addEntryResources(entry, groupPermissions,
			guestPermissions);
	}

	public void addEntryResources(long entryId, boolean addGroupPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.addEntryResources(entryId, addGroupPermissions,
			addGuestPermissions);
	}

	public void addEntryResources(long entryId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.addEntryResources(entryId, groupPermissions,
			guestPermissions);
	}

	public void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteEntries(groupId);
	}

	public void deleteEntry(com.liferay.portlet.blogs.model.BlogsEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteEntry(entry);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.deleteEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getCompanyEntries(companyId, status,
			start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getCompanyEntries(companyId, status,
			start, end, obc);
	}

	public int getCompanyEntriesCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getCompanyEntriesCount(companyId, status);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry[] getEntriesPrevAndNext(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getEntriesPrevAndNext(entryId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getEntry(entryId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getEntry(groupId, urlTitle);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupEntries(groupId, status, start,
			end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupEntries(groupId, status, start,
			end, obc);
	}

	public int getGroupEntriesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupEntriesCount(groupId, status);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupsEntries(companyId, groupId,
			status, start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			status, start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupUserEntries(groupId, userId,
			status, start, end, obc);
	}

	public int getGroupUserEntriesCount(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getGroupUserEntriesCount(groupId,
			userId, status);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getNoAssetEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getNoAssetEntries();
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getOrganizationEntries(organizationId,
			status, start, end);
	}

	public int getOrganizationEntriesCount(long organizationId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.getOrganizationEntriesCount(organizationId,
			status);
	}

	public void subscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.subscribe(userId, groupId);
	}

	public void unsubscribe(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.unsubscribe(userId, groupId);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.updateAsset(userId, entry, assetCategoryIds,
			assetTagNames, assetLinkEntryIds);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(long userId,
		long entryId, java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.updateEntry(userId, entryId, title,
			description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	public void updateEntryResources(
		com.liferay.portlet.blogs.model.BlogsEntry entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryLocalService.updateEntryResources(entry, groupPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateStatus(
		long userId, long entryId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryLocalService.updateStatus(userId, entryId, status,
			serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public BlogsEntryLocalService getWrappedBlogsEntryLocalService() {
		return _blogsEntryLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {
		_blogsEntryLocalService = blogsEntryLocalService;
	}

	public BlogsEntryLocalService getWrappedService() {
		return _blogsEntryLocalService;
	}

	public void setWrappedService(BlogsEntryLocalService blogsEntryLocalService) {
		_blogsEntryLocalService = blogsEntryLocalService;
	}

	private BlogsEntryLocalService _blogsEntryLocalService;
}