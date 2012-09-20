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

package com.liferay.portalweb.portlet.messageboards.mbthread;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.messageboards.mbthread.deletemarkasanswermbcategoryquestionreply.DeleteMarkAsAnswerMBCategoryQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.deletembcategorythread.DeleteMBCategoryThreadTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.editmbcategorythreadbody.EditMBCategoryThreadBodyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.editmbcategorythreadbodynull.EditMBCategoryThreadBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.editmbcategorythreadsubject.EditMBCategoryThreadSubjectTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.editmbcategorythreadsubjectnull.EditMBCategoryThreadSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.guestviewmbthreadguestviewoff.Guest_ViewMBThreadGuestViewOffTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.markasanswermbcategorythreadquestionreply.MarkAsAnswerMBCategoryThreadQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.movembcategorythreadtocategory.MoveMBCategoryThreadToCategoryTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.movembcategorythreadtocategoryexplanation.MoveMBCategoryThreadToCategoryExplanationTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategoriesthread.PostNewMBCategoriesThreadTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategoryThreadTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythreadbodynull.PostNewMBCategoryThreadBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythreadquestion.PostNewMBCategoryThreadQuestionTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythreads.PostNewMBCategoryThreadsTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythreadsubjectnull.PostNewMBCategoryThreadSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythreadutf8.PostNewMBCategoryThreadUTF8Tests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbsubcategorythread.PostNewMBSubcategoryThreadTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbsubcategorythreadbodynull.PostNewMBSubcategoryThreadBodyNullTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbsubcategorythreads.PostNewMBSubcategoryThreadsTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbsubcategorythreadsubjectnull.PostNewMBSubcategoryThreadSubjectNullTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbthread.PostNewMBThreadTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbthreads.PostNewMBThreadsTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.previewmbthreaddetails.PreviewMBThreadDetailsTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.ratembcategorythread.RateMBCategoryThreadTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbcategorythreadquestionreply.ReplyMBCategoryThreadQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbcategorythreadreplies.ReplyMBCategoryThreadRepliesTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbcategorythreadreply.ReplyMBCategoryThreadReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbcategorythreadreplyreply.ReplyMBCategoryThreadReplyReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbthreadreplies.ReplyMBThreadRepliesTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbthreadreply.ReplyMBThreadReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.searchmbcategorythread.SearchMBCategoryThreadTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.splitthreadmbcategorythreadreply.SplitThreadMBCategoryThreadReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.splitthreadmbcategorythreadreplybackbutton.SplitThreadMBCategoryThreadReplyBackButtonTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.splitthreadmbcategorythreadreplyexplanation.SplitThreadMBCategoryThreadReplyExplanationTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.splitthreadmbcategorythreadreplyreply.SplitThreadMBCategoryThreadReplyReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.unmarkmbcategorythreadquestionreply.UnmarkMBCategoryThreadQuestionReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbthread.viewmbcategorythreadpostcount.ViewMBCategoryThreadPostCountTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MBThreadTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(
			DeleteMarkAsAnswerMBCategoryQuestionReplyTests.suite());
		testSuite.addTest(DeleteMBCategoryThreadTests.suite());
		testSuite.addTest(EditMBCategoryThreadBodyTests.suite());
		testSuite.addTest(EditMBCategoryThreadBodyNullTests.suite());
		testSuite.addTest(EditMBCategoryThreadSubjectTests.suite());
		testSuite.addTest(EditMBCategoryThreadSubjectNullTests.suite());
		testSuite.addTest(Guest_ViewMBThreadGuestViewOffTests.suite());
		testSuite.addTest(
			MarkAsAnswerMBCategoryThreadQuestionReplyTests.suite());
		testSuite.addTest(MoveMBCategoryThreadToCategoryTests.suite());
		testSuite.addTest(
			MoveMBCategoryThreadToCategoryExplanationTests.suite());
		testSuite.addTest(PostNewMBCategoriesThreadTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadBodyNullTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadQuestionTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadsTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadSubjectNullTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadUTF8Tests.suite());
		testSuite.addTest(PostNewMBSubcategoryThreadTests.suite());
		testSuite.addTest(PostNewMBSubcategoryThreadBodyNullTests.suite());
		testSuite.addTest(PostNewMBSubcategoryThreadsTests.suite());
		testSuite.addTest(PostNewMBSubcategoryThreadSubjectNullTests.suite());
		testSuite.addTest(PostNewMBThreadTests.suite());
		testSuite.addTest(PostNewMBThreadsTests.suite());
		testSuite.addTest(PreviewMBThreadDetailsTests.suite());
		testSuite.addTest(RateMBCategoryThreadTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadQuestionReplyTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadRepliesTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadReplyTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadReplyReplyTests.suite());
		testSuite.addTest(ReplyMBThreadRepliesTests.suite());
		testSuite.addTest(ReplyMBThreadReplyTests.suite());
		testSuite.addTest(SearchMBCategoryThreadTests.suite());
		testSuite.addTest(SplitThreadMBCategoryThreadReplyTests.suite());
		testSuite.addTest(
			SplitThreadMBCategoryThreadReplyBackButtonTests.suite());
		testSuite.addTest(
			SplitThreadMBCategoryThreadReplyExplanationTests.suite());
		testSuite.addTest(SplitThreadMBCategoryThreadReplyReplyTests.suite());
		testSuite.addTest(UnmarkMBCategoryThreadQuestionReplyTests.suite());
		testSuite.addTest(ViewMBCategoryThreadPostCountTests.suite());

		return testSuite;
	}

}