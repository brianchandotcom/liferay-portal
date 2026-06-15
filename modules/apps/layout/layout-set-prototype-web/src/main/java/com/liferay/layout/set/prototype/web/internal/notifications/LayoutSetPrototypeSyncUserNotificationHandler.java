/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.web.internal.notifications;

import com.liferay.layout.set.prototype.constants.LayoutSetPrototypeConstants;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
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

		Locale locale = _portal.getLocale(serviceContext.getRequest());

		JSONObject layoutSetPrototypeNameMapJSONObject =
			jsonObject.getJSONObject("layoutSetPrototypeNameMap");

		String layoutSetPrototypeName =
			layoutSetPrototypeNameMapJSONObject.getString(
				LocaleUtil.toLanguageId(locale));

		if (Validator.isNull(layoutSetPrototypeName)) {
			layoutSetPrototypeName =
				layoutSetPrototypeNameMapJSONObject.getString(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
		}

		return _language.format(
			locale, _getLanguageKey(jsonObject.getString("result")),
			layoutSetPrototypeName);
	}

	private String _getLanguageKey(String result) {
		String key = "";

		if (Objects.equals(
				result,
				LayoutSetPrototypeConstants.STATUS_COMPLETED_WITH_ERRORS)) {

			key = "the-sync-of-the-site-template-x-finished-with-errors";
		}
		else if (Objects.equals(
					result, LayoutSetPrototypeConstants.STATUS_FAILED)) {

			key =
				"the-sync-of-the-site-template-x-failed-and-the-process-did-" +
					"not-finish";
		}
		else if (Objects.equals(
					result, LayoutSetPrototypeConstants.STATUS_SUCCESSFUL)) {

			key = "the-sync-of-the-site-template-x-finished-successfully";
		}

		return key;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}