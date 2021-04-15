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
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.base.CommerceNotificationTemplateServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationTemplateServiceImpl
	extends CommerceNotificationTemplateServiceBaseImpl {

	@Override
	public CommerceNotificationTemplate addCommerceNotificationTemplate(
			long userId, long groupId, String name, String description,
			String from, Map<Locale, String> fromNameMap, String to, String cc,
			String bcc, String type, boolean enabled,
			Map<Locale, String> subjectMap, Map<Locale, String> bodyMap,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceNotificationActionKeys.ADD_COMMERCE_NOTIFICATION_TEMPLATE);

		return commerceNotificationTemplateLocalService.
			addCommerceNotificationTemplate(
				userId, groupId, name, description, from, fromNameMap, to, cc,
				bcc, type, enabled, subjectMap, bodyMap, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceNotificationTemplate addCommerceNotificationTemplate(
			String name, String description, String from,
			Map<Locale, String> fromNameMap, String to, String cc, String bcc,
			String type, boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException {

		return commerceNotificationTemplateService.
			addCommerceNotificationTemplate(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				name, description, from, fromNameMap, to, cc, bcc, type,
				enabled, subjectMap, bodyMap, serviceContext);
	}

	@Override
	public void deleteCommerceNotificationTemplate(
			long commerceNotificationTemplateId)
		throws PortalException {

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(), commerceNotificationTemplateId,
			ActionKeys.DELETE);

		commerceNotificationTemplateLocalService.
			deleteCommerceNotificationTemplate(commerceNotificationTemplateId);
	}

	@Override
	public CommerceNotificationTemplate getCommerceNotificationTemplate(
			long commerceNotificationTemplateId)
		throws PortalException {

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(), commerceNotificationTemplateId,
			ActionKeys.VIEW);

		return commerceNotificationTemplateLocalService.
			getCommerceNotificationTemplate(commerceNotificationTemplateId);
	}

	@Override
	public List<CommerceNotificationTemplate> getCommerceNotificationTemplates(
			long groupId, boolean enabled, int start, int end,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws PortalException {

		return commerceNotificationTemplatePersistence.filterFindByG_E(
			groupId, enabled, start, end, orderByComparator);
	}

	@Override
	public List<CommerceNotificationTemplate> getCommerceNotificationTemplates(
			long groupId, int start, int end,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws PortalException {

		return commerceNotificationTemplatePersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationTemplatesCount(long groupId)
		throws PortalException {

		return commerceNotificationTemplatePersistence.filterCountByGroupId(
			groupId);
	}

	@Override
	public int getCommerceNotificationTemplatesCount(
			long groupId, boolean enabled)
		throws PortalException {

		return commerceNotificationTemplatePersistence.filterCountByG_E(
			groupId, enabled);
	}

	@Override
	public CommerceNotificationTemplate updateCommerceNotificationTemplate(
			long commerceNotificationTemplateId, String name,
			String description, String from, Map<Locale, String> fromNameMap,
			String to, String cc, String bcc, String type, boolean enabled,
			Map<Locale, String> subjectMap, Map<Locale, String> bodyMap,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(), commerceNotificationTemplateId,
			ActionKeys.UPDATE);

		return commerceNotificationTemplateLocalService.
			updateCommerceNotificationTemplate(
				commerceNotificationTemplateId, name, description, from,
				fromNameMap, to, cc, bcc, type, enabled, subjectMap, bodyMap,
				serviceContext);
	}

	private static volatile ModelResourcePermission
		<CommerceNotificationTemplate>
			_commerceNotificationTemplateResourcePermission =
				ModelResourcePermissionFactory.getInstance(
					CommerceNotificationTemplateServiceImpl.class,
					"_commerceNotificationTemplateResourcePermission",
					CommerceNotificationTemplate.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceNotificationTemplateServiceImpl.class,
				"_portletResourcePermission",
				CommerceNotificationConstants.RESOURCE_NAME);

}