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

package com.liferay.vulcan.liferay.portal.internal;

import com.liferay.vulcan.endpoint.RootEndpoint;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.wiring.osgi.manager.ResourceManager;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class RootEndpointImpl implements RootEndpoint {

	@Override
	public <T> Try<Routes<T>> getRoutes(String path) {
		Try<Optional<Routes<T>>> optionalTry = Try.success(
			_resourceManager.getRoutes(path, _httpServletRequest));

		return optionalTry.map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			() -> new NotFoundException("No resource found for path: " + path)
		);
	}

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ResourceManager _resourceManager;

}