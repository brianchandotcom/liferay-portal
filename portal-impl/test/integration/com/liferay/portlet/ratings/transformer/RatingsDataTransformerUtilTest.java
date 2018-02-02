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
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.ratings.kernel.transformer.RatingsDataTransformerUtil;

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
			new LiferayIntegrationTestRule(),
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
	public void testTransformCompanyRatingsData() throws Exception {
		_atomicState.reset();

		PortletPreferences oldPortletPreferences = new PortletPreferencesImpl();

		oldPortletPreferences.setValue(
			"com.liferay.blogs.model.BlogsEntry_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.bookmarks.model.BookmarksEntry_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.document.library.kernel.model.DLFileEntry_RatingsType",
			"like");
		oldPortletPreferences.setValue(
			"com.liferay.journal.model.JournalArticle_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.knowledge.base.model.KBArticle_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.message.boards.model.MBDiscussion_RatingsType",
			"like");
		oldPortletPreferences.setValue(
			"com.liferay.message.boards.model.MBMessage_RatingsType", "like");
		oldPortletPreferences.setValue(
			"com.liferay.wiki.model.WikiPage_RatingsType", "like");

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.setProperty(
			"com.liferay.blogs.model.BlogsEntry_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.bookmarks.model.BookmarksEntry_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.document.library.kernel.model.DLFileEntry_RatingsType",
			"stars");
		unicodeProperties.setProperty(
			"com.liferay.journal.model.JournalArticle_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.knowledge.base.model.KBArticle_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.message.boards.model.MBDiscussion_RatingsType",
			"stars");
		unicodeProperties.setProperty(
			"com.liferay.message.boards.model.MBMessage_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.wiki.model.WikiPage_RatingsType", "stars");

		RatingsDataTransformerUtil.transformCompanyRatingsData(
			1, oldPortletPreferences, unicodeProperties);

		Assert.assertTrue(_atomicState.isSet());
	}

	@Test
	public void testTransformGroupRatingsData() throws Exception {
		_atomicState.reset();

		UnicodeProperties oldUnicodeProperties = new UnicodeProperties();

		oldUnicodeProperties.setProperty(
			"com.liferay.blogs.model.BlogsEntry_RatingsType", "like");
		oldUnicodeProperties.setProperty(
			"com.liferay.bookmarks.model.BookmarksEntry_RatingsType", "like");
		oldUnicodeProperties.setProperty(
			"com.liferay.document.library.kernel.model.DLFileEntry_RatingsType",
			"like");
		oldUnicodeProperties.setProperty(
			"com.liferay.journal.model.JournalArticle_RatingsType", "like");
		oldUnicodeProperties.setProperty(
			"com.liferay.knowledge.base.model.KBArticle_RatingsType", "like");
		oldUnicodeProperties.setProperty(
			"com.liferay.message.boards.model.MBDiscussion_RatingsType",
			"like");
		oldUnicodeProperties.setProperty(
			"com.liferay.message.boards.model.MBMessage_RatingsType", "like");
		oldUnicodeProperties.setProperty(
			"com.liferay.wiki.model.WikiPage_RatingsType", "like");

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.setProperty(
			"com.liferay.blogs.model.BlogsEntry_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.bookmarks.model.BookmarksEntry_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.document.library.kernel.model.DLFileEntry_RatingsType",
			"stars");
		unicodeProperties.setProperty(
			"com.liferay.journal.model.JournalArticle_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.knowledge.base.model.KBArticle_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.message.boards.model.MBDiscussion_RatingsType",
			"stars");
		unicodeProperties.setProperty(
			"com.liferay.message.boards.model.MBMessage_RatingsType", "stars");
		unicodeProperties.setProperty(
			"com.liferay.wiki.model.WikiPage_RatingsType", "stars");

		RatingsDataTransformerUtil.transformGroupRatingsData(
			1, oldUnicodeProperties, unicodeProperties);

		Assert.assertTrue(_atomicState.isSet());
	}

	private static AtomicState _atomicState;

}