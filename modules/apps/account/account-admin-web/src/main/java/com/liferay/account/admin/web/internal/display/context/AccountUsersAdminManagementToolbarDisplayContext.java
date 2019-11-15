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
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
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
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Albert Lee
 */
public class AccountUsersAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public AccountUsersAdminManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer searchContainer) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", (String)null);
		clearResultsURL.setParameter("accountEntriesNavigation", "all");
		clearResultsURL.setParameter("accountEntryIds", StringPool.BLANK);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.putData("action", "addAccountUser");

						PortletURL accountEntrySelectorURL =
							liferayPortletResponse.createRenderURL();

						accountEntrySelectorURL.setParameter(
							"mvcPath",
							"/account_users_admin/select_account_entry.jsp");
						accountEntrySelectorURL.setWindowState(
							LiferayWindowState.POP_UP);

						dropdownItem.putData(
							"accountEntrySelectorURL",
							accountEntrySelectorURL.toString());

						PortletURL addAccountUserURL =
							liferayPortletResponse.createRenderURL();

						addAccountUserURL.setParameter(
							"mvcRenderCommandName",
							"/account_admin/add_account_user");
						addAccountUserURL.setParameter(
							"backURL",
							String.valueOf(
								liferayPortletResponse.createRenderURL()));

						dropdownItem.putData(
							"addAccountUserURL", addAccountUserURL.toString());

						dropdownItem.putData(
							"dialogTitle",
							LanguageUtil.get(request, "select-an-account"));

						dropdownItem.setLabel(
							LanguageUtil.get(request, "add-user"));
					});
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return "ACCOUNT_USERS_ADMIN_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		DropdownItemList filterDropdownItems = new DropdownItemList() {
			{
				List<DropdownItem> filterAccountEntriesDropdownItems =
					_getFilterByAccountEntriesDropdownItems();

				if (filterAccountEntriesDropdownItems != null) {
					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getFilterByAccountEntriesDropdownItems());
							dropdownGroupItem.setLabel(
								_getFilterByAccountEntriesDropdownItemsLabel());
						});
				}
			}
		};

		filterDropdownItems.addAll(super.getFilterDropdownItems());

		if (filterDropdownItems.isEmpty()) {
			return null;
		}

		return filterDropdownItems;
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				if (Objects.equals(
						_getAccountEntriesNavigation(), "accounts")) {

					long[] accountEntryIds = ParamUtil.getLongValues(
						request, "accountEntryIds");

					for (long accountEntryId : accountEntryIds) {
						AccountEntry accountEntry =
							AccountEntryLocalServiceUtil.fetchAccountEntry(
								accountEntryId);

						add(
							labelItem -> labelItem.setLabel(
								LanguageUtil.get(
									request, accountEntry.getName())));
					}
				}
			}
		};
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

	private String _getAccountEntriesNavigation() {
		return ParamUtil.getString(
			liferayPortletRequest, "accountEntriesNavigation", "all");
	}

	private List<DropdownItem> _getFilterByAccountEntriesDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(
								_getAccountEntriesNavigation(), "all"));

						dropdownItem.setLabel(LanguageUtil.get(request, "all"));

						dropdownItem.setHref(
							PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse),
							"accountEntriesNavigation", "all");
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(
								_getAccountEntriesNavigation(), "accounts"));

						dropdownItem.putData("action", "selectAccountEntries");

						PortletURL accountEntriesSelectorURL =
							liferayPortletResponse.createRenderURL();

						accountEntriesSelectorURL.setParameter(
							"mvcPath",
							"/account_users_admin/select_account_entries.jsp");
						accountEntriesSelectorURL.setParameter(
							"accountEntriesNavigation", "accounts");
						accountEntriesSelectorURL.setWindowState(
							LiferayWindowState.POP_UP);

						dropdownItem.putData(
							"accountEntriesSelectorURL",
							accountEntriesSelectorURL.toString());

						dropdownItem.putData(
							"dialogTitle",
							LanguageUtil.get(request, "select-accounts"));
						dropdownItem.putData(
							"redirectURL", currentURLObj.toString());

						dropdownItem.setLabel(
							LanguageUtil.get(request, "accounts"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(
								_getAccountEntriesNavigation(),
								"no-assigned-account"));

						dropdownItem.setLabel(
							LanguageUtil.get(request, "no-assigned-account"));

						dropdownItem.setHref(
							PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse),
							"accountEntriesNavigation", "no-assigned-account");
					});
			}
		};
	}

	private String _getFilterByAccountEntriesDropdownItemsLabel() {
		return LanguageUtil.get(request, "filter-by-accounts");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountUsersAdminManagementToolbarDisplayContext.class);

}