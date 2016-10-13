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

package com.liferay.blogs.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.blogs.exception.EntryUrlTitleException;
import com.liferay.blogs.kernel.exception.EntryContentException;
import com.liferay.blogs.kernel.exception.EntryCoverImageCropException;
import com.liferay.blogs.kernel.exception.EntryDisplayDateException;
import com.liferay.blogs.kernel.exception.EntrySmallImageNameException;
import com.liferay.blogs.kernel.exception.EntrySmallImageScaleException;
import com.liferay.blogs.kernel.exception.EntryTitleException;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.persistence.BlogsEntryFinder;
import com.liferay.blogs.kernel.service.persistence.BlogsEntryPersistence;
import com.liferay.blogs.kernel.util.comparator.EntryDisplayDateComparator;
import com.liferay.blogs.kernel.util.comparator.EntryIdComparator;
import com.liferay.blogs.service.base.BlogsEntryLocalServiceBaseImpl;
import com.liferay.blogs.settings.BlogsGroupServiceSettings;
import com.liferay.blogs.util.BlogsEntryAttachmentFileEntryHelper;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.service.FriendlyURLLocalService;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelectorProcessor;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.util.LayoutURLUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.constants.BlogsConstants;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.blogs.social.BlogsActivityKeys;
import com.liferay.portlet.blogs.util.BlogsUtil;
import com.liferay.portlet.blogs.util.LinkbackProducerUtil;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.exception.TrashEntryException;
import com.liferay.trash.kernel.model.TrashEntry;

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
@ProviderType
public class BlogsEntryLocalServiceImpl extends BlogsEntryLocalServiceBaseImpl {

	@Override
	public Folder addAttachmentsFolder(long userId, long groupId)
		throws PortalException {

		return doAddFolder(userId, groupId, BlogsConstants.SERVICE_NAME);
	}

