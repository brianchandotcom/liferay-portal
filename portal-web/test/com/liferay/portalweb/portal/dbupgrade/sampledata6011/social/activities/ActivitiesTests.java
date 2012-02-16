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

package com.liferay.portalweb.portal.dbupgrade.sampledata6011.social.activities;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivitiesTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageActivitiesTest.class);
		testSuite.addTestSuite(AddPortletActivitiesTest.class);
		testSuite.addTestSuite(AddBlogsEntryCPTest.class);
		testSuite.addTestSuite(AddMBCategoryCPTest.class);
		testSuite.addTestSuite(PostMBCategoryThreadMessageCPTest.class);
		testSuite.addTestSuite(ViewActivityBlogsEntryTest.class);
		testSuite.addTestSuite(ViewActivityMBCategoryThreadMessageTest.class);

		return testSuite;
	}
}