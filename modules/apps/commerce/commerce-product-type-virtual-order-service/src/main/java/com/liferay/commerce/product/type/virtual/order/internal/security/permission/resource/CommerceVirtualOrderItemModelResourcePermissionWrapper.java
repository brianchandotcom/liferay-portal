/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.internal.security.permission.resource;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.type.virtual.order.constants.CommerceVirtualOrderConstants;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem",
	service = ModelResourcePermission.class
)
public class CommerceVirtualOrderItemModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<CommerceVirtualOrderItem> {

	@Override
	protected ModelResourcePermission<CommerceVirtualOrderItem>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			CommerceVirtualOrderItem.class,
			CommerceVirtualOrderItem::getCommerceVirtualOrderItemId,
			_commerceVirtualOrderItemLocalService::getCommerceVirtualOrderItem,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new CommerceVirtualOrderItemModelResourcePermissionLogic(
					_commerceOrderModelResourcePermission)));
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)",
		unbind = "-"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference
	private CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;

	@Reference(
		target = "(resource.name=" + CommerceVirtualOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}