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

package com.liferay.portal.template;

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.tools.ToolDependencies;
import com.liferay.portal.util.HtmlImpl;
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
import org.junit.Test;

/**
 * @author László Csontos
 */
public class TemplatePortletPreferencesTest {

	@BeforeClass
	public static void setUpClass() {
		ToolDependencies.wireCaches();

		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

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