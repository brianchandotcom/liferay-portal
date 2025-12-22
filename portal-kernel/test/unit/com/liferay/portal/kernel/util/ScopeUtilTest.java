/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;
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
		Group group = _getGroup(
			RandomTestUtil.randomString(), RandomTestUtil.randomLong());

		Mockito.when(
			GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				group.getExternalReferenceCode(), _COMPANY_ID)
		).thenReturn(
			group
		);

		String[] inputs = {
			null, StringPool.BLANK, "null", group.getExternalReferenceCode(),
			RandomTestUtil.randomString()
		};

		long scopeGroupId = RandomTestUtil.randomLong();

		Long[] expectedOutputs = {
			scopeGroupId, scopeGroupId, scopeGroupId, group.getGroupId(), null
		};

		for (int i = 0; i < inputs.length; i++) {
			Assert.assertEquals(
				expectedOutputs[i],
				ScopeUtil.getItemGroupId(_COMPANY_ID, inputs[i], scopeGroupId));
		}
	}

	@Test
	public void testGetItemScopeExternalReferenceCode() throws Exception {
		Group group = _getGroup(
			RandomTestUtil.randomString(), RandomTestUtil.randomLong());

		Mockito.when(
			GroupLocalServiceUtil.getGroup(group.getGroupId())
		).thenReturn(
			group
		);

		String scopeExternalReferenceCode = RandomTestUtil.randomString();

		String[] inputs = {
			null, StringPool.BLANK, "null", group.getExternalReferenceCode(),
			scopeExternalReferenceCode
		};
		String[] expectedOutputs = {
			null, null, null, null, scopeExternalReferenceCode
		};

		for (int i = 0; i < inputs.length; i++) {
			Assert.assertEquals(
				expectedOutputs[i],
				ScopeUtil.getItemScopeExternalReferenceCode(
					inputs[i], group.getGroupId()));
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

	private static final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);

}