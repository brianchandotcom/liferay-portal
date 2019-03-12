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

package com.liferay.portal.remote.cors.client.test;

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.HttpImpl;

import java.io.File;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.cxf.jaxrs.provider.json.JSONProvider;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.BeforeClass;

import org.osgi.framework.BundleActivator;

/**
 * @author Marta Medio
 */
public abstract class BaseCORSClientTestCase {

	public static Archive<?> getArchive(
			Class<? extends BundleActivator> bundleActivatorClass)
		throws Exception {

		return OAuth2ProviderTestUtil.getArchive(
			bundleActivatorClass, new File("bnd.bnd"));
	}

	@BeforeClass
	public static void setUpClass() {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());

		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());
	}

	protected static Client getClient() {
		Client client = ClientBuilder.newClient();

		return client.register(JSONProvider.class);
	}

	protected WebTarget getWebTarget(String... paths) {
		Client client = getClient();

		WebTarget target = client.target(_getPortalURL());

		target = target.path("o");

		for (String path : paths) {
			target = target.path(path);
		}

		return target;
	}

	private URI _getPortalURL() {
		try {
			return _url.toURI();
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException(urise);
		}
	}

	@ArquillianResource
	private URL _url;

}