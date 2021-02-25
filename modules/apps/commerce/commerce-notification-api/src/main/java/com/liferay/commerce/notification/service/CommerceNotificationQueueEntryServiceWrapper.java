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

package com.liferay.commerce.notification.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceNotificationQueueEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryService
 * @generated
 */
public class CommerceNotificationQueueEntryServiceWrapper
	implements CommerceNotificationQueueEntryService,
			   ServiceWrapper<CommerceNotificationQueueEntryService> {

	public CommerceNotificationQueueEntryServiceWrapper() {
		this(null);
	}

	public CommerceNotificationQueueEntryServiceWrapper(
		CommerceNotificationQueueEntryService
			commerceNotificationQueueEntryService) {

		_commerceNotificationQueueEntryService =
			commerceNotificationQueueEntryService;
	}

	@Override
	public void deleteCommerceNotificationQueueEntry(
			long commerceNotificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceNotificationQueueEntryService.
			deleteCommerceNotificationQueueEntry(
				commerceNotificationQueueEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry>
				getCommerceNotificationQueueEntries(
					long groupId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.notification.model.
							CommerceNotificationQueueEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationQueueEntryService.
			getCommerceNotificationQueueEntries(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationQueueEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationQueueEntryService.
			getCommerceNotificationQueueEntriesCount(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceNotificationQueueEntryService.
			getOSGiServiceIdentifier();
	}

	@Override
	public
		com.liferay.commerce.notification.model.CommerceNotificationQueueEntry
				resendCommerceNotificationQueueEntry(
					long commerceNotificationQueueEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationQueueEntryService.
			resendCommerceNotificationQueueEntry(
				commerceNotificationQueueEntryId);
	}

	@Override
	public CommerceNotificationQueueEntryService getWrappedService() {
		return _commerceNotificationQueueEntryService;
	}

	@Override
	public void setWrappedService(
		CommerceNotificationQueueEntryService
			commerceNotificationQueueEntryService) {

		_commerceNotificationQueueEntryService =
			commerceNotificationQueueEntryService;
	}

	private CommerceNotificationQueueEntryService
		_commerceNotificationQueueEntryService;

}