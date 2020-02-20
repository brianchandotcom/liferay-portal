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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

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
		SearchContainer searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

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
					ParamUtil.getString(request, "accountEntryIds"));

				dropdownItem.putData(
					"deactivateAccountUsersURL",
					deactivateAccountUsersURL.toString());

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
					ParamUtil.getString(request, "accountEntryIds"));

				dropdownItem.putData(
					"activateAccountUsersURL",
					activateAccountUsersURL.toString());

				dropdownItem.setIcon("undo");
				dropdownItem.setLabel(LanguageUtil.get(request, "activate"));
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
					ParamUtil.getString(request, "accountEntryIds"));

				dropdownItem.putData(
					"deleteAccountUsersURL", deleteAccountUsersURL.toString());

				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(LanguageUtil.get(request, "delete"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			});
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
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addAccountUser");

				PortletURL accountEntrySelectorURL =
					liferayPortletResponse.createRenderURL();

				accountEntrySelectorURL.setParameter(
					"mvcPath", "/account_users_admin/select_account_entry.jsp");
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
					LanguageUtil.get(request, "select-an-account"));

				dropdownItem.setLabel(LanguageUtil.get(request, "add-user"));
			}
		).build();
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
										request, accountEntry.getName()));
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
									request, "no-assigned-account"));
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