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

package com.liferay.portal.remote.soap.extender.test;

import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.portal.remote.soap.extender.test.internal.service.Greeter;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@BndFile("bnd-component-handler.bnd")
@RunAsClient
@RunWith(Arquillian.class)
public class JaxWsComponentHandlerRegistrationTest {

	@Test
	public void testHandlerIsRegistered() throws Exception {
		URL url = new URL(_url, "/o/soap-test/greeter?wsdl");

		QName qName = new QName(
			"http://service.internal.test.extender.soap.remote.portal.liferay" +
				".com/",
			"GreeterImplService");

		Service service = Service.create(url, qName);

		Greeter greeter = service.getPort(Greeter.class);

		String greeting = greeter.greet();

		Assert.assertTrue(greeting.endsWith("was handled."));
	}

	@ArquillianResource
	private URL _url;

}