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

package com.liferay.notification.service.test;

import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Gustavo Lima
 */
public class NotificationTestUtil {

	public static NotificationTemplate addNotificationTemplate(
			long userId, String from, String name)
		throws Exception {

		return NotificationTemplateLocalServiceUtil.addNotificationTemplate(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), from,
			RandomTestUtil.randomLocaleStringMap(), name,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap());
	}

}