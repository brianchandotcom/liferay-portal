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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.blogs.service.base.BlogsEntryServiceBaseImpl;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * Provides the remote service for accessing, adding, deleting, subscription
 * handling of, trash handling of, and updating blog entries. Its methods
 * include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.blogs.service.impl.BlogsEntryServiceImpl}
 */
@Deprecated
public class BlogsEntryServiceImpl extends BlogsEntryServiceBaseImpl {

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addEntry(String, String,
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

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
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

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getCompanyEntries(
			long companyId, Date displayDate, int status, int max)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public String getCompanyEntriesRSS(
			long companyId, Date displayDate, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public BlogsEntry getEntry(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate, int status, int max) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate, int status, int start, int end) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupEntries(long groupId, int status, int max) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end,
		OrderByComparator<BlogsEntry> obc) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public int getGroupEntriesCount(
		long groupId, Date displayDate, int status) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public int getGroupEntriesCount(long groupId, int status) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public String getGroupEntriesRSS(
			long groupId, Date displayDate, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupsEntries(
			long companyId, long groupId, Date displayDate, int status, int max)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int status, int start, int end,
		OrderByComparator<BlogsEntry> obc) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getGroupUserEntries(
		long groupId, long userId, int[] statuses, int start, int end,
		OrderByComparator<BlogsEntry> obc) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public int getGroupUserEntriesCount(long groupId, long userId, int status) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public int getGroupUserEntriesCount(
		long groupId, long userId, int[] statuses) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, Date displayDate, int status, int max)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public String getOrganizationEntriesRSS(
			long organizationId, Date displayDate, int status, int max,
			String type, double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public BlogsEntry moveEntryToTrash(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public void restoreEntryFromTrash(long entryId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public void subscribe(long groupId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	@Override
	public void unsubscribe(long groupId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, String,
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

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
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

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.blogs.service.impl.BlogsEntryServiceImpl");
	}

}