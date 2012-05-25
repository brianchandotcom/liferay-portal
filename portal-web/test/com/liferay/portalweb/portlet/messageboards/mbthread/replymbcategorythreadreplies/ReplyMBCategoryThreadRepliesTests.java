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

package com.liferay.portalweb.portlet.messageboards.mbthread.replymbcategorythreadreplies;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.AddMBCategoryTest;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.TearDownMBCategoryTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategoryThreadTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbthread.TearDownMBThreadTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbcategorythreadreply.ReplyMBCategoryThreadReply1Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbcategorythreadreply.ReplyMBCategoryThreadReply2Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.replymbcategorythreadreply.ReplyMBCategoryThreadReply3Test;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmb.AddPageMBTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmb.AddPortletMBTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ReplyMBCategoryThreadRepliesTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(AddMBCategoryTest.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadTest.class);
		testSuite.addTestSuite(ReplyMBCategoryThreadReply1Test.class);
		testSuite.addTestSuite(ViewMBCategoryThreadReply1Test.class);
		testSuite.addTestSuite(ReplyMBCategoryThreadReply2Test.class);
		testSuite.addTestSuite(ViewMBCategoryThreadReply2Test.class);
		testSuite.addTestSuite(ReplyMBCategoryThreadReply3Test.class);
		testSuite.addTestSuite(ViewMBCategoryThreadReply3Test.class);
		testSuite.addTestSuite(TearDownMBCategoryTest.class);
		testSuite.addTestSuite(TearDownMBThreadTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}