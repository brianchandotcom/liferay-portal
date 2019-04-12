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

import com.liferay.headless.admin.user.dto.v1_0.Email;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.EmailUtil;
import com.liferay.headless.admin.user.resource.v1_0.EmailResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/email.properties",
	scope = ServiceScope.PROTOTYPE, service = EmailResource.class
)
public class EmailResourceImpl extends BaseEmailResourceImpl {

	@Override
	public Email getEmail(Long emailId) throws Exception {
		return EmailUtil.toEmail(_emailAddressService.getEmailAddress(emailId));
	}

	@Override
	public Page<Email> getOrganizationEmailsPage(Long organizationId)
		throws Exception {

		Organization organization = _organizationService.getOrganization(
			organizationId);

		return Page.of(
			transform(
				_emailAddressService.getEmailAddresses(
					organization.getModelClassName(),
					organization.getOrganizationId()),
				EmailUtil::toEmail));
	}

	@Override
	public Page<Email> getUserAccountEmailsPage(Long userAccountId)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		return Page.of(
			transform(
				_emailAddressService.getEmailAddresses(
					Contact.class.getName(), user.getContactId()),
				EmailUtil::toEmail));
	}

	@Reference
	private EmailAddressService _emailAddressService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private UserService _userService;

}