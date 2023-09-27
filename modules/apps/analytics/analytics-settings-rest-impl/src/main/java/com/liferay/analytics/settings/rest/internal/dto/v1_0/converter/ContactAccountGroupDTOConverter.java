/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	service = DTOConverter.class
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