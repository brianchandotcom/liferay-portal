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

package com.liferay.commerce.data.integration.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.CommerceAccountIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.CommerceAddressIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.CommerceCountryIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.CommerceRegionIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.CommerceAddressCreatorForm;
import com.liferay.commerce.data.integration.apio.internal.util.ServiceContextHelper;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class CommerceAddressNestedCollectionResource
	implements
		NestedCollectionResource<CommerceAddress, Long, CommerceAddressIdentifier, Long,
                CommerceAccountIdentifier> {

	@Override
	public NestedCollectionRoutes<CommerceAddress, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CommerceAddress, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addCommerceAddress,
			_hasPermission.forAddingIn(CommerceAddressIdentifier.class),
			CommerceAddressCreatorForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "commerce-address";
	}

	@Override
	public ItemRoutes<CommerceAddress, Long> itemRoutes(
		ItemRoutes.Builder<CommerceAddress, Long> builder) {

		return builder.addGetter(
			_commerceAddressService::getCommerceAddress
		).addRemover(
			_commerceAddressService::deleteCommerceAddress,
			_hasPermission::forDeleting
		).addUpdater(
			this::_updateCommerceAddress,
			_hasPermission::forUpdating,
			CommerceAddressCreatorForm::buildForm
		).build();
	}

	@Override
	public Representor<CommerceAddress> representor(
		Representor.Builder<CommerceAddress, Long> builder) {

		return builder.types(
			"CommerceAddress"
		).identifier(
			CommerceAddress::getCommerceAddressId
		).addBidirectionalModel(
			"commerceAccount", "commerceAddresses", CommerceAccountIdentifier.class,
			CommerceAddress::getClassPK
		).addString(
			"name", CommerceAddress::getName
		).addString(
			"description", CommerceAddress::getDescription
		).addString(
			"street1", CommerceAddress::getStreet1
		).addString(
			"street2", CommerceAddress::getStreet2
		).addString(
			"street3", CommerceAddress::getStreet3
		).addString(
			"city", CommerceAddress::getCity
		).addString(
			"zip", CommerceAddress::getZip
		).addLinkedModel(
			"commerceCountry", CommerceCountryIdentifier.class,
			CommerceAddress::getCommerceCountryId
		).addLinkedModel(
			"commerceRegion", CommerceRegionIdentifier.class,
			CommerceAddress::getCommerceRegionId
		).addNumber(
			"latitude", CommerceAddress::getLatitude
		).addNumber(
			"longitude", CommerceAddress::getLongitude
		).addString(
			"phoneNumber", CommerceAddress::getPhoneNumber
		).addBoolean(
			"defaultBilling", CommerceAddress::getDefaultBilling
		).addBoolean(
			"defaultShipping", CommerceAddress::getDefaultShipping
		).build();
	}

	private CommerceAddress _addCommerceAddress(
			Long organizationId, CommerceAddressCreatorForm commerceAddressCreatorForm)
		throws PortalException {

		Organization organization = _commerceOrganizationService.getOrganization(
			organizationId);

		Group group = organization.getGroup();

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			group.getGroupId());

		return _commerceAddressService.addCommerceAddress(
			group.getClassName(), group.getClassPK(), commerceAddressCreatorForm.getName(),
			commerceAddressCreatorForm.getDescription(), commerceAddressCreatorForm.getStreet1(),
			commerceAddressCreatorForm.getStreet2(), commerceAddressCreatorForm.getStreet3(),
			commerceAddressCreatorForm.getCity(), commerceAddressCreatorForm.getZip(),
			commerceAddressCreatorForm.getRegionId(), commerceAddressCreatorForm.getCountryId(),
			commerceAddressCreatorForm.getPhoneNumber(), commerceAddressCreatorForm.getDefaultBilling(),
			commerceAddressCreatorForm.getDefaultShipping(), serviceContext);
	}

	private PageItems<CommerceAddress> _getPageItems(
		Pagination pagination, Long organizationId) throws PortalException {

		Organization organization = _commerceOrganizationService.getOrganization(
			organizationId);

		Group group = organization.getGroup();

		List<CommerceAddress> commerceAddresses =
			_commerceAddressService.getCommerceAddresses(
				group.getGroupId(), group.getClassName(),
				group.getClassPK());

		int total = _commerceAddressService.getCommerceAddressesCount(
			group.getGroupId(), group.getClassName(), group.getClassPK());

		return new PageItems<>(commerceAddresses, total);
	}

	private CommerceAddress _updateCommerceAddress(
			Long commerceAddressId, CommerceAddressCreatorForm commerceAddressCreatorForm)
		throws PortalException {

		return _commerceAddressService.updateCommerceAddress(
			commerceAddressId, commerceAddressCreatorForm.getName(),
			commerceAddressCreatorForm.getDescription(), commerceAddressCreatorForm.getStreet1(),
			commerceAddressCreatorForm.getStreet2(), commerceAddressCreatorForm.getStreet3(),
			commerceAddressCreatorForm.getCity(), commerceAddressCreatorForm.getZip(),
			commerceAddressCreatorForm.getRegionId(), commerceAddressCreatorForm.getCountryId(),
			commerceAddressCreatorForm.getPhoneNumber(), commerceAddressCreatorForm.getDefaultBilling(),
			commerceAddressCreatorForm.getDefaultShipping(), new ServiceContext());
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference(
			target = "(model.class.name=com.liferay.commerce.model.CommerceAddress)"
	)
	private HasPermission<Long> _hasPermission;

}