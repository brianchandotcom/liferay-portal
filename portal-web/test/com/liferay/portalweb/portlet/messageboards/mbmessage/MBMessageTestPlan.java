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
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbcategoriesthreadmessage.PostNewMBCategoriesThreadMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbcategorythreadmessage.PostNewMBCategoryThreadMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbcategorythreadsmessage.PostNewMBCategoryThreadsMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbthreadmessage.PostNewMBThreadMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbthreadsmessage.PostNewMBThreadsMessageTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbcategorythreadmessagereplies.ReplyMBCategoryThreadMessageRepliesTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbcategorythreadmessagereply.ReplyMBCategoryThreadMessageReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbcategorythreadmessagereplyreply.ReplyMBCategoryThreadMessageReplyReplyTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereplies.ReplyMBThreadMessageRepliesTests;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereply.ReplyMBThreadMessageReplyTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessageTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(PostNewMBCategoriesThreadMessageTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadMessageTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadsMessageTests.suite());
		testSuite.addTest(PostNewMBThreadMessageTests.suite());
		testSuite.addTest(PostNewMBThreadsMessageTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadMessageRepliesTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadMessageReplyTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadMessageReplyReplyTests.suite());
		testSuite.addTest(ReplyMBThreadMessageRepliesTests.suite());
		testSuite.addTest(ReplyMBThreadMessageReplyTests.suite());

		return testSuite;
	}

}