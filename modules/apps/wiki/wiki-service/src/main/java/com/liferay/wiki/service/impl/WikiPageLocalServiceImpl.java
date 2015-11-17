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

import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.diff.DiffHtmlUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.util.LayoutURLUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeUtil;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.escape.WikiEscapeUtil;
import com.liferay.wiki.exception.DuplicatePageException;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.exception.PageTitleException;
import com.liferay.wiki.exception.PageVersionException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.model.WikiPageDisplay;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.model.impl.WikiPageDisplayImpl;
import com.liferay.wiki.model.impl.WikiPageImpl;
import com.liferay.wiki.service.base.WikiPageLocalServiceBaseImpl;
import com.liferay.wiki.social.WikiActivityKeys;
import com.liferay.wiki.util.WikiCacheThreadLocal;
import com.liferay.wiki.util.WikiCacheUtil;
import com.liferay.wiki.util.WikiUtil;
import com.liferay.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.wiki.util.comparator.PageVersionComparator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides the local service for accessing, adding, deleting, moving,
 * subscription handling of, trash handling of, updating, and validating wiki
 * pages.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Julio Camarero
 * @author Wesley Gong
 * @author Marcellus Tavares
 * @author Zsigmond Rab
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
public class WikiPageLocalServiceImpl extends WikiPageLocalServiceBaseImpl {

	@Override
	public WikiPage addPage(
			long userId, long nodeId, String title, double version,
			String content, String summary, boolean minorEdit, String format,
			boolean head, String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException {

		// Page

		User user = userPersistence.findByPrimaryKey(userId);
		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);
		Date now = new Date();

		long pageId = counterLocalService.increment();

		content = SanitizerUtil.sanitize(
			user.getCompanyId(), node.getGroupId(), userId,
			WikiPage.class.getName(), pageId, "text/" + format, content);

		validate(title, nodeId, content, format);

		long resourcePrimKey =
			wikiPageResourceLocalService.getPageResourcePrimKey(
				node.getGroupId(), nodeId, title);

		WikiPage page = wikiPagePersistence.create(pageId);

		page.setUuid(serviceContext.getUuid());
		page.setResourcePrimKey(resourcePrimKey);
		page.setGroupId(node.getGroupId());
		page.setCompanyId(user.getCompanyId());
		page.setUserId(user.getUserId());
		page.setUserName(user.getFullName());
		page.setNodeId(nodeId);
		page.setTitle(title);
		page.setVersion(version);
		page.setMinorEdit(minorEdit);
		page.setContent(content);
		page.setSummary(summary);
		page.setFormat(format);
		page.setHead(head);
		page.setParentTitle(parentTitle);
		page.setRedirectTitle(redirectTitle);
		page.setStatus(WorkflowConstants.STATUS_DRAFT);
		page.setStatusByUserId(userId);
		page.setStatusDate(serviceContext.getModifiedDate(now));
		page.setExpandoBridgeAttributes(serviceContext);

		wikiPagePersistence.update(page);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addPageResources(
				page, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addPageResources(
				page, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Node

		node.setLastPostDate(serviceContext.getModifiedDate(now));

		wikiNodePersistence.update(node);

		// Asset

		updateAsset(
			userId, page, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Message boards

		WikiGroupServiceOverriddenConfiguration
			wikiGroupServiceOverriddenConfiguration =
				configurationFactory.getConfiguration(
					WikiGroupServiceOverriddenConfiguration.class,
					new GroupServiceSettingsLocator(
						node.getGroupId(), WikiConstants.SERVICE_NAME));

		if (wikiGroupServiceOverriddenConfiguration.pageCommentsEnabled()) {
			CommentManagerUtil.addDiscussion(
				userId, page.getGroupId(), WikiPage.class.getName(),
				resourcePrimKey, page.getUserName());
		}

		// Workflow

		page = startWorkflowInstance(userId, page, serviceContext);

		return page;
	}

	@Override
	public WikiPage addPage(
			long userId, long nodeId, String title, String content,
			String summary, boolean minorEdit, ServiceContext serviceContext)
		throws PortalException {

		double version = WikiPageConstants.VERSION_DEFAULT;

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		WikiGroupServiceOverriddenConfiguration
			wikiGroupServiceOverriddenConfiguration =
				configurationFactory.getConfiguration(
					WikiGroupServiceOverriddenConfiguration.class,
					new GroupServiceSettingsLocator(
						node.getGroupId(), WikiConstants.SERVICE_NAME));

		String format = wikiGroupServiceOverriddenConfiguration.defaultFormat();

		boolean head = false;
		String parentTitle = null;
		String redirectTitle = null;

		return addPage(
			userId, nodeId, title, version, content, summary, minorEdit, format,
			head, parentTitle, redirectTitle, serviceContext);
	}

	@Override
	public FileEntry addPageAttachment(
			long userId, long nodeId, String title, String fileName, File file,
			String mimeType)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		Folder folder = page.addAttachmentsFolder();

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			page.getGroupId(), userId, WikiPage.class.getName(),
			page.getResourcePrimKey(), WikiPortletKeys.WIKI,
			folder.getFolderId(), file, fileName, mimeType, true);

		if (userId == 0) {
			userId = page.getUserId();
		}

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
		extraDataJSONObject.put("fileEntryTitle", fileEntry.getTitle());
		extraDataJSONObject.put("title", page.getTitle());
		extraDataJSONObject.put("version", page.getVersion());

		SocialActivityManagerUtil.addActivity(
			userId, page, SocialActivityConstants.TYPE_ADD_ATTACHMENT,
			extraDataJSONObject.toString(), 0);

		return fileEntry;
	}

	@Override
	public FileEntry addPageAttachment(
			long userId, long nodeId, String title, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		Folder folder = page.addAttachmentsFolder();

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			page.getGroupId(), userId, WikiPage.class.getName(),
			page.getResourcePrimKey(), WikiPortletKeys.WIKI,
			folder.getFolderId(), inputStream, fileName, mimeType, true);

		if (userId == 0) {
			userId = page.getUserId();
		}

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
		extraDataJSONObject.put("fileEntryTitle", fileEntry.getTitle());
		extraDataJSONObject.put("title", page.getTitle());
		extraDataJSONObject.put("version", page.getVersion());

		SocialActivityManagerUtil.addActivity(
			userId, page, SocialActivityConstants.TYPE_ADD_ATTACHMENT,
			extraDataJSONObject.toString(), 0);

		return fileEntry;
	}

