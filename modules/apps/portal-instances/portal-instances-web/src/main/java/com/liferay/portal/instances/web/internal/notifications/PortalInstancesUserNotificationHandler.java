/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.notifications;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instances.web.internal.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.PortletRequest;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(
	property = "jakarta.portlet.name=" + PortalInstancesPortletKeys.PORTAL_INSTANCES,
	service = UserNotificationHandler.class
)
public class PortalInstancesUserNotificationHandler
	extends BaseUserNotificationHandler {

	public PortalInstancesUserNotificationHandler() {
		setPortletId(PortalInstancesPortletKeys.PORTAL_INSTANCES);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				_getBody(jsonObject, serviceContext.getLocale()),
				getTitle(userNotificationEvent, serviceContext)
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				serviceContext.getRequest(), serviceContext.getScopeGroup(),
				PortalInstancesPortletKeys.PORTAL_INSTANCES, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/portal_instances/view"
		).buildString();
	}

	@Override
	protected String getTitle(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return _portal.getPortletTitle(
			PortalInstancesPortletKeys.PORTAL_INSTANCES,
			serviceContext.getLocale());
	}

	private String _getBody(JSONObject jsonObject, Locale locale) {
		PortalInstancesOperationType portalInstancesOperationType =
			PortalInstancesOperationType.parse(
				jsonObject.getString(
					PortalInstancesNotificationPayload.OPERATION_TYPE));

		String status = jsonObject.getString(
			PortalInstancesNotificationPayload.STATUS);

		if (portalInstancesOperationType == PortalInstancesOperationType.ADD) {
			String webId = HtmlUtil.escape(
				jsonObject.getString(
					PortalInstancesNotificationPayload.WEB_ID));

			if (status.equals(BackgroundTaskConstants.LABEL_SUCCESSFUL)) {
				return _language.format(
					locale, "the-virtual-instance-x-was-added-successfully",
					webId, false);
			}

			if (status.equals(BackgroundTaskConstants.LABEL_FAILED)) {
				return StringBundler.concat(
					_language.format(
						locale, "the-virtual-instance-x-could-not-be-added",
						webId, false),
					StringPool.SPACE, _getErrorMessage(jsonObject, locale));
			}
		}

		throw new IllegalArgumentException(
			StringBundler.concat(
				"No portal instances user notification found for operation ",
				"type ", portalInstancesOperationType.getValue(),
				" and status ", status));
	}

	private String _getErrorMessage(JSONObject jsonObject, Locale locale) {
		String errorMessage = jsonObject.getString(
			PortalInstancesNotificationPayload.ERROR_MESSAGE);

		if (Validator.isNull(errorMessage)) {
			return _language.get(locale, "an-unexpected-error-occurred");
		}

		return _language.get(locale, errorMessage);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}