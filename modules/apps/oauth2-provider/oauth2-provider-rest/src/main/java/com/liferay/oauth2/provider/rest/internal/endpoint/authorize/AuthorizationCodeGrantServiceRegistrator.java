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

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.OAuthError;
import org.apache.cxf.rs.security.oauth2.common.OOBAuthorizationResponse;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;
import org.apache.cxf.rs.security.oauth2.provider.SubjectCreator;
import org.apache.cxf.rs.security.oauth2.services.AuthorizationCodeGrantService;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	immediate = true, service = {}
)
public class AuthorizationCodeGrantServiceRegistrator {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		OAuth2ProviderConfiguration oAuth2ProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				OAuth2ProviderConfiguration.class, properties);

		if (!oAuth2ProviderConfiguration.allowAuthorizationCodeGrant() &&
			!oAuth2ProviderConfiguration.allowAuthorizationCodePKCEGrant()) {

			return;
		}

		AuthorizationCodeGrantService authorizationCodeGrantService =
			new AuthorizationCodeGrantService() {

				@Override
				protected Response deliverOOBResponse(
					OOBAuthorizationResponse oobAuthorizationResponse) {

					_log.error(
						"The parameter \"redirect_uri\" was not found in the " +
							"request for client " +
								oobAuthorizationResponse.getClientId());

					return Response.status(
						500
					).build();
				}

				@Override
				protected Client getClient(
					String clientId, MultivaluedMap<String, String> params) {

					try {
						Client client = getValidClient(clientId, params);

						if (client != null) {
							return client;
						}
					}
					catch (OAuthServiceException oase) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Unable to validate remote client", oase);
						}

						if (oase.getError() != null) {
							reportInvalidRequestError(oase.getError(), null);
						}
					}

					reportInvalidRequestError(
						new OAuthError(OAuthConstants.INVALID_CLIENT), null);

					return null;
				}

			};

		authorizationCodeGrantService.setCanSupportPublicClients(
			oAuth2ProviderConfiguration.allowAuthorizationCodePKCEGrant());
		authorizationCodeGrantService.setDataProvider(
			_liferayOAuthDataProvider);
		authorizationCodeGrantService.setSubjectCreator(_subjectCreator);

		Dictionary<String, Object> authorizationCodeGrantProperties =
			new HashMapDictionary<>();

		authorizationCodeGrantProperties.put(
			"osgi.jaxrs.application.select",
			"(osgi.jaxrs.name=Liferay.OAuth2.Application)");
		authorizationCodeGrantProperties.put(
			"osgi.jaxrs.name", "Liferay.Authorization.Code.Grant.Service");
		authorizationCodeGrantProperties.put("osgi.jaxrs.resource", true);

		_serviceRegistration = bundleContext.registerService(
			Object.class, authorizationCodeGrantService,
			authorizationCodeGrantProperties);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AuthorizationCodeGrantServiceRegistrator.class);

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private ServiceRegistration<Object> _serviceRegistration;

	@Reference
	private SubjectCreator _subjectCreator;

}