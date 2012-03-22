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

package com.liferay.portalweb.portlet.messageboards;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.messageboards.category.CategoryTestPlan;
import com.liferay.portalweb.portlet.messageboards.lar.LARTestPlan;
import com.liferay.portalweb.portlet.messageboards.mbcategory.MBCategoryTestPlan;
import com.liferay.portalweb.portlet.messageboards.mbmessage.MBMessageTestPlan;
import com.liferay.portalweb.portlet.messageboards.message.MessageTestPlan;
import com.liferay.portalweb.portlet.messageboards.portlet.PortletTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageBoardsTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(CategoryTestPlan.suite());
		testSuite.addTest(LARTestPlan.suite());
		testSuite.addTest(MBCategoryTestPlan.suite());
		testSuite.addTest(MBMessageTestPlan.suite());
		testSuite.addTest(MessageTestPlan.suite());
		testSuite.addTest(PortletTestPlan.suite());

		return testSuite;
	}

}