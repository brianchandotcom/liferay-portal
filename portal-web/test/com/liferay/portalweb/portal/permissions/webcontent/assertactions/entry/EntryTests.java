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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.adddiscussion.AddDiscussionTests;
import com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.delete.DeleteTests;
import com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.deletediscussion.DeleteDiscussionTests;
import com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.expire.ExpireTests;
import com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.permissions.PermissionsTests;
import com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.update.UpdateTests;
import com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.updatediscussion.UpdateDiscussionTests;
import com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.view.ViewTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDiscussionTests.suite());
		testSuite.addTest(DeleteTests.suite());
		testSuite.addTest(DeleteDiscussionTests.suite());
		testSuite.addTest(ExpireTests.suite());
		testSuite.addTest(PermissionsTests.suite());
		testSuite.addTest(UpdateTests.suite());
		testSuite.addTest(UpdateDiscussionTests.suite());
		testSuite.addTest(ViewTests.suite());

		return testSuite;
	}

}