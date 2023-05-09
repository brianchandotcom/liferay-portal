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
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(service = {})
public class ServletContextUtil {

	public static CommerceWorkflowedModelHelper getCommerceOrderHelper() {
		return _servletContextUtil._getCommerceOrderHelper();
	}

	public static ModelResourcePermission<CommerceOrder>
		getCommerceOrderModelResourcePermission() {

		return _servletContextUtil._getCommerceOrderModelResourcePermission();
	}

	public static CommerceOrderStatusRegistry getCommerceOrderStatusRegistry() {
		return _servletContextUtil._getCommerceOrderStatusRegistry();
	}

	public static CommerceOrderValidatorRegistry
		getCommerceOrderValidatorRegistry() {

		return _servletContextUtil._getCommerceOrderValidatorRegistry();
	}

	public static CommercePriceFormatter getCommercePriceFormatter() {
		return _servletContextUtil._getCommercePriceFormatter();
	}

	public static CommercePriceListLocalService
		getCommercePriceListLocalService() {

		return _servletContextUtil._getCommercePriceListLocalService();
	}

	public static ConfigurationProvider getConfigurationProvider() {
		return _servletContextUtil._getConfigurationProvider();
	}

	public static PanelAppRegistry getPanelAppRegistry() {
		return _servletContextUtil._getPanelAppRegistry();
	}

	public static PanelCategoryRegistry getPanelCategoryRegistry() {
		return _servletContextUtil._getPanelCategoryRegistry();
	}

	public static ServletContext getServletContext() {
		return _servletContextUtil._getServletContext();
	}

	@Activate
	protected void activate() {
		_servletContextUtil = this;
	}

	@Deactivate
	protected void deactivate() {
		_servletContextUtil = null;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)",
		unbind = "-"
	)
	protected void setCommerceOrderModelResourcePermission(
		ModelResourcePermission<CommerceOrder>
			commerceOrderModelResourcePermission) {

		_commerceOrderModelResourcePermission =
			commerceOrderModelResourcePermission;
	}

	@Reference(unbind = "-")
	protected void setCommerceOrderStatusRegistry(
		CommerceOrderStatusRegistry commerceOrderStatusRegistry) {

		_commerceOrderStatusRegistry = commerceOrderStatusRegistry;
	}

	@Reference(unbind = "-")
	protected void setCommerceOrderValidatorRegistry(
		CommerceOrderValidatorRegistry commerceOrderValidatorRegistry) {

		_commerceOrderValidatorRegistry = commerceOrderValidatorRegistry;
	}

	@Reference(unbind = "-")
	protected void setCommercePriceFormatter(
		CommercePriceFormatter commercePriceFormatter) {

		_commercePriceFormatter = commercePriceFormatter;
	}

	@Reference(unbind = "-")
	protected void setCommercePriceListLocalService(
		CommercePriceListLocalService commercePriceListLocalService) {

		_commercePriceListLocalService = commercePriceListLocalService;
	}

	@Reference(unbind = "-")
	protected void setCommerceWorkflowedModelHelper(
		CommerceWorkflowedModelHelper commerceWorkflowedModelHelper) {

		_commerceWorkflowedModelHelper = commerceWorkflowedModelHelper;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private CommerceWorkflowedModelHelper _getCommerceOrderHelper() {
		return _commerceWorkflowedModelHelper;
	}

	private ModelResourcePermission<CommerceOrder>
		_getCommerceOrderModelResourcePermission() {

		return _commerceOrderModelResourcePermission;
	}

	private CommerceOrderStatusRegistry _getCommerceOrderStatusRegistry() {
		return _commerceOrderStatusRegistry;
	}

	private CommerceOrderValidatorRegistry
		_getCommerceOrderValidatorRegistry() {

		return _commerceOrderValidatorRegistry;
	}

	private CommercePriceFormatter _getCommercePriceFormatter() {
		return _commercePriceFormatter;
	}

	private CommercePriceListLocalService _getCommercePriceListLocalService() {
		return _commercePriceListLocalService;
	}

	private ConfigurationProvider _getConfigurationProvider() {
		return _configurationProvider;
	}

	private PanelAppRegistry _getPanelAppRegistry() {
		return _panelAppRegistry;
	}

	private PanelCategoryRegistry _getPanelCategoryRegistry() {
		return _panelCategoryRegistry;
	}

	private ServletContext _getServletContext() {
		return _servletContext;
	}

	private static ServletContextUtil _servletContextUtil;

	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;
	private CommercePriceFormatter _commercePriceFormatter;
	private CommercePriceListLocalService _commercePriceListLocalService;
	private CommerceWorkflowedModelHelper _commerceWorkflowedModelHelper;
	private ConfigurationProvider _configurationProvider;
	private PanelAppRegistry _panelAppRegistry;
	private PanelCategoryRegistry _panelCategoryRegistry;
	private ServletContext _servletContext;

}