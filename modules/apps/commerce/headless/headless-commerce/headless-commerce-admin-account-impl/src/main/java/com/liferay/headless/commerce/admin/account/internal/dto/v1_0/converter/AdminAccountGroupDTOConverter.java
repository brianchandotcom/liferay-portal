/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AdminAccountGroup;
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
public class AdminAccountGroupDTOConverter
	implements DTOConverter<AccountGroup, AdminAccountGroup> {

	@Override
	public String getContentType() {
		return AdminAccountGroup.class.getSimpleName();
	}

	@Override
	public AdminAccountGroup toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AccountGroup accountGroup = _accountGroupService.getAccountGroup(
			(Long)dtoConverterContext.getId());

		ExpandoBridge expandoBridge = accountGroup.getExpandoBridge();

		return new AdminAccountGroup() {
			{
				customFields = expandoBridge.getAttributes();
				externalReferenceCode = accountGroup.getExternalReferenceCode();
				id = accountGroup.getAccountGroupId();
				name = accountGroup.getName();
			}
		};
	}

	@Reference
	private AccountGroupService _accountGroupService;

}