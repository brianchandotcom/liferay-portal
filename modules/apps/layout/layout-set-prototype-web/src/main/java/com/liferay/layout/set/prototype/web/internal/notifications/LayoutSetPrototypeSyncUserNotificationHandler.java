/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.web.internal.notifications;

import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(
	property = "jakarta.portlet.name=" + LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE,
	service = UserNotificationHandler.class
)
public class LayoutSetPrototypeSyncUserNotificationHandler
	extends BaseUserNotificationHandler {

	public LayoutSetPrototypeSyncUserNotificationHandler() {
		setPortletId(LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		String result = jsonObject.getString("result");
		String siteTemplateName = HtmlUtil.escape(
			jsonObject.getString("siteTemplateName"));

		String key;

		if (Objects.equals(result, "successful")) {
			key = "sync-of-x-site-template-finished-successfully";
		}
		else if (Objects.equals(result, "completed_with_errors")) {
			key = "sync-of-x-site-template-finished-with-errors";
		}
		else {
			key =
				"sync-of-x-site-template-failed-and-the-process-did-not-finish";
		}

		return _language.format(
			_portal.getLocale(serviceContext.getRequest()), key,
			siteTemplateName);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}