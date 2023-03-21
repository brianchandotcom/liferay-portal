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

import com.liferay.account.admin.web.internal.display.AccountGroupDisplay;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountGroupPermission;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.permission.PortalPermission;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class AccountGroupDetailsScreenNavigationEntry
	extends AccountGroupDetailsScreenNavigationCategory
	implements ScreenNavigationEntry<AccountGroupDisplay> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public boolean isVisible(
		User user, AccountGroupDisplay accountGroupDisplay) {

		if (accountGroupDisplay.getAccountGroupId() ==
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) {

			return portalPermission.contains(
				PermissionCheckerFactoryUtil.create(user),
				AccountActionKeys.ADD_ACCOUNT_GROUP);
		}

		return AccountGroupPermission.contains(
			PermissionCheckerFactoryUtil.create(user),
			accountGroupDisplay.getAccountGroupId(), ActionKeys.UPDATE);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/account_groups_admin/account_group/details.jsp");
	}

	@Reference
	protected JSPRenderer jspRenderer;

	@Reference
	protected PortalPermission portalPermission;

}