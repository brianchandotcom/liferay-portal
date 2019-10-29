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
public class ViewUsersManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewUsersManagementToolbarDisplayContext(
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

		clearResultsURL.setParameter("accountEntryIds", StringPool.BLANK);
		clearResultsURL.setParameter("accountNavigation", "all");
		clearResultsURL.setParameter("navigation", (String)null);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getDefaultEventHandler() {
		return "USERS_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		DropdownItemList filterDropdownItems = new DropdownItemList() {
			{
				List<DropdownItem> filterAccountsDropdownItems =
					_getFilterAccountsDropdownItems();

				if (filterAccountsDropdownItems != null) {
					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getFilterAccountsDropdownItems());
							dropdownGroupItem.setLabel(
								_getFilterAccountsDropdownItemsLabel());
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
				String accountNavigation = ParamUtil.getString(
					request, "accountNavigation", "all");

				if (accountNavigation.equals("accounts")) {
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

	private List<DropdownItem> _getFilterAccountsDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getNavigation(), "all"));

						dropdownItem.setLabel(LanguageUtil.get(request, "all"));

						dropdownItem.setHref(
							PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse),
							"accountNavigation", "all");
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getNavigation(), "accounts"));

						PortletURL accountSelectorURL =
							liferayPortletResponse.createRenderURL();

						accountSelectorURL.setParameter(
							"mvcPath", "/select_accounts.jsp");
						accountSelectorURL.setParameter(
							"accountNavigation", "accounts");
						accountSelectorURL.setWindowState(
							LiferayWindowState.POP_UP);

						dropdownItem.putData(
							"redirectURL", currentURLObj.toString());
						dropdownItem.putData(
							"accountSelectorURL",
							accountSelectorURL.toString());
						dropdownItem.putData("action", "selectAccount");

						dropdownItem.setLabel(
							LanguageUtil.get(request, "accounts"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(
								getNavigation(), "no-assigned-account"));

						dropdownItem.setLabel(
							LanguageUtil.get(request, "no-assigned-account"));

						dropdownItem.setHref(
							PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse),
							"accountNavigation", "no-assigned-account");
					});
			}
		};
	}

	private String _getFilterAccountsDropdownItemsLabel() {
		return LanguageUtil.get(request, "filter-by-accounts");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewUsersManagementToolbarDisplayContext.class);

}