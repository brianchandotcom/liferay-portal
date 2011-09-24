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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut;

import com.liferay.portalweb.portal.BaseTests;
//import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut.adddiscussion.AddDocumentShortcutTests;
//import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut.delete.DeleteShortcutTests;
//import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut.deletediscussion.DeleteShortcutDiscussionTests;
//import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut.permissions.ShortcutPermissionsTests;
//import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut.update.UpdateShortcutTests;
//import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut.updatediscussion.UpdateShortcutDiscussionTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryshortcut.view.ViewShortcutTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentLibraryShortcutTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

//		testSuite.addTest(AddDocumentDiscussionTests.suite());
//		testSuite.addTest(DeleteDocumentTests.suite());
//		testSuite.addTest(DeleteDocumentDiscussionTests.suite());
//		testSuite.addTest(DocumentPermissionsTests.suite());
//		testSuite.addTest(UpdateDocumentTests.suite());
//		testSuite.addTest(UpdateDocumentDiscussionTests.suite());
		testSuite.addTest(ViewShortcutTests.suite());

		return testSuite;
	}

}