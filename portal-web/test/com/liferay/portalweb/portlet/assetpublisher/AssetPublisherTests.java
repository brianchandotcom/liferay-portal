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

package com.liferay.portalweb.portlet.assetpublisher;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.archivedsetup.ArchivedSetupTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.BlogsEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentrycomment.BlogsEntryCommentTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.BMBookmarkTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.DLDocumentTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocumentcomment.DLDocumentCommentTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.IGImageTests;
import com.liferay.portalweb.portlet.assetpublisher.lar.LARTests;
import com.liferay.portalweb.portlet.assetpublisher.mbthreadmessage.MBThreadMessageTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.PortletTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.WCWebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontentcomment.WCWebContentCommentTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.WikiPageTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipagecomment.WikiPageCommentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetPublisherTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ArchivedSetupTests.suite());
		testSuite.addTest(BlogsEntryTests.suite());
		testSuite.addTest(BlogsEntryCommentTests.suite());
		testSuite.addTest(BMBookmarkTests.suite());
		testSuite.addTest(DLDocumentTests.suite());
		testSuite.addTest(DLDocumentCommentTests.suite());
		testSuite.addTest(IGImageTests.suite());
		testSuite.addTest(LARTests.suite());
		testSuite.addTest(MBThreadMessageTests.suite());
		testSuite.addTest(PortletTests.suite());
		testSuite.addTest(WCWebContentTests.suite());
		testSuite.addTest(WCWebContentCommentTests.suite());
		testSuite.addTest(WikiPageTests.suite());
		testSuite.addTest(WikiPageCommentTests.suite());

		return testSuite;
	}

}