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

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.portal.apio.test.util.ContentSpaceApioTestUtil;
import com.liferay.portal.apio.test.util.FileTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.structured.content.apio.client.test.internal.activator.StructuredContentApioTestBundleActivator;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
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
		URL rootEndpointURL = new URL(_url, "/o/api");

		_contentSpaceURL = new URL(
			ContentSpaceApioTestUtil.getContentSpaceHref(
				rootEndpointURL.toExternalForm(),
				StructuredContentApioTestBundleActivator.SITE_NAME));

		URL contentStructuresEndpointURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				_contentSpaceURL.toExternalForm()
			).then(
			).extract(
			).path(
				"_links.contentStructures.href"
			));

		_contentStructureURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				contentStructuresEndpointURL.toExternalForm()
			).then(
			).extract(
			).path(
				"_embedded.Structure.find {it.name == '" +
					StructuredContentApioTestBundleActivator.SITE_NAME +
						"'}._links.self.href"
			));

		_structuredContentEndpointURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				_contentSpaceURL.toExternalForm()
			).then(
			).extract(
			).path(
				"_links.structuredContents.href"
			));
	}

	@After
	public void tearDown() throws Exception {
		ContentSpaceApioTestUtil.deleteAllStructuredContents(_contentSpaceURL);
	}

	@Test
	public void testCreateStructuredContent() throws Exception {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			FileTestUtil.readFile(
				"test-create-structured-content.json", getClass(),
				Collections.singletonList(
					_contentStructureURL.toExternalForm()))
		).when(
		).post(
			_structuredContentEndpointURL.toExternalForm()
		).then(
		).statusCode(
			200
		).body(
			"title", Matchers.equalTo("Example Structured Content")
		).body(
			"_embedded.values._embedded.find {it.name == 'MyDecimal'}.value",
			Matchers.equalTo("1.0")
		);
	}

	@Test
	public void testDeleteStructuredContent() throws Exception {
		URL structuredContentURL = _createStructuredContent(
			LocaleUtil.US, "test-create-structured-content.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).when(
		).delete(
			structuredContentURL.toExternalForm()
		).then(
		).statusCode(
			204
		);
	}

	@Test
	public void testGetStructuredContentWithAcceptedLanguageEqualsDefaultLocale()
		throws Exception {

		URL structuredContentURL = _createStructuredContent(
			LocaleUtil.US, "test-get-structured-content-us.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		_updateStructuredContent(
			structuredContentURL, LocaleUtil.SPAIN,
			"test-get-structured-content-es.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_structuredContentEndpointURL.toExternalForm() +
				"?filter=title eq 'Example Structured Content in English'"
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0].title",
			IsEqual.equalTo("Example Structured Content in English")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.inputControl",
			IsEqual.equalTo("checkbox")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyColor'}.inputControl",
			IsNull.nullValue()
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'TextFieldName'}.label",
			IsEqual.equalTo("TextFieldNameLabel_us")
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testGetStructuredContentWithAcceptedLanguageEqualsLanguageAvailableInStructureContent()
		throws Exception {

		URL structuredContentURL = _createStructuredContent(
			LocaleUtil.US, "test-get-structured-content-us.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		_updateStructuredContent(
			structuredContentURL, LocaleUtil.SPAIN,
			"test-get-structured-content-es.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "es-ES"
		).when(
		).get(
			_structuredContentEndpointURL.toExternalForm() +
				"?filter=title eq 'Example Structured Content in Spanish'"
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0].title",
			IsEqual.equalTo("Example Structured Content in Spanish")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.inputControl",
			IsEqual.equalTo("checkbox")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyColor'}.inputControl",
			IsNull.nullValue()
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'TextFieldName'}.label",
			IsEqual.equalTo("TextFieldNameLabel_es")
		).body(
			"_embedded.StructuredContent[0]._links.self.href",
			IsEqual.equalTo(structuredContentURL.toExternalForm())
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testGetStructuredContentWithAcceptedLanguageEqualsLanguageNotAvailableInStructureContent()
		throws Exception {

		URL structuredContentURL = _createStructuredContent(
			LocaleUtil.US, "test-get-structured-content-us.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		_updateStructuredContent(
			structuredContentURL, LocaleUtil.SPAIN,
			"test-get-structured-content-es.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "de-DE"
		).when(
		).get(
			_structuredContentEndpointURL.toExternalForm() +
				"?filter=title eq 'Example Structured Content in English'"
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0].title",
			IsEqual.equalTo("Example Structured Content in English")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.inputControl",
			IsEqual.equalTo("checkbox")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyColor'}.inputControl",
			IsNull.nullValue()
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'TextFieldName'}.label",
			IsEqual.equalTo("TextFieldNameLabel_us")
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testGetStructuredContentWithoutAcceptedLanguageAndDefaultLanguageDifferentThanUserLanguage()
		throws Exception {

		URL structuredContentURL = _createStructuredContent(
			LocaleUtil.SPAIN, "test-get-structured-content-es.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_structuredContentEndpointURL.toExternalForm() +
				"?filter=title eq 'Example Structured Content in Spanish'"
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0].title",
			IsEqual.equalTo("Example Structured Content in Spanish")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.inputControl",
			IsEqual.equalTo("checkbox")
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyColor'}.inputControl",
			IsNull.nullValue()
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'TextFieldName'}.label",
			IsEqual.equalTo("TextFieldNameLabel_us")
		).body(
			"_embedded.StructuredContent[0]._links.self.href",
			IsEqual.equalTo(structuredContentURL.toExternalForm())
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testStructuredContentsMatchesSelfLink() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_structuredContentEndpointURL.toExternalForm()
		).then(
		).statusCode(
			200
		).body(
			"_links.self.href",
			Matchers.startsWith(_structuredContentEndpointURL.toExternalForm())
		);
	}

	@Test
	public void testUpdateStructuredContent() throws Exception {
		URL structuredContentURL = _createStructuredContent(
			LocaleUtil.US, "test-create-structured-content.json",
			Collections.singletonList(_contentStructureURL.toExternalForm()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			FileTestUtil.readFile(
				"test-update-structured-content.json", getClass(),
				Collections.singletonList(
					_contentStructureURL.toExternalForm()))
		).when(
		).put(
			structuredContentURL.toExternalForm()
		).then(
		).body(
			"title", Matchers.equalTo("Example Structured Content Modified")
		).body(
			"_embedded.values._embedded.find {it.name == 'MyDecimal'}.value",
			Matchers.equalTo("2.0")
		);
	}

	private URL _createStructuredContent(
			Locale locale, String fileName, List<String> vars)
		throws Exception {

		return new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).header(
				"Accept-Language", LocaleUtil.toW3cLanguageId(locale)
			).header(
				"Content-Type", "application/json"
			).body(
				FileTestUtil.readFile(fileName, getClass(), vars)
			).when(
			).post(
				_structuredContentEndpointURL.toExternalForm()
			).then(
			).extract(
			).path(
				"_links.self.href"
			));
	}

	private void _updateStructuredContent(
			URL structuredContentURL, Locale locale, String fileName,
			List<String> vars)
		throws Exception {

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", LocaleUtil.toW3cLanguageId(locale)
		).header(
			"Content-Type", "application/json"
		).body(
			FileTestUtil.readFile(fileName, getClass(), vars)
		).when(
		).put(
			structuredContentURL.toExternalForm()
		);
	}

	private URL _contentSpaceURL;
	private URL _contentStructureURL;
	private URL _structuredContentEndpointURL;

	@ArquillianResource
	private URL _url;

}