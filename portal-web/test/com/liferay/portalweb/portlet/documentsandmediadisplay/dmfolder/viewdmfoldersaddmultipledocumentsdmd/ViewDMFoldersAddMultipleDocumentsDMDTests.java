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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.viewdmfoldersaddmultipledocumentsdmd;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfolderdmd.AddDMFolder1DMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfolderdmd.AddDMFolder2DMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfolderdmd.AddDMFolder3DMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfolderdmd.TearDownDMDFolderTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfoldersdmd.ViewDMFoldersDMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.addportletdmd.AddPageDMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.addportletdmd.AddPortletDMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowactions.ConfigurePortletShowActionsTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowactions.ViewPortletShowActionsTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowfoldermenu.ConfigurePortletShowFolderMenuTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowfoldermenu.ViewPortletShowFolderMenuTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshownavigationlinks.ConfigurePortletShowNavigationLinksTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshownavigationlinks.ViewPortletShowNavigationLinksTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowsearch.ConfigurePortletShowSearchTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowsearch.ViewPortletShowSearchTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFoldersAddMultipleDocumentsDMDTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDMDTest.class);
		testSuite.addTestSuite(AddPortletDMDTest.class);
		testSuite.addTestSuite(ConfigurePortletShowFolderMenuTest.class);
		testSuite.addTestSuite(ConfigurePortletShowActionsTest.class);
		testSuite.addTestSuite(ConfigurePortletShowNavigationLinksTest.class);
		testSuite.addTestSuite(ConfigurePortletShowSearchTest.class);
		testSuite.addTestSuite(ViewPortletShowFolderMenuTest.class);
		testSuite.addTestSuite(ViewPortletShowActionsTest.class);
		testSuite.addTestSuite(ViewPortletShowNavigationLinksTest.class);
		testSuite.addTestSuite(ViewPortletShowSearchTest.class);
		testSuite.addTestSuite(AddDMFolder1DMDTest.class);
		testSuite.addTestSuite(AddDMFolder2DMDTest.class);
		testSuite.addTestSuite(AddDMFolder3DMDTest.class);
		testSuite.addTestSuite(ViewDMFoldersDMDTest.class);
		testSuite.addTestSuite(ViewDMFolder1AddMultipleDocumentsActionsDMDTest.class);
		testSuite.addTestSuite(ViewDMFolder2AddMultipleDocumentsActionsDMDTest.class);
		testSuite.addTestSuite(ViewDMFolder3AddMultipleDocumentsActionsDMDTest.class);
		testSuite.addTestSuite(ViewDMFolder1AddMultipleDocumentsDetailsDMDTest.class);
		testSuite.addTestSuite(ViewDMFolder2AddMultipleDocumentsDetailsDMDTest.class);
		testSuite.addTestSuite(ViewDMFolder3AddMultipleDocumentsDetailsDMDTest.class);
		testSuite.addTestSuite(TearDownDMDFolderTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}