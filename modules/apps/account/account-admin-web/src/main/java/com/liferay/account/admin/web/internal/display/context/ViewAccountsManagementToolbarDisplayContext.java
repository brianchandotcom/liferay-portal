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

import com.liferay.account.admin.web.internal.display.AccountDisplay;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountEntryPermission;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountPermission;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ViewAccountsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewAccountsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer searchContainer) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			searchContainer);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemList.of(
			() -> {
				if (Objects.equals(getNavigation(), "inactive")) {
					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "deactivateAccounts");

				PortletURL deactivateAccountsURL =
					liferayPortletResponse.createActionURL();

				deactivateAccountsURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/update_account_entry_status");
				deactivateAccountsURL.setParameter(
					Constants.CMD, Constants.DEACTIVATE);
				deactivateAccountsURL.setParameter(
					"navigation", getNavigation());

				dropdownItem.putData(
					"deactivateAccountsURL", deactivateAccountsURL.toString());

				dropdownItem.setIcon("hidden");
				dropdownItem.setLabel(LanguageUtil.get(request, "deactivate"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			},
			() -> {
				if (Objects.equals(getNavigation(), "active")) {
					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "activateAccounts");

				PortletURL activateAccountsURL =
					liferayPortletResponse.createActionURL();

				activateAccountsURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/update_account_entry_status");
				activateAccountsURL.setParameter(
					Constants.CMD, Constants.RESTORE);
				activateAccountsURL.setParameter("navigation", getNavigation());

				dropdownItem.putData(
					"activateAccountsURL", activateAccountsURL.toString());

				dropdownItem.setIcon("undo");
				dropdownItem.setLabel(LanguageUtil.get(request, "activate"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			},
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "deleteAccounts");

				PortletURL deleteAccountsURL =
					liferayPortletResponse.createActionURL();

				deleteAccountsURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/delete_account_entry");
				deleteAccountsURL.setParameter("navigation", getNavigation());

				dropdownItem.putData(
					"deleteAccountsURL", deleteAccountsURL.toString());

				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(LanguageUtil.get(request, "delete"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			});
	}

	public List<String> getAvailableActions(AccountDisplay accountDisplay)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!AccountEntryPermission.contains(
				themeDisplay.getPermissionChecker(),
				accountDisplay.getAccountId(), ActionKeys.DELETE)) {

			return availableActions;
		}

		if (accountDisplay.isActive()) {
			availableActions.add("deactivateAccounts");
		}
		else {
			availableActions.add("activateAccounts");
		}

		availableActions.add("deleteAccounts");

		return availableActions;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", (String)null);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "accountsManagementToolbar";
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							liferayPortletResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/account_admin/edit_account_entry", "backURL",
							currentURLObj.toString());
						dropdownItem.setLabel(
							LanguageUtil.get(request, "add-account"));
					});
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return "ACCOUNTS_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public PortletURL getPortletURL() {
		try {
			return PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return liferayPortletResponse.createRenderURL();
		}
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

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return AccountPermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			AccountActionKeys.ADD_ACCOUNT_ENTRY);
	}

	@Override
	protected String getNavigation() {
		return ParamUtil.getString(
			liferayPortletRequest, getNavigationParam(), "active");
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"active", "inactive"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name", "account-owner", "parent-account"};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewAccountsManagementToolbarDisplayContext.class);

}