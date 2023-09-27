/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.account.exception.NoSuchGroupException;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupService;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderRuleAccountGroupUtil {

	public static COREntryRel addCOREntryAccountGroupRel(
			AccountGroupService accountGroupService,
			COREntryRelService corEntryRelService, COREntry corEntry,
			OrderRuleAccountGroup orderRuleAccountGroup)
		throws PortalException {

		AccountGroup accountGroup = null;

		if (Validator.isNull(
				orderRuleAccountGroup.getAccountGroupExternalReferenceCode())) {

			accountGroup = accountGroupService.getAccountGroup(
				orderRuleAccountGroup.getAccountGroupId());
		}
		else {
			accountGroup =
				accountGroupService.fetchAccountGroupByExternalReferenceCode(
					orderRuleAccountGroup.
						getAccountGroupExternalReferenceCode(),
					corEntry.getCompanyId());

			if (accountGroup == null) {
				String accountGroupExternalReferenceCode =
					orderRuleAccountGroup.
						getAccountGroupExternalReferenceCode();

				throw new NoSuchGroupException(
					"Unable to find account group with external reference " +
						"code " + accountGroupExternalReferenceCode);
			}
		}

		return corEntryRelService.addCOREntryRel(
			AccountGroup.class.getName(), accountGroup.getAccountGroupId(),
			corEntry.getCOREntryId());
	}

}