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

package com.liferay.object.web.internal.notifications;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

/**
 * @author Feliphe Marinho
 */
public class ObjectUserNotificationsHandler
	extends BaseModelUserNotificationHandler {

	public ObjectUserNotificationsHandler(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		ObjectDefinition objectDefinition) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_objectDefinition = objectDefinition;

		setPortletId(objectDefinition.getPortletId());
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		return HtmlUtil.escape(jsonObject.getString("notificationMessage"));
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		String friendlyURL =
			_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				_objectDefinition.getClassName(), jsonObject.getLong("classPK"),
				serviceContext.getThemeDisplay());

		if (friendlyURL != null) {
			return friendlyURL;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				serviceContext.getRequest(), serviceContext.getScopeGroup(),
				jsonObject.getString("portletId"), 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/object_entries/edit_object_entry"
		).setParameter(
			"externalReferenceCode",
			jsonObject.getString("externalReferenceCode")
		).setParameter(
			"objectDefinitionId", _objectDefinition.getObjectDefinitionId()
		).setWindowState(
			WindowState.MAXIMIZED
		).buildString();
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final ObjectDefinition _objectDefinition;

}