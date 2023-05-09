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

package com.liferay.commerce.frontend.taglib.internal.servlet;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.info.item.renderer.InfoItemRendererRegistry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import javax.servlet.ServletContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class ServletContextUtil {

	public static CommerceChannelLocalService getCommerceChannelLocalService() {
		return _commerceChannelLocalServiceSnapshot.get();
	}

	public static CommerceInventoryEngine getCommerceInventoryEngine() {
		return _commerceInventoryEngineSnapshot.get();
	}

	public static CommerceOrderHttpHelper getCommerceOrderHttpHelper() {
		return _commerceOrderHttpHelperSnapshot.get();
	}

	public static CommerceOrderItemLocalService
		getCommerceOrderItemLocalService() {

		return _commerceOrderItemLocalServiceSnapshot.get();
	}

	public static PortletResourcePermission
		getCommerceOrderPortletResourcePermission() {

		return _commerceOrderPortletResourcePermissionSnapshot.get();
	}

	public static CommerceOrderTypeLocalService
		getCommerceOrderTypeLocalService() {

		return _commerceOrderTypeLocalServiceSnapshot.get();
	}

	public static ConfigurationProvider getConfigurationProvider() {
		return _configurationProviderSnapshot.get();
	}

	public static CPCompareHelper getCPCompareHelper() {
		return _cpCompareHelperSnapshot.get();
	}

	public static CPContentHelper getCPContentHelper() {
		return _cpContentHelperSnapshot.get();
	}

	public static CPDefinitionHelper getCPDefinitionHelper() {
		return _cpDefinitionHelperSnapshot.get();
	}

	public static CPFriendlyURL getCPFriendlyURL() {
		return _cpFriendlyURLSnapshot.get();
	}

	public static CPInstanceHelper getCPInstanceHelper() {
		return _cpInstanceHelperSnapshot.get();
	}

	public static CPSubscriptionTypeRegistry getCPSubscriptionTypeRegistry() {
		return _cpSubscriptionTypeRegistrySnapshot.get();
	}

	public static InfoItemRendererRegistry getInfoItemRendererRegistry() {
		return _infoItemRendererRegistrySnapshot.get();
	}

	public static NPMResolver getNPMResolver() {
		return _npmResolverSnapshot.get();
	}

	public static ProductHelper getProductHelper() {
		return _productHelperSnapshot.get();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	private static final Snapshot<CommerceChannelLocalService>
		_commerceChannelLocalServiceSnapshot = new Snapshot<>(
			ServletContextUtil.class, CommerceChannelLocalService.class);
	private static final Snapshot<CommerceInventoryEngine>
		_commerceInventoryEngineSnapshot = new Snapshot<>(
			ServletContextUtil.class, CommerceInventoryEngine.class);
	private static final Snapshot<CommerceOrderHttpHelper>
		_commerceOrderHttpHelperSnapshot = new Snapshot<>(
			ServletContextUtil.class, CommerceOrderHttpHelper.class);
	private static final Snapshot<CommerceOrderItemLocalService>
		_commerceOrderItemLocalServiceSnapshot = new Snapshot<>(
			ServletContextUtil.class, CommerceOrderItemLocalService.class);
	private static final Snapshot<PortletResourcePermission>
		_commerceOrderPortletResourcePermissionSnapshot = new Snapshot<>(
			ServletContextUtil.class, PortletResourcePermission.class,
			"(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")");
	private static final Snapshot<CommerceOrderTypeLocalService>
		_commerceOrderTypeLocalServiceSnapshot = new Snapshot<>(
			ServletContextUtil.class, CommerceOrderTypeLocalService.class);
	private static final Snapshot<ConfigurationProvider>
		_configurationProviderSnapshot = new Snapshot<>(
			ServletContextUtil.class, ConfigurationProvider.class);
	private static final Snapshot<CPCompareHelper> _cpCompareHelperSnapshot =
		new Snapshot<>(ServletContextUtil.class, CPCompareHelper.class);
	private static final Snapshot<CPContentHelper> _cpContentHelperSnapshot =
		new Snapshot<>(ServletContextUtil.class, CPContentHelper.class);
	private static final Snapshot<CPDefinitionHelper>
		_cpDefinitionHelperSnapshot = new Snapshot<>(
			ServletContextUtil.class, CPDefinitionHelper.class);
	private static final Snapshot<CPFriendlyURL> _cpFriendlyURLSnapshot =
		new Snapshot<>(ServletContextUtil.class, CPFriendlyURL.class);
	private static final Snapshot<CPInstanceHelper> _cpInstanceHelperSnapshot =
		new Snapshot<>(ServletContextUtil.class, CPInstanceHelper.class);
	private static final Snapshot<CPSubscriptionTypeRegistry>
		_cpSubscriptionTypeRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, CPSubscriptionTypeRegistry.class);
	private static final Snapshot<InfoItemRendererRegistry>
		_infoItemRendererRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, InfoItemRendererRegistry.class);
	private static final Snapshot<NPMResolver> _npmResolverSnapshot =
		new Snapshot<>(ServletContextUtil.class, NPMResolver.class);
	private static final Snapshot<ProductHelper> _productHelperSnapshot =
		new Snapshot<>(ServletContextUtil.class, ProductHelper.class);
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.commerce.frontend.taglib)");

}