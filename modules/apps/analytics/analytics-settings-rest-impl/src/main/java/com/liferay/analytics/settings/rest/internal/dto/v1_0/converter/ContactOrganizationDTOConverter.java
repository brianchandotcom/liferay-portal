/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	service = DTOConverter.class
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