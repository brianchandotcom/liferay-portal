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

package com.liferay.portal.vulcan.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carlos Correa
 */
public class UriInfoUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);

		PropsUtil.setProps(_props);
	}

	@Test
	public void testGetAbsolutePath() {
		Mockito.when(
			_uriBuilder.build()
		).thenReturn(
			_uri
		);
		Mockito.when(
			_uriInfo.getAbsolutePathBuilder()
		).thenReturn(
			_uriBuilder
		);

		Assert.assertEquals(
			String.valueOf(_uri), UriInfoUtil.getAbsolutePath(_uriInfo));

		Mockito.verify(
			_uriBuilder
		).build();

		Mockito.verify(
			_uriInfo
		).getAbsolutePathBuilder();
	}

	@Test
	public void testGetAbsolutePathHttps() {
		Mockito.when(
			_uriBuilder.build()
		).thenReturn(
			_uri
		);

		Mockito.when(
			_uriBuilder.scheme(Mockito.anyString())
		).thenReturn(
			_uriBuilder
		);

		Mockito.when(
			_uriInfo.getAbsolutePathBuilder()
		).thenReturn(
			_uriBuilder
		);

		_setProtocol(Http.HTTPS);

		Assert.assertEquals(
			String.valueOf(_uri), UriInfoUtil.getAbsolutePath(_uriInfo));

		Mockito.verify(
			_uriBuilder
		).build();

		Mockito.verify(
			_uriBuilder
		).scheme(
			Http.HTTPS
		);

		Mockito.verify(
			_uriInfo
		).getAbsolutePathBuilder();
	}

	@Test
	public void testGetAbsolutePathPathContext() {
		String path = StringPool.SLASH + RandomTestUtil.randomString();

		Mockito.when(
			_uri.getPath()
		).thenReturn(
			path
		);

		Mockito.when(
			_uriBuilder.build()
		).thenReturn(
			_uri
		);

		Mockito.when(
			_uriBuilder.replacePath(Mockito.anyString())
		).thenReturn(
			_uriBuilder
		);

		Mockito.when(
			_uriInfo.getAbsolutePathBuilder()
		).thenReturn(
			_uriBuilder
		);

		String pathContext = StringPool.SLASH + RandomTestUtil.randomString();

		_setPathContext(path, pathContext);

		Assert.assertEquals(
			String.valueOf(_uri), UriInfoUtil.getAbsolutePath(_uriInfo));

		Mockito.verify(
			_uri
		).getPath();

		Mockito.verify(
			_uriBuilder, Mockito.times(2)
		).build();

		Mockito.verify(
			_uriBuilder
		).replacePath(
			pathContext + path
		);

		Mockito.verify(
			_uriInfo
		).getAbsolutePathBuilder();
	}

	@Test
	public void testGetBaseUriBuilder() {
		Mockito.when(
			_uriInfo.getBaseUriBuilder()
		).thenReturn(
			_uriBuilder
		);

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Mockito.verify(
			_uriInfo
		).getBaseUriBuilder();
	}

	@Test
	public void testGetBaseUriBuilderHttps() {
		Mockito.when(
			_uriBuilder.scheme(Mockito.anyString())
		).thenReturn(
			_uriBuilder
		);

		Mockito.when(
			_uriInfo.getBaseUriBuilder()
		).thenReturn(
			_uriBuilder
		);

		_setProtocol(Http.HTTPS);

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Mockito.verify(
			_uriBuilder
		).scheme(
			Http.HTTPS
		);

		Mockito.verify(
			_uriInfo
		).getBaseUriBuilder();
	}

	@Test
	public void testGetBaseUriBuilderPathContext() {
		String path = StringPool.SLASH + RandomTestUtil.randomString();

		Mockito.when(
			_uri.getPath()
		).thenReturn(
			path
		);

		Mockito.when(
			_uriBuilder.build()
		).thenReturn(
			_uri
		);

		Mockito.when(
			_uriBuilder.replacePath(Mockito.anyString())
		).thenReturn(
			_uriBuilder
		);

		Mockito.when(
			_uriInfo.getBaseUriBuilder()
		).thenReturn(
			_uriBuilder
		);

		String pathContext = StringPool.SLASH + RandomTestUtil.randomString();

		_setPathContext(path, pathContext);

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Mockito.verify(
			_uri
		).getPath();

		Mockito.verify(
			_uriBuilder
		).build();

		Mockito.verify(
			_uriBuilder
		).replacePath(
			pathContext + path
		);

		Mockito.verify(
			_uriInfo
		).getBaseUriBuilder();
	}

	private void _setPathContext(String path, String pathContext) {
		Mockito.when(
			_portal.getPathContext()
		).thenReturn(
			pathContext
		);

		Mockito.when(
			_portal.getPathContext(Mockito.anyString())
		).thenReturn(
			pathContext + path
		);
	}

	private void _setProtocol(String protocol) {
		Mockito.when(
			_props.get(PropsKeys.WEB_SERVER_PROTOCOL)
		).thenReturn(
			protocol
		);
	}

	private final Portal _portal = Mockito.mock(Portal.class);
	private final Props _props = Mockito.mock(Props.class);
	private final URI _uri = Mockito.mock(URI.class);
	private final UriBuilder _uriBuilder = Mockito.mock(UriBuilder.class);
	private final UriInfo _uriInfo = Mockito.mock(UriInfo.class);

}