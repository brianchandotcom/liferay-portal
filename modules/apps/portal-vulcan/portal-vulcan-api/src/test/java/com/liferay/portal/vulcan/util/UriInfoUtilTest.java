/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

import org.apache.cxf.jaxrs.impl.UriBuilderImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carlos Correa
 * @author Raymond Augé
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

		Mockito.when(
			_uriInfo.getBaseUriBuilder()
		).thenReturn(
			_uriBuilder
		);

		_setProtocol(null);
	}

	@After
	public void tearDown() {
		PropsUtil.set(PropsKeys.WEB_SERVER_HOST, null);
		PropsUtil.set(PropsKeys.WEB_SERVER_HTTP_PORT, null);
		PropsUtil.set(PropsKeys.WEB_SERVER_HTTPS_PORT, null);
	}

	@Test
	public void testGetBaseUriBuilderHostNoScheme() throws Exception {
		_uriBuilder.host("localhost");

		_assertUriBuilder(
			0, "", 0, 0, _uriBuilder, _uriInfo, "localhost/test-path");
	}

	@Test
	public void testGetBaseUriBuilderHostScheme() throws Exception {
		_uriBuilder.host("localhost");
		_uriBuilder.scheme("http");

		_assertUriBuilder(
			0, "", 0, 0, _uriBuilder, _uriInfo, "http://localhost/test-path");
	}

	@Test
	public void testGetBaseUriBuilderHttpsHostNoScheme() throws Exception {
		_uriBuilder.host("localhost");

		_setProtocol(Http.HTTPS);

		_assertUriBuilder(
			0, "", 0, 1, _uriBuilder, _uriInfo, "https://localhost/test-path");
	}

	@Test
	public void testGetBaseUriBuilderHttpsHostScheme() throws Exception {
		_uriBuilder.host("localhost");
		_uriBuilder.scheme("http");

		_setProtocol(Http.HTTPS);

		_assertUriBuilder(
			0, "", 0, 1, _uriBuilder, _uriInfo, "https://localhost/test-path");
	}

	@Test
	public void testGetBaseUriBuilderHttpsNoHostNoScheme() throws Exception {
		_setProtocol(Http.HTTPS);

		_assertUriBuilder(0, "", 0, 0, _uriBuilder, _uriInfo, "/test-path");
	}

	@Test
	public void testGetBaseUriBuilderNoHostNoScheme() throws Exception {
		_assertUriBuilder(0, "", 0, 0, _uriBuilder, _uriInfo, "/test-path");
	}

	@Test
	public void testGetBaseUriBuilderPathContext() throws Exception {
		String path = StringPool.SLASH + RandomTestUtil.randomString();

		String pathContext = StringPool.SLASH + RandomTestUtil.randomString();

		_setPathContext(path, pathContext);

		_assertUriBuilder(
			1, pathContext + path, 1, 0, _uriBuilder, _uriInfo,
			pathContext + path);

		_assertUriBuilder(
			1, pathContext + path, 0, 0, _uriBuilder, _uriInfo,
			pathContext + path);
	}

	@Test
	public void testGetBaseUriBuilderWebServerHostHttpCustomPort()
		throws Exception {

		int httpPort = RandomTestUtil.randomInt(
			_PORT_MIN_INCLUSIVE, _PORT_MAX_INCLUSIVE);

		PropsUtil.set(PropsKeys.WEB_SERVER_HOST, _WEB_SERVER_HOST);
		PropsUtil.set(PropsKeys.WEB_SERVER_PROTOCOL, Http.HTTP);
		PropsUtil.set(PropsKeys.WEB_SERVER_HTTP_PORT, String.valueOf(httpPort));

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Assert.assertEquals(
			new URI(
				StringBundler.concat(
					Http.HTTP_WITH_SLASH, _WEB_SERVER_HOST, StringPool.COLON,
					httpPort, _PATH)),
			_uriBuilder.build());
	}

	@Test
	public void testGetBaseUriBuilderWebServerHostHttpDefaultPort()
		throws Exception {

		PropsUtil.set(PropsKeys.WEB_SERVER_HOST, _WEB_SERVER_HOST);
		PropsUtil.set(PropsKeys.WEB_SERVER_PROTOCOL, Http.HTTP);
		PropsUtil.set(
			PropsKeys.WEB_SERVER_HTTP_PORT, String.valueOf(Http.HTTP_PORT));

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Assert.assertEquals(
			new URI(Http.HTTP_WITH_SLASH + _WEB_SERVER_HOST + _PATH),
			_uriBuilder.build());
	}

	@Test
	public void testGetBaseUriBuilderWebServerHostHttpsCustomPort()
		throws Exception {

		int httpsPort = RandomTestUtil.randomInt(
			_PORT_MIN_INCLUSIVE, _PORT_MAX_INCLUSIVE);

		PropsUtil.set(PropsKeys.WEB_SERVER_HOST, _WEB_SERVER_HOST);
		PropsUtil.set(PropsKeys.WEB_SERVER_PROTOCOL, Http.HTTPS);
		PropsUtil.set(
			PropsKeys.WEB_SERVER_HTTPS_PORT, String.valueOf(httpsPort));

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Assert.assertEquals(
			new URI(
				StringBundler.concat(
					Http.HTTPS_WITH_SLASH, _WEB_SERVER_HOST, StringPool.COLON,
					httpsPort, _PATH)),
			_uriBuilder.build());
	}

	@Test
	public void testGetBaseUriBuilderWebServerHostHttpsDefaultPort()
		throws Exception {

		PropsUtil.set(PropsKeys.WEB_SERVER_HOST, _WEB_SERVER_HOST);
		PropsUtil.set(PropsKeys.WEB_SERVER_PROTOCOL, Http.HTTPS);
		PropsUtil.set(
			PropsKeys.WEB_SERVER_HTTPS_PORT, String.valueOf(Http.HTTPS_PORT));

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Assert.assertEquals(
			new URI(Http.HTTPS_WITH_SLASH + _WEB_SERVER_HOST + _PATH),
			_uriBuilder.build());
	}

	@Test
	public void testGetBaseUriBuilderWebServerHostNullPreservesUriInfoHost()
		throws Exception {

		int spoofedPort = RandomTestUtil.randomInt(
			_PORT_MIN_INCLUSIVE, _PORT_MAX_INCLUSIVE);

		_uriBuilder.host(_SPOOFED_HOST);
		_uriBuilder.port(spoofedPort);

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Assert.assertEquals(
			new URI(
				StringBundler.concat(
					_SPOOFED_HOST, StringPool.COLON, spoofedPort, _PATH)),
			_uriBuilder.build());
	}

	@Test
	public void testGetBaseUriBuilderWebServerHostOverridesSpoofedHost()
		throws Exception {

		int spoofedPort = RandomTestUtil.randomInt(
			_PORT_MIN_INCLUSIVE, _PORT_MAX_INCLUSIVE);

		PropsUtil.set(PropsKeys.WEB_SERVER_HOST, _WEB_SERVER_HOST);
		PropsUtil.set(PropsKeys.WEB_SERVER_PROTOCOL, Http.HTTPS);
		PropsUtil.set(
			PropsKeys.WEB_SERVER_HTTPS_PORT, String.valueOf(Http.HTTPS_PORT));

		_uriBuilder.host(_SPOOFED_HOST);
		_uriBuilder.port(spoofedPort);
		_uriBuilder.scheme(Http.HTTP);

		Assert.assertSame(_uriBuilder, UriInfoUtil.getBaseUriBuilder(_uriInfo));

		Assert.assertEquals(
			new URI(Http.HTTPS_WITH_SLASH + _WEB_SERVER_HOST + _PATH),
			_uriBuilder.build());
	}

	private void _assertUriBuilder(
			int buildTimes, String path, int replacePathTimes, int schemeTimes,
			UriBuilder uriBuilder, UriInfo uriInfo, String uriString)
		throws Exception {

		Assert.assertSame(uriBuilder, UriInfoUtil.getBaseUriBuilder(uriInfo));

		Mockito.verify(
			uriBuilder, Mockito.times(buildTimes)
		).build();

		Mockito.verify(
			uriBuilder, Mockito.times(replacePathTimes)
		).replacePath(
			path
		);

		Mockito.verify(
			uriBuilder, Mockito.times(schemeTimes)
		).scheme(
			Http.HTTPS
		);

		Mockito.verify(
			uriInfo
		).getBaseUriBuilder();

		Assert.assertEquals(new URI(uriString), uriBuilder.build());

		Mockito.clearInvocations(uriBuilder, uriInfo);
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
		PropsUtil.set(PropsKeys.WEB_SERVER_PROTOCOL, protocol);
	}

	private static final String _PATH = "/test-path";

	private static final int _PORT_MAX_INCLUSIVE = 65535;

	private static final int _PORT_MIN_INCLUSIVE = 1025;

	private static final String _SPOOFED_HOST = "internal-host";

	private static final String _WEB_SERVER_HOST = "example.com";

	private final Portal _portal = Mockito.mock(Portal.class);
	private final UriBuilder _uriBuilder = Mockito.spy(
		new UriBuilderImpl(
		).path(
			_PATH
		));
	private final UriInfo _uriInfo = Mockito.mock(UriInfo.class);

}