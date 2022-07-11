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

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.model.CommerceOrder",
	service = ModelResourcePermission.class
)
public class CommerceOrderModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<CommerceOrder> {

	@Override
	protected ModelResourcePermission<CommerceOrder>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			CommerceOrder.class, CommerceOrder::getCommerceOrderId,
			_commerceOrderLocalService::getCommerceOrder,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> {
				consumer.accept(
					new CommerceOrderWorkflowedModelPermissionLogic(
						_workflowPermission, modelResourcePermission,
						CommerceOrder::getCommerceOrderId));

				consumer.accept(
					new CommerceOrderModelResourcePermissionLogic(
						_configurationProvider, _groupLocalService,
						_portletResourcePermission,
						_workflowDefinitionLinkLocalService));
			});
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowPermission _workflowPermission;

}