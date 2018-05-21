/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.util;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Beslic
 */
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class WebServerUtilTest extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(PropsUtil.class);
	}

	@Test
	public void testNoWebServerGetOriginalURL() throws Exception {
		setUpPropsUtil(null, "-1", "-1");

		StringBuffer portalServerURL = new StringBuffer(
			"http://liferay.com:8080/path/to/resource");

		Assert.assertNull(WebServerUtil.getWebServerURL(portalServerURL));

		setUpPropsUtil("https", "-1", "-1");

		Assert.assertNull(WebServerUtil.getWebServerURL(portalServerURL));
	}

	@Test
	public void testProxyWebServerGetOriginalURL() throws Exception {
		setUpPropsUtil(null, "80", "-1");

		StringBuffer portalServerURL = new StringBuffer(
			"http://liferay.com:8080/path/to/resource");

		Assert.assertEquals(
			"http://liferay.com/path/to/resource",
			WebServerUtil.getWebServerURL(portalServerURL));

		setUpPropsUtil(null, "80", "443");

		Assert.assertEquals(
			"http://liferay.com/path/to/resource",
			WebServerUtil.getWebServerURL(portalServerURL));

		setUpPropsUtil(null, "9080", "443");

		Assert.assertEquals(
			"http://liferay.com:9080/path/to/resource",
			WebServerUtil.getWebServerURL(portalServerURL));

		portalServerURL = new StringBuffer(
			"https://liferay.com:8443/path/to/resource");

		setUpPropsUtil("https", "-1", "443");

		Assert.assertEquals(
			"https://liferay.com/path/to/resource",
			WebServerUtil.getWebServerURL(portalServerURL));

		setUpPropsUtil("https", "80", "443");

		Assert.assertEquals(
			"https://liferay.com/path/to/resource",
			WebServerUtil.getWebServerURL(portalServerURL));

		setUpPropsUtil("https", "80", "9443");

		Assert.assertEquals(
			"https://liferay.com:9443/path/to/resource",
			WebServerUtil.getWebServerURL(portalServerURL));
	}

	@Test
	public void testTerminateHTTPSWebServerGetOriginalURL() throws Exception {
		setUpPropsUtil("https", "80", "443");

		StringBuffer portalServerURL = new StringBuffer(
			"http://liferay.com:8080/path/to/resource");

		Assert.assertEquals(
			"https://liferay.com/path/to/resource",
			WebServerUtil.getWebServerURL(portalServerURL));
	}

	protected void setUpPropsUtil(
		String protocol, String httpPort, String httpsPort) {

		when(
			PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL)
		).thenReturn(
			protocol
		);

		when(
			PropsUtil.get(PropsKeys.WEB_SERVER_HTTP_PORT)
		).thenReturn(
			httpPort
		);

		when(
			PropsUtil.get(PropsKeys.WEB_SERVER_HTTPS_PORT)
		).thenReturn(
			httpsPort
		);
	}

}