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

package com.liferay.portalweb.portlet.documentsandmedia.dmimage;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.adddmfolderimage.AddDMFolderImageTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.adddmfolderimagefilenull.AddDMFolderImageFileNullTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.adddmfolderimages.AddDMFolderImagesTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.adddmfolderimagetitleduplicate.AddDMFolderImageTitleDuplicateTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.adddmfolderimagetitlenull.AddDMFolderImageTitleNullTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.adddmsubfolderimage.AddDMSubfolderImageTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.deletedmfolderimageactions.DeleteDMFolderImageActionsTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.deletedmsubfolderimageactions.DeleteDMSubfolderImageActionsTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.editdmfolderimagedetails.EditDMFolderImageDetailsTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.editdmsubfolderimagedetails.EditDMSubfolderImageDetailsTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.movedmfolder1imagetofolder2.MoveDMFolder1ImageToFolder2Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.searchdmfolderimage.SearchDMFolderImageTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.searchdmfolderimagefolderdetails.SearchDMFolderImageFolderDetailsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMImageTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMFolderImageTests.suite());
		testSuite.addTest(AddDMFolderImageFileNullTests.suite());
		testSuite.addTest(AddDMFolderImagesTests.suite());
		testSuite.addTest(AddDMFolderImageTitleDuplicateTests.suite());
		testSuite.addTest(AddDMFolderImageTitleNullTests.suite());
		testSuite.addTest(AddDMSubfolderImageTests.suite());
		testSuite.addTest(DeleteDMFolderImageActionsTests.suite());
		testSuite.addTest(DeleteDMSubfolderImageActionsTests.suite());
		testSuite.addTest(EditDMFolderImageDetailsTests.suite());
		testSuite.addTest(EditDMSubfolderImageDetailsTests.suite());
		testSuite.addTest(MoveDMFolder1ImageToFolder2Tests.suite());
		testSuite.addTest(SearchDMFolderImageTests.suite());
		testSuite.addTest(SearchDMFolderImageFolderDetailsTests.suite());

		return testSuite;
	}

}