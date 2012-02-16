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

package com.liferay.portalweb.portal.dbupgrade.sampledata6011.tags.messageboards;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageBoardsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddCommunityTagsMBTest.class);
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(AddMBCategoryTest.class);
		testSuite.addTestSuite(AddMBMessage1Tag1Test.class);
		testSuite.addTestSuite(AddMBMessage2Tag2Test.class);
		testSuite.addTestSuite(AddMBMessage3Tag3Test.class);
		testSuite.addTestSuite(AddMBMessageATagTest.class);
		testSuite.addTestSuite(AddMBMessageBTagTest.class);
		testSuite.addTestSuite(AddMBMessageCTagTest.class);
		testSuite.addTestSuite(SearchTagsTest.class);
		testSuite.addTestSuite(ViewTagsTest.class);

		return testSuite;
	}
}