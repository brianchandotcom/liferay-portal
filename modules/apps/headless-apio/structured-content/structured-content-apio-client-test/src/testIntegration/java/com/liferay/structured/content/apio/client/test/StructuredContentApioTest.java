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
import com.liferay.structured.content.apio.client.test.activator.StructuredContentApioTestBundleActivator;

import java.net.MalformedURLException;
import java.net.URL;

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
		_rootEndpointURL = new URL(_url, "/o/api");
	}

	@Test
	public void testAdminUserSeesAllStructuredContents() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItems(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_NO_GROUP,
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_YES_GROUP,
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP)
		);
	}

	@Test
	public void testBooleanFieldDataTypeIsDisplayed() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		);

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
				href, "?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_DEFAULT,
				"'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.dataType",
			IsEqual.equalTo("boolean")
		);
	}

	@Test
	public void testBooleanFieldInputControlIsDisplayed() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		);

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
				href, "?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_DEFAULT,
				"'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyBoolean'}.inputControl",
			IsEqual.equalTo("checkbox")
		);
	}

	@Test
	public void testColorFieldInputControlIsNotDisplayed() throws Exception {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		);

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
				href, "?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_DEFAULT,
				"'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.find " +
				"{it.name == 'MyColor'}.inputControl",
			IsNull.nullValue()
		);
	}

	@Test
	public void testDefaultStructuredFieldLabelIsDisplayedWhenAcceptLanguageIsSpecifiedAndDoesNotMatch() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		);

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
				href, "?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_DEFAULT,
				"'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.label",
			Matchers.hasItems(
				"NestedTextFieldNameLabel_us", "TextFieldNameLabel_us")
		);
	}

	@Test
	public void testDefaultStructuredFieldValueIsDisplayedWhenAcceptLanguageIsSpecifiedAndDoesNotMatch() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		);

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
				href, "?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_DEFAULT,
				"'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.value",
			Matchers.hasItems("NestedTextFieldValue_us", "TextFieldValue_us")
		);
	}

	@Test
	public void testDefaultTitleIsDisplayedWhenAcceptLanguageIsNotSpecified() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItem(
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_DEFAULT)
		);
	}

	@Test
	public void testDefaultTitleIsDisplayedWhenAcceptLanguageIsSpecifiedAndDoesNotMatch() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "de-DE"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItem(
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_DEFAULT)
		);
	}

	@Test
	public void testGuestUserSeesRightStructuredContents() {
		ApioClientBuilder.given(
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent.title",
			Matchers.not(
				Matchers.hasItem(
					StructuredContentApioTestBundleActivator.
						TITLE_NO_GUEST_NO_GROUP))
		).body(
			"_embedded.StructuredContent.title",
			Matchers.not(
				Matchers.hasItem(
					StructuredContentApioTestBundleActivator.
						TITLE_NO_GUEST_NO_GROUP))
		).body(
			"_embedded.StructuredContent.title",
			Matchers.not(
				Matchers.hasItem(
					StructuredContentApioTestBundleActivator.
						TITLE_NO_GUEST_YES_GROUP))
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItem(
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP)
		);
	}

	@Test
	public void testLocalizedStructuredFieldLabelIsDisplayedWhenAcceptLanguageIsSpecifiedAndMatches() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "es-ES"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		);

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
				href, "?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_ES, "'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.label",
			Matchers.hasItems(
				"NestedTextFieldNameLabel_es", "TextFieldNameLabel_es")
		);
	}

	@Test
	public void testLocalizedStructuredFieldValueIsDisplayedWhenAcceptLanguageIsSpecifiedAndMatches() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "es-ES"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		);

		ApioClientBuilder.given(
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "es-ES"
		).when(
		).get(
			StringBundler.concat(
				href, "?filter=title eq '",
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_ES, "'")
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent", Matchers.hasSize(1)
		).body(
			"_embedded.StructuredContent[0]._embedded.values._embedded.value",
			Matchers.hasItems("NestedTextFieldValue_es", "TextFieldValue_es")
		);
	}

	@Test
	public void testLocalizedTitleIsDisplayedWhenAcceptLanguageIsSpecifiedAndMatches() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "es-ES"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItem(
				StructuredContentApioTestBundleActivator.TITLE_2_LOCALE_ES)
		);
	}

	@Test
	public void testNotSiteMemberUserSeesRightStructuredContents() {
		ApioClientBuilder.given(
		).basicAuth(
			StructuredContentApioTestBundleActivator.
				NOT_A_SITE_MEMBER_EMAIL_ADDRESS,
			"test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent.title",
			Matchers.not(
				Matchers.hasItem(
					StructuredContentApioTestBundleActivator.
						TITLE_NO_GUEST_NO_GROUP))
		).body(
			"_embedded.StructuredContent.title",
			Matchers.not(
				Matchers.hasItem(
					StructuredContentApioTestBundleActivator.
						TITLE_NO_GUEST_YES_GROUP))
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItem(
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP)
		);
	}

	@Test
	public void testSetDefaultTitleIsDisplayedWhenAcceptLanguageIsNotSpecified() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItem(
				StructuredContentApioTestBundleActivator.TITLE_1_LOCALE_ES)
		);
	}

	@Test
	public void testSiteMemberUserSeesRightStructuredContents() {
		ApioClientBuilder.given(
		).basicAuth(
			StructuredContentApioTestBundleActivator.SITE_MEMBER_EMAIL_ADDRESS,
			"test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.StructuredContent.title",
			Matchers.not(
				Matchers.hasItems(
					StructuredContentApioTestBundleActivator.
						TITLE_NO_GUEST_NO_GROUP))
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItems(
				StructuredContentApioTestBundleActivator.
					TITLE_NO_GUEST_YES_GROUP)
		).body(
			"_embedded.StructuredContent.title",
			Matchers.hasItems(
				StructuredContentApioTestBundleActivator.
					TITLE_YES_GUEST_YES_GROUP)
		);
	}

	@Test
	public void testStructuredContentsExistsInContentSpaceEndpoint() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).follow(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		).then(
		).statusCode(
			200
		).body(
			"_links.self.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testStructuredContentsMatchesSelfLink() {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.content-space.href"
		).then(
		).extract(
		).path(
			"_embedded.ContentSpace.find { it.name == '" +
				StructuredContentApioTestBundleActivator.SITE_NAME +
					"' }._links.structuredContents.href"
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			href
		).then(
		).statusCode(
			200
		).body(
			"_links.self.href", Matchers.startsWith(href)
		);
	}

	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}