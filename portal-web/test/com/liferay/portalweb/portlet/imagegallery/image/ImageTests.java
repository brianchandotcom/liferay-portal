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

package com.liferay.portalweb.portlet.imagegallery.image;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimage.AddFolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimageimageinvalid.AddFolderImageImageInvalidTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimageimagenull.AddFolderImageImageNullTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimagemultiple.AddFolderImageMultipleTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimagenameduplicate.AddFolderImageNameDuplicateTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimagenamenull.AddFolderImageNameNullTests;
import com.liferay.portalweb.portlet.imagegallery.image.addsubfolderimage.AddSubfolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.deletefolderimage.DeleteFolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.deletesubfolderimage.DeleteSubfolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.editfolderimage.EditFolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.editsubfolderimage.EditSubfolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.searchfolderimage.SearchFolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.searchfolderimagefolderdetails.SearchFolderImageFolderDetailsTests;
import com.liferay.portalweb.portlet.imagegallery.image.viewfolderimageeditingwindow.ViewFolderImageEditingWindowTests;
import com.liferay.portalweb.portlet.imagegallery.image.viewfolderimagemyimages.ViewFolderImageMyImagesTests;
import com.liferay.portalweb.portlet.imagegallery.image.viewfolderimagerecentimages.ViewFolderImageRecentImagesTests;
import com.liferay.portalweb.portlet.imagegallery.image.viewfolderimageslideshow.ViewFolderImageSlideShowTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddFolderImageTests.suite());
		testSuite.addTest(AddFolderImageImageInvalidTests.suite());
		testSuite.addTest(AddFolderImageImageNullTests.suite());
		testSuite.addTest(AddFolderImageMultipleTests.suite());
		testSuite.addTest(AddFolderImageNameDuplicateTests.suite());
		testSuite.addTest(AddFolderImageNameNullTests.suite());
		testSuite.addTest(AddSubfolderImageTests.suite());
		testSuite.addTest(DeleteFolderImageTests.suite());
		testSuite.addTest(DeleteSubfolderImageTests.suite());
		testSuite.addTest(EditFolderImageTests.suite());
		testSuite.addTest(EditSubfolderImageTests.suite());
		testSuite.addTest(SearchFolderImageTests.suite());
		testSuite.addTest(SearchFolderImageFolderDetailsTests.suite());
		testSuite.addTest(ViewFolderImageEditingWindowTests.suite());
		testSuite.addTest(ViewFolderImageMyImagesTests.suite());
		testSuite.addTest(ViewFolderImageRecentImagesTests.suite());
		testSuite.addTest(ViewFolderImageSlideShowTests.suite());

		return testSuite;
	}

}