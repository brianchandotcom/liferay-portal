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
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.service.base.CommerceNotificationQueueEntryServiceBaseImpl;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceNotificationQueueEntry"
	},
	service = AopService.class
)
public class CommerceNotificationQueueEntryServiceImpl
	extends CommerceNotificationQueueEntryServiceBaseImpl {

	@Override
	public void deleteCommerceNotificationQueueEntry(
			long commerceNotificationQueueEntryId)
		throws PortalException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntry(
					commerceNotificationQueueEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceNotificationQueueEntry.getGroupId(),
			CommerceNotificationActionKeys.
				DELETE_COMMERCE_NOTIFICATION_QUEUE_ENTRY);

		commerceNotificationQueueEntryLocalService.
			deleteCommerceNotificationQueue(commerceNotificationQueueEntry);
	}

	@Override
	public List<CommerceNotificationQueueEntry>
			getCommerceNotificationQueueEntries(
				long groupId, int start, int end,
				OrderByComparator<CommerceNotificationQueueEntry>
					orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceNotificationActionKeys.
				VIEW_COMMERCE_NOTIFICATION_QUEUE_ENTRIES);

		return commerceNotificationQueueEntryLocalService.
			getCommerceNotificationQueueEntries(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationQueueEntriesCount(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceNotificationActionKeys.
				VIEW_COMMERCE_NOTIFICATION_QUEUE_ENTRIES);

		return commerceNotificationQueueEntryLocalService.
			getCommerceNotificationQueueEntriesCount(groupId);
	}

	@Override
	public CommerceNotificationQueueEntry resendCommerceNotificationQueueEntry(
			long commerceNotificationQueueEntryId)
		throws PortalException {

		CommerceNotificationQueueEntry commerceNotificationQueueEntry =
			commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntry(
					commerceNotificationQueueEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceNotificationQueueEntry.getGroupId(),
			CommerceNotificationActionKeys.
				RESEND_COMMERCE_NOTIFICATION_QUEUE_ENTRY);

		return commerceNotificationQueueEntryLocalService.
			resendCommerceNotificationQueueEntry(
				commerceNotificationQueueEntryId);
	}

	@Reference(
		target = "(resource.name=" + CPConstants.RESOURCE_NAME_CHANNEL + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}