/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class NamespaceServletRequestTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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

		Assert.assertEquals(parameterMap.toString(), 3, parameterMap.size());
		Assert.assertArrayEquals(
			new String[] {parameterValue1}, parameterMap.get(parameterName1));
		Assert.assertArrayEquals(
			new String[] {parameterValue2}, parameterMap.get(parameterName2));
		Assert.assertArrayEquals(
			new String[] {parameterValue3}, parameterMap.get(parameterName3));

		Set<String> attributeNames = SetUtil.fromEnumeration(
			dynamicServletRequest.getAttributeNames());

		Assert.assertEquals(
			attributeNames.toString(), 1, attributeNames.size());
		Assert.assertTrue(
			attributeNames.toString(), attributeNames.contains(attributeName));

		Assert.assertEquals(
			attributeValue, dynamicServletRequest.getAttribute(attributeName));
	}

}