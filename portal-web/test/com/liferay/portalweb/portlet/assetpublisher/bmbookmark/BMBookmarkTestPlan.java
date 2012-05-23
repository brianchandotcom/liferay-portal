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

package com.liferay.portalweb.portlet.assetpublisher.bmbookmark;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.addnewbmfolderbookmarkapactions.AddNewBMFolderBookmarkAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.deletebmfolderbookmarkap.DeleteBMFolderBookmarkAPTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.selectexistingbmfolderbookmarkapactions.SelectExistingBMFolderBookmarkAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.viewportletabstractsbmfolderbookmarkap.ViewPortletAbstractsBMFolderBookmarkAPTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.viewportletavailablebookmarksentryap.ViewPortletAvailableBookmarksEntryAPTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.viewportletcurrentbookmarksentryap.ViewPortletCurrentBookmarksEntryAPTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.viewportletfullcontentbmfolderbookmarkap.ViewPortletFullContentBMFolderBookmarkAPTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.viewportlettablebmfolderbookmarkap.ViewPortletTableBMFolderBookmarkAPTests;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.viewportlettitlelistbmfolderbookmarkap.ViewPortletTitleListBMFolderBookmarkAPTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BMBookmarkTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddNewBMFolderBookmarkAPActionsTests.suite());
		testSuite.addTest(DeleteBMFolderBookmarkAPTests.suite());
		testSuite.addTest(SelectExistingBMFolderBookmarkAPActionsTests.suite());
		testSuite.addTest(ViewPortletAbstractsBMFolderBookmarkAPTests.suite());
		testSuite.addTest(ViewPortletAvailableBookmarksEntryAPTests.suite());
		testSuite.addTest(ViewPortletCurrentBookmarksEntryAPTests.suite());
		testSuite.addTest(
			ViewPortletFullContentBMFolderBookmarkAPTests.suite());
		testSuite.addTest(ViewPortletTableBMFolderBookmarkAPTests.suite());
		testSuite.addTest(ViewPortletTitleListBMFolderBookmarkAPTests.suite());

		return testSuite;
	}

}