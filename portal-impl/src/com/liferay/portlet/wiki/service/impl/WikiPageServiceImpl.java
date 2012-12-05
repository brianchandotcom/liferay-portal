/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Diff;
import com.liferay.portal.kernel.util.DiffResult;
import com.liferay.portal.kernel.util.DiffUtil;
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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.base.WikiPageServiceBaseImpl;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;
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

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class WikiPageServiceImpl extends WikiPageServiceBaseImpl {

	public WikiPageServiceImpl() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		String templateId = "com/liferay/portlet/wiki/dependencies/rss.vm";

		URL url = classLoader.getResource(templateId);

		_templateResource = new URLTemplateResource(templateId, url);
	}

	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			getUserId(), nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, String format, String parentTitle,
			String redirectTitle, ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			getUserId(), nodeId, title, WikiPageConstants.VERSION_DEFAULT,
			content, summary, minorEdit, format, true, parentTitle,
			redirectTitle, serviceContext);
	}

	public void addPageAttachment(
			long nodeId, String title, String fileName, File file)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.addPageAttachment(
			getUserId(), nodeId, title, fileName, file);
	}

	public void addPageAttachment(
			long nodeId, String title, String fileName, InputStream inputStream)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.addPageAttachment(
			getUserId(), nodeId, title, fileName, inputStream);
	}

	public void addPageAttachments(
			long nodeId, String title,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.addPageAttachments(
			getUserId(), nodeId, title, inputStreamOVPs);
	}

	public String addTempPageAttachment(
			long nodeId, String fileName, String tempFolderName,
			InputStream inputStream)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addTempPageAttachment(
			getUserId(), fileName, tempFolderName, inputStream);
	}

	public void changeParent(
			long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.changeParent(
			getUserId(), nodeId, title, newParentTitle, serviceContext);
	}

	public void deletePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePage(nodeId, title);
	}

	public void deletePage(long nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, version, ActionKeys.DELETE);

		wikiPageLocalService.deletePage(nodeId, title, version);
	}

	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	public void deletePageAttachments(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deletePageAttachments(nodeId, title);
	}

	public void deleteTempPageAttachment(
			long nodeId, String fileName, String tempFolderName)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.deleteTempPageAttachment(
			getUserId(), fileName, tempFolderName);
	}

	public void deleteTrashPageAttachments(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.deleteTrashPageAttachments(nodeId, title);
	}

	public List<WikiPage> getChildren(
			long groupId, long nodeId, boolean head, String parentTitle)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		return wikiPagePersistence.filterFindByG_N_H_P_S(
			groupId, nodeId, head, parentTitle,
			WorkflowConstants.STATUS_APPROVED);
	}

	public WikiPage getDraftPage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getDraftPage(nodeId, title);
	}

	public List<WikiPage> getNodePages(long nodeId, int max)
		throws PortalException, SystemException {

		List<WikiPage> pages = new ArrayList<WikiPage>();

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

				if (WikiPagePermission.contains(
						getPermissionChecker(), page, ActionKeys.VIEW)) {

					pages.add(page);
				}
			}
		}

		return pages;
	}

	public String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		long companyId = node.getCompanyId();
		String name = node.getName();
		String description = node.getDescription();
		List<WikiPage> pages = getNodePages(nodeId, max);
		boolean diff = false;
		Locale locale = null;

		return exportToRSS(
			companyId, name, description, type, version, displayStyle, feedURL,
			entryURL, pages, diff, locale);
	}

	public List<WikiPage> getOrphans(long groupId, long nodeId)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		List<WikiPage> pages = wikiPagePersistence.filterFindByG_N_H_S(
			groupId, nodeId, true, WorkflowConstants.STATUS_APPROVED);

		return WikiUtil.filterOrphans(pages);
	}

	public WikiPage getPage(long groupId, long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.filterFindByG_N_T_H(
			groupId, nodeId, title, true, 0, 1);

		if (!pages.isEmpty()) {
			return pages.get(0);
		}
		else {
			throw new NoSuchPageException();
		}
	}

	public WikiPage getPage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title);
	}

	public WikiPage getPage(long nodeId, String title, Boolean head)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title, head);
	}

	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		return wikiPageLocalService.getPage(nodeId, title, version);
	}

	public List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, int status, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		WikiNodePermission.check(
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

	public List<WikiPage> getPages(
			long groupId, long userId, long nodeId, int status, int start,
			int end)
		throws PortalException, SystemException {

		WikiNodePermission.check(
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

	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		return wikiPagePersistence.filterCountByG_N_H_S(
			groupId, nodeId, head, WorkflowConstants.STATUS_APPROVED);
	}

	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws PortalException, SystemException {

		WikiNodePermission.check(
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

	public String getPagesRSS(
			long companyId, long nodeId, String title, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, Locale locale)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.VIEW);

		String description = title;
		List<WikiPage> pages = wikiPageLocalService.getPages(
			nodeId, title, 0, max, new PageCreateDateComparator(true));
		boolean diff = true;

		return exportToRSS(
			companyId, title, description, type, version, displayStyle, feedURL,
			entryURL, pages, diff, locale);
	}

	public List<WikiPage> getRecentChanges(
			long groupId, long nodeId, int start, int end)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.filterFindByCreateDate(
			groupId, nodeId, calendar.getTime(), false, start, end);
	}

	public int getRecentChangesCount(long groupId, long nodeId)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.filterCountByCreateDate(
			groupId, nodeId, calendar.getTime(), false);
	}

	public String[] getTempPageAttachmentNames(
			long nodeId, String tempFolderName)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.getTempPageAttachmentNames(
			getUserId(), tempFolderName);
	}

	public void movePage(
			long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.movePage(
			getUserId(), nodeId, title, newTitle, serviceContext);
	}

	public long movePageAttachmentToTrash(
			long nodeId, String title, String fileName)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		return wikiPageLocalService.movePageAttachmentToTrash(
			getUserId(), nodeId, title, fileName);
	}

	public void movePageToTrash(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.DELETE);

		wikiPageLocalService.movePageToTrash(getUserId(), nodeId, title);
	}

	public void movePageToTrash(long nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, version, ActionKeys.DELETE);

		wikiPageLocalService.movePageToTrash(
			getUserId(), nodeId, title, version);
	}

	public void restorePageAttachmentFromTrash(
			long nodeId, String title, String fileName)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.restorePageAttachmentFromTrash(
			getUserId(), nodeId, title, fileName);
	}

	public void restorePageFromTrash(long resourcePrimKey)
		throws PortalException, SystemException {

		WikiPage page = wikiPageLocalService.getPage(resourcePrimKey);

		WikiPagePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		wikiPageLocalService.restorePageFromTrash(getUserId(), page);
	}

	public WikiPage revertPage(
			long nodeId, String title, double version,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return wikiPageLocalService.revertPage(
			getUserId(), nodeId, title, version, serviceContext);
	}

	public void subscribePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.SUBSCRIBE);

		wikiPageLocalService.subscribePage(getUserId(), nodeId, title);
	}

	public void unsubscribePage(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.SUBSCRIBE);

		wikiPageLocalService.unsubscribePage(getUserId(), nodeId, title);
	}

	public WikiPage updatePage(
			long nodeId, String title, double version, String content,
			String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPagePermission.check(
			getPermissionChecker(), nodeId, title, ActionKeys.UPDATE);

		return wikiPageLocalService.updatePage(
			getUserId(), nodeId, title, version, content, summary, minorEdit,
			format, parentTitle, redirectTitle, serviceContext);
	}

	protected String exportToRSS(
			long companyId, String name, String description, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, List<WikiPage> pages, boolean diff, Locale locale)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

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
				if (latestPage != null) {
					sb.append(StringPool.QUESTION);
					sb.append(PortalUtil.getPortletNamespace(PortletKeys.WIKI));
					sb.append("version=");
					sb.append(page.getVersion());

					String value = getPageDiff(
						companyId, latestPage, page, locale);

					syndContent.setValue(value);

					syndEntry.setDescription(syndContent);

					syndEntries.add(syndEntry);
				}
			}
			else {
				String value = null;

				if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
					value = StringUtil.shorten(
						HtmlUtil.extractText(page.getContent()),
						PropsValues.WIKI_RSS_ABSTRACT_LENGTH, StringPool.BLANK);
				}
				else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
					value = StringPool.BLANK;
				}
				else {
					value = page.getContent();
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

		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		syndFeed.setLinks(syndLinks);

		SyndLink syndLinkSelf = new SyndLinkImpl();

		syndLinks.add(syndLinkSelf);

		syndLinkSelf.setHref(feedURL);
		syndLinkSelf.setRel("self");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(name);
		syndFeed.setUri(feedURL);
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

	protected String getPageDiff(
			long companyId, WikiPage latestPage, WikiPage page, Locale locale)
		throws SystemException {

		try {
			Template template = TemplateManagerUtil.getTemplate(
				TemplateManager.VELOCITY, _templateResource,
				TemplateContextType.STANDARD);

			template.put("companyId", companyId);
			template.put("contextLine", Diff.CONTEXT_LINE);
			template.put("diffUtil", new DiffUtil());
			template.put("languageUtil", LanguageUtil.getLanguage());
			template.put("locale", locale);

			String sourceContent = WikiUtil.processContent(
				latestPage.getContent());
			String targetContent = WikiUtil.processContent(page.getContent());

			sourceContent = HtmlUtil.escape(sourceContent);
			targetContent = HtmlUtil.escape(targetContent);

			List<DiffResult>[] diffResults = DiffUtil.diff(
				new UnsyncStringReader(sourceContent),
				new UnsyncStringReader(targetContent));

			template.put("sourceResults", diffResults[0]);
			template.put("targetResults", diffResults[1]);

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			template.processTemplate(unsyncStringWriter);

			return unsyncStringWriter.toString();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private TemplateResource _templateResource;

}