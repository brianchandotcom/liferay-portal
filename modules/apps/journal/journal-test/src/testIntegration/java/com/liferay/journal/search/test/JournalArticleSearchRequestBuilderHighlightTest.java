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

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.test.util.BaseSearchRequestBuilderHighlightTestCase;

import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class JournalArticleSearchRequestBuilderHighlightTest
	extends BaseSearchRequestBuilderHighlightTestCase {

	@Override
	protected void addAssets(String... keywords) throws Exception {
		for (String keyword : keywords) {
			JournalTestUtil.addArticle(
				group.getGroupId(), 0,
				PortalUtil.getClassNameId(JournalArticle.class),
				HashMapBuilder.put(
					LocaleUtil.US, keyword
				).build(),
				HashMapBuilder.put(
					LocaleUtil.US, keyword
				).build(),
				HashMapBuilder.put(
					LocaleUtil.US, keyword
				).build(),
				LocaleUtil.getSiteDefault(), false, true, serviceContext);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalArticle.class;
	}

	protected String[] getFieldNames() {
		return new String[] {
			"content_en_US", "description_en_US", "title_en_US"
		};
	}

}