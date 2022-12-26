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

package com.liferay.portal.remote.jaxrs.whiteboard.jaxb.json.internal;

import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "org.apache.aries.jax.rs.jackson",
	property = {
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
		JaxrsWhiteboardConstants.JAX_RS_MEDIA_TYPE + "=" + MediaType.APPLICATION_JSON,
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=jaxb-json",
		"service.ranking:Integer=" + Integer.MIN_VALUE
	},
	service = {}
)
public class JacksonJsonProviderRegistrator {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_serviceRegistration = bundleContext.registerService(
			new String[] {
				MessageBodyReader.class.getName(),
				MessageBodyWriter.class.getName()
			},
			new JacksonJsonProviderPrototypeServiceFactory(),
			HashMapDictionaryBuilder.create(
				properties
			).put(
				"jackson.jaxb.version",
				String.valueOf(
					com.fasterxml.jackson.module.jaxb.PackageVersion.VERSION)
			).put(
				"jackson.jaxrs.json.version",
				String.valueOf(
					com.fasterxml.jackson.jaxrs.json.PackageVersion.VERSION)
			).build());
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private ServiceRegistration<?> _serviceRegistration;

}