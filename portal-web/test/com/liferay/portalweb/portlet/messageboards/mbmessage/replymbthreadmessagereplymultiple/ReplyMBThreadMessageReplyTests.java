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

package com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereplymultiple;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbthreadmessage.PostNewMBThreadMessageTest;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbthreadmessage.TearDownMBThreadTest;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereply.ReplyMBThreadMessageReply1Test;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereply.ReplyMBThreadMessageReply2Test;
import com.liferay.portalweb.portlet.messageboards.mbmessage.replymbthreadmessagereply.ReplyMBThreadMessageReply3Test;
import com.liferay.portalweb.portlet.messageboards.portlet.addportlet.AddPageMBTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportlet.AddPortletMBTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ReplyMBThreadMessageReplyTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(PostNewMBThreadMessageTest.class);
		testSuite.addTestSuite(ReplyMBThreadMessageReply1Test.class);
		testSuite.addTestSuite(ViewMBThreadMessageReply1Test.class);
		testSuite.addTestSuite(ReplyMBThreadMessageReply2Test.class);
		testSuite.addTestSuite(ViewMBThreadMessageReply2Test.class);
		testSuite.addTestSuite(ReplyMBThreadMessageReply3Test.class);
		testSuite.addTestSuite(ViewMBThreadMessageReply3Test.class);
		testSuite.addTestSuite(TearDownMBThreadTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}