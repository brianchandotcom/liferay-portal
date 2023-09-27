/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.webdav.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
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
			HashMapDictionaryBuilder.<String, Object>put(
				"service.ranking", Integer.MAX_VALUE
			).put(
				"webdav.storage.token", _TOKEN
			).build());
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