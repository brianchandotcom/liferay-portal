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
import com.liferay.portalweb.portlet.documentsandmedia.dmcomment.DMCommentTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.DMDocumentTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.DMDocumentTypeTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.DMFolderTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmimage.DMImageTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.dmlar.DMLARTestPlan;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.PortletTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentsAndMediaTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(DMCommentTestPlan.suite());
		testSuite.addTest(DMDocumentTestPlan.suite());
		testSuite.addTest(DMDocumentTypeTestPlan.suite());
		testSuite.addTest(DMFolderTestPlan.suite());
		testSuite.addTest(DMImageTestPlan.suite());
		testSuite.addTest(DMLARTestPlan.suite());
		testSuite.addTest(PortletTestPlan.suite());

		return testSuite;
	}

}