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

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PersonalMenuEntryHelper;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
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
		return HashMapBuilder.<String, Object>put(
			"items", _getNavItemsJSONArray()
		).build();
	}

	private void _addPanelCategoryItems(
		JSONArray panelCategoryNavItemsJSONArray, String panelCategoryKey) {

		for (PanelCategory panelCategory :
				_panelCategoryRegistry.getChildPanelCategories(
					panelCategoryKey)) {

			JSONObject panelCategoryJSONObject = _getPanelCategoryJSONObject(
				panelCategory, new String[0]);

			if (panelCategoryJSONObject != null) {
				panelCategoryNavItemsJSONArray.put(panelCategoryJSONObject);
			}
		}
	}

	private JSONObject _getApplicationsNavItemsJSONObject() {
		Set<String> hiddenPortletIds = Collections.emptySet();

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			_themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		PortletCategory hiddenPortletCategory = portletCategory.getCategory(
			PortletCategoryConstants.NAME_HIDDEN);

		if (hiddenPortletCategory != null) {
			hiddenPortletIds = hiddenPortletCategory.getPortletIds();
		}

		JSONArray applicationsNavItemsJSONArray =
			JSONFactoryUtil.createJSONArray();

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

			applicationsNavItemsJSONArray.put(
				_getNavItemJSONObject(
					PortalUtil.getPortletLongTitle(
						portlet, _servletContext, _locale),
					portletId));
		}

		return _getNavItemJSONObject(
			LanguageUtil.get(_locale, "applications"),
			applicationsNavItemsJSONArray);
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

	private JSONObject _getNavItemJSONObject(
		String label, JSONArray navItemsJSONArray) {

		return JSONUtil.put(
			"items", navItemsJSONArray
		).put(
			"label", label
		);
	}

	private JSONObject _getNavItemJSONObject(
		String label, JSONArray navItemsJSONArray, Boolean initialExpanded) {

		return _getNavItemJSONObject(
			label, navItemsJSONArray
		).put(
			"initialExpanded", initialExpanded
		);
	}

	private JSONObject _getNavItemJSONObject(
		String label, String portletResource) {

		return JSONUtil.put(
			"active", _portletResource.equals(portletResource)
		).put(
			"label", label
		).put(
			"resourceURL", _getEditPermissionsResourceURL(portletResource)
		);
	}

	private JSONArray _getNavItemsJSONArray() {
		JSONArray navItemsJSONArray = JSONUtil.put(_getSummaryJSONObject());

		int roleType = _role.getType();

		if (roleType == RoleConstants.TYPE_ORGANIZATION) {
			navItemsJSONArray.put(_getUsersAndOrganizationsJSONObject());
		}
		else if (roleType == RoleConstants.TYPE_REGULAR) {
			JSONArray controlPanelPanelCategoryNavItemsJSONArray = JSONUtil.put(
				_getNavItemJSONObject(
					LanguageUtil.get(_locale, "general-permissions"),
					PortletKeys.PORTAL));

			_addPanelCategoryItems(
				controlPanelPanelCategoryNavItemsJSONArray,
				PanelCategoryKeys.CONTROL_PANEL);

			navItemsJSONArray.put(
				_getNavItemJSONObject(
					LanguageUtil.get(_locale, "control-panel"),
					controlPanelPanelCategoryNavItemsJSONArray, true)
			).put(
				_getNavItemJSONObject(
					LanguageUtil.get(_locale, "commerce"),
					_getPanelCategoryNavItemsJSONArray(
						PanelCategoryKeys.COMMERCE),
					true)
			).put(
				_getNavItemJSONObject(
					LanguageUtil.get(_locale, "applications-menu"),
					_getPanelCategoryNavItemsJSONArray(
						PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS),
					true)
			);
		}

		if (!_accountRoleGroupScope) {
			String[] excludedPanelAppKeys =
				(String[])_httpServletRequest.getAttribute(
					RolesAdminWebKeys.EXCLUDED_PANEL_APP_KEYS);

			for (String panelCategoryKey :
					(String[])_httpServletRequest.getAttribute(
						RolesAdminWebKeys.PANEL_CATEGORY_KEYS)) {

				JSONObject panelCategoryJSONObject =
					_getPanelCategoryJSONObject(
						_panelCategoryRegistry.getPanelCategory(
							panelCategoryKey),
						excludedPanelAppKeys);

				if (panelCategoryJSONObject != null) {
					navItemsJSONArray.put(panelCategoryJSONObject);
				}
			}
		}

		JSONArray siteAdministrationPanelCategoryNavItemsJSONArray =
			_getSiteAdministrationPanelCategoryNavItemsJSONArray();

		siteAdministrationPanelCategoryNavItemsJSONArray.put(
			_getApplicationsNavItemsJSONObject());

		navItemsJSONArray.put(
			_getNavItemJSONObject(
				LanguageUtil.get(
					_locale, "site-and-asset-library-administration"),
				siteAdministrationPanelCategoryNavItemsJSONArray));

		if (roleType == RoleConstants.TYPE_REGULAR) {
			navItemsJSONArray.put(
				_getNavItemJSONObject(
					LanguageUtil.get(_locale, "user"),
					_getUserNavItemsJSONArray()));

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

					JSONObject panelCategoryJSONObject =
						_getUnfilteredPanelCategoryJSONObject(panelCategory);

					if (panelCategoryJSONObject != null) {
						navItemsJSONArray.put(panelCategoryJSONObject);
					}
				}
			}
		}

		return navItemsJSONArray;
	}

	private JSONObject _getPanelCategoryJSONObject(
		PanelCategory panelCategory, String[] excludedPanelAppKeys) {

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			panelCategory);

		if (!panelApps.isEmpty()) {
			JSONArray panelCategoryNavItemsJSONArray =
				JSONFactoryUtil.createJSONArray();

			for (PanelApp panelApp : panelApps) {
				Portlet panelAppPortlet =
					PortletLocalServiceUtil.getPortletById(
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

				panelCategoryNavItemsJSONArray.put(
					_getNavItemJSONObject(
						PortalUtil.getPortletLongTitle(
							panelAppPortlet, _servletContext, _locale),
						panelAppPortlet.getPortletId()));
			}

			return _getNavItemJSONObject(
				panelCategory.getLabel(_locale),
				panelCategoryNavItemsJSONArray);
		}

		return null;
	}

	private JSONArray _getPanelCategoryNavItemsJSONArray(
		String panelCategoryKey) {

		JSONArray panelCategoryNavItemsJSONArray =
			JSONFactoryUtil.createJSONArray();

		_addPanelCategoryItems(
			panelCategoryNavItemsJSONArray, panelCategoryKey);

		return panelCategoryNavItemsJSONArray;
	}

	private String _getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_httpServletRequest, "portletResource");

		return _portletResource;
	}

	private JSONArray _getSiteAdministrationPanelCategoryNavItemsJSONArray() {
		JSONArray siteAdministrationPanelCategoryNavItemsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (PanelCategory panelCategory :
				_panelCategoryRegistry.getChildPanelCategories(
					PanelCategoryKeys.SITE_ADMINISTRATION)) {

			JSONObject panelCategoryJSONObject =
				_getUnfilteredPanelCategoryJSONObject(panelCategory);

			if (panelCategoryJSONObject != null) {
				siteAdministrationPanelCategoryNavItemsJSONArray.put(
					panelCategoryJSONObject);
			}
		}

		return siteAdministrationPanelCategoryNavItemsJSONArray;
	}

	private JSONObject _getSummaryJSONObject() {
		return JSONUtil.put(
			"active", Validator.isNull(_getPortletResource())
		).put(
			"className", "mb-4"
		).put(
			"ignoreFilter", true
		).put(
			"label", LanguageUtil.get(_locale, "summary")
		).put(
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
			).buildString()
		);
	}

	private JSONObject _getUnfilteredPanelCategoryJSONObject(
		PanelCategory panelCategory) {

		List<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			panelCategory);

		if (!panelApps.isEmpty()) {
			JSONArray panelCategoryNavItemsJSONArray =
				JSONFactoryUtil.createJSONArray();

			for (PanelApp panelApp : panelApps) {
				Portlet panelAppPortlet =
					PortletLocalServiceUtil.getPortletById(
						_themeDisplay.getCompanyId(), panelApp.getPortletId());

				panelCategoryNavItemsJSONArray.put(
					_getNavItemJSONObject(
						PortalUtil.getPortletLongTitle(
							panelAppPortlet, _servletContext, _locale),
						panelAppPortlet.getPortletId()));
			}

			return _getNavItemJSONObject(
				panelCategory.getLabel(_locale),
				panelCategoryNavItemsJSONArray);
		}

		return null;
	}

	private JSONArray _getUserNavItemsJSONArray() {
		JSONArray userNavItemsJSONArray = JSONFactoryUtil.createJSONArray();

		for (BasePersonalMenuEntry basePersonalMenuEntry :
				_personalMenuEntryHelper.getBasePersonalMenuEntries()) {

			Portlet personalPortlet = PortletLocalServiceUtil.getPortletById(
				_themeDisplay.getCompanyId(),
				basePersonalMenuEntry.getPortletId());

			userNavItemsJSONArray.put(
				_getNavItemJSONObject(
					PortalUtil.getPortletLongTitle(
						personalPortlet, _servletContext, _locale),
					personalPortlet.getPortletId()));
		}

		return userNavItemsJSONArray;
	}

	private JSONObject _getUsersAndOrganizationsJSONObject() {
		Portlet usersAdminPortlet = PortletLocalServiceUtil.getPortletById(
			_themeDisplay.getCompanyId(),
			PortletProviderUtil.getPortletId(
				User.class.getName(), PortletProvider.Action.VIEW));

		return _getNavItemJSONObject(
			PortalUtil.getPortletLongTitle(
				usersAdminPortlet, _servletContext, _locale),
			usersAdminPortlet.getPortletId());
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