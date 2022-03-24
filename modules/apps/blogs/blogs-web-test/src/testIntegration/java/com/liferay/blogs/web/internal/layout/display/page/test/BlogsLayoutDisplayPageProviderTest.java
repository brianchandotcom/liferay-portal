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

package com.liferay.blogs.web.internal.layout.display.page.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class BlogsLayoutDisplayPageProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetLayoutDisplayPageObjectProviderLatestUrlTitle()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		BlogsEntry updatedBlogsEntry = _updateBlogsEntry(
			blogsEntry, RandomTestUtil.randomString());

		LayoutDisplayPageObjectProvider layoutDisplayPageObjectProvider =
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				TestPropsValues.getGroupId(), updatedBlogsEntry.getUrlTitle());

		Assert.assertEquals(
			updatedBlogsEntry,
			layoutDisplayPageObjectProvider.getDisplayObject());
	}

	@Test
	public void testGetLayoutDisplayPageObjectProviderPreviousUrlTitle()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		String previousUrlTitle = blogsEntry.getUrlTitle();

		BlogsEntry updatedBlogsEntry = _updateBlogsEntry(
			blogsEntry, RandomTestUtil.randomString());

		Assert.assertNotEquals(
			previousUrlTitle, updatedBlogsEntry.getUrlTitle());

		LayoutDisplayPageObjectProvider layoutDisplayPageObjectProvider =
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				TestPropsValues.getGroupId(), previousUrlTitle);

		Assert.assertEquals(
			updatedBlogsEntry,
			layoutDisplayPageObjectProvider.getDisplayObject());
	}

	@Test
	public void testGetLayoutDisplayPageObjectProviderSingleUrlTitle()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		LayoutDisplayPageObjectProvider layoutDisplayPageObjectProvider =
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				TestPropsValues.getGroupId(), blogsEntry.getUrlTitle());

		Assert.assertEquals(
			blogsEntry, layoutDisplayPageObjectProvider.getDisplayObject());
	}

	private BlogsEntry _addBlogsEntry() throws Exception {
		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	private BlogsEntry _updateBlogsEntry(BlogsEntry blogsEntry, String urlTitle)
		throws Exception {

		return _blogsEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), blogsEntry.getEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			urlTitle, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), false, false, null, null,
			null, null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject(filter = "component.name=*.BlogsLayoutDisplayPageProvider")
	private LayoutDisplayPageProvider _layoutDisplayPageProvider;

}