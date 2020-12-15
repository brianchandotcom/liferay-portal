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

import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountEntryPermission;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountPermission;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownGroupItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
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
import com.liferay.portal.kernel.util.ArrayUtil;
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
public class ViewAccountEntriesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewAccountEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<AccountEntryDisplay> searchContainer) {

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

				dropdownItem.putData("action", "deactivateAccountEntries");

				PortletURL deactivateAccountEntriesURL =
					liferayPortletResponse.createActionURL();

				deactivateAccountEntriesURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/update_account_entry_status");
				deactivateAccountEntriesURL.setParameter(
					Constants.CMD, Constants.DEACTIVATE);
				deactivateAccountEntriesURL.setParameter(
					"navigation", getNavigation());

				dropdownItem.putData(
					"deactivateAccountEntriesURL",
					deactivateAccountEntriesURL.toString());

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

				dropdownItem.putData("action", "activateAccountEntries");

				PortletURL activateAccountEntriesURL =
					liferayPortletResponse.createActionURL();

				activateAccountEntriesURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/update_account_entry_status");
				activateAccountEntriesURL.setParameter(
					Constants.CMD, Constants.RESTORE);
				activateAccountEntriesURL.setParameter(
					"navigation", getNavigation());

				dropdownItem.putData(
					"activateAccountEntriesURL",
					activateAccountEntriesURL.toString());

				dropdownItem.setIcon("undo");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "activate"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			},
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "deleteAccountEntries");

				PortletURL deleteAccountEntriesURL =
					liferayPortletResponse.createActionURL();

				deleteAccountEntriesURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/delete_account_entry");
				deleteAccountEntriesURL.setParameter(
					"navigation", getNavigation());

				dropdownItem.putData(
					"deleteAccountEntriesURL",
					deleteAccountEntriesURL.toString());

				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);

				return dropdownItem;
			});
	}

	public List<String> getAvailableActions(
			AccountEntryDisplay accountEntryDisplay)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!AccountEntryPermission.contains(
				themeDisplay.getPermissionChecker(),
				accountEntryDisplay.getAccountEntryId(), ActionKeys.DELETE)) {

			return availableActions;
		}

		if (accountEntryDisplay.isActive()) {
			availableActions.add("deactivateAccountEntries");
		}
		else {
			availableActions.add("activateAccountEntries");
		}

		availableActions.add("deleteAccountEntries");

		return availableActions;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("navigation", (String)null);
		clearResultsURL.setParameter("type", (String)null);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "accountEntriesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					liferayPortletResponse.createRenderURL(),
					"mvcRenderCommandName", "/account_admin/edit_account_entry",
					"backURL", currentURLObj.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-account"));
			}
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "ACCOUNT_ENTRIES_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		List<DropdownItem> filterDropdownItems = super.getFilterDropdownItems();

		addFilterTypeDropdownItems(filterDropdownItems);

		return filterDropdownItems;
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			() -> !Objects.equals(getNavigation(), "active"),
			labelItem -> {
				PortletURL removeLabelURL = getPortletURL();

				removeLabelURL.setParameter("navigation", (String)null);

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);

				String label = String.format(
					"%s: %s", LanguageUtil.get(httpServletRequest, "status"),
					LanguageUtil.get(httpServletRequest, getNavigation()));

				labelItem.setLabel(label);
			}
		).add(
			() -> !Objects.equals(getType(), "all"),
			labelItem -> {
				PortletURL removeLabelURL = getPortletURL();

				removeLabelURL.setParameter("type", (String)null);

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);

				String label = String.format(
					"%s: %s", LanguageUtil.get(httpServletRequest, "type"),
					LanguageUtil.get(httpServletRequest, getType()));

				labelItem.setLabel(label);
			}
		).build();
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

		return AccountPermission.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			AccountActionKeys.ADD_ACCOUNT_ENTRY);
	}

	protected void addFilterTypeDropdownItems(
		List<DropdownItem> filterDropdownItems) {

		DropdownGroupItem filterDropdownItemsGroup = new DropdownGroupItem();

		filterDropdownItemsGroup.setDropdownItems(
			getDropdownItems(
				getDefaultEntriesMap(getFilterByTypeKeys()), getPortletURL(),
				"type", getType()));
		filterDropdownItemsGroup.setLabel(
			LanguageUtil.get(httpServletRequest, "filter-by-type"));

		filterDropdownItems.add(1, filterDropdownItemsGroup);
	}

	protected String[] getFilterByTypeKeys() {
		return ArrayUtil.append(
			new String[] {"all"}, AccountConstants.ACCOUNT_ENTRY_TYPES,
			new String[] {"guest"});
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
		return new String[] {"name"};
	}

	protected String getType() {
		return ParamUtil.getString(liferayPortletRequest, "type", "all");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewAccountEntriesManagementToolbarDisplayContext.class);

}