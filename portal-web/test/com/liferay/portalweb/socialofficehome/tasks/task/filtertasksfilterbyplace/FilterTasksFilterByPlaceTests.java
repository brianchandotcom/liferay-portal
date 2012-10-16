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

package com.liferay.portalweb.socialofficehome.tasks.task.filtertasksfilterbyplace;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtome.TearDownTasksTaskTest;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtometag.AddTasksTaskAssignedToMeTagTest;
import com.liferay.portalweb.socialofficehome.tasks.task.filtertasksfilterbytags.AddPageTasksTest;
import com.liferay.portalweb.socialofficehome.tasks.task.filtertasksfilterbytags.AddPortletTasksTest;
import com.liferay.portalweb.socialofficehome.tasks.task.filtertasksfilterbytags.AddTasksTaskAssignedToMeTagPortalTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class FilterTasksFilterByPlaceTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageTasksTest.class);
		testSuite.addTestSuite(AddPortletTasksTest.class);
		testSuite.addTestSuite(AddTasksTaskAssignedToMeTagPortalTest.class);
		testSuite.addTestSuite(AddTasksTaskAssignedToMeTagTest.class);
		testSuite.addTestSuite(FilterTasksFilterByPlaceTest.class);
		testSuite.addTestSuite(TearDownTasksTaskTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}