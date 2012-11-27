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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.blogs.blogsentry.addblogsentrycp.TearDownBlogsEntryCPTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(SetupTest.class);
		testSuite.addTestSuite(AssertNoTagsInTagsAdminTest.class);
		testSuite.addTestSuite(AssertNoTagsInSelectTagsTest.class);
		testSuite.addTestSuite(AddTagTest.class);
		testSuite.addTestSuite(AddAmpersandTagTest.class);
		testSuite.addTestSuite(AddApostropheTagTest.class);
		testSuite.addTestSuite(AddAsteriskTagTest.class);
		testSuite.addTestSuite(AddAtTagTest.class);
		testSuite.addTestSuite(AddBackSlashTagTest.class);
		testSuite.addTestSuite(AddBracketTagTest.class);
		testSuite.addTestSuite(AddCompareCharacterTagTest.class);
		testSuite.addTestSuite(AddCurlyBraceTagTest.class);
		testSuite.addTestSuite(AddColonTagTest.class);
		testSuite.addTestSuite(AddDuplicateTagTest.class);
		testSuite.addTestSuite(AddEqualSignTagTest.class);
		testSuite.addTestSuite(AddForwardSlashTagTest.class);
		testSuite.addTestSuite(AddNullTagTest.class);
		testSuite.addTestSuite(AddPercentTagTest.class);
		testSuite.addTestSuite(AddPlusTagTest.class);
		testSuite.addTestSuite(AddPoundTagTest.class);
		testSuite.addTestSuite(AddQuestionTagTest.class);
		testSuite.addTestSuite(AddQuoteTagTest.class);
		testSuite.addTestSuite(AddSemiColonTagTest.class);
		testSuite.addTestSuite(AddTildeTagTest.class);
		testSuite.addTestSuite(AddTagThroughJavaScriptTest.class);
		testSuite.addTestSuite(AddMultipleTagsTest.class);
		testSuite.addTestSuite(AssertTagsInTagsAdminTest.class);
		testSuite.addTestSuite(AssertTagsInSelectTagsTest.class);
		testSuite.addTestSuite(SelectTagsTest.class);
		testSuite.addTestSuite(RemoveTagThroughSelectTagTest.class);
		testSuite.addTestSuite(AssertAutoSuggestionDropDownTest.class);
		testSuite.addTestSuite(SelectTagThroughAutoSuggestionTest.class);
		testSuite.addTestSuite(SearchTagsTest.class);
		testSuite.addTestSuite(RemoveTagThroughJavaScriptTest.class);
		testSuite.addTestSuite(SearchRemovedTagTest.class);
		testSuite.addTestSuite(DeleteTagTest.class);
		testSuite.addTestSuite(AssertDeletedTagDoesNotPersistTest.class);
		testSuite.addTestSuite(SuggestionTagTest.class);
		testSuite.addTestSuite(AddUTFTagTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryCPTest.class);
		testSuite.addTestSuite(TearDownTagTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}