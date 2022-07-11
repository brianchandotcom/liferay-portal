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

package com.liferay.commerce.notification.internal.security.permission.resource;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalService;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.notification.model.CommerceNotificationTemplate",
	service = ModelResourcePermission.class
)
public class CommerceNotificationTemplateModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<CommerceNotificationTemplate> {

	@Override
	protected ModelResourcePermission<CommerceNotificationTemplate>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			CommerceNotificationTemplate.class,
			CommerceNotificationTemplate::getCommerceNotificationTemplateId,
			_commerceNotificationTemplateLocalService::
				getCommerceNotificationTemplate,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new StagedModelPermissionLogic<>(
					_stagingPermission, CPPortletKeys.COMMERCE_CHANNELS,
					CommerceNotificationTemplate::
						getCommerceNotificationTemplateId)));
	}

	@Reference
	private CommerceNotificationTemplateLocalService
		_commerceNotificationTemplateLocalService;

	@Reference(
		target = "(resource.name=" + CPConstants.RESOURCE_NAME_CHANNEL + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StagingPermission _stagingPermission;

}