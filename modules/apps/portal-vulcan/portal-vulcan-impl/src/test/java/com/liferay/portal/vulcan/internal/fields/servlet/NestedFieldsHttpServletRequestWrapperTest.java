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

package com.liferay.portal.vulcan.internal.fields.servlet;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class NestedFieldsHttpServletRequestWrapperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetParameter() {
		NestedFieldsHttpServletRequestWrapper
			nestedFieldsHttpServletRequestWrapper =
				new NestedFieldsHttpServletRequestWrapper(
					"skus",
					new MockHttpServletRequest(
						"skus", "externalReferenceCode", "12345", "width",
						"11"));

		Assert.assertEquals(
			"12345",
			nestedFieldsHttpServletRequestWrapper.getParameter(
				"externalReferenceCode"));
		Assert.assertEquals(
			"11", nestedFieldsHttpServletRequestWrapper.getParameter("width"));
	}

	public static class MockHttpServletRequest
		extends org.springframework.mock.web.MockHttpServletRequest {

		public MockHttpServletRequest() {
		}

		public MockHttpServletRequest(String fieldName, String... parameters) {
			for (int i = 0; i < (parameters.length - 1); i++) {
				_parameters.put(
					fieldName + "." + parameters[i], parameters[i + 1]);
			}
		}

		@Override
		public String getParameter(String name) {
			return _parameters.get(name);
		}

		private Map<String, String> _parameters = new HashMap<>();

	}

}