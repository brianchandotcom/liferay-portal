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

package com.liferay.portalweb.portlet.assetpublisher.igimage;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.addnewigfolderimageapactions.AddNewIGFolderImageAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.deleteigfolderimageap.DeleteIGFolderImageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.selectexistingigfolderimageapactions.SelectExistingIGFolderImageAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportletabstractsigimageap.ViewConfigurePortletAbstractsIGImageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportletavailableigimageap.ViewConfigurePortletAvailableIGImageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportletcurrentigimageap.ViewConfigurePortletCurrentIGImageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportletfullcontentigimageap.ViewConfigurePortletFullContentIGImageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportlettableigimageap.ViewConfigurePortletTableIGImageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportlettitlelistigimageap.ViewConfigurePortletTitleListIGImageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.igimage.viewigfolderimageviewcountap.ViewIGFolderImageViewCountAPTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class IGImageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddNewIGFolderImageAPActionsTests.suite());
		testSuite.addTest(DeleteIGFolderImageAPTests.suite());
		testSuite.addTest(SelectExistingIGFolderImageAPActionsTests.suite());
		testSuite.addTest(ViewConfigurePortletAbstractsIGImageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletAvailableIGImageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletCurrentIGImageAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletFullContentIGImageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTableIGImageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTitleListIGImageAPTests.suite());
		testSuite.addTest(ViewIGFolderImageViewCountAPTests.suite());

		return testSuite;
	}

}