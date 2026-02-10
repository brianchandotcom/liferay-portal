/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.internal.notification;

import com.liferay.antivirus.async.store.constants.AntivirusAsyncPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = "jakarta.portlet.name=" + AntivirusAsyncPortletKeys.ANTIVIRUS_ASYNC_NOTIFICATION,
	service = UserNotificationHandler.class
)
public class AntivirusAsyncUserNotificationHandler
	extends BaseUserNotificationHandler {

	public AntivirusAsyncUserNotificationHandler() {
		setActionable(false);
		setPortletId(AntivirusAsyncPortletKeys.ANTIVIRUS_ASYNC_NOTIFICATION);
	}

	@Override
	public boolean isDeliver(
			long userId, long classNameId, int notificationType,
			int deliveryType, ServiceContext serviceContext)
		throws PortalException {

		return true;
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		return _language.format(
			serviceContext.getLocale(),
			"the-file-x-version-x-was-deleted-due-to-the-detection-of-virus-" +
				"x-during-an-asynchronous-scan",
			new Object[] {
				jsonObject.getString("fileName"),
				jsonObject.getString("version"),
				jsonObject.getString("virusName")
			},
			false);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}