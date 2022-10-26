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

import com.liferay.analytics.settings.rest.dto.v1_0.ContactOrganization;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Organization",
	service = {ContactOrganizationDTOConverter.class, DTOConverter.class}
)
public class ContactOrganizationDTOConverter
	implements DTOConverter<Organization, ContactOrganization> {

	@Override
	public String getContentType() {
		return ContactOrganization.class.getSimpleName();
	}

	@Override
	public ContactOrganization toDTO(
			DTOConverterContext dtoConverterContext, Organization organization)
		throws Exception {

		ContactOrganizationDTOConverterContext
			contactOrganizationDTOConverterContext =
				(ContactOrganizationDTOConverterContext)dtoConverterContext;

		return new ContactOrganization() {
			{
				id = organization.getOrganizationId();
				name = organization.getName();
				selected = contactOrganizationDTOConverterContext.isSelected(
					String.valueOf(organization.getOrganizationId()));
			}
		};
	}

}