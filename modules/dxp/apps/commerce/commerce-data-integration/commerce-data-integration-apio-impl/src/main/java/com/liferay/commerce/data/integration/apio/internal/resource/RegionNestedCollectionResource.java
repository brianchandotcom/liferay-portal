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
import com.liferay.commerce.data.integration.apio.identifiers.RegionIdentifier;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
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
public class RegionNestedCollectionResource
        implements NestedCollectionResource<CommerceRegion, Long,
        RegionIdentifier, Long, CountryIdentifier> {

    @Override
    public NestedCollectionRoutes<CommerceRegion, Long, Long> collectionRoutes(NestedCollectionRoutes.Builder<CommerceRegion, Long, Long> builder) {
        return builder.addGetter(
            this::_getPageItems
        ).build();
    }

    @Override
    public String getName() {
        return "regions";
    }

    @Override
    public Representor<CommerceRegion> representor(Representor.Builder<CommerceRegion, Long> builder) {
        return builder.types(
            "Region"
        ).identifier(
            CommerceRegion::getCommerceRegionId
        ).addBidirectionalModel(
            "country", "regions", CountryIdentifier.class,
            CommerceRegion::getCommerceCountryId
        ).addString(
            "name",
            CommerceRegion::getName
        ).addString(
            "code",
            CommerceRegion::getCode
        ).build();
    }

    @Override
    public ItemRoutes<CommerceRegion, Long> itemRoutes(ItemRoutes.Builder<CommerceRegion, Long> builder) {
        return builder.addGetter(
            this::_getCommerceRegion
        ).build();
    }

    private PageItems<CommerceRegion> _getPageItems(
            Pagination pagination, Long commerceCountryId) {

        List<CommerceRegion> countries = _commerceRegionService.getCommerceRegions(
            commerceCountryId, pagination.getStartPosition(),
            pagination.getEndPosition(), null);

        int count = _commerceRegionService.getCommerceRegionsCount(commerceCountryId);

        return new PageItems<>(countries, count);
    }

    private CommerceRegion _getCommerceRegion(Long commerceRegionId) {
        try {
            return _commerceRegionService.getCommerceRegion(commerceRegionId);
        }
        catch (PortalException pe) {
            throw new ServerErrorException(500, pe);
        }
    }

    @Reference
    private CommerceRegionService _commerceRegionService;

}
