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

package com.liferay.product.navigation.product.menu.web.internal.servlet.taglib;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.applications.menu.configuration.ApplicationsMenuInstanceConfiguration;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import java.io.IOException;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(service = DynamicInclude.class)
public class ProductMenuBodyTopDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if ((_isApplicationsMenuApp(themeDisplay) || scopeGroup.isDepot()) &&
			_isEnableApplicationsMenu(themeDisplay.getCompanyId())) {

			return;
		}

		if (!_hasPanelCategories(themeDisplay)) {
			return;
		}

		PageContext pageContext = PageContextFactoryUtil.create(
			httpServletRequest, httpServletResponse);

		try {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write("<div class=\"");

			String productMenuState = SessionClicks.get(
				httpServletRequest,
				"com.liferay.product.navigation.product.menu." +
					"web_productMenuState",
				"closed");

			if (Objects.equals(productMenuState, "open")) {
				productMenuState += " product-menu-open";
			}

			jspWriter.write(productMenuState);

			jspWriter.write(
				" cadmin d-print-none lfr-product-menu-panel sidenav-fixed " +
					"sidenav-menu-slider\" id=\"");

			String portletNamespace = _portal.getPortletNamespace(
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU);

			jspWriter.write(portletNamespace);

			jspWriter.write("sidenavSliderId\">");
			jspWriter.write(
				"<div class=\"product-menu sidebar sidenav-menu\">");

			RuntimeTag runtimeTag = new RuntimeTag();

			runtimeTag.setPortletName(
				ProductNavigationProductMenuPortletKeys.
					PRODUCT_NAVIGATION_PRODUCT_MENU);

			runtimeTag.doTag(pageContext);

			jspWriter.write("</div></div>");
		}
		catch (Exception exception) {
			ReflectionUtil.throwException(exception);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/body_top.jsp#post");
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private boolean _hasPanelCategories(ThemeDisplay themeDisplay) {
		List<PanelCategory> childPanelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.ROOT, themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		if (!childPanelCategories.isEmpty()) {
			return true;
		}

		if (!_isEnableApplicationsMenu(themeDisplay.getCompanyId())) {
			childPanelCategories =
				_panelCategoryRegistry.getChildPanelCategories(
					PanelCategoryKeys.APPLICATIONS_MENU,
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroup());

			if (!childPanelCategories.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private boolean _isApplicationsMenuApp(ThemeDisplay themeDisplay) {
		if (Validator.isNull(themeDisplay.getPpid())) {
			return false;
		}

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		if (!panelCategoryHelper.isApplicationsMenuApp(
				themeDisplay.getPpid())) {

			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout != null) && !layout.isTypeControlPanel()) {
			return false;
		}

		return true;
	}

	private boolean _isEnableApplicationsMenu(long companyId) {
		try {
			ApplicationsMenuInstanceConfiguration
				applicationsMenuInstanceConfiguration =
					_configurationProvider.getCompanyConfiguration(
						ApplicationsMenuInstanceConfiguration.class, companyId);

			if (applicationsMenuInstanceConfiguration.
					enableApplicationsMenu()) {

				return true;
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get applications menu instance configuration",
					configurationException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductMenuBodyTopDynamicInclude.class);

	private volatile BundleContext _bundleContext;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	@Reference
	private Portal _portal;

}