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

package com.liferay.headless.commerce.admin.order.internal.util.v1_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupService;
import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
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
			accountGroup = accountGroupService.fetchByExternalReferenceCode(
				corEntry.getCompanyId(),
				orderRuleAccountGroup.getAccountGroupExternalReferenceCode());

			if (accountGroup == null) {
				String accountGroupExternalReferenceCode =
					orderRuleAccountGroup.
						getAccountGroupExternalReferenceCode();

				throw new NoSuchAccountGroupException(
					"Unable to find account group with external reference " +
						"code " + accountGroupExternalReferenceCode);
			}
		}

		return corEntryRelService.addCOREntryRel(
			AccountGroup.class.getName(), accountGroup.getAccountGroupId(),
			corEntry.getCOREntryId());
	}

}