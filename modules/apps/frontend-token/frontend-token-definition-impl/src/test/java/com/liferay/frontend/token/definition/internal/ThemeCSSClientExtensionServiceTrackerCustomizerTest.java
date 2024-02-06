/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
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
public class ThemeCSSClientExtensionServiceTrackerCustomizerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayUnitTestRule();

	@Before
	public void setUp() {
		_bundleContext = Mockito.mock(BundleContext.class);

		_frontendTokenDefinitionManager = Mockito.mock(
			FrontendTokenDefinitionManager.class);

		_themeCSSClientExtensionServiceTrackerCustomizer =
			new ThemeCSSClientExtensionServiceTrackerCustomizer(
				_bundleContext, _frontendTokenDefinitionManager);
	}

	@Test
	public void testAddingService() {
		ThemeCSSCET themeCSSCET =
			FrontendTokenDefinitionTestUtils.getDummyThemeCSSCET();

		Mockito.when(
			_bundleContext.getService(Mockito.any(ServiceReference.class))
		).thenReturn(
			themeCSSCET
		);

		_themeCSSClientExtensionServiceTrackerCustomizer.addingService(
			Mockito.mock(ServiceReference.class));

		Mockito.verify(
			_frontendTokenDefinitionManager
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
			FrontendTokenDefinitionTestUtils.getDummyThemeCSSCET();

		Mockito.when(
			_bundleContext.getService(Mockito.any(ServiceReference.class))
		).thenReturn(
			themeCSSCET
		);

		_themeCSSClientExtensionServiceTrackerCustomizer.removedService(
			serviceReference, themeCSSCET);

		Mockito.verify(
			_frontendTokenDefinitionManager
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
	private FrontendTokenDefinitionManager _frontendTokenDefinitionManager;
	private ThemeCSSClientExtensionServiceTrackerCustomizer
		_themeCSSClientExtensionServiceTrackerCustomizer;

}