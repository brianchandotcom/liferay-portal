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
 * Provides a wrapper for {@link BlogsEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryService
 * @generated
 */
@ProviderType
public class BlogsEntryServiceWrapper implements BlogsEntryService,
	ServiceWrapper<BlogsEntryService> {
	public BlogsEntryServiceWrapper(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addEntry(String, String,
	String, String, int, int, int, int, int, boolean, boolean,
	String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.addEntry(title, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			smallImage, smallImageURL, smallImageFileName,
			smallImageInputStream, serviceContext);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.addEntry(title, subtitle, description,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryService.deleteEntry(entryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, java.util.Date displayDate, int status, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.getCompanyEntries(companyId, displayDate,
			status, max);
	}

	@Override
	public java.lang.String getCompanyEntriesRSS(long companyId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.getCompanyEntriesRSS(companyId, displayDate,
			status, max, type, version, displayStyle, feedURL, entryURL,
			themeDisplay);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.getEntry(entryId);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.getEntry(groupId, urlTitle);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int max) {
		return _blogsEntryService.getGroupEntries(groupId, displayDate, status,
			max);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int start, int end) {
		return _blogsEntryService.getGroupEntries(groupId, displayDate, status,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int max) {
		return _blogsEntryService.getGroupEntries(groupId, status, max);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end) {
		return _blogsEntryService.getGroupEntries(groupId, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.blogs.model.BlogsEntry> obc) {
		return _blogsEntryService.getGroupEntries(groupId, status, start, end,
			obc);
	}

	@Override
	public int getGroupEntriesCount(long groupId, java.util.Date displayDate,
		int status) {
		return _blogsEntryService.getGroupEntriesCount(groupId, displayDate,
			status);
	}

	@Override
	public int getGroupEntriesCount(long groupId, int status) {
		return _blogsEntryService.getGroupEntriesCount(groupId, status);
	}

	@Override
	public java.lang.String getGroupEntriesRSS(long groupId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.getGroupEntriesRSS(groupId, displayDate,
			status, max, type, version, displayStyle, feedURL, entryURL,
			themeDisplay);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.blogs.model.BlogsEntry> obc) {
		return _blogsEntryService.getGroupUserEntries(groupId, userId, status,
			start, end, obc);
	}

	@Override
	public int getGroupUserEntriesCount(long groupId, long userId, int status) {
		return _blogsEntryService.getGroupUserEntriesCount(groupId, userId,
			status);
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate, int status,
		int max) throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.getGroupsEntries(companyId, groupId,
			displayDate, status, max);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _blogsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, java.util.Date displayDate, int status, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.getOrganizationEntries(organizationId,
			displayDate, status, max);
	}

	@Override
	public java.lang.String getOrganizationEntriesRSS(long organizationId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.getOrganizationEntriesRSS(organizationId,
			displayDate, status, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry moveEntryToTrash(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.moveEntryToTrash(entryId);
	}

	@Override
	public void restoreEntryFromTrash(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryService.restoreEntryFromTrash(entryId);
	}

	@Override
	public void subscribe(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryService.subscribe(groupId);
	}

	@Override
	public void unsubscribe(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_blogsEntryService.unsubscribe(groupId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, String,
	String, String, String, int, int, int, int, int, boolean,
	boolean, String[], String, ImageSelector, ImageSelector,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String description,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks, boolean smallImage,
		java.lang.String smallImageURL, java.lang.String smallImageFileName,
		java.io.InputStream smallImageInputStream,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.updateEntry(entryId, title, description,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, smallImage, smallImageURL,
			smallImageFileName, smallImageInputStream, serviceContext);
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		long entryId, java.lang.String title, java.lang.String subtitle,
		java.lang.String description, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowPingbacks,
		boolean allowTrackbacks, java.lang.String[] trackbacks,
		java.lang.String coverImageCaption,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector coverImageImageSelector,
		com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector smallImageImageSelector,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _blogsEntryService.updateEntry(entryId, title, subtitle,
			description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, coverImageCaption,
			coverImageImageSelector, smallImageImageSelector, serviceContext);
	}

	@Override
	public BlogsEntryService getWrappedService() {
		return _blogsEntryService;
	}

	@Override
	public void setWrappedService(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	private BlogsEntryService _blogsEntryService;
}