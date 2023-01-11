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

package com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Account;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountEntry",
	service = {AccountDTOConverter.class, DTOConverter.class}
)
public class AccountDTOConverter
	implements DTOConverter<AccountEntry, Account> {

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public Account toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AccountEntry accountEntry;

		if ((Long)dtoConverterContext.getId() ==
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST) {

			User user = dtoConverterContext.getUser();

			if (user == null) {
				user = _userLocalService.getUserById(
					PrincipalThreadLocal.getUserId());
			}

			accountEntry = _accountEntryLocalService.getGuestAccountEntry(
				user.getCompanyId());
		}
		else {
			accountEntry = _accountEntryLocalService.getAccountEntry(
				(Long)dtoConverterContext.getId());
		}

		ExpandoBridge expandoBridge = accountEntry.getExpandoBridge();

		return new Account() {
			{
				customFields = expandoBridge.getAttributes();
				emailAddress = accountEntry.getEmailAddress();
				externalReferenceCode = accountEntry.getExternalReferenceCode();
				id = accountEntry.getAccountEntryId();
				logoId = accountEntry.getLogoId();
				name = accountEntry.getName();
				root =
					accountEntry.getParentAccountEntryId() ==
						AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT;
				taxId = accountEntry.getTaxIdNumber();
				type = _toCommerceAccountType(accountEntry.getType());
			}
		};
	}

	private Integer _toCommerceAccountType(String accountEntryType) {
		if (Objects.equals(
				accountEntryType,
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS)) {

			return CommerceAccountConstants.ACCOUNT_TYPE_BUSINESS;
		}
		else if (Objects.equals(
					accountEntryType,
					AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST)) {

			return CommerceAccountConstants.ACCOUNT_TYPE_GUEST;
		}
		else if (Objects.equals(
					accountEntryType,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON)) {

			return CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL;
		}

		return CommerceAccountConstants.ACCOUNT_TYPE_GUEST;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private UserLocalService _userLocalService;

}