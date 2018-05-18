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

package com.liferay.commerce.notification.service.impl;

import com.liferay.commerce.notification.constants.CommerceNotificationActionKeys;
import com.liferay.commerce.notification.constants.CommerceNotificationConstants;
import com.liferay.commerce.notification.model.CommerceNotificationQueue;
import com.liferay.commerce.notification.service.base.CommerceNotificationQueueServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationQueueServiceImpl
	extends CommerceNotificationQueueServiceBaseImpl {

	@Override
	public void deleteCommerceNotificationQueue(
			long commerceNotificationQueueId)
		throws PortalException {

		CommerceNotificationQueue commerceNotificationQueue =
			commerceNotificationQueueLocalService.getCommerceNotificationQueue(
				commerceNotificationQueueId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceNotificationQueue.getGroupId(),
			CommerceNotificationActionKeys.DELETE_COMMERCE_NOTIFICATION_QUEUE);

		commerceNotificationQueueLocalService.deleteCommerceNotificationQueue(
			commerceNotificationQueue);
	}

	@Override
	public List<CommerceNotificationQueue> getCommerceNotificationQueues(
			long groupId, int start, int end,
			OrderByComparator<CommerceNotificationQueue> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceNotificationActionKeys.VIEW_COMMERCE_NOTIFICATION_QUEUES);

		return
			commerceNotificationQueueLocalService.getCommerceNotificationQueues(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationQueuesCount(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceNotificationActionKeys.VIEW_COMMERCE_NOTIFICATION_QUEUES);

		return commerceNotificationQueueLocalService.
			getCommerceNotificationQueuesCount(groupId);
	}

	@Override
	public CommerceNotificationQueue resendCommerceNotificationQueue(
			long commerceNotificationQueueId, boolean sent,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceNotificationQueue commerceNotificationQueue =
			commerceNotificationQueueLocalService.getCommerceNotificationQueue(
				commerceNotificationQueueId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceNotificationQueue.getGroupId(),
			CommerceNotificationActionKeys.RESEND_COMMERCE_NOTIFICATION_QUEUE);

		return commerceNotificationQueueLocalService.
			resendCommerceNotificationQueue(
				commerceNotificationQueueId, sent, serviceContext);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceNotificationQueueServiceImpl.class,
				"_portletResourcePermission",
				CommerceNotificationConstants.RESOURCE_NAME);

}