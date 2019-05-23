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

package com.liferay.portal.vulcan.internal.fields.nested.servlet;

import com.liferay.portal.vulcan.internal.util.request.MockHttpServletRequest;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class NestedHttpServletRequestWrapperTest {

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

}