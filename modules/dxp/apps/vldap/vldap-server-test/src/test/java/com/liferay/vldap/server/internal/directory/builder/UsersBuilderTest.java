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

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author William Newbury
 */
@RunWith(PowerMockRunner.class)
public class UsersBuilderTest extends BaseDirectoryBuilderTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		directoryBuilder = new UsersBuilder();
	}

	@Test
	public void testBuildDirectories() throws Exception {
		User user = mock(User.class);

		when(
			user.getScreenName()
		).thenReturn(
			"testScreenName"
		);

		when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			Arrays.asList(user)
		);

		doTestBuildDirectories();
	}

	@Test
	public void testValidAttributes() {
		doTestValidAttributes("objectclass", "organizationalUnit", "top", "*");
		doTestValidAttributes("ou", "Users", "*");
	}

}