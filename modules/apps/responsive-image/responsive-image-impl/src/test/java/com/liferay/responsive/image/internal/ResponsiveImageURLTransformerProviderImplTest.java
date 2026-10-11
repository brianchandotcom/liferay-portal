/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.responsive.image.ResponsiveImageURLTransformer;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageURLTransformerProviderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_responsiveImageURLTransformerProviderImpl, "_serviceTrackerMap",
			_serviceTrackerMap);
	}

	@Test
	public void testGetResponsiveImageURLTransformerWithBlankName() {
		_mockServiceTrackerMap(_RESPONSIVE_IMAGE_URL_TRANSFORMER_NAME);

		Assert.assertNull(
			_responsiveImageURLTransformerProviderImpl.
				getResponsiveImageURLTransformer(StringPool.BLANK));
	}

	@Test
	public void testGetResponsiveImageURLTransformerWithKnownName() {
		_mockServiceTrackerMap(
			_RESPONSIVE_IMAGE_URL_TRANSFORMER_NAME,
			RandomTestUtil.randomString());

		Assert.assertSame(
			_responsiveImageURLTransformer,
			_responsiveImageURLTransformerProviderImpl.
				getResponsiveImageURLTransformer(
					_RESPONSIVE_IMAGE_URL_TRANSFORMER_NAME));
	}

	@Test
	public void testGetResponsiveImageURLTransformerWithNullName() {
		_mockServiceTrackerMap(_RESPONSIVE_IMAGE_URL_TRANSFORMER_NAME);

		Assert.assertNull(
			_responsiveImageURLTransformerProviderImpl.
				getResponsiveImageURLTransformer(null));
	}

	@Test
	public void testGetResponsiveImageURLTransformerWithUnknownName() {
		_mockServiceTrackerMap(_RESPONSIVE_IMAGE_URL_TRANSFORMER_NAME);

		Assert.assertNull(
			_responsiveImageURLTransformerProviderImpl.
				getResponsiveImageURLTransformer(
					RandomTestUtil.randomString()));
	}

	private void _mockServiceTrackerMap(String... names) {
		Set<String> responsiveImageURLTransformerNames = new LinkedHashSet<>();

		Collections.addAll(responsiveImageURLTransformerNames, names);

		Mockito.when(
			_serviceTrackerMap.keySet()
		).thenReturn(
			responsiveImageURLTransformerNames
		);

		for (String responsiveImageURLTransformerName :
				responsiveImageURLTransformerNames) {

			Mockito.when(
				_serviceTrackerMap.getService(responsiveImageURLTransformerName)
			).thenReturn(
				_responsiveImageURLTransformer
			);
		}
	}

	private static final String _RESPONSIVE_IMAGE_URL_TRANSFORMER_NAME =
		RandomTestUtil.randomString();

	private final ResponsiveImageURLTransformer _responsiveImageURLTransformer =
		Mockito.mock(ResponsiveImageURLTransformer.class);
	private final ResponsiveImageURLTransformerProviderImpl
		_responsiveImageURLTransformerProviderImpl =
			new ResponsiveImageURLTransformerProviderImpl();
	private final ServiceTrackerMap<String, ResponsiveImageURLTransformer>
		_serviceTrackerMap = Mockito.mock(ServiceTrackerMap.class);

}