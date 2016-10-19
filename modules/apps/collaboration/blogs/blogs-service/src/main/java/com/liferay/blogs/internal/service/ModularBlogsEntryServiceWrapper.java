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

package com.liferay.blogs.internal.service;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryService;
import com.liferay.blogs.kernel.service.BlogsEntryServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularBlogsEntryServiceWrapper extends BlogsEntryServiceWrapper {

	public ModularBlogsEntryServiceWrapper() {
		super(null);
	}

	public ModularBlogsEntryServiceWrapper(
		BlogsEntryService blogsEntryService) {

		super(blogsEntryService);
	}

	/**
	 * @deprecated As of 1.1.0, replaced by {@link #addEntry(String, String,
	 *             String, String, int, int, int, int, int, boolean, boolean,
	 *             String[], String, ImageSelector, ImageSelector,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public BlogsEntry addEntry(
			String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.addEntry(
				title, description, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				allowPingbacks, allowTrackbacks, trackbacks, smallImage,
				smallImageURL, smallImageFileName, smallImageInputStream,
				serviceContext));
	}

	@Override
	public BlogsEntry addEntry(
			String title, String subtitle, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.addEntry(
				title, subtitle, description, content, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
				coverImageCaption, coverImageImageSelector,
				smallImageImageSelector, serviceContext));
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		_blogsEntryService.deleteEntry(entryId);
	}

	@Override
	public List<BlogsEntry> getCompanyEntries(
			long companyId, Date displayDate, int status, int max)
		throws PortalException {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getCompanyEntries(
				companyId, displayDate, status, max));
	}

	@Override
	public String getCompanyEntriesRSS(
			long companyId, Date displayDate, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		return _blogsEntryService.getCompanyEntriesRSS(
			companyId, displayDate, status, max, type, version, displayStyle,
			feedURL, entryURL, themeDisplay);
	}

	@Override
	public BlogsEntry getEntry(long entryId) throws PortalException {
		return ModelAdaptorUtil.adapt(
			BlogsEntry.class, _blogsEntryService.getEntry(entryId));
	}

	@Override
	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class, _blogsEntryService.getEntry(groupId, urlTitle));
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate, int status, int max) {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getGroupEntries(
				groupId, displayDate, status, max));
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate, int status, int start, int end) {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getGroupEntries(
				groupId, displayDate, status, start, end));
	}

	@Override
	public List<BlogsEntry> getGroupEntries(long groupId, int status, int max) {
		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getGroupEntries(groupId, status, max));
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end) {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getGroupEntries(groupId, status, start, end));
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end,
		OrderByComparator<BlogsEntry> obc) {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getGroupEntries(
				groupId, status, start, end,
				ModelAdaptorUtil.adapt(BlogsEntry.class, obc)));
	}

	@Override
	public int getGroupEntriesCount(
		long groupId, Date displayDate, int status) {

		return _blogsEntryService.getGroupEntriesCount(
			groupId, displayDate, status);
	}

	@Override
	public int getGroupEntriesCount(long groupId, int status) {
		return _blogsEntryService.getGroupEntriesCount(groupId, status);
	}

	@Override
	public String getGroupEntriesRSS(
			long groupId, Date displayDate, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		return _blogsEntryService.getGroupEntriesRSS(
			groupId, displayDate, status, max, type, version, displayStyle,
			feedURL, entryURL, themeDisplay);
	}

	@Override
	public List<BlogsEntry> getGroupsEntries(
			long companyId, long groupId, Date displayDate, int status, int max)
		throws PortalException {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getGroupsEntries(
				companyId, groupId, displayDate, status, max));
	}

	@Override
	public List<BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<BlogsEntry> obc) {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getGroupUserEntries(
				groupId, userId, status, start, end,
				ModelAdaptorUtil.adapt(BlogsEntry.class, obc)));
	}

	@Override
	public List<BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int[] statuses, int start, int end,
		OrderByComparator<BlogsEntry> obc) {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getGroupUserEntries(
				groupId, userId, statuses, start, end,
				ModelAdaptorUtil.adapt(BlogsEntry.class, obc)));
	}

	@Override
	public int getGroupUserEntriesCount(long groupId, long userId, int status) {
		return _blogsEntryService.getGroupUserEntriesCount(
			groupId, userId, status);
	}

	@Override
	public int getGroupUserEntriesCount(
		long groupId, long userId, int[] statuses) {

		return _blogsEntryService.getGroupUserEntriesCount(
			groupId, userId, statuses);
	}

	@Override
	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, Date displayDate, int status, int max)
		throws PortalException {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.getOrganizationEntries(
				organizationId, displayDate, status, max));
	}

	@Override
	public String getOrganizationEntriesRSS(
			long organizationId, Date displayDate, int status, int max,
			String type, double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		return _blogsEntryService.getOrganizationEntriesRSS(
			organizationId, displayDate, status, max, type, version,
			displayStyle, feedURL, entryURL, themeDisplay);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _blogsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public BlogsEntryService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public BlogsEntry moveEntryToTrash(long entryId) throws PortalException {
		return ModelAdaptorUtil.adapt(
			BlogsEntry.class, _blogsEntryService.moveEntryToTrash(entryId));
	}

	@Override
	public void restoreEntryFromTrash(long entryId) throws PortalException {
		_blogsEntryService.restoreEntryFromTrash(entryId);
	}

	@Override
	public void setWrappedService(BlogsEntryService blogsEntryService) {
		super.setWrappedService(blogsEntryService);
	}

	@Override
	public void subscribe(long groupId) throws PortalException {
		_blogsEntryService.subscribe(groupId);
	}

	@Override
	public void unsubscribe(long groupId) throws PortalException {
		_blogsEntryService.unsubscribe(groupId);
	}

	/**
	 * @deprecated As of 1.1.0, replaced by {@link #updateEntry(long, String,
	 *             String, String, String, int, int, int, int, int, boolean,
	 *             boolean, String[], String, ImageSelector, ImageSelector,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public BlogsEntry updateEntry(
			long entryId, String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.updateEntry(
				entryId, title, description, content, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
				smallImage, smallImageURL, smallImageFileName,
				smallImageInputStream, serviceContext));
	}

	@Override
	public BlogsEntry updateEntry(
			long entryId, String title, String subtitle, String description,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdaptorUtil.adapt(
			BlogsEntry.class,
			_blogsEntryService.updateEntry(
				entryId, title, subtitle, description, content,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, allowPingbacks,
				allowTrackbacks, trackbacks, coverImageCaption,
				coverImageImageSelector, smallImageImageSelector,
				serviceContext));
	}

	@Reference
	protected void setBlogsEntryLocalService(
		com.liferay.blogs.service.BlogsEntryService blogsEntryService) {

		_blogsEntryService = blogsEntryService;
	}

	private com.liferay.blogs.service.BlogsEntryService _blogsEntryService;

}