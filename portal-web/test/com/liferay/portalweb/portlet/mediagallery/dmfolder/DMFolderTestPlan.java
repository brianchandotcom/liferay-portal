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

package com.liferay.portalweb.portlet.mediagallery.dmfolder;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmfoldermg.AddDMFolderMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmfoldernameduplicatemg.AddDMFolderNameDuplicateMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmfoldernameinvalidmg.AddDMFolderNameInvalidMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmfoldernamenullmg.AddMGFolderNameNullMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmfoldersmg.AddDMFoldersMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmsubfoldermg.AddDMSubfolderMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmsubfoldernameimagenamemg.AddDMSubfolderNameImageNameMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmsubfoldersmg.AddDMSubfoldersMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.editdmfoldermg.EditDMFolderMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.editdmsubfoldermg.EditDMSubfolderMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.movedmsubfoldertofoldermg.MoveDMSubfolderToFolderMGTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMFolderTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMFolderMGTests.suite());
		testSuite.addTest(AddDMFolderNameDuplicateMGTests.suite());
		testSuite.addTest(AddDMFolderNameInvalidMGTests.suite());
		testSuite.addTest(AddMGFolderNameNullMGTests.suite());
		testSuite.addTest(AddDMFoldersMGTests.suite());
		testSuite.addTest(AddDMSubfolderMGTests.suite());
		testSuite.addTest(AddDMSubfolderNameImageNameMGTests.suite());
		testSuite.addTest(AddDMSubfoldersMGTests.suite());
		testSuite.addTest(EditDMFolderMGTests.suite());
		testSuite.addTest(EditDMSubfolderMGTests.suite());
		testSuite.addTest(MoveDMSubfolderToFolderMGTests.suite());

		return testSuite;
	}

}