	@Override
	public void addCoverImage(long entryId, ImageSelector imageSelector)
		throws PortalException {

		if (imageSelector == null) {
			return;
		}

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		String coverImageURL = StringPool.BLANK;
		long coverImageFileEntryId = 0;

		if (Validator.isNotNull(imageSelector.getImageURL())) {
			coverImageURL = imageSelector.getImageURL();
		}
		else if (imageSelector.getImageBytes() != null) {
			coverImageFileEntryId = addCoverImageFileEntry(
				entry.getUserId(), entry.getGroupId(), entryId, imageSelector);
		}

		entry.setCoverImageURL(coverImageURL);
		entry.setCoverImageFileEntryId(coverImageFileEntryId);

		blogsEntryPersistence.update(entry);
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String content, Date displayDate,
			ServiceContext serviceContext)
		throws PortalException {

		return addEntry(
			userId, title, StringPool.BLANK, StringPool.BLANK, content,
			displayDate, true, true, new String[0], StringPool.BLANK, null,
			null, serviceContext);
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String content,
			ServiceContext serviceContext)
		throws PortalException {

		return addEntry(
			userId, title, StringPool.BLANK, StringPool.BLANK, content,
			new Date(), true, true, new String[0], StringPool.BLANK, null, null,
			serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addEntry(long, String,
	 *             String, String, String, int, int, int, int, int, boolean,
	 *             boolean, String[], String, ImageSelector, ImageSelector,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public BlogsEntry addEntry(
			long userId, String title, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks, boolean smallImage,
			String smallImageURL, String smallImageFileName,
			InputStream smallImageInputStream, ServiceContext serviceContext)
		throws PortalException {

		ImageSelector coverImageImageSelector = null;
		ImageSelector smallImageImageSelector = null;

		if (smallImage) {
			if (Validator.isNotNull(smallImageFileName) &&
				(smallImageInputStream != null)) {

				try {
					byte[] bytes = FileUtil.getBytes(smallImageInputStream);

					smallImageImageSelector = new ImageSelector(
						bytes, smallImageFileName,
						MimeTypesUtil.getContentType(smallImageFileName), null);
				}
				catch (IOException ioe) {
					_log.error("Unable to create image selector", ioe);
				}
			}
			else if (Validator.isNotNull(smallImageURL)) {
				smallImageImageSelector = new ImageSelector(smallImageURL);
			}
		}

		return addEntry(
			userId, title, StringPool.BLANK, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			StringPool.BLANK, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry addEntry(
			long userId, String title, String subtitle, String description,
			String content, Date displayDate, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		return addEntry(
			userId, title, subtitle, StringPool.BLANK, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry addEntry(
			long userId, String title, String subtitle, String urlTitle,
			String description, String content, Date displayDate,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector, ServiceContext serviceContext)
		throws PortalException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		int status = WorkflowConstants.STATUS_DRAFT;

		validate(title, urlTitle, content, status);

		long entryId = counterLocalService.increment();

		if (Validator.isNotNull(urlTitle)) {
			long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

			friendlyURLLocalService.validate(
				user.getCompanyId(), groupId, classNameId, urlTitle);
		}

		BlogsEntry entry = blogsEntryPersistence.create(entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setTitle(title);
		entry.setSubtitle(subtitle);

		if (Validator.isNotNull(urlTitle)) {
			FriendlyURL friendlyURL = friendlyURLLocalService.addFriendlyURL(
				user.getCompanyId(), groupId, BlogsEntry.class, entryId,
				urlTitle);

			entry.setUrlTitle(friendlyURL.getUrlTitle());
		}

		entry.setDescription(description);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setAllowPingbacks(allowPingbacks);
		entry.setAllowTrackbacks(allowTrackbacks);
		entry.setStatus(status);
		entry.setStatusByUserId(userId);
		entry.setStatusDate(serviceContext.getModifiedDate(null));
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
			addEntryResources(entry, serviceContext.getModelPermissions());
		}

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Images

		long coverImageFileEntryId = 0;
		String coverImageURL = null;

		if (coverImageImageSelector != null) {
			coverImageURL = coverImageImageSelector.getImageURL();

			if (coverImageImageSelector.getImageBytes() != null) {
				coverImageFileEntryId = addCoverImageFileEntry(
					userId, groupId, entryId, coverImageImageSelector);
			}
		}

		long smallImageFileEntryId = 0;
		String smallImageURL = null;

		if (smallImageImageSelector != null) {
			smallImageURL = smallImageImageSelector.getImageURL();

			if (smallImageImageSelector.getImageBytes() != null) {
				smallImageFileEntryId = addSmallImageFileEntry(
					userId, groupId, entryId, smallImageImageSelector);
			}
		}

		validate(smallImageFileEntryId);

		entry.setCoverImageCaption(coverImageCaption);
		entry.setCoverImageFileEntryId(coverImageFileEntryId);
		entry.setCoverImageURL(coverImageURL);

		if ((smallImageFileEntryId != 0) ||
			Validator.isNotNull(smallImageURL)) {

			entry.setSmallImage(true);
		}

		entry.setSmallImageFileEntryId(smallImageFileEntryId);
		entry.setSmallImageURL(smallImageURL);

		blogsEntryPersistence.update(entry);

		// Workflow

		if (ArrayUtil.isNotEmpty(trackbacks)) {
			serviceContext.setAttribute("trackbacks", trackbacks);
		}
		else {
			serviceContext.setAttribute("trackbacks", null);
		}

		return startWorkflowInstance(userId, entry, serviceContext);
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String subtitle, String urlTitle,
			String description, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			EntryDisplayDateException.class);

		return addEntry(
			userId, title, subtitle, urlTitle, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	@Override
	public BlogsEntry addEntry(
			long userId, String title, String subtitle, String description,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		return addEntry(
			userId, title, subtitle, StringPool.BLANK, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	@Override
	public void addEntryResources(
			BlogsEntry entry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
			BlogsEntry entry, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), modelPermissions);
	}

	@Override
	public void addEntryResources(
			long entryId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
			long entryId, ModelPermissions modelPermissions)
		throws PortalException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, modelPermissions);
	}

	@Override
	public long addOriginalImageFileEntry(
			long userId, long groupId, long entryId,
			ImageSelector imageSelector)
		throws PortalException {

		byte[] imageBytes = imageSelector.getImageBytes();

		if (imageBytes == null) {
			return 0;
		}

		Folder folder = addAttachmentsFolder(userId, groupId);

		FileEntry originalFileEntry =
			BlogsEntryAttachmentFileEntryHelper.
				addBlogsEntryAttachmentFileEntry(
					groupId, userId, entryId, folder.getFolderId(),
					imageSelector.getImageTitle(),
					imageSelector.getImageMimeType(), imageBytes);

		return originalFileEntry.getFileEntryId();
	}

	@Override
	public void addSmallImage(long entryId, ImageSelector imageSelector)
		throws PortalException {

		if (imageSelector == null) {
			return;
		}

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		boolean smallImage = false;
		long smallImageFileEntryId = 0;
		String smallImageURL = StringPool.BLANK;

		if (Validator.isNotNull(imageSelector.getImageURL())) {
			smallImage = true;

			smallImageURL = imageSelector.getImageURL();
		}
		else if (imageSelector.getImageBytes() != null) {
			smallImage = true;

			smallImageFileEntryId = addSmallImageFileEntry(
				entry.getUserId(), entry.getGroupId(), entryId, imageSelector);
		}

		entry.setSmallImage(smallImage);
		entry.setSmallImageFileEntryId(smallImageFileEntryId);
		entry.setSmallImageURL(smallImageURL);

		blogsEntryPersistence.update(entry);
	}

	@Override
	public void checkEntries() throws PortalException {
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

			String portletId = PortletProviderUtil.getPortletId(
				BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

			if (Validator.isNotNull(portletId)) {
				String layoutFullURL = PortalUtil.getLayoutFullURL(
					entry.getGroupId(), portletId);

				serviceContext.setLayoutFullURL(layoutFullURL);
			}

			serviceContext.setScopeGroupId(entry.getGroupId());

			blogsEntryLocalService.updateStatus(
				entry.getStatusByUserId(), entry.getEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<String, Serializable>());
		}
	}

	@Override
	public void deleteEntries(long groupId) throws PortalException {
		for (BlogsEntry entry : blogsEntryPersistence.findByGroupId(groupId)) {
			blogsEntryLocalService.deleteEntry(entry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public BlogsEntry deleteEntry(BlogsEntry entry) throws PortalException {

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

		// Attachments

		long coverImageFileEntryId = entry.getCoverImageFileEntryId();

		if (coverImageFileEntryId != 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				coverImageFileEntryId);
		}

		long smallImageFileEntryId = entry.getSmallImageFileEntryId();

		if (smallImageFileEntryId != 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				smallImageFileEntryId);
		}

		// Comment

		deleteDiscussion(entry);

		// Expando

		expandoRowLocalService.deleteRows(entry.getEntryId());

		// Friendly URL

		friendlyURLLocalService.deleteFriendlyURL(
			entry.getCompanyId(), entry.getGroupId(), BlogsEntry.class,
			entry.getEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Trash

		trashEntryLocalService.deleteEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			entry.getCompanyId(), entry.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId());

		return entry;
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		blogsEntryLocalService.deleteEntry(entry);
	}

	@Override
	public Folder fetchAttachmentsFolder(long userId, long groupId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, BlogsConstants.SERVICE_NAME);

		try {
			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				BlogsConstants.SERVICE_NAME);

			return folder;
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public BlogsEntry fetchBlogsEntry(long entryId) {
		return
			com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil.
				fetchBlogsEntry(entryId);
	}

	@Override
	public BlogsEntry fetchBlogsEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return
			com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil.
				fetchBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public BlogsEntry fetchEntry(long groupId, String urlTitle) {
		Group group = groupPersistence.fetchByPrimaryKey(groupId);

		FriendlyURL friendlyURL = friendlyURLLocalService.fetchFriendlyURL(
			group.getCompanyId(), groupId, BlogsEntry.class, urlTitle);

		if (friendlyURL != null) {
			return blogsEntryPersistence.fetchByPrimaryKey(
				friendlyURL.getClassPK());
		}

		return blogsEntryPersistence.fetchByG_UT(groupId, urlTitle);
	}

	@Override
	public List<BlogsEntry> getBlogsEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {

		return
			com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil.
				getBlogsEntriesByUuidAndCompanyId(
					uuid, companyId, start, end, orderByComparator);
	}

	@Override
	public BlogsEntry getBlogsEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return
			com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil.
				getBlogsEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<BlogsEntry> getCompanyEntries(
		long companyId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

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

	@Override
	public int getCompanyEntriesCount(
		long companyId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

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
		throws PortalException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		BlogsEntry[] entries = blogsEntryPersistence.findByG_D_S_PrevAndNext(
			entryId, entry.getGroupId(), entry.getDisplayDate(),
			WorkflowConstants.STATUS_APPROVED, new EntryIdComparator(true));

		if (entries[0] == null) {
			entries[0] = blogsEntryPersistence.fetchByG_LtD_S_Last(
				entry.getGroupId(), entry.getDisplayDate(),
				WorkflowConstants.STATUS_APPROVED,
				new EntryDisplayDateComparator(true));
		}

		if (entries[2] == null) {
			entries[2] = blogsEntryPersistence.fetchByG_GtD_S_First(
				entry.getGroupId(), entry.getDisplayDate(),
				WorkflowConstants.STATUS_APPROVED,
				new EntryDisplayDateComparator(true));
		}

		return entries;
	}

	@Override
	public BlogsEntry getEntry(long entryId) throws PortalException {
		return blogsEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException {

		Group group = groupPersistence.fetchByPrimaryKey(groupId);

		FriendlyURL friendlyURL = friendlyURLLocalService.fetchFriendlyURL(
			group.getCompanyId(), groupId, BlogsEntry.class, urlTitle);

		if (friendlyURL != null) {
			return blogsEntryPersistence.findByPrimaryKey(
				friendlyURL.getClassPK());
		}

		return blogsEntryPersistence.findByG_UT(groupId, urlTitle);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		return
			com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil.
				getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

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

	@Override
	public List<BlogsEntry> getGroupEntries(
		long groupId, QueryDefinition<BlogsEntry> queryDefinition) {

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

	@Override
	public int getGroupEntriesCount(
		long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.countByG_LtD_NotS(
				groupId, displayDate, queryDefinition.getStatus());
		}
		else {
			return blogsEntryPersistence.countByG_LtD_S(
				groupId, displayDate, queryDefinition.getStatus());
		}
	}

	@Override
	public int getGroupEntriesCount(
		long groupId, QueryDefinition<BlogsEntry> queryDefinition) {

		if (queryDefinition.isExcludeStatus()) {
			return blogsEntryPersistence.countByG_NotS(
				groupId, queryDefinition.getStatus());
		}
		else {
			return blogsEntryPersistence.countByG_S(
				groupId, queryDefinition.getStatus());
		}
	}

	@Override
	public List<BlogsEntry> getGroupsEntries(
		long companyId, long groupId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return blogsEntryFinder.findByGroupIds(
			companyId, groupId, displayDate, queryDefinition);
	}

	@Override
	public List<BlogsEntry> getGroupUserEntries(
		long groupId, long userId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

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

	@Override
	public int getGroupUserEntriesCount(
		long groupId, long userId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

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
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return
			com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil.
				getIndexableActionableDynamicQuery();
	}

	@Override
	public List<BlogsEntry> getNoAssetEntries() {
		return blogsEntryFinder.findByNoAssets();
	}

	@Override
	public List<BlogsEntry> getOrganizationEntries(
		long organizationId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return blogsEntryFinder.findByOrganizationId(
			organizationId, displayDate, queryDefinition);
	}

	@Override
	public int getOrganizationEntriesCount(
		long organizationId, Date displayDate,
		QueryDefinition<BlogsEntry> queryDefinition) {

		return blogsEntryFinder.countByOrganizationId(
			organizationId, displayDate, queryDefinition);
	}

	@Override
	public void moveEntriesToTrash(long groupId, long userId)
		throws PortalException {

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
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry moveEntryToTrash(long userId, BlogsEntry entry)
		throws PortalException {

		// Entry

		if (entry.isInTrash()) {
			throw new TrashEntryException();
		}

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

		SocialActivityManagerUtil.addActivity(
			userId, entry, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
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
	 */
	@Override
	public BlogsEntry moveEntryToTrash(long userId, long entryId)
		throws PortalException {

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
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry restoreEntryFromTrash(long userId, long entryId)
		throws PortalException {

		// Entry

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		if (!entry.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			BlogsEntry.class.getName(), entryId);

		entry = updateStatus(
			userId, entryId, trashEntry.getStatus(), new ServiceContext());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getTitle());

		SocialActivityManagerUtil.addActivity(
			userId, entry, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		return entry;
	}

	@Override
	public void subscribe(long userId, long groupId) throws PortalException {
		subscriptionLocalService.addSubscription(
			userId, groupId, BlogsEntry.class.getName(), groupId);
	}

	@Override
	public void unsubscribe(long userId, long groupId) throws PortalException {
		subscriptionLocalService.deleteSubscription(
			userId, BlogsEntry.class.getName(), groupId);
	}

	@Override
	public void updateAsset(
			long userId, BlogsEntry entry, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

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
			assetTagNames, true, visible, null, null, null, null,
			ContentTypes.TEXT_HTML, entry.getTitle(), entry.getDescription(),
			summary, null, null, 0, 0, priority);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Override
	public BlogsEntry updateBlogsEntry(BlogsEntry blogsEntry) {
		return
			com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil.
				updateBlogsEntry(blogsEntry);
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String content,
			ServiceContext serviceContext)
		throws PortalException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		return updateEntry(
			userId, entryId, title, entry.getSubtitle(), entry.getDescription(),
			content, entry.getDisplayDate(), entry.getAllowPingbacks(),
			entry.getAllowTrackbacks(), StringUtil.split(entry.getTrackbacks()),
			StringPool.BLANK, null, null, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	 *             String, String, String, String, int, int, int, int, int,
	 *             boolean, boolean, String[], String, ImageSelector,
	 *             ImageSelector, ServiceContext)}
	 */
	@Deprecated
	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String description,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, boolean smallImage, String smallImageURL,
			String smallImageFileName, InputStream smallImageInputStream,
			ServiceContext serviceContext)
		throws PortalException {

		ImageSelector coverImageImageSelector = null;
		ImageSelector smallImageImageSelector = null;

		if (smallImage) {
			if (Validator.isNotNull(smallImageFileName) &&
				(smallImageInputStream != null)) {

				try {
					byte[] bytes = FileUtil.getBytes(smallImageInputStream);

					smallImageImageSelector = new ImageSelector(
						bytes, smallImageFileName,
						MimeTypesUtil.getContentType(smallImageFileName), null);
				}
				catch (IOException ioe) {
					_log.error("Unable to create image selector", ioe);
				}
			}
			else if (Validator.isNotNull(smallImageURL)) {
				smallImageImageSelector = new ImageSelector(smallImageURL);
			}
		}
		else {
			smallImageImageSelector = new ImageSelector();
		}

		return updateEntry(
			userId, entryId, title, StringPool.BLANK, description, content,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
			StringPool.BLANK, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String description, String content, Date displayDate,
			boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		return updateEntry(
			userId, entryId, title, subtitle, StringPool.BLANK, description,
			content, displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String urlTitle, String description, String content,
			Date displayDate, boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		// Entry

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		int status = entry.getStatus();

		if (!entry.isPending() && !entry.isDraft()) {
			status = WorkflowConstants.STATUS_DRAFT;
		}

		validate(title, urlTitle, content, status);

		if (Validator.isNotNull(urlTitle)) {
			long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

			friendlyURLLocalService.validate(
				entry.getCompanyId(), entry.getGroupId(), classNameId, entryId,
				urlTitle);
		}

		String oldUrlTitle = entry.getUrlTitle();

		entry.setTitle(title);
		entry.setSubtitle(subtitle);

		if (Validator.isNotNull(urlTitle)) {
			FriendlyURL friendlyURL = friendlyURLLocalService.addFriendlyURL(
				entry.getCompanyId(), entry.getGroupId(), BlogsEntry.class,
				entry.getEntryId(), urlTitle);

			entry.setUrlTitle(friendlyURL.getUrlTitle());
		}

		entry.setDescription(description);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setAllowPingbacks(allowPingbacks);
		entry.setAllowTrackbacks(allowTrackbacks);
		entry.setStatus(status);

		entry.setExpandoBridgeAttributes(serviceContext);

		blogsEntryPersistence.update(entry);

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Images

		long coverImageFileEntryId = entry.getCoverImageFileEntryId();
		String coverImageURL = entry.getCoverImageURL();

		long deletePreviousCoverImageFileEntryId = 0;

		if (coverImageImageSelector != null) {
			coverImageURL = coverImageImageSelector.getImageURL();

			if (coverImageImageSelector.getImageBytes() != null) {
				coverImageFileEntryId = addCoverImageFileEntry(
					userId, entry.getGroupId(), entryId,
					coverImageImageSelector);
			}
			else {
				coverImageFileEntryId = 0;
			}

			deletePreviousCoverImageFileEntryId =
				entry.getCoverImageFileEntryId();
		}

		long smallImageFileEntryId = entry.getSmallImageFileEntryId();
		String smallImageURL = entry.getSmallImageURL();

		long deletePreviousSmallImageFileEntryId = 0;

		if (smallImageImageSelector != null) {
			smallImageURL = smallImageImageSelector.getImageURL();

			if (smallImageImageSelector.getImageBytes() != null) {
				smallImageFileEntryId = addSmallImageFileEntry(
					userId, entry.getGroupId(), entryId,
					smallImageImageSelector);
			}
			else {
				smallImageFileEntryId = 0;
			}

			deletePreviousSmallImageFileEntryId =
				entry.getSmallImageFileEntryId();
		}

		validate(smallImageFileEntryId);

		entry.setCoverImageCaption(coverImageCaption);
		entry.setCoverImageFileEntryId(coverImageFileEntryId);
		entry.setCoverImageURL(coverImageURL);

		if ((smallImageFileEntryId != 0) ||
			Validator.isNotNull(smallImageURL)) {

			entry.setSmallImage(true);
		}

		entry.setSmallImageFileEntryId(smallImageFileEntryId);
		entry.setSmallImageURL(smallImageURL);

		blogsEntryPersistence.update(entry);

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

		entry = startWorkflowInstance(userId, entry, serviceContext);

		if (deletePreviousCoverImageFileEntryId != 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				deletePreviousCoverImageFileEntryId);
		}

		if (deletePreviousSmallImageFileEntryId != 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				deletePreviousSmallImageFileEntryId);
		}

		return entry;
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String description, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		return updateEntry(
			userId, entryId, title, subtitle, StringPool.BLANK, description,
			content, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, allowPingbacks, allowTrackbacks,
			trackbacks, coverImageCaption, coverImageImageSelector,
			smallImageImageSelector, serviceContext);
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String urlTitle, String description, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			EntryDisplayDateException.class);

		return updateEntry(
			userId, entryId, title, subtitle, urlTitle, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);
	}

	@Override
	public void updateEntryResources(
			BlogsEntry entry, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.updateResources(
			entry.getCompanyId(), entry.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId(), modelPermissions);
	}

	@Override
	public void updateEntryResources(
			BlogsEntry entry, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

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
		throws PortalException {

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
		throws PortalException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		validate(
			entry.getTitle(), entry.getUrlTitle(), entry.getContent(), status);

		int oldStatus = entry.getStatus();

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			now.before(entry.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		entry.setStatus(status);
		entry.setStatusByUserId(user.getUserId());
		entry.setStatusByUserName(user.getFullName());
		entry.setStatusDate(serviceContext.getModifiedDate(now));

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			Validator.isNull(entry.getUrlTitle())) {

			String uniqueUrlTitle = getUniqueUrlTitle(
				entryId, entry.getGroupId(), entry.getTitle());

			FriendlyURL friendlyURL = friendlyURLLocalService.addFriendlyURL(
				entry.getCompanyId(), entry.getGroupId(), BlogsEntry.class,
				entry.getEntryId(), uniqueUrlTitle);

			entry.setUrlTitle(friendlyURL.getUrlTitle());
		}

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
				null, true, true);

			// Social

			if ((oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
				(oldStatus != WorkflowConstants.STATUS_SCHEDULED)) {

				if (serviceContext.isCommandUpdate()) {
					SocialActivityManagerUtil.addActivity(
						user.getUserId(), entry, BlogsActivityKeys.UPDATE_ENTRY,
						extraDataJSONObject.toString(), 0);
				}
				else {
					SocialActivityManagerUtil.addUniqueActivity(
						user.getUserId(), entry, BlogsActivityKeys.ADD_ENTRY,
						extraDataJSONObject.toString(), 0);
				}
			}

			// Trash

			if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
				if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
					CommentManagerUtil.restoreDiscussionFromTrash(
						BlogsEntry.class.getName(), entryId);
				}

				trashEntryLocalService.deleteEntry(
					BlogsEntry.class.getName(), entryId);
			}

			if (oldStatus != WorkflowConstants.STATUS_IN_TRASH) {

				// Subscriptions

				notifySubscribers(
					userId, entry, serviceContext, workflowContext);

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
					SocialActivityManagerUtil.addActivity(
						user.getUserId(), entry, BlogsActivityKeys.UPDATE_ENTRY,
						extraDataJSONObject.toString(), 0);
				}
				else {
					SocialActivityManagerUtil.addUniqueActivity(
						user.getUserId(), entry, BlogsActivityKeys.ADD_ENTRY,
						extraDataJSONObject.toString(), 0);
				}
			}

			// Trash

			if (status == WorkflowConstants.STATUS_IN_TRASH) {
				if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
					CommentManagerUtil.moveDiscussionToTrash(
						BlogsEntry.class.getName(), entryId);
				}

				trashEntryLocalService.addTrashEntry(
					userId, entry.getGroupId(), BlogsEntry.class.getName(),
					entry.getEntryId(), entry.getUuid(), null, oldStatus, null,
					null);
			}
			else if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
				if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
					CommentManagerUtil.restoreDiscussionFromTrash(
						BlogsEntry.class.getName(), entryId);
				}

				trashEntryLocalService.deleteEntry(
					BlogsEntry.class.getName(), entryId);
			}
		}

		return entry;
	}

	protected long addCoverImageFileEntry(
			long userId, long groupId, long entryId,
			ImageSelector imageSelector)
		throws PortalException {

		byte[] imageBytes = imageSelector.getImageBytes();

		if (imageBytes == null) {
			return 0;
		}

		try {
			ImageSelectorProcessor imageSelectorProcessor =
				new ImageSelectorProcessor(imageSelector.getImageBytes());

			imageBytes = imageSelectorProcessor.cropImage(
				imageSelector.getImageCropRegion());

			if (imageBytes == null) {
				throw new EntryCoverImageCropException();
			}

			Folder folder = addCoverImageFolder(userId, groupId);

			return addProcessedImageFileEntry(
				userId, groupId, entryId, folder.getFolderId(),
				imageSelector.getImageTitle(), imageSelector.getImageMimeType(),
				imageBytes);
		}
		catch (IOException ioe) {
			throw new EntryCoverImageCropException();
		}
	}

	protected Folder addCoverImageFolder(long userId, long groupId)
		throws PortalException {

		return doAddFolder(userId, groupId, _COVER_IMAGE_FOLDER_NAME);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected void addDiscussion(BlogsEntry entry, long userId, long groupId)
		throws PortalException {

		if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
			CommentManagerUtil.addDiscussion(
				userId, groupId, BlogsEntry.class.getName(), entry.getEntryId(),
				entry.getUserName());
		}
	}

	protected long addProcessedImageFileEntry(
			long userId, long groupId, long entryId, long folderId,
			String title, String mimeType, byte[] bytes)
		throws PortalException {

		if (Validator.isNull(title)) {
			title = StringUtil.randomString() + "_processedImage_" + entryId;
		}

		FileEntry processedImageFileEntry =
			BlogsEntryAttachmentFileEntryHelper.
				addBlogsEntryAttachmentFileEntry(
					groupId, userId, entryId, folderId, title, mimeType, bytes);

		return processedImageFileEntry.getFileEntryId();
	}

	protected long addSmallImageFileEntry(
			long userId, long groupId, long entryId,
			ImageSelector imageSelector)
		throws PortalException {

		byte[] imageBytes = imageSelector.getImageBytes();

		if (imageBytes == null) {
			return 0;
		}

		try {
			BlogsGroupServiceSettings blogsGroupServiceSettings =
				BlogsGroupServiceSettings.getInstance(groupId);

			ImageSelectorProcessor imageSelectorProcessor =
				new ImageSelectorProcessor(imageSelector.getImageBytes());

			imageBytes = imageSelectorProcessor.scaleImage(
				blogsGroupServiceSettings.getSmallImageWidth());

			if (imageBytes == null) {
				throw new EntrySmallImageScaleException();
			}

			Folder folder = addSmallImageFolder(userId, groupId);

			return addProcessedImageFileEntry(
				userId, groupId, entryId, folder.getFolderId(),
				imageSelector.getImageTitle(), imageSelector.getImageMimeType(),
				imageBytes);
		}
		catch (IOException ioe) {
			throw new EntrySmallImageScaleException();
		}
	}

	protected Folder addSmallImageFolder(long userId, long groupId)
		throws PortalException {

		return doAddFolder(userId, groupId, _SMALL_IMAGE_FOLDER_NAME);
	}

	protected void deleteDiscussion(BlogsEntry entry) throws PortalException {
		CommentManagerUtil.deleteDiscussion(
			BlogsEntry.class.getName(), entry.getEntryId());
	}

	protected Folder doAddFolder(long userId, long groupId, String folderName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, BlogsConstants.SERVICE_NAME, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderName,
			serviceContext);

		return folder;
	}

	protected String getEntryURL(
			BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException {

		String entryURL = GetterUtil.getString(
			serviceContext.getAttribute("entryURL"));

		if (Validator.isNotNull(entryURL)) {
			return entryURL;
		}

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return StringPool.BLANK;
		}

		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

		if (Validator.isNotNull(portletId)) {
			String layoutURL = LayoutURLUtil.getLayoutURL(
				entry.getGroupId(), portletId, serviceContext);

			if (Validator.isNotNull(layoutURL)) {
				return layoutURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs" +
					StringPool.SLASH + entry.getEntryId();
			}
		}

		portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.MANAGE);

		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			request, portletId, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/blogs/view_entry");
		portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

		return portletURL.toString();
	}

	protected String getUniqueUrlTitle(
		long entryId, long groupId, String title) {

		String urlTitle = BlogsUtil.getUrlTitle(entryId, title);

		for (int i = 1;; i++) {
			BlogsEntry entry = fetchEntry(groupId, urlTitle);

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

	protected void notifySubscribers(
			long userId, BlogsEntry entry, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		String entryURL = (String)workflowContext.get(
			WorkflowConstants.CONTEXT_URL);

		if (!entry.isApproved() || Validator.isNull(entryURL)) {
			return;
		}

		BlogsGroupServiceSettings blogsGroupServiceSettings =
			BlogsGroupServiceSettings.getInstance(entry.getGroupId());

		boolean sendEmailEntryUpdated = GetterUtil.getBoolean(
			serviceContext.getAttribute("sendEmailEntryUpdated"));

		if (serviceContext.isCommandAdd() &&
			blogsGroupServiceSettings.isEmailEntryAddedEnabled()) {
		}
		else if (sendEmailEntryUpdated && serviceContext.isCommandUpdate() &&
				 blogsGroupServiceSettings.isEmailEntryUpdatedEnabled()) {
		}
		else {
			return;
		}

		Group group = groupPersistence.findByPrimaryKey(entry.getGroupId());

		String entryTitle = entry.getTitle();

		String fromName = blogsGroupServiceSettings.getEmailFromName();
		String fromAddress = blogsGroupServiceSettings.getEmailFromAddress();

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (serviceContext.isCommandUpdate()) {
			subjectLocalizedValuesMap =
				blogsGroupServiceSettings.getEmailEntryUpdatedSubject();
			bodyLocalizedValuesMap =
				blogsGroupServiceSettings.getEmailEntryUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				blogsGroupServiceSettings.getEmailEntryAddedSubject();
			bodyLocalizedValuesMap =
				blogsGroupServiceSettings.getEmailEntryAddedBody();
		}

		SubscriptionSender subscriptionSender =
			new GroupSubscriptionCheckSubscriptionSender(
				BlogsPermission.RESOURCE_NAME);

		subscriptionSender.setClassPK(entry.getEntryId());
		subscriptionSender.setClassName(entry.getModelClassName());
		subscriptionSender.setCompanyId(entry.getCompanyId());
		subscriptionSender.setContextAttribute(
			"[$BLOGS_ENTRY_CONTENT$]",
			StringUtil.shorten(HtmlUtil.stripHtml(entry.getContent()), 500),
			false);
		subscriptionSender.setContextAttributes(
			"[$BLOGS_ENTRY_CREATE_DATE$]",
			Time.getSimpleDate(entry.getCreateDate(), "yyyy/MM/dd"),
			"[$BLOGS_ENTRY_DESCRIPTION$]", entry.getDescription(),
			"[$BLOGS_ENTRY_SITE_NAME$]",
			group.getDescriptiveName(serviceContext.getLocale()),
			"[$BLOGS_ENTRY_STATUS_BY_USER_NAME$]", entry.getStatusByUserName(),
			"[$BLOGS_ENTRY_TITLE$]", entryTitle,
			"[$BLOGS_ENTRY_UPDATE_COMMENT$]",
			HtmlUtil.replaceNewLine(
				GetterUtil.getString(
					serviceContext.getAttribute("emailEntryUpdatedComment"))),
			"[$BLOGS_ENTRY_URL$]", entryURL,
			"[$BLOGS_ENTRY_USER_PORTRAIT_URL$]",
			workflowContext.get(WorkflowConstants.CONTEXT_USER_PORTRAIT_URL),
			"[$BLOGS_ENTRY_USER_URL$]",
			workflowContext.get(WorkflowConstants.CONTEXT_USER_URL));
		subscriptionSender.setContextCreatorUserPrefix("BLOGS_ENTRY");
		subscriptionSender.setCreatorUserId(entry.getUserId());
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setEntryURL(entryURL);
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

		subscriptionSender.setMailId("blogs_entry", entry.getEntryId());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

		subscriptionSender.setPortletId(portletId);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(entry.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);

		subscriptionSender.addPersistedSubscribers(
			BlogsEntry.class.getName(), entry.getGroupId());

		subscriptionSender.addPersistedSubscribers(
			BlogsEntry.class.getName(), entry.getEntryId());

		subscriptionSender.flushNotificationsAsync();
	}

	protected void pingGoogle(BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException {

		if (!PropsValues.BLOGS_PING_GOOGLE_ENABLED || !entry.isApproved()) {
			return;
		}

		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.MANAGE);

		if (Validator.isNull(portletId)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not pinging Google because there is no blogs portlet " +
						"provider");
			}

			return;
		}

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			serviceContext.getScopeGroupId(), portletId);

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
		String changesURL = serviceContext.getPathMain() + "/blogs/rss";

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
		throws PortalException {

		if (!PropsValues.BLOGS_PINGBACK_ENABLED || !entry.isAllowPingbacks() ||
			!entry.isApproved()) {

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
		throws PortalException {

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

		Map<String, String> parts = new HashMap<>();

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
			trackbacksSet = new HashSet<>();
		}

		if (pingOldTrackbacks) {
			trackbacksSet.addAll(
				SetUtil.fromArray(StringUtil.split(entry.getTrackbacks())));

			entry.setTrackbacks(StringPool.BLANK);

			blogsEntryPersistence.update(entry);
		}

		Set<String> oldTrackbacks = SetUtil.fromArray(
			StringUtil.split(entry.getTrackbacks()));

		Set<String> validTrackbacks = new HashSet<>();

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

	protected BlogsEntry startWorkflowInstance(
			long userId, BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(
			WorkflowConstants.CONTEXT_URL, getEntryURL(entry, serviceContext));

		String userPortraitURL = StringPool.BLANK;
		String userURL = StringPool.BLANK;

		if (serviceContext.getThemeDisplay() != null) {
			User user = userPersistence.findByPrimaryKey(userId);

			userPortraitURL = user.getPortraitURL(
				serviceContext.getThemeDisplay());
			userURL = user.getDisplayURL(serviceContext.getThemeDisplay());
		}

		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_PORTRAIT_URL, userPortraitURL);
		workflowContext.put(WorkflowConstants.CONTEXT_USER_URL, userURL);

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			entry.getCompanyId(), entry.getGroupId(), userId,
			BlogsEntry.class.getName(), entry.getEntryId(), entry,
			serviceContext, workflowContext);
	}

	protected void validate(long smallImageFileEntryId) throws PortalException {
		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.BLOGS_IMAGE_EXTENSIONS, StringPool.COMMA);

		if (smallImageFileEntryId != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				smallImageFileEntryId);

			boolean validSmallImageExtension = false;

			for (String imageExtension : imageExtensions) {
				if (StringPool.STAR.equals(imageExtension) ||
					imageExtension.equals(
						StringPool.PERIOD + fileEntry.getExtension())) {

					validSmallImageExtension = true;

					break;
				}
			}

			if (!validSmallImageExtension) {
				throw new EntrySmallImageNameException(
					"Invalid small image for file entry " +
						smallImageFileEntryId);
			}
		}
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #validate(String, String,
	 *             String, int)}
	 */
	@Deprecated
	protected void validate(String title, String urlTitle, String content)
		throws PortalException {

		validate(title, urlTitle, content, WorkflowConstants.STATUS_APPROVED);
	}

