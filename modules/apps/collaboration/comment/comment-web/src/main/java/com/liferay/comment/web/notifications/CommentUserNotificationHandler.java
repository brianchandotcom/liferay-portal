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

package com.liferay.comment.web.notifications;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.comment.web.constants.CommentPortletKeys;
import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.service.MBDiscussionLocalService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.language.LanguageResources;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + CommentPortletKeys.COMMENT},
	service = UserNotificationHandler.class
)
public class CommentUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public CommentUserNotificationHandler() {
		setPortletId(CommentPortletKeys.COMMENT);
	}

	protected MBDiscussion fetchDiscussion(JSONObject jsonObject) {
		long classPK = jsonObject.getLong("classPK");

		try {
			return _mbDiscussionLocalService.fetchDiscussion(classPK);
		}
		catch (SystemException se) {
			return null;
		}
	}

	@Override
	protected AssetRenderer getAssetRenderer(JSONObject jsonObject) {
		MBDiscussion mbDiscussion = fetchDiscussion(jsonObject);

		if (mbDiscussion == null) {
			return null;
		}

		return getAssetRenderer(
			mbDiscussion.getClassName(), mbDiscussion.getClassPK());
	}

	@Override
	protected String getBodyContent(JSONObject jsonObject) {
		return HtmlUtil.stripHtml(super.getBodyContent(jsonObject));
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}

	@Override
	protected String getTitle(
		JSONObject jsonObject, AssetRenderer assetRenderer,
		ServiceContext serviceContext) {

		MBDiscussion mbDiscussion = fetchDiscussion(jsonObject);

		if (mbDiscussion == null) {
			return null;
		}

		String message = StringPool.BLANK;

		int notificationType = jsonObject.getInt("notificationType");

		if (notificationType ==
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY) {

			if (assetRenderer != null) {
				message = "x-added-a-new-comment-to-x";
			}
			else {
				message = "x-added-a-new-comment";
			}
		}
		else if (notificationType ==
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY) {

			if (assetRenderer != null) {
				message = "x-updated-a-comment-to-x";
			}
			else {
				message = "x-updated-a-comment";
			}
		}

		String[] arguments = null;

		if (assetRenderer != null) {
			arguments = new String[] {
				HtmlUtil.escape(
					PortalUtil.getUserName(
						jsonObject.getLong("userId"),
						StringPool.BLANK)),
				HtmlUtil.escape(
					assetRenderer.getTitle(serviceContext.getLocale()))
			};
		}
		else {
			arguments = new String[] {
				HtmlUtil.escape(
					PortalUtil.getUserName(
						jsonObject.getLong("userId"), StringPool.BLANK))
			};
		}

		ResourceBundle resourceBundle = getResourceBundle(
			serviceContext.getLocale());

		return ResourceBundleUtil.getString(resourceBundle, message, arguments);
	}

	@Reference(unbind = "-")
	protected void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {

		_mbDiscussionLocalService = mbDiscussionLocalService;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.comment.web)", unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader, LanguageResources.RESOURCE_BUNDLE_LOADER);
	}

	private MBDiscussionLocalService _mbDiscussionLocalService;
	private ResourceBundleLoader _resourceBundleLoader;

}