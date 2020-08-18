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

package com.liferay.product.navigation.applications.menu.web.internal.product.navigation.control.menu;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.applications.menu.configuration.ApplicationsMenuInstanceConfiguration;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=600"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class ApplicationsMenuSiteProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry {

	@Override
	public String getIconJspPath() {
		return "/applications_menu/applications_menu.jsp";
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_isEnableApplicationsMenu(themeDisplay.getCompanyId())) {
			return false;
		}

		if (_isApplicationsMenuApp(themeDisplay)) {
			return false;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			return false;
		}

		List<PanelCategory> applicationsMenuPanelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.APPLICATIONS_MENU,
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		for (PanelCategory panelCategory : applicationsMenuPanelCategories) {
			List<PanelCategory> childPanelCategories =
				_panelCategoryRegistry.getChildPanelCategories(
					panelCategory.getKey(), themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroup());

			if (!childPanelCategories.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	@Reference(
		target = "(panel.category.key=" + PanelCategoryKeys.HIDDEN + ")",
		unbind = "-"
	)
	public void setPanelCategory(PanelCategory panelCategory) {
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.applications.menu.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
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
		ApplicationsMenuSiteProductNavigationControlMenuEntry.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

}