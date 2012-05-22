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

package com.liferay.portalweb.kaleo.workflow.workflowtask;

import com.liferay.portalweb.kaleo.workflow.workflowtask.approvetaskwebcontentdetails.ApproveTaskWebContentDetailsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.assertnoactionstaskactions.AssertNoActionsTaskActionsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.assigntometaskwebcontentdetails.AssignToMeTaskWebContentDetailsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.rejecttaskwebcontentdetails.RejectTaskWebContentDetailsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.resubmittaskwebcontentdetails.ResubmitTaskWebContentDetailsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.viewpaginationtaskmbmessage.ViewPaginationTaskMBMessageTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.viewtaskwebcontentassignedtouser.ViewTaskWebContentAssignedToUserTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.viewwithdrawsubmissiontaskwcassignedtome.ViewWithdrawSubmissionTaskWCAssignedToMeTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.viewwithdrawsubmissiontaskwcassignedtomyroles.ViewWithdrawSubmissionTaskWCAssignedToMyRolesTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.withdrawsubmissiontaskwcassignedtomeactions.WithdrawSubmissionTaskWCAssignedToMeActionsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.withdrawsubmissiontaskwcassignedtomedetails.WithdrawSubmissionTaskWCAssignedToMeDetailsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.withdrawsubmissiontaskwcassignedtomyrolesactions.WithdrawSubmissionTaskWCAssignedToMyRolesActionsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.withdrawsubmissiontaskwcassignedtomyrolesdetails.WithdrawSubmissionTaskWCAssignedToMyRolesDetailsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.withdrawsubmissiontaskwccompletedactions.WithdrawSubmissionTaskWCCompletedActionsTests;
import com.liferay.portalweb.kaleo.workflow.workflowtask.withdrawsubmissiontaskwccompleteddetails.WithdrawSubmissionTaskWCCompletedDetailsTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowTaskTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ApproveTaskWebContentDetailsTests.suite());
		testSuite.addTest(AssertNoActionsTaskActionsTests.suite());
		testSuite.addTest(AssignToMeTaskWebContentDetailsTests.suite());
		testSuite.addTest(RejectTaskWebContentDetailsTests.suite());
		testSuite.addTest(ResubmitTaskWebContentDetailsTests.suite());
		testSuite.addTest(ViewPaginationTaskMBMessageTests.suite());
		testSuite.addTest(ViewTaskWebContentAssignedToUserTests.suite());
		testSuite.addTest(
			ViewWithdrawSubmissionTaskWCAssignedToMeTests.suite());
		testSuite.addTest(
			ViewWithdrawSubmissionTaskWCAssignedToMyRolesTests.suite());
		testSuite.addTest(
			WithdrawSubmissionTaskWCAssignedToMeActionsTests.suite());
		testSuite.addTest(
			WithdrawSubmissionTaskWCAssignedToMeDetailsTests.suite());
		testSuite.addTest(
			WithdrawSubmissionTaskWCAssignedToMyRolesActionsTests.suite());
		testSuite.addTest(
			WithdrawSubmissionTaskWCAssignedToMyRolesDetailsTests.suite());
		testSuite.addTest(
			WithdrawSubmissionTaskWCCompletedActionsTests.suite());
		testSuite.addTest(
			WithdrawSubmissionTaskWCCompletedDetailsTests.suite());

		return testSuite;
	}

}