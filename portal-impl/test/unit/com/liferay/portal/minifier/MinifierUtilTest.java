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

package com.liferay.portal.minifier;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Iván Zaera Avellón
 */
public class MinifierUtilTest extends Mockito {

	@Before
	public void setUp() {
		Registry registry = mock(Registry.class);

		when(
			registry.setRegistry(any(Registry.class))
		).thenReturn(
			registry
		);

		when(
			registry.getRegistry()
		).thenReturn(
			registry
		);

		ServiceTracker serviceTracker = mock(ServiceTracker.class);

		when(
			registry.trackServices(any(Class.class))
		).thenReturn(
			serviceTracker
		);

		RegistryUtil.setRegistry(registry);
	}

	@Test
	public void testProcessMinifiedCssWithMultipleOps() {
		String minifiedCss = MinifierUtil.minifyCss(
			"margin: calc(10px+50%*2/3);");

		Assert.assertEquals("margin:calc(10px + 50% * 2 / 3);", minifiedCss);
	}

	@Test
	public void testProcessMinifiedCssWithNegativeNumbers() {
		String minifiedCss = MinifierUtil.minifyCss(
			"left: calc(-1px + -1px - -1px * -1px / -1px - 1px);");

		Assert.assertEquals(
			"left:calc(-1px + -1px - -1px * -1px / -1px - 1px);", minifiedCss);
	}

	@Test
	public void testProcessMinifiedCssWithParentheses() {
		String minifiedCss = MinifierUtil.minifyCss(
			"left: calc((10px+50%)*2+20px);");

		Assert.assertEquals("left:calc((10px + 50%) * 2 + 20px);", minifiedCss);
	}

	@Test
	public void testProcessMinifiedCssWithSimpleOps() {
		String minifiedCss = MinifierUtil.minifyCss(
			"margin: calc(10px+50%) calc(10px-50%) calc(10px*50%) " +
				"calc(10px/50%);");

		Assert.assertEquals(
			"margin:calc(10px + 50%) calc(10px - 50%) calc(10px * 50%) " +
				"calc(10px / 50%);",
			minifiedCss);
	}

}