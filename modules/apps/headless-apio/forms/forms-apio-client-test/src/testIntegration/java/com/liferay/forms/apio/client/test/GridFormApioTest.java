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

import com.liferay.forms.apio.client.test.internal.activator.GridFormApioTestBundleActivator;
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
 * @author Marcelo Mello
 */
@RunAsClient
@RunWith(Arquillian.class)
public class GridFormApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			GridFormApioTestBundleActivator.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		URL rootEndpointURL = new URL(_url, "/o/api");

		URL contentSpaceHrefURL = new URL(
			ContentSpaceApioTestUtil.getContentSpaceHref(
				rootEndpointURL.toExternalForm(),
				GridFormApioTestBundleActivator.SITE_NAME));

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
	public void testGetGridField() {
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
			withArgs(GridFormApioTestBundleActivator.GRID_FIELD_NAME)
		).body(
			"hasFormRules", isBoolean()
		).body(
			"required", isBoolean()
		).body(
			"showLabel", isBoolean()
		);
	}

	@Test
	public void testGridFieldColumnsIsDisplayed() {
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
				"find {it.name == '%s'}._embedded.columns._embedded",
			withArgs(GridFormApioTestBundleActivator.GRID_FIELD_NAME)
		).body(
			"size()", equalTo(3)
		).appendRoot(
			"find {it.value == 'Column1'}"
		).body(
			"label", equalTo("Column 1")
		).detachRoot(
			"find {it.value == 'Column1'}"
		).appendRoot(
			"find {it.value == 'Column2'}"
		).body(
			"label", equalTo("Column 2")
		).detachRoot(
			"find {it.value == 'Column2'}"
		).appendRoot(
			"find {it.value == 'Column3'}"
		).body(
			"label", equalTo("Column 3")
		);
	}

	@Test
	public void testGridFieldDataTypeIsDisplayed() {
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
			withArgs(GridFormApioTestBundleActivator.GRID_FIELD_NAME)
		).body(
			"dataType", equalTo("string")
		);
	}

	@Test
	public void testGridFieldInputControlIsDisplayed() {
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
			withArgs(GridFormApioTestBundleActivator.GRID_FIELD_NAME)
		).body(
			"inputControl", equalTo("grid")
		);
	}

	@Test
	public void testGridFieldLabelIsDisplayed() {
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
			withArgs(GridFormApioTestBundleActivator.GRID_FIELD_NAME)
		).body(
			"label", equalTo("My Grid Field")
		);
	}

	@Test
	public void testGridFieldRowsIsDisplayed() {
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
				"find {it.name == '%s'}._embedded.rows._embedded",
			withArgs(GridFormApioTestBundleActivator.GRID_FIELD_NAME)
		).body(
			"size()", equalTo(3)
		).appendRoot(
			"find {it.value == 'Row1'}"
		).body(
			"label", equalTo("Row 1")
		).detachRoot(
			"find {it.value == 'Row1'}"
		).appendRoot(
			"find {it.value == 'Row2'}"
		).body(
			"label", equalTo("Row 2")
		).detachRoot(
			"find {it.value == 'Row2'}"
		).appendRoot(
			"find {it.value == 'Row3'}"
		).body(
			"label", equalTo("Row 3")
		);
	}

	private URL _formHrefURL;

	@ArquillianResource
	private URL _url;

}