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

package com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroup.AddUGUserGroupTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroupnameasterisk.AddUGUserGroupNameAsteriskTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroupnamecomma.AddUGUserGroupNameCommaTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroupnameduplicate.AddUGUserGroupNameDuplicateTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroupnamenull.AddUGUserGroupNameNullTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroupnamenumber.AddUGUserGroupNameNumberTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroups.AddUGUserGroupsTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.assignmembersugusergroupuser.AssignMembersUGUserGroupUserTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.deleteugusergroupuser.DeleteUGUserGroupUserTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.removemembersugusergroupuser.RemoveMembersUGUserGroupUserTests;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.searchugusergroup.SearchUGUserGroupTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UGUserGroupTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddUGUserGroupTests.suite());
		testSuite.addTest(AddUGUserGroupNameAsteriskTests.suite());
		testSuite.addTest(AddUGUserGroupNameCommaTests.suite());
		testSuite.addTest(AddUGUserGroupNameDuplicateTests.suite());
		testSuite.addTest(AddUGUserGroupNameNullTests.suite());
		testSuite.addTest(AddUGUserGroupNameNumberTests.suite());
		testSuite.addTest(AddUGUserGroupsTests.suite());
		testSuite.addTest(AssignMembersUGUserGroupUserTests.suite());
		testSuite.addTest(DeleteUGUserGroupUserTests.suite());
		testSuite.addTest(RemoveMembersUGUserGroupUserTests.suite());
		testSuite.addTest(SearchUGUserGroupTests.suite());

		return testSuite;
	}

}