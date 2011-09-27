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

package com.liferay.portalweb.portlet.documentlibrary;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.documentlibrary.comment.CommentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.DocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.folder.FolderTests;
import com.liferay.portalweb.portlet.documentlibrary.image.ImageTests;
import com.liferay.portalweb.portlet.documentlibrary.lar.LARTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.PortletTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentLibraryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(CommentTests.suite());
		testSuite.addTest(DocumentTests.suite());
		testSuite.addTest(FolderTests.suite());
		testSuite.addTest(ImageTests.suite());
		testSuite.addTest(LARTests.suite());
		testSuite.addTest(PortletTests.suite());

		return testSuite;
	}

}