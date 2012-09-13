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

package com.liferay.portalweb.demo.media.dmkaleo2workflow;

import com.liferay.portalweb.demo.media.dmdocumenttypemusic.AddDMDocumentTypeMusicTest;
import com.liferay.portalweb.demo.media.dmdocumenttypemusic.AddDMMetadataSetSongInformationTest;
import com.liferay.portalweb.demo.media.dmdocumenttypemusic.ConfigureDMMaximumFileSizeCPTest;
import com.liferay.portalweb.demo.media.dmdocumenttypemusic.TearDownDMDocumentTypeTest;
import com.liferay.portalweb.demo.media.dmdocumenttypemusic.TearDownDMMetadataSetTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUserPasswordTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder.AddDMFolderTest;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder.TearDownDMFolderTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPageDMTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPortletDMTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMKaleo2WorkflowTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(ConfigureDMMaximumFileSizeCPTest.class);
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(EditUserPasswordTest.class);
		testSuite.addTestSuite(AddMembersLiferaySiteUserSMTest.class);
		testSuite.addTestSuite(EditDMHomeFolderDefaultWorkflowSingleApproverTest.class);
		testSuite.addTestSuite(AddDMFolderTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AddDMFolderDocumentDocSATest.class);
		testSuite.addTestSuite(User_ViewDMFolderDocumentDocTest.class);
		testSuite.addTestSuite(User_ViewTaskDMFolderDocumentDocMSTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AssignToMeTaskDMFolderDocumentDocTest.class);
		testSuite.addTestSuite(RejectTaskDMFolderDocumentDocTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_EditDMFolderDocumentDocSATest.class);
		testSuite.addTestSuite(User_ResubmitDMFolderDocumentDocMSTest.class);
		testSuite.addTestSuite(User_ViewResubmitDMFolderDocumentDocMSTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AssignToMeResubmitTaskDMFolderDocumentDocTest.class);
		testSuite.addTestSuite(ApproveResubmitTaskDMFolderDocumentDocTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_ViewApproveDMFolderDocumentDocTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AddDMFolderMusicTest.class);
		testSuite.addTestSuite(AddDMFolderMusicSubfolderTest.class);
		testSuite.addTestSuite(AddDMMetadataSetSongInformationTest.class);
		testSuite.addTestSuite(AddDMDocumentTypeMusicTest.class);
		testSuite.addTestSuite(EditDMFolderMusicSubfolderDefaultWorkflowSingleApproverTest.class);
		testSuite.addTestSuite(AddDMFolderMusicSubfolderMusicTest.class);
		testSuite.addTestSuite(TearDownDMHomeFolderTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownDMFolderTest.class);
		testSuite.addTestSuite(TearDownDMDocumentTypeTest.class);
		testSuite.addTestSuite(TearDownDMMetadataSetTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}