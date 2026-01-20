/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	property = "indexer.class.name=com.liferay.account.model.AccountRole",
	service = ModelDocumentContributor.class
)
public class AccountRoleModelDocumentContributor
	implements ModelDocumentContributor<AccountRole> {

	@Override
	public void contribute(Document document, AccountRole accountRole) {
		RoleAccountRoleDocumentContributorUtil.contribue(document, accountRole);
	}

}