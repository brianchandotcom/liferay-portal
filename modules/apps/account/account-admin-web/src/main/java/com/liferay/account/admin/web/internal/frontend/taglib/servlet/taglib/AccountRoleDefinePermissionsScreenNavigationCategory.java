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

package com.liferay.account.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.account.admin.web.internal.constants.AccountScreenNavigationEntryConstants;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.application.list.display.context.logic.PersonalMenuEntryHelper;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.roles.admin.constants.RolesAdminWebKeys;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class AccountRoleDefinePermissionsScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<AccountRole> {

	@Override
	public String getCategoryKey() {
		return AccountScreenNavigationEntryConstants.
			CATEGORY_KEY_DEFINE_PERMISSIONS;
	}

	@Override
	public String getEntryKey() {
		return AccountScreenNavigationEntryConstants.
			ENTRY_KEY_DEFINE_PERMISSIONS;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale), "define-permissions");
	}

	@Override
	public String getScreenNavigationKey() {
		return AccountScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_ACCOUNT_ROLE;
	}

	@Override
	public boolean isVisible(User user, AccountRole accountRole) {
		if (accountRole == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_setAttributes(httpServletRequest);

		DynamicServletRequest dynamicServletRequest = new DynamicServletRequest(
			httpServletRequest);

		dynamicServletRequest.appendParameter("tabs1", "define-permissions");
		dynamicServletRequest.appendParameter(Constants.CMD, Constants.VIEW);
		dynamicServletRequest.appendParameter(
			"redirect", _getRedirect(httpServletRequest));
		dynamicServletRequest.appendParameter(
			"backURL", _getBackURL(httpServletRequest));

		AccountRole accountRole = _accountRoleLocalService.fetchAccountRole(
			ParamUtil.getLong(httpServletRequest, "accountRoleId"));

		dynamicServletRequest.appendParameter(
			"roleId", String.valueOf(accountRole.getRoleId()));

		_jspRenderer.renderJSP(
			_servletContext, dynamicServletRequest, httpServletResponse,
			"/edit_role_permissions.jsp");
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			resourceBundle, _portal.getResourceBundle(locale));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "_removePersonalMenuEntry"
	)
	private void _addPersonalMenuEntry(PersonalMenuEntry personalMenuEntry) {
		_personalMenuEntries.add(personalMenuEntry);
	}

	private String _getBackURL(HttpServletRequest httpServletRequest) {
		PortletURL backURL = _portal.getControlPanelPortletURL(
			httpServletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
			PortletRequest.RENDER_PHASE);

		backURL.setParameter(
			"mvcRenderCommandName", "/account_admin/edit_account_entry");
		backURL.setParameter(
			"screenNavigationCategoryKey",
			AccountScreenNavigationEntryConstants.CATEGORY_KEY_ROLES);
		backURL.setParameter(
			"accountEntryId",
			ParamUtil.getString(httpServletRequest, "accountEntryId"));

		return backURL.toString();
	}

	private String _getRedirect(HttpServletRequest httpServletRequest) {
		PortletURL redirect = _portal.getControlPanelPortletURL(
			httpServletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
			PortletRequest.RENDER_PHASE);

		redirect.setParameter(
			"mvcRenderCommandName",
			"/account_admin/edit_account_role_permissions");
		redirect.setParameter(
			"screenNavigationCategoryKey",
			AccountScreenNavigationEntryConstants.
				CATEGORY_KEY_DEFINE_PERMISSIONS);
		redirect.setParameter(
			"accountEntryId",
			ParamUtil.getString(httpServletRequest, "accountEntryId"));
		redirect.setParameter(
			"accountRoleId",
			ParamUtil.getString(httpServletRequest, "accountRoleId"));

		return redirect.toString();
	}

	private void _removePersonalMenuEntry(PersonalMenuEntry personalMenuEntry) {
		_personalMenuEntries.remove(personalMenuEntry);
	}

	private void _setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY, _panelAppRegistry);
		httpServletRequest.setAttribute(
			RolesAdminWebKeys.CURRENT_ROLE_TYPE, _accountRoleTypeContributor);
		httpServletRequest.setAttribute(
			RolesAdminWebKeys.SHOW_NAV_TABS, Boolean.FALSE);

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER, panelCategoryHelper);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY,
			_panelCategoryRegistry);

		PersonalMenuEntryHelper personalMenuEntryHelper =
			new PersonalMenuEntryHelper(_personalMenuEntries);

		httpServletRequest.setAttribute(
			ApplicationListWebKeys.PERSONAL_MENU_ENTRY_HELPER,
			personalMenuEntryHelper);
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference(target = "(component.name=*.AccountRoleTypeContributor)")
	private RoleTypeContributor _accountRoleTypeContributor;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	private final List<PersonalMenuEntry> _personalMenuEntries =
		new CopyOnWriteArrayList<>();

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.roles.admin.web)")
	private ServletContext _servletContext;

}