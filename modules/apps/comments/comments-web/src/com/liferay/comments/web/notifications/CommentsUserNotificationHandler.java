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

package com.liferay.comments.web.notifications;

import com.liferay.comments.web.constants.CommentsPortletKeys;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + CommentsPortletKeys.COMMENTS},
	service = UserNotificationHandler.class
)
public class CommentsUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public CommentsUserNotificationHandler() {
		setPortletId(CommentsPortletKeys.COMMENTS);
	}

	protected MBDiscussion fetchDiscussion(JSONObject jsonObject) {
		long classPK = jsonObject.getLong("classPK");

		try {
			return MBDiscussionLocalServiceUtil.fetchDiscussion(classPK);
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

		if (assetRenderer != null) {
			message = LanguageUtil.format(
				serviceContext.getLocale(), message,
				new String[] {
					HtmlUtil.escape(
						PortalUtil.getUserName(
							jsonObject.getLong("userId"), StringPool.BLANK)),
					HtmlUtil.escape(
						assetRenderer.getTitle(serviceContext.getLocale()))
				},
				false);
		}
		else {
			message = LanguageUtil.format(
				serviceContext.getLocale(), message,
				new String[] {
					HtmlUtil.escape(
						PortalUtil.getUserName(
							jsonObject.getLong("userId"), StringPool.BLANK))
				},
				false);
		}

		return message;
	}

}