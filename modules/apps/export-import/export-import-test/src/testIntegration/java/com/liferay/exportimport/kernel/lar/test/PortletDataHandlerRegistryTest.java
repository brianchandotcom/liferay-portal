/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerRegistry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

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
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class PortletDataHandlerRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetPortletDataHandler() throws Exception {
		Assert.assertEquals(
			_defaultPortletDataHandler,
			_portletDataHandlerRegistry.getPortletDataHandler(
				TestPropsValues.getCompanyId(), RandomTestUtil.randomString()));

		long companyId1 = RandomTestUtil.randomLong();
		long companyId2 = RandomTestUtil.randomLong();

		BasePortletDataHandler portletDataHandler1 =
			new TestPortletDataHandler();
		BasePortletDataHandler portletDataHandler2 =
			new TestPortletDataHandler();
		BasePortletDataHandler portletDataHandler3 =
			new TestPortletDataHandler();
		BasePortletDataHandler portletDataHandler4 =
			new TestPortletDataHandler();
		BasePortletDataHandler portletDataHandler5 =
			new TestPortletDataHandler();

		String portletId1 = RandomTestUtil.randomString();
		String portletId2 = RandomTestUtil.randomString();
		String portletId3 = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable1 = _registerServiceWithSafeCloseable(
				null, portletDataHandler1, portletId1);
			SafeCloseable safeCloseable2 = _registerServiceWithSafeCloseable(
				null, portletDataHandler2, portletId2);
			SafeCloseable safeCloseable3 = _registerServiceWithSafeCloseable(
				List.of(companyId1), portletDataHandler3, portletId1);
			SafeCloseable safeCloseable4 = _registerServiceWithSafeCloseable(
				List.of(companyId2), portletDataHandler4, portletId2);
			SafeCloseable safeCloseable5 = _registerServiceWithSafeCloseable(
				List.of(companyId1, companyId2), portletDataHandler5,
				portletId3)) {

			Assert.assertEquals(
				portletDataHandler1,
				_portletDataHandlerRegistry.getPortletDataHandler(
					RandomTestUtil.randomLong(), portletId1));
			Assert.assertEquals(
				portletDataHandler2,
				_portletDataHandlerRegistry.getPortletDataHandler(
					RandomTestUtil.randomLong(), portletId2));
			Assert.assertEquals(
				_defaultPortletDataHandler,
				_portletDataHandlerRegistry.getPortletDataHandler(
					companyId1, RandomTestUtil.randomString()));
			Assert.assertEquals(
				portletDataHandler3,
				_portletDataHandlerRegistry.getPortletDataHandler(
					companyId1, portletId1));
			Assert.assertEquals(
				portletDataHandler2,
				_portletDataHandlerRegistry.getPortletDataHandler(
					companyId1, portletId2));
			Assert.assertEquals(
				portletDataHandler5,
				_portletDataHandlerRegistry.getPortletDataHandler(
					companyId1, portletId3));
			Assert.assertEquals(
				_defaultPortletDataHandler,
				_portletDataHandlerRegistry.getPortletDataHandler(
					companyId2, RandomTestUtil.randomString()));
			Assert.assertEquals(
				portletDataHandler1,
				_portletDataHandlerRegistry.getPortletDataHandler(
					companyId2, portletId1));
			Assert.assertEquals(
				portletDataHandler4,
				_portletDataHandlerRegistry.getPortletDataHandler(
					companyId2, portletId2));
			Assert.assertEquals(
				portletDataHandler5,
				_portletDataHandlerRegistry.getPortletDataHandler(
					companyId2, portletId3));
		}
	}

	private SafeCloseable _registerServiceWithSafeCloseable(
		List<Long> companyIds, PortletDataHandler portletDataHandler,
		String portletId) {

		Bundle bundle = FrameworkUtil.getBundle(
			PortletDataHandlerRegistryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<PortletDataHandler> serviceRegistration =
			bundleContext.registerService(
				PortletDataHandler.class, portletDataHandler,
				HashMapDictionaryBuilder.<String, Object>put(
					"companyId",
					() -> (companyIds == null) ? null :
						TransformUtil.transform(companyIds, String::valueOf)
				).put(
					"jakarta.portlet.name", portletId
				).build());

		return serviceRegistration::unregister;
	}

	@Inject(filter = "jakarta.portlet.name=ALL")
	private PortletDataHandler _defaultPortletDataHandler;

	@Inject
	private PortletDataHandlerRegistry _portletDataHandlerRegistry;

	private static class TestPortletDataHandler extends BasePortletDataHandler {
	}

}