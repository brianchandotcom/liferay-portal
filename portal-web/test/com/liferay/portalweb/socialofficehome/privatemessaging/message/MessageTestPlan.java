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

package com.liferay.portalweb.socialofficehome.privatemessaging.message;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.addpmmessage.AddPMMessageTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.addpmmessageapostrophequotation.AddPMMessageApostropheQuotationTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.addpmmessageattachment.AddPMMessageAttachmentTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.addpmmessagemultiple.AddPMMessageMultipleTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.addpmmessagenonuser.AddPMMessageNonUserTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.backtomessagespmmessagedetails.BackToMessagesPMMessageDetailsTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.deletepmmessage.DeletePMMessageTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.deletepmmessageall.DeletePMMessageAllTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.deletepmmessagedetails.DeletePMMessageDetailsTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.markasunreadpmmessageall.MarkAsUnreadPMMessageAllTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.selectallpmmessage.SelectAllPMMessageTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.selectnonepmmessage.SelectNonePMMessageTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.sous2deletepmmessage.SOUs2_DeletePMMessageTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.sousaddpmmessagereply.SOUs_AddPMMessageReplyTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.sousaddpmmessagereplyattachment.SOUs_AddPMMessageReplyAttachmentTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.sousdeletepmmessage.SOUs_DeletePMMessageTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.sousmarkasunreadpmmessage.SOUs_MarkAsUnreadPMMessageTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.sousmarkasunreadpmmessagedetails.SOUs_MarkAsUnreadPMMessageDetailsTests;
import com.liferay.portalweb.socialofficehome.privatemessaging.message.viewpmmessage.ViewPMMessageTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPMMessageTests.suite());
		testSuite.addTest(AddPMMessageApostropheQuotationTests.suite());
		testSuite.addTest(AddPMMessageAttachmentTests.suite());
		testSuite.addTest(AddPMMessageMultipleTests.suite());
		testSuite.addTest(AddPMMessageNonUserTests.suite());
		testSuite.addTest(BackToMessagesPMMessageDetailsTests.suite());
		testSuite.addTest(DeletePMMessageTests.suite());
		testSuite.addTest(DeletePMMessageAllTests.suite());
		testSuite.addTest(DeletePMMessageDetailsTests.suite());
		//testSuite.addTest(Gmail_ViewPMMessageTests.suite());
		testSuite.addTest(MarkAsUnreadPMMessageAllTests.suite());
		testSuite.addTest(SelectAllPMMessageTests.suite());
		testSuite.addTest(SelectNonePMMessageTests.suite());
		testSuite.addTest(SOUs2_DeletePMMessageTests.suite());
		testSuite.addTest(SOUs_AddPMMessageReplyTests.suite());
		testSuite.addTest(SOUs_AddPMMessageReplyAttachmentTests.suite());
		testSuite.addTest(SOUs_DeletePMMessageTests.suite());
		testSuite.addTest(SOUs_MarkAsUnreadPMMessageTests.suite());
		testSuite.addTest(SOUs_MarkAsUnreadPMMessageDetailsTests.suite());
		testSuite.addTest(ViewPMMessageTests.suite());

		return testSuite;
	}

}