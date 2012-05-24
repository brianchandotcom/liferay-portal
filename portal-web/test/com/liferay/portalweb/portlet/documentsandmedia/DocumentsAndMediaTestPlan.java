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

package com.liferay.portalweb.portlet.documentsandmedia;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmedia.dmbreadcrumb.BreadcrumbTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmcomment.CommentTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.DocumentTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.DocumentTypeTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocumentversion.DMDocumentVersionTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.FolderTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.ImageTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmlar.LARTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.PortletTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentsAndMediaTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(BreadcrumbTestPlan.suite());
		testSuite.addTest(CommentTestPlan.suite());
		testSuite.addTest(DMDocumentVersionTestPlan.suite());
		testSuite.addTest(DocumentTestPlan.suite());
		testSuite.addTest(DocumentTypeTestPlan.suite());
		testSuite.addTest(FolderTestPlan.suite());
		testSuite.addTest(ImageTestPlan.suite());
		testSuite.addTest(LARTestPlan.suite());
		testSuite.addTest(PortletTestPlan.suite());

		return testSuite;
	}

}