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

package com.liferay.microblogs.web.notifications;

import com.liferay.microblogs.constants.MicroblogsPortletKeys;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.MicroblogsEntryConstants;
import com.liferay.microblogs.service.MicroblogsEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Lee
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + MicroblogsPortletKeys.MICROBLOGS},
	service = UserNotificationHandler.class
)
public class MicroblogsUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public MicroblogsUserNotificationHandler() {
		setPortletId(MicroblogsPortletKeys.MICROBLOGS);
	}

	@Override
	protected String getTitle(
		JSONObject jsonObject, AssetRenderer<?> assetRenderer,
		ServiceContext serviceContext) {

		MicroblogsEntry microblogsEntry =
			_microblogsEntryLocalService.fetchMicroblogsEntry(
				assetRenderer.getClassPK());

		String title = StringPool.BLANK;

		String userFullName = HtmlUtil.escape(
			PortalUtil.getUserName(
				microblogsEntry.getUserId(), StringPool.BLANK));

		int notificationType = jsonObject.getInt("notificationType");

		if (notificationType ==
				MicroblogsEntryConstants.NOTIFICATION_TYPE_REPLY) {

			title = serviceContext.translate(
				"x-commented-on-your-post", userFullName);
		}
		else if (notificationType ==
					MicroblogsEntryConstants.
						NOTIFICATION_TYPE_REPLY_TO_REPLIED) {

			long parentMicroblogsEntryUserId =
				microblogsEntry.fetchParentMicroblogsEntryUserId();

			User user = _userLocalService.fetchUser(
				parentMicroblogsEntryUserId);

			if (user != null) {
				title = serviceContext.translate(
					"x-also-commented-on-x's-post", userFullName,
					user.getFullName());
			}
		}
		else if (notificationType ==
					MicroblogsEntryConstants.
						NOTIFICATION_TYPE_REPLY_TO_TAGGED) {

			title = serviceContext.translate(
				"x-commented-on-a-post-you-are-tagged-in", userFullName);
		}
		else if (notificationType ==
					MicroblogsEntryConstants.NOTIFICATION_TYPE_TAG) {

			title = serviceContext.translate(
				"x-tagged-you-in-a-post", userFullName);
		}

		return title;
	}

	@Reference(unbind = "-")
	protected void setMicroblogsEntryLocalService(
		MicroblogsEntryLocalService microblogsEntryLocalService) {

		_microblogsEntryLocalService = microblogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile MicroblogsEntryLocalService _microblogsEntryLocalService;
	private volatile UserLocalService _userLocalService;

}