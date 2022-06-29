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

package com.liferay.portal.vulcan.internal.jaxrs.writer.interceptor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionThreadLocal;
import com.liferay.portal.vulcan.internal.extension.ExtensionProviders;
import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 * @author Javier de Arcos
 */
@Provider
public class EntityExtensionWriterInterceptor implements WriterInterceptor {

	@Override
	public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
		throws IOException {

		Map<String, Serializable> extendedProperties =
			EntityExtensionThreadLocal.getExtendedProperties();
		ExtensionProviders extensionProviders = _getExtensionProviders(
			writerInterceptorContext.getType(),
			writerInterceptorContext.getMediaType());

		try {
			if ((extendedProperties != null) && (extensionProviders != null)) {
				extensionProviders.setExtendedProperties(
					_company.getCompanyId(),
					writerInterceptorContext.getEntity(), extendedProperties);
			}

			if (extensionProviders != null) {
				_extendEntity(extensionProviders, writerInterceptorContext);
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new WebApplicationException(exception);
		}

		writerInterceptorContext.proceed();
	}

	private void _extendEntity(
			ExtensionProviders extensionProviders,
			WriterInterceptorContext writerInterceptorContext)
		throws Exception {

		writerInterceptorContext.setEntity(
			ExtendedEntity.extend(
				writerInterceptorContext.getEntity(),
				extensionProviders.getExtendedProperties(
					_company.getCompanyId(),
					writerInterceptorContext.getEntity()),
				extensionProviders.getFilteredPropertyNames(
					_company.getCompanyId(),
					writerInterceptorContext.getEntity())));

		writerInterceptorContext.setGenericType(ExtendedEntity.class);
	}

	private ExtensionProviders _getExtensionProviders(
		Class<?> clazz, MediaType mediaType) {

		ContextResolver<ExtensionProviders> extensionProvidersContextResolver =
			_providers.getContextResolver(ExtensionProviders.class, mediaType);

		if (extensionProvidersContextResolver == null) {
			return null;
		}

		return extensionProvidersContextResolver.getContext(clazz);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EntityExtensionWriterInterceptor.class);

	@Context
	private Company _company;

	@Context
	private Providers _providers;

}