/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.signin.portlet;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.signin.portlet.addportletsi.AddPortletSITests;
import com.liferay.portalweb.portlet.signin.portlet.addportletsiduplicate.AddPortletSIDuplicateTests;
import com.liferay.portalweb.portlet.signin.portlet.removeportletsi.RemovePortletSITests;
import com.liferay.portalweb.portlet.signin.portlet.viewportletforgotpassword.ViewPortletForgotPasswordTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletSITests.suite());
		testSuite.addTest(AddPortletSIDuplicateTests.suite());
		testSuite.addTest(RemovePortletSITests.suite());
		testSuite.addTest(ViewPortletForgotPasswordTests.suite());

		return testSuite;
	}

}