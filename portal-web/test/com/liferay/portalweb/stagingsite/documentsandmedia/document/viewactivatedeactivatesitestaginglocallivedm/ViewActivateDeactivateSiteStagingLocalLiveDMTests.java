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

package com.liferay.portalweb.stagingsite.documentsandmedia.document.viewactivatedeactivatesitestaginglocallivedm;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.adddmdocumentsitestaginglocallivedm.ActivateSiteStagingLocalLiveDMTest;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.adddmdocumentsitestaginglocallivedm.AddDMDocumentSiteStagingLocalLiveDMTest;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.adddmdocumentsitestaginglocallivedm.AddSitePublicPageDMTest;
import com.liferay.portalweb.stagingsite.documentsandmedia.document.adddmdocumentsitestaginglocallivedm.AddSitePublicPagePortletDMTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewActivateDeactivateSiteStagingLocalLiveDMTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddSitePublicPageDMTest.class);
		testSuite.addTestSuite(AddSitePublicPagePortletDMTest.class);
		testSuite.addTestSuite(ActivateSiteStagingLocalLiveDMTest.class);
		testSuite.addTestSuite(AddDMDocumentSiteStagingLocalLiveDMTest.class);
		testSuite.addTestSuite(DeactivateStagingTest.class);
		testSuite.addTestSuite(ActivateDeactivateSiteStagingLocalLiveDMTest.class);
		testSuite.addTestSuite(ViewActivateDeactivateSiteStagingLocalLiveDMTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}
}