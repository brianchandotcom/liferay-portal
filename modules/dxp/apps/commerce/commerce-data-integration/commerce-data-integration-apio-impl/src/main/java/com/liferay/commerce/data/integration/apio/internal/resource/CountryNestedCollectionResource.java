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
import com.liferay.commerce.data.integration.apio.identifiers.CountryIdentifier;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.ServerErrorException;
import java.util.List;
import java.util.Locale;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class CountryNestedCollectionResource
        implements NestedCollectionResource<CommerceCountry, Long,
                CountryIdentifier, Long, WebSiteIdentifier> {

    @Override
    public NestedCollectionRoutes<CommerceCountry, Long, Long> collectionRoutes(NestedCollectionRoutes.Builder<CommerceCountry, Long, Long> builder) {
        return builder.addGetter(
            this::_getPageItems
        ).build();
    }

    @Override
    public String getName() {
        return "countries";
    }

    @Override
    public Representor<CommerceCountry> representor(Representor.Builder<CommerceCountry, Long> builder) {
        return builder.types(
                "Country"
        ).identifier(
            CommerceCountry::getCommerceCountryId
        ).addBidirectionalModel(
            "webSite", "countries", WebSiteIdentifier.class,
            CommerceCountry::getGroupId
        ).addLocalizedStringByLocale(
            "name",
            this::_getName
        ).addString(
            "threeLettersISOCode",
            CommerceCountry::getThreeLettersISOCode
        ).addNumber(
            "numericISOCode",
            CommerceCountry::getNumericISOCode
        ).addBoolean(
            "billingAllowed",
            CommerceCountry::getBillingAllowed
        ).addBoolean(
            "ShippingAllowed",
            CommerceCountry::getShippingAllowed
        ).build();
    }

    private String _getName(CommerceCountry commerceCountry, Locale locale) {
        return commerceCountry.getName(locale);
    }

    @Override
    public ItemRoutes<CommerceCountry, Long> itemRoutes(ItemRoutes.Builder<CommerceCountry, Long> builder) {
        return builder.addGetter(
                this::_getCommerceCountry
        ).build();
    }

    private PageItems<CommerceCountry> _getPageItems(
            Pagination pagination, Long webSiteId) {

        List<CommerceCountry> countries = _commerceCountryService.getCommerceCountries(
            webSiteId, pagination.getStartPosition(),
            pagination.getEndPosition(), null);

        int count = _commerceCountryService.getCommerceCountriesCount(webSiteId);

        return new PageItems<>(countries, count);
    }

    private CommerceCountry _getCommerceCountry(Long commerceCountryId) {
        try {
            return _commerceCountryService.getCommerceCountry(commerceCountryId);
        }
        catch (PortalException pe) {
            throw new ServerErrorException(500, pe);
        }
    }

    @Reference
    private CommerceCountryService _commerceCountryService;

}
