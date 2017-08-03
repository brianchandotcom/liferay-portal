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

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.saml.opensaml.integration.internal.bootstrap.OpenSamlBootstrap;

import java.io.InputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.opensaml.xml.parse.BasicParserPool;

/**
 * @author Mika Koivisto
 */
public class MetadataUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoaderPool.register(
			"saml-portlet", currentThread.getContextClassLoader());

		PortletClassLoaderUtil.setServletContextName("saml-portlet");

		OpenSamlBootstrap.bootstrap();

		_metadataUtil = new MetadataUtilImpl();

		_metadataUtil.parserPool = new BasicParserPool();
	}

	@AfterClass
	public static void tearDownClass() {
		ClassLoaderPool.unregister("saml-portlet");
	}

	@Test
	public void testParseEntitiesDescriptor() throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/entities-descriptor.xml");

		String metadata = _metadataUtil.parseMetadataXml(
			inputStream, "https://saml.liferay.com/shibboleth");

		Assert.assertNotNull(metadata);
	}

	@Test
	public void testParseEntityDescriptor() throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/entity-descriptor.xml");

		String metadata = _metadataUtil.parseMetadataXml(
			inputStream, "liferaysamlidpdemo");

		Assert.assertNotNull(metadata);
	}

	private static MetadataUtilImpl _metadataUtil;

}