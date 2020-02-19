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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Albert Lee
 */
public class ViewAccountUsersManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewAccountUsersManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemList.of(
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "removeUsers");

				PortletURL removeUsersURL =
					liferayPortletResponse.createActionURL();

				removeUsersURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/remove_account_users");
				removeUsersURL.setParameter(
					"redirect", currentURLObj.toString());

				dropdownItem.putData(
					"removeUsersURL", removeUsersURL.toString());

				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(LanguageUtil.get(request, "remove"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			});
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", (String)null);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "selectAccountUsers");

				long accountEntryId = ParamUtil.getLong(
					request, "accountEntryId");

				AccountEntry accountEntry =
					AccountEntryLocalServiceUtil.fetchAccountEntry(
						accountEntryId);

				if (accountEntry != null) {
					dropdownItem.putData(
						"accountEntryName", accountEntry.getName());
				}

				PortletURL assignAccountUsersURL =
					liferayPortletResponse.createActionURL();

				assignAccountUsersURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/assign_account_users");
				assignAccountUsersURL.setParameter(
					"redirect", currentURLObj.toString());

				dropdownItem.putData(
					"assignAccountUsersURL", assignAccountUsersURL.toString());

				PortletURL selectAccountUsersURL =
					liferayPortletResponse.createRenderURL();

				selectAccountUsersURL.setParameter(
					"mvcPath",
					"/account_entries_admin/select_account_users.jsp");
				selectAccountUsersURL.setParameter(
					"accountEntryId", String.valueOf(accountEntryId));
				selectAccountUsersURL.setWindowState(LiferayWindowState.POP_UP);

				dropdownItem.putData(
					"selectAccountUsersURL", selectAccountUsersURL.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(request, "assign-users"));
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "ACCOUNT_USERS_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				if (!Objects.equals(getNavigation(), "active")) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = getPortletURL();

							removeLabelURL.setParameter(
								"navigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							String label = String.format(
								"%s: %s", LanguageUtil.get(request, "status"),
								LanguageUtil.get(request, getNavigation()));

							labelItem.setLabel(label);
						});
				}
			}
		};
	}

	@Override
	public PortletURL getPortletURL() {
		try {
			return PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
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
		long accountEntryId = ParamUtil.getLong(
			liferayPortletRequest, "accountEntryId");

		long count =
			AccountEntryUserRelLocalServiceUtil.
				getAccountEntryUserRelsCountByAccountEntryId(accountEntryId);

		if (count > 0) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return PortalPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), ActionKeys.ADD_USER);
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
	protected String getOrderByCol() {
		return ParamUtil.getString(
			liferayPortletRequest, getOrderByColParam(), "last-name");
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"first-name", "last-name", "email-address"};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewAccountUsersManagementToolbarDisplayContext.class);

}