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

import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.base.JournalFeedServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	property = {
		"json.web.service.context.name=journal",
		"json.web.service.context.path=JournalFeed"
	},
	service = AopService.class
)
public class JournalFeedServiceImpl extends JournalFeedServiceBaseImpl {

	@Override
	public JournalFeed addFeed(
			long groupId, String feedId, boolean autoFeedId, String name,
			String description, String ddmStructureKey, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_FEED);

		return journalFeedLocalService.addFeed(
			getUserId(), groupId, feedId, autoFeedId, name, description,
			ddmStructureKey, ddmTemplateKey, ddmRendererTemplateKey, delta,
			orderByCol, orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedType, feedVersion, serviceContext);
	}

	@Override
	public void deleteFeed(long feedId) throws PortalException {
		_journalFeedModelResourcePermission.check(
			getPermissionChecker(), feedId, ActionKeys.DELETE);

		journalFeedLocalService.deleteFeed(feedId);
	}

	@Override
	public void deleteFeed(long groupId, String feedId) throws PortalException {
		JournalFeed feed = journalFeedPersistence.findByG_F(groupId, feedId);

		_journalFeedModelResourcePermission.check(
			getPermissionChecker(), feed, ActionKeys.DELETE);

		journalFeedLocalService.deleteFeed(feed);
	}

	@Override
	public JournalFeed getFeed(long feedId) throws PortalException {
		JournalFeed feed = journalFeedLocalService.getFeed(feedId);

		_journalFeedModelResourcePermission.check(
			getPermissionChecker(), feed, ActionKeys.VIEW);

		return feed;
	}

	@Override
	public JournalFeed getFeed(long groupId, String feedId)
		throws PortalException {

		JournalFeed feed = journalFeedPersistence.findByG_F(groupId, feedId);

		_journalFeedModelResourcePermission.check(
			getPermissionChecker(), feed, ActionKeys.VIEW);

		return feed;
	}

	@Override
	public JournalFeed updateFeed(
			long groupId, String feedId, String name, String description,
			String ddmStructureKey, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion, ServiceContext serviceContext)
		throws PortalException {

		JournalFeed feed = journalFeedPersistence.findByG_F(groupId, feedId);

		_journalFeedModelResourcePermission.check(
			getPermissionChecker(), feed, ActionKeys.UPDATE);

		return journalFeedLocalService.updateFeed(
			groupId, feedId, name, description, ddmStructureKey, ddmTemplateKey,
			ddmRendererTemplateKey, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, serviceContext);
	}

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFeed)"
	)
	private ModelResourcePermission<JournalFeed>
		_journalFeedModelResourcePermission;

	@Reference(
		target = "(resource.name=" + JournalConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}