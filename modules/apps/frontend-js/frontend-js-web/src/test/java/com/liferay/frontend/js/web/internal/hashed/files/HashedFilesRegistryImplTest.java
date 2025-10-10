/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.ServletContext;

import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

/**
 * @author Iván Zaera Avellón
 */
public class HashedFilesRegistryImplTest {

	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testGetResource() throws Exception {
		HashedFilesRegistryImpl hashedFilesRegistryImpl =
			new HashedFilesRegistryImpl();

		BundleContext bundleContext = Mockito.mock(BundleContext.class);

		hashedFilesRegistryImpl.activate(bundleContext);

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getPathContext()
		).thenReturn(
			StringPool.BLANK
		);

		ReflectionTestUtil.setFieldValue(
			hashedFilesRegistryImpl, "_portal", portal);

		ServiceTrackerMap<String, ServletContext> serviceTrackerMap =
			Mockito.mock(ServiceTrackerMap.class);

		ServletContext servletContext = Mockito.mock(ServletContext.class);

		Mockito.when(
			servletContext.getResource("/main.css")
		).thenReturn(
			Mockito.mock(URL.class)
		);

		Mockito.when(
			serviceTrackerMap.getService("/o/frontend-js-web")
		).thenReturn(
			servletContext
		);

		ReflectionTestUtil.setFieldValue(
			hashedFilesRegistryImpl, "_serviceTrackerMap", serviceTrackerMap);

		URL url = hashedFilesRegistryImpl.getResource(
			"/o/frontend-js-web/main.css");

		Assert.assertNotNull(url);
	}

	@Test
	public void testGetResourceWithPortalContext() throws Exception {
		HashedFilesRegistryImpl hashedFilesRegistryImpl =
			new HashedFilesRegistryImpl();

		BundleContext bundleContext = Mockito.mock(BundleContext.class);

		hashedFilesRegistryImpl.activate(bundleContext);

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getPathContext()
		).thenReturn(
			"liferay"
		);

		ReflectionTestUtil.setFieldValue(
			hashedFilesRegistryImpl, "_portal", portal);

		ServiceTrackerMap<String, ServletContext> serviceTrackerMap =
			Mockito.mock(ServiceTrackerMap.class);

		ServletContext servletContext = Mockito.mock(ServletContext.class);

		Mockito.when(
			servletContext.getResource("/main.css")
		).thenReturn(
			Mockito.mock(URL.class)
		);

		Mockito.when(
			serviceTrackerMap.getService("/liferay/o/frontend-js-web")
		).thenReturn(
			servletContext
		);

		ReflectionTestUtil.setFieldValue(
			hashedFilesRegistryImpl, "_serviceTrackerMap", serviceTrackerMap);

		URL url = hashedFilesRegistryImpl.getResource(
			"/liferay/o/frontend-js-web/main.css");

		Assert.assertNotNull(url);
	}

}