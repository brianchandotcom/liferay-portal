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
	service = {ContactUserGroupDTOConverter.class, DTOConverter.class}
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