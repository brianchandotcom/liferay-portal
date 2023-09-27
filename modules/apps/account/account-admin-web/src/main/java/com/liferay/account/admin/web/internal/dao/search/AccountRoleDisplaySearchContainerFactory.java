/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountRoleDisplay;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Pei-Jung Lan
 */
public class AccountRoleDisplaySearchContainerFactory {

	public static SearchContainer<AccountRoleDisplay> create(
		long accountEntryId, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<AccountRoleDisplay> searchContainer =
			new SearchContainer(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "there-are-no-roles");

		searchContainer.setId("accountRoles");
		searchContainer.setOrderByCol("name");
		searchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"account-role-order-by-type", "asc"));

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords");

		List<String> excludedRoleNames = new ArrayList<>();

		excludedRoleNames.add(
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER);

		try {
			AccountEntry accountEntry =
				AccountEntryLocalServiceUtil.getAccountEntry(accountEntryId);

			if (!AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER.equals(
					accountEntry.getType())) {

				excludedRoleNames.add(
					AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		BaseModelSearchResult<AccountRole> baseModelSearchResult =
			AccountRoleLocalServiceUtil.searchAccountRoles(
				themeDisplay.getCompanyId(),
				new long[] {
					accountEntryId, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT
				},
				keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"excludedRoleNames",
					excludedRoleNames.toArray(new String[0])
				).build(),
				searchContainer.getStart(), searchContainer.getEnd(),
				new RoleNameComparator(
					Objects.equals(searchContainer.getOrderByType(), "asc")));

		searchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				baseModelSearchResult.getBaseModels(),
				accountRole -> {
					if (!AccountRoleConstants.isImpliedRole(
							accountRole.getRole())) {

						return AccountRoleDisplay.of(accountRole);
					}

					return null;
				}),
			baseModelSearchResult.getLength());

		searchContainer.setRowChecker(
			new AccountRoleRowChecker(liferayPortletResponse));

		return searchContainer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountRoleDisplaySearchContainerFactory.class);

}