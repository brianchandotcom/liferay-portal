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

package com.liferay.commerce.taglib.servlet.taglib.internal.servlet;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.util.CommerceWorkflowedModelHelper;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import javax.servlet.ServletContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
public class ServletContextUtil {

	public static CommerceWorkflowedModelHelper getCommerceOrderHelper() {
		return _commerceWorkflowedModelHelperSnapshot.get();
	}

	public static ModelResourcePermission<CommerceOrder>
		getCommerceOrderModelResourcePermission() {

		return _commerceOrderModelResourcePermissionSnapshot.get();
	}

	public static CommerceOrderStatusRegistry getCommerceOrderStatusRegistry() {
		return _commerceOrderStatusRegistrySnapshot.get();
	}

	public static CommerceOrderValidatorRegistry
		getCommerceOrderValidatorRegistry() {

		return _commerceOrderValidatorRegistrySnapshot.get();
	}

	public static CommercePriceFormatter getCommercePriceFormatter() {
		return _commercePriceFormatterSnapshot.get();
	}

	public static CommercePriceListLocalService
		getCommercePriceListLocalService() {

		return _commercePriceListLocalServiceSnapshot.get();
	}

	public static ConfigurationProvider getConfigurationProvider() {
		return _configurationProviderSnapshot.get();
	}

	public static PanelAppRegistry getPanelAppRegistry() {
		return _panelAppRegistrySnapshot.get();
	}

	public static PanelCategoryRegistry getPanelCategoryRegistry() {
		return _panelCategoryRegistrySnapshot.get();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	private static final Snapshot<ModelResourcePermission<CommerceOrder>>
		_commerceOrderModelResourcePermissionSnapshot = new Snapshot<>(
			ServletContextUtil.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.commerce.model.CommerceOrder)");
	private static final Snapshot<CommerceOrderStatusRegistry>
		_commerceOrderStatusRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, CommerceOrderStatusRegistry.class);
	private static final Snapshot<CommerceOrderValidatorRegistry>
		_commerceOrderValidatorRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, CommerceOrderValidatorRegistry.class);
	private static final Snapshot<CommercePriceFormatter>
		_commercePriceFormatterSnapshot = new Snapshot<>(
			ServletContextUtil.class, CommercePriceFormatter.class);
	private static final Snapshot<CommercePriceListLocalService>
		_commercePriceListLocalServiceSnapshot = new Snapshot<>(
			ServletContextUtil.class, CommercePriceListLocalService.class);
	private static final Snapshot<CommerceWorkflowedModelHelper>
		_commerceWorkflowedModelHelperSnapshot = new Snapshot<>(
			ServletContextUtil.class, CommerceWorkflowedModelHelper.class);
	private static final Snapshot<ConfigurationProvider>
		_configurationProviderSnapshot = new Snapshot<>(
			ServletContextUtil.class, ConfigurationProvider.class);
	private static final Snapshot<PanelAppRegistry> _panelAppRegistrySnapshot =
		new Snapshot<>(ServletContextUtil.class, PanelAppRegistry.class);
	private static final Snapshot<PanelCategoryRegistry>
		_panelCategoryRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, PanelCategoryRegistry.class);
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.commerce.taglib)");

}