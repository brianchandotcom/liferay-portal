/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "indexer.class.name=com.liferay.account.model.AccountGroup",
	service = ModelDocumentContributor.class
)
public class AccountGroupModelDocumentContributor
	implements ModelDocumentContributor<AccountGroup> {

	@Override
	public void contribute(Document document, AccountGroup accountGroup) {
		document.addText(Field.DESCRIPTION, accountGroup.getDescription());
		document.addText(Field.NAME, accountGroup.getName());
		document.addKeyword(Field.TYPE, accountGroup.getType());
		document.addKeyword(
			"accountEntryIds", _getAccountEntryIds(accountGroup));
		document.addKeyword(
			"defaultAccountGroup", accountGroup.isDefaultAccountGroup());

		document.remove(Field.USER_NAME);
	}

	private Long[] _getAccountEntryIds(AccountGroup accountGroup) {
		return TransformUtil.transformToArray(
			_accountGroupRelLocalService.getAccountGroupRels(
				accountGroup.getAccountGroupId(), AccountEntry.class.getName()),
			AccountGroupRel::getClassPK, Long.class);
	}

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

}