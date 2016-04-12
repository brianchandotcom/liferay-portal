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

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.microblogs.constants.MicroblogsPortletKeys;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.MicroblogsEntryConstants;
import com.liferay.microblogs.service.MicroblogsEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.LanguageResources;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Lee
 * @author Roberto Díaz
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
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}

	@Override
	protected String getTitle(
		JSONObject jsonObject, AssetRenderer<?> assetRenderer,
		ServiceContext serviceContext) {

		MicroblogsEntry microblogsEntry =
			_microblogsEntryLocalService.fetchMicroblogsEntry(
				assetRenderer.getClassPK());

		String userFullName = HtmlUtil.escape(
			PortalUtil.getUserName(
				microblogsEntry.getUserId(), StringPool.BLANK));

		int notificationType = jsonObject.getInt("notificationType");

		String message = StringPool.BLANK;
		String[] arguments = null;

		if (notificationType ==
				MicroblogsEntryConstants.NOTIFICATION_TYPE_REPLY) {

			message = "x-commented-on-your-microblogs-entry";
			arguments = new String[] {userFullName};
		}
		else if (notificationType ==
					MicroblogsEntryConstants.
						NOTIFICATION_TYPE_REPLY_TO_REPLIED) {

			long parentMicroblogsEntryUserId =
				microblogsEntry.fetchParentMicroblogsEntryUserId();

			User user = _userLocalService.fetchUser(
				parentMicroblogsEntryUserId);

			if (user != null) {
				message = "x-also-commented-on-x's-microblogs-entry";
				arguments = new String[] {userFullName, user.getFullName()};
			}
		}
		else if (notificationType ==
					MicroblogsEntryConstants.
						NOTIFICATION_TYPE_REPLY_TO_TAGGED) {

			message = "x-commented-on-a-microblogs-entry-you-are-tagged-in";
			arguments = new String[] {userFullName};
		}
		else if (notificationType ==
					MicroblogsEntryConstants.NOTIFICATION_TYPE_TAG) {

			message = "x-tagged-you-in-a-microblogs-entry";
			arguments = new String[] {userFullName};
		}

		if (Validator.isNull(message)) {
			return StringPool.BLANK;
		}

		ResourceBundle resourceBundle = getResourceBundle(
			serviceContext.getLocale());

		return ResourceBundleUtil.getString(resourceBundle, message, arguments);
	}

	@Reference(unbind = "-")
	protected void setMicroblogsEntryLocalService(
		MicroblogsEntryLocalService microblogsEntryLocalService) {

		_microblogsEntryLocalService = microblogsEntryLocalService;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.microblogs.web)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader, LanguageResources.RESOURCE_BUNDLE_LOADER);
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private MicroblogsEntryLocalService _microblogsEntryLocalService;
	private ResourceBundleLoader _resourceBundleLoader;
	private UserLocalService _userLocalService;

}