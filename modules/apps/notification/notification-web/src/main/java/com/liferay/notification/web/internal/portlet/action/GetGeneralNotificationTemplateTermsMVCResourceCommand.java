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

package com.liferay.notification.web.internal.portlet.action;

import com.liferay.notification.constants.NotificationPortletKeys;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;
import java.util.Set;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = {
		"javax.portlet.name=" + NotificationPortletKeys.NOTIFICATION_TEMPLATES,
		"mvc.command.name=/notification_templates/get_general_notification_template_terms"
	},
	service = MVCResourceCommand.class
)
public class GetGeneralNotificationTemplateTermsMVCResourceCommand
	extends BaseNotificationTemplateTermsMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			getNotificationTemplateTermsJSONArray());
	}

	@Override
	protected Set<Map.Entry<String, String>> getTermNamesEntries() {
		return _termNames.entrySet();
	}

	private final Map<String, String> _termNames = HashMapBuilder.put(
		"current-user-email-address", "[%CURRENT_USER_EMAIL_ADDRESS%]"
	).put(
		"current-user-first-name", "[%CURRENT_USER_FIRST_NAME%]"
	).put(
		"current-user-id", "[%CURRENT_USER_ID%]"
	).put(
		"current-user-last-name", "[%CURRENT_USER_LAST_NAME%]"
	).put(
		"current-user-middle-name", "[%CURRENT_USER_MIDDLE_NAME%]"
	).put(
		"current-user-prefix", "[%CURRENT_USER_PREFIX%]"
	).put(
		"current-user-suffix", "[%CURRENT_USER_SUFFIX%]"
	).build();

}