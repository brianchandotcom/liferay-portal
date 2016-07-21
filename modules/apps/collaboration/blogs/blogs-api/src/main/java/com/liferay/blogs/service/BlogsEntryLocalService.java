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

import com.liferay.blogs.kernel.model.BlogsEntry;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for BlogsEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryLocalServiceUtil
 * @see com.liferay.blogs.service.base.BlogsEntryLocalServiceBaseImpl
 * @see com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BlogsEntryLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlogsEntryLocalServiceUtil} to access the blogs entry local service. Add custom service methods to {@link com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public BlogsEntry addEntry(long userId, java.lang.String title,
		java.lang.String content, ServiceContext serviceContext)
		throws PortalException;

	public BlogsEntry addEntry(long userId, java.lang.String title,
		java.lang.String content, Date displayDate,
		ServiceContext serviceContext) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addEntry(long, String,
	String, String, String, int, int, int, int, int, boolean,
	boolean, String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public BlogsEntry addEntry(long userId, java.lang.String title,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		boolean smallImage, java.lang.String smallImageURL,
		java.lang.String smallImageFileName, InputStream smallImageInputStream,
		ServiceContext serviceContext) throws PortalException;

	public BlogsEntry addEntry(long userId, java.lang.String title,
		java.lang.String subtitle, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, java.lang.String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public BlogsEntry addEntry(long userId, java.lang.String title,
		java.lang.String subtitle, java.lang.String description,
		java.lang.String content, Date displayDate, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public BlogsEntry deleteEntry(BlogsEntry entry) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry fetchBlogsEntry(long entryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry fetchBlogsEntryByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry getBlogsEntryByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry getEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry getEntry(long groupId, java.lang.String urlTitle)
		throws PortalException;

	/**
	* Moves the blogs entry to the recycle bin. Social activity counters for
	* this entry get disabled.
	*
	* @param userId the primary key of the user moving the blogs entry
	* @param entry the blogs entry to be moved
	* @return the moved blogs entry
	*/
	@Indexable(type = IndexableType.REINDEX)
	public BlogsEntry moveEntryToTrash(long userId, BlogsEntry entry)
		throws PortalException;

	/**
	* Moves the blogs entry with the ID to the recycle bin.
	*
	* @param userId the primary key of the user moving the blogs entry
	* @param entryId the primary key of the blogs entry to be moved
	* @return the moved blogs entry
	*/
	public BlogsEntry moveEntryToTrash(long userId, long entryId)
		throws PortalException;

	/**
	* Restores the blogs entry with the ID from the recycle bin. Social
	* activity counters for this entry get activated.
	*
	* @param userId the primary key of the user restoring the blogs entry
	* @param entryId the primary key of the blogs entry to be restored
	* @return the restored blogs entry from the recycle bin
	*/
	@Indexable(type = IndexableType.REINDEX)
	public BlogsEntry restoreEntryFromTrash(long userId, long entryId)
		throws PortalException;

	public BlogsEntry updateBlogsEntry(BlogsEntry blogsEntry);

	public BlogsEntry updateEntry(long userId, long entryId,
		java.lang.String title, java.lang.String content,
		ServiceContext serviceContext) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	String, String, String, String, int, int, int, int, int,
	boolean, boolean, String[], String, ImageSelector,
	ImageSelector, ServiceContext)}
	*/
	@java.lang.Deprecated
	public BlogsEntry updateEntry(long userId, long entryId,
		java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException;

	public BlogsEntry updateEntry(long userId, long entryId,
		java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public BlogsEntry updateEntry(long userId, long entryId,
		java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		Date displayDate, boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, java.lang.String coverImageCaption,
		ImageSelector coverImageImageSelector,
		ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, long,
	int, ServiceContext, Map)}
	*/
	@java.lang.Deprecated
	public BlogsEntry updateStatus(long userId, long entryId, int status,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public BlogsEntry updateStatus(long userId, long entryId, int status,
		ServiceContext serviceContext,
		Map<java.lang.String, Serializable> workflowContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogsEntry[] getEntriesPrevAndNext(long entryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	public Folder addAttachmentsFolder(long userId, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Folder fetchAttachmentsFolder(long userId, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyEntriesCount(long companyId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId,
		QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupUserEntriesCount(long groupId, long userId,
		Date displayDate, QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrganizationEntriesCount(long organizationId,
		Date displayDate, QueryDefinition<BlogsEntry> queryDefinition);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getBlogsEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getCompanyEntries(long companyId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId,
		QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupEntries(long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupUserEntries(long groupId, long userId,
		Date displayDate, QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getGroupsEntries(long companyId, long groupId,
		Date displayDate, QueryDefinition<BlogsEntry> queryDefinition);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getNoAssetEntries();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogsEntry> getOrganizationEntries(long organizationId,
		Date displayDate, QueryDefinition<BlogsEntry> queryDefinition);

	public long addOriginalImageFileEntry(long userId, long groupId,
		long entryId, ImageSelector imageSelector) throws PortalException;

	public void addCoverImage(long entryId, ImageSelector imageSelector)
		throws PortalException;

	public void addEntryResources(BlogsEntry entry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addEntryResources(BlogsEntry entry,
		ModelPermissions modelPermissions) throws PortalException;

	public void addEntryResources(long entryId, boolean addGroupPermissions,
		boolean addGuestPermissions) throws PortalException;

	public void addEntryResources(long entryId,
		ModelPermissions modelPermissions) throws PortalException;

	public void addSmallImage(long entryId, ImageSelector imageSelector)
		throws PortalException;

	public void checkEntries() throws PortalException;

	public void deleteEntries(long groupId) throws PortalException;

	public void deleteEntry(long entryId) throws PortalException;

	public void moveEntriesToTrash(long groupId, long userId)
		throws PortalException;

	public void subscribe(long userId, long groupId) throws PortalException;

	public void unsubscribe(long userId, long groupId)
		throws PortalException;

	public void updateAsset(long userId, BlogsEntry entry,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws PortalException;

	public void updateEntryResources(BlogsEntry entry,
		ModelPermissions modelPermissions) throws PortalException;

	public void updateEntryResources(BlogsEntry entry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws PortalException;
}