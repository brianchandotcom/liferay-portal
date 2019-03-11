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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.resource.v1_0.PostalAddressResource;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.permission.CommonPermissionUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.ws.rs.core.Context;

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
			Long organizationId, Pagination pagination)
		throws Exception {

		Organization organization = _organizationService.getOrganization(
			organizationId);

		return Page.of(
			transform(
				_addressLocalService.getAddresses(
					contextCompany.getCompanyId(),
					organization.getModelClassName(),
					organization.getOrganizationId()),
				this::_toPostalAddress));
	}

	@Override
	public PostalAddress getPostalAddress(Long postalAddressId)
		throws Exception {

		return _toPostalAddress(_addressService.getAddress(postalAddressId));
	}

	@Override
	public Page<PostalAddress> getUserAccountPostalAddressesPage(
			Long userAccountId, Pagination pagination)
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
				this::_toPostalAddress));
	}

	private PostalAddress _toPostalAddress(Address address) {
		ListType listType = address.getType();

		return new PostalAddress() {
			{
				addressLocality = address.getCity();
				addressType = listType.getName();
				id = address.getAddressId();
				postalCode = address.getZip();
				streetAddressLine1 = address.getStreet1();
				streetAddressLine2 = address.getStreet2();
				streetAddressLine3 = address.getStreet3();

				setAddressCountry(
					() -> {
						if (address.getCountryId() <= 0) {
							return null;
						}

						Country country = address.getCountry();

						return country.getName(
							contextAcceptLanguage.getPreferredLocale());
					});
				setAddressRegion(
					() -> {
						if (address.getRegionId() <= 0) {
							return null;
						}

						Region region = address.getRegion();

						return region.getName();
					});
			}
		};
	}

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AddressService _addressService;

	@Reference
	private OrganizationService _organizationService;

	@Context
	private User _user;

	@Reference
	private UserService _userService;

}