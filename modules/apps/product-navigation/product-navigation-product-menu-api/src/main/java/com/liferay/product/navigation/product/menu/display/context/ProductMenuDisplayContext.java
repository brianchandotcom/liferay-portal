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

package com.liferay.product.navigation.product.menu.display.context;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.global.menu.configuration.GlobalMenuInstanceConfiguration;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Julio Camarero
 */
public class ProductMenuDisplayContext {

	public ProductMenuDisplayContext(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;

		_panelAppRegistry = (PanelAppRegistry)_portletRequest.getAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY);
		_panelCategoryHelper =
			(PanelCategoryHelper)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_HELPER);
		_panelCategoryRegistry =
			(PanelCategoryRegistry)_portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);
		_themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<PanelCategory> getChildPanelCategories() {
		if (_childPanelCategories != null) {
			return _childPanelCategories;
		}

		_childPanelCategories = _panelCategoryRegistry.getChildPanelCategories(
			PanelCategoryKeys.ROOT, _themeDisplay.getPermissionChecker(),
			_themeDisplay.getScopeGroup());

		if (_isEnableGlobalMenu()) {
			return _childPanelCategories;
		}

		_childPanelCategories.addAll(
			0,
			_panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.GLOBAL_MENU,
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup()));

		return _childPanelCategories;
	}

	public int getNotificationsCount(PanelCategory panelCategory) {
		return _panelCategoryHelper.getNotificationsCount(
			panelCategory.getKey(), _themeDisplay.getPermissionChecker(),
			_themeDisplay.getScopeGroup(), _themeDisplay.getUser());
	}

	public String getRootPanelCategoryKey() {
		if (_rootPanelCategoryKey != null) {
			return _rootPanelCategoryKey;
		}

		_rootPanelCategoryKey = StringPool.BLANK;

		List<PanelCategory> childPanelCategories = getChildPanelCategories();

		if (!childPanelCategories.isEmpty()) {
			PanelCategory lastChildPanelCategory = childPanelCategories.get(
				childPanelCategories.size() - 1);

			_rootPanelCategoryKey = lastChildPanelCategory.getKey();

			if (Validator.isNotNull(_themeDisplay.getPpid())) {
				PanelCategoryHelper panelCategoryHelper =
					new PanelCategoryHelper(
						_panelAppRegistry, _panelCategoryRegistry);

				for (PanelCategory panelCategory :
						_panelCategoryRegistry.getChildPanelCategories(
							PanelCategoryKeys.ROOT)) {

					if (panelCategoryHelper.containsPortlet(
							_themeDisplay.getPpid(), panelCategory.getKey(),
							_themeDisplay.getPermissionChecker(),
							_themeDisplay.getScopeGroup())) {

						_rootPanelCategoryKey = panelCategory.getKey();

						return _rootPanelCategoryKey;
					}
				}

				if (_isEnableGlobalMenu()) {
					return _rootPanelCategoryKey;
				}

				for (PanelCategory panelCategory :
						_panelCategoryRegistry.getChildPanelCategories(
							PanelCategoryKeys.GLOBAL_MENU)) {

					if (panelCategoryHelper.containsPortlet(
							_themeDisplay.getPpid(), panelCategory.getKey(),
							_themeDisplay.getPermissionChecker(),
							_themeDisplay.getScopeGroup())) {

						_rootPanelCategoryKey = panelCategory.getKey();

						return _rootPanelCategoryKey;
					}
				}
			}
		}

		return _rootPanelCategoryKey;
	}

	public boolean hasUserPanelCategory() {
		List<PanelCategory> panelCategories = getChildPanelCategories();

		for (PanelCategory panelCategory : panelCategories) {
			String panelCategoryKey = panelCategory.getKey();

			if (panelCategoryKey.equals(PanelCategoryKeys.USER)) {
				return true;
			}
		}

		return false;
	}

	public boolean isShowProductMenu() {
		Layout layout = _themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return true;
		}

		List<PanelCategory> childPanelCategories = getChildPanelCategories();

		if (childPanelCategories.isEmpty()) {
			return false;
		}

		return true;
	}

	private boolean _isEnableGlobalMenu() {
		if (_enableGlobalMenu != null) {
			return _enableGlobalMenu;
		}

		_enableGlobalMenu = false;

		try {
			GlobalMenuInstanceConfiguration globalMenuInstanceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					GlobalMenuInstanceConfiguration.class,
					_themeDisplay.getCompanyId());

			_enableGlobalMenu =
				globalMenuInstanceConfiguration.enableGlobalMenu();

			return _enableGlobalMenu;
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get global menu instance configuration",
					configurationException);
			}
		}

		return _enableGlobalMenu;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductMenuDisplayContext.class);

	private List<PanelCategory> _childPanelCategories;
	private Boolean _enableGlobalMenu;
	private final PanelAppRegistry _panelAppRegistry;
	private final PanelCategoryHelper _panelCategoryHelper;
	private final PanelCategoryRegistry _panelCategoryRegistry;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private String _rootPanelCategoryKey;
	private final ThemeDisplay _themeDisplay;

}