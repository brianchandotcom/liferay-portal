/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Drew Brokke
 */
public class AccountRoleModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<AccountRole> {

	public AccountRoleModelIndexerWriterContributor(
		AccountRoleLocalService accountRoleLocalService) {

		_accountRoleLocalService = accountRoleLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _accountRoleLocalService.getIndexableActionableDynamicQuery();
	}

	private final AccountRoleLocalService _accountRoleLocalService;

}