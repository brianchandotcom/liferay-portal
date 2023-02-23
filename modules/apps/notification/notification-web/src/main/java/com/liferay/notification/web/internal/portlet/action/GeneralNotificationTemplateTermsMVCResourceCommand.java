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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = {
		"javax.portlet.name=" + NotificationPortletKeys.NOTIFICATION_TEMPLATES,
		"mvc.command.name=/notification_templates/general_notification_template_terms"
	},
	service = MVCResourceCommand.class
)
public class GeneralNotificationTemplateTermsMVCResourceCommand
	extends BaseNotificationTemplateTermsMVCResourceCommand {

	@Override
	protected Set<Map.Entry<String, String>> getEntrySet() {
		return _currentUserTermsMap.entrySet();
	}

	private static final Map<String, String> _currentUserTermsMap =
		Collections.unmodifiableMap(
			HashMapBuilder.put(
				"current-user-email-address", "[%CURRENT_USER_EMAIL%]"
			).put(
				"current-user-first-name", "[%CURRENT_USER_FIRSTNAME%]"
			).put(
				"current-user-id", "[%CURRENT_USER_ID%]"
			).put(
				"current-user-last-name", "[%CURRENT_USER_LASTNAME%]"
			).put(
				"current-user-middle-name", "[%CURRENT_USER_MIDDLENAME%]"
			).put(
				"current-user-prefix", "[%CURRENT_USER_PREFIX%]"
			).put(
				"current-user-suffix", "[%CURRENT_USER_SUFFIX%]"
			).build());

}