/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.assetpublisher.archivedsetup.ArchivedSetupTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.BlogsEntryTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.blogsentrycomment.BlogsEntryCommentTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.BMBookmarkTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.DMDocumentTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.dmdocumentcomment.DMDocumentCommentTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.dmimage.DMImageTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.lar.LARTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.mbthread.MBThreadTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.portlet.PortletTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.WCWebContentTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontentcomment.WCWebContentCommentTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.WikiPageTestPlan;
import com.liferay.portalweb.portlet.assetpublisher.wikipagecomment.WikiPageCommentTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetPublisherTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ArchivedSetupTestPlan.suite());
		testSuite.addTest(BlogsEntryTestPlan.suite());
		testSuite.addTest(BlogsEntryCommentTestPlan.suite());
		testSuite.addTest(BMBookmarkTestPlan.suite());
		testSuite.addTest(DMDocumentTestPlan.suite());
		testSuite.addTest(DMDocumentCommentTestPlan.suite());
		testSuite.addTest(DMImageTestPlan.suite());
		testSuite.addTest(LARTestPlan.suite());
		testSuite.addTest(MBThreadTestPlan.suite());
		testSuite.addTest(PortletTestPlan.suite());
		testSuite.addTest(WCWebContentTestPlan.suite());
		testSuite.addTest(WCWebContentCommentTestPlan.suite());
		testSuite.addTest(WikiPageTestPlan.suite());
		testSuite.addTest(WikiPageCommentTestPlan.suite());

		return testSuite;
	}

}