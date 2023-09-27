/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.dto.v1_0.converter;

import com.liferay.analytics.settings.rest.dto.v1_0.ContactUserGroup;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.UserGroup",
	service = DTOConverter.class
)
public class ContactUserGroupDTOConverter
	implements DTOConverter<UserGroup, ContactUserGroup> {

	@Override
	public String getContentType() {
		return ContactUserGroup.class.getSimpleName();
	}

	@Override
	public ContactUserGroup toDTO(
			DTOConverterContext dtoConverterContext, UserGroup userGroup)
		throws Exception {

		ContactUserGroupDTOConverterContext
			contactUserGroupDTOConverterContext =
				(ContactUserGroupDTOConverterContext)dtoConverterContext;

		return new ContactUserGroup() {
			{
				id = userGroup.getUserGroupId();
				name = userGroup.getName();
				selected = contactUserGroupDTOConverterContext.isSelected(
					String.valueOf(userGroup.getUserGroupId()));
			}
		};
	}

}