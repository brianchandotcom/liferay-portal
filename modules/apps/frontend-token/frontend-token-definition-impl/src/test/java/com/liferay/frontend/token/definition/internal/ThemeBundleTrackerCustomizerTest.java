/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
public class ThemeBundleTrackerCustomizerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayUnitTestRule();

	@Before
	public void setUp() {
		_manager = Mockito.mock(FrontendTokenDefinitionManager.class);

		Portal portal = Mockito.mock(Portal.class);

		_themeBundleTrackerCustomizer = new ThemeBundleTrackerCustomizer(
			_manager, portal);
	}

	@Test
	public void testAddingBundle() {
		Bundle bundle = Mockito.mock(Bundle.class);
		BundleEvent bundleEvent = Mockito.mock(BundleEvent.class);

		try (MockedStatic<ResourceBundleLoaderUtil>
				resourceBundleLoaderUtilMockedStatic = Mockito.mockStatic(
					ResourceBundleLoaderUtil.class)) {

			ResourceBundleLoader resourceBundleLoader = Mockito.mock(
				ResourceBundleLoader.class);

			resourceBundleLoaderUtilMockedStatic.when(
				() ->
					ResourceBundleLoaderUtil.
						getResourceBundleLoaderByBundleSymbolicName(
							Mockito.anyString())
			).thenReturn(
				resourceBundleLoader
			);

			_themeBundleTrackerCustomizer.addingBundle(bundle, bundleEvent);

			Mockito.verify(
				_manager
			).addFrontendTokenDefinition(
				Mockito.any(), Mockito.any(), Mockito.any()
			);
		}
	}

	@Test
	public void testRemoveBundle() {
		Bundle bundle = Mockito.mock(Bundle.class);
		BundleEvent bundleEvent = Mockito.mock(BundleEvent.class);
		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl = Mockito.mock(
			FrontendTokenDefinitionImpl.class);

		_themeBundleTrackerCustomizer.removedBundle(
			bundle, bundleEvent, frontendTokenDefinitionImpl);

		Mockito.verify(
			_manager
		).removeFrontendTokenDefinition(
			Mockito.any()
		);
	}

	private FrontendTokenDefinitionManager _manager;
	private ThemeBundleTrackerCustomizer _themeBundleTrackerCustomizer;

}