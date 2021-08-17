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

import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = ServletContextUtil.class
)
public class ServletContextUtil {

	public static CommerceChannelLocalService getCommerceChannelLocalService() {
		return _servletContextUtil._getCommerceChannelLocalService();
	}

	public static CommerceInventoryEngine getCommerceInventoryEngine() {
		return _servletContextUtil._getCommerceInventoryEngine();
	}

	public static CommerceOrderHttpHelper getCommerceOrderHttpHelper() {
		return _servletContextUtil._getCommerceOrderHttpHelper();
	}

	public static CommerceOrderItemLocalService
		getCommerceOrderItemLocalService() {

		return _servletContextUtil._getCommerceOrderItemLocalService();
	}

	public static CommerceOrderTypeLocalService
		getCommerceOrderTypeLocalService() {

		return _servletContextUtil._getCommerceOrderTypeLocalService();
	}

	public static CommerceProductPriceCalculation
		getCommerceProductPriceCalculation() {

		return _servletContextUtil._getCommerceProductPriceCalculation();
	}

	public static ConfigurationProvider getConfigurationProvider() {
		return _servletContextUtil._getConfigurationProvider();
	}

	public static CPCompareHelper getCPCompareHelper() {
		return _servletContextUtil._getCPCompareHelper();
	}

	public static CPContentHelper getCPContentHelper() {
		return _servletContextUtil._getCPContentHelper();
	}

	public static CPDefinitionHelper getCPDefinitionHelper() {
		return _servletContextUtil._getCPDefinitionHelper();
	}

	public static CPInstanceHelper getCPInstanceHelper() {
		return _servletContextUtil._getCPInstanceHelper();
	}

	public static CPSubscriptionTypeRegistry getCPSubscriptionTypeRegistry() {
		return _servletContextUtil._getCPSubscriptionTypeRegistry();
	}

	public static NPMResolver getNPMResolver() {
		return _servletContextUtil._getNPMResolver();
	}

	public static ProductHelper getProductHelper() {
		return _servletContextUtil._getProductHelper();
	}

	public static ServletContext getServletContext() {
		return _servletContextUtil._getServletContext();
	}

	@Reference(unbind = "-")
	public void setCommerceInventoryEngine(
		CommerceInventoryEngine commerceInventoryEngine) {

		_commerceInventoryEngine = commerceInventoryEngine;
	}

	@Reference(unbind = "-")
	public void setCommerceOrderItemLocalService(
		CommerceOrderItemLocalService commerceOrderItemLocalService) {

		_commerceOrderItemLocalService = commerceOrderItemLocalService;
	}

	@Reference(unbind = "-")
	public void setCommerceOrderTypeLocalService(
		CommerceOrderTypeLocalService commerceOrderTypeLocalService) {

		_commerceOrderTypeLocalService = commerceOrderTypeLocalService;
	}

	@Activate
	protected void activate() {
		_servletContextUtil = this;
	}

	@Deactivate
	protected void deactivate() {
		_servletContextUtil = null;
	}

	@Reference(unbind = "-")
	protected void setCommerceChannelLocalService(
		CommerceChannelLocalService commerceChannelLocalService) {

		_commerceChannelLocalService = commerceChannelLocalService;
	}

	@Reference(unbind = "-")
	protected void setCommerceOrderHttpHelper(
		CommerceOrderHttpHelper commerceOrderHttpHelper) {

		_commerceOrderHttpHelper = commerceOrderHttpHelper;
	}

	@Reference(unbind = "-")
	protected void setCommerceProductPriceCalculation(
		CommerceProductPriceCalculation commerceProductPriceCalculation) {

		_commerceProductPriceCalculation = commerceProductPriceCalculation;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setCPCompareHelper(CPCompareHelper cpCompareHelper) {
		_cpCompareHelper = cpCompareHelper;
	}

	@Reference(unbind = "-")
	protected void setCPContentHelper(CPContentHelper cpContentHelper) {
		_cpContentHelper = cpContentHelper;
	}

	@Reference(unbind = "-")
	protected void setCPDefinitionHelper(
		CPDefinitionHelper cpDefinitionHelper) {

		_cpDefinitionHelper = cpDefinitionHelper;
	}

	@Reference(unbind = "-")
	protected void setCPInstanceHelper(CPInstanceHelper cpInstanceHelper) {
		_cpInstanceHelper = cpInstanceHelper;
	}

	@Reference(unbind = "-")
	protected void setCPSubscriptionTypeRegistry(
		CPSubscriptionTypeRegistry cpSubscriptionTypeRegistry) {

		_cpSubscriptionTypeRegistry = cpSubscriptionTypeRegistry;
	}

	@Reference(unbind = "-")
	protected void setNPMResolver(NPMResolver npmResolver) {
		_npmResolver = npmResolver;
	}

	@Reference(unbind = "-")
	protected void setProductHelper(ProductHelper productHelper) {
		_productHelper = productHelper;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.frontend.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private CommerceChannelLocalService _getCommerceChannelLocalService() {
		return _commerceChannelLocalService;
	}

	private CommerceInventoryEngine _getCommerceInventoryEngine() {
		return _commerceInventoryEngine;
	}

	private CommerceOrderHttpHelper _getCommerceOrderHttpHelper() {
		return _commerceOrderHttpHelper;
	}

	private CommerceOrderItemLocalService _getCommerceOrderItemLocalService() {
		return _commerceOrderItemLocalService;
	}

	private CommerceOrderTypeLocalService _getCommerceOrderTypeLocalService() {
		return _commerceOrderTypeLocalService;
	}

	private CommerceProductPriceCalculation
		_getCommerceProductPriceCalculation() {

		return _commerceProductPriceCalculation;
	}

	private ConfigurationProvider _getConfigurationProvider() {
		return _configurationProvider;
	}

	private CPCompareHelper _getCPCompareHelper() {
		return _cpCompareHelper;
	}

	private CPContentHelper _getCPContentHelper() {
		return _cpContentHelper;
	}

	private CPDefinitionHelper _getCPDefinitionHelper() {
		return _cpDefinitionHelper;
	}

	private CPInstanceHelper _getCPInstanceHelper() {
		return _cpInstanceHelper;
	}

	private CPSubscriptionTypeRegistry _getCPSubscriptionTypeRegistry() {
		return _cpSubscriptionTypeRegistry;
	}

	private NPMResolver _getNPMResolver() {
		return _npmResolver;
	}

	private ProductHelper _getProductHelper() {
		return _productHelper;
	}

	private ServletContext _getServletContext() {
		return _servletContext;
	}

	private static ServletContextUtil _servletContextUtil;

	private CommerceChannelLocalService _commerceChannelLocalService;
	private CommerceInventoryEngine _commerceInventoryEngine;
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;
	private ConfigurationProvider _configurationProvider;
	private CPCompareHelper _cpCompareHelper;
	private CPContentHelper _cpContentHelper;
	private CPDefinitionHelper _cpDefinitionHelper;
	private CPInstanceHelper _cpInstanceHelper;
	private CPSubscriptionTypeRegistry _cpSubscriptionTypeRegistry;
	private NPMResolver _npmResolver;
	private ProductHelper _productHelper;
	private ServletContext _servletContext;

}