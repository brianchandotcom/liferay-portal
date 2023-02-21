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

package com.liferay.journal.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Georgel Pop
 */
@Component(
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = AssetRendererFactory.class
)
public class JournalFeedAssetRendererFactory
	extends BaseAssetRendererFactory<JournalFeed> {

	public static final String TYPE = "content_feed";

	public JournalFeedAssetRendererFactory() {
		setClassName(JournalFeed.class.getName());
		setPortletId(JournalPortletKeys.JOURNAL);
		setSelectable(false);
	}

	@Override
	public AssetRenderer<JournalFeed> getAssetRenderer(long classPK, int type)
		throws PortalException {

		JournalFeedAssetRenderer journalFeedAssetRenderer =
			new JournalFeedAssetRenderer(
				_journalFeedLocalService.getFeed(classPK));

		journalFeedAssetRenderer.setAssetRendererType(type);

		return journalFeedAssetRenderer;
	}

	@Override
	public String getClassName() {
		return JournalFeed.class.getName();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return _journalFeedModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference
	private JournalFeedLocalService _journalFeedLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFeed)"
	)
	private ModelResourcePermission<JournalFeed>
		_journalFeedModelResourcePermission;

}