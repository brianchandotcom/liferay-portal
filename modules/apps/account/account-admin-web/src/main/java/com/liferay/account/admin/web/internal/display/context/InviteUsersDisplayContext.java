/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.display.context;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.MultiselectItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.MultiselectItemBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class InviteUsersDisplayContext {

	public List<MultiselectItem> getAvailableAccountRolesMultiselectItems(
		long accountEntryId, long companyId) {

		BaseModelSearchResult<AccountRole> baseModelSearchResult =
			AccountRoleLocalServiceUtil.searchAccountRoles(
				companyId,
				new long[] {
					accountEntryId, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT
				},
				null,
				LinkedHashMapBuilder.<String, Object>put(
					"excludedRoleNames",
					new String[] {
						AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER
					}
				).build(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new RoleNameComparator(true));

		List<MultiselectItem> multiselectItems = new ArrayList<>();

		for (AccountRole accountRole : baseModelSearchResult.getBaseModels()) {
			multiselectItems.add(
				MultiselectItemBuilder.setLabel(
					accountRole::getRoleName
				).setValue(
					String.valueOf(accountRole.getAccountRoleId())
				).build());
		}

		return multiselectItems;
	}

}