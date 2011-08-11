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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PollsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddQuestionTest.class);
		testSuite.addTestSuite(AddVoteTest.class);
		testSuite.addTestSuite(ViewQuestionGraphsTest.class);
		testSuite.addTestSuite(AddQuestion2Test.class);
		testSuite.addTestSuite(EditQuestionTest.class);
		testSuite.addTestSuite(ExpireQuestionTest.class);
		testSuite.addTestSuite(AssertExpiredQuestionTest.class);
		testSuite.addTestSuite(AssertDeleteChoiceTest.class);
		testSuite.addTestSuite(AddNullTitlePollTest.class);
		testSuite.addTestSuite(AddNullDescriptionPollTest.class);
		testSuite.addTestSuite(AddNullChoicePollTest.class);
		testSuite.addTestSuite(TearDownQuestionTest.class);

		return testSuite;
	}

}