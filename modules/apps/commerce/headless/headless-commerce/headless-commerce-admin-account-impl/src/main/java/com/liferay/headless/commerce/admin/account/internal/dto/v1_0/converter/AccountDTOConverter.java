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

package com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.account.dto.v1_0.Account;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.account.model.CommerceAccount",
	service = {AccountDTOConverter.class, DTOConverter.class}
)
public class AccountDTOConverter
	implements DTOConverter<CommerceAccount, Account> {

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public Account toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceAccount commerceAccount;

		if ((Long)dtoConverterContext.getId() == -1) {
			User user = dtoConverterContext.getUser();

			if (user == null) {
				user = _userLocalService.getUserById(
					PrincipalThreadLocal.getUserId());
			}

			commerceAccount =
				_commerceAccountLocalService.getGuestCommerceAccount(
					user.getCompanyId());
		}
		else {
			commerceAccount = _commerceAccountService.getCommerceAccount(
				(Long)dtoConverterContext.getId());
		}

		ExpandoBridge expandoBridge = commerceAccount.getExpandoBridge();

		return new Account() {
			{
				active = commerceAccount.isActive();
				customFields = expandoBridge.getAttributes();
				dateCreated = commerceAccount.getCreateDate();
				dateModified = commerceAccount.getModifiedDate();
				defaultBillingAccountAddressId =
					commerceAccount.getDefaultBillingAddressId();
				defaultShippingAccountAddressId =
					commerceAccount.getDefaultShippingAddressId();
				emailAddresses = new String[] {commerceAccount.getEmail()};
				externalReferenceCode =
					commerceAccount.getExternalReferenceCode();
				id = commerceAccount.getCommerceAccountId();
				logoId = commerceAccount.getLogoId();
				logoURL = _getLogoURL(commerceAccount.getLogoId());
				name = commerceAccount.getName();
				root = commerceAccount.isRoot();
				taxId = commerceAccount.getTaxId();
				type = commerceAccount.getType();
			}
		};
	}

	private String _getLogoURL(long logoId) {
		return StringBundler.concat(
			"/image/organization_logo?img_id=", logoId, "&t=",
			WebServerServletTokenUtil.getToken(logoId));
	}

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private UserLocalService _userLocalService;

}