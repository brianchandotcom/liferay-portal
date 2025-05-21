/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.util.test;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class PanelCategoryRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetServiceReferenceComparator() {
		ServiceRegistration<PanelCategory> serviceRegistration1 = null;
		ServiceRegistration<PanelCategory> serviceRegistration2 = null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.application.list.util.PanelCategoryRegistryUtil",
				LoggerTestUtil.WARN)) {

			Bundle bundle = FrameworkUtil.getBundle(
				PanelCategoryRegistryUtilTest.class);

			BundleContext bundleContext = bundle.getBundleContext();

			Dictionary<String, String> properties =
				HashMapDictionaryBuilder.put(
					"panel.category.key", "panelCategoryKey"
				).put(
					"panel.category.order", "100"
				).build();

			serviceRegistration1 = bundleContext.registerService(
				PanelCategory.class, new TestPanelCategory1(), properties);
			serviceRegistration2 = bundleContext.registerService(
				PanelCategory.class, new TestPanelCategory2(), properties);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"The panel categories \"testKey2\" and \"testKey1\" have the " +
					"same order 100 and category key \"panelCategoryKey\"",
				logEntry.getMessage());
		}
		finally {
			if (serviceRegistration1 != null) {
				serviceRegistration1.unregister();
			}

			if (serviceRegistration2 != null) {
				serviceRegistration2.unregister();
			}
		}
	}

	private static class TestPanelCategory1 extends BasePanelCategory {

		@Override
		public String getKey() {
			return "testKey1";
		}

		@Override
		public String getLabel(Locale locale) {
			return RandomTestUtil.randomString();
		}

	}

	private static class TestPanelCategory2 extends BasePanelCategory {

		@Override
		public String getKey() {
			return "testKey2";
		}

		@Override
		public String getLabel(Locale locale) {
			return RandomTestUtil.randomString();
		}

	}

}