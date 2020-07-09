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

package com.liferay.account.admin.web.internal.display.context;

import com.liferay.account.admin.web.internal.display.AccountUserDisplay;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class SelectAccountUsersManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public SelectAccountUsersManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<AccountUserDisplay> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", (String)null);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!isShowCreateButton()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addAccountEntryUser");

				if (isOpenModalOnRedirect()) {
					dropdownItem.putData(
						"openModalOnRedirect", Boolean.TRUE.toString());
				}

				PortletURL addAccountEntryUserURL =
					PortletURLFactoryUtil.create(
						liferayPortletRequest,
						AccountPortletKeys.ACCOUNT_USERS_ADMIN,
						PortletRequest.RENDER_PHASE);

				addAccountEntryUserURL.setParameter(
					"mvcRenderCommandName", "/account_admin/add_account_user");
				addAccountEntryUserURL.setParameter(
					"redirect", ParamUtil.getString(request, "redirect"));
				addAccountEntryUserURL.setParameter(
					"backURL", ParamUtil.getString(request, "redirect"));
				addAccountEntryUserURL.setParameter(
					"accountEntryId",
					ParamUtil.getString(request, "accountEntryId"));

				dropdownItem.putData(
					"addAccountEntryUserURL",
					addAccountEntryUserURL.toString());

				dropdownItem.setLabel(LanguageUtil.get(request, "new-user"));
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "SELECT_ACCOUNT_USERS_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public String getNavigation() {
		return ParamUtil.getString(
			liferayPortletRequest, getNavigationParam(),
			"current-account-users");
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public Boolean isDisabled() {
		return false;
	}

	public boolean isOpenModalOnRedirect() {
		return ParamUtil.getBoolean(request, "openModalOnRedirect");
	}

	@Override
	public Boolean isSelectable() {
		return !isSingleSelect();
	}

	public boolean isShowCreateButton() {
		return ParamUtil.getBoolean(liferayPortletRequest, "showCreateButton");
	}

	public boolean isSingleSelect() {
		return ParamUtil.getBoolean(liferayPortletRequest, "singleSelect");
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"current-account-users", "all-users"};
	}

	@Override
	protected String getOrderByCol() {
		return ParamUtil.getString(
			liferayPortletRequest, getOrderByColParam(), "last-name");
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"first-name", "last-name", "email-address"};
	}

}