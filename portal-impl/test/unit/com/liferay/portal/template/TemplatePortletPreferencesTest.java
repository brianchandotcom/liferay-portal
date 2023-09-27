/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.ToolDependencies;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author László Csontos
 */
public class TemplatePortletPreferencesTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ToolDependencies.wireCaches();

		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			new SecureXMLFactoryProviderImpl());

		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			new PortletPreferencesFactoryImpl());
	}

	@Before
	public void setUp() throws Exception {
		_executorService = Executors.newFixedThreadPool(_THREADS_SIZE);

		_templatePortletPreferences = new TemplatePortletPreferences();
	}

	@After
	public void tearDown() {
		_executorService.shutdownNow();
	}

	@Test
	public void testGetPreferences() throws Exception {
		Callable<String> callable = new TemplateCallable();

		List<Future<String>> futures = new ArrayList<>(_THREADS_SIZE);

		for (int i = 0; i < _THREADS_SIZE; i++) {
			futures.add(_executorService.submit(callable));
		}

		for (Future<String> future : futures) {
			String xml = future.get();

			PortletPreferencesImpl portletPreferencesImpl =
				(PortletPreferencesImpl)
					PortletPreferencesFactoryUtil.fromDefaultXML(xml);

			Map<String, String[]> map = portletPreferencesImpl.getMap();

			Assert.assertEquals(map.toString(), 1, map.size());
		}
	}

	private static final int _THREADS_SIZE;

	static {
		Runtime runtime = Runtime.getRuntime();

		_THREADS_SIZE = runtime.availableProcessors();
	}

	private ExecutorService _executorService;
	private TemplatePortletPreferences _templatePortletPreferences;

	private class TemplateCallable implements Callable<String> {

		@Override
		public String call() throws Exception {
			String randomString = RandomTestUtil.randomString(8);

			// Synchronization was necessary here in order to make the scenario
			// deterministically fail. Otherwise there would be only statistical
			// ways to prove that the fix indeed eliminates the race condition.

			synchronized (_templatePortletPreferences) {
				return _templatePortletPreferences.getPreferences(
					randomString, randomString);
			}
		}

	}

}