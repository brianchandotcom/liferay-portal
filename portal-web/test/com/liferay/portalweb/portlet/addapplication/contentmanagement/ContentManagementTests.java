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

package com.liferay.portalweb.portlet.addapplication.contentmanagement;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchassetpublisher.SearchAssetPublisherTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchbreadcrumb.SearchBreadcrumbTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchcategoriesnavigation.SearchCategoriesNavigationTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchdocumentlibrary.SearchDocumentLibraryTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchdocumentlibrarydisplay.SearchDocumentLibraryDisplayTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchimagegallery.SearchImageGalleryTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchnavigation.SearchNavigationTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchnestedportlets.SearchNestedPortletsTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchpollsdisplay.SearchPollsDisplayTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchrecentdownloads.SearchRecentDownloadsTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchsitemap.SearchSiteMapTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchtagcloud.SearchTagCloudTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchtagsnavigation.SearchTagsNavigationTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchwebcontentdisplay.SearchWebContentDisplayTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchwebcontentlist.SearchWebContentListTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchwebcontentsearch.SearchWebContentSearchTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.searchxslcontent.SearchXSLContentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ContentManagementTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(SearchAssetPublisherTests.suite());
		testSuite.addTest(SearchBreadcrumbTests.suite());
		testSuite.addTest(SearchCategoriesNavigationTests.suite());
		testSuite.addTest(SearchDocumentLibraryTests.suite());
		testSuite.addTest(SearchDocumentLibraryDisplayTests.suite());
		testSuite.addTest(SearchImageGalleryTests.suite());
		testSuite.addTest(SearchNavigationTests.suite());
		testSuite.addTest(SearchNestedPortletsTests.suite());
		testSuite.addTest(SearchPollsDisplayTests.suite());
		testSuite.addTest(SearchRecentDownloadsTests.suite());
		testSuite.addTest(SearchSiteMapTests.suite());
		testSuite.addTest(SearchTagCloudTests.suite());
		testSuite.addTest(SearchTagsNavigationTests.suite());
		testSuite.addTest(SearchWebContentDisplayTests.suite());
		testSuite.addTest(SearchWebContentListTests.suite());
		testSuite.addTest(SearchWebContentSearchTests.suite());
		testSuite.addTest(SearchXSLContentTests.suite());

		return testSuite;
	}

}