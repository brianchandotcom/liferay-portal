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

package com.liferay.portal.sharepoint;

import com.liferay.portal.util.PropsUtil;

import javax.servlet.FilterConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mariano Alvaro Saiz
 */
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class SharepointFilterTest {

	@Test
	public void testSharepointFilterIsDisabled() {
		PowerMockito.mockStatic(PropsUtil.class);

		Mockito.when(
			PropsUtil.get(SharepointFilter.class.getName())
		).thenReturn(
			"false"
		);

		SharepointFilter sharepointFilter = new SharepointFilter();

		sharepointFilter.init(_filterConfig);

		Assert.assertFalse(sharepointFilter.isFilterEnabled());
	}

	@Test
	public void testSharepointFilterIsEnabled() {
		PowerMockito.mockStatic(PropsUtil.class);

		Mockito.when(
			PropsUtil.get(SharepointFilter.class.getName())
		).thenReturn(
			"true"
		);

		SharepointFilter sharepointFilter = new SharepointFilter();

		sharepointFilter.init(_filterConfig);

		Assert.assertTrue(sharepointFilter.isFilterEnabled());
	}

	@Mock
	private FilterConfig _filterConfig;

}