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
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
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
		SearchContainer<AccountUserDisplay> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemList.of(
			() -> {
				if (Objects.equals(getNavigation(), "inactive")) {
					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "deactivateAccountUsers");

				PortletURL deactivateAccountUsersURL =
					liferayPortletResponse.createActionURL();

				deactivateAccountUsersURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/edit_account_users");
				deactivateAccountUsersURL.setParameter(
					Constants.CMD, Constants.DEACTIVATE);
				deactivateAccountUsersURL.setParameter(
					"navigation", getNavigation());
				deactivateAccountUsersURL.setParameter(
					"accountEntriesNavigation", _getAccountEntriesNavigation());
				deactivateAccountUsersURL.setParameter(
					"accountEntryIds",
					ParamUtil.getString(httpServletRequest, "accountEntryIds"));

				dropdownItem.putData(
					"deactivateAccountUsersURL",
					deactivateAccountUsersURL.toString());

				dropdownItem.setIcon("hidden");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "deactivate"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			},
			() -> {
				if (Objects.equals(getNavigation(), "active")) {
					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "activateAccountUsers");

				PortletURL activateAccountUsersURL =
					liferayPortletResponse.createActionURL();

				activateAccountUsersURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/edit_account_users");
				activateAccountUsersURL.setParameter(
					Constants.CMD, Constants.RESTORE);
				activateAccountUsersURL.setParameter(
					"navigation", getNavigation());
				activateAccountUsersURL.setParameter(
					"accountEntriesNavigation", _getAccountEntriesNavigation());
				activateAccountUsersURL.setParameter(
					"accountEntryIds",
					ParamUtil.getString(httpServletRequest, "accountEntryIds"));

				dropdownItem.putData(
					"activateAccountUsersURL",
					activateAccountUsersURL.toString());

				dropdownItem.setIcon("undo");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "activate"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			},
			() -> {
				if (Objects.equals(getNavigation(), "active")) {
					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "deleteAccountUsers");

				PortletURL deleteAccountUsersURL =
					liferayPortletResponse.createActionURL();

				deleteAccountUsersURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/edit_account_users");
				deleteAccountUsersURL.setParameter(
					Constants.CMD, Constants.DELETE);
				deleteAccountUsersURL.setParameter(
					"navigation", getNavigation());
				deleteAccountUsersURL.setParameter(
					"accountEntriesNavigation", _getAccountEntriesNavigation());
				deleteAccountUsersURL.setParameter(
					"accountEntryIds",
					ParamUtil.getString(httpServletRequest, "accountEntryIds"));

				dropdownItem.putData(
					"deleteAccountUsersURL", deleteAccountUsersURL.toString());

				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			});
	}

	public List<String> getAvailableActions(
			AccountUserDisplay accountUserDisplay)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!UserPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				accountUserDisplay.getUserId(), ActionKeys.DELETE)) {

			return availableActions;
		}

		if (Objects.equals(
				accountUserDisplay.getStatus(),
				WorkflowConstants.STATUS_APPROVED)) {

			availableActions.add("deactivateAccountUsers");
		}
		else {
			availableActions.add("activateAccountUsers");
			availableActions.add("deleteAccountUsers");
		}

		return availableActions;
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

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addAccountUser");

				PortletURL accountEntrySelectorURL =
					liferayPortletResponse.createRenderURL();

				accountEntrySelectorURL.setParameter(
					"mvcPath", "/account_users_admin/select_account_entry.jsp");
				accountEntrySelectorURL.setParameter("singleSelect", "true");
				accountEntrySelectorURL.setWindowState(
					LiferayWindowState.POP_UP);

				dropdownItem.putData(
					"accountEntrySelectorURL",
					accountEntrySelectorURL.toString());

				PortletURL addAccountUserURL =
					liferayPortletResponse.createRenderURL();

				addAccountUserURL.setParameter(
					"mvcRenderCommandName", "/account_admin/add_account_user");
				addAccountUserURL.setParameter(
					"backURL",
					String.valueOf(liferayPortletResponse.createRenderURL()));

				dropdownItem.putData(
					"addAccountUserURL", addAccountUserURL.toString());

				dropdownItem.putData(
					"dialogTitle",
					LanguageUtil.get(httpServletRequest, "select-an-account"));

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-user"));
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "ACCOUNT_USERS_ADMIN_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		List<DropdownItem> filterAccountEntriesDropdownItems =
			_getFilterByAccountEntriesDropdownItems();

		DropdownItemList filterDropdownItems = DropdownItemListBuilder.addGroup(
			() -> filterAccountEntriesDropdownItems != null,
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					filterAccountEntriesDropdownItems);
				dropdownGroupItem.setLabel(
					_getFilterByAccountEntriesDropdownItemsLabel());
			}
		).build();

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
						httpServletRequest, "accountEntryIds");

					for (long accountEntryId : accountEntryIds) {
						AccountEntry accountEntry =
							AccountEntryLocalServiceUtil.fetchAccountEntry(
								accountEntryId);

						add(
							labelItem -> {
								PortletURL removeLabelURL = getPortletURL();

								long[] newAccountEntryIds = ArrayUtil.remove(
									accountEntryIds, accountEntryId);

								if (newAccountEntryIds.length == 0) {
									removeLabelURL.setParameter(
										"accountEntriesNavigation",
										(String)null);
								}

								removeLabelURL.setParameter(
									"accountEntryIds",
									StringUtil.merge(
										newAccountEntryIds, StringPool.COMMA));

								labelItem.putData(
									"removeLabelURL",
									removeLabelURL.toString());

								labelItem.setCloseable(true);

								labelItem.setLabel(
									LanguageUtil.get(
										httpServletRequest,
										accountEntry.getName()));
							});
					}
				}

				if (Objects.equals(
						_getAccountEntriesNavigation(),
						"no-assigned-account")) {

					add(
						labelItem -> {
							PortletURL removeLabelURL = getPortletURL();

							removeLabelURL.setParameter(
								"accountEntriesNavigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							labelItem.setLabel(
								LanguageUtil.get(
									httpServletRequest, "no-assigned-account"));
						});
				}

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
								"%s: %s",
								LanguageUtil.get(httpServletRequest, "status"),
								LanguageUtil.get(
									httpServletRequest, getNavigation()));

							labelItem.setLabel(label);
						});
				}
			}
		};
	}

	@Override
	public String getFilterNavigationDropdownItemsLabel() {
		return LanguageUtil.get(httpServletRequest, "filter-by-status");
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
		return false;
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
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

	private String _getAccountEntriesNavigation() {
		return ParamUtil.getString(
			liferayPortletRequest, "accountEntriesNavigation", "all");
	}

	private List<DropdownItem> _getFilterByAccountEntriesDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(_getAccountEntriesNavigation(), "all"));

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "all"));

				dropdownItem.setHref(
					PortletURLUtil.clone(currentURLObj, liferayPortletResponse),
					"accountEntriesNavigation", "all");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(_getAccountEntriesNavigation(), "accounts"));

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
					LanguageUtil.get(httpServletRequest, "select-accounts"));
				dropdownItem.putData("redirectURL", currentURLObj.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "accounts"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(
						_getAccountEntriesNavigation(), "no-assigned-account"));

				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "no-assigned-account"));

				dropdownItem.setHref(
					PortletURLUtil.clone(currentURLObj, liferayPortletResponse),
					"accountEntriesNavigation", "no-assigned-account");
			}
		).build();
	}

	private String _getFilterByAccountEntriesDropdownItemsLabel() {
		return LanguageUtil.get(httpServletRequest, "filter-by-accounts");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountUsersAdminManagementToolbarDisplayContext.class);

}