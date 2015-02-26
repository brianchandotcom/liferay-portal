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

package com.liferay.portlet.ratings.transformer;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicStateUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class RatingsDataTransformerUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.ratingsdatatransformerutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicStateUtil = new AtomicStateUtil();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicStateUtil.close();
	}

	@Test
	public void testTransformCompanyRatingsData() {
		_atomicStateUtil.resetState();

		try {
			PortletPreferencesImpl ppi = new PortletPreferencesImpl();
			UnicodeProperties uprops = new UnicodeProperties();

			ppi.setValue(
				"com.liferay.portlet.messageboards.model.MBDiscussion" +
				"_RatingsType", "like");
			ppi.setValue(
				"com.liferay.portlet.journal.model.JournalArticle_RatingsType",
				"like");
			ppi.setValue(
				"com.liferay.portlet.messageboards.model.MBMessage_RatingsType",
				"like");
			ppi.setValue(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry" +
				"_RatingsType", "like");
			ppi.setValue(
				"com.liferay.bookmarks.model.BookmarksEntry_RatingsType",
				"like");
			ppi.setValue("com.liferay.wiki.model.WikiPage_RatingsType", "like");
			ppi.setValue(
				"com.liferay.portlet.blogs.model.BlogsEntry_RatingsType",
				"like");

			uprops.setProperty(
				"com.liferay.portlet.blogs.model.BlogsEntry_RatingsType",
				"stars");
			uprops.setProperty(
				"com.liferay.portlet.messageboards.model.MBDiscussion" +
				"_RatingsType", "stars");
			uprops.setProperty(
				"com.liferay.portlet.journal.model.JournalArticle_RatingsType",
				"stars");
			uprops.setProperty(
				"com.liferay.portlet.messageboards.model.MBMessage_RatingsType",
				"stars");
			uprops.setProperty(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry" +
				"_RatingsType", "stars");
			uprops.setProperty(
				"com.liferay.bookmarks.model.BookmarksEntry_RatingsType",
				"stars");
			uprops.setProperty(
				"com.liferay.wiki.model.WikiPage_RatingsType", "stars");

			RatingsDataTransformerUtil.transformCompanyRatingsData(
				1, ppi, uprops);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	@Test
	public void testTransformGroupRatingsData() {
		_atomicStateUtil.resetState();

		try {
			UnicodeProperties ppi = new UnicodeProperties();
			UnicodeProperties uprops = new UnicodeProperties();

			ppi.setProperty(
				"com.liferay.portlet.messageboards.model.MBDiscussion" +
				"_RatingsType", "like");
			ppi.setProperty(
				"com.liferay.portlet.journal.model.JournalArticle_RatingsType",
				"like");
			ppi.setProperty(
				"com.liferay.portlet.messageboards.model.MBMessage_RatingsType",
				"like");
			ppi.setProperty(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry" +
				"_RatingsType", "like");
			ppi.setProperty(
				"com.liferay.bookmarks.model.BookmarksEntry_RatingsType",
				"like");
			ppi.setProperty(
				"com.liferay.wiki.model.WikiPage_RatingsType", "like");
			ppi.setProperty(
				"com.liferay.portlet.blogs.model.BlogsEntry_RatingsType",
				"like");

			uprops.setProperty(
				"com.liferay.portlet.blogs.model.BlogsEntry_RatingsType",
				"stars");
			uprops.setProperty(
				"com.liferay.portlet.messageboards.model.MBDiscussion" +
				"_RatingsType", "stars");
			uprops.setProperty(
				"com.liferay.portlet.journal.model.JournalArticle_RatingsType",
				"stars");
			uprops.setProperty(
				"com.liferay.portlet.messageboards.model.MBMessage_RatingsType",
				"stars");
			uprops.setProperty(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry" +
				"_RatingsType", "stars");
			uprops.setProperty(
				"com.liferay.bookmarks.model.BookmarksEntry_RatingsType",
				"stars");
			uprops.setProperty(
				"com.liferay.wiki.model.WikiPage_RatingsType", "stars");

			RatingsDataTransformerUtil.transformGroupRatingsData(
				1, ppi, uprops);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicStateUtil.isStateSet());
	}

	private static AtomicStateUtil _atomicStateUtil;

}