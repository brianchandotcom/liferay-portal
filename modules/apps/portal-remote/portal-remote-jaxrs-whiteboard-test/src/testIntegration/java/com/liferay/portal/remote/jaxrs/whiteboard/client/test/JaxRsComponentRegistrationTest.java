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

package com.liferay.portal.remote.jaxrs.whiteboard.client.test;

import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@RunAsClient
@RunWith(Arquillian.class)
public class JaxRsComponentRegistrationTest {

	@Test
	public void testIsRegistered() throws Exception {
		URL url = new URL(_url, "/o/test-rest/greeter1/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL(_url, "/o/test-rest/greeter2/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL(_url, "/o/test-rest/greeter3/sayHello");

		Assert.assertEquals("Hello.", StringUtil.read(url.openStream()));

		url = new URL(_url, "/o/test-rest/greeter3/addon");

		Assert.assertEquals("addon", StringUtil.read(url.openStream()));
	}

	@ArquillianResource
	private URL _url;

}