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
 * Provides a wrapper for {@link CommerceNotificationTemplateService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateService
 * @generated
 */
public class CommerceNotificationTemplateServiceWrapper
	implements CommerceNotificationTemplateService,
			   ServiceWrapper<CommerceNotificationTemplateService> {

	public CommerceNotificationTemplateServiceWrapper(
		CommerceNotificationTemplateService
			commerceNotificationTemplateService) {

		_commerceNotificationTemplateService =
			commerceNotificationTemplateService;
	}

	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationTemplate
			addCommerceNotificationTemplate(
				long groupId, String name, String description, String from,
				java.util.Map<java.util.Locale, String> fromNameMap, String to,
				String cc, String bcc, String type, boolean enabled,
				java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> bodyMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateService.
			addCommerceNotificationTemplate(
				groupId, name, description, from, fromNameMap, to, cc, bcc,
				type, enabled, subjectMap, bodyMap, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationTemplate
			addCommerceNotificationTemplate(
				String name, String description, String from,
				java.util.Map<java.util.Locale, String> fromNameMap, String to,
				String cc, String bcc, String type, boolean enabled,
				java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> bodyMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateService.
			addCommerceNotificationTemplate(
				name, description, from, fromNameMap, to, cc, bcc, type,
				enabled, subjectMap, bodyMap, serviceContext);
	}

	@Override
	public void deleteCommerceNotificationTemplate(
			long commerceNotificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceNotificationTemplateService.deleteCommerceNotificationTemplate(
			commerceNotificationTemplateId);
	}

	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationTemplate
			getCommerceNotificationTemplate(long commerceNotificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateService.
			getCommerceNotificationTemplate(commerceNotificationTemplateId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.notification.model.CommerceNotificationTemplate>
				getCommerceNotificationTemplates(
					long groupId, boolean enabled, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.notification.model.
							CommerceNotificationTemplate> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateService.
			getCommerceNotificationTemplates(
				groupId, enabled, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.notification.model.CommerceNotificationTemplate>
				getCommerceNotificationTemplates(
					long groupId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.notification.model.
							CommerceNotificationTemplate> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateService.
			getCommerceNotificationTemplates(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationTemplatesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateService.
			getCommerceNotificationTemplatesCount(groupId);
	}

	@Override
	public int getCommerceNotificationTemplatesCount(
			long groupId, boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateService.
			getCommerceNotificationTemplatesCount(groupId, enabled);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceNotificationTemplateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationTemplate
			updateCommerceNotificationTemplate(
				long commerceNotificationTemplateId, String name,
				String description, String from,
				java.util.Map<java.util.Locale, String> fromNameMap, String to,
				String cc, String bcc, String type, boolean enabled,
				java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> bodyMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationTemplateService.
			updateCommerceNotificationTemplate(
				commerceNotificationTemplateId, name, description, from,
				fromNameMap, to, cc, bcc, type, enabled, subjectMap, bodyMap,
				serviceContext);
	}

	@Override
	public CommerceNotificationTemplateService getWrappedService() {
		return _commerceNotificationTemplateService;
	}

	@Override
	public void setWrappedService(
		CommerceNotificationTemplateService
			commerceNotificationTemplateService) {

		_commerceNotificationTemplateService =
			commerceNotificationTemplateService;
	}

	private CommerceNotificationTemplateService
		_commerceNotificationTemplateService;

}