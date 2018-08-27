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

package com.liferay.address.apio.internal.architect.router;

import com.liferay.address.apio.architect.identifier.AddressIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.organization.apio.architect.identifier.OrganizationIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.OrganizationService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Address">Address</a> resources contained inside an <a
 * href="http://schema.org/Organization">Organization</a> through a web API. The
 * resources are mapped from the internal model {@link Address}.
 *
 * @author Javier Gamarra
 * @review
 */
@Component(immediate = true)
public class OrganizationAddressNestedCollectionRouter
	implements NestedCollectionRouter
		<Address, Long, AddressIdentifier, Long, OrganizationIdentifier> {

	@Override
	public NestedCollectionRoutes<Address, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Address, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<Address> _getPageItems(
			Pagination pagination, long organizationId)
		throws PortalException {

		Organization organization = _organizationService.getOrganization(
			organizationId);

		List<Address> addresses = _addressService.getAddresses(
			organization.getModelClassName(), organization.getOrganizationId());

		return new PageItems<>(addresses, addresses.size());
	}

	@Reference
	private AddressService _addressService;

	@Reference
	private OrganizationService _organizationService;

}