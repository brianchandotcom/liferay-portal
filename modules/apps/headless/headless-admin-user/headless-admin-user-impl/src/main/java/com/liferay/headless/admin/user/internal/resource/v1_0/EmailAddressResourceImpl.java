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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.EmailAddressUtil;
import com.liferay.headless.admin.user.resource.v1_0.EmailAddressResource;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserService;
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
			Long organizationId)
		throws Exception {

		Organization organization = _organizationService.getOrganization(
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

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private UserService _userService;

}