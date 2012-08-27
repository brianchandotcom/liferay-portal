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

package com.liferay.portalweb.stagingsite.documentsandmedia.document;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.adddmdocumentsitestaginglocallivedm.AddDMDocumentSiteStagingLocalLiveDMTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.adddmdocumentsitestaginglocallivenodm.AddDMDocumentSiteStagingLocalLiveNoDMTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.deletedmdocumentsitestaginglocallivedmaction.DeleteDMDocumentSiteStagingLocalLiveDMActionTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.deletelivepagesitestaginglocallivedm.DeleteLivePageSiteStagingLocalLiveDMTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.deletesitestaginglocallivedmdmdocumentaction.DeleteSiteStagingLocalLiveDMDMDocumentActionTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowdmdocumentdock.PublishToLiveNowDMDocumentDockTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowdmdocumentnodatadock.PublishToLiveNowDMDocumentNoDataDockTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowdmdocumentnodmdock.PublishToLiveNowDMDocumentNoDMDockTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowdmdocumentnopagesdock.PublishToLiveNowDMDocumentNoPagesDockTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowpagedmdock.PublishToLiveNowPageDMDockTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowpagedmnopagesdock.PublishToLiveNowPageDMNoPagesDockTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowportletdmdock.PublishToLiveNowPortletDMDockTests;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.viewactivatedeactivatesitestaginglocallivedm.ViewActivateDeactivateSiteStagingLocalLiveDMTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMDocumentSiteStagingLocalLiveDMTests.suite());
		testSuite.addTest(AddDMDocumentSiteStagingLocalLiveNoDMTests.suite());
		testSuite.addTest(
			DeleteDMDocumentSiteStagingLocalLiveDMActionTests.suite());
		testSuite.addTest(DeleteLivePageSiteStagingLocalLiveDMTests.suite());
		testSuite.addTest(
			DeleteSiteStagingLocalLiveDMDMDocumentActionTests.suite());
		testSuite.addTest(PublishToLiveNowDMDocumentDockTests.suite());
		testSuite.addTest(PublishToLiveNowDMDocumentNoDataDockTests.suite());
		testSuite.addTest(PublishToLiveNowDMDocumentNoDMDockTests.suite());
		testSuite.addTest(PublishToLiveNowDMDocumentNoPagesDockTests.suite());
		testSuite.addTest(PublishToLiveNowPageDMDockTests.suite());
		testSuite.addTest(PublishToLiveNowPageDMNoPagesDockTests.suite());
		testSuite.addTest(PublishToLiveNowPortletDMDockTests.suite());
		testSuite.addTest(
			ViewActivateDeactivateSiteStagingLocalLiveDMTests.suite());

		return testSuite;
	}

}