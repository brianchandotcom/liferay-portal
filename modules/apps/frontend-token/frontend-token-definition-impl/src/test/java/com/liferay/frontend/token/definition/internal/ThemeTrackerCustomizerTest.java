/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URL;

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
public class ThemeTrackerCustomizerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayUnitTestRule();

	@Before
	public void setUp() {
		_frontendTokenDefinitionManager = Mockito.mock(
			FrontendTokenDefinitionManager.class);

		_themeTrackerCustomizer = new ThemeTrackerCustomizer(
			_frontendTokenDefinitionManager, Mockito.mock(Portal.class));
	}

	@Test
	public void testAddingBundle() {
		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getEntry(Mockito.anyString())
		).thenReturn(
			Mockito.mock(URL.class)
		);

		MockedStatic<URLUtil> urlUtilMockedStatic = Mockito.mockStatic(
			URLUtil.class);

		urlUtilMockedStatic.when(
			() -> URLUtil.toString(Mockito.any())
		).thenReturn(
			"{}"
		);

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

			_themeTrackerCustomizer.addingBundle(
				bundle, Mockito.mock(BundleEvent.class));

			Mockito.verify(
				_frontendTokenDefinitionManager
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

		_themeTrackerCustomizer.removedBundle(
			bundle, bundleEvent, frontendTokenDefinitionImpl);

		Mockito.verify(
			_frontendTokenDefinitionManager
		).removeFrontendTokenDefinition(
			Mockito.any()
		);
	}

	private FrontendTokenDefinitionManager _frontendTokenDefinitionManager;
	private ThemeTrackerCustomizer _themeTrackerCustomizer;

}