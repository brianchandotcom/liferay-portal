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

package com.liferay.journal.web.notifications;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.web.asset.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = UserNotificationHandler.class
)
public class JournalUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public JournalUserNotificationHandler() {
		setPortletId(JournalPortletKeys.JOURNAL);
	}

	@Override
	protected String getTitle(
		JSONObject jsonObject, AssetRenderer<?> assetRenderer,
		ServiceContext serviceContext) {

		String title = StringPool.BLANK;

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		JournalArticleAssetRenderer journalArticleAssetRenderer =
			(JournalArticleAssetRenderer)assetRenderer;

		JournalArticle journalArticle =
			journalArticleAssetRenderer.getArticle();

		String userFullName = HtmlUtil.escape(
			PortalUtil.getUserName(
				journalArticle.getUserId(), StringPool.BLANK));

		int notificationType = jsonObject.getInt("notificationType");

		if (notificationType ==
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY) {

			title = ResourceBundleUtil.getString(
				resourceBundle, "x-added-a-new-web-content-article",
				userFullName);
		}
		else if (notificationType ==
					UserNotificationDefinition.
						NOTIFICATION_TYPE_UPDATE_ENTRY) {

			title = ResourceBundleUtil.getString(
				resourceBundle, "x-updated-a-web-content-article",
				userFullName);
		}
		else if (notificationType ==
					JournalArticleConstants.
						NOTIFICATION_TYPE_MOVE_ENTRY_FROM_FOLDER) {

			title = ResourceBundleUtil.getString(
				resourceBundle, "x-moved-a-web-content-from-a-folder",
				userFullName);
		}
		else if (notificationType ==
					JournalArticleConstants.
						NOTIFICATION_TYPE_MOVE_ENTRY_TO_FOLDER) {

			title = ResourceBundleUtil.getString(
				resourceBundle, "x-moved-a-web-content-to-a-folder",
				userFullName);
		}

		return title;
	}

}