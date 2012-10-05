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

package com.liferay.portalweb.asset.documentsandmedia.dmimage;

import com.liferay.portalweb.asset.documentsandmedia.dmimage.addnewdmfolderimageapactions.AddNewDMFolderImageAPActionsTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.deletedmfolderimageap.DeleteDMFolderImageAPTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.selectexistingdmfolderimageapactions.SelectExistingDMFolderImageAPActionsTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.viewconfigureportletabstractsdmimageap.ViewConfigurePortletAbstractsDMImageAPTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.viewconfigureportletavailabledmimageap.ViewConfigurePortletAvailableDMImageAPTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.viewconfigureportletcurrentdmimageap.ViewConfigurePortletCurrentDMImageAPTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.viewconfigureportletfullcontentdmimageap.ViewConfigurePortletFullContentDMImageAPTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.viewconfigureportlettabledmimageap.ViewConfigurePortletTableDMImageAPTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.viewconfigureportlettitlelistdmimageap.ViewConfigurePortletTitleListDMImageAPTests;
import com.liferay.portalweb.asset.documentsandmedia.dmimage.viewdmfolderimageviewcountap.ViewDMFolderImageViewCountAPTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMImageTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddNewDMFolderImageAPActionsTests.suite());
		testSuite.addTest(DeleteDMFolderImageAPTests.suite());
		testSuite.addTest(SelectExistingDMFolderImageAPActionsTests.suite());
		testSuite.addTest(ViewConfigurePortletAbstractsDMImageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletAvailableDMImageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletCurrentDMImageAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletFullContentDMImageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTableDMImageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTitleListDMImageAPTests.suite());
		testSuite.addTest(ViewDMFolderImageViewCountAPTests.suite());

		return testSuite;
	}

}