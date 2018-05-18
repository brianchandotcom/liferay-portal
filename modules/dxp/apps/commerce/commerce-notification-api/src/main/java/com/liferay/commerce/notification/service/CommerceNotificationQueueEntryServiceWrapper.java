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

package com.liferay.commerce.notification.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceNotificationQueueEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryService
 * @generated
 */
@ProviderType
public class CommerceNotificationQueueEntryServiceWrapper
	implements CommerceNotificationQueueEntryService,
		ServiceWrapper<CommerceNotificationQueueEntryService> {
	public CommerceNotificationQueueEntryServiceWrapper(
		CommerceNotificationQueueEntryService commerceNotificationQueueEntryService) {
		_commerceNotificationQueueEntryService = commerceNotificationQueueEntryService;
	}

	@Override
	public void deleteCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceNotificationQueueEntryService.deleteCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry> getCommerceNotificationQueueEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationQueueEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationQueueEntryService.getCommerceNotificationQueueEntries(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationQueueEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationQueueEntryService.getCommerceNotificationQueueEntriesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceNotificationQueueEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationQueueEntry resendCommerceNotificationQueueEntry(
		long commerceNotificationQueueEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationQueueEntryService.resendCommerceNotificationQueueEntry(commerceNotificationQueueEntryId);
	}

	@Override
	public CommerceNotificationQueueEntryService getWrappedService() {
		return _commerceNotificationQueueEntryService;
	}

	@Override
	public void setWrappedService(
		CommerceNotificationQueueEntryService commerceNotificationQueueEntryService) {
		_commerceNotificationQueueEntryService = commerceNotificationQueueEntryService;
	}

	private CommerceNotificationQueueEntryService _commerceNotificationQueueEntryService;
}