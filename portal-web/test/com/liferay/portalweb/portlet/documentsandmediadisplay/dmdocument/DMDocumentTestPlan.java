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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.adddmdocumentdmd.AddDMDocumentDMDTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.adddmdocumentsdmd.AddDMDocumentsDMDTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.adddmfolderdocumentdmd.AddDMFolderDocumentDMDTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.adddmfolderdocumentsdmd.AddDMFolderDocumentsDMDTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.deletedmdocumentsdmdactions.DeleteDMDocumentsDMDActionsTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.searchdmfolderdocumentdmd.SearchDMFolderDocumentDMDTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMDocumentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMDocumentDMDTests.suite());
		testSuite.addTest(AddDMDocumentsDMDTests.suite());
		testSuite.addTest(AddDMFolderDocumentDMDTests.suite());
		testSuite.addTest(AddDMFolderDocumentsDMDTests.suite());
		testSuite.addTest(DeleteDMDocumentsDMDActionsTests.suite());
		testSuite.addTest(SearchDMFolderDocumentDMDTests.suite());

		return testSuite;
	}

}