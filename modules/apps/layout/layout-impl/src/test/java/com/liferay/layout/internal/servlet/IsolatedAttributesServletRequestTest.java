/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.servlet;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Javier Moral
 */
public class IsolatedAttributesServletRequestTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAttribute() {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		String name = RandomTestUtil.randomString();
		String value1 = RandomTestUtil.randomString();

		httpServletRequest.setAttribute(name, value1);

		IsolatedAttributesServletRequest isolatedAttributesServletRequest =
			new IsolatedAttributesServletRequest(httpServletRequest);

		Assert.assertEquals(
			value1, isolatedAttributesServletRequest.getAttribute(name));

		String value2 = RandomTestUtil.randomString();

		isolatedAttributesServletRequest.setAttribute(name, value2);

		Assert.assertEquals(
			value2, isolatedAttributesServletRequest.getAttribute(name));

		Assert.assertEquals(value1, httpServletRequest.getAttribute(name));
	}

	@Test
	public void testGetAttributeNames() {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		String name1 = RandomTestUtil.randomString();

		httpServletRequest.setAttribute(name1, RandomTestUtil.randomString());

		IsolatedAttributesServletRequest isolatedAttributesServletRequest =
			new IsolatedAttributesServletRequest(httpServletRequest);

		isolatedAttributesServletRequest.removeAttribute(name1);

		String name2 = RandomTestUtil.randomString();

		isolatedAttributesServletRequest.setAttribute(
			name2, RandomTestUtil.randomString());

		List<String> names = Collections.list(
			isolatedAttributesServletRequest.getAttributeNames());

		Assert.assertEquals(names.toString(), 1, names.size());
		Assert.assertEquals(name2, names.get(0));
	}

	@Test
	public void testRemoveAttribute() {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		String name = RandomTestUtil.randomString();
		String value = RandomTestUtil.randomString();

		httpServletRequest.setAttribute(name, value);

		IsolatedAttributesServletRequest isolatedAttributesServletRequest =
			new IsolatedAttributesServletRequest(httpServletRequest);

		isolatedAttributesServletRequest.removeAttribute(name);

		Assert.assertNull(isolatedAttributesServletRequest.getAttribute(name));

		Assert.assertEquals(value, httpServletRequest.getAttribute(name));
	}

	@Test
	public void testSetAttribute() {
		_testSetAttribute();

		_testSetAttributeWithRequestDispatcherAttribute();
	}

	private void _testSetAttribute() {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		IsolatedAttributesServletRequest isolatedAttributesServletRequest =
			new IsolatedAttributesServletRequest(httpServletRequest);

		String name = RandomTestUtil.randomString();
		String value = RandomTestUtil.randomString();

		isolatedAttributesServletRequest.setAttribute(name, value);

		Assert.assertEquals(
			value, isolatedAttributesServletRequest.getAttribute(name));

		Assert.assertNull(httpServletRequest.getAttribute(name));
	}

	private void _testSetAttributeWithRequestDispatcherAttribute() {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		IsolatedAttributesServletRequest isolatedAttributesServletRequest =
			new IsolatedAttributesServletRequest(httpServletRequest);

		String value = RandomTestUtil.randomString();

		isolatedAttributesServletRequest.setAttribute(
			JavaConstants.JAKARTA_SERVLET_INCLUDE_REQUEST_URI, value);

		Assert.assertEquals(
			value,
			httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_SERVLET_INCLUDE_REQUEST_URI));
	}

}