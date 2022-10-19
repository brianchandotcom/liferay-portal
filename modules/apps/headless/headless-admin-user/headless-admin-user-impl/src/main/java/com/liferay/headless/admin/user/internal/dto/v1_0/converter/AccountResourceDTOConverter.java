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

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.headless.admin.user.dto.v1_0.Account;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountEntry",
	service = {AccountResourceDTOConverter.class, DTOConverter.class}
)
public class AccountResourceDTOConverter
	implements DTOConverter<AccountEntry, Account> {

	public long getAccountEntryId(String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry = getObject(externalReferenceCode);

		return accountEntry.getAccountEntryId();
	}

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public AccountEntry getObject(String externalReferenceCode)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				CompanyThreadLocal.getCompanyId(), externalReferenceCode);

		if (accountEntry == null) {
			accountEntry = _accountEntryLocalService.getAccountEntry(
				GetterUtil.getLong(externalReferenceCode));
		}

		return accountEntry;
	}

	@Override
	public Account toDTO(
		DTOConverterContext dtoConverterContext, AccountEntry accountEntry) {

		if (accountEntry == null) {
			return null;
		}

		return new Account() {
			{
				actions = dtoConverterContext.getActions();
				description = accountEntry.getDescription();
				domains = StringUtil.split(accountEntry.getDomains());
				externalReferenceCode = accountEntry.getExternalReferenceCode();
				id = accountEntry.getAccountEntryId();
				name = accountEntry.getName();
				numberOfUsers =
					(int)
						_accountEntryUserRelLocalService.
							getAccountEntryUserRelsCountByAccountEntryId(
								accountEntry.getAccountEntryId());
				organizationIds = TransformUtil.transformToArray(
					_accountEntryOrganizationRelLocalService.
						getAccountEntryOrganizationRels(
							accountEntry.getAccountEntryId()),
					AccountEntryOrganizationRel::getOrganizationId, Long.class);
				parentAccountId = accountEntry.getParentAccountEntryId();
				status = accountEntry.getStatus();
				type = Account.Type.create(accountEntry.getType());
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

}