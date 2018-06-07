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
 * Provides a wrapper for {@link CommerceNotificationTemplateService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateService
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateServiceWrapper
	implements CommerceNotificationTemplateService,
		ServiceWrapper<CommerceNotificationTemplateService> {
	public CommerceNotificationTemplateServiceWrapper(
		CommerceNotificationTemplateService commerceNotificationTemplateService) {
		_commerceNotificationTemplateService = commerceNotificationTemplateService;
	}

	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationTemplate addCommerceNotificationTemplate(
		String name, String description, String from,
		java.util.Map<java.util.Locale, String> fromNameMap, String cc,
		String bcc, String type, boolean enabled,
		java.util.Map<java.util.Locale, String> subjectMap,
		java.util.Map<java.util.Locale, String> bodyMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateService.addCommerceNotificationTemplate(name,
			description, from, fromNameMap, cc, bcc, type, enabled, subjectMap,
			bodyMap, serviceContext);
	}

	@Override
	public void deleteCommerceNotificationTemplate(
		long commerceNotificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceNotificationTemplateService.deleteCommerceNotificationTemplate(commerceNotificationTemplateId);
	}

	@Override
	public com.liferay.commerce.notification.model.CommerceNotificationTemplate getCommerceNotificationTemplate(
		long commerceNotificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateService.getCommerceNotificationTemplate(commerceNotificationTemplateId);
	}

	@Override
	public java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplate> getCommerceNotificationTemplates(
		long groupId, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateService.getCommerceNotificationTemplates(groupId,
			enabled, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplate> getCommerceNotificationTemplates(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateService.getCommerceNotificationTemplates(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationTemplatesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateService.getCommerceNotificationTemplatesCount(groupId);
	}

	@Override
	public int getCommerceNotificationTemplatesCount(long groupId,
		boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateService.getCommerceNotificationTemplatesCount(groupId,
			enabled);
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
	public com.liferay.commerce.notification.model.CommerceNotificationTemplate updateCommerceNotificationTemplate(
		long commerceNotificationTemplateId, String name, String description,
		String from, java.util.Map<java.util.Locale, String> fromNameMap,
		String cc, String bcc, String type, boolean enabled,
		java.util.Map<java.util.Locale, String> subjectMap,
		java.util.Map<java.util.Locale, String> bodyMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceNotificationTemplateService.updateCommerceNotificationTemplate(commerceNotificationTemplateId,
			name, description, from, fromNameMap, cc, bcc, type, enabled,
			subjectMap, bodyMap, serviceContext);
	}

	@Override
	public CommerceNotificationTemplateService getWrappedService() {
		return _commerceNotificationTemplateService;
	}

	@Override
	public void setWrappedService(
		CommerceNotificationTemplateService commerceNotificationTemplateService) {
		_commerceNotificationTemplateService = commerceNotificationTemplateService;
	}

	private CommerceNotificationTemplateService _commerceNotificationTemplateService;
}