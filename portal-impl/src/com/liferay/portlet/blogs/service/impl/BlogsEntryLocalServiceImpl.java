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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.blogs.BlogsSettings;
import com.liferay.portlet.blogs.EntryContentException;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.EntrySmallImageNameException;
import com.liferay.portlet.blogs.EntrySmallImageSizeException;
import com.liferay.portlet.blogs.EntryTitleException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.base.BlogsEntryLocalServiceBaseImpl;
import com.liferay.portlet.blogs.social.BlogsActivityKeys;
import com.liferay.portlet.blogs.util.BlogsUtil;
import com.liferay.portlet.blogs.util.LinkbackProducerUtil;
import com.liferay.portlet.blogs.util.comparator.EntryDisplayDateComparator;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

/**
 * Provides the local service for accessing, adding, checking, deleting,
 * subscription handling of, trash handling of, and updating blog entries.
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 * @author Thiago Moreira
 * @author Juan Fernández
 * @author Zsolt Berentey
 */
public class BlogsEntryLocalServiceImpl extends BlogsEntryLocalServiceBaseImpl {

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			EntryDisplayDateException.class);

		byte[] smallImageBytes = null;

		try {
			if ((smallImageInputStream != null) && smallImage) {
				smallImageBytes = FileUtil.getBytes(smallImageInputStream);
			}
		}
		catch (IOException ioe) {
		}

		Date now = new Date();

		validate(
			title, content, smallImage, smallImageURL, smallImageFileName,
			smallImageBytes);

		long entryId = counterLocalService.increment();

		BlogsEntry entry = blogsEntryPersistence.create(entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(serviceContext.getCreateDate(now));
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setTitle(title);
		entry.setUrlTitle(
			getUniqueUrlTitle(entryId, title, null, serviceContext));
		entry.setDescription(description);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setAllowPingbacks(allowPingbacks);
		entry.setAllowTrackbacks(allowTrackbacks);
		entry.setSmallImage(smallImage);
		entry.setSmallImageId(counterLocalService.increment());
		entry.setSmallImageURL(smallImageURL);
		entry.setStatus(WorkflowConstants.STATUS_DRAFT);
		entry.setStatusByUserId(userId);
		entry.setStatusDate(serviceContext.getModifiedDate(now));
		entry.setExpandoBridgeAttributes(serviceContext);

		blogsEntryPersistence.update(entry);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addEntryResources(
				entry, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addEntryResources(
				entry, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Small image

		saveImages(smallImage, entry.getSmallImageId(), smallImageBytes);

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Message boards

		if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, entry.getUserName(), groupId,
				BlogsEntry.class.getName(), entryId,
				WorkflowConstants.ACTION_PUBLISH);
		}

		// Workflow

		if (ArrayUtil.isNotEmpty(trackbacks)) {
			serviceContext.setAttribute("trackbacks", trackbacks);
		}
		else {
			serviceContext.setAttribute("trackbacks", null);
		}

		startWorkflowInstance(userId, entry, serviceContext);

		return entry;
	}

	@Override
	public void addEntryResources(
			BlogsEntry entry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
			BlogsEntry entry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), groupPermissions,
			guestPermissions);
	}

	@Override
	public void addEntryResources(
			long entryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
			long entryId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, groupPermissions, guestPermissions);
	}

	@Override
	public void checkEntries() throws PortalException, SystemException {
		Date now = new Date();

		int count = blogsEntryPersistence.countByLtD_S(
			now, WorkflowConstants.STATUS_SCHEDULED);

		if (count == 0) {
			return;
		}

		List<BlogsEntry> entries = blogsEntryPersistence.findByLtD_S(
			now, WorkflowConstants.STATUS_SCHEDULED);

		for (BlogsEntry entry : entries) {
			ServiceContext serviceContext = new ServiceContext();

			String[] trackbacks = StringUtil.split(entry.getTrackbacks());

			serviceContext.setAttribute("trackbacks", trackbacks);

			serviceContext.setCommand(Constants.UPDATE);

			String layoutFullURL = PortalUtil.getLayoutFullURL(
				entry.getGroupId(), PortletKeys.BLOGS);

			serviceContext.setLayoutFullURL(layoutFullURL);

			serviceContext.setScopeGroupId(entry.getGroupId());

			blogsEntryLocalService.updateStatus(
				entry.getStatusByUserId(), entry.getEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<String, Serializable>());
		}
	}

	@Override
	public void deleteEntries(long groupId)
		throws PortalException, SystemException {

		for (BlogsEntry entry : blogsEntryPersistence.findByGroupId(groupId)) {
			blogsEntryLocalService.deleteEntry(entry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public BlogsEntry deleteEntry(BlogsEntry entry)
		throws PortalException, SystemException {

		// Entry

		blogsEntryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), BlogsEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Image

		imageLocalService.deleteImage(entry.getSmallImageId());

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			entry.getCompanyId(), BlogsEntry.class.getName(),
			entry.getEntryId());

		// Statistics

		blogsStatsUserLocalService.updateStatsUser(
			entry.getGroupId(), entry.getUserId(), entry.getDisplayDate());

		// Asset

		assetEntryLocalService.deleteEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Expando

		expandoRowLocalService.deleteRows(entry.getEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Trash

		trashEntryLocalService.deleteEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			BlogsEntry.class);

		indexer.delete(entry);

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			entry.getCompanyId(), entry.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId());

		return entry;
	}

	@Override
	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		blogsEntryLocalService.deleteEntry(entry);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getCompanyEntries(long,
	 *             Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getCompanyEntries(
			long companyId, Date displayDate, int status, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getCompanyEntries(companyId, displayDate, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getCompanyEntries(long,
	 *             Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getCompanyEntries(
			long companyId, Date displayDate, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return getCompanyEntries(companyId, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getCompanyEntries(
			long companyId, Date displayDate, QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.findByC_LtD_NotS(
				companyId, displayDate, queryDefinition.getStatus(),
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}
		else {
			return blogsEntryPersistence.findByC_LtD_S(
				companyId, displayDate, queryDefinition.getStatus(),
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getCompanyEntriesCount(long,
	 *             Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public int getCompanyEntriesCount(
			long companyId, Date displayDate, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getCompanyEntriesCount(companyId, displayDate, queryDefinition);
	}

	@Override
	public int getCompanyEntriesCount(
			long companyId, Date displayDate, QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.countByC_LtD_NotS(
				companyId, displayDate, queryDefinition.getStatus());
		}
		else {
			return blogsEntryPersistence.countByC_LtD_S(
				companyId, displayDate, queryDefinition.getStatus());
		}
	}

	@Override
	public BlogsEntry[] getEntriesPrevAndNext(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		return blogsEntryPersistence.findByG_S_PrevAndNext(
			entry.getEntryId(), entry.getGroupId(),
			WorkflowConstants.STATUS_APPROVED,
			new EntryDisplayDateComparator(true));
	}

	@Override
	public BlogsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return blogsEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException, SystemException {

		return blogsEntryPersistence.findByG_UT(groupId, urlTitle);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupEntries(long, Date,
	 *             QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getGroupEntries(
			long groupId, Date displayDate, int status, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getGroupEntries(groupId, displayDate, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupEntries(long, Date,
	 *             QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getGroupEntries(
			long groupId, Date displayDate, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return getGroupEntries(groupId, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
			long groupId, Date displayDate, QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.findByG_LtD_NotS(
				groupId, displayDate, queryDefinition.getStatus(),
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}
		else {
			return blogsEntryPersistence.findByG_LtD_S(
				groupId, displayDate, queryDefinition.getStatus(),
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupEntries(long,
	 *             QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getGroupEntries(
			long groupId, int status, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getGroupEntries(groupId, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupEntries(long,
	 *             QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getGroupEntries(
			long groupId, int status, int start, int end, OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return getGroupEntries(groupId, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
			long groupId, QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.findByG_NotS(
				groupId, queryDefinition.getStatus(),
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}
		else {
			return blogsEntryPersistence.findByG_S(
				groupId, queryDefinition.getStatus(),
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupEntriesCount(long,
	 *             Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public int getGroupEntriesCount(long groupId, Date displayDate, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getGroupEntriesCount(groupId, displayDate, queryDefinition);
	}

	@Override
	public int getGroupEntriesCount(
			long groupId, Date displayDate, QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.countByG_LtD_NotS(
				groupId, displayDate, queryDefinition.getStatus());
		}
		else {
			return blogsEntryPersistence.countByG_LtD_S(
				groupId, displayDate, queryDefinition.getStatus());
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupEntriesCount(long,
	 *             QueryDefinition)}
	 */
	@Deprecated
	@Override
	public int getGroupEntriesCount(long groupId, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getGroupEntriesCount(groupId, queryDefinition);
	}

	@Override
	public int getGroupEntriesCount(
			long groupId, QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.countByG_NotS(
				groupId, queryDefinition.getStatus());
		}
		else {
			return blogsEntryPersistence.countByG_S(
				groupId, queryDefinition.getStatus());
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupsEntries(long, long,
	 *             Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getGroupsEntries(
			long companyId, long groupId, Date displayDate, int status,
			int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getGroupsEntries(
			companyId, groupId, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getGroupsEntries(
			long companyId, long groupId, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		return blogsEntryFinder.findByGroupIds(
			companyId, groupId, displayDate, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupUserEntries(long,
	 *             long, Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, Date displayDate, int status, int start,
			int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getGroupUserEntries(
			groupId, userId, displayDate, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupUserEntries(long,
	 *             long, Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, Date displayDate, int status, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return getGroupUserEntries(
			groupId, userId, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.findByG_U_NotS(
				groupId, userId, queryDefinition.getStatus(),
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}
		else {
			return blogsEntryPersistence.findByG_U_S(
				groupId, userId, queryDefinition.getStatus(),
				queryDefinition.getStart(), queryDefinition.getEnd(),
				queryDefinition.getOrderByComparator());
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getGroupUserEntriesCount(long, long, Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public int getGroupUserEntriesCount(
			long groupId, long userId, Date displayDate, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getGroupUserEntriesCount(
			groupId, userId, displayDate, queryDefinition);
	}

	@Override
	public int getGroupUserEntriesCount(
			long groupId, long userId, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.countByG_U_LtD_NotS(
				groupId, userId, displayDate, queryDefinition.getStatus());
		}
		else {
			return blogsEntryPersistence.countByG_U_LtD_S(
				groupId, userId, displayDate, queryDefinition.getStatus());
		}
	}

	@Override
	public List<BlogsEntry> getNoAssetEntries() throws SystemException {
		return blogsEntryFinder.findByNoAssets();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getOrganizationEntries(long,
	 *             Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, Date displayDate, int status, int start,
			int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return getOrganizationEntries(
			organizationId, displayDate, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getOrganizationEntries(long,
	 *             Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, Date displayDate, int status, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, obc);

		return getOrganizationEntries(
			organizationId, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		return blogsEntryFinder.findByOrganizationId(
			organizationId, displayDate, queryDefinition);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #getOrganizationEntriesCount(long, Date, QueryDefinition)}
	 */
	@Deprecated
	@Override
	public int getOrganizationEntriesCount(
			long organizationId, Date displayDate, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return getOrganizationEntriesCount(
			organizationId, displayDate, queryDefinition);
	}

	@Override
	public int getOrganizationEntriesCount(
			long organizationId, Date displayDate,
			QueryDefinition queryDefinition)
		throws SystemException {

		return blogsEntryFinder.countByOrganizationId(
			organizationId, displayDate, queryDefinition);
	}

	@Override
	public void moveEntriesToTrash(long groupId, long userId)
		throws PortalException, SystemException {

		List<BlogsEntry> entries = blogsEntryPersistence.findByGroupId(groupId);

		for (BlogsEntry entry : entries) {
			blogsEntryLocalService.moveEntryToTrash(userId, entry);
		}
	}

	/**
	 * Moves the blogs entry to the recycle bin. Social activity counters for
	 * this entry get disabled.
	 *
	 * @param  userId the primary key of the user moving the blogs entry
	 * @param  entry the blogs entry to be moved
	 * @return the moved blogs entry
	 * @throws PortalException if a user with the primary key could not be found
	 *         or if the blogs entry owner's social activity counter could not
	 *         be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry moveEntryToTrash(long userId, BlogsEntry entry)
		throws PortalException, SystemException {

		// Entry

		int oldStatus = entry.getStatus();

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			entry.setStatus(WorkflowConstants.STATUS_DRAFT);

			blogsEntryPersistence.update(entry);
		}

		entry = updateStatus(
			userId, entry.getEntryId(), WorkflowConstants.STATUS_IN_TRASH,
			new ServiceContext());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getTitle());

		socialActivityLocalService.addActivity(
			userId, entry.getGroupId(), BlogsEntry.class.getName(),
			entry.getEntryId(), SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		// Workflow

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				entry.getCompanyId(), entry.getGroupId(),
				BlogsEntry.class.getName(), entry.getEntryId());
		}

		return entry;
	}

	/**
	 * Moves the blogs entry with the ID to the recycle bin.
	 *
	 * @param  userId the primary key of the user moving the blogs entry
	 * @param  entryId the primary key of the blogs entry to be moved
	 * @return the moved blogs entry
	 * @throws PortalException if a user or blogs entry with the primary key
	 *         could not be found or if the blogs entry owner's social activity
	 *         counter could not be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BlogsEntry moveEntryToTrash(long userId, long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		return blogsEntryLocalService.moveEntryToTrash(userId, entry);
	}

	/**
	 * Restores the blogs entry with the ID from the recycle bin. Social
	 * activity counters for this entry get activated.
	 *
	 * @param  userId the primary key of the user restoring the blogs entry
	 * @param  entryId the primary key of the blogs entry to be restored
	 * @return the restored blogs entry from the recycle bin
	 * @throws PortalException if a user or blogs entry with the primary key
	 *         could not be found or if the blogs entry owner's social activity
	 *         counter could not be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry restoreEntryFromTrash(long userId, long entryId)
		throws PortalException, SystemException {

		// Entry

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			BlogsEntry.class.getName(), entryId);

		BlogsEntry entry = updateStatus(
			userId, entryId, trashEntry.getStatus(), new ServiceContext());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getTitle());

		socialActivityLocalService.addActivity(
			userId, trashEntry.getGroupId(), BlogsEntry.class.getName(),
			entryId, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		return entry;
	}

	@Override
	public void subscribe(long userId, long groupId)
		throws PortalException, SystemException {

		subscriptionLocalService.addSubscription(
			userId, groupId, BlogsEntry.class.getName(), groupId);
	}

	@Override
	public void unsubscribe(long userId, long groupId)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(
			userId, BlogsEntry.class.getName(), groupId);
	}

	@Override
	public void updateAsset(
			long userId, BlogsEntry entry, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		boolean visible = false;

		if (entry.isApproved()) {
			visible = true;
		}

		String summary = HtmlUtil.extractText(
			StringUtil.shorten(entry.getContent(), 500));

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, entry.getGroupId(), entry.getCreateDate(),
			entry.getModifiedDate(), BlogsEntry.class.getName(),
			entry.getEntryId(), entry.getUuid(), 0, assetCategoryIds,
			assetTagNames, visible, null, null, null, ContentTypes.TEXT_HTML,
			entry.getTitle(), entry.getDescription(), summary, null, null, 0, 0,
			null, false);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String description,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, boolean smallImage, String smallImageURL,
			String smallImageFileName, InputStream smallImageInputStream,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			EntryDisplayDateException.class);

		byte[] smallImageBytes = null;

		try {
			if ((smallImageInputStream != null) && smallImage) {
				smallImageBytes = FileUtil.getBytes(smallImageInputStream);
			}
		}
		catch (IOException ioe) {
		}

		validate(
			title, content, smallImage, smallImageURL, smallImageFileName,
			smallImageBytes);

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		String oldUrlTitle = entry.getUrlTitle();

		entry.setModifiedDate(serviceContext.getModifiedDate(null));
		entry.setTitle(title);
		entry.setUrlTitle(
			getUniqueUrlTitle(entryId, title, oldUrlTitle, serviceContext));
		entry.setDescription(description);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setAllowPingbacks(allowPingbacks);
		entry.setAllowTrackbacks(allowTrackbacks);
		entry.setSmallImage(smallImage);

		if (entry.getSmallImageId() == 0) {
			entry.setSmallImageId(counterLocalService.increment());
		}

		entry.setSmallImageURL(smallImageURL);

		if (entry.isPending() || entry.isDraft()) {
		}
		else {
			entry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		entry.setExpandoBridgeAttributes(serviceContext);

		blogsEntryPersistence.update(entry);

		// Resources

		if ((serviceContext.getGroupPermissions() != null) ||
			(serviceContext.getGuestPermissions() != null)) {

			updateEntryResources(
				entry, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Small image

		saveImages(smallImage, entry.getSmallImageId(), smallImageBytes);

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Workflow

		boolean pingOldTrackbacks = false;

		if (!oldUrlTitle.equals(entry.getUrlTitle())) {
			pingOldTrackbacks = true;
		}

		serviceContext.setAttribute(
			"pingOldTrackbacks", String.valueOf(pingOldTrackbacks));

		if (ArrayUtil.isNotEmpty(trackbacks)) {
			serviceContext.setAttribute("trackbacks", trackbacks);
		}
		else {
			serviceContext.setAttribute("trackbacks", null);
		}

		startWorkflowInstance(userId, entry, serviceContext);

		return entry;
	}

	@Override
	public void updateEntryResources(
			BlogsEntry entry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.updateResources(
			entry.getCompanyId(), entry.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId(), groupPermissions,
			guestPermissions);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, long,
	 *             int, ServiceContext, Map)}
	 */
	@Deprecated
	@Override
	public BlogsEntry updateStatus(
			long userId, long entryId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateStatus(
			userId, entryId, status, serviceContext,
			new HashMap<String, Serializable>());
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry updateStatus(
			long userId, long entryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		int oldStatus = entry.getStatus();

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			now.before(entry.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setStatus(status);
		entry.setStatusByUserId(user.getUserId());
		entry.setStatusByUserName(user.getFullName());
		entry.setStatusDate(serviceContext.getModifiedDate(now));

		blogsEntryPersistence.update(entry);

		// Statistics

		blogsStatsUserLocalService.updateStatsUser(
			entry.getGroupId(), entry.getUserId(), entry.getDisplayDate());

		AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
			BlogsEntry.class.getName(), entryId);

		if ((assetEntry == null) || (assetEntry.getPublishDate() == null)) {
			serviceContext.setCommand(Constants.ADD);
		}

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getTitle());

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			assetEntryLocalService.updateEntry(
				BlogsEntry.class.getName(), entryId, entry.getDisplayDate(),
				true);

			// Social

			if ((oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
				(oldStatus != WorkflowConstants.STATUS_SCHEDULED)) {

				if (serviceContext.isCommandUpdate()) {
					socialActivityLocalService.addActivity(
						user.getUserId(), entry.getGroupId(),
						BlogsEntry.class.getName(), entryId,
						BlogsActivityKeys.UPDATE_ENTRY,
						extraDataJSONObject.toString(), 0);
				}
				else {
					socialActivityLocalService.addUniqueActivity(
						user.getUserId(), entry.getGroupId(),
						BlogsEntry.class.getName(), entryId,
						BlogsActivityKeys.ADD_ENTRY,
						extraDataJSONObject.toString(), 0);
				}
			}

			// Trash

			if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
				trashEntryLocalService.deleteEntry(
					BlogsEntry.class.getName(), entryId);
			}

			if (oldStatus != WorkflowConstants.STATUS_IN_TRASH) {

				// Subscriptions

				notifySubscribers(
					entry,
					(String)workflowContext.get(WorkflowConstants.CONTEXT_URL),
					serviceContext);

				// Ping

				String[] trackbacks = (String[])serviceContext.getAttribute(
					"trackbacks");
				Boolean pingOldTrackbacks = ParamUtil.getBoolean(
					serviceContext, "pingOldTrackbacks");

				pingGoogle(entry, serviceContext);
				pingPingback(entry, serviceContext);
				pingTrackbacks(
					entry, trackbacks, pingOldTrackbacks, serviceContext);
			}
		}
		else {

			// Asset

			assetEntryLocalService.updateVisible(
				BlogsEntry.class.getName(), entryId, false);

			// Social

			if ((status == WorkflowConstants.STATUS_SCHEDULED) &&
				(oldStatus != WorkflowConstants.STATUS_IN_TRASH)) {

				if (serviceContext.isCommandUpdate()) {
					socialActivityLocalService.addActivity(
						user.getUserId(), entry.getGroupId(),
						BlogsEntry.class.getName(), entryId,
						BlogsActivityKeys.UPDATE_ENTRY,
						extraDataJSONObject.toString(), 0);
				}
				else {
					socialActivityLocalService.addUniqueActivity(
						user.getUserId(), entry.getGroupId(),
						BlogsEntry.class.getName(), entryId,
						BlogsActivityKeys.ADD_ENTRY,
						extraDataJSONObject.toString(), 0);
				}
			}

			// Trash

			if (status == WorkflowConstants.STATUS_IN_TRASH) {
				trashEntryLocalService.addTrashEntry(
					userId, entry.getGroupId(), BlogsEntry.class.getName(),
					entry.getEntryId(), entry.getUuid(), null, oldStatus, null,
					null);
			}
			else if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
				trashEntryLocalService.deleteEntry(
					BlogsEntry.class.getName(), entryId);
			}
		}

		return entry;
	}

	protected String getEntryURL(
			BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return StringPool.BLANK;
		}

		String layoutURL = getLayoutURL(
			entry.getGroupId(), PortletKeys.BLOGS, serviceContext);

		if (Validator.isNotNull(layoutURL)) {
			return layoutURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs" +
				StringPool.SLASH + entry.getEntryId();
		}

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			serviceContext.getCompanyId());

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, PortletKeys.BLOGS_ADMIN, controlPanelPlid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/blogs_admin/view_entry");
		portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

		return portletURL.toString();
	}

	protected String getUniqueUrlTitle(long entryId, long groupId, String title)
		throws SystemException {

		String urlTitle = BlogsUtil.getUrlTitle(entryId, title);

		for (int i = 1;; i++) {
			BlogsEntry entry = blogsEntryPersistence.fetchByG_UT(
				groupId, urlTitle);

			if ((entry == null) || (entryId == entry.getEntryId())) {
				break;
			}
			else {
				String suffix = StringPool.DASH + i;

				String prefix = urlTitle;

				if (urlTitle.length() > suffix.length()) {
					prefix = urlTitle.substring(
						0, urlTitle.length() - suffix.length());
				}

				urlTitle = prefix + suffix;
			}
		}

		return urlTitle;
	}

	protected String getUniqueUrlTitle(
			long entryId, String title, String oldUrlTitle,
			ServiceContext serviceContext)
		throws SystemException {

		String serviceContextUrlTitle = ParamUtil.getString(
			serviceContext, "urlTitle");

		String urlTitle = null;

		if (Validator.isNotNull(serviceContextUrlTitle)) {
			urlTitle = BlogsUtil.getUrlTitle(entryId, serviceContextUrlTitle);
		}
		else if (Validator.isNotNull(oldUrlTitle)) {
			return oldUrlTitle;
		}
		else {
			urlTitle = getUniqueUrlTitle(
				entryId, serviceContext.getScopeGroupId(), title);
		}

		BlogsEntry urlTitleEntry = blogsEntryPersistence.fetchByG_UT(
			serviceContext.getScopeGroupId(), urlTitle);

		if ((urlTitleEntry != null) &&
			(urlTitleEntry.getEntryId() != entryId)) {

			urlTitle = getUniqueUrlTitle(
				entryId, serviceContext.getScopeGroupId(), urlTitle);
		}

		return urlTitle;
	}

	protected void notifySubscribers(
			BlogsEntry entry, String entryURL, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!entry.isApproved() || Validator.isNull(entryURL)) {
			return;
		}

		BlogsSettings blogsSettings = BlogsSettings.getInstance(
			entry.getGroupId());

		if (serviceContext.isCommandAdd() &&
			blogsSettings.isEmailEntryAddedEnabled()) {
		}
		else if (serviceContext.isCommandUpdate() &&
				 blogsSettings.isEmailEntryUpdatedEnabled()) {
		}
		else {
			return;
		}

		String entryTitle = entry.getTitle();

		String fromName = blogsSettings.getEmailFromName();
		String fromAddress = blogsSettings.getEmailFromAddress();

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (serviceContext.isCommandUpdate()) {
			subjectLocalizedValuesMap =
				blogsSettings.getEmailEntryUpdatedSubject();
			bodyLocalizedValuesMap = blogsSettings.getEmailEntryUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				blogsSettings.getEmailEntryAddedSubject();
			bodyLocalizedValuesMap = blogsSettings.getEmailEntryAddedBody();
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setClassPK(entry.getEntryId());
		subscriptionSender.setClassName(entry.getModelClassName());
		subscriptionSender.setCompanyId(entry.getCompanyId());
		subscriptionSender.setContextAttribute(
			"[$BLOGS_ENTRY_CONTENT$]", entry.getContent(), false);
		subscriptionSender.setContextAttributes(
			"[$BLOGS_ENTRY_DESCRIPTION$]", entry.getDescription(),
			"[$BLOGS_ENTRY_STATUS_BY_USER_NAME$]", entry.getStatusByUserName(),
			"[$BLOGS_ENTRY_TITLE$]", entryTitle, "[$BLOGS_ENTRY_URL$]",
			entryURL);
		subscriptionSender.setContextUserPrefix("BLOGS_ENTRY");
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setEntryURL(entryURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(bodyLocalizedValuesMap);
		subscriptionSender.setLocalizedSubjectMap(subjectLocalizedValuesMap);
		subscriptionSender.setMailId("blogs_entry", entry.getEntryId());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		subscriptionSender.setPortletId(PortletKeys.BLOGS);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(entry.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setUserId(entry.getUserId());

		subscriptionSender.addPersistedSubscribers(
			BlogsEntry.class.getName(), entry.getGroupId());

		subscriptionSender.addPersistedSubscribers(
			BlogsEntry.class.getName(), entry.getEntryId());

		subscriptionSender.flushNotificationsAsync();
	}

	protected void pingGoogle(BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.BLOGS_PING_GOOGLE_ENABLED || !entry.isApproved()) {
			return;
		}

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			serviceContext.getScopeGroupId(), PortletKeys.BLOGS);

		if (Validator.isNull(layoutFullURL)) {
			return;
		}

		if (layoutFullURL.contains("://localhost")) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not pinging Google because of localhost URL " +
						layoutFullURL);
			}

			return;
		}

		Group group = groupPersistence.findByPrimaryKey(entry.getGroupId());

		StringBundler sb = new StringBundler(6);

		String name = group.getDescriptiveName();
		String url = layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs";
		String changesURL =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs/rss";

		sb.append("http://blogsearch.google.com/ping?name=");
		sb.append(HttpUtil.encodeURL(name));
		sb.append("&url=");
		sb.append(HttpUtil.encodeURL(url));
		sb.append("&changesURL=");
		sb.append(HttpUtil.encodeURL(changesURL));

		String location = sb.toString();

		if (_log.isInfoEnabled()) {
			_log.info("Pinging Google at " + location);
		}

		try {
			String response = HttpUtil.URLtoString(sb.toString());

			if (_log.isInfoEnabled()) {
				_log.info("Google ping response: " + response);
			}
		}
		catch (IOException ioe) {
			_log.error("Unable to ping Google at " + location, ioe);
		}
	}

	protected void pingPingback(BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.BLOGS_PINGBACK_ENABLED ||
			!entry.isAllowPingbacks() || !entry.isApproved()) {

			return;
		}

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutFullURL = PortalUtil.getLayoutFullURL(themeDisplay);

		if (Validator.isNull(layoutFullURL)) {
			return;
		}

		String sourceUri =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs/" +
				entry.getUrlTitle();

		Source source = new Source(entry.getContent());

		List<StartTag> tags = source.getAllStartTags("a");

		for (StartTag tag : tags) {
			String targetUri = tag.getAttributeValue("href");

			if (Validator.isNotNull(targetUri)) {
				try {
					LinkbackProducerUtil.sendPingback(sourceUri, targetUri);
				}
				catch (Exception e) {
					_log.error("Error while sending pingback " + targetUri, e);
				}
			}
		}
	}

	protected void pingTrackbacks(
			BlogsEntry entry, String[] trackbacks, boolean pingOldTrackbacks,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.BLOGS_TRACKBACK_ENABLED ||
			!entry.isAllowTrackbacks() || !entry.isApproved()) {

			return;
		}

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutFullURL = PortalUtil.getLayoutFullURL(themeDisplay);

		if (Validator.isNull(layoutFullURL)) {
			return;
		}

		Map<String, String> parts = new HashMap<String, String>();

		String excerpt = StringUtil.shorten(
			HtmlUtil.extractText(entry.getContent()),
			PropsValues.BLOGS_LINKBACK_EXCERPT_LENGTH);
		String url =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs/" +
				entry.getUrlTitle();

		parts.put("title", entry.getTitle());
		parts.put("excerpt", excerpt);
		parts.put("url", url);
		parts.put("blog_name", entry.getUserName());

		Set<String> trackbacksSet = null;

		if (ArrayUtil.isNotEmpty(trackbacks)) {
			trackbacksSet = SetUtil.fromArray(trackbacks);
		}
		else {
			trackbacksSet = new HashSet<String>();
		}

		if (pingOldTrackbacks) {
			trackbacksSet.addAll(
				SetUtil.fromArray(StringUtil.split(entry.getTrackbacks())));

			entry.setTrackbacks(StringPool.BLANK);

			blogsEntryPersistence.update(entry);
		}

		Set<String> oldTrackbacks = SetUtil.fromArray(
			StringUtil.split(entry.getTrackbacks()));

		Set<String> validTrackbacks = new HashSet<String>();

		for (String trackback : trackbacksSet) {
			if (oldTrackbacks.contains(trackback)) {
				continue;
			}

			try {
				if (LinkbackProducerUtil.sendTrackback(trackback, parts)) {
					validTrackbacks.add(trackback);
				}
			}
			catch (Exception e) {
				_log.error("Error while sending trackback at " + trackback, e);
			}
		}

		if (!validTrackbacks.isEmpty()) {
			String newTrackbacks = StringUtil.merge(validTrackbacks);

			if (Validator.isNotNull(entry.getTrackbacks())) {
				newTrackbacks += StringPool.COMMA + entry.getTrackbacks();
			}

			entry.setTrackbacks(newTrackbacks);

			blogsEntryPersistence.update(entry);
		}
	}

	protected void saveImages(
			boolean smallImage, long smallImageId, byte[] smallImageBytes)
		throws PortalException, SystemException {

		if (smallImage) {
			if (smallImageBytes != null) {
				imageLocalService.updateImage(smallImageId, smallImageBytes);
			}
		}
		else {
			imageLocalService.deleteImage(smallImageId);
		}
	}

	protected void startWorkflowInstance(
			long userId, BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		workflowContext.put(
			WorkflowConstants.CONTEXT_URL, getEntryURL(entry, serviceContext));

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			entry.getCompanyId(), entry.getGroupId(), userId,
			BlogsEntry.class.getName(), entry.getEntryId(), entry,
			serviceContext, workflowContext);
	}

	protected void validate(
			String title, String content, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			byte[] smallImageBytes)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			throw new EntryTitleException();
		}
		else if (Validator.isNull(content)) {
			throw new EntryContentException();
		}

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.BLOGS_IMAGE_EXTENSIONS, StringPool.COMMA);

		if (smallImage && Validator.isNull(smallImageURL) &&
			(smallImageBytes != null)) {

			if (smallImageFileName != null) {
				boolean validSmallImageExtension = false;

				for (String _imageExtension : imageExtensions) {
					if (StringPool.STAR.equals(_imageExtension) ||
						StringUtil.endsWith(
							smallImageFileName, _imageExtension)) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new EntrySmallImageNameException(smallImageFileName);
				}
			}

			long smallImageMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.BLOGS_IMAGE_SMALL_MAX_SIZE);

			if ((smallImageMaxSize > 0) &&
				(smallImageBytes.length > smallImageMaxSize)) {

				throw new EntrySmallImageSizeException();
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BlogsEntryLocalServiceImpl.class);

}