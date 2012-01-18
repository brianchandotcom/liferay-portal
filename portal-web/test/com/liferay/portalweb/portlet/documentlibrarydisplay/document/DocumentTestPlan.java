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

package com.liferay.portalweb.portlet.documentlibrarydisplay.document;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentlibrarydisplay.document.adddldocumentdld.AddDLDocumentDLDTests;
import com.liferay.portalweb.portlet.documentlibrarydisplay.document.deletedldocumentmultipledldactions.DeleteDLDocumentMultipleDLDActionsTests;
import com.liferay.portalweb.portlet.documentlibrarydisplay.document.searchfolderdocument.SearchFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrarydisplay.document.viewfolderdocument.ViewFolderDocumentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDLDocumentDLDTests.suite());
		testSuite.addTest(DeleteDLDocumentMultipleDLDActionsTests.suite());
		testSuite.addTest(SearchFolderDocumentTests.suite());
		testSuite.addTest(ViewFolderDocumentTests.suite());

		return testSuite;
	}

}