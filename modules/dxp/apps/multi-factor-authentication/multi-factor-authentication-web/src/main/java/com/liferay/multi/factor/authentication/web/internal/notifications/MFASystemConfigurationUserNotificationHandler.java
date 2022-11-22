/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.web.internal.notifications;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(
	property = "javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
	service = UserNotificationHandler.class
)
public class MFASystemConfigurationUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public MFASystemConfigurationUserNotificationHandler() {
		setPortletId(ConfigurationAdminPortletKeys.INSTANCE_SETTINGS);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		boolean mfaDisableGlobally = jsonObject.getBoolean(
			"mfaDisableGlobally");

		String body = null;

		if (mfaDisableGlobally) {
			body = _language.get(
				serviceContext.getLocale(),
				"multi-factor-authentication-has-been-disabled-by-the-system-" +
					"administrator-and-is-unavailable-to-all-instances");
		}
		else {
			body = _language.get(
				serviceContext.getLocale(),
				"multi-factor-authentication-has-been-enabled-by-the-system-" +
					"administrator");
		}

		String title = _language.get(
			serviceContext.getLocale(), "multi-factor-authentication");

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {body, title});
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}