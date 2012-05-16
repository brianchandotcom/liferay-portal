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

package com.liferay.portalweb.portlet.messageboards.mbmessage;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbcategorymessage.AddMBCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbcategorymessagebodynull.AddMBCategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbcategorymessagemultiple.AddMBCategoryMessageMultipleTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbcategorymessagequestion.AddMBCategoryMessageQuestionTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbcategorymessagereply.AddMBCategoryMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbcategorymessagereplymultiple.AddMBCategoryMessageReplyMultipleTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbcategorymessagesubjectnull.AddMBCategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbcategorymessageutf8.AddMBCategoryMessageUTF8Tests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbmessage.AddMBMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbsubcategorymessage.AddMBSubcategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbsubcategorymessagebodynull.AddMBSubcategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbsubcategorymessagemultiple.AddMBSubcategoryMessageMultipleTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.addmbsubcategorymessagesubjectnull.AddMBSubcategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.deletemarkasanswermbcategorymessagereply.DeleteMarkAsAnswerMBCategoryMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.deletembcategorymessage.DeleteMBCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.editmbcategorymessagebody.EditMBCategoryMessageBodyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.editmbcategorymessagebodynull.EditMBCategoryMessageBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.editmbcategorymessagesubject.EditMBCategoryMessageSubjectTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.editmbcategorymessagesubjectnull.EditMBCategoryMessageSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.guestviewmbthreadmessageguestviewoff.Guest_ViewMBThreadMessageGuestViewOffTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.markasanswermbcategorymessagequestionreply.MarkAsAnswerMBCategoryMessageQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.movembcategorymessagetocategory.MoveMBCategoryMessageToCategoryTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.movembcategorymessagetocategoryexplanation.MoveMBCategoryMessageToCategoryExplanationTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.nextmbcategorymessage.NextMBCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbcategoriesthreadmessage.PostNewMBCategoriesThreadMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbcategorythreadmessage.PostNewMBCategoryThreadMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbcategorythreadsmessage.PostNewMBCategoryThreadsMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbthreadmessage.PostNewMBThreadMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbthreadsmessage.PostNewMBThreadsMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.previewmbthreadmessagedetails.PreviewMBThreadMessageDetailsTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.previousmbcategorymessage.PreviousMBCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.ratembcategorymessage.RateMBCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbcategorythreadmessagereplies.ReplyMBCategoryThreadMessageRepliesTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbcategorythreadmessagereply.ReplyMBCategoryThreadMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbcategorythreadmessagereplyreply.ReplyMBCategoryThreadMessageReplyReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereplies.ReplyMBThreadMessageRepliesTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereply.ReplyMBThreadMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereplymultiple.ReplyMBThreadMessageReplyMultipleTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.searchmbcategorymessage.SearchMBCategoryMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.splitthreadmbcategorymessagereply.SplitThreadMBCategoryMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.splitthreadmbcategorymessagereplybackbutton.SplitThreadMBCategoryMessageReplyBackButtonTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.splitthreadmbcategorymessagereplyexplanation.SplitThreadMBCategoryMessageReplyExplanationTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.splitthreadmbcategorymessagereplyreply.SplitThreadMBCategoryMessageReplyReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.unmarkmbcategorymessagequestionreply.UnmarkMBCategoryMessageQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.viewmbcategorymessagemyposts.ViewMBCategoryMessageMyPostsTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.viewmbcategorymessagepostcount.ViewMBCategoryMessagePostCountTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.viewmbcategorymessagerecentposts.ViewMBCategoryMessageRecentPostsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessageTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddMBCategoryMessageTests.suite());
		testSuite.addTest(AddMBCategoryMessageBodyNullTests.suite());
		testSuite.addTest(AddMBCategoryMessageMultipleTests.suite());
		testSuite.addTest(AddMBCategoryMessageQuestionTests.suite());
		testSuite.addTest(AddMBCategoryMessageReplyTests.suite());
		testSuite.addTest(AddMBCategoryMessageReplyMultipleTests.suite());
		testSuite.addTest(AddMBCategoryMessageSubjectNullTests.suite());
		testSuite.addTest(AddMBCategoryMessageUTF8Tests.suite());
		testSuite.addTest(AddMBMessageTests.suite());
		testSuite.addTest(AddMBSubcategoryMessageTests.suite());
		testSuite.addTest(AddMBSubcategoryMessageBodyNullTests.suite());
		testSuite.addTest(AddMBSubcategoryMessageMultipleTests.suite());
		testSuite.addTest(AddMBSubcategoryMessageSubjectNullTests.suite());
		testSuite.addTest(DeleteMBCategoryMessageTests.suite());
		testSuite.addTest(DeleteMarkAsAnswerMBCategoryMessageReplyTests.suite());
		testSuite.addTest(EditMBCategoryMessageBodyTests.suite());
		testSuite.addTest(EditMBCategoryMessageBodyNullTests.suite());
		testSuite.addTest(EditMBCategoryMessageSubjectTests.suite());
		testSuite.addTest(EditMBCategoryMessageSubjectNullTests.suite());
		testSuite.addTest(Guest_ViewMBThreadMessageGuestViewOffTests.suite());
		testSuite.addTest(
			MarkAsAnswerMBCategoryMessageQuestionReplyTests.suite());
		testSuite.addTest(MoveMBCategoryMessageToCategoryTests.suite());
		testSuite.addTest(
			MoveMBCategoryMessageToCategoryExplanationTests.suite());
		testSuite.addTest(NextMBCategoryMessageTests.suite());
		testSuite.addTest(PostNewMBCategoriesThreadMessageTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadMessageTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadsMessageTests.suite());
		testSuite.addTest(PostNewMBThreadMessageTests.suite());
		testSuite.addTest(PostNewMBThreadsMessageTests.suite());
		testSuite.addTest(PreviewMBThreadMessageDetailsTests.suite());
		testSuite.addTest(PreviousMBCategoryMessageTests.suite());
		testSuite.addTest(RateMBCategoryMessageTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadMessageRepliesTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadMessageReplyTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadMessageReplyReplyTests.suite());
		testSuite.addTest(ReplyMBThreadMessageRepliesTests.suite());
		testSuite.addTest(ReplyMBThreadMessageReplyTests.suite());
		testSuite.addTest(ReplyMBThreadMessageReplyMultipleTests.suite());
		testSuite.addTest(SearchMBCategoryMessageTests.suite());
		testSuite.addTest(SplitThreadMBCategoryMessageReplyTests.suite());
		testSuite.addTest(
			SplitThreadMBCategoryMessageReplyBackButtonTests.suite());
		testSuite.addTest(
			SplitThreadMBCategoryMessageReplyExplanationTests.suite());
		testSuite.addTest(SplitThreadMBCategoryMessageReplyReplyTests.suite());
		testSuite.addTest(UnmarkMBCategoryMessageQuestionReplyTests.suite());
		testSuite.addTest(ViewMBCategoryMessageMyPostsTests.suite());
		testSuite.addTest(ViewMBCategoryMessagePostCountTests.suite());
		testSuite.addTest(ViewMBCategoryMessageRecentPostsTests.suite());

		return testSuite;
	}

}