/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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