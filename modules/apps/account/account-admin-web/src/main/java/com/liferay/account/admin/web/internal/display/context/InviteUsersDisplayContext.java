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