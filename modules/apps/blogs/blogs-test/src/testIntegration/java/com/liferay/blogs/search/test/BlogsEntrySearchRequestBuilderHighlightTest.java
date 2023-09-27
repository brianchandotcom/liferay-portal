/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	protected void addModels(String... keywords) throws Exception {
		for (String keyword : keywords) {
			_blogsEntryFixture.addEntry(keyword, keyword);
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