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

package com.liferay.blogs.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.search.test.util.BaseSearchRequestBuilderHighlightTestCase;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class BlogsEntrySearchRequestBuilderHighlightTest
	extends BaseSearchRequestBuilderHighlightTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_blogsEntryFixture = new BlogsEntryFixture(
			BlogsEntryLocalServiceUtil.getService(), group);
	}

	@Override
	protected void addAssets(String... keywords) throws Exception {
		for (String keyword : keywords) {
			_blogsEntryFixture.createBlogsEntry(keyword, keyword);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BlogsEntry.class;
	}

	protected String[] getFieldNames() {
		return new String[] {"content_en_US", "title_en_US"};
	}

	private BlogsEntryFixture _blogsEntryFixture;

}