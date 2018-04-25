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
import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRestEndpointConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.OutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.rs.security.oauth2.common.OAuthAuthorizationData;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	property = OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_OAUTH2_ENDPOINT_JAXRS_PROVIDER + "=true",
	service = Object.class
)
@Produces("text/html")
@Provider
public class AuthorizeScreenRedirectMessageBodyWriter
	implements MessageBodyWriter<OAuthAuthorizationData> {

	@Override
	public long getSize(
		OAuthAuthorizationData oAuthAuthorizationData, Class<?> clazz,
		Type genericType, Annotation[] annotations, MediaType mediaType) {

		return -1L;
	}

	@Override
	public boolean isWriteable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		if (clazz.isAssignableFrom(OAuthAuthorizationData.class) &&
			StringUtil.equalsIgnoreCase(mediaType.getType(), "text") &&
			StringUtil.equalsIgnoreCase(mediaType.getSubtype(), "html")) {

			return true;
		}

		return false;
	}

	@Override
	public void writeTo(
			OAuthAuthorizationData oAuthAuthorizationData, Class<?> clazz,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
		throws WebApplicationException {

		HttpServletRequest httpServletRequest =
			_messageContext.getHttpServletRequest();

		String authorizeScreenURLString = null;

		try {
			authorizeScreenURLString = getAuthorizeScreenURL(
				_portal.getCompanyId(httpServletRequest));
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to locate configuration", ce);

			throw new WebApplicationException(
				Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}

		if (!_http.hasDomain(authorizeScreenURLString)) {
			String portalURLString = _portal.getPortalURL(httpServletRequest);

			authorizeScreenURLString =
				portalURLString + authorizeScreenURLString;
		}

		authorizeScreenURLString = setParameter(
			authorizeScreenURLString,
			OAuthConstants.AUTHORIZATION_CODE_CHALLENGE,
			oAuthAuthorizationData.getClientCodeChallenge());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, OAuthConstants.CLIENT_AUDIENCE,
			oAuthAuthorizationData.getAudience());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, OAuthConstants.CLIENT_ID,
			oAuthAuthorizationData.getClientId());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, OAuthConstants.NONCE,
			oAuthAuthorizationData.getNonce());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, OAuthConstants.REDIRECT_URI,
			oAuthAuthorizationData.getRedirectUri());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, OAuthConstants.RESPONSE_TYPE,
			oAuthAuthorizationData.getResponseType());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, OAuthConstants.SCOPE,
			oAuthAuthorizationData.getProposedScope());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, OAuthConstants.SESSION_AUTHENTICITY_TOKEN,
			oAuthAuthorizationData.getAuthenticityToken());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, OAuthConstants.STATE,
			oAuthAuthorizationData.getState());
		authorizeScreenURLString = setParameter(
			authorizeScreenURLString, "reply_to",
			oAuthAuthorizationData.getReplyTo());

		if (authorizeScreenURLString.length() > _invokerFilterURIMaxLength) {
			authorizeScreenURLString = removeParameter(
				authorizeScreenURLString, OAuthConstants.SCOPE);
		}

		throw new WebApplicationException(
			Response.status(
				Response.Status.FOUND
			).location(
				URI.create(authorizeScreenURLString)
			).build());
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
		AuthorizeScreenRedirectMessageBodyWriter.class);

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