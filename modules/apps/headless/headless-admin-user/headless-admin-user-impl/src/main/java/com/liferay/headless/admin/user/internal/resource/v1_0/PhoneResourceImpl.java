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

import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PhoneUtil;
import com.liferay.headless.admin.user.resource.v1_0.PhoneResource;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/phone.properties",
	scope = ServiceScope.PROTOTYPE, service = PhoneResource.class
)
public class PhoneResourceImpl extends BasePhoneResourceImpl {

	@Override
	public Page<Phone> getOrganizationPhonesPage(Long organizationId)
		throws Exception {

		Organization organization = _organizationService.getOrganization(
			organizationId);

		return Page.of(
			transform(
				_phoneService.getPhones(
					organization.getModelClassName(),
					organization.getOrganizationId()),
				PhoneUtil::toPhone));
	}

	@Override
	public Phone getPhone(Long phoneId) throws Exception {
		return PhoneUtil.toPhone(_phoneService.getPhone(phoneId));
	}

	@Override
	public Page<Phone> getUserAccountPhonesPage(Long userAccountId)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		return Page.of(
			transform(
				_phoneService.getPhones(
					Contact.class.getName(), user.getContactId()),
				PhoneUtil::toPhone));
	}

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private PhoneService _phoneService;

	@Reference
	private UserService _userService;

}