	@Override
	public List<FileEntry> addPageAttachments(
			long userId, long nodeId, String title,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException {

		List<FileEntry> fileEntries = new ArrayList<>();

		if (inputStreamOVPs.isEmpty()) {
			return Collections.emptyList();
		}

		for (int i = 0; i < inputStreamOVPs.size(); i++) {
			ObjectValuePair<String, InputStream> inputStreamOVP =
				inputStreamOVPs.get(i);

			String fileName = inputStreamOVP.getKey();
			InputStream inputStream = inputStreamOVP.getValue();

			File file = null;

			try {
				file = FileUtil.createTempFile(inputStream);

				String mimeType = MimeTypesUtil.getContentType(file, fileName);

				FileEntry fileEntry = addPageAttachment(
					userId, nodeId, title, fileName, file, mimeType);

				fileEntries.add(fileEntry);
			}
			catch (IOException ioe) {
				throw new SystemException(
					"Unable to write temporary file", ioe);
			}
			finally {
				FileUtil.delete(file);
			}
		}

		return fileEntries;
	}

	@Override
	public void addPageResources(
			long nodeId, String title, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		addPageResources(page, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addPageResources(
			long nodeId, String title, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		addPageResources(page, groupPermissions, guestPermissions);
	}

	@Override
	public void addPageResources(
			WikiPage page, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			page.getCompanyId(), page.getGroupId(), page.getUserId(),
			WikiPage.class.getName(), page.getResourcePrimKey(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addPageResources(
			WikiPage page, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			page.getCompanyId(), page.getGroupId(), page.getUserId(),
			WikiPage.class.getName(), page.getResourcePrimKey(),
			groupPermissions, guestPermissions);
	}

	@Override
	public FileEntry addTempFileEntry(
			long groupId, long userId, String folderName, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		return TempFileEntryUtil.addTempFileEntry(
			groupId, userId, folderName, fileName, inputStream, mimeType);
	}

	/**
	 * @deprecated As of 7.0.0 replaced by {@link #addTempFileEntry(long, long,
	 *             String, String, InputStream, String)}
	 */
	@Deprecated
	@Override
	public void addTempPageAttachment(
			long groupId, long userId, String fileName, String tempFolderName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		addTempFileEntry(
			groupId, userId, tempFolderName, fileName, inputStream, mimeType);
	}

	@Override
	public WikiPage changeParent(
			long userId, long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNotNull(newParentTitle)) {
			WikiPage parentPage = getPage(nodeId, newParentTitle);

			if (Validator.isNotNull(parentPage.getRedirectTitle())) {
				newParentTitle = parentPage.getRedirectTitle();
			}
		}

		WikiPage page = getPage(nodeId, title);

		String originalParentTitle = page.getParentTitle();

		double version = page.getVersion();
		String content = page.getContent();
		String summary = serviceContext.translate(
			"changed-parent-from-x", originalParentTitle);
		boolean minorEdit = false;
		String format = page.getFormat();
		String redirectTitle = page.getRedirectTitle();

		populateServiceContext(serviceContext, page);

		page = updatePage(
			userId, nodeId, title, version, content, summary, minorEdit, format,
			newParentTitle, redirectTitle, serviceContext);

		List<WikiPage> oldPages = wikiPagePersistence.findByN_T_H(
			nodeId, title, false);

		for (WikiPage oldPage : oldPages) {
			if (!WorkflowThreadLocal.isEnabled()) {
				oldPage.setParentTitle(originalParentTitle);

				wikiPagePersistence.update(oldPage);
			}
		}

		return page;
	}

	@Override
	public void copyPageAttachments(
			long userId, long templateNodeId, String templateTitle, long nodeId,
			String title)
		throws PortalException {

		WikiPage templatePage = getPage(templateNodeId, templateTitle);

		List<FileEntry> templateFileEntries =
			templatePage.getAttachmentsFileEntries();

		for (FileEntry templateFileEntry : templateFileEntries) {
			addPageAttachment(
				userId, nodeId, title, templateFileEntry.getTitle(),
				templateFileEntry.getContentStream(),
				templateFileEntry.getMimeType());
		}
	}

	@Override
	public void deletePage(long nodeId, String title) throws PortalException {
		List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
			nodeId, title, true, 0, 1);

		if (!pages.isEmpty()) {
			wikiPageLocalService.deletePage(pages.get(0));
		}
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
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP, send = false,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deletePage(WikiPage page) throws PortalException {

		// Child pages

		List<WikiPage> childPages = wikiPagePersistence.findByN_P(
			page.getNodeId(), page.getTitle());

		for (WikiPage childPage : childPages) {
			if (childPage.isApproved() || childPage.isInTrashImplicitly()) {
				wikiPageLocalService.deletePage(childPage);
			}
			else {
				childPage.setParentTitle(StringPool.BLANK);

				wikiPagePersistence.update(childPage);
			}
		}

		List<WikiPage> redirectorPages = getRedirectorPages(
			page.getNodeId(), page.getTitle());

		for (WikiPage redirectorPage : redirectorPages) {
			if (redirectorPage.isApproved() ||
				redirectorPage.isInTrashImplicitly()) {

				wikiPageLocalService.deletePage(redirectorPage);
			}
			else {
				redirectorPage.setRedirectTitle(StringPool.BLANK);

				wikiPagePersistence.update(redirectorPage);
			}
		}

		List<WikiPage> versionPages = wikiPagePersistence.findByN_T(
			page.getNodeId(), page.getTitle());

		wikiPagePersistence.removeByN_T(page.getNodeId(), page.getTitle());

		// References

		wikiPagePersistence.removeByN_R(page.getNodeId(), page.getTitle());

		// Resources

		resourceLocalService.deleteResource(
			page.getCompanyId(), WikiPage.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, page.getResourcePrimKey());

		// Resource

		WikiPageResource pageResource =
			wikiPageResourceLocalService.fetchPageResource(
				page.getNodeId(), page.getTitle());

		if (pageResource != null) {
			wikiPageResourceLocalService.deleteWikiPageResource(pageResource);
		}

		// Attachments

		long folderId = page.getAttachmentsFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			PortletFileRepositoryUtil.deletePortletFolder(folderId);
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			page.getCompanyId(), WikiPage.class.getName(),
			page.getResourcePrimKey());

		// Asset

		SystemEventHierarchyEntryThreadLocal.pop(
			page.getModelClass(), page.getPageId());

		try {
			for (WikiPage versionPage : versionPages) {
				assetEntryLocalService.deleteEntry(
					WikiPage.class.getName(), versionPage.getPrimaryKey());
			}
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.push(
				page.getModelClass(), page.getPageId());
		}

		assetEntryLocalService.deleteEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Comment

		CommentManagerUtil.deleteDiscussion(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Expando

		expandoRowLocalService.deleteRows(page.getPrimaryKey());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Trash

		if (page.isInTrash()) {
			if (page.isInTrashExplicitly()) {
				page.setTitle(TrashUtil.getOriginalTitle(page.getTitle()));

				trashEntryLocalService.deleteEntry(
					WikiPage.class.getName(), page.getResourcePrimKey());
			}
			else {
				for (WikiPage versionPage : versionPages) {
					trashVersionLocalService.deleteTrashVersion(
						WikiPage.class.getName(), versionPage.getPageId());
				}
			}
		}

		// Indexer

		Indexer<WikiPage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WikiPage.class);

		indexer.delete(page);

		// Cache

		clearPageCache(page);

		// Version pages

		for (WikiPage versionPage : versionPages) {

			// Workflow

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				versionPage.getCompanyId(), versionPage.getGroupId(),
				WikiPage.class.getName(), versionPage.getPageId());
		}

		if (pageResource != null) {
			systemEventLocalService.addSystemEvent(
				0, page.getGroupId(), page.getModelClassName(),
				page.getPrimaryKey(), pageResource.getUuid(), null,
				SystemEventConstants.TYPE_DELETE, StringPool.BLANK);
		}
	}

	@Override
	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		long folderId = page.getAttachmentsFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			page.getGroupId(), folderId, fileName);

