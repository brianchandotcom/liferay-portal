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

package com.liferay.web.url.apio.internal.architect.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.web.url.apio.architect.identifier.WebUrlIdentifier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code WebSite} resources
 * through a web API.
 *
 * @author Javier Gamarra
 * @review
 */
@Component(immediate = true)
public class WebUrlCollectionResource
	implements ItemResource<Website, Long, WebUrlIdentifier> {

	@Override
	public String getName() {
		return "web-urls";
	}

	@Override
	public ItemRoutes<Website, Long> itemRoutes(
		ItemRoutes.Builder<Website, Long> builder) {

		return builder.addGetter(
			_websiteService::getWebsite
		).build();
	}

	@Override
	public Representor<Website> representor(
		Representor.Builder<Website, Long> builder) {

		return builder.types(
			"WebUrl"
		).identifier(
			Website::getWebsiteId
		).addString(
			"urlType",
			website -> Try.fromFallible(
				website::getType
			).map(
				ListType::getName
			).orElse(
				null
			)
		).addString(
			"url", Website::getUrl
		).build();
	}

	@Reference
	private WebsiteService _websiteService;

}