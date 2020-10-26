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

package com.liferay.portal.remote.cors.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.HttpMethod;
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

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marta Medio
 */
@RunWith(Arquillian.class)
public class PortalConfigurationCORSClientTest extends BaseCORSClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCORSUsingBasicWithCustomConfig() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("configuration.name", "test-cors");

		properties.put("filter.mapping.url.pattern", "/api/jsonws/*");

		createFactoryConfiguration(
			PortalCORSConfiguration.class.getName(), properties);

		assertJsonWSUrl("/user/get-current-user", HttpMethod.OPTIONS, true);
		assertJsonWSUrl("/user/get-current-user", HttpMethod.GET, false);
	}

	@Test
	public void testCORSUsingBasicWithDefaultConfig() throws Exception {
		assertJsonWSUrl("/user/get-current-user", HttpMethod.OPTIONS, true);
		assertJsonWSUrl("/user/get-current-user", HttpMethod.GET, false);
	}

	@Test
	public void testNoCORSUsingPortalSession() throws Exception {
		Cookie authenticatedCookie = _getAuthenticatedCookie(
			"test@liferay.com", "test");

		Invocation.Builder invocationBuilder = _getJsonWebTarget(
			"user", "get-current-user"
		).request();

		invocationBuilder = invocationBuilder.cookie(authenticatedCookie);

		invocationBuilder = invocationBuilder.header(
			"Origin", "http://test-cors.com");

		Response response = invocationBuilder.get();

		String corsHeaderString = response.getHeaderString(
			"Access-Control-Allow-Origin");

		Assert.assertNull(corsHeaderString);
	}

	private Cookie _getAuthenticatedCookie(String login, String password) {
		Invocation.Builder invocationBuilder = _getWebTarget(
			"web", "guest"
		).request();

		Response response = invocationBuilder.get();

		_pAuth = _parsePAuthToken(response);

		Map<String, NewCookie> cookies = response.getCookies();

		NewCookie newCookie = cookies.get(CookieKeys.JSESSIONID);

		invocationBuilder = _getWebTarget(
			"c", "portal", "login"
		).request();

		invocationBuilder = invocationBuilder.cookie(newCookie);

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.add("login", login);
		formData.add("password", password);
		formData.add("p_auth", _pAuth);

		response = invocationBuilder.post(Entity.form(formData));

		cookies = response.getCookies();

		newCookie = cookies.get(CookieKeys.JSESSIONID);

		if (newCookie == null) {
			return null;
		}

		return newCookie.toCookie();
	}

	private WebTarget _getJsonWebTarget(String... paths) {
		WebTarget webTarget = _getLocalhostWebTarget();

		webTarget = webTarget.path("api");
		webTarget = webTarget.path("jsonws");

		for (String path : paths) {
			webTarget = webTarget.path(path);
		}

		return webTarget.queryParam("p_auth", _pAuth);
	}

	private WebTarget _getLocalhostWebTarget() {
		ClientBuilder clientBuilder = new ClientBuilderImpl();

		Client client = clientBuilder.build();

		RuntimeDelegate runtimeDelegate = new RuntimeDelegateImpl();

		UriBuilder uriBuilder = runtimeDelegate.createUriBuilder();

		return client.target(uriBuilder.uri("http://localhost:8080"));
	}

	private WebTarget _getWebTarget(String... paths) {
		WebTarget webTarget = _getLocalhostWebTarget();

		for (String path : paths) {
			webTarget = webTarget.path(path);
		}

		return webTarget;
	}

	private String _parsePAuthToken(Response response) {
		String bodyContent = response.readEntity(String.class);

		Matcher matcher = _pAuthTokenPattern.matcher(bodyContent);

		matcher.find();

		return matcher.group(2);
	}

	private static final Pattern _pAuthTokenPattern = Pattern.compile(
		"Liferay.authToken\\s*=\\s*(['\"])(((?!\\1).)*)\\1;");

	private String _pAuth;

}