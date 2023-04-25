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

package com.liferay.osb.faro.web.internal.activator;

import com.liferay.osb.faro.service.FaroNotificationLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Geyson Silva
 */
@Component(service = NotificationActivator.class)
public class NotificationActivator {

	@Activate
	protected void activate() {
		if (log.isInfoEnabled()) {
			log.info("Cleaning up notifications");
		}

		_faroNotificationLocalService.clearDismissedNotifications();
	}

	protected static final Log log = LogFactoryUtil.getLog(
		NotificationActivator.class);

	@Reference
	private FaroNotificationLocalService _faroNotificationLocalService;

}