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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Account;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.account.model.CommerceAccount",
	service = {AccountDTOConverter.class, DTOConverter.class}
)
public class AccountDTOConverter
	implements DTOConverter<CommerceAccount, Account> {

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	public Account toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.getCommerceAccount(
				(Long)dtoConverterContext.getId());

		ExpandoBridge expandoBridge = commerceAccount.getExpandoBridge();

		return new Account() {
			{
				customFields = expandoBridge.getAttributes();
				emailAddress = commerceAccount.getEmail();
				externalReferenceCode =
					commerceAccount.getExternalReferenceCode();
				id = commerceAccount.getCommerceAccountId();
				logoId = commerceAccount.getLogoId();
				name = commerceAccount.getName();
				root = commerceAccount.isRoot();
				taxId = commerceAccount.getTaxId();
				type = commerceAccount.getType();
			}
		};
	}

	@Reference
	private CommerceAccountService _commerceAccountService;

}