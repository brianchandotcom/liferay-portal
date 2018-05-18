/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.taglib.servlet.taglib.internal.servlet;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class ServletContextUtil {

	public static final CommerceOrderHelper getCommerceOrderHelper() {
		return _instance._getCommerceOrderHelper();
	}

	public static final ModelResourcePermission<CommerceOrder>
		getCommerceOrderModelResourcePermission() {

		return _instance._getCommerceOrderModelResourcePermission();
	}

	public static final CommercePriceCalculation getCommercePriceCalculation() {
		return _instance._getCommercePriceCalculation();
	}

	public static final PanelAppRegistry getPanelAppRegistry() {
		return _instance._getPanelAppRegistry();
	}

	public static final PanelCategoryRegistry getPanelCategoryRegistry() {
		return _instance._getPanelCategoryRegistry();
	}

	public static final ServletContext getServletContext() {
		return _instance._getServletContext();
	}

	@Activate
	protected void activate() {
		_instance = this;
	}

	@Deactivate
	protected void deactivate() {
		_instance = null;
	}

	@Reference(unbind = "-")
	protected void setCommerceOrderHelper(
		CommerceOrderHelper commerceOrderHelper) {

		_commerceOrderHelper = commerceOrderHelper;
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
	protected void setCommercePriceCalculation(
		CommercePriceCalculation commercePriceCalculation) {

		_commercePriceCalculation = commercePriceCalculation;
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

	private CommerceOrderHelper _getCommerceOrderHelper() {
		return _commerceOrderHelper;
	}

	private ModelResourcePermission<CommerceOrder>
		_getCommerceOrderModelResourcePermission() {

		return _commerceOrderModelResourcePermission;
	}

	private CommercePriceCalculation _getCommercePriceCalculation() {
		return _commercePriceCalculation;
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

	private static ServletContextUtil _instance;

	private CommerceOrderHelper _commerceOrderHelper;
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;
	private CommercePriceCalculation _commercePriceCalculation;
	private PanelAppRegistry _panelAppRegistry;
	private PanelCategoryRegistry _panelCategoryRegistry;
	private ServletContext _servletContext;

}