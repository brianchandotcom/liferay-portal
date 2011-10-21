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

package com.liferay.portalweb.portlet.documentlibrary.portlet;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportlet.AddPortletTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportletduplicate.AddPortletDuplicateTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletdocumentsperpage5.ConfigurePortletDocumentsPerPage5Tests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletenablecommentratings.ConfigurePortletEnableCommentRatingsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletfoldersperpage5.ConfigurePortletFoldersPerPage5Tests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletrootfolderselectfolder.ConfigurePortletRootFolderSelectFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletrootfolderselectsubfolder.ConfigurePortletRootFolderSelectSubfolderTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletshowdocumentcolumns.ConfigurePortletShowDocumentColumnsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletshowfoldersearch.ConfigurePortletShowFolderSearchTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.removeportlet.RemovePortletTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletTests.suite());
		testSuite.addTest(AddPortletDuplicateTests.suite());
		testSuite.addTest(ConfigurePortletDocumentsPerPage5Tests.suite());
		testSuite.addTest(ConfigurePortletEnableCommentRatingsTests.suite());
		testSuite.addTest(ConfigurePortletFoldersPerPage5Tests.suite());
		testSuite.addTest(ConfigurePortletRootFolderSelectFolderTests.suite());
		testSuite.addTest(
			ConfigurePortletRootFolderSelectSubfolderTests.suite());
		testSuite.addTest(ConfigurePortletShowDocumentColumnsTests.suite());
		testSuite.addTest(ConfigurePortletShowFolderSearchTests.suite());
		testSuite.addTest(RemovePortletTests.suite());

		return testSuite;
	}

}