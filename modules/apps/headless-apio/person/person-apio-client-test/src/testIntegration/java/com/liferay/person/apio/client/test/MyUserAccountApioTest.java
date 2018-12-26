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

package com.liferay.person.apio.client.test;

import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sarai Díaz
 */
@RunAsClient
@RunWith(Arquillian.class)
public class MyUserAccountApioTest {

	@Before
	public void setUp() throws MalformedURLException {
		_rootEndpointURL = new URL(_url, "/o/api");
	}

	@Test
	public void testGetMyUserAccount() throws Exception {
		String href = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).then(
		).extract(
		).path(
			"_links.user-account.href"
		);

		String userHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			_read("test-get-my-user-account-create-user.json")
		).when(
		).post(
			href
		).then(
		).statusCode(
			200
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
			_read("test-get-my-user-account-update-user.json")
		).when(
		).put(
			userHref
		).then(
		).statusCode(
			200
		);

		ApioClientBuilder.given(
		).basicAuth(
			"kate.williams@liferay.com", "wkate"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).follow(
			"_links.my-user-account.href"
		).then(
		).statusCode(
			200
		).body(
			"_embedded.'Liferay:UserAccount'.find {it.alternateName == " +
				"'katew'}",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:UserAccount'.find {it.birthDate == " +
				"'1993-09-13T00:00Z'}",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:UserAccount'.find {it.email == " +
				"'kate.williams@liferay.com'}",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:UserAccount'.find {it.familyName == " +
				"'Williams'}",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:UserAccount'.find {it.givenName == 'Kate'}",
			IsNull.notNullValue()
		).body(
			"_embedded.'Liferay:UserAccount'.find {it.jobTitle == 'UX " +
				"Designer'}",
			IsNull.notNullValue()
		);

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).when(
		).delete(
			userHref
		).then(
		).statusCode(
			200
		);
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		return String.format(StringUtil.read(url.openStream()));
	}

	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

}