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

package com.liferay.apio.architect.sample.liferay.portal.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSite;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSiteService;
import com.liferay.portal.kernel.model.Company;

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
	implements CollectionResource<WebSite, Long> {

	@Override
	public CollectionRoutes<WebSite> collectionRoutes(
		CollectionRoutes.Builder<WebSite> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "web-sites";
	}

	@Override
	public ItemRoutes<WebSite> itemRoutes(
		ItemRoutes.Builder<WebSite, Long> builder) {

		return builder.addGetter(
			this::_getWebSite
		).build();
	}

	@Override
	public Representor<WebSite, Long> representor(
		Representor.Builder<WebSite, Long> builder) {

		return builder.types(
			"WebSite"
		).identifier(
			WebSite::getWebSiteId
		).addLocalizedString(
			"name", WebSite::getName
		).addString(
			"description", WebSite::getDescription
		).build();
	}

	private PageItems<WebSite> _getPageItems(
		Pagination pagination, Company company) {

		return _webSiteService.getPageItems(pagination, company.getCompanyId());
	}

	private WebSite _getWebSite(Long webSiteId) {
		Optional<WebSite> optional = _webSiteService.getWebSite(webSiteId);

		return optional.orElseThrow(
			() -> new NotFoundException("Unable to get website " + webSiteId));
	}

	@Reference
	private WebSiteService _webSiteService;

}