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
			String name, String description, String cc, String ccn, String type,
			boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceNotificationActionKeys.ADD_COMMERCE_NOTIFICATION_TEMPLATE);

		return commerceNotificationTemplateLocalService.
			addCommerceNotificationTemplate(
				name, description, cc, ccn, type, enabled, subjectMap, bodyMap,
				serviceContext);
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
			long groupId, int start, int end,
			OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceNotificationActionKeys.
				VIEW_COMMERCE_NOTIFICATION_TEMPLATES);

		return commerceNotificationTemplateLocalService.
			getCommerceNotificationTemplates(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationTemplatesCount(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceNotificationActionKeys.
				VIEW_COMMERCE_NOTIFICATION_TEMPLATES);

		return commerceNotificationTemplateLocalService.
			getCommerceNotificationTemplatesCount(groupId);
	}

	@Override
	public CommerceNotificationTemplate updateCommerceNotificationTemplate(
			long commerceNotificationTemplateId, String name,
			String description, String cc, String ccn, String type,
			boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap, ServiceContext serviceContext)
		throws PortalException {

		_commerceNotificationTemplateResourcePermission.check(
			getPermissionChecker(), commerceNotificationTemplateId,
			ActionKeys.UPDATE);

		return commerceNotificationTemplateLocalService.
			updateCommerceNotificationTemplate(
				commerceNotificationTemplateId, name, description, cc, ccn,
				type, enabled, subjectMap, bodyMap, serviceContext);
	}

	private static volatile
		ModelResourcePermission<CommerceNotificationTemplate>
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