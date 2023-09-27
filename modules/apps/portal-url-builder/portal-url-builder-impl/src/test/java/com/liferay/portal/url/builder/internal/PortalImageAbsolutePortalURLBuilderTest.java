/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.PortalImageAbsolutePortalURLBuilder;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class PortalImageAbsolutePortalURLBuilderTest
	extends BaseAbsolutePortalURLBuilderTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(name = "{0}: context={1}, proxy={2}, cdnHost={3}")
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, false, false, false}, {1, false, false, true},
				{2, true, false, false}, {3, true, true, false},
				{4, false, true, false}
			});
	}

	@Before
	public void setUp() throws Exception {
		_absolutePortalURLBuilder = new AbsolutePortalURLBuilderImpl(
			mockCacheHelper(), mockPortal(context, proxy, cdnHost),
			mockHttpServletRequest());

		_portalImageAbsolutePortalURLBuilder =
			_absolutePortalURLBuilder.forPortalImage("path/to/image.png");
	}

	@Test
	public void test() {
		Assert.assertEquals(
			_RESULTS[index], _portalImageAbsolutePortalURLBuilder.build());
	}

	@Test
	public void testIgnoreCDN() {
		_portalImageAbsolutePortalURLBuilder.ignoreCDNHost();

		Assert.assertEquals(
			_RESULTS_IGNORE_CDN[index],
			_portalImageAbsolutePortalURLBuilder.build());
	}

	@Parameterized.Parameter(3)
	public boolean cdnHost;

	@Parameterized.Parameter(1)
	public boolean context;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(2)
	public boolean proxy;

	private static final String[] _RESULTS = {
		"/image/path/to/image.png", "http://cdn-host/image/path/to/image.png",
		"/context/image/path/to/image.png",
		"/proxy/context/image/path/to/image.png",
		"/proxy/image/path/to/image.png"
	};

	private static final String[] _RESULTS_IGNORE_CDN = {
		"/image/path/to/image.png", "/image/path/to/image.png",
		"/context/image/path/to/image.png",
		"/proxy/context/image/path/to/image.png",
		"/proxy/image/path/to/image.png"
	};

	private AbsolutePortalURLBuilder _absolutePortalURLBuilder;
	private PortalImageAbsolutePortalURLBuilder
		_portalImageAbsolutePortalURLBuilder;

}