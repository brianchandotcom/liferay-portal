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

package com.liferay.knowledge.base.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.internal.util.KBArticleSiblingNavigationHelper;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBArticleSearchDisplay;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.impl.KBArticleSearchDisplayImpl;
import com.liferay.knowledge.base.service.base.KBArticleServiceBaseImpl;
import com.liferay.knowledge.base.service.permission.AdminPermission;
import com.liferay.knowledge.base.service.permission.DisplayPermission;
import com.liferay.knowledge.base.service.permission.KBArticlePermission;
import com.liferay.knowledge.base.service.util.AdminUtil;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.knowledge.base.util.comparator.KBArticleModifiedDateComparator;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.rss.util.RSSUtil;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class KBArticleServiceImpl extends KBArticleServiceBaseImpl {

	@Override
	public KBArticle addKBArticle(
			String portletId, long parentResourceClassNameId,
			long parentResourcePrimKey, String title, String urlTitle,
			String content, String description, String sourceURL,
			String[] sections, String[] selectedFileNames,
			ServiceContext serviceContext)
		throws PortalException {

		if (portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {
			AdminPermission.check(
				getPermissionChecker(), serviceContext.getScopeGroupId(),
				KBActionKeys.ADD_KB_ARTICLE);
		}
		else if (portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) {
			DisplayPermission.check(
				getPermissionChecker(), serviceContext.getScopeGroupId(),
				KBActionKeys.ADD_KB_ARTICLE);
		}

		return kbArticleLocalService.addKBArticle(
			getUserId(), parentResourceClassNameId, parentResourcePrimKey,
			title, urlTitle, content, description, sourceURL, sections,
			selectedFileNames, serviceContext);
	}

	@Override
	public int addKBArticlesMarkdown(
			long groupId, long parentKBFolderId, String fileName,
			boolean prioritizeByNumericalPrefix, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException {

		AdminPermission.check(
			getPermissionChecker(), groupId, KBActionKeys.IMPORT_KB_ARTICLES);

		return kbArticleLocalService.addKBArticlesMarkdown(
			getUserId(), groupId, parentKBFolderId, fileName,
			prioritizeByNumericalPrefix, inputStream, serviceContext);
	}

	@Override
	public void addTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName, InputStream inputStream, String mimeType)
		throws PortalException {

		_checkAttachmentPermissions(
			groupId, KBPortletKeys.KNOWLEDGE_BASE_ADMIN, resourcePrimKey);

		kbArticleLocalService.addTempAttachment(
			groupId, getUserId(), fileName, tempFolderName, inputStream,
			mimeType);
	}

	@Override
	public KBArticle deleteKBArticle(long resourcePrimKey)
		throws PortalException {

		KBArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, KBActionKeys.DELETE);

		return kbArticleLocalService.deleteKBArticle(resourcePrimKey);
	}

	@Override
	public void deleteKBArticles(long groupId, long[] resourcePrimKeys)
		throws PortalException {

		AdminPermission.check(
			getPermissionChecker(), groupId, KBActionKeys.DELETE_KB_ARTICLES);

		kbArticleLocalService.deleteKBArticles(resourcePrimKeys);
	}

	@Override
	public void deleteTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName)
		throws PortalException {

		_checkAttachmentPermissions(
			groupId, KBPortletKeys.KNOWLEDGE_BASE_ADMIN, resourcePrimKey);

		kbArticleLocalService.deleteTempAttachment(
			groupId, getUserId(), fileName, tempFolderName);
	}

	@Override
	public KBArticle fetchFirstChildKBArticle(
		long groupId, long parentResourcePrimKey) {

		List<KBArticle> kbArticles = kbArticlePersistence.filterFindByG_P_L(
			groupId, parentResourcePrimKey, true, 0, 1,
			new KBArticlePriorityComparator(true));

		if (kbArticles.isEmpty()) {
			return null;
		}

		return kbArticles.get(0);
	}

	@Override
	public KBArticle fetchKBArticleByUrlTitle(
			long groupId, long kbFolderId, String urlTitle)
		throws PortalException {

		KBArticle kbArticle = kbArticleLocalService.fetchKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle);

		if (kbArticle == null) {
			return null;
		}

		if (KBArticlePermission.contains(
				getPermissionChecker(), kbArticle, ActionKeys.VIEW)) {

			return kbArticle;
		}

		return null;
	}

	@Override
	public KBArticle fetchLatestKBArticle(long resourcePrimKey, int status)
		throws PortalException {

		KBArticle kbArticle = kbArticleLocalService.fetchLatestKBArticle(
			resourcePrimKey, status);

		if (kbArticle == null) {
			return null;
		}

		KBArticlePermission.check(
			getPermissionChecker(), kbArticle, KBActionKeys.VIEW);

		return kbArticle;
	}

	/**
	 * @deprecated As of 1.1.0, replaced by {@link
	 *             #getAllDescendantKBArticles(long, long, int,
	 *             OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<KBArticle> getAllDescendantKBArticles(
			long resourcePrimKey, int status,
			OrderByComparator<KBArticle> orderByComparator)
		throws PortalException {

		return getAllDescendantKBArticles(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, resourcePrimKey, status,
			orderByComparator);
	}

	@Override
	public List<KBArticle> getAllDescendantKBArticles(
			long groupId, long resourcePrimKey, int status,
			OrderByComparator<KBArticle> orderByComparator)
		throws PortalException {

		groupId = checkGroupId(groupId, resourcePrimKey);

		return getAllDescendantKBArticles(
			groupId, resourcePrimKey, status, orderByComparator, false);
	}

	@Override
	public List<KBArticle> getGroupKBArticles(
		long groupId, int status, int start, int end,
		OrderByComparator<KBArticle> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return kbArticlePersistence.filterFindByG_L(
				groupId, true, start, end, orderByComparator);
		}
		else if (status == WorkflowConstants.STATUS_APPROVED) {
			return kbArticlePersistence.filterFindByG_M(
				groupId, true, start, end, orderByComparator);
		}

		return kbArticlePersistence.filterFindByG_S(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getGroupKBArticlesCount(long groupId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return kbArticlePersistence.filterCountByG_L(groupId, true);
		}
		else if (status == WorkflowConstants.STATUS_APPROVED) {
			return kbArticlePersistence.filterCountByG_M(groupId, true);
		}

		return kbArticlePersistence.filterCountByG_S(groupId, status);
	}

	@Override
	public String getGroupKBArticlesRSS(
			int status, int rssDelta, String rssDisplayStyle, String rssFormat,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Group group = themeDisplay.getScopeGroup();

		String descriptiveName = HtmlUtil.escape(
			group.getDescriptiveName(themeDisplay.getLocale()));

		String name = descriptiveName;
		String description = descriptiveName;

		String feedURL = PortalUtil.getLayoutFullURL(themeDisplay);

		List<KBArticle> kbArticles = getGroupKBArticles(
			group.getGroupId(), status, 0, rssDelta,
			new KBArticleModifiedDateComparator());

		return exportToRSS(
			rssDisplayStyle, rssFormat, name, description, feedURL, kbArticles,
			themeDisplay);
	}

	@Override
	public KBArticle getKBArticle(long resourcePrimKey, int version)
		throws PortalException {

		KBArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, KBActionKeys.VIEW);

		return kbArticleLocalService.getKBArticle(resourcePrimKey, version);
	}

	@Override
	public List<KBArticle> getKBArticleAndAllDescendantKBArticles(
			long resourcePrimKey, int status,
			OrderByComparator<KBArticle> orderByComparator)
		throws PortalException {

		return getAllDescendantKBArticles(
			resourcePrimKey, status, orderByComparator, true);
	}

	/**
	 * @deprecated As of 1.1.0, replaced by {@link
	 *             #getKBArticleAndAllDescendantKBArticles(long, int,
	 *             OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<KBArticle> getKBArticleAndAllDescendants(
		long groupId, long resourcePrimKey, int status,
		OrderByComparator<KBArticle> orderByComparator) {

		try {
			return getKBArticleAndAllDescendantKBArticles(
				resourcePrimKey, status, orderByComparator);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return Collections.emptyList();
		}
	}

	@Override
	public String getKBArticleRSS(
			long resourcePrimKey, int status, int rssDelta,
			String rssDisplayStyle, String rssFormat, ThemeDisplay themeDisplay)
		throws PortalException {

		KBArticle kbArticle = kbArticleLocalService.getLatestKBArticle(
			resourcePrimKey, status);

		String name = kbArticle.getTitle();
		String description = kbArticle.getTitle();

		String feedURL = KnowledgeBaseUtil.getKBArticleURL(
			themeDisplay.getPlid(), resourcePrimKey, status,
			themeDisplay.getPortalURL(), false);

		List<KBArticle> kbArticles = getAllDescendantKBArticles(
			resourcePrimKey, status, new KBArticleModifiedDateComparator());

		return exportToRSS(
			rssDisplayStyle, rssFormat, name, description, feedURL,
			ListUtil.subList(kbArticles, 0, rssDelta), themeDisplay);
	}

	@Override
	public List<KBArticle> getKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end, OrderByComparator<KBArticle> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return kbArticlePersistence.filterFindByG_P_L(
				groupId, parentResourcePrimKey, true, start, end,
				orderByComparator);
		}
		else if (status == WorkflowConstants.STATUS_APPROVED) {
			return kbArticlePersistence.filterFindByG_P_M(
				groupId, parentResourcePrimKey, true, start, end,
				orderByComparator);
		}

		return kbArticlePersistence.filterFindByG_P_S(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	@Override
	public List<KBArticle> getKBArticles(
		long groupId, long[] resourcePrimKeys, int status, int start, int end,
		OrderByComparator<KBArticle> orderByComparator) {

		List<KBArticle> kbArticles = new ArrayList<>();

		Long[][] params = new Long[][] {ArrayUtil.toArray(resourcePrimKeys)};

		while ((params = KnowledgeBaseUtil.getParams(params[0])) != null) {
			List<KBArticle> curKBArticles = null;

			if (status == WorkflowConstants.STATUS_ANY) {
				curKBArticles = kbArticlePersistence.filterFindByR_G_L(
					ArrayUtil.toArray(params[1]), groupId, true, start, end);
			}
			else if (status == WorkflowConstants.STATUS_APPROVED) {
				curKBArticles = kbArticlePersistence.filterFindByR_G_M(
					ArrayUtil.toArray(params[1]), groupId, true, start, end);
			}
			else {
				curKBArticles = kbArticlePersistence.filterFindByR_G_S(
					ArrayUtil.toArray(params[1]), groupId, status, start, end);
			}

			kbArticles.addAll(curKBArticles);
		}

		if (orderByComparator != null) {
			kbArticles = ListUtil.sort(kbArticles, orderByComparator);
		}
		else {
			kbArticles = KnowledgeBaseUtil.sort(resourcePrimKeys, kbArticles);
		}

		return Collections.unmodifiableList(kbArticles);
	}

	@Override
	public List<KBArticle> getKBArticles(
		long groupId, long[] resourcePrimKeys, int status,
		OrderByComparator<KBArticle> orderByComparator) {

		return getKBArticles(
			groupId, resourcePrimKeys, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, orderByComparator);
	}

	@Override
	public int getKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return kbArticlePersistence.filterCountByG_P_L(
				groupId, parentResourcePrimKey, true);
		}
		else if (status == WorkflowConstants.STATUS_APPROVED) {
			return kbArticlePersistence.filterCountByG_P_M(
				groupId, parentResourcePrimKey, true);
		}

		return kbArticlePersistence.filterCountByG_P_S(
			groupId, parentResourcePrimKey, status);
	}

	@Override
	public int getKBArticlesCount(
		long groupId, long[] resourcePrimKeys, int status) {

		int count = 0;

		Long[][] params = new Long[][] {ArrayUtil.toArray(resourcePrimKeys)};

		while ((params = KnowledgeBaseUtil.getParams(params[0])) != null) {
			if (status == WorkflowConstants.STATUS_ANY) {
				count += kbArticlePersistence.filterCountByR_G_L(
					ArrayUtil.toArray(params[1]), groupId, true);
			}
			else if (status == WorkflowConstants.STATUS_APPROVED) {
				count += kbArticlePersistence.filterCountByR_G_M(
					ArrayUtil.toArray(params[1]), groupId, true);
			}
			else {
				count += kbArticlePersistence.filterCountByR_G_S(
					ArrayUtil.toArray(params[1]), groupId, status);
			}
		}

		return count;
	}

	@Override
	public KBArticleSearchDisplay getKBArticleSearchDisplay(
			long groupId, String title, String content, int status,
			Date startDate, Date endDate, boolean andOperator,
			int[] curStartValues, int cur, int delta,
			OrderByComparator<KBArticle> orderByComparator)
		throws PortalException {

		// See LPS-9546

		int start = 0;

		if (curStartValues.length > (cur - SearchContainer.DEFAULT_CUR)) {
			start = curStartValues[cur - SearchContainer.DEFAULT_CUR];

			curStartValues = ArrayUtil.subset(
				curStartValues, 0, cur - SearchContainer.DEFAULT_CUR + 1);
		}
		else {
			cur = SearchContainer.DEFAULT_CUR;

			curStartValues = new int[] {0};
		}

		int end = start + _INTERVAL;

		List<KBArticle> kbArticles = new ArrayList<>();

		int curStartValue = 0;

		while (curStartValue == 0) {
			List<KBArticle> curKBArticles = kbArticleLocalService.search(
				groupId, title, content, status, startDate, endDate,
				andOperator, start, end, orderByComparator);

			if (curKBArticles.isEmpty()) {
				break;
			}

			for (int i = 0; i < curKBArticles.size(); i++) {
				KBArticle curKBArticle = curKBArticles.get(i);

				if (!KBArticlePermission.contains(
						getPermissionChecker(), curKBArticle,
						KBActionKeys.VIEW)) {

					continue;
				}

				if (kbArticles.size() == delta) {
					curStartValue = start + i;

					break;
				}

				kbArticles.add(curKBArticle);
			}

			start = start + _INTERVAL;

			end = start + _INTERVAL;
		}

		int total = ((cur - 1) * delta) + kbArticles.size();

		if (curStartValue > 0) {
			curStartValues = ArrayUtil.append(curStartValues, curStartValue);

			total = total + 1;
		}

		return new KBArticleSearchDisplayImpl(
			kbArticles, total, curStartValues);
	}

	@Override
	public List<KBArticle> getKBArticleVersions(
		long groupId, long resourcePrimKey, int status, int start, int end,
		OrderByComparator<KBArticle> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return kbArticlePersistence.filterFindByR_G(
				resourcePrimKey, groupId, start, end, orderByComparator);
		}

		return kbArticlePersistence.filterFindByR_G_S(
			resourcePrimKey, groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getKBArticleVersionsCount(
		long groupId, long resourcePrimKey, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return kbArticlePersistence.filterCountByR_G(
				resourcePrimKey, groupId);
		}

		return kbArticlePersistence.filterCountByR_G_S(
			resourcePrimKey, groupId, status);
	}

	@Override
	public KBArticle getLatestKBArticle(long resourcePrimKey, int status)
		throws PortalException {

		KBArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, KBActionKeys.VIEW);

		return kbArticleLocalService.getLatestKBArticle(
			resourcePrimKey, status);
	}

	@Override
	public KBArticle[] getPreviousAndNextKBArticles(long kbArticleId)
		throws PortalException {

		KBArticle kbArticle = kbArticlePersistence.findByPrimaryKey(
			kbArticleId);

		KBArticlePermission.check(
			getPermissionChecker(), kbArticle, KBActionKeys.VIEW);

		KBArticleSiblingNavigationHelper kbArticleSiblingNavigationHelper =
			new KBArticleSiblingNavigationHelper(kbArticlePersistence);

		return kbArticleSiblingNavigationHelper.getPreviousAndNextKBArticles(
			kbArticleId);
	}

	@Override
	public List<KBArticle> getSectionsKBArticles(
		long groupId, String[] sections, int status, int start, int end,
		OrderByComparator<KBArticle> orderByComparator) {

		String[] array = AdminUtil.escapeSections(sections);

		for (int i = 0; i < array.length; i++) {
			array[i] = StringUtil.quote(array[i], StringPool.PERCENT);
		}

		if (status == WorkflowConstants.STATUS_ANY) {
			return kbArticlePersistence.filterFindByG_S_L(
				groupId, array, true, start, end, orderByComparator);
		}
		else if (status == WorkflowConstants.STATUS_APPROVED) {
			return kbArticlePersistence.filterFindByG_S_M(
				groupId, array, true, start, end, orderByComparator);
		}

		return kbArticlePersistence.filterFindByG_S_S(
			groupId, array, status, start, end, orderByComparator);
	}

	@Override
	public int getSectionsKBArticlesCount(
		long groupId, String[] sections, int status) {

		String[] array = AdminUtil.escapeSections(sections);

		for (int i = 0; i < array.length; i++) {
			array[i] = StringUtil.quote(array[i], StringPool.PERCENT);
		}

		if (status == WorkflowConstants.STATUS_ANY) {
			return kbArticlePersistence.filterCountByG_S_L(
				groupId, array, true);
		}
		else if (status == WorkflowConstants.STATUS_APPROVED) {
			return kbArticlePersistence.filterCountByG_S_M(
				groupId, array, true);
		}

		return kbArticlePersistence.filterCountByG_S_S(groupId, array, status);
	}

	/**
	 * @deprecated As of 1.1.0, replaced by {@link #getKBArticles(long, long,
	 *             int, int, int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<KBArticle> getSiblingKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end, OrderByComparator<KBArticle> orderByComparator) {

		return getKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of 1.1.0, replaced by {@link #getKBArticlesCount(long,
	 *             long, int)}
	 */
	@Deprecated
	@Override
	public int getSiblingKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return getKBArticlesCount(groupId, parentResourcePrimKey, status);
	}

	@Override
	public String[] getTempAttachmentNames(long groupId, String tempFolderName)
		throws PortalException {

		return kbArticleLocalService.getTempAttachmentNames(
			groupId, getUserId(), tempFolderName);
	}

	@Override
	public void moveKBArticle(
			long resourcePrimKey, long parentResourceClassNameId,
			long parentResourcePrimKey, double priority)
		throws PortalException {

		KBArticlePermission.check(
			getPermissionChecker(), resourcePrimKey,
			KBActionKeys.MOVE_KB_ARTICLE);

		kbArticleLocalService.moveKBArticle(
			getUserId(), resourcePrimKey, parentResourceClassNameId,
			parentResourcePrimKey, priority);
	}

	@Override
	public KBArticle revertKBArticle(
			long resourcePrimKey, int version, ServiceContext serviceContext)
		throws PortalException {

		KBArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, KBActionKeys.UPDATE);

		return kbArticleLocalService.revertKBArticle(
			getUserId(), resourcePrimKey, version, serviceContext);
	}

	@Override
	public void subscribeGroupKBArticles(long groupId, String portletId)
		throws PortalException {

		if (portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {
			AdminPermission.check(
				getPermissionChecker(), groupId, KBActionKeys.SUBSCRIBE);
		}
		else if (portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) {
			DisplayPermission.check(
				getPermissionChecker(), groupId, KBActionKeys.SUBSCRIBE);
		}

		kbArticleLocalService.subscribeGroupKBArticles(getUserId(), groupId);
	}

	@Override
	public void subscribeKBArticle(long groupId, long resourcePrimKey)
		throws PortalException {

		KBArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, KBActionKeys.SUBSCRIBE);

		kbArticleLocalService.subscribeKBArticle(
			getUserId(), groupId, resourcePrimKey);
	}

	@Override
	public void unsubscribeGroupKBArticles(long groupId, String portletId)
		throws PortalException {

		if (portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {
			AdminPermission.check(
				getPermissionChecker(), groupId, KBActionKeys.SUBSCRIBE);
		}
		else if (portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) {
			DisplayPermission.check(
				getPermissionChecker(), groupId, KBActionKeys.SUBSCRIBE);
		}

		kbArticleLocalService.unsubscribeGroupKBArticles(getUserId(), groupId);
	}

	@Override
	public void unsubscribeKBArticle(long resourcePrimKey)
		throws PortalException {

		KBArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, KBActionKeys.SUBSCRIBE);

		kbArticleLocalService.unsubscribeKBArticle(
			getUserId(), resourcePrimKey);
	}

	@Override
	public KBArticle updateKBArticle(
			long resourcePrimKey, String title, String content,
			String description, String sourceURL, String[] sections,
			String[] selectedFileNames, long[] removeFileEntryIds,
			ServiceContext serviceContext)
		throws PortalException {

		KBArticlePermission.check(
			getPermissionChecker(), resourcePrimKey, KBActionKeys.UPDATE);

		return kbArticleLocalService.updateKBArticle(
			getUserId(), resourcePrimKey, title, content, description,
			sourceURL, sections, selectedFileNames, removeFileEntryIds,
			serviceContext);
	}

	@Override
	public void updateKBArticlesPriorities(
			long groupId, Map<Long, Double> resourcePrimKeyToPriorityMap)
		throws PortalException {

		AdminPermission.check(
			getPermissionChecker(), groupId,
			KBActionKeys.UPDATE_KB_ARTICLES_PRIORITIES);

		kbArticleLocalService.updateKBArticlesPriorities(
			resourcePrimKeyToPriorityMap);
	}

	protected long checkGroupId(long groupId, long resourcePrimKey)
		throws PortalException {

		if (groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			if (resourcePrimKey == KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				throw new PrincipalException();
			}

			KBArticle kbArticle = fetchLatestKBArticle(
				resourcePrimKey, WorkflowConstants.STATUS_ANY);

			if (kbArticle != null) {
				return kbArticle.getGroupId();
			}

			KBFolder kbFolder = kbFolderService.fetchKBFolder(resourcePrimKey);

			if (kbFolder == null) {
				throw new PrincipalException();
			}

			return kbFolder.getGroupId();
		}

		if (resourcePrimKey == KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return groupId;
		}

		KBArticle kbArticle = fetchLatestKBArticle(
			resourcePrimKey, WorkflowConstants.STATUS_ANY);

		if (kbArticle != null) {
			if (kbArticle.getGroupId() != groupId) {
				throw new PrincipalException();
			}

			return kbArticle.getGroupId();
		}

		KBFolder kbFolder = kbFolderService.fetchKBFolder(resourcePrimKey);

		if ((kbFolder == null) || (kbFolder.getGroupId() != groupId)) {
			throw new PrincipalException();
		}

		return kbFolder.getGroupId();
	}

	protected String exportToRSS(
		String rssDisplayStyle, String rssFormat, String name,
		String description, String feedURL, List<KBArticle> kbArticles,
		ThemeDisplay themeDisplay) {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<>();

		syndFeed.setEntries(syndEntries);

		for (KBArticle kbArticle : kbArticles) {
			SyndEntry syndEntry = new SyndEntryImpl();

			String author = PortalUtil.getUserName(kbArticle);

			syndEntry.setAuthor(author);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = null;

			if (rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
				value = HtmlUtil.extractText(kbArticle.getDescription());

				if (Validator.isNull(value)) {
					value = StringUtil.shorten(
						HtmlUtil.extractText(kbArticle.getContent()), 200);
				}
			}
			else if (rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = StringUtil.replace(
					kbArticle.getContent(),
					new String[] {"href=\"/", "src=\"/"},
					new String[] {
						"href=\"" + themeDisplay.getURLPortal() + "/",
						"src=\"" + themeDisplay.getURLPortal() + "/"
					});
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			String link = KnowledgeBaseUtil.getKBArticleURL(
				themeDisplay.getPlid(), kbArticle.getResourcePrimKey(),
				kbArticle.getStatus(), themeDisplay.getPortalURL(), false);

			syndEntry.setLink(link);

			syndEntry.setPublishedDate(kbArticle.getCreateDate());
			syndEntry.setTitle(kbArticle.getTitle());
			syndEntry.setUpdatedDate(kbArticle.getModifiedDate());
			syndEntry.setUri(syndEntry.getLink());

			syndEntries.add(syndEntry);
		}

		String feedType = RSSUtil.getFeedType(
			RSSUtil.getFormatType(rssFormat),
			RSSUtil.getFormatVersion(rssFormat));

		syndFeed.setFeedType(feedType);

		List<SyndLink> syndLinks = new ArrayList<>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		selfSyndLink.setHref(feedURL);
		selfSyndLink.setRel("self");

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

	/**
	 * @deprecated As of 1.1.0, replaced by {@link
	 *             #getAllDescendantKBArticles(long, long, int,
	 *             OrderByComparator, boolean)}
	 */
	@Deprecated
	protected List<KBArticle> getAllDescendantKBArticles(
			long resourcePrimKey, int status,
			OrderByComparator<KBArticle> orderByComparator,
			boolean includeParentArticle)
		throws PortalException {

		return getAllDescendantKBArticles(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, resourcePrimKey, status,
			orderByComparator, includeParentArticle);
	}

	protected List<KBArticle> getAllDescendantKBArticles(
			long groupId, long resourcePrimKey, int status,
			OrderByComparator<KBArticle> orderByComparator,
			boolean includeParentArticle)
		throws PortalException {

		List<KBArticle> kbArticles = null;

		if (includeParentArticle) {
			kbArticles = getKBArticles(
				groupId, new long[] {resourcePrimKey}, status, null);

			kbArticles = ListUtil.copy(kbArticles);
		}
		else {
			kbArticles = new ArrayList<>();
		}

		_getAllDescendantKBArticles(
			kbArticles, groupId, resourcePrimKey, status, orderByComparator);

		return Collections.unmodifiableList(kbArticles);
	}

	private void _checkAttachmentPermissions(
			long groupId, String portletId, long resourcePrimKey)
		throws PortalException {

		if ((resourcePrimKey <= 0) &&
			portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ADMIN)) {

			AdminPermission.check(
				getPermissionChecker(), groupId, KBActionKeys.ADD_KB_ARTICLE);
		}
		else if ((resourcePrimKey <= 0) &&
				 portletId.equals(KBPortletKeys.KNOWLEDGE_BASE_DISPLAY)) {

			DisplayPermission.check(
				getPermissionChecker(), groupId, KBActionKeys.ADD_KB_ARTICLE);
		}
		else {
			KBArticlePermission.check(
				getPermissionChecker(), resourcePrimKey, KBActionKeys.UPDATE);
		}
	}

	private void _getAllDescendantKBArticles(
		List<KBArticle> kbArticles, long groupId, long resourcePrimKey,
		int status, OrderByComparator<KBArticle> orderByComparator) {

		List<KBArticle> curKBArticles = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			curKBArticles = kbArticlePersistence.filterFindByG_P_L(
				groupId, resourcePrimKey, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);
		}
		else if (status == WorkflowConstants.STATUS_APPROVED) {
			curKBArticles = kbArticlePersistence.findByG_P_M(
				groupId, resourcePrimKey, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);
		}
		else {
			curKBArticles = kbArticlePersistence.findByG_P_S(
				groupId, resourcePrimKey, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);
		}

		for (KBArticle curKBArticle : curKBArticles) {
			kbArticles.add(curKBArticle);

			_getAllDescendantKBArticles(
				kbArticles, groupId, curKBArticle.getResourcePrimKey(), status,
				orderByComparator);
		}
	}

	private static final int _INTERVAL = 200;

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleServiceImpl.class);

}