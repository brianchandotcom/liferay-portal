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

package com.liferay.portalweb.portlet.assetpublisher.dldocument;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.addnewdlfolderdocumentapactions.AddNewDLFolderDocumentAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.deletedlfolderdocumentap.DeleteDLFolderDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.ratedlfolderdocumentap.RateDLFolderDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.selectexistingdlfolderdocumentapactions.SelectExistingDLFolderDocumentAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewconfigureportletabstractsdldocumentap.ViewConfigurePortletAbstractsDLDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewconfigureportletavailabledldocumentap.ViewConfigurePortletAvailableDLDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewconfigureportletcurrentdldocumentap.ViewConfigurePortletCurrentDLDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewconfigureportletfullcontentdldocumentap.ViewConfigurePortletFullContentDLDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewconfigureportlettabledldocumentap.ViewConfigurePortletTableDLDocumentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewconfigureportlettitlelistdldocumentap.ViewConfigurePortletTitleListDLDocumentAPTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DLDocumentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddNewDLFolderDocumentAPActionsTests.suite());
		testSuite.addTest(DeleteDLFolderDocumentAPTests.suite());
		testSuite.addTest(RateDLFolderDocumentAPTests.suite());
		testSuite.addTest(SelectExistingDLFolderDocumentAPActionsTests.suite());
		testSuite.addTest(
			ViewConfigurePortletAbstractsDLDocumentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletAvailableDLDocumentAPTests.suite());
		testSuite.addTest(ViewConfigurePortletCurrentDLDocumentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletFullContentDLDocumentAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTableDLDocumentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletTitleListDLDocumentAPTests.suite());

		return testSuite;
	}

}