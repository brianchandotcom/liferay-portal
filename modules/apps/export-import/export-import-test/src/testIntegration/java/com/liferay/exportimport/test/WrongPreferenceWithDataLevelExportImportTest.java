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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Dictionary;

import javax.portlet.Portlet;

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
 * @author Arthur Chan
 */
@RunWith(Arquillian.class)
public class WrongPreferenceWithDataLevelExportImportTest
	extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testExportLayoutPortlets() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			WrongPreferenceWithDataLevelExportImportTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, String> portletProperties =
			MapUtil.singletonDictionary("javax.portlet.name", _PORTLET_NAME);

		ServiceRegistration<Portlet> portletServiceRegistration =
			bundleContext.registerService(
				Portlet.class, new MVCPortlet(), portletProperties);

		ServiceRegistration<PortletDataHandler>
			portletDataHandlerServiceRegistration =
				bundleContext.registerService(
					PortletDataHandler.class,
					new BasePortletDataHandler() {
					},
					portletProperties);

		try {
			exportLayouts(
				new long[] {layout.getLayoutId()}, getExportParameterMap(),
				true);

			Assert.fail();
		}
		catch (PortletDataException portletDataException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portletDataException);
			}
		}
		finally {
			portletServiceRegistration.unregister();
			portletDataHandlerServiceRegistration.unregister();
		}
	}

	private static final String _PORTLET_NAME =
		"com_liferay_exportimport_test_WrongPreferenceWithDataLevelPortlet";

	private static final Log _log = LogFactoryUtil.getLog(
		WrongPreferenceWithDataLevelExportImportTest.class);

}