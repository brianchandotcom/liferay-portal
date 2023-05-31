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

package com.liferay.roles.admin.web.internal.display.context;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PersonalMenuEntryHelper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.PortletCategoryConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.AdministratorControlPanelEntry;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.OmniadminControlPanelEntry;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletTitleComparator;
import com.liferay.portal.util.WebAppPool;
import com.liferay.product.navigation.personal.menu.BasePersonalMenuEntry;
import com.liferay.roles.admin.constants.RolesAdminWebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Evan Thibodeau
 */
public class EditRolePermissionsNavigationDisplayContext {

	public EditRolePermissionsNavigationDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		Role role, Boolean accountRoleGroupScope) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
		_role = role;
		_accountRoleGroupScope = accountRoleGroupScope;

		_panelCategoryRegistry =
			(PanelCategoryRegistry)httpServletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);
		_panelAppRegistry = (PanelAppRegistry)httpServletRequest.getAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY);
		_personalMenuEntryHelper =
			(PersonalMenuEntryHelper)httpServletRequest.getAttribute(
				ApplicationListWebKeys.PERSONAL_MENU_ENTRY_HELPER);
		_locale = httpServletRequest.getLocale();
		_servletContext = httpServletRequest.getServletContext();
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getData() {
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.convertValue(
			_getTopLevelClayVerticalNavItem(), Map.class);
	}

	private ClayVerticalNavItem _getApplicationsClayVerticalNavItem() {
		Set<String> hiddenPortletIds = Collections.emptySet();

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			_themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		PortletCategory hiddenPortletCategory = portletCategory.getCategory(
			PortletCategoryConstants.NAME_HIDDEN);

		if (hiddenPortletCategory != null) {
			hiddenPortletIds = hiddenPortletCategory.getPortletIds();
		}

		List<ClayVerticalNavItem> clayVerticalNavItems = new ArrayList<>();

		boolean includeSystemPortlets = false;

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(
			_themeDisplay.getCompanyId(), includeSystemPortlets, false);

		portlets = ListUtil.sort(
			portlets, new PortletTitleComparator(_servletContext, _locale));

		for (Portlet portlet : portlets) {
			String portletId = portlet.getPortletId();

			if (Validator.isNull(portletId) ||
				hiddenPortletIds.contains(portletId)) {

				continue;
			}

			clayVerticalNavItems.add(
				ClayVerticalNavItem.create(
					PortalUtil.getPortletLongTitle(
						portlet, _servletContext, _locale),
					_getPortletResourceClayVerticalNavItemConsumer(portletId)));
		}

		return ClayVerticalNavItem.create(
			LanguageUtil.get(_locale, "applications"),
			clayVerticalNavItem -> clayVerticalNavItem.addItems(
				clayVerticalNavItems));
	}

	private String _getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(_httpServletRequest, "backURL");

		return _backURL;
	}

	private String _getEditPermissionsResourceURL(String portletResource) {
		return ResourceURLBuilder.createResourceURL(
			_renderResponse
		).setMVCPath(
			"/view_resources.jsp"
		).setCMD(
			Constants.EDIT
		).setBackURL(
			_getBackURL()
		).setPortletResource(
			portletResource
		).setTabs2(
			"roles"
		).setParameter(
			"accountRoleGroupScope", _accountRoleGroupScope
		).setParameter(
			"roleId", _role.getRoleId()
		).setParameter(
			"p_p_isolated", "true"
		).buildString();
	}

	private ClayVerticalNavItem _getPanelCategoryClayVerticalNavItem(
		PanelCategory panelCategory, String[] excludedPanelAppKeys) {

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			panelCategory);

		if (panelApps.isEmpty()) {
			return null;
		}

		List<ClayVerticalNavItem> clayVerticalNavItems = new ArrayList<>();

		for (PanelApp panelApp : panelApps) {
			Portlet panelAppPortlet = PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(), panelApp.getPortletId());

			String controlPanelEntryClassName =
				panelAppPortlet.getControlPanelEntryClass();
			ControlPanelEntry controlPanelEntry =
				panelAppPortlet.getControlPanelEntryInstance();

			if (Objects.equals(
					controlPanelEntryClassName,
					AdministratorControlPanelEntry.class.getName()) ||
				Objects.equals(
					controlPanelEntryClassName,
					OmniadminControlPanelEntry.class.getName()) ||
				AdministratorControlPanelEntry.class.isAssignableFrom(
					controlPanelEntry.getClass()) ||
				OmniadminControlPanelEntry.class.isAssignableFrom(
					controlPanelEntry.getClass()) ||
				ArrayUtil.contains(
					excludedPanelAppKeys, panelApp.getPortletId())) {

				continue;
			}

			clayVerticalNavItems.add(
				ClayVerticalNavItem.create(
					PortalUtil.getPortletLongTitle(
						panelAppPortlet, _servletContext, _locale),
					_getPortletResourceClayVerticalNavItemConsumer(
						panelAppPortlet.getPortletId())));
		}

		return ClayVerticalNavItem.create(
			panelCategory.getLabel(_locale),
			clayVerticalNavItem -> clayVerticalNavItem.addItems(
				clayVerticalNavItems));
	}

	private List<ClayVerticalNavItem> _getPanelCategoryClayVerticalNavItems(
		String panelCategoryKey) {

		List<ClayVerticalNavItem> clayVerticalNavItems = new ArrayList<>();

		for (PanelCategory panelCategory :
				_panelCategoryRegistry.getChildPanelCategories(
					panelCategoryKey)) {

			ClayVerticalNavItem panelCategoryClayVerticalNavItem =
				_getPanelCategoryClayVerticalNavItem(
					panelCategory, new String[0]);

			if (panelCategoryClayVerticalNavItem != null) {
				clayVerticalNavItems.add(panelCategoryClayVerticalNavItem);
			}
		}

		return clayVerticalNavItems;
	}

	private String _getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_httpServletRequest, "portletResource");

		return _portletResource;
	}

	private Consumer<ClayVerticalNavItem>
		_getPortletResourceClayVerticalNavItemConsumer(String portletResource) {

		return clayVerticalNavItem -> {
			clayVerticalNavItem.setActive(
				_portletResource.equals(portletResource));
			clayVerticalNavItem.put(
				"resourceURL", _getEditPermissionsResourceURL(portletResource));
		};
	}

	private List<ClayVerticalNavItem>
		_getSiteAdministrationPanelCategoryClayVerticalNavItems() {

		List<ClayVerticalNavItem> clayVerticalNavItems = new ArrayList<>();

		for (PanelCategory panelCategory :
				_panelCategoryRegistry.getChildPanelCategories(
					PanelCategoryKeys.SITE_ADMINISTRATION)) {

			ClayVerticalNavItem clayVerticalNavItem =
				_getUnfilteredPanelCategoryClayVerticalNavItem(panelCategory);

			if (clayVerticalNavItem != null) {
				clayVerticalNavItems.add(clayVerticalNavItem);
			}
		}

		return clayVerticalNavItems;
	}

	private ClayVerticalNavItem _getSummaryClayVerticalNavItem() {
		return ClayVerticalNavItem.create(
			LanguageUtil.get(_locale, "summary"),
			clayVerticalNavItem -> {
				clayVerticalNavItem.setActive(
					Validator.isNull(_getPortletResource()));
				clayVerticalNavItem.put("className", "mb-4");
				clayVerticalNavItem.put("ignoreFilter", true);
				clayVerticalNavItem.put(
					"resourceURL",
					ResourceURLBuilder.createResourceURL(
						_renderResponse
					).setMVCPath(
						"/view_resources.jsp"
					).setCMD(
						Constants.VIEW
					).setBackURL(
						_getBackURL()
					).setTabs1(
						"roles"
					).setParameter(
						"accountRoleGroupScope", _accountRoleGroupScope
					).setParameter(
						"roleId", _role.getRoleId()
					).setParameter(
						"p_p_isolated", "true"
					).buildString());
			});
	}

	private ClayVerticalNavItem _getTopLevelClayVerticalNavItem() {
		ClayVerticalNavItem topLevelClayVerticalNavItem =
			new ClayVerticalNavItem(null);

		topLevelClayVerticalNavItem.addItems(_getSummaryClayVerticalNavItem());

		int roleType = _role.getType();

		if (roleType == RoleConstants.TYPE_ORGANIZATION) {
			topLevelClayVerticalNavItem.addItems(
				_getUsersAndOrganizationsClayVerticalNavItem());
		}
		else if (roleType == RoleConstants.TYPE_REGULAR) {
			topLevelClayVerticalNavItem.addItems(
				ClayVerticalNavItem.create(
					LanguageUtil.get(_locale, "control-panel"),
					clayVerticalNavItem -> {
						clayVerticalNavItem.addItems(
							ClayVerticalNavItem.create(
								LanguageUtil.get(
									_locale, "general-permissions"),
								_getPortletResourceClayVerticalNavItemConsumer(
									PortletKeys.PORTAL)));

						clayVerticalNavItem.addItems(
							_getPanelCategoryClayVerticalNavItems(
								PanelCategoryKeys.CONTROL_PANEL));

						clayVerticalNavItem.setInitialExpanded(true);
					}));

			topLevelClayVerticalNavItem.addItems(
				ClayVerticalNavItem.create(
					LanguageUtil.get(_locale, "commerce"),
					clayVerticalNavItem -> {
						clayVerticalNavItem.addItems(
							_getPanelCategoryClayVerticalNavItems(
								PanelCategoryKeys.COMMERCE));
						clayVerticalNavItem.setInitialExpanded(true);
					}));

			topLevelClayVerticalNavItem.addItems(
				ClayVerticalNavItem.create(
					LanguageUtil.get(_locale, "applications-menu"),
					clayVerticalNavItem -> {
						clayVerticalNavItem.addItems(
							_getPanelCategoryClayVerticalNavItems(
								PanelCategoryKeys.
									APPLICATIONS_MENU_APPLICATIONS));
						clayVerticalNavItem.setInitialExpanded(true);
					}));
		}

		if (!_accountRoleGroupScope) {
			String[] excludedPanelAppKeys =
				(String[])_httpServletRequest.getAttribute(
					RolesAdminWebKeys.EXCLUDED_PANEL_APP_KEYS);

			for (String panelCategoryKey :
					(String[])_httpServletRequest.getAttribute(
						RolesAdminWebKeys.PANEL_CATEGORY_KEYS)) {

				ClayVerticalNavItem panelCategoryClayVerticalNavItem =
					_getPanelCategoryClayVerticalNavItem(
						_panelCategoryRegistry.getPanelCategory(
							panelCategoryKey),
						excludedPanelAppKeys);

				if (panelCategoryClayVerticalNavItem != null) {
					topLevelClayVerticalNavItem.addItems(
						panelCategoryClayVerticalNavItem);
				}
			}
		}

		topLevelClayVerticalNavItem.addItems(
			ClayVerticalNavItem.create(
				LanguageUtil.get(
					_locale, "site-and-asset-library-administration"),
				clayVerticalNavItem -> {
					clayVerticalNavItem.addItems(
						_getSiteAdministrationPanelCategoryClayVerticalNavItems());
					clayVerticalNavItem.addItems(
						_getApplicationsClayVerticalNavItem());
				}));

		if (roleType == RoleConstants.TYPE_REGULAR) {
			topLevelClayVerticalNavItem.addItems(
				ClayVerticalNavItem.create(
					LanguageUtil.get(_locale, "user"),
					clayVerticalNavItem -> clayVerticalNavItem.addItems(
						_getUserClayVerticalNavItems())));

			List<PanelCategory> panelCategories = new ArrayList<>();

			panelCategories.addAll(
				_panelCategoryRegistry.getChildPanelCategories(
					PanelCategoryKeys.APPLICATIONS_MENU));
			panelCategories.addAll(
				_panelCategoryRegistry.getChildPanelCategories(
					PanelCategoryKeys.ROOT));

			for (PanelCategory panelCategory : panelCategories) {
				if (ListUtil.isNotEmpty(
						_panelAppRegistry.getPanelApps(panelCategory))) {

					ClayVerticalNavItem panelCategoryClayVerticalNavItem =
						_getUnfilteredPanelCategoryClayVerticalNavItem(
							panelCategory);

					if (panelCategoryClayVerticalNavItem != null) {
						topLevelClayVerticalNavItem.addItems(
							panelCategoryClayVerticalNavItem);
					}
				}
			}
		}

		return topLevelClayVerticalNavItem;
	}

	private ClayVerticalNavItem _getUnfilteredPanelCategoryClayVerticalNavItem(
		PanelCategory panelCategory) {

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			panelCategory);

		if (panelApps.isEmpty()) {
			return null;
		}

		return ClayVerticalNavItem.create(
			panelCategory.getLabel(_locale),
			clayVerticalNavItem -> {
				for (PanelApp panelApp : panelApps) {
					Portlet panelAppPortlet =
						PortletLocalServiceUtil.getPortletById(
							_themeDisplay.getCompanyId(),
							panelApp.getPortletId());

					clayVerticalNavItem.addItems(
						ClayVerticalNavItem.create(
							PortalUtil.getPortletLongTitle(
								panelAppPortlet, _servletContext, _locale),
							_getPortletResourceClayVerticalNavItemConsumer(
								panelAppPortlet.getPortletId())));
				}
			});
	}

	private List<ClayVerticalNavItem> _getUserClayVerticalNavItems() {
		List<ClayVerticalNavItem> clayVerticalNavItems = new ArrayList<>();

		for (BasePersonalMenuEntry basePersonalMenuEntry :
				_personalMenuEntryHelper.getBasePersonalMenuEntries()) {

			Portlet personalPortlet = PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(),
				basePersonalMenuEntry.getPortletId());

			clayVerticalNavItems.add(
				ClayVerticalNavItem.create(
					PortalUtil.getPortletLongTitle(
						personalPortlet, _servletContext, _locale),
					_getPortletResourceClayVerticalNavItemConsumer(
						personalPortlet.getPortletId())));
		}

		return clayVerticalNavItems;
	}

	private ClayVerticalNavItem _getUsersAndOrganizationsClayVerticalNavItem() {
		Portlet usersAdminPortlet = PortletLocalServiceUtil.getPortletById(
			_themeDisplay.getCompanyId(),
			PortletProviderUtil.getPortletId(
				User.class.getName(), PortletProvider.Action.VIEW));

		return ClayVerticalNavItem.create(
			PortalUtil.getPortletLongTitle(
				usersAdminPortlet, _servletContext, _locale),
			_getPortletResourceClayVerticalNavItemConsumer(
				usersAdminPortlet.getPortletId()));
	}

	private final Boolean _accountRoleGroupScope;
	private String _backURL;
	private final HttpServletRequest _httpServletRequest;
	private final Locale _locale;
	private final PanelAppRegistry _panelAppRegistry;
	private final PanelCategoryRegistry _panelCategoryRegistry;
	private final PersonalMenuEntryHelper _personalMenuEntryHelper;
	private String _portletResource;
	private final RenderResponse _renderResponse;
	private final Role _role;
	private final ServletContext _servletContext;
	private final ThemeDisplay _themeDisplay;

}