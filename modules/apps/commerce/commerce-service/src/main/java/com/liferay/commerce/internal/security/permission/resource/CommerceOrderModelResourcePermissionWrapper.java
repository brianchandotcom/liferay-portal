/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
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
						_accountEntryLocalService, _commerceChannelLocalService,
						_configurationProvider, _groupLocalService,
						_portletResourcePermission, _userGroupRoleLocalService,
						_workflowDefinitionLinkLocalService));
			});
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

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
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowPermission _workflowPermission;

}