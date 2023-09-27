/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.blogs.internal.item.type;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
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

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		Assert.assertTrue(
			infoItemIdentifier instanceof ClassPKInfoItemIdentifier);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Assert.assertEquals(0L, classPKInfoItemIdentifier.getClassPK());
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
			).toString(),
			blogsEntryContentDashboardItemSubtype.toJSONString(LocaleUtil.US));
	}

}