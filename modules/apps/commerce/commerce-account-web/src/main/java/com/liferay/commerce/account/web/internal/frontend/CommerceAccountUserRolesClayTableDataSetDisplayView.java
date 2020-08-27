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

package com.liferay.commerce.account.web.internal.frontend;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.web.internal.model.AccountRole;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceAccountUserRolesClayTableDataSetDisplayView.NAME,
		"clay.data.set.display.name=" + CommerceAccountUserRolesClayTableDataSetDisplayView.NAME
	},
	service = {ClayDataSetDataProvider.class, ClayDataSetDisplayView.class}
)
public class CommerceAccountUserRolesClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetDataProvider<AccountRole> {

	public static final String NAME = "commerceAccountUserRoles";

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<AccountRole> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");
		long userId = ParamUtil.getLong(httpServletRequest, "userId");

		CommerceAccount commerceAccount =
			_commerceAccountService.getCommerceAccount(commerceAccountId);

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRoles(
				userId, commerceAccount.getCommerceAccountGroupId(),
				pagination.getStartPosition(), pagination.getEndPosition());

		List<AccountRole> accountRoles = new ArrayList<>();

		for (UserGroupRole userGroupRole : userGroupRoles) {
			Role role = userGroupRole.getRole();

			accountRoles.add(
				new AccountRole(
					role.getRoleId(),
					role.getTitle(themeDisplay.getLanguageId())));
		}

		return accountRoles;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");
		long userId = ParamUtil.getLong(httpServletRequest, "userId");

		CommerceAccount commerceAccount =
			_commerceAccountService.getCommerceAccount(commerceAccountId);

		return _userGroupRoleLocalService.getUserGroupRolesCount(
			userId, commerceAccount.getCommerceAccountGroupId());
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}