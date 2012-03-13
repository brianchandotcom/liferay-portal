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

package com.liferay.portalweb.demo.devcon6100.media.dmdraganddropdocument;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocument.TearDownDLDocumentTest;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportlet.AddPageDMTest;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportlet.AddPortletDMTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMDragAndDropDocumentTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(ViewDMPortletTest.class);
		testSuite.addTestSuite(AddDMFolder1Test.class);
		testSuite.addTestSuite(AddDMDocument1Test.class);
		testSuite.addTestSuite(MoveDMDocument1NewFolderFolder1ActionsTest.class);
		testSuite.addTestSuite(ViewMoveDMDocument1NewFolderFolder1Test.class);
		testSuite.addTestSuite(AddDMDocument2Test.class);
		testSuite.addTestSuite(MoveDMDocument2NewFolderFolder1DADTest.class);
		testSuite.addTestSuite(ViewMoveDMDocument2NewFolderFolder1Test.class);
		testSuite.addTestSuite(MoveDMFolder1Document2NewFolderHomeDADTest.class);
		testSuite.addTestSuite(ViewMoveDMFolder1Document2NewFolderHomeTest.class);
		testSuite.addTestSuite(TearDownDLDocumentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}