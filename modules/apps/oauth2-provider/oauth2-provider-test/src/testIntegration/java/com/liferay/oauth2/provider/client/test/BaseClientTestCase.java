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

package com.liferay.oauth2.provider.client.test;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URI;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.jaxrs.client.spec.ClientBuilderImpl;
import org.apache.cxf.jaxrs.impl.RuntimeDelegateImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Carlos Sierra Andrés
 */
public abstract class BaseClientTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalRestrictedHeaderSet = ReflectionTestUtil.getFieldValue(
			Class.forName("sun.net.www.protocol.http.HttpURLConnection"),
			"restrictedHeaderSet");

		_restrictedHeaderSet = new HashSet<>(_originalRestrictedHeaderSet);

		_originalRestrictedHeaderSet.clear();
	}

	@AfterClass
	public static void tearDownClass() {
		_originalRestrictedHeaderSet.addAll(_restrictedHeaderSet);
	}

	@Before
	public void setUp() throws Exception {
		_bundleActivator = getBundleActivator();

		Bundle bundle = FrameworkUtil.getBundle(BaseClientTestCase.class);

		_bundleContext = bundle.getBundleContext();

		_bundleActivator.start(_bundleContext);
	}

	@After
	public void tearDown() throws Exception {
		_bundleActivator.stop(_bundleContext);
	}

	protected Invocation.Builder authorize(
		Invocation.Builder invocationBuilder, String token) {

		return invocationBuilder.header("Authorization", "Bearer " + token);
	}

	protected Cookie getAuthenticatedCookie(
		String login, String password, String hostname) {

		Invocation.Builder invocationBuilder = getInvocationBuilder(
			hostname, getPortalWebTarget());

		Response response = invocationBuilder.get();

		String pAuthToken = parsePAuthToken(response);

		Map<String, NewCookie> cookies = response.getCookies();

		NewCookie newCookie = cookies.get(CookieKeys.JSESSIONID);

		invocationBuilder = getInvocationBuilder(hostname, getLoginWebTarget());

		invocationBuilder.cookie(newCookie);

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.add("login", login);
		formData.add("password", password);
		formData.add("p_auth", pAuthToken);

		response = invocationBuilder.post(Entity.form(formData));

		cookies = response.getCookies();

		newCookie = cookies.get(CookieKeys.JSESSIONID);

		if (newCookie == null) {
			return null;
		}

		return newCookie.toCookie();
	}

	protected Function<WebTarget, Invocation.Builder>
		getAuthenticatedInvocationBuilderFunction(
			String login, String password, String hostname) {

		Cookie authenticatedCookie = getAuthenticatedCookie(
			login, password, hostname);

		return webtarget -> {
			Invocation.Builder invocationBuilder = getInvocationBuilder(
				hostname, webtarget);

			invocationBuilder = invocationBuilder.accept(
				"text/html"
			).cookie(
				authenticatedCookie
			);

			return invocationBuilder;
		};
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getAuthorizationCodeBiFunction(
			String user, String password, String hostname) {

		return getAuthorizationCodeBiFunction(
			user, password, hostname, (String)null);
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getAuthorizationCodeBiFunction(
			String user, String password, String hostname, String scope) {

		return (clientId, invocationBuilder) -> {
			String authorizationCode = getCodeResponse(
				user, password, hostname,
				getCodeFunction(
					webTarget -> webTarget.queryParam(
						"client_id", clientId
					).queryParam(
						"response_type", "code"
					).queryParam(
						"scope", scope
					)),
				this::parseAuthorizationCodeString);

			BiFunction<String, Invocation.Builder, Response>
				authorizationCodePKCEBiFunction =
					getExchangeAuthorizationCodeBiFunction(
						authorizationCode, null);

			return authorizationCodePKCEBiFunction.apply(
				clientId, invocationBuilder);
		};
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getAuthorizationCodePKCEBiFunction(
			String userName, String password, String hostname) {

		return (clientId, invocationBuilder) -> {
			String codeVerifier = RandomTestUtil.randomString();

			String base64Digest = DigesterUtil.digestBase64(
				Digester.SHA_256, codeVerifier);

			String base64UrlDigest = StringUtil.replace(
				base64Digest, new char[] {CharPool.PLUS, CharPool.SLASH},
				new char[] {CharPool.MINUS, CharPool.UNDERLINE});

			base64UrlDigest = StringUtil.removeChar(
				base64UrlDigest, CharPool.EQUAL);

			final String codeChallenge = base64UrlDigest;

			String authorizationCode = getCodeResponse(
				userName, password, hostname,
				getCodeFunction(
					webTarget -> webTarget.queryParam(
						"client_id", clientId
					).queryParam(
						"code_challenge", codeChallenge
					).queryParam(
						"response_type", "code"
					)),
				this::parseAuthorizationCodeString);

			BiFunction<String, Invocation.Builder, Response>
				authorizationCodePKCEBiFunction =
					getExchangeAuthorizationCodePKCEBiFunction(
						authorizationCode, null, codeVerifier);

			return authorizationCodePKCEBiFunction.apply(
				clientId, invocationBuilder);
		};
	}

	protected WebTarget getAuthorizeDecisionWebTarget() {
		WebTarget webTarget = getAuthorizeWebTarget();

		return webTarget.path("decision");
	}

	protected WebTarget getAuthorizeWebTarget() {
		WebTarget webTarget = getOAuth2WebTarget();

		return webTarget.path("authorize");
	}

	protected abstract BundleActivator getBundleActivator();

	protected Response getClientCredentialsResponse(
		String clientId, Invocation.Builder invocationBuilder) {

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.add("client_id", clientId);
		formData.add("client_secret", "oauthTestApplicationSecret");
		formData.add("grant_type", "client_credentials");

		return invocationBuilder.post(Entity.form(formData));
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getClientCredentialsResponseBiFunction(String scope) {

		return (clientId, invocationBuilder) -> {
			MultivaluedMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("client_secret", "oauthTestApplicationSecret");
			formData.add("grant_type", "client_credentials");
			formData.add("scope", scope);

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected Function<Function<WebTarget, Invocation.Builder>, Response>
		getCodeFunction(
			Function<WebTarget, WebTarget> authorizeRequestFunction) {

		return invocationBuilderFunction -> {
			Invocation.Builder invocationBuilder =
				invocationBuilderFunction.apply(
					authorizeRequestFunction.apply(getAuthorizeWebTarget()));

			Response response = invocationBuilder.get();

			URI uri = response.getLocation();

			if (uri == null) {
				return response;
			}

			Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
				uri.getQuery());

			if (parameterMap.containsKey("error")) {
				return response;
			}

			MultivaluedMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("oauthDecision", "allow");

			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				String key = entry.getKey();

				if (!StringUtil.startsWith(key, "oauth2_")) {
					continue;
				}

				formData.add(
					key.substring("oauth2_".length()), entry.getValue()[0]);
			}

			invocationBuilder = invocationBuilderFunction.apply(
				getAuthorizeDecisionWebTarget());

			response = invocationBuilder.post(Entity.form(formData));

			return response;
		};
	}

	protected <T> T getCodeResponse(
		String login, String password, String hostname,
		Function<Function<WebTarget, Invocation.Builder>, Response>
			authorizationResponseFunction,
		Function<Response, T> codeParser) {

		return codeParser.apply(
			authorizationResponseFunction.apply(
				getAuthenticatedInvocationBuilderFunction(
					login, password, hostname)));
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getExchangeAuthorizationCodeBiFunction(
			String authorizationCode, String redirectUri) {

		return (clientId, invocationBuilder) -> {
			MultivaluedMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("client_secret", "oauthTestApplicationSecret");
			formData.add("code", authorizationCode);
			formData.add("grant_type", "authorization_code");

			if (Validator.isNotNull(redirectUri)) {
				formData.add("redirect_uri", redirectUri);
			}

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getExchangeAuthorizationCodePKCEBiFunction(
			String authorizationCode, String redirectUri, String codeVerifier) {

		return (clientId, invocationBuilder) -> {
			MultivaluedMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("code", authorizationCode);
			formData.add("code_verifier", codeVerifier);
			formData.add("grant_type", "authorization_code");

			if (Validator.isNotNull(redirectUri)) {
				formData.add("redirect_uri", redirectUri);
			}

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected Invocation.Builder getInvocationBuilder(
		String hostname, WebTarget webTarget) {

		Invocation.Builder invocationBuilder = webTarget.request();

		if (hostname != null) {
			invocationBuilder = invocationBuilder.header("Host", hostname);
		}

		return invocationBuilder;
	}

	protected WebTarget getJsonWebTarget(String... paths) {
		WebTarget webTarget = getWebTarget();

		webTarget = webTarget.path("api");
		webTarget = webTarget.path("jsonws");

		for (String path : paths) {
			webTarget = webTarget.path(path);
		}

		return webTarget;
	}

	protected WebTarget getLoginWebTarget() {
		WebTarget webTarget = getWebTarget();

		webTarget = webTarget.path("c");
		webTarget = webTarget.path("portal");
		webTarget = webTarget.path("login");

		return webTarget;
	}

	protected WebTarget getOAuth2WebTarget() {
		WebTarget webTarget = getWebTarget();

		webTarget = webTarget.path("o");
		webTarget = webTarget.path("oauth2");

		return webTarget;
	}

	protected WebTarget getPortalWebTarget() {
		WebTarget webTarget = getWebTarget();

		webTarget = webTarget.path("web");
		webTarget = webTarget.path("guest");

		return webTarget;
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getResourceOwnerPasswordBiFunction(String userName, String password) {

		return (clientId, invocationBuilder) -> {
			MultivaluedMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("client_secret", "oauthTestApplicationSecret");
			formData.add("grant_type", "password");
			formData.add("password", password);
			formData.add("username", userName);

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getResourceOwnerPasswordBiFunction(
			String userName, String password, String scope) {

		return (clientId, invocationBuilder) -> {
			MultivaluedMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("client_secret", "oauthTestApplicationSecret");
			formData.add("grant_type", "password");
			formData.add("password", password);
			formData.add("scope", scope);
			formData.add("username", userName);

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected String getToken(String clientId) {
		return getToken(clientId, null);
	}

	protected String getToken(String clientId, String hostname) {
		return parseTokenString(
			getClientCredentialsResponse(
				clientId, getTokenInvocationBuilder(hostname)));
	}

	protected <T> T getToken(
		String clientId, String hostname,
		BiFunction<String, Invocation.Builder, Response> credentialsBiFunction,
		Function<Response, T> tokenParserFunction) {

		return tokenParserFunction.apply(
			credentialsBiFunction.apply(
				clientId, getTokenInvocationBuilder(hostname)));
	}

	protected Invocation.Builder getTokenInvocationBuilder(String hostname) {
		return getInvocationBuilder(hostname, getTokenWebTarget());
	}

	protected WebTarget getTokenWebTarget() {
		WebTarget webTarget = getOAuth2WebTarget();

		return webTarget.path("token");
	}

	protected WebTarget getWebTarget() {
		ClientBuilder clientBuilder = new ClientBuilderImpl();

		Client client = clientBuilder.build();

		RuntimeDelegate runtimeDelegate = new RuntimeDelegateImpl();

		UriBuilder uriBuilder = runtimeDelegate.createUriBuilder();

		return client.target(uriBuilder.uri("http://localhost:8080"));
	}

	protected WebTarget getWebTarget(String... paths) {
		WebTarget target = getWebTarget();

		target = target.path("o");
		target = target.path("oauth2-test");

		for (String path : paths) {
			target = target.path(path);
		}

		return target;
	}

	protected String parseAuthorizationCodeString(Response response) {
		URI uri = response.getLocation();

		if (uri == null) {
			throw new IllegalArgumentException(
				"Authorization service response missing \"Location\" header " +
					"from which code is extracted");
		}

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			uri.getQuery());

		if (!parameterMap.containsKey("code")) {
			return null;
		}

		return parameterMap.get("code")[0];
	}

	protected String parseError(Response response) {
		return parseJsonField(response, "error");
	}

	protected String parseErrorParameter(Response response) {
		URI uri = response.getLocation();

		if (uri == null) {
			throw new IllegalArgumentException(
				"Authorization service response missing \"Location\" header " +
					"from which error is extracted");
		}

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			uri.getQuery());

		if (!parameterMap.containsKey("error")) {
			return null;
		}

		return parameterMap.get("error")[0];
	}

	protected String parseJsonField(Response response, String fieldName) {
		JSONObject jsonObject = parseJSONObject(response);

		return jsonObject.getString(fieldName);
	}

	protected JSONObject parseJSONObject(Response response) {
		String json = response.readEntity(String.class);

		try {
			return new JSONObjectImpl(json);
		}
		catch (JSONException jsone) {
			throw new IllegalArgumentException(
				"The token service returned " + json);
		}
	}

	protected String parsePAuthToken(Response response) {
		String bodyContent = response.readEntity(String.class);

		Matcher matcher = _pAuthTokenPattern.matcher(bodyContent);

		matcher.find();

		return matcher.group(2);
	}

	protected String parseScopeString(Response response) {
		return parseJsonField(response, "scope");
	}

	protected String parseTokenString(Response response) {
		return parseJsonField(response, "access_token");
	}

	private static Set<String> _originalRestrictedHeaderSet;
	private static final Pattern _pAuthTokenPattern = Pattern.compile(
		"Liferay.authToken\\s*=\\s*(['\"])(((?!\\1).)*)\\1;");
	private static Set<String> _restrictedHeaderSet;

	private BundleActivator _bundleActivator;
	private BundleContext _bundleContext;

}