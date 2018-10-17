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

package com.liferay.structured.content.apio.client.test;

import com.jayway.jsonpath.JsonPath;

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.structured.content.apio.client.test.activator.StructuredContentApioTestBundleActivator;

import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ruben Pulido
 */
@RunAsClient
@RunWith(Arquillian.class)
public class StructuredContentApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			StructuredContentApioTestBundleActivator.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		_rootEndpointURL = new URL(_url, "/o/api");
	}

	@Test
	public void testAdminUserSeesAllStructuredContents() throws Exception {
		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		List<String> titles = JsonPath.read(
			_toStringAsAdmin(hrefs.get(0)),
			"$._embedded.StructuredContent[*].title");

		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_NO_GROUP));
		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_YES_GROUP));
		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP));
	}

	@Test
	public void testDefaultStructuredFieldValueIsDisplayedWhenAcceptLanguageIsSpecifiedAndDoesNotMatch()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		Map<String, String> headers = _getHeaders();

		headers.put("Accept-Language", "de-DE");

		List<String> values = JsonPath.read(
			_toStringAsGuest(
				_getURLWithFilterByTitle(
					hrefs.get(0),
					StructuredContentApioTestBundleActivator.
						TITLE_2_LOCALE_DEFAULT),
				headers),
			"$._embedded.StructuredContent[*]._embedded.values._embedded[*]." +
				"value");

		Assert.assertTrue(values.contains("TextFieldValue_us"));
		Assert.assertTrue(values.contains("NestedTextFieldValue_us"));
	}

	@Test
	public void testDefaultTitleIsDisplayedWhenAcceptLanguageIsNotSpecified()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		Map<String, String> headers = _getHeaders();

		List<String> titles = JsonPath.read(
			_toStringAsGuest(hrefs.get(0), headers),
			"$._embedded.StructuredContent[*].title");

		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_2_LOCALE_DEFAULT));
	}

	@Test
	public void testDefaultTitleIsDisplayedWhenAcceptLanguageIsSpecifiedAndDoesNotMatch()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		Map<String, String> headers = _getHeaders();

		headers.put("Accept-Language", "de-DE");

		List<String> titles = JsonPath.read(
			_toStringAsGuest(hrefs.get(0), headers),
			"$._embedded.StructuredContent[*].title");

		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_2_LOCALE_DEFAULT));
	}

	@Test
	public void testGuestUserSeesRightStructuredContents() throws Exception {
		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		List<String> titles = JsonPath.read(
			_toStringAsGuest(hrefs.get(0)),
			"$._embedded.StructuredContent[*].title");

		Assert.assertFalse(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_NO_GROUP));
		Assert.assertFalse(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_YES_GROUP));
		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP));
	}

	@Test
	public void testLocalizedStructuredFieldValueIsDisplayedWhenAcceptLanguageIsSpecifiedAndMatches()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		Map<String, String> headers = _getHeaders();

		headers.put("Accept-Language", "es-ES");

		List<String> values = JsonPath.read(
			_toStringAsGuest(
				_getURLWithFilterByTitle(
					hrefs.get(0),
					StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_ES),
				headers),
			"$._embedded.StructuredContent[*]._embedded.values._embedded[*]." +
				"value");

		Assert.assertTrue(values.contains("TextFieldValue_es"));
		Assert.assertTrue(values.contains("NestedTextFieldValue_es"));
	}

	@Test
	public void testLocalizedTitleIsDisplayedWhenAcceptLanguageIsSpecifiedAndMatches()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		Map<String, String> headers = _getHeaders();

		headers.put("Accept-Language", "es-ES");

		List<String> titles = JsonPath.read(
			_toStringAsGuest(hrefs.get(0), headers),
			"$._embedded.StructuredContent[*].title");

		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_ES));
	}

	@Test
	public void testNotSiteMemberUserSeesRightStructuredContents()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		List<String> titles = JsonPath.read(
			_toStringAsUser(
				hrefs.get(0),
				StructuredContentApioTestBundleActivator.
					NOT_A_SITE_MEMBER_EMAIL_ADDRESS,
				"test"),
			"$._embedded.StructuredContent[*].title");

		Assert.assertFalse(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_NO_GROUP));
		Assert.assertFalse(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_YES_GROUP));
		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP));
	}

	@Test
	public void testSetDefaultTitleIsDisplayedWhenAcceptLanguageIsNotSpecified()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsAdmin(
				JsonPath.read(
					_toStringAsAdmin(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		Map<String, String> headers = _getHeaders();

		List<String> titles = JsonPath.read(
			_toStringAsGuest(hrefs.get(0), headers),
			"$._embedded.StructuredContent[*].title");

		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.TITLE_1_LOCALE_ES));
	}

	@Test
	public void testSiteMemberUserSeesRightStructuredContents()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsGuest(
				JsonPath.read(
					_toStringAsGuest(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		List<String> titles = JsonPath.read(
			_toStringAsUser(
				hrefs.get(0),
				StructuredContentApioTestBundleActivator.
					SITE_MEMBER_EMAIL_ADDRESS,
				"test"),
			"$._embedded.StructuredContent[*].title");

		Assert.assertFalse(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_NO_GROUP));
		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_YES_GROUP));
		Assert.assertTrue(
			titles.contains(
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP));
	}

	@Test
	public void testStructuredContentsExistsInContentSpaceEndpoint()
		throws Exception {

		List<String> hrefs = JsonPath.read(
			_toStringAsGuest(
				JsonPath.read(
					_toStringAsGuest(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		Assert.assertNotNull(hrefs.get(0));
	}

	@Test
	public void testStructuredContentsMatchesSelfLink() throws Exception {
		List<String> hrefs = JsonPath.read(
			_toStringAsGuest(
				JsonPath.read(
					_toStringAsGuest(_rootEndpointURL.toExternalForm()),
					"$._links.content-space.href")),
			"$._embedded.ContentSpace[?(@.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"')]._links.structuredContents.href");

		String href = JsonPath.read(
			_toStringAsGuest(hrefs.get(0)), "$._links.self.href");

		Assert.assertTrue(href.startsWith(hrefs.get(0)));
	}

	private JSONWebServiceClient _getGuestJSONWebServiceClient() {
		JSONWebServiceClient jsonWebServiceClient =
			new JSONWebServiceClientImpl();

		jsonWebServiceClient.setHostName(_rootEndpointURL.getHost());
		jsonWebServiceClient.setHostPort(_rootEndpointURL.getPort());
		jsonWebServiceClient.setProtocol(_rootEndpointURL.getProtocol());

		return jsonWebServiceClient;
	}

	private Map<String, String> _getHeaders() {
		return new HashMap<String, String>() {
			{
				put("Accept", "application/hal+json");
			}
		};
	}

	private JSONWebServiceClient _getJSONWebServiceClient(
		String login, String password) {

		JSONWebServiceClient jsonWebServiceClient =
			_jsonWebServiceClientMap.get(login);

		if (jsonWebServiceClient == null) {
			jsonWebServiceClient = _getGuestJSONWebServiceClient();

			jsonWebServiceClient.setLogin(login);
			jsonWebServiceClient.setPassword(password);

			_jsonWebServiceClientMap.put(login, jsonWebServiceClient);
		}

		return jsonWebServiceClient;
	}

	private String _getURLWithFilterByTitle(String url, String title)
		throws UnsupportedEncodingException {

		return StringBundler.concat(
			url, "?filter=(",
			URLEncoder.encode("title eq '" + title, StringPool.UTF8), "')");
	}

	private String _toString(
			JSONWebServiceClient jsonWebServiceClient, String url)
		throws Exception {

		return _toString(
			jsonWebServiceClient, url,
			Collections.singletonMap("Accept", "application/hal+json"));
	}

	private String _toString(
			JSONWebServiceClient jsonWebServiceClient, String url,
			Map<String, String> headers)
		throws Exception {

		return jsonWebServiceClient.doGet(url, Collections.emptyMap(), headers);
	}

	private String _toStringAsAdmin(String url) throws Exception {
		return _toStringAsUser(url, "test@liferay.com", "test");
	}

	private String _toStringAsGuest(String url) throws Exception {
		return _toStringAsGuest(url, _getHeaders());
	}

	private String _toStringAsGuest(String url, Map<String, String> headers)
		throws Exception {

		return _toString(_getGuestJSONWebServiceClient(), url, headers);
	}

	private String _toStringAsUser(String url, String login, String password)
		throws Exception {

		JSONWebServiceClient jsonWebServiceClient = _getJSONWebServiceClient(
			login, password);

		return _toString(jsonWebServiceClient, url);
	}

	private final Map<String, JSONWebServiceClient> _jsonWebServiceClientMap =
		new HashMap<>();
	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}