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

package com.liferay.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.test.util.PropsTestUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class ItemSelectorViewDescriptorRendererDisplayContextTest {

	@BeforeClass
	public static void setUpClass() {
		PropsTestUtil.setProps(Collections.emptyMap());
	}

	@Test
	public void testGetDisplayStyle() {
		ItemSelectorViewDescriptorRendererDisplayContext
			itemSelectorViewDescriptorRendererDisplayContext =
				new ItemSelectorViewDescriptorRendererDisplayContext(
					new MockHttpServletRequest(), null, null, null);

		Assert.assertEquals(
			"icon",
			itemSelectorViewDescriptorRendererDisplayContext.getDisplayStyle());
	}

	@Test
	public void testGetDisplayStyleWithDisplayStyleParameter() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter("displayStyle", "descriptive");

		ItemSelectorViewDescriptorRendererDisplayContext
			itemSelectorViewDescriptorRendererDisplayContext =
				new ItemSelectorViewDescriptorRendererDisplayContext(
					mockHttpServletRequest, null, null, null);

		Assert.assertEquals(
			"descriptive",
			itemSelectorViewDescriptorRendererDisplayContext.getDisplayStyle());
	}

}