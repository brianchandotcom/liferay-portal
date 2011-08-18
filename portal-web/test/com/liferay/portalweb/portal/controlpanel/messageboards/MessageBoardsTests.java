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

package com.liferay.portalweb.portal.controlpanel.messageboards;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageBoardsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddCategoryTest.class);
		testSuite.addTestSuite(AddSubcategoryTest.class);
		testSuite.addTestSuite(AddMessageTest.class);
		testSuite.addTestSuite(AddReplyMessageTest.class);
		testSuite.addTestSuite(AddSecondReplyMessageTest.class);
		testSuite.addTestSuite(AddThirdReplyMessageTest.class);
		testSuite.addTestSuite(SearchTest.class);
		testSuite.addTestSuite(SplitThreadTest.class);
		testSuite.addTestSuite(AddSecondSubcategoryTest.class);
		testSuite.addTestSuite(AddNullEntryTest.class);
		testSuite.addTestSuite(AddNullTitleTest.class);
		testSuite.addTestSuite(AddDeletableMessageTest.class);
		testSuite.addTestSuite(RecentPostsTest.class);
		testSuite.addTestSuite(AddMoveCategoryTest.class);
		testSuite.addTestSuite(MoveThreadTest.class);
		testSuite.addTestSuite(DeleteMessageTest.class);
		testSuite.addTestSuite(EditCategoryTest.class);
		testSuite.addTestSuite(EditMessageTest.class);
		testSuite.addTestSuite(AddQuestionThreadTest.class);
		testSuite.addTestSuite(AddAnswerThreadTest.class);
		testSuite.addTestSuite(TearDownMBCategoryCPTest.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(TearDownMBCategoryCPTest.class);

		return testSuite;
	}

}