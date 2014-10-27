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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class NamespaceServletRequestTest {

	@Test
	public void testNestedWrapping() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String parameterName1 = "parameterName1";
		String parameterValue1 = "parameterValue1";

		mockHttpServletRequest.setParameter(parameterName1, parameterValue1);

		NamespaceServletRequest namespaceServletRequest =
			new NamespaceServletRequest(
				mockHttpServletRequest, "attrNamespace", "paramNamespace",
				false);

		String parameterName2 = "parameterName2";
		String parameterValue2 = "parameterValue2";

		namespaceServletRequest.setParameter(parameterName2, parameterValue2);

		String attributeName = "attributeName";
		String attributeValue = "attributeValue";

		namespaceServletRequest.setAttribute(attributeName, attributeValue);

		DynamicServletRequest dynamicServletRequest = new DynamicServletRequest(
			namespaceServletRequest);

		String parameterName3 = "parameterName3";
		String parameterValue3 = "parameterValue3";

		dynamicServletRequest.setParameter(parameterName3, parameterValue3);

		Map<String, String[]> parameterMap =
			dynamicServletRequest.getParameterMap();

		Assert.assertEquals(3, parameterMap.size());
		Assert.assertArrayEquals(
			new String[] {parameterValue1}, parameterMap.get(parameterName1));
		Assert.assertArrayEquals(
			new String[] {parameterValue2}, parameterMap.get(parameterName2));
		Assert.assertArrayEquals(
			new String[] {parameterValue3}, parameterMap.get(parameterName3));

		Set<String> attributeNames = SetUtil.fromEnumeration(
			dynamicServletRequest.getAttributeNames());

		Assert.assertEquals(1, attributeNames.size());
		Assert.assertTrue(attributeNames.contains(attributeName));
		Assert.assertEquals(
			attributeValue, dynamicServletRequest.getAttribute(attributeName));
	}

}