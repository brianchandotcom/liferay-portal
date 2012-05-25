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

package com.liferay.portalweb.portlet.messageboards.mbthread.replymbthreadreplies;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbthread.PostNewMBThreadTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbthread.TearDownMBThreadTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbthreadreply.ReplyMBThreadReply1Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbthreadreply.ReplyMBThreadReply2Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbthreadreply.ReplyMBThreadReply3Test;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmb.AddPageMBTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmb.AddPortletMBTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ReplyMBThreadRepliesTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(PostNewMBThreadTest.class);
		testSuite.addTestSuite(ReplyMBThreadReply1Test.class);
		testSuite.addTestSuite(ViewMBThreadReply1Test.class);
		testSuite.addTestSuite(ReplyMBThreadReply2Test.class);
		testSuite.addTestSuite(ViewMBThreadReply2Test.class);
		testSuite.addTestSuite(ReplyMBThreadReply3Test.class);
		testSuite.addTestSuite(ViewMBThreadReply3Test.class);
		testSuite.addTestSuite(TearDownMBThreadTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}