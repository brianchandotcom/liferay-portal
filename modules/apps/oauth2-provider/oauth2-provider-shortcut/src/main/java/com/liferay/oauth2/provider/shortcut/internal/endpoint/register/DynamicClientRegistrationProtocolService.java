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

package com.liferay.oauth2.provider.shortcut.internal.endpoint.register;

import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.exception.DuplicateOAuth2ApplicationClientIdException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationClientGrantTypeException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationHomePageURLException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationHomePageURLSchemeException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationNameException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationPrivacyPolicyURLException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationPrivacyPolicyURLSchemeException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURIException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURIFragmentException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURIMissingException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURIPathException;
import com.liferay.oauth2.provider.exception.OAuth2ApplicationRedirectURISchemeException;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.shortcut.internal.endpoint.register.extension.Extension;
import com.liferay.oauth2.provider.shortcut.internal.endpoint.register.model.DynamicClientRegistrationRequest;
import com.liferay.oauth2.provider.shortcut.internal.endpoint.register.model.DynamicClientRegistrationResponse;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.security.access.control.AccessControl;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.InetAddress;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.name=Dynamic.Client.Registration.Protocol.Service",
		"osgi.jaxrs.resource=true"
	},
	service = Object.class
)
@Path("register")
public class DynamicClientRegistrationProtocolService {

	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Extension.class, "type");
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(String body) {
		User user = null;

		try {
			user = getAuthorizedUser();

			if (user == null) {
				return Response.status(
					Response.Status.FORBIDDEN
				).build();
			}
		}
		catch (Throwable t) {
			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("description", t.getMessage());
			jsonObject.put("error", "invalid_authorization");

			return Response.status(
				Response.Status.FORBIDDEN
			).entity(
				jsonObject.toString()
			).build();
		}

		long threadLocalUserId = PrincipalThreadLocal.getUserId();

		try {
			_accessControl.initContextUser(user.getUserId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			final User authorizedUser = user;

			return TransactionInvokerUtil.invoke(
				TransactionConfig.Factory.create(
					Propagation.REQUIRED, new Class<?>[] {Exception.class}),
				() -> registerTransactionally(body, authorizedUser));
		}
		catch (Throwable t) {
			if (_log.isDebugEnabled()) {
				_log.debug(t, t);
			}

			String message = t.getMessage();

			if (Validator.isBlank(message)) {
				Class<?> clazz = t.getClass();

				message = clazz.getSimpleName();
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("description", message);
			jsonObject.put("error", "invalid_client_metadata");

			return Response.status(
				Response.Status.BAD_REQUEST
			).entity(
				jsonObject.toString()
			).build();
		}
		finally {
			if (threadLocalUserId > 0) {
				try {
					_accessControl.initContextUser(threadLocalUserId);
				}
				catch (AuthException ae) {
					_log.error(ae, ae);
				}
			}
		}
	}

	protected User getAuthorizedUser() throws Exception {
		String authorization = _httpServletRequest.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isBlank(authorization)) {
			return null;
		}

		int pos = authorization.indexOf(StringPool.SPACE);

		if (pos <= 0) {
			return null;
		}

		String jwtToken = authorization.substring(pos + 1);

		DynamicClientRegistrationTokenService.RegistrationToken
			dynamicClientRegistration =
				_dynamicClientRegistrationAuthorizationService.parse(jwtToken);

		long userId = dynamicClientRegistration.getUserId();

		if (userId > 0) {
			return _userLocalService.fetchUser(userId);
		}

		return null;
	}

	protected Response registerTransactionally(String body, User user)
		throws Exception {

		DynamicClientRegistrationRequest dynamicClientRegistrationRequest =
			_jsonFactory.looseDeserialize(
				body, DynamicClientRegistrationRequest.class);

		if (dynamicClientRegistrationRequest == null) {
			throw new Exception("Unable to parse request body");
		}

		List<String> extensions =
			dynamicClientRegistrationRequest.getExtensions();

		Set<String> processedExtensions = new HashSet<>();

		if (!extensions.isEmpty()) {
			processedExtensions.addAll(
				_processExtensions(body, extensions, user, null, true));
		}

		OAuth2Application oAuth2Application = _addOAuth2Application(
			user.getCompanyId(), dynamicClientRegistrationRequest);

		oAuth2Application = _downloadLogo(
			oAuth2Application, dynamicClientRegistrationRequest);

		if (!extensions.isEmpty()) {
			processedExtensions.addAll(
				_processExtensions(
					body, extensions, user, oAuth2Application, false));
		}

		DynamicClientRegistrationResponse dynamicClientRegistrationResponse =
			DynamicClientRegistrationResponse.fromOAuth2Application(
				oAuth2Application);

		dynamicClientRegistrationResponse.setExtensions(
			new ArrayList<>(processedExtensions));

		dynamicClientRegistrationResponse.setLogoUri(
			dynamicClientRegistrationRequest.getLogoUri());

		dynamicClientRegistrationResponse.setScope(
			dynamicClientRegistrationRequest.getScope());

		return Response.status(
			Response.Status.CREATED
		).entity(
			_jsonFactory.looseSerialize(dynamicClientRegistrationResponse)
		).build();
	}

	private static String _generateRandomId() {
		String randomSecret = _generateRandomSecret();

		return StringUtil.replace(randomSecret, "secret-", "dynamic-client-");
	}

	private static String _generateRandomSecret() {
		int size = 16;

		int count = (int)Math.ceil((double)size / 8);

		byte[] buffer = new byte[count * 8];

		for (int i = 0; i < count; i++) {
			BigEndianCodec.putLong(buffer, i * 8, SecureRandomUtil.nextLong());
		}

		StringBundler sb = new StringBundler(size);

		for (int i = 0; i < size; i++) {
			sb.append(Integer.toHexString(0xFF & buffer[i]));
		}

		Matcher matcher = _baseIdPattern.matcher(sb.toString());

		return matcher.replaceFirst("secret-$1-$2-$3-$4-$5");
	}

	private OAuth2Application _addOAuth2Application(
			long companyId,
			DynamicClientRegistrationRequest dynamicClientRegistrationRequest)
		throws Exception {

		List<GrantType> allowedGrantTypesList = new ArrayList<>();

		for (String registrationGrantType :
				dynamicClientRegistrationRequest.getGrantTypes()) {

			if (Validator.isBlank(registrationGrantType)) {
				continue;
			}

			if (registrationGrantType.equals("password")) {
				allowedGrantTypesList.add(GrantType.RESOURCE_OWNER_PASSWORD);
			}
			else {
				try {
					allowedGrantTypesList.add(
						GrantType.valueOf(
							StringUtil.toUpperCase(registrationGrantType)));
				}
				catch (IllegalArgumentException iae) {
					throw new Exception(
						"Unsupported grant type: " + registrationGrantType,
						iae);
				}
			}
		}

		if (allowedGrantTypesList.isEmpty()) {
			allowedGrantTypesList.add(GrantType.AUTHORIZATION_CODE);
		}

		String clientId = _generateRandomId();

		int clientProfile = ClientProfile.OTHER.id();

		for (ClientProfile clientProfileEnum : ClientProfile.values()) {
			if (StringUtil.equals(
					dynamicClientRegistrationRequest.getApplicationType(),
					StringUtil.toLowerCase(clientProfileEnum.name()))) {

				clientProfile = clientProfileEnum.id();
			}
		}

		String clientSecret = null;

		for (GrantType grantType : allowedGrantTypesList) {
			if (!grantType.isSupportsPublicClients()) {
				clientSecret = _generateRandomSecret();
			}
		}

		String description = null;
		List<String> featuresList = new ArrayList<>();
		String homePageURL = dynamicClientRegistrationRequest.getClientUri();
		long iconFileEntryId = 0;
		String name = dynamicClientRegistrationRequest.getClientName();
		String privacyPolicyURL =
			dynamicClientRegistrationRequest.getPolicyUri();
		List<String> redirectURIsList =
			dynamicClientRegistrationRequest.getRedirectURIs();

		List<String> scopeAliasesList = new ArrayList<>();

		Collection<String> companyScopeAliases = _scopeLocator.getScopeAliases(
			companyId);

		for (String scope :
				StringUtil.split(
					dynamicClientRegistrationRequest.getScope(),
					StringPool.SPACE)) {

			if (!companyScopeAliases.contains(scope)) {
				throw new Exception("Unsupported scope: " + scope);
			}

			scopeAliasesList.add(scope);
		}

		ServiceContext serviceContext = new ServiceContext();

		try {
			return _oAuth2ApplicationService.addOAuth2Application(
				allowedGrantTypesList, clientId, clientProfile, clientSecret,
				description, featuresList, homePageURL, iconFileEntryId, name,
				privacyPolicyURL, redirectURIsList, scopeAliasesList,
				serviceContext);
		}
		catch (OAuth2ApplicationClientGrantTypeException oaacgte) {
			throw new Exception(
				"Unable to register the client, invalid grant type " +
					oaacgte.getMessage(),
				oaacgte);
		}
		catch (DuplicateOAuth2ApplicationClientIdException doaacie) {
			throw new Exception(
				"Unable to register the client, client_id already exists",
				doaacie);
		}
		catch (OAuth2ApplicationHomePageURLSchemeException oaahpurlse) {
			throw new Exception(
				"Unable to register the client, invalid client_uri scheme",
				oaahpurlse);
		}
		catch (OAuth2ApplicationHomePageURLException oaahpurle) {
			throw new Exception(
				"Unable to register the client, invalid client_uri", oaahpurle);
		}
		catch (OAuth2ApplicationNameException oaane) {
			throw new Exception(
				"Unable to register the client, missing client_name", oaane);
		}
		catch (OAuth2ApplicationPrivacyPolicyURLSchemeException oaappurlse) {
			throw new Exception(
				"Unable to register the client, invalid policy_uri scheme",
				oaappurlse);
		}
		catch (OAuth2ApplicationPrivacyPolicyURLException oaappurle) {
			throw new Exception(
				"Unable to register the client, invalid policy_uri", oaappurle);
		}
		catch (OAuth2ApplicationRedirectURIMissingException oaarurime) {
			throw new Exception(
				"Unable to register the client, missing redirect_uris",
				oaarurime);
		}
		catch (OAuth2ApplicationRedirectURIException |
			   OAuth2ApplicationRedirectURIFragmentException |
			   OAuth2ApplicationRedirectURIPathException |
			   OAuth2ApplicationRedirectURISchemeException e) {

			throw new Exception(
				"Unable to register the client, invalid redirect_uri: " +
					e.getMessage(),
				e);
		}
		catch (PortalException pe) {
			String message = pe.getMessage();

			if (Validator.isBlank(message)) {
				Class<?> clazz = pe.getClass();

				message = clazz.getSimpleName();
			}

			throw new Exception(
				"Unable to register the client: " + message, pe);
		}
	}

	private OAuth2Application _downloadLogo(
			OAuth2Application oAuth2Application,
			DynamicClientRegistrationRequest dynamicClientRegistration)
		throws Exception {

		if (!Validator.isBlank(dynamicClientRegistration.getLogoUri())) {
			try {
				URL iconURL = new URL(dynamicClientRegistration.getLogoUri());

				if (InetAddressUtil.isLocalInetAddress(
						InetAddress.getByName(iconURL.getHost()))) {

					throw new Exception(
						"Invalid logo URL: " +
							dynamicClientRegistration.getLogoUri());
				}

				InputStream inputStream = _http.URLtoInputStream(
					iconURL.toString());

				return _oAuth2ApplicationService.updateIcon(
					oAuth2Application.getOAuth2ApplicationId(), inputStream);
			}
			catch (IOException | PortalException e) {
				throw new Exception(
					"Invalid logo URL: " +
						dynamicClientRegistration.getLogoUri(),
					e);
			}
		}

		return oAuth2Application;
	}

	private List<String> _processExtensions(
			String body, List<String> extensions, User user,
			OAuth2Application oAuth2Application, boolean preProcess)
		throws Exception {

		List<String> processedExtensions = new ArrayList<>();

		JSONObject bodyJSONObject = _jsonFactory.createJSONObject(body);

		for (String extensionType : extensions) {
			Extension extension = _serviceTrackerMap.getService(extensionType);

			if (extension == null) {
				continue;
			}

			Object object = bodyJSONObject.get(extensionType);

			if (preProcess) {
				extension.preProcess(object, user);
			}
			else {
				extension.postProcess(object, oAuth2Application);
			}

			processedExtensions.add(extensionType);
		}

		return processedExtensions;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicClientRegistrationProtocolService.class);

	private static final Pattern _baseIdPattern = Pattern.compile(
		"(.{8})(.{4})(.{4})(.{4})(.*)");

	@Reference
	private AccessControl _accessControl;

	@Reference
	private DynamicClientRegistrationTokenService
		_dynamicClientRegistrationAuthorizationService;

	@Reference
	private Http _http;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OAuth2ApplicationService _oAuth2ApplicationService;

	@Reference
	private ScopeLocator _scopeLocator;

	private ServiceTrackerMap<String, Extension> _serviceTrackerMap;

	@Reference
	private UserLocalService _userLocalService;

}