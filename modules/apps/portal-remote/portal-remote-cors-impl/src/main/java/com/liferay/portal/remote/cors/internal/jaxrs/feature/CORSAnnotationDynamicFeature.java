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

package com.liferay.portal.remote.cors.internal.jaxrs.feature;

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.remote.cors.annotation.CORS;
import com.liferay.portal.remote.cors.internal.CORSSupport;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(liferay.cors.annotation=true)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.CORS.Annotation.Extension"
	},
	scope = ServiceScope.PROTOTYPE, service = DynamicFeature.class
)
public class CORSAnnotationDynamicFeature implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		CORS cors = getCORS(resourceInfo);

		if (cors != null) {
			CORSSupport corsSupport = getCORSSupport(cors);

			context.register(
				new CORSPreflighContainerRequestFilter(corsSupport));
			context.register(buildCORSResponseFilter(corsSupport));
		}
	}

	protected ContainerResponseFilter buildCORSResponseFilter(
		CORSSupport corsSupport) {

		return (containerRequestContext, containerResponseContext) -> {
			MultivaluedMap<String, String> headers =
				containerRequestContext.getHeaders();

			if (corsSupport.isCORSRequest(headers::getFirst) &&
				corsSupport.isValidCORSRequest(
					containerRequestContext.getMethod(), headers::getFirst)) {

				MultivaluedMap<String, Object> responseHeaders =
					containerResponseContext.getHeaders();

				corsSupport.writeResponseHeaders(
					headers::getFirst, responseHeaders::addFirst);
			}
		};
	}

	protected CORS getCORS(ResourceInfo resourceInfo) {
		return AnnotationLocator.locate(
			resourceInfo.getResourceMethod(), resourceInfo.getResourceClass(),
			CORS.class);
	}

	protected CORSSupport getCORSSupport(CORS cors) {
		CORSSupport corsSupport = new CORSSupport();

		Map<String, String> corsHeaders = new HashMap<>();

		corsHeaders.put(
			CORSSupport.ACCESS_CONTROL_ALLOW_CREDENTIALS,
			String.valueOf(cors.allowCredentials()));
		corsHeaders.put(
			CORSSupport.ACCESS_CONTROL_ALLOW_HEADERS,
			StringUtil.merge(cors.allowHeaders(), StringPool.COMMA));
		corsHeaders.put(
			CORSSupport.ACCESS_CONTROL_ALLOW_METHODS,
			StringUtil.merge(cors.allowMethods(), StringPool.COMMA));
		corsHeaders.put(
			CORSSupport.ACCESS_CONTROL_ALLOW_ORIGIN, cors.allowOrigin());
		corsHeaders.put(
			CORSSupport.ACCESS_CONTROL_EXPOSE_HEADERS,
			StringUtil.merge(cors.exposeHeaders(), StringPool.COMMA));
		corsHeaders.put(
			CORSSupport.ACCESS_CONTROL_MAX_AGE, String.valueOf(cors.maxAge()));

		corsSupport.setCORSHeaders(corsHeaders);

		return corsSupport;
	}

	@Context
	private ResourceInfo _resourceInfo;

	@PreMatching
	private static class CORSPreflighContainerRequestFilter
		implements ContainerRequestFilter {

		public CORSPreflighContainerRequestFilter(CORSSupport corsSupport) {
			_corsSupport = corsSupport;
		}

		@Override
		public void filter(ContainerRequestContext containerRequestContext)
			throws IOException {

			MultivaluedMap<String, String> headers =
				containerRequestContext.getHeaders();

			if (_corsSupport.isCORSRequest(headers::getFirst) &&
				StringUtil.equals(
					containerRequestContext.getMethod(), "OPTIONS") &&
				_corsSupport.isValidCORSPreflightRequest(headers::getFirst)) {

				Response.ResponseBuilder responseBuilder = Response.ok();

				_corsSupport.writeResponseHeaders(
					headers::getFirst, responseBuilder::header);

				containerRequestContext.abortWith(responseBuilder.build());
			}
		}

		private final CORSSupport _corsSupport;

	}

}