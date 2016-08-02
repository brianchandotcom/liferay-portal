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

package com.liferay.blogs.internal.wrapper;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryLocalService;
import com.liferay.blogs.kernel.service.BlogsEntryLocalServiceWrapper;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularBlogsEntryLocalServiceWrapper
	extends BlogsEntryLocalServiceWrapper {

	public ModularBlogsEntryLocalServiceWrapper() {
		super(null);
	}

	public ModularBlogsEntryLocalServiceWrapper(
		BlogsEntryLocalService blogsEntryLocalService) {

		super(blogsEntryLocalService);
	}

	@Override
	public Folder addAttachmentsFolder(long userId, long groupId)
		throws PortalException {

		return _blogsEntryLocalService.addAttachmentsFolder(userId, groupId);
	}

	@Override
	public BlogsEntry addBlogsEntry(BlogsEntry blogsEntry) {
		return super.addBlogsEntry(blogsEntry);
	}

	@Override
	public void addCoverImage(long entryId, ImageSelector imageSelector)
		throws PortalException {

		_blogsEntryLocalService.addCoverImage(entryId, imageSelector);
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String content, Date displayDate,
			ServiceContext serviceContext)
		throws PortalException {

		return _blogsEntryLocalService.addEntry(
			userId, title, content, displayDate, serviceContext);
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String content,
			ServiceContext serviceContext)
		throws PortalException {

		return _blogsEntryLocalService.addEntry(
			userId, title, content, serviceContext);
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

		return _blogsEntryLocalService.addEntry(
			userId, title, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String subtitle, String description,
			String content, Date displayDate, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		return _blogsEntryLocalService.addEntry(
			userId, title, subtitle, description, content, displayDate,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
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

		return _blogsEntryLocalService.addEntry(
			userId, title, subtitle, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	@Override
	public void addEntryResources(
			BlogsEntry entry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_blogsEntryLocalService.addEntryResources(
			entry, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
			BlogsEntry entry, ModelPermissions modelPermissions)
		throws PortalException {

		_blogsEntryLocalService.addEntryResources(entry, modelPermissions);
	}

	@Override
	public void addEntryResources(
			long entryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_blogsEntryLocalService.addEntryResources(
			entryId, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
			long entryId, ModelPermissions modelPermissions)
		throws PortalException {

		_blogsEntryLocalService.addEntryResources(entryId, modelPermissions);
	}

	@Override
	public long addOriginalImageFileEntry(
			long userId, long groupId, long entryId,
			ImageSelector imageSelector)
		throws PortalException {

		return _blogsEntryLocalService.addOriginalImageFileEntry(
			userId, groupId, entryId, imageSelector);
	}

	@Override
	public void addSmallImage(long entryId, ImageSelector imageSelector)
		throws PortalException {

		_blogsEntryLocalService.addSmallImage(entryId, imageSelector);
	}

	@Override
	public void checkEntries() throws PortalException {
		_blogsEntryLocalService.checkEntries();
	}

	@Override
	public BlogsEntry createBlogsEntry(long entryId) {
		return super.createBlogsEntry(entryId);
	}

	@Override
	public BlogsEntry deleteBlogsEntry(BlogsEntry blogsEntry) {
		return super.deleteBlogsEntry(blogsEntry);
	}

	@Override
	public BlogsEntry deleteBlogsEntry(long entryId) throws PortalException {
		return super.deleteBlogsEntry(entryId);
	}

	@Override
	public void deleteEntries(long groupId) throws PortalException {
		_blogsEntryLocalService.deleteEntries(groupId);
	}

	@Override
	public BlogsEntry deleteEntry(BlogsEntry entry) throws PortalException {
		return _blogsEntryLocalService.deleteEntry(entry);
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		_blogsEntryLocalService.deleteEntry(entryId);
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return super.deletePersistedModel(persistedModel);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return super.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return super.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return super.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return super.dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return super.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return super.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public Folder fetchAttachmentsFolder(long userId, long groupId) {
		return _blogsEntryLocalService.fetchAttachmentsFolder(userId, groupId);
	}

	@Override
	public BlogsEntry fetchBlogsEntry(long entryId) {
		return super.fetchBlogsEntry(entryId);
	}

	@Override
	public BlogsEntry fetchBlogsEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return super.fetchBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return super.getActionableDynamicQuery();
	}

	@Override
	public List<BlogsEntry> getBlogsEntries(int start, int end) {
		return super.getBlogsEntries(start, end);
	}

	@Override
	public List<BlogsEntry> getBlogsEntriesByUuidAndCompanyId(
		String uuid, long companyId) {

		return super.getBlogsEntriesByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public List<BlogsEntry> getBlogsEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {

		return super.getBlogsEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	@Override
	public int getBlogsEntriesCount() {
		return super.getBlogsEntriesCount();
	}

	@Override
	public BlogsEntry getBlogsEntry(long entryId) throws PortalException {
		return super.getBlogsEntry(entryId);
	}

	@Override
	public BlogsEntry getBlogsEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return super.getBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<BlogsEntry> getCompanyEntries(
		long companyId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getCompanyEntries(
			companyId, displayDate, queryDefinition);
	}

	@Override
	public int getCompanyEntriesCount(
		long companyId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getCompanyEntriesCount(
			companyId, displayDate, queryDefinition);
	}

	@Override
	public BlogsEntry[] getEntriesPrevAndNext(long entryId)
		throws PortalException {

		return _blogsEntryLocalService.getEntriesPrevAndNext(entryId);
	}

	@Override
	public BlogsEntry getEntry(long entryId) throws PortalException {
		return _blogsEntryLocalService.getEntry(entryId);
	}

	@Override
	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException {

		return _blogsEntryLocalService.getEntry(groupId, urlTitle);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return super.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getGroupEntries(
			groupId, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getGroupEntries(
			groupId, queryDefinition);
	}

	@Override
	public int getGroupEntriesCount(
		long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getGroupEntriesCount(
			groupId, displayDate, queryDefinition);
	}

	@Override
	public int getGroupEntriesCount(
		long groupId, QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getGroupEntriesCount(
			groupId, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getGroupsEntries(
		long companyId, long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getGroupsEntries(
			companyId, groupId, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getGroupUserEntries(
		long groupId, long userId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getGroupUserEntries(
			groupId, userId, displayDate, queryDefinition);
	}

	@Override
	public int getGroupUserEntriesCount(
		long groupId, long userId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getGroupUserEntriesCount(
			groupId, userId, displayDate, queryDefinition);
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return super.getIndexableActionableDynamicQuery();
	}

	@Override
	public List<BlogsEntry> getNoAssetEntries() {
		return _blogsEntryLocalService.getNoAssetEntries();
	}

	@Override
	public List<BlogsEntry> getOrganizationEntries(
		long organizationId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getOrganizationEntries(
			organizationId, displayDate, queryDefinition);
	}

	@Override
	public int getOrganizationEntriesCount(
		long organizationId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return _blogsEntryLocalService.getOrganizationEntriesCount(
			organizationId, displayDate, queryDefinition);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _blogsEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return super.getPersistedModel(primaryKeyObj);
	}

	@Override
	public BlogsEntryLocalService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public void moveEntriesToTrash(long groupId, long userId)
		throws PortalException {

		_blogsEntryLocalService.moveEntriesToTrash(groupId, userId);
	}

	@Override
	public BlogsEntry moveEntryToTrash(long userId, BlogsEntry entry)
		throws PortalException {

		return _blogsEntryLocalService.moveEntryToTrash(userId, entry);
	}

	@Override
	public BlogsEntry moveEntryToTrash(long userId, long entryId)
		throws PortalException {

		return _blogsEntryLocalService.moveEntryToTrash(userId, entryId);
	}

	@Override
	public BlogsEntry restoreEntryFromTrash(long userId, long entryId)
		throws PortalException {

		return _blogsEntryLocalService.restoreEntryFromTrash(userId, entryId);
	}

	@Override
	public void setWrappedService(
		BlogsEntryLocalService blogsEntryLocalService) {

		super.setWrappedService(blogsEntryLocalService);
	}

	@Override
	public void subscribe(long userId, long groupId) throws PortalException {
		_blogsEntryLocalService.subscribe(userId, groupId);
	}

	@Override
	public void unsubscribe(long userId, long groupId) throws PortalException {
		_blogsEntryLocalService.unsubscribe(userId, groupId);
	}

	@Override
	public void updateAsset(
			long userId, BlogsEntry entry, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		_blogsEntryLocalService.updateAsset(
			userId, entry, assetCategoryIds, assetTagNames, assetLinkEntryIds,
			priority);
	}

	@Override
	public BlogsEntry updateBlogsEntry(BlogsEntry blogsEntry) {
		return super.updateBlogsEntry(blogsEntry);
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String content,
			ServiceContext serviceContext)
		throws PortalException {

		return _blogsEntryLocalService.updateEntry(
			userId, entryId, title, content, serviceContext);
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

		return _blogsEntryLocalService.updateEntry(
			userId, entryId, title, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

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

		return _blogsEntryLocalService.updateEntry(
			userId, entryId, title, subtitle, description, content, displayDate,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
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

		return _blogsEntryLocalService.updateEntry(
			userId, entryId, title, subtitle, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	@Override
	public void updateEntryResources(
			BlogsEntry entry, ModelPermissions modelPermissions)
		throws PortalException {

		_blogsEntryLocalService.updateEntryResources(entry, modelPermissions);
	}

	@Override
	public void updateEntryResources(
			BlogsEntry entry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		_blogsEntryLocalService.updateEntryResources(
			entry, groupPermissions, guestPermissions);
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

		return _blogsEntryLocalService.updateStatus(
			userId, entryId, status, serviceContext);
	}

	@Override
	public BlogsEntry updateStatus(
			long userId, long entryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		return _blogsEntryLocalService.updateStatus(
			userId, entryId, status, serviceContext, workflowContext);
	}

	@Reference
	protected void setBlogsEntryLocalService(
		com.
			liferay.
				blogs.service.BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	private com.liferay.blogs.service.BlogsEntryLocalService
		_blogsEntryLocalService;

}