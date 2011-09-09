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

package com.liferay.portalweb.stagingcommunity.documentlibrary.document;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.adddldocumentsitestaginglocallivedl.AddDLDocumentSiteStagingLocalLiveDLTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.adddldocumentsitestaginglocallivenodl.AddDLDocumentSiteStagingLocalLiveNoDLTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.deletedldocumentsitestaginglocallivedlactions.DeleteDLDocumentSiteStagingLocalLiveDLActionsTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.deletelivepagesitestaginglocallivedl.DeleteLivePageSiteStagingLocalLiveDLTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.deletesitestaginglocallivedldldocumentactions.DeleteSiteStagingLocalLiveDLDLDocumentActionsTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentdock.PublishToLiveNowDLDocumentDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentnodatadock.PublishToLiveNowDLDocumentNoDataDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentnodldock.PublishToLiveNowDLDocumentNoDLDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentnopagesdock.PublishToLiveNowDLDocumentNoPagesDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowpagedldock.PublishToLiveNowPageDLDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowpagedlnopagesdock.PublishToLiveNowPageDLNoPagesDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowportletdldock.PublishToLiveNowPortletDLDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.viewactivatedeactivatesitestaginglocallivedl.ViewActivateDeactivateSiteStagingLocalLiveDLTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDLDocumentSiteStagingLocalLiveDLTests.suite());
		testSuite.addTest(AddDLDocumentSiteStagingLocalLiveNoDLTests.suite());
		testSuite.addTest(
			DeleteDLDocumentSiteStagingLocalLiveDLActionsTests.suite());
		testSuite.addTest(DeleteLivePageSiteStagingLocalLiveDLTests.suite());
		testSuite.addTest(
			DeleteSiteStagingLocalLiveDLDLDocumentActionsTests.suite());
		testSuite.addTest(PublishToLiveNowDLDocumentDockTests.suite());
		testSuite.addTest(PublishToLiveNowDLDocumentNoDataDockTests.suite());
		testSuite.addTest(PublishToLiveNowDLDocumentNoDLDockTests.suite());
		testSuite.addTest(PublishToLiveNowDLDocumentNoPagesDockTests.suite());
		testSuite.addTest(PublishToLiveNowPageDLDockTests.suite());
		testSuite.addTest(PublishToLiveNowPageDLNoPagesDockTests.suite());
		testSuite.addTest(PublishToLiveNowPortletDLDockTests.suite());
		testSuite.addTest(
			ViewActivateDeactivateSiteStagingLocalLiveDLTests.suite());

		return testSuite;
	}

}