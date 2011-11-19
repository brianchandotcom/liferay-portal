/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal;

import com.liferay.portalweb.portal.login.LoginTests;
import com.liferay.portalweb.portlet.activities.ActivitiesTests;
import com.liferay.portalweb.portlet.amazonrankings.AmazonRankingsTests;
import com.liferay.portalweb.portlet.announcements.AnnouncementsTests;
import com.liferay.portalweb.portlet.assetpublisher.AssetPublisherTests;
import com.liferay.portalweb.portlet.blogs.BlogsTests;
import com.liferay.portalweb.portlet.blogsaggregator.BlogsAggregatorTests;
import com.liferay.portalweb.portlet.bookmarks.BookmarksTests;
import com.liferay.portalweb.portlet.breadcrumb.BreadcrumbTests;
import com.liferay.portalweb.portlet.calendar.CalendarTests;
import com.liferay.portalweb.portlet.currencyconverter.CurrencyConverterTests;
import com.liferay.portalweb.portlet.dictionary.DictionaryTests;
import com.liferay.portalweb.portlet.directory.DirectoryTests;
import com.liferay.portalweb.portlet.documentlibrary.DocumentLibraryTests;
import com.liferay.portalweb.portlet.documentlibrarydisplay.DocumentLibraryDisplayTests;
import com.liferay.portalweb.portlet.hellovelocity.HelloVelocityTests;
import com.liferay.portalweb.portlet.helloworld.HelloWorldTests;
import com.liferay.portalweb.portlet.iframe.IFrameTests;
import com.liferay.portalweb.portlet.imagegallery.ImageGalleryTests;
import com.liferay.portalweb.portlet.invitation.InvitationTests;
import com.liferay.portalweb.portlet.language.LanguageTests;
import com.liferay.portalweb.portlet.loancalculator.LoanCalculatorTests;
import com.liferay.portalweb.portlet.managepages.ManagePagesTests;
import com.liferay.portalweb.portlet.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portlet.myaccount.MyAccountTests;
import com.liferay.portalweb.portlet.navigation.NavigationTests;
import com.liferay.portalweb.portlet.nestedportlets.NestedPortletsTests;
import com.liferay.portalweb.portlet.networkutilities.NetworkUtilitiesTests;
import com.liferay.portalweb.portlet.pagecomments.PageCommentsTests;
import com.liferay.portalweb.portlet.pageratings.PageRatingsTests;
import com.liferay.portalweb.portlet.passwordgenerator.PasswordGeneratorTests;
import com.liferay.portalweb.portlet.pollsdisplay.PollsDisplayTests;
import com.liferay.portalweb.portlet.quicknote.QuickNoteTests;
import com.liferay.portalweb.portlet.recentbloggers.RecentBloggersTests;
import com.liferay.portalweb.portlet.recentdocuments.RecentDocumentsTests;
import com.liferay.portalweb.portlet.rss.RSSTests;
import com.liferay.portalweb.portlet.search.SearchTests;
import com.liferay.portalweb.portlet.shopping.ShoppingTests;
import com.liferay.portalweb.portlet.sitemap.SiteMapTests;
import com.liferay.portalweb.portlet.softwarecatalog.SoftwareCatalogTests;
import com.liferay.portalweb.portlet.translator.TranslatorTests;
import com.liferay.portalweb.portlet.unitconverter.UnitConverterTests;
import com.liferay.portalweb.portlet.webcontentdisplay.WebContentDisplayTests;
import com.liferay.portalweb.portlet.webcontentlist.WebContentListTests;
import com.liferay.portalweb.portlet.webcontentsearch.WebContentSearchTests;
import com.liferay.portalweb.portlet.webproxy.WebProxyTests;
import com.liferay.portalweb.portlet.wiki.WikiTests;
import com.liferay.portalweb.portlet.wikidisplay.WikiDisplayTests;
import com.liferay.portalweb.portlet.words.WordsTests;
import com.liferay.portalweb.portlet.xslcontent.XSLContentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalWebTestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(ActivitiesTests.suite());
		testSuite.addTest(AmazonRankingsTests.suite());
		testSuite.addTest(AnnouncementsTests.suite());
		testSuite.addTest(AssetPublisherTests.suite());
		testSuite.addTest(BlogsTests.suite());
		testSuite.addTest(BlogsAggregatorTests.suite());
		testSuite.addTest(BookmarksTests.suite());
		testSuite.addTest(BreadcrumbTests.suite());
		testSuite.addTest(CalendarTests.suite());
		testSuite.addTest(CurrencyConverterTests.suite());
		testSuite.addTest(DictionaryTests.suite());
		testSuite.addTest(DirectoryTests.suite());
		testSuite.addTest(DocumentLibraryTests.suite());
		testSuite.addTest(DocumentLibraryDisplayTests.suite());
		testSuite.addTest(HelloVelocityTests.suite());
		testSuite.addTest(HelloWorldTests.suite());
		testSuite.addTest(IFrameTests.suite());
		testSuite.addTest(ImageGalleryTests.suite());
		testSuite.addTest(InvitationTests.suite());
		testSuite.addTest(LanguageTests.suite());
		testSuite.addTest(LoanCalculatorTests.suite());
		testSuite.addTest(ManagePagesTests.suite());
		testSuite.addTest(MessageBoardsTests.suite());
		testSuite.addTest(MyAccountTests.suite());
		testSuite.addTest(NavigationTests.suite());
		testSuite.addTest(NestedPortletsTests.suite());
		testSuite.addTest(NetworkUtilitiesTests.suite());
		testSuite.addTest(PageCommentsTests.suite());
		testSuite.addTest(PageRatingsTests.suite());
		testSuite.addTest(PasswordGeneratorTests.suite());
		testSuite.addTest(PollsDisplayTests.suite());
		testSuite.addTest(QuickNoteTests.suite());
		testSuite.addTest(RecentBloggersTests.suite());
		testSuite.addTest(RecentDocumentsTests.suite());
		testSuite.addTest(RSSTests.suite());
		testSuite.addTest(SearchTests.suite());
		testSuite.addTest(ShoppingTests.suite());
		testSuite.addTest(SiteMapTests.suite());
		testSuite.addTest(SoftwareCatalogTests.suite());
		testSuite.addTest(TranslatorTests.suite());
		testSuite.addTest(UnitConverterTests.suite());
		testSuite.addTest(WebContentDisplayTests.suite());
		testSuite.addTest(WebContentListTests.suite());
		testSuite.addTest(WebContentSearchTests.suite());
		testSuite.addTest(WebProxyTests.suite());
		testSuite.addTest(WikiTests.suite());
		testSuite.addTest(WikiDisplayTests.suite());
		testSuite.addTest(WordsTests.suite());
		testSuite.addTest(XSLContentTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}