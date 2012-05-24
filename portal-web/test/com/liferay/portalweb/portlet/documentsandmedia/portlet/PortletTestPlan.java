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

package com.liferay.portalweb.portlet.documentsandmedia.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPortletDMTests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdmduplicate.AddPortletDMDuplicateTests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdmsite.AddPortletDMSiteTests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletdocumentsperpage5.ConfigureDMPortletDocumentsPerPage5Tests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletenablecommentratings.ConfigureDMPortletEnableCommentRatingsTests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletfoldersperpage5.ConfigureDMPortletFoldersPerPage5Tests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletselectfolder.ConfigureDMPortletSelectFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletselectsubfolder.ConfigureDMPortletSelectSubfolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletshowdocumentcolumns.ConfigureDMPortletShowDocumentColumnsTests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletshowfoldersearch.ConfigureDMPortletShowFolderSearchTests;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.removeportletdm.RemovePortletDMTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletDMTests.suite());

		testSuite.addTest(AddPortletDMDuplicateTests.suite());
		testSuite.addTest(AddPortletDMSiteTests.suite());
		testSuite.addTest(ConfigureDMPortletDocumentsPerPage5Tests.suite());
		testSuite.addTest(ConfigureDMPortletEnableCommentRatingsTests.suite());
		testSuite.addTest(ConfigureDMPortletFoldersPerPage5Tests.suite());
		testSuite.addTest(ConfigureDMPortletSelectFolderTests.suite());
		testSuite.addTest(ConfigureDMPortletSelectSubfolderTests.suite());
		testSuite.addTest(ConfigureDMPortletShowDocumentColumnsTests.suite());
		testSuite.addTest(ConfigureDMPortletShowFolderSearchTests.suite());
		testSuite.addTest(RemovePortletDMTests.suite());

		return testSuite;
	}

}