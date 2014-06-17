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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.journal.DuplicateFeedIdException;
import com.liferay.portlet.journal.FeedContentFieldException;
import com.liferay.portlet.journal.FeedIdException;
import com.liferay.portlet.journal.FeedNameException;
import com.liferay.portlet.journal.FeedTargetLayoutFriendlyUrlException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFeedConstants;
import com.liferay.portlet.journal.service.base.JournalFeedLocalServiceBaseImpl;
import com.liferay.util.RSSUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class JournalFeedLocalServiceImpl
	extends JournalFeedLocalServiceBaseImpl {

	@Override
	public JournalFeed addFeed(
			long userId, long groupId, String feedId, boolean autoFeedId,
			String name, String description, String type, String structureId,
			String templateId, String rendererTemplateId, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedFormat, double feedVersion,
			ServiceContext serviceContext)
		throws PortalException {

		// Feed

		User user = userPersistence.findByPrimaryKey(userId);
		feedId = StringUtil.toUpperCase(feedId.trim());
		Date now = new Date();

		validate(
			user.getCompanyId(), groupId, feedId, autoFeedId, name, structureId,
			targetLayoutFriendlyUrl, contentField);

		if (autoFeedId) {
			feedId = String.valueOf(counterLocalService.increment());
		}

		long id = counterLocalService.increment();

		JournalFeed feed = journalFeedPersistence.create(id);

		feed.setUuid(serviceContext.getUuid());
		feed.setGroupId(groupId);
		feed.setCompanyId(user.getCompanyId());
		feed.setUserId(user.getUserId());
		feed.setUserName(user.getFullName());
		feed.setCreateDate(serviceContext.getCreateDate(now));
		feed.setModifiedDate(serviceContext.getModifiedDate(now));
		feed.setFeedId(feedId);
		feed.setName(name);
		feed.setDescription(description);
		feed.setType(type);
		feed.setStructureId(structureId);
		feed.setTemplateId(templateId);
		feed.setRendererTemplateId(rendererTemplateId);
		feed.setDelta(delta);
		feed.setOrderByCol(orderByCol);
		feed.setOrderByType(orderByType);
		feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		feed.setTargetPortletId(targetPortletId);
		feed.setContentField(contentField);

		if (Validator.isNull(feedFormat)) {
			feed.setFeedFormat(RSSUtil.FORMAT_DEFAULT);
			feed.setFeedVersion(RSSUtil.VERSION_DEFAULT);
		}
		else {
			feed.setFeedFormat(feedFormat);
			feed.setFeedVersion(feedVersion);
		}

		feed.setExpandoBridgeAttributes(serviceContext);

		journalFeedPersistence.update(feed);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFeedResources(
				feed, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFeedResources(
				feed, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return feed;
	}

	@Override
	public void addFeedResources(
			JournalFeed feed, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			feed.getCompanyId(), feed.getGroupId(), feed.getUserId(),
			JournalFeed.class.getName(), feed.getId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFeedResources(
			JournalFeed feed, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			feed.getCompanyId(), feed.getGroupId(), feed.getUserId(),
			JournalFeed.class.getName(), feed.getId(), groupPermissions,
			guestPermissions);
	}

	@Override
	public void addFeedResources(
			long feedId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		JournalFeed feed = journalFeedPersistence.findByPrimaryKey(feedId);

		addFeedResources(feed, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFeedResources(
			long feedId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		JournalFeed feed = journalFeedPersistence.findByPrimaryKey(feedId);

		addFeedResources(feed, groupPermissions, guestPermissions);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFeed(JournalFeed feed) throws PortalException {

		// Feed

		journalFeedPersistence.remove(feed);

		// Resources

		resourceLocalService.deleteResource(
			feed.getCompanyId(), JournalFeed.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, feed.getId());

		// Expando

		expandoValueLocalService.deleteValues(
			JournalFeed.class.getName(), feed.getId());
	}

	@Override
	public void deleteFeed(long feedId) throws PortalException {
		JournalFeed feed = journalFeedPersistence.findByPrimaryKey(feedId);

		journalFeedLocalService.deleteFeed(feed);
	}

	@Override
	public void deleteFeed(long groupId, String feedId) throws PortalException {
		JournalFeed feed = journalFeedPersistence.findByG_F(groupId, feedId);

		journalFeedLocalService.deleteFeed(feed);
	}

	@Override
	public JournalFeed fetchFeed(long groupId, String feedId) {
		return journalFeedPersistence.fetchByG_F(groupId, feedId);
	}

	@Override
	public JournalFeed getFeed(long feedId) throws PortalException {
		return journalFeedPersistence.findByPrimaryKey(feedId);
	}

	@Override
	public JournalFeed getFeed(long groupId, String feedId)
		throws PortalException {

		return journalFeedPersistence.findByG_F(groupId, feedId);
	}

	@Override
	public List<JournalFeed> getFeeds() {
		return journalFeedPersistence.findAll();
	}

	@Override
	public List<JournalFeed> getFeeds(long groupId) {
		return journalFeedPersistence.findByGroupId(groupId);
	}

	@Override
	public List<JournalFeed> getFeeds(long groupId, int start, int end) {
		return journalFeedPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public int getFeedsCount(long groupId) {
		return journalFeedPersistence.countByGroupId(groupId);
	}

	@Override
	public List<JournalFeed> search(
		long companyId, long groupId, String keywords, int start, int end,
		OrderByComparator obc) {

		return journalFeedFinder.findByKeywords(
			companyId, groupId, keywords, start, end, obc);
	}

	@Override
	public List<JournalFeed> search(
		long companyId, long groupId, String feedId, String name,
		String description, boolean andOperator, int start, int end,
		OrderByComparator obc) {

		return journalFeedFinder.findByC_G_F_N_D(
			companyId, groupId, feedId, name, description, andOperator, start,
			end, obc);
	}

	@Override
	public int searchCount(long companyId, long groupId, String keywords) {
		return journalFeedFinder.countByKeywords(companyId, groupId, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long groupId, String feedId, String name,
		String description, boolean andOperator) {

		return journalFeedFinder.countByC_G_F_N_D(
			companyId, groupId, feedId, name, description, andOperator);
	}

	@Override
	public JournalFeed updateFeed(
			long groupId, String feedId, String name, String description,
			String type, String structureId, String templateId,
			String rendererTemplateId, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedFormat,
			double feedVersion, ServiceContext serviceContext)
		throws PortalException {

		// Feed

		JournalFeed feed = journalFeedPersistence.findByG_F(groupId, feedId);

		validate(
			feed.getCompanyId(), groupId, name, structureId,
			targetLayoutFriendlyUrl, contentField);

		feed.setModifiedDate(serviceContext.getModifiedDate(null));
		feed.setName(name);
		feed.setDescription(description);
		feed.setType(type);
		feed.setStructureId(structureId);
		feed.setTemplateId(templateId);
		feed.setRendererTemplateId(rendererTemplateId);
		feed.setDelta(delta);
		feed.setOrderByCol(orderByCol);
		feed.setOrderByType(orderByType);
		feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		feed.setTargetPortletId(targetPortletId);
		feed.setContentField(contentField);

		if (Validator.isNull(feedFormat)) {
			feed.setFeedFormat(RSSUtil.FORMAT_DEFAULT);
			feed.setFeedVersion(RSSUtil.VERSION_DEFAULT);
		}
		else {
			feed.setFeedFormat(feedFormat);
			feed.setFeedVersion(feedVersion);
		}

		feed.setExpandoBridgeAttributes(serviceContext);

		journalFeedPersistence.update(feed);

		return feed;
	}

	protected boolean isValidStructureField(
		long groupId, String structureId, String contentField) {

		if (contentField.equals(JournalFeedConstants.WEB_CONTENT_DESCRIPTION) ||
			contentField.equals(JournalFeedConstants.RENDERED_WEB_CONTENT)) {

			return true;
		}

		try {
			DDMStructure ddmStructure =
				ddmStructureLocalService.getStructure(
					groupId,
					classNameLocalService.getClassNameId(JournalArticle.class),
					structureId);

			DDMForm ddmForm = ddmStructure.getDDMForm();

			Map<String, DDMFormField> ddmFormFieldsMap =
				ddmForm.getDDMFormFieldsMap(true);

			return ddmFormFieldsMap.containsKey(contentField);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	protected void validate(
			long companyId, long groupId, String feedId, boolean autoFeedId,
			String name, String structureId, String targetLayoutFriendlyUrl,
			String contentField)
		throws PortalException {

		if (!autoFeedId) {
			if (Validator.isNull(feedId) || Validator.isNumber(feedId) ||
				(feedId.indexOf(CharPool.COMMA) != -1) ||
				(feedId.indexOf(CharPool.SPACE) != -1)) {

				throw new FeedIdException();
			}

			JournalFeed feed = journalFeedPersistence.fetchByG_F(
				groupId, feedId);

			if (feed != null) {
				StringBundler sb = new StringBundler(5);

				sb.append("{groupId=");
				sb.append(groupId);
				sb.append(", feedId=");
				sb.append(feedId);
				sb.append("}");

				throw new DuplicateFeedIdException(sb.toString());
			}
		}

		validate(
			companyId, groupId, name, structureId, targetLayoutFriendlyUrl,
			contentField);
	}

	protected void validate(
			long companyId, long groupId, String name, String structureId,
			String targetLayoutFriendlyUrl, String contentField)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new FeedNameException();
		}

		long plid = PortalUtil.getPlidFromFriendlyURL(
			companyId, targetLayoutFriendlyUrl);

		if (plid <= 0) {
			throw new FeedTargetLayoutFriendlyUrlException();
		}

		if (!isValidStructureField(groupId, structureId, contentField)) {
			throw new FeedContentFieldException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalFeedLocalServiceImpl.class);

}