	protected void validate(String title, String urlTitle, String content, int status)
		throws PortalException {

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			Validator.isNull(title)) {

			throw new EntryTitleException("Title is null");
		}

		if (Validator.isNotNull(title)) {
			int titleMaxLength = ModelHintsUtil.getMaxLength(
				BlogsEntry.class.getName(), "title");

			if (title.length() > titleMaxLength) {
				throw new EntryTitleException(
					"Title has more than " + titleMaxLength + " characters");
			}
		}

		if (Validator.isNotNull(urlTitle)) {
			int urlTitleMaxLength = ModelHintsUtil.getMaxLength(
				BlogsEntry.class.getName(), "urlTitle");

			if (urlTitle.length() > urlTitleMaxLength) {
				throw new EntryUrlTitleException(
					"URL title has more than " + urlTitleMaxLength +
						" characters");
			}
		}

		if (Validator.isNull(content)) {
			throw new EntryContentException("Content is null");
		}

		int contentMaxLength = ModelHintsUtil.getMaxLength(
			BlogsEntry.class.getName(), "content");

		if (content.length() > contentMaxLength) {
			throw new EntryContentException(
				"Content has more than " + contentMaxLength + " characters");
		}
	}

	@ServiceReference(type = FriendlyURLLocalService.class)
	protected FriendlyURLLocalService friendlyURLLocalService;

	@ServiceReference(type = BlogsEntryFinder.class)
	protected BlogsEntryFinder blogsEntryFinder;

	@ServiceReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;

	private static final String _COVER_IMAGE_FOLDER_NAME = "Cover Image";

	private static final String _SMALL_IMAGE_FOLDER_NAME = "Small Image";

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryLocalServiceImpl.class);

}