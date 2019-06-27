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

import com.liferay.headless.admin.user.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.PostalAddressUtil;
import com.liferay.headless.admin.user.resource.v1_0.PostalAddressResource;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.CommonPermissionUtil;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/postal-address.properties",
	scope = ServiceScope.PROTOTYPE, service = PostalAddressResource.class
)
public class PostalAddressResourceImpl extends BasePostalAddressResourceImpl {

	@Override
	public Page<PostalAddress> getOrganizationPostalAddressesPage(
			Long organizationId)
		throws Exception {

		Organization organization = _organizationService.getOrganization(
			organizationId);

		return Page.of(
			transform(
				_addressLocalService.getAddresses(
					contextCompany.getCompanyId(),
					organization.getModelClassName(),
					organization.getOrganizationId()),
				address -> PostalAddressUtil.toPostalAddress(
					address, contextAcceptLanguage.getPreferredLocale())));
	}

	@Override
	public PostalAddress getPostalAddress(Long postalAddressId)
		throws Exception {

		return PostalAddressUtil.toPostalAddress(
			_addressService.getAddress(postalAddressId),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public Page<PostalAddress> getUserAccountPostalAddressesPage(
			Long userAccountId)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		CommonPermissionUtil.check(
			PermissionThreadLocal.getPermissionChecker(),
			user.getModelClassName(), user.getUserId(), ActionKeys.VIEW);

		return Page.of(
			transform(
				_addressLocalService.getAddresses(
					user.getCompanyId(), Contact.class.getName(),
					user.getContactId()),
				address -> PostalAddressUtil.toPostalAddress(
					address, contextAcceptLanguage.getPreferredLocale())));
	}

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AddressService _addressService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private UserService _userService;

}