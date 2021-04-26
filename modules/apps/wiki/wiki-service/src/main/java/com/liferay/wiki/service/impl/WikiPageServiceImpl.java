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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.rss.export.RSSExporter;
import com.liferay.rss.model.SyndContent;
import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndFeed;
import com.liferay.rss.model.SyndLink;
import com.liferay.rss.model.SyndModelFactory;
import com.liferay.rss.util.RSSUtil;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPageConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.base.WikiPageServiceBaseImpl;
import com.liferay.wiki.util.comparator.PageCreateDateComparator;

import java.io.File;
import java.io.InputStream;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the remote service for accessing, adding, deleting, moving,
 * subscription handling of, trash handling of, and updating wiki pages and wiki
 * page attachments. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
@Component(
	property = {
		"json.web.service.context.name=wiki",
		"json.web.service.context.path=WikiPage"
	},
	service = AopService.class
)
public class WikiPageServiceImpl extends WikiPageServiceBaseImpl {

	@Override
	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, ServiceContext serviceContext)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			getUserId(), nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * #addPage(String, long, String, String, String, boolean, String, String,
	 * String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, String format, String parentTitle,
			String redirectTitle, ServiceContext serviceContext)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			getUserId(), nodeId, title, WikiPageConstants.VERSION_DEFAULT,
			content, summary, minorEdit, format, true, parentTitle,
			redirectTitle, serviceContext);
	}

	@Override
	public WikiPage addPage(
			String externalReferenceCode, long nodeId, String title,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		return wikiPageLocalService.addPage(
			externalReferenceCode, getUserId(), nodeId, title,
			WikiPageConstants.VERSION_DEFAULT, content, summary, minorEdit,
			format, true, parentTitle, redirectTitle, serviceContext);
	}

	@Override
	public FileEntry addPageAttachment(
			long nodeId, String title, String fileName, File file,
			String mimeType)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addPageAttachment(
			getUserId(), nodeId, title, fileName, file, mimeType);
	}

	@Override
	public FileEntry addPageAttachment(
			long nodeId, String title, String fileName, InputStream inputStream,
			String mimeType)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addPageAttachment(
			getUserId(), nodeId, title, fileName, inputStream, mimeType);
	}

	@Override
	public List<FileEntry> addPageAttachments(
			long nodeId, String title,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addPageAttachments(
			getUserId(), nodeId, title, inputStreamOVPs);
	}

	@Override
	public FileEntry addTempFileEntry(
			long nodeId, String folderName, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), node, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.addTempFileEntry(
			node.getGroupId(), getUserId(), folderName, fileName, inputStream,
			mimeType);
	}

	@Override
	public void changeParent(
			long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException {

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(),
			wikiPageLocalService.getPage(nodeId, title, null),
			ActionKeys.UPDATE);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.changeParent(
			getUserId(), nodeId, title, newParentTitle, serviceContext);
	}

	@Override
	public void copyPageAttachments(
			long templateNodeId, String templateTitle, long nodeId,
			String title)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.copyPageAttachments(
			getUserId(), templateNodeId, templateTitle, nodeId, title);
	}

	@Override
	public void deletePage(long nodeId, String title) throws PortalException {
		WikiPage page = wikiPagePersistence.fetchByN_T_H_First(
			nodeId, title, true, null);

		if (page == null) {
			return;
		}

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		wikiPageLocalService.deletePage(page);
	}

	@Override
	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException {

		WikiPage page = wikiPagePersistence.fetchByN_T_H_First(
			nodeId, title, true, null);

		if (page == null) {
			return;
		}

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	@Override
	public void deletePageAttachments(long nodeId, String title)
		throws PortalException {

		WikiPage page = wikiPagePersistence.findByN_T_First(
			nodeId, title, null);

		if (page == null) {
			return;
		}

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		wikiPageLocalService.deletePageAttachments(nodeId, title);
	}

	@Override
	public void deleteTempFileEntry(
			long nodeId, String folderName, String fileName)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), node, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.deleteTempFileEntry(
			node.getGroupId(), getUserId(), folderName, fileName);
	}

	@Override
	public void deleteTrashPageAttachments(long nodeId, String title)
		throws PortalException {

		WikiPage page = wikiPagePersistence.findByN_T_First(
			nodeId, title, null);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		wikiPageLocalService.deleteTrashPageAttachments(nodeId, title);
	}

	@Override
	public void discardDraft(long nodeId, String title, double version)
		throws PortalException {

		WikiPage page = wikiPagePersistence.findByN_T_V(nodeId, title, version);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		wikiPagePersistence.remove(page);
	}

	@Override
	public WikiPage fetchPage(long nodeId, String title, double version)
		throws PortalException {

		WikiPage page = wikiPageLocalService.fetchPage(nodeId, title, version);

		if (page != null) {
			_wikiPageModelResourcePermission.check(
				getPermissionChecker(), page, ActionKeys.VIEW);
		}

		return page;
	}

	@Override
	public List<WikiPage> getChildren(
			long groupId, long nodeId, boolean head, String parentTitle)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		return wikiPagePersistence.filterFindByG_N_H_P_S(
			groupId, nodeId, head, parentTitle,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public WikiPage getDraftPage(long nodeId, String title)
		throws PortalException {

		WikiPage page = wikiPageLocalService.getDraftPage(nodeId, title);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.VIEW);

		return page;
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
			listNotExhausted = pageList.size() == max;

			for (WikiPage page : pageList) {
				if (pages.size() >= max) {
					break;
				}

				if (_wikiPageModelResourcePermission.contains(
						getPermissionChecker(), page, ActionKeys.VIEW)) {

					pages.add(page);
				}
			}
		}

		return pages;
	}

	@Override
	public String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		List<WikiPage> pages = getNodePages(nodeId, max);

		return exportToRSS(
			node.getName(), node.getDescription(), type, version, displayStyle,
			feedURL, entryURL, attachmentURLPrefix, pages, false, null);
	}

	@Override
	public List<WikiPage> getOrphans(WikiNode node) throws PortalException {
		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), node, ActionKeys.VIEW);

		List<WikiPage> pages = wikiPagePersistence.filterFindByG_N_H_S(
			node.getGroupId(), node.getNodeId(), true,
			WorkflowConstants.STATUS_APPROVED);

		return wikiPageLocalService.getOrphans(pages);
	}

	@Override
	public WikiPage getPage(long pageId) throws PortalException {
		WikiPage page = wikiPageLocalService.getPage(pageId);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.VIEW);

		return page;
	}

	@Override
	public WikiPage getPage(long groupId, long nodeId, String title)
		throws PortalException {

		List<WikiPage> pages = wikiPagePersistence.filterFindByG_N_T_H(
			groupId, nodeId, title, true, 0, 1);

		if (!pages.isEmpty()) {
			return pages.get(0);
		}

		StringBundler sb = new StringBundler(5);

		sb.append("{nodeId=");
		sb.append(nodeId);
		sb.append(", title=");
		sb.append(title);
		sb.append("}");

		throw new NoSuchPageException(sb.toString());
	}

	@Override
	public WikiPage getPage(long nodeId, String title) throws PortalException {
		WikiPage page = wikiPageLocalService.getPage(nodeId, title);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.VIEW);

		return page;
	}

	@Override
	public WikiPage getPage(long nodeId, String title, Boolean head)
		throws PortalException {

		WikiPage page = wikiPageLocalService.getPage(nodeId, title, head);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.VIEW);

		return page;
	}

	@Override
	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException {

		WikiPage page = wikiPageLocalService.getPage(nodeId, title, version);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.VIEW);

		return page;
	}

	@Override
	public List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, int status, int start,
			int end, OrderByComparator<WikiPage> orderByComparator)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		if (status == WorkflowConstants.STATUS_ANY) {
			return wikiPagePersistence.filterFindByG_N_H(
				groupId, nodeId, head, start, end, orderByComparator);
		}

		return wikiPagePersistence.filterFindByG_N_H_S(
			groupId, nodeId, head, status, start, end, orderByComparator);
	}

	@Override
	public List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status, int start, int end,
			OrderByComparator<WikiPage> orderByComparator)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		QueryDefinition<WikiPage> queryDefinition = new QueryDefinition<>(
			status, userId, includeOwner);

		queryDefinition.setEnd(end);
		queryDefinition.setOrderByComparator(orderByComparator);
		queryDefinition.setStart(start);

		return wikiPageFinder.filterFindByG_N_H_S(
			groupId, nodeId, head, queryDefinition);
	}

	@Override
	public List<WikiPage> getPages(
			long groupId, long userId, long nodeId, int status, int start,
			int end)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		if (userId > 0) {
			return wikiPagePersistence.filterFindByG_U_N_S(
				groupId, userId, nodeId, status, start, end,
				new PageCreateDateComparator(false));
		}

		return wikiPagePersistence.filterFindByG_N_S(
			groupId, nodeId, status, start, end,
			new PageCreateDateComparator(false));
	}

	@Override
	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		return wikiPagePersistence.filterCountByG_N_H_S(
			groupId, nodeId, head, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getPagesCount(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		QueryDefinition<WikiPage> queryDefinition = new QueryDefinition<>(
			status, userId, includeOwner);

		return wikiPageFinder.filterCountByG_N_H_S(
			groupId, nodeId, head, queryDefinition);
	}

	@Override
	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		if (userId > 0) {
			return wikiPagePersistence.filterCountByG_U_N_S(
				groupId, userId, nodeId, status);
		}

		return wikiPagePersistence.filterCountByG_N_S(groupId, nodeId, status);
	}

	@Override
	public String getPagesRSS(
			long nodeId, String title, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix, Locale locale)
		throws PortalException {

		WikiPage page = wikiPageLocalService.fetchPage(nodeId, title);

		if (page == null) {
			_wikiNodeModelResourcePermission.check(
				getPermissionChecker(), nodeId, ActionKeys.VIEW);
		}
		else {
			_wikiPageModelResourcePermission.check(
				getPermissionChecker(), page, ActionKeys.VIEW);
		}

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

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.findByModifiedDate(
			groupId, nodeId, new Timestamp(calendar.getTimeInMillis()), false,
			start, end);
	}

	@Override
	public int getRecentChangesCount(long groupId, long nodeId)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.VIEW);

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.countByModifiedDate(
			groupId, nodeId, calendar.getTime(), false);
	}

	@Override
	public String[] getTempFileNames(long nodeId, String folderName)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), node, ActionKeys.ADD_ATTACHMENT);

		return wikiPageLocalService.getTempFileNames(
			node.getGroupId(), getUserId(), folderName);
	}

	@Override
	public FileEntry movePageAttachmentToTrash(
			long nodeId, String title, String fileName)
		throws PortalException {

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), wikiPageLocalService.getPage(nodeId, title),
			ActionKeys.DELETE);

		return wikiPageLocalService.movePageAttachmentToTrash(
			getUserId(), nodeId, title, fileName);
	}

	@Override
	public WikiPage movePageToTrash(long nodeId, String title)
		throws PortalException {

		WikiPage page = wikiPagePersistence.fetchByN_T_H_First(
			nodeId, title, true, null);

		if (page == null) {
			return null;
		}

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		return wikiPageLocalService.movePageToTrash(getUserId(), page);
	}

	@Override
	public WikiPage movePageToTrash(long nodeId, String title, double version)
		throws PortalException {

		WikiPage page = wikiPagePersistence.findByN_T_V(nodeId, title, version);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		return wikiPageLocalService.movePageToTrash(getUserId(), page);
	}

	@Override
	public void renamePage(
			long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPage page = wikiPageLocalService.fetchPage(nodeId, title);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.UPDATE);

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_PAGE);

		wikiPageLocalService.renamePage(
			getUserId(), nodeId, title, newTitle, serviceContext);
	}

	@Override
	public void restorePageAttachmentFromTrash(
			long nodeId, String title, String fileName)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			getPermissionChecker(), nodeId, ActionKeys.ADD_ATTACHMENT);

		wikiPageLocalService.restorePageAttachmentFromTrash(
			getUserId(), nodeId, title, fileName);
	}

	@Override
	public void restorePageFromTrash(long resourcePrimKey)
		throws PortalException {

		WikiPage page = wikiPageLocalService.getPage(resourcePrimKey);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.DELETE);

		wikiPageLocalService.restorePageFromTrash(getUserId(), page);
	}

	@Override
	public WikiPage revertPage(
			long nodeId, String title, double version,
			ServiceContext serviceContext)
		throws PortalException {

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(),
			wikiPageLocalService.getPage(nodeId, title, version),
			ActionKeys.UPDATE);

		return wikiPageLocalService.revertPage(
			getUserId(), nodeId, title, version, serviceContext);
	}

	@Override
	public void subscribePage(long nodeId, String title)
		throws PortalException {

		WikiPage page = wikiPageLocalService.getPage(nodeId, title);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.SUBSCRIBE);

		_subscriptionLocalService.addSubscription(
			getUserId(), page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey());
	}

	@Override
	public void unsubscribePage(long nodeId, String title)
		throws PortalException {

		WikiPage page = wikiPageLocalService.getPage(nodeId, title);

		_wikiPageModelResourcePermission.check(
			getPermissionChecker(), page, ActionKeys.SUBSCRIBE);

		_subscriptionLocalService.deleteSubscription(
			getUserId(), WikiPage.class.getName(), page.getResourcePrimKey());
	}

	@Override
	public WikiPage updatePage(
			long nodeId, String title, double version, String content,
			String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPage page = wikiPageLocalService.fetchPage(nodeId, title);

		if (page == null) {
			_wikiNodeModelResourcePermission.check(
				getPermissionChecker(), nodeId, ActionKeys.VIEW);
		}
		else {
			_wikiPageModelResourcePermission.check(
				getPermissionChecker(), page, ActionKeys.UPDATE);
		}

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

		SyndFeed syndFeed = _syndModelFactory.createSyndFeed();

		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<>();

		syndFeed.setEntries(syndEntries);

		WikiPage latestPage = null;

		StringBundler sb = new StringBundler(6);

		for (WikiPage page : pages) {
			SyndEntry syndEntry = _syndModelFactory.createSyndEntry();

			syndEntry.setAuthor(_portal.getUserName(page));

			SyndContent syndContent = _syndModelFactory.createSyndContent();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			sb.setIndex(0);

			sb.append(entryURL);

			if (entryURL.endsWith(StringPool.SLASH)) {
				sb.append(URLCodec.encodeURL(page.getTitle()));
			}

			if (diff) {
				if ((latestPage != null) || (pages.size() == 1)) {
					sb.append(StringPool.QUESTION);
					sb.append(
						_portal.getPortletNamespace(WikiPortletKeys.WIKI));
					sb.append("version=");
					sb.append(page.getVersion());

					String value = null;

					if (latestPage == null) {
						value = _wikiEngineRenderer.convert(
							page, null, null, attachmentURLPrefix);
					}
					else {
						try {
							value = _wikiEngineRenderer.diffHtml(
								latestPage, page, null, null,
								attachmentURLPrefix);
						}
						catch (PortalException portalException) {
							throw portalException;
						}
						catch (SystemException systemException) {
							throw systemException;
						}
						catch (Exception exception) {
							throw new SystemException(exception);
						}
					}

					syndContent.setValue(value);

					syndEntry.setDescription(syndContent);

					syndEntries.add(syndEntry);
				}
			}
			else {
				String value = null;

				if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
					WikiGroupServiceOverriddenConfiguration
						wikiGroupServiceOverriddenConfiguration =
							_configurationProvider.getConfiguration(
								WikiGroupServiceOverriddenConfiguration.class,
								new GroupServiceSettingsLocator(
									page.getGroupId(),
									WikiConstants.SERVICE_NAME));

					value = StringUtil.shorten(
						HtmlUtil.extractText(page.getContent()),
						wikiGroupServiceOverriddenConfiguration.
							rssAbstractLength(),
						StringPool.BLANK);
				}
				else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
					value = StringPool.BLANK;
				}
				else {
					value = _wikiEngineRenderer.convert(
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
				title += StringBundler.concat(
					StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
					LanguageUtil.get(locale, "minor-edit"),
					StringPool.CLOSE_PARENTHESIS);
			}

			syndEntry.setTitle(title);

			syndEntry.setUpdatedDate(page.getModifiedDate());
			syndEntry.setUri(sb.toString());

			latestPage = page;
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));

		List<SyndLink> syndLinks = new ArrayList<>();

		syndFeed.setLinks(syndLinks);

		SyndLink syndLinkSelf = _syndModelFactory.createSyndLink();

		syndLinks.add(syndLinkSelf);

		syndLinkSelf.setHref(feedURL);
		syndLinkSelf.setRel("self");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(name);
		syndFeed.setUri(feedURL);

		return _rssExporter.export(syndFeed);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	@Reference
	private RSSExporter _rssExporter;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private SyndModelFactory _syndModelFactory;

	@Reference
	private WikiEngineRenderer _wikiEngineRenderer;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiNode)")
	private ModelResourcePermission<WikiNode> _wikiNodeModelResourcePermission;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiPage)")
	private ModelResourcePermission<WikiPage> _wikiPageModelResourcePermission;

}