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

package com.liferay.portalweb.demo.dynamicdata.kaleoticketdefinitionworkflow;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTicketDefinitionWorkflowTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageKaleoFormsTest.class);
		testSuite.addTestSuite(AddPortletKaleoFormsTest.class);
		testSuite.addTestSuite(AddUserJavascriptDeveloperTest.class);
		testSuite.addTestSuite(AddUserKaleoDeveloperTest.class);
		testSuite.addTestSuite(AddUserQAEngineerTest.class);
		testSuite.addTestSuite(AddUserQAManagerTest.class);
		testSuite.addTestSuite(AddUserProjectManagerTest.class);
		testSuite.addTestSuite(AddUserCodeReviewerTest.class);
		testSuite.addTestSuite(AddRoleJavascriptDeveloperTest.class);
		testSuite.addTestSuite(AddRoleKaleoDeveloperTest.class);
		testSuite.addTestSuite(AddRoleQAEngineerTest.class);
		testSuite.addTestSuite(AddRoleQAManagerTest.class);
		testSuite.addTestSuite(AddRoleProjectManagerTest.class);
		testSuite.addTestSuite(AddRoleCodeReviewerTest.class);
		testSuite.addTestSuite(DefineRoleJavascriptDeveloperTest.class);
		testSuite.addTestSuite(DefineRoleKaleoDeveloperTest.class);
		testSuite.addTestSuite(DefineRoleQAEngineerTest.class);
		testSuite.addTestSuite(DefineRoleQAManagerTest.class);
		testSuite.addTestSuite(DefineRoleProjectManagerTest.class);
		testSuite.addTestSuite(DefineRoleCodeReviewerTest.class);
		testSuite.addTestSuite(AssignRoleJavascriptDeveloperTest.class);
		testSuite.addTestSuite(AssignRoleKaleoDeveloperTest.class);
		testSuite.addTestSuite(AssignRoleQAEngineerTest.class);
		testSuite.addTestSuite(AssignRoleQAManagerTest.class);
		testSuite.addTestSuite(AssignRoleProjectManagerTest.class);
		testSuite.addTestSuite(AssignRoleCodeReviewerTest.class);
		testSuite.addTestSuite(SignInUsersTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AddDataDefinitionTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldPriorityTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldComponentTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldSummaryTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldAffectsVersionsTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldDescriptionTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldAttachmentsTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldTestedRevisionTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldTestedStatusTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldPullRequestURLTest.class);
		testSuite.addTestSuite(EditDataDefinitionFieldStatusTest.class);
		testSuite.addTestSuite(AddTemplateTicketCreationTest.class);
		testSuite.addTestSuite(AddTemplateDeveloperViewTest.class);
		testSuite.addTestSuite(AddTemplateQAViewTest.class);
		testSuite.addTestSuite(AddTemplateProjectManagerViewTest.class);
		testSuite.addTestSuite(AddTemplateCodeReviewViewTest.class);
		testSuite.addTestSuite(AddWorkflowDefinitionTest.class);
		testSuite.addTestSuite(AddProcessKFTest.class);
		testSuite.addTestSuite(AddTaskKaleoTicketKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(KD_SignInTest.class);
		testSuite.addTestSuite(KD_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(KD_CompleteFormInProgressTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(CR_SignInTest.class);
		testSuite.addTestSuite(CR_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(CR_CompleteFormResolveTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(QAE_SignInTest.class);
		testSuite.addTestSuite(QAE_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(QAE_CompleteFormCloseTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(QAM_SignInTest.class);
		testSuite.addTestSuite(QAM_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(QAM_ViewTaskKaleoTicketClosedKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(PM_SignInTest.class);
		testSuite.addTestSuite(PM_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(PM_CompletedTaskKaleoTicketKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(ViewCompletedTaskKaleoTicketKFTest.class);
		testSuite.addTestSuite(AddTaskJavascriptTicketKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(JD_SignInTest.class);
		testSuite.addTestSuite(JD_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(JD_CompleteFormInReviewTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(CR_SignInTest.class);
		testSuite.addTestSuite(CR_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(CR_RollbackTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(JD_SignInTest.class);
		testSuite.addTestSuite(JD_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(JD_SubmitTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(CR_SignInTest.class);
		testSuite.addTestSuite(CR_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(CR_CompleteFormResolveTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(QAE_SignInTest.class);
		testSuite.addTestSuite(QAE_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(QAE_CompleteFormFailedTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(QAM_SignInTest.class);
		testSuite.addTestSuite(QAM_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(QAM_ViewTaskJavascriptTicketFailedKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(JD_SignInTest.class);
		testSuite.addTestSuite(JD_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(JD_CompleteFormInReviewTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(CR_SignInTest.class);
		testSuite.addTestSuite(CR_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(CR_CompleteFormResolveTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(QAE_SignInTest.class);
		testSuite.addTestSuite(QAE_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(QAE_CompleteFormCloseTaskKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(QAM_SignInTest.class);
		testSuite.addTestSuite(QAM_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(QAM_ViewTaskJavascriptTicketClosedKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(PM_SignInTest.class);
		testSuite.addTestSuite(PM_AssignToMeTaskKFTest.class);
		testSuite.addTestSuite(PM_CompletedTaskJavascriptTicketKFTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(ViewCompletedTaskJavascriptTicketKFTest.class);

		return testSuite;
	}
}