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
import com.liferay.account.admin.web.internal.helper.AccountRoleRequestHelper;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		Role role = _roleLocalService.fetchRole(accountRole.getRoleId());

		if ((role != null) && AccountRoleConstants.isSharedRole(role)) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_accountRoleRequestHelper.setRequestAttributes(httpServletRequest);

		DynamicServletRequest dynamicServletRequest = new DynamicServletRequest(
			httpServletRequest);

		dynamicServletRequest.appendParameter(Constants.CMD, Constants.VIEW);
		dynamicServletRequest.appendParameter("tabs1", "define-permissions");
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
		PortletURL redirectURL = _portal.getControlPanelPortletURL(
			httpServletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
			PortletRequest.RENDER_PHASE);

		redirectURL.setParameter(
			"mvcRenderCommandName",
			"/account_admin/edit_account_role_permissions");
		redirectURL.setParameter(
			"screenNavigationCategoryKey",
			AccountScreenNavigationEntryConstants.
				CATEGORY_KEY_DEFINE_PERMISSIONS);
		redirectURL.setParameter(
			"accountEntryId",
			ParamUtil.getString(httpServletRequest, "accountEntryId"));
		redirectURL.setParameter(
			"accountRoleId",
			ParamUtil.getString(httpServletRequest, "accountRoleId"));

		return redirectURL.toString();
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private AccountRoleRequestHelper _accountRoleRequestHelper;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.roles.admin.web)")
	private ServletContext _servletContext;

}