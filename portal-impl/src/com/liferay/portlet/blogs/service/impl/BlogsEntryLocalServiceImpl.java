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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
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

import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

/**
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 * @author Thiago Moreira
 * @author Juan Fernández
 * @author Zsolt Berentey
 */
public class BlogsEntryLocalServiceImpl extends BlogsEntryLocalServiceBaseImpl {

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
			new EntryDisplayDateException());

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
		entry.setUrlTitle(getUniqueUrlTitle(entryId, groupId, title));
		entry.setDescription(description);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setAllowPingbacks(allowPingbacks);
		entry.setAllowTrackbacks(allowTrackbacks);
		entry.setSmallImage(smallImage);
		entry.setSmallImageId(counterLocalService.increment());
		entry.setSmallImageURL(smallImageURL);
		entry.setStatus(WorkflowConstants.STATUS_DRAFT);
		entry.setStatusDate(serviceContext.getModifiedDate(now));
		entry.setExpandoBridgeAttributes(serviceContext);

		blogsEntryPersistence.update(entry, false);

		// Resources

		if (serviceContext.getAddGroupPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addEntryResources(
				entry, serviceContext.getAddGroupPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addEntryResources(
				entry, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Small image

		saveImages(
			smallImage, entry.getSmallImageId(), smallImageBytes);

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

		if ((trackbacks != null) && (trackbacks.length > 0)) {
			serviceContext.setAttribute("trackbacks", trackbacks);
		}
		else {
			serviceContext.setAttribute("trackbacks", null);
		}

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId, BlogsEntry.class.getName(),
			entry.getEntryId(), entry, serviceContext);

		return entry;
	}

	public void addEntryResources(
			BlogsEntry entry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			BlogsEntry entry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), groupPermissions,
			guestPermissions);
	}

	public void addEntryResources(
			long entryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, addGroupPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			long entryId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, groupPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId)
		throws PortalException, SystemException {

		for (BlogsEntry entry : blogsEntryPersistence.findByGroupId(groupId)) {
			deleteEntry(entry);
		}
	}

	public void deleteEntry(BlogsEntry entry)
		throws PortalException, SystemException {

		// Entry

		blogsEntryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), BlogsEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Image

		imageLocalService.deleteImage(entry.getSmallImageId());

		// Statistics

		blogsStatsUserLocalService.updateStatsUser(
			entry.getGroupId(), entry.getUserId());

		// Asset

		assetEntryLocalService.deleteEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Expando

		expandoValueLocalService.deleteValues(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

		indexer.delete(entry);

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			entry.getCompanyId(), entry.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId());
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.findByC_LtD(
				companyId, new Date(), start, end);
		}
		else {
			return blogsEntryPersistence.findByC_LtD_S(
				companyId, new Date(), status, start, end);
		}
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.findByC_LtD(
				companyId, new Date(), start, end, obc);
		}
		else {
			return blogsEntryPersistence.findByC_LtD_S(
				companyId, new Date(), status, start, end, obc);
		}
	}

	public int getCompanyEntriesCount(long companyId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.countByC_LtD(
				companyId, new Date());
		}
		else {
			return blogsEntryPersistence.countByC_LtD_S(
				companyId, new Date(), status);
		}
	}

	public BlogsEntry[] getEntriesPrevAndNext(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		return blogsEntryPersistence.findByG_S_PrevAndNext(
			entry.getEntryId(), entry.getGroupId(),
			WorkflowConstants.STATUS_APPROVED,
			new EntryDisplayDateComparator(true));
	}

	public BlogsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return blogsEntryPersistence.findByPrimaryKey(entryId);
	}

	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException, SystemException {

		return blogsEntryPersistence.findByG_UT(groupId, urlTitle);
	}

	public List<BlogsEntry> getGroupEntries(
			long groupId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.findByG_LtD(
				groupId, new Date(), start, end);
		}
		else {
			return blogsEntryPersistence.findByG_LtD_S(
				groupId, new Date(), status, start, end);
		}
	}

	public List<BlogsEntry> getGroupEntries(
			long groupId, int status, int start, int end, OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.findByG_LtD(
				groupId, new Date(), start, end, obc);
		}
		else {
			return blogsEntryPersistence.findByG_LtD_S(
				groupId, new Date(), status, start, end, obc);
		}
	}

	public int getGroupEntriesCount(long groupId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.countByG_LtD(groupId, new Date());
		}
		else {
			return blogsEntryPersistence.countByG_LtD_S(
				groupId, new Date(), status);
		}
	}

	public List<BlogsEntry> getGroupsEntries(
			long companyId, long groupId, int status, int start, int end)
		throws SystemException {

		return blogsEntryFinder.findByGroupIds(
			companyId, groupId, status, start, end);
	}

	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, int status, int start, int end)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.findByG_U_LtD(
				groupId, userId, new Date(), start, end);
		}
		else {
			return blogsEntryPersistence.findByG_U_LtD_S(
				groupId, userId, new Date(), status, start, end);
		}
	}

	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.findByG_U_LtD(
				groupId, userId, new Date(), start, end, obc);
		}
		else {
			return blogsEntryPersistence.findByG_U_LtD_S(
				groupId, userId, new Date(), status, start, end, obc);
		}
	}

	public int getGroupUserEntriesCount(long groupId, long userId, int status)
		throws SystemException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return blogsEntryPersistence.countByG_U_LtD(
				groupId, userId, new Date());
		}
		else {
			return blogsEntryPersistence.countByG_U_LtD_S(
				groupId, userId, new Date(), status);
		}
	}

	public List<BlogsEntry> getNoAssetEntries() throws SystemException {
		return blogsEntryFinder.findByNoAssets();
	}

	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, int status, int start, int end)
		throws SystemException {

		return blogsEntryFinder.findByOrganizationId(
			organizationId, new Date(), status, start, end);
	}

	public int getOrganizationEntriesCount(long organizationId, int status)
		throws SystemException {

		return blogsEntryFinder.countByOrganizationId(
			organizationId, new Date(), status);
	}

	public void subscribe(long userId, long groupId)
		throws PortalException, SystemException {

		subscriptionLocalService.addSubscription(
			userId, groupId, BlogsEntry.class.getName(), groupId);
	}

	public void unsubscribe(long userId, long groupId)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(
			userId, BlogsEntry.class.getName(), groupId);
	}

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
			userId, entry.getGroupId(), BlogsEntry.class.getName(),
			entry.getEntryId(), entry.getUuid(), 0, assetCategoryIds,
			assetTagNames, visible, null, null, entry.getDisplayDate(), null,
			ContentTypes.TEXT_HTML, entry.getTitle(), null, summary, null, null,
			0, 0, null, false);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

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
			new EntryDisplayDateException());

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
			getUniqueUrlTitle(entryId, entry.getGroupId(), title));
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

		if (!entry.isPending()) {
			entry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		entry.setExpandoBridgeAttributes(serviceContext);

		blogsEntryPersistence.update(entry, false);

		// Resources

		if ((serviceContext.getGroupPermissions() != null) ||
			(serviceContext.getGuestPermissions() != null)) {

			updateEntryResources(
				entry, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Small image

		saveImages(
			smallImage, entry.getSmallImageId(), smallImageBytes);

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

		if (Validator.isNotNull(trackbacks)) {
			serviceContext.setAttribute("trackbacks", trackbacks);
		}
		else {
			serviceContext.setAttribute("trackbacks", null);
		}

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), entry.getGroupId(), userId,
			BlogsEntry.class.getName(), entry.getEntryId(), entry,
			serviceContext);

		return entry;
	}

	public void updateEntryResources(
			BlogsEntry entry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.updateResources(
			entry.getCompanyId(), entry.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId(), groupPermissions,
			guestPermissions);
	}

	public BlogsEntry updateStatus(
			long userId, long entryId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		int oldStatus = entry.getStatus();
		long oldStatusByUserId = entry.getStatusByUserId();

		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setStatus(status);
		entry.setStatusByUserId(user.getUserId());
		entry.setStatusByUserName(user.getFullName());
		entry.setStatusDate(serviceContext.getModifiedDate(now));

		blogsEntryPersistence.update(entry, false);

		Indexer indexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Statistics

			blogsStatsUserLocalService.updateStatsUser(
				entry.getGroupId(), entry.getUserId(), entry.getDisplayDate());

			if (oldStatus != WorkflowConstants.STATUS_APPROVED) {

				// Asset

				assetEntryLocalService.updateVisible(
					BlogsEntry.class.getName(), entryId, true);

				// Social

				if (oldStatusByUserId == 0) {
					socialActivityLocalService.addUniqueActivity(
						entry.getUserId(), entry.getGroupId(),
						BlogsEntry.class.getName(), entryId,
						BlogsActivityKeys.ADD_ENTRY, StringPool.BLANK, 0);
				}
				else {
					socialActivityLocalService.addActivity(
						entry.getUserId(), entry.getGroupId(),
						BlogsEntry.class.getName(), entryId,
						BlogsActivityKeys.UPDATE_ENTRY, StringPool.BLANK, 0);
				}

				socialEquityLogLocalService.addEquityLogs(
					userId, BlogsEntry.class.getName(), entryId,
					ActionKeys.ADD_ENTRY, StringPool.BLANK);
			}

			// Indexer

			indexer.reindex(entry);

			// Subscriptions

			notifySubscribers(entry, serviceContext);

			// Ping

			String[] trackbacks = (String[])serviceContext.getAttribute(
				"trackbacks");
			Boolean pingOldTrackbacks = GetterUtil.getBoolean(
				(String)serviceContext.getAttribute("pingOldTrackbacks"));

			pingGoogle(entry, serviceContext);
			pingPingback(entry, serviceContext);
			pingTrackbacks(
				entry, trackbacks, pingOldTrackbacks, serviceContext);
		}
		else if (status != WorkflowConstants.STATUS_APPROVED) {

			// Asset

			assetEntryLocalService.updateVisible(
				BlogsEntry.class.getName(), entryId, false);

			// Indexer

			indexer.delete(entry);
		}

		return entry;
	}

	protected String getUniqueUrlTitle(
			long entryId, long groupId, String title)
		throws SystemException {

		String urlTitle = BlogsUtil.getUrlTitle(entryId, title);

		String newUrlTitle = ModelHintsUtil.trimString(
			BlogsEntry.class.getName(), "urlTitle", urlTitle);

		for (int i = 1;; i++) {
			BlogsEntry entry = blogsEntryPersistence.fetchByG_UT(
				groupId, newUrlTitle);

			if ((entry == null) || (entry.getEntryId() == entryId)) {
				break;
			}
			else {
				String suffix = StringPool.DASH + i;

				String prefix = newUrlTitle;

				if (newUrlTitle.length() > suffix.length()) {
					prefix = newUrlTitle.substring(
						0, newUrlTitle.length() - suffix.length());
				}

				newUrlTitle = prefix + suffix;
			}
		}

		return newUrlTitle;
	}

	protected void notifySubscribers(
			BlogsEntry entry, ServiceContext serviceContext)
		throws SystemException {

		if (!entry.isApproved()) {
			return;
		}

		String layoutFullURL = serviceContext.getLayoutFullURL();

		if (Validator.isNull(layoutFullURL)) {
			return;
		}

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if (preferences == null) {
			long ownerId = entry.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.BLOGS;
			String defaultPreferences = null;

			preferences = portletPreferencesLocalService.getPreferences(
				entry.getCompanyId(), ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		if (serviceContext.isCommandAdd() &&
			BlogsUtil.getEmailEntryAddedEnabled(preferences)) {
		}
		else if (serviceContext.isCommandUpdate() &&
				 BlogsUtil.getEmailEntryUpdatedEnabled(preferences)) {
		}
		else {
			return;
		}

		String entryURL =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs" +
				StringPool.SLASH + entry.getEntryId();

		String fromName = BlogsUtil.getEmailFromName(
			preferences, entry.getCompanyId());
		String fromAddress = BlogsUtil.getEmailFromAddress(
			preferences, entry.getCompanyId());

		Map<Locale, String> localizedSubjectMap = null;
		Map<Locale, String> localizedBodyMap = null;

		if (serviceContext.isCommandUpdate()) {
			localizedSubjectMap = BlogsUtil.getEmailEntryUpdatedSubjectMap(
				preferences);
			localizedBodyMap = BlogsUtil.getEmailEntryUpdatedBodyMap(
				preferences);
		}
		else {
			localizedSubjectMap = BlogsUtil.getEmailEntryAddedSubjectMap(
				preferences);
			localizedBodyMap = BlogsUtil.getEmailEntryAddedBodyMap(
				preferences);
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(entry.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$BLOGS_ENTRY_URL$]", entryURL);
		subscriptionSender.setContextUserPrefix("BLOGS_ENTRY");
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(localizedBodyMap);
		subscriptionSender.setLocalizedSubjectMap(localizedSubjectMap);
		subscriptionSender.setMailId("blogs_entry", entry.getEntryId());
		subscriptionSender.setPortletId(PortletKeys.BLOGS);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(entry.getGroupId());
		subscriptionSender.setUserId(entry.getUserId());

		subscriptionSender.addPersistedSubscribers(
			BlogsEntry.class.getName(), entry.getGroupId());

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

	protected void pingPingback(
		BlogsEntry entry, ServiceContext serviceContext) {

		if (!PropsValues.BLOGS_PINGBACK_ENABLED ||
			!entry.isAllowPingbacks() || !entry.isApproved()) {

			return;
		}

		String layoutFullURL = serviceContext.getLayoutFullURL();

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
		throws SystemException {

		if (!PropsValues.BLOGS_TRACKBACK_ENABLED ||
			!entry.isAllowTrackbacks() || !entry.isApproved()) {

			return;
		}

		String layoutFullURL = serviceContext.getLayoutFullURL();

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

		if (Validator.isNotNull(trackbacks)) {
			trackbacksSet = SetUtil.fromArray(trackbacks);
		}
		else {
			trackbacksSet = new HashSet<String>();
		}

		if (pingOldTrackbacks) {
			trackbacksSet.addAll(
				SetUtil.fromArray(StringUtil.split(entry.getTrackbacks())));

			entry.setTrackbacks(StringPool.BLANK);

			blogsEntryPersistence.update(entry, false);
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

			blogsEntryPersistence.update(entry, false);
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
				((smallImageBytes == null) ||
				 (smallImageBytes.length > smallImageMaxSize))) {

				throw new EntrySmallImageSizeException();
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BlogsEntryLocalServiceImpl.class);

}