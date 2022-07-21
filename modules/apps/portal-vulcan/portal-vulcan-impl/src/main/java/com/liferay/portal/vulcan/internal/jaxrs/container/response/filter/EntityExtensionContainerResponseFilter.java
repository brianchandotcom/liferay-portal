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

package com.liferay.portal.vulcan.internal.jaxrs.container.response.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionThreadLocal;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

/**
 * @author Carlos Correa
 */
@Provider
public class EntityExtensionContainerResponseFilter
	implements ContainerResponseFilter {

	@Override
	public void filter(
			ContainerRequestContext containerRequestContext,
			ContainerResponseContext containerResponseContext)
		throws IOException {

		Map<String, Serializable> extendedProperties =
			EntityExtensionThreadLocal.getExtendedProperties();

		if (extendedProperties == null) {
			return;
		}

		MediaType mediaType = containerResponseContext.getMediaType();

		ContextResolver<EntityExtensionHandler> contextResolver =
			_providers.getContextResolver(
				EntityExtensionHandler.class, mediaType);

		if (contextResolver == null) {
			return;
		}

		EntityExtensionHandler entityExtensionHandler =
			_getEntityExtensionHandler(
				containerResponseContext.getEntityClass(), contextResolver,
				mediaType);

		if (entityExtensionHandler == null) {
			return;
		}

		try {
			entityExtensionHandler.setExtendedProperties(
				_company.getCompanyId(), containerResponseContext.getEntity(),
				extendedProperties);
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new WebApplicationException(exception);
		}
	}

	private EntityExtensionHandler _getEntityExtensionHandler(
		Class<?> clazz, ContextResolver<EntityExtensionHandler> contextResolver,
		MediaType mediaType) {

		if (clazz == null) {
			return null;
		}

		EntityExtensionHandler entityExtensionHandler =
			contextResolver.getContext(clazz);

		if (entityExtensionHandler == null) {
			entityExtensionHandler = _getEntityExtensionHandler(
				clazz.getSuperclass(), contextResolver, mediaType);
		}

		return entityExtensionHandler;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EntityExtensionContainerResponseFilter.class);

	@Context
	private Company _company;

	@Context
	private Providers _providers;

}