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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portlet.internal.PortletContainerImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Akos Thurzo
 */
public class SecurityPortletContainerWrapperTest {

	@Test
	public void testIsValidPortletId() {
		PortletContainer portletContainer = new PortletContainerImpl();

		SecurityPortletContainerWrapper securityPortletContainerWrapper =
			new SecurityPortletContainerWrapper(portletContainer);

		Assert.assertTrue(
			securityPortletContainerWrapper.isValidPortletId("aaa"));
		Assert.assertTrue(
			securityPortletContainerWrapper.isValidPortletId("AAA"));
		Assert.assertTrue(
			securityPortletContainerWrapper.isValidPortletId("123"));
		Assert.assertTrue(
			securityPortletContainerWrapper.isValidPortletId("aA1"));
		Assert.assertTrue(
			securityPortletContainerWrapper.isValidPortletId("aaa_bbb"));
		Assert.assertTrue(
			securityPortletContainerWrapper.isValidPortletId("aaa#bbb"));
		Assert.assertFalse(
			securityPortletContainerWrapper.isValidPortletId(
				"2_INSTANCE_'\"><script>alert(1)</script>"));
	}

}