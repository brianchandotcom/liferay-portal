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

import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryOrganizationRelService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountOrganization;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountEntryOrganizationRel",
	service = DTOConverter.class
)
public class AccountOrganizationDTOConverter
	implements DTOConverter<AccountEntryOrganizationRel, AccountOrganization> {

	@Override
	public String getContentType() {
		return AccountOrganization.class.getSimpleName();
	}

	@Override
	public AccountOrganization toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AccountEntryOrganizationRel accountEntryOrganizationRel =
			_accountEntryOrganizationRelService.
				fetchAccountEntryOrganizationRel(
					(long)dtoConverterContext.getId());

		Organization organization =
			accountEntryOrganizationRel.getOrganization();

		return new AccountOrganization() {
			{
				accountId = accountEntryOrganizationRel.getAccountEntryId();
				name = organization.getName();
				organizationId = organization.getOrganizationId();
				treePath = organization.getTreePath();
			}
		};
	}

	@Reference
	private AccountEntryOrganizationRelService
		_accountEntryOrganizationRelService;

}