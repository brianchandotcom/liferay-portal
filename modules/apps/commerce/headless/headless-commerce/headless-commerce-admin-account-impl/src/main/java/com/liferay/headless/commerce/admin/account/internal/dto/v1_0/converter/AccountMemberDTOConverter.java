/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountMember;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountRole;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountEntryUserRel",
	service = DTOConverter.class
)
public class AccountMemberDTOConverter
	implements DTOConverter<AccountEntryUserRel, AccountMember> {

	@Override
	public String getContentType() {
		return AccountMember.class.getSimpleName();
	}

	@Override
	public AccountMember toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AccountEntryUserRel accountEntryUserRel =
			_accountEntryUserRelService.fetchAccountEntryUserRel(
				(long)dtoConverterContext.getId());

		User user = accountEntryUserRel.getUser();

		return new AccountMember() {
			{
				accountId = accountEntryUserRel.getAccountEntryId();
				accountRoles = _getAccountRoles(
					accountEntryUserRel, dtoConverterContext);
				email = user.getEmailAddress();
				name = user.getFullName();
				userId = user.getUserId();
			}
		};
	}

	private AccountRole[] _getAccountRoles(
			AccountEntryUserRel accountEntryUserRel,
			DTOConverterContext dtoConverterContext)
		throws Exception {

		List<AccountRole> accountRoles = new ArrayList<>();

		for (UserGroupRole userGroupRole :
				accountEntryUserRel.getUserGroupRoles()) {

			accountRoles.add(
				_accountRoleDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						userGroupRole.getPrimaryKey(),
						dtoConverterContext.getLocale())));
		}

		return accountRoles.toArray(new AccountRole[0]);
	}

	@Reference
	private AccountEntryUserRelService _accountEntryUserRelService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountRoleDTOConverter)"
	)
	private DTOConverter<UserGroupRole, AccountRole> _accountRoleDTOConverter;

}