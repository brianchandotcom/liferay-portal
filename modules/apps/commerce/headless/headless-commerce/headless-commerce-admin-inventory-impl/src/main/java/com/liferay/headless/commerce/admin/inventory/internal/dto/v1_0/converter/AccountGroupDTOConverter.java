/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter;

import com.liferay.account.service.AccountGroupService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.AccountGroup;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountGroup",
	service = DTOConverter.class
)
public class AccountGroupDTOConverter
	implements DTOConverter
		<com.liferay.account.model.AccountGroup, AccountGroup> {

	@Override
	public String getContentType() {
		return AccountGroup.class.getSimpleName();
	}

	@Override
	public AccountGroup toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		com.liferay.account.model.AccountGroup accountGroup =
			_accountGroupService.getAccountGroup(
				(Long)dtoConverterContext.getId());

		return new AccountGroup() {
			{
				setId(accountGroup::getAccountGroupId);
				setName(accountGroup::getName);
			}
		};
	}

	@Reference
	private AccountGroupService _accountGroupService;

}