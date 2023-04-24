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

package com.liferay.asset.publisher.web.internal.notifications;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = "javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER,
	service = UserNotificationHandler.class
)
public class AssetPublisherNotificationHandler
	extends BaseModelUserNotificationHandler {

	public AssetPublisherNotificationHandler() {
		setPortletId(AssetPublisherPortletKeys.ASSET_PUBLISHER);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				getBodyContent(
					_jsonFactory.createJSONObject(
						userNotificationEvent.getPayload())),
				getTitle(userNotificationEvent, serviceContext)
			});
	}

	@Override
	protected String getBodyContent(JSONObject jsonObject) {
		JSONObject contextJSONObject = jsonObject.getJSONObject("context");

		JSONObject assetEntriesJSONObject = contextJSONObject.getJSONObject(
			"[$ASSET_ENTRIES$]");

		return assetEntriesJSONObject.getString("originalValue");
	}

	protected String getTitle(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		Map<String, String> localizedJsonSubjectMap =
			(Map)_jsonFactory.looseDeserialize(
				String.valueOf(jsonObject.get("localizedSubjectMap")));

		String subject = localizedJsonSubjectMap.get(
			serviceContext.getLanguageId());

		return StringUtil.replace(
			subject, new String[] {"[$PORTLET_TITLE$]"},
			new String[] {
				HtmlUtil.escape(
					_portal.getPortletTitle(
						AssetPublisherPortletKeys.ASSET_PUBLISHER,
						serviceContext.getLanguageId()))
			});
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}