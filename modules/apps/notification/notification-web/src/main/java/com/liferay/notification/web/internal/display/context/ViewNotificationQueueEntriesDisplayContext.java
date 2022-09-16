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

package com.liferay.notification.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.notification.web.internal.display.context.helper.NotificationRequestHelper;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Paulo Albuquerque
 */
public class ViewNotificationQueueEntriesDisplayContext {

	public ViewNotificationQueueEntriesDisplayContext(
		HttpServletRequest httpServletRequest) {

		_notificationRequestHelper = new NotificationRequestHelper(
			httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/notification/v1.0/notification-queue-entries";
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				getAPIURL() + "/{id}/resend", null, "put",
				LanguageUtil.get(
					_notificationRequestHelper.getRequest(), "resend"),
				"put", "update", "async"),
			new FDSActionDropdownItem(
				getAPIURL() + "/{id}", "trash", "delete",
				LanguageUtil.get(
					_notificationRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	private final NotificationRequestHelper _notificationRequestHelper;

}