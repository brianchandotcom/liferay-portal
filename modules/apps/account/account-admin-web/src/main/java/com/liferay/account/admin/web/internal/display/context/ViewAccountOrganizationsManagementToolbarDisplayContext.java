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
import com.liferay.account.service.AccountEntryOrganizationRelLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ViewAccountOrganizationsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ViewAccountOrganizationsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuUtil.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "selectAccountOrganizations");

				long accountEntryId = ParamUtil.getLong(
					request, "accountEntryId");

				AccountEntry accountEntry =
					AccountEntryLocalServiceUtil.fetchAccountEntry(
						accountEntryId);

				if (accountEntry != null) {
					dropdownItem.putData(
						"accountEntryName", accountEntry.getName());
				}

				PortletURL assignAccountOrganizationsURL =
					liferayPortletResponse.createActionURL();

				assignAccountOrganizationsURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/account_admin/assign_account_organizations");
				assignAccountOrganizationsURL.setParameter(
					"redirect", currentURLObj.toString());

				dropdownItem.putData(
					"assignAccountOrganizationsURL",
					assignAccountOrganizationsURL.toString());

				PortletURL selectAccountOrganizationsURL =
					liferayPortletResponse.createRenderURL();

				selectAccountOrganizationsURL.setParameter(
					"mvcPath",
					"/account_entries_admin/select_account_organizations.jsp");
				selectAccountOrganizationsURL.setParameter(
					"accountEntryId", String.valueOf(accountEntryId));
				selectAccountOrganizationsURL.setWindowState(
					LiferayWindowState.POP_UP);

				dropdownItem.putData(
					"selectAccountOrganizationsURL",
					selectAccountOrganizationsURL.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(request, "assign-organizations"));
			});
	}

	@Override
	public String getDefaultEventHandler() {
		return "ACCOUNT_ORGANIZATIONS_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
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
			AccountEntryOrganizationRelLocalServiceUtil.
				getAccountEntryOrganizationRelsCount(accountEntryId);

		if (count > 0) {
			return false;
		}

		return true;
	}

	@Override
	protected String getOrderByCol() {
		return ParamUtil.getString(
			liferayPortletRequest, getOrderByColParam(), "name");
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name"};
	}

}