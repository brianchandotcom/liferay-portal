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

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Account;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.account.model.AccountEntry",
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

		if ((Long)dtoConverterContext.getId() == -1) {
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

		return new Account() {
			{
				id = accountEntry.getAccountEntryId();
				logoId = accountEntry.getLogoId();
				name = accountEntry.getName();
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}