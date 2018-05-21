/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.directory.builder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author William Newbury
 */
@RunWith(PowerMockRunner.class)
public class UserGroupsBuilderTest extends BaseDirectoryBuilderTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		directoryBuilder = new UserGroupsBuilder();
	}

	@Test
	public void testBuildDirectoriesDefaultFilter() throws Exception {
		doTestBuildDirectories();
	}

	@Test
	public void testValidAttributes() {
		doTestValidAttributes("objectclass", "organizationalUnit", "top", "*");
		doTestValidAttributes("ou", "User Groups", "*");
	}

}