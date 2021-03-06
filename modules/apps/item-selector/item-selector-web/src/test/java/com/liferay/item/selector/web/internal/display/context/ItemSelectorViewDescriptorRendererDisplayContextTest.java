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

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class ItemSelectorViewDescriptorRendererDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PropsTestUtil.setProps(Collections.emptyMap());
	}

	@Test
	public void testGetDisplayStyle() {
		ItemSelectorViewDescriptorRendererDisplayContext
			itemSelectorViewDescriptorRendererDisplayContext =
				new ItemSelectorViewDescriptorRendererDisplayContext(
					new MockHttpServletRequest(), null,
					new ItemSelectorViewDescriptor<Object>() {

						@Override
						public ItemDescriptor getItemDescriptor(Object object) {
							return null;
						}

						@Override
						public ItemSelectorReturnType
							getItemSelectorReturnType() {

							return null;
						}

						@Override
						public SearchContainer<Object> getSearchContainer() {
							return null;
						}

					},
					null, null);

		Assert.assertEquals(
			"icon",
			itemSelectorViewDescriptorRendererDisplayContext.getDisplayStyle());
	}

	@Test
	public void testGetDisplayStyleWithDefaultDisplayStyle() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter("displayStyle", "descriptive");

		ItemSelectorViewDescriptorRendererDisplayContext
			itemSelectorViewDescriptorRendererDisplayContext =
				new ItemSelectorViewDescriptorRendererDisplayContext(
					mockHttpServletRequest, null,
					new ItemSelectorViewDescriptor<Object>() {

						@Override
						public String getDefaultDisplayStyle() {
							return "descriptive";
						}

						@Override
						public ItemDescriptor getItemDescriptor(Object object) {
							return null;
						}

						@Override
						public ItemSelectorReturnType
							getItemSelectorReturnType() {

							return null;
						}

						@Override
						public SearchContainer<Object> getSearchContainer() {
							return null;
						}

					},
					null, null);

		Assert.assertEquals(
			"descriptive",
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
					mockHttpServletRequest, null,
					new ItemSelectorViewDescriptor<Object>() {

						@Override
						public ItemDescriptor getItemDescriptor(Object object) {
							return null;
						}

						@Override
						public ItemSelectorReturnType
							getItemSelectorReturnType() {

							return null;
						}

						@Override
						public SearchContainer<Object> getSearchContainer() {
							return null;
						}

					},
					null, null);

		Assert.assertEquals(
			"descriptive",
			itemSelectorViewDescriptorRendererDisplayContext.getDisplayStyle());
	}

}