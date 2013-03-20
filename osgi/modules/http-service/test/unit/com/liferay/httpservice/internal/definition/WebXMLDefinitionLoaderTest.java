/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.definition;

import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.xml.SAXReaderImpl;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.osgi.framework.Bundle;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Miguel Pastor
 */
@PrepareForTest(SAXReaderUtil.class)
@RunWith(PowerMockRunner.class)
public class WebXMLDefinitionLoaderTest extends PowerMockito {

	@BeforeClass
	public static void setUp() {
		spy(SAXReaderUtil.class);

		when(
			SAXReaderUtil.getSAXReader()
		).thenReturn(
			new SAXReaderImpl()
		);
	}

	@AfterClass
	public static void tearDown() {
		verifyStatic();
	}

	public WebXMLDefinitionLoaderTest() throws DocumentException {
		_webXMLDefinitionLoader = new WebXMLDefinitionLoader();
	}

	@Test
	public void testReadDefaultConfig() throws Exception {
		mockDefaultLoad();

		_webXMLDefinitionLoader.loadWebXML(_bundle);

		verifyDefaultLoad();
	}

	@SuppressWarnings("rawtypes")
	protected void mockDefaultLoad() throws ClassNotFoundException {
		for (String clazz : _CLASSES) {
			when(
				_bundle.loadClass(clazz)
			).thenReturn(
				(Class)Class.forName(clazz)
			);
		}
	}

	protected void verifyDefaultLoad() throws ClassNotFoundException {
		Bundle bundle = Mockito.verify(_bundle);

		for (String clazz : _CLASSES) {
			bundle.loadClass(clazz);
		}
	}

	private static final String[] _CLASSES = {
		"org.eclipse.jetty.servlet.listener.ELContextCleaner",
		"org.eclipse.jetty.servlet.listener.IntrospectorCleaner",
		"org.apache.jasper.servlet.JspServlet",
		"com.liferay.httpservice.servlet.ResourceServlet"
	};

	@Mock
	private Bundle _bundle;
	private WebXMLDefinitionLoader _webXMLDefinitionLoader;

}