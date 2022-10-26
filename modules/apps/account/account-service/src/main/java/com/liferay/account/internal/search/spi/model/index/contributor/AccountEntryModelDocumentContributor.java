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

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRelModel;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "indexer.class.name=com.liferay.account.model.AccountEntry",
	service = ModelDocumentContributor.class
)
public class AccountEntryModelDocumentContributor
	implements ModelDocumentContributor<AccountEntry> {

	@Override
	public void contribute(Document document, AccountEntry accountEntry) {
		document.addText(Field.DESCRIPTION, accountEntry.getDescription());
		document.addText(Field.NAME, accountEntry.getName());
		document.addKeyword(Field.STATUS, accountEntry.getStatus());

		String type = accountEntry.getType();

		document.addKeyword(Field.TYPE, type);

		document.addKeyword("accountEntryId", accountEntry.getAccountEntryId());
		document.addKeyword(
			"accountGroupIds", _getAccountGroupIds(accountEntry));

		long[] accountUserIds = _getAccountUserIds(accountEntry);

		document.addKeyword("accountUserIds", accountUserIds);
		document.addKeyword(
			"allowNewUserMembership",
			_isAllowNewUserMembership(accountUserIds, type));

		document.addKeyword("domains", _getDomains(accountEntry));
		document.addKeyword(
			"externalReferenceCode", accountEntry.getExternalReferenceCode());
		document.addKeyword(
			"organizationIds", _getOrganizationIds(accountEntry));
		document.addKeyword(
			"parentAccountEntryId", accountEntry.getParentAccountEntryId());
		document.addText("taxIdNumber", accountEntry.getTaxIdNumber());
		document.remove(Field.USER_NAME);
	}

	private long[] _getAccountGroupIds(AccountEntry accountEntry) {
		return ListUtil.toLongArray(
			_accountGroupRelLocalService.getAccountGroupRels(
				AccountEntry.class.getName(), accountEntry.getAccountEntryId()),
			AccountGroupRel::getAccountGroupId);
	}

	private long[] _getAccountUserIds(AccountEntry accountEntry) {
		return ListUtil.toLongArray(
			_accountUserRetriever.getAccountUsers(
				accountEntry.getAccountEntryId()),
			User.USER_ID_ACCESSOR);
	}

	private String[] _getDomains(AccountEntry accountEntry) {
		return ArrayUtil.toStringArray(
			StringUtil.split(accountEntry.getDomains(), CharPool.COMMA));
	}

	private long[] _getOrganizationIds(AccountEntry accountEntry) {
		return ListUtil.toLongArray(
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(
					accountEntry.getAccountEntryId()),
			AccountEntryOrganizationRelModel::getOrganizationId);
	}

	private boolean _isAllowNewUserMembership(
		long[] accountUserIds, String type) {

		if (Objects.equals(type, AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON) &&
			ArrayUtil.isNotEmpty(accountUserIds)) {

			return false;
		}

		return true;
	}

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private AccountUserRetriever _accountUserRetriever;

}