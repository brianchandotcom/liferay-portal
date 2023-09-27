/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.web.internal.processor;

import com.liferay.adaptive.media.AMURIResolver;
import com.liferay.adaptive.media.web.internal.constants.AMWebConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class DefaultAMURIResolverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);

		ReflectionTestUtil.setFieldValue(_amURIResolver, "_portal", _portal);
	}

	@Test
	public void testMediaURIWhenPathDoesNotEndInSlash() {
		String pathModule = StringPool.SLASH + RandomTestUtil.randomString();

		Mockito.when(
			_portal.getPathModule()
		).thenReturn(
			pathModule
		);

		URI relativeURI = URI.create(RandomTestUtil.randomString());

		String uriString = String.valueOf(
			_amURIResolver.resolveURI(relativeURI));

		Assert.assertTrue(uriString, uriString.contains(pathModule));
		Assert.assertTrue(
			uriString, uriString.contains(AMWebConstants.SERVLET_PATH));
		Assert.assertTrue(
			uriString, uriString.contains(relativeURI.toString()));
	}

	@Test
	public void testMediaURIWhenPathEndsInSlash() {
		String pathModule =
			StringPool.SLASH + RandomTestUtil.randomString() + StringPool.SLASH;

		Mockito.when(
			_portal.getPathModule()
		).thenReturn(
			pathModule
		);

		URI relativeURI = URI.create(RandomTestUtil.randomString());

		String uriString = String.valueOf(
			_amURIResolver.resolveURI(relativeURI));

		Assert.assertTrue(uriString, uriString.contains(pathModule));
		Assert.assertTrue(
			uriString, uriString.contains(AMWebConstants.SERVLET_PATH));
		Assert.assertTrue(
			uriString, uriString.contains(relativeURI.toString()));
	}

	private final AMURIResolver _amURIResolver = new DefaultAMURIResolver();
	private final Portal _portal = Mockito.mock(Portal.class);

}