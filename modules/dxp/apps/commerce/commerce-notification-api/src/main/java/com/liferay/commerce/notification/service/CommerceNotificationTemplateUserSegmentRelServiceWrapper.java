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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceNotificationTemplateUserSegmentRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRelService
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelServiceWrapper
	implements CommerceNotificationTemplateUserSegmentRelService,
		ServiceWrapper<CommerceNotificationTemplateUserSegmentRelService> {
	public CommerceNotificationTemplateUserSegmentRelServiceWrapper(
		CommerceNotificationTemplateUserSegmentRelService commerceNotificationTemplateUserSegmentRelService) {
		_commerceNotificationTemplateUserSegmentRelService = commerceNotificationTemplateUserSegmentRelService;
	}

	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel addCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateUserSegmentRelService.addCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateId,
			commerceUserSegmentEntryId, serviceContext);
	}

	@Override
	public void deleteCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceNotificationTemplateUserSegmentRelService.deleteCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRelId);
	}

	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel fetchCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateUserSegmentRelService.fetchCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateId,
			commerceUserSegmentEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> getCommerceNotificationTemplateUserSegmentRels(
		long commerceNotificationTemplateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateUserSegmentRelService.getCommerceNotificationTemplateUserSegmentRels(commerceNotificationTemplateId,
			start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceNotificationTemplateUserSegmentRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceNotificationTemplateUserSegmentRelService getWrappedService() {
		return _commerceNotificationTemplateUserSegmentRelService;
	}

	@Override
	public void setWrappedService(
		CommerceNotificationTemplateUserSegmentRelService commerceNotificationTemplateUserSegmentRelService) {
		_commerceNotificationTemplateUserSegmentRelService = commerceNotificationTemplateUserSegmentRelService;
	}

	private CommerceNotificationTemplateUserSegmentRelService _commerceNotificationTemplateUserSegmentRelService;
}