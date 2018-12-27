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

import java.net.URL;

import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
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
	public void setUp() throws Exception {
		_rootEndpointURL = new URL(_url, "/o/api");

		String userAccountHrefURL = ApioClientBuilder.given(
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

		_userHrefURL = ApioClientBuilder.given(
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
			userAccountHrefURL
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
			_userHrefURL
		).then(
		).statusCode(
			200
		);

		_myUserAccountHref = ApioClientBuilder.given(
		).basicAuth(
			"kate.williams@liferay.com", "wkate"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_rootEndpointURL.toExternalForm()
		).then(
		).extract(
		).path(
			"_links.my-user-account.href"
		);
	}

	@After
	public void tearDown() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).when(
		).delete(
			_userHrefURL
		).then(
		).statusCode(
			200
		);
	}

	@Test
	public void testGetMyUserAccount() {
		ApioClientBuilder.given(
		).basicAuth(
			"kate.williams@liferay.com", "wkate"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_myUserAccountHref
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
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		return String.format(StringUtil.read(url.openStream()));
	}

	private String _myUserAccountHref;
	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;

	private String _userHrefURL;

}