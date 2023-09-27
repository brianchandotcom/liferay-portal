/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.EmailAddressUtil;
import com.liferay.headless.admin.user.resource.v1_0.EmailAddressResource;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/email-address.properties",
	scope = ServiceScope.PROTOTYPE, service = EmailAddressResource.class
)
public class EmailAddressResourceImpl extends BaseEmailAddressResourceImpl {

	@Override
	public EmailAddress getEmailAddress(Long emailAddressId) throws Exception {
		return EmailAddressUtil.toEmailAddress(
			_emailAddressService.getEmailAddress(emailAddressId));
	}

	@Override
	public Page<EmailAddress> getOrganizationEmailAddressesPage(
			String organizationId)
		throws Exception {

		Organization organization = _organizationResourceDTOConverter.getObject(
			organizationId);

		return Page.of(
			transform(
				_emailAddressService.getEmailAddresses(
					organization.getModelClassName(),
					organization.getOrganizationId()),
				EmailAddressUtil::toEmailAddress));
	}

	@Override
	public Page<EmailAddress> getUserAccountEmailAddressesPage(
			Long userAccountId)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		return Page.of(
			transform(
				_emailAddressService.getEmailAddresses(
					Contact.class.getName(), user.getContactId()),
				EmailAddressUtil::toEmailAddress));
	}

	@Reference
	private EmailAddressService _emailAddressService;

	@Reference(
		target = DTOConverterConstants.ORGANIZATION_RESOURCE_DTO_CONVERTER
	)
	private DTOConverter
		<Organization, com.liferay.headless.admin.user.dto.v1_0.Organization>
			_organizationResourceDTOConverter;

	@Reference
	private UserService _userService;

}