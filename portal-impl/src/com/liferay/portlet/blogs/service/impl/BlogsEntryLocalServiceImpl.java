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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portlet.blogs.service.base.BlogsEntryLocalServiceBaseImpl;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, checking, deleting,
 * subscription handling of, trash handling of, and updating blog entries.
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 * @author Thiago Moreira
 * @author Juan Fernández
 * @author Zsolt Berentey
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl}
 */
@Deprecated
public class BlogsEntryLocalServiceImpl extends BlogsEntryLocalServiceBaseImpl {

	@Override
	public Folder addAttachmentsFolder(long userId, long groupId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void addCoverImage(long entryId, ImageSelector imageSelector)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String content, Date displayDate,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String content,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addEntry(long, String,
	 *             String, String, String, int, int, int, int, int, boolean,
	 *             boolean, String[], String, ImageSelector, ImageSelector,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public BlogsEntry addEntry(
			long userId, String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");

	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry addEntry(
			long userId, String title, String subtitle, String description,
			String content, Date displayDate, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");

	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String subtitle, String description,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void addEntryResources(
			BlogsEntry entry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void addEntryResources(
			BlogsEntry entry, ModelPermissions modelPermissions)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void addEntryResources(
			long entryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void addEntryResources(
			long entryId, ModelPermissions modelPermissions)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public long addOriginalImageFileEntry(
			long userId, long groupId, long entryId,
			ImageSelector imageSelector)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void addSmallImage(long entryId, ImageSelector imageSelector)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void checkEntries() throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void deleteEntries(long groupId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public BlogsEntry deleteEntry(BlogsEntry entry) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public Folder fetchAttachmentsFolder(long userId, long groupId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public List<BlogsEntry> getCompanyEntries(
		long companyId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public int getCompanyEntriesCount(
		long companyId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public BlogsEntry[] getEntriesPrevAndNext(long entryId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public BlogsEntry getEntry(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public int getGroupEntriesCount(
		long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public int getGroupEntriesCount(
		long groupId, QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupsEntries(
		long companyId, long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupUserEntries(
		long groupId, long userId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public int getGroupUserEntriesCount(
		long groupId, long userId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public List<BlogsEntry> getNoAssetEntries() {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public List<BlogsEntry> getOrganizationEntries(
		long organizationId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public int getOrganizationEntriesCount(
		long organizationId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void moveEntriesToTrash(long groupId, long userId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	/**
	 * Moves the blogs entry to the recycle bin. Social activity counters for
	 * this entry get disabled.
	 *
	 * @param  userId the primary key of the user moving the blogs entry
	 * @param  entry the blogs entry to be moved
	 * @return the moved blogs entry
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry moveEntryToTrash(long userId, BlogsEntry entry)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	/**
	 * Moves the blogs entry with the ID to the recycle bin.
	 *
	 * @param  userId the primary key of the user moving the blogs entry
	 * @param  entryId the primary key of the blogs entry to be moved
	 * @return the moved blogs entry
	 */
	@Override
	public BlogsEntry moveEntryToTrash(long userId, long entryId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	/**
	 * Restores the blogs entry with the ID from the recycle bin. Social
	 * activity counters for this entry get activated.
	 *
	 * @param  userId the primary key of the user restoring the blogs entry
	 * @param  entryId the primary key of the blogs entry to be restored
	 * @return the restored blogs entry from the recycle bin
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry restoreEntryFromTrash(long userId, long entryId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void subscribe(long userId, long groupId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void unsubscribe(long userId, long groupId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void updateAsset(
			long userId, BlogsEntry entry, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String content,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	 *             String, String, String, String, int, int, int, int, int,
	 *             boolean, boolean, String[], String, ImageSelector,
	 *             ImageSelector, ServiceContext)}
	 */
	@Deprecated
	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String description,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, boolean smallImage, String smallImageURL,
			String smallImageFileName, InputStream smallImageInputStream,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String description, String content, Date displayDate,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String description, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void updateEntryResources(
			BlogsEntry entry, ModelPermissions modelPermissions)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Override
	public void updateEntryResources(
			BlogsEntry entry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, long,
	 *             int, ServiceContext, Map)}
	 */
	@Deprecated
	@Override
	public BlogsEntry updateStatus(
			long userId, long entryId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry updateStatus(
			long userId, long entryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryLocalServiceImpl");
	}

}