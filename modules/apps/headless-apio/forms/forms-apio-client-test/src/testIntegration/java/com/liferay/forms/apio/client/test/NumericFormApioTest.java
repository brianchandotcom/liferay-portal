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

package com.liferay.forms.apio.client.test;

import static com.liferay.forms.apio.client.test.matcher.FormApioTestMatchers.isBoolean;

import static io.restassured.RestAssured.withArgs;

import static org.hamcrest.Matchers.equalTo;

import com.liferay.forms.apio.client.test.internal.activator.NumericFormApioTestBundleActivator;
import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.portal.apio.test.util.ContentSpaceApioTestUtil;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Paulo Cruz
 */
@RunAsClient
@RunWith(Arquillian.class)
public class NumericFormApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			NumericFormApioTestBundleActivator.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		URL rootEndpointURL = new URL(_url, "/o/api");

		URL contentSpaceHrefURL = new URL(
			ContentSpaceApioTestUtil.getContentSpaceHref(
				rootEndpointURL.toExternalForm(),
				NumericFormApioTestBundleActivator.SITE_NAME));

		_formHrefURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).header(
				"Accept-Language", "en-US"
			).when(
			).get(
				contentSpaceHrefURL.toExternalForm()
			).follow(
				"_links.forms.href"
			).then(
			).extract(
			).path(
				"_embedded.Form[0]._links.self.href"
			));
	}

	@Test
	public void testGetNumericFields() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_formHrefURL.toExternalForm() + "?embedded=structure"
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).root(
			"_embedded.formPages._embedded[0]._embedded.fields._embedded." +
				"find {it.name == '%s'}",
			withArgs(NumericFormApioTestBundleActivator.DOUBLE_FIELD_NAME)
		).body(
			"hasFormRules", isBoolean()
		).body(
			"showLabel", isBoolean()
		).body(
			"repeatable", isBoolean()
		).body(
			"required", isBoolean()
		).noRoot(
		).root(
			"_embedded.formPages._embedded[0]._embedded.fields._embedded." +
				"find {it.name == '%s'}",
			withArgs(NumericFormApioTestBundleActivator.INTEGER_FIELD_NAME)
		).body(
			"hasFormRules", isBoolean()
		).body(
			"showLabel", isBoolean()
		).body(
			"repeatable", isBoolean()
		).body(
			"required", isBoolean()
		);
	}

	@Test
	public void testNumericFieldsDataTypeIsDisplayed() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_formHrefURL.toExternalForm() + "?embedded=structure"
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).root(
			"_embedded.formPages._embedded[0]._embedded.fields._embedded." +
				"find {it.name == '%s'}",
			withArgs(NumericFormApioTestBundleActivator.DOUBLE_FIELD_NAME)
		).body(
			"dataType", equalTo("double")
		).noRoot(
		).root(
			"_embedded.formPages._embedded[0]._embedded.fields._embedded." +
				"find {it.name == '%s'}",
			withArgs(NumericFormApioTestBundleActivator.INTEGER_FIELD_NAME)
		).body(
			"dataType", equalTo("integer")
		);
	}

	@Test
	public void testNumericFieldsLabelIsDisplayed() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Accept-Language", "en-US"
		).when(
		).get(
			_formHrefURL.toExternalForm() + "?embedded=structure"
		).then(
		).log(
		).ifError(
		).statusCode(
			200
		).root(
			"_embedded.formPages._embedded[0]._embedded.fields._embedded." +
				"find {it.name == '%s'}",
			withArgs(NumericFormApioTestBundleActivator.DOUBLE_FIELD_NAME)
		).body(
			"label", equalTo("My Double Field")
		).noRoot(
		).root(
			"_embedded.formPages._embedded[0]._embedded.fields._embedded." +
				"find {it.name == '%s'}",
			withArgs(NumericFormApioTestBundleActivator.INTEGER_FIELD_NAME)
		).body(
			"label", equalTo("My Integer Field")
		);
	}

	private URL _formHrefURL;

	@ArquillianResource
	private URL _url;

}