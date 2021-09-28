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

import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountRole;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.portal.kernel.model.UserGroupRole",
	service = {AccountRoleDTOConverter.class, DTOConverter.class}
)
public class AccountRoleDTOConverter
	implements DTOConverter<UserGroupRole, AccountRole> {

	@Override
	public String getContentType() {
		return AccountRole.class.getSimpleName();
	}

	@Override
	public AccountRole toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		UserGroupRole userGroupRole =
			_userGroupRoleLocalService.getUserGroupRole(
				(long)dtoConverterContext.getId());

		Role role = userGroupRole.getRole();

		return new AccountRole() {
			{
				description = LanguageUtils.getLanguageIdMap(
					role.getDescriptionMap());
				name = role.getName();
				roleId = role.getRoleId();
				title = LanguageUtils.getLanguageIdMap(role.getTitleMap());
			}
		};
	}

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}