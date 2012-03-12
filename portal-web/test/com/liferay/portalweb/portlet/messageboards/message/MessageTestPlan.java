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

package com.liferay.portalweb.portlet.messageboards.message;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessage.AddCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagebodynull.AddCategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagemultiple.AddCategoryMessageMultipleTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagequestion.AddCategoryMessageQuestionTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagereply.AddCategoryMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagereplymultiple.AddCategoryMessageReplyMultipleTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessagesubjectnull.AddCategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.message.addcategorymessageutf8.AddCategoryMessageUTF8Tests;
import com.liferay.portalweb.portlet.messageboards.message.addmessage.AddMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.addsubcategorymessage.AddSubcategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.addsubcategorymessagebodynull.AddSubcategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.message.addsubcategorymessagemultiple.AddSubcategoryMessageMultipleTests;
import com.liferay.portalweb.portlet.messageboards.message.addsubcategorymessagesubjectnull.AddSubcategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.message.deletecategorymessage.DeleteCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.deletemarkasanswercategorymessagequestionreply.DeleteMarkAsAnswerCategoryMessageQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.message.editcategorymessagebody.EditCategoryMessageBodyTests;
import com.liferay.portalweb.portlet.messageboards.message.editcategorymessagebodynull.EditCategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.message.editcategorymessagesubject.EditCategoryMessageSubjectTests;
import com.liferay.portalweb.portlet.messageboards.message.editcategorymessagesubjectnull.EditCategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.message.gmailviewmbthreadmessagegmail.Gmail_ViewMBThreadMessageGmailTests;
import com.liferay.portalweb.portlet.messageboards.message.guestviewpermissionsmbthreadmessageguestviewoff.Guest_ViewPermissionsMBThreadMessageGuestViewOffTests;
import com.liferay.portalweb.portlet.messageboards.message.markasanswercategorymessagequestionreply.MarkAsAnswerCategoryMessageQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.message.movecategorymessagetocategory.MoveCategoryMessageToCategoryTests;
import com.liferay.portalweb.portlet.messageboards.message.movecategorymessagetocategoryexplanation.MoveCategoryMessageToCategoryExplanationTests;
import com.liferay.portalweb.portlet.messageboards.message.nextcategorymessage.NextCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.previewmbthreadmessagedetails.PreviewMBThreadMessageDetailsTests;
import com.liferay.portalweb.portlet.messageboards.message.previouscategorymessage.PreviousCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.ratecategorymessage.RateCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.searchcategorymessage.SearchCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.message.unmarkcategorymessagequestionreply.UnmarkCategoryMessageQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.message.viewcategorymessagemyposts.ViewCategoryMessageMyPostsTests;
import com.liferay.portalweb.portlet.messageboards.message.viewcategorymessagepostcount.ViewCategoryMessagePostCountTests;
import com.liferay.portalweb.portlet.messageboards.message.viewcategorymessagerecentposts.ViewCategoryMessageRecentPostsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCategoryMessageTests.suite());
		testSuite.addTest(AddCategoryMessageBodyNullTests.suite());
		testSuite.addTest(AddCategoryMessageMultipleTests.suite());
		testSuite.addTest(AddCategoryMessageQuestionTests.suite());
		testSuite.addTest(AddCategoryMessageReplyTests.suite());
		testSuite.addTest(AddCategoryMessageReplyMultipleTests.suite());
		testSuite.addTest(AddCategoryMessageSubjectNullTests.suite());
		testSuite.addTest(AddCategoryMessageUTF8Tests.suite());
		testSuite.addTest(AddMessageTests.suite());
		testSuite.addTest(AddSubcategoryMessageTests.suite());
		testSuite.addTest(AddSubcategoryMessageBodyNullTests.suite());
		testSuite.addTest(AddSubcategoryMessageMultipleTests.suite());
		testSuite.addTest(AddSubcategoryMessageSubjectNullTests.suite());
		testSuite.addTest(DeleteCategoryMessageTests.suite());
		testSuite.addTest(
			DeleteMarkAsAnswerCategoryMessageQuestionReplyTests.suite());
		testSuite.addTest(EditCategoryMessageBodyTests.suite());
		testSuite.addTest(EditCategoryMessageBodyNullTests.suite());
		testSuite.addTest(EditCategoryMessageSubjectTests.suite());
		testSuite.addTest(EditCategoryMessageSubjectNullTests.suite());
		testSuite.addTest(Gmail_ViewMBThreadMessageGmailTests.suite());
		testSuite.addTest(
			Guest_ViewPermissionsMBThreadMessageGuestViewOffTests.suite());
		testSuite.addTest(
			MarkAsAnswerCategoryMessageQuestionReplyTests.suite());
		testSuite.addTest(MoveCategoryMessageToCategoryTests.suite());
		testSuite.addTest(
			MoveCategoryMessageToCategoryExplanationTests.suite());
		testSuite.addTest(NextCategoryMessageTests.suite());
		testSuite.addTest(PreviewMBThreadMessageDetailsTests.suite());
		testSuite.addTest(PreviousCategoryMessageTests.suite());
		testSuite.addTest(RateCategoryMessageTests.suite());
		testSuite.addTest(SearchCategoryMessageTests.suite());
		testSuite.addTest(UnmarkCategoryMessageQuestionReplyTests.suite());
		testSuite.addTest(ViewCategoryMessageMyPostsTests.suite());
		testSuite.addTest(ViewCategoryMessagePostCountTests.suite());
		testSuite.addTest(ViewCategoryMessageRecentPostsTests.suite());

		return testSuite;
	}

}