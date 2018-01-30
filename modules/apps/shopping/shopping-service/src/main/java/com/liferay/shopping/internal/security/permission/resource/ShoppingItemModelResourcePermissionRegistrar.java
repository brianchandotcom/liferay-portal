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

package com.liferay.shopping.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.resource.DynamicInheritancePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.util.PropsValues;
import com.liferay.shopping.constants.ShoppingConstants;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingCategoryConstants;
import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.service.ShoppingItemLocalService;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true)
public class ShoppingItemModelResourcePermissionRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("model.class.name", ShoppingItem.class.getName());

		_serviceRegistration = bundleContext.registerService(
			ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				ShoppingItem.class, ShoppingItem::getItemId,
				_shoppingItemLocalService::getShoppingItem,
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> {
					if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
						consumer.accept(
							new DynamicInheritancePermissionLogic<>(
								_shoppingCategoryModelResourcePermission,
								item -> {
									if (ShoppingCategoryConstants.
											DEFAULT_PARENT_CATEGORY_ID ==
												item.getCategoryId()) {

										return null;
									}

									return item.getCategory();
								},
								false));
					}
				}),
			properties);
	}

	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference(
		target = "(resource.name=" + ShoppingConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

	@Reference(
		target = "(model.class.name=com.liferay.shopping.model.ShoppingCategory)"
	)
	private ModelResourcePermission<ShoppingCategory>
		_shoppingCategoryModelResourcePermission;

	@Reference
	private ShoppingItemLocalService _shoppingItemLocalService;

}