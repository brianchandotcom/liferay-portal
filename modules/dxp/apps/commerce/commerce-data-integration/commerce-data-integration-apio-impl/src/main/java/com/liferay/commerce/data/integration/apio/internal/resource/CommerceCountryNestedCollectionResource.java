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
import com.liferay.commerce.data.integration.apio.identifiers.CommerceCountryIdentifier;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class CommerceCountryNestedCollectionResource
	implements NestedCollectionResource<CommerceCountry, Long,
		CommerceCountryIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<CommerceCountry, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<CommerceCountry, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "commerce-country";
	}

	@Override
	public ItemRoutes<CommerceCountry, Long> itemRoutes(
		ItemRoutes.Builder<CommerceCountry, Long> builder) {

		return builder.addGetter(
			this::_getCommerceCountry
		).build();
	}

	@Override
	public Representor<CommerceCountry> representor(
		Representor.Builder<CommerceCountry, Long> builder) {

		return builder.types(
			"CommerceCountry"
		).identifier(
			CommerceCountry::getCommerceCountryId
		).addBidirectionalModel(
			"webSite", "commerceCountries", WebSiteIdentifier.class,
			CommerceCountry::getGroupId
		).addLocalizedStringByLocale(
			"name", this::_getName
		).addString(
			"threeLettersISOCode", CommerceCountry::getThreeLettersISOCode
		).addNumber(
			"numericISOCode", CommerceCountry::getNumericISOCode
		).addBoolean(
			"billingAllowed", CommerceCountry::getBillingAllowed
		).addBoolean(
			"shippingAllowed", CommerceCountry::getShippingAllowed
		).build();
	}

	private CommerceCountry _getCommerceCountry(Long commerceCountryId) throws PortalException {
		return _commerceCountryService.getCommerceCountry(
			commerceCountryId);
	}

	private String _getName(CommerceCountry commerceCountry, Locale locale) {
		return commerceCountry.getName(locale);
	}

	private PageItems<CommerceCountry> _getPageItems(
		Pagination pagination, Long webSiteId) {

		List<CommerceCountry> commerceCountries =
			_commerceCountryService.getCommerceCountries(
				webSiteId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		int total = _commerceCountryService.getCommerceCountriesCount(
			webSiteId);

		return new PageItems<>(commerceCountries, total);
	}

	@Reference
	private CommerceCountryService _commerceCountryService;

}