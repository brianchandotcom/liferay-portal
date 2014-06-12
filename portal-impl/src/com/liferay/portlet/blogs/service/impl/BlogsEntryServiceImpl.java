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

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.FeedException;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides the remote service for accessing, adding, deleting, subscription
 * handling of, trash handling of, and updating blog entries. Its methods
 * include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class BlogsEntryServiceImpl extends BlogsEntryServiceBaseImpl {

	@Override
	public BlogsEntry addEntry(
			String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException {

		return addEntry(
			title, StringPool.BLANK, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);
	}

	@Override
	public BlogsEntry addEntry(
			String title, String deckTitle, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException {

		BlogsPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return blogsEntryLocalService.addEntry(
			getUserId(), title, deckTitle, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			smallImage, smallImageURL, smallImageFileName,
			smallImageInputStream, serviceContext);
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		blogsEntryLocalService.deleteEntry(entryId);
	}

	@Override
	public List<BlogsEntry> getCompanyEntries(
			long companyId, Date displayDate, int status, int max)
		throws PortalException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		boolean listNotExhausted = true;

		QueryDefinition queryDefinition = new QueryDefinition(
			status, false, 0, 0, new EntryDisplayDateComparator());

		if (status == WorkflowConstants.STATUS_ANY) {
			queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}

		while ((entries.size() < max) && listNotExhausted) {
			queryDefinition.setEnd(queryDefinition.getStart() + max);

			List<BlogsEntry> entryList =
				blogsEntryLocalService.getCompanyEntries(
					companyId, displayDate, queryDefinition);

			queryDefinition.setStart(queryDefinition.getStart() + max);

			listNotExhausted = (entryList.size() == max);

			for (BlogsEntry entry : entryList) {
				if (entries.size() >= max) {
					break;
				}

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	@Override
	public String getCompanyEntriesRSS(
			long companyId, Date displayDate, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String name = company.getName();
		List<BlogsEntry> blogsEntries = getCompanyEntries(
			companyId, displayDate, status, max);

		return exportToRSS(
			name, name, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	@Override
	public BlogsEntry getEntry(long entryId) throws PortalException {
		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return blogsEntryLocalService.getEntry(entryId);
	}

	@Override
	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException {

		BlogsEntry entry = blogsEntryLocalService.getEntry(groupId, urlTitle);

		BlogsEntryPermission.check(
			getPermissionChecker(), entry.getEntryId(), ActionKeys.VIEW);

		return entry;
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate, int status, int max) {

		return getGroupEntries(groupId, displayDate, status, 0, max);
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.filterFindByG_LtD_NotS(
				groupId, displayDate, WorkflowConstants.STATUS_IN_TRASH, start,
				end);
		}
		else {
			return blogsEntryPersistence.filterFindByG_LtD_S(
				groupId, displayDate, status, start, end);
		}
	}

	@Override
	public List<BlogsEntry> getGroupEntries(long groupId, int status, int max) {

		return getGroupEntries(groupId, status, 0, max);
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.filterFindByG_NotS(
				groupId, WorkflowConstants.STATUS_IN_TRASH, start, end);
		}
		else {
			return blogsEntryPersistence.filterFindByG_S(
				groupId, status, start, end);
		}
	}

	@Override
	public int getGroupEntriesCount(
		long groupId, Date displayDate, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.filterCountByG_LtD_NotS(
				groupId, displayDate, WorkflowConstants.STATUS_IN_TRASH);
		}
		else {
			return blogsEntryPersistence.filterCountByG_LtD_S(
				groupId, displayDate, status);
		}
	}

	@Override
	public int getGroupEntriesCount(long groupId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.filterCountByG_NotS(
				groupId, WorkflowConstants.STATUS_IN_TRASH);
		}
		else {
			return blogsEntryPersistence.filterCountByG_S(groupId, status);
		}
	}

	@Override
	public String getGroupEntriesRSS(
			long groupId, Date displayDate, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		String name = group.getDescriptiveName();
		List<BlogsEntry> blogsEntries = getGroupEntries(
			groupId, displayDate, status, max);

		return exportToRSS(
			name, name, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	@Override
	public List<BlogsEntry> getGroupsEntries(
			long companyId, long groupId, Date displayDate, int status, int max)
		throws PortalException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		boolean listNotExhausted = true;

		QueryDefinition queryDefinition = new QueryDefinition(
			status, false, 0, 0, new EntryDisplayDateComparator());

		if (status == WorkflowConstants.STATUS_ANY) {
			queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}

		while ((entries.size() < max) && listNotExhausted) {
			queryDefinition.setEnd(queryDefinition.getStart() + max);

			List<BlogsEntry> entryList =
				blogsEntryLocalService.getGroupsEntries(
					companyId, groupId, displayDate, queryDefinition);

			queryDefinition.setStart(queryDefinition.getStart() + max);

			listNotExhausted = (entryList.size() == max);

			for (BlogsEntry entry : entryList) {
				if (entries.size() >= max) {
					break;
				}

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	@Override
	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, Date displayDate, int status, int max)
		throws PortalException {

		List<BlogsEntry> entries = new ArrayList<BlogsEntry>();

		boolean listNotExhausted = true;

		QueryDefinition queryDefinition = new QueryDefinition(
			status, false, 0, 0, new EntryDisplayDateComparator());

		if (status == WorkflowConstants.STATUS_ANY) {
			queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}

		while ((entries.size() < max) && listNotExhausted) {
			queryDefinition.setEnd(queryDefinition.getStart() + max);

			List<BlogsEntry> entryList = blogsEntryFinder.findByOrganizationId(
				organizationId, displayDate, queryDefinition);

			queryDefinition.setStart(queryDefinition.getStart() + max);

			listNotExhausted = (entryList.size() == max);

			for (BlogsEntry entry : entryList) {
				if (entries.size() >= max) {
					break;
				}

				if (BlogsEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
		}

		return entries;
	}

	@Override
	public String getOrganizationEntriesRSS(
			long organizationId, Date displayDate, int status, int max,
			String type, double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		String name = organization.getName();
		List<BlogsEntry> blogsEntries = getOrganizationEntries(
			organizationId, displayDate, status, max);

		return exportToRSS(
			name, name, type, version, displayStyle, feedURL, entryURL,
			blogsEntries, themeDisplay);
	}

	@Override
	public BlogsEntry moveEntryToTrash(long entryId) throws PortalException {
		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		return blogsEntryLocalService.moveEntryToTrash(getUserId(), entryId);
	}

	@Override
	public void restoreEntryFromTrash(long entryId) throws PortalException {
		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		blogsEntryLocalService.restoreEntryFromTrash(getUserId(), entryId);
	}

	@Override
	public void subscribe(long groupId) throws PortalException {
		BlogsPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		blogsEntryLocalService.subscribe(getUserId(), groupId);
	}

	@Override
	public void unsubscribe(long groupId) throws PortalException {
		BlogsPermission.check(
			getPermissionChecker(), groupId, ActionKeys.SUBSCRIBE);

		blogsEntryLocalService.unsubscribe(getUserId(), groupId);
	}

	@Override
	public BlogsEntry updateEntry(
			long entryId, String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException {

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
		List<BlogsEntry> blogsEntries, ThemeDisplay themeDisplay) {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		for (BlogsEntry entry : blogsEntries) {
			SyndEntry syndEntry = new SyndEntryImpl();

			String author = PortalUtil.getUserName(entry);

			syndEntry.setAuthor(author);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
				String summary = entry.getDescription();

				if (Validator.isNull(summary)) {
					summary = entry.getContent();
				}

				value = StringUtil.shorten(
					HtmlUtil.extractText(summary),
					PropsValues.BLOGS_RSS_ABSTRACT_LENGTH, StringPool.BLANK);
			}
			else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = StringUtil.replace(
					entry.getContent(),
					new String[] {
						"href=\"/", "src=\"/"
					},
					new String[] {
						"href=\"" + themeDisplay.getURLPortal() + "/",
						"src=\"" + themeDisplay.getURLPortal() + "/"
					});
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			StringBundler sb = new StringBundler(4);

			if (entryURL.endsWith("/blogs/rss")) {
				sb.append(entryURL.substring(0, entryURL.length() - 3));
				sb.append(entry.getUrlTitle());
			}
			else {
				sb.append(entryURL);

				if (!entryURL.endsWith(StringPool.QUESTION)) {
					sb.append(StringPool.AMPERSAND);
				}

				sb.append("entryId=");
				sb.append(entry.getEntryId());
			}

			String link = sb.toString();

			syndEntry.setLink(link);

			syndEntry.setPublishedDate(entry.getDisplayDate());
			syndEntry.setTitle(entry.getTitle());
			syndEntry.setUpdatedDate(entry.getModifiedDate());
			syndEntry.setUri(link);

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));

		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		selfSyndLink.setHref(feedURL);
		selfSyndLink.setRel("self");

		if (feedURL.endsWith("/-/blogs/rss")) {
			SyndLink alternateSyndLink = new SyndLinkImpl();

			syndLinks.add(alternateSyndLink);

			alternateSyndLink.setHref(
				feedURL.substring(0, feedURL.length() - 12));
			alternateSyndLink.setRel("alternate");
		}

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(name);
		syndFeed.setUri(feedURL);

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

}