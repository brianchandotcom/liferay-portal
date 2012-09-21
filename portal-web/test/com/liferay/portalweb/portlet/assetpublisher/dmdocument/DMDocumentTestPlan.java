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

package com.liferay.portalweb.portlet.assetpublisher.dmdocument;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.addnewdmfolderdocumentapactions.AddNewDMFolderDocumentAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.deletedmfolderdocumentap.DeleteDMFolderDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.ratedmfolderdocumentap.RateDMFolderDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.selectexistingdmfolderdocumentapactions.SelectExistingDMFolderDocumentAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.viewconfigureportletabstractsdmdocumentap.ViewConfigurePortletAbstractsDMDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.viewconfigureportletavailabledmdocumentap.ViewConfigurePortletAvailableDMDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.viewconfigureportletcurrentdmdocumentap.ViewConfigurePortletCurrentDMDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.viewconfigureportletfullcontentdmdocumentap.ViewConfigurePortletFullContentDMDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.viewconfigureportlettabledmdocumentap.ViewConfigurePortletTableDMDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dmdocument.viewconfigureportlettitlelistdmdocumentap.ViewConfigurePortletTitleListDMDocumentAPTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMDocumentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddNewDMFolderDocumentAPActionsTests.suite());
		testSuite.addTest(DeleteDMFolderDocumentAPTests.suite());
		testSuite.addTest(RateDMFolderDocumentAPTests.suite());
		testSuite.addTest(SelectExistingDMFolderDocumentAPActionsTests.suite());
		testSuite.addTest(
			ViewConfigurePortletAbstractsDMDocumentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletAvailableDMDocumentAPTests.suite());
		testSuite.addTest(ViewConfigurePortletCurrentDMDocumentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletFullContentDMDocumentAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTableDMDocumentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletTitleListDMDocumentAPTests.suite());

		return testSuite;
	}

}