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

package com.liferay.frontend.taglib.clay.internal.application;

import com.liferay.frontend.taglib.clay.internal.data.provider.ClayDataSetDataProviderResource;
import com.liferay.frontend.taglib.clay.internal.data.provider.PaginationContextProvider;
import com.liferay.frontend.taglib.clay.internal.data.provider.SortContextProvider;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/frontend-taglib-clay/app",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Frontend.Clay",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true", "liferay.oauth2=false"
	},
	service = Application.class
)
public class FrontendClayApplication extends Application {

	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(_clayDataSetDataProviderResource);
		singletons.add(_paginationContextProvider);
		singletons.add(_sortContextProvider);

		return singletons;
	}

	@Reference
	private ClayDataSetDataProviderResource _clayDataSetDataProviderResource;

	@Reference
	private PaginationContextProvider _paginationContextProvider;

	@Reference
	private SortContextProvider _sortContextProvider;

}