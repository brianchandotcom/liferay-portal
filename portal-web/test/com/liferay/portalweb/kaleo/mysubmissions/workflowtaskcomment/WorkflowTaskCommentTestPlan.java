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

package com.liferay.portalweb.kaleo.mysubmissions.workflowtaskcomment;

import com.liferay.portalweb.kaleo.mysubmissions.workflowtaskcomment.addwebcontenttaskcommentassignedtome.AddWebContentTaskCommentAssignedToMeTests;
import com.liferay.portalweb.kaleo.mysubmissions.workflowtaskcomment.addwebcontenttaskcommentassignedtomyroles.AddWebContentTaskCommentAssignedToMyRolesTests;
import com.liferay.portalweb.kaleo.mysubmissions.workflowtaskcomment.addwebcontenttaskcommentcompleted.AddWebContentTaskCommentCompletedTests;
import com.liferay.portalweb.kaleo.mysubmissions.workflowtaskcomment.viewnoworkflowaddtaskcommentassignedtome.ViewNoWorkflowAddTaskCommentAssignedToMeTests;
import com.liferay.portalweb.kaleo.mysubmissions.workflowtaskcomment.viewnoworkflowaddtaskcommentassigntomyroles.ViewNoWorkflowAddTaskCommentAssignToMyRolesTests;
import com.liferay.portalweb.kaleo.mysubmissions.workflowtaskcomment.viewnoworkflowaddtaskcommentcompleted.ViewNoWorkflowAddTaskCommentCompletedTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowTaskCommentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWebContentTaskCommentAssignedToMeTests.suite());
		testSuite.addTest(
			AddWebContentTaskCommentAssignedToMyRolesTests.suite());
		testSuite.addTest(AddWebContentTaskCommentCompletedTests.suite());
		testSuite.addTest(
			ViewNoWorkflowAddTaskCommentAssignedToMeTests.suite());
		testSuite.addTest(
			ViewNoWorkflowAddTaskCommentAssignToMyRolesTests.suite());
		testSuite.addTest(ViewNoWorkflowAddTaskCommentCompletedTests.suite());

		return testSuite;
	}

}