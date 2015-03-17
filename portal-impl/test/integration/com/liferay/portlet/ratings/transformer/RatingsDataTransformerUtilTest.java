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
import com.liferay.portal.util.test.AtomicState;
import com.liferay.portlet.PortletPreferencesImpl;

import javax.portlet.PortletPreferences;

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
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testTransformCompanyRatingsData() {
		_atomicState.reset();

		try {
			PortletPreferences portletPreferences =
				new PortletPreferencesImpl();

			UnicodeProperties unicodeProperties = new UnicodeProperties();

			portletPreferences.setValue(
				"com.liferay.portlet.messageboards.model.MBDiscussion" +
				"_RatingsType", "like");
			portletPreferences.setValue(
				"com.liferay.portlet.journal.model.JournalArticle_RatingsType",
				"like");
			portletPreferences.setValue(
				"com.liferay.portlet.messageboards.model.MBMessage_RatingsType",
				"like");
			portletPreferences.setValue(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry" +
				"_RatingsType", "like");
			portletPreferences.setValue(
				"com.liferay.bookmarks.model.BookmarksEntry_RatingsType",
				"like");
			portletPreferences.setValue(
				"com.liferay.wiki.model.WikiPage_RatingsType", "like");
			portletPreferences.setValue(
				"com.liferay.portlet.blogs.model.BlogsEntry_RatingsType",
				"like");

			unicodeProperties.setProperty(
				"com.liferay.portlet.blogs.model.BlogsEntry_RatingsType",
				"stars");
			unicodeProperties.setProperty(
				"com.liferay.portlet.messageboards.model.MBDiscussion" +
				"_RatingsType", "stars");
			unicodeProperties.setProperty(
				"com.liferay.portlet.journal.model.JournalArticle_RatingsType",
				"stars");
			unicodeProperties.setProperty(
				"com.liferay.portlet.messageboards.model.MBMessage_RatingsType",
				"stars");
			unicodeProperties.setProperty(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry" +
				"_RatingsType", "stars");
			unicodeProperties.setProperty(
				"com.liferay.bookmarks.model.BookmarksEntry_RatingsType",
				"stars");
			unicodeProperties.setProperty(
				"com.liferay.wiki.model.WikiPage_RatingsType", "stars");

			RatingsDataTransformerUtil.transformCompanyRatingsData(
				1, portletPreferences, unicodeProperties);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testTransformGroupRatingsData() {
		_atomicState.reset();

		try {
			UnicodeProperties unicodePreferences = new UnicodeProperties();
			UnicodeProperties unicodeProperties = new UnicodeProperties();

			unicodePreferences.setProperty(
				"com.liferay.portlet.messageboards.model.MBDiscussion" +
				"_RatingsType", "like");
			unicodePreferences.setProperty(
				"com.liferay.portlet.journal.model.JournalArticle_RatingsType",
				"like");
			unicodePreferences.setProperty(
				"com.liferay.portlet.messageboards.model.MBMessage_RatingsType",
				"like");
			unicodePreferences.setProperty(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry" +
				"_RatingsType", "like");
			unicodePreferences.setProperty(
				"com.liferay.bookmarks.model.BookmarksEntry_RatingsType",
				"like");
			unicodePreferences.setProperty(
				"com.liferay.wiki.model.WikiPage_RatingsType", "like");
			unicodePreferences.setProperty(
				"com.liferay.portlet.blogs.model.BlogsEntry_RatingsType",
				"like");

			unicodeProperties.setProperty(
				"com.liferay.portlet.blogs.model.BlogsEntry_RatingsType",
				"stars");
			unicodeProperties.setProperty(
				"com.liferay.portlet.messageboards.model.MBDiscussion" +
				"_RatingsType", "stars");
			unicodeProperties.setProperty(
				"com.liferay.portlet.journal.model.JournalArticle_RatingsType",
				"stars");
			unicodeProperties.setProperty(
				"com.liferay.portlet.messageboards.model.MBMessage_RatingsType",
				"stars");
			unicodeProperties.setProperty(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry" +
				"_RatingsType", "stars");
			unicodeProperties.setProperty(
				"com.liferay.bookmarks.model.BookmarksEntry_RatingsType",
				"stars");
			unicodeProperties.setProperty(
				"com.liferay.wiki.model.WikiPage_RatingsType", "stars");

			RatingsDataTransformerUtil.transformGroupRatingsData(
				1, unicodePreferences, unicodeProperties);
		}
		catch (Exception e) {
			Assert.fail();
		}

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}