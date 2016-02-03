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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Peter Fellwock
 */
public class AuthVerifierPipelineTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.authverifierpipeline"));

	@Test
	public void testVerifyRequest() {
		try {
			AccessControlContext accessControlContext =
				new AccessControlContext();

			MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
				"/foo/hello");

			mockHttpServletRequest.setAttribute(
				WebKeys.COMPANY_ID,
				Long.valueOf(TestPropsValues.getCompanyId()));

			accessControlContext.setRequest(mockHttpServletRequest);

			AuthVerifierPipeline.verifyRequest(accessControlContext);
		}
		catch (PortalException pe) {
			Assert.fail();
		}
	}

	protected MockHttpServletRequest createHttpRequest(String pathInfo) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequestTest();

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setPathInfo(pathInfo);

		return mockHttpServletRequest;
	}

	private class MockHttpServletRequestTest extends MockHttpServletRequest {

		public MockHttpServletRequestTest() {
			_mockServletContext = new MockServletContext() {};

			_mockServletContext.setContextPath(StringPool.BLANK);
			_mockServletContext.setServletContextName(StringPool.BLANK);
		}

		@Override
		public MockServletContext getServletContext() {
			return _mockServletContext;
		}

		private final MockServletContext _mockServletContext;

	}

}