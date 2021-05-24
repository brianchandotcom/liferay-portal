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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountServiceUtil;
import com.liferay.commerce.frontend.taglib.internal.model.AccountRole;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Fabio Diego Mastrorilli
 */
public class UserRolesModalTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		putValue("spritemap", themeDisplay.getPathThemeImages() + "/icons.svg");

		try {
			List<AccountRole> selectedRoles = new ArrayList<>();

			Map<String, Object> context = getContext();

			long userId = GetterUtil.getLong(context.get("userId"));

			long commerceAccountId = GetterUtil.getLong(
				context.get("commerceAccountId"));

			CommerceAccount commerceAccount =
				CommerceAccountServiceUtil.getCommerceAccount(
					commerceAccountId);

			List<UserGroupRole> userGroupRoles =
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(
					userId, commerceAccount.getCommerceAccountGroupId());

			for (UserGroupRole userGroupRole : userGroupRoles) {
				Role role = userGroupRole.getRole();

				selectedRoles.add(
					new AccountRole(role.getRoleId(), role.getName()));
			}

			putValue("selectedRoles", selectedRoles);

			List<AccountRole> availableRoles = new ArrayList<>();

			List<Role> roles = RoleServiceUtil.getRoles(
				PortalUtil.getCompanyId(httpServletRequest),
				new int[] {RoleConstants.TYPE_SITE});

			for (Role role : roles) {
				availableRoles.add(
					new AccountRole(
						role.getRoleId(),
						role.getTitle(themeDisplay.getLocale())));
			}

			putValue("roles", availableRoles);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		setTemplateNamespace("UserRolesModal.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = ServletContextUtil.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"commerce-frontend-taglib/user_roles_modal/UserRolesModal.es");
	}

	public void setCommerceAccountId(long commerceAccountId) {
		putValue("commerceAccountId", commerceAccountId);
	}

	public void setUserId(long userId) {
		putValue("userId", userId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserRolesModalTag.class);

}