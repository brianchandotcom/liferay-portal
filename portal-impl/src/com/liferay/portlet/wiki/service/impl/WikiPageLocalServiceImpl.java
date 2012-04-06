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
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.wiki.DuplicatePageException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.PageTitleException;
import com.liferay.portlet.wiki.PageVersionException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.model.WikiPageDisplay;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiPageDisplayImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.portlet.wiki.service.base.WikiPageLocalServiceBaseImpl;
import com.liferay.portlet.wiki.social.WikiActivityKeys;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.portlet.wiki.util.comparator.PageCreateDateComparator;
import com.liferay.portlet.wiki.util.comparator.PageVersionComparator;
import com.liferay.util.UniqueList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Julio Camarero
 * @author Wesley Gong
 * @author Marcellus Tavares
 * @author Zsigmond Rab
 * @author Zsolt Berentey
 */
public class WikiPageLocalServiceImpl extends WikiPageLocalServiceBaseImpl {

	public WikiPage addPage(
			long userId, long nodeId, String title, double version,
			String content, String summary, boolean minorEdit, String format,
			boolean head, String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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
			wikiPageResourceLocalService.getPageResourcePrimKey(nodeId, title);

		WikiPage page = wikiPagePersistence.create(pageId);

		page.setUuid(serviceContext.getUuid());
		page.setResourcePrimKey(resourcePrimKey);
		page.setGroupId(node.getGroupId());
		page.setCompanyId(user.getCompanyId());
		page.setUserId(user.getUserId());
		page.setUserName(user.getFullName());
		page.setCreateDate(serviceContext.getCreateDate(now));
		page.setModifiedDate(serviceContext.getModifiedDate(now));
		page.setNodeId(nodeId);
		page.setTitle(title);
		page.setVersion(version);
		page.setMinorEdit(minorEdit);
		page.setContent(content);
		page.setStatus(WorkflowConstants.STATUS_DRAFT);
		page.setSummary(summary);
		page.setFormat(format);
		page.setHead(head);
		page.setParentTitle(parentTitle);
		page.setRedirectTitle(redirectTitle);

		wikiPagePersistence.update(page, false);

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

		wikiNodePersistence.update(node, false);

		// Asset

		updateAsset(
			userId, page, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Expando

		ExpandoBridge expandoBridge = page.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Message boards

		if (PropsValues.WIKI_PAGE_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, page.getUserName(), page.getGroupId(),
				WikiPage.class.getName(), resourcePrimKey,
				WorkflowConstants.ACTION_PUBLISH);
		}

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), page.getGroupId(), userId,
			WikiPage.class.getName(), page.getPageId(), page, serviceContext);

		return page;
	}

	public WikiPage addPage(
			long userId, long nodeId, String title, String content,
			String summary, boolean minorEdit, ServiceContext serviceContext)
		throws PortalException, SystemException {

		double version = WikiPageConstants.VERSION_DEFAULT;
		String format = WikiPageConstants.DEFAULT_FORMAT;
		boolean head = false;
		String parentTitle = null;
		String redirectTitle = null;

		return addPage(
			userId, nodeId, title, version, content, summary, minorEdit, format,
			head, parentTitle, redirectTitle, serviceContext);
	}

	public void addPageAttachment(
			long userId, long nodeId, String title, String fileName, File file)
		throws PortalException, SystemException {

		if (Validator.isNull(fileName)) {
			return;
		}

		WikiPage page = getPage(nodeId, title);

		if (userId == 0) {
			userId = page.getUserId();
		}

		socialActivityLocalService.addActivity(
			userId, page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(),
			SocialActivityConstants.TYPE_ADD_ATTACHMENT,
			page.getAttachmentsDir() + "/" + fileName, 0);

		try {
			DLStoreUtil.addDirectory(
				page.getCompanyId(), CompanyConstants.SYSTEM,
				page.getAttachmentsDir());
		}
		catch (DuplicateDirectoryException dde) {
		}

		DLStoreUtil.addFile(
			page.getCompanyId(), CompanyConstants.SYSTEM,
			page.getAttachmentsDir() + "/" + fileName, file);
	}

	public void addPageAttachment(
			long userId, long nodeId, String title, String fileName,
			InputStream inputStream)
		throws PortalException, SystemException {

		if (Validator.isNull(fileName)) {
			return;
		}

		WikiPage page = getPage(nodeId, title);

		if (userId == 0) {
			userId = page.getUserId();
		}

		socialActivityLocalService.addActivity(
			userId, page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(),
			SocialActivityConstants.TYPE_ADD_ATTACHMENT,
			page.getAttachmentsDir() + "/" + fileName, 0);

		try {
			DLStoreUtil.addDirectory(
				page.getCompanyId(), CompanyConstants.SYSTEM,
				page.getAttachmentsDir());
		}
		catch (DuplicateDirectoryException dde) {
		}

		DLStoreUtil.addFile(
			page.getCompanyId(), CompanyConstants.SYSTEM,
			page.getAttachmentsDir() + "/" + fileName, inputStream);
	}

	public void addPageAttachment(
			long companyId, String dirName, Date modifiedDate, String fileName,
			InputStream inputStream)
		throws PortalException, SystemException {

		if (inputStream == null) {
			return;
		}

		long repositoryId = CompanyConstants.SYSTEM;

		try {
			DLStoreUtil.addDirectory(companyId, repositoryId, dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		DLStoreUtil.addFile(
			companyId, repositoryId, dirName + "/" + fileName, false,
			inputStream);
	}

	public void addPageAttachments(
			long userId, long nodeId, String title,
			List<ObjectValuePair<String, InputStream>> inputStreams)
		throws PortalException, SystemException {

		if (inputStreams.size() == 0) {
			return;
		}

		for (int i = 0; i < inputStreams.size(); i++) {
			ObjectValuePair<String, InputStream> ovp = inputStreams.get(i);

			String fileName = ovp.getKey();
			InputStream inputStream = ovp.getValue();

			addPageAttachment(userId, nodeId, title, fileName, inputStream);
		}
	}

	public void addPageResources(
			long nodeId, String title, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		addPageResources(page, addGroupPermissions, addGuestPermissions);
	}

	public void addPageResources(
			long nodeId, String title, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		addPageResources(page, groupPermissions, guestPermissions);
	}

	public void addPageResources(
			WikiPage page, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			page.getCompanyId(), page.getGroupId(), page.getUserId(),
			WikiPage.class.getName(), page.getResourcePrimKey(), false,
			addGroupPermissions, addGuestPermissions);
	}

	public void addPageResources(
			WikiPage page, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			page.getCompanyId(), page.getGroupId(), page.getUserId(),
			WikiPage.class.getName(), page.getResourcePrimKey(),
			groupPermissions, guestPermissions);
	}

	public String addTempPageAttachment(
			long userId, String fileName, String tempFolderName,
			InputStream inputStream)
		throws IOException, PortalException, SystemException {

		return TempFileUtil.addTempFile(
			userId, fileName, tempFolderName, inputStream);
	}

	public void changeParent(
			long userId, long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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

		long[] assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			WikiPage.class.getName(), page.getResourcePrimKey());
		String[] assetTagNames = assetTagLocalService.getTagNames(
			WikiPage.class.getName(), page.getResourcePrimKey());

		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetLinkEntryIds(null);
		serviceContext.setAssetTagNames(assetTagNames);

		updatePage(
			userId, nodeId, title, version, content, summary, minorEdit, format,
			newParentTitle, redirectTitle, serviceContext);

		List<WikiPage> oldPages = wikiPagePersistence.findByN_T_H(
			nodeId, title, false);

		for (WikiPage oldPage : oldPages) {
			oldPage.setParentTitle(originalParentTitle);

			wikiPagePersistence.update(oldPage, false);
		}
	}

	public void deletePage(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
			nodeId, title, true, 0, 1);

		if (!pages.isEmpty()) {
			WikiPage page = pages.iterator().next();

			deletePage(page);
		}
	}

	public void deletePage(long nodeId, String title, double version)
		throws PortalException, SystemException {

		wikiPagePersistence.removeByN_T_V(nodeId, title, version);
	}

	public void deletePage(WikiPage page)
		throws PortalException, SystemException {

		// Children

		List<WikiPage> children = wikiPagePersistence.findByN_H_P(
			page.getNodeId(), true, page.getTitle());

		for (WikiPage curPage : children) {
			deletePage(curPage);
		}

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WikiPage.class);

		indexer.delete(page);

		// Attachments

		long companyId = page.getCompanyId();
		long repositoryId = CompanyConstants.SYSTEM;
		String dirName = page.getAttachmentsDir();

		try {
			DLStoreUtil.deleteDirectory(companyId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			page.getCompanyId(), WikiPage.class.getName(),
			page.getResourcePrimKey());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Expando

		expandoValueLocalService.deleteValues(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Asset

		List<WikiPage> pageVersions = wikiPagePersistence.findByN_T(
			page.getNodeId(), page.getTitle());

		for (WikiPage pageVersion : pageVersions) {
			assetEntryLocalService.deleteEntry(
				WikiPage.class.getName(), pageVersion.getPrimaryKey());
		}

		assetEntryLocalService.deleteEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		// Resources

		resourceLocalService.deleteResource(
			page.getCompanyId(), WikiPage.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, page.getResourcePrimKey());

		// Resource

		try {
			wikiPageResourceLocalService.deletePageResource(
				page.getNodeId(), page.getTitle());
		}
		catch (NoSuchPageResourceException nspre) {
		}

		// All versions

		List<WikiPage> pages = wikiPagePersistence.findByN_T(
			page.getNodeId(), page.getTitle());

		for (WikiPage curPage : pages) {

			// Workflow

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				curPage.getCompanyId(), curPage.getGroupId(),
				WikiPage.class.getName(), curPage.getPageId());
		}

		wikiPagePersistence.removeByN_T(page.getNodeId(), page.getTitle());

		// All referrals

		wikiPagePersistence.removeByN_R(page.getNodeId(), page.getTitle());

		// Cache

		clearPageCache(page);
	}

	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException, SystemException {

		if (Validator.isNull(fileName)) {
			return;
		}

		WikiPage page = getPage(nodeId, title);

		long companyId = page.getCompanyId();
		long repositoryId = CompanyConstants.SYSTEM;

		try {
			DLStoreUtil.deleteFile(companyId, repositoryId, fileName);
		}
		catch (NoSuchFileException nsfe) {
		}
	}

	public void deletePages(long nodeId)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_H_P(
			nodeId, true, StringPool.BLANK);

		for (WikiPage page : pages) {
			deletePage(page);
		}

		pages = wikiPagePersistence.findByN_H_P(
			nodeId, false, StringPool.BLANK);

		for (WikiPage page : pages) {
			deletePage(page);
		}
	}

	public void deleteTempPageAttachment(
		long userId, String fileName, String tempFolderName) {

		TempFileUtil.deleteTempFile(userId, fileName, tempFolderName);
	}

	public List<WikiPage> getChildren(
			long nodeId, boolean head, String parentTitle)
		throws SystemException {

		return wikiPagePersistence.findByN_H_P_S(
			nodeId, head, parentTitle, WorkflowConstants.STATUS_APPROVED);
	}

	public WikiPage getDraftPage(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_T_S(
			nodeId, title, WorkflowConstants.STATUS_DRAFT, 0, 1);

		if (!pages.isEmpty()) {
			return pages.get(0);
		}
		else {
			pages = wikiPagePersistence.findByN_T_S(
				nodeId, title, WorkflowConstants.STATUS_PENDING, 0, 1);

			if (!pages.isEmpty()) {
				return pages.get(0);
			}
			else {
				throw new NoSuchPageException();
			}
		}

	}

	public List<WikiPage> getIncomingLinks(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> links = new UniqueList<WikiPage>();

		List<WikiPage> pages = wikiPagePersistence.findByN_H(nodeId, true);

		for (WikiPage page : pages) {
			if (isLinkedTo(page, title)) {
				links.add(page);
			}
		}

		List<WikiPage> referrals = wikiPagePersistence.findByN_R(nodeId, title);

		for (WikiPage referral : referrals) {
			for (WikiPage page : pages) {
				if (isLinkedTo(page, referral.getTitle())) {
					links.add(page);
				}
			}
		}

		return ListUtil.sort(links);
	}

	public List<WikiPage> getNoAssetPages() throws SystemException {
		return wikiPageFinder.findByNoAssets();
	}

	public List<WikiPage> getOrphans(long nodeId)
		throws PortalException, SystemException {

		List<Map<String, Boolean>> pageTitles =
			new ArrayList<Map<String, Boolean>>();

		List<WikiPage> pages = wikiPagePersistence.findByN_H_S(
			nodeId, true, WorkflowConstants.STATUS_APPROVED);

		for (WikiPage page : pages) {
			pageTitles.add(WikiCacheUtil.getOutgoingLinks(page));
		}

		Set<WikiPage> notOrphans = new HashSet<WikiPage>();

		for (WikiPage page : pages) {
			for (Map<String, Boolean> pageTitle : pageTitles) {
				if (pageTitle.get(page.getTitle().toLowerCase()) != null) {
					notOrphans.add(page);

					break;
				}
			}
		}

		List<WikiPage> orphans = new ArrayList<WikiPage>();

		for (WikiPage page : pages) {
			if (!notOrphans.contains(page)) {
				orphans.add(page);
			}
		}

		orphans = ListUtil.sort(orphans);

		return orphans;
	}

	public List<WikiPage> getOutgoingLinks(long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		Map<String, WikiPage> pages = new LinkedHashMap<String, WikiPage>();

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

	public WikiPage getPage(long resourcePrimKey)
		throws PortalException, SystemException {

		return getPage(resourcePrimKey, Boolean.TRUE);
	}

	public WikiPage getPage(long resourcePrimKey, Boolean head)
		throws PortalException, SystemException {

		WikiPageResource wikiPageResource =
			wikiPageResourceLocalService.getPageResource(resourcePrimKey);

		return getPage(
			wikiPageResource.getNodeId(), wikiPageResource.getTitle(), head);
	}

	public WikiPage getPage(long nodeId, String title)
		throws PortalException, SystemException {

		List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
			nodeId, title, true, 0, 1);

		if (!pages.isEmpty()) {
			return pages.get(0);
		}
		else {
			throw new NoSuchPageException();
		}
	}

	public WikiPage getPage(long nodeId, String title, Boolean head)
		throws PortalException, SystemException {

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
			throw new NoSuchPageException();
		}
	}

	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException, SystemException {

		WikiPage page = null;

		if (version == 0) {
			page = getPage(nodeId, title);
		}
		else {
			page = wikiPagePersistence.findByN_T_V(nodeId, title, version);
		}

		return page;
	}

	public WikiPage getPageByPageId(long pageId)
		throws PortalException, SystemException {

		return wikiPagePersistence.findByPrimaryKey(pageId);
	}

	public WikiPageDisplay getPageDisplay(
			long nodeId, String title, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		return getPageDisplay(
			page, viewPageURL, editPageURL, attachmentURLPrefix);
	}

	public WikiPageDisplay getPageDisplay(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PortalException, SystemException {

		String formattedContent = WikiUtil.convert(
			page, viewPageURL, editPageURL, attachmentURLPrefix);

		return new WikiPageDisplayImpl(
			page.getUserId(), page.getNodeId(), page.getTitle(),
			page.getVersion(), page.getContent(), formattedContent,
			page.getFormat(), page.getHead(), page.getAttachmentsFiles());
	}

	public List<WikiPage> getPages(
			long nodeId, boolean head, int start, int end)
		throws SystemException {

		return getPages(
			nodeId, head, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(
			long nodeId, boolean head, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return wikiPagePersistence.findByN_H_S(
			nodeId, head, WorkflowConstants.STATUS_APPROVED, start, end, obc);
	}

	public List<WikiPage> getPages(long nodeId, int start, int end)
		throws SystemException {

		return getPages(
			nodeId, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(
			long nodeId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return wikiPagePersistence.findByNodeId(nodeId, start, end, obc);
	}

	public List<WikiPage> getPages(
			long resourcePrimKey, long nodeId, int status)
		throws SystemException {

		return wikiPagePersistence.findByR_N_S(resourcePrimKey, nodeId, status);
	}

	public List<WikiPage> getPages(
			long userId, long nodeId, int status, int start, int end)
		throws SystemException {

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

	public List<WikiPage> getPages(
			long nodeId, String title, boolean head, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByN_T_H(
			nodeId, title, head, start, end,
			new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(
			long nodeId, String title, int start, int end)
		throws SystemException {

		return wikiPagePersistence.findByN_T(
			nodeId, title, start, end, new PageCreateDateComparator(false));
	}

	public List<WikiPage> getPages(
			long nodeId, String title, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return wikiPagePersistence.findByN_T(nodeId, title, start, end, obc);
	}

	public List<WikiPage> getPages(String format) throws SystemException {
		return wikiPagePersistence.findByFormat(format);
	}

	public int getPagesCount(long nodeId) throws SystemException {
		return wikiPagePersistence.countByNodeId(nodeId);
	}

	public int getPagesCount(long nodeId, boolean head) throws SystemException {
		return wikiPagePersistence.countByN_H_S(
			nodeId, head, WorkflowConstants.STATUS_APPROVED);
	}

	public int getPagesCount(long userId, long nodeId, int status)
		throws SystemException {

		if (userId > 0) {
			return wikiPagePersistence.countByU_N_S(userId, nodeId, status);
		}
		else {
			return wikiPagePersistence.countByN_S(nodeId, status);
		}
	}

	public int getPagesCount(long nodeId, String title) throws SystemException {
		return wikiPagePersistence.countByN_T(nodeId, title);
	}

	public int getPagesCount(long nodeId, String title, boolean head)
		throws SystemException {

		return wikiPagePersistence.countByN_T_H(nodeId, title, head);
	}

	public int getPagesCount(String format) throws SystemException {
		return wikiPagePersistence.countByFormat(format);
	}

	public List<WikiPage> getRecentChanges(long nodeId, int start, int end)
		throws SystemException {

		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.findByCreateDate(
			nodeId, cal.getTime(), false, start, end);
	}

	public int getRecentChangesCount(long nodeId) throws SystemException {
		Calendar cal = CalendarFactoryUtil.getCalendar();

		cal.add(Calendar.WEEK_OF_YEAR, -1);

		return wikiPageFinder.countByCreateDate(nodeId, cal.getTime(), false);
	}

	public String[] getTempPageAttachmentNames(
		long userId, String tempFolderName) {

		return TempFileUtil.getTempFileEntryNames(userId, tempFolderName);
	}

	public boolean hasDraftPage(long nodeId, String title)
		throws SystemException {

		int count = wikiPagePersistence.countByN_T_S(
			nodeId, title, WorkflowConstants.STATUS_DRAFT);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void movePage(
			long userId, long nodeId, String title, String newTitle,
			boolean strict, ServiceContext serviceContext)
		throws PortalException, SystemException {

		validateTitle(newTitle);

		// Check if the new title already exists

		if (title.equalsIgnoreCase(newTitle)) {
			throw new DuplicatePageException(newTitle);
		}

		if (isUsedTitle(nodeId, newTitle)) {
			WikiPage page = getPage(nodeId, newTitle);

			// Support moving back to a previously moved title

			if (((page.getVersion() == WikiPageConstants.VERSION_DEFAULT) &&
				 (page.getContent().length() < 200)) ||
				!strict) {

				deletePage(nodeId, newTitle);
			}
			else {
				throw new DuplicatePageException(newTitle);
			}
		}

		// All versions

		List<WikiPage> pageVersions = wikiPagePersistence.findByN_T(
			nodeId, title);

		if (pageVersions.size() == 0) {
			return;
		}

		for (WikiPage page : pageVersions) {
			page.setTitle(newTitle);

			wikiPagePersistence.update(page, false);
		}

		// Children

		List<WikiPage> children = wikiPagePersistence.findByN_P(nodeId, title);

		for (WikiPage page : children) {
			page.setParentTitle(newTitle);

			wikiPagePersistence.update(page, false);
		}

		WikiPage page = pageVersions.get(pageVersions.size() - 1);

		long resourcePrimKey = page.getResourcePrimKey();

		// Page resource

		WikiPageResource wikiPageResource =
			wikiPageResourcePersistence.findByPrimaryKey(resourcePrimKey);

		wikiPageResource.setTitle(newTitle);

		wikiPageResourcePersistence.update(wikiPageResource, false);

		// Create stub page at the old location

		double version = WikiPageConstants.VERSION_DEFAULT;
		String summary = WikiPageConstants.MOVED + " to " + title;
		String format = page.getFormat();
		boolean head = true;
		String parentTitle = page.getParentTitle();
		String redirectTitle = page.getTitle();
		String content =
			StringPool.DOUBLE_OPEN_BRACKET + redirectTitle +
				StringPool.DOUBLE_CLOSE_BRACKET;

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			WikiPage.class.getName(), page.getResourcePrimKey());

		List<AssetLink> assetLinks = assetLinkLocalService.getDirectLinks(
			assetEntry.getEntryId(), AssetLinkConstants.TYPE_RELATED);

		long[] assetLinkEntryIds = StringUtil.split(
			ListUtil.toString(assetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L);

		serviceContext.setAssetLinkEntryIds(assetLinkEntryIds);

		addPage(
			userId, nodeId, title, version, content, summary, false, format,
			head, parentTitle, redirectTitle, serviceContext);

		// Move redirects to point to the page with the new title

		List<WikiPage> redirectedPages = wikiPagePersistence.findByN_R(
			nodeId, title);

		for (WikiPage redirectedPage : redirectedPages) {
			redirectedPage.setRedirectTitle(newTitle);

			wikiPagePersistence.update(redirectedPage, false);
		}

		// Asset

		updateAsset(userId, page, null, null, null);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WikiPage.class);

		indexer.delete(
			new Object[] {page.getCompanyId(), page.getNodeId(), title});

		indexer.reindex(page);
	}

	public void movePage(
			long userId, long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		movePage(userId, nodeId, title, newTitle, true, serviceContext);
	}

	public WikiPage revertPage(
			long userId, long nodeId, String title, double version,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPage oldPage = getPage(nodeId, title, version);

		return updatePage(
			userId, nodeId, title, 0, oldPage.getContent(),
			WikiPageConstants.REVERTED + " to " + version, false,
			oldPage.getFormat(), getParentPageTitle(oldPage),
			oldPage.getRedirectTitle(), serviceContext);
	}

	public void subscribePage(long userId, long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		subscriptionLocalService.addSubscription(
			userId, page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey());
	}

	public void unsubscribePage(long userId, long nodeId, String title)
		throws PortalException, SystemException {

		WikiPage page = getPage(nodeId, title);

		subscriptionLocalService.deleteSubscription(
			userId, WikiPage.class.getName(), page.getResourcePrimKey());
	}

	public void updateAsset(
			long userId, WikiPage page, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException, SystemException {

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
				userId, page.getGroupId(), WikiPage.class.getName(),
				page.getPrimaryKey(), page.getUuid(), 0, assetCategoryIds,
				assetTagNames, false, null, null, null, null,
				ContentTypes.TEXT_HTML, page.getTitle(), null, null, null, null,
				0, 0, null, false);
		}
		else {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, page.getGroupId(), WikiPage.class.getName(),
				page.getResourcePrimKey(), page.getUuid(), 0, assetCategoryIds,
				assetTagNames, page.isApproved(), null, null, null, null,
				ContentTypes.TEXT_HTML, page.getTitle(), null, null, null, null,
				0, 0, null, false);
		}

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	public WikiPage updatePage(
			long userId, long nodeId, String title, double version,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Page

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		WikiPage oldPage = null;

		try {
			oldPage = wikiPagePersistence.findByN_T_First(nodeId, title, null);
		}
		catch (NoSuchPageException nspe) {
			return addPage(
				userId, nodeId, title, WikiPageConstants.VERSION_DEFAULT,
				content, summary, minorEdit, format, true, parentTitle,
				redirectTitle, serviceContext);
		}

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

		validate(nodeId, content, format);

		double oldVersion = oldPage.getVersion();

		if ((version > 0) && (version != oldVersion)) {
			throw new PageVersionException();
		}

		serviceContext.validateModifiedDate(
			oldPage, PageVersionException.class);

		long resourcePrimKey =
			wikiPageResourceLocalService.getPageResourcePrimKey(nodeId, title);
		long groupId = oldPage.getGroupId();

		WikiPage page = oldPage;

		double newVersion = oldVersion;

		if (oldPage.isApproved()) {
			newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

			page = wikiPagePersistence.create(pageId);
		}

		page.setResourcePrimKey(resourcePrimKey);
		page.setGroupId(groupId);
		page.setCompanyId(user.getCompanyId());
		page.setUserId(user.getUserId());
		page.setUserName(user.getFullName());
		page.setCreateDate(serviceContext.getModifiedDate(now));
		page.setModifiedDate(serviceContext.getModifiedDate(now));
		page.setNodeId(nodeId);
		page.setTitle(title);
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

		wikiPagePersistence.update(page, false);

		// Expando

		ExpandoBridge expandoBridge = page.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Node

		WikiNode node = wikiNodePersistence.findByPrimaryKey(nodeId);

		node.setLastPostDate(serviceContext.getModifiedDate(now));

		wikiNodePersistence.update(node, false);

		// Asset

		updateAsset(
			userId, page, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), page.getGroupId(), userId,
			WikiPage.class.getName(), page.getPageId(), page, serviceContext);

		return page;
	}

	public WikiPage updateStatus(
			long userId, long resourcePrimKey, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		WikiPageResource wikiPageResource =
			wikiPageResourceLocalService.getPageResource(resourcePrimKey);

		List<WikiPage> pages = wikiPagePersistence.findByN_T(
			wikiPageResource.getNodeId(), wikiPageResource.getTitle(), 0, 1,
			new PageVersionComparator());

		WikiPage page = null;

		if (!pages.isEmpty()) {
			page = pages.get(0);
		}
		else {
			throw new NoSuchPageException();
		}

		return updateStatus(userId, page, status, serviceContext);
	}

	public WikiPage updateStatus(
			long userId, WikiPage page, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Page

		User user = userPersistence.findByPrimaryKey(userId);
		WikiNode node = wikiNodePersistence.findByPrimaryKey(page.getNodeId());

		Date now = new Date();

		int oldStatus = page.getStatus();

		page.setStatus(status);
		page.setStatusByUserId(userId);
		page.setStatusByUserName(user.getFullName());
		page.setStatusDate(now);

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			if ((oldStatus != WorkflowConstants.STATUS_APPROVED) &&
				(page.getVersion() != WikiPageConstants.VERSION_DEFAULT)) {

				try {
					AssetEntry draftAssetEntry =
						assetEntryLocalService.getEntry(
							WikiPage.class.getName(), page.getPrimaryKey());

					long[] assetCategoryIds = draftAssetEntry.getCategoryIds();
					String[] assetTagNames = draftAssetEntry.getTagNames();

					List<AssetLink> assetLinks =
						assetLinkLocalService.getDirectLinks(
							draftAssetEntry.getEntryId(),
							AssetLinkConstants.TYPE_RELATED);

					long[] assetLinkEntryIds = StringUtil.split(
						ListUtil.toString(
							assetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L);

					AssetEntry assetEntry = assetEntryLocalService.updateEntry(
						userId, page.getGroupId(), WikiPage.class.getName(),
						page.getResourcePrimKey(), page.getUuid(), 0,
						assetCategoryIds, assetTagNames, true, null, null, null,
						null, ContentTypes.TEXT_HTML, page.getTitle(), null,
						null, null, null, 0, 0, null, false);

					// Asset Links

					assetLinkLocalService.updateLinks(
						userId, assetEntry.getEntryId(), assetLinkEntryIds,
						AssetLinkConstants.TYPE_RELATED);

					assetEntryLocalService.deleteEntry(
						draftAssetEntry.getEntryId());
				}
				catch (NoSuchEntryException nsee) {
				}
			}

			assetEntryLocalService.updateVisible(
				WikiPage.class.getName(), page.getResourcePrimKey(), true);

			// Social

			int activity = WikiActivityKeys.ADD_PAGE;

			if (page.getVersion() > 1.1) {
				activity = WikiActivityKeys.UPDATE_PAGE;
			}

			socialActivityLocalService.addActivity(
				userId, page.getGroupId(), WikiPage.class.getName(),
				page.getResourcePrimKey(), activity, StringPool.BLANK, 0);

			// Subscriptions

			if (!page.isMinorEdit() && NotificationThreadLocal.isEnabled()) {
				boolean update = false;

				if (page.getVersion() > 1.1) {
					update = true;
				}

				notifySubscribers(node, page, serviceContext, update);
			}

			// Indexer

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				WikiPage.class);

			indexer.reindex(page);

			// Cache

			clearPageCache(page);

			// Head

			page.setHead(true);

			List<WikiPage> pages = wikiPagePersistence.findByN_T_H(
				page.getNodeId(), page.getTitle(), true);

			for (WikiPage curPage : pages) {
				if (!curPage.equals(page)) {
					curPage.setHead(false);

					wikiPagePersistence.update(curPage, false);
				}
			}
		}
		else {

			// Head

			page.setHead(false);

			List<WikiPage> pages = wikiPagePersistence.findByN_T_S(
				page.getNodeId(), page.getTitle(),
				WorkflowConstants.STATUS_APPROVED);

			for (WikiPage curPage : pages) {
				if (!curPage.equals(page)) {
					curPage.setHead(true);

					wikiPagePersistence.update(curPage, false);

					break;
				}
			}
		}

		return wikiPagePersistence.update(page, false);
	}

	public void validateTitle(String title) throws PortalException {
		if (title.equals("all_pages") || title.equals("orphan_pages") ||
			title.equals("recent_changes")) {

			throw new PageTitleException(title + " is reserved");
		}

		if (Validator.isNotNull(PropsValues.WIKI_PAGE_TITLES_REGEXP)) {
			Pattern pattern = Pattern.compile(
				PropsValues.WIKI_PAGE_TITLES_REGEXP);

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

	protected WikiPage getPreviousVersionPage(WikiPage page)
		throws PortalException, SystemException {

		double previousVersion = MathUtil.format(page.getVersion() - 0.1, 1, 1);

		if (previousVersion < 1) {
			return null;
		}

		return getPage(page.getNodeId(), page.getTitle(), previousVersion);
	}

	protected boolean isLinkedTo(WikiPage page, String targetTitle)
		throws PortalException {

		Map<String, Boolean> links = WikiCacheUtil.getOutgoingLinks(page);

		Boolean link = links.get(targetTitle.toLowerCase());

		if (link != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isUsedTitle(long nodeId, String title)
		throws SystemException {

		if (getPagesCount(nodeId, title, true) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void notifySubscribers(
			WikiNode node, WikiPage page, ServiceContext serviceContext,
			boolean update)
		throws PortalException, SystemException {

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if (preferences == null) {
			long ownerId = node.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.WIKI;
			String defaultPreferences = null;

			preferences = portletPreferencesLocalService.getPreferences(
				node.getCompanyId(), ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		if (!update && WikiUtil.getEmailPageAddedEnabled(preferences)) {
		}
		else if (update && WikiUtil.getEmailPageUpdatedEnabled(preferences)) {
		}
		else {
			return;
		}

		String portalURL = serviceContext.getPortalURL();
		String layoutFullURL = serviceContext.getLayoutFullURL();

		WikiPage previousVersionPage = getPreviousVersionPage(page);

		String attachmentURLPrefix =
			portalURL + serviceContext.getPathMain() +
				"/wiki/get_page_attachment?p_l_id=" + serviceContext.getPlid() +
					"&nodeId=" + page.getNodeId() + "&title=" +
						HttpUtil.encodeURL(page.getTitle()) + "&fileName=";

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

		String pageURL = StringPool.BLANK;
		String diffsURL = StringPool.BLANK;

		if (Validator.isNotNull(layoutFullURL)) {
			pageURL =
				layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "wiki/" +
					node.getNodeId() + StringPool.SLASH +
						HttpUtil.encodeURL(page.getTitle());

			if (previousVersionPage != null) {
				StringBundler sb = new StringBundler(16);

				sb.append(layoutFullURL);
				sb.append("?p_p_id=");
				sb.append(PortletKeys.WIKI);
				sb.append("&p_p_state=");
				sb.append(WindowState.MAXIMIZED);
				sb.append("&struts_action=");
				sb.append(HttpUtil.encodeURL("/wiki/compare_versions"));
				sb.append("&nodeId=");
				sb.append(node.getNodeId());
				sb.append("&title=");
				sb.append(HttpUtil.encodeURL(page.getTitle()));
				sb.append("&sourceVersion=");
				sb.append(previousVersionPage.getVersion());
				sb.append("&targetVersion=");
				sb.append(page.getVersion());
				sb.append("&type=html");

				diffsURL = sb.toString();
			}
		}

		String fromName = WikiUtil.getEmailFromName(
			preferences, page.getCompanyId());
		String fromAddress = WikiUtil.getEmailFromAddress(
			preferences, page.getCompanyId());

		String subjectPrefix = null;
		String body = null;
		String signature = null;

		if (update) {
			subjectPrefix = WikiUtil.getEmailPageUpdatedSubjectPrefix(
				preferences);
			body = WikiUtil.getEmailPageUpdatedBody(preferences);
			signature = WikiUtil.getEmailPageUpdatedSignature(preferences);
		}
		else {
			subjectPrefix = WikiUtil.getEmailPageAddedSubjectPrefix(
				preferences);
			body = WikiUtil.getEmailPageAddedBody(preferences);
			signature = WikiUtil.getEmailPageAddedSignature(preferences);
		}

		String subject = page.getTitle();

		if (subject.indexOf(subjectPrefix) == -1) {
			subject = subjectPrefix + StringPool.SPACE + subject;
		}

		if (Validator.isNotNull(signature)) {
			body += "\n" + signature;
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(page.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$DIFFS_URL$]", diffsURL, "[$NODE_NAME$]", node.getName(),
			"[$PAGE_DATE_UPDATE$]", page.getModifiedDate(), "[$PAGE_ID$]",
			page.getPageId(), "[$PAGE_SUMMARY$]", page.getSummary(),
			"[$PAGE_TITLE$]", page.getTitle(), "[$PAGE_URL$]", pageURL);
		subscriptionSender.setContextAttribute(
			"[$PAGE_CONTENT$]", pageContent, false);
		subscriptionSender.setContextAttribute(
			"[$PAGE_DIFFS$]", replaceStyles(pageDiffs), false);
		subscriptionSender.setContextUserPrefix("PAGE");
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId(
			"wiki_page", page.getNodeId(), page.getPageId());
		subscriptionSender.setPortletId(PortletKeys.WIKI);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(node.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setSubject(subject);
		subscriptionSender.setUserId(page.getUserId());

		subscriptionSender.addPersistedSubscribers(
			WikiNode.class.getName(), node.getNodeId());
		subscriptionSender.addPersistedSubscribers(
			WikiPage.class.getName(), page.getResourcePrimKey());

		subscriptionSender.flushNotificationsAsync();
	}

	protected String replaceStyles(String html) {
		return StringUtil.replace(
			html,
			new String[] {
				"class=\"diff-html-added\"", "class=\"diff-html-removed\"",
				"class=\"diff-html-changed\"",
				"changeType=\"diff-added-image\"",
				"changeType=\"diff-removed-image\"",
				"changeType=\"diff-changed-image\""
			},
			new String[] {
				"style=\"background-color: #CFC;\"",
				"style=\"background-color: #FDC6C6; text-decoration: " +
					"line-through;\"",
				"style=\"border-bottom: 2px dotted blue;\"",
				"style=\"border: 10px solid #CFC;\"",
				"style=\"border: 10px solid #FDC6C6;\"",
				"style=\"border: 10px solid blue;\""
			}
		);
	}

	protected void validate(long nodeId, String content, String format)
		throws PortalException {

		if (!WikiUtil.validate(nodeId, content, format)) {
			throw new PageContentException();
		}
	}

	protected void validate(
			String title, long nodeId, String content, String format)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			throw new PageTitleException();
		}

		if (isUsedTitle(nodeId, title)) {
			throw new DuplicatePageException();
		}

		validateTitle(title);

		validate(nodeId, content, format);
	}

}