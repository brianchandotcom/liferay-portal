/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class ScopeUtilTest {

	@AfterClass
	public static void tearDownClass() throws Exception {
		_groupLocalServiceUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		Mockito.when(
			_mockGroup.getGroupId()
		).thenReturn(
			_REMOTE_SCOPE_GROUP_ID
		);

		Mockito.when(
			_mockGroup.getExternalReferenceCode()
		).thenReturn(
			_REMOTE_SCOPE_ERC
		);
	}

	@Test
	public void testGetItemGroup() {
		Group localGroup = Mockito.mock(Group.class);
		String invalidERC = RandomTestUtil.randomString();

		Mockito.when(
			GroupLocalServiceUtil.fetchGroup(_LOCAL_SCOPE_GROUP_ID)
		).thenReturn(
			localGroup
		);

		Mockito.when(
			GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				_REMOTE_SCOPE_ERC, _COMPANY_ID)
		).thenReturn(
			_mockGroup
		);

		Mockito.when(
			GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				invalidERC, _COMPANY_ID)
		).thenReturn(
			null
		);

		String[] inputs = {null, "", "null", _REMOTE_SCOPE_ERC, invalidERC};

		Group[] expectedOutputs = {
			localGroup, localGroup, localGroup, _mockGroup, null
		};

		for (int i = 0; i < inputs.length; i++) {
			Assert.assertEquals(
				expectedOutputs[i],
				ScopeUtil.getItemGroup(
					_COMPANY_ID, inputs[i], _LOCAL_SCOPE_GROUP_ID));
		}
	}

	@Test
	public void testGetItemGroupId() {
		Mockito.when(
			GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				_REMOTE_SCOPE_ERC, _COMPANY_ID)
		).thenReturn(
			_mockGroup
		);

		String invalidERC = RandomTestUtil.randomString();

		Mockito.when(
			GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				invalidERC, _COMPANY_ID)
		).thenReturn(
			null
		);

		String[] inputs = {null, "", "null", _REMOTE_SCOPE_ERC, invalidERC};

		Long[] expectedOutputs = {
			_LOCAL_SCOPE_GROUP_ID, _LOCAL_SCOPE_GROUP_ID, _LOCAL_SCOPE_GROUP_ID,
			_REMOTE_SCOPE_GROUP_ID, null
		};

		for (int i = 0; i < inputs.length; i++) {
			Assert.assertEquals(
				expectedOutputs[i],
				ScopeUtil.getItemGroupId(
					_COMPANY_ID, inputs[i], _LOCAL_SCOPE_GROUP_ID));
		}
	}

	@Test
	public void testGetItemScopeExternalReferenceCodeWithERC()
		throws Exception {

		Group localGroup = Mockito.mock(Group.class);

		Mockito.when(
			localGroup.getExternalReferenceCode()
		).thenReturn(
			_LOCAL_SCOPE_ERC
		);

		Mockito.when(
			GroupLocalServiceUtil.getGroup(_LOCAL_SCOPE_GROUP_ID)
		).thenReturn(
			localGroup
		);

		String[] inputs = {null, _LOCAL_SCOPE_ERC, _REMOTE_SCOPE_ERC};

		String[] expectedOutputs = {null, null, _REMOTE_SCOPE_ERC};

		for (int i = 0; i < inputs.length; i++) {
			Assert.assertEquals(
				expectedOutputs[i],
				ScopeUtil.getItemScopeExternalReferenceCode(
					inputs[i], _LOCAL_SCOPE_GROUP_ID));
		}
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _LOCAL_SCOPE_ERC =
		RandomTestUtil.randomString();

	private static final long _LOCAL_SCOPE_GROUP_ID =
		RandomTestUtil.randomLong();

	private static final String _REMOTE_SCOPE_ERC =
		RandomTestUtil.randomString();

	private static final long _REMOTE_SCOPE_GROUP_ID =
		RandomTestUtil.randomLong();

	private static final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);

	@Mock
	private Group _mockGroup = Mockito.mock(Group.class);

}