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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryfolder;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.adddiscussion.AddDocumentDiscussionTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.delete.DeleteDocumentTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.deletediscussion.DeleteDocumentDiscussionTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.permissions.DocumentPermissionsTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.update.UpdateDocumentTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.updatediscussion.UpdateDocumentDiscussionTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.view.ViewDocumentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentLibraryFolderTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDocumentDiscussionTests.suite());
		testSuite.addTest(DeleteDocumentTests.suite());
		testSuite.addTest(DeleteDocumentDiscussionTests.suite());
		testSuite.addTest(DocumentPermissionsTests.suite());
		testSuite.addTest(UpdateDocumentTests.suite());
		testSuite.addTest(UpdateDocumentDiscussionTests.suite());
		testSuite.addTest(ViewDocumentTests.suite());

		return testSuite;
	}

}