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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.security.permission.ResourceActionsImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class BlogsEntryContentDashboardItemSubtypeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		ResourceActionsUtil resourceActionsUtil = new ResourceActionsUtil();

		resourceActionsUtil.setResourceActions(new ResourceActionsImpl());
	}

	@Test
	public void testCreation() {
		BlogsEntryContentDashboardItemSubtype
			blogsEntryContentDashboardItemSubtype =
				new BlogsEntryContentDashboardItemSubtype();

		Assert.assertEquals(
			"model.resource." + BlogsEntry.class.getName(),
			blogsEntryContentDashboardItemSubtype.getFullLabel(LocaleUtil.US));
		Assert.assertEquals(
			"model.resource." + BlogsEntry.class.getName(),
			blogsEntryContentDashboardItemSubtype.getLabel(LocaleUtil.US));

		InfoItemReference infoItemReference =
			blogsEntryContentDashboardItemSubtype.getInfoItemReference();

		Assert.assertEquals(
			BlogsEntry.class.getName(), infoItemReference.getClassName());
		Assert.assertEquals(0L, infoItemReference.getClassPK());
	}

	@Test
	public void testEquals() {
		BlogsEntryContentDashboardItemSubtype
			blogsEntryContentDashboardItemSubtype1 =
				new BlogsEntryContentDashboardItemSubtype();

		Assert.assertTrue(
			blogsEntryContentDashboardItemSubtype1.equals(
				new BlogsEntryContentDashboardItemSubtype()));
	}

	@Test
	public void testToJSONString() {
		BlogsEntryContentDashboardItemSubtype
			blogsEntryContentDashboardItemSubtype =
				new BlogsEntryContentDashboardItemSubtype();

		Assert.assertEquals(
			JSONUtil.put(
				"entryClassName", BlogsEntry.class.getName()
			).put(
				"title",
				blogsEntryContentDashboardItemSubtype.getFullLabel(
					LocaleUtil.US)
			).toJSONString(),
			blogsEntryContentDashboardItemSubtype.toJSONString(LocaleUtil.US));
	}

}