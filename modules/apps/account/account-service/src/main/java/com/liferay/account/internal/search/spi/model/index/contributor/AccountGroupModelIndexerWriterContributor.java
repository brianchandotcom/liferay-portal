/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Drew Brokke
 */
public class AccountGroupModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<AccountGroup> {

	public AccountGroupModelIndexerWriterContributor(
		AccountGroupLocalService accountGroupLocalService) {

		_accountGroupLocalService = accountGroupLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _accountGroupLocalService.getIndexableActionableDynamicQuery();
	}

	private final AccountGroupLocalService _accountGroupLocalService;

}