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
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=OAuthAuthorizationDataMessageBodyWriter"
	}
)
@Produces("text/html")
@Provider
public class OAuthAuthorizationDataMessageBodyWriter
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

		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.AUTHORIZATION_CODE_CHALLENGE,
			oAuthAuthorizationData.getClientCodeChallenge());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.CLIENT_AUDIENCE,
			oAuthAuthorizationData.getAudience());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.CLIENT_ID,
			oAuthAuthorizationData.getClientId());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.NONCE,
			oAuthAuthorizationData.getNonce());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.REDIRECT_URI,
			oAuthAuthorizationData.getRedirectUri());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.RESPONSE_TYPE,
			oAuthAuthorizationData.getResponseType());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.SCOPE,
			oAuthAuthorizationData.getProposedScope());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.SESSION_AUTHENTICITY_TOKEN,
			oAuthAuthorizationData.getAuthenticityToken());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, OAuthConstants.STATE,
			oAuthAuthorizationData.getState());
		authorizeScreenURL = setParameter(
			authorizeScreenURL, "reply_to",
			oAuthAuthorizationData.getReplyTo());

		if (authorizeScreenURL.length() > _invokerFilterURIMaxLength) {
			authorizeScreenURL = removeParameter(
				authorizeScreenURL, OAuthConstants.SCOPE);
		}

		throw new WebApplicationException(
			Response.status(
				Response.Status.FOUND
			).location(
				URI.create(authorizeScreenURL)
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
		OAuthAuthorizationDataMessageBodyWriter.class);

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