/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.taglib.internal.jaxrs.application;

import com.liferay.frontend.data.set.provider.FDSDataProviderRegistry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Juanjo Fernandez
 */
public class FDSApplicationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_fdsApplication, "_fdsDataProviderRegistry",
			_fdsDataProviderRegistry);
	}

	@Test
	public void testGetFDSDataProvider() {
		Mockito.when(
			_fdsDataProviderRegistry.getFDSDataProvider("openapi.json")
		).thenReturn(
			null
		);

		Response response = _fdsApplication.getFDSData(
			"openapi.json", RandomTestUtil.randomString(), 0, 0, null, null,
			null, null, null, null, null);

		Assert.assertEquals(
			Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	private final FDSApplication _fdsApplication = new FDSApplication();
	private final FDSDataProviderRegistry _fdsDataProviderRegistry =
		Mockito.mock(FDSDataProviderRegistry.class);

}