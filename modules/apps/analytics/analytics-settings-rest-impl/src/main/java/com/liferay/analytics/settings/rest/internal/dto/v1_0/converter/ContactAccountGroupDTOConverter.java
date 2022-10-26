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

package com.liferay.analytics.settings.rest.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountGroup;
import com.liferay.analytics.settings.rest.dto.v1_0.ContactAccountGroup;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountGroup",
	service = {ContactAccountGroupDTOConverter.class, DTOConverter.class}
)
public class ContactAccountGroupDTOConverter
	implements DTOConverter<AccountGroup, ContactAccountGroup> {

	@Override
	public String getContentType() {
		return ContactAccountGroup.class.getSimpleName();
	}

	@Override
	public ContactAccountGroup toDTO(
			DTOConverterContext dtoConverterContext, AccountGroup accountGroup)
		throws Exception {

		ContactAccountGroupDTOConverterContext
			contactAccountGroupDTOConverterContext =
				(ContactAccountGroupDTOConverterContext)dtoConverterContext;

		return new ContactAccountGroup() {
			{
				id = accountGroup.getAccountGroupId();
				name = accountGroup.getName();
				selected = contactAccountGroupDTOConverterContext.isSelected(
					String.valueOf(accountGroup.getAccountGroupId()));
			}
		};
	}

}