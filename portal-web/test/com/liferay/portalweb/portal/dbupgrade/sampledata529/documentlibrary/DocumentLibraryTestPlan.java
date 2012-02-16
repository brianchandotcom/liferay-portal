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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.documentlibrary;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.dbupgrade.sampledata529.documentlibrary.document.DocumentTests;
import com.liferay.portalweb.portal.dbupgrade.sampledata529.documentlibrary.documentlock.DocumentLockTests;
import com.liferay.portalweb.portal.dbupgrade.sampledata529.documentlibrary.documentversion.DocumentVersionTests;
import com.liferay.portalweb.portal.dbupgrade.sampledata529.documentlibrary.folder.FolderTests;
import com.liferay.portalweb.portal.dbupgrade.sampledata529.documentlibrary.shortcut.ShortcutTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentLibraryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(DocumentTests.suite());
		testSuite.addTest(DocumentLockTests.suite());
		testSuite.addTest(DocumentVersionTests.suite());
		testSuite.addTest(FolderTests.suite());
		testSuite.addTest(ShortcutTests.suite());

		return testSuite;
	}

}