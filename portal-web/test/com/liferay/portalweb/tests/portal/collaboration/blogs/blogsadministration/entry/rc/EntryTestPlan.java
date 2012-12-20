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

package com.liferay.portalweb.tests.portal.collaboration.blogs.blogsadministration.entry.rc;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryTestPlan extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddBlogsEntriesCPTest.class);
		testSuite.addTestSuite(AddBlogsEntryAutoDraftCPTest.class);
		testSuite.addTestSuite(AddBlogsEntryContentNullCPTest.class);
		testSuite.addTestSuite(AddBlogsEntryTitle150CharactersCPTest.class);
		testSuite.addTestSuite(AddBlogsEntryTitle151CharactersCPTest.class);
		testSuite.addTestSuite(AddBlogsEntryTitleEscapeCharacterCPTest.class);
		testSuite.addTestSuite(AddBlogsEntryTitleNullCPTest.class);
		testSuite.addTestSuite(AddBlogsEntryTrackbackCPTest.class);
		testSuite.addTestSuite(DeleteBlogsEntryActionsCPTest.class);
		testSuite.addTestSuite(DeleteBlogsEntryDetailsCPTest.class);
		testSuite.addTestSuite(DeleteBlogsEntryTitle150CharactersActionsCPTest.class);
		testSuite.addTestSuite(DeleteBlogsEntryTitleEscapeCharacterActionsCPTest.class);
		testSuite.addTestSuite(EditBlogsEntryContentActionsCPTest.class);
		testSuite.addTestSuite(EditBlogsEntryTitleActionsCPTest.class);
		testSuite.addTestSuite(PublishSaveAsDraftBlogsEntryDetailsCPTest.class);
		testSuite.addTestSuite(RateBlogsEntryCPTest.class);
		testSuite.addTestSuite(SaveAsDraftBlogsEntryDetailsCPTest.class);
		testSuite.addTestSuite(SearchBlogsEntryTitleCPTest.class);

		return testSuite;
	}
}