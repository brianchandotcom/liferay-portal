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

import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountMember;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountRole;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.account.model.CommerceAccountUserRel",
	service = {AccountMemberDTOConverter.class, DTOConverter.class}
)
public class AccountMemberDTOConverter
	implements DTOConverter<CommerceAccountUserRel, AccountMember> {

	@Override
	public String getContentType() {
		return AccountMember.class.getSimpleName();
	}

	@Override
	public AccountMember toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceAccountUserRel commerceAccountUserRel =
			_commerceAccountUserRelService.getCommerceAccountUserRel(
				(CommerceAccountUserRelPK)dtoConverterContext.getId());

		User user = commerceAccountUserRel.getUser();

		return new AccountMember() {
			{
				accountId = commerceAccountUserRel.getCommerceAccountId();
				accountRoles = _getAccountRoles(
					commerceAccountUserRel, dtoConverterContext);
				email = user.getEmailAddress();
				name = user.getFullName();
				userId = user.getUserId();
			}
		};
	}

	private AccountRole[] _getAccountRoles(
			CommerceAccountUserRel commerceAccountUserRel,
			DTOConverterContext dtoConverterContext)
		throws Exception {

		List<AccountRole> accountRoles = new ArrayList<>();

		for (UserGroupRole userGroupRole :
				commerceAccountUserRel.getUserGroupRoles()) {

			accountRoles.add(
				_accountRoleDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						userGroupRole.getPrimaryKey(),
						dtoConverterContext.getLocale())));
		}

		Stream<AccountRole> stream = accountRoles.stream();

		return stream.toArray(AccountRole[]::new);
	}

	@Reference
	private AccountRoleDTOConverter _accountRoleDTOConverter;

	@Reference
	private CommerceAccountUserRelService _commerceAccountUserRelService;

}