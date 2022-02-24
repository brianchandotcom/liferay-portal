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

package com.liferay.comment.web.internal.notifications;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.comment.web.internal.constants.CommentPortletKeys;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CommentPortletKeys.COMMENT,
	service = UserNotificationHandler.class
)
public class CommentUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public CommentUserNotificationHandler() {
		setPortletId(CommentPortletKeys.COMMENT);
	}

	@Override
	protected AssetRenderer<?> getAssetRenderer(JSONObject jsonObject) {
		MBDiscussion mbDiscussion = _fetchDiscussion(jsonObject);

		if (mbDiscussion == null) {
			return null;
		}

		AssetRenderer<?> assetRenderer = getAssetRenderer(
			mbDiscussion.getClassName(), mbDiscussion.getClassPK());

		if (assetRenderer == null) {
			try {
				AssetRendererFactory<?> assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(
							mbDiscussion.getClassName());

				assetRenderer = assetRendererFactory.getAssetRenderer(
					mbDiscussion.getClassPK(),
					AssetRendererFactory.TYPE_LATEST);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		return assetRenderer;
	}

	@Override
	protected String getBodyContent(JSONObject jsonObject) {
		return _htmlParser.extractText(super.getBodyContent(jsonObject));
	}

	@Override
	protected String getTitle(
		JSONObject jsonObject, AssetRenderer<?> assetRenderer,
		ServiceContext serviceContext) {

		MBDiscussion mbDiscussion = _fetchDiscussion(jsonObject);

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
						_portal.getUserName(
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
						_portal.getUserName(
							jsonObject.getLong("userId"), StringPool.BLANK))
				},
				false);
		}

		return message;
	}

	private MBDiscussion _fetchDiscussion(JSONObject jsonObject) {
		long classPK = jsonObject.getLong("classPK");

		try {
			return _mbDiscussionLocalService.fetchDiscussion(classPK);
		}
		catch (SystemException systemException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(systemException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommentUserNotificationHandler.class);

	@Reference
	private HtmlParser _htmlParser;

	@Reference
	private MBDiscussionLocalService _mbDiscussionLocalService;

	@Reference
	private Portal _portal;

}