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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.IOException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
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
public class PageEntityExtensionWriterInterceptor implements WriterInterceptor {

	@Override
	public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
		throws IOException {

		if (Page.class.isAssignableFrom(writerInterceptorContext.getType())) {
			ParameterizedType parameterizedType =
				(ParameterizedType)writerInterceptorContext.getGenericType();

			Type entityType = parameterizedType.getActualTypeArguments()[0];

			EntityExtensionHandler entityExtensionHandler =
				_getEntityExtensionHandler(
					(Class)entityType, writerInterceptorContext.getMediaType());

			if (entityExtensionHandler != null) {
				_extendPageEntities(
					entityExtensionHandler, writerInterceptorContext);
			}
		}

		writerInterceptorContext.proceed();
	}

	private void _extendPageEntities(
		EntityExtensionHandler entityExtensionHandler,
		WriterInterceptorContext writerInterceptorContext) {

		Page<?> page = (Page<?>)writerInterceptorContext.getEntity();

		List<ExtendedEntity> extendedEntities = new ArrayList<>();

		try {
			for (Object item : page.getItems()) {
				extendedEntities.add(
					ExtendedEntity.extend(
						item,
						entityExtensionHandler.getExtendedProperties(
							_company.getCompanyId(), item),
						entityExtensionHandler.getFilteredPropertyNames(
							_company.getCompanyId(), item)));
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new WebApplicationException(exception);
		}

		Pagination pagination = Pagination.of(
			GetterUtil.getInteger(page.getPage()),
			GetterUtil.getInteger(page.getPageSize()));

		writerInterceptorContext.setEntity(
			Page.of(
				page.getActions(), extendedEntities, pagination,
				page.getTotalCount()));

		writerInterceptorContext.setGenericType(
			new GenericType<Page<ExtendedEntity>>() {
			}.getType());
	}

	private EntityExtensionHandler _getEntityExtensionHandler(
		Class<?> clazz, MediaType mediaType) {

		ContextResolver<EntityExtensionHandler> contextResolver =
			_providers.getContextResolver(
				EntityExtensionHandler.class, mediaType);

		if (contextResolver == null) {
			return null;
		}

		return contextResolver.getContext(clazz);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageEntityExtensionWriterInterceptor.class);

	@Context
	private Company _company;

	@Context
	private Providers _providers;

}