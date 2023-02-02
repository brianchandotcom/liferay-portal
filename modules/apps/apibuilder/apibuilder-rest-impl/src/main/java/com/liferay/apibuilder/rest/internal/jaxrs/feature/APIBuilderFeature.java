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

package com.liferay.apibuilder.rest.internal.jaxrs.feature;

import com.liferay.apibuilder.operation.provider.OperationProvider;
import com.liferay.apibuilder.rest.internal.jaxrs.context.provider.OperationContextProvider;
import com.liferay.portal.kernel.util.Portal;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Carlos Correa
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.extension.select=\\(osgi.jaxrs.name=Liferay.APIBuilder.Feature\\))",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.APIBuilder.Feature"
	},
	scope = ServiceScope.PROTOTYPE, service = Feature.class
)
public class APIBuilderFeature implements Feature {

	@Override
	public boolean configure(FeatureContext featureContext) {
		featureContext.register(
			new OperationContextProvider(_operationProvider, _portal));

		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private OperationProvider _operationProvider;

	@Reference
	private Portal _portal;

}