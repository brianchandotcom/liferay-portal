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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.base.BlogsEntryServiceBaseImpl;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.blogs.util.comparator.EntryDisplayDateComparator;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryServiceImpl extends BlogsEntryServiceBaseImpl {

	public BlogsEntry addEntry(
			String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BlogsPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return blogsEntryLocalService.addEntry(
			getUserId(), title, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		blogsEntryLocalService.deleteEntry(entryId);
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, int status, int max)
		throws PortalException, SystemException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;

		while ((entries.size() < max) && listNotExhausted) {
			List<BlogsEntry> entryList =
				blogsEntryLocalService.getCompanyEntries(
					companyId, status, lastIntervalStart,
					lastIntervalStart + max, new EntryDisplayDateComparator());

			Iterator<BlogsEntry> itr = entryList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (entryList.size() == max);

			while (itr.hasNext() && (entries.size() < max)) {
				BlogsEntry entry = itr.next();

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	public String getCompanyEntriesRSS(
			long companyId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String name = company.getName();
		String description = name;
		List<BlogsEntry> blogsEntries = getCompanyEntries(
			companyId, status, max);

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	public BlogsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return blogsEntryLocalService.getEntry(entryId);
	}

	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryLocalService.getEntry(groupId, urlTitle);

		BlogsEntryPermission.check(
			getPermissionChecker(), entry.getEntryId(), ActionKeys.VIEW);

		return entry;
	}

	public List<BlogsEntry> getGroupEntries(long groupId, int status, int max)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.filterFindByGroupId(groupId, 0, max);
		}
		else {
			return blogsEntryPersistence.filterFindByG_S(
				groupId, status, 0, max);
		}
	}

	public List<BlogsEntry> getGroupEntries(
			long groupId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.filterFindByGroupId(
				groupId, start, end);
		}
		else {
			return blogsEntryPersistence.filterFindByG_S(
				groupId, status, start, end);
		}
	}

	public int getGroupEntriesCount(long groupId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.filterCountByGroupId(groupId);
		}
		else {
			return blogsEntryPersistence.filterCountByG_S(groupId, status);
		}
	}

	public String getGroupEntriesRSS(
			long groupId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		String name = HtmlUtil.escape(group.getDescriptiveName());
		String description = name;
		List<BlogsEntry> blogsEntries = getGroupEntries(groupId, status, max);

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	public List<BlogsEntry> getGroupsEntries(
			long companyId, long groupId, int status, int max)
		throws PortalException, SystemException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;

		while ((entries.size() < max) && listNotExhausted) {
			List<BlogsEntry> entryList =
				blogsEntryLocalService.getGroupsEntries(
					companyId, groupId, status, lastIntervalStart,
					lastIntervalStart + max);

			Iterator<BlogsEntry> itr = entryList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (entryList.size() == max);

			while (itr.hasNext() && (entries.size() < max)) {
				BlogsEntry entry = itr.next();

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, int status, int max)
		throws PortalException, SystemException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		Date displayDate = new Date();
		int lastIntervalStart = 0;
		boolean listNotExhausted = true;

		while ((entries.size() < max) && listNotExhausted) {
			List<BlogsEntry> entryList = blogsEntryFinder.findByOrganizationId(
				organizationId, displayDate, status, lastIntervalStart,
				lastIntervalStart + max);

			Iterator<BlogsEntry> itr = entryList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (entryList.size() == max);

			while (itr.hasNext() && (entries.size() < max)) {
				BlogsEntry entry = itr.next();

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	public String getOrganizationEntriesRSS(
			long organizationId, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		String name = organization.getName();
		String description = name;
		List<BlogsEntry> blogsEntries = getOrganizationEntries(
			organizationId, status, max);

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	public void subscribe(long groupId)
		throws PortalException, SystemException {

		BlogsPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		blogsEntryLocalService.subscribe(getUserId(), groupId);
	}

	public void unsubscribe(long groupId)
		throws PortalException, SystemException {

		BlogsPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		blogsEntryLocalService.unsubscribe(getUserId(), groupId);
	}

	public BlogsEntry updateEntry(
			long entryId, String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return blogsEntryLocalService.updateEntry(
			getUserId(), entryId, title, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			List<BlogsEntry> blogsEntries, ThemeDisplay themeDisplay)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		for (BlogsEntry entry : blogsEntries) {
			String author = HtmlUtil.escape(
				PortalUtil.getUserName(entry.getUserId(), entry.getUserName()));

			StringBundler link = new StringBundler(4);

			if (entryURL.endsWith("/blogs/rss")) {
				link.append(entryURL.substring(0, entryURL.length() - 3));
				link.append(entry.getUrlTitle());
			}
			else {
				link.append(entryURL);

				if (!entryURL.endsWith(StringPool.QUESTION)) {
					link.append(StringPool.AMPERSAND);
				}

				link.append("entryId=");
				link.append(entry.getEntryId());
			}

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
				value = StringUtil.shorten(
					HtmlUtil.extractText(entry.getDescription()),
					PropsValues.BLOGS_RSS_ABSTRACT_LENGTH, StringPool.BLANK);
			}
			else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = StringUtil.replace(
					entry.getContent(),
					new String[] {
						"href=\"/",
						"src=\"/"
					},
					new String[] {
						"href=\"" + themeDisplay.getURLPortal() + "/",
						"src=\"" + themeDisplay.getURLPortal() + "/"
					}
				);
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);
			syndEntry.setTitle(entry.getTitle());
			syndEntry.setLink(link.toString());
			syndEntry.setUri(syndEntry.getLink());
			syndEntry.setPublishedDate(entry.getCreateDate());
			syndEntry.setUpdatedDate(entry.getModifiedDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);
			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			syndEntries.add(syndEntry);
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

}