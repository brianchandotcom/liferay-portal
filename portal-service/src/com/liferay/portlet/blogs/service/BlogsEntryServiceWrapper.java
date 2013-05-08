/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * Provides a wrapper for {@link BlogsEntryService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryService
 * @generated
 */
public class BlogsEntryServiceWrapper implements BlogsEntryService,
	ServiceWrapper<BlogsEntryService> {
	public BlogsEntryServiceWrapper(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _blogsEntryService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_blogsEntryService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry addEntry(
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
		return _blogsEntryService.addEntry(title, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			smallImage, smallImageURL, smallImageFileName,
			smallImageInputStream, serviceContext);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryService.deleteEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		long companyId, java.util.Date displayDate, int status, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getCompanyEntries(companyId, displayDate,
			status, max);
	}

	public java.lang.String getCompanyEntriesRSS(long companyId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getCompanyEntriesRSS(companyId, displayDate,
			status, max, type, version, displayStyle, feedURL, entryURL,
			themeDisplay);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getEntry(entryId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry getEntry(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getEntry(groupId, urlTitle);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int max)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getGroupEntries(groupId, displayDate, status,
			max);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, java.util.Date displayDate, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getGroupEntries(groupId, displayDate, status,
			start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int max)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getGroupEntries(groupId, status, max);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getGroupEntries(groupId, status, start, end);
	}

	public int getGroupEntriesCount(long groupId, java.util.Date displayDate,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getGroupEntriesCount(groupId, displayDate,
			status);
	}

	public int getGroupEntriesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getGroupEntriesCount(groupId, status);
	}

	public java.lang.String getGroupEntriesRSS(long groupId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getGroupEntriesRSS(groupId, displayDate,
			status, max, type, version, displayStyle, feedURL, entryURL,
			themeDisplay);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupsEntries(
		long companyId, long groupId, java.util.Date displayDate, int status,
		int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getGroupsEntries(companyId, groupId,
			displayDate, status, max);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		long organizationId, java.util.Date displayDate, int status, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getOrganizationEntries(organizationId,
			displayDate, status, max);
	}

	public java.lang.String getOrganizationEntriesRSS(long organizationId,
		java.util.Date displayDate, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntryService.getOrganizationEntriesRSS(organizationId,
			displayDate, status, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay);
	}

	public void moveEntryToTrash(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryService.moveEntryToTrash(entryId);
	}

	public void restoreEntryFromTrash(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryService.restoreEntryFromTrash(entryId);
	}

	public void subscribe(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryService.subscribe(groupId);
	}

	public void unsubscribe(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsEntryService.unsubscribe(groupId);
	}

	public com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
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
		return _blogsEntryService.updateEntry(entryId, title, description,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, smallImage, smallImageURL,
			smallImageFileName, smallImageInputStream, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public BlogsEntryService getWrappedBlogsEntryService() {
		return _blogsEntryService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedBlogsEntryService(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	public BlogsEntryService getWrappedService() {
		return _blogsEntryService;
	}

	public void setWrappedService(BlogsEntryService blogsEntryService) {
		_blogsEntryService = blogsEntryService;
	}

	private BlogsEntryService _blogsEntryService;
}