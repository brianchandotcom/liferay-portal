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

package com.liferay.portalweb.portlet.shopping.archivesetup.restorearchivesetup;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.shopping.archivesetup.savearchivesetup.SaveArchiveSetupTest;
import com.liferay.portalweb.portlet.shopping.archivesetup.savearchivesetup.TearDownArchiveSetupTest;
import com.liferay.portalweb.portlet.shopping.portlet.addportletshopping.AddPageShoppingTest;
import com.liferay.portalweb.portlet.shopping.portlet.addportletshopping.AddPortletShoppingTest;
import com.liferay.portalweb.portlet.shopping.portlet.configureportletacceptedcreditcard.ConfigurePortletAcceptedCreditCardTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class RestoreArchiveSetupTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageShoppingTest.class);
		testSuite.addTestSuite(AddPortletShoppingTest.class);
		testSuite.addTestSuite(SaveArchiveSetupTest.class);
		testSuite.addTestSuite(ConfigurePortletAcceptedCreditCardTest.class);
		testSuite.addTestSuite(RestoreArchiveSetupTest.class);
		testSuite.addTestSuite(TearDownArchiveSetupTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}