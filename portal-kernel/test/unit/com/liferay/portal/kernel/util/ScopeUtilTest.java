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
import org.junit.Test;

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

	@Test
	public void testGetItemGroupId() {
		Group group = _getGroup(_REMOTE_SCOPE_ERC, _REMOTE_SCOPE_GROUP_ID);

		Mockito.when(
			GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				_REMOTE_SCOPE_ERC, _COMPANY_ID)
		).thenReturn(
			group
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

		Group localGroup = _getGroup(_LOCAL_SCOPE_ERC, _LOCAL_SCOPE_GROUP_ID);

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

	private Group _getGroup(String externalReferenceCode, long groupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			groupId
		);

		return group;
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

}