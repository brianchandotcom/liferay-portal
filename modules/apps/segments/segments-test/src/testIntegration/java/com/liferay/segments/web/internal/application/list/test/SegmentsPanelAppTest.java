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

package com.liferay.segments.web.internal.application.list.test;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.constants.SegmentsPortletKeys;

import java.util.Collection;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class SegmentsPanelAppTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(SegmentsPanelAppTest.class);

		_bundleContext = bundle.getBundleContext();

		Collection<ServiceReference<PanelApp>> serviceReferences =
			_bundleContext.getServiceReferences(
				PanelApp.class,
				"(panel.category.key=site_administration.members)");

		Assert.assertTrue(serviceReferences.size() > 1);

		for (ServiceReference<PanelApp> serviceReference : serviceReferences) {
			PanelApp panelApp = _bundleContext.getService(serviceReference);

			Class<? extends PanelApp> clazz = panelApp.getClass();

			if (Objects.equals(
					clazz.getCanonicalName(),
					"com.liferay.segments.web.internal.application.list." +
						"SegmentsPanelApp")) {

				_serviceReference = serviceReference;

				_panelApp = panelApp;
			}
		}

		Assert.assertNotNull(_panelApp);
	}

	@After
	public void tearDown() throws Exception {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testGetKey() {
		Assert.assertEquals(
			"com.liferay.segments.web.internal.application.list." +
				"SegmentsPanelApp",
			_panelApp.getKey());
	}

	@Test
	public void testGetLabel() {
		Assert.assertEquals("Segments", _panelApp.getLabel(LocaleUtil.US));
	}

	@Test
	public void testGetPanelCategoryKey() {
		Assert.assertEquals(
			PanelCategoryKeys.SITE_ADMINISTRATION_MEMBERS,
			_serviceReference.getProperty("panel.category.key"));
	}

	@Test
	public void testGetPortletId() {
		Assert.assertEquals(
			SegmentsPortletKeys.SEGMENTS, _panelApp.getPortletId());
	}

	private BundleContext _bundleContext;
	private PanelApp _panelApp;
	private ServiceReference<PanelApp> _serviceReference;

}