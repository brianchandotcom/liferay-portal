/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.product.menu.display.context;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.applications.menu.configuration.ApplicationsMenuInstanceConfiguration;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class ProductMenuDisplayContext {

	public ProductMenuDisplayContext(PortletRequest portletRequest) {
		_httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		_panelAppRegistry = (PanelAppRegistry)portletRequest.getAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY);
		_panelCategoryHelper = (PanelCategoryHelper)portletRequest.getAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER);
		_panelCategoryRegistry =
			(PanelCategoryRegistry)portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);
		_themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<PanelCategory> getChildPanelCategories() {
		if (_childPanelCategories != null) {
			return _childPanelCategories;
		}

		_childPanelCategories = _panelCategoryRegistry.getChildPanelCategories(
			PanelCategoryKeys.ROOT, _themeDisplay.getPermissionChecker(),
			_themeDisplay.getScopeGroup());

		if (_isEnableApplicationsMenu()) {
			return _childPanelCategories;
		}

		List<PanelCategory> applicationsMenuChildPanelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.APPLICATIONS_MENU,
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup());

		Collections.reverse(applicationsMenuChildPanelCategories);

		_childPanelCategories.addAll(0, applicationsMenuChildPanelCategories);

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

				if (_isEnableApplicationsMenu()) {
					return _rootPanelCategoryKey;
				}

				for (PanelCategory panelCategory :
						_panelCategoryRegistry.getChildPanelCategories(
							PanelCategoryKeys.APPLICATIONS_MENU)) {

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

	public boolean isLayoutsTreeDisabled() {
		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(_httpServletRequest);

		String ppid = ParamUtil.getString(
			_httpServletRequest, "selPpid",
			ParamUtil.getString(originalHttpServletRequest, "p_p_id"));
		String mvcRenderCommandName = ParamUtil.getString(
			originalHttpServletRequest,
			PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES) +
				"mvcRenderCommandName");
		String mvcPath = ParamUtil.getString(
			originalHttpServletRequest, "mvcPath");

		if (!ppid.equals(LayoutAdminPortletKeys.GROUP_PAGES) ||
			(ppid.equals(LayoutAdminPortletKeys.GROUP_PAGES) &&
			 Validator.isNotNull(mvcRenderCommandName)) ||
			(ppid.equals(LayoutAdminPortletKeys.GROUP_PAGES) &&
			 Validator.isNotNull(mvcPath))) {

			return false;
		}

		return true;
	}

	public boolean isShowLayoutsTree() throws Exception {
		Group group = _themeDisplay.getScopeGroup();

		if ((group != null) && !group.isCompany() && !group.isDepot() &&
			_hasAdministrationPortletPermission()) {

			return true;
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

	private boolean _hasAdministrationPortletPermission() throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			_themeDisplay.getCompanyId(), LayoutAdminPortletKeys.GROUP_PAGES);

		if (portlet == null) {
			return false;
		}

		ControlPanelEntry controlPanelEntry =
			portlet.getControlPanelEntryInstance();

		if (!controlPanelEntry.hasAccessPermission(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroup(), portlet)) {

			return false;
		}

		return true;
	}

	private boolean _isEnableApplicationsMenu() {
		if (_enableApplicationsMenu != null) {
			return _enableApplicationsMenu;
		}

		_enableApplicationsMenu = false;

		try {
			ApplicationsMenuInstanceConfiguration
				applicationsMenuInstanceConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						ApplicationsMenuInstanceConfiguration.class,
						_themeDisplay.getCompanyId());

			_enableApplicationsMenu =
				applicationsMenuInstanceConfiguration.enableApplicationsMenu();

			return _enableApplicationsMenu;
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get applications menu instance configuration",
					configurationException);
			}
		}

		return _enableApplicationsMenu;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductMenuDisplayContext.class);

	private List<PanelCategory> _childPanelCategories;
	private Boolean _enableApplicationsMenu;
	private final HttpServletRequest _httpServletRequest;
	private final PanelAppRegistry _panelAppRegistry;
	private final PanelCategoryHelper _panelCategoryHelper;
	private final PanelCategoryRegistry _panelCategoryRegistry;
	private String _rootPanelCategoryKey;
	private final ThemeDisplay _themeDisplay;

}