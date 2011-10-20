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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Diff;
import com.liferay.portal.kernel.util.DiffResult;
import com.liferay.portal.kernel.util.DiffUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.base.WikiPageServiceBaseImpl;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.util.ContentUtil;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class WikiPageServiceImpl extends WikiPageServiceBaseImpl {

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

	public void addPageAttachments(
			long nodeId, String title,
			List<ObjectValuePair<String, InputStream>> inputStream)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.addPageAttachments(
			getUserId(), nodeId, title, inputStream);
	}

	public String addTempPageAttachment(
			long nodeId, String fileName, String tempFolderName,
			InputStream inputStream)
		throws IOException, PortalException, SystemException {

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

	public void deleteTempPageAttachment(
			long nodeId, String fileName, String tempFolderName)
		throws PortalException, SystemException {

		WikiNodePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.deleteTempPageAttachment(
			getUserId(), fileName, tempFolderName);
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

			Iterator<WikiPage> itr = pageList.iterator();

			lastIntervalStart += max;
			listNotExhausted = (pageList.size() == max);

			while (itr.hasNext() && (pages.size() < max)) {
				WikiPage page = itr.next();

				if (WikiPagePermission.contains(getPermissionChecker(), page,
						ActionKeys.VIEW)) {

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
			companyId, name, description, type, version, displayStyle,
			feedURL, entryURL, pages, diff, locale);
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

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		WikiPage latestPage = null;

		StringBundler link = new StringBundler(7);

		for (WikiPage page : pages) {
			String author = HtmlUtil.escape(
				PortalUtil.getUserName(page.getUserId(), page.getUserName()));

			String title =
				page.getTitle() + StringPool.SPACE + page.getVersion();

			if (page.isMinorEdit()) {
				title +=
					StringPool.SPACE + StringPool.OPEN_PARENTHESIS +
						LanguageUtil.get(locale, "minor-edit") +
							StringPool.CLOSE_PARENTHESIS;
			}

			link.setIndex(0);

			link.append(entryURL);
			link.append(StringPool.AMPERSAND);
			link.append(HttpUtil.encodeURL(page.getTitle()));

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);
			syndEntry.setTitle(title);
			syndEntry.setPublishedDate(page.getCreateDate());
			syndEntry.setUpdatedDate(page.getModifiedDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			if (diff) {
				if (latestPage != null) {
					link.append(StringPool.QUESTION);
					link.append(
						PortalUtil.getPortletNamespace(PortletKeys.WIKI));
					link.append("version=");
					link.append(page.getVersion());

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

			syndEntry.setLink(link.toString());
			syndEntry.setUri(syndEntry.getLink());

			latestPage = page;
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

	protected String getPageDiff(
			long companyId, WikiPage latestPage, WikiPage page,
			Locale locale)
		throws SystemException {

		String sourceContent = WikiUtil.processContent(latestPage.getContent());
		String targetContent = WikiUtil.processContent(page.getContent());

		sourceContent = HtmlUtil.escape(sourceContent);
		targetContent = HtmlUtil.escape(targetContent);

		List<DiffResult>[] diffResults = DiffUtil.diff(
			new UnsyncStringReader(sourceContent),
			new UnsyncStringReader(targetContent));

		String velocityTemplateId =
			"com/liferay/portlet/wiki/dependencies/rss.vm";
		String velocityTemplateContent = ContentUtil.get(velocityTemplateId);

		VelocityContext velocityContext =
			VelocityEngineUtil.getWrappedStandardToolsContext();

		velocityContext.put("companyId", companyId);
		velocityContext.put("contextLine", Diff.CONTEXT_LINE);
		velocityContext.put("diffUtil", new DiffUtil());
		velocityContext.put("languageUtil", LanguageUtil.getLanguage());
		velocityContext.put("locale", locale);
		velocityContext.put("sourceResults", diffResults[0]);
		velocityContext.put("targetResults", diffResults[1]);

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			VelocityEngineUtil.mergeTemplate(
				velocityTemplateId, velocityTemplateContent, velocityContext,
				unsyncStringWriter);

			return unsyncStringWriter.toString();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}