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

package com.liferay.portalweb.portlet.documentlibrary.folder;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.adddlsubfoldernameimagename.AddDLSubfolderNameImageNameTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addfolder.AddFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addfoldermultiple.AddFolderMultipleTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addfoldernameduplicate.AddFolderNameDuplicateTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addfoldernamenull.AddFolderNameNullTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addfoldernamespace.AddFolderNameSpaceTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addfoldernamesymbol.AddFolderNameSymbolTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addsubfolder.AddSubfolderTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addsubfoldermultiple.AddSubfolderMultipleTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.addsubfoldernamedocumentname.AddSubfolderNameDocumentNameTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.deletefolder.DeleteFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.deletesubfolder.DeleteSubfolderTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.editfolder.EditFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.editsubfolder.EditSubfolderTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.movedlsubfoldertofolder2.MoveDLSubfolderToFolder2Tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class FolderTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDLSubfolderNameImageNameTests.suite());
		testSuite.addTest(AddFolderTests.suite());
		testSuite.addTest(AddFolderMultipleTests.suite());
		testSuite.addTest(AddFolderNameDuplicateTests.suite());
		testSuite.addTest(AddFolderNameNullTests.suite());
		testSuite.addTest(AddFolderNameSpaceTests.suite());
		testSuite.addTest(AddFolderNameSymbolTests.suite());
		testSuite.addTest(AddSubfolderTests.suite());
		testSuite.addTest(AddSubfolderMultipleTests.suite());
		testSuite.addTest(AddSubfolderNameDocumentNameTests.suite());
		testSuite.addTest(DeleteFolderTests.suite());
		testSuite.addTest(DeleteSubfolderTests.suite());
		testSuite.addTest(EditFolderTests.suite());
		testSuite.addTest(EditSubfolderTests.suite());
		testSuite.addTest(MoveDLSubfolderToFolder2Tests.suite());

		return testSuite;
	}

}