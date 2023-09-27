/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.extender.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.net.URL;

import java.util.Random;

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
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class JSONWebServiceTrackerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			JSONWebServiceTrackerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			Object.class, new TestWebService(),
			HashMapDictionaryBuilder.<String, Object>put(
				"json.web.service.context.name", "test"
			).put(
				"json.web.service.context.path", "webservice"
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testWebServiceContextAppearsInTheSummary() throws IOException {
		URL url = new URL("http://localhost:8080/api/jsonws");

		String body = StringUtil.read(url.openStream());

		Assert.assertTrue(body, body.contains("test"));
	}

	@Test
	public void testWebServiceInvocation() throws IOException {
		Random random = new Random();

		int a = random.nextInt(50);
		int b = random.nextInt(50);

		URL url = new URL(
			StringBundler.concat(
				"http://localhost:8080/api/jsonws/test.testweb/sum/a/", a,
				"/b/", b));

		Assert.assertEquals(
			a + b, GetterUtil.getInteger(StringUtil.read(url.openStream())));
	}

	@JSONWebService
	public static class TestWebService {

		public int sum(int a, int b) {
			return a + b;
		}

	}

	private static ServiceRegistration<?> _serviceRegistration;

}