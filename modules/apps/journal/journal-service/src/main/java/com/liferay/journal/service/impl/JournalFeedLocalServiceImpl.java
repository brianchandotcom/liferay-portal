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

package com.liferay.journal.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.journal.constants.JournalFeedConstants;
import com.liferay.journal.exception.DuplicateFeedIdException;
import com.liferay.journal.exception.FeedContentFieldException;
import com.liferay.journal.exception.FeedIdException;
import com.liferay.journal.exception.FeedNameException;
import com.liferay.journal.exception.FeedTargetLayoutFriendlyUrlException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.base.JournalFeedLocalServiceBaseImpl;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.rss.util.RSSUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalFeed",
	service = AopService.class
)
public class JournalFeedLocalServiceImpl
	extends JournalFeedLocalServiceBaseImpl {

	@Override
	public JournalFeed addFeed(
			long userId, long groupId, String feedId, boolean autoFeedId,
			String name, String description, String ddmStructureKey,
			String ddmTemplateKey, String ddmRendererTemplateKey, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedFormat, double feedVersion,
			ServiceContext serviceContext)
		throws PortalException {

		// Feed

		User user = _userLocalService.getUser(userId);
		feedId = StringUtil.toUpperCase(StringUtil.trim(feedId));

		validate(
			user.getCompanyId(), groupId, feedId, autoFeedId, name,
			ddmStructureKey, targetLayoutFriendlyUrl, contentField);

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
		feed.setFeedId(feedId);
		feed.setName(name);
		feed.setDescription(description);
		feed.setDDMStructureKey(ddmStructureKey);
		feed.setDDMTemplateKey(ddmTemplateKey);
		feed.setDDMRendererTemplateKey(ddmRendererTemplateKey);
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

		feed = journalFeedPersistence.update(feed);

		// DDM Structure Link

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			groupId,
			_classNameLocalService.getClassNameId(JournalArticle.class),
			ddmStructureKey, true);

		_ddmStructureLinkLocalService.addStructureLink(
			_classNameLocalService.getClassNameId(JournalFeed.class),
			feed.getPrimaryKey(), ddmStructure.getStructureId());

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFeedResources(
				feed, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFeedResources(feed, serviceContext.getModelPermissions());
		}

		return feed;
	}

	@Override
	public void addFeedResources(
			JournalFeed feed, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_resourceLocalService.addResources(
			feed.getCompanyId(), feed.getGroupId(), feed.getUserId(),
			JournalFeed.class.getName(), feed.getId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFeedResources(
			JournalFeed feed, ModelPermissions modelPermissions)
		throws PortalException {

		_resourceLocalService.addModelResources(
			feed.getCompanyId(), feed.getGroupId(), feed.getUserId(),
			JournalFeed.class.getName(), feed.getId(), modelPermissions);
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
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFeed(JournalFeed feed) throws PortalException {

		// Feed

		journalFeedPersistence.remove(feed);

		// DDM Structure Link

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			feed.getGroupId(),
			_classNameLocalService.getClassNameId(JournalArticle.class),
			feed.getDDMStructureKey(), true);

		_ddmStructureLinkLocalService.deleteStructureLink(
			_classNameLocalService.getClassNameId(JournalFeed.class),
			feed.getPrimaryKey(), ddmStructure.getStructureId());

		// Resources

		_resourceLocalService.deleteResource(
			feed.getCompanyId(), JournalFeed.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, feed.getId());

		// Expando

		_expandoValueLocalService.deleteValues(
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
		OrderByComparator<JournalFeed> orderByComparator) {

		return journalFeedFinder.findByKeywords(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	@Override
	public List<JournalFeed> search(
		long companyId, long groupId, String feedId, String name,
		String description, boolean andOperator, int start, int end,
		OrderByComparator<JournalFeed> orderByComparator) {

		return journalFeedFinder.findByC_G_F_N_D(
			companyId, groupId, feedId, name, description, andOperator, start,
			end, orderByComparator);
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
			String ddmStructureKey, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedFormat,
			double feedVersion, ServiceContext serviceContext)
		throws PortalException {

		// Feed

		JournalFeed feed = journalFeedPersistence.findByG_F(groupId, feedId);

		validate(
			feed.getCompanyId(), groupId, name, ddmStructureKey,
			targetLayoutFriendlyUrl, contentField);

		feed.setName(name);
		feed.setDescription(description);
		feed.setDDMStructureKey(ddmStructureKey);
		feed.setDDMTemplateKey(ddmTemplateKey);
		feed.setDDMRendererTemplateKey(ddmRendererTemplateKey);
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

		feed = journalFeedPersistence.update(feed);

		// DDM Structure Link

		long classNameId = _classNameLocalService.getClassNameId(
			JournalFeed.class);

		DDMStructureLink ddmStructureLink =
			_ddmStructureLinkLocalService.getUniqueStructureLink(
				classNameId, feed.getPrimaryKey());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			groupId,
			_classNameLocalService.getClassNameId(JournalArticle.class),
			ddmStructureKey, true);

		_ddmStructureLinkLocalService.updateStructureLink(
			ddmStructureLink.getStructureLinkId(), classNameId,
			feed.getPrimaryKey(), ddmStructure.getStructureId());

		return feed;
	}

	protected boolean isValidStructureOptionValue(
		Map<String, DDMFormField> ddmFormFieldsMap, String contentField) {

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			String ddmFormFieldType = ddmFormField.getType();

			if (!(ddmFormFieldType.equals("radio") ||
				  ddmFormFieldType.equals("select"))) {

				continue;
			}

			DDMFormFieldOptions ddmFormFieldOptions =
				ddmFormField.getDDMFormFieldOptions();

			for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
				optionValue =
					ddmFormField.getName() + StringPool.UNDERLINE + optionValue;

				if (contentField.equals(optionValue)) {
					return true;
				}
			}
		}

		return false;
	}

	protected void validate(
			long companyId, long groupId, String feedId, boolean autoFeedId,
			String name, String ddmStructureKey, String targetLayoutFriendlyUrl,
			String contentField)
		throws PortalException {

		if (!autoFeedId) {
			if (Validator.isNull(feedId) || Validator.isNumber(feedId) ||
				(feedId.indexOf(CharPool.COMMA) != -1) ||
				(feedId.indexOf(CharPool.SPACE) != -1)) {

				throw new FeedIdException("Invalid feedId: " + feedId);
			}

			JournalFeed feed = journalFeedPersistence.fetchByG_F(
				groupId, feedId);

			if (feed != null) {
				throw new DuplicateFeedIdException(
					StringBundler.concat(
						"{groupId=", groupId, ", feedId=", feedId, "}"));
			}
		}

		validate(
			companyId, groupId, name, ddmStructureKey, targetLayoutFriendlyUrl,
			contentField);
	}

	protected void validate(
			long companyId, long groupId, String name, String ddmStructureKey,
			String targetLayoutFriendlyUrl, String contentField)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new FeedNameException("Name is null");
		}

		long plid = _portal.getPlidFromFriendlyURL(
			companyId, targetLayoutFriendlyUrl);

		if (plid <= 0) {
			throw new FeedTargetLayoutFriendlyUrlException(
				StringBundler.concat(
					"No layout exists for company ", companyId,
					" and friendly URL ", targetLayoutFriendlyUrl));
		}

		if (contentField.equals(JournalFeedConstants.RENDERED_WEB_CONTENT) ||
			contentField.equals(JournalFeedConstants.WEB_CONTENT_DESCRIPTION)) {

			return;
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			groupId,
			_classNameLocalService.getClassNameId(JournalArticle.class),
			ddmStructureKey, true);

		DDMForm ddmForm = ddmStructure.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		if (ddmFormFieldsMap.containsKey(contentField)) {
			return;
		}

		if (!isValidStructureOptionValue(ddmFormFieldsMap, contentField)) {
			throw new FeedContentFieldException(
				"Invalid content field " + contentField);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}