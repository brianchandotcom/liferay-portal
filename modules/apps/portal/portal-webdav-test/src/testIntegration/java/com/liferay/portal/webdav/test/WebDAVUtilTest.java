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

package com.liferay.portal.webdav.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
@RunWith(Arquillian.class)
public class WebDAVUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(WebDAVUtilTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_webDAVStorage = ProxyFactory.newDummyInstance(WebDAVStorage.class);

		_serviceRegistration = bundleContext.registerService(
			WebDAVStorage.class, _webDAVStorage,
			new HashMapDictionary<String, Object>() {
				{
					put("service.ranking", Integer.MAX_VALUE);
					put("webdav.storage.token", _TOKEN);
				}
			});
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetStorage() {
		Assert.assertSame(_webDAVStorage, WebDAVUtil.getStorage(_TOKEN));
	}

	@Test
	public void testGetStorageTokens() {
		Collection<String> storageTokens = WebDAVUtil.getStorageTokens();

		Assert.assertTrue(
			_TOKEN + " not found in " + storageTokens,
			storageTokens.contains(_TOKEN));
	}

	private static final String _TOKEN = "TOKEN";

	private static ServiceRegistration<WebDAVStorage> _serviceRegistration;
	private static WebDAVStorage _webDAVStorage;

}