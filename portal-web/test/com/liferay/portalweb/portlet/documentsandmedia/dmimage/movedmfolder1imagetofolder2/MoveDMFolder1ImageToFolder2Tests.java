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

package com.liferay.portalweb.portlet.documentsandmedia.dmimage.movedmfolder1imagetofolder2;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder.AddDMFolder1Test;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder.AddDMFolder2Test;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder.TearDownDMFolderTest;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.adddmfolderimage.AddDMFolder1ImageTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPageDMTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPortletDMTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveDMFolder1ImageToFolder2Tests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(AddDMFolder1Test.class);
		testSuite.addTestSuite(AddDMFolder1ImageTest.class);
		testSuite.addTestSuite(AddDMFolder2Test.class);
		testSuite.addTestSuite(MoveDMFolder1ImageToFolder2Test.class);
		testSuite.addTestSuite(ViewMoveDMFolder1ImageToFolder2Test.class);
		testSuite.addTestSuite(TearDownDMFolderTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}