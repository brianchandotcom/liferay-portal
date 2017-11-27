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

package com.liferay.vulcan.sample.liferay.portal.resource.internal;

import com.liferay.portal.kernel.model.Company;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.CollectionResource;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.Representor.Builder;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.resource.identifier.RootIdentifier;
import com.liferay.vulcan.sample.liferay.portal.website.WebSite;
import com.liferay.vulcan.sample.liferay.portal.website.WebSiteService;

import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/WebSite">WebSite </a> resources through a web API.
 * The resources are mapped from the internal model {@link WebSite}.
 *
 * @author Victor Oliveira
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class WebSiteCollectionResource
	implements CollectionResource<WebSite, LongIdentifier> {

	@Override
	public Representor<WebSite, LongIdentifier> buildRepresentor(
		Representor.Builder<WebSite, LongIdentifier> representorBuilder) {

		return representorBuilder.identifier(
			WebSite::getWebSiteLongIdentifier
		).addLocalizedString(
			"name", WebSite::getName
		).addString(
			"description", WebSite::getDescription
		).addType(
			"WebSite"
		).build();
	}

	@Override
	public String getName() {
		return "web-sites";
	}

	@Override
	public Routes<WebSite> routes(
		RoutesBuilder<WebSite, LongIdentifier> routesBuilder) {

		return routesBuilder.addCollectionPageGetter(
			this::_getPageItems, RootIdentifier.class, Company.class
		).addCollectionPageItemGetter(
			this::_getWebSite
		).build();
	}

	private PageItems<WebSite> _getPageItems(
		Pagination pagination, RootIdentifier rootIdentifier, Company company) {

		return _webSiteService.getPageItems(pagination, company.getCompanyId());
	}

	private WebSite _getWebSite(LongIdentifier webSiteLongIdentifier) {
		Optional<WebSite> optional = _webSiteService.getWebSite(
			webSiteLongIdentifier.getId());

		return optional.orElseThrow(
			() -> new NotFoundException(
				"Unable to get website " + webSiteLongIdentifier.getId()));
	}

	@Reference
	private WebSiteService _webSiteService;

}