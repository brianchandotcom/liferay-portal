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

package com.liferay.wiki.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.RSSUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.base.WikiPageServiceBaseImpl;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.service.permission.WikiPagePermissionChecker;
import com.liferay.wiki.util.WikiUtil;
import com.liferay.wiki.util.comparator.PageCreateDateComparator;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.FeedException;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Provides the remote service for accessing, adding, deleting, moving,
 * subscription handling of, trash handling of, and updating wiki pages and wiki
 * page attachments. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class WikiPageServiceImpl extends WikiPageServiceBaseImpl {

	@Override
	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, ServiceContext serviceContext)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			getUserId(), nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	@Override
	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, String format, String parentTitle,
			String redirectTitle, ServiceContext serviceContext)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			getUserId(), nodeId, title, WikiPageConstants.VERSION_DEFAULT,
			content, summary, minorEdit, format, true, parentTitle,
			redirectTitle, serviceContext);
	}

	@Override
	public FileEntry addPageAttachment(
			long nodeId, String title, String fileName, File file,
			String mimeType)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addPageAttachment(
			getUserId(), nodeId, title, fileName, file, mimeType);
	}

	@Override
	public FileEntry addPageAttachment(
			long nodeId, String title, String fileName, InputStream inputStream,
			String mimeType)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addPageAttachment(
			getUserId(), nodeId, title, fileName, inputStream, mimeType);
	}

	@Override
	public List<FileEntry> addPageAttachments(
			long nodeId, String title,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addPageAttachments(
			getUserId(), nodeId, title, inputStreamOVPs);
	}

	@Override
	public FileEntry addTempFileEntry(
			long nodeId, String folderName, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		WikiNode node = wikiNodeLocalService.getNode(nodeId);

		WikiNodePermissionChecker.check(
			getPermissionChecker(), node, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addTempFileEntry(
			node.getGroupId(), getUserId(), folderName, fileName, inputStream,
			mimeType);
	}

	/**
	 * @deprecated As of 7.0.0 replaced by {@link #addTempFileEntry(long,
	 *             String, String, InputStream, String)}
	 */
	@Deprecated
	@Override
	public void addTempPageAttachment(
			long nodeId, String fileName, String tempFolderName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		addTempFileEntry(
			nodeId, tempFolderName, fileName, inputStream, mimeType);
	}

	@Override
	public void changeNode(
			long nodeId, String title, long newNodeId,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		WikiNodePermissionChecker.check(
			getPermissionChecker(), newNodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.changeNode(
			getUserId(), nodeId, title, newNodeId, serviceContext);
	}

	@Override
	public void changeParent(
			long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.changeParent(
			getUserId(), nodeId, title, newParentTitle, serviceContext);
	}

	@Override
	public void copyPageAttachments(
			long templateNodeId, String templateTitle, long nodeId,
			String title)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.copyPageAttachments(
			getUserId(), templateNodeId, templateTitle, nodeId, title);
	}

	@Override
	public void deletePage(long nodeId, String title) throws PortalException {
		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePage(nodeId, title);
	}

	/**
	 * @deprecated As of 6.2.0 replaced by {@link #discardDraft(long, String,
	 *             double)}
	 */
	@Deprecated
	@Override
	public void deletePage(long nodeId, String title, double version)
		throws PortalException {

		discardDraft(nodeId, title, version);
	}

	@Override
	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	@Override
	public void deletePageAttachments(long nodeId, String title)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePageAttachments(nodeId, title);
	}

	@Override
	public void deleteTempFileEntry(
			long nodeId, String folderName, String fileName)
		throws PortalException {

		WikiNode node = wikiNodeLocalService.getNode(nodeId);

		WikiNodePermissionChecker.check(
			getPermissionChecker(), node, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.deleteTempFileEntry(
			node.getGroupId(), getUserId(), folderName, fileName);
	}

	@Override
	public void deleteTrashPageAttachments(long nodeId, String title)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deleteTrashPageAttachments(nodeId, title);
	}

	@Override
	public void discardDraft(long nodeId, String title, double version)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, version, ActionKeys.DELETE);

		wikiPageLocalService.discardDraft(nodeId, title, version);
	}

	@Override
	public WikiPage fetchPage(long nodeId, String title, double version)
		throws PortalException {

		WikiPage page = wikiPageLocalService.fetchPage(nodeId, title, version);

		if (page != null) {
			WikiPagePermissionChecker.check(
				getPermissionChecker(), page, ActionKeys.VIEW);
		}

		return page;
	}

	@Override
	public List<WikiPage> getChildren(
			long groupId, long nodeId, boolean head, String parentTitle)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		return wikiPagePersistence.filterFindByG_N_H_P_S(
			groupId, nodeId, head, parentTitle,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public WikiPage getDraftPage(long nodeId, String title)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getDraftPage(nodeId, title);
	}

	@Override
	public List<WikiPage> getNodePages(long nodeId, int max)
		throws PortalException {

		List<WikiPage> pages = new ArrayList<>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;

		while ((pages.size() < max) && listNotExhausted) {
			List<WikiPage> pageList = wikiPageLocalService.getPages(
				nodeId, true, lastIntervalStart, lastIntervalStart + max);

			lastIntervalStart += max;
			listNotExhausted = (pageList.size() == max);

			for (WikiPage page : pageList) {
				if (pages.size() >= max) {
					break;
				}

				if (WikiPagePermissionChecker.contains(
						getPermissionChecker(), page, ActionKeys.VIEW)) {

					pages.add(page);
				}
			}
		}

		return pages;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getNodePagesRSS(long, int,
	 *             String, double, String, String, String, String)}
	 */
	@Deprecated
	@Override
	public String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL)
		throws PortalException {

		return getNodePagesRSS(
			nodeId, max, type, version, displayStyle, feedURL, entryURL, null);
	}

	@Override
	public String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		List<WikiPage> pages = getNodePages(nodeId, max);

		return exportToRSS(
			node.getName(), node.getDescription(), type, version, displayStyle,
			feedURL, entryURL, attachmentURLPrefix, pages, false, null);
	}

	@Override
	public List<WikiPage> getOrphans(long groupId, long nodeId)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		List<WikiPage> pages = wikiPagePersistence.filterFindByG_N_H_S(
			groupId, nodeId, true, WorkflowConstants.STATUS_APPROVED);

		return WikiUtil.filterOrphans(pages);
	}

	@Override
	public WikiPage getPage(long groupId, long nodeId, String title)
		throws PortalException {

		List<WikiPage> pages = wikiPagePersistence.filterFindByG_N_T_H(
			groupId, nodeId, title, true, 0, 1);

		if (!pages.isEmpty()) {
			return pages.get(0);
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append("{nodeId=");
			sb.append(nodeId);
			sb.append(", title=");
			sb.append(title);
			sb.append("}");

			throw new NoSuchPageException(sb.toString());
		}
	}

	@Override
	public WikiPage getPage(long nodeId, String title) throws PortalException {
		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title);
	}

	@Override
	public WikiPage getPage(long nodeId, String title, Boolean head)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title, head);
	}

	@Override
	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title, version);
	}

	@Override
	public List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, int status, int start,
			int end, OrderByComparator<WikiPage> obc)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		if (status == WorkflowConstants.STATUS_ANY) {
			return wikiPagePersistence.filterFindByG_N_H(
				groupId, nodeId, head, start, end, obc);
		}
		else {
			return wikiPagePersistence.filterFindByG_N_H_S(
				groupId, nodeId, head, status, start, end, obc);
		}
	}

	@Override
	public List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status, int start, int end,
			OrderByComparator<WikiPage> obc)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		QueryDefinition<WikiPage> queryDefinition = new QueryDefinition<>(
			status, userId, includeOwner);

		queryDefinition.setEnd(end);
		queryDefinition.setOrderByComparator(obc);
		queryDefinition.setStart(start);

		return wikiPageFinder.filterFindByG_N_H_S(
			groupId, nodeId, head, queryDefinition);
	}

	@Override
	public List<WikiPage> getPages(
			long groupId, long userId, long nodeId, int status, int start,
			int end)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		if (userId > 0) {
			return wikiPagePersistence.filterFindByG_U_N_S(
				groupId, userId, nodeId, status, start, end,
				new PageCreateDateComparator(false));
		}
		else {
			return wikiPagePersistence.filterFindByG_N_S(
				groupId, nodeId, status, start, end,
				new PageCreateDateComparator(false));
		}
	}

	@Override
	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		return wikiPagePersistence.filterCountByG_N_H_S(
			groupId, nodeId, head, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getPagesCount(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		QueryDefinition<WikiPage> queryDefinition = new QueryDefinition<>(
			status, userId, includeOwner);

		return wikiPageFinder.filterCountByG_N_H_S(
			groupId, nodeId, head, queryDefinition);
	}

	@Override
	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		if (userId > 0) {
			return wikiPagePersistence.filterCountByG_U_N_S(
				groupId, userId, nodeId, status);
		}
		else {
			return wikiPagePersistence.filterCountByG_N_S(
				groupId, nodeId, status);
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getPagesRSS(long, String,
	 *             int, String, double, String, String, String, String,
	 *             java.util.Locale)}
	 */
	@Deprecated
	@Override
	public String getPagesRSS(
			long companyId, long nodeId, String title, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, Locale locale)
		throws PortalException {

		return getPagesRSS(
			nodeId, title, max, type, version, displayStyle, feedURL, entryURL,
			null, locale);
	}

	@Override
	public String getPagesRSS(
			long nodeId, String title, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix, Locale locale)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		List<WikiPage> pages = wikiPageLocalService.getPages(
			nodeId, title, 0, max, new PageCreateDateComparator(true));

		return exportToRSS(
			title, title, type, version, displayStyle, feedURL, entryURL,
			attachmentURLPrefix, pages, true, locale);
	}

	@Override
	public List<WikiPage> getRecentChanges(
			long groupId, long nodeId, int start, int end)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.filterFindByCreateDate(
			groupId, nodeId, calendar.getTime(), false, start, end);
	}

	@Override
	public int getRecentChangesCount(long groupId, long nodeId)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.filterCountByCreateDate(
			groupId, nodeId, calendar.getTime(), false);
	}

	@Override
	public String[] getTempFileNames(long nodeId, String folderName)
		throws PortalException {

		WikiNode node = wikiNodeLocalService.getNode(nodeId);

		WikiNodePermissionChecker.check(
			getPermissionChecker(), node, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.getTempFileNames(
			node.getGroupId(), getUserId(), folderName);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #renamePage(long, String,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void movePage(
			long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException {

		renamePage(nodeId, title, newTitle, serviceContext);
	}

	@Override
	public FileEntry movePageAttachmentToTrash(
			long nodeId, String title, String fileName)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		return wikiPageLocalService.movePageAttachmentToTrash(
			getUserId(), nodeId, title, fileName);
	}

	@Override
	public WikiPage movePageToTrash(long nodeId, String title)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		return wikiPageLocalService.movePageToTrash(getUserId(), nodeId, title);
	}

	@Override
	public WikiPage movePageToTrash(long nodeId, String title, double version)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, version, ActionKeys.DELETE);

		return wikiPageLocalService.movePageToTrash(
			getUserId(), nodeId, title, version);
	}

	@Override
	public void renamePage(
			long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.renamePage(
			getUserId(), nodeId, title, newTitle, serviceContext);
	}

	@Override
	public void restorePageAttachmentFromTrash(
			long nodeId, String title, String fileName)
		throws PortalException {

		WikiNodePermissionChecker.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.restorePageAttachmentFromTrash(
			getUserId(), nodeId, title, fileName);
	}

	@Override
	public void restorePageFromTrash(long resourcePrimKey)
		throws PortalException {

		WikiPage page = wikiPageLocalService.getPage(resourcePrimKey);

		WikiPagePermissionChecker.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		wikiPageLocalService.restorePageFromTrash(getUserId(), page);
	}

	@Override
	public WikiPage revertPage(
			long nodeId, String title, double version,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return wikiPageLocalService.revertPage(
			getUserId(), nodeId, title, version, serviceContext);
	}

	@Override
	public void subscribePage(long nodeId, String title)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.SUBSCRIBE);

		wikiPageLocalService.subscribePage(getUserId(), nodeId, title);
	}

	@Override
	public void unsubscribePage(long nodeId, String title)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.SUBSCRIBE);

		wikiPageLocalService.unsubscribePage(getUserId(), nodeId, title);
	}

	@Override
	public WikiPage updatePage(
			long nodeId, String title, double version, String content,
			String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPagePermissionChecker.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return wikiPageLocalService.updatePage(
			getUserId(), nodeId, title, version, content, summary, minorEdit,
			format, parentTitle, redirectTitle, serviceContext);
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix, List<WikiPage> pages, boolean diff,
			Locale locale)
		throws PortalException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<>();

		syndFeed.setEntries(syndEntries);

		WikiPage latestPage = null;

		StringBundler sb = new StringBundler(6);

		for (WikiPage page : pages) {
			SyndEntry syndEntry = new SyndEntryImpl();

			String author = PortalUtil.getUserName(page);

			syndEntry.setAuthor(author);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			sb.setIndex(0);

			sb.append(entryURL);

			if (entryURL.endsWith(StringPool.SLASH)) {
				sb.append(HttpUtil.encodeURL(page.getTitle()));
			}

			if (diff) {
				if ((latestPage != null) || (pages.size() == 1)) {
					sb.append(StringPool.QUESTION);
					sb.append(
						PortalUtil.getPortletNamespace(WikiPortletKeys.WIKI));
					sb.append("version=");
					sb.append(page.getVersion());

					String value = null;

					if (latestPage == null) {
						value = WikiUtil.convert(
							page, null, null, attachmentURLPrefix);
					}
					else {
						try {
							value = WikiUtil.diffHtml(
								latestPage, page, null, null,
								attachmentURLPrefix);
						}
						catch (PortalException pe) {
							throw pe;
						}
						catch (SystemException se) {
							throw se;
						}
						catch (Exception e) {
							throw new SystemException(e);
						}
					}

					syndContent.setValue(value);

					syndEntry.setDescription(syndContent);

					syndEntries.add(syndEntry);
				}
			}
			else {
				String value = null;

				WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
					configurationFactory.getConfiguration(
						WikiGroupServiceOverriddenConfiguration.class,
						new GroupServiceSettingsLocator(
							page.getGroupId(), WikiConstants.SERVICE_NAME));

				if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
					value = StringUtil.shorten(
						HtmlUtil.extractText(page.getContent()),
						wikiGroupServiceConfiguration.rssAbstractLength(),
						StringPool.BLANK);
				}
				else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
					value = StringPool.BLANK;
				}
				else {
					value = WikiUtil.convert(
						page, null, null, attachmentURLPrefix);
				}

				syndContent.setValue(value);

				syndEntry.setDescription(syndContent);

				syndEntries.add(syndEntry);
			}

			syndEntry.setLink(sb.toString());
			syndEntry.setPublishedDate(page.getCreateDate());

			String title =
				page.getTitle() + StringPool.SPACE + page.getVersion();

			if (page.isMinorEdit()) {
				title +=
					StringPool.SPACE + StringPool.OPEN_PARENTHESIS +
						LanguageUtil.get(locale, "minor-edit") +
							StringPool.CLOSE_PARENTHESIS;
			}

			syndEntry.setTitle(title);

			syndEntry.setUpdatedDate(page.getModifiedDate());
			syndEntry.setUri(sb.toString());

			latestPage = page;
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));

		List<SyndLink> syndLinks = new ArrayList<>();

		syndFeed.setLinks(syndLinks);

		SyndLink syndLinkSelf = new SyndLinkImpl();

		syndLinks.add(syndLinkSelf);

		syndLinkSelf.setHref(feedURL);
		syndLinkSelf.setRel("self");

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

	@ServiceReference(type = ConfigurationFactory.class)
	protected ConfigurationFactory configurationFactory;

}