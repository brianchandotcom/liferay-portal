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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.rs.security.oauth2.common.OAuthError;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=OAuthErrorDataMessageBodyWriter"
	}
)
@Produces("text/html")
@Provider
public class OAuthErrorDataMessageBodyWriter
	implements MessageBodyWriter<OAuthError> {

	public long getSize(
		OAuthError oAuthError, Class<?> aClass, Type type,
		Annotation[] annotations, MediaType mediaType) {

		return -1L;
	}

	@Override
	public boolean isWriteable(
		Class<?> aClass, Type type, Annotation[] annotations,
		MediaType mediaType) {

		if (aClass.isAssignableFrom(OAuthError.class) &&
			StringUtil.equalsIgnoreCase(mediaType.getType(), "text") &&
			StringUtil.equalsIgnoreCase(mediaType.getSubtype(), "html")) {

			return true;
		}

		return false;
	}

	@Override
	public void writeTo(
			OAuthError oAuthError, Class<?> aClass, Type type,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> multivaluedMap,
			OutputStream outputStream)
		throws IOException, WebApplicationException {

		HttpServletRequest httpServletRequest =
			_messageContext.getHttpServletRequest();

		String authorizeScreenURL = null;

		try {
			authorizeScreenURL = getAuthorizeScreenURL(
				_portal.getCompanyId(httpServletRequest));
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get authorize screen configuration", ce);

			throw new WebApplicationException(
				Response.status(
					Response.Status.INTERNAL_SERVER_ERROR
				).build());
		}

		if (!_http.hasDomain(authorizeScreenURL)) {
			String portalURL = _portal.getPortalURL(httpServletRequest);

			authorizeScreenURL = portalURL + authorizeScreenURL;
		}

		String redirect_uri = ParamUtil.get(
			httpServletRequest, "redirect_uri", StringPool.BLANK);

		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.REDIRECT_URI, redirect_uri);
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.ERROR_KEY,
			OAuthConstants.INVALID_CLIENT);

		_messageContext.put("http.request.redirected", Boolean.TRUE);

		HttpServletResponse httpServletResponse =
			_messageContext.getHttpServletResponse();

		try {
			httpServletResponse.sendRedirect(authorizeScreenURL);
		}
		catch (IOException ioe) {
			throw new WebApplicationException(ioe);
		}
	}

	@Activate
	protected void activate() {
		_invokerFilterURIMaxLength = GetterUtil.getInteger(
			_props.get(PropsKeys.INVOKER_FILTER_URI_MAX_LENGTH),
			_invokerFilterURIMaxLength);
	}

	protected String getAuthorizeScreenURL(long companyId)
		throws ConfigurationException {

		AuthorizeScreenConfiguration authorizeScreenConfiguration =
			_configurationProvider.getConfiguration(
				AuthorizeScreenConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, AuthorizeScreenConfiguration.class.getName()));

		return authorizeScreenConfiguration.authorizeScreenURL();
	}

	protected String removeParameter(String url, String name) {
		return _http.removeParameter(url, "oauth2_" + name);
	}

	protected String setParameter(String url, String name, String value) {
		if (Validator.isBlank(value)) {
			return url;
		}

		return _http.addParameter(url, "oauth2_" + name, value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthErrorDataMessageBodyWriter.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	private int _invokerFilterURIMaxLength = 4000;

	@Context
	private MessageContext _messageContext;

	@Reference
	private Portal _portal;

	@Reference
	private Props _props;

}