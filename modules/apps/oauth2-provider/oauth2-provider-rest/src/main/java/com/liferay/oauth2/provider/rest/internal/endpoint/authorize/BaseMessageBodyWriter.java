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

package com.liferay.oauth2.provider.rest.internal.endpoint.authorize;

import com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration.AuthorizeScreenConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.cxf.jaxrs.ext.MessageContext;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
public abstract class BaseMessageBodyWriter<T> implements MessageBodyWriter<T> {

	@Override
	public long getSize(
		T t, Class<?> aClass, Type type, Annotation[] annotations,
		MediaType mediaType) {

		return -1L;
	}

	@Override
	public void writeTo(
			T t, Class<?> aClass, Type type, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap,
			OutputStream outputStream)
		throws WebApplicationException {

		HttpServletRequest httpServletRequest =
			messageContext.getHttpServletRequest();

		String authorizeScreenURL = null;

		try {
			authorizeScreenURL = getAuthorizeScreenURL(
				portal.getCompanyId(httpServletRequest));
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get authorize screen configuration", ce);

			throw new WebApplicationException(
				Response.status(
					Response.Status.INTERNAL_SERVER_ERROR
				).build());
		}

		if (!http.hasDomain(authorizeScreenURL)) {
			String portalURL = portal.getPortalURL(httpServletRequest);

			authorizeScreenURL = portalURL + authorizeScreenURL;
		}

		authorizeScreenURL = writeTo(t, authorizeScreenURL);

		messageContext.put("http.request.redirected", Boolean.TRUE);

		HttpServletResponse httpServletResponse =
			messageContext.getHttpServletResponse();

		try {
			httpServletResponse.sendRedirect(authorizeScreenURL);
		}
		catch (IOException ioe) {
			throw new WebApplicationException(ioe);
		}
	}

	protected String getAuthorizeScreenURL(long companyId)
		throws ConfigurationException {

		AuthorizeScreenConfiguration authorizeScreenConfiguration =
			configurationProvider.getConfiguration(
				AuthorizeScreenConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, AuthorizeScreenConfiguration.class.getName()));

		return authorizeScreenConfiguration.authorizeScreenURL();
	}

	protected String removeParameter(String url, String name) {
		return http.removeParameter(url, "oauth2_" + name);
	}

	protected String setParameter(String url, String name, String value) {
		if (Validator.isBlank(value)) {
			return url;
		}

		return http.addParameter(url, "oauth2_" + name, value);
	}

	protected abstract String writeTo(T t, String authorizeScreenURL);

	@Reference
	protected ConfigurationProvider configurationProvider;

	@Reference
	protected Http http;

	@Context
	protected MessageContext messageContext;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMessageBodyWriter.class);

}