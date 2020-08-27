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

package com.liferay.commerce.notification.test.util;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Luca Pellizzon
 */
public class CommerceNotificationTestUtil {

	public static CommerceNotificationTemplate addCommerceNotificationTemplate(
			String name, String description, String from, String to,
			String type, ServiceContext serviceContext)
		throws PortalException {

		return CommerceNotificationTemplateLocalServiceUtil.
			addCommerceNotificationTemplate(
				name, description, from, RandomTestUtil.randomLocaleStringMap(),
				to, null, null, type, true,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), serviceContext);
	}

	public static CommerceNotificationTemplate addNotificationTemplate(
			String to, String notificationType, ServiceContext serviceContext)
		throws PortalException {

		return addCommerceNotificationTemplate(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), to, notificationType,
			serviceContext);
	}

}