		deletePageAttachment(fileEntry.getFileEntryId());
	}

	@Override
	public void deletePageAttachments(long nodeId, String title)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		long folderId = page.getAttachmentsFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		PortletFileRepositoryUtil.deletePortletFileEntries(
			page.getGroupId(), folderId);
	}

	@Override
	public void deletePages(long nodeId) throws PortalException {
		List<WikiPage> pages = wikiPagePersistence.findByN_H_P(
			nodeId, true, StringPool.BLANK);

		for (WikiPage page : pages) {
			wikiPageLocalService.deletePage(page);
		}

		pages = wikiPagePersistence.findByN_H_P(
			nodeId, false, StringPool.BLANK);

		for (WikiPage page : pages) {
			wikiPageLocalService.deletePage(page);
		}
	}

	@Override
	public void deleteTempFileEntry(
			long groupId, long userId, String folderName, String fileName)
		throws PortalException {

		TempFileEntryUtil.deleteTempFileEntry(
			groupId, userId, folderName, fileName);
	}

	@Override
	public void deleteTrashPageAttachments(long nodeId, String title)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		long folderId = page.getAttachmentsFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		PortletFileRepositoryUtil.deletePortletFileEntries(
			page.getGroupId(), folderId, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public void discardDraft(long nodeId, String title, double version)
		throws PortalException {

		wikiPagePersistence.removeByN_T_V(nodeId, title, version);
	}

	@Override
	public WikiPage fetchLatestPage(
		long resourcePrimKey, int status, boolean preferApproved) {

		WikiPage page = null;

		OrderByComparator<WikiPage> orderByComparator =
			new PageVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			if (preferApproved) {
				page = wikiPagePersistence.fetchByR_S_First(
					resourcePrimKey, WorkflowConstants.STATUS_APPROVED,
					orderByComparator);
			}

			if (page == null) {
				page = wikiPagePersistence.fetchByResourcePrimKey_First(
					resourcePrimKey, orderByComparator);
			}
		}
		else {
			page = wikiPagePersistence.fetchByR_S_First(
				resourcePrimKey, status, orderByComparator);
		}

		return page;
	}

	@Override
	public WikiPage fetchLatestPage(
		long resourcePrimKey, long nodeId, int status, boolean preferApproved) {

		WikiPage page = null;

		OrderByComparator<WikiPage> orderByComparator =
			new PageVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			if (preferApproved) {
				page = wikiPagePersistence.fetchByR_N_S_First(
					resourcePrimKey, nodeId, WorkflowConstants.STATUS_APPROVED,
					orderByComparator);
			}

			if (page == null) {
				page = wikiPagePersistence.fetchByR_N_First(
					resourcePrimKey, nodeId, orderByComparator);
			}
		}
		else {
			page = wikiPagePersistence.fetchByR_N_S_First(
				resourcePrimKey, nodeId, status, orderByComparator);
		}

		return page;
	}

	@Override
	public WikiPage fetchLatestPage(
		long nodeId, String title, int status, boolean preferApproved) {

		WikiPage page = null;

		OrderByComparator<WikiPage> orderByComparator =
			new PageVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			if (preferApproved) {
				page = wikiPagePersistence.fetchByN_T_S_First(
					nodeId, title, WorkflowConstants.STATUS_APPROVED,
					orderByComparator);
			}

			if (page == null) {
				page = wikiPagePersistence.fetchByN_T_First(
					nodeId, title, orderByComparator);
			}
		}
		else {
			page = wikiPagePersistence.fetchByN_T_S_First(
				nodeId, title, status, orderByComparator);
		}

		return page;
	}

	@Override
	public WikiPage fetchPage(long resourcePrimKey) {
		WikiPageResource pageResource =
			wikiPageResourceLocalService.fetchWikiPageResource(resourcePrimKey);

		if (pageResource == null) {
			return null;
		}

		return fetchPage(pageResource.getNodeId(), pageResource.getTitle());
	}

	@Override
	public WikiPage fetchPage(long nodeId, String title) {
		return wikiPagePersistence.fetchByN_T_H_First(
			nodeId, title, true, null);
	}

	@Override
	public WikiPage fetchPage(long nodeId, String title, double version) {
		WikiPage page = null;

		if (version == 0) {
			page = fetchPage(nodeId, title);
		}
		else {
			page = wikiPagePersistence.fetchByN_T_V(nodeId, title, version);
		}

		return page;
	}

	@Override
	public List<WikiPage> getChildren(
		long nodeId, boolean head, String parentTitle) {

		return getChildren(
			nodeId, head, parentTitle, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<WikiPage> getChildren(
		long nodeId, boolean head, String parentTitle, int status) {

		return wikiPagePersistence.findByN_H_P_S(
			nodeId, head, parentTitle, status);
	}

	@Override
	public List<WikiPage> getChildren(
		long nodeId, boolean head, String parentTitle, int start, int end) {

		return wikiPagePersistence.findByN_H_P_S(
			nodeId, head, parentTitle, WorkflowConstants.STATUS_APPROVED, start,
			end);
	}

	@Override
	public int getChildrenCount(long nodeId, boolean head, String parentTitle) {
		return getChildrenCount(
			nodeId, head, parentTitle, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getChildrenCount(
		long nodeId, boolean head, String parentTitle, int status) {

		return wikiPagePersistence.countByN_H_P_S(
			nodeId, head, parentTitle, status);
	}

	@Override
	public List<WikiPage> getDependentPages(
		long nodeId, boolean head, String title, int status) {

		List<WikiPage> dependentPages = new ArrayList<>();

		List<WikiPage> childPages = getChildren(nodeId, head, title, status);

		dependentPages.addAll(childPages);

		List<WikiPage> redirectorPages = getRedirectorPages(
			nodeId, head, title, status);

		dependentPages.addAll(redirectorPages);

		return dependentPages;
	}

	@Override
	public WikiPage getDraftPage(long nodeId, String title)
		throws PortalException {

		List<WikiPage> pages = wikiPagePersistence.findByN_T_S(
			nodeId, title, WorkflowConstants.STATUS_DRAFT, 0, 1);

		if (!pages.isEmpty()) {
			return pages.get(0);
		}

		pages = wikiPagePersistence.findByN_T_S(
			nodeId, title, WorkflowConstants.STATUS_PENDING, 0, 1);

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
	public List<WikiPage> getIncomingLinks(long nodeId, String title)
		throws PortalException {

		Set<WikiPage> links = new HashSet<>();

		List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

		for (WikiPage page : pages) {
			if (isLinkedTo(page, title)) {
				links.add(page);
			}
		}

		List<WikiPage> redirectorPages = getRedirectorPages(nodeId, title);

		for (WikiPage redirectorPage : redirectorPages) {
			for (WikiPage page : pages) {
				if (isLinkedTo(page, redirectorPage.getTitle())) {
					links.add(page);
				}
			}
		}

		return ListUtil.sort(new ArrayList<WikiPage>(links));
	}

	@Override
	public WikiPage getLatestPage(
			long resourcePrimKey, int status, boolean preferApproved)
		throws PortalException {

		WikiPage page = fetchLatestPage(
			resourcePrimKey, status, preferApproved);

		if (page == null) {
			StringBundler sb = new StringBundler(5);

			sb.append("{resourcePrimKey=");
			sb.append(resourcePrimKey);
			sb.append(", status=");
			sb.append(status);
			sb.append("}");

			throw new NoSuchPageException(sb.toString());
		}

		return page;
	}

	@Override
	public WikiPage getLatestPage(
			long resourcePrimKey, long nodeId, int status,
			boolean preferApproved)
		throws PortalException {

		WikiPage page = fetchLatestPage(
			resourcePrimKey, nodeId, status, preferApproved);

		if (page == null) {
			StringBundler sb = new StringBundler(7);

			sb.append("{resourcePrimKey=");
			sb.append(resourcePrimKey);
			sb.append(", nodeId=");
			sb.append(nodeId);
			sb.append(", status=");
			sb.append(status);
			sb.append("}");

			throw new NoSuchPageException(sb.toString());
		}

		return page;
	}

	@Override
	public WikiPage getLatestPage(
			long nodeId, String title, int status, boolean preferApproved)
		throws PortalException {

		WikiPage page = fetchLatestPage(nodeId, title, status, preferApproved);

		if (page == null) {
			StringBundler sb = new StringBundler(7);

			sb.append("{nodeId=");
			sb.append(nodeId);
			sb.append(", title=");
			sb.append(title);
			sb.append(", status=");
			sb.append(status);
			sb.append("}");

			throw new NoSuchPageException(sb.toString());
		}

		return page;
	}

	@Override
	public List<WikiPage> getNoAssetPages() {
		return wikiPageFinder.findByNoAssets();
	}

	@Override
	public List<WikiPage> getOrphans(long nodeId) throws PortalException {
		List<WikiPage> pages = wikiPagePersistence.findByN_H_S(
			nodeId, true, WorkflowConstants.STATUS_APPROVED);

		return WikiUtil.filterOrphans(pages);
	}

	@Override
	public List<WikiPage> getOutgoingLinks(long nodeId, String title)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		Map<String, WikiPage> pages = new LinkedHashMap<>();

		Map<String, Boolean> links = WikiCacheUtil.getOutgoingLinks(page);

		for (Map.Entry<String, Boolean> entry : links.entrySet()) {
			String curTitle = entry.getKey();
			Boolean exists = entry.getValue();

			if (exists) {
				WikiPage curPage = getPage(nodeId, curTitle);

				if (!pages.containsKey(curPage.getTitle())) {
					pages.put(curPage.getTitle(), curPage);
				}
			}
			else {
				WikiPageImpl newPage = new WikiPageImpl();

				newPage.setNew(true);
				newPage.setNodeId(nodeId);
				newPage.setTitle(curTitle);

				if (!pages.containsKey(curTitle)) {
					pages.put(curTitle, newPage);
				}
			}
		}

		return ListUtil.fromMapValues(pages);
	}

	@Override
	public WikiPage getPage(long resourcePrimKey) throws PortalException {
		return getPage(resourcePrimKey, Boolean.TRUE);
	}

	@Override
	public WikiPage getPage(long resourcePrimKey, Boolean head)
		throws PortalException {

		WikiPageResource pageResource =
			wikiPageResourceLocalService.getPageResource(resourcePrimKey);

		return getPage(pageResource.getNodeId(), pageResource.getTitle(), head);
	}

	@Override
	public WikiPage getPage(long nodeId, String title) throws PortalException {
		WikiPage page = fetchPage(nodeId, title);

		if (page != null) {
			return page;
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
	public WikiPage getPage(long nodeId, String title, Boolean head)
		throws PortalException {

		List<WikiPage> pages;

		if (head == null) {
			pages = wikiPagePersistence.findByN_T(nodeId, title, 0, 1);
		}
		else {
			pages = wikiPagePersistence.findByN_T_H(nodeId, title, head, 0, 1);
		}

		if (!pages.isEmpty()) {
			return pages.get(0);
		}
		else {
			StringBundler sb = new StringBundler(7);

			sb.append("{nodeId=");
			sb.append(nodeId);
			sb.append(", title=");
			sb.append(title);
			sb.append(", head=");
			sb.append(head);
			sb.append("}");

			throw new NoSuchPageException(sb.toString());
		}
	}

	@Override
	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException {

		WikiPage page = null;

		if (version == 0) {
			page = getPage(nodeId, title);
		}
		else {
			page = wikiPagePersistence.findByN_T_V(nodeId, title, version);
		}

		return page;
	}

	@Override
	public WikiPage getPageByPageId(long pageId) throws PortalException {
		return wikiPagePersistence.findByPrimaryKey(pageId);
	}

	@Override
	public WikiPageDisplay getPageDisplay(
			long nodeId, String title, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		return getPageDisplay(
			page, viewPageURL, editPageURL, attachmentURLPrefix);
	}

	@Override
	public WikiPageDisplay getPageDisplay(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PortalException {

		String formattedContent = WikiUtil.convert(
			page, viewPageURL, editPageURL, attachmentURLPrefix);

		return new WikiPageDisplayImpl(
			page.getUserId(), page.getNodeId(), page.getTitle(),
			page.getVersion(), page.getContent(), formattedContent,
			page.getFormat(), page.getHead(), page.getAttachmentsFileEntries());
	}

	@Override
	public List<WikiPage> getPages(
		long nodeId, boolean head, int start, int end) {

		return getPages(
			nodeId, head, start, end, new PageCreateDateComparator(false));
	}

	@Override
	public List<WikiPage> getPages(
		long nodeId, boolean head, int status, int start, int end) {

		return getPages(
			nodeId, head, status, start, end,
			new PageCreateDateComparator(false));
	}

	@Override
	public List<WikiPage> getPages(
		long nodeId, boolean head, int status, int start, int end,
		OrderByComparator<WikiPage> obc) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return wikiPagePersistence.findByN_H(nodeId, head, start, end, obc);
		}
		else {
			return wikiPagePersistence.findByN_H_S(
				nodeId, head, status, start, end, obc);
		}
	}

	@Override
	public List<WikiPage> getPages(
		long nodeId, boolean head, int start, int end,
		OrderByComparator<WikiPage> obc) {

		return getPages(
			nodeId, head, WorkflowConstants.STATUS_APPROVED, start, end, obc);
	}

	@Override
	public List<WikiPage> getPages(long nodeId, int start, int end) {
		return getPages(
			nodeId, start, end, new PageCreateDateComparator(false));
	}

	@Override
	public List<WikiPage> getPages(
		long nodeId, int start, int end, OrderByComparator<WikiPage> obc) {

		return wikiPagePersistence.findByNodeId(nodeId, start, end, obc);
	}

	@Override
	public List<WikiPage> getPages(
		long resourcePrimKey, long nodeId, int status) {

		return wikiPagePersistence.findByR_N_S(resourcePrimKey, nodeId, status);
	}

	@Override
	public List<WikiPage> getPages(
		long userId, long nodeId, int status, int start, int end) {

		if (userId > 0) {
			return wikiPagePersistence.findByU_N_S(
				userId, nodeId, status, start, end,
				new PageCreateDateComparator(false));
		}
		else {
			return wikiPagePersistence.findByN_S(
				nodeId, status, start, end,
				new PageCreateDateComparator(false));
		}
	}

	@Override
	public List<WikiPage> getPages(
		long nodeId, String title, boolean head, int start, int end) {

		return wikiPagePersistence.findByN_T_H(
			nodeId, title, head, start, end,
			new PageCreateDateComparator(false));
	}

	@Override
	public List<WikiPage> getPages(
		long nodeId, String title, int start, int end) {

		return wikiPagePersistence.findByN_T(
			nodeId, title, start, end, new PageCreateDateComparator(false));
	}

	@Override
	public List<WikiPage> getPages(
		long nodeId, String title, int start, int end,
		OrderByComparator<WikiPage> obc) {

		return wikiPagePersistence.findByN_T(nodeId, title, start, end, obc);
	}

	@Override
	public List<WikiPage> getPages(String format) {
		return wikiPagePersistence.findByFormat(format);
	}

	@Override
	public int getPagesCount(long nodeId) {
		return wikiPagePersistence.countByNodeId(nodeId);
	}

	@Override
	public int getPagesCount(long nodeId, boolean head) {
		return wikiPagePersistence.countByN_H_S(
			nodeId, head, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getPagesCount(long nodeId, boolean head, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return wikiPagePersistence.countByN_H_NotS(
				nodeId, head, WorkflowConstants.STATUS_IN_TRASH);
		}
		else {
			return wikiPagePersistence.countByN_H_S(nodeId, head, status);
		}
	}

	@Override
	public int getPagesCount(long nodeId, int status) {
		return wikiPagePersistence.countByN_S(nodeId, status);
	}

	@Override
	public int getPagesCount(long userId, long nodeId, int status) {
		if (userId > 0) {
			return wikiPagePersistence.countByU_N_S(userId, nodeId, status);
		}
		else {
			return wikiPagePersistence.countByN_S(nodeId, status);
		}
	}

	@Override
	public int getPagesCount(long nodeId, String title) {
		return wikiPagePersistence.countByN_T(nodeId, title);
	}

	@Override
	public int getPagesCount(long nodeId, String title, boolean head) {
		return wikiPagePersistence.countByN_T_H(nodeId, title, head);
	}

	@Override
	public int getPagesCount(String format) {
		return wikiPagePersistence.countByFormat(format);
	}

	@Override
	public WikiPage getPreviousVersionPage(WikiPage page)
		throws PortalException {

		double previousVersion = MathUtil.format(page.getVersion() - 0.1, 1, 1);

		if (previousVersion < 1) {
			return null;
		}

		return getPage(page.getNodeId(), page.getTitle(), previousVersion);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getRecentChanges(long, long,
	 *             int, int)}
	 */
	@Deprecated
	@Override
	public List<WikiPage> getRecentChanges(long nodeId, int start, int end)
		throws PortalException {

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		return getRecentChanges(node.getGroupId(), nodeId, start, end);
	}

	@Override
	public List<WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end) {

		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.findByCreateDate(
			groupId, nodeId, cal.getTime(), false, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getRecentChangesCount(long,
	 *             long)}
	 */
	@Deprecated
	@Override
	public int getRecentChangesCount(long nodeId) throws PortalException {
		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		return getRecentChangesCount(node.getGroupId(), nodeId);
	}

	@Override
	public int getRecentChangesCount(long groupId, long nodeId) {
		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.countByCreateDate(
			groupId, nodeId, cal.getTime(), false);
	}

	@Override
	public List<WikiPage> getRedirectorPages(
		long nodeId, boolean head, String redirectTitle, int status) {

		return wikiPagePersistence.findByN_H_R_S(
			nodeId, head, redirectTitle, status);
	}

	@Override
	public List<WikiPage> getRedirectorPages(
		long nodeId, String redirectTitle) {

		return wikiPagePersistence.findByN_R(nodeId, redirectTitle);
	}

	@Override
	public String[] getTempFileNames(
			long groupId, long userId, String folderName)
		throws PortalException {

		return TempFileEntryUtil.getTempFileNames(groupId, userId, folderName);
	}

	@Override
	public boolean hasDraftPage(long nodeId, String title) {
		int count = wikiPagePersistence.countByN_T_S(
			nodeId, title, WorkflowConstants.STATUS_DRAFT);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void moveDependentToTrash(WikiPage page, long trashEntryId)
		throws PortalException {

		moveDependentToTrash(page, trashEntryId, false);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #renamePage(long, long,
	 *             String, String, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void movePage(
			long userId, long nodeId, String title, String newTitle,
			boolean strict, ServiceContext serviceContext)
		throws PortalException {

		renamePage(userId, nodeId, title, newTitle, strict, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #renamePage(long, long,
	 *             String, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void movePage(
			long userId, long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException {

		renamePage(userId, nodeId, title, newTitle, true, serviceContext);
	}

	@Override
	public FileEntry movePageAttachmentToTrash(
			long userId, long nodeId, String title, String fileName)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			page.getGroupId(), page.getAttachmentsFolderId(), fileName);

		fileEntry = PortletFileRepositoryUtil.movePortletFileEntryToTrash(
			userId, fileEntry.getFileEntryId());

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
		extraDataJSONObject.put(
			"fileEntryTitle", TrashUtil.getOriginalTitle(fileEntry.getTitle()));
		extraDataJSONObject.put("title", page.getTitle());
		extraDataJSONObject.put("version", page.getVersion());

		SocialActivityManagerUtil.addActivity(
			userId, page, SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		return fileEntry;
	}

	@Override
	public WikiPage movePageFromTrash(
			long userId, long nodeId, String title, long newNodeId,
			String newParentTitle)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		if (page.isInTrashExplicitly()) {
			movePageFromTrash(userId, page, newNodeId, newParentTitle);
		}
		else {
			moveDependentFromTrash(page, newNodeId, newParentTitle);
		}

		return page;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #movePageFromTrash(long,
	 *             long, String, long, String)} *
	 */
	@Deprecated
	@Override
	public WikiPage movePageFromTrash(
			long userId, long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException {

		return movePageFromTrash(userId, nodeId, title, nodeId, newParentTitle);
	}

	@Override
	public WikiPage movePageToTrash(long userId, long nodeId, String title)
		throws PortalException {

		List<WikiPage> wikiPages = wikiPagePersistence.findByN_T_H(
			nodeId, title, true, 0, 1);

		if (!wikiPages.isEmpty()) {
			return movePageToTrash(userId, wikiPages.get(0));
		}

		return null;
	}

	@Override
	public WikiPage movePageToTrash(
			long userId, long nodeId, String title, double version)
		throws PortalException {

		WikiPage page = wikiPagePersistence.findByN_T_V(nodeId, title, version);

		return movePageToTrash(userId, page);
	}

	@Override
	public WikiPage movePageToTrash(long userId, WikiPage page)
		throws PortalException {

		// Page

		int oldStatus = page.getStatus();
		String oldTitle = page.getTitle();

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			page.setStatus(WorkflowConstants.STATUS_DRAFT);

			wikiPagePersistence.update(page);
		}

		List<WikiPage> pageVersions = wikiPagePersistence.findByR_N_H(
			page.getResourcePrimKey(), page.getNodeId(), false);

		pageVersions = ListUtil.sort(pageVersions, new PageVersionComparator());

		List<ObjectValuePair<Long, Integer>> pageVersionStatusOVPs =
			new ArrayList<>();

		if ((pageVersions != null) && !pageVersions.isEmpty()) {
			pageVersionStatusOVPs = getPageVersionStatuses(pageVersions);
		}

		page = updateStatus(
			userId, page, WorkflowConstants.STATUS_IN_TRASH,
			new ServiceContext(), new HashMap<String, Serializable>());

		// Trash

		WikiPageResource pageResource =
			wikiPageResourcePersistence.fetchByPrimaryKey(
				page.getResourcePrimKey());

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("title", page.getTitle());

		TrashEntry trashEntry = trashEntryLocalService.addTrashEntry(
			userId, page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(), page.getUuid(), null, oldStatus,
			pageVersionStatusOVPs, typeSettingsProperties);

		String trashTitle = TrashUtil.getTrashTitle(trashEntry.getEntryId());

		for (WikiPage pageVersion : pageVersions) {
			pageVersion.setTitle(trashTitle);
			pageVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

			wikiPagePersistence.update(pageVersion);
		}

		pageResource.setTitle(trashTitle);

		wikiPageResourcePersistence.update(pageResource);

		page.setTitle(trashTitle);

		wikiPagePersistence.update(page);

		// Child pages

		moveDependentChildPagesToTrash(
			page.getNodeId(), oldTitle, trashTitle, trashEntry.getEntryId(),
			true);

		// Redirect pages

		moveDependentRedirectorPagesToTrash(
			page.getNodeId(), oldTitle, trashTitle, trashEntry.getEntryId(),
			true);

		// Asset

		assetEntryLocalService.updateVisible(
			WikiPage.class.getName(), page.getResourcePrimKey(), false);

		// Attachments

		for (FileEntry fileEntry : page.getAttachmentsFileEntries()) {
			PortletFileRepositoryUtil.movePortletFileEntryToTrash(
				userId, fileEntry.getFileEntryId());
		}

		// Comment

		CommentManagerUtil.moveDiscussionToTrash(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put(
			"title", TrashUtil.getOriginalTitle(page.getTitle()));
		extraDataJSONObject.put("version", page.getVersion());

		SocialActivityManagerUtil.addActivity(
			userId, page, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		if (!pageVersions.isEmpty()) {
			Indexer<WikiPage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				WikiPage.class);

			for (WikiPage pageVersion : pageVersions) {
				indexer.reindex(pageVersion);
			}
		}

		// Workflow

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				page.getCompanyId(), page.getGroupId(),
				WikiPage.class.getName(), page.getPageId());
		}

		return page;
	}

	@Override
	public void renamePage(
			long userId, long nodeId, String title, String newTitle,
			boolean strict, ServiceContext serviceContext)
		throws PortalException {

		validateTitle(newTitle);

		if (StringUtil.equalsIgnoreCase(title, newTitle)) {
			throw new DuplicatePageException(newTitle);
		}

		if (isUsedTitle(nodeId, newTitle)) {

			// Support moving back to a previously moved title

			WikiPage page = getPage(nodeId, newTitle);

			if (((page.getVersion() == WikiPageConstants.VERSION_DEFAULT) &&
				 (page.getContent().length() < 200)) ||
				!strict) {

				deletePage(nodeId, newTitle);
			}
			else {
				throw new DuplicatePageException(newTitle);
			}
		}

		WikiPage page = getPage(nodeId, title);

		String summary = page.getSummary();

		if (Validator.isNotNull(page.getRedirectTitle())) {
			page.setRedirectTitle(StringPool.BLANK);

			summary = StringPool.BLANK;
		}

		serviceContext.setCommand(Constants.RENAME);

		updatePage(
			userId, page, 0, newTitle, page.getContent(), summary,
			page.getMinorEdit(), page.getFormat(), page.getParentTitle(),
			page.getRedirectTitle(), serviceContext);
	}

	@Override
	public void renamePage(
			long userId, long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException {

		renamePage(userId, nodeId, title, newTitle, true, serviceContext);
	}

	@Override
	public void restorePageAttachmentFromTrash(
			long userId, long nodeId, String title, String fileName)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			page.getGroupId(), page.getAttachmentsFolderId(), fileName);

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("fileEntryId", fileEntry.getFileEntryId());
		extraDataJSONObject.put(
			"fileEntryTitle", TrashUtil.getOriginalTitle(fileEntry.getTitle()));
		extraDataJSONObject.put("title", page.getTitle());
		extraDataJSONObject.put("version", page.getVersion());

		PortletFileRepositoryUtil.restorePortletFileEntryFromTrash(
			userId, fileEntry.getFileEntryId());

		SocialActivityManagerUtil.addActivity(
			userId, page,
			SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH,
			extraDataJSONObject.toString(), 0);
	}

	@Override
	public void restorePageFromTrash(long userId, WikiPage page)
		throws PortalException {

		if (page.isInTrashExplicitly()) {
			movePageFromTrash(
				userId, page, page.getNodeId(), page.getParentTitle());
		}
		else {
			moveDependentFromTrash(
				page, page.getNodeId(), page.getParentTitle());
		}
	}

	@Override
	public WikiPage revertPage(
			long userId, long nodeId, String title, double version,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPage oldPage = getPage(nodeId, title, version);

		populateServiceContext(serviceContext, oldPage);

		return updatePage(
			userId, nodeId, title, 0, oldPage.getContent(),
			WikiPageConstants.REVERTED + " to " + version, false,
			oldPage.getFormat(), getParentPageTitle(oldPage),
			oldPage.getRedirectTitle(), serviceContext);
	}

	@Override
	public void subscribePage(long userId, long nodeId, String title)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		subscriptionLocalService.addSubscription(
			userId, page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey());
	}

	@Override
	public void unsubscribePage(long userId, long nodeId, String title)
		throws PortalException {

		WikiPage page = getPage(nodeId, title);

		subscriptionLocalService.deleteSubscription(
			userId, WikiPage.class.getName(), page.getResourcePrimKey());
	}

	@Override
	public void updateAsset(
			long userId, WikiPage page, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException {

		boolean addDraftAssetEntry = false;

		if (!page.isApproved() &&
			(page.getVersion() != WikiPageConstants.VERSION_DEFAULT)) {

			int approvedPagesCount = wikiPagePersistence.countByN_T_S(
				page.getNodeId(), page.getTitle(),
				WorkflowConstants.STATUS_APPROVED);

			if (approvedPagesCount > 0) {
				addDraftAssetEntry = true;
			}
		}

		AssetEntry assetEntry = null;

		if (addDraftAssetEntry) {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, page.getGroupId(), page.getCreateDate(),
				page.getModifiedDate(), WikiPage.class.getName(),
				page.getPrimaryKey(), page.getUuid(), 0, assetCategoryIds,
				assetTagNames, false, null, null, null, ContentTypes.TEXT_HTML,
				page.getTitle(), null, null, null, null, 0, 0, null, false);
		}
		else {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, page.getGroupId(), page.getCreateDate(),
				page.getModifiedDate(), WikiPage.class.getName(),
				page.getResourcePrimKey(), page.getUuid(), 0, assetCategoryIds,
				assetTagNames, page.isApproved(), null, null, null,
				ContentTypes.TEXT_HTML, page.getTitle(), null, null, null, null,
				0, 0, null, false);
		}

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Override
	public WikiPage updatePage(
			long userId, long nodeId, String title, double version,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException {

		// Page

		WikiPage oldPage = null;

		try {
			oldPage = wikiPagePersistence.findByN_T_First(nodeId, title, null);

			if ((version > 0) && (version != oldPage.getVersion())) {
				throw new PageVersionException();
			}

			return updatePage(
				userId, oldPage, 0, StringPool.BLANK, content, summary,
				minorEdit, format, parentTitle, redirectTitle, serviceContext);
		}
		catch (NoSuchPageException nspe) {
			return addPage(
				userId, nodeId, title, WikiPageConstants.VERSION_DEFAULT,
				content, summary, minorEdit, format, true, parentTitle,
				redirectTitle, serviceContext);
		}
	}

	@Override
	public WikiPage updateStatus(
			long userId, long resourcePrimKey, int status,
			ServiceContext serviceContext)
		throws PortalException {

		WikiPageResource pageResource =
			wikiPageResourceLocalService.getPageResource(resourcePrimKey);

		List<WikiPage> pages = wikiPagePersistence.findByN_T(
			pageResource.getNodeId(), pageResource.getTitle(), 0, 1,
			new PageVersionComparator());

		WikiPage page = null;

		if (!pages.isEmpty()) {
			page = pages.get(0);
		}
		else {
			throw new NoSuchPageException(
				"{resourcePrimKey=" + resourcePrimKey + "}");
		}

		return updateStatus(
			userId, page, status, serviceContext,
			new HashMap<String, Serializable>());
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, WikiPage,
	 *             int, ServiceContext, Map)}
	 */
	@Deprecated
	@Override
	public WikiPage updateStatus(
			long userId, WikiPage page, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return updateStatus(
			userId, page, status, serviceContext,
			new HashMap<String, Serializable>());
	}

	@Override
	public WikiPage updateStatus(
			long userId, WikiPage page, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		// Page

		User user = userPersistence.findByPrimaryKey(userId);

		int oldStatus = page.getStatus();

		page.setStatus(status);
		page.setStatusByUserId(userId);
		page.setStatusByUserName(user.getFullName());
		page.setStatusDate(new Date());

		wikiPagePersistence.update(page);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			String cmd = GetterUtil.getString(
				workflowContext.get(WorkflowConstants.CONTEXT_COMMAND));

			if (cmd.equals(Constants.RENAME)) {
				long resourcePrimKey = page.getResourcePrimKey();

				WikiPage oldPage = getPage(resourcePrimKey, true);

				page = doRenamePage(
					userId, page.getNodeId(), oldPage.getTitle(),
					page.getTitle(), serviceContext);
			}

			// Asset

			if ((oldStatus != WorkflowConstants.STATUS_APPROVED) &&
				(page.getVersion() != WikiPageConstants.VERSION_DEFAULT)) {

				AssetEntry draftAssetEntry = assetEntryLocalService.fetchEntry(
					WikiPage.class.getName(), page.getPrimaryKey());

				if (draftAssetEntry != null) {
					long[] assetCategoryIds = draftAssetEntry.getCategoryIds();
					String[] assetTagNames = draftAssetEntry.getTagNames();

					List<AssetLink> assetLinks =
						assetLinkLocalService.getDirectLinks(
							draftAssetEntry.getEntryId(),
							AssetLinkConstants.TYPE_RELATED);

					long[] assetLinkEntryIds = ListUtil.toLongArray(
						assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

					AssetEntry assetEntry = assetEntryLocalService.updateEntry(
						userId, page.getGroupId(), page.getCreateDate(),
						page.getModifiedDate(), WikiPage.class.getName(),
						page.getResourcePrimKey(), page.getUuid(), 0,
						assetCategoryIds, assetTagNames, true, null, null, null,
						ContentTypes.TEXT_HTML, page.getTitle(), null, null,
						null, null, 0, 0, null, false);

					// Asset Links

					assetLinkLocalService.updateLinks(
						userId, assetEntry.getEntryId(), assetLinkEntryIds,
						AssetLinkConstants.TYPE_RELATED);

					SystemEventHierarchyEntryThreadLocal.push(WikiPage.class);

					try {
						assetEntryLocalService.deleteEntry(
							draftAssetEntry.getEntryId());
					}
					finally {
						SystemEventHierarchyEntryThreadLocal.pop(
							WikiPage.class);
					}
				}
			}

			assetEntryLocalService.updateVisible(
				WikiPage.class.getName(), page.getResourcePrimKey(), true);

			// Social

			WikiGroupServiceOverriddenConfiguration
				wikiGroupServiceOverriddenConfiguration =
					configurationFactory.getConfiguration(
						WikiGroupServiceOverriddenConfiguration.class,
						new GroupServiceSettingsLocator(
							page.getGroupId(), WikiConstants.SERVICE_NAME));

			if ((oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
				(!page.isMinorEdit() ||
				 wikiGroupServiceOverriddenConfiguration.
					 pageMinorEditAddSocialActivity())) {

				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject();

				extraDataJSONObject.put("title", page.getTitle());
				extraDataJSONObject.put("version", page.getVersion());

				int type = WikiActivityKeys.UPDATE_PAGE;

				if (serviceContext.isCommandAdd()) {
					type = WikiActivityKeys.ADD_PAGE;
				}

				SocialActivityManagerUtil.addActivity(
					userId, page, type, extraDataJSONObject.toString(), 0);
			}

			// Subscriptions

			if (NotificationThreadLocal.isEnabled() &&
				(!page.isMinorEdit() ||
				 wikiGroupServiceOverriddenConfiguration.
					 pageMinorEditSendEmail())) {

				notifySubscribers(
					userId, page,
					(String)workflowContext.get(WorkflowConstants.CONTEXT_URL),
					serviceContext);
			}

			// Cache

			clearPageCache(page);
		}

		// Head

		if (status == WorkflowConstants.STATUS_APPROVED) {
			page.setHead(true);

			List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
				page.getNodeId(), page.getTitle(), true);

			for (WikiPage curPage : pages) {
				if (!curPage.equals(page)) {
					curPage.setHead(false);

					wikiPagePersistence.update(curPage);
				}
			}
		}
		else if (status != WorkflowConstants.STATUS_IN_TRASH) {
			page.setHead(false);

			List<WikiPage> pages = wikiPagePersistence.findByN_T_S(
				page.getNodeId(), page.getTitle(),
				WorkflowConstants.STATUS_APPROVED);

			for (WikiPage curPage : pages) {
				if (!curPage.equals(page)) {
					curPage.setHead(true);

					wikiPagePersistence.update(curPage);

					break;
				}
			}
		}

		// Indexer

		Indexer<WikiPage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WikiPage.class);

		indexer.reindex(page);

		return wikiPagePersistence.update(page);
	}

	@Override
	public void validateTitle(String title) throws PortalException {
		if (title.equals("all_pages") || title.equals("orphan_pages") ||
			title.equals("recent_changes")) {

			throw new PageTitleException(title + " is reserved");
		}

		if (Validator.isNotNull(
				wikiGroupServiceConfiguration.pageTitlesRegexp())) {

			Pattern pattern = Pattern.compile(
				wikiGroupServiceConfiguration.pageTitlesRegexp());

			Matcher matcher = pattern.matcher(title);

			if (!matcher.matches()) {
				throw new PageTitleException();
			}
		}
	}

	protected void clearPageCache(WikiPage page) {
		if (!WikiCacheThreadLocal.isClearCache()) {
			return;
		}

		WikiCacheUtil.clearCache(page.getNodeId());
	}

	protected void deletePageAttachment(long fileEntryId)
		throws PortalException {

		PortletFileRepositoryUtil.deletePortletFileEntry(fileEntryId);
	}

	protected WikiPage doRenamePage(
			long userId, long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException {

		// Version pages

		List<WikiPage> versionPages = wikiPagePersistence.findByN_T(
			nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageVersionComparator());

		WikiPage page = fetchLatestPage(
			nodeId, newTitle, WorkflowConstants.STATUS_ANY, false);

		if (page == null) {
			page = getLatestPage(
				nodeId, title, WorkflowConstants.STATUS_ANY, false);
		}

		for (WikiPage versionPage : versionPages) {
			versionPage.setRedirectTitle(page.getRedirectTitle());
			versionPage.setTitle(newTitle);

			wikiPagePersistence.update(versionPage);
		}

		// Page resource

		long resourcePrimKey = page.getResourcePrimKey();

		WikiPageResource pageResource =
			wikiPageResourcePersistence.findByPrimaryKey(resourcePrimKey);

		pageResource.setTitle(newTitle);

		wikiPageResourcePersistence.update(pageResource);

		// Create stub page at the old location

		double version = WikiPageConstants.VERSION_DEFAULT;
		String summary = LanguageUtil.format(
			serviceContext.getLocale(), "renamed-as-x", newTitle);
		String format = page.getFormat();
		boolean head = true;
		String parentTitle = page.getParentTitle();
		String redirectTitle = page.getTitle();
		String content =
			StringPool.DOUBLE_OPEN_BRACKET + redirectTitle +
				StringPool.DOUBLE_CLOSE_BRACKET;

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		populateServiceContext(serviceContext, page);

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		WorkflowThreadLocal.setEnabled(false);

		serviceContext.setCommand(Constants.ADD);

		addPage(
			userId, nodeId, title, version, content, summary, false, format,
			head, parentTitle, redirectTitle, serviceContext);

		WorkflowThreadLocal.setEnabled(workflowEnabled);

		// Child pages

		List<WikiPage> childPages = wikiPagePersistence.findByN_P(
			nodeId, title);

		for (WikiPage childPage : childPages) {
			childPage.setParentTitle(newTitle);

			wikiPagePersistence.update(childPage);
		}

		// Redirect pages

		List<WikiPage> redirectorPages = getRedirectorPages(nodeId, title);

		for (WikiPage redirectorPage : redirectorPages) {
			redirectorPage.setRedirectTitle(newTitle);

			wikiPagePersistence.update(redirectorPage);
		}

		// Asset

		updateAsset(
			userId, page, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		return page;
	}

	protected String getDiffsURL(
			WikiPage page, WikiPage previousVersionPage,
			ServiceContext serviceContext)
		throws PortalException {

		if (previousVersionPage == null) {
			return StringPool.BLANK;
		}

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = null;

		long plid = serviceContext.getPlid();

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletURL = PortalUtil.getControlPanelPortletURL(
				request, WikiPortletKeys.WIKI_ADMIN,
				PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = PortletURLFactoryUtil.create(
				request, WikiPortletKeys.WIKI, plid,
				PortletRequest.RENDER_PHASE);
		}

		portletURL.setParameter(
			"mvcRenderCommandName", "/wiki/compare_versions");
		portletURL.setParameter("nodeId", String.valueOf(page.getNodeId()));
		portletURL.setParameter("title", page.getTitle());
		portletURL.setParameter(
			"sourceVersion", String.valueOf(previousVersionPage.getVersion()));
		portletURL.setParameter(
			"targetVersion", String.valueOf(page.getVersion()));
		portletURL.setParameter("type", "html");

		return portletURL.toString();
	}

	protected String getPageURL(WikiPage page, ServiceContext serviceContext)
		throws PortalException {

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return StringPool.BLANK;
		}

		String layoutFullURL = LayoutURLUtil.getLayoutURL(
			page.getGroupId(), WikiPortletKeys.WIKI, serviceContext);

		if (Validator.isNotNull(layoutFullURL)) {
			return layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "wiki/" +
				page.getNodeId() + StringPool.SLASH +
					HttpUtil.encodeURL(
						WikiEscapeUtil.escapeName(page.getTitle()));
		}
		else {
			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				request, WikiPortletKeys.WIKI_ADMIN,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName", "/wiki/view_page_activities");
			portletURL.setParameter("nodeId", String.valueOf(page.getNodeId()));
			portletURL.setParameter("title", page.getTitle());

			return portletURL.toString();
		}
	}

	protected List<ObjectValuePair<Long, Integer>> getPageVersionStatuses(
		List<WikiPage> pages) {

		List<ObjectValuePair<Long, Integer>> pageVersionStatusOVPs =
			new ArrayList<>(pages.size());

		for (WikiPage page : pages) {
			int status = page.getStatus();

			if (status == WorkflowConstants.STATUS_PENDING) {
				status = WorkflowConstants.STATUS_DRAFT;
			}

			ObjectValuePair<Long, Integer> pageVersionStatusOVP =
				new ObjectValuePair<>(page.getPageId(), status);

			pageVersionStatusOVPs.add(pageVersionStatusOVP);
		}

		return pageVersionStatusOVPs;
	}

	protected String getParentPageTitle(WikiPage page) {

		// LPS-4586

		try {
			WikiPage parentPage = getPage(
				page.getNodeId(), page.getParentTitle());

			return parentPage.getTitle();
		}
		catch (Exception e) {
			return null;
		}
	}

	protected boolean isLinkedTo(WikiPage page, String targetTitle)
		throws PortalException {

		Map<String, Boolean> links = WikiCacheUtil.getOutgoingLinks(page);

		Boolean link = links.get(StringUtil.toLowerCase(targetTitle));

		if (link != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isUsedTitle(long nodeId, String title) {
		if (getPagesCount(nodeId, title, true) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void moveDependentChildPagesFromTrash(
			WikiPage newParentPage, long oldParentPageNodeId,
			String oldParentPageTitle)
		throws PortalException {

		List<WikiPage> childPages = getChildren(
			oldParentPageNodeId, true, oldParentPageTitle,
			WorkflowConstants.STATUS_IN_TRASH);

		for (WikiPage childPage : childPages) {
			childPage = getPageByPageId(childPage.getPageId());

			childPage.setParentTitle(newParentPage.getTitle());

			wikiPagePersistence.update(childPage);

			if (childPage.isInTrashImplicitly()) {
				moveDependentFromTrash(
					childPage, newParentPage.getNodeId(),
					newParentPage.getTitle());
			}
		}
	}

	protected void moveDependentChildPagesToTrash(
			long parentNodeId, String parentTitle, String parentTrashTitle,
			long trashEntryId, boolean createTrashVersion)
		throws PortalException {

		List<WikiPage> childPages = wikiPagePersistence.findByN_H_P(
			parentNodeId, true, parentTitle);

		for (WikiPage childPage : childPages) {
			childPage = getPageByPageId(childPage.getPageId());

			childPage.setParentTitle(parentTrashTitle);

			wikiPagePersistence.update(childPage);

			if (!childPage.isInTrash()) {
				moveDependentToTrash(
					childPage, trashEntryId, createTrashVersion);
			}
		}
	}

	protected void moveDependentFromTrash(
			WikiPage page, long newNodeId, String newParentTitle)
		throws PortalException {

		// Page

		String trashTitle = page.getTitle();

		TrashVersion trashVersion = trashVersionLocalService.fetchVersion(
			WikiPage.class.getName(), page.getPageId());

		long oldNodeId = page.getNodeId();

		if (newNodeId == 0) {
			newNodeId = oldNodeId;
		}

		page.setNodeId(newNodeId);

		WikiPageResource pageResource =
			wikiPageResourcePersistence.findByPrimaryKey(
				page.getResourcePrimKey());

		pageResource.setNodeId(newNodeId);

		if (trashVersion != null) {
			String originalTitle = TrashUtil.getOriginalTitle(page.getTitle());

			pageResource.setTitle(originalTitle);

			page.setTitle(originalTitle);
		}

		wikiPageResourcePersistence.update(pageResource);

		page.setParentTitle(newParentTitle);

		wikiPagePersistence.update(page);

		int oldStatus = WorkflowConstants.STATUS_APPROVED;

		if (trashVersion != null) {
			oldStatus = trashVersion.getStatus();
		}

		// Version pages

		List<WikiPage> versionPages = wikiPagePersistence.findByR_N(
			page.getResourcePrimKey(), page.getNodeId());

		for (WikiPage versionPage : versionPages) {

			// Version page

			versionPage.setParentTitle(newParentTitle);
			versionPage.setNodeId(newNodeId);
			versionPage.setTitle(page.getTitle());

			trashVersion = trashVersionLocalService.fetchVersion(
				WikiPage.class.getName(), versionPage.getPageId());

			int versionPageOldStatus = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				versionPageOldStatus = trashVersion.getStatus();
			}

			versionPage.setStatus(versionPageOldStatus);

			wikiPagePersistence.update(versionPage);

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}
		}

		// Asset

		if (oldStatus == WorkflowConstants.STATUS_APPROVED) {
			assetEntryLocalService.updateVisible(
				WikiPage.class.getName(), page.getResourcePrimKey(), true);
		}

		// Attachments

		WikiNode node = page.getNode();

		for (FileEntry fileEntry : page.getAttachmentsFileEntries()) {
			PortletFileRepositoryUtil.restorePortletFileEntryFromTrash(
				node.getStatusByUserId(), fileEntry.getFileEntryId());
		}

		// Index

		Indexer<WikiPage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WikiPage.class);

		indexer.reindex(page);

		// Child pages

		moveDependentChildPagesFromTrash(page, oldNodeId, trashTitle);

		// Redirect pages

		moveDependentRedirectorPagesFromTrash(page, oldNodeId, trashTitle);
	}

	protected void moveDependentRedirectorPagesFromTrash(
			WikiPage newRedirectPage, long oldRedirectPageNodeId,
			String oldRedirectPageTrashTitle)
		throws PortalException {

		List<WikiPage> redirectorPages = getRedirectorPages(
			oldRedirectPageNodeId, true, oldRedirectPageTrashTitle,
			WorkflowConstants.STATUS_IN_TRASH);

		for (WikiPage redirectorPage : redirectorPages) {
			redirectorPage = getPageByPageId(redirectorPage.getPageId());

			redirectorPage.setRedirectTitle(newRedirectPage.getTitle());

			wikiPagePersistence.update(redirectorPage);

			if (redirectorPage.isInTrashImplicitly()) {
				moveDependentFromTrash(
					redirectorPage, newRedirectPage.getNodeId(),
					redirectorPage.getParentTitle());
			}
		}
	}

	protected void moveDependentRedirectorPagesToTrash(
			long redirectPageNodeId, String redirectPageTitle,
			String redirectPageTrashTitle, long trashEntryId,
			boolean createTrashVersion)
		throws PortalException {

		List<WikiPage> redirectorPages = wikiPagePersistence.findByN_H_R(
			redirectPageNodeId, true, redirectPageTitle);

		for (WikiPage redirectorPage : redirectorPages) {
			redirectorPage = getPageByPageId(redirectorPage.getPageId());

			redirectorPage.setRedirectTitle(redirectPageTrashTitle);

			wikiPagePersistence.update(redirectorPage);

			if (!redirectorPage.isInTrash()) {
				moveDependentToTrash(
					redirectorPage, trashEntryId, createTrashVersion);
			}
		}
	}

	protected void moveDependentToTrash(
			WikiPage page, long trashEntryId, boolean createTrashVersion)
		throws PortalException {

		// Page

		String oldTitle = page.getTitle();

		String trashTitle = oldTitle;

		if (createTrashVersion) {
			UnicodeProperties typeSettingsProperties = new UnicodeProperties();

			typeSettingsProperties.put("title", oldTitle);

			TrashVersion trashVersion =
				trashVersionLocalService.addTrashVersion(
					trashEntryId, WikiPage.class.getName(), page.getPageId(),
					page.getStatus(), typeSettingsProperties);

			trashTitle = TrashUtil.getTrashTitle(trashVersion.getVersionId());

			WikiPageResource pageResource =
				wikiPageResourcePersistence.findByPrimaryKey(
					page.getResourcePrimKey());

			pageResource.setTitle(trashTitle);

			wikiPageResourcePersistence.update(pageResource);

			page.setTitle(trashTitle);

			wikiPagePersistence.update(page);
		}

		int oldStatus = page.getStatus();

		if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
			return;
		}

		// Version pages

		List<WikiPage> versionPages = wikiPagePersistence.findByR_N(
			page.getResourcePrimKey(), page.getNodeId());

		for (WikiPage versionPage : versionPages) {

			// Version page

			versionPage.setTitle(page.getTitle());

			int versionPageOldStatus = versionPage.getStatus();

			versionPage.setStatus(WorkflowConstants.STATUS_IN_TRASH);

			wikiPagePersistence.update(versionPage);

			// Trash

			int status = versionPageOldStatus;

			if (versionPageOldStatus == WorkflowConstants.STATUS_PENDING) {
				status = WorkflowConstants.STATUS_DRAFT;
			}

			if (versionPageOldStatus != WorkflowConstants.STATUS_APPROVED) {
				trashVersionLocalService.addTrashVersion(
					trashEntryId, WikiPage.class.getName(),
					versionPage.getPageId(), status, null);
			}
		}

		// Asset

		if (oldStatus == WorkflowConstants.STATUS_APPROVED) {
			assetEntryLocalService.updateVisible(
				WikiPage.class.getName(), page.getResourcePrimKey(), false);
		}

		// Attachments

		WikiNode node = page.getNode();

		for (FileEntry fileEntry : page.getAttachmentsFileEntries()) {
			PortletFileRepositoryUtil.movePortletFileEntryToTrash(
				node.getStatusByUserId(), fileEntry.getFileEntryId());
		}

		// Indexer

		Indexer<WikiPage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WikiPage.class);

		indexer.reindex(page);

		// Cache

		if (WikiCacheThreadLocal.isClearCache()) {
			WikiCacheUtil.clearCache(page.getNodeId());
		}

		// Workflow

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				page.getCompanyId(), page.getGroupId(),
				WikiPage.class.getName(), page.getResourcePrimKey());
		}

		// Child pages

		moveDependentChildPagesToTrash(
			page.getNodeId(), oldTitle, trashTitle, trashEntryId,
			createTrashVersion);

		// Redirect pages

		moveDependentRedirectorPagesToTrash(
			page.getNodeId(), oldTitle, trashTitle, trashEntryId,
			createTrashVersion);
	}

	protected void movePageFromTrash(
			long userId, WikiPage page, long newNodeId, String newParentTitle)
		throws PortalException {

		// Page

		String trashTitle = page.getTitle();

		String originalTitle = TrashUtil.getOriginalTitle(trashTitle);

		long oldNodeId = page.getNodeId();

		if (newNodeId == 0) {
			newNodeId = oldNodeId;
		}

		List<WikiPage> pageVersions = wikiPagePersistence.findByR_N_H(
			page.getResourcePrimKey(), oldNodeId, false);

		for (WikiPage pageVersion : pageVersions) {
			pageVersion.setParentTitle(newParentTitle);
			pageVersion.setNodeId(newNodeId);
			pageVersion.setTitle(originalTitle);

			wikiPagePersistence.update(pageVersion);
		}

		WikiPageResource pageResource =
			wikiPageResourcePersistence.fetchByPrimaryKey(
				page.getResourcePrimKey());

		pageResource.setNodeId(newNodeId);
		pageResource.setTitle(originalTitle);

		wikiPageResourcePersistence.update(pageResource);

		page.setNodeId(newNodeId);
		page.setTitle(originalTitle);

		WikiPage parentPage = page.getParentPage();

		if ((parentPage != null) && parentPage.isInTrash()) {
			page.setParentTitle(StringPool.BLANK);
		}

		if (Validator.isNotNull(newParentTitle)) {
			WikiPage newParentPage = getPage(newNodeId, newParentTitle);

			if (!newParentPage.isInTrash()) {
				page.setParentTitle(newParentTitle);
			}
		}

		WikiPage redirectPage = page.getRedirectPage();

		if ((redirectPage != null) && redirectPage.isInTrash()) {
			page.setRedirectTitle(StringPool.BLANK);
		}

		wikiPagePersistence.update(page);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		updateStatus(
			userId, page, trashEntry.getStatus(), new ServiceContext(),
			new HashMap<String, Serializable>());

		// Attachments

		for (FileEntry fileEntry : page.getAttachmentsFileEntries()) {
			PortletFileRepositoryUtil.restorePortletFileEntryFromTrash(
				userId, fileEntry.getFileEntryId());
		}

		// Child pages

		moveDependentChildPagesFromTrash(page, oldNodeId, trashTitle);

		// Redirect pages

		moveDependentRedirectorPagesFromTrash(page, oldNodeId, trashTitle);

		// Trash

		List<TrashVersion> trashVersions = trashVersionLocalService.getVersions(
			trashEntry.getEntryId());

		for (TrashVersion trashVersion : trashVersions) {
			WikiPage trashArticleVersion = wikiPagePersistence.findByPrimaryKey(
				trashVersion.getClassPK());

			trashArticleVersion.setStatus(trashVersion.getStatus());

			wikiPagePersistence.update(trashArticleVersion);
		}

		trashEntryLocalService.deleteEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Comment

		CommentManagerUtil.restoreDiscussionFromTrash(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", page.getTitle());
		extraDataJSONObject.put("version", page.getVersion());

		SocialActivityManagerUtil.addActivity(
			userId, page, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		if (!pageVersions.isEmpty()) {
			Indexer<WikiPage> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				WikiPage.class);

			for (WikiPage pageVersion : pageVersions) {
				indexer.reindex(pageVersion);
			}
		}
	}

	protected void notifySubscribers(
			long userId, WikiPage page, String pageURL,
			ServiceContext serviceContext)
		throws PortalException {

		if (!page.isApproved() || Validator.isNull(pageURL)) {
			return;
		}

		WikiGroupServiceOverriddenConfiguration
			wikiGroupServiceOverriddenConfiguration =
				configurationFactory.getConfiguration(
					WikiGroupServiceOverriddenConfiguration.class,
					new GroupServiceSettingsLocator(
						page.getGroupId(), WikiConstants.SERVICE_NAME));

		boolean update = false;

		if (page.getVersion() > WikiPageConstants.VERSION_DEFAULT) {
			update = true;
		}

		if (!update &&
			wikiGroupServiceOverriddenConfiguration.emailPageAddedEnabled()) {
		}
		else if (update &&
				 wikiGroupServiceOverriddenConfiguration.
					 emailPageUpdatedEnabled()) {
		}
		else {
			return;
		}

		String portalURL = serviceContext.getPortalURL();

		WikiPage previousVersionPage = getPreviousVersionPage(page);

		String attachmentURLPrefix = WikiUtil.getAttachmentURLPrefix(
			serviceContext.getPathMain(), serviceContext.getPlid(),
			page.getNodeId(), page.getTitle());

		attachmentURLPrefix = portalURL + attachmentURLPrefix;

		String pageDiffs = StringPool.BLANK;

		try {
			pageDiffs = WikiUtil.diffHtml(
				previousVersionPage, page, null, null, attachmentURLPrefix);
		}
		catch (Exception e) {
		}

		String pageContent = null;

		if (Validator.equals(page.getFormat(), "creole")) {
			pageContent = WikiUtil.convert(
				page, null, null, attachmentURLPrefix);
		}
		else {
			pageContent = page.getContent();
			pageContent = WikiUtil.processContent(pageContent);
		}

		String pageTitle = page.getTitle();

		String fromName =
			wikiGroupServiceOverriddenConfiguration.emailFromName();
		String fromAddress =
			wikiGroupServiceOverriddenConfiguration.emailFromAddress();

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (update) {
			subjectLocalizedValuesMap =
				wikiGroupServiceOverriddenConfiguration.
					emailPageUpdatedSubject();
			bodyLocalizedValuesMap =
				wikiGroupServiceOverriddenConfiguration.emailPageUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				wikiGroupServiceOverriddenConfiguration.emailPageAddedSubject();
			bodyLocalizedValuesMap =
				wikiGroupServiceOverriddenConfiguration.emailPageAddedBody();
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setClassName(page.getModelClassName());
		subscriptionSender.setClassPK(page.getPageId());
		subscriptionSender.setCompanyId(page.getCompanyId());
		subscriptionSender.setContextAttribute(
			"[$PAGE_CONTENT$]", pageContent, false);
		subscriptionSender.setContextAttribute(
			"[$PAGE_DIFFS$]", DiffHtmlUtil.replaceStyles(pageDiffs), false);

		WikiNode node = page.getNode();

		subscriptionSender.setContextAttributes(
			"[$DIFFS_URL$]",
			getDiffsURL(page, previousVersionPage, serviceContext),
			"[$NODE_NAME$]", node.getName(), "[$PAGE_DATE_UPDATE$]",
			page.getModifiedDate(), "[$PAGE_ID$]", page.getPageId(),
			"[$PAGE_SUMMARY$]", page.getSummary(), "[$PAGE_TITLE$]", pageTitle,
			"[$PAGE_URL$]", pageURL);

		subscriptionSender.setContextCreatorUserPrefix("PAGE");
		subscriptionSender.setCreatorUserId(page.getUserId());
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(pageTitle);
		subscriptionSender.setEntryURL(pageURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);

		if (bodyLocalizedValuesMap != null) {
			subscriptionSender.setLocalizedBodyMap(
				LocalizationUtil.getMap(bodyLocalizedValuesMap));
		}

		if (subjectLocalizedValuesMap != null) {
			subscriptionSender.setLocalizedSubjectMap(
				LocalizationUtil.getMap(subjectLocalizedValuesMap));
		}

		subscriptionSender.setMailId(
			"wiki_page", page.getNodeId(), page.getPageId());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		subscriptionSender.setPortletId(WikiPortletKeys.WIKI);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(page.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);

		subscriptionSender.addPersistedSubscribers(
			WikiNode.class.getName(), page.getNodeId());

		subscriptionSender.addPersistedSubscribers(
			WikiPage.class.getName(), page.getResourcePrimKey());

		subscriptionSender.flushNotificationsAsync();
	}

	protected void populateServiceContext(
			ServiceContext serviceContext, WikiPage page)
		throws PortalException {

		long[] assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			WikiPage.class.getName(), page.getResourcePrimKey());

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		List<AssetLink> assetLinks = assetLinkLocalService.getLinks(
			assetEntry.getEntryId());

		long[] assetLinkEntryIds = ListUtil.toLongArray(
			assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

		serviceContext.setAssetLinkEntryIds(assetLinkEntryIds);

		String[] assetTagNames = assetTagLocalService.getTagNames(
			WikiPage.class.getName(), page.getResourcePrimKey());

		serviceContext.setAssetTagNames(assetTagNames);

		ExpandoBridge expandoBridge = page.getExpandoBridge();

		serviceContext.setExpandoBridgeAttributes(
			expandoBridge.getAttributes());
	}

	protected WikiPage startWorkflowInstance(
			long userId, WikiPage page, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(
			WorkflowConstants.CONTEXT_COMMAND, serviceContext.getCommand());
		workflowContext.put(
			WorkflowConstants.CONTEXT_URL, getPageURL(page, serviceContext));

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			page.getCompanyId(), page.getGroupId(), userId,
			WikiPage.class.getName(), page.getPageId(), page, serviceContext,
			workflowContext);
	}

	protected WikiPage updatePage(
			long userId, WikiPage oldPage, long newNodeId, String newTitle,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		long pageId = 0;

		if (oldPage.isApproved()) {
			pageId = counterLocalService.increment();
		}
		else {
			pageId = oldPage.getPageId();
		}

		content = SanitizerUtil.sanitize(
			user.getCompanyId(), oldPage.getGroupId(), userId,
			WikiPage.class.getName(), pageId, "text/" + format, content);

		long nodeId = oldPage.getNodeId();

		if (newNodeId != 0) {
			nodeId = newNodeId;
		}

		validate(nodeId, content, format);

		serviceContext.validateModifiedDate(
			oldPage, PageVersionException.class);

		long resourcePrimKey =
			wikiPageResourceLocalService.getPageResourcePrimKey(
				oldPage.getGroupId(), oldPage.getNodeId(), oldPage.getTitle());

		Date now = new Date();

		WikiPage page = oldPage;

		double newVersion = oldPage.getVersion();

		if (oldPage.isApproved()) {
			newVersion = MathUtil.format(oldPage.getVersion() + 0.1, 1, 1);

			page = wikiPagePersistence.create(pageId);

			page.setUuid(serviceContext.getUuid());
		}

		page.setResourcePrimKey(resourcePrimKey);
		page.setGroupId(oldPage.getGroupId());
		page.setCompanyId(user.getCompanyId());
		page.setUserId(user.getUserId());
		page.setUserName(user.getFullName());
		page.setCreateDate(oldPage.getCreateDate());
		page.setNodeId(nodeId);
		page.setTitle(
			Validator.isNull(newTitle) ? oldPage.getTitle() : newTitle);
		page.setVersion(newVersion);
		page.setMinorEdit(minorEdit);
		page.setContent(content);

		if (oldPage.isPending()) {
			page.setStatus(oldPage.getStatus());
		}
		else {
			page.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		page.setSummary(summary);
		page.setFormat(format);

		if (Validator.isNotNull(parentTitle)) {
			page.setParentTitle(parentTitle);
		}

		if (Validator.isNotNull(redirectTitle)) {
			page.setRedirectTitle(redirectTitle);
		}

		ExpandoBridgeUtil.setExpandoBridgeAttributes(
			oldPage.getExpandoBridge(), page.getExpandoBridge(),
			serviceContext);

		wikiPagePersistence.update(page);

		// Node

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		node.setLastPostDate(serviceContext.getModifiedDate(now));

		wikiNodePersistence.update(node);

		// Asset

		updateAsset(
			userId, page, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Workflow

		page = startWorkflowInstance(userId, page, serviceContext);

		return page;
	}

	protected void validate(long nodeId, String content, String format)
		throws PortalException {

		if (!WikiUtil.validate(nodeId, content, format)) {
			throw new PageContentException();
		}
	}

	protected void validate(
			String title, long nodeId, String content, String format)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new PageTitleException();
		}

		if (isUsedTitle(nodeId, title)) {
			throw new DuplicatePageException("{nodeId=" + nodeId + "}");
		}

		validateTitle(title);

		validate(nodeId, content, format);
	}

	@ServiceReference(type = ConfigurationFactory.class)
	protected ConfigurationFactory configurationFactory;

	@ServiceReference(type = WikiGroupServiceConfiguration.class)
	protected WikiGroupServiceConfiguration wikiGroupServiceConfiguration;

}