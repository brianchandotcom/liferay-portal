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

package com.liferay.portalweb.portlet.documentlibrary.image;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.documentlibrary.image.adddlfolderimage.AddDLFolderImageTests;
import com.liferay.portalweb.portlet.documentlibrary.image.adddlfolderimageimagenull.AddDLFolderImageImageNullTests;
import com.liferay.portalweb.portlet.documentlibrary.image.adddlfolderimagemultiple.AddDLFolderImageMultipleTests;
import com.liferay.portalweb.portlet.documentlibrary.image.adddlfolderimagenameduplicate.AddDLFolderImageNameDuplicateTests;
import com.liferay.portalweb.portlet.documentlibrary.image.adddlfolderimagenamenull.AddDLFolderImageNameNullTests;
import com.liferay.portalweb.portlet.documentlibrary.image.adddlsubfolderimage.AddDLSubfolderImageTests;
import com.liferay.portalweb.portlet.documentlibrary.image.deletedlfolderimageactions.DeleteDLFolderImageActionsTests;
import com.liferay.portalweb.portlet.documentlibrary.image.deletedlsubfolderimageactions.DeleteDLSubfolderImageActionsTests;
import com.liferay.portalweb.portlet.documentlibrary.image.editdlfolderimagedetails.EditDLFolderImageDetailsTests;
import com.liferay.portalweb.portlet.documentlibrary.image.editdlsubfolderimagedetails.EditDLSubfolderImageDetailsTests;
import com.liferay.portalweb.portlet.documentlibrary.image.movedlfolder1imagetofolder2.MoveDLFolder1ImageToFolder2Tests;
import com.liferay.portalweb.portlet.documentlibrary.image.searchdlfolderimage.SearchDLFolderImageTests;
import com.liferay.portalweb.portlet.documentlibrary.image.searchdlfolderimagefolderdetails.SearchDLFolderImageFolderDetailsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDLFolderImageTests.suite());
		testSuite.addTest(AddDLFolderImageImageNullTests.suite());
		testSuite.addTest(AddDLFolderImageMultipleTests.suite());
		testSuite.addTest(AddDLFolderImageNameDuplicateTests.suite());
		testSuite.addTest(AddDLFolderImageNameNullTests.suite());
		testSuite.addTest(AddDLSubfolderImageTests.suite());
		testSuite.addTest(DeleteDLFolderImageActionsTests.suite());
		testSuite.addTest(DeleteDLSubfolderImageActionsTests.suite());
		testSuite.addTest(EditDLFolderImageDetailsTests.suite());
		testSuite.addTest(EditDLSubfolderImageDetailsTests.suite());
		testSuite.addTest(MoveDLFolder1ImageToFolder2Tests.suite());
		testSuite.addTest(SearchDLFolderImageTests.suite());
		testSuite.addTest(SearchDLFolderImageFolderDetailsTests.suite());

		return testSuite;
	}

}