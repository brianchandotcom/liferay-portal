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

package com.liferay.portal.servlet.filters.secure;

import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.util.PropsUtil;

import javax.servlet.FilterConfig;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mariano Álvaro Sáiz
 */
public class SecureFilterTest {

	@Test
	public void testSecureFilterIsEnabledIfDisabled() {
		PropsUtil.set(SecureFilter.class.getName(), "false");

		SecureFilter secureFilter = new SecureFilter();

		secureFilter.init(_filterConfig);

		Assert.assertTrue(secureFilter.isFilterEnabled());
	}

	@Test
	public void testSecureFilterIsEnabledIfEnabled() {
		PropsUtil.set(SecureFilter.class.getName(), "true");

		SecureFilter secureFilter = new SecureFilter();

		secureFilter.init(_filterConfig);

		Assert.assertTrue(secureFilter.isFilterEnabled());
	}

	private final FilterConfig _filterConfig = ProxyFactory.newDummyInstance(
		FilterConfig.class);

}