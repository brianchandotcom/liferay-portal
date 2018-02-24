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

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.WorkflowedModelPermissionLogic;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommerceOrderModelResourcePermissionRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", CommerceOrder.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				CommerceOrder.class, CommerceOrder::getCommerceOrderId,
				_commerceOrderLocalService::getCommerceOrder,
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> {
					consumer.accept(
						new WorkflowedModelPermissionLogic<>(
							_workflowPermission, modelResourcePermission,
							CommerceOrder::getCommerceOrderId));

					consumer.accept(
						new CommerceOrderModelResourcePermissionLogic());
				}),
			properties);
	}

	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	@Reference
	private WorkflowPermission _workflowPermission;

	private class CommerceOrderModelResourcePermissionLogic
		implements ModelResourcePermissionLogic<CommerceOrder> {

		@Override
		public Boolean contains(
				PermissionChecker permissionChecker, String name,
				CommerceOrder commerceOrder, String actionId)
			throws PortalException {

			if (actionId.equals(ActionKeys.VIEW)) {
				return _hasViewPermission(permissionChecker, commerceOrder);
			}

			return null;
		}

		private Boolean _hasViewPermission(
			PermissionChecker permissionChecker, CommerceOrder commerceOrder) {

			long userId = permissionChecker.getUserId();

			if ((userId == commerceOrder.getUserId()) ||
				(userId == commerceOrder.getOrderUserId())) {

				return true;
			}

			long groupId = commerceOrder.getGroupId();
			long siteGroupId = commerceOrder.getSiteGroupId();

			int orderStatus = commerceOrder.getOrderStatus();

			if (orderStatus == CommerceOrderConstants.ORDER_STATUS_OPEN) {
				if (_portletResourcePermission.contains(
						permissionChecker, groupId,
						CommerceActionKeys.APPROVE_OPEN_COMMERCE_ORDERS) ||
					_portletResourcePermission.contains(
						permissionChecker, groupId,
						CommerceActionKeys.VIEW_OPEN_COMMERCE_ORDERS)) {

					return true;
				}
			}
			else {
				if (_portletResourcePermission.contains(
						permissionChecker, groupId,
						CommerceActionKeys.MANAGE_COMMERCE_ORDERS) ||
					_portletResourcePermission.contains(
						permissionChecker, siteGroupId,
						CommerceActionKeys.MANAGE_COMMERCE_ORDERS)) {

					return true;
				}
			}

			return false;
		}

	}

}