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

package com.liferay.vulcan.application.internal;

import com.liferay.vulcan.endpoint.RootEndpoint;
import com.liferay.vulcan.provider.pagination.PaginationParamsContextProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.ext.ContextProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra
 * @author Jorge Ferrer
 */
@ApplicationPath("/")
@Component(immediate = true, service = Application.class)
public class VulcanApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

		classes.add(PaginationParamsContextProvider.class);

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(_rootEndpoint);

		singletons.addAll(_contextProviders);

		return singletons;
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(vulcan-context-provider=true)"
	)
	private List<ContextProvider> _contextProviders;

	@Reference
	private RootEndpoint _rootEndpoint;

}