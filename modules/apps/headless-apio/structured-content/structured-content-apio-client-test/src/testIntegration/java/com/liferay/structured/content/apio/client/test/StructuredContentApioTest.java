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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.portal.apio.test.util.ContentSpaceApioTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.structured.content.apio.client.test.internal.activator.StructuredContentApioTestBundleActivator;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

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
		URL rootEndpointURL = new URL(_url, "/o/api");

		URL contentSpaceEndpointURL = new URL(
			ContentSpaceApioTestUtil.getContentSpaceHref(
				rootEndpointURL.toExternalForm(),
				StructuredContentApioTestBundleActivator.SITE_NAME));

		_contentStructuresEndpointURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				contentSpaceEndpointURL.toExternalForm()
			).then(
			).extract(
			).path(
				"_links.contentStructures.href"
			));

		_structuredContentEndpointURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				contentSpaceEndpointURL.toExternalForm()
			).then(
			).extract(
			).path(
				"_links.structuredContents.href"
			));
	}

	@Test
	public void testCreateStructuredContent() throws Exception {
		String contentStructureId = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_contentStructuresEndpointURL.toExternalForm()
		).then(
		).extract(
		).path(
			"_embedded.Structure.find {it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"'}._links.self.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			_read(
				"test-create-structured-content.json",
				Collections.singletonList(contentStructureId))
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
		String contentStructureId = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_contentStructuresEndpointURL.toExternalForm()
		).then(
		).extract(
		).path(
			"_embedded.Structure.find {it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"'}._links.self.href"
		);

		String structuredContentHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			_read(
				"test-create-structured-content.json",
				Collections.singletonList(contentStructureId))
		).when(
		).post(
			_structuredContentEndpointURL.toExternalForm()
		).then(
		).extract(
		).path(
			"_links.self.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).when(
		).delete(
			structuredContentHref
		).then(
		).statusCode(
			Matchers.isOneOf(200, 204)
		);
	}

	@Test
	public void testGetStructuredContentWithAcceptedLanguageEqualsDefaultLocale() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			StringBundler.concat(
				_structuredContentEndpointURL.toExternalForm(),
				"?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_US, "'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0].title",
			IsEqual.equalTo(
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_US)
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
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'NestedTextFieldName'}.label",
			IsEqual.equalTo("NestedTextFieldNameLabel_us")
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testGetStructuredContentWithAcceptedLanguageEqualsLanguageAvailableInStructureContent() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "es-ES"
		).when(
		).get(
			StringBundler.concat(
				_structuredContentEndpointURL.toExternalForm(),
				"?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_ES, "'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0].title",
			IsEqual.equalTo(
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_ES)
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
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'NestedTextFieldName'}.label",
			IsEqual.equalTo("NestedTextFieldNameLabel_es")
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testGetStructuredContentWithAcceptedLanguageEqualsLanguageNotAvailableInStructureContent() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "de-DE"
		).when(
		).get(
			StringBundler.concat(
				_structuredContentEndpointURL.toExternalForm(),
				"?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_US, "'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0].title",
			IsEqual.equalTo(
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_US)
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
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'NestedTextFieldName'}.label",
			IsEqual.equalTo("NestedTextFieldNameLabel_us")
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testGetStructuredContentWithoutAcceptedLanguageAndDefaultLanguageDifferentThanUserLanguage() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			StringBundler.concat(
				_structuredContentEndpointURL.toExternalForm(),
				"?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_1_LOCALE_ES, "'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0].title",
			IsEqual.equalTo(
				StructuredContentApioTestBundleActivator.TITLE_1_LOCALE_ES)
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
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'NestedTextFieldName'}.label",
			IsEqual.equalTo("NestedTextFieldNameLabel_us")
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
		String contentStructureId = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_contentStructuresEndpointURL.toExternalForm()
		).then(
		).extract(
		).path(
			"_embedded.Structure.find {it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"'}._links.self.href"
		);

		String structuredContentHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			_read(
				"test-create-structured-content.json",
				Collections.singletonList(contentStructureId))
		).when(
		).post(
			_structuredContentEndpointURL.toExternalForm()
		).then(
		).extract(
		).path(
			"_links.self.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			_read(
				"test-update-structured-content.json",
				Collections.singletonList(contentStructureId))
		).when(
		).put(
			structuredContentHref
		).then(
		).body(
			"title", Matchers.equalTo("Example Structured Content Modified")
		).body(
			"_embedded.values._embedded.find {it.name == 'MyDecimal'}.value",
			Matchers.equalTo("2.0")
		);
	}

	private String _read(String fileName, List<String> vars) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		return String.format(StringUtil.read(url.openStream()), vars.toArray());
	}

	private URL _contentStructuresEndpointURL;
	private URL _structuredContentEndpointURL;

	@ArquillianResource
	private URL _url;

}