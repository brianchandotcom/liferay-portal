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
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
public class MetadataManagerImplTest extends BaseSamlTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGetRequestPath() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				HttpMethods.GET,
				"/c/portal/login;jsessionid=ACD311312312323BF.worker1");

		Assert.assertEquals(
			"/c/portal/login",
			metadataManagerImpl.getRequestPath(mockHttpServletRequest));
	}

	@Test
	public void testGetRequestPathWithContext() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				HttpMethods.GET,
				"/portal/c/portal/login;jsessionid=ACD311312312323BF.worker1");

		mockHttpServletRequest.setContextPath("/portal");

		Assert.assertEquals(
			"/c/portal/login",
			metadataManagerImpl.getRequestPath(mockHttpServletRequest));
	}

	@Test
	public void testGetRequestPathWithoutJsessionId() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(HttpMethods.GET, "/c/portal/login");

		Assert.assertEquals(
			"/c/portal/login",
			metadataManagerImpl.getRequestPath(mockHttpServletRequest));
	}

}