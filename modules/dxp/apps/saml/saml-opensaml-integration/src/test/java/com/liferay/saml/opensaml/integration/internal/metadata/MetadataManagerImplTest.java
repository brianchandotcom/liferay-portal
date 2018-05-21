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

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.Http;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
@RunWith(PowerMockRunner.class)
public class MetadataManagerImplTest extends BaseSamlTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_http = mock(Http.class);

		metadataManagerImpl.setHttp(_http);
	}

	@Test
	public void testGetRequestPath() {
		when(
			_http.removePathParameters(
				"/c/portal/login;jsessionid=ACD311312312323BF.worker1")
		).thenReturn(
			"/c/portal/login"
		);

		MockHttpServletRequest request = new MockHttpServletRequest(
			HttpMethods.GET,
			"/c/portal/login;jsessionid=ACD311312312323BF.worker1");

		Assert.assertEquals(
			"/c/portal/login", metadataManagerImpl.getRequestPath(request));
	}

	@Test
	public void testGetRequestPathWithContext() {
		when(
			_http.removePathParameters(
				"/c/portal/login;jsessionid=ACD311312312323BF.worker1")
		).thenReturn(
			"/c/portal/login"
		);

		MockHttpServletRequest request = new MockHttpServletRequest(
			HttpMethods.GET,
			"/portal/c/portal/login;jsessionid=ACD311312312323BF.worker1");

		request.setContextPath("/portal");

		Assert.assertEquals(
			"/c/portal/login", metadataManagerImpl.getRequestPath(request));
	}

	@Test
	public void testGetRequestPathWithoutJsessionId() {
		when(
			_http.removePathParameters("/c/portal/login")
		).thenReturn(
			"/c/portal/login"
		);

		MockHttpServletRequest request = new MockHttpServletRequest(
			HttpMethods.GET, "/c/portal/login");

		Assert.assertEquals(
			"/c/portal/login", metadataManagerImpl.getRequestPath(request));
	}

	private Http _http;

}