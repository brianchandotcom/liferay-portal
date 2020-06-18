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

package com.liferay.product.navigation.product.menu.internal.helper;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.global.menu.configuration.GlobalMenuInstanceConfiguration;
import com.liferay.product.navigation.product.menu.helper.ProductNavigationProductMenuHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ProductNavigationProductMenuHelper.class)
public class ProductNavigationProductMenuHelperImpl
	implements ProductNavigationProductMenuHelper {

	@Override
	public boolean isShowProductMenu(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return false;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return false;
		}

		User user = themeDisplay.getUser();

		if (!themeDisplay.isImpersonated() && !user.isSetupComplete()) {
			return false;
		}

		boolean enableGlobalMenu = _isEnableGlobalMenu(
			themeDisplay.getCompanyId());

		if (enableGlobalMenu && _isGlobalMenuApp(themeDisplay)) {
			return false;
		}

		List<PanelCategory> childPanelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.ROOT, themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		if (!enableGlobalMenu) {
			childPanelCategories.addAll(
				_panelCategoryRegistry.getChildPanelCategories(
					PanelCategoryKeys.GLOBAL_MENU,
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroup()));
		}

		if (childPanelCategories.isEmpty()) {
			return false;
		}

		return true;
	}

	private boolean _isEnableGlobalMenu(long companyId) {
		try {
			GlobalMenuInstanceConfiguration globalMenuInstanceConfiguration =
				_configurationProvider.getCompanyConfiguration(
					GlobalMenuInstanceConfiguration.class, companyId);

			if (globalMenuInstanceConfiguration.enableGlobalMenu()) {
				return true;
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get global menu instance configuration",
					configurationException);
			}
		}

		return false;
	}

	private boolean _isGlobalMenuApp(ThemeDisplay themeDisplay) {
		if (Validator.isNull(themeDisplay.getPpid())) {
			return false;
		}

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		if (!panelCategoryHelper.isGlobalMenuApp(themeDisplay.getPpid())) {
			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout != null) && !layout.isTypeControlPanel()) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductNavigationProductMenuHelperImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

}