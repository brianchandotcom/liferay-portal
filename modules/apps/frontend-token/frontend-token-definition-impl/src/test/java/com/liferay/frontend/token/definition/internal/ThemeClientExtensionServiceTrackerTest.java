/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.frontend.token.definition.internal.util.FrontendTokenDefinitionTestUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
public class ThemeClientExtensionServiceTrackerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayUnitTestRule();

	@Before
	public void setUp() throws Exception {
		_bundleContext = Mockito.mock(BundleContext.class);
		_manager = Mockito.mock(FrontendTokenDefinitionManager.class);

		_tracker = new ThemeClientExtensionServiceTracker(
			_bundleContext, _manager);
	}

	@Test
	public void testAddingService() {
		ServiceReference<ThemeCSSCET> serviceReference = Mockito.mock(
			ServiceReference.class);

		ThemeCSSCET themeCSSCET =
			FrontendTokenDefinitionTestUtils.newDummyThemeCSSCET();

		Mockito.when(
			_bundleContext.getService(Mockito.any(ServiceReference.class))
		).thenReturn(
			themeCSSCET
		);

		_tracker.addingService(serviceReference);

		Mockito.verify(
			_manager
		).addFrontendTokenDefinition(
			themeCSSCET.getCompanyId(), themeCSSCET.getExternalReferenceCode(),
			themeCSSCET.getFrontendTokenDefinition()
		);
	}

	@Test
	public void testRemovedService() {
		ServiceReference<ThemeCSSCET> serviceReference = Mockito.mock(
			ServiceReference.class);

		ThemeCSSCET themeCSSCET =
			FrontendTokenDefinitionTestUtils.newDummyThemeCSSCET();

		Mockito.when(
			_bundleContext.getService(Mockito.any(ServiceReference.class))
		).thenReturn(
			themeCSSCET
		);

		_tracker.removedService(serviceReference, themeCSSCET);

		Mockito.verify(
			_manager
		).removeFrontendTokenDefinition(
			themeCSSCET.getCompanyId(), themeCSSCET.getExternalReferenceCode()
		);

		Mockito.verify(
			_bundleContext
		).ungetService(
			serviceReference
		);
	}

	private BundleContext _bundleContext;
	private FrontendTokenDefinitionManager _manager;
	private ThemeClientExtensionServiceTracker _tracker;

}