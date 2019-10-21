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

package com.liferay.segments.experiment.web.internal.notifications;

import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
	service = UserNotificationDefinition.class
)
public class SegmentsExperimentUpdateStatusUserNotificationDefinition
	extends UserNotificationDefinition {

	public SegmentsExperimentUpdateStatusUserNotificationDefinition() {
		super(
			SegmentsPortletKeys.SEGMENTS_EXPERIMENT, 0,
			SegmentsExperimentConstants.NOTIFICATION_TYPE_UPDATE_STATUS,
			"receive-a-notification-when-someone-changes-the-status-of-your-" +
				"ab-tests");

		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"email", UserNotificationDeliveryConstants.TYPE_EMAIL, true,
				true));
		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				true));
	}

}