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

package com.liferay.portalweb.portlet.documentlibrary.dmdocumentversion;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentlibrary.dmdocumentversion.deleteversiondmfolderdocumentfile10test.DeleteVersionDMFolderDocumentFile10Tests;
import com.liferay.portalweb.portlet.documentlibrary.dmdocumentversion.deleteversiondmfolderdocumentfile11test.DeleteVersionDMFolderDocumentFile11Tests;
import com.liferay.portalweb.portlet.documentlibrary.dmdocumentversion.deleteversiondmfolderdocumenttitle10test.DeleteVersionDMFolderDocumentTitle10Tests;
import com.liferay.portalweb.portlet.documentlibrary.dmdocumentversion.deleteversiondmfolderdocumenttitle11test.DeleteVersionDMFolderDocumentTitle11Tests;
import com.liferay.portalweb.portlet.documentlibrary.dmdocumentversion.revertversiondmfolderdocumentfile10test.RevertVersionDMFolderDocumentFile10Tests;
import com.liferay.portalweb.portlet.documentlibrary.dmdocumentversion.revertversiondmfolderdocumenttitle10test.RevertVersionDMFolderDocumentTitle10Tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMDocumentVersionTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(DeleteVersionDMFolderDocumentFile10Tests.suite());
		testSuite.addTest(DeleteVersionDMFolderDocumentFile11Tests.suite());
		testSuite.addTest(DeleteVersionDMFolderDocumentTitle10Tests.suite());
		testSuite.addTest(DeleteVersionDMFolderDocumentTitle11Tests.suite());
		testSuite.addTest(RevertVersionDMFolderDocumentFile10Tests.suite());
		testSuite.addTest(RevertVersionDMFolderDocumentTitle10Tests.suite());

		return testSuite;
	}

}