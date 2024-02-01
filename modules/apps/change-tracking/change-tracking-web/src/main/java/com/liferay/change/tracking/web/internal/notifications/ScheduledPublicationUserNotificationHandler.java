/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.notifications;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = ScheduledPublicationUserNotificationHandler.class)
public class ScheduledPublicationUserNotificationHandler
	extends BaseUserNotificationHandler {

	public ScheduledPublicationUserNotificationHandler() {
		setPortletId(CTPortletKeys.PUBLICATIONS);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		boolean showConflicts = jsonObject.getBoolean("showConflicts");

		String body = null;

		if (showConflicts) {
			body = _language.get(
				serviceContext.getLocale(),
				"click-on-this-notification-to-see-the-list-of-conflicts-" +
					"that-need-to-be-manually-resolved");
		}
		else {
			body = _language.get(
				serviceContext.getLocale(),
				"an-unexpected-error-occurred-while-publishing-the-scheduled-" +
					"publication");
		}

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				body,
				_language.format(
					serviceContext.getLocale(),
					"x-scheduled-publication-failed",
					new Object[] {jsonObject.getString("ctCollectionName")},
					false)
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		boolean showConflicts = jsonObject.getBoolean("showConflicts");

		if (showConflicts) {
			return PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					serviceContext.getRequest(), serviceContext.getScopeGroup(),
					CTPortletKeys.PUBLICATIONS, 0, 0,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/change_tracking/view_conflicts"
			).setParameter(
				"ctCollectionId",
				_jsonFactory.createJSONObject(
					userNotificationEvent.getPayload()
				).getLong(
					"ctCollectionId"
				)
			).buildString();
		}

		return